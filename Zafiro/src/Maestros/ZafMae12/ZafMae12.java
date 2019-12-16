/*
 * ZafMae12.java
 *
 * Created on 20 de octubre de 2004, 12:50 PM
 */
package Maestros.ZafMae12;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import Librerias.ZafToolBar.*;
import Librerias.ZafParSis.*; 
import Librerias.ZafUtil.*;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;

/**
 *
 * @author  jresabala
 * corregido por jsalazar 20/oct/2005
 * ultima actualizacion 28/dic/2005
 */
public class ZafMae12 extends javax.swing.JInternalFrame {
        Connection conCab; //Variable para conexion a la Base de Datos
        Statement stmCab; //Variable para ejecucion de sentencias SQL
        ResultSet rstCab;//Variable para manipular registro de la tabla en ejecucion de la tabala ciudad
        String strSql,  strAux; //Variable de tipo cadena en la cual se almacenara el Query
        String strTit, strMsg;// Variable para mostrar mensajes
        int intCon;//Contador de vector
        int intConPai;//Variable que contiene lo que se selecciona del combo pais
        int intCodPai;//Variable que almacena el codigo de pais
        char chrEst;//Variable para guardar el estado del registro
        char chrFlgEst;//Bandera para saber en que estado se encuentra la forma 
        Vector vecCodPai;//Vector que guarda el codigo del pais
        JOptionPane oppMsg;//Objeto de tipo OptionPane para presentar Mensajes
        thetoolbar objTooBar;//Objeto de tipo objTooBar para poder manipular la clase ZafToolBar
        ZafUtil objUti;//Objeto del tipo de la clase ZafUtil, el cual me va a permitir manipular los diferentes metodos de esta clase
        ZafParSis objZafParSis;//Objeto que me permitira obtener los parametros del sistema, como podria ser la ruta de la base de datos, etc.  
        javax.swing.JInternalFrame jfrThis;//Variable que se refiere al JInternalFrame 
        String strFlt;//EL filtro de la Consulta actual
        char chrFlgCon = 'N'; //Bandera para saber si se ha perdido el foco en un objeto
        char chrFlgMsj = 'N'; //Bandera para saber si ya se presento el mensaje
        char chrFlgLst = 'N'; //Bandera para saber si ya se presento el mensaje al pèrder el foco el objeto
        boolean blnCam = false;//Detecta que se hizo cambios en el documento
        boolean blnCln = false; //Si los TextField estan vacios
        LisTxt objLisCam;// Instancia de clase que detecta cambios
        
        
        private String strDesCorPro, strDesLarPro;
        private ZafVenCon vcoPro;
        private String strSQL;
        
    /** Creates new form ZafMae12 */
    public ZafMae12(ZafParSis obj) {
        initComponents();
        addListenerCambios();
     try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            objUti = new ZafUtil();
            oppMsg=new JOptionPane();
            objTooBar = new thetoolbar(this);
            this.getContentPane().add(objTooBar,"South");
            llenarCboPai();
            this.setTitle(objZafParSis.getNombreMenu() + " v0.1");
            txtCodCiu.setBackground(objZafParSis.getColorCamposSistema());
            jfrThis=this;
            blnCam = false;
            this.setBounds(10,10, 700, 450);  
            
            
            configurarVenConPro();
            txtCodPro.setVisible(false);
            txtCodPro.setEnabled(false);
            
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }                  
    }        
    public void llenarCboPai()
	{
	vecCodPai = new Vector();   
        objUti.llenarCbo_F1(this,objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), 
                                 objZafParSis.getClaveBaseDatos(), "SELECT co_pai, tx_deslar FROM tbm_pai where st_reg='A' ORDER BY co_pai", 
                                 cboPai, vecCodPai); 
        cboPai.setSelectedIndex(0);	
        }
    
    public boolean grabarInformacion(Connection con){
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        int intNumReg=1;
        boolean blnRes=true;
                try{
                    if (con!= null){
                        stm = con.createStatement();
                        intCodPai=Integer.parseInt(vecCodPai.get(cboPai.getSelectedIndex()).toString());
                        if(cboEstReg.getSelectedIndex()==0){
                            chrEst='A';}
                        else{
                            chrEst='I';}                            
                            
                            strSql = "";
                            strSql = strSql + "SELECT MAX(co_ciu)+1 as co_ciu from tbm_ciu";
                            rst=stm.executeQuery(strSql);                                    
                            if (rst.next())
                                intNumReg=rst.getInt("co_ciu");

                            strSql="";	
                            strSql+=" INSERT INTO tbm_ciu (co_ciu, co_pai, tx_desCor, tx_desLar, st_reg, co_pro)";
                            strSql+=" VALUES (";
                            strSql+=" " + intNumReg + "";
                            strSql+=" ," + intCodPai + "";
                            strSql+=" ," + ((strAux=txtDesCor.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'"); //" ,'" + txtDesCor.getText() + "'";
                            strSql+=" ," + ((strAux=txtDesLar.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");// " ,'" + txtDesLar.getText() +"'";
                            strSql+=" ,'" + chrEst + "'";
                            strSql+=" ," + txtCodPro.getText() + "";
                            strSql+=" )";
                            System.out.println("INSERT: " + strSql );
                            stm.executeUpdate(strSql);
                    }
            }
            catch (SQLException evt){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
            }
            catch (Exception evt){
                blnRes=false;
                objUti.mostrarMsgErr_F1(jfrThis, evt);
            }
   
        return blnRes;
}


/*Funcion que sirve para eliminar los datos de la empresa*/    
    public boolean eliDat()
    {
    try{
	java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos()); 		
        try{
			if (con != null)
			{
				java.sql.PreparedStatement pstReg;
				strSql="";	
				strSql = " DELETE FROM tbm_ciu WHERE co_ciu=" + txtCodCiu.getText() + "";
                                pstReg = con.prepareStatement(strSql);
                                pstReg.executeUpdate();
				con.commit();
				con.close();
                                limpiarPant();
			}
		}catch (SQLException evt){
                    con.rollback();
                    con.close();
                    objUti.mostrarMsgErr_F1(jfrThis, evt);
                    return false;
		}
    }catch (Exception evt){
            objUti.mostrarMsgErr_F1(jfrThis, evt);
            return false;
    }
    return true;
}
    
          
    
/*Funcion que sirve para modificar los datos de la empresa*/
    public boolean modDat()
    {
        try{
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos()); 		
            intCodPai=Integer.parseInt(vecCodPai.get(cboPai.getSelectedIndex()).toString());
                        
                        if(cboEstReg.getSelectedIndex()==0){
                            chrEst='A';}
                        else{
                            chrEst='I';}
                    try{
                        if (con != null){
				java.sql.PreparedStatement pstReg;
				strSql="";	
				strSql = strSql + " UPDATE tbm_ciu SET";
                                strSql = strSql + " co_pai=" + intCodPai + "";
                                strSql = strSql + ", co_pro=" + txtCodPro.getText() + "";
				strSql = strSql + ", tx_desCor=" + ((strAux=txtDesCor.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'"); //" ,'" + txtDesCor.getText() + "'";
				strSql = strSql + ", tx_desLar=" + ((strAux=txtDesLar.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");// " ,'" + txtDesLar.getText() +"'";
				strSql = strSql + ", st_reg='" + chrEst + "'";
                                strSql = strSql + " WHERE co_ciu=" + txtCodCiu.getText() + "";
                                pstReg = con.prepareStatement(strSql); 
                                pstReg.executeUpdate();
                                con.commit();
				con.close();                               
			}
		}catch (SQLException evt){                       
                    con.rollback();
                    con.close();
                    objUti.mostrarMsgErr_F1(jfrThis, evt);
                        return false;
		}
        }catch (Exception evt){
                objUti.mostrarMsgErr_F1(jfrThis, evt);
                return false;
        }
        return true;
    }
    
    /**procedimiento que limpia todas las cajas de texto que existen en el frame*/            
    public void limpiarPant()
        { 
        txtCodCiu.setText("");
        txtDesCor.setText("");
        txtDesLar.setText("");
        txtCodPro.setText("");
        txtDesCorPro.setText("");
        txtDesLarPro.setText("");
        cboPai.setSelectedIndex(0);
        cboEstReg.setSelectedIndex(0);
        
        } 
    
     private String FilSql() {               
                String sqlFiltro = "";

                //Agregando filtro por codigo de ciudad
                strAux = txtCodCiu.getText();
                if(!txtCodCiu.getText().equals(""))
                    sqlFiltro = sqlFiltro + " (co_ciu) = " + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "";

                //Agregando filtro por descripcion corta de la ciudad
                strAux = txtDesCor.getText();
                if(!txtDesCor.getText().equals("")){
                    if (!sqlFiltro.equals(""))sqlFiltro = sqlFiltro + " AND" ;
                    sqlFiltro = sqlFiltro + " LOWER(tx_descor) LIKE '" + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
                }

                //Agregando filtro por descripcion larga de la ciudad
                strAux = txtDesLar.getText();
                if(!txtDesLar.getText().equals("")){
                    if (!sqlFiltro.equals(""))sqlFiltro = sqlFiltro + " AND" ;
                    sqlFiltro = sqlFiltro + " LOWER(tx_deslar) LIKE '" + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
                }
                return sqlFiltro ;
    }
     
       private int Mensaje(){
                String strTit, strMsg;
                strTit="Mensaje del sistema Zafiro";
                strMsg="áDeseá guardar los cambios efectuados a este registro?\n";
                strMsg+="Si no guarda los cambios perdera toda la informacián que no haya guardado.";
                javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                return obj.showConfirmDialog(jfrThis ,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);                
            }

       private void addListenerCambios(){
            objLisCam = new LisTxt();
            txtCodCiu.setText("");
            txtCodCiu.getDocument().addDocumentListener(objLisCam);
            txtDesCor.getDocument().addDocumentListener(objLisCam);
            txtDesLar.getDocument().addDocumentListener(objLisCam);
       }   
    

     /* Clase de tipo DocumentListener para detectar los cambios en los textcomponent*/
    class LisTxt implements javax.swing.event.DocumentListener {
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            blnCam = true;
        }
        
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            blnCam = true;
        }
        
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            blnCam = true;
        }
    }
       
       public boolean txtCln(){
        if ((txtCodCiu.getText().equals("")) && (txtDesCor.getText().equals("")) && (txtDesLar.getText().equals(""))){    
            blnCln = false;
        }
        else {
            blnCln = true;
        }
        return blnCln;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblMaeCiu = new javax.swing.JLabel();
        panCon = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        lblCodCiu = new javax.swing.JLabel();
        lblCodPai = new javax.swing.JLabel();
        lblDesCor = new javax.swing.JLabel();
        lblDesLar = new javax.swing.JLabel();
        lblEstReg = new javax.swing.JLabel();
        txtCodCiu = new javax.swing.JTextField();
        txtDesCor = new javax.swing.JTextField();
        txtDesLar = new javax.swing.JTextField();
        cboEstReg = new javax.swing.JComboBox();
        cboPai = new javax.swing.JComboBox();
        lblObs1 = new javax.swing.JLabel();
        txtCodPro = new javax.swing.JTextField();
        txtDesCorPro = new javax.swing.JTextField();
        txtDesLarPro = new javax.swing.JTextField();
        butPro = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
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

        lblMaeCiu.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        lblMaeCiu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMaeCiu.setText("Maestro Ciudades");
        getContentPane().add(lblMaeCiu, java.awt.BorderLayout.NORTH);

        panCon.setLayout(new java.awt.BorderLayout());

        panGen.setLayout(null);

        lblCodCiu.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodCiu.setText("Còdigo Ciudad:");
        panGen.add(lblCodCiu);
        lblCodCiu.setBounds(4, 8, 100, 20);

        lblCodPai.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodPai.setText("Còdigo pais:");
        panGen.add(lblCodPai);
        lblCodPai.setBounds(8, 48, 100, 20);

        lblDesCor.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDesCor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDesCor.setText("Descripciòn corta:");
        panGen.add(lblDesCor);
        lblDesCor.setBounds(8, 88, 104, 20);

        lblDesLar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDesLar.setText("Descripciòn larga:");
        panGen.add(lblDesLar);
        lblDesLar.setBounds(8, 128, 100, 20);

        lblEstReg.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblEstReg.setText("Estado Registro:");
        panGen.add(lblEstReg);
        lblEstReg.setBounds(8, 210, 100, 20);
        panGen.add(txtCodCiu);
        txtCodCiu.setBounds(118, 8, 100, 20);

        txtDesCor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorFocusLost(evt);
            }
        });
        panGen.add(txtDesCor);
        txtDesCor.setBounds(118, 88, 100, 20);

        txtDesLar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarFocusLost(evt);
            }
        });
        panGen.add(txtDesLar);
        txtDesLar.setBounds(118, 128, 200, 20);

        cboEstReg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Inactivo" }));
        cboEstReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstRegActionPerformed(evt);
            }
        });
        panGen.add(cboEstReg);
        cboEstReg.setBounds(108, 210, 100, 20);

        cboPai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPaiActionPerformed(evt);
            }
        });
        panGen.add(cboPai);
        cboPai.setBounds(118, 48, 180, 20);

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObs1.setText("Código de provincia:");
        panGen.add(lblObs1);
        lblObs1.setBounds(8, 170, 108, 14);
        panGen.add(txtCodPro);
        txtCodPro.setBounds(88, 170, 30, 20);

        txtDesCorPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorProActionPerformed(evt);
            }
        });
        txtDesCorPro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorProFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorProFocusLost(evt);
            }
        });
        panGen.add(txtDesCorPro);
        txtDesCorPro.setBounds(118, 170, 90, 20);

        txtDesLarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarProActionPerformed(evt);
            }
        });
        txtDesLarPro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarProFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarProFocusLost(evt);
            }
        });
        panGen.add(txtDesLarPro);
        txtDesLarPro.setBounds(208, 170, 350, 20);

        butPro.setText("...");
        butPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butProActionPerformed(evt);
            }
        });
        panGen.add(butPro);
        butPro.setBounds(558, 170, 20, 20);

        tabGen.addTab("General", panGen);

        panCon.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCon, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-600)/2, (screenSize.height-358)/2, 600, 358);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDesLarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarFocusLost
    if (chrFlgLst == 'N') {
       if (txtDesLar.getText().length()>30 && chrFlgLst == 'N'){ 
            strTit="Mensaje del sistema Zafiro";
            strMsg="El campo <<Descripcián larga>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
            txtDesLar.setText("");
            txtDesLar.requestFocus();
            chrFlgMsj = 'S';
            chrFlgLst = 'S';
        } 
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
         }  
    }//GEN-LAST:event_txtDesLarFocusLost

    private void txtDesCorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorFocusLost
    if (chrFlgLst == 'N') {
       if (txtDesCor.getText().length()>3 && chrFlgLst == 'N'){ 
            strTit="Mensaje del sistema Zafiro";
            strMsg="El campo <<Descripcián corta>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
            txtDesCor.setText("");
            txtDesCor.requestFocus();
            chrFlgMsj = 'S';
            chrFlgLst = 'S';
        } 
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
         }
    }//GEN-LAST:event_txtDesCorFocusLost

    private void cboPaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPaiActionPerformed
                blnCam = true;
    }//GEN-LAST:event_cboPaiActionPerformed

    private void cboEstRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstRegActionPerformed
                blnCam = true;
    }//GEN-LAST:event_cboEstRegActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
     javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="áEstá seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_exitForm

    private void txtDesCorProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorProActionPerformed
        // TODO add your handling code here:
        txtDesCorPro.transferFocus();
    }//GEN-LAST:event_txtDesCorProActionPerformed

    private void txtDesCorProFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorProFocusGained
        // TODO add your handling code here:
        strDesCorPro = txtDesCorPro.getText();
        txtDesCorPro.selectAll();
    }//GEN-LAST:event_txtDesCorProFocusGained

    private void txtDesCorProFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorProFocusLost
        if (!txtDesCorPro.getText().equalsIgnoreCase(strDesCorPro)) {
            if (txtDesCorPro.getText().equals("")) {
                txtCodPro.setText("");
                txtDesLarPro.setText("");
            } else {
                mostrarVenConPro(1);
            }
        } else {
            txtDesCorPro.setText(strDesCorPro);
        }
    }//GEN-LAST:event_txtDesCorProFocusLost

    private void txtDesLarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarProActionPerformed
        // TODO add your handling code here:
        txtDesLarPro.transferFocus();
    }//GEN-LAST:event_txtDesLarProActionPerformed

    private void txtDesLarProFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarProFocusGained
        // TODO add your handling code here:
        strDesLarPro = txtDesLarPro.getText();
        txtDesLarPro.selectAll();
    }//GEN-LAST:event_txtDesLarProFocusGained

    private void txtDesLarProFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarProFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarPro.getText().equalsIgnoreCase(strDesLarPro)) {
            if (txtDesLarPro.getText().equals("")) {
                txtCodPro.setText("");
                txtDesCorPro.setText("");
            } else {
                mostrarVenConPro(2);
            }
        } else {
            txtDesLarPro.setText(strDesLarPro);
        }
    }//GEN-LAST:event_txtDesLarProFocusLost

    private void butProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butProActionPerformed
        // TODO add your handling code here:
        mostrarVenConPro(0);
    }//GEN-LAST:event_butProActionPerformed
     private void exitForm() 
    {
        dispose();
    }     
    private void MensajeInf(String strMensaje){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Zafiro";
            obj.showMessageDialog(jfrThis,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
   /**
     * Esta funcián muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
      /**
     * Esta funcián se encarga de agregar el listener "DocumentListener" a los objTooBars
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        String strAux="áDesea guardar los cambios efectuados a áste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la informacián que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnCam=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }   
    
  /**
     * Esta funcián permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (!cargarCabReg())
            {
                MensajeInf("Error al cargar registro");
                blnCam=false;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }        
    private boolean cargarCabReg()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                java.sql.Statement stm=con.createStatement();
                String strSQL = "  SELECT a1.co_ciu, a1.tx_descor, a1.tx_deslar, a1.co_pai, a1.st_reg, a1.st_regrep, a1.co_pro "+ 
                                "  ,  a2.tx_desCor AS tx_desCorPro,  a2.tx_desLar AS tx_desLarPro"+
                                "  FROM tbm_ciu AS a1"+ 
                                "  LEFT OUTER JOIN tbm_pro AS a2"+ 
                                "  ON a1.co_pro=a2.co_pro"+ 
                                " Where co_ciu=" + rstCab.getString("co_ciu") + " ORDER BY a1.tx_deslar"  ;
                System.out.println("cargarCabReg: " + strSQL);
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodCiu.setText(rst.getString("co_ciu"));                    
                    txtCodCiu.setText(((rst.getString("co_ciu")==null)?"":rst.getString("co_ciu")));
                    txtDesCor.setText(((rst.getString("tx_descor")==null)?"":rst.getString("tx_descor")));
                    txtDesLar.setText(((rst.getString("tx_deslar")==null)?"":rst.getString("tx_deslar")));
                    
                    txtCodPro.setText(((rst.getString("co_pro")==null)?"":rst.getString("co_pro")));
                    txtDesCorPro.setText(((rst.getString("tx_desCorPro")==null)?"":rst.getString("tx_desCorPro")));
                    txtDesLarPro.setText(((rst.getString("tx_desLarPro")==null)?"":rst.getString("tx_desLarPro")));
                    
                    
                    strAux=rst.getString("co_pai");
                    objUti.setItemCombo(cboPai,vecCodPai,strAux);
                    String strReg = rst.getString("st_reg");
                    if(strReg.equals("I")){
                        cboEstReg.setSelectedIndex(1);}
                    else{
                        cboEstReg.setSelectedIndex(0);}
                    
                    String strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarPant();
                }
            
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Mostrar la posicián relativa del registro.
                intPosRel=rstCab.getRow();
                rstCab.last();
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
                rstCab.absolute(intPosRel);
                blnCam=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    public class thetoolbar extends ZafToolBar{
        public thetoolbar (javax.swing.JInternalFrame ifrtem){
            super(ifrtem, objZafParSis);
        }
        public boolean beforeInsertar()
        {
            return true;
        }
        
        public boolean beforeConsultar()
        {
            return true;
        }

        public boolean beforeModificar()
        {
            return true;
        }

        public boolean beforeEliminar()
        {
            return true;
        }

        public boolean beforeAnular()
        {
            return true;
        }

        public boolean beforeImprimir()
        {
            return true;
        }

        public boolean beforeVistaPreliminar()
        {
            return true;
        }

        public boolean beforeAceptar()
        {
            return true;
        }
        
        public boolean beforeCancelar()
        {
            return true;
        }
        
            public boolean anular()
        {
            String strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnCam=false;
            return true;
        }

        public void clickAnterior(){
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnCam)
                    {
                        if (isRegPro())
                        {
                            rstCab.previous();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.previous();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickFin() {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnCam)
                    {
                        if (isRegPro())
                        {
                            rstCab.last();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.last();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInicio(){
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnCam)
                    {
                        if (isRegPro())
                        {
                            rstCab.first();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.first();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInsertar()
        {
            try
            {
                if (blnCam)
                {
                    isRegPro();
                }
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                txtDesCor.requestFocus();
                txtCodCiu.setEditable(false);
                limpiarPant();
                chrFlgEst = 'I';
                blnCam=false;
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickSiguiente(){
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnCam)
                    {
                        if (isRegPro())
                        {
                            rstCab.next();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.next();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        public boolean anularReg(){
            try{
                java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                try{
                    if (con!=null){
                        con.setAutoCommit(false);
                        java.sql.PreparedStatement pstReg;
                        String strSQL = "";
                        strSQL = " Update tbm_ciu set st_reg = 'I' " +
                                 " where co_ciu =" + txtCodCiu.getText();
                        pstReg = con.prepareStatement(strSQL);
                        pstReg.executeUpdate();
                        con.commit();
                        con.close();
                    }
                }catch(SQLException e){
                    con.rollback();
                    con.close();
                    objUti.mostrarMsgErr_F1(this, e);
                    return false;                    
                }
            }catch(Exception e){
                objUti.mostrarMsgErr_F1(this, e);
                return false;
            }
            return true;
        }
        
        public boolean consultar() 
        {
            consultarReg();
            return true;
        }

        public boolean eliminar()
        {
            try
            {
                String strAux=objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado"))
                {
                    MensajeInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }
                if (!eliDat())
                    return false;
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast())
                {
                    rstCab.next();
                    cargarReg();
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarPant();
                }
                blnCam=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }

        public boolean insertar()
        {
            try{
                java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if(con!=null){
                    con.setAutoCommit(false);
                    if (grabarInformacion(con)){
                        con.commit();
                        con.close();
                        limpiarPant();

                    }
                    else{
                        con.rollback();
                        return false;
                    }
                    limpiarPant();
                    blnCam=false;
                }

            }
            catch(Exception e){
                return false;
            }

            return true;
        }

        public boolean modificar()
        {
            String strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                MensajeInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                if (mensaje("Para modificar primero debe reactivar la ciudad \n áDesea reactivarlo?")==JOptionPane.YES_OPTION){
                    return Reactivar();
                }              
            }
//            if (!validaCampos())
//                return false;
            if (!modDat())
                return false;
            blnCam=false;
            return true;
        }
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (blnCam)
                {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                    {
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarPant();
            blnCam=false;
            return blnRes;
        }
  
        public void clickAceptar() {
        }
        
        public void clickAnular() {
        }
        
        public void clickCancelar() {
          txtCln();   
        }
        
        
        public void clickConsultar() {
             chrFlgEst = 'C';
        }
        
        
        public void clickEliminar() {
        }
        
       
        public void clickImprimir() {
        }
    
        public void clickModificar() {
            txtCodCiu.setEditable(false);
             chrFlgEst = 'M';
        }
       
        public void clickVisPreliminar() {
        }
        
        public boolean consultarReg() {
             return _consultar(FilSql());
        }
      
        public boolean aceptar() {
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
            objTooBar.setEstado('w');
            consultarReg();
            
            return true;
        }
        
        public boolean afterModificar() {
            return true;
        }
        
        public boolean afterVistaPreliminar() {
            return true;
        }     
        public boolean imprimir() {
            return true;
        }
        
        public boolean vistaPreliminar() {
            return true;
        }
        
    }
 private boolean Reactivar()
    {
        try{
            java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            try{
                if (con!=null){
                    java.sql.PreparedStatement pstReg;
                    String strSQL="";
                    strSQL = " Update tbm_ciu set st_reg= 'A' where co_ciu = " + txtCodCiu.getText();
//                    System.out.println(strSQL);
                    pstReg = con.prepareStatement(strSQL);
                    pstReg.executeUpdate();
                    con.commit();
                    con.close();
                    objTooBar.setEstadoRegistro("Activo");
                }
            }catch(SQLException e){
                con.rollback();
                con.close();
                objUti.mostrarMsgErr_F1(this, e);   
                return false;
            }
        }catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            return false;
        }
        return true;        
    }
    private int mensaje(String strMsg){
        return oppMsg.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);                
    }        
     private boolean _consultar(String strFiltro){
       strFlt = strFiltro;  
    try{
                    conCab = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos()); 		
                    if (conCab!=null){
                        stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                         /*
                         * Agregando el Sql de Consulta para el maestro de empresas
                         */
                        strSql = "";
                        strSql = "SELECT * FROM tbm_ciu ";
                        if (!strFlt.equals("")){
                            strSql = strSql + " WHERE " + strFlt + "";
                        }
                        rstCab = stmCab.executeQuery(strSql);

                        if(rstCab.next()){
                            rstCab.last();
                            objTooBar.setMenSis(rstCab.getRow() + " Registros encontrados");
                            cargarReg();
                        }
                        else{
                           objTooBar.setMenSis("Se encontraron 0 registros...");
                            strTit="Mensaje del sistema Zafiro";
                            strMsg="No se ha encontrado ningán registro que cumpla el criterio de básqueda especáficado.";
                            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);  
                            rstCab = null;
                            stmCab.close();
                            conCab.close();
                            stmCab=null;
                            conCab=null;                               
                            limpiarPant();
                            return false;
                        }
                    }
           }
           catch(SQLException Evt)
           {
                  objUti.mostrarMsgErr_F1(jfrThis, Evt);
                  return false;
            }
            catch(Exception Evt)
            {
                  objUti.mostrarMsgErr_F1(jfrThis, Evt);
                  return false;
            }                       
       return true;     
     }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butPro;
    private javax.swing.JComboBox cboEstReg;
    private javax.swing.JComboBox cboPai;
    private javax.swing.JLabel lblCodCiu;
    private javax.swing.JLabel lblCodPai;
    private javax.swing.JLabel lblDesCor;
    private javax.swing.JLabel lblDesLar;
    private javax.swing.JLabel lblEstReg;
    private javax.swing.JLabel lblMaeCiu;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panGen;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTextField txtCodCiu;
    private javax.swing.JTextField txtCodPro;
    private javax.swing.JTextField txtDesCor;
    private javax.swing.JTextField txtDesCorPro;
    private javax.swing.JTextField txtDesLar;
    private javax.swing.JTextField txtDesLarPro;
    // End of variables declaration//GEN-END:variables
 
    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConPro(int intTipBus)
    {
        boolean blnRes=true;
        String strFilAdi="";
        int intCodPaiSel=-1;
        try
        {
            intCodPaiSel=Integer.parseInt(vecCodPai.get(cboPai.getSelectedIndex()).toString());
            
            strFilAdi="";
            strFilAdi+=" AND a1.co_pai=" + intCodPaiSel + "";
            strFilAdi+=" ORDER BY a1.tx_desLar";
            System.out.println("strFilAdi: " + strFilAdi);
            vcoPro.setCondicionesSQL(strFilAdi);
            
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoPro.setCampoBusqueda(1);
                    vcoPro.show();
                    if (vcoPro.getSelectedButton()==vcoPro.INT_BUT_ACE){
                        txtCodPro.setText(vcoPro.getValueAt(1));
                        txtDesCorPro.setText(vcoPro.getValueAt(2));
                        txtDesLarPro.setText(vcoPro.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoPro.buscar("a1.tx_desCor", txtDesCorPro.getText())){
                        txtCodPro.setText(vcoPro.getValueAt(1));
                        txtDesCorPro.setText(vcoPro.getValueAt(2));
                        txtDesLarPro.setText(vcoPro.getValueAt(3));
                    }
                    else
                    {
                        vcoPro.setCampoBusqueda(1);
                        vcoPro.setCriterio1(11);
                        vcoPro.cargarDatos();
                        vcoPro.show();
                        if (vcoPro.getSelectedButton()==vcoPro.INT_BUT_ACE){
                            txtCodPro.setText(vcoPro.getValueAt(1));
                            txtDesCorPro.setText(vcoPro.getValueAt(2));
                            txtDesLarPro.setText(vcoPro.getValueAt(3));
                        }
                        else
                        {
                            txtDesCorPro.setText(strDesCorPro);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoPro.buscar("a1.tx_desLar", txtDesLarPro.getText()))
                    {
                        txtCodPro.setText(vcoPro.getValueAt(1));
                        txtDesCorPro.setText(vcoPro.getValueAt(2));
                        txtDesLarPro.setText(vcoPro.getValueAt(3));
                    }
                    else
                    {
                        vcoPro.setCampoBusqueda(2);
                        vcoPro.setCriterio1(11);
                        vcoPro.cargarDatos();
                        vcoPro.show();
                        if (vcoPro.getSelectedButton()==vcoPro.INT_BUT_ACE){
                            txtCodPro.setText(vcoPro.getValueAt(1));
                            txtDesCorPro.setText(vcoPro.getValueAt(2));
                            txtDesLarPro.setText(vcoPro.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarPro.setText(strDesLarPro);
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



    private boolean configurarVenConPro()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_pro");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Alias");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_pro, a1.tx_descor, a1.tx_deslar";
            strSQL+=" FROM tbm_pro AS a1 WHERE a1.st_reg NOT IN('I','E')";

            vcoPro=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de provincias", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoPro.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }




}
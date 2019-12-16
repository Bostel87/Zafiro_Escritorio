/*
 * ZafMae11.java
 *
 * Created on 20 de octubre de 2004, 12:12 PM
 */
package Maestros.ZafMae11;

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


/**
 *
 * @author  jresabala
 * corregido por jsalazar 20/oct/2005
 * ultima actualizacion 28/dic/2005
 */
public class ZafMae11 extends javax.swing.JInternalFrame {
        Connection conCab; //Variable para conexion a la Base de Datos
        Statement stmCab; //Variable para ejecucion de sentencias SQL
        ResultSet rstCab;//Variable para manipular registro de la tabla pais
        String strSql,strAux; //Variable de tipo cadena en la cual se almacenara el Query
         String strTit, strMsg;//Variables tipo cadena en la cual se almacena los mensaje que se van a enviar al usuario
        int intCon;//Contador de vector
        char chrEst;//Variable para guardar el estado del registro
        char chrFlgEst;//Bandera para saber en que estado se encuentra la forma 
        JOptionPane oppMsg;//Objeto de tipo OptionPane para presentar Mensajes
        thetoolbar objTooBar;//Objeto de tipo objTooBar para poder manipular la clase ZafToolBar
         ZafUtil objUti;//Objeto del tipo de la clase ZafUtil, el cual me va a permitir manipular los diferentes metodos de esta clase
        ZafParSis objZafParSis;//Objeto que me permitira obtener los parametros del sistema, como podria ser la ruta de la base de datos, etc.  
        javax.swing.JInternalFrame jfrThis;//Variable que se refiere al JInternalFrame  
        String strFlt;//EL filtro de la Consulta actual
        char chrFlgCon = 'N'; //Bandera para saber si se ha perdido el foco en un objeto
        char chrFlgMsj = 'N'; //Bandera para saber si ya se presento el mensaje
        char chrFlgLst = 'N'; //Bandera para saber si ya se presento el mensaje al pÃ¨rder el foco el objeto
        boolean blnCam = false;//Detecta que se hizo cambios en el documento
        boolean blnCln = false; //Si los TextField estan vacios
        LisTxt objLisCam;// Instancia de clase que detecta cambios
        
        /** Creates new form ZafMae11 */
    public ZafMae11(ZafParSis obj) {
        initComponents();
        addListenerCambios();
        try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone(); 
            objUti = new ZafUtil();
            oppMsg=new JOptionPane();
            objTooBar = new thetoolbar(this);
            this.getContentPane().add(objTooBar,"South");
            this.setTitle(objZafParSis.getNombreMenu());
            txtCodPai.setBackground(objZafParSis.getColorCamposSistema());
            jfrThis=this;
            blnCam = false;
            this.setBounds(10,10, 700, 450);  
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }              
    }    
    
    public boolean grabarInformacion()
	{
            try
		{
			java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos()); 		
                        if(cboEstReg.getSelectedIndex()==0)
                            chrEst='A';
                        else
                            chrEst='I';
                   try{     
                        if (con != null){
                                java.sql.PreparedStatement pstReg;
                                java.sql.Statement stm = con.createStatement();
                                strSql = "";
                                strSql = strSql + "SELECT MAX(co_pai)+1 as co_pai from tbm_pai";
                                java.sql.ResultSet rst = stm.executeQuery(strSql);
                                
                                int intNumReg;
                                if (rst.next())
                                    intNumReg=rst.getInt("co_pai");
                                else
                                    intNumReg=1;
                                rst.close();
                                stm = con.createStatement();
				strSql="";	
				strSql = strSql + " INSERT INTO tbm_pai (co_pai, tx_desCor, tx_desLar, st_reg)";
				strSql = strSql + " VALUES (";
				strSql = strSql + " " + intNumReg + "";
				strSql = strSql + " ," + ((strAux=txtDesCor.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txtDesCor.getText() + "'";
				strSql = strSql + " ," + ((strAux=txtDesLar.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txtDesLar.getText() +"'";
				strSql = strSql + " ,'" + chrEst + "'";
                                strSql = strSql + " )";
                                pstReg = con.prepareStatement(strSql);
                                pstReg.executeUpdate();
                                con.commit();
				stm.close();
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


/*Funcion que sirve para eliminar los datos de la empresa*/    
    public boolean eliDat()
    {
        try{
            java.sql.Connection	con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos()); 		
            try {
		if (con != null){
				java.sql.PreparedStatement pstReg;
				strSql="";	
				strSql = strSql + " DELETE FROM tbm_pai WHERE co_pai=" + txtCodPai.getText() + "";
                                pstReg =con.prepareStatement(strSql);
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
            java.sql.Connection	con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos()); 		

                        if(cboEstReg.getSelectedIndex()==0)
                            chrEst='A';
                        else
                            chrEst='I';
               try{
                        if (con != null){
				java.sql.PreparedStatement pstReg;
				strSql="";	
				strSql = strSql + " UPDATE tbm_pai SET";
                                strSql = strSql + " tx_desCor=" + ((strAux=txtDesCor.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txtDesCor.getText() + "'";
				strSql = strSql + ", tx_desLar=" + ((strAux=txtDesLar.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txtDesLar.getText() +"'";
				strSql = strSql + ", st_reg='" + chrEst + "'";
                                strSql = strSql + " WHERE co_pai=" + txtCodPai.getText() + "";
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
        txtCodPai.setText("");
        txtDesCor.setText("");
        txtDesLar.setText("");
    }
    
     private String FilSql() {               
                String sqlFiltro = "";

                //Agregando filtro por codigo de pais
                strAux = txtCodPai.getText();
                if(!txtCodPai.getText().equals(""))
                    sqlFiltro = sqlFiltro + " (co_pai) = " + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "";

                //Agregando filtro por descripcion corta del pais
                strAux = txtDesCor.getText();
                if(!txtDesCor.getText().equals("")){
                    if (!sqlFiltro.equals(""))sqlFiltro = sqlFiltro + " AND" ;
                    sqlFiltro = sqlFiltro + " LOWER(tx_descor) LIKE '" +  strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
                }

                //Agregando filtro por descripcion lasrgadel pais
                strAux = txtDesLar.getText();
                if(!txtDesLar.getText().equals("")){
                    if (!sqlFiltro.equals(""))sqlFiltro = sqlFiltro + " AND" ;
                    sqlFiltro = sqlFiltro + " LOWER(tx_deslar) LIKE '" +  strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";                    
                }
                return sqlFiltro ;
    }
    
         
     private int Mensaje(){
                String strTit, strMsg;
                strTit="Sistema Zafiro";
                strMsg="¿Deseà guardar los cambios efectuados a este registro?\n";
                strMsg+="Si no guarda los cambios perdera toda la informaciòn que no haya guardado.";
                javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                return obj.showConfirmDialog(jfrThis ,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);                
            }
     
      private void addListenerCambios(){
            objLisCam = new LisTxt();
            txtCodPai.setText("");
            txtCodPai.getDocument().addDocumentListener(objLisCam);
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
        if ((txtCodPai.getText().equals("")) && (txtDesCor.getText().equals("")) && (txtDesLar.getText().equals(""))){    
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
    private void initComponents() {//GEN-BEGIN:initComponents
        lblMaePai = new javax.swing.JLabel();
        panCon = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        lblCodPai = new javax.swing.JLabel();
        lblDesCor = new javax.swing.JLabel();
        lblDesLar = new javax.swing.JLabel();
        lblEstReg = new javax.swing.JLabel();
        txtCodPai = new javax.swing.JTextField();
        txtDesCor = new javax.swing.JTextField();
        txtDesLar = new javax.swing.JTextField();
        cboEstReg = new javax.swing.JComboBox();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
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

        lblMaePai.setFont(new java.awt.Font("SansSerif", 1, 11));
        lblMaePai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMaePai.setText("Maestro Paises");
        getContentPane().add(lblMaePai, java.awt.BorderLayout.NORTH);

        panCon.setLayout(new java.awt.BorderLayout());

        panGen.setLayout(null);

        lblCodPai.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodPai.setText("C\u00f2digo Pais:");
        panGen.add(lblCodPai);
        lblCodPai.setBounds(4, 8, 100, 20);

        lblDesCor.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDesCor.setText("Descripci\u00f2n corta:");
        panGen.add(lblDesCor);
        lblDesCor.setBounds(4, 48, 100, 20);

        lblDesLar.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDesLar.setText("Descripci\u00f2n Larga:");
        panGen.add(lblDesLar);
        lblDesLar.setBounds(4, 88, 100, 20);

        lblEstReg.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblEstReg.setText("Estado Registro");
        panGen.add(lblEstReg);
        lblEstReg.setBounds(4, 128, 100, 20);

        panGen.add(txtCodPai);
        txtCodPai.setBounds(104, 8, 100, 20);

        txtDesCor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorFocusLost(evt);
            }
        });

        panGen.add(txtDesCor);
        txtDesCor.setBounds(104, 48, 100, 20);

        txtDesLar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarFocusLost(evt);
            }
        });

        panGen.add(txtDesLar);
        txtDesLar.setBounds(104, 88, 200, 20);

        cboEstReg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Inactivo" }));
        cboEstReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstRegActionPerformed(evt);
            }
        });

        panGen.add(cboEstReg);
        cboEstReg.setBounds(104, 128, 100, 20);

        tabGen.addTab("General", panGen);

        panCon.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCon, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-600)/2, (screenSize.height-400)/2, 600, 400);
    }//GEN-END:initComponents

    private void txtDesLarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarFocusLost
    if (chrFlgLst == 'N') {
       if (txtDesLar.getText().length()>30 && chrFlgLst == 'N'){ 
            strTit="Mensaje del sistema Zafiro";
            strMsg="El campo <<Descripciòn larga>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
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
            strMsg="El campo <<Descripciòn corta>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
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

    private void cboEstRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstRegActionPerformed
            blnCam = true;
    }//GEN-LAST:event_cboEstRegActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
     javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_exitForm
    
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
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
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
     * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
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
     * Esta función permite cargar el registro seleccionado.
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
                String strSQL = "  SELECT * FROM tbm_pai"+ 
                     " Where co_pai=" + rstCab.getString("co_pai")  ;
//                System.out.println(strSQL);
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodPai.setText(rst.getString("co_pai"));
                    txtCodPai.setText(((rst.getString("co_pai")==null)?"":rst.getString("co_pai")));
                    txtDesCor.setText(((rst.getString("tx_descor")==null)?"":rst.getString("tx_descor")));
                    txtDesLar.setText(((rst.getString("tx_deslar")==null)?"":rst.getString("tx_deslar")));
                    if(rst.getString("st_reg").equals("I")){
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
                //Mostrar la posición relativa del registro.
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
                txtCodPai.setEditable(false);
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
                        strSQL = " Update tbm_pai set st_reg = 'I' " +
                                 " where co_pai =" + txtCodPai.getText();
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
//            if (!validaCampos())
//                return false;
            if (!grabarInformacion())
                return false;
            limpiarPant();
            blnCam=false;
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
                if (mensaje("Para modificar primero debe reactivar el pais \n ¿Desea reactivarlo?")==JOptionPane.YES_OPTION){
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
            txtCodPai.setEditable(false);
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
                    strSQL = " Update tbm_pai set st_reg= 'A' where co_pai = " + txtCodPai.getText();
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
                        strSql = "SELECT * FROM tbm_pai ";
                        if (!strFlt.equals("")){
                            strSql = strSql + " WHERE " + strFlt + "";
                        }
//                        System.out.println(strSql);
                        rstCab = stmCab.executeQuery(strSql);

                        if(rstCab.next()){
                            rstCab.last();
                            objTooBar.setMenSis(rstCab.getRow() + " Registros encontrados");
                            cargarReg();
                        }
                        else{
                           objTooBar.setMenSis("Se encontraron 0 registros...");
                            strTit="Mensaje del sistema Zafiro";
                            strMsg="No se ha encontrado ningùn registro que cumpla el criterio de bùsqueda especìficado.";
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
    private javax.swing.JComboBox cboEstReg;
    private javax.swing.JLabel lblCodPai;
    private javax.swing.JLabel lblDesCor;
    private javax.swing.JLabel lblDesLar;
    private javax.swing.JLabel lblEstReg;
    private javax.swing.JLabel lblMaePai;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panGen;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTextField txtCodPai;
    private javax.swing.JTextField txtDesCor;
    private javax.swing.JTextField txtDesLar;
    // End of variables declaration//GEN-END:variables
}

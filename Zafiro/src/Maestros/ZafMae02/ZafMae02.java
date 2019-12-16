/*
 * ZafMae02.java
 *
 * Created on 30 de septiembre de 2004, 11:39
 */

/**
 *
 * @author  jresabala
 *mejorado por jsalazar 31/oct/2005
 */

package Maestros.ZafMae02;

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

public class ZafMae02 extends javax.swing.JInternalFrame {
        Connection conCab; //Variable para conexion a la Base de Datos
        Statement stmCab; //Variable para ejecucion de sentencias SQL
        ResultSet rstCab; //Variable para manipular registro de la tabla en ejecucion
        String strSql, strAux; //Variable de tipo cadena en la cual se almacenara el Query
        int intCon; //Contador de vector
        int intConCiu;//Variable para contar el codigo de la ciudad
        int intCodCiu;//Variable para contener el codigo de la ciudad
        Vector vecCodCiu;//Vector para guardar el codigo de la ciudad
        Vector vecCodEmp;//Vector para guardar el codigo de la empresa
        char chrEst;//Variable para guardar el estado del registro
        char chrFlgEst;//Bandera para saber en que estado se encuentra la forma 
        int intNumReg;//Variable que sirve para llevar la secuencia del numero del local
        int intOpt;//Variable para guardar las opciones
        int intConEspNot;//Variable que contiene el numero de notificacion del contribuyente especial
        int intConEspRes;//Variable que contiene el numero de la resolucion del contribuyente especial
        String strTit, strMsg;//Variable que contiene los mensajes del sistema
        JOptionPane oppMsg;//Variable que contiene los mensajes del option pane
        thetoolbar objTooBar;//Objeto de tipo objTooBar para poder manipular la clase ZafobjTooBar
        ZafUtil objUti;//Objeto del tipo de la clase ZafUtil, el cual me va a permitir manipular los diferentes metodos de esta clase
        ZafParSis objZafParSis;//Objeto que me permitira obtener los parametros del sistema, como podria ser la ruta de la base de datos, etc.  
        Librerias.ZafUtil.ZafCtaCtb objCtaCtb;
        javax.swing.JInternalFrame jfrThis;//Variable que se refiere al JInternalFrame  
        String strFlt;//EL filtro de la Consulta actual
        char chrFlgCon = 'N'; //Bandera para saber si se ha perdido el foco en un objeto
        char chrFlgMsj = 'N'; //Bandera para saber si ya se presento el mensaje
        char chrFlgLst = 'N'; //Bandera para saber si ya se presento el mensaje al pÃ¨rder el foco el objeto
        boolean blnCam = false;//Detecta que se hizo cambios en el documento
        boolean blnCln = false; //Si los TextField estan vacios
        boolean blnFlgNum = false; //Variable booleana que servira como bandera para saber si la caja contiene caracteres invalidos
        boolean blnFlgCon = false;//Variable booleana que servira como bandera para saber si la caja contiene algo 
        LisTxt objLisCam;// Instancia de clase que detecta cambios
        
        private final String VERSION = " v0.1";
        /** Creates new form ZafMae02 */
    public ZafMae02(ZafParSis obj) {
        initComponents();
        addListenerCambios();
        try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();                 
 
            objUti = new ZafUtil();
            oppMsg=new JOptionPane();
            objTooBar = new thetoolbar(this);
            objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb(objZafParSis);
            this.getContentPane().add(objTooBar,"South");
            llenarCboCiu();
            this.setTitle(objZafParSis.getNombreMenu()+VERSION);
            txtCodLoc.setBackground(objZafParSis.getColorCamposSistema());
            txtAutSRI.setBackground(objZafParSis.getColorCamposObligatorios());
            txtNomLoc.setBackground(objZafParSis.getColorCamposObligatorios());
            txtCodCtaCostos.setBackground(objZafParSis.getColorCamposObligatorios());
            txtCodCtaDesCom.setBackground(objZafParSis.getColorCamposObligatorios());
            txtCodCtaDesVen.setBackground(objZafParSis.getColorCamposObligatorios());
            txtNomCtaCostos.setBackground(objZafParSis.getColorCamposObligatorios());
            txtNomCtaDesCom.setBackground(objZafParSis.getColorCamposObligatorios());
            txtNomCtaDesVen.setBackground(objZafParSis.getColorCamposObligatorios());
            jfrThis=this;    
            jfrThis.setBounds(10,10,700, 450);
            blnCam = false;
          this.setBounds(10,10, 700, 450);            
        }catch (CloneNotSupportedException e){
             objUti.mostrarMsgErr_F1(this, e);
        }                 
             
    }   
    public void llenarCboCiu()
	{
            vecCodCiu = new Vector();   
            objUti.llenarCbo_F1(this,objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), 
                                 objZafParSis.getClaveBaseDatos(), "SELECT co_ciu, tx_deslar FROM tbm_ciu ORDER BY co_ciu", 
                                 cboCiu, vecCodCiu); 
            cboCiu.setSelectedIndex(0);
	}
    
    private void FndCta(String strBusqueda,int TipoBusqueda)
    {
        Librerias.ZafConsulta.ZafConsulta  objFnd = 
        new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(this),
           "Codigo,Alias,Descripcion,Debe/Haber","cta.co_cta , tx_codcta,tx_deslar , tx_natcta",
             "select  cta.co_cta , tx_codcta,tx_deslar , tx_natcta from tbm_placta as cta " +
                " where   cta.co_emp = " + objZafParSis.getCodigoEmpresa()  + " and tx_tipcta='D' and st_reg='A'",strBusqueda, 
         objZafParSis.getStringConexion(), 
         objZafParSis.getUsuarioBaseDatos(), 
         objZafParSis.getClaveBaseDatos()
         );        

        objFnd.setTitle("Listado Cuentas");
        
        switch (TipoBusqueda)
        {
            case 1:                                        
                 objFnd.setSelectedCamBus(0);
                if(!objFnd.buscar("cta.co_cta = " + txtCodCtaCostos.getText()))
                    objFnd.show();
                 break;
            case 2:
                 objFnd.setSelectedCamBus(2);
                if(!objFnd.buscar("tx_deslar = '" + txtNomCtaCostos.getText()+"'"))
                    objFnd.show();                 
                 break;
            default:
                objFnd.show();
                break;             
         }
        if(!objFnd.GetCamSel(1).equals("")){
            txtCodCtaCostos.setText(objFnd.GetCamSel(1).toString());
            txtNomCtaCostos.setText(objFnd.GetCamSel(3).toString());
        }
    }
    private void FndCtaDesCom(String strBusqueda,int TipoBusqueda)
    {
        Librerias.ZafConsulta.ZafConsulta  objFnd = 
        new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(this),
           "Codigo,Alias,Descripcion,Debe/Haber","cta.co_cta , tx_codcta,tx_deslar , tx_natcta",
             "select  cta.co_cta , tx_codcta,tx_deslar , tx_natcta from tbm_placta as cta " +
                " where   cta.co_emp = " + objZafParSis.getCodigoEmpresa()   ,strBusqueda, 
         objZafParSis.getStringConexion(), 
         objZafParSis.getUsuarioBaseDatos(), 
         objZafParSis.getClaveBaseDatos()
         );        

        objFnd.setTitle("Listado Cuentas");
        
        switch (TipoBusqueda)
        {
            case 1:                                        
                 objFnd.setSelectedCamBus(0);
                if(!objFnd.buscar("cta.co_cta = " + txtCodCtaDesCom.getText()))
                    objFnd.show();
                 break;
            case 2:
                 objFnd.setSelectedCamBus(2);
                if(!objFnd.buscar("tx_deslar = '" + txtNomCtaDesCom.getText()+"'"))
                    objFnd.show();                 
                 break;
            default:
                objFnd.show();
                break;             
         }
        if(!objFnd.GetCamSel(1).equals("")){
            txtCodCtaDesCom.setText(objFnd.GetCamSel(1).toString());
            txtNomCtaDesCom.setText(objFnd.GetCamSel(3).toString());
        }
    }
    private void FndCtaDesVen(String strBusqueda,int TipoBusqueda)
    {
        Librerias.ZafConsulta.ZafConsulta  objFnd = 
        new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(this),
           "Codigo,Alias,Descripcion,Debe/Haber","cta.co_cta , tx_codcta,tx_deslar , tx_natcta",
             "select  cta.co_cta , tx_codcta,tx_deslar , tx_natcta from tbm_placta as cta " +
                " where   cta.co_emp = " + objZafParSis.getCodigoEmpresa()  ,strBusqueda, 
         objZafParSis.getStringConexion(), 
         objZafParSis.getUsuarioBaseDatos(), 
         objZafParSis.getClaveBaseDatos()
         );        

        objFnd.setTitle("Listado Cuentas");
        
        switch (TipoBusqueda)
        {
            case 1:                                        
                 objFnd.setSelectedCamBus(0);
                if(!objFnd.buscar("cta.co_cta = " + txtCodCtaDesVen.getText()))
                    objFnd.show();
                 break;
            case 2:
                 objFnd.setSelectedCamBus(2);
                if(!objFnd.buscar("tx_deslar = '" + txtNomCtaDesVen.getText()+"'"))
                    objFnd.show();                 
                 break;
            default:
                objFnd.show();
                break;             
         }
        if(!objFnd.GetCamSel(1).equals("")){
            txtCodCtaDesVen.setText(objFnd.GetCamSel(1).toString());
            txtNomCtaDesVen.setText(objFnd.GetCamSel(3).toString());
        }
    }
    
 /**Funcion que me permite guardar los datos de la Empresa*/   
    public boolean grabarInformacion()
	{
            try
		{
			java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos()); 		
                        intConCiu=cboCiu.getSelectedIndex();
                        intCodCiu=Integer.parseInt(vecCodCiu.get(intConCiu).toString());
                        intCon=cboEstLoc.getSelectedIndex();
                         if(intCon==0){
                            chrEst='A';}
                        else{
                            chrEst='I';}
                        
                        if (con != null)
			{
                                java.sql.Statement stm = con.createStatement();
                                strSql = "";
                                strSql = strSql + "SELECT MAX(co_loc) from tbm_loc where co_emp ="+ objZafParSis.getCodigoEmpresa();
                                java.sql.ResultSet rst = stm.executeQuery(strSql);
                                
                                if(this.txtConEspNot.getText().length()==0)
                                    intConEspNot = 0;
                                else
                                    intConEspNot = Integer.parseInt(txtConEspNot.getText());
                                
                                if(this.txtConEspRes.getText().length()==0)
                                    intConEspRes = 0;
                                else
                                    intConEspRes = Integer.parseInt(txtConEspRes.getText());

                                if (rst.next())
                                    intNumReg=rst.getInt(1)+1;
                                else
                                    intNumReg=1;

                                stm = con.createStatement();
				String strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
                                strSql="";	
				strSql = strSql + " INSERT INTO tbm_loc (co_emp, co_loc, tx_nom, tx_dir, tx_tel, tx_fax, co_ciu, tx_autsri, tx_secdoc, ne_conespnot, ne_conespres,co_ctadescom,co_ctadesven,co_ctacosven, tx_obs1, tx_obs2, st_reg,fe_ing,co_usring)";
				strSql = strSql + " VALUES (";
				strSql = strSql + " " + objZafParSis.getCodigoEmpresa() + "";
                                strSql = strSql + " , " + intNumReg + "";
				strSql = strSql + " ," + ((strAux=txtNomLoc.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txtNomLoc.getText() + "'";
				strSql = strSql + " ," + ((strAux=txtDirLoc.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txtDirLoc.getText()+"'";
                                strSql = strSql + " ," + ((strAux=txtTelLoc.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");// " ,'" + txtTelLoc.getText()+"'";
                                strSql = strSql + " ," + ((strAux=txtFaxLoc.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txtFaxLoc.getText()+"'";
                                strSql = strSql + " , " + intCodCiu + "";
                                strSql = strSql + " ," + ((strAux=txtAutSRI.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txtAutSRI.getText()+"'";
                                strSql = strSql + " ," + ((strAux=txtSecDoc.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txtSecDoc.getText()+"'";
                                strSql = strSql + " , " + intConEspNot +"";
                                strSql = strSql + " , " + intConEspRes +"";
                                strSql = strSql + " , " + ((strAux=txtCodCtaCostos.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'") +"";
                                strSql = strSql + " , " + ((strAux=txtCodCtaDesCom.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'") +"";
                                strSql = strSql + " , " + ((strAux=txtCodCtaDesVen.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'") +"";
                                strSql = strSql + " ," + ((strAux=txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txaObs1.getText()+"'";
                                strSql = strSql + " ," + ((strAux=txaObs2.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txaObs2.getText()+"'";
                                strSql = strSql + " ,'" + chrEst + "'";
                                strSql = strSql + " ,'" + strFecSis + "'";
                                strSql = strSql + " ," + objZafParSis.getCodigoUsuario() + "";
                                strSql = strSql + " )";
                                stm.executeUpdate(strSql);
				stm.close();
				con.close();
                                limpiarPant();
			}
		}
		catch (SQLException evt)
		{
			objUti.mostrarMsgErr_F1(jfrThis, evt);
                        return false;
		}
		catch (Exception evt)
		{
			objUti.mostrarMsgErr_F1(jfrThis, evt);
                        return false;
		}
                return true;
    }

/*Funcion que sirve para eliminar los datos de la empresa*/    
    public boolean eliDat()
    {
      try{
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos()); 		
            try{
                if (con != null){
                        java.sql.PreparedStatement pstReg;
                        strSql="";	
                        strSql = " DELETE FROM tbm_loc WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLoc.getText() + "";
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
            this.intConCiu=cboCiu.getSelectedIndex();
            this.intCodCiu=Integer.parseInt(vecCodCiu.get(intConCiu).toString());
            intCon=cboEstLoc.getSelectedIndex();
            
             if(this.txtConEspNot.getText().length()==0)
                intConEspNot = 0;
             else
                intConEspNot = Integer.parseInt(txtConEspNot.getText());
                                
             if(this.txtConEspRes.getText().length()==0)
                intConEspRes = 0;
             else
                intConEspRes = Integer.parseInt(txtConEspRes.getText());
        try
		{
			java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos()); 		
			
                        intCon=cboEstLoc.getSelectedIndex();
                         if(intCon==0){
                            chrEst='A';}
                        else{
                            chrEst='I';}
                        
                        if (con != null)
			{
				java.sql.Statement stm = con.createStatement();
                                String strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
				strSql="";	
				strSql = strSql +" UPDATE tbm_loc SET";
                                strSql = strSql + "  tx_nom=" + ((strAux=txtNomLoc.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txtNomLoc.getText() + "'";
				strSql = strSql + " , tx_dir=" + ((strAux=txtDirLoc.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txtDirLoc.getText()+"'";
                                strSql = strSql + " , tx_tel=" + ((strAux=txtTelLoc.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");// " ,'" + txtTelLoc.getText()+"'";
                                strSql = strSql + " , tx_fax=" + ((strAux=txtFaxLoc.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txtFaxLoc.getText()+"'";
                                strSql = strSql + " , co_ciu=" + intCodCiu + "";
                                strSql = strSql + " , tx_autSRI=" + ((strAux=txtAutSRI.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txtAutSRI.getText()+"'";
                                strSql = strSql + " , tx_secDoc=" + ((strAux=txtSecDoc.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txtSecDoc.getText()+"'";
                                strSql = strSql + " , ne_conEspNot =" + intConEspNot +"";
                                strSql = strSql + " , ne_conespres =" + intConEspRes +"";
                                strSql = strSql + " , co_ctaCosVen=" + ((strAux=txtCodCtaCostos.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'") +"";
                                strSql = strSql + " , co_ctaDesCom=" + ((strAux=txtCodCtaDesCom.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'") +"";
                                strSql = strSql + " , co_ctaDesVen=" + ((strAux=txtCodCtaDesVen.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'") +"";
                                strSql = strSql + " , tx_obs1=" + ((strAux=txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txaObs1.getText()+"'";
                                strSql = strSql + " , tx_obs2=" + ((strAux=txaObs2.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");//" ,'" + txaObs2.getText()+"'";
                                strSql = strSql + " , st_reg='" + chrEst + "'";
                                strSql = strSql + " , fe_ultmod='" + strFecSis + "'";
                                strSql = strSql + " , co_usrmod=" + objZafParSis.getCodigoUsuario() + "";
                                strSql = strSql + " WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_loc = "+ txtCodLoc.getText() +" ";
                                stm.executeUpdate(strSql);
                                
                                
				stm.close();
				con.close();                                
			}
		}
        	catch (SQLException evt)
		{
			objUti.mostrarMsgErr_F1(jfrThis, evt);
                        return false;
		}
		catch (Exception evt)
		{
			objUti.mostrarMsgErr_F1(jfrThis, evt);
                        return false;
		}
                return true;    
    }
  
    /**procedimiento que limpia todas las cajas de texto que existen en el frame*/            
    public void limpiarPant()
   { 
          txtCodLoc.setText("");
          txtNomLoc.setText("");
          txtDirLoc.setText("");   
          txtTelLoc.setText("");
          txtFaxLoc.setText("");
          txtAutSRI.setText("");
          txtSecDoc.setText("");
          txtConEspNot.setText("");
          txtConEspRes.setText("");
          txaObs1.setText("");
          txaObs2.setText("");
          txtCodCtaCostos.setText("");
          txtNomCtaCostos.setText("");
          txtCodCtaDesCom.setText("");
          txtNomCtaDesCom.setText("");
          txtCodCtaDesVen.setText("");
          txtNomCtaDesVen.setText("");
   }
    
     private String FilSql() {               
                String sqlFiltro = "";

                //Agregando filtro por codigo de empresa
                strAux = txtCodLoc.getText();
                if(!txtCodLoc.getText().equals(""))
                    sqlFiltro = sqlFiltro + " AND (co_loc) LIKE " + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "";

                //Agregando filtro por ruc
                strAux = txtNomLoc.getText();
                if(!txtNomLoc.getText().equals(""))
                    sqlFiltro = sqlFiltro + " AND LOWER(tx_nom) LIKE '" + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
                    
                //Agregando filtro por Direccion
                strAux = txtDirLoc.getText();
                if(!txtDirLoc.getText().equals(""))
                    sqlFiltro = sqlFiltro + " AND LOWER(tx_dir) LIKE '" + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
                         
                return sqlFiltro ;
    }
     
     
       private int Mensaje(){
                strTit="Mensaje del sistema Zafiro";
                strMsg="¿Deseà guardar los cambios efectuados a este registro?\n";
                strMsg+="Si no guarda los cambios perderà toda la informaciòn que no haya guardado.";
                javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                return obj.showConfirmDialog(jfrThis ,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);                
             }
       
       private void addListenerCambios(){
            objLisCam = new LisTxt();
            txtCodLoc.setText("");
            txtCodLoc.getDocument().addDocumentListener(objLisCam);
            txtNomLoc.getDocument().addDocumentListener(objLisCam);
            txtDirLoc.getDocument().addDocumentListener(objLisCam);
            txtTelLoc.getDocument().addDocumentListener(objLisCam);
            txtFaxLoc.getDocument().addDocumentListener(objLisCam);
            txtAutSRI.getDocument().addDocumentListener(objLisCam);
            txtConEspNot.getDocument().addDocumentListener(objLisCam);
            txtConEspRes.getDocument().addDocumentListener(objLisCam);
            txtSecDoc.getDocument().addDocumentListener(objLisCam);
            txaObs1.getDocument().addDocumentListener(objLisCam);
            txaObs2.getDocument().addDocumentListener(objLisCam);
            txtCodCtaCostos.getDocument().addDocumentListener(objLisCam);
            txtCodCtaDesCom.getDocument().addDocumentListener(objLisCam);
            txtCodCtaDesVen.getDocument().addDocumentListener(objLisCam);
    }   
       
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
        if ((txtCodLoc.getText().equals("")) && (txtNomLoc.getText().equals("")) && (txtDirLoc.getText().equals("")) && (txtTelLoc.getText().equals("")) && (txtFaxLoc.getText().equals("")) && (txtAutSRI.getText().equals("")) && (txtSecDoc.getText().equals("")) && (txtConEspRes.getText().equals("")) && (txtConEspNot.getText().equals("")) && (txaObs1.getText().equals("")) && (txaObs2.getText().equals(""))){    
            blnCln = false;
        }
        else {
            blnCln = true;
        }
        return blnCln;
    }
          
      private boolean flagNum(boolean blnFlgNum)
    {
        return blnFlgNum; 
    }
    
    private boolean flagCon(boolean blnFlgCon)
    {
        return blnFlgCon; 
    }        
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        lblMaeLoc = new javax.swing.JLabel();
        panCon = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        lblTelLoc = new javax.swing.JLabel();
        lblDirLoc = new javax.swing.JLabel();
        lblNomLoc = new javax.swing.JLabel();
        lblCodLoc = new javax.swing.JLabel();
        txtTelLoc = new javax.swing.JTextField();
        txtDirLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        txtCodLoc = new javax.swing.JTextField();
        lblCiu = new javax.swing.JLabel();
        cboCiu = new javax.swing.JComboBox();
        lblObs1 = new javax.swing.JLabel();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        lblObs2 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        cboEstLoc = new javax.swing.JComboBox();
        lblEstLoc = new javax.swing.JLabel();
        txtFaxLoc = new javax.swing.JTextField();
        lblFaxLoc = new javax.swing.JLabel();
        lblAutSRI = new javax.swing.JLabel();
        txtAutSRI = new javax.swing.JTextField();
        lblConEspNot = new javax.swing.JLabel();
        txtSecDoc = new javax.swing.JTextField();
        lblSecDoc = new javax.swing.JLabel();
        txtConEspNot = new javax.swing.JTextField();
        lblConEspRes = new javax.swing.JLabel();
        txtConEspRes = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCodCtaCostos = new javax.swing.JTextField();
        txtNomCtaCostos = new javax.swing.JTextField();
        txtCodCtaDesVen = new javax.swing.JTextField();
        txtNomCtaDesVen = new javax.swing.JTextField();
        txtCodCtaDesCom = new javax.swing.JTextField();
        txtNomCtaDesCom = new javax.swing.JTextField();
        butCtaCostos = new javax.swing.JButton();
        butCtaDesVen = new javax.swing.JButton();
        butCtaDesCom = new javax.swing.JButton();

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

        lblMaeLoc.setFont(new java.awt.Font("SansSerif", 1, 11));
        lblMaeLoc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMaeLoc.setText("NOMBRE DEL LOCAL");
        getContentPane().add(lblMaeLoc, java.awt.BorderLayout.NORTH);

        panCon.setLayout(new java.awt.BorderLayout());

        tabGen.setName("");
        panGen.setLayout(null);

        lblTelLoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTelLoc.setText("Tel\u00e9fono:");
        panGen.add(lblTelLoc);
        lblTelLoc.setBounds(5, 100, 100, 10);

        lblDirLoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDirLoc.setText("Direcci\u00f2n Local:");
        panGen.add(lblDirLoc);
        lblDirLoc.setBounds(8, 72, 100, 20);

        lblNomLoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNomLoc.setText("Nombre:");
        panGen.add(lblNomLoc);
        lblNomLoc.setBounds(8, 48, 100, 20);

        lblCodLoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodLoc.setText("C\u00f3digo Local:");
        panGen.add(lblCodLoc);
        lblCodLoc.setBounds(8, 4, 100, 20);

        txtTelLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTelLocFocusLost(evt);
            }
        });

        panGen.add(txtTelLoc);
        txtTelLoc.setBounds(108, 95, 120, 20);

        txtDirLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDirLocFocusLost(evt);
            }
        });

        panGen.add(txtDirLoc);
        txtDirLoc.setBounds(108, 70, 460, 20);

        txtNomLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLocFocusLost(evt);
            }
        });

        panGen.add(txtNomLoc);
        txtNomLoc.setBounds(108, 48, 460, 20);

        txtCodLoc.setEditable(false);
        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });

        panGen.add(txtCodLoc);
        txtCodLoc.setBounds(108, 4, 130, 20);

        lblCiu.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCiu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCiu.setText("Ciudad");
        panGen.add(lblCiu);
        lblCiu.setBounds(5, 125, 33, 10);

        cboCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCiuActionPerformed(evt);
            }
        });

        panGen.add(cboCiu);
        cboCiu.setBounds(110, 120, 120, 20);

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs1.setText("Observaci\u00f2n1:");
        panGen.add(lblObs1);
        lblObs1.setBounds(10, 250, 100, 15);

        txaObs2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txaObs2FocusLost(evt);
            }
        });

        spnObs2.setViewportView(txaObs2);

        panGen.add(spnObs2);
        spnObs2.setBounds(110, 270, 460, 50);

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs2.setText("Observaci\u00f2n2:");
        panGen.add(lblObs2);
        lblObs2.setBounds(10, 290, 100, 15);

        txaObs1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txaObs1FocusLost(evt);
            }
        });

        spnObs1.setViewportView(txaObs1);

        panGen.add(spnObs1);
        spnObs1.setBounds(110, 220, 460, 50);

        cboEstLoc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Inactivo" }));
        cboEstLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstLocActionPerformed(evt);
            }
        });

        panGen.add(cboEstLoc);
        cboEstLoc.setBounds(350, 120, 120, 20);

        lblEstLoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblEstLoc.setText("Estado del registro");
        panGen.add(lblEstLoc);
        lblEstLoc.setBounds(250, 120, 100, 20);

        txtFaxLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFaxLocFocusLost(evt);
            }
        });

        panGen.add(txtFaxLoc);
        txtFaxLoc.setBounds(350, 95, 120, 20);

        lblFaxLoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFaxLoc.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFaxLoc.setText("Fax:");
        panGen.add(lblFaxLoc);
        lblFaxLoc.setBounds(240, 100, 30, 20);

        lblAutSRI.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblAutSRI.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAutSRI.setText("Aut. del SRI:");
        lblAutSRI.setToolTipText("Autorizacion del SRI");
        panGen.add(lblAutSRI);
        lblAutSRI.setBounds(5, 30, 60, 20);

        txtAutSRI.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAutSRIFocusLost(evt);
            }
        });

        panGen.add(txtAutSRI);
        txtAutSRI.setBounds(108, 26, 130, 20);

        lblConEspNot.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblConEspNot.setText("Notificaci\u00f2n N\u00ba");
        panGen.add(lblConEspNot);
        lblConEspNot.setBounds(440, 0, 72, 20);

        txtSecDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSecDocFocusLost(evt);
            }
        });

        panGen.add(txtSecDoc);
        txtSecDoc.setBounds(385, 25, 180, 20);

        lblSecDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblSecDoc.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSecDoc.setText("Secuencial:");
        panGen.add(lblSecDoc);
        lblSecDoc.setBounds(320, 30, 60, 10);

        txtConEspNot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtConEspNotFocusLost(evt);
            }
        });

        panGen.add(txtConEspNot);
        txtConEspNot.setBounds(515, 5, 50, 20);

        lblConEspRes.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblConEspRes.setText("Resoluci\u00f2n N\u00ba");
        panGen.add(lblConEspRes);
        lblConEspRes.setBounds(310, 5, 72, 20);

        txtConEspRes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtConEspResFocusLost(evt);
            }
        });

        panGen.add(txtConEspRes);
        txtConEspRes.setBounds(385, 5, 50, 20);

        jLabel1.setText("Cuenta Costos de Ventas");
        panGen.add(jLabel1);
        jLabel1.setBounds(5, 150, 170, 15);

        jLabel2.setText("Cuenta Descuentos en Compras");
        panGen.add(jLabel2);
        jLabel2.setBounds(5, 190, 200, 15);

        jLabel3.setText("Cuenta Descuentos en Ventas");
        panGen.add(jLabel3);
        jLabel3.setBounds(5, 170, 200, 15);

        txtCodCtaCostos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaCostosActionPerformed(evt);
            }
        });

        panGen.add(txtCodCtaCostos);
        txtCodCtaCostos.setBounds(250, 150, 60, 21);

        txtNomCtaCostos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCtaCostosActionPerformed(evt);
            }
        });

        panGen.add(txtNomCtaCostos);
        txtNomCtaCostos.setBounds(310, 150, 260, 21);

        txtCodCtaDesVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaDesVenActionPerformed(evt);
            }
        });

        panGen.add(txtCodCtaDesVen);
        txtCodCtaDesVen.setBounds(250, 170, 60, 21);

        txtNomCtaDesVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCtaDesVenActionPerformed(evt);
            }
        });

        panGen.add(txtNomCtaDesVen);
        txtNomCtaDesVen.setBounds(310, 170, 260, 21);

        txtCodCtaDesCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaDesComActionPerformed(evt);
            }
        });

        panGen.add(txtCodCtaDesCom);
        txtCodCtaDesCom.setBounds(250, 190, 60, 21);

        txtNomCtaDesCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCtaDesComActionPerformed(evt);
            }
        });

        panGen.add(txtNomCtaDesCom);
        txtNomCtaDesCom.setBounds(310, 190, 260, 21);

        butCtaCostos.setText("...");
        butCtaCostos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaCostosActionPerformed(evt);
            }
        });

        panGen.add(butCtaCostos);
        butCtaCostos.setBounds(575, 150, 20, 20);

        butCtaDesVen.setText("...");
        butCtaDesVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaDesVenActionPerformed(evt);
            }
        });

        panGen.add(butCtaDesVen);
        butCtaDesVen.setBounds(575, 170, 20, 20);

        butCtaDesCom.setText("...");
        butCtaDesCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaDesComActionPerformed(evt);
            }
        });

        panGen.add(butCtaDesCom);
        butCtaDesCom.setBounds(575, 190, 20, 20);

        tabGen.addTab("General", panGen);

        panCon.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCon, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void butCtaDesComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaDesComActionPerformed
        // TODO add your handling code here:
        FndCtaDesCom("",0);        
    }//GEN-LAST:event_butCtaDesComActionPerformed

    private void txtNomCtaDesComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCtaDesComActionPerformed
        // TODO add your handling code here:
        FndCtaDesCom(txtNomCtaDesCom.getText(),2);        
    }//GEN-LAST:event_txtNomCtaDesComActionPerformed

    private void txtCodCtaDesComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaDesComActionPerformed
        // TODO add your handling code here:
        FndCtaDesCom(txtCodCtaDesCom.getText(),1);        
    }//GEN-LAST:event_txtCodCtaDesComActionPerformed

    private void butCtaDesVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaDesVenActionPerformed
        // TODO add your handling code here:
        FndCtaDesVen("",0);        
    }//GEN-LAST:event_butCtaDesVenActionPerformed

    private void txtNomCtaDesVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCtaDesVenActionPerformed
        // TODO add your handling code here:
        FndCtaDesVen(txtNomCtaDesVen.getText(),2);        
    }//GEN-LAST:event_txtNomCtaDesVenActionPerformed

    private void txtCodCtaDesVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaDesVenActionPerformed
        // TODO add your handling code here:
        FndCtaDesVen(txtCodCtaDesVen.getText(),1);        
    }//GEN-LAST:event_txtCodCtaDesVenActionPerformed

    private void butCtaCostosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaCostosActionPerformed
        // TODO add your handling code here:
        FndCta("",0);        
    }//GEN-LAST:event_butCtaCostosActionPerformed

    private void txtNomCtaCostosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCtaCostosActionPerformed
        // TODO add your handling code here:
        FndCta(txtNomCtaCostos.getText(),2);        
    }//GEN-LAST:event_txtNomCtaCostosActionPerformed

    private void txtCodCtaCostosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaCostosActionPerformed
        // TODO add your handling code here:
        FndCta(txtCodCtaCostos.getText(),1);        
    }//GEN-LAST:event_txtCodCtaCostosActionPerformed

    private void txaObs2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txaObs2FocusLost
          if (chrFlgLst == 'N') {
       if (txaObs2.getText().length()>200 && chrFlgLst == 'N'){ 
            tabGen.setSelectedIndex(0);
            strTit="Mensaje del sistema Zafiro";
            strMsg="El campo <<Observaciòn 2>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
            txaObs2.setText("");
            txaObs2.requestFocus();
            chrFlgMsj = 'S';
            chrFlgLst = 'S';
        } 
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
         }
    }//GEN-LAST:event_txaObs2FocusLost

    private void txaObs1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txaObs1FocusLost
             if (chrFlgLst == 'N') {
       if (txaObs1.getText().length()>200 && chrFlgLst == 'N'){ 
            tabGen.setSelectedIndex(0);
            strTit="Mensaje del sistema Zafiro";
            strMsg="El campo <<Observaciòn 1>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
            txaObs1.setText("");
            txaObs1.requestFocus();
            chrFlgMsj = 'S';
            chrFlgLst = 'S';
        } 
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
         }
    }//GEN-LAST:event_txaObs1FocusLost

    private void txtConEspResFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtConEspResFocusLost
     if (chrFlgLst == 'N') {
       if (txtConEspRes.getText().length()>5 && chrFlgLst == 'N'){ 
            tabGen.setSelectedIndex(0);
            strTit="Mensaje del sistema Zafiro";
            strMsg="El campo <<Resoluciòn Nº>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
            txtConEspRes.setText("");
            txtConEspRes.requestFocus();
            chrFlgMsj = 'S';
            chrFlgLst = 'S';
        } 
        flagNum(blnFlgNum);
        flagCon(blnFlgCon);
        if (txtConEspRes.getText().length()>0 && blnFlgNum == false && blnFlgCon == false && chrFlgMsj == 'N') {
            String numero = txtConEspRes.getText();
            objUti.isNumero(numero);
            if (objUti.isNumero(numero) == false) {
                 strTit="Mensaje del sistema Zafiro";
                 strMsg="El campo <<Resoluciòn Nº>> solo acepta valores numericos.\nDigite caracteres validos y vuelva a intentarlo.";
                 oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                 txtConEspRes.setText("");
                 txtConEspRes.requestFocus();
                 chrFlgMsj = 'S';
            }
        }
        blnFlgNum = false;
        blnFlgCon = false;
    chrFlgMsj = 'N';
    chrFlgLst = 'N';
    }   
    }//GEN-LAST:event_txtConEspResFocusLost

    private void txtConEspNotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtConEspNotFocusLost
     if (chrFlgLst == 'N') {
       if (txtConEspNot.getText().length()>5 && chrFlgLst == 'N'){ 
            tabGen.setSelectedIndex(0);
            strTit="Mensaje del sistema Zafiro";
            strMsg="El campo <<Notificaciòn Nº>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
            txtConEspNot.setText("");
            txtConEspNot.requestFocus();
            chrFlgMsj = 'S';
            chrFlgLst = 'S';
        } 
        flagNum(blnFlgNum);
        flagCon(blnFlgCon);
        if (txtConEspNot.getText().length()>0 && blnFlgNum == false && blnFlgCon == false && chrFlgMsj == 'N') {
            String numero = txtConEspNot.getText();
            objUti.isNumero(numero);
            if (objUti.isNumero(numero) == false) {
                 strTit="Mensaje del sistema Zafiro";
                 strMsg="El campo <<Notificaciòn Nº>> solo acepta valores numericos.\nDigite caracteres validos y vuelva a intentarlo.";
                 oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                 txtConEspNot.setText("");
                 txtConEspNot.requestFocus();
                 chrFlgMsj = 'S';
            }
        }
        blnFlgNum = false;
        blnFlgCon = false;
    chrFlgMsj = 'N';
    chrFlgLst = 'N';
    } 
    }//GEN-LAST:event_txtConEspNotFocusLost

    private void txtSecDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSecDocFocusLost
     if (chrFlgLst == 'N') {
       if (txtSecDoc.getText().length()>7 && chrFlgLst == 'N'){ 
            tabGen.setSelectedIndex(0);
            strTit="Mensaje del sistema Zafiro";
            strMsg="El campo <<Secuencial>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
            txtSecDoc.setText("");
            txtSecDoc.requestFocus();
            chrFlgMsj = 'S';
            chrFlgLst = 'S';
        } 
        flagNum(blnFlgNum);
        flagCon(blnFlgCon);
        if (txtSecDoc.getText().length()>0 && blnFlgNum == false && blnFlgCon == false && chrFlgMsj == 'N') {
            String numero = txtSecDoc.getText();
            objUti.isNumero(numero);
            if (objUti.isNumero(numero) == false) {
                 strTit="Mensaje del sistema Zafiro";
                 strMsg="El campo <<Secuencial>> solo acepta valores numericos.\nDigite caracteres validos y vuelva a intentarlo.";
                 oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                 txtSecDoc.setText("");
                 txtSecDoc.requestFocus();
                 chrFlgMsj = 'S';
            }
        }
        blnFlgNum = false;
        blnFlgCon = false;
    chrFlgMsj = 'N';
    chrFlgLst = 'N';
    } 
    }//GEN-LAST:event_txtSecDocFocusLost

    private void txtAutSRIFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAutSRIFocusLost
     if (chrFlgLst == 'N') {
       if (txtAutSRI.getText().length()>10 && chrFlgLst == 'N'){ 
            tabGen.setSelectedIndex(0);
            strTit="Mensaje del sistema Zafiro";
            strMsg="El campo <<Autorizaciòn del SRI>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
            txtAutSRI.setText("");
            txtAutSRI.requestFocus();
            chrFlgMsj = 'S';
            chrFlgLst = 'S';
        } 
        flagNum(blnFlgNum);
        flagCon(blnFlgCon);
        if (txtAutSRI.getText().length()>0 && blnFlgNum == false && blnFlgCon == false && chrFlgMsj == 'N') {
            String numero = txtAutSRI.getText();
            objUti.isNumero(numero);
            if (objUti.isNumero(numero) == false) {
                 strTit="Mensaje del sistema Zafiro";
                 strMsg="El campo <<Autorizaciòn del SRI>> solo acepta valores numericos.\nDigite caracteres validos y vuelva a intentarlo.";
                 oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                 txtAutSRI.setText("");
                 txtAutSRI.requestFocus();
                 chrFlgMsj = 'S';
            }
        }
        blnFlgNum = false;
        blnFlgCon = false;
    chrFlgMsj = 'N';
    chrFlgLst = 'N';
    } 
    }//GEN-LAST:event_txtAutSRIFocusLost

    private void txtFaxLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFaxLocFocusLost
   if (chrFlgLst == 'N') {
       if (txtFaxLoc.getText().length()>30 && chrFlgLst == 'N'){ 
            strTit="Mensaje del sistema Zafiro";
            strMsg="El campo <<Fax>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
            txtFaxLoc.setText("");
            txtFaxLoc.requestFocus();
            chrFlgMsj = 'S';
            chrFlgLst = 'S';
        } 
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
         }  
    }//GEN-LAST:event_txtFaxLocFocusLost

    private void txtTelLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelLocFocusLost
   if (chrFlgLst == 'N') {
       if (txtTelLoc.getText().length()>60 && chrFlgLst == 'N'){ 
            strTit="Mensaje del sistema Zafiro";
            strMsg="El campo <<Telèfono>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
            txtTelLoc.setText("");
            txtTelLoc.requestFocus();
            chrFlgMsj = 'S';
            chrFlgLst = 'S';
        } 
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
         }  
    }//GEN-LAST:event_txtTelLocFocusLost

    private void txtDirLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDirLocFocusLost
      if (chrFlgLst == 'N') {
       if (txtDirLoc.getText().length()>120 && chrFlgLst == 'N'){ 
            strTit="Mensaje del sistema Zafiro";
            strMsg="El campo <<Direcciòn>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
            txtDirLoc.setText("");
            txtDirLoc.requestFocus();
            chrFlgMsj = 'S';
            chrFlgLst = 'S';
        } 
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
         }   
    }//GEN-LAST:event_txtDirLocFocusLost

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
   if (chrFlgLst == 'N') {
       if (txtNomLoc.getText().length()>80 && chrFlgLst == 'N'){ 
            strTit="Mensaje del sistema Zafiro";
            strMsg="El campo <<Nombre>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
            txtNomLoc.setText("");
            txtNomLoc.requestFocus();
            chrFlgMsj = 'S';
            chrFlgLst = 'S';
        } 
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
         }  
    }//GEN-LAST:event_txtNomLocFocusLost

    private void cboCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCiuActionPerformed
              blnCam = true;  
    }//GEN-LAST:event_cboCiuActionPerformed

    private void cboEstLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstLocActionPerformed
        blnCam = true;    
    }//GEN-LAST:event_cboEstLocActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
       
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
                String strSQL = "  SELECT * FROM tbm_loc"+ 
                     " Where co_emp=" + rstCab.getString("co_emp") +
                      " AND co_loc="    + rstCab.getString("co_loc") ;
//                System.out.println(strSQL);
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    limpiarPant();
                    txtCodLoc.setText(rst.getString("co_loc"));
                    txtCodLoc.setText(((rst.getString("co_loc")==null)?"":rst.getString("co_loc")));
                    txtNomLoc.setText(((rst.getString("tx_nom")==null)?"":rst.getString("tx_nom")));
                    txtDirLoc.setText(((rst.getString("tx_dir")==null)?"":rst.getString("tx_dir")));
                    txtTelLoc.setText(((rst.getString("tx_tel")==null)?"":rst.getString("tx_tel")));
                    txtFaxLoc.setText(((rst.getString("tx_fax")==null)?"":rst.getString("tx_fax")));
                    txtAutSRI.setText(((rst.getString("tx_autSRI")==null)?"":rst.getString("tx_autSRI")));
                    txtConEspNot.setText(((rst.getString("ne_conespnot")==null)?"":rst.getString("ne_conespnot")));
                    txtConEspRes.setText(((rst.getString("ne_conespres")==null)?"":rst.getString("ne_conespres")));
                    txtSecDoc.setText(((rst.getString("tx_secdoc")==null)?"":rst.getString("tx_secdoc")));
                    txtCodCtaCostos.setText((rst.getString("co_ctacosven")==null)?"":rst.getString("co_ctacosven"));
                    txtCodCtaDesCom.setText((rst.getString("co_ctadescom")==null)?"":rst.getString("co_ctadescom"));
                    txtCodCtaDesVen.setText((rst.getString("co_ctadesven")==null)?"":rst.getString("co_ctadesven"));                    
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));
                    String strReg = rst.getString("st_reg");
                    objUti.setItemCombo(cboCiu,vecCodCiu,rst.getString("co_ciu"));
                    txtNomCtaCostos.setText(objCtaCtb.getNomCta(rst.getInt("co_ctacosven")));
                    txtNomCtaDesCom.setText(objCtaCtb.getNomCta(rst.getInt("co_ctadescom")));
                    txtNomCtaDesVen.setText(objCtaCtb.getNomCta(rst.getInt("co_ctadesven")));

                    if(strReg.equals("I"))
                        cboEstLoc.setSelectedIndex(1);
                    else
                        cboEstLoc.setSelectedIndex(0);
                    
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
            super(ifrtem,objZafParSis);
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
                txtNomLoc.requestFocus();
                txtCodLoc.setEditable(false);
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
                        strSQL = " Update tbm_loc set st_reg = 'I' " +
                                 " where co_emp =" + objZafParSis.getCodigoEmpresa() +" and co_loc =" + txtCodLoc.getText();
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
                if (mensaje("Para modificar primero debe reactivar el local \n ¿Desea reactivarlo?")==JOptionPane.YES_OPTION){
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
        
        public void clickConsultar() {
            chrFlgEst = 'C';
        }
        
        public void clickEliminar() {
        }
        
        public void clickImprimir() {
        }
        
        public void clickCancelar(){
            
        }
        public void clickModificar() {
            txtCodLoc.setEditable(false);
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
                        strSql = strSql + "SELECT * FROM tbm_loc ";
                        strSql = strSql + " WHERE co_emp = " + objZafParSis.getCodigoEmpresa() +"";
                        strSql = strSql + strFlt + " order by co_loc";
                        rstCab=stmCab.executeQuery(strSql);

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
                            rstCab=null;
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
    private int mensaje(String strMsg){
        return oppMsg.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);                
    }     
    private boolean Reactivar()
    {
        try{
            java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            try{
                if (con!=null){
                    java.sql.PreparedStatement pstReg;
                    String strSQL="";
                    strSQL = " Update tbm_loc set st_reg= 'A' where co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_loc = " + txtCodLoc.getText();
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
     private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
     }//GEN-LAST:event_txtCodLocActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCtaCostos;
    private javax.swing.JButton butCtaDesCom;
    private javax.swing.JButton butCtaDesVen;
    private javax.swing.JComboBox cboCiu;
    private javax.swing.JComboBox cboEstLoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblAutSRI;
    private javax.swing.JLabel lblCiu;
    private javax.swing.JLabel lblCodLoc;
    private javax.swing.JLabel lblConEspNot;
    private javax.swing.JLabel lblConEspRes;
    private javax.swing.JLabel lblDirLoc;
    private javax.swing.JLabel lblEstLoc;
    private javax.swing.JLabel lblFaxLoc;
    private javax.swing.JLabel lblMaeLoc;
    private javax.swing.JLabel lblNomLoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblSecDoc;
    private javax.swing.JLabel lblTelLoc;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panGen;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtAutSRI;
    private javax.swing.JTextField txtCodCtaCostos;
    private javax.swing.JTextField txtCodCtaDesCom;
    private javax.swing.JTextField txtCodCtaDesVen;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtConEspNot;
    private javax.swing.JTextField txtConEspRes;
    private javax.swing.JTextField txtDirLoc;
    private javax.swing.JTextField txtFaxLoc;
    private javax.swing.JTextField txtNomCtaCostos;
    private javax.swing.JTextField txtNomCtaDesCom;
    private javax.swing.JTextField txtNomCtaDesVen;
    private javax.swing.JTextField txtNomLoc;
    private javax.swing.JTextField txtSecDoc;
    private javax.swing.JTextField txtTelLoc;
    // End of variables declaration//GEN-END:variables
}
/*
 * ZAFMAE01.java
 *
 * Created on 16 de septiembre de 2004, 11:02
 */
package Maestros.ZafMae01;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import Librerias.ZafToolBar.*;
import Librerias.ZafUtil.*;
import Librerias.ZafParSis.*;

/**
 *
 * @author  jresabala
 * corregido por jsalazar 22/dic/2005
 * ultima actualizacion 26/dic/2005
 */

public class ZafMae01 extends javax.swing.JInternalFrame {
        java.sql.Connection conCab;
        java.sql.Statement stmCab;
        java.sql.ResultSet rstCab; //Variable para manipular registro de la tabla en ejecucion
        String strSql, strAux; //Variable de tipo cadena en la cual se almacenara el Query
        String strTit, strMsg;//Variable de tipo cadena en la cual se almacenara los mnsajes del sistema
        int intCon; //Contador de vector
        char chrEst;//Variable para guardar el estado del registro
        char chrFlgEst;//Bandera para saber en que estado se encuentra la forma 
        int intNumReg;//Variable que almacena el codigo
        JOptionPane oppMsg;//Objeto de tipo OptionPane para presentar Mensajes
        thetoolbar objTooBar;//Objeto de tipo objTooBar para poder manipular la clase ZafToolBar
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
        private Vector vecTipPer;
        
        private final String VERSION = " v0.1";
        /** Creates new form ZAFMAE01 */
    public ZafMae01(ZafParSis obj) {
        initComponents();
        addListenerCambios();
        try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();                 
            objUti = new ZafUtil();
            oppMsg=new JOptionPane();
            objTooBar = new thetoolbar(this);
            objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb(objZafParSis);
            this.getContentPane().add(objTooBar,"South");
            this.setTitle(objZafParSis.getNombreMenu()+VERSION);
            txtRuc.setBackground(objZafParSis.getColorCamposObligatorios());
            txtPorIvaCom.setBackground(objZafParSis.getColorCamposObligatorios());
            txtPorIvaVen.setBackground(objZafParSis.getColorCamposObligatorios());
            txtCodEmp.setBackground(objZafParSis.getColorCamposSistema());
            vecTipPer = new Vector();
            objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), 
                                 objZafParSis.getClaveBaseDatos(), "SELECT co_tipper,tx_deslar FROM tbm_tipper Where co_emp = " + (txtCodEmp.getText().equals("")?"0":txtCodEmp.getText()) + " order by co_tipper", cboTipPer, vecTipPer);      

            jfrThis=this;
           this.setBounds(10,10, 700, 450);            
        }catch (CloneNotSupportedException e){
             objUti.mostrarMsgErr_F1(this, e);
        }                 
       }
    
  
    private void clearTxtContabilidad(){
        txtCodCtaBco.setText("");
        txtCodCtaCaj.setText("");
        txtCodCtaGas.setText("");
        txtCodCtaIvaCom.setText("");
        txtCodCtaIvaVen.setText("");
        txtCodCtaRes.setText("");
        txtCodCtaRetFteCom.setText("");
        txtCodCtaRetFteVen.setText("");
        txtCodCtaRetIvaCom.setText("");
        txtCodCtaRetIvaVen.setText("");
        txtAltCtaBco.setText("");
        txtAltCtaCaj.setText("");
        txtAltCtaGas.setText("");
        txtAltCtaIvaCom.setText("");
        txtAltCtaIvaVen.setText("");
        txtAltCtaRes.setText("");
        txtAltCtaRetFteCom.setText("");
        txtAltCtaRetFteVen.setText("");
        txtAltCtaRetIvaCom.setText("");
        txtAltCtaRetIvaVen.setText("");
        txtNomCtaBco.setText("");
        txtNomCtaCaj.setText("");
        txtNomCtaGas.setText("");
        txtNomCtaIvaCom.setText("");
        txtNomCtaIvaVen.setText("");
        txtNomCtaRes.setText("");
        txtNomCtaRetFteCom.setText("");
        txtNomCtaRetFteVen.setText("");
        txtNomCtaRetIvaCom.setText("");
        txtNomCtaRetIvaVen.setText("");
        
    }
 /**Funcion que me permite guardar los datos de la Empresa*/   

    public boolean grabarInformacion()
	{
            try
		{
			java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos()); 		
                        if (con != null)
			{
                                java.sql.Statement stm = con.createStatement();
                                strSql = "";
                                strSql = strSql + "SELECT MAX(co_emp)+1 as co_emp from tbm_emp";
                                java.sql.ResultSet rst = stm.executeQuery(strSql);
                                
                                if (rst.next()){
                                    intNumReg = rst.getInt("co_emp");
                                    }
                                else{
                                    intNumReg=1;
                                    }
                             if(cboEstReg.getSelectedIndex() == 0)
                                chrEst='A';
                             else
                                chrEst='I';                        

                                stm = con.createStatement();
				strSql="";	
				strSql = strSql + " INSERT INTO tbm_emp (co_emp, tx_ruc, tx_nom, tx_dir, tx_tel, tx_fax, tx_dirweb, tx_corele, tx_obs1, tx_obs2,co_ctaivacom,co_ctaivaven,co_ctaban,co_ctacaj,co_ctares,co_ctagas,co_ctaretftecom,co_ctaretfteven,co_ctaretivacom,co_ctaretivaven, nd_ivaCom, nd_ivaVen, st_reg)";
				strSql = strSql + " VALUES (";
				strSql = strSql + " " + intNumReg + "";
				strSql = strSql + " ," + ((strAux=txtRuc.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                                strSql = strSql + " ," + ((strAux=txtNomEmp.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                                strSql = strSql + " ," + ((strAux=txtDirEmp.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                                strSql = strSql + " ," + ((strAux=txtTelEmp.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                                strSql = strSql + " ," + ((strAux=txtFaxEmp.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                                strSql = strSql + " ," + ((strAux=txtWebEmp.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                                strSql = strSql + " ," + ((strAux=txtEmaEmp.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                                strSql = strSql + " ," + ((strAux=txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                                strSql = strSql + " ," + ((strAux=txaObs2.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                                strSql = strSql + " ," + (txtCodCtaIvaCom.getText().equals("")?"Null":txtCodCtaIvaCom.getText()) +
						  " ," + (txtCodCtaIvaVen.getText().equals("")?"Null":txtCodCtaIvaCom.getText()) +
                                                  " ," + (txtCodCtaBco.getText().equals("")?"Null":txtCodCtaBco.getText()) +
                                                  " ," + (txtCodCtaCaj.getText().equals("")?"Null":txtCodCtaCaj.getText()) +
                                                  " ," + (txtCodCtaRes.getText().equals("")?"Null":txtCodCtaRes.getText()) +
                                                  " ," + (txtCodCtaGas.getText().equals("")?"Null":txtCodCtaGas.getText()) +
                                                  " ," + (txtCodCtaRetFteCom.getText().equals("")?"Null":txtCodCtaRetFteCom.getText()) +
                                                  " ," + (txtCodCtaRetFteVen.getText().equals("")?"Null":txtCodCtaRetFteVen.getText()) +
                                                  " ," + (txtCodCtaRetIvaCom.getText().equals("")?"Null":txtCodCtaRetIvaCom.getText()) +
                                                  " ," + (txtCodCtaRetIvaVen.getText().equals("")?"Null":txtCodCtaRetIvaVen.getText()) ;
                                 if (txtPorIvaCom.getText().equals(""))
                                strSql = strSql + ", " + null + "";
                                else{
                                strSql = strSql + "," + txtPorIvaCom.getText() + "";}
                                if (txtPorIvaVen.getText().equals(""))
                                strSql = strSql + ", " + null + "";
                                else{
                                strSql = strSql + "," + txtPorIvaVen.getText() + "";}
                                strSql = strSql + " ,'" + chrEst + "'";
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
        try
		{
			java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos()); 		
                        try{
                            if (con != null)
                            {
                                    con.setAutoCommit(false);
                                    java.sql.PreparedStatement pstReg;
                                    strSql="";	
                                    strSql = " DELETE FROM tbm_emp WHERE co_emp=" + txtCodEmp.getText() + "";
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
            try{
                if (con != null){
                    java.sql.PreparedStatement pstReg;
                    String strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
                    if(cboEstReg.getSelectedIndex()==0)
                        chrEst='A';
                    else
                        chrEst='I';
                            
                    strSql="";	
                    strSql = strSql + " UPDATE tbm_emp SET";
                    strSql = strSql + " tx_ruc=" + ((strAux=txtRuc.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql = strSql + ", tx_nom=" + ((strAux=txtNomEmp.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql = strSql + ", tx_dir=" + ((strAux=txtDirEmp.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql = strSql + ", tx_tel=" + ((strAux=txtTelEmp.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql = strSql + ", tx_fax=" + ((strAux=txtFaxEmp.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql = strSql + ", tx_dirweb=" + ((strAux=txtWebEmp.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql = strSql + ", tx_corele=" + ((strAux=txtEmaEmp.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql = strSql + ", tx_obs1=" + ((strAux=txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql = strSql + ", tx_obs2=" + ((strAux=txaObs2.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    if (txtPorIvaCom.getText().equals(""))
                        strSql = strSql + ", nd_ivaCom = " + null + "";
                    else{
                        strSql = strSql + ", nd_ivaCom = " + txtPorIvaCom.getText() + "";} 
                    if (txtPorIvaVen.getText().equals(""))
                        strSql = strSql + ", nd_ivaVen = " + null + "";
                    else{
                        strSql = strSql + ", nd_ivaVen = " + txtPorIvaVen.getText() + "";}      
                    strSql = strSql + ", st_reg='" + chrEst + "'";
                    strSql = strSql + " ,co_ctaivacom=" + (txtCodCtaIvaCom.getText().equals("")?"Null":txtCodCtaIvaCom.getText()) +
                                      " ,co_ctaivaven=" + (txtCodCtaIvaVen.getText().equals("")?"Null":txtCodCtaIvaCom.getText()) +
                                      " ,co_ctaban=" + (txtCodCtaBco.getText().equals("")?"Null":txtCodCtaBco.getText()) +
                                      " ,co_ctacaj=" + (txtCodCtaCaj.getText().equals("")?"Null":txtCodCtaCaj.getText()) +
                                      " ,co_ctares=" + (txtCodCtaRes.getText().equals("")?"Null":txtCodCtaRes.getText()) +
                                      " ,co_ctagas=" + (txtCodCtaGas.getText().equals("")?"Null":txtCodCtaGas.getText()) +
                                      " ,co_ctaretftecom=" + (txtCodCtaRetFteCom.getText().equals("")?"Null":txtCodCtaRetFteCom.getText()) +
                                      " ,co_ctaretfteven=" + (txtCodCtaRetFteVen.getText().equals("")?"Null":txtCodCtaRetFteVen.getText()) +
                                      " ,co_ctaretivacom=" + (txtCodCtaRetIvaCom.getText().equals("")?"Null":txtCodCtaRetIvaCom.getText()) +
                                      " ,co_ctaretivaven=" + (txtCodCtaRetIvaVen.getText().equals("")?"Null":txtCodCtaRetIvaVen.getText()) +
                                      " ,fe_ultmod = '" + strFecSis + "' "+
                                      " ,co_usrmod ="+objZafParSis.getCodigoUsuario();
                    if (cboTipPer.getSelectedIndex()>0){
                        strSql = strSql + ", co_tipper = " + vecTipPer.get(cboTipPer.getSelectedIndex());
                    }
                    strSql = strSql + " WHERE co_emp=" + txtCodEmp.getText() + "";
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
        txtCodEmp.setText("");
        txtRuc.setText("");
        txtNomEmp.setText("");
        txtDirEmp.setText("");
        txtTelEmp.setText("");
        txtFaxEmp.setText("");
        txtWebEmp.setText("");
        txtEmaEmp.setText("");
        txtPorIvaVen.setText("");
        txtPorIvaCom.setText("");
        txaObs1.setText("");
        txaObs2.setText(""); 
        clearTxtContabilidad();
    }
    
     private String FilSql() {               
                String sqlFiltro = "";

                //Agregando filtro por codigo de empresa
                strAux = txtCodEmp.getText();
                if(!txtCodEmp.getText().equals(""))
                    sqlFiltro = sqlFiltro + " AND ( co_emp) LIKE " + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "";

                //Agregando filtro por ruc
                strAux = txtRuc.getText();
                if(!txtRuc.getText().equals(""))
                    sqlFiltro = sqlFiltro + " AND LOWER( tx_ruc) LIKE '" + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";

                //Agregando filtro por nombre de empresa
                strAux = txtNomEmp.getText();
                if(!txtNomEmp.getText().equals(""))
                    sqlFiltro = sqlFiltro + " AND LOWER(tx_nom) LIKE '" + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
                    
                //Agregando filtro por Direccion
                strAux = txtDirEmp.getText();
                if(!txtDirEmp.getText().equals(""))
                    sqlFiltro = sqlFiltro + " AND LOWER(tx_dir) LIKE '" +  strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";

                return sqlFiltro ;
    }
     
     public void  refrescaDatos(){
        try{
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            java.sql.Statement stm = con.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY );
            if (con!=null){                
                    String strSQL = " Select co_emp,co_ctaivacom,co_ctaivaven,co_ctaban,co_ctacaj,co_ctares,co_ctagas,co_ctaretftecom,co_ctaretfteven,co_ctaretivacom,co_ctaretivaven from tbm_emp as emp " +
                                    " where emp.co_emp = " + rstCab.getString("co_emp");
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);
                    if (rst.next()){
                        txtCodCtaBco.setText(rst.getString("co_ctaban"));
                        txtCodCtaCaj.setText(rst.getString("co_ctacaj"));
                        txtCodCtaGas.setText(rst.getString("co_ctagas"));
                        txtCodCtaIvaCom.setText(rst.getString("co_ctaivacom"));
                        txtCodCtaIvaVen.setText(rst.getString("co_ctaivaven"));
                        txtCodCtaRes.setText(rst.getString("co_ctares"));
                        txtCodCtaRetFteCom.setText(rst.getString("co_ctaretftecom"));
                        txtCodCtaRetFteVen.setText(rst.getString("co_ctaretfteven"));
                        txtCodCtaRetIvaCom.setText(rst.getString("co_ctaretivacom"));
                        txtCodCtaRetIvaVen.setText(rst.getString("co_ctaretivaven"));                        
                        if (!txtCodCtaBco.getText().equals("")) txtAltCtaBco.setText(objCtaCtb.getNumCtaCtb(rst.getInt("co_emp"),rst.getInt("co_ctaban")));
                        if (!txtCodCtaCaj.getText().equals("")) txtAltCtaCaj.setText(objCtaCtb.getNumCtaCtb(rst.getInt("co_emp"),rst.getInt("co_ctacaj")));
                        if (!txtCodCtaGas.getText().equals("")) txtAltCtaGas.setText(objCtaCtb.getNumCtaCtb(rst.getInt("co_emp"),rst.getInt("co_ctagas")));
                        if (!txtCodCtaIvaCom.getText().equals("")) txtAltCtaIvaCom.setText(objCtaCtb.getNumCtaCtb(rst.getInt("co_emp"),rst.getInt("co_ctaivacom")));
                        if (!txtCodCtaIvaVen.getText().equals("")) txtAltCtaIvaVen.setText(objCtaCtb.getNumCtaCtb(rst.getInt("co_emp"),rst.getInt("co_ctaivaven")));
                        if (!txtCodCtaRes.getText().equals("")) txtAltCtaRes.setText(objCtaCtb.getNumCtaCtb(rst.getInt("co_emp"),rst.getInt("co_ctares")));
                        if (!txtCodCtaRetFteCom.getText().equals("")) txtAltCtaRetFteCom.setText(objCtaCtb.getNumCtaCtb(rst.getInt("co_emp"),rst.getInt("co_ctaretftecom")));
                        if (!txtCodCtaRetFteVen.getText().equals("")) txtAltCtaRetFteVen.setText(objCtaCtb.getNumCtaCtb(rst.getInt("co_emp"),rst.getInt("co_ctaretfteven")));
                        if (!txtCodCtaRetIvaCom.getText().equals("")) txtAltCtaRetIvaCom.setText(objCtaCtb.getNumCtaCtb(rst.getInt("co_emp"),rst.getInt("co_ctaretivacom")));
                        if (!txtCodCtaRetIvaVen.getText().equals("")) txtAltCtaRetIvaVen.setText(objCtaCtb.getNumCtaCtb(rst.getInt("co_emp"),rst.getInt("co_ctaretivaven")));
                        if (!txtCodCtaBco.getText().equals("")) txtNomCtaBco.setText(objCtaCtb.getNomCta(rst.getInt("co_emp"),rst.getInt("co_ctaban")));
                        if (!txtCodCtaCaj.getText().equals("")) txtNomCtaCaj.setText(objCtaCtb.getNomCta(rst.getInt("co_emp"),rst.getInt("co_ctacaj")));
                        if (!txtCodCtaGas.getText().equals("")) txtNomCtaGas.setText(objCtaCtb.getNomCta(rst.getInt("co_emp"),rst.getInt("co_ctagas")));
                        if (!txtCodCtaIvaCom.getText().equals("")) txtNomCtaIvaCom.setText(objCtaCtb.getNomCta(rst.getInt("co_emp"),rst.getInt("co_ctaivacom")));
                        if (!txtCodCtaIvaVen.getText().equals("")) txtNomCtaIvaVen.setText(objCtaCtb.getNomCta(rst.getInt("co_emp"),rst.getInt("co_ctaivaven")));
                        if (!txtCodCtaRes.getText().equals("")) txtNomCtaRes.setText(objCtaCtb.getNomCta(rst.getInt("co_ctares")));
                        if (!txtCodCtaRetFteCom.getText().equals("")) txtNomCtaRetFteCom.setText(objCtaCtb.getNomCta(rst.getInt("co_emp"),rst.getInt("co_ctaretftecom")));
                        if (!txtCodCtaRetFteVen.getText().equals("")) txtNomCtaRetFteVen.setText(objCtaCtb.getNomCta(rst.getInt("co_emp"),rst.getInt("co_ctaretfteven")));
                        if (!txtCodCtaRetIvaCom.getText().equals("")) txtNomCtaRetIvaCom.setText(objCtaCtb.getNomCta(rst.getInt("co_emp"),rst.getInt("co_ctaretivacom")));
                        if (!txtCodCtaRetIvaVen.getText().equals("")) txtNomCtaRetIvaVen.setText(objCtaCtb.getNomCta(rst.getInt("co_emp"),rst.getInt("co_ctaretivaven")));
                        
                    }
                                        
                    rst.close();
                    stm.close();
                    con.close();
              }
             blnCam = false;
        }catch(SQLException Evt){
                objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }catch(Exception Evt){
                objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }                       
    }
      private int Mensaje(){
            String strTit, strMsg;
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Deseà guardar los cambios efectuados a este registro?\n";
            strMsg+="Si no guarda los cambios perdera toda la informaciòn que no haya guardado.";
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            return obj.showConfirmDialog(jfrThis ,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);                
        }

      private int mensaje(String strMsg){
        return oppMsg.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);                
      }       
      private boolean isCamVal()
    {
       strSql="";
        strSql+=" SELECT emp.tx_ruc";
        strSql+=" FROM tbm_emp AS emp";
        strSql+=" WHERE emp.co_emp=" + objZafParSis.getCodigoEmpresa();
        strSql+=" AND emp.tx_ruc='" + txtRuc.getText().replaceAll("'", "''") + "'";
        if (!objUti.isCodigoUnico(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSql))
        {
            strTit="Mensaje del sistema Zafiro";
            strMsg="El RUC <<" + txtRuc.getText() + ">> ya existe.\nEscriba otro RUC y vuelva a intentarlo.";
            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
            txtRuc.selectAll();
            txtRuc.requestFocus();
            return false;
        } 
        return true;
      }
      
      public boolean valDat()
      {
           try
           {
          if (txtRuc.getText().equals("")){
                tabGen.setSelectedIndex(0);
                strTit="Mensaje del sistema Zafiro";
                strMsg="El campo <<Ruc>> es obligatorio.\nEscriba el Ruc para la Empresa y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                txtRuc.requestFocus();
                return false;
          }else if(txtRuc.getText().length()>0){                
                     String numero=txtRuc.getText(); 
                     objUti.isNumero(numero);
                    if(objUti.isNumero(numero)==false ){    
                        tabGen.setSelectedIndex(0);
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="El campo <<Ruc>> es numerico.\nDigite caracteres validos y vuelva a intentarlo.";
                        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                        txtRuc.setText("");
                        txtRuc.requestFocus();
                        return false;}
          }if (txtRuc.getText().length()!=13){
                tabGen.setSelectedIndex(0);
                strTit="Mensaje del sistema Zafiro";
                strMsg="El campo <<RUC>> debe contener 13 digitos.\nEscriba el Ruc para la Empresa y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                txtRuc.selectAll();
                txtRuc.requestFocus();
                return false;
        }
           if(txtPorIvaCom.getText().equals("")){
                tabGen.setSelectedIndex(0);
                strTit="Mensaje del sistema Zafiro";
                strMsg="El campo <<Porcentaje de iva en compra>> es obligatorio.\nEscriba el PORCENTAJE DE IVA EN COMPRA para la Empresa y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                txtPorIvaCom.requestFocus();
                return false;
           }else if(txtPorIvaVen.getText().equals("")){
                tabGen.setSelectedIndex(0);
                strTit="Mensaje del sistema Zafiro";
                strMsg="El campo <<Porcentaje de iva en venta>> es obligatorio.\nEscriba el PORCENTAJE DE IVA EN VENTA para la Empresa y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);       
                txtPorIvaVen.requestFocus();
                return false;
        } 
           }
       catch(Exception Evt)
     {
       objUti.mostrarMsgErr_F1(jfrThis, Evt);
             return false;  
     }
      return true;
      }
    
      private void addListenerCambios(){
            objLisCam = new LisTxt();
            txtCodEmp.setText("");
            txtCodEmp.getDocument().addDocumentListener(objLisCam);
            txtNomEmp.getDocument().addDocumentListener(objLisCam);
            txtDirEmp.getDocument().addDocumentListener(objLisCam);
            txtTelEmp.getDocument().addDocumentListener(objLisCam);
            txtFaxEmp.getDocument().addDocumentListener(objLisCam);
            txtWebEmp.getDocument().addDocumentListener(objLisCam);
            txtEmaEmp.getDocument().addDocumentListener(objLisCam);
            txtRuc.getDocument().addDocumentListener(objLisCam);
            txtPorIvaVen.getDocument().addDocumentListener(objLisCam);
            txtPorIvaCom.getDocument().addDocumentListener(objLisCam);
            txaObs1.getDocument().addDocumentListener(objLisCam);
            txaObs2.getDocument().addDocumentListener(objLisCam);
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
        if ((txtCodEmp.getText().equals("")) && (txtNomEmp.getText().equals("")) && (txtDirEmp.getText().equals("")) && (txtTelEmp.getText().equals("")) && (txtFaxEmp.getText().equals("")) && (txtWebEmp.getText().equals("")) && (txtEmaEmp.getText().equals("")) && (txtRuc.getText().equals("")) && (txtPorIvaVen.getText().equals("")) && (txtPorIvaCom.getText().equals("")) && (txaObs1.getText().equals("")) && (txaObs2.getText().equals(""))){    
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
        lblnMaeEmp = new javax.swing.JLabel();
        panCon = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        lblEstReg = new javax.swing.JLabel();
        lblTel = new javax.swing.JLabel();
        lblWeb = new javax.swing.JLabel();
        lblDir = new javax.swing.JLabel();
        lblNom = new javax.swing.JLabel();
        lblRuc = new javax.swing.JLabel();
        lblCod = new javax.swing.JLabel();
        lblEma = new javax.swing.JLabel();
        lblFax = new javax.swing.JLabel();
        cboEstReg = new javax.swing.JComboBox();
        txtCodEmp = new javax.swing.JTextField();
        txtTelEmp = new javax.swing.JTextField();
        txtDirEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        txtRuc = new javax.swing.JTextField();
        txtEmaEmp = new javax.swing.JTextField();
        txtFaxEmp = new javax.swing.JTextField();
        txtWebEmp = new javax.swing.JTextField();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        lblPorIvaVen = new javax.swing.JLabel();
        lblPorIvaCom = new javax.swing.JLabel();
        txtPorIvaVen = new javax.swing.JTextField();
        txtPorIvaCom = new javax.swing.JTextField();
        cboTipPer = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        panCta = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtCodCtaIvaCom = new javax.swing.JTextField();
        txtAltCtaIvaCom = new javax.swing.JTextField();
        txtNomCtaIvaCom = new javax.swing.JTextField();
        butCtaIvaCom = new javax.swing.JButton();
        txtCodCtaIvaVen = new javax.swing.JTextField();
        txtAltCtaIvaVen = new javax.swing.JTextField();
        txtNomCtaIvaVen = new javax.swing.JTextField();
        butCtaIvaVen = new javax.swing.JButton();
        txtCodCtaRes = new javax.swing.JTextField();
        txtCodCtaGas = new javax.swing.JTextField();
        txtAltCtaRes = new javax.swing.JTextField();
        txtAltCtaGas = new javax.swing.JTextField();
        txtNomCtaRes = new javax.swing.JTextField();
        txtNomCtaGas = new javax.swing.JTextField();
        butCtaRes = new javax.swing.JButton();
        butCtaGas = new javax.swing.JButton();
        txtCodCtaRetFteCom = new javax.swing.JTextField();
        txtCodCtaRetFteVen = new javax.swing.JTextField();
        txtAltCtaRetFteCom = new javax.swing.JTextField();
        txtAltCtaRetFteVen = new javax.swing.JTextField();
        txtNomCtaRetFteCom = new javax.swing.JTextField();
        txtNomCtaRetFteVen = new javax.swing.JTextField();
        butCtaRetFteCom = new javax.swing.JButton();
        butCtaRetFteVen = new javax.swing.JButton();
        txtCodCtaRetIvaCom = new javax.swing.JTextField();
        txtCodCtaRetIvaVen = new javax.swing.JTextField();
        txtAltCtaRetIvaCom = new javax.swing.JTextField();
        txtAltCtaRetIvaVen = new javax.swing.JTextField();
        txtNomCtaRetIvaCom = new javax.swing.JTextField();
        txtNomCtaRetIvaVen = new javax.swing.JTextField();
        butCtaRetIvaCom = new javax.swing.JButton();
        butCtaRetIvaVen = new javax.swing.JButton();
        txtCodCtaBco = new javax.swing.JTextField();
        txtCodCtaCaj = new javax.swing.JTextField();
        txtAltCtaBco = new javax.swing.JTextField();
        txtAltCtaCaj = new javax.swing.JTextField();
        txtNomCtaBco = new javax.swing.JTextField();
        txtNomCtaCaj = new javax.swing.JTextField();
        butCtaBco = new javax.swing.JButton();
        butCtaCaj = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

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

        lblnMaeEmp.setFont(new java.awt.Font("SansSerif", 1, 11));
        lblnMaeEmp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblnMaeEmp.setText("Nombre de Empresa");
        getContentPane().add(lblnMaeEmp, java.awt.BorderLayout.NORTH);

        panCon.setLayout(new java.awt.BorderLayout());

        panGen.setLayout(null);

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs1.setText("Observacion 1:");
        panGen.add(lblObs1);
        lblObs1.setBounds(10, 230, 100, 15);

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs2.setText("Observacion 2:");
        panGen.add(lblObs2);
        lblObs2.setBounds(10, 270, 100, 15);

        lblEstReg.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblEstReg.setText("Estado del registro");
        panGen.add(lblEstReg);
        lblEstReg.setBounds(10, 160, 100, 20);

        lblTel.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTel.setText("Tel\u00e8fonos:");
        panGen.add(lblTel);
        lblTel.setBounds(8, 92, 100, 20);

        lblWeb.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblWeb.setText("Web:");
        panGen.add(lblWeb);
        lblWeb.setBounds(8, 116, 100, 20);

        lblDir.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDir.setText("Direcci\u00f2n:");
        panGen.add(lblDir);
        lblDir.setBounds(8, 72, 100, 20);

        lblNom.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNom.setText("Nombre:");
        panGen.add(lblNom);
        lblNom.setBounds(8, 48, 100, 20);

        lblRuc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblRuc.setText("R.U.C:");
        panGen.add(lblRuc);
        lblRuc.setBounds(8, 28, 100, 20);

        lblCod.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCod.setText("Codigo:");
        panGen.add(lblCod);
        lblCod.setBounds(8, 4, 100, 20);

        lblEma.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblEma.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEma.setText("Email:");
        panGen.add(lblEma);
        lblEma.setBounds(268, 116, 60, 20);

        lblFax.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFax.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFax.setText("Fax:");
        panGen.add(lblFax);
        lblFax.setBounds(268, 92, 60, 20);

        cboEstReg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Inactivo" }));
        cboEstReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstRegActionPerformed(evt);
            }
        });

        panGen.add(cboEstReg);
        cboEstReg.setBounds(110, 160, 120, 20);

        txtCodEmp.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodEmp.setNextFocusableComponent(txtRuc);
        panGen.add(txtCodEmp);
        txtCodEmp.setBounds(108, 4, 130, 20);

        txtTelEmp.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTelEmp.setNextFocusableComponent(txtFaxEmp);
        txtTelEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTelEmpFocusLost(evt);
            }
        });

        panGen.add(txtTelEmp);
        txtTelEmp.setBounds(108, 92, 160, 20);

        txtDirEmp.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDirEmp.setNextFocusableComponent(txtTelEmp);
        txtDirEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDirEmpFocusLost(evt);
            }
        });

        panGen.add(txtDirEmp);
        txtDirEmp.setBounds(108, 70, 380, 20);

        txtNomEmp.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNomEmp.setNextFocusableComponent(txtDirEmp);
        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });

        panGen.add(txtNomEmp);
        txtNomEmp.setBounds(108, 48, 380, 20);

        txtRuc.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtRuc.setNextFocusableComponent(txtNomEmp);
        panGen.add(txtRuc);
        txtRuc.setBounds(108, 26, 130, 20);

        txtEmaEmp.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEmaEmp.setNextFocusableComponent(txtPorIvaVen);
        txtEmaEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmaEmpFocusLost(evt);
            }
        });

        panGen.add(txtEmaEmp);
        txtEmaEmp.setBounds(328, 114, 160, 20);

        txtFaxEmp.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtFaxEmp.setNextFocusableComponent(txtWebEmp);
        txtFaxEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFaxEmpFocusLost(evt);
            }
        });

        panGen.add(txtFaxEmp);
        txtFaxEmp.setBounds(328, 92, 160, 20);

        txtWebEmp.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtWebEmp.setNextFocusableComponent(txtEmaEmp);
        txtWebEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtWebEmpFocusLost(evt);
            }
        });

        panGen.add(txtWebEmp);
        txtWebEmp.setBounds(108, 114, 160, 20);

        txaObs1.setNextFocusableComponent(txaObs2);
        txaObs1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txaObs1FocusLost(evt);
            }
        });

        spnObs1.setViewportView(txaObs1);

        panGen.add(spnObs1);
        spnObs1.setBounds(110, 220, 380, 40);

        txaObs2.setNextFocusableComponent(cboEstReg);
        txaObs2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txaObs2FocusLost(evt);
            }
        });

        spnObs2.setViewportView(txaObs2);

        panGen.add(spnObs2);
        spnObs2.setBounds(110, 260, 380, 40);

        lblPorIvaVen.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblPorIvaVen.setText("% IVA ventas:");
        panGen.add(lblPorIvaVen);
        lblPorIvaVen.setBounds(268, 140, 60, 15);

        lblPorIvaCom.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblPorIvaCom.setText("% IVA compra:");
        panGen.add(lblPorIvaCom);
        lblPorIvaCom.setBounds(8, 136, 100, 20);

        txtPorIvaVen.setNextFocusableComponent(txaObs1);
        txtPorIvaVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPorIvaVenActionPerformed(evt);
            }
        });
        txtPorIvaVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPorIvaVenFocusLost(evt);
            }
        });

        panGen.add(txtPorIvaVen);
        txtPorIvaVen.setBounds(328, 136, 160, 20);

        txtPorIvaCom.setNextFocusableComponent(txtPorIvaCom);
        txtPorIvaCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPorIvaComActionPerformed(evt);
            }
        });
        txtPorIvaCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPorIvaComFocusLost(evt);
            }
        });

        panGen.add(txtPorIvaCom);
        txtPorIvaCom.setBounds(108, 136, 160, 20);

        panGen.add(cboTipPer);
        cboTipPer.setBounds(110, 190, 160, 21);

        jLabel1.setText("Tipo Persona:");
        panGen.add(jLabel1);
        jLabel1.setBounds(10, 190, 100, 15);

        tabGen.addTab("General", panGen);

        panCta.setLayout(null);

        jLabel2.setText("Iva en Compras:");
        panCta.add(jLabel2);
        jLabel2.setBounds(20, 30, 160, 15);

        jLabel3.setText("Iva en Ventas:");
        panCta.add(jLabel3);
        jLabel3.setBounds(20, 55, 160, 15);

        jLabel4.setText("Bancos:");
        panCta.add(jLabel4);
        jLabel4.setBounds(20, 80, 160, 15);

        jLabel5.setText("Caja:");
        panCta.add(jLabel5);
        jLabel5.setBounds(20, 110, 160, 15);

        jLabel6.setText("Resultados:");
        panCta.add(jLabel6);
        jLabel6.setBounds(20, 140, 160, 15);

        jLabel7.setText("Gas:");
        panCta.add(jLabel7);
        jLabel7.setBounds(20, 170, 160, 15);

        jLabel8.setText("Retencion Fte. Compras:");
        panCta.add(jLabel8);
        jLabel8.setBounds(20, 200, 160, 15);

        jLabel9.setText("Retencion Fte. Ventas:");
        panCta.add(jLabel9);
        jLabel9.setBounds(20, 230, 160, 15);

        jLabel10.setText("Retencion IVA. Ventas:");
        panCta.add(jLabel10);
        jLabel10.setBounds(20, 290, 160, 15);

        jLabel11.setText("Retencion IVA. Compras:");
        panCta.add(jLabel11);
        jLabel11.setBounds(20, 260, 160, 15);

        txtCodCtaIvaCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaIvaComActionPerformed(evt);
            }
        });

        panCta.add(txtCodCtaIvaCom);
        txtCodCtaIvaCom.setBounds(200, 30, 60, 21);

        txtAltCtaIvaCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAltCtaIvaComActionPerformed(evt);
            }
        });

        panCta.add(txtAltCtaIvaCom);
        txtAltCtaIvaCom.setBounds(260, 30, 100, 21);

        txtNomCtaIvaCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCtaIvaComActionPerformed(evt);
            }
        });

        panCta.add(txtNomCtaIvaCom);
        txtNomCtaIvaCom.setBounds(360, 30, 300, 21);

        butCtaIvaCom.setText("...");
        butCtaIvaCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaIvaComActionPerformed(evt);
            }
        });

        panCta.add(butCtaIvaCom);
        butCtaIvaCom.setBounds(660, 30, 20, 20);

        txtCodCtaIvaVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaIvaVenActionPerformed(evt);
            }
        });

        panCta.add(txtCodCtaIvaVen);
        txtCodCtaIvaVen.setBounds(200, 55, 60, 21);

        txtAltCtaIvaVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAltCtaIvaVenActionPerformed(evt);
            }
        });

        panCta.add(txtAltCtaIvaVen);
        txtAltCtaIvaVen.setBounds(260, 55, 100, 21);

        txtNomCtaIvaVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCtaIvaVenActionPerformed(evt);
            }
        });

        panCta.add(txtNomCtaIvaVen);
        txtNomCtaIvaVen.setBounds(360, 55, 300, 21);

        butCtaIvaVen.setText("...");
        butCtaIvaVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaIvaVenActionPerformed(evt);
            }
        });

        panCta.add(butCtaIvaVen);
        butCtaIvaVen.setBounds(660, 55, 20, 20);

        txtCodCtaRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaResActionPerformed(evt);
            }
        });

        panCta.add(txtCodCtaRes);
        txtCodCtaRes.setBounds(200, 140, 60, 21);

        txtCodCtaGas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaGasActionPerformed(evt);
            }
        });

        panCta.add(txtCodCtaGas);
        txtCodCtaGas.setBounds(200, 170, 60, 21);

        txtAltCtaRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAltCtaResActionPerformed(evt);
            }
        });

        panCta.add(txtAltCtaRes);
        txtAltCtaRes.setBounds(260, 140, 100, 21);

        txtAltCtaGas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAltCtaGasActionPerformed(evt);
            }
        });

        panCta.add(txtAltCtaGas);
        txtAltCtaGas.setBounds(260, 170, 100, 21);

        txtNomCtaRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCtaResActionPerformed(evt);
            }
        });

        panCta.add(txtNomCtaRes);
        txtNomCtaRes.setBounds(360, 140, 300, 21);

        txtNomCtaGas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCtaGasActionPerformed(evt);
            }
        });

        panCta.add(txtNomCtaGas);
        txtNomCtaGas.setBounds(360, 170, 300, 21);

        butCtaRes.setText("...");
        butCtaRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaResActionPerformed(evt);
            }
        });

        panCta.add(butCtaRes);
        butCtaRes.setBounds(660, 140, 20, 20);

        butCtaGas.setText("...");
        butCtaGas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaGasActionPerformed(evt);
            }
        });

        panCta.add(butCtaGas);
        butCtaGas.setBounds(660, 170, 20, 20);

        txtCodCtaRetFteCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaRetFteComActionPerformed(evt);
            }
        });

        panCta.add(txtCodCtaRetFteCom);
        txtCodCtaRetFteCom.setBounds(200, 200, 60, 21);

        txtCodCtaRetFteVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaRetFteVenActionPerformed(evt);
            }
        });

        panCta.add(txtCodCtaRetFteVen);
        txtCodCtaRetFteVen.setBounds(200, 230, 60, 21);

        txtAltCtaRetFteCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAltCtaRetFteComActionPerformed(evt);
            }
        });

        panCta.add(txtAltCtaRetFteCom);
        txtAltCtaRetFteCom.setBounds(260, 200, 100, 21);

        txtAltCtaRetFteVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAltCtaRetFteVenActionPerformed(evt);
            }
        });

        panCta.add(txtAltCtaRetFteVen);
        txtAltCtaRetFteVen.setBounds(260, 230, 100, 21);

        txtNomCtaRetFteCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCtaRetFteComActionPerformed(evt);
            }
        });

        panCta.add(txtNomCtaRetFteCom);
        txtNomCtaRetFteCom.setBounds(360, 200, 300, 21);

        txtNomCtaRetFteVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCtaRetFteVenActionPerformed(evt);
            }
        });

        panCta.add(txtNomCtaRetFteVen);
        txtNomCtaRetFteVen.setBounds(360, 230, 300, 21);

        butCtaRetFteCom.setText("...");
        butCtaRetFteCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaRetFteComActionPerformed(evt);
            }
        });

        panCta.add(butCtaRetFteCom);
        butCtaRetFteCom.setBounds(660, 200, 20, 20);

        butCtaRetFteVen.setText("...");
        butCtaRetFteVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaRetFteVenActionPerformed(evt);
            }
        });

        panCta.add(butCtaRetFteVen);
        butCtaRetFteVen.setBounds(660, 230, 20, 20);

        txtCodCtaRetIvaCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaRetIvaComActionPerformed(evt);
            }
        });

        panCta.add(txtCodCtaRetIvaCom);
        txtCodCtaRetIvaCom.setBounds(200, 260, 60, 21);

        txtCodCtaRetIvaVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaRetIvaVenActionPerformed(evt);
            }
        });

        panCta.add(txtCodCtaRetIvaVen);
        txtCodCtaRetIvaVen.setBounds(200, 290, 60, 21);

        txtAltCtaRetIvaCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAltCtaRetIvaComActionPerformed(evt);
            }
        });

        panCta.add(txtAltCtaRetIvaCom);
        txtAltCtaRetIvaCom.setBounds(260, 260, 100, 21);

        txtAltCtaRetIvaVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAltCtaRetIvaVenActionPerformed(evt);
            }
        });

        panCta.add(txtAltCtaRetIvaVen);
        txtAltCtaRetIvaVen.setBounds(260, 290, 100, 21);

        txtNomCtaRetIvaCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCtaRetIvaComActionPerformed(evt);
            }
        });

        panCta.add(txtNomCtaRetIvaCom);
        txtNomCtaRetIvaCom.setBounds(360, 260, 300, 21);

        txtNomCtaRetIvaVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCtaRetIvaVenActionPerformed(evt);
            }
        });

        panCta.add(txtNomCtaRetIvaVen);
        txtNomCtaRetIvaVen.setBounds(360, 290, 300, 21);

        butCtaRetIvaCom.setText("...");
        butCtaRetIvaCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaRetIvaComActionPerformed(evt);
            }
        });

        panCta.add(butCtaRetIvaCom);
        butCtaRetIvaCom.setBounds(660, 260, 20, 20);

        butCtaRetIvaVen.setText("...");
        butCtaRetIvaVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaRetIvaVenActionPerformed(evt);
            }
        });

        panCta.add(butCtaRetIvaVen);
        butCtaRetIvaVen.setBounds(660, 290, 20, 20);

        txtCodCtaBco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaBcoActionPerformed(evt);
            }
        });

        panCta.add(txtCodCtaBco);
        txtCodCtaBco.setBounds(200, 80, 60, 21);

        txtCodCtaCaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaCajActionPerformed(evt);
            }
        });

        panCta.add(txtCodCtaCaj);
        txtCodCtaCaj.setBounds(200, 110, 60, 21);

        txtAltCtaBco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAltCtaBcoActionPerformed(evt);
            }
        });

        panCta.add(txtAltCtaBco);
        txtAltCtaBco.setBounds(260, 80, 100, 21);

        txtAltCtaCaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAltCtaCajActionPerformed(evt);
            }
        });

        panCta.add(txtAltCtaCaj);
        txtAltCtaCaj.setBounds(260, 110, 100, 21);

        txtNomCtaBco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCtaBcoActionPerformed(evt);
            }
        });

        panCta.add(txtNomCtaBco);
        txtNomCtaBco.setBounds(360, 80, 300, 21);

        txtNomCtaCaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCtaCajActionPerformed(evt);
            }
        });

        panCta.add(txtNomCtaCaj);
        txtNomCtaCaj.setBounds(360, 110, 300, 21);

        butCtaBco.setText("...");
        butCtaBco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaBcoActionPerformed(evt);
            }
        });

        panCta.add(butCtaBco);
        butCtaBco.setBounds(660, 80, 20, 20);

        butCtaCaj.setText("...");
        butCtaCaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaCajActionPerformed(evt);
            }
        });

        panCta.add(butCtaCaj);
        butCtaCaj.setBounds(660, 110, 20, 20);

        jLabel12.setText("Cod. Sistema");
        panCta.add(jLabel12);
        jLabel12.setBounds(180, 10, 80, 15);

        jLabel13.setText("Cod. Plan Ctas.");
        panCta.add(jLabel13);
        jLabel13.setBounds(270, 10, 100, 15);

        jLabel14.setText("Descripcion");
        panCta.add(jLabel14);
        jLabel14.setBounds(490, 10, 80, 15);

        tabGen.addTab("Contabilidad", panCta);

        panCon.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCon, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void butCtaRetIvaVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaRetIvaVenActionPerformed
        // TODO add your handling code here:
        FndCta("",40);
    }//GEN-LAST:event_butCtaRetIvaVenActionPerformed

    private void txtNomCtaRetIvaVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCtaRetIvaVenActionPerformed
        // TODO add your handling code here:
        FndCta(txtNomCtaRetIvaVen.getText(), 39);
        
    }//GEN-LAST:event_txtNomCtaRetIvaVenActionPerformed

    private void txtAltCtaRetIvaVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAltCtaRetIvaVenActionPerformed
        // TODO add your handling code here:
        FndCta(txtAltCtaRetIvaVen.getText(), 38);
    }//GEN-LAST:event_txtAltCtaRetIvaVenActionPerformed

    private void txtCodCtaRetIvaVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaRetIvaVenActionPerformed
        // TODO add your handling code here:
        FndCta(txtCodCtaRetIvaVen.getText(), 37);
    }//GEN-LAST:event_txtCodCtaRetIvaVenActionPerformed

    private void butCtaRetIvaComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaRetIvaComActionPerformed
        // TODO add your handling code here:
        FndCta("", 36);
    }//GEN-LAST:event_butCtaRetIvaComActionPerformed

    private void txtAltCtaRetIvaComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAltCtaRetIvaComActionPerformed
        // TODO add your handling code here:
        FndCta(txtAltCtaRetIvaCom.getText(),34);
    }//GEN-LAST:event_txtAltCtaRetIvaComActionPerformed

    private void txtCodCtaRetIvaComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaRetIvaComActionPerformed
        // TODO add your handling code here:
        FndCta(txtCodCtaRetIvaCom.getText(), 33);
    }//GEN-LAST:event_txtCodCtaRetIvaComActionPerformed

    private void butCtaRetFteVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaRetFteVenActionPerformed
        // TODO add your handling code here:
        FndCta("",32);
    }//GEN-LAST:event_butCtaRetFteVenActionPerformed

    private void txtNomCtaRetFteVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCtaRetFteVenActionPerformed
        // TODO add your handling code here:
        FndCta(txtNomCtaRetFteVen.getText(), 31);
    }//GEN-LAST:event_txtNomCtaRetFteVenActionPerformed

    private void txtAltCtaRetFteVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAltCtaRetFteVenActionPerformed
        // TODO add your handling code here:
        FndCta(txtAltCtaRetFteVen.getText(), 30);
    }//GEN-LAST:event_txtAltCtaRetFteVenActionPerformed

    private void txtCodCtaRetFteVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaRetFteVenActionPerformed
        // TODO add your handling code here:
        FndCta(txtCodCtaRetFteVen.getText(), 29);
    }//GEN-LAST:event_txtCodCtaRetFteVenActionPerformed

    private void butCtaRetFteComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaRetFteComActionPerformed
        // TODO add your handling code here:
        FndCta("",28);
    }//GEN-LAST:event_butCtaRetFteComActionPerformed

    private void txtNomCtaRetFteComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCtaRetFteComActionPerformed
        // TODO add your handling code here:
        FndCta(txtNomCtaRetFteCom.getText(),27);
    }//GEN-LAST:event_txtNomCtaRetFteComActionPerformed

    private void txtCodCtaRetFteComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaRetFteComActionPerformed
        // TODO add your handling code here:
        FndCta(txtCodCtaRetFteCom.getText(), 25);
    }//GEN-LAST:event_txtCodCtaRetFteComActionPerformed

    private void butCtaGasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaGasActionPerformed
        // TODO add your handling code here:
        FndCta("", 24);
    }//GEN-LAST:event_butCtaGasActionPerformed

    private void txtAltCtaGasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAltCtaGasActionPerformed
        // TODO add your handling code here:
        FndCta(txtAltCtaGas.getText(), 22);
    }//GEN-LAST:event_txtAltCtaGasActionPerformed

    private void txtCodCtaGasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaGasActionPerformed
        // TODO add your handling code here:
        FndCta(txtCodCtaGas.getText(), 21);
    }//GEN-LAST:event_txtCodCtaGasActionPerformed

    private void butCtaResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaResActionPerformed
        // TODO add your handling code here:
        FndCta("",20);        
    }//GEN-LAST:event_butCtaResActionPerformed

    private void txtNomCtaResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCtaResActionPerformed
        // TODO add your handling code here:
        FndCta(txtNomCtaRes.getText(),19);
    }//GEN-LAST:event_txtNomCtaResActionPerformed

    private void txtAltCtaResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAltCtaResActionPerformed
        // TODO add your handling code here:
        FndCta(txtAltCtaRes.getText(), 18);
    }//GEN-LAST:event_txtAltCtaResActionPerformed

    private void txtCodCtaResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaResActionPerformed
        // TODO add your handling code here:
        FndCta(txtCodCtaRes.getText(), 17);
    }//GEN-LAST:event_txtCodCtaResActionPerformed

    private void butCtaCajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaCajActionPerformed
        // TODO add your handling code here:
        FndCta("",16);
    }//GEN-LAST:event_butCtaCajActionPerformed

    private void txtNomCtaCajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCtaCajActionPerformed
        // TODO add your handling code here:
        FndCta(txtNomCtaCaj.getText(), 15);
    }//GEN-LAST:event_txtNomCtaCajActionPerformed

    private void txtAltCtaCajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAltCtaCajActionPerformed
        // TODO add your handling code here:
        FndCta(txtAltCtaCaj.getText(), 14);
    }//GEN-LAST:event_txtAltCtaCajActionPerformed

    private void txtCodCtaCajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaCajActionPerformed
        // TODO add your handling code here:
        FndCta(txtCodCtaCaj.getText(), 13);
    }//GEN-LAST:event_txtCodCtaCajActionPerformed

    private void txtAltCtaBcoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAltCtaBcoActionPerformed
        // TODO add your handling code here:
        FndCta(txtAltCtaBco.getText(),10);
    }//GEN-LAST:event_txtAltCtaBcoActionPerformed

    private void txtCodCtaBcoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaBcoActionPerformed
        // TODO add your handling code here:
        FndCta(txtCodCtaBco.getText(), 9);
    }//GEN-LAST:event_txtCodCtaBcoActionPerformed

    private void butCtaBcoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaBcoActionPerformed
        // TODO add your handling code here:
        FndCta("",12);
    }//GEN-LAST:event_butCtaBcoActionPerformed

    private void butCtaIvaVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaIvaVenActionPerformed
        // TODO add your handling code here:
        FndCta("",8);
    }//GEN-LAST:event_butCtaIvaVenActionPerformed

    private void txtNomCtaIvaVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCtaIvaVenActionPerformed
        // TODO add your handling code here:
        FndCta(txtNomCtaIvaVen.getText(), 7);
    }//GEN-LAST:event_txtNomCtaIvaVenActionPerformed

    private void txtAltCtaIvaVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAltCtaIvaVenActionPerformed
        // TODO add your handling code here:
        FndCta(txtAltCtaIvaVen.getText(), 6);
    }//GEN-LAST:event_txtAltCtaIvaVenActionPerformed

    private void txtCodCtaIvaVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaIvaVenActionPerformed
        // TODO add your handling code here:
        FndCta(txtCodCtaIvaVen.getText(), 5);
    }//GEN-LAST:event_txtCodCtaIvaVenActionPerformed

    private void butCtaIvaComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaIvaComActionPerformed
        // TODO add your handling code here:
        FndCta("", 4);
    }//GEN-LAST:event_butCtaIvaComActionPerformed

    private void txtAltCtaIvaComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAltCtaIvaComActionPerformed
        // TODO add your handling code here:
        FndCta(txtAltCtaIvaCom.getText(), 2);
    }//GEN-LAST:event_txtAltCtaIvaComActionPerformed

    private void txtCodCtaIvaComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaIvaComActionPerformed
        // TODO add your handling code here:
        FndCta(txtCodCtaIvaCom.getText(), 1);
    }//GEN-LAST:event_txtCodCtaIvaComActionPerformed

    private void txtNomCtaRetIvaComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCtaRetIvaComActionPerformed
        // TODO add your handling code here:
        FndCta(txtNomCtaRetIvaCom.getText(), 35);
    }//GEN-LAST:event_txtNomCtaRetIvaComActionPerformed

    private void txtNomCtaGasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCtaGasActionPerformed
        // TODO add your handling code here:
        FndCta(txtNomCtaGas.getText(), 23);
    }//GEN-LAST:event_txtNomCtaGasActionPerformed

    private void txtNomCtaBcoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCtaBcoActionPerformed
        // TODO add your handling code here:
        FndCta(txtNomCtaBco.getText(), 11);
    }//GEN-LAST:event_txtNomCtaBcoActionPerformed

    private void txtNomCtaIvaComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCtaIvaComActionPerformed
        // TODO add your handling code here:
        FndCta(txtNomCtaIvaCom.getText(), 3);
    }//GEN-LAST:event_txtNomCtaIvaComActionPerformed

    private void txtAltCtaRetFteComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAltCtaRetFteComActionPerformed
        // TODO add your handling code here:
        FndCta(txtAltCtaRetFteCom.getText(), 26);
    }//GEN-LAST:event_txtAltCtaRetFteComActionPerformed

        
    private void FndCta(String strBusqueda,int TipoBusqueda)
    {
        Librerias.ZafConsulta.ZafConsulta  objFnd = 
        new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(this),
           "Codigo,Alias,Descripcion,Debe/Haber","cta.co_cta , tx_codcta,tx_deslar , tx_natcta",
             "select  cta.co_cta , tx_codcta,tx_deslar , tx_natcta from tbm_placta as cta " +
                " where   cta.co_emp = " + txtCodEmp.getText() + " and tx_tipcta='D' and st_reg='A'",strBusqueda, 
         objZafParSis.getStringConexion(), 
         objZafParSis.getUsuarioBaseDatos(), 
         objZafParSis.getClaveBaseDatos()
         );        

        objFnd.setTitle("Listado Cuentas");
        
        if  (TipoBusqueda==1 || TipoBusqueda==5 || TipoBusqueda==9 || TipoBusqueda==13 || TipoBusqueda==17 || TipoBusqueda==21 || TipoBusqueda==25 || TipoBusqueda==29 || TipoBusqueda==33 || TipoBusqueda==37 )
        {
            //case 1-5-9,13,17,21,25,29,33,37:                                        
                 objFnd.setSelectedCamBus(0);
                if(!objFnd.buscar("cta.co_cta = " + strBusqueda))
                    objFnd.show();
        }else if  (TipoBusqueda==2 || TipoBusqueda==6 || TipoBusqueda==10 || TipoBusqueda==14 || TipoBusqueda==18 || TipoBusqueda==22 || TipoBusqueda==26 || TipoBusqueda==30 || TipoBusqueda==34 || TipoBusqueda==38 )
        {
            objFnd.setSelectedCamBus(1);
                if(!objFnd.buscar("tx_codcta = '" + strBusqueda+"'"))
                    objFnd.show();                 
        }else if  (TipoBusqueda==3 || TipoBusqueda==7 || TipoBusqueda==11 || TipoBusqueda==15 || TipoBusqueda==19 || TipoBusqueda==23 || TipoBusqueda==27 || TipoBusqueda==31 || TipoBusqueda==35 || TipoBusqueda==39 )
        {
            objFnd.setSelectedCamBus(2);
                if(!objFnd.buscar("tx_deslar = '" + strBusqueda+"'"))
                    objFnd.show();                 
        }else{
             objFnd.show();              
        }
        if(!objFnd.GetCamSel(1).equals("")){
            if (TipoBusqueda==1 || TipoBusqueda==2 || TipoBusqueda==3 || TipoBusqueda==4 ){
                txtCodCtaIvaCom.setText(objFnd.GetCamSel(1).toString());
                txtAltCtaIvaCom.setText(objFnd.GetCamSel(2).toString());
                txtNomCtaIvaCom.setText(objFnd.GetCamSel(3).toString());
            }
            if (TipoBusqueda==5 || TipoBusqueda==6 || TipoBusqueda==7 || TipoBusqueda==8 ){
                txtCodCtaIvaVen.setText(objFnd.GetCamSel(1).toString());
                txtAltCtaIvaVen.setText(objFnd.GetCamSel(2).toString());
                txtNomCtaIvaVen.setText(objFnd.GetCamSel(3).toString());
            }
            if (TipoBusqueda==9 || TipoBusqueda==10 || TipoBusqueda==11 || TipoBusqueda==12 ){
                txtCodCtaBco.setText(objFnd.GetCamSel(1).toString());
                txtAltCtaBco.setText(objFnd.GetCamSel(2).toString());
                txtNomCtaBco.setText(objFnd.GetCamSel(3).toString());
            }
            if (TipoBusqueda==13 || TipoBusqueda==14 || TipoBusqueda==15 || TipoBusqueda==16 ){
                txtCodCtaCaj.setText(objFnd.GetCamSel(1).toString());
                txtAltCtaCaj.setText(objFnd.GetCamSel(2).toString());
                txtNomCtaCaj.setText(objFnd.GetCamSel(3).toString());
            }
            if (TipoBusqueda==17 || TipoBusqueda==18 || TipoBusqueda==19 || TipoBusqueda==20 ){
                txtCodCtaRes.setText(objFnd.GetCamSel(1).toString());
                txtAltCtaRes.setText(objFnd.GetCamSel(2).toString());
                txtNomCtaRes.setText(objFnd.GetCamSel(3).toString());
            }
            if (TipoBusqueda==21 || TipoBusqueda==22 || TipoBusqueda==23 || TipoBusqueda==24 ){
                txtCodCtaGas.setText(objFnd.GetCamSel(1).toString());
                txtAltCtaGas.setText(objFnd.GetCamSel(2).toString());
                txtNomCtaGas.setText(objFnd.GetCamSel(3).toString());
            }
            if (TipoBusqueda==25 || TipoBusqueda==26 || TipoBusqueda==27 || TipoBusqueda==28 ){
                txtCodCtaRetFteCom.setText(objFnd.GetCamSel(1).toString());
                txtAltCtaRetFteCom.setText(objFnd.GetCamSel(2).toString());
                txtNomCtaRetFteCom.setText(objFnd.GetCamSel(3).toString());
            }
            if (TipoBusqueda==29 || TipoBusqueda==30 || TipoBusqueda==31 || TipoBusqueda==32 ){
                txtCodCtaRetFteVen.setText(objFnd.GetCamSel(1).toString());
                txtAltCtaRetFteVen.setText(objFnd.GetCamSel(2).toString());
                txtNomCtaRetFteVen.setText(objFnd.GetCamSel(3).toString());
            }
            if (TipoBusqueda==33 || TipoBusqueda==34 || TipoBusqueda==35 || TipoBusqueda==36 ){
                txtCodCtaRetIvaCom.setText(objFnd.GetCamSel(1).toString());
                txtAltCtaRetIvaCom.setText(objFnd.GetCamSel(2).toString());
                txtNomCtaRetIvaCom.setText(objFnd.GetCamSel(3).toString());
            }
            if (TipoBusqueda==37 || TipoBusqueda==38 || TipoBusqueda==39 || TipoBusqueda==40 ){
                txtCodCtaRetIvaVen.setText(objFnd.GetCamSel(1).toString());
                txtAltCtaRetIvaVen.setText(objFnd.GetCamSel(2).toString());
                txtNomCtaRetIvaVen.setText(objFnd.GetCamSel(3).toString());
            }
        }
    }

    
    private void cboEstRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstRegActionPerformed
        blnCam = true;
    }//GEN-LAST:event_cboEstRegActionPerformed

    
    private void txtTelEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelEmpFocusLost
        if (chrFlgLst == 'N') {
            if (txtTelEmp.getText().length()>60 && chrFlgLst == 'N'){
                strTit="Mensaje del sistema Zafiro";
                strMsg="El campo <<Telèfono>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                txtTelEmp.setText("");
                txtTelEmp.requestFocus();
                chrFlgMsj = 'S';
                chrFlgLst = 'S';
            }
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
        }
    }//GEN-LAST:event_txtTelEmpFocusLost

    private void txtDirEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDirEmpFocusLost
        if (chrFlgLst == 'N') {
            if (txtDirEmp.getText().length()>120 && chrFlgLst == 'N'){
                strTit="Mensaje del sistema Zafiro";
                strMsg="El campo <<Direcciòn>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                txtDirEmp.setText("");
                txtDirEmp.requestFocus();
                chrFlgMsj = 'S';
                chrFlgLst = 'S';
            }
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
        }
    }//GEN-LAST:event_txtDirEmpFocusLost

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        if (chrFlgLst == 'N') {
            if (txtNomEmp.getText().length()>80 && chrFlgLst == 'N'){
                strTit="Mensaje del sistema Zafiro";
                strMsg="El campo <<Nombre>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                txtNomEmp.setText("");
                txtNomEmp.requestFocus();
                chrFlgMsj = 'S';
                chrFlgLst = 'S';
            }
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
        }
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void txtEmaEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmaEmpFocusLost
        if (chrFlgLst == 'N') {
            if (txtEmaEmp.getText().length()>30 && chrFlgLst == 'N'){
                strTit="Mensaje del sistema Zafiro";
                strMsg="El campo <<Email>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                txtEmaEmp.setText("");
                txtEmaEmp.requestFocus();
                chrFlgMsj = 'S';
                chrFlgLst = 'S';
            }
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
        }
    }//GEN-LAST:event_txtEmaEmpFocusLost

    private void txtFaxEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFaxEmpFocusLost
        if (chrFlgLst == 'N') {
            if (txtFaxEmp.getText().length()>30 && chrFlgLst == 'N'){
                strTit="Mensaje del sistema Zafiro";
                strMsg="El campo <<Fax>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                txtFaxEmp.setText("");
                txtFaxEmp.requestFocus();
                chrFlgMsj = 'S';
                chrFlgLst = 'S';
            }
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
        }
    }//GEN-LAST:event_txtFaxEmpFocusLost

    private void txtWebEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtWebEmpFocusLost
        if (chrFlgLst == 'N') {
            if (txtWebEmp.getText().length()>30 && chrFlgLst == 'N'){
                strTit="Mensaje del sistema Zafiro";
                strMsg="El campo <<Web>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                txtWebEmp.setText("");
                txtWebEmp.requestFocus();
                chrFlgMsj = 'S';
                chrFlgLst = 'S';
            }
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
        }
    }//GEN-LAST:event_txtWebEmpFocusLost

    private void txaObs1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txaObs1FocusLost
        if (chrFlgLst == 'N') {
            if (txaObs1.getText().length()>200 && chrFlgLst == 'N'){
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

    private void txaObs2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txaObs2FocusLost
        if (chrFlgLst == 'N') {
            if (txaObs2.getText().length()>200 && chrFlgLst == 'N'){
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

    private void txtPorIvaVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPorIvaVenActionPerformed
        
    }//GEN-LAST:event_txtPorIvaVenActionPerformed

    private void txtPorIvaVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPorIvaVenFocusLost
        if (chrFlgLst == 'N') {
            if (txtPorIvaVen.getText().length()>2 && chrFlgLst == 'N'){
                tabGen.setSelectedIndex(0);
                strTit="Mensaje del sistema Zafiro";
                strMsg="El campo <<Poncentaje de iva en ventas>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                txtPorIvaVen.setText("");
                txtPorIvaVen.requestFocus();
                chrFlgMsj = 'S';
                chrFlgLst = 'S';
            }
            if (txtPorIvaVen.getText().length()>0 && blnFlgNum == false && blnFlgCon == false && chrFlgMsj == 'N') {
                String numero = txtPorIvaVen.getText();
                objUti.isNumero(numero);
                if (objUti.isNumero(numero) == false) {
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="El campo <<Poncentaje de iva en ventas>> solo acepta valores numericos.\nDigite caracteres validos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                    txtPorIvaVen.setText("");
                    txtPorIvaVen.requestFocus();
                    chrFlgMsj = 'S';
                }
            }
            blnFlgNum = false;
            blnFlgCon = false;
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
        }
    }//GEN-LAST:event_txtPorIvaVenFocusLost

    private void txtPorIvaComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPorIvaComActionPerformed
        
    }//GEN-LAST:event_txtPorIvaComActionPerformed

    private void txtPorIvaComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPorIvaComFocusLost
        if (chrFlgLst == 'N') {
            if (txtPorIvaCom.getText().length()>2 && chrFlgLst == 'N'){
                tabGen.setSelectedIndex(0);
                strTit="Mensaje del sistema Zafiro";
                strMsg="El campo <<Poncentaje de iva en compras>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                txtPorIvaCom.setText("");
                txtPorIvaCom.requestFocus();
                chrFlgMsj = 'S';
                chrFlgLst = 'S';
            }
 
            if (txtPorIvaCom.getText().length()>0 && blnFlgNum == false && blnFlgCon == false && chrFlgMsj == 'N') {
                String numero = txtPorIvaCom.getText();
                objUti.isNumero(numero);
                if (objUti.isNumero(numero) == false) {
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="El campo <<Poncentaje de iva en compras>> solo acepta valores numericos.\nDigite caracteres validos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                    txtPorIvaCom.setText("");
                    txtPorIvaCom.requestFocus();
                    chrFlgMsj = 'S';
                }
            }
            blnFlgNum = false;
            blnFlgCon = false;
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
        }
    }//GEN-LAST:event_txtPorIvaComFocusLost

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm
/** Cerrar la aplicación. */
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
            if (cargarCabReg())
            {
                refrescaDatos();                
            }
            else
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
                String strSQL = "  SELECT * FROM tbm_emp"+ 
                     " Where co_emp=" + rstCab.getString("co_emp") ;
//                System.out.println(strSQL);
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    limpiarPant();
                    txtCodEmp.setText(""+ rst.getInt("co_emp"));
                    txtCodEmp.setText(((rst.getString("co_emp")==null)?"":rst.getString("co_emp")));
                    txtRuc.setText(((rst.getString("tx_ruc")==null)?"":rst.getString("tx_ruc")));
                    txtNomEmp.setText(((rst.getString("tx_nom")==null)?"":rst.getString("tx_nom")));
                    txtDirEmp.setText(((rst.getString("tx_dir")==null)?"":rst.getString("tx_dir")));
                    txtTelEmp.setText(((rst.getString("tx_tel")==null)?"":rst.getString("tx_tel")));
                    txtFaxEmp.setText(((rst.getString("tx_fax")==null)?"":rst.getString("tx_fax")));
                    txtWebEmp.setText(((rst.getString("tx_dirweb")==null)?"":rst.getString("tx_dirweb")));
                    txtEmaEmp.setText(((rst.getString("tx_corele")==null)?"":rst.getString("tx_corele")));
                    txtPorIvaCom.setText(((rst.getString("nd_ivacom")==null)?"":rst.getString("nd_ivacom")));
                    txtPorIvaVen.setText(((rst.getString("nd_ivaven")==null)?"":rst.getString("nd_ivaven"))); 
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));
                    objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), 
                                        objZafParSis.getClaveBaseDatos(), "SELECT co_tipper,tx_deslar FROM tbm_tipper Where co_emp = " + txtCodEmp.getText() + " order by co_tipper", cboTipPer, vecTipPer,(rstCab.getString("co_tipper")==null?"0":rstCab.getString("co_tipper")));      

                    if(rst.getString("st_reg").equals("I"))
                        cboEstReg.setSelectedIndex(1);
                    else
                        cboEstReg.setSelectedIndex(0);
                                        
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
//                    stmCab.close();
//                    conCab.close();
                    rstCab=null;
//                    stmCab=null;
//                    conCab=null;
                }
                txtRuc.requestFocus();
                txtCodEmp.setEditable(false); 
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
                        strSQL = " Update tbm_emp set st_reg = 'I' " +
                                 " where co_emp =" + txtCodEmp.getText();
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
            return _consultar(FilSql());
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
              if (mensaje("Para modificar primero debe reactivar la empresa \n ¿Desea reactivarlo?")==JOptionPane.YES_OPTION){
                    if (Reactivar()){
                        MensajeInf("Empresa reactivada satisfactoriamente");
                    }                        
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
            limpiarPant();
        }
          
        public void clickConsultar() {
        }
        
        public void clickEliminar() {
          }
        
        
        public void clickImprimir() {
        }
                
        public void clickModificar() {
            txtCodEmp.setEditable(false);
             chrFlgEst = 'M';
        }
        
        public void clickVisPreliminar() {
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
        
        public boolean beforeAceptar() {
            return true;
        }
        
        public boolean beforeAnular() {
            return true;
        }
        
        public boolean beforeCancelar() {
            return true;
        }
        
        public boolean beforeConsultar() {
            return true;
        }
        
        public boolean beforeEliminar() {
            return true;
        }
        
        public boolean beforeImprimir() {
            return true;
        }
        
        public boolean beforeInsertar() {
            return true;
        }
        
        public boolean beforeModificar() {
            return true;
        }
        
        public boolean beforeVistaPreliminar() {
            return true;
        }
                
        public boolean imprimir() {
            return true;
        }
        
        public boolean vistaPreliminar() {
            return true;
        }
        
  }   
 
     private boolean Reactivar(){
        try{
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            try{
                if (con!=null)
                {
                    con.setAutoCommit(false);
                    java.sql.PreparedStatement pstReg;
                    String strSQL = "Update tbm_emp set st_reg='A' " +
                                    "where co_emp = " + rstCab.getString("co_emp") ;
                    pstReg = con.prepareStatement(strSQL);
                    pstReg.executeUpdate();
                    con.commit();
                    con.close();
                    objTooBar.setEstadoRegistro("Activo");
                    cboEstReg.setSelectedIndex(0);
                    
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
       private boolean _consultar(String strFiltro){
       strFlt = strFiltro;  
       try{
                    conCab = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos()); 		
                    if (conCab!=null){
                        stmCab=conCab.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY );
                         /*
                         * Agregando el Sql de Consulta para el maestro de empresas
                         */
                        strSql = "";
                        strSql = strSql + "SELECT * FROM tbm_emp ";
                        strSql = strSql + " WHERE co_emp = co_emp ";
                        strSql = strSql + strFlt + " Order by co_emp";
                        rstCab=stmCab.executeQuery(strSql);
                        if(rstCab.next()){
                            rstCab.last();
                            objTooBar.setMenSis(rstCab.getRow() + " Registros encontrados");
                            cargarReg();
                        }else{
                           objTooBar.setMenSis("Se encontraron 0 registros...");
                            strTit="Mensaje del sistema Zafiro";
                            strMsg="No se ha encontrado ningùn registro que cumpla el criterio de bùsqueda especìficado.";
                            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);  
                            rstCab = null;
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
    private javax.swing.JButton butCtaBco;
    private javax.swing.JButton butCtaCaj;
    private javax.swing.JButton butCtaGas;
    private javax.swing.JButton butCtaIvaCom;
    private javax.swing.JButton butCtaIvaVen;
    private javax.swing.JButton butCtaRes;
    private javax.swing.JButton butCtaRetFteCom;
    private javax.swing.JButton butCtaRetFteVen;
    private javax.swing.JButton butCtaRetIvaCom;
    private javax.swing.JButton butCtaRetIvaVen;
    private javax.swing.JComboBox cboEstReg;
    private javax.swing.JComboBox cboTipPer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblCod;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblEma;
    private javax.swing.JLabel lblEstReg;
    private javax.swing.JLabel lblFax;
    private javax.swing.JLabel lblNom;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPorIvaCom;
    private javax.swing.JLabel lblPorIvaVen;
    private javax.swing.JLabel lblRuc;
    private javax.swing.JLabel lblTel;
    private javax.swing.JLabel lblWeb;
    private javax.swing.JLabel lblnMaeEmp;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panCta;
    private javax.swing.JPanel panGen;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtAltCtaBco;
    private javax.swing.JTextField txtAltCtaCaj;
    private javax.swing.JTextField txtAltCtaGas;
    private javax.swing.JTextField txtAltCtaIvaCom;
    private javax.swing.JTextField txtAltCtaIvaVen;
    private javax.swing.JTextField txtAltCtaRes;
    private javax.swing.JTextField txtAltCtaRetFteCom;
    private javax.swing.JTextField txtAltCtaRetFteVen;
    private javax.swing.JTextField txtAltCtaRetIvaCom;
    private javax.swing.JTextField txtAltCtaRetIvaVen;
    private javax.swing.JTextField txtCodCtaBco;
    private javax.swing.JTextField txtCodCtaCaj;
    private javax.swing.JTextField txtCodCtaGas;
    private javax.swing.JTextField txtCodCtaIvaCom;
    private javax.swing.JTextField txtCodCtaIvaVen;
    private javax.swing.JTextField txtCodCtaRes;
    private javax.swing.JTextField txtCodCtaRetFteCom;
    private javax.swing.JTextField txtCodCtaRetFteVen;
    private javax.swing.JTextField txtCodCtaRetIvaCom;
    private javax.swing.JTextField txtCodCtaRetIvaVen;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtDirEmp;
    private javax.swing.JTextField txtEmaEmp;
    private javax.swing.JTextField txtFaxEmp;
    private javax.swing.JTextField txtNomCtaBco;
    private javax.swing.JTextField txtNomCtaCaj;
    private javax.swing.JTextField txtNomCtaGas;
    private javax.swing.JTextField txtNomCtaIvaCom;
    private javax.swing.JTextField txtNomCtaIvaVen;
    private javax.swing.JTextField txtNomCtaRes;
    private javax.swing.JTextField txtNomCtaRetFteCom;
    private javax.swing.JTextField txtNomCtaRetFteVen;
    private javax.swing.JTextField txtNomCtaRetIvaCom;
    private javax.swing.JTextField txtNomCtaRetIvaVen;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtPorIvaCom;
    private javax.swing.JTextField txtPorIvaVen;
    private javax.swing.JTextField txtRuc;
    private javax.swing.JTextField txtTelEmp;
    private javax.swing.JTextField txtWebEmp;
    // End of variables declaration//GEN-END:variables
}

/*
 * ZafMae03.java
 * Esta clase almacena registros de las Bodegas
 * Created on 16 de septiembre de 2004, 11:17
 */

package Maestros.ZafMae03;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafParSis.ZafParSis;

/**
 * @author  kcerezo
 * mejorado por jsalazar 31/oct/2005
 * ultima actualizacion 26/Dic/2005
 */

public class ZafMae03 extends javax.swing.JInternalFrame {
        Connection conCab;       //Variable para conexiÃ³n a la Base de Datos
	Statement stmCab;        //Variable para ejecuciÃ³n de sentencias SQL
	ResultSet rstCab;        //Variable para manipular registro de la tabla en ejecuciÃ³n
	String strSql;        //Variable de tipo cadena en la cual se almacenarÃ¡ el Query 
        String strMsg;        //Variable de tipo cadena para enviar los mensajes por pantalla
        String strAux;        //Variable auxiliar de tipo string la cual servira para guardar aquellas cadenas en las cuales me esten enviando algun caracter invalido
        String strFil;        //Filtro para almacenar el criterio de bÃºsqueda actual
        boolean blnCmb = false; //Detecta si se realizaron cambios en el documento dentro de las caja de texto
        boolean blnCln = false; //Si los TextField estan vacios
        final String strTit = "Zafiro"; //Constante del titulo del mensaje
        JOptionPane oppMsg;   //Objeto de tipo OptionPane para presentar Mensajes
        ToolBar objTooBar;       //Objeto de tipo ToolBar para poder manipular la clase ZafToolBar
        ZafUtil objUti;       //Objeto del tipo de la clase ZafUtil, el cual me va a permitir manipular los diferentes metodos de esta clase
        ZafParSis objZafParSis;  //Objeto que me permitira obtener los parametros del sistema, como podria ser la ruta de la base de datos, etc.  
        LisTxt objLisCmb;     // Instancia de clase que detecta cambios
        Librerias.ZafInventario.ZafInventario objInventario;
        Librerias.ZafUtil.ZafCtaCtb objCtaCtb;
        
        private final String VERSION = " v0.1";
        
    /** Creates new form ZafMae03 */
    public ZafMae03(ZafParSis obj) {
        initComponents();
        addListenerCambios();
      try{
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();    //Asignando el contenido del objZafParSist que me tre los parametros de l sistema a objZafParSis mi objeto
        objTooBar = new ToolBar(this); //Inicializando el objeto de la libreria ZafToolBar servira para manipular la Barra de Herramientas
        objUti = new ZafUtil();     //Inicializando el objeto de la libreria ZafUtil 
        oppMsg = new JOptionPane(); //Inicializando el objeto para los mensajes
        objInventario = new Librerias.ZafInventario.ZafInventario(this,objZafParSis);
        objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb(objZafParSis);
        txaObs1Bod.setLineWrap(true);
        txaObs1Bod.setWrapStyleWord(true);
        spnObs1.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        spnObs1.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        txaObs2Bod.setLineWrap(true);
        txaObs2Bod.setWrapStyleWord(true);
        spnObs2.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        spnObs2.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.getContentPane().add(objTooBar, "South");
        txtNomBod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodBod.setBackground(objZafParSis.getColorCamposSistema());
        this.setTitle(objZafParSis.getNombreMenu()+VERSION);
        txtCodCtaExi.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomCtaExi.setBackground(objZafParSis.getColorCamposObligatorios());        
        /**Alineando las cajas de texto que contendran valores numericos*/
        txtCodBod.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTelBod.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtFaxBod.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        this.setBounds(10,10, 700, 450);  
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }        
        
    }
    
    private void MensajeInf(String strMensaje){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Zafiro";
            obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
        strTit="Zafiro";
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
                blnCmb=false;
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
                blnCmb=false;
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
                String strSQL = "  SELECT * FROM tbm_bod"+ 
                     " Where co_emp=" + rstCab.getString("co_emp") +
                      " AND co_bod="    + rstCab.getString("co_bod") ;
//                System.out.println(strSQL);
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
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
                    limpiarData();
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
                blnCmb=false;
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
    
    public class ToolBar extends ZafToolBar {
    
        public ToolBar (javax.swing.JInternalFrame ifrTmp) {
            super (ifrTmp, objZafParSis);
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
            blnCmb=false;
            return true;
        }

        public void clickInsertar()
        {
            try
            {
                if (blnCmb)
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
                limpiarData();
                habilitarData();
                cboEstBod.setSelectedIndex(0);
                txtCodBod.setEditable(false);
                txtNomBod.requestFocus();
                butCod.setEnabled(false);
                blnCmb=false;
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

        public void clickAnterior(){
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnCmb)
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
                    if (blnCmb)
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
                    if (blnCmb)
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

        public void clickSiguiente(){
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnCmb)
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
                        strSQL = " Update tbm_bod set st_reg = 'I' " +
                                 " where co_emp =" + objZafParSis.getCodigoEmpresa() +" and co_bod =" + txtCodBod.getText();
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
        
        public boolean consultar(){
            consultarReg();
            return true;
        }

        public boolean eliminar(){
            try
            {
                String strAux=objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado"))
                {
                    MensajeInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }
                if (!eliminarReg())
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
                    limpiarData();
                }
                blnCmb=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }

        public boolean insertar(){
            if (!validaCampos())
                return false;
            if (!insertarReg())
                return false;
            limpiarData();
            blnCmb=false;
            return true;
        }

        public boolean modificar(){
            String strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                MensajeInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")){
                deshabilitarData();
                if (mensaje("Para modificar primero debe reactivar la bodega \n ¿Desea reactivarlo?")==JOptionPane.YES_OPTION){
                    habilitarData();
                    return Reactivar();
                }                
            }
            if (!validaCampos())
                return false;
            if (!modificarReg())
                return false;
            blnCmb=false;
            return true;
        }
        
        public boolean cancelar(){
            boolean blnRes=true;
            try
            {
                if (blnCmb)
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
            limpiarData();
            blnCmb=false;
            return blnRes;
        }
           
        public void clickAceptar() {
        }
        public void clickAnular() {
        }
        
        public void clickCancelar(){
            
        }
        
        public void clickConsultar() {
            txtCodBod.setEditable(true);
            txtCodBod.setEnabled(true);
            habilitarData();
        }
        
        public void clickEliminar() {
            deshabilitarData();
        }
        
        public void clickImprimir() {
        }
        
        public void clickModificar() {
            habilitarData();
            txtCodBod.setEnabled(false);
            txtNomBod.requestFocus();
        }
       
        public void clickVisPreliminar() {
        }
        
        public boolean consultarReg() {
            return consultarData(filtrarSql());
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
    
    private boolean validaCampos(){
        if (txtNomBod.getText().equals("")){
            MensajeInf("Ingrese descripcion de la bodega");
            return false;
        }
        if (txtCodCtaExi.getText().equals("")){
            MensajeInf("Ingrese numero de cuenta contable de la bodega");
            return false;
        }
        return true;
    }
    
    private int mensaje(){
        strMsg = "Â¿Desea guardar los cambios efectuados a este registro?\n";
        strMsg+= "Si no guarda los cambios perderÃ¡ toda la informaciÃ³n que no haya guardado.";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);                
    }
        
    
    public void refrescaDatos(){
        try{ 
            
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            try{
                if (con!=null){
                    java.sql.Statement stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                    String strSQL = "  SELECT * FROM tbm_bod  " +
                                    " Where co_emp=" + rstCab.getString("co_emp") + " AND co_bod="+ rstCab.getString("co_bod") ;
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);
                    if(rst.next()){
                        limpiarData();
                        txtCodBod.setText(rst.getString("co_bod"));
                        txtNomBod.setText(((rst.getString("tx_nom")==null)?"":rst.getString("tx_nom")));
                        txtDirBod.setText(((rst.getString("tx_dir")==null)?"":rst.getString("tx_dir")));
                        txtTelBod.setText(((rst.getString("tx_tel")==null)?"":rst.getString("tx_tel")));
                        txtFaxBod.setText(((rst.getString("tx_fax")==null)?"":rst.getString("tx_fax")));
                        txtCodCtaExi.setText(((rst.getString("co_ctaexi")==null)?"":rst.getString("co_ctaexi")));
                        txtNomCtaExi.setText(objCtaCtb.getNomCta(rst.getInt("co_ctaexi")));
                        txaObs1Bod.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                        txaObs2Bod.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));
                        if(rst.getString("st_reg").equals("I"))
                            cboEstBod.setSelectedIndex(1);
                        else
                            cboEstBod.setSelectedIndex(0);
                    }                            
                    blnCmb = false;
                    rst.close();
                    stm.close();
                    con.close();
                }
                
                
            }catch(SQLException Evt){
                  con.close();                  
                  objUti.mostrarMsgErr_F1(this, Evt);
            }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
        }catch(Exception Evt){
              objUti.mostrarMsgErr_F1(this, Evt);
        }                       
    }
    
    
    /**FunciÃ³n que me permÃ­te guardar los datos de la Bodega*/
    public boolean insertarReg(){
            char chrEst;
            //Asigno a la variable el valor segun lo que haya seleccionado el usuario en el combo
            if (cboEstBod.getSelectedIndex()==0)
                chrEst= 'A';
            else
                chrEst= 'I';
            
		try
		{
                    java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                    try{
			if (con!=null)
                        {
                            MensajeInf("Este proceso puede tomar algun tiempo...");
                                con.setAutoCommit(false);                                
                                java.sql.Statement stm = con.createStatement();
                                strSql = "";
                                strSql = strSql + "SELECT MAX(co_bod)+1 as co_bod from tbm_bod where co_emp ="+objZafParSis.getCodigoEmpresa();
                                java.sql.ResultSet rst = stm.executeQuery(strSql);                              
                                int intNumReg;
                                if (rst.next())
                                    intNumReg=rst.getInt("co_bod");
                                else
                                    intNumReg=1;
                                java.sql.PreparedStatement pstReg;
                                String strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
                                strSql = "";
                                strSql = strSql + "INSERT INTO tbm_bod (co_emp, co_bod, tx_nom, tx_dir, tx_tel, tx_fax,co_ctaexi";
                                strSql = strSql + ", tx_obs1, tx_obs2, st_reg, co_usring, fe_ing)";
                                strSql = strSql + " VALUES (" + objZafParSis.getCodigoEmpresa() + "," + intNumReg + "";
                                strSql = strSql + ", " + ((strAux = txtNomBod.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                                strSql = strSql + ", " + ((strAux = txtDirBod.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                                strSql = strSql + ", " + ((strAux = txtTelBod.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                                strSql = strSql + ", " + ((strAux = txtFaxBod.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                                strSql = strSql + ", " + ((strAux = txtCodCtaExi.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                                strSql = strSql + ", " + ((strAux = txaObs1Bod.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                                strSql = strSql + ", " + ((strAux = txaObs2Bod.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                                strSql = strSql + ", '" + chrEst + "', "+ objZafParSis.getCodigoUsuario() +"";
                                strSql = strSql + ", '" + strFecSis + "')";
                                pstReg = con.prepareStatement(strSql);
                                pstReg.executeUpdate();
                                strSql = " Insert into tbr_bodloc (co_emp,co_loc,co_bod) values ("+ objZafParSis.getCodigoEmpresa() + ","+objZafParSis.getCodigoLocal()+"," +intNumReg + ")";
                                pstReg = con.prepareStatement(strSql);
                                pstReg.executeUpdate();
                                
                                //Creacion de Inventario Bodega

                                strSql = " select co_itm from tbm_inv where co_emp =" + objZafParSis.getCodigoEmpresa() + " and st_reg='A' order by co_itm ";
                                rst = stm.executeQuery(strSql);                    
                                while(rst.next()){
                                    if (!objInventario.creaItemBodega(con,objZafParSis.getCodigoEmpresa(),rst.getInt("co_itm"),intNumReg)){
                                        con.rollback();
                                        con.close();
                                        return false;
                                    }
                                }                    
                                
                                con.commit();
                                stm.close();
                                rst.close();
				con.close();
                                
                                limpiarData();
                                cboEstBod.setSelectedIndex(0);
                                txtCodBod.setEditable(false);
                                butCod.setEnabled(false);
                                txtNomBod.requestFocus();
                        }
                        
                    }catch(SQLException evt){                            
                            con.rollback();
                            con.close();
                            objUti.mostrarMsgErr_F1(this, evt);
                            return false;
                    }                    
                }catch(Exception evt){
			objUti.mostrarMsgErr_F1(this, evt);
                        return false;
		}
                blnCmb = false;
                return true;
	}
    
    
    /**FunciÃ³n que me permÃ­te modificar los datos de la Bodega seleccionada*/
    public boolean modificarReg(){
            char chrEst;
            if (cboEstBod.getSelectedIndex()==0)
                chrEst= 'A';
            else
                chrEst= 'I';
                        
	    try{
                    java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                    try{
                        if (con!=null){
                            java.sql.PreparedStatement pstReg;
                            String strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
                            strSql = "";
                            strSql = strSql + "UPDATE tbm_bod SET";
                            strSql = strSql + " tx_nom = " + ((strAux = txtNomBod.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                            strSql = strSql + ", tx_dir = " + ((strAux = txtDirBod.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                            strSql = strSql + ", tx_tel = " + ((strAux = txtTelBod.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                            strSql = strSql + ", tx_fax = " + ((strAux = txtFaxBod.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                            strSql = strSql + ", co_ctaexi=" + ((strAux = txtCodCtaExi.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                            strSql = strSql + ", tx_obs1 = " + ((strAux = txaObs1Bod.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                            strSql = strSql + ", tx_obs2 = " + ((strAux = txaObs2Bod.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                            strSql = strSql + ", st_reg = '" + chrEst + "'";
                            strSql = strSql + ", co_usrmod = " + objZafParSis.getCodigoUsuario() ;
                            strSql = strSql + ", fe_ultmod = '" + strFecSis + "'" + " WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_bod = " + txtCodBod.getText().replaceAll("'", "''") + "";   
                            pstReg = con.prepareStatement(strSql);
                            pstReg.executeUpdate();

                            con.commit();
                            con.close();
                        }
                    }catch(SQLException evt){
                        con.rollback();
                        con.close();
                        objUti.mostrarMsgErr_F1(this, evt);
                        return false;
                    }
                }catch(Exception evt){
                    objUti.mostrarMsgErr_F1(this, evt);
                    return false;
		}
                blnCmb = false;
                return true;
	}
    
    
    /**FunciÃ³n que me permitirÃ¡ eliminar un registro*/
    public boolean eliminarReg()
   {
        try
        {
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            try{
                if (con!=null){
                    con.setAutoCommit(false);
                    java.sql.Statement stm = con.createStatement();
                    java.sql.PreparedStatement pstReg;
                    strSql = "";
                    strSql = " Select count(co_doc) as num_mov from tbm_detmovinv where co_emp =" + objZafParSis.getCodigoEmpresa() +" and co_bod = "+txtCodBod.getText();
                    java.sql.ResultSet rst = stm.executeQuery(strSql);
                    if (rst.next()){
                        if (rst.getInt("num_mov")==0){
                            strSql = "Delete from tbm_invbod where co_emp =" + objZafParSis.getCodigoEmpresa() +" and co_bod = "+txtCodBod.getText();
                            pstReg = con.prepareStatement(strSql);
                            pstReg.executeUpdate();
                            strSql = "Delete from tbr_bodloc where co_emp =" + objZafParSis.getCodigoEmpresa() +" and co_bod = "+txtCodBod.getText();
                            pstReg = con.prepareStatement(strSql);
                            pstReg.executeUpdate();
                        }else{
                            MensajeInf("Existen movimientos asociados a la bodega");
                            return false;
                        }
                    }else{
                        return false;
                    }
                    strSql = "DELETE FROM tbm_bod WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_bod = " + txtCodBod.getText().replaceAll("'", "''") + "";
                    pstReg = con.prepareStatement(strSql);
                    pstReg.executeUpdate();

                    con.commit();                    
                    rst.close();
                    stm.close();
                    con.close();

                    limpiarData();
                    deshabilitarData();
                }
            }catch(SQLException evt){
                con.rollback();
                con.close();
                objUti.mostrarMsgErr_F1(this, evt);
                return false;
            }
        }catch(Exception evt){
            objUti.mostrarMsgErr_F1(this, evt);
            return false;
        }
        blnCmb = false;
        return true;
    }
    
   private boolean Reactivar()
    {
        try{
            java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            try{
                if (con!=null){
                    java.sql.PreparedStatement pstReg;
                    String strSQL="";
                    strSQL = " Update tbm_bod set st_reg= 'A' where co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_bod = " + txtCodBod.getText();
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
       
    private String filtrarSql() {
        String sqlFlt = "";
        //Agregando filtro por Codigo de la Bodega
        if(!txtCodBod.getText().equals(""))
            sqlFlt = sqlFlt + " and co_bod = " + txtCodBod.getText().replaceAll("'", "''") + "";

        //Agregando filtro por Nombre de la Bodega
        if(!txtNomBod.getText().equals(""))
            sqlFlt = sqlFlt + " and LOWER(tx_nom) LIKE '" + txtNomBod.getText().replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";

        //Agregando filtro por Direccion de la Bodega
        if(!txtDirBod.getText().equals(""))
            sqlFlt = sqlFlt + " and LOWER(tx_dir) LIKE '" + txtDirBod.getText().replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
        
        //Agregando filtro por Telefono de Bodega
        if(!txtTelBod.getText().equals(""))
            sqlFlt = sqlFlt + " and tx_tel LIKE '" + txtTelBod.getText().replaceAll("'", "''") + "%'";
        
        //Agregando filtro por Fax de Bodega
        if(!txtFaxBod.getText().equals(""))
            sqlFlt = sqlFlt + " and tx_fax LIKE '" + txtFaxBod.getText().replaceAll("'", "''") + "%'";
        
        //Agregando filtro por ObservaciÃ³n 1 de Bodega
        if(!txaObs1Bod.getText().equals(""))
            sqlFlt = sqlFlt + " and tx_obs1 LIKE '" + txaObs1Bod.getText().replaceAll("'", "''").toLowerCase() + "' ";
        
        //Agregando filtro por ObservaciÃ³n 2 de Bodega
        if(!txaObs2Bod.getText().equals(""))
            sqlFlt = sqlFlt + " and tx_obs2 LIKE '" + txaObs2Bod.getText().replaceAll("'", "''").toLowerCase() + "' ";
        
      return sqlFlt ;
    }
    
    
    private boolean consultarData(String strFlt){
       strFil = strFlt;
       try{
                conCab = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conCab!=null){

                    stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                    
                    //Agregando el Sql de Consulta para el Maestro
                    strSql = "";
                    strSql = strSql + "SELECT * FROM tbm_bod WHERE co_emp =" + objZafParSis.getCodigoEmpresa() + ""; 
                    strSql = strSql + strFil + " ORDER BY co_bod";
//                    System.out.println(strSql);
                    rstCab = stmCab.executeQuery(strSql);
                    
                    if(rstCab.next()){
                        rstCab.last();
                        objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros.");
                        cargarReg();
                    }
                    else{
                        objTooBar.setMenSis("Se encontraron 0 registros...");
                        strMsg="No se ha encontrado ningÃºn registro que cumpla el criterio de bÃºsqueda especÃ­ficado.";
                        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);  
                        rstCab = null;
                        stmCab.close();
                        conCab.close();
                        stmCab=null;
                        conCab=null;
                        limpiarData();
                        return false;
                    }
                blnCmb = false;
                }
       }
       catch(SQLException Evt)
       {
          objUti.mostrarMsgErr_F1(this, Evt);
          return false;
        }
        catch(Exception Evt)
        {
          objUti.mostrarMsgErr_F1(this, Evt);
          return false;
        }                       
   return true;     
}
    
    
    /* Agrega el listener para detectar que hubo algun cambio en la caja de texto*/
    private void addListenerCambios(){
        objLisCmb = new LisTxt();
            txtCodBod.setText("");
            txtCodBod.getDocument().addDocumentListener(objLisCmb);
            txtNomBod.getDocument().addDocumentListener(objLisCmb);
            txtDirBod.getDocument().addDocumentListener(objLisCmb);
            txtTelBod.getDocument().addDocumentListener(objLisCmb);
            txtFaxBod.getDocument().addDocumentListener(objLisCmb);
            txaObs1Bod.getDocument().addDocumentListener(objLisCmb);
            txaObs2Bod.getDocument().addDocumentListener(objLisCmb);
    }   
    

     /* Clase de tipo DocumentListener para detectar los cambios en los textcomponent*/
    class LisTxt implements javax.swing.event.DocumentListener {
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            blnCmb = true;
        }
        
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            blnCmb = true;
        }
        
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            blnCmb = true;
        }
    }
    
    
    /**procedimiento que limpia todas las cajas de texto que existen en el frame*/
    public void limpiarData()
    { 
       txtCodBod.setText("");
       txtNomBod.setText("");
       txtDirBod.setText("");
       txtTelBod.setText("");
       txtFaxBod.setText("");
       txaObs1Bod.setText("");
       txaObs2Bod.setText(""); 
       txtCodCtaExi.setText("");
       txtNomCtaExi.setText("");
    }
    
    
    /**Procedimiento que me permÃ­te habilitar las cajas de texto para que estas puedan 
    ser manipuladas por el usuario*/
    public void habilitarData()
    {
       txtNomBod.setEditable(true);
       txtDirBod.setEnabled(true);
       txtTelBod.setEnabled(true);
       txtFaxBod.setEnabled(true);
       txaObs1Bod.setEditable(true);
       txaObs2Bod.setEditable(true);
       cboEstBod.setEnabled(true); 
       butCod.setEnabled(true);
    }
    
    
    /**Procedimiento que deshabilta las cajas de texto para que estas no puedan ser 
    manipuladas por el usuario*/
    public void deshabilitarData()
    {
       txtCodBod.setEditable(false);
       txtNomBod.setEditable(false);
       txtDirBod.setEnabled(false);
       txtTelBod.setEnabled(false);
       txtFaxBod.setEnabled(false);
       txaObs1Bod.setEditable(false);
       txaObs2Bod.setEditable(false);
       cboEstBod.setEnabled(false);
       butCod.setEnabled(false);
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        pangen = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        lblCodBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        lblNom = new javax.swing.JLabel();
        txtDirBod = new javax.swing.JTextField();
        lblDir = new javax.swing.JLabel();
        lblTel = new javax.swing.JLabel();
        txtTelBod = new javax.swing.JTextField();
        lblFax = new javax.swing.JLabel();
        txtFaxBod = new javax.swing.JTextField();
        lblEst = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1Bod = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2Bod = new javax.swing.JTextArea();
        lblObs2 = new javax.swing.JLabel();
        lblObs1 = new javax.swing.JLabel();
        cboEstBod = new javax.swing.JComboBox();
        butCod = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtCodCtaExi = new javax.swing.JTextField();
        txtNomCtaExi = new javax.swing.JTextField();
        butCtaExi = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Zafiro >> Bodegas");
        setFrameIcon(null);
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

        pangen.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 14));
        lblTit.setForeground(new java.awt.Color(10, 36, 106));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Maestro de Bodegas");
        lblTit.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        pangen.add(lblTit, java.awt.BorderLayout.NORTH);

        tabGen.setFont(new java.awt.Font("Dialog", 1, 11));
        panDat.setLayout(null);

        lblCodBod.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodBod.setText("C\u00f3digo de Bodega");
        panDat.add(lblCodBod);
        lblCodBod.setBounds(12, 20, 120, 15);

        txtCodBod.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtCodBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodActionPerformed(evt);
            }
        });
        txtCodBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodFocusLost(evt);
            }
        });

        panDat.add(txtCodBod);
        txtCodBod.setBounds(128, 20, 44, 20);

        txtNomBod.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtNomBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodActionPerformed(evt);
            }
        });
        txtNomBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodFocusLost(evt);
            }
        });

        panDat.add(txtNomBod);
        txtNomBod.setBounds(128, 44, 280, 20);

        lblNom.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNom.setText("Nombre");
        panDat.add(lblNom);
        lblNom.setBounds(12, 44, 56, 15);

        txtDirBod.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtDirBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDirBodActionPerformed(evt);
            }
        });
        txtDirBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDirBodFocusLost(evt);
            }
        });

        panDat.add(txtDirBod);
        txtDirBod.setBounds(128, 68, 408, 20);

        lblDir.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDir.setText("Direcci\u00f3n");
        panDat.add(lblDir);
        lblDir.setBounds(12, 68, 64, 15);

        lblTel.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTel.setText("Tel\u00e9fonos");
        panDat.add(lblTel);
        lblTel.setBounds(12, 92, 68, 15);

        txtTelBod.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtTelBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelBodActionPerformed(evt);
            }
        });
        txtTelBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTelBodFocusLost(evt);
            }
        });

        panDat.add(txtTelBod);
        txtTelBod.setBounds(128, 92, 184, 20);

        lblFax.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFax.setText("Fax");
        panDat.add(lblFax);
        lblFax.setBounds(320, 92, 18, 15);

        txtFaxBod.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtFaxBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFaxBodActionPerformed(evt);
            }
        });
        txtFaxBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFaxBodFocusLost(evt);
            }
        });

        panDat.add(txtFaxBod);
        txtFaxBod.setBounds(352, 92, 184, 20);

        lblEst.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblEst.setText("Estado");
        panDat.add(lblEst);
        lblEst.setBounds(12, 116, 56, 15);

        txaObs1Bod.setFont(new java.awt.Font("SansSerif", 0, 11));
        txaObs1Bod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txaObs1BodFocusLost(evt);
            }
        });

        spnObs1.setViewportView(txaObs1Bod);

        panDat.add(spnObs1);
        spnObs1.setBounds(130, 170, 408, 52);

        txaObs2Bod.setFont(new java.awt.Font("SansSerif", 0, 11));
        txaObs2Bod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txaObs2BodFocusLost(evt);
            }
        });

        spnObs2.setViewportView(txaObs2Bod);

        panDat.add(spnObs2);
        spnObs2.setBounds(130, 230, 408, 52);

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs2.setText("Observaci\u00f3n 2");
        panDat.add(lblObs2);
        lblObs2.setBounds(10, 230, 108, 15);

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs1.setText("Observaci\u00f3n 1");
        panDat.add(lblObs1);
        lblObs1.setBounds(10, 170, 92, 15);

        cboEstBod.setFont(new java.awt.Font("SansSerif", 0, 11));
        cboEstBod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Inactivo" }));
        cboEstBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstBodActionPerformed(evt);
            }
        });

        panDat.add(cboEstBod);
        cboEstBod.setBounds(128, 116, 180, 20);

        butCod.setFont(new java.awt.Font("SansSerif", 1, 12));
        butCod.setText("...");
        butCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCodActionPerformed(evt);
            }
        });

        panDat.add(butCod);
        butCod.setBounds(172, 20, 24, 20);

        jLabel1.setText("Cuenta Existencia");
        panDat.add(jLabel1);
        jLabel1.setBounds(10, 140, 110, 15);

        txtCodCtaExi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaExiActionPerformed(evt);
            }
        });

        panDat.add(txtCodCtaExi);
        txtCodCtaExi.setBounds(130, 140, 60, 21);

        txtNomCtaExi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCtaExiActionPerformed(evt);
            }
        });

        panDat.add(txtNomCtaExi);
        txtNomCtaExi.setBounds(190, 140, 260, 21);

        butCtaExi.setText("...");
        butCtaExi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaExiActionPerformed(evt);
            }
        });

        panDat.add(butCtaExi);
        butCtaExi.setBounds(455, 140, 20, 20);

        tabGen.addTab("Datos de Bodega", panDat);

        pangen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(pangen, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void butCtaExiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaExiActionPerformed
        // TODO add your handling code here:
        FndCta("", 0);
    }//GEN-LAST:event_butCtaExiActionPerformed

    private void txtNomCtaExiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCtaExiActionPerformed
        // TODO add your handling code here:
        FndCta(txtNomCtaExi.getText(),2);
    }//GEN-LAST:event_txtNomCtaExiActionPerformed

    private void txtCodCtaExiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaExiActionPerformed
        // TODO add your handling code here:
        FndCta(txtCodCtaExi.getText(),1);
    }//GEN-LAST:event_txtCodCtaExiActionPerformed
    private void FndCta(String strBusqueda,int TipoBusqueda)
    {
        Librerias.ZafConsulta.ZafConsulta  objFnd = 
        new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(this),
           "Codigo,Alias,Descripcion,Debe/Haber","cta.co_cta , tx_codcta,tx_deslar , tx_natcta",
             "select  cta.co_cta , tx_codcta,tx_deslar , tx_natcta from tbm_placta as cta " +
                " where   cta.co_emp = " + objZafParSis.getCodigoEmpresa() + " and tx_tipcta='D' and st_reg='A'" ,strBusqueda, 
         objZafParSis.getStringConexion(), 
         objZafParSis.getUsuarioBaseDatos(), 
         objZafParSis.getClaveBaseDatos()
         );        

        objFnd.setTitle("Listado Cuentas");
        
        switch (TipoBusqueda)
        {
            case 1:                                        
                 objFnd.setSelectedCamBus(0);
                if(!objFnd.buscar("cta.co_cta = " + txtCodCtaExi.getText()))
                    objFnd.show();
                 break;
            case 2:
                 objFnd.setSelectedCamBus(2);
                if(!objFnd.buscar("tx_deslar = '" + txtNomCtaExi.getText()+"'"))
                    objFnd.show();                 
                 break;
            default:
                objFnd.show();
                break;             
         }
        if(!objFnd.GetCamSel(1).equals("")){
            txtCodCtaExi.setText(objFnd.GetCamSel(1).toString());
            txtNomCtaExi.setText(objFnd.GetCamSel(3).toString());
        }
    }

    private void txaObs2BodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txaObs2BodFocusLost
            if (!txaObs2Bod.getText().equals("")) {
                if (txaObs2Bod.getText().length()>200){ 
                    strMsg="El campo <<ObservaciÃ³n 2>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                    txaObs2Bod.setText("");
                    txaObs2Bod.requestFocus();
                } 
            }
    }//GEN-LAST:event_txaObs2BodFocusLost

    private void txaObs1BodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txaObs1BodFocusLost
        if (!txaObs1Bod.getText().equals("")) {
            if (txaObs1Bod.getText().length()>200 ){ 
                strMsg="El campo <<ObservaciÃ³n 1>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                txaObs1Bod.setText("");
                txaObs1Bod.requestFocus();
            } 
        }
    }//GEN-LAST:event_txaObs1BodFocusLost

    private void txtDirBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDirBodFocusLost
        if (!txtDirBod.getText().equals("")) {
            if (txtDirBod.getText().length()>120){ 
                strMsg="El campo <<DirecciÃ³n de Bodega>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                txtDirBod.setText("");
                txtDirBod.requestFocus();

            } 
        }
    }//GEN-LAST:event_txtDirBodFocusLost

    private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
        if (!txtNomBod.getText().equals("")) {
            if (txtNomBod.getText().length()>80){ 
                strMsg="El campo <<Nombre de Bodega>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                txtNomBod.setText("");
                txtNomBod.requestFocus();
            } 
        }
    }//GEN-LAST:event_txtNomBodFocusLost

    private void txtFaxBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFaxBodFocusLost
        if (!txtFaxBod.getText().equals("")) {
            if (txtFaxBod.getText().length()>30){ 
                strMsg="El campo <<Fax de Bodega>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                txtFaxBod.setText("");
                txtFaxBod.requestFocus();
            } 
        }
    }//GEN-LAST:event_txtFaxBodFocusLost

    private void txtTelBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelBodFocusLost
        if (!txtTelBod.getText().equals("")) {
            if (txtTelBod.getText().length()>60 ){ 
                strMsg="El campo <<TÃ©lefono de Bodega>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                txtTelBod.setText("");
                txtTelBod.requestFocus();
            } 
        }
    }//GEN-LAST:event_txtTelBodFocusLost

    private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost

    }//GEN-LAST:event_txtCodBodFocusLost

    private void cboEstBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstBodActionPerformed
        blnCmb = true;
        txaObs1Bod.requestFocus();
    }//GEN-LAST:event_cboEstBodActionPerformed

    private void butCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCodActionPerformed
        Librerias.ZafConsulta.ZafConsulta objFndCod = 
        new Librerias.ZafConsulta.ZafConsulta(new javax.swing.JFrame(),
        "Codigo,Nombre,Direccion,Telefono,Fax,Estado","co_bod,tx_nom,tx_dir,tx_tel,tx_fax,st_reg",
        "SELECT co_bod,tx_nom,tx_dir,tx_tel,tx_fax,st_reg FROM tbm_bod WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "", 
        txtCodBod.getText(), 
        objZafParSis.getStringConexion(), 
        objZafParSis.getUsuarioBaseDatos(), 
        objZafParSis.getClaveBaseDatos()
        );
     
        objFndCod.setTitle("Listado de Bodegas");
        objFndCod.show();

        if(!objFndCod.GetCamSel(1).equals("")){
            txtCodBod.setText(objFndCod.GetCamSel(1));
            txtNomBod.setText(objFndCod.GetCamSel(2));
        }
    }//GEN-LAST:event_butCodActionPerformed

    private void txtFaxBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFaxBodActionPerformed
        if (!objUti.isNumero(txtFaxBod.getText())){
            MensajeInf("Ingrese solo valores numericos");
            txtFaxBod.requestFocus();
        }
    }//GEN-LAST:event_txtFaxBodActionPerformed

    private void txtTelBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelBodActionPerformed
    }//GEN-LAST:event_txtTelBodActionPerformed

    private void txtDirBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDirBodActionPerformed
       
    }//GEN-LAST:event_txtDirBodActionPerformed

    private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
   
    }//GEN-LAST:event_txtNomBodActionPerformed

    private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
        Librerias.ZafConsulta.ZafConsulta objFndBod = 
        new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this),
        "Codigo,Nombre,Direccion,Telefono,Fax,Estado","co_bod,tx_nom,tx_dir,tx_tel,tx_fax,st_reg",
        "SELECT co_bod,tx_nom,tx_dir,tx_tel,tx_fax,st_reg FROM tbm_bod WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "", 
        txtCodBod.getText(), 
        objZafParSis.getStringConexion(), 
        objZafParSis.getUsuarioBaseDatos(), 
        objZafParSis.getClaveBaseDatos()
        );
     
        objFndBod.setTitle("Listado de Bodegas");
        objFndBod.show();
        objFndBod.setSelectedCamBus(0);
        if(txtCodBod.getText().equals("")){
            objFndBod.show();
        }else{
             if(!objFndBod.buscar("co_bod = " + txtCodBod.getText().replaceAll("'", "''") + "")) 
                objFndBod.show(); 
         }
        if(!objFndBod.GetCamSel(1).equals("")){
            txtCodBod.setText(objFndBod.GetCamSel(1));
            txtNomBod.setText(objFndBod.GetCamSel(2));
        }

    }//GEN-LAST:event_txtCodBodActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        strMsg="¿Esta seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm
        
    private void exitForm() 
    {
        dispose();
    } 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCod;
    private javax.swing.JButton butCtaExi;
    private javax.swing.JComboBox cboEstBod;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblCodBod;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblEst;
    private javax.swing.JLabel lblFax;
    private javax.swing.JLabel lblNom;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTel;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel pangen;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTextArea txaObs1Bod;
    private javax.swing.JTextArea txaObs2Bod;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodCtaExi;
    private javax.swing.JTextField txtDirBod;
    private javax.swing.JTextField txtFaxBod;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNomCtaExi;
    private javax.swing.JTextField txtTelBod;
    // End of variables declaration//GEN-END:variables

}


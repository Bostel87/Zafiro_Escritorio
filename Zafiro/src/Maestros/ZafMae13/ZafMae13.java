/*
 * ZafMae13.java
 *
 * Created on 10 de noviembre de 2004, 9:58
 */

/**Esta clase sirve para almacenar los datos de grupos de clientes / proveedores*/

package Maestros.ZafMae13;

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
 *
 * @author  kcerezo
 * MODIFICADO POR DARIO CARDENAS
 * ULTIMA ACTUALIZACION 06/MAR/2009 
 */

public class ZafMae13 extends javax.swing.JInternalFrame 
{
    Connection conCab;       //Variable para conexiÃ³n a la Base de Datos
    Statement stmCab;        //Variable para ejecuciÃ³n de sentencias SQL
    ResultSet rstCab;        //Variable para manipular registro de la tabla en ejecuciÃ³n
    String strSql, strSQL;        //Variable de tipo cadena en la cual se almacenarÃ¡ el Query
    String strMsg;        //Variable de tipo cadena para enviar los mensajes por pantalla
    String strAux;        //Variable de tipo Auxiliar la cual almacenara la cadena del query de forma uxiliar, esta cambiera segun la funcion que este cumpliendo
    String strFil;        //Filtro para almacenar el criterio de bÃºsqueda actual
    int intConEst;        //Contador de vector de estado
    int intCodGrp;        //Variable de tipo entero la cual almacenara el codigo del registro al que estamos haciendo referencia
    int intOpp;           //Entero que almacena lo que elige el usuario en el OptionPane
    char chrEst;          //Variable de tipo char para almacenar el estado del registro  
    char chrMod;          //Variable de tipo char para almacenar el Modo en el que estamos trabajando
    char chrFlgEst;       //Bandera para saber en que estado se encuentra la forma 
    char chrFlgCon = 'N'; //Bandera para saber si se ha perdido el foco en un objeto
    char chrFlgMsj = 'N'; //Bandera para saber si ya se presento el mensaje
    char chrFlgLst = 'N'; //Bandera para saber si ya se presento el mensaje al pÃ¨rder el foco el objeto
    boolean blnFlgNum = false; //Variable booleana que servira como bandera para saber si la caja contiene caracteres invÃ¡lidos
    boolean blnFlgCon = false; //Variable booleana que servira como bandera para saber si la caja contiene algo 
    boolean blnCmb = false; //Detecta si se hicieron cambios en el documento
    boolean blnCln = false; //Si los TextField estan vacios
    final String strTit = "Sistema Zafiro"; //Constante del titulo del mensaje
    JOptionPane oppMsg;   //Objeto de tipo OptionPane para presentar Mensajes
    ToolBar objTooBar;       //Objeto de tipo ToolBar para poder manipular la clase ZafToolBar
    ZafUtil objUti;      //Objeto del tipo de la clase ZafUtil, el cual me va a permitir manipular los diferentes metodos de esta clase
    ZafParSis objZafParSis;  //Objeto que me permitira obtener los parametros del sistema, como podria ser la ruta de la base de datos, etc.  
    LisTxt objLisCmb;     // Instancia de clase que detecta cambios
    java.sql.Connection con;
    java.sql.Statement stm;
    private java.util.Date datFecAux;                       //Auxiliar: Para almacenar fechas.          
            
    /** Creates new form ZafMae13 */
    public ZafMae13(ZafParSis obj) 
    {
        initComponents();
        addListenerCambios();
        try
        {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone(); 
            objTooBar = new ToolBar(this);
            objUti = new ZafUtil();
            oppMsg = new JOptionPane(); //Inicializando el objeto para los mensajes
            txaObs1.setLineWrap(true);
            txaObs1.setWrapStyleWord(true);
            spnObs1.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            spnObs1.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            txaObs2.setLineWrap(true);
            txaObs2.setWrapStyleWord(true);
            spnObs2.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            spnObs2.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            deshabilitarDatGrp();
            txtNom.setBackground(objZafParSis.getColorCamposObligatorios());
            this.getContentPane().add(objTooBar, "South");
            txtCodGrp.setEditable(false);
            txtCodGrp.setBackground(objZafParSis.getColorCamposSistema());
            txtCodGrp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            this.setTitle(objZafParSis.getNombreMenu());
            blnCmb = false;
            this.setBounds(10,10, 700, 450);  
      }
      catch (CloneNotSupportedException e)
      {
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
            if (!cargarCabReg())
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
                String strSQL = "  SELECT * FROM tbm_grpCli"+ 
                     " Where co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_grp=" + rstCab.getString("co_grp") ;
//                System.out.println(strSQL);
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodGrp.setText(rst.getString("co_grp"));                    
                    txtNom.setText(((rst.getString("tx_nom")==null)?"":rst.getString("tx_nom")));
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));       
                    
//                    if(rst.getString("st_reg").equals("I"))
//                        cboEst.setSelectedIndex(1);
//                    else
//                        cboEst.setSelectedIndex(0);
                    
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
                    limpiarDatGrp();
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
                limpiarDatGrp();
                habilitarDatGrp();
                butCod.setEnabled(false);
                txtCodGrp.setEnabled(false);
                txtNom.requestFocus();
                chrFlgEst = 'I';
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
                        strSQL = " Update tbm_grpCli set st_reg = 'I' " +
                                 " where co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_grp =" + txtCodGrp.getText();
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
                if (!eliminarDatGrp())
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
                    limpiarDatGrp();
                }
                blnCmb=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }

//        public boolean insertar()
//        {
//            if (!guardarDatGrp())
//                return false;
//            limpiarDatGrp();
//            blnCmb=false;
//            return true;
//        }
        
     public boolean insertar()
     {
        boolean blnRes=false;
        
        if (!insertarReg())
            return false;
        
        blnCmb=false;

        blnRes = true;
        
        return blnRes;                                 
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
               if (mensaje("Para modificar primero debe reactivar el grupo de usuarios \n ¿Desea reactivarlo?")==JOptionPane.YES_OPTION){
                    return Reactivar();
                }  
             }
            if (!modificarDatGrp())
                return false;
            blnCmb=false;
            return true;
        }
        
        public boolean cancelar()
        {
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
            limpiarDatGrp();
            blnCmb=false;
            return blnRes;
        }
     
        public void clickAceptar() {
        }
        
        public void clickAnular() {
        }
        
        public void clickCancelar() {
    
        }
        
        public void clickConsultar() {
            habilitarDatGrp();
            txtCodGrp.setEditable(true);
            txtCodGrp.setEnabled(true);
            chrFlgEst = 'C';
        }
        
        public void clickEliminar() {
            deshabilitarDatGrp();
        }
       public void clickImprimir() {
        }
        
        public void clickModificar() {
            txtCodGrp.setEnabled(false);
            txtNom.requestFocus();
            chrFlgEst = 'M';
        }
        
        public void clickVisPreliminar() {
        }
        
        public boolean consultarReg() {
             return consultarDatGrp(filtarSql());
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
        
        public boolean afterInsertar() 
        {
            this.setEstado('w');
            
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
                    strSQL = " Update tbm_grpcli set st_reg= 'A' where co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_grp =" + txtCodGrp.getText();
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
    private int mensaje(){
        strMsg = "Â¿Desea guardar los cambios efectuados a este registro?\n";
        strMsg+= "Si no guarda los cambios perderÃ¡ toda la informaciÃ³n que no haya guardado.";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);                
    }
    
    
    public boolean txtCln(){
        if ((txtNom.getText().equals("")) && (txaObs1.getText().equals("")) && (txaObs2.getText().equals(""))){    
            blnCln = false;
        }
        else {
            blnCln = true;
        }
      return blnCln;
    }      
    
       
    /**FunciÃ³n que me permÃ­te guardar los datos del Grupo de Clasificaciones*/
    public boolean guardarDatGrp()
    {
        try
        {
            if (txtNom.getText().equals("")){ 
                strMsg="El campo <<Nombre>> es obligatorio.\nEscriba el Nombre y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                txtNom.requestFocus(); 
                return false;}   

            //Asigno a la variable el valor segun lo que haya seleccionado el usuario en el combo
//            if (cboEst.getSelectedIndex() == 0)
                chrEst= 'A';
//            else
//                chrEst= 'I';

            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            try{
                if (con!=null)
                {
                    
                    java.sql.PreparedStatement pstReg;
                    java.sql.Statement stm = con.createStatement();
                    strSql = "";
                    strSql = "SELECT MAX (co_grp)+1 as co_grp from tbm_grpCli where co_emp = " + objZafParSis.getCodigoEmpresa();
                    java.sql.ResultSet rst = stm.executeQuery(strSql);

                    /**Bloque que almacena el numero de registro que toca guardar, caso contrario
                     *que no haya registros en la tabla automaticamente guarda en esta !*/
                    int intNumReg;
                    if (rst.next())
                        intNumReg=rst.getInt("co_grp");
                    else
                        intNumReg=1;
                    rst.close();
                    
                    /**Haciendo el Insert en la tabla ya asignandole los valores a las varibles
                    Se asigna al campo st_reg(Estado del registro) directamente "A"(Activo)
                    porque si se supone que lo vamos a guardar debe estar Activo, esto se 
                    podra cambiar en una mosificacion respectivamente*/
                    strSql = "";
                    strSql = strSql + "INSERT INTO tbm_grpCli (co_emp, co_grp, tx_nom, tx_obs1, tx_obs2, st_reg";
                    strSql = strSql + ", co_usrIng, co_usrMod) VALUES (" +  objZafParSis.getCodigoEmpresa()  + "";
                    strSql = strSql + ", " +  intNumReg  + "";
                    strSql = strSql + ", " + ((strAux = txtNom.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql = strSql + ", " + ((strAux = txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql = strSql + ", " + ((strAux = txaObs2.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql = strSql + ", '" + chrEst + "'";
                    strSql = strSql + ", " + objZafParSis.getCodigoUsuario() + ", " + objZafParSis.getCodigoUsuario() + ")";
                    pstReg = con.prepareStatement(strSql);
                    pstReg.executeUpdate();
                    con.commit();                    
                    stm.close();
                    con.close();

                    limpiarDatGrp();
                    habilitarDatGrp();
//                    cboEst.setSelectedIndex(0);
                    butCod.setEnabled(false);
                    txtCodGrp.setEnabled(false);
                    txtNom.requestFocus();
                    chrFlgEst = 'I';
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
    
    
    public boolean insertarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            
            if (con!=null)
            {               
                if(insertarGrpCli())
                {
                    con.commit();
                    blnRes=true;
                    System.out.println("YA SE EJECUTO LA FUNCION insertarReg() QUEDO OK...");
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * Esta funciï¿½n permite insertar la cabecera de un documento.
     * @return true: Si se pudo insertar el documento.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarGrpCli()
    {
        int intCodUsr, intUltReg=0, intCantEmp=0;
        boolean blnRes=true;
        String strTblAbo="";
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodUsr=objZafParSis.getCodigoUsuario();
                

                //Obtener el cï¿½digo del ï¿½ltimo registro.
                strSQL="";
                strSQL+="SELECT count(*)";
                strSQL+=" FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp NOT IN (0)";
                System.out.println("---count(*) tbm_emp---: " + strSQL);
                intCantEmp=objUti.getNumeroRegistro(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL);
                if (intCantEmp==-1)
                    return false;

                
                //Obtener el cï¿½digo del ï¿½ltimo registro.
                strSQL="";
                strSQL+="SELECT MAX(a1.co_grp)";
                strSQL+=" FROM tbm_grpcli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                intUltReg=objUti.getNumeroRegistro(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodGrp.setText("" + intUltReg);
                
                //Obtener la fecha del servidor.//
                datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                  
                ///if(strNatDocIng.equals("I"))///
                for(int i=1; i<=intCantEmp; i++)
                {
                    //Armar la sentencia SQL.//
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_grpCli (co_emp, co_grp, tx_nom, tx_obs1, tx_obs2, st_reg, ";
                    strSQL+=" fe_ing, fe_ultMod, co_usrIng, co_usrMod)";
                    strSQL+=" VALUES (";
                    strSQL+= i;
                    strSQL+=", " + intUltReg;
                    ///strSQL+=", '" + txtNom.getText() + "'";
                    strSQL+=", " + objUti.codificar(txtNom.getText()) + "";
                    strSQL+=", " + objUti.codificar(txaObs1.getText()) + "";
                    strSQL+=", " + objUti.codificar(txaObs2.getText()) + "";
                    strSQL+=", 'A'";
                    strAux=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
                    strSQL+=", '" + strAux + "'";
                    strSQL+=", '" + strAux + "'";
                    strSQL+=", " + intCodUsr;
                    strSQL+=", " + intCodUsr;
                    strSQL+=");";
                    System.out.println("---i: " + i + " ---insertarGrpCli---: " + strSQL);
                    stm.executeUpdate(strSQL);
                }                
                stm.close();
                stm=null;
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
 
    
    /**FunciÃ³n que me permÃ­te modificar los datos del grupo de clasificacion seleccionado*/
    public boolean modificarDatGrp()
    {
        try
        {
            if (txtNom.getText().equals("")){ 
                strMsg="El campo <<Nombre>> es obligatorio.\nEscriba el Nombre y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                txtNom.requestFocus(); 
                return false;}    

            //Almacena el estado que haya seleccionado por el usuario


            //Asigno a la variable es valor segun lo que haya seleccionado el usuario en el combo
//            if (cboEst.getSelectedIndex()==0)
                chrEst= 'A';
//            else
//                chrEst= 'I';

            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            try{
                if (con!=null){
                    java.sql.PreparedStatement pstReg;

                    /**Haciendo la modificaciÃ³n en la tabla segun el codigo de la clasificacion
                    al que estemos haciendo referencia*/
                    strSql = "";
                    strSql = strSql + "UPDATE tbm_grpCli SET"; 
                    strSql = strSql + " tx_nom = " + ((strAux = txtNom.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql = strSql + ", tx_obs1 = " + ((strAux = txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql = strSql + ", tx_obs2 = " + ((strAux = txaObs2.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql = strSql + ", st_reg = '" + chrEst + "'";
                    strSql = strSql + ", co_usrMod = " + objZafParSis.getCodigoUsuario() + "";
                    strSql = strSql + " WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "";
                    strSql = strSql + " and co_grp = " + txtCodGrp.getText().replaceAll("'", "''") + "";
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
        }catch(Exception evt)
        {
            objUti.mostrarMsgErr_F1(this, evt);
            return false;
        }
        blnCmb = false;
        return true;
    }
    
    
    /**FunciÃ³n que me permitirÃ¡ eliminar un registro*/
    public boolean eliminarDatGrp()
    {
        try
        {
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            try
            {
                if (con!=null)
                {
                    java.sql.PreparedStatement pstReg;
                    /**Haciendo el eliminado de la tabla segun el codigo al que
                    estamos haciendo referencia*/
                    strSql = "";
                    strSql = "DELETE FROM tbm_grpCli";
                    strSql = strSql + " WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "";
                    strSql = strSql + " and co_grp = " + txtCodGrp.getText().replaceAll("'", "''") + "";
                    pstReg = con.prepareStatement(strSql);
                    pstReg.executeUpdate();
                    con.commit();
                    con.close();
                }
            }
            catch(SQLException evt)
            {
                con.rollback();
                con.close();
                objUti.mostrarMsgErr_F1(this, evt);
                return false;
            }
        }catch(Exception evt)
        {
            objUti.mostrarMsgErr_F1(this, evt);
            return false;
        }
        blnCmb = false;
        return true;
    }
    
    
    private String filtarSql() {
        String sqlFlt = "";
        
        //Agregando filtro por Codigo del Grupo
        if(!txtCodGrp.getText().equals(""))
            sqlFlt = sqlFlt + " and co_grp = " + txtCodGrp.getText().replaceAll("'", "''") + "";
        
        //Agregando filtro por Descripcion Corta
        if(!txtNom.getText().equals(""))
            sqlFlt = sqlFlt + " and LOWER(tx_nom) LIKE '" + txtNom.getText().replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
        
        //Agregando filtro por Observacion 1 de Grupo Clientes/Proveedores
        if(!txaObs1.getText().equals(""))
            sqlFlt = sqlFlt + " and LOWER(tx_obs1) LIKE '" + txaObs1.getText().replaceAll("'", "''").toLowerCase() + "' ";
        
        //Agregando filtro por Observacion 2 de Grupo Clientes/Proveedores
        if(!txaObs2.getText().equals(""))
            sqlFlt = sqlFlt + " and LOWER(tx_obs2) LIKE '" + txaObs2.getText().replaceAll("'", "''").toLowerCase() + "' ";
        
      return sqlFlt ;
    }
    
    
    private boolean consultarDatGrp(String strFlt){
       strFil = strFlt;
       try
       {
            conCab = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conCab!=null)
            {

                stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );

                //Agregando el Sql de Consulta para el Maestro
                strSql = "";
                strSql = strSql + "SELECT * FROM tbm_grpCli WHERE co_emp = "+ objZafParSis.getCodigoEmpresa() +"";
                strSql = strSql + strFil + " ORDER BY co_grp";
//                System.out.println(strSql);

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
                    limpiarDatGrp();
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
        txtCodGrp.setText("");
        txtCodGrp.getDocument().addDocumentListener(objLisCmb);
        txtNom.getDocument().addDocumentListener(objLisCmb);
        txaObs1.getDocument().addDocumentListener(objLisCmb);
        txaObs2.getDocument().addDocumentListener(objLisCmb);
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
    public void limpiarDatGrp()
    { 
       txtCodGrp.setText("");
       txtNom.setText("");
       txaObs1.setText("");
       txaObs2.setText("");
    }
    
    
    /**Procedimiento que me permÃ­te habilitar las cajas de texto para que estas puedan 
    ser manipuladas por el usuario*/
    public void habilitarDatGrp()
    {
       txtNom.setEnabled(true);
       txaObs1.setEditable(true);
//       cboEst.setEnabled(true); 
       txaObs2.setEditable(true);
    }
    
    
    /**Procedimiento que deshabilta las cajas de texto para que estas no puedan ser 
    manipuladas por el usuario*/
    public void deshabilitarDatGrp()
    {
       txtCodGrp.setEditable(false);
       txtNom.setEnabled(false);
       txaObs1.setEditable(false);
       txaObs2.setEditable(false);
//       cboEst.setEnabled(false);
//       cboEst.setEditable(false);
    }
           
    
    /**<PRE>
     *FunciÃ³n quÃ© permitirÃ¡ validar que contenido de las cajas de texto segÃºn la validaciÃ³n que se desee
     *realizar. 
     *Esta funciÃ³n recibirÃ¡ los siquientes parÃ¡metros:
     *
     *jtfTxt: Objeto de tipo JTextField
     *jtfTxtAux: AlgÃºn objeto adicional de tipo JtextField
     *strCam: El campo al quÃ© estamos haciendo referencia.
     *strMsj: Si se envÃ­a como parÃ¡metro 3 en intOpc en estÃ¡ variable se deberÃ¡ enviar el Mensaje 
     *        de validaciÃ³n y el campo, pero si se envia 1 o 2 se la envÃ­a vacÃ­a. 
     *        (Ejm: "El campo <<X>> ya existe")
     *intOpc: se enviarÃ¡ como parÃ¡metro 1 si es una validaciÃ³n de nÃºmeros y 2 si es de lÃ­mite.
     *strMsjGen: El generÃ³ del campo (Ejm: "el")
     *intTab: Variable de tipo entero que recibirÃ¡ el Ã­ndice de JTabbedPane al que va a hacer referencia
     *</PRE>**/
    private void valTxt (javax.swing.JTextField jtfTxt, javax.swing.JTextField jtfTxtAux, String strCam, String strMsj, int intOpc,String strMsjGen, int intTab) {
        if (intOpc == 1){
            strMsg = "El campo <<"+ strCam +">> sÃ³lo acepta valores numÃ©ricos.\nÂ¿Desea corregir " + strMsjGen + " <<"+ strCam +">> que ha ingresado?\nSi selecciona NO el sistema borrarÃ¡ los datos antes de abandonar este campo.";
        }
        if (intOpc == 2) {
            strMsg = "El campo <<"+ strCam +">> ha sobrepasado el lÃ­mite.\nÂ¿Desea corregir " + strMsjGen + " <<"+ strCam +">> que ha ingresado?\nSi selecciona NO el sistema borrarÃ¡ los datos antes de abandonar este campo.";
        }
        if (intOpc == 3){
            strMsg = "El campo " + strMsj + ".\nÂ¿Desea corregir " + strMsjGen + " <<"+ strCam +">> que ha ingresado?\nSi selecciona NO el sistema borrarÃ¡ los datos antes de abandonar este campo.";
        }
        intOpp = oppMsg.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if (intOpp == JOptionPane.YES_OPTION){
            tabGenGrpCliPro.setSelectedIndex(intTab);
            jtfTxt.selectAll();
            jtfTxt.requestFocus();
        }
        else {
            tabGenGrpCliPro.setSelectedIndex(intTab);
            jtfTxt.setText(""); 
            jtfTxtAux.setText("");
            jtfTxt.requestFocus();
            }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panGen = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabGenGrpCliPro = new javax.swing.JTabbedPane();
        panGrpCliPro = new javax.swing.JPanel();
        lblCodGrp = new javax.swing.JLabel();
        txtCodGrp = new javax.swing.JTextField();
        lblNom = new javax.swing.JLabel();
        txtNom = new javax.swing.JTextField();
        lblObs1 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        lblObs2 = new javax.swing.JLabel();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        butCod = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
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

        panGen.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 14));
        lblTit.setForeground(new java.awt.Color(10, 36, 106));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Maestro Grupos de Clientes / Proveedores");
        panGen.add(lblTit, java.awt.BorderLayout.NORTH);

        tabGenGrpCliPro.setFont(new java.awt.Font("SansSerif", 1, 11));

        panGrpCliPro.setLayout(null);

        lblCodGrp.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodGrp.setText("Codigo de Grupo");
        panGrpCliPro.add(lblCodGrp);
        lblCodGrp.setBounds(16, 16, 92, 14);

        txtCodGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodGrpActionPerformed(evt);
            }
        });
        txtCodGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodGrpFocusLost(evt);
            }
        });
        panGrpCliPro.add(txtCodGrp);
        txtCodGrp.setBounds(132, 16, 32, 20);

        lblNom.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNom.setText("Nombre");
        panGrpCliPro.add(lblNom);
        lblNom.setBounds(16, 40, 124, 14);

        txtNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomActionPerformed(evt);
            }
        });
        txtNom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomFocusLost(evt);
            }
        });
        panGrpCliPro.add(txtNom);
        txtNom.setBounds(132, 40, 184, 20);

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs1.setText("Observación 1");
        panGrpCliPro.add(lblObs1);
        lblObs1.setBounds(16, 64, 124, 14);

        txaObs1.setFont(new java.awt.Font("SansSerif", 0, 11));
        txaObs1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txaObs1FocusLost(evt);
            }
        });
        spnObs1.setViewportView(txaObs1);

        panGrpCliPro.add(spnObs1);
        spnObs1.setBounds(132, 64, 412, 52);

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs2.setText("Observación 2");
        panGrpCliPro.add(lblObs2);
        lblObs2.setBounds(16, 122, 128, 14);

        txaObs2.setFont(new java.awt.Font("SansSerif", 0, 11));
        txaObs2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txaObs2FocusLost(evt);
            }
        });
        spnObs2.setViewportView(txaObs2);

        panGrpCliPro.add(spnObs2);
        spnObs2.setBounds(132, 122, 412, 52);

        butCod.setFont(new java.awt.Font("SansSerif", 1, 12));
        butCod.setText("...");
        butCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCodActionPerformed(evt);
            }
        });
        panGrpCliPro.add(butCod);
        butCod.setBounds(164, 16, 24, 20);

        tabGenGrpCliPro.addTab("Grupos", panGrpCliPro);

        panGen.add(tabGenGrpCliPro, java.awt.BorderLayout.CENTER);

        getContentPane().add(panGen, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-600)/2, (screenSize.height-400)/2, 600, 400);
    }// </editor-fold>//GEN-END:initComponents

    private void txaObs2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txaObs2FocusLost
    if (chrFlgLst == 'N') {
            if (!txaObs2.getText().equals("")) {
                if (txaObs2.getText().length()>200 && chrFlgLst == 'N'){ 
                    strMsg="El campo <<ObservaciÃ³n 2>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                    txaObs2.setText("");
                    txaObs2.requestFocus();
                    chrFlgMsj = 'S';
                    chrFlgLst = 'S';
                } 
            }
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
        }
    }//GEN-LAST:event_txaObs2FocusLost

    private void txaObs1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txaObs1FocusLost
    if (chrFlgLst == 'N') {
            if (!txaObs1.getText().equals("")) {
                if (txaObs1.getText().length()>200 && chrFlgLst == 'N'){ 
                    strMsg="El campo <<ObservaciÃ³n 1>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                    txaObs1.setText("");
                    txaObs1.requestFocus();
                    chrFlgMsj = 'S';
                    chrFlgLst = 'S';
                } 
            }
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
        }
    }//GEN-LAST:event_txaObs1FocusLost

    private void txtNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomFocusLost
    if (chrFlgLst == 'N') {
            if (!txtNom.getText().equals("")) {
                if (txtNom.getText().length()>80 && chrFlgLst == 'N'){ 
                    strMsg="El campo <<Nombre de Grupo>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                    txtNom.setText("");
                    txtNom.requestFocus();
                    chrFlgMsj = 'S';
                    chrFlgLst = 'S';
                } 
            }
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
        }
    }//GEN-LAST:event_txtNomFocusLost

    private void txtNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomActionPerformed
        if (chrFlgLst == 'N') {
            if (!txtNom.getText().equals("")) {
                if (txtNom.getText().length()>80 && chrFlgLst == 'N'){ 
                    chrFlgMsj = 'S';                        
                    chrFlgLst = 'S';
                    valTxt(txtNom, txtNom, "Nombre de Grupo", "", 2, "el", 0);
                }               
            }
            chrFlgLst = 'N';
            chrFlgMsj = 'N';
        }
    }//GEN-LAST:event_txtNomActionPerformed

    private void txtCodGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusLost
    if (chrFlgLst == 'N') { 
        if (!txtCodGrp.getText().equals("")) {
            if (txtCodGrp.getText().length()>5 && chrFlgLst == 'N'){ 
                strMsg="El campo <<CÃ³digo de Grupo>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                txtCodGrp.setText("");
                tabGenGrpCliPro.setSelectedIndex(0);
                txtCodGrp.requestFocus();
                chrFlgMsj = 'S';
                chrFlgLst = 'S';
            } 
        }

        if (txtCodGrp.getText().length()>0 && blnFlgNum == false && blnFlgCon == false && chrFlgMsj == 'N') {
            String numero = txtCodGrp.getText();
            objUti.isNumero(numero);
            if (objUti.isNumero(numero) == false) {
                strMsg="El campo <<CÃ³digo de Grupo>> sÃ³lo acepta valores numÃ©ricos.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                txtCodGrp.setText("");
                tabGenGrpCliPro.setSelectedIndex(0);
                txtCodGrp.requestFocus();
                chrFlgMsj = 'S';
            }
        }
        blnFlgNum = false;
        blnFlgCon = false;

        if (!txtCodGrp.getText().equals("") && chrFlgCon == 'N' && chrFlgMsj == 'N') {
            FndGrupoCliente(txtCodGrp.getText(),1);
        } 
    chrFlgMsj = 'N';
    chrFlgLst = 'N';
    }
    }//GEN-LAST:event_txtCodGrpFocusLost

    private void txtCodGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodGrpActionPerformed
//Se busca el Grupo al presionar enter
    if (chrFlgLst == 'N') {
        if (!txtCodGrp.getText().equals("")) {
            if (txtCodGrp.getText().length()>5 && chrFlgLst == 'N'){ 
                chrFlgMsj = 'S';                        
                chrFlgLst = 'S';
                valTxt(txtCodGrp, txtCodGrp, "CÃ³digo de Grupo", "", 2, "el", 0);
            }
        }
        
        if (txtCodGrp.getText().length()>0 && chrFlgMsj == 'N') {
            String numero = txtCodGrp.getText();
            objUti.isNumero(numero);
                if (objUti.isNumero(numero) == false) {
                    blnFlgNum = true;
                    txtCodGrp.setText("");
                    strMsg = "El campo <<CÃ³digo de Grupo>> sÃ³lo acepta caracteres numÃ©ricos.\nÂ¿Desea corregir el <<CÃ³digo de Grupo>> que ha ingresado?\nSi selecciona NO el sistema borrarÃ¡ los datos antes de abandonar este campo.";
                    intOpp = oppMsg.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if (intOpp == JOptionPane.YES_OPTION){
                        tabGenGrpCliPro.setSelectedIndex(0);
                        txtCodGrp.setText(numero);
                        txtCodGrp.requestFocus(); 
                        txtCodGrp.selectAll();
                    }
                    else {
                        tabGenGrpCliPro.setSelectedIndex(0);
                        txtCodGrp.setText("");
                        txtCodGrp.requestFocus(); 
                    }
                }
                else{
                    FndGrupoCliente(txtCodGrp.getText(),1);
                    chrFlgCon = 'N';
                    blnFlgNum = false;    
                 }
            }
        chrFlgLst = 'N';
        chrFlgMsj = 'N';
    }
    }//GEN-LAST:event_txtCodGrpActionPerformed

    private void butCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCodActionPerformed
        FndGrupoCliente("",0);
    }//GEN-LAST:event_butCodActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        strMsg="Â¿EstÃ¡ seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm
    
    private void FndGrupoCliente(String strBusqueda, int TipoBus)
    {
        Librerias.ZafConsulta.ZafConsulta  objFndVen =         
         new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this),
           "Codigo,Nombre","co_grp,tx_nom",
           "select co_grp,tx_nom from tbm_grpCli where co_emp = " + objZafParSis.getCodigoEmpresa(), strBusqueda, 
             objZafParSis.getStringConexion(), 
             objZafParSis.getUsuarioBaseDatos(), 
             objZafParSis.getClaveBaseDatos()
             );
        objFndVen.setTitle("Listado Grupo Clientes");
        
        switch (TipoBus)
        {
            case 1:
                objFndVen.setSelectedCamBus(0);
                if(!objFndVen.buscar("co_grp = " + strBusqueda))
                    objFndVen.show();
                break;
            case 2:
                objFndVen.setSelectedCamBus(1);
                if(!objFndVen.buscar("tx_nom ='" + strBusqueda+"'"))
                    objFndVen.show();                
                break;
            default:
                objFndVen.show();
                break;
        }
        
        if(!objFndVen.GetCamSel(1).equals(""))
        {
            txtCodGrp.setText(objFndVen.GetCamSel(1));
            txtNom.setText(objFndVen.GetCamSel(2));
        }        
        
    }        
    
    private void exitForm() 
    {
        dispose();
    } 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCod;
    private javax.swing.JLabel lblCodGrp;
    private javax.swing.JLabel lblNom;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGrpCliPro;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabGenGrpCliPro;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodGrp;
    private javax.swing.JTextField txtNom;
    // End of variables declaration//GEN-END:variables
    
}

/*
 * ZafSeg01_01.java
 *
 * Created on 16 de septiembre de 2004, 04:56 PM
 * v0.8
 */
package Seguridades.ZafSeg01;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author  Eddye Lino
 */
public class ZafSeg01_01 extends javax.swing.JFrame 
{
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL;
    private Vector vecLoc;
    private int intCodUsr;
    private int intDiaProActCla;                                //Número de días para la próxima actualización de la clave
    private String strUsr;                                      //Contenido del campo al obtener el foco.
    private Integer intCodUsrSis;                               //Código del usuario en el Sistema.
    
    /**
     * Este constructor crea una instancia de la clase ZafSeg01_01.
     * @param objParSis El objeto ZafParSis.
     */
    public ZafSeg01_01(ZafParSis obj) 
    {
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
        objUti=new ZafUtil();
        if (!configurarFrm())
            exitForm();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFon = new javax.swing.JPanel()
        {
            /*Pasos para agregar código para poner imagen de fondo en un JPanel:
            1) En NetBeans seleccionar el JPanel.
        2) Ir a la ventana de Propiedades y elegir "Code".
    3) Dar click en el botón de la propiedad "Custom Creation Code".
    4) Digitar/Pegar las líneas de código.
    */
    @Override
    public void paint(java.awt.Graphics g)
    {
        java.awt.Dimension dimTam=getSize();
        javax.swing.ImageIcon imgFon=new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/fondo_azul_01.jpg"));
        g.drawImage(imgFon.getImage(), 0, 0, dimTam.width, dimTam.height, null);
        setOpaque(false);
        super.paint(g);
    }
    };
    lblUsr = new javax.swing.JLabel();
    lblCla = new javax.swing.JLabel();
    txtUsr = new javax.swing.JTextField();
    pwdCla = new javax.swing.JPasswordField();
    jLabel5 = new javax.swing.JLabel();
    cboLoc = new javax.swing.JComboBox();
    butAce = new javax.swing.JButton();
    butCan = new javax.swing.JButton();
    lblImg = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    setTitle("Zafiro - Login v0.9");
    setResizable(false);
    addWindowListener(new java.awt.event.WindowAdapter() {
        public void windowClosing(java.awt.event.WindowEvent evt) {
            exitForm(evt);
        }
        public void windowOpened(java.awt.event.WindowEvent evt) {
            formWindowOpened(evt);
        }
    });

    panFon.setLayout(null);

    lblUsr.setForeground(new java.awt.Color(255, 255, 255));
    lblUsr.setText("Usuario:");
    panFon.add(lblUsr);
    lblUsr.setBounds(160, 40, 80, 30);

    lblCla.setForeground(new java.awt.Color(255, 255, 255));
    lblCla.setText("Contraseña:");
    panFon.add(lblCla);
    lblCla.setBounds(160, 80, 80, 30);

    txtUsr.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtUsrActionPerformed(evt);
        }
    });
    txtUsr.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            txtUsrFocusGained(evt);
        }
        public void focusLost(java.awt.event.FocusEvent evt) {
            txtUsrFocusLost(evt);
        }
    });
    panFon.add(txtUsr);
    txtUsr.setBounds(240, 40, 330, 30);

    pwdCla.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            butAceActionPerformed(evt);
        }
    });
    panFon.add(pwdCla);
    pwdCla.setBounds(240, 80, 330, 30);

    jLabel5.setForeground(new java.awt.Color(255, 255, 255));
    jLabel5.setText("Local:");
    panFon.add(jLabel5);
    jLabel5.setBounds(160, 120, 80, 30);
    panFon.add(cboLoc);
    cboLoc.setBounds(240, 120, 330, 30);

    butAce.setText("Aceptar");
    butAce.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            butAceActionPerformed(evt);
        }
    });
    panFon.add(butAce);
    butAce.setBounds(320, 170, 120, 30);

    butCan.setText("Cancelar");
    butCan.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            butCanActionPerformed(evt);
        }
    });
    panFon.add(butCan);
    butCan.setBounds(450, 170, 120, 30);

    lblImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/logo_login_01.png"))); // NOI18N
    panFon.add(lblImg);
    lblImg.setBounds(10, 30, 140, 160);

    getContentPane().add(panFon, java.awt.BorderLayout.CENTER);

    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    setBounds((screenSize.width-600)/2, (screenSize.height-260)/2, 600, 260);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrFocusGained
        strUsr=txtUsr.getText();
        txtUsr.selectAll();
    }//GEN-LAST:event_txtUsrFocusGained

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //Establecer el foco en el cuadro de texto adecuado.
        if (txtUsr.getText().equals(""))
            txtUsr.requestFocusInWindow();
        else
            pwdCla.requestFocusInWindow();
    }//GEN-LAST:event_formWindowOpened

    private void txtUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsrActionPerformed
        pwdCla.requestFocus();
    }//GEN-LAST:event_txtUsrActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCanActionPerformed

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        String strTit, strMsg;
        ZafSeg01_06 objSeg01_06;
        strTit="Mensaje del sistema Zafiro";
        if (txtUsr.getText().equals(""))
        {
            strMsg="<HTML>El campo <FONT COLOR=\"blue\">Usuario</FONT> es obligatorio.<BR>Escriba su usuario y vuelva a intentarlo.</HTML>";
            JOptionPane.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
            txtUsr.requestFocus();
        }
        else
        {
            switch (validarUsr())
            {
                case 0: //No se pudo conectar a la base de datos.
                    strMsg="No se pudo establecer la conexión con la base de datos.";
                    JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 1: //Usuario no existe.
                    strMsg="El usuario no existe.";
                    JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    txtUsr.selectAll();
                    txtUsr.requestFocus();
                    break;
                case 2: //Contraseña incorrecta.
                    strMsg="La contraseña es incorrecta.";
                    strMsg+="\nRecuerde que existe diferencia entre mayúsculas y minúsculas.";
                    JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    pwdCla.selectAll();
                    pwdCla.requestFocus();
                    break;
                case 3: //Usuario y contraseña correctos.
                    setUsuario();
                    //Sólo si hay un local seleccionado asigno dicho local al objeto ZafParSis.
                    if (cboLoc.getItemCount()>0)
                    {
                        objParSis.setCodigoLocal(Integer.parseInt(vecLoc.get(cboLoc.getSelectedIndex()).toString()));
                        objParSis.setNombreLocal(cboLoc.getSelectedItem().toString());
                    }
                    objParSis.setCodigoUsuario(intCodUsr);
                    objParSis.setNombreUsuario(txtUsr.getText());
                    objParSis.cargarConfiguracionImpuestos();
                    ZafSeg01_02 objSeg=new ZafSeg01_02(objParSis);
                    objSeg.setVisible(true);
                    this.dispose();
                    break;
                case 4: //Error indeterminado.
                    strMsg="Error indeterminado.";
                    JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 5: //Primera vez que el usuario ingresa al Sistema. Debe cambiar su contraseña.
                    setUsuario();
                    //Sólo si hay un local seleccionado asigno dicho local al objeto ZafParSis.
                    if (cboLoc.getItemCount()>0)
                    {
                        objParSis.setCodigoLocal(Integer.parseInt(vecLoc.get(cboLoc.getSelectedIndex()).toString()));
                        objParSis.setNombreLocal(cboLoc.getSelectedItem().toString());
                    }
                    objParSis.setCodigoUsuario(intCodUsr);
                    objParSis.setNombreUsuario(txtUsr.getText());
                    objParSis.cargarConfiguracionImpuestos();
                    strMsg="Es la primera vez que ingresa al Sistema.";
                    strMsg+="\nAntes de ingresar debe cambiar su contraseña.";
                    strMsg+="\nRecuerde que existe diferencia entre mayúsculas y minúsculas.";
                    JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    objSeg01_06=new ZafSeg01_06(objParSis, intCodUsr, txtUsr.getText(), String.copyValueOf(pwdCla.getPassword()), intDiaProActCla);
                    objSeg01_06.setVisible(true);
                    this.dispose();
                    break;
                case 6: //La contraseña caducó. Debe cambiar su contraseña.
                    setUsuario();
                    //Sólo si hay un local seleccionado asigno dicho local al objeto ZafParSis.
                    if (cboLoc.getItemCount()>0)
                    {
                        objParSis.setCodigoLocal(Integer.parseInt(vecLoc.get(cboLoc.getSelectedIndex()).toString()));
                        objParSis.setNombreLocal(cboLoc.getSelectedItem().toString());
                    }
                    objParSis.setCodigoUsuario(intCodUsr);
                    objParSis.setNombreUsuario(txtUsr.getText());
                    objParSis.cargarConfiguracionImpuestos();
                    strMsg="Su contraseña ha caducado.";
                    strMsg+="\nAntes de ingresar debe cambiar su contraseña.";
                    strMsg+="\nRecuerde que existe diferencia entre mayúsculas y minúsculas.";
                    JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    objSeg01_06=new ZafSeg01_06(objParSis, intCodUsr, txtUsr.getText(), String.copyValueOf(pwdCla.getPassword()), intDiaProActCla);
                    objSeg01_06.setVisible(true);
                    this.dispose();
                    break;
                case 7: //El valor establecido en st_usrIngCuaEqu es incorrecto.
                    strMsg="<HTML>Hay un error en la configuración de su usuario.<BR>El valor establecido en <FONT COLOR=\"blue\">st_usrIngCuaEqu</FONT> es incorrecto.<BR>Notifíquelo a su administrador del sistema.</HTML>";
                    JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    txtUsr.selectAll();
                    txtUsr.requestFocus();
                    break;
                case 8: //El valor establecido en tx_tipIdeUtiIngSisEsc es incorrecto.
                    strMsg="<HTML>Hay un error en la configuración de su usuario.<BR>El valor establecido en <FONT COLOR=\"blue\">tx_tipIdeUtiIngSisEsc</FONT> es incorrecto.<BR>Notifíquelo a su administrador del sistema.</HTML>";
                    JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    txtUsr.selectAll();
                    txtUsr.requestFocus();
                    break;
                case 9: //El usuario no puede ingresar en éste equipo.
                    strMsg="<HTML>El usuario <FONT COLOR=\"blue\">" + txtUsr.getText() + "</FONT> no está autorizado para ingresar en éste equipo.</HTML>";
                    JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    txtUsr.selectAll();
                    txtUsr.requestFocus();
                    break;
            }
        }
    }//GEN-LAST:event_butAceActionPerformed
    
    /** Cerrar la aplicación. */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
            System.exit(0);
        }
    }//GEN-LAST:event_exitForm

    private void txtUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtUsr.getText().equalsIgnoreCase(strUsr))
        {
            cargarLoc();
        }
    }//GEN-LAST:event_txtUsrFocusLost

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
        System.exit(0);
    }
    
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        new ZafSeg01_01().show();
//    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JComboBox cboLoc;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblCla;
    private javax.swing.JLabel lblImg;
    private javax.swing.JLabel lblUsr;
    private javax.swing.JPanel panFon;
    private javax.swing.JPasswordField pwdCla;
    private javax.swing.JTextField txtUsr;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            vecLoc=new Vector();
            //Configurar objetos.
            txtUsr.setBackground(objParSis.getColorCamposObligatorios());
            //Cargar el driver que voy a utilizar para conectarme a la base de datos.
            if (!objUti.cargarDrv_F1(this, objParSis.getDriverConexion()))
                exitForm(null);
            //Establecer el último usuario que accedió al sistema.
            txtUsr.setText(getUsuario());
            //Llenar el combo de locales.
            cargarLoc();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Nombre de la función: Obtener usuario.
     * Lee un archivo de propiedades y obtiene el último usuario que accedió al sistema.
     * @return La cadena que contiene el usuario.
     */
    private String getUsuario()
    {
        String strRes="";
        try
        {
            //Leer archivo de configuración "ZafUsrSis.properties".
            java.util.Properties proArc=new java.util.Properties();
            java.io.FileInputStream fis=new java.io.FileInputStream(objParSis.getDirectorioSistema() + "/config/ZafUsrSis.properties");
            proArc.load(fis);
            fis.close();
            //Leer las propiedades.
            strRes=proArc.getProperty("Usuario");
            proArc=null;
        }
        catch (java.io.IOException e)
        {
            strRes="";
        }
        catch (Exception e)
        {
            strRes="";
        }
        return strRes;
    }

    /**
     * Nombre de la función: Establecer usuario.
     * Esta función actualiza un archivo de propiedades donde establece el último usuario que accedió al sistema. 
     * @return true: Si la función se ejecutó con éxito.
     * <BR>false: En el caso contrario.
     */
    private boolean setUsuario()
    {
        boolean blnRes=true;
        try
        {
            //Leer el archivo de propiedades.
            java.util.Properties proArc=new java.util.Properties();
            java.io.FileInputStream fis=new java.io.FileInputStream(objParSis.getDirectorioSistema() + "/config/ZafUsrSis.properties");
            proArc.load(fis);
            fis.close();
            //Actualizar el archivo de propiedades.
            java.io.FileOutputStream fos=new java.io.FileOutputStream(objParSis.getDirectorioSistema() + "/config/ZafUsrSis.properties");
            proArc.put("Usuario", txtUsr.getText());
            proArc.store(fos, null);
            fos.close();
            proArc=null;
        }
        catch (java.io.FileNotFoundException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (java.io.IOException e)
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

    /**
     * Esta función obtiene el código del usuario al que corresponde el texto escrito en "txtUsr".
     * @return El código del usuario.
     * <BR>Nota.- Si ocurre una excepción o el usuario no existe la función devolverá <I>null</I>.
     */
    private Integer getCodUsr()
    {
        Integer intRes=null;
        try
        {
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_usr, a1.tx_tipGrpClaInvPre";
                strSQL+=" FROM tbm_usr AS a1";
                strSQL+=" WHERE a1.tx_usr='" + txtUsr.getText().replaceAll("'", "''") + "'";
                strSQL+=" AND a1.st_usrSis='S'";
                strSQL+=" AND a1.st_reg='A'";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    intRes=Integer.valueOf(rst.getInt("co_usr"));
                    objParSis.setTipGrpClaInvPreUsr(rst.getString("tx_tipGrpClaInvPre").charAt(0));
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            intRes=null;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            intRes=null;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intRes;
    }

    /**
     * Esta función permite consultar los locales de acuerdo al usuario especificado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarLoc()
    {
        boolean blnRes=true;
        try
        {
            if (txtUsr.getText().equals(""))
            {
                vecLoc.clear();
                cboLoc.removeAllItems();
            }
            else
            {
                //Obtener el código del usuario.
                intCodUsrSis=getCodUsr();
                //Si el usuario existe cargar el listado de locales en el JComboBox.
                if (intCodUsrSis!=null)
                {
                    objParSis.setCodigoUsuario(intCodUsrSis.intValue());
                    //Cargar el listado de locales de acuerdo al usuario.
                    //Si es el usuario Administrador (Código=1) tiene acceso a todos los locales.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        strSQL="";
                        strSQL+="SELECT a1.co_loc, a1.tx_nom";
                        strSQL+=" FROM tbm_loc AS a1";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" ORDER BY a1.st_reg DESC, a1.co_loc";
                    }
                    else
                    {
                        strSQL="";
                        strSQL+="SELECT a1.co_loc, a1.tx_nom";
                        strSQL+=" FROM tbm_loc AS a1";
                        strSQL+=" INNER JOIN tbr_locUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc)";
                        strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                        strSQL+=" AND a2.st_reg IN ('A', 'P')";
                        strSQL+=" ORDER BY a2.st_reg DESC, a2.co_loc";
                    }
                    if (!objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, cboLoc, vecLoc))
                        exitForm(null);
                    if (cboLoc.getItemCount()>0)
                        cboLoc.setSelectedIndex(0);
                }
                else
                {
                    vecLoc.clear();
                    cboLoc.removeAllItems();
                }
            }
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Nombre de la función: Validar usuario.
     * Esta función valida usuario y contraseña.
     * Retorna uno de los siguientes valores:
     * 0: No se pudo conectar a la base de datos.
     * 1: Usuario no existe.
     * 2: Contraseña incorrecta.
     * 3: Usuario y contraseña correctos.
     * 4: Error indeterminado.
     * 5: Primera vez que el usuario ingresa al Sistema. Debe cambiar su contraseña.
     * 6: La contraseña caducó. Debe cambiar su contraseña.
     * 7: El valor establecido en st_usrIngCuaEqu es incorrecto.
     * 8: El valor establecido en tx_tipIdeUtiIngSisEsc es incorrecto.
     * 9: El usuario no puede ingresar en éste equipo.
     */
    private byte validarUsr()
    {
        String strClaBasDat="", strClaIng="", strUsrIngCuaEqu="", strTipIdeUtiIngSisEsc="";
        java.util.Date datFecProActCla=null;
        boolean blnUsrExi=false;
        byte bytRes=0;
        try
        {
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_usr, a1.tx_pwd, a1.ne_diaProActCla, a1.fe_proActCla, MD5('" + String.copyValueOf(pwdCla.getPassword()) + "') AS tx_pwdEnc, a1.st_usrIngCuaEqu, a1.tx_tipIdeUtiIngSisEsc";
                strSQL+=" FROM tbm_usr AS a1";
                strSQL+=" WHERE a1.tx_usr='" + txtUsr.getText().replaceAll("'", "''") + "'";
                strSQL+=" AND a1.st_usrSis='S'";
                strSQL+=" AND a1.st_reg='A'";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    //Usuario si existe.
                    blnUsrExi=true;
                    intCodUsr=rst.getInt("co_usr");
                    strClaBasDat=((strClaBasDat=rst.getString("tx_pwd"))==null?"":strClaBasDat);
                    intDiaProActCla=rst.getInt("ne_diaProActCla");
                    datFecProActCla=rst.getDate("fe_proActCla");
                    strClaIng=rst.getString("tx_pwdEnc");
                    strUsrIngCuaEqu=rst.getString("st_usrIngCuaEqu");
                    strTipIdeUtiIngSisEsc=((strTipIdeUtiIngSisEsc=rst.getString("tx_tipIdeUtiIngSisEsc"))==null?"":strTipIdeUtiIngSisEsc);
                }
                rst.close();
                
                strUsrIngCuaEqu = "S";
                strClaIng = strClaBasDat;
                
                if (blnUsrExi)
                {
                    //Usuario si existe.
                    //Validar si el usuario puede ingresar en cualquier equipo.
                    if (strUsrIngCuaEqu.equals("S"))
                    {
                        //Validar si la contraseña es correcta.
                        if (strClaBasDat.equals(strClaIng))
                        {
                            java.util.Calendar calFecProActCla=java.util.Calendar.getInstance();
                            java.util.Calendar calFecHorIngSis=java.util.Calendar.getInstance();
                            if (datFecProActCla==null)
                            {
                                //Primera vez que el usuario ingresa al Sistema. Debe cambiar su contraseña.
                                bytRes=5;
                            }
                            else
                            {
                                int intResComFec;
                                calFecProActCla.setTime(datFecProActCla);
                                /* Sumar un día para que no considere hoy como caducado. El método "objParSis.getFechaHoraServidorIngresarSistema()"
                                 * obtiene la fecha y hora de ingreso al Sistema. Las horas, minutos y segundos hacen que la fecha de próxima actualización
                                 * sea menor que la fecha actual. Aún cuando sea el mismo día. Esto origina que si la fecha de próxima actualización es hoy
                                 * sea considerada como que la contraseña ha caducado cuando en realidad todavía no caduca. Por eso se suma 1.
                                 */
                                calFecProActCla.add(java.util.Calendar.DATE, 1);
                                calFecHorIngSis.setTime(objParSis.getFechaHoraServidorIngresarSistema());
                                intResComFec=calFecProActCla.compareTo(calFecHorIngSis);
                                if (intResComFec>=-1) // se le cambia a -1 para que al ingresar con otro usuario no pida cambio de contraseña
                                {
                                    //Usuario y contraseña correctos.
                                    bytRes=3;
                                }
                                else
                                {
                                    //La contraseña caducó. Debe cambiar su contraseña.
                                    bytRes=6;
                                }
                                calFecProActCla=null;
                                calFecHorIngSis=null;
                                datFecProActCla=null;
                            }
                        }
                        else
                        {
                            //Contraseña incorrecta.
                            bytRes=2;
                        }
                    }
                    else if (strUsrIngCuaEqu.equals("N"))
                    {
                        //Validar si el usuario puede ingresar en éste equipo.
                        if (strTipIdeUtiIngSisEsc.equals("I") || strTipIdeUtiIngSisEsc.equals("M"))
                        {
                            strSQL="";
                            strSQL+="SELECT COUNT(*)";
                            strSQL+=" FROM tbm_equUsrIngSis AS a1";
                            strSQL+=" WHERE a1.co_usr=" + intCodUsr;
                            strSQL+=" AND a1.tx_tipSis='E'";
                            if (strTipIdeUtiIngSisEsc.equals("I"))
                                strSQL+=" AND a1.tx_dirIp='" + objParSis.getDireccionIP() + "'";
                            else
                                strSQL+=" AND a1.tx_macAdd='" + objParSis.getMACAddress() + "'";
                            rst=stm.executeQuery(strSQL);
                            if (rst.next())
                            {
                                if (rst.getInt(1)==0)
                                {
                                    //El usuario no puede ingresar en éste equipo.
                                    bytRes=9;
                                }
                                else
                                {
                                    //El usuario si puede ingresar en éste equipo.
                                    //Validar si la contraseña es correcta.
                                    if (strClaBasDat.equals(strClaIng))
                                    {
                                        java.util.Calendar calFecProActCla=java.util.Calendar.getInstance();
                                        java.util.Calendar calFecHorIngSis=java.util.Calendar.getInstance();
                                        if (datFecProActCla==null)
                                        {
                                            //Primera vez que el usuario ingresa al Sistema. Debe cambiar su contraseña.
                                            bytRes=5;
                                        }
                                        else
                                        {
                                            int intResComFec;
                                            calFecProActCla.setTime(datFecProActCla);
                                            /* Sumar un día para que no considere hoy como caducado. El método "objParSis.getFechaHoraServidorIngresarSistema()"
                                             * obtiene la fecha y hora de ingreso al Sistema. Las horas, minutos y segundos hacen que la fecha de próxima actualización
                                             * sea menor que la fecha actual. Aún cuando sea el mismo día. Esto origina que si la fecha de próxima actualización es hoy
                                             * sea considerada como que la contraseña ha caducado cuando en realidad todavía no caduca. Por eso se suma 1.
                                             */
                                            calFecProActCla.add(java.util.Calendar.DATE, 1);
                                            calFecHorIngSis.setTime(objParSis.getFechaHoraServidorIngresarSistema());
                                            intResComFec=calFecProActCla.compareTo(calFecHorIngSis);
                                            if (intResComFec>=0)
                                            {
                                                //Usuario y contraseña correctos.
                                                bytRes=3;
                                            }
                                            else
                                            {
                                                //La contraseña caducó. Debe cambiar su contraseña.
                                                bytRes=6;
                                            }
                                            calFecProActCla=null;
                                            calFecHorIngSis=null;
                                            datFecProActCla=null;
                                        }
                                    }
                                    else
                                    {
                                        //Contraseña incorrecta.
                                        bytRes=2;
                                    }
                                }
                            }
                            rst.close();
                        }
                        else
                        {
                            //El valor establecido en tx_tipIdeUtiIngSisEsc es incorrecto.
                            bytRes=8;
                        }
                    }
                    else
                    {
                        //El valor establecido en st_usrIngCuaEqu es incorrecto.
                        bytRes=7;
                    }
                }
                else
                {
                    //Usuario no existe.
                    bytRes=1;
                }
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            bytRes=0;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            bytRes=4;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return bytRes;
    }

}
/*
 * ZafMae15.java
 * Clase generadora deinformes segun los criterios de busqueda que se hayan seleccionado en la interface
 * Created on 10 de febrero de 2005, 13:09
 */

package Maestros.ZafMae15;

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
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafPopupMenu.ZafPopupMenu;

/**
 *
 * @author  kcerezo
 */

public class ZafMae15 extends javax.swing.JInternalFrame {
    Connection con;       //Variable para conexiÃ³n a la Base de Datos
    Statement stm;        //Variable para ejecuciÃ³n de sentencias SQL
    ResultSet rst;        //Variable para manipular registro de la tabla en ejecuciÃ³n
    ResultSet rstCta;     //Variable para manipular registro de la tabla en ejecuciÃ³n
    String strSql;        //Variable de tipo cadena en la cual se almacenarÃ¡ el Query
    String strSqlAux;     //Variable de tipo cadena en la cual se almacenarÃ¡ el Query
    String strMsg;        //Variable de tipo cadena para enviar los mensajes por pantalla
    String strFil;        //EL filtro de la Consulta actual
    String strAux;        //Variable auxiliar de tipo string la cual servira para guardar aquellas cadenas en las cuales me esten enviando algun caracter invalido
    String strStk;
    final String strTit = "Mensaje del Sistema Zafiro"; //Constante del titulo del mensaje
    int intOpp;           //Entero que almacena lo que elige el usuario en el OptionPane
    int intCodEmp;        //Entero que almacena el codigo de empresa en el q estamos trabajando
    char chrFlgCon = 'N'; //Bandera para saber si se ha perdido el foco en un objeto
    char chrFlgMsj = 'N'; //Bandera para saber si ya se presento el mensaje
    char chrFlgLst = 'N'; //Bandera para saber si ya se presento el mensaje al pÃ¨rder el foco el objeto
    boolean blnFlgNum = false; //Variable booleana que servira como bandera para saber si la caja contiene caracteres invÃ¡lidos
    boolean blnFlgCon = false; //Variable booleana que servira como bandera para saber si la caja contiene algo 
    boolean blnCon;       //true: Continua la ejecucion del hilo.
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN = 0;     //Linea
    static final int INT_TBL_DAT_COD_SIS = 1; //Codigo secuencial del Sistema
    static final int INT_TBL_DAT_COD_ALT = 2; //Codigo alterno del item
    static final int INT_TBL_DAT_NOM_ITM = 3; //Nombre del item
    static final int INT_TBL_DAT_STK_ACT = 4; //Stock Actual
    static final int INT_TBL_DAT_PRE_UNI1 = 5; //Precio Unitario1
    static final int INT_TBL_DAT_PRE_UNI2 = 6; //Precio Unitario2
    static final int INT_TBL_DAT_PRE_UNI3 = 7; //Precio Unitario3
    
    JOptionPane oppMsg;   //Objeto de tipo OptionPane para presentar Mensajes
    ZafParSis objParSis;
    ZafUtil objUtl;
    ZafColNumerada objColNum;
    ZafPopupMenu objPopMnu;
    ZafTableModel objTblMod;
    ZafThreadGUI objThrGUI;
    Vector vecDat, vecCab, vecReg, vecEstReg;
    
    /** Creates new form ZafMae15 */
    public ZafMae15(ZafParSis objParSist) {
        initComponents();
        objParSis = objParSist;
        objUtl = new ZafUtil();
        if (!configurarFrm())
            exitForm();
    }
    
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes = true;
        try
        {
            intCodEmp = objParSis.getCodigoEmpresa();
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.2");
            lblTit.setText(strAux);
            txtCodLin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodGrp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodMar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodBod.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            //Configurar el combo "Estado de registro".
            vecEstReg = new Vector();
            vecEstReg.add("");
            vecEstReg.add("A");
            vecEstReg.add("I");
            cboEstReg.addItem("(Todos)");
            cboEstReg.addItem("Activos");
            cboEstReg.addItem("Inactivos");
            cboEstReg.setSelectedIndex(0);            
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(8);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_SIS,"CÃ³digo");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Alterno");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre");
            vecCab.add(INT_TBL_DAT_STK_ACT,"Stock Actual");
            vecCab.add(INT_TBL_DAT_PRE_UNI1,"Precio Unitario 1");
            vecCab.add(INT_TBL_DAT_PRE_UNI2,"Precio Unitario 2");
            vecCab.add(INT_TBL_DAT_PRE_UNI3,"Precio Unitario 3");
            objTblMod = new ZafTableModel();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccion.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum = new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(50);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(80);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(250);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_STK_ACT).setPreferredWidth(75);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PRE_UNI1).setPreferredWidth(100);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PRE_UNI2).setPreferredWidth(100);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PRE_UNI3).setPreferredWidth(100);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_SIS).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_SIS).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_SIS).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_SIS).setResizable(false);
        }
        catch(Exception e)
        {
            blnRes = false;
            objUtl.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funciï¿½n permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intNumTotReg, i;
        boolean blnRes=true;
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener el maximo de stock
                strSql = "";
                strSql = strSql + "SELECT MAX (nd_stkAct) FROM tbm_inv  where co_emp = "+ objParSis.getCodigoEmpresa()  ;
                rst = stm.executeQuery(strSql);
                if (rst.next()) {
                    strStk = rst.getString(1); }
                rst = null;
                //Obtener la condiciÃ³n.
                strAux="";
                if (txtCodDes.getText().length()>0 || txtCodHas.getText().length()>0)
                    strAux+=" AND ((i.tx_codAlt BETWEEN '" + txtCodDes.getText().replaceAll("'", "''") + "' AND '" + txtCodHas.getText().replaceAll("'", "''") + "') OR i.tx_codAlt LIKE '" + txtCodHas.getText().replaceAll("'", "''") + "%')";
                if (txtNomDes.getText().length()>0 || txtNomHas.getText().length()>0)
                    strAux+=" AND ((i.tx_nomItm BETWEEN '" + txtNomDes.getText().replaceAll("'", "''") + "' AND '" + txtNomHas.getText().replaceAll("'", "''") + "') OR i.tx_nomItm LIKE '" + txtNomHas.getText().replaceAll("'", "''") + "%')";
                if (cboEstReg.getSelectedIndex()>0)
                    strAux+=" AND i.st_reg = '" + vecEstReg.get(cboEstReg.getSelectedIndex()) + "'";
                if (chkSer.isSelected())
                    strAux+=" AND i.st_ser = 'S'";
                if (chkStk.isSelected())
                    strAux+=" AND i.nd_stkAct BETWEEN '1' AND '" + strStk + "'";
                if (txtCodLin.getText().length()>0)
                    strAux+=" AND i.co_cla1 = " + txtCodLin.getText().replaceAll("'", "''") + "";
                if (txtCodGrp.getText().length()>0)
                    strAux+=" AND i.co_cla2 = " + txtCodGrp.getText().replaceAll("'", "''") + "";
                if (txtCodMar.getText().length()>0)
                    strAux+=" AND i.co_cla3 = " + txtCodMar.getText().replaceAll("'", "''") + "";
                if (txtCodBod.getText().length()>0)
                    strAux+=" AND b.co_bod = " + txtCodBod.getText().replaceAll("'", "''") + "";
                //Obtener el nÃºmero total de registros.
                strSql="";
                strSql = strSql + "SELECT COUNT(*) ";
                strSql = strSql + "FROM tbm_inv as i, tbm_invBod as b ";
                strSql = strSql + "WHERE i.co_emp = " + intCodEmp + " AND b.co_emp = " + intCodEmp + " ";
                strSql = strSql + "AND i.co_itm = b.co_itm ";
                strSql = strSql + strAux;
                intNumTotReg=objUtl.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSql);
                if (intNumTotReg==-1)
                    return false;
                    tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(250);
                //Armar la sentencia SQL.
                strSql="";
                strSql = strSql + "SELECT i.co_itm, i.tx_codAlt, i.tx_nomItm, i.nd_preVta1, i.nd_preVta2, i.nd_preVta3, i.nd_StkAct";
                strSql = strSql + ", b.co_bod, b.co_itm";
                strSql = strSql + " FROM tbm_inv as i, tbm_invBod as b";
                strSql = strSql + " WHERE i.co_emp = " + intCodEmp + " AND b.co_emp = " + intCodEmp + "";
                strSql = strSql + " AND i.co_itm = b.co_itm";
                strSql = strSql + strAux;
                strSql = strSql + " ORDER BY i.co_itm";
                System.out.println(strSql);
                rst = stm.executeQuery(strSql);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                while (rst.next())
                {
                    int intDec = objParSis.getDecimalesMostrar();
                    double dblStk = objUtl.redondeo(rst.getDouble("nd_StkAct"),intDec);
                    double dblPreUni1 = objUtl.redondeo(rst.getDouble("nd_preVta1"),intDec);
                    double dblPreUni2 = objUtl.redondeo(rst.getDouble("nd_preVta2"),intDec);
                    double dblPreUni3 = objUtl.redondeo(rst.getDouble("nd_preVta3"),intDec);
                    if (blnCon)
                    {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_SIS,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_STK_ACT,new Double(dblStk));
                        vecReg.add(INT_TBL_DAT_PRE_UNI1,new Double(dblPreUni1));
                        vecReg.add(INT_TBL_DAT_PRE_UNI2,new Double(dblPreUni2));
                        vecReg.add(INT_TBL_DAT_PRE_UNI3,new Double(dblPreUni3));
                        
                        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);
                    }
                    else
                    {
                        break;
                    }
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                if (intNumTotReg == tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sÃ³lo se procesaron " + tblDat.getRowCount() + ".");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes = false;
            objUtl.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUtl.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean abrirFrm()
    {
        boolean blnRes = true;
        try
        {
            if (!((tblDat.getSelectedColumn()==-1) || (tblDat.getSelectedRow()==-1)))
            {
                strAux = "Maestros.ZafMae06.ZafMae06";
                objParSis.setNombreMenu("Maestro de Inventario...");
                if (!strAux.equals(""))
                    invocarClase(strAux);
            }       
        }
        catch(Exception e)
        {
            blnRes = false;
            objUtl.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    private boolean invocarClase(String clase)
    {
        boolean blnRes = true;
        try
        {
            //Obtener el constructor de la clase que se va a invocar.
            Class objVen = Class.forName(clase);
            Class objCla[] = new Class[2];
            objCla[0] = objParSis.getClass();
            objCla[1] = new Integer(0).getClass();
            java.lang.reflect.Constructor objCon = objVen.getConstructor(objCla);
            //Inicializar el constructor que se obtuvo.
            Object objObj[] = new Object[2];
            objObj[0] = objParSis;
            objObj[1] = new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_SIS).toString());
            javax.swing.JInternalFrame ifrVen;
            ifrVen = (javax.swing.JInternalFrame)objCon.newInstance(objObj);
            this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
            ifrVen.show();
        }
        catch (ClassNotFoundException e) 
        {
            blnRes = false;
            objUtl.mostrarMsgErr_F1(this, e);
        }
        catch (NoSuchMethodException e) 
        {
            blnRes = false;
            objUtl.mostrarMsgErr_F1(this, e);
        }
        catch (SecurityException e) 
        {
            blnRes = false;
            objUtl.mostrarMsgErr_F1(this, e);
        }
        catch (InstantiationException e) 
        {
            blnRes = false;
            objUtl.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalAccessException e) 
        {
            blnRes = false;
            objUtl.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalArgumentException e) 
        {
            blnRes = false;
            objUtl.mostrarMsgErr_F1(this, e);
        }
        catch (java.lang.reflect.InvocationTargetException e) 
        {
            blnRes = false;
            objUtl.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta clase crea un modelo para el JTable basada en el uso de la clase "AbstractTableModel".
     * Para almacenar la cabecera y los datos del JTable se utiliza la clase "Vector".
     */
    private class ZafTableModel extends javax.swing.table.AbstractTableModel
    {
        private Vector vecCabMod;
        private Vector vecDatMod;
        
        public ZafTableModel()
        {
            vecCabMod = new Vector();
            vecDatMod = new Vector();
        }

        public int getRowCount()
        {
            return vecDatMod.size();
        }
        
        public int getColumnCount()
        {
            return vecCabMod.size();
        }
        
        public Object getValueAt(int row, int col)
        {
            Vector vecAux = (Vector)vecDatMod.elementAt(row);
            return vecAux.elementAt(col);
        }
        
        public String getColumnName(int col)
        {
            return vecCabMod.elementAt(col).toString();
        }

        public void setHeader(Vector cabecera)
        {
            vecCabMod = cabecera;
        }

        public void setData(Vector datos)
        {
            vecDatMod = datos;
            fireTableDataChanged();
        }

        public void setValues(Vector cabecera, Vector datos)
        {
            vecCabMod = cabecera;
            vecDatMod = datos;
            fireTableDataChanged();
        }
        
        public boolean isCellEditable(int row, int col)
        {
            return false;
        }
    }

    
    /**
     * Esta clase crea un hilo que permite manipular la interface grÃ¡fica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que estï¿½ ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podrï¿½a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estarï¿½a informado en todo
     * momento de lo que ocurre. Si se desea hacer ï¿½sto es necesario utilizar ï¿½sta clase
     * ya que si no sï¿½lo se apreciarï¿½a los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sï¿½lo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI = null;
        }
    }
    
    
    private boolean flagNum(boolean blnFlgNum)
    {
        return blnFlgNum; 
    }
    
    
    private boolean flagCon(boolean blnFlgCon)
    {
        return blnFlgCon; 
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
            tabFrm.setSelectedIndex(intTab);
            jtfTxt.selectAll();
            jtfTxt.requestFocus();
        }
        else {
            tabFrm.setSelectedIndex(intTab);
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
    private void initComponents() {//GEN-BEGIN:initComponents
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        lblEstReg = new javax.swing.JLabel();
        cboEstReg = new javax.swing.JComboBox();
        optFil = new javax.swing.JRadioButton();
        panNom = new javax.swing.JPanel();
        lblNomDes = new javax.swing.JLabel();
        txtNomDes = new javax.swing.JTextField();
        lblNomHas = new javax.swing.JLabel();
        txtNomHas = new javax.swing.JTextField();
        panCod = new javax.swing.JPanel();
        lblCodDes = new javax.swing.JLabel();
        txtCodDes = new javax.swing.JTextField();
        lblCodHas = new javax.swing.JLabel();
        txtCodHas = new javax.swing.JTextField();
        lblLin = new javax.swing.JLabel();
        txtCodLin = new javax.swing.JTextField();
        txtLin = new javax.swing.JTextField();
        butLin = new javax.swing.JButton();
        lblGrp = new javax.swing.JLabel();
        txtCodGrp = new javax.swing.JTextField();
        txtGrp = new javax.swing.JTextField();
        butGrp = new javax.swing.JButton();
        butMar = new javax.swing.JButton();
        txtMar = new javax.swing.JTextField();
        txtCodMar = new javax.swing.JTextField();
        lblMar = new javax.swing.JLabel();
        chkSer = new javax.swing.JCheckBox();
        chkStk = new javax.swing.JCheckBox();
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtBod = new javax.swing.JTextField();
        butBod = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panEst = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("T\u00edtulo de la Ventana");
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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("T\u00edtulo de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setFont(new java.awt.Font("SansSerif", 1, 12));
        panFil.setLayout(null);

        panFil.setFont(new java.awt.Font("SansSerif", 1, 12));
        optTod.setFont(new java.awt.Font("SansSerif", 1, 11));
        optTod.setSelected(true);
        optTod.setText("Todos los Items");
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });

        panFil.add(optTod);
        optTod.setBounds(4, 8, 400, 20);

        lblEstReg.setFont(new java.awt.Font("SansSerif", 1, 11));
        lblEstReg.setText("Estado del registro:");
        lblEstReg.setToolTipText("Estado del registro:");
        panFil.add(lblEstReg);
        lblEstReg.setBounds(20, 244, 172, 20);

        cboEstReg.setFont(new java.awt.Font("SansSerif", 0, 11));
        cboEstReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstRegActionPerformed(evt);
            }
        });

        panFil.add(cboEstReg);
        cboEstReg.setBounds(136, 244, 180, 20);

        optFil.setFont(new java.awt.Font("SansSerif", 1, 11));
        optFil.setText("S\u00f3lo los Items que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });

        panFil.add(optFil);
        optFil.setBounds(4, 28, 400, 20);

        panNom.setLayout(null);

        panNom.setBorder(new javax.swing.border.TitledBorder(null, "Nombre del Item", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 11)));
        panNom.setFont(new java.awt.Font("Dialog", 1, 12));
        lblNomDes.setFont(new java.awt.Font("SansSerif", 1, 11));
        lblNomDes.setText("Desde:");
        panNom.add(lblNomDes);
        lblNomDes.setBounds(12, 16, 44, 20);

        txtNomDes.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtNomDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDesFocusLost(evt);
            }
        });

        panNom.add(txtNomDes);
        txtNomDes.setBounds(56, 16, 220, 20);

        lblNomHas.setFont(new java.awt.Font("SansSerif", 1, 11));
        lblNomHas.setText("Hasta:");
        panNom.add(lblNomHas);
        lblNomHas.setBounds(284, 16, 44, 20);

        txtNomHas.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtNomHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomHasFocusLost(evt);
            }
        });

        panNom.add(txtNomHas);
        txtNomHas.setBounds(328, 16, 220, 20);

        panFil.add(panNom);
        panNom.setBounds(12, 96, 560, 44);

        panCod.setLayout(null);

        panCod.setBorder(new javax.swing.border.TitledBorder(null, "C\u00f3digo Alterno", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 11)));
        panCod.setFont(new java.awt.Font("Dialog", 1, 12));
        lblCodDes.setFont(new java.awt.Font("SansSerif", 1, 11));
        lblCodDes.setText("Desde:");
        panCod.add(lblCodDes);
        lblCodDes.setBounds(12, 16, 44, 20);

        txtCodDes.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtCodDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDesFocusLost(evt);
            }
        });

        panCod.add(txtCodDes);
        txtCodDes.setBounds(56, 16, 220, 20);

        lblCodHas.setFont(new java.awt.Font("SansSerif", 1, 11));
        lblCodHas.setText("Hasta:");
        panCod.add(lblCodHas);
        lblCodHas.setBounds(284, 16, 44, 20);

        txtCodHas.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtCodHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodHasFocusLost(evt);
            }
        });

        panCod.add(txtCodHas);
        txtCodHas.setBounds(328, 16, 220, 20);

        panFil.add(panCod);
        panCod.setBounds(12, 52, 560, 44);

        lblLin.setFont(new java.awt.Font("SansSerif", 1, 11));
        lblLin.setText("Linea:");
        panFil.add(lblLin);
        lblLin.setBounds(20, 184, 136, 14);

        txtCodLin.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtCodLin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLinActionPerformed(evt);
            }
        });
        txtCodLin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLinFocusLost(evt);
            }
        });

        panFil.add(txtCodLin);
        txtCodLin.setBounds(136, 184, 40, 20);

        txtLin.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtLin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLinActionPerformed(evt);
            }
        });
        txtLin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLinFocusLost(evt);
            }
        });

        panFil.add(txtLin);
        txtLin.setBounds(176, 184, 144, 20);

        butLin.setFont(new java.awt.Font("SansSerif", 1, 12));
        butLin.setText("...");
        butLin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLinActionPerformed(evt);
            }
        });
        butLin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                butLinFocusLost(evt);
            }
        });

        panFil.add(butLin);
        butLin.setBounds(320, 184, 24, 20);

        lblGrp.setFont(new java.awt.Font("SansSerif", 1, 11));
        lblGrp.setText("Grupo:");
        panFil.add(lblGrp);
        lblGrp.setBounds(20, 204, 136, 14);

        txtCodGrp.setFont(new java.awt.Font("SansSerif", 0, 11));
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

        panFil.add(txtCodGrp);
        txtCodGrp.setBounds(136, 204, 40, 20);

        txtGrp.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGrpActionPerformed(evt);
            }
        });
        txtGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGrpFocusLost(evt);
            }
        });

        panFil.add(txtGrp);
        txtGrp.setBounds(176, 204, 144, 20);

        butGrp.setFont(new java.awt.Font("SansSerif", 1, 12));
        butGrp.setText("...");
        butGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGrpActionPerformed(evt);
            }
        });
        butGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                butGrpFocusLost(evt);
            }
        });

        panFil.add(butGrp);
        butGrp.setBounds(320, 204, 24, 20);

        butMar.setFont(new java.awt.Font("SansSerif", 1, 12));
        butMar.setText("...");
        butMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMarActionPerformed(evt);
            }
        });
        butMar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                butMarFocusLost(evt);
            }
        });

        panFil.add(butMar);
        butMar.setBounds(320, 224, 24, 20);

        txtMar.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMarActionPerformed(evt);
            }
        });
        txtMar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMarFocusLost(evt);
            }
        });

        panFil.add(txtMar);
        txtMar.setBounds(176, 224, 144, 20);

        txtCodMar.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtCodMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodMarActionPerformed(evt);
            }
        });
        txtCodMar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodMarFocusLost(evt);
            }
        });

        panFil.add(txtCodMar);
        txtCodMar.setBounds(136, 224, 40, 20);

        lblMar.setFont(new java.awt.Font("SansSerif", 1, 11));
        lblMar.setText("Marca:");
        panFil.add(lblMar);
        lblMar.setBounds(20, 224, 140, 14);

        chkSer.setFont(new java.awt.Font("SansSerif", 1, 11));
        chkSer.setText("S\u00f3lo items de servicio");
        chkSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSerActionPerformed(evt);
            }
        });

        panFil.add(chkSer);
        chkSer.setBounds(16, 140, 180, 22);

        chkStk.setFont(new java.awt.Font("SansSerif", 1, 11));
        chkStk.setText("S\u00f3lo items con existencia");
        chkStk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkStkActionPerformed(evt);
            }
        });

        panFil.add(chkStk);
        chkStk.setBounds(16, 160, 196, 22);

        lblBod.setFont(new java.awt.Font("SansSerif", 1, 11));
        lblBod.setText("Bodega:");
        panFil.add(lblBod);
        lblBod.setBounds(308, 144, 88, 14);

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

        panFil.add(txtCodBod);
        txtCodBod.setBounds(360, 144, 40, 20);

        txtBod.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBodActionPerformed(evt);
            }
        });
        txtBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBodFocusLost(evt);
            }
        });

        panFil.add(txtBod);
        txtBod.setBounds(400, 144, 144, 20);

        butBod.setFont(new java.awt.Font("SansSerif", 1, 12));
        butBod.setText("...");
        butBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodActionPerformed(evt);
            }
        });

        panFil.add(butBod);
        butBod.setBounds(544, 144, 24, 20);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDat.setToolTipText("Doble click o ENTER para abrir la opci\u00f3n seleccionada.");
        tblDat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDatKeyPressed(evt);
            }
        });
        tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMouseClicked(evt);
            }
        });

        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });

        panBot.add(butCon);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });

        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setLayout(new java.awt.BorderLayout());

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panEst.setLayout(new java.awt.BorderLayout(2, 2));

        panEst.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panEst.setMinimumSize(new java.awt.Dimension(24, 26));
        panEst.setPreferredSize(new java.awt.Dimension(200, 15));
        pgrSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        panEst.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panEst, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-600)/2, (screenSize.height-400)/2, 600, 400);
    }//GEN-END:initComponents

    private void txtBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBodFocusLost
    if (chrFlgLst == 'N') {
            if (!txtBod.getText().equals("")) {
                if (txtBod.getText().length()>80 && chrFlgLst == 'N'){ 
                    strMsg="El campo <<Bodega>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                    txtBod.setText("");
                    tabFrm.setSelectedIndex(0);
                    txtBod.requestFocus();
                    chrFlgMsj = 'S';
                    chrFlgLst = 'S';
                } 
            }
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
        }
    
    if (txtBod.getText().equals("")) {
        txtCodBod.setText("");
    }
    }//GEN-LAST:event_txtBodFocusLost

    private void txtBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBodActionPerformed
        Librerias.ZafConsulta.ZafConsulta objFndBod = 
        new Librerias.ZafConsulta.ZafConsulta(new javax.swing.JFrame(),
        "CÃ³digo,Nombre,DirecciÃ³n,Estado","co_bod,tx_nom,tx_dir,st_reg",
        "SELECT co_bod,tx_nom,tx_dir,st_reg FROM tbm_bod WHERE co_emp = "+ intCodEmp +" AND tx_nom = '"+ txtBod.getText().replaceAll("'", "''") +"'", 
        txtBod.getText(), 
        objParSis.getStringConexion(), 
        objParSis.getUsuarioBaseDatos(), 
        objParSis.getClaveBaseDatos()
        );

        objFndBod.setTitle("Listado de Bodegas");
        objFndBod.setSelectedCamBus(2);

        if(txtBod.getText().equals(""))
            objFndBod.show();
        else
            if(!objFndBod.buscar("tx_nom = '" + txtBod.getText().replaceAll("'", "''") + "'")) 
                objFndBod.show();

        if(!objFndBod.GetCamSel(2).equals("")){
            if (objFndBod.GetCamSel(4).equals("I")){
                strMsg="Esta Bodega estÃ¡ en estado <<Inactivo>>.\nVerifique otra Bodega y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);}
            else {
                txtCodBod.setText(objFndBod.GetCamSel(1)); 
                try{ 
                    con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    if(con != null){
                        stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                        strSql = "";                        
                        strSql = "SELECT tx_nom FROM tbm_bod WHERE co_emp = "+ objParSis.getCodigoEmpresa() +" and co_bod = " + txtCodBod.getText().replaceAll("'", "''") + " and co_emp = "+ intCodEmp +"";
                        rst = stm.executeQuery(strSql);
                        if (rst.next()){
                            txtBod.setText(((rst.getString("tx_nom")==null)?"":rst.getString("tx_nom")));
                            optFil.setSelected(true);
                            optTod.setSelected(false);
                        }
                        else{
                            lblMsgSis.setText("Se encontraron 0 registros...");
                            strMsg="No se ha encontrado ningÃºn registro que cumpla el criterio de bÃºsqueda especÃ­ficado.";
                            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);  
                            rst = null;
                        }
                    }
                }
                catch(SQLException Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                catch(Exception Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }
            }
        }
    }//GEN-LAST:event_txtBodActionPerformed

    private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
        if (chrFlgLst == 'N') { 
            if (!txtCodBod.getText().equals("")) {
                if (txtCodBod.getText().length()>5 && chrFlgLst == 'N'){ 
                    strMsg="El campo <<CÃ³digo de Bodega>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                    txtCodBod.setText("");
                    tabFrm.setSelectedIndex(0);
                    txtBod.setText("");
                    txtCodBod.requestFocus();
                    chrFlgMsj = 'S';
                    chrFlgLst = 'S';
                } 
            }

            flagNum(blnFlgNum);
            flagCon(blnFlgCon);
            if (txtCodBod.getText().length()>0 && blnFlgNum == false && blnFlgCon == false && chrFlgMsj == 'N') {
                String numero = txtCodBod.getText();
                objUtl.isNumero(numero);
                if (objUtl.isNumero(numero) == false) {
                    strMsg="El campo <<CÃ³digo de Bodega>> sÃ³lo acepta valores numÃ©ricos.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                    txtCodBod.setText("");
                    tabFrm.setSelectedIndex(0);
                    txtBod.setText("");
                    txtCodBod.requestFocus();
                    chrFlgMsj = 'S';
                }
            }
            blnFlgNum = false; 
            blnFlgCon = false;

            if (!txtCodBod.getText().equals("") && chrFlgCon == 'N' && chrFlgMsj == 'N') {
                try{ 
                    con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                        if(con != null) {
                            stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                            strSql="";
                            strSql = strSql + "SELECT co_bod, co_emp, tx_nom";
                            strSql = strSql + " FROM tbm_bod";
                            strSql = strSql + " WHERE co_emp = "+  intCodEmp +" and co_bod = " + txtCodBod.getText().replaceAll("'", "''") + "";
                            strSql = strSql + " and st_reg = 'A'";

                            rst = stm.executeQuery(strSql);
                            System.out.println(strSql);

                            if (!rst.next()){
                                strMsg="El campo <<CÃ³digo de Bodega>> no existe.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                                txtCodBod.setText("");
                                tabFrm.setSelectedIndex(0);
                                txtBod.setText("");
                                txtCodBod.requestFocus();
                           }
                            else {
                                txtCodBod.setText(((rst.getString("co_bod")==null)?"":rst.getString("co_bod")));
                                txtBod.setText(((rst.getString("tx_nom")==null)?"":rst.getString("tx_nom")));
                                optFil.setSelected(true);
                                optTod.setSelected(false);
                            }
                      }
                }
                catch(SQLException Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                catch(Exception Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }  
            }
        chrFlgMsj = 'N';
        chrFlgLst = 'N';
        }
        
        if (txtCodBod.getText().equals("")) {
            txtBod.setText("");
        }
    }//GEN-LAST:event_txtCodBodFocusLost

    private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
        if (txtCodBod.getText().length()>0) {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
        
        if (chrFlgLst == 'N') {
            if (!txtCodBod.getText().equals("")) {
                if (txtCodBod.getText().length()>5 && chrFlgLst == 'N'){ 
                    chrFlgMsj = 'S';
                    chrFlgLst = 'S';
                    valTxt(txtCodBod, txtBod, "CÃ³digo de Bodega", "", 2 , "el", 0);
                }
            }

            if (txtCodBod.getText().length()>0 && chrFlgMsj == 'N' ) {
                String numero = txtCodBod.getText();
                objUtl.isNumero(numero);
                    if (objUtl.isNumero(numero) == false) {
                        blnFlgNum = true;
                        flagNum(blnFlgNum);
                        txtCodBod.setText("");
                        txtBod.setText("");
                        strMsg = "El campo <<CÃ³digo de Bodega>> sÃ³lo acepta caracteres numÃ©ricos.\nÂ¿Desea corregir el <<CÃ³digo de LÃ­nea>> que ha ingresado?\nSi selecciona NO el sistema borrarÃ¡ los datos antes de abandonar este campo.";
                        intOpp = oppMsg.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if (intOpp == JOptionPane.YES_OPTION) {
                            tabFrm.setSelectedIndex(0);
                            txtCodBod.setText(numero);
                            txtCodBod.requestFocus(); 
                            txtCodBod.selectAll();
                        }
                        else {
                            tabFrm.setSelectedIndex(0);
                            txtCodBod.setText("");
                            txtBod.setText("");
                            txtCodBod.requestFocus(); 
                        }
                    }
                    else{
                        Librerias.ZafConsulta.ZafConsulta objFndCodBod = 
                        new Librerias.ZafConsulta.ZafConsulta(new javax.swing.JFrame(),
                        "CÃ³digo,Nombre,DirecciÃ³n,Estado","co_bod,tx_nom,tx_dir,st_reg",
                        "SELECT co_bod,tx_nom,tx_dir,st_reg FROM tbm_bod WHERE co_emp = "+ intCodEmp +" AND co_bod = "+ txtCodBod.getText().replaceAll("'", "''") +"", 
                        txtCodBod.getText(), 
                        objParSis.getStringConexion(),  
                        objParSis.getUsuarioBaseDatos(), 
                        objParSis.getClaveBaseDatos()
                        );

                        objFndCodBod.setTitle("Listado de Bodegas");
                        objFndCodBod.setSelectedCamBus(0);

                        if(txtCodBod.getText().equals(""))
                            objFndCodBod.show();
                        else
                            if(!objFndCodBod.buscar("co_bod = " + txtCodBod.getText().replaceAll("'", "''") + "")) {
                                chrFlgCon = 'S';
                                objFndCodBod.show(); }

                        if(!objFndCodBod.GetCamSel(1).equals("")){
                            if (objFndCodBod.GetCamSel(4).equals("I")){
                                strMsg="Esta Bodega estÃ¡ en estado <<Inactivo>>.\nVerifique otra Bodega y vuelva a intentarlo.";
                                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);}
                            else { 
                                txtCodBod.setText(objFndCodBod.GetCamSel(1)); 
                                blnFlgCon = true;
                                flagCon(blnFlgCon);
                                try{ 
                                    con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                                    if(con != null){
                                        stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                                        strSql = "";                        
                                        strSql = "SELECT tx_nom FROM tbm_bod WHERE co_bod = " + txtCodBod.getText().replaceAll("'", "''") + " AND co_emp = "+ intCodEmp +"";
                                        rst = stm.executeQuery(strSql);
                                        if (rst.next()){
                                            txtBod.setText(((rst.getString("tx_nom")==null)?"":rst.getString("tx_nom")));
                                            optFil.setSelected(true);
                                            optTod.setSelected(false);
                                        }
                                        else{
                                            lblMsgSis.setText("Se encontraron 0 registros...");
                                            strMsg="No se ha encontrado ningÃºn registro que cumpla el criterio de bÃºsqueda especÃ­ficado.";
                                            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);  
                                            rst = null; 
                                        }
                                    }
                                }
                                catch(SQLException Evt)
                                {
                                    objUtl.mostrarMsgErr_F1(this, Evt);
                                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                                catch(Exception Evt)
                                {
                                    objUtl.mostrarMsgErr_F1(this, Evt);
                                }
                            }
                        }
                        chrFlgCon = 'N';
                        blnFlgNum = false;   
                        }
                }
            chrFlgLst = 'N';
            chrFlgMsj = 'N';
        }
    }//GEN-LAST:event_txtCodBodActionPerformed

    private void butBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodActionPerformed
        Librerias.ZafConsulta.ZafConsulta objFndButBod = 
        new Librerias.ZafConsulta.ZafConsulta(new javax.swing.JFrame(),
        "CÃ³digo,Nombre,DirecciÃ³n,Estado","co_bod,tx_nom,tx_dir,st_reg",
        "SELECT co_bod,tx_nom,tx_dir,st_reg FROM tbm_bod WHERE co_emp = "+ intCodEmp +"", 
        txtCodBod.getText(), 
        objParSis.getStringConexion(),  
        objParSis.getUsuarioBaseDatos(), 
        objParSis.getClaveBaseDatos()
        );
        
        objFndButBod.setTitle("Listado de Bodegas");
        objFndButBod.show();
        
        if(!objFndButBod.GetCamSel(1).equals("")){
            if (objFndButBod.GetCamSel(4).equals("I")){
                strMsg="Esta Bodega estÃ¡ en estado <<Inactivo>>.\nVerifique otra Bodega y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);}
            else {
                txtCodBod.setText(objFndButBod.GetCamSel(1));
                try{ 
                    con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    if(con != null){
                        stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                        strSql = "";                        
                        strSql = "SELECT tx_nom FROM tbm_bod WHERE co_emp = "+ objParSis.getCodigoEmpresa() +" and co_bod = " + txtCodBod.getText().replaceAll("'", "''") +"";
                        rst = stm.executeQuery(strSql);
                        if (rst.next()){
                            txtBod.setText(((rst.getString("tx_nom")==null)?"":rst.getString("tx_nom")));
                            optFil.setSelected(true);
                            optTod.setSelected(false);
                        }
                        else{
                            lblMsgSis.setText("Se encontraron 0 registros...");
                            strMsg = "No se ha encontrado ningÃºn registro que cumpla el criterio de bÃºsqueda especÃ­ficado.";
                            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);  
                            rst = null;
                        }
                    }
                }
                catch(SQLException Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                catch(Exception Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }
            }
        }
    }//GEN-LAST:event_butBodActionPerformed

    private void butMarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_butMarFocusLost
        if (txtCodMar.getText().length()>0 && txtMar.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_butMarFocusLost

    private void butGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_butGrpFocusLost
	if (txtCodGrp.getText().length()>0 && txtGrp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_butGrpFocusLost

    private void butLinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_butLinFocusLost
        if (txtCodLin.getText().length()>0 && txtLin.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_butLinFocusLost

    private void butMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMarActionPerformed
        Librerias.ZafConsulta.ZafConsulta objFndButMar = 
        new Librerias.ZafConsulta.ZafConsulta(new javax.swing.JFrame(),
        "CÃ³digo,Desc Corta,Desc Larga,Estado","co_reg,tx_desCor,tx_desLar,st_reg",
        "SELECT co_reg,tx_desCor,tx_desLar,st_reg FROM tbm_var WHERE co_grp = 4", 
        txtCodMar.getText(), 
        objParSis.getStringConexion(),  
        objParSis.getUsuarioBaseDatos(), 
        objParSis.getClaveBaseDatos()
        );
        
        objFndButMar.setTitle("Listado de Marcas");
        objFndButMar.show();
        
        if(!objFndButMar.GetCamSel(3).equals("")){
            if (objFndButMar.GetCamSel(4).equals("I")){
                strMsg="Esta Marca estÃ¡ en estado <<Inactivo>>.\nVerifique otra Marca y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);}
            else {
                txtCodMar.setText(objFndButMar.GetCamSel(1));
                try{ 
                    con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    if(con != null){
                        stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                        strSql = "";                        
                        strSql = "SELECT tx_desLar FROM tbm_var WHERE co_reg = " + txtCodMar.getText().replaceAll("'", "''") + " and co_grp = 4";
                        rst = stm.executeQuery(strSql);
                        if (rst.next()){
                            txtMar.setText(((rst.getString("tx_desLar")==null)?"":rst.getString("tx_desLar")));
                            optFil.setSelected(true);
                            optTod.setSelected(false);
                        }
                        else{
                            lblMsgSis.setText("Se encontraron 0 registros...");
                            strMsg = "No se ha encontrado ningÃºn registro que cumpla el criterio de bÃºsqueda especÃ­ficado.";
                            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);  
                            rst = null;
                        }
                    }
                }
                catch(SQLException Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                catch(Exception Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }
            }
        }
    }//GEN-LAST:event_butMarActionPerformed

    private void butGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGrpActionPerformed
        Librerias.ZafConsulta.ZafConsulta objFndButGrp = 
        new Librerias.ZafConsulta.ZafConsulta(new javax.swing.JFrame(),
        "CÃ³digo,Desc Corta,Desc Larga,Estado","co_reg,tx_desCor,tx_desLar,st_reg",
        "SELECT co_reg,tx_desCor,tx_desLar,st_reg FROM tbm_var WHERE co_grp = 3", 
        txtCodGrp.getText(), 
        objParSis.getStringConexion(),  
        objParSis.getUsuarioBaseDatos(), 
        objParSis.getClaveBaseDatos()
        );
        
        objFndButGrp.setTitle("Listado de Grupos");
        objFndButGrp.show();
        
        if(!objFndButGrp.GetCamSel(3).equals("")){
            if (objFndButGrp.GetCamSel(4).equals("I")){
                strMsg="Este Grupo estÃ¡ en estado <<Inactivo>>.\nVerifique otro Grupo y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);}
            else {
                txtCodGrp.setText(objFndButGrp.GetCamSel(1)); 
                try{ 
                    con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    if(con != null){
                        stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                        strSql = "";                        
                        strSql = "SELECT tx_desLar FROM tbm_var WHERE co_reg = " + txtCodGrp.getText().replaceAll("'", "''") + " and co_grp = 3";
                        rst = stm.executeQuery(strSql);
                        if (rst.next()){
                            txtGrp.setText(((rst.getString("tx_desLar")==null)?"":rst.getString("tx_desLar")));
                            optFil.setSelected(true);
                            optTod.setSelected(false);
                        }
                        else{
                            lblMsgSis.setText("Se encontraron 0 registros...");
                            strMsg = "No se ha encontrado ningÃºn registro que cumpla el criterio de bÃºsqueda especÃ­ficado.";
                            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);  
                            rst = null;
                        }
                    }
                }
                catch(SQLException Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                catch(Exception Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }
            }
        }
    }//GEN-LAST:event_butGrpActionPerformed

    private void butLinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLinActionPerformed
        Librerias.ZafConsulta.ZafConsulta objFndButLin = 
        new Librerias.ZafConsulta.ZafConsulta(new javax.swing.JFrame(),
        "CÃ³digo,Desc Corta,Desc Larga,Estado","co_reg,tx_desCor,tx_desLar,st_reg",
        "SELECT co_reg,tx_desCor,tx_desLar,st_reg FROM tbm_var WHERE co_grp = 2", 
        txtCodLin.getText(), 
        objParSis.getStringConexion(), 
        objParSis.getUsuarioBaseDatos(), 
        objParSis.getClaveBaseDatos()
        );
        
        objFndButLin.setTitle("Listado de LÃ­neas");
        objFndButLin.show();
        
        if(!objFndButLin.GetCamSel(3).equals("")){
            if (objFndButLin.GetCamSel(4).equals("I")){
                strMsg = "Esta LÃ­nea estÃ¡ en estado <<Inactivo>>.\nVerifique otra LÃ­nea y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);}
            else {
                txtCodLin.setText(objFndButLin.GetCamSel(1)); 
                try{ 
                    con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    if(con != null){
                        stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                        strSql = "";                        
                        strSql = "SELECT tx_desLar FROM tbm_var WHERE co_reg = " + txtCodLin.getText().replaceAll("'", "''") + " and co_grp = 2";
                        rst = stm.executeQuery(strSql);
                        if (rst.next()){
                            txtLin.setText(((rst.getString("tx_desLar")==null)?"":rst.getString("tx_desLar")));
                            optFil.setSelected(true);
                            optTod.setSelected(false);
                        }
                        else{
                            lblMsgSis.setText("Se encontraron 0 registros...");
                            strMsg = "No se ha encontrado ningÃºn registro que cumpla el criterio de bÃºsqueda especÃ­ficado.";
                            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);  
                            rst = null;
                        }
                    }
                }
                catch(SQLException Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                catch(Exception Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }
            }
        }
    }//GEN-LAST:event_butLinActionPerformed

    private void txtMarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMarFocusLost
        if (chrFlgLst == 'N') {
            if (!txtMar.getText().equals("")) {
                if (txtMar.getText().length()>30 && chrFlgLst == 'N'){ 
                    strMsg = "El campo <<DescripciÃ³n de Marca>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                    txtMar.setText("");
                    tabFrm.setSelectedIndex(0);
                    txtMar.requestFocus();
                    chrFlgMsj = 'S';
                    chrFlgLst = 'S';
                } 
            }
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
        } 
        
        if (txtMar.getText().equals("")) {
            txtCodMar.setText("");
        }
    }//GEN-LAST:event_txtMarFocusLost

    private void txtMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMarActionPerformed
        Librerias.ZafConsulta.ZafConsulta objFndMar = 
        new Librerias.ZafConsulta.ZafConsulta(new javax.swing.JFrame(),
        "CÃ³digo,Desc Corta,Desc Larga,Estado","co_reg,tx_desCor,tx_desLar,st_reg",
        "SELECT co_reg,tx_desCor,tx_desLar,st_reg FROM tbm_var WHERE co_grp = 4 AND tx_desLar = '"+ txtMar.getText().replaceAll("'", "''") +"'", 
        txtMar.getText(), 
        objParSis.getStringConexion(), 
        objParSis.getUsuarioBaseDatos(), 
        objParSis.getClaveBaseDatos()
        );

        objFndMar.setTitle("Listado de Marcas");
        objFndMar.setSelectedCamBus(2);

        if(txtMar.getText().equals(""))
            objFndMar.show();
        else
            if(!objFndMar.buscar("tx_desLar = '" + txtMar.getText().replaceAll("'", "''") + "'"))
                objFndMar.show();

        if(!objFndMar.GetCamSel(3).equals("")){
            if (objFndMar.GetCamSel(4).equals("I")){
                strMsg = "Esta Marca estÃ¡ en estado <<Inactivo>>.\nVerifique otra Marca y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);}
            else {
                txtCodMar.setText(objFndMar.GetCamSel(1)); 
                try{ 
                    con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    if(con != null){
                        stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                        strSql = "";                        
                        strSql = "SELECT tx_desLar FROM tbm_var WHERE co_reg = " + txtCodMar.getText().replaceAll("'", "''") + " and co_grp = 4";
                        rst = stm.executeQuery(strSql);
                        if (rst.next()){
                            txtMar.setText(((rst.getString("tx_desLar")==null)?"":rst.getString("tx_desLar")));
                            optFil.setSelected(true);
                            optTod.setSelected(false);
                        }
                        else{
                            lblMsgSis.setText("Se encontraron 0 registros...");
                            strMsg="No se ha encontrado ningÃºn registro que cumpla el criterio de bÃºsqueda especÃ­ficado.";
                            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);  
                            rst = null; 
                        }
                    }
                }
                catch(SQLException Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                catch(Exception Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }
            }
        }
    }//GEN-LAST:event_txtMarActionPerformed

    private void txtGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGrpFocusLost
        if (chrFlgLst == 'N') {
            if (!txtGrp.getText().equals("")) {
                if (txtGrp.getText().length()>30 && chrFlgLst == 'N'){ 
                    strMsg = "El campo <<DescripciÃ³n de Grupo>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                    txtGrp.setText("");
                    tabFrm.setSelectedIndex(0);
                    txtGrp.requestFocus();
                    chrFlgMsj = 'S';
                    chrFlgLst = 'S';
                } 
            }
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
        }
        
        if (txtGrp.getText().equals("")) {
            txtCodGrp.setText("");
        }
    }//GEN-LAST:event_txtGrpFocusLost

    private void txtGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGrpActionPerformed
        Librerias.ZafConsulta.ZafConsulta objFndGrp = 
        new Librerias.ZafConsulta.ZafConsulta(new javax.swing.JFrame(),
        "CÃ³digo,Desc Corta,Desc Larga,Estado","co_reg,tx_desCor,tx_desLar,st_reg",
        "SELECT co_reg,tx_desCor,tx_desLar,st_reg FROM tbm_var WHERE co_grp = 3 AND tx_desLar = '"+ txtGrp.getText().replaceAll("'", "''") +"'", 
        txtGrp.getText(), 
        objParSis.getStringConexion(),  
        objParSis.getUsuarioBaseDatos(),  
        objParSis.getClaveBaseDatos()
        );

        objFndGrp.setTitle("Listado de Grupos");
        objFndGrp.setSelectedCamBus(2);

        if(txtGrp.getText().equals(""))
            objFndGrp.show();
        else
            if(!objFndGrp.buscar("tx_desLar = '" + txtGrp.getText().replaceAll("'", "''") + "'"))
                objFndGrp.show();

        if(!objFndGrp.GetCamSel(3).equals("")){
            if (objFndGrp.GetCamSel(4).equals("I")){
                strMsg = "Este Grupo estÃ¡ en estado <<Inactivo>>.\nVerifique otro Grupo y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);}
            else {
                txtCodGrp.setText(objFndGrp.GetCamSel(1)); 
                try{ 
                    con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    if(con != null){
                        stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                        strSql = "";                        
                        strSql = "SELECT tx_desLar FROM tbm_var WHERE co_reg = " + txtCodGrp.getText().replaceAll("'", "''") + " and co_grp = 3";
                        rst = stm.executeQuery(strSql);
                        if (rst.next()){
                            txtGrp.setText(((rst.getString("tx_desLar")==null)?"":rst.getString("tx_desLar")));
                            optFil.setSelected(true);
                            optTod.setSelected(false);
                        }
                        else{
                            lblMsgSis.setText("Se encontraron 0 registros...");
                            strMsg="No se ha encontrado ningÃºn registro que cumpla el criterio de bÃºsqueda especÃ­ficado.";
                            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);  
                            rst = null;
                        }
                    }
                }
                catch(SQLException Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                catch(Exception Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }
            }
        }
    }//GEN-LAST:event_txtGrpActionPerformed

    private void txtLinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLinFocusLost
    if (chrFlgLst == 'N') {
            if (!txtLin.getText().equals("")) {
                if (txtLin.getText().length()>30 && chrFlgLst == 'N'){ 
                    strMsg="El campo <<DescripciÃ³n de LÃ­nea>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                    txtLin.setText("");
                    tabFrm.setSelectedIndex(0);
                    txtLin.requestFocus();
                    chrFlgMsj = 'S';
                    chrFlgLst = 'S';
                } 
            }
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
        }
    
    if (txtLin.getText().equals("")) {
        txtCodLin.setText("");
    }
    }//GEN-LAST:event_txtLinFocusLost

    private void txtLinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLinActionPerformed
        Librerias.ZafConsulta.ZafConsulta objFndLin = 
        new Librerias.ZafConsulta.ZafConsulta(new javax.swing.JFrame(),
        "CÃ³digo,Desc Corta,Desc Larga,Estado","co_reg,tx_desCor,tx_desLar,st_reg",
        "SELECT co_reg,tx_desCor,tx_desLar,st_reg FROM tbm_var WHERE co_grp = 2 AND tx_desLar = '"+ txtLin.getText().replaceAll("'", "''") +"'", 
        txtLin.getText(), 
        objParSis.getStringConexion(), 
        objParSis.getUsuarioBaseDatos(), 
        objParSis.getClaveBaseDatos()
        );

        objFndLin.setTitle("Listado de LÃ­neas");
        objFndLin.setSelectedCamBus(2);

        if(txtLin.getText().equals(""))
            objFndLin.show();
        else
            if(!objFndLin.buscar("tx_desLar = '" + txtLin.getText().replaceAll("'", "''") + "'")) 
                objFndLin.show();

        if(!objFndLin.GetCamSel(3).equals("")){
            if (objFndLin.GetCamSel(4).equals("I")){
                strMsg="Esta LÃ­nea estÃ¡ en estado <<Inactivo>>.\nVerifique otra LÃ­nea y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);}
            else {
                txtCodLin.setText(objFndLin.GetCamSel(1)); 
                try{ 
                    con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    if(con != null){
                        stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                        strSql = "";                        
                        strSql = "SELECT tx_desLar FROM tbm_var WHERE co_reg = " + txtCodLin.getText().replaceAll("'", "''") + " and co_grp = 2";
                        rst = stm.executeQuery(strSql);
                        if (rst.next()){
                            txtLin.setText(((rst.getString("tx_desLar")==null)?"":rst.getString("tx_desLar")));
                            optFil.setSelected(true);
                            optTod.setSelected(false);
                        }
                        else{
                            lblMsgSis.setText("Se encontraron 0 registros...");
                            strMsg="No se ha encontrado ningÃºn registro que cumpla el criterio de bÃºsqueda especÃ­ficado.";
                            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);  
                            rst = null;
                        }
                    }
                }
                catch(SQLException Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                catch(Exception Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }
            }
        }
    }//GEN-LAST:event_txtLinActionPerformed

    private void txtCodMarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMarFocusLost
        if (chrFlgLst == 'N') {
            if (!txtCodMar.getText().equals("")) {
                if (txtCodMar.getText().length()>5 && chrFlgLst == 'N'){ 
                    strMsg="El campo <<CÃ³digo de Marca>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                    txtCodMar.setText("");
                    tabFrm.setSelectedIndex(0);
                    txtMar.setText("");
                    txtCodMar.requestFocus();
                    chrFlgMsj = 'S';
                    chrFlgLst = 'S';
                } 
            }

            flagNum(blnFlgNum);
            flagCon(blnFlgCon);
            if (txtCodMar.getText().length()>0 && blnFlgNum == false && blnFlgCon == false && chrFlgMsj == 'N') {
                String numero = txtCodMar.getText();
                objUtl.isNumero(numero);
                if (objUtl.isNumero(numero) == false) {
                    strMsg="El campo <<CÃ³digo de Marca>> sÃ³lo acepta valores numÃ©ricos.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                    txtCodMar.setText("");
                    tabFrm.setSelectedIndex(0);
                    txtMar.setText("");
                    txtCodMar.requestFocus();
                    chrFlgMsj = 'S';
                }
            }
            blnFlgNum = false;
            blnFlgCon = false;

            if (!txtCodMar.getText().equals("") && chrFlgCon == 'N' && chrFlgMsj == 'N') {
                try{ 
                    con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    if(con != null){
                        stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                        strSql = "";
                        strSql = strSql + "SELECT co_reg, co_grp, tx_desLar";
                        strSql = strSql + " FROM tbm_var";
                        strSql = strSql + " WHERE co_grp = 4 and co_reg = " + txtCodMar.getText().replaceAll("'", "''") + "";
                        strSql = strSql + " and st_reg = 'A'";

                        rst = stm.executeQuery(strSql);
                        System.out.println(strSql);

                        if (!rst.next()){
                            strMsg="El campo <<CÃ³digo de Marca>> no pertenece al grupo de Marca.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                            txtCodMar.setText("");
                            tabFrm.setSelectedIndex(0);
                            txtMar.setText("");
                            txtCodMar.requestFocus();
                       }
                        else {
                            txtCodMar.setText(((rst.getString("co_reg")==null)?"":rst.getString("co_reg")));
                            txtMar.setText(((rst.getString("tx_desLar")==null)?"":rst.getString("tx_desLar")));
                            optFil.setSelected(true);
                            optTod.setSelected(false);
                        }
                    }
                }
                catch(SQLException Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                catch(Exception Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }  
            }
        chrFlgMsj = 'N';
        chrFlgLst = 'N';
        }
        
        if (txtCodMar.getText().equals("")) {
            txtMar.setText("");
        }
    }//GEN-LAST:event_txtCodMarFocusLost

    private void txtCodGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusLost
        if (chrFlgLst == 'N') { 
            if (!txtCodGrp.getText().equals("")) {
                if (txtCodGrp.getText().length()>5 && chrFlgLst == 'N'){ 
                    strMsg="El campo <<CÃ³digo de Grupo>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                    txtCodGrp.setText("");
                    tabFrm.setSelectedIndex(0);
                    txtGrp.setText("");
                    txtCodGrp.requestFocus();
                    chrFlgMsj = 'S';
                    chrFlgLst = 'S';
                } 
            }

            flagNum(blnFlgNum);
            flagCon(blnFlgCon);
            if (txtCodGrp.getText().length()>0 && blnFlgNum == false && blnFlgCon == false && chrFlgMsj == 'N') {
                String numero = txtCodGrp.getText();
                objUtl.isNumero(numero);
                if (objUtl.isNumero(numero) == false) {
                    strMsg="El campo <<CÃ³digo de Grupo>> sÃ³lo acepta valores numÃ©ricos.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                    txtCodGrp.setText("");
                    tabFrm.setSelectedIndex(0);
                    txtGrp.setText("");
                    txtCodGrp.requestFocus();
                    chrFlgMsj = 'S';
                }
            }
            blnFlgNum = false;
            blnFlgCon = false;

            if (!txtCodGrp.getText().equals("") && chrFlgCon == 'N' && chrFlgMsj == 'N') {
                try{ 
                    con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                        if(con != null){
                            stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                            strSql="";
                            strSql = strSql + "SELECT co_reg, co_grp, tx_desLar";
                            strSql = strSql + " FROM tbm_var";
                            strSql = strSql + " WHERE co_grp = 3 and co_reg = " + txtCodGrp.getText().replaceAll("'", "''") + "";
                            strSql = strSql + " and st_reg = 'A'";

                            rst = stm.executeQuery(strSql);
                            System.out.println(strSql);

                            if (!rst.next()){
                                strMsg="El campo <<CÃ³digo de Grupo>> no pertenece a este grupo de ClasificaciÃ³n.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                                txtCodGrp.setText("");
                                tabFrm.setSelectedIndex(0);
                                txtGrp.setText("");
                                txtCodGrp.requestFocus();
                           }
                            else {
                                txtCodGrp.setText(((rst.getString("co_reg")==null)?"":rst.getString("co_reg")));
                                txtGrp.setText(((rst.getString("tx_desLar")==null)?"":rst.getString("tx_desLar")));
                                optFil.setSelected(true);
                                optTod.setSelected(false);
                            }
                      }
                }
                catch(SQLException Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                catch(Exception Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }  
            } 
        chrFlgMsj = 'N';
        chrFlgLst = 'N';
        }
        
        if (txtCodGrp.getText().equals("")) {
            txtGrp.setText("");
        }
    }//GEN-LAST:event_txtCodGrpFocusLost

    private void txtCodLinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLinFocusLost
        if (chrFlgLst == 'N') { 
            if (!txtCodLin.getText().equals("")) {
                if (txtCodLin.getText().length()>5 && chrFlgLst == 'N'){ 
                    strMsg="El campo <<CÃ³digo de LÃ­nea>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                    txtCodLin.setText("");
                    tabFrm.setSelectedIndex(0);
                    txtLin.setText("");
                    txtCodLin.requestFocus();
                    chrFlgMsj = 'S';
                    chrFlgLst = 'S';
                } 
            }

            flagNum(blnFlgNum);
            flagCon(blnFlgCon);
            if (txtCodLin.getText().length()>0 && blnFlgNum == false && blnFlgCon == false && chrFlgMsj == 'N') {
                String numero = txtCodLin.getText();
                objUtl.isNumero(numero);
                if (objUtl.isNumero(numero) == false) {
                    strMsg="El campo <<CÃ³digo de LÃ­nea>> sÃ³lo acepta valores numÃ©ricos.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                    txtCodLin.setText("");
                    tabFrm.setSelectedIndex(0);
                    txtLin.setText("");
                    txtCodLin.requestFocus();
                    chrFlgMsj = 'S';
                }
            }
            blnFlgNum = false; 
            blnFlgCon = false;

            if (!txtCodLin.getText().equals("") && chrFlgCon == 'N' && chrFlgMsj == 'N') {
                try{ 
                    con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                        if(con != null) {
                            stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                            strSql="";
                            strSql = strSql + "SELECT co_reg, co_grp, tx_desLar";
                            strSql = strSql + " FROM tbm_var";
                            strSql = strSql + " WHERE co_grp = 2 and co_reg = " + txtCodLin.getText().replaceAll("'", "''") + "";
                            strSql = strSql + " and st_reg = 'A'";

                            rst = stm.executeQuery(strSql);
                            System.out.println(strSql);

                            if (!rst.next()){
                                strMsg="El campo <<CÃ³digo de LÃ­nea>> no pertenece al grupo de LÃ­nea.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                                txtCodLin.setText("");
                                tabFrm.setSelectedIndex(0);
                                txtLin.setText("");
                                txtCodLin.requestFocus();
                           }
                            else {
                                txtCodLin.setText(((rst.getString("co_reg")==null)?"":rst.getString("co_reg")));
                                txtLin.setText(((rst.getString("tx_desLar")==null)?"":rst.getString("tx_desLar")));
                                optFil.setSelected(true);
                                optTod.setSelected(false);
                            }
                      }
                }
                catch(SQLException Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                catch(Exception Evt)
                {
                    objUtl.mostrarMsgErr_F1(this, Evt);
                }  
            }
        chrFlgMsj = 'N';
        chrFlgLst = 'N';
        }
        
        if (txtCodLin.getText().equals("")) {
           txtLin.setText("");
        }
    }//GEN-LAST:event_txtCodLinFocusLost

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected())
            optFil.setSelected(false);
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected())
            optTod.setSelected(false);
    }//GEN-LAST:event_optFilActionPerformed

    private void cboEstRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstRegActionPerformed
        if (cboEstReg.getSelectedIndex()>0) {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_cboEstRegActionPerformed

    private void txtCodMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMarActionPerformed
        if (txtCodMar.getText().length()>0) {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
        
        if (chrFlgLst == 'N') {
            if (!txtCodMar.getText().equals("")) {
                if (txtCodMar.getText().length()>5 && chrFlgLst == 'N'){ 
                    chrFlgMsj = 'S';
                    chrFlgLst = 'S';
                    valTxt(txtCodMar, txtMar, "CÃ³digo de Marca", "", 2, "el", 0);
                }
            }

            if (txtCodMar.getText().length()>0 && chrFlgMsj == 'N') {
                String numero = txtCodMar.getText();
                objUtl.isNumero(numero);
                    if (objUtl.isNumero(numero) == false) {
                        blnFlgNum = true;
                        flagNum(blnFlgNum);
                        txtCodMar.setText("");
                        txtMar.setText("");
                        strMsg = "El campo <<CÃ³digo de Marca>> sÃ³lo acepta caracteres numÃ©ricos.\nÂ¿Desea corregir el <<CÃ³digo de Marca>> que ha ingresado?\nSi selecciona NO el sistema borrarÃ¡ los datos antes de abandonar este campo.";
                        intOpp = oppMsg.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if (intOpp == JOptionPane.YES_OPTION){
                            tabFrm.setSelectedIndex(0);
                            txtCodMar.setText(numero);
                            txtCodMar.requestFocus();
                            txtCodMar.selectAll(); 
                        }
                        else {
                            tabFrm.setSelectedIndex(0);
                            txtCodMar.setText("");
                            txtMar.setText("");
                            txtCodMar.requestFocus(); 
                        }
                    }
                    else{
                        Librerias.ZafConsulta.ZafConsulta objFndCodMar = 
                        new Librerias.ZafConsulta.ZafConsulta(new javax.swing.JFrame(),
                        "CÃ³digo,Desc Corta,Desc Larga,Estado","co_reg,tx_desCor,tx_desLar,st_reg",
                        "SELECT co_reg,tx_desCor,tx_desLar,st_reg FROM tbm_var WHERE co_grp = 4 AND co_reg = "+ txtCodMar.getText().replaceAll("'", "''") +"", 
                        txtCodMar.getText(), 
                        objParSis.getStringConexion(),  
                        objParSis.getUsuarioBaseDatos(), 
                        objParSis.getClaveBaseDatos()
                         );

                        objFndCodMar.setTitle("Listado de Marcas");
                        objFndCodMar.setSelectedCamBus(0);

                         if(txtCodMar.getText().equals(""))
                            objFndCodMar.show();
                         else
                             if(!objFndCodMar.buscar("co_reg = " + txtCodMar.getText().replaceAll("'", "''") + "")) { 
                                 chrFlgCon = 'S';
                                objFndCodMar.show(); }

                        if(!objFndCodMar.GetCamSel(1).equals("")){
                            if (objFndCodMar.GetCamSel(4).equals("I")){
                                strMsg = "Esta Marca estÃ¡ en estado <<Inactivo>>.\nVerifique otro Grupo y vuelva a intentarlo.";
                                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);}
                            else {
                                txtCodMar.setText(objFndCodMar.GetCamSel(1)); 
                                blnFlgCon=true;
                                flagCon(blnFlgCon);
                                try{ 
                                    con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                                    if(con != null){
                                        stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                                        strSql = "";                        
                                        strSql = "SELECT tx_desLar FROM tbm_var WHERE co_reg = " + txtCodMar.getText().replaceAll("'", "''") + " AND co_grp = 4";
                                        rst = stm.executeQuery(strSql);
                                        if (rst.next()){
                                            txtMar.setText(((rst.getString("tx_desLar")==null)?"":rst.getString("tx_desLar")));
                                            optFil.setSelected(true);
                                            optTod.setSelected(false);
                                        }
                                        else{
                                            lblMsgSis.setText("Se encontraron 0 registros...");
                                            strMsg="No se ha encontrado ningÃºn registro que cumpla el criterio de bÃºsqueda especÃ­ficado.";
                                            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);  
                                            rst = null;
                                        }
                                    }
                                }
                                catch(SQLException Evt)
                                {
                                    objUtl.mostrarMsgErr_F1(this, Evt);
                                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                                catch(Exception Evt)
                                {
                                    objUtl.mostrarMsgErr_F1(this, Evt);
                                }
                            }
                        } 
                        chrFlgCon = 'N';
                        blnFlgNum = false;
                        }
                }
            chrFlgMsj = 'N';
            chrFlgLst = 'N';
        }
    }//GEN-LAST:event_txtCodMarActionPerformed

    private void txtCodGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodGrpActionPerformed
        if (txtCodGrp.getText().length()>0) {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
        
        if (chrFlgLst == 'N') {
            if (!txtCodGrp.getText().equals("")) {
                if (txtCodGrp.getText().length()>5 && chrFlgLst == 'N'){ 
                    chrFlgMsj = 'S';
                    chrFlgLst = 'S';
                    valTxt(txtCodGrp, txtGrp, "CÃ³digo de Grupo", "", 2, "el", 0);
                }
            }

            if (txtCodGrp.getText().length()>0 && chrFlgMsj == 'N') {
                String numero = txtCodGrp.getText();
                objUtl.isNumero(numero);
                    if (objUtl.isNumero(numero) == false) {
                        blnFlgNum = true;
                        flagNum(blnFlgNum);
                        txtCodGrp.setText("");
                        txtGrp.setText("");
                        strMsg = "El campo <<CÃ³digo de Grupo>> sÃ³lo acepta caracteres numÃ©ricos.\nÂ¿Desea corregir el <<CÃ³digo de Grupo>> que ha ingresado?\nSi selecciona NO el sistema borrarÃ¡ los datos antes de abandonar este campo.";
                        intOpp = oppMsg.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if (intOpp == JOptionPane.YES_OPTION){
                            tabFrm.setSelectedIndex(0);
                            txtCodGrp.setText(numero);
                            txtCodGrp.requestFocus(); 
                            txtCodGrp.selectAll();
                        }
                        else {
                            tabFrm.setSelectedIndex(0);
                            txtCodGrp.setText("");
                            txtGrp.setText("");
                            txtCodGrp.requestFocus(); 
                        }
                    }
                    else{
                        Librerias.ZafConsulta.ZafConsulta objFndCodGrp = 
                        new Librerias.ZafConsulta.ZafConsulta(new javax.swing.JFrame(),
                        "CÃ³digo,Desc Corta,Desc Larga,Estado","co_reg,tx_desCor,tx_desLar,st_reg",
                        "SELECT co_reg,tx_desCor,tx_desLar,st_reg FROM tbm_var WHERE co_grp = 3 AND co_reg = "+ txtCodGrp.getText().replaceAll("'", "''") +"", 
                        txtCodGrp.getText(), 
                        objParSis.getStringConexion(), 
                        objParSis.getUsuarioBaseDatos(), 
                        objParSis.getClaveBaseDatos()
                        );

                        objFndCodGrp.setTitle("Listado de Grupos");
                        objFndCodGrp.setSelectedCamBus(0);

                        if(txtCodGrp.getText().equals(""))
                            objFndCodGrp.show();
                        else
                            if(!objFndCodGrp.buscar("co_reg = " + txtCodGrp.getText().replaceAll("'", "''") + "")) {
                                chrFlgCon = 'S';
                                objFndCodGrp.show(); }

                        if(!objFndCodGrp.GetCamSel(1).equals("")){
                            if (objFndCodGrp.GetCamSel(4).equals("I")){
                                strMsg = "Este Grupo estÃ¡ en estado <<Inactivo>>.\nVerifique otro Grupo y vuelva a intentarlo.";
                                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);}
                            else {
                                txtCodGrp.setText(objFndCodGrp.GetCamSel(1)); 
                                blnFlgCon = true;
                                flagCon(blnFlgCon);
                                try{ 
                                    con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                                    if(con != null){
                                        stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                                        strSql = "";                        
                                        strSql = "SELECT tx_desLar FROM tbm_var WHERE co_reg = " + txtCodGrp.getText().replaceAll("'", "''") + " AND co_grp = 3";
                                        rst = stm.executeQuery(strSql);
                                        if (rst.next()){
                                            txtGrp.setText(((rst.getString("tx_desLar")==null)?"":rst.getString("tx_desLar")));
                                            optFil.setSelected(true);
                                            optTod.setSelected(false);
                                        }
                                        else{
                                            lblMsgSis.setText("Se encontraron 0 registros...");
                                            strMsg="No se ha encontrado ningÃºn registro que cumpla el criterio de bÃºsqueda especÃ­ficado.";
                                            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);  
                                            rst = null;
                                        }
                                    }
                                }
                                catch(SQLException Evt)
                                {
                                    objUtl.mostrarMsgErr_F1(this, Evt);
                                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                                catch(Exception Evt)
                                {
                                    objUtl.mostrarMsgErr_F1(this, Evt);
                                }
                            }
                        }
                        chrFlgCon = 'N';
                        blnFlgNum = false;    
                     }
                }
            chrFlgLst = 'N';
            chrFlgMsj = 'N';
        }
    }//GEN-LAST:event_txtCodGrpActionPerformed

    private void txtCodLinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLinActionPerformed
        if (txtCodLin.getText().length()>0) {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
        
        if (chrFlgLst == 'N') {
            if (!txtCodLin.getText().equals("")) {
                if (txtCodLin.getText().length()>5 && chrFlgLst == 'N'){ 
                    chrFlgMsj = 'S';
                    chrFlgLst = 'S';
                    valTxt(txtCodLin, txtLin, "CÃ³digo de LÃ­nea", "", 2 , "el", 0);
                }
            }

            if (txtCodLin.getText().length()>0 && chrFlgMsj == 'N' ) {
                String numero = txtCodLin.getText();
                objUtl.isNumero(numero);
                    if (objUtl.isNumero(numero) == false) {
                        blnFlgNum = true;
                        flagNum(blnFlgNum);
                        txtCodLin.setText("");
                        txtLin.setText("");
                        strMsg = "El campo <<CÃ³digo de LÃ­nea>> sÃ³lo acepta caracteres numÃ©ricos.\nÂ¿Desea corregir el <<CÃ³digo de LÃ­nea>> que ha ingresado?\nSi selecciona NO el sistema borrarÃ¡ los datos antes de abandonar este campo.";
                        intOpp = oppMsg.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if (intOpp == JOptionPane.YES_OPTION) {
                            tabFrm.setSelectedIndex(0);
                            txtCodLin.setText(numero);
                            txtCodLin.requestFocus(); 
                            txtCodLin.selectAll();
                        }
                        else {
                            tabFrm.setSelectedIndex(0);
                            txtCodLin.setText("");
                            txtLin.setText("");
                            txtCodLin.requestFocus(); 
                        }
                    }
                    else{
                        Librerias.ZafConsulta.ZafConsulta objFndCodLin = 
                        new Librerias.ZafConsulta.ZafConsulta(new javax.swing.JFrame(),
                        "CÃ³digo,Desc Corta,Desc Larga,Estado","co_reg,tx_desCor,tx_desLar,st_reg",
                        "SELECT co_reg,tx_desCor,tx_desLar,st_reg FROM tbm_var WHERE co_grp = 2 AND co_reg = "+ txtCodLin.getText().replaceAll("'", "''") +"", 
                        txtCodLin.getText(), 
                        objParSis.getStringConexion(),  
                        objParSis.getUsuarioBaseDatos(), 
                        objParSis.getClaveBaseDatos()
                        );

                        objFndCodLin.setTitle("Listado de LÃ­neas");
                        objFndCodLin.setSelectedCamBus(0);

                        if(txtCodLin.getText().equals(""))
                            objFndCodLin.show();
                        else
                            if(!objFndCodLin.buscar("co_reg = " + txtCodLin.getText().replaceAll("'", "''") + "")) {
                                chrFlgCon = 'S';
                                objFndCodLin.show(); }

                        if(!objFndCodLin.GetCamSel(1).equals("")){
                            if (objFndCodLin.GetCamSel(4).equals("I")){
                                strMsg="Esta LÃ­nea estÃ¡ en estado <<Inactivo>>.\nVerifique otra Linea y vuelva a intentarlo.";
                                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);}
                            else { 
                                txtCodLin.setText(objFndCodLin.GetCamSel(1)); 
                                blnFlgCon = true;
                                flagCon(blnFlgCon);
                                try{ 
                                    con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                                    if(con != null){
                                        stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                                        strSql = "";                        
                                        strSql = "SELECT tx_desLar FROM tbm_var WHERE co_reg = " + txtCodLin.getText().replaceAll("'", "''") + " AND co_grp = 2";
                                        rst = stm.executeQuery(strSql);
                                        if (rst.next()){
                                            txtLin.setText(((rst.getString("tx_desLar")==null)?"":rst.getString("tx_desLar")));
                                            optFil.setSelected(true);
                                            optTod.setSelected(false);
                                        }
                                        else{
                                            lblMsgSis.setText("Se encontraron 0 registros...");
                                            strMsg="No se ha encontrado ningÃºn registro que cumpla el criterio de bÃºsqueda especÃ­ficado.";
                                            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);  
                                            rst = null; 
                                        }
                                    }
                                }
                                catch(SQLException Evt)
                                {
                                    objUtl.mostrarMsgErr_F1(this, Evt);
                                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                                catch(Exception Evt)
                                {
                                    objUtl.mostrarMsgErr_F1(this, Evt);
                                }
                            }
                        }
                        chrFlgCon = 'N';
                        blnFlgNum = false;   
                        }
                }
            chrFlgLst = 'N';
            chrFlgMsj = 'N';
        }
    }//GEN-LAST:event_txtCodLinActionPerformed

    private void chkStkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkStkActionPerformed
        if (chkStk.isSelected()) {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_chkStkActionPerformed

    private void chkSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSerActionPerformed
        if (chkSer.isSelected()) {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_chkSerActionPerformed

    private void txtNomHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomHasFocusLost
        if (txtNomHas.getText().length()>0) {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtNomHasFocusLost

    private void txtCodHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodHasFocusLost
        if (txtCodHas.getText().length()>0) {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodHasFocusLost

    private void txtNomDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDesFocusLost
        if (txtNomDes.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            if (txtNomHas.getText().length()==0)
                txtNomHas.setText(txtNomDes.getText());
        }
    }//GEN-LAST:event_txtNomDesFocusLost

    private void txtCodDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDesFocusLost
        if (txtCodDes.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            if (txtCodHas.getText().length()==0)
                txtCodHas.setText(txtCodDes.getText());
        }
    }//GEN-LAST:event_txtCodDesFocusLost

    private void txtNomHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomHasFocusGained
        txtNomHas.selectAll();
    }//GEN-LAST:event_txtNomHasFocusGained

    private void txtNomDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDesFocusGained
        txtNomDes.selectAll();
    }//GEN-LAST:event_txtNomDesFocusGained

    private void txtCodHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodHasFocusGained
        txtCodHas.selectAll();
    }//GEN-LAST:event_txtCodHasFocusGained

    private void txtCodDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDesFocusGained
        txtCodDes.selectAll();
    }//GEN-LAST:event_txtCodDesFocusGained

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodDes.setText("");
            txtCodHas.setText("");
            txtNomDes.setText("");
            txtNomHas.setText("");
            chkSer.setSelected(false);
            chkStk.setSelected(false);
            txtCodLin.setText("");
            txtLin.setText("");
            txtCodGrp.setText("");
            txtGrp.setText("");
            txtCodMar.setText("");
            txtMar.setText("");
            cboEstReg.setSelectedIndex(0);
            optFil.setSelected(false);
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        //Abrir la opciï¿½n seleccionada al presionar ENTER.
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            evt.consume();
            abrirFrm();
        }
    }//GEN-LAST:event_tblDatKeyPressed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acciï¿½n de acuerdo a la etiqueta del botï¿½n ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }            
        }
        else
        {
            blnCon=false;
        }     
    }//GEN-LAST:event_butConActionPerformed

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
        //Abrir la opciï¿½n seleccionada al dar doble click.
        if (evt.getClickCount()==2)
        {
            abrirFrm();
        }
    }//GEN-LAST:event_tblDatMouseClicked

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        strMsg="Â¿EstÃ¡ seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void exitForm() {
        dispose();
    } 
    
    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBod;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGrp;
    private javax.swing.JButton butLin;
    private javax.swing.JButton butMar;
    private javax.swing.JComboBox cboEstReg;
    private javax.swing.JCheckBox chkSer;
    private javax.swing.JCheckBox chkStk;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblCodDes;
    private javax.swing.JLabel lblCodHas;
    private javax.swing.JLabel lblEstReg;
    private javax.swing.JLabel lblGrp;
    private javax.swing.JLabel lblLin;
    private javax.swing.JLabel lblMar;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNomDes;
    private javax.swing.JLabel lblNomHas;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCod;
    private javax.swing.JPanel panEst;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panNom;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtBod;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodDes;
    private javax.swing.JTextField txtCodGrp;
    private javax.swing.JTextField txtCodHas;
    private javax.swing.JTextField txtCodLin;
    private javax.swing.JTextField txtCodMar;
    private javax.swing.JTextField txtGrp;
    private javax.swing.JTextField txtLin;
    private javax.swing.JTextField txtMar;
    private javax.swing.JTextField txtNomDes;
    private javax.swing.JTextField txtNomHas;
    // End of variables declaration//GEN-END:variables
}

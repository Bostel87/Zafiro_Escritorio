/*
 * ZafUtil.java
 *
 * Created on 25 de agosto de 2004, 13:49
 * v0.54
 */

package Librerias.ZafUtil;
import java.math.BigDecimal;
import java.sql.DriverManager;

/**
 * El fin de la creación de esta clase es el de ubicar en ella todas aquellas operaciones
 * que se harían de uso común para el sistema. Esta clase contendrá tantos mï¿½todos como
 * operaciones vaya a realizar. Por ejemplo, como ejemplo de operaciones comunes tenemos
 * la opción de desactivar los componentes en el momento que se realiza una consulta para
 * que no se haga modificaciones a los datos que se presentan; otra operación es la de
 * añadir o eliminar filas de un JTable, que se usará cada vez que vayamos a crear una
 * factura o una cotización.
 * @author Josï¿½ Javier Naranjo Ch.
 * @since 25/Ago/2004
 */
public class ZafUtil
{
   
    static java.awt.Color colComDes;
    //Constantes: Columnas utilizadas por el proceso de recosteo:
    private static final int INT_REC_COD_EMP=0;                    //Código de la empresa.
    private static final int INT_REC_COD_LOC=1;                    //Código del local.
    private static final int INT_REC_COD_TIP_DOC=2;                //Código del tipo de documento.
    private static final int INT_REC_COD_DOC=3;                    //Código del documento.
    private static final int INT_REC_COD_REG=4;                    //Código del registro.
    private static final int INT_REC_EST_CUC=5;                    //Estado de costo unitario calculado.
    private static final int INT_REC_NAT_DOC=6;                    //Naturaleza del documento.
    private static final int INT_REC_EST_DOC=7;                    //Estado del documento.
    private static final int INT_REC_CAN=8;                        //Cantidad.
    private static final int INT_REC_CAN_DEV=9;                    //Cantidad.
    private static final int INT_REC_COS_UNI=10;                   //Costo unitario.
    private static final int INT_REC_POR_DES=11;                   //Porcentaje de descuento.
    private static final int INT_REC_COS_TOT=12;                   //Costo total.
    private static final int INT_REC_SAL_UNI=13;                   //Saldo en unidades.
    private static final int INT_REC_SAL_VAL=14;                   //Saldo en valores.
    private static final int INT_REC_COS_PRO=15;                   //Costo promedio.
    
    /**
    * Crea una nueva instancia de la clase ZafUtil
    */
    public ZafUtil()
    {
        
    }

    /**
     * Recibe un componente del tipo JInternalFrame, por medio de un método recursivo
     * extrae cada uno de los componentes que éste tenga y establece como desactivados
     * a los componentes que sean de los siguientes tipos:
     *    <PRE>
     *    JTextField
     *    JTextArea
     *    JList
     *    JTable
     *    JRadioButton
     *    JCheckBox
     *    JCombobox
     *    JSpinner
     *    </PRE>
     * Ejemplo de su uso:
     *    <PRE>
     *         ZafUtil objZafUtil = new ZafUtil();
     *            .
     *            .
     *            .
     *         objZafUtil.desactivarCom(dskpri.getSelectedFrame());
     *    </PRE>
     * @param ifrtem JInternalFrame del cual se extraerán los componentes a desactivar
     */            
    public void desactivarCom(javax.swing.JInternalFrame ifrtem)
    {
        java.awt.Component objCom[];
        java.awt.Container objCon;
        objCom = ifrtem.getContentPane().getComponents();
        for (int i=0; i< objCom.length; i++)
        {
            objCon = (java.awt.Container)objCom[i];
            extraer_desactivarCom(objCon);
        }
    }
    
    public void desactivarCom(javax.swing.JDialog ifrtem)
    {
        java.awt.Component objCom[];
        java.awt.Container objCon;
        objCom = ifrtem.getContentPane().getComponents();
        for (int i=0; i< objCom.length; i++)
        {
            objCon = (java.awt.Container)objCom[i];
            extraer_desactivarCom(objCon);
        }
    }
    
    /**
     * Recibe un componente del tipo JTabbedPane, por medio de un método recursivo
     * extrae cada uno de los componentes que éste tenga y establece como desactivados
     * a los componentes que sean de los siguientes tipos:
     *    <PRE>
     *    JTextField
     *    JTextArea
     *    JList
     *    JTable
     *    JRadioButton
     *    JCheckBox
     *    JCombobox
     *    JSpinner
     *    </PRE>
     *    Ejemplo de su uso:
     *    <PRE>
     *         ZafUtil objZafUtil = new ZafUtil();
     *            .
     *            .
     *            .
     *         objZafUtil.desactivarCom(tabpri);
     *    </PRE>
     * @param tabtem JTabbedPane del cual se extraerán los componentes a desactivar.
     */    
    public void desactivarCom(javax.swing.JTabbedPane tabtem)
    {
        java.awt.Container objCon;
        objCon = (java.awt.Container)tabtem.getComponentAt(tabtem.getSelectedIndex());
        extraer_desactivarCom(objCon);
    }
    
    //Funcion Recursiva que extrae componentes de un contenedor dado.
    private static void extraer_desactivarCom(java.awt.Container con) 
    {
	java.awt.Component[] comps = con.getComponents();
        for (int i = 0; i < comps.length; i++) 
        {
            if ((comps[i] instanceof java.awt.Container) && !(comps[i] instanceof javax.swing.JToolBar)) 
            {
                extraer_desactivarCom((java.awt.Container)comps[i]);
            }
            if (comps[i] instanceof javax.swing.text.JTextComponent && !(comps[i] instanceof javax.swing.JToolBar)) 
            {
                javax.swing.text.JTextComponent txtpru = (javax.swing.text.JTextComponent)comps[i];
                colComDes = txtpru.getBackground();
                txtpru.setEditable(false);
                txtpru.setBackground(colComDes);
            }
            else
            {
                if (comps[i] instanceof javax.swing.JList ||
                    comps[i] instanceof javax.swing.JButton ||
                    comps[i] instanceof javax.swing.JTable ||
                    comps[i] instanceof javax.swing.JRadioButton ||
                    comps[i] instanceof javax.swing.JCheckBox ||
                    comps[i] instanceof javax.swing.JComboBox ||
                    comps[i] instanceof javax.swing.JSpinner)
                {
                    comps[i].setEnabled(false);
                }
            }
        }
    }
    
    public void activarCom(javax.swing.JInternalFrame ifrtem)
    {
        java.awt.Component objCom[];
        java.awt.Container objCon;
        objCom = ifrtem.getContentPane().getComponents();
        for (int i=0; i< objCom.length; i++)
        {
            objCon = (java.awt.Container)objCom[i];
            extraer_activarCom(objCon);
        }
    }
    
    public void activarCom(javax.swing.JTabbedPane tabtem)
    {
        java.awt.Container objCon;
        objCon = (java.awt.Container)tabtem.getComponentAt(tabtem.getSelectedIndex());
        extraer_activarCom(objCon);
    }
    
    private static void extraer_activarCom(java.awt.Container con) 
    {
	java.awt.Component[] comps = con.getComponents();
        for (int i = 0; i < comps.length; i++) 
        {
            if ((comps[i] instanceof java.awt.Container) && !(comps[i] instanceof javax.swing.JToolBar)) 
            {
                extraer_activarCom((java.awt.Container)comps[i]);
            }
            if (comps[i] instanceof javax.swing.text.JTextComponent && !(comps[i] instanceof javax.swing.JToolBar)) 
            {
                javax.swing.text.JTextComponent txtpru = (javax.swing.text.JTextComponent)comps[i];
                colComDes = txtpru.getBackground();
                txtpru.setEditable(true);
                txtpru.setBackground(colComDes);
            }
            else
            {
                if (comps[i] instanceof javax.swing.JList ||
                    comps[i] instanceof javax.swing.JTable ||
                    comps[i] instanceof javax.swing.JButton ||
                    comps[i] instanceof javax.swing.JRadioButton ||
                    comps[i] instanceof javax.swing.JCheckBox ||
                    comps[i] instanceof javax.swing.JComboBox ||
                    comps[i] instanceof javax.swing.JSpinner)
                {
                    comps[i].setEnabled(true);
                }
            }
        }
    }
    
    /**
     * Muestra un cuadro de mensaje con los datos de la excepción que se generó.
     * Esta función presenta el nombre de la clase en la que se generó la excepción,
     * la función donde se generó la excepción y la excepción que se generó.
     * Con esta información el desarrollador puede determinar de manera sencilla
     * el lugar donde se generó la excepción y proceder con la respectiva corrección.
     * @param padre El formulario padre para el cuadro de mensaje. Puede ser cualquier objeto que
     * herede de la clase <I>Component</I>. Por ejemplo: JFrame, JInternalFrame, etc
     * @param excepcion La excepción que se generó.
     */    
    public void mostrarMsgErr_F1(java.awt.Component padre, Exception excepcion)
    {
        String strTit, strMsg, strFun="", strExc="";
        StackTraceElement[] objSte=excepcion.getStackTrace();
        for (int i=0;i<objSte.length;i++)
        {
            if ( (!(objSte[i].toString().startsWith("java"))) && (!(objSte[i].toString().startsWith("sun"))) && (!(objSte[i].toString().startsWith("org"))) && (!(objSte[i].toString().startsWith("net"))) )
                strFun+=objSte[i].toString() + "<BR>";
        }
        strExc=excepcion.toString();
        if (strExc.length()>120)
            strExc=strExc.substring(0,120) + "<BR>" + strExc.substring(120);
        strTit="Mensaje del sistema Zafiro";
        strMsg="<HTML>Ocurrió el siguiente error:";
        strMsg+="<BR><FONT COLOR=\"blue\">Fecha y hora:</FONT> " + formatearFecha(new java.util.Date(), "dd/MMM/yyyy HH:mm:ss");
        strMsg+="<BR><FONT COLOR=\"blue\">Clase:</FONT> " + ((padre==null)?"(Indeterminada)":padre.getClass().getName());
        strMsg+="<BR><FONT COLOR=\"blue\">Función:</FONT> " + strFun;
        strMsg+="<FONT COLOR=\"blue\">Error:</FONT> " + strExc + "</HTML>";
        strMsg=strMsg.replaceAll("\n", "<BR>");
        javax.swing.JOptionPane.showMessageDialog(padre,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE,null);
    }

    /**
     * Esta función devuelve verdadero si puede cargar el driver y falso en el caso contrario.
     * @param padre El formulario padre para el cuadro de mensaje. Puede ser cualquier objeto que
     * herede de la clase <I>Component</I>. Por ejemplo: JFrame, JInternalFrame
     * @param driver El driver que se utiliza para conectarse a la base de datos.
     * <BR>Por ejemplo:
     *         <BR><I>Driver ODBC:</I> sun.jdbc.odbc.JdbcOdbcDriver
     *         <BR><I>Driver Postgresql:</I> org.postgresql.Driver
     * @return true: Si se pudo cargar el driver.
     * <BR>false: En el caso contrario.
     */
    public boolean cargarDrv_F1(java.awt.Component padre, String driver)
    {
        boolean blnRes=true;
        try
        {
            Class.forName(driver);
        }
        catch (ClassNotFoundException e) 
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite llenar un combo con datos que se encuentran en la base de datos. La idea es llenar 
     * el combo y un vector. En el combo se almacenan las descripciones mientras que en el vector se almacenan
     * los códigos. 
     * <BR>Por ejemplo:
     *          <CENTER>
     *          <TABLE BORDER=1>
     *              <TR><TD><I>Vector</I></TD><TD><I>Combo</I></TD></TR>
     *              <TR><TD>15</TD><TD>Soltero</TD></TR>
     *              <TR><TD>16</TD><TD>Casado</TD></TR>
     *              <TR><TD>17</TD><TD>Viudo</TD></TR>
     *              <TR><TD>18</TD><TD>Divorciado</TD></TR>
     *          </TABLE>
     *          </CENTER>
     * <BR>15,16,17 y 18 representan los códigos de Soltero, Casado, Viudo y Divorciado respectivamente
     * @param padre El formulario padre para el cuadro de mensaje. Puede ser cualquier objeto que
     * herede de la clase <I>Component</I>. Por ejemplo: JFrame, JInternalFrame
     * @param stringConexion La cadena que contiene información para conectarse a la base de datos.
     * @param usuario El usuario para conectarse a la base de datos.
     * @param clave La clave del usuario para conectarse a la base de datos.
     * @param sentenciaSQL La sentencia SQL que sirve para obtener los datos de la base de datos.
     * @param combo El combo que se va a llenar.
     * @param vector El vector que se va a llenar. 
     * @return true: Si se pudo llenar el combo.
     * <BR>false: En el caso contrario.
     */
    public boolean llenarCbo_F1(java.awt.Component padre, String stringConexion, String usuario, String clave,String sentenciaSQL, javax.swing.JComboBox combo, java.util.Vector vector)
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        boolean blnRes=true;
        try
        {
            combo.setSelectedIndex(-1);
            combo.removeAllItems();
            vector.clear();
            con = java.sql.DriverManager.getConnection(stringConexion,usuario,clave);
            if (con != null)
            {
                stm = con.createStatement();	
                rst = stm.executeQuery(sentenciaSQL);
                while (rst.next())
                {
                    vector.add(rst.getString(1));
                    combo.addItem(rst.getString(2));
                }
                combo.setSelectedIndex(-1);
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
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite llenar un combo con datos que se encuentran en la base de datos. La idea es llenar 
     * el combo y un vector. En el combo se almacenan las descripciones mientras que en el vector se almacenan
     * los códigos. 
     * <BR>Por ejemplo:
     *          <CENTER>
     *          <TABLE BORDER=1>
     *              <TR><TD><I>Vector</I></TD><TD><I>Combo</I></TD></TR>
     *              <TR><TD>15</TD><TD>Soltero</TD></TR>
     *              <TR><TD>16</TD><TD>Casado</TD></TR>
     *              <TR><TD>17</TD><TD>Viudo</TD></TR>
     *              <TR><TD>18</TD><TD>Divorciado</TD></TR>
     *          </TABLE>
     *          </CENTER>
     * <BR>15,16,17 y 18 representan los códigos de Soltero, Casado, Viudo y Divorciado respectivamente
     * @param padre El formulario padre para el cuadro de mensaje. Puede ser cualquier objeto que
     * herede de la clase <I>Component</I>. Por ejemplo: JFrame, JInternalFrame
     * @param stringConexion La cadena que contiene información para conectarse a la base de datos.
     * @param usuario El usuario para conectarse a la base de datos.
     * @param clave La clave del usuario para conectarse a la base de datos.
     * @param sentenciaSQL La sentencia SQL que sirve para obtener los datos de la base de datos.
     * @param combo El combo que se va a llenar.
     * @param vector El vector que se va a llenar. 
     * @param co_Buscar EL codigo que se va a buscar
     * @return true: Si se pudo llenar el combo.
     * <BR>false: En el caso contrario.
     */
    public boolean llenarCbo_F1(java.awt.Component padre, String stringConexion, String usuario, String clave,String sentenciaSQL, javax.swing.JComboBox combo, java.util.Vector vector, String co_Buscar)
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        boolean blnRes=true;
        try
        {   
            combo.setSelectedIndex(-1);
            combo.removeAllItems();
            vector.clear();
            con = java.sql.DriverManager.getConnection(stringConexion,usuario,clave);
            if (con != null)
            {
                stm = con.createStatement();	
                rst = stm.executeQuery(sentenciaSQL);
                int idx=0, selectedIdx=-1;
                for (idx=0; rst.next(); idx++)
                {   
                    vector.add(rst.getString(1));
                    if(vector.elementAt(idx).toString().trim().equals(co_Buscar.trim())){
                        selectedIdx = idx;
                    }
                    combo.addItem(rst.getString(2));
                }
                combo.setSelectedIndex(selectedIdx);
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
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función determina si la cadena que se recibe como argumento es un valor numérico o no. 
     * @param numero La cadena que contiene el número que se desea evaluar.
     * @return true: Si el valor es un número.
     * <BR>false: En el caso contrario.
     */
    public boolean isNumero(String numero)
    {
        double dblNum;
        boolean blnRes=true;
        try
        {
            dblNum=Double.parseDouble(numero);
        }
        catch (NumberFormatException e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    public java.util.Calendar getFecha(String stringConexion, String usuario, String clave)
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        java.util.Calendar objFec = java.util.Calendar.getInstance();
        int fecha[];
        try
        {
            con = java.sql.DriverManager.getConnection(stringConexion,usuario,clave);
            if (con != null)
            {
                stm = con.createStatement();	
                rst = stm.executeQuery("Select CURRENT_DATE");
                
                rst.last();
                String prueba = rst.getString(1);
                int intNumFil;	//Número de filas
		int i=0;
		java.util.StringTokenizer stkLin=new java.util.StringTokenizer(prueba,"-",false);
		intNumFil=stkLin.countTokens();
		fecha=new int[intNumFil];	//Le doy dimensiones al arreglo.
		while (stkLin.hasMoreTokens())
		{
                    fecha[i]=Integer.parseInt(stkLin.nextToken());
                    i++;
		}
                rst.close(); 
                stm.close();
                con.close();
                fecha[1]-=1;
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecha[2]);
                objFec.set(java.util.Calendar.MONTH, fecha[1]); 
                objFec.set(java.util.Calendar.YEAR, fecha[0]);
            }
        }
        catch (java.sql.SQLException e)
        {
            System.out.println(e.toString());
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return objFec;
    }
    
    /**
     * Esta función redondea un número a los decimales especificados.
     * @param valor El número que se desea redondear.
     * @param dec El número de decimales al que se desea redondear.
     * @return El número redondeado.
     * @deprecated No se recomienda el uso de éste método. En su lugar utilice los métodos "redondear" y "redondearBigDecimal".
     * @see #redondear(java.lang.String, int)
     * @see #redondear(double, int)
     * @see #redondearBigDecimal(java.math.BigDecimal, int)
     * @see #redondearBigDecimal(java.lang.String, int)
     */
    public double redondeo(double valor, int dec)
    {
        double val=valor;
        //La suma "3.65+0.05" debería dar "3.70". Pero Java genera "3.6999999999999997".
        //Para evitar éste problema se debe trabajar con la clase "java.math.BigDecimal".
        java.math.BigDecimal objBigDec=new java.math.BigDecimal("" + val);
        objBigDec=objBigDec.setScale(6,java.math.BigDecimal.ROUND_HALF_UP);
        val=objBigDec.doubleValue();
        /*
         * Se usa elmetodo doubleToString para obtener el valor real 
         * sin notacion exponencial..
         */
        String cantidad = "" + doubleToString(val);
        int temp=0;
        int punto = cantidad.indexOf(".");
        String entero = cantidad.substring(0, punto);
        String decimales = cantidad.substring(punto +1, cantidad.length());
        if (decimales.length() > dec && dec!=0)
        {
            for (int x=decimales.length(); x>dec; x--)
            {
                int aux = Integer.parseInt(decimales.substring(x-1, x));
                int aux2 = Integer.parseInt(decimales.substring(x-2, x-1));
                decimales = decimales.substring(0, x-2);
                if (aux>=5 && temp!=1)
                {
                    if (aux2!=9)
                        aux2++;
                    else
                        temp=1;
                }
                else
                {
                    if (temp==1)
                    {
                        temp=0;
                        if (aux2!=9)
                            aux2++;
                        else
                            temp=1;
                    }
                }
                decimales+=aux2;
             }
            int deci = Integer.parseInt(decimales);
            String sfinal;
            if (deci==0)
                {sfinal = "" + entero;}
            else
                {sfinal = "" + entero + "." + decimales;}
            
            val=Double.parseDouble(sfinal);
        }
    return val;    
    }
    
    /*
     * Metodo que retorna un string convertido en el valor real sin notacion exponencial
     * @parameter double dblOrigen con el valor 
     * @return  String con el valor double sin notacion exponecial .
     */
    private String doubleToString(double dblOrigen){
        boolean isNegativo = false;
        if(dblOrigen<0)
            isNegativo = true;
        String strOrigen = Math.abs(dblOrigen) + "", strValorReal = dblOrigen + "";
        int idxE = strOrigen.indexOf('E');
        if (idxE >=0)
        {
            String strMul1 = strOrigen.substring(0, idxE) ;
            int    intMul2 = Integer.parseInt(strOrigen.substring(idxE + 1 , strOrigen.length() ));
            boolean blnComaAderecha = (intMul2<0)?false:true;
            String strValorEntero = strMul1.substring(0, strMul1.indexOf('.'));
            String strValorDecimal= strMul1.substring(strMul1.indexOf('.')+1 );
            String strDigitoaAgregar = "" , 
                   strValorBase = (blnComaAderecha)?strValorEntero:strValorDecimal, 
                   strValorAextraer = (blnComaAderecha)?strValorDecimal:stringReverse(strValorEntero);
            for(int i = 0 ;i< Math.abs(intMul2) ; i++ )
            {
                 if(strValorAextraer.length()>0)
                 {
                      strDigitoaAgregar  = strValorAextraer.charAt(0) + "";
                      strValorAextraer =  strValorAextraer.substring(1);
                 }
                 else
                 {
                      strDigitoaAgregar = "0";
                 }
                 strValorBase = (blnComaAderecha)?strValorBase + strDigitoaAgregar : strDigitoaAgregar + strValorBase ;
            }
            if (blnComaAderecha)
                strValorReal = strValorBase + "." + ((strValorAextraer.length()>0)?strValorAextraer:"0");
            else
                strValorReal = ((strValorAextraer.length()>0)?strValorAextraer:"0") + "." + strValorBase;
        }
        return (isNegativo)?"-"+strValorReal:strValorReal;
    }
    
    /*
     * Metodo Utilizado para poner al reves el contenido de un string
     * @parameters strSource String con la cadena que se pondra al reves
     * @return  String con los datos al reves
     */
    public String stringReverse(String strSource)
    {
        String strDest = "";
        for(int i = strSource.length(); i > 0 ; i--)
        {
            strDest = strDest + strSource.charAt(i-1);
        }
        return  strDest;
    }
    
    /**
     * Esta función hace que se vean N decimales en la columna pasada como parametro
     * sin alterar los decmales con los ke trabaja internamente.
     * @param tblConDec Tabla en la ke se va a trabajar
     * @param intColConDec el numero de columna en el ke se va a presentar los n decimales
     * @param intNumDecimales Numero de decimales ke se presentaran
     */
    public void verDecimalesEnTabla(javax.swing.JTable tblConDec, int intColConDec, int intNumDecimales)
    {
        tblConDec.getColumnModel().getColumn(intColConDec).setCellRenderer(new RenderDecimales(intNumDecimales)); 
    }
    
    /*
     * Clase utilizada para poner n decimales en los numeros en la tabla
     * en el contructor recibe los numeros de decimales que va a presentar.
     */
    private class RenderDecimales extends javax.swing.JLabel implements javax.swing.table.TableCellRenderer
    {
        int intNumDecimales = 0;
        public RenderDecimales(int intNumDecimales)
        {
            this.intNumDecimales=intNumDecimales ;
            setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            setOpaque(true);
            setBackground(new java.awt.Color(255, 255, 255));
        }
        
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            double dblSubPro = Double.parseDouble( (value == null)?"0":""+value);
            Librerias.ZafUtil.ZafUtil objutil = new Librerias.ZafUtil.ZafUtil();
            if (isSelected)
            {
                setBorder(new javax.swing.border.LineBorder(javax.swing.UIManager.getDefaults().getColor("Button.focus"), 1));
            }
            else
            {
                setBorder(null);
            }
            this.setText("" + objutil.redondeo(dblSubPro, intNumDecimales));
            this.setFont(new java.awt.Font("SansSerif", 0, 11));
            return this;
        }
    }

    /**
     * Esta función permite determinar si un código ya existe en la base de datos. En algunos casos es necesario
     * almacenar códigos sin que se repitan. Por ejemplo, un RUC no puede repetirse para mï¿½s de una empresa. Para
     * estos casos se puede utilizar ésta función evitando así que se duplique el RUC. 
     * @param padre El formulario padre para el cuadro de mensaje. Puede ser cualquier objeto que
     * herede de la clase <I>Component</I>. Por ejemplo: JFrame, JInternalFrame
     * @param stringConexion La cadena que contiene información para conectarse a la base de datos.
     * @param usuario El usuario para conectarse a la base de datos.
     * @param clave La clave del usuario para conectarse a la base de datos.
     * @param sentenciaSQL La sentencia SQL que sirve para validar el campo.
     * @return true: Si el código todavía no existe.
     * <BR>false: En el caso contrario.
     */
    public boolean isCodigoUnico(java.awt.Component padre, String stringConexion, String usuario, String clave, String sentenciaSQL)
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        boolean blnRes=true;
        try
        {
            con=java.sql.DriverManager.getConnection(stringConexion,usuario,clave);
            if (con!=null)
            {
                stm=con.createStatement();	
                rst=stm.executeQuery(sentenciaSQL);
                if (rst.next())
                    blnRes=false;
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
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite obtener el código del registro que se especifique en la sentecnia SQL.
     * En la mayoría de los casos es necesario obtener el código del último registro de una tabla.
     * Esta función también puede ser utilizada para obtener el primero, segundo, tercero o cualquier
     * registro que se indique en la setencia SQL.
     * @param padre El formulario padre para el cuadro de mensaje. Puede ser cualquier objeto que
     * herede de la clase <I>Component</I>. Por ejemplo: JFrame, JInternalFrame
     * @param stringConexion La cadena que contiene información para conectarse a la base de datos.
     * @param usuario El usuario para conectarse a la base de datos.
     * @param clave La clave del usuario para conectarse a la base de datos.
     * @param sentenciaSQL La sentencia SQL que sirve para obtener el número de registro.
     * @return Puede retornar uno de los siguientes valores:
     * <BR>-1: Si ocurrió un error al ejecutar el método.
     * <BR> 0: Si no hay registros.
     * <BR> n: El número de registro.
     */
    public int getNumeroRegistro(java.awt.Component padre, String stringConexion, String usuario, String clave, String sentenciaSQL)
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        int intRes=0;
        try
        {
            con=java.sql.DriverManager.getConnection(stringConexion,usuario,clave);
            if (con!=null)
            {
                stm=con.createStatement();
                rst=stm.executeQuery(sentenciaSQL);
                if (rst.next())
                    intRes=rst.getInt(1);
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
            intRes=-1;
            this.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e)
        {
            intRes=-1;
            this.mostrarMsgErr_F1(padre, e);
        }
        return intRes;
    }

    /**
     * Esta función permite ubicarse en un elemento del combo. Hay que recordar que cada combo tiene un vector
     * asociado donde se almacenan los códigos que corresponden a los elementos que aparecen en el combo. 
     * <BR>Por ejemplo:
     *          <CENTER>
     *          <TABLE BORDER=1>
     *              <TR><TD><I>Vector</I></TD><TD><I>Combo</I></TD></TR>
     *              <TR><TD>15</TD><TD>Soltero</TD></TR>
     *              <TR><TD>16</TD><TD>Casado</TD></TR>
     *              <TR><TD>17</TD><TD>Viudo</TD></TR>
     *              <TR><TD>18</TD><TD>Divorciado</TD></TR>
     *          </TABLE>
     *          </CENTER>
     * <BR>15,16,17 y 18 representan los código de Soltero, Casado, Viudo y Divorciado respectivamente
     * <BR>Si el elemento buscado no se encuentra en el combo la función no selecciona ningún elemento.
     * Es decir, ubica el combo en el elemento -1.
     * @param combo El combo que contiene las descripciones que ve el usuario.
     * @param vector El vector que almacena los códigos.
     * @param elemento El elemento que se va a buscar en el vector.
     * @return true: Si se pudo localizar el elemento en el combo.
     * <BR>false: En el caso contrario.
     * <BR>Los motivos por los que puede devolver <I>false</I> son los siguientes:
     * <BR> Si el combo no tiene elementos.
     * <BR> Si no se encontró el elemento buscado en el vector.
     */
    public boolean setItemCombo(javax.swing.JComboBox combo, java.util.Vector vector, int elemento)
    {
        boolean blnRes=false;
        combo.setSelectedIndex(-1);
        if (combo.getItemCount()>0)
        {
            for (int i=0;i<vector.size();i++)
            {
                if (Integer.parseInt(vector.get(i).toString())==elemento)
                {
                    combo.setSelectedIndex(i);
                    blnRes=true;
                    break;
                }
            }
        }
        return blnRes;
    }

    /**
     * Esta función permite ubicarse en un elemento del combo. Hay que recordar que cada combo tiene un vector
     * asociado donde se almacenan los códigos que corresponden a los elementos que aparecen en el combo. 
     * <BR>Por ejemplo:
     *          <CENTER>
     *          <TABLE BORDER=1>
     *              <TR><TD><I>Vector</I></TD><TD><I>Combo</I></TD></TR>
     *              <TR><TD>15</TD><TD>Soltero</TD></TR>
     *              <TR><TD>16</TD><TD>Casado</TD></TR>
     *              <TR><TD>17</TD><TD>Viudo</TD></TR>
     *              <TR><TD>18</TD><TD>Divorciado</TD></TR>
     *          </TABLE>
     *          </CENTER>
     * <BR>15,16,17 y 18 representan los código de Soltero, Casado, Viudo y Divorciado respectivamente
     * <BR>Si el elemento buscado no se encuentra en el combo la función no selecciona ningún elemento.
     * Es decir, ubica el combo en el elemento -1.
     * @param combo El combo que contiene las descripciones que ve el usuario.
     * @param vector El vector que almacena los códigos.
     * @param elemento El elemento que se va a buscar en el vector.
     * @return true: Si se pudo localizar el elemento en el combo.
     * <BR>false: En el caso contrario.
     * <BR>Los motivos por los que puede devolver <I>false</I> son los siguientes:
     * <BR> Si el combo no tiene elementos.
     * <BR> Si no se encontró el elemento buscado en el vector.
     */
    public boolean setItemCombo(javax.swing.JComboBox combo, java.util.Vector vector, String elemento)
    {
        boolean blnRes=false;
        combo.setSelectedIndex(-1);
        if (combo.getItemCount()>0)
        {
            for (int i=0;i<vector.size();i++)
            {
                if (vector.get(i).toString().equals(elemento))
                {
                    combo.setSelectedIndex(i);
                    blnRes=true;
                    break;
                }
            }
        }
        return blnRes;
    }
    
    /**
     * Este metodo recibe un JTable y básicamente se encarga de añaadir una fila si el usuario
     * presiona la tecla ENTER o eliminar una fila si el usuario presiona ESCAPE.
     * Para añadir una fila con ENTER sólo bastará estar en la última fila de JTable, en
     * cualquier celda, la única observación es que debe pertenecer a la última fila.
     * Para eliminar una fila con la tecla ESCAPE hay que tener en cuenta que la fila no
     * debe contener ningún valor en ninguna de sus celdas.
     * Author: Idi Reyes Marcillo
     * Ejemplo de uso:
     *     <PRE>
     *        ZafUtil objutil = new ZafUtil();
     *        objutil.AddDelLinTbl(tabla);
     *     </PRE>
     * @param objTbl JTable al que se desea añadir o quitar filas
     * @deprecated Ver Clase Librerias.ZafAddDelLinTbl.ZafAddDelLinTbl
     */
    public void AddDelLinTbl(javax.swing.JTable objTbl)
    {
        objTbl.setSurrendersFocusOnKeystroke(true);
        javax.swing.KeyStroke enter = javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER,0);
        /*  Registrando el KeyBoard Listener para la tecla enter
         *  para ke se agregue una fila cuando la tecla enter es presionada
         */
        objTbl.registerKeyboardAction( new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                javax.swing.JTable  tblAddDelLin =  (javax.swing.JTable)evt.getSource();
                int intRowSel = tblAddDelLin.getSelectedRow();
                javax.swing.table.DefaultTableModel dafMod = (javax.swing.table.DefaultTableModel)tblAddDelLin.getModel();
                int colSel=0;
                if (((tblAddDelLin.getRowCount() - 1) == tblAddDelLin.getSelectedRow())){
                    dafMod.addRow(new java.util.Vector());
                }
                
                if(tblAddDelLin.getSelectedColumn()+1==tblAddDelLin.getColumnCount()){
                    colSel=0;
                    intRowSel++;
                }else{
                    colSel=tblAddDelLin.getSelectedColumn()+1;
                }
                tblAddDelLin.changeSelection(intRowSel, colSel, false, false);
                //Tbl.editCellAt(intRowSel,colSel);
            }
        }, "enter", enter,javax.swing.JComponent.WHEN_FOCUSED );
        
        objTbl.addKeyListener(  new java.awt.event.KeyListener(){
            public void keyPressed(java.awt.event.KeyEvent e) {
                javax.swing.JTable  tblAddDelLin =  (javax.swing.JTable)e.getSource();
                int tecla = e.getKeyChar();
                javax.swing.table.DefaultTableModel dafMod = (javax.swing.table.DefaultTableModel)tblAddDelLin.getModel();
                
                if (tecla == 27)
                {
                    if( tblAddDelLin.getSelectedRow()!=-1 && tblAddDelLin.getSelectedColumn()!=-1 )
                    {
                        if(tblAddDelLin.getRowCount()>1)
                        {
                            int intRowSel = tblAddDelLin.getSelectedRow();
                            boolean noEliminar = false;
                            for(int idx=0 ; idx<tblAddDelLin.getColumnCount(); idx++)
                            {
                                if(dafMod.getColumnClass(idx)==String.class || dafMod.getColumnClass(idx)==Integer.class || dafMod.getColumnClass(idx)==Double.class || dafMod.getColumnClass(idx)==Float.class)
                                {
                                    if(dafMod.getValueAt(intRowSel, idx)!=null)
                                    {
                                        if(!dafMod.getValueAt(intRowSel, idx).toString().equals(""))
                                        {
                                            noEliminar=true;
                                        }
                                    }
                                }
                            }
                            if(!noEliminar)
                            {
                                dafMod.removeRow(intRowSel);
                                tblAddDelLin.setRowSelectionInterval((tblAddDelLin.getRowCount()==intRowSel)?intRowSel-1:intRowSel,(tblAddDelLin.getRowCount()==intRowSel)?intRowSel-1:intRowSel);
                            }
                        }
                    }
                }
            }
            
            public void keyTyped(java.awt.event.KeyEvent e)
            {
                
            }
            
            public void keyReleased(java.awt.event.KeyEvent e)
            {
                
            }
        });//fin tb1 addkeylistener
    }
    
    public boolean verifyTbl(javax.swing.JTable tbl_a_Validar, int[] ColsObligatory)
    {
        //tbl_a_Validar.
        javax.swing.table.DefaultTableModel modTbl = (javax.swing.table.DefaultTableModel)tbl_a_Validar.getModel();
        boolean noEliminar = false;
        boolean isRowEmpty = true; //Verdadero si la fila esta vacia por completo
        boolean isColObligatoryLlenas = true; //Falso si las columnas obligatorias estan vacias
        tbl_a_Validar.removeEditor();
        
        for(int idxRow=0; idxRow < tbl_a_Validar.getRowCount() && isColObligatoryLlenas; idxRow++)
        {
            isRowEmpty = true; //Verdadero si la fila esta vacia por completo
            for(int idxCol=0 ; idxCol<tbl_a_Validar.getColumnCount(); idxCol++)
            {
                if(modTbl.getColumnClass(idxCol)==String.class  ||
                modTbl.getColumnClass(idxCol)==Integer.class ||
                modTbl.getColumnClass(idxCol)==Double.class  ||
                modTbl.getColumnClass(idxCol)==Float.class)
                {
                    if(modTbl.getValueAt(idxRow, idxCol)!=null)
                    {
                        if(modTbl.getColumnClass(idxCol)==String.class)
                        {
                            if(!modTbl.getValueAt(idxRow, idxCol).toString().equals(""))
                            {
                                isRowEmpty=false; //Significa que la fila tiene datos en por lo menos un campo
                                break;
                            }
                        }
                        if(modTbl.getColumnClass(idxCol)==Integer.class)
                        {
                            if(!modTbl.getValueAt(idxRow, idxCol).toString().equals(""))
                            {
                                int intValIn =  Integer.parseInt(modTbl.getValueAt(idxRow, idxCol).toString());
                                if(intValIn!=0)
                                {
                                    isRowEmpty=false; //Significa que la fila tiene datos en por lo menos un campo
                                    break;
                                }
                            }
                        }
                        if(modTbl.getColumnClass(idxCol)==Double.class)
                        {
                            if(!modTbl.getValueAt(idxRow, idxCol).toString().equals(""))
                            {
                                double intValIn =  Double.parseDouble(modTbl.getValueAt(idxRow, idxCol).toString());
                                if(intValIn!=0)
                                {
                                    isRowEmpty=false; //Significa que la fila tiene datos en por lo menos un campo
                                    break;
                                }
                            }
                        }
                        if(modTbl.getColumnClass(idxCol)==Float.class)
                        {
                            if(!modTbl.getValueAt(idxRow, idxCol).toString().equals(""))
                            {
                                float intValIn =  Float.parseFloat(modTbl.getValueAt(idxRow, idxCol).toString());
                                if(intValIn!=0)
                                {
                                    isRowEmpty=false; //Significa que la fila tiene datos en por lo menos un campo
                                    break;
                                }
                            }
                        }                        
                    }
                }
            }//Fin del for de idx
            if (isRowEmpty)
            {
                // La fila estuvo vacia y hay ke eliminarla
                modTbl.removeRow(idxRow);
                idxRow--;
            }
            else
            {
                // La fila tiene datos hay ke validar si a
                // los campos obligatorios les faltan datos
                for(int idxColObli=0; idxColObli<ColsObligatory.length; idxColObli++)
                {
                    if(modTbl.getValueAt(idxRow, ColsObligatory[idxColObli])==null ||
                    modTbl.getValueAt(idxRow, ColsObligatory[idxColObli]).toString().equals(""))
                    {
                        isColObligatoryLlenas=false; //Significa que alguno de los campos obligatorios esta vacio
                        tbl_a_Validar.requestFocus();
                        tbl_a_Validar.changeSelection(idxRow, ColsObligatory[idxColObli], false, false);
                        tbl_a_Validar.editCellAt(idxRow, ColsObligatory[idxColObli]);
                        break;
                    }
                }
            }
        }//Fin del For de idxRow
        if(tbl_a_Validar.getRowCount()>0 && isColObligatoryLlenas)
        {
            tbl_a_Validar.changeSelection(0, 0, false, false);
        }
        return isColObligatoryLlenas ;
    }
    
    /**
     * Esta función permite formatear números de acuerdo al formato especificado. Al formatear un
     * número se realiza un proceso de redondeo cuando la cantidad de decimales a mostrar es menor
     * que la cantidad de decimales del número a formatear.
     * <BR>Por ejemplo:
     *          <CENTER>
     *          <TABLE BORDER=1>
     *              <TR><TD><I>Número</I></TD><TD><I>Formato</I></TD><TD><I>Resultado</I></TD></TR>
     *              <TR><TD>46.87568</TD><TD>###.###</TD><TD>46.876</TD></TR>
     *              <TR><TD>15.3467</TD><TD>###.##</TD><TD>15.35</TD></TR>
     *              <TR><TD>15</TD><TD>##0.00</TD><TD>15.00</TD></TR>
     *              <TR><TD>15</TD><TD>$##0.00</TD><TD>$15.00</TD></TR>
     *          </TABLE>
     *          </CENTER>
     * @param numero El número que se desea formatear.
     * @param formato El formato que se debe aplicar al número. Podría ser "###,###.###", "###.##", "$###,###.##", "\u00a5###,###.###", etc.
     * @param mostrarCero Se aplica si el número a formatear es un cero. 
     * Puede tomar los siguientes valores:
     * <BR>true: La función devolverá el cero formateado.
     * <BR>false: La función devolverá una cadena vacía.
     * @return El número formateado.
     * <BR>Nota.- Si hay un error al aplicar un formato la función devolverá "[ERROR]".
     */
    public String formatearNumero(double numero, String formato, boolean mostrarCero)
    {
        java.text.DecimalFormat objDecFor;
        int intNumDec;
        String strRes="";
        try
        {
            objDecFor=new java.text.DecimalFormat(formato);
            intNumDec=objDecFor.getMaximumFractionDigits();
            if (numero==0)
            {
                if (mostrarCero)
                {
                    //Formatear el número de acuerdo al formato.
                    strRes=objDecFor.format(numero);
                }
            }
            else
            {
                //Redondear el número a n decimales.
                numero=redondear(numero,intNumDec);
                //Formatear el número de acuerdo al formato.
                strRes=objDecFor.format(numero);
            }
        }
        catch (NumberFormatException e)
        {   
            strRes="[ERROR]";
        }
        catch (Exception e)
        {
            strRes="[ERROR]";
        }
        return strRes;
    }
    
     /**
     * Esta función permite formatear números de acuerdo al formato especificado. Al formatear un
     * número se realiza un proceso de redondeo cuando la cantidad de decimales a mostrar es menor
     * que la cantidad de decimales del número a formatear.
     * <BR>Por ejemplo:
     *          <CENTER>
     *          <TABLE BORDER=1>
     *              <TR><TD><I>Número</I></TD><TD><I>Formato</I></TD><TD><I>Resultado</I></TD></TR>
     *              <TR><TD>46.87568</TD><TD>###.###</TD><TD>46.876</TD></TR>
     *              <TR><TD>15.3467</TD><TD>###.##</TD><TD>15.35</TD></TR>
     *              <TR><TD>15</TD><TD>##0.00</TD><TD>15.00</TD></TR>
     *              <TR><TD>15</TD><TD>$##0.00</TD><TD>$15.00</TD></TR>
     *          </TABLE>
     *          </CENTER>
     * @param numero El número que se desea formatear.
     * @param formato El formato que se debe aplicar al número. Podría ser "###,###.###", "###.##", "$###,###.##", "\u00a5###,###.###", etc.
     * @param mostrarCero Se aplica si el número a formatear es un cero. 
     * Puede tomar los siguientes valores:
     * <BR>true: La función devolverá el cero formateado.
     * <BR>false: La función devolverá una cadena vacía.
     * @return El número formateado.
     * <BR>Nota.- Si hay un error al aplicar un formato la función devolverá "[ERROR]".
     */
    public String formatearNumero(String numero, String formato, boolean mostrarCero)
    {
        java.text.DecimalFormat objDecFor;
        double dblNum;
        int intNumDec;
        String strRes="";
        try
        {
            objDecFor=new java.text.DecimalFormat(formato);
            intNumDec=objDecFor.getMaximumFractionDigits();
            if (!numero.equals(""))
            {
                dblNum=Double.parseDouble(numero);
                if (dblNum==0)
                {
                    if (mostrarCero)
                    {
                        //Formatear el número de acuerdo al formato.
                        strRes=objDecFor.format(dblNum);
                    }
                }
                else
                {
                    //Redondear el número a n decimales.
                    dblNum=redondear(Double.parseDouble(numero),intNumDec);
                    //Formatear el número de acuerdo al formato.
                    strRes=objDecFor.format(dblNum);
                }
            }
        }
        catch (NumberFormatException e)
        {
            strRes="[ERROR]";
        }
        catch (Exception e)
        {
            strRes="[ERROR]";
        }
        return strRes;
    }
    
    /**
     * Esta función permite formatear fechas de acuerdo al formato especificado.
     * <BR>Por ejemplo:
     *          <CENTER>
     *          <TABLE BORDER=1>
     *              <TR><TD><I>Fecha</I></TD><TD><I>Formato</I></TD><TD><I>Resultado</I></TD></TR>
     *              <TR><TD>2005-11-03</TD><TD>dd.MM.yy</TD><TD>03.11.05</TD></TR>
     *              <TR><TD>2005-11-03</TD><TD>dd.MMM.yyyy</TD><TD>03.Nov.2005</TD></TR>
     *              <TR><TD>2005-11-03</TD><TD>dd/MM/yyyy</TD><TD>03/11/2005</TD></TR>
     *              <TR><TD>2005-11-03</TD><TD>dd/MMM/yyyy</TD><TD>03/Nov/2005</TD></TR>
     *          </TABLE>
     *          </CENTER>
     * @param fecha La fecha que se desea formatear.
     * @param formato El formato que se debe aplicar a la fecha. Podría ser "dd.MM.yy", "dd/MMM/yyyy", etc.
     * @return La fecha formateada.
     * <BR>Nota.- Si hay un error al aplicar un formato la función devolverá "[ERROR]".
     */
    public String formatearFecha(java.util.Date fecha, String formato)
    {
        java.text.SimpleDateFormat objSimDatFor;
        String strRes="";
        try
        {
            objSimDatFor=new java.text.SimpleDateFormat(formato);
            //Formatear la fecha de acuerdo al formato.
            strRes=objSimDatFor.format(fecha);
            objSimDatFor=null;
        }
        catch (IllegalArgumentException e)
        {
            strRes="[ERROR]";
        }
        catch (Exception e)
        {
            strRes="[ERROR]";
        }
        return strRes;
    }
 
    /**
     * Esta función permite formatear fechas de acuerdo al formato especificado.
     * <BR>Por ejemplo:
     *          <CENTER>
     *          <TABLE BORDER=1>
     *              <TR><TD><I>Fecha</I></TD><TD><I>Formato actual</I></TD><TD><I>Nuevo formato</I></TD><TD><I>Resultado</I></TD></TR>
     *              <TR><TD>2005-11-03</TD><TD>yyyy-MM-dd</TD><TD>dd.MM.yy</TD><TD>03.11.05</TD></TR>
     *              <TR><TD>2005/11/03</TD><TD>yyyy/MM/dd</TD><TD>dd.MMM.yyyy</TD><TD>03.Nov.2005</TD></TR>
     *              <TR><TD>03-05-11</TD><TD>dd-yy-MM</TD><TD>dd/MM/yyyy</TD><TD>03/11/2005</TD></TR>
     *              <TR><TD>05-11-03</TD><TD>yy-MM-dd</TD><TD>dd/MMM/yyyy</TD><TD>03/Nov/2005</TD></TR>
     *          </TABLE>
     *          </CENTER>
     * @param fecha La fecha que se desea formatear.
     * @param formatoActual El formato en el que se encuentra la fecha. Podría ser "dd.MM.yy", "dd/MMM/yyyy", etc.
     * @param nuevoFormato El formato que se debe aplicar a la fecha. Podría ser "dd.MM.yy", "dd/MMM/yyyy", etc.
     * @return La fecha formateada.
     * <BR>Nota.- Si hay un error al aplicar un formato la función devolverá "[ERROR]".
     */
    public String formatearFecha(String fecha, String formatoActual, String nuevoFormato)
    {
        java.text.SimpleDateFormat objSimDatFor;
        java.util.Date datRes;
        String strRes="";
        try
        {
            //Obtener el objeto Date.
            objSimDatFor=new java.text.SimpleDateFormat(formatoActual);
            datRes=objSimDatFor.parse(fecha);
            //Establecer el nuevo formato.
            objSimDatFor.applyPattern(nuevoFormato);
            //Formatear la fecha de acuerdo al formato.
            strRes=objSimDatFor.format(datRes);
            objSimDatFor=null;
            datRes=null;
        }
        catch (java.text.ParseException e)
        {
            strRes="[ERROR]";
        }
        catch (IllegalArgumentException e)
        {
            strRes="[ERROR]";
        }
        catch (Exception e)
        {
            strRes="[ERROR]";
        }
        return strRes;
    }
    
    public int getMesFecha(String fecha, String formatoActual, String nuevoFormato)
    {
        java.text.SimpleDateFormat objSimDatFor;
        java.util.Date datRes;
        String strRes="";
        int intMes=-1;
        try
        {
           objSimDatFor=new java.text.SimpleDateFormat(formatoActual);
           datRes=objSimDatFor.parse(fecha);
           intMes=datRes.getMonth();
        }
        catch (java.text.ParseException e)
        {
           intMes=-1;
        }
        catch (IllegalArgumentException e)
        {
            intMes=-1;
        }
        catch (Exception e)
        {
           intMes=-1;
        }
        return intMes;
    }
    
    public int getMesAnio(String fecha, String formatoActual, String nuevoFormato)
    {
        java.text.SimpleDateFormat objSimDatFor;
        java.util.Date datRes;
        String strRes="";
        int intMes=-1;
        try
        {
           objSimDatFor=new java.text.SimpleDateFormat(formatoActual);
           datRes=objSimDatFor.parse(fecha);
           intMes=(datRes.getYear()+1900);
        }
        catch (java.text.ParseException e)
        {
           intMes=-1;
        }
        catch (IllegalArgumentException e)
        {
            intMes=-1;
        }
        catch (Exception e)
        {
           intMes=-1;
        }
        return intMes;
    }
    
    /**
     * Esta función obtiene la fecha y hora del servidor de base de datos. La fecha puede ser obtenida
     * en el formato que lo devuelve la base de datos o si se desea se puede aplicar otro formato.
     * @param stringConexion La cadena que contiene información para conectarse a la base de datos.
     * @param usuario El usuario para conectarse a la base de datos.
     * @param clave La clave del usuario para conectarse a la base de datos.
     * @param sentenciaSQL La sentencia SQL que sirve para obtener la fecha de la base de datos.
     * @param formato El formato que se aplicará a la fecha. Si se recibe <I>null</I> no se formateará la
     * fecha y se la devolverá tal y como la base de datos la devuelve.
     * @return La fecha y hora de la base de datos.
     * <BR>Nota.- Si hay un error al obtener la fecha la función devolverá <I>null</I>.
     */
    public String getFechaServidor(String stringConexion, String usuario, String clave, String sentenciaSQL, String formato)
    {
        java.text.SimpleDateFormat objSimDatFor;
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
        String strRes="";
        try
        {
            con=java.sql.DriverManager.getConnection(stringConexion,usuario,clave);
            if (con!=null)
            {
                stm=con.createStatement();
                rst=stm.executeQuery(sentenciaSQL);
                if (rst.next())
                {
                    if (formato==null)
                    {
                        //Devolver la fecha tal y como se la obtiene de la base de datos.
                        strRes=rst.getTimestamp(1).toString();
                    }
                    else
                    {
                        //Formatear la fecha de acuerdo al formato recibido como parámetro.
                        objSimDatFor=new java.text.SimpleDateFormat(formato);
                        strRes=objSimDatFor.format((java.util.Date)rst.getTimestamp(1));
                    }
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
            strRes=null;
        }
        catch (IllegalArgumentException e)
        {
            strRes=null;
        }
        catch (Exception e)
        {
            strRes=null;
        }
        return strRes;        
    }
    
    /**
     * Esta función obtiene la fecha y hora del servidor de base de datos.
     * @param stringConexion La cadena que contiene información para conectarse a la base de datos.
     * @param usuario El usuario para conectarse a la base de datos.
     * @param clave La clave del usuario para conectarse a la base de datos.
     * @param sentenciaSQL La sentencia SQL que sirve para obtener la fecha de la base de datos.
     * @return La fecha y hora de la base de datos.
     * <BR>Nota.- Si hay un error al obtener la fecha la función devolverá <I>null</I>.
     */
    public java.util.Date getFechaServidor(String stringConexion, String usuario, String clave, String sentenciaSQL)
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
        java.util.Date datRes=null;
        try
        {
            con=java.sql.DriverManager.getConnection(stringConexion,usuario,clave);
            if (con!=null)
            {
                stm=con.createStatement();
                rst=stm.executeQuery(sentenciaSQL);
                if (rst.next())
                    datRes=(java.util.Date)rst.getTimestamp(1);
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
            datRes=null;
        }
        catch (Exception e)
        {
            datRes=null;
        }
        return datRes;        
    }

   /**
     * Esta función codifica la cadena que recibe como argumento para que pueda ser enviada a la base de datos.
     * La idea de ésta función es evitar problemas generados por el uso de caracteres especiales. Como un ejemplo
     * típico tenemos el uso de comillas simples en una cadena lo que provoca problemas a la hora de grabar
     * dicho campo en la base de datos.
     * @param cadena La cadena que se desea codificar.
     * @return La cadena codificada en función de las siguientes consideraciones:
     * <UL>
     * <LI> Si la cadena que se recibe es null la función devuelve "Null".
     * <LI> Si la cadena que se recibe contiene una cadena vacía la función devuelve "Null".
     * <LI> Si la cadena que se recibe contiene una cadena no vacía la función devuelve la cadena codificada.
     * </UL>
     */
    public String codificar(Object cadena)
    {
        if (cadena==null)
            return "Null";
        if (cadena.toString().equals(""))
            return "Null";
        return "'" + cadena.toString().replaceAll("'", "''") + "'";
        
     
    }
      // validar que no tenga el nombre de cliente "tab" o espacios al inicio y al final
     public String remplazarEspacios(Object cadena)
    {
        if (cadena==null)
            return "Null";
        if (cadena.toString().equals(""))
            return "Null";
       // cadena=cadena.toString().replace("\t","");
       cadena= cadena.toString().replaceAll("\\s+"," ");
       cadena= cadena.toString().trim();
        
       return  cadena.toString();
       //cadena.toString().replaceAll("\\A\\s+(.*?)\\s+\\Z","");
          
    }
   /**
     * Esta función codifica la cadena que recibe como argumento para que pueda ser enviada a la base de datos.
     * La idea de ésta función es evitar problemas generados por el uso de caracteres especiales. Como un ejemplo
     * típico tenemos el uso de comillas simples en una cadena lo que provoca problemas a la hora de grabar
     * dicho campo en la base de datos.
     * @param cadena La cadena que se desea codificar.
     * @param tipo Tipo de retorno que se devolverá cuando la cadena sea null o esté vacía.
     * <BR>Puede tomar los siguientes valores:
     * <BR>
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo de retorno</I></TD><TD><I>Descripción</I></TD></TR>
     *     <TR><TD>0</TD><TD>Cadena de texto que codificada puede retornar "Null".</TD></TR>
     *     <TR><TD>1</TD><TD>Cadena de texto que codificada puede retornar una cadena vacía.</TD></TR>
     *     <TR><TD>2</TD><TD>Cadena numérica que codificada puede retornar "Null".</TD></TR>
     *     <TR><TD>3</TD><TD>Cadena numérica que codificada puede retornar "0".</TD></TR>
     * </TABLE>
     * </CENTER>
     * <BR>Ejemplos:
     * <UL>
     * <LI> Utilice tipo=0 cuando la cadena a codificar sea una cadena de texto y desee que la función devuelva "Null" si
     * la cadena está vacía o es Null.
     * <LI> Utilice tipo=3 cuando la cadena a codificar sea un número y desee que la función devuelva "0" si la cadena
     * está vacía o es Null.
     * </UL>
     * @return La cadena que retorna esta función depende del parámetro "tipo".
     * <BR>
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo de retorno</I></TD><TD><I>Descripción</I></TD></TR>
     *     <TR><TD>0</TD><TD>Si la cadena de texto que se recibe es null o contiene una cadena vacía la función devuelve "Null".</TD></TR>
     *     <TR><TD>1</TD><TD>Si la cadena de texto que se recibe es null o contiene una cadena vacía la función devuelve "''". Es decir una cadena vacía.</TD></TR>
     *     <TR><TD>2</TD><TD>Si la cadena numérica que se recibe es null o contiene una cadena vacía la función devuelve "Null".</TD></TR>
     *     <TR><TD>3</TD><TD>Si la cadena numérica que se recibe es null o contiene una cadena vacía la función devuelve "0".</TD></TR>
     * </TABLE>
     * </CENTER>
     * <BR>Si la cadena no está vacía y es diferente a Null devuelve la cadena codificada.
     * <BR>Nota.- En el caso de campos de texto no es necesario que en la sentencia SQL se encierre el campo 
     * entre comillas simples porque ésta función ya devuelve la cadena encerrada entre comillas simples.
     */
    public String codificar(Object cadena, int tipo)
    {
        String strAux="";
        switch (tipo)
        {
            case 0:
                //CADENA_NULL
                strAux="Null";
                break;
            case 1:
                //CADENA_VACIA
                strAux="''";
                break;
            case 2:
                //NUMERO_NULL
                strAux="Null";
                break;
            case 3:
                //NUMERO_CERO
                strAux="0";
                break;
        }
        if (cadena==null)
            return strAux;
        if (cadena.toString().equals(""))
            return strAux;
        if (tipo==2 || tipo==3)
            return cadena.toString();
        else
            return "'" + cadena.toString().replaceAll("'", "''") + "'";
    }
 
    /**
     * Esta función convierte una cadena que contiene una fecha a tipo Date. Se debe indicar el
     * formato en el que se encuentra la fecha para que la función haga la conversión correctamente.
     * <BR>Por ejemplo:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Cadena</I></TD><TD><I>Formato</I></TD><I>&nbsp;</I></TR>
     *     <TR><TD>2005/05/03</TD><TD>yyyy/MM/dd</TD><TD>Representa el 03 de Mayo del 2005</TD></TR>
     *     <TR><TD>18/07/2009</TD><TD>dd/MM/yyyy</TD><TD>Representa el 18 de Julio del 2009</TD></TR>
     *     <TR><TD>08/05/2006</TD><TD>MM/dd/yyyy</TD><TD>Representa el 05 de Agosto del 2006</TD></TR>
     *     <TR><TD>08/05/2006</TD><TD>MM/dd/yyyy</TD><TD>Representa el 05 de Agosto del 2006</TD></TR>
     * </TABLE>
     * </CENTER>
     * <I>Caso especial:</I>
     * <BR>Puede ser que la función reciba más días de los que tiene el mes a procesar. Si éste es
     * el caso, la función sumará los días en exceso al mes siguiente hasta completar los días en 
     * exceso.
     * <BR>Por ejemplo:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Cadena</I></TD><TD><I>Formato</I></TD><TD><I>&nbsp;</I></TD></TR>
     *     <TR><TD>2005/07/32</TD><TD>yyyy/MM/dd</TD><TD>Representa el 01 de Agosto del 2005</TD></TR>
     *     <TR><TD>2005/07/33</TD><TD>yyyy/MM/dd</TD><TD>Representa el 02 de Agosto del 2005</TD></TR>
     *     <TR><TD>2005/07/100</TD><TD>yyyy/MM/dd</TD><TD>Representa el 08 de Octubre del 2005</TD></TR>
     *     <TR><TD>2005/07/366</TD><TD>yyyy/MM/dd</TD><TD>Representa el 01 de Julio del 2006</TD></TR>
     * </TABLE>
     * </CENTER>
     * @param fecha La cadena que se desea convertir a Date.
     * @param formato El formato en el que se encuentra la fecha. Podría ser "dd/MM/yyyy", "MM/dd/yyyy", "yyyy/MM/dd", etc.
     * @return La cadena convertida a tipo Date.
     * <BR>Nota.- Si la cadena no corresponde a una fecha la función devolverá <I>null</I>.
     */
    public java.util.Date parseDate(String fecha, String formato)
    {
        java.text.SimpleDateFormat objSimDatFor;
        java.util.Date datRes;
        try
        {
            objSimDatFor=new java.text.SimpleDateFormat(formato);
            datRes=objSimDatFor.parse(fecha);
        }
        catch (java.text.ParseException e)
        {
            datRes=null;
        }
        catch (Exception e)
        {
            datRes=null;
        }
        return datRes;
    }
    
    /**
     * Esta función convierte un objeto que contiene un número a tipo "double".
     * <BR>Se pueden presentar los siguientes casos:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Objeto</I></TD><TD><I>Resultado</I></TR>
     *     <TR><TD>null</TD><TD>0</TD></TR>
     *     <TR><TD>"24.96"</TD><TD>24.96</TD></TR>
     *     <TR><TD>"hola"</TD><TD>0</TD></TR>
     *     <TR><TD>""</TD><TD>0</TD></TR>
     * </TABLE>
     * </CENTER>
     * @param numero El objeto que contiene el número que se desea convertir a "double".
     * @return El objeto convertido a tipo "double".
     */
    public double parseDouble(Object numero)
    {
        try
        {
            if (numero==null)
                return 0;
            else
            {
                return Double.parseDouble(numero.toString());
            }
        }
        catch (NumberFormatException e)
        {
            return 0;
        }
    }
    
    /**
     * Esta función convierte una cadena que contiene un número a tipo "double".
     * <BR>Se pueden presentar los siguientes casos:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Cadena</I></TD><TD><I>Resultado</I></TR>
     *     <TR><TD>null</TD><TD>0</TD></TR>
     *     <TR><TD>"24.96"</TD><TD>24.96</TD></TR>
     *     <TR><TD>"hola"</TD><TD>0</TD></TR>
     *     <TR><TD>""</TD><TD>0</TD></TR>
     * </TABLE>
     * </CENTER>
     * @param numero La cadena que contiene el número que se desea convertir a "double".
     * @return La cadena convertida a tipo "double".
     */
    public double parseDouble(String numero)
    {
        try
        {
            if (numero==null)
                return 0;
            else
            {
                return Double.parseDouble(numero);
            }
        }
        catch (NumberFormatException e)
        {
            return 0;
        }
    }
    
    /**
     * Esta función redondea un número a los decimales especificados.
     * @param numero El número que se desea redondear.
     * @param decimales El número de decimales al que se desea redondear.
     * @return El número redondeado.
     * <BR>Nota.- Si se produce algún error se retorna el mismo número.
     */
    public double redondear(double numero, int decimales)
    {
        try
        {
            //La suma "3.65+0.05" debería dar "3.70". Pero Java genera "3.6999999999999997".
            //Para evitar éste problema se debe trabajar con la clase "java.math.BigDecimal".
            java.math.BigDecimal objBigDec=new java.math.BigDecimal("" + numero);
            objBigDec=objBigDec.setScale(decimales,java.math.BigDecimal.ROUND_HALF_UP);
            numero=objBigDec.doubleValue();
            objBigDec=null;
        }
        catch (NumberFormatException e)
        {
            return numero;
        }
        catch (ArithmeticException e)
        {
            return numero;
        }
        return numero;
    }
    
    /**
     * Esta función redondea un número a los decimales especificados.
     * @param numero La cadena que contiene el número que se desea redondear.
     * @param decimales El número de decimales al que se desea redondear.
     * @return El número redondeado.
     * <BR>Nota.- Si se produce algún error se retorna -1.
     */
    public double redondear(String numero, int decimales)
    {
        double dblNum;
        try
        {
            dblNum=Double.parseDouble(numero);
            //La suma "3.65+0.05" debería dar "3.70". Pero Java genera "3.6999999999999997".
            //Para evitar éste problema se debe trabajar con la clase "java.math.BigDecimal".
            java.math.BigDecimal objBigDec=new java.math.BigDecimal("" + numero);
            objBigDec=objBigDec.setScale(decimales,java.math.BigDecimal.ROUND_HALF_UP);
            dblNum=objBigDec.doubleValue();
            objBigDec=null;
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
        catch (ArithmeticException e)
        {
            return -1;
        }
        return dblNum;
    }
 
    /**
     * Esta función convierte un objeto que contiene una cadena a tipo "String".
     * <BR>Se pueden presentar los siguientes casos:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Objeto</I></TD><TD><I>Resultado</I></TR>
     *     <TR><TD>null</TD><TD>Una cadena vacía</TD></TR>
     *     <TR><TD>"24.96"</TD><TD>"24.96"</TD></TR>
     *     <TR><TD>"hola"</TD><TD>"hola"</TD></TR>
     *     <TR><TD>""</TD><TD>""</TD></TR>
     * </TABLE>
     * </CENTER>
     * @param cadena El objeto que contiene la cadena que se desea convertir a "String".
     * @return El objeto convertido a tipo "String".
     */
    public String parseString(Object cadena)
    {
        if (cadena==null)
            return "";
        else
        {
            return cadena.toString();
        }
    }
    
    /**
     * Esta función obtiene el valor que se encuentra en la fila y columna
     * especificada en un ArrayList. El ArrayList debe estar formado por otros
     * ArrayList de manera que se forme una matriz de la cual se pueda obtener
     * el valor que se encuentra en la fila y columna especificada.
     * @param objeto El ArrayList del que se desea obtener el valor.
     * @param row La fila de la que se va a obtener el valor.
     * @param col La columna de la que se va a obtener el valor.
     * @return El objeto que se encuentra en la posición especificada.
     * <BR>Nota.- La función devolverá <I>null</I> en los siguientes casos:
     * <UL>
     * <LI>El ArrayList recibido es <I>null</I>.
     * <LI>El objeto que se encuentra en la posición especificada es <I>null</I>.
     * <LI>Ocurrió un error.
     * </UL>
     */
    public Object getObjectValueAt(java.util.ArrayList objeto, int row, int col)
    {
        java.util.ArrayList arlAux;
        try
        {
            if (objeto==null)
                return null;
            else
            {
                arlAux=(java.util.ArrayList)objeto.get(row);
                if (arlAux==null)
                    return null;
                else
                    if (arlAux.get(col)==null)
                        return null;
                    else
                        return arlAux.get(col);
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }
    
    /**
     * Esta función obtiene el valor que se encuentra en la fila y columna
     * especificada en un ArrayList. El ArrayList debe estar formado por otros
     * ArrayList de manera que se forme una matriz de la cual se pueda obtener
     * el valor que se encuentra en la fila y columna especificada.
     * @param objeto El ArrayList del que se desea obtener el valor.
     * @param row La fila de la que se va a obtener el valor.
     * @param col La columna de la que se va a obtener el valor.
     * @return La cadena que se encuentra en la posición especificada.
     * <BR>Nota.- La función devolverá <I>Una cadena vacía</I> en los siguientes casos:
     * <UL>
     * <LI>El ArrayList recibido es <I>null</I>.
     * <LI>El objeto que se encuentra en la posición especificada es <I>null</I>.
     * <LI>Ocurrió un error.
     * </UL>
     */
    public String getStringValueAt(java.util.ArrayList objeto, int row, int col)
    {
        java.util.ArrayList arlAux;
        try
        {
            if (objeto==null)
                return "";
            else
            {
                arlAux=(java.util.ArrayList)objeto.get(row);
                if (arlAux==null)
                    return "";
                else
                    if (arlAux.get(col)==null)
                        return "";
                    else
                        return arlAux.get(col).toString();
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return "";
        }
    }
    
    /**
     * Esta función obtiene el valor que se encuentra en la fila y columna
     * especificada en un ArrayList. El ArrayList debe estar formado por otros
     * ArrayList de manera que se forme una matriz de la cual se pueda obtener
     * el valor que se encuentra en la fila y columna especificada.
     * @param objeto El ArrayList del que se desea obtener el valor.
     * @param row La fila de la que se va a obtener el valor.
     * @param col La columna de la que se va a obtener el valor.
     * @return El valor entero que se encuentra en la posición especificada.
     * <BR>Nota.- La función devolverá <I>0</I> en los siguientes casos:
     * <UL>
     * <LI>El ArrayList recibido es <I>null</I>.
     * <LI>El objeto que se encuentra en la posición especificada es <I>null</I>.
     * <LI>Ocurrió un error.
     * </UL>
     */
    public int getIntValueAt(java.util.ArrayList objeto, int row, int col)
    {
        java.util.ArrayList arlAux;
        try
        {
            if (objeto==null)
                return 0;
            else
            {
                arlAux=(java.util.ArrayList)objeto.get(row);
                if (arlAux==null)
                    return 0;
                else
                    if (arlAux.get(col)==null)
                        return 0;
                    else
                        return Integer.parseInt(arlAux.get(col).toString());
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return 0;
        }
        catch (NumberFormatException e)
        {
            return 0;
        }
    }
    
    /**
     * Esta función obtiene el valor que se encuentra en la fila y columna
     * especificada en un ArrayList. El ArrayList debe estar formado por otros
     * ArrayList de manera que se forme una matriz de la cual se pueda obtener
     * el valor que se encuentra en la fila y columna especificada.
     * @param objeto El ArrayList del que se desea obtener el valor.
     * @param row La fila de la que se va a obtener el valor.
     * @param col La columna de la que se va a obtener el valor.
     * @return El valor decimal que se encuentra en la posición especificada.
     * <BR>Nota.- La función devolverá <I>0</I> en los siguientes casos:
     * <UL>
     * <LI>El ArrayList recibido es <I>null</I>.
     * <LI>El objeto que se encuentra en la posición especificada es <I>null</I>.
     * <LI>Ocurrió un error.
     * </UL>
     */
    public double getDoubleValueAt(java.util.ArrayList objeto, int row, int col)
    {
        java.util.ArrayList arlAux;
        try
        {
            if (objeto==null)
                return 0;
            else
            {
                arlAux=(java.util.ArrayList)objeto.get(row);
                if (arlAux==null)
                    return 0;
                else
                    if (arlAux.get(col)==null)
                        return 0;
                    else
                        return Double.parseDouble(arlAux.get(col).toString());
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return 0;
        }
        catch (NumberFormatException e)
        {
            return 0;
        }
    }
    
    /**
     * Esta función obtiene el valor que se encuentra en la fila y columna
     * especificada en un Vector. El Vector debe estar formado por otros
     * Vector de manera que se forme una matriz de la cual se pueda obtener
     * el valor que se encuentra en la fila y columna especificada.
     * @param objeto El Vector del que se desea obtener el valor.
     * @param row La fila de la que se va a obtener el valor.
     * @param col La columna de la que se va a obtener el valor.
     * @return El objeto que se encuentra en la posición especificada.
     * <BR>Nota.- La función devolverá <I>null</I> en los siguientes casos:
     * <UL>
     * <LI>El Vector recibido es <I>null</I>.
     * <LI>El objeto que se encuentra en la posición especificada es <I>null</I>.
     * <LI>Ocurrió un error.
     * </UL>
     */
    public Object getObjectValueAt(java.util.Vector objeto, int row, int col)
    {
        java.util.Vector vecAux;
        try
        {
            if (objeto==null)
                return null;
            else
            {
                vecAux=(java.util.Vector)objeto.get(row);
                if (vecAux==null)
                    return null;
                else
                    if (vecAux.get(col)==null)
                        return null;
                    else
                        return vecAux.get(col);
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return null;
        }
    }
    
    /**
     * Esta función obtiene el valor que se encuentra en la fila y columna
     * especificada en un Vector. El Vector debe estar formado por otros
     * Vector de manera que se forme una matriz de la cual se pueda obtener
     * el valor que se encuentra en la fila y columna especificada.
     * @param objeto El Vector del que se desea obtener el valor.
     * @param row La fila de la que se va a obtener el valor.
     * @param col La columna de la que se va a obtener el valor.
     * @return La cadena que se encuentra en la posición especificada.
     * <BR>Nota.- La función devolverá <I>Una cadena vacía</I> en los siguientes casos:
     * <UL>
     * <LI>El Vector recibido es <I>null</I>.
     * <LI>El objeto que se encuentra en la posición especificada es <I>null</I>.
     * <LI>Ocurrió un error.
     * </UL>
     */
    public String getStringValueAt(java.util.Vector objeto, int row, int col)
    {
        java.util.Vector vecAux;
        try
        {
            if (objeto==null)
                return "";
            else
            {
                vecAux=(java.util.Vector)objeto.get(row);
                if (vecAux==null)
                    return "";
                else
                    if (vecAux.get(col)==null)
                        return "";
                    else
                        return vecAux.get(col).toString();
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return null;
        }
    }
    
    /**
     * Esta función obtiene el valor que se encuentra en la fila y columna
     * especificada en un Vector. El Vector debe estar formado por otros
     * Vector de manera que se forme una matriz de la cual se pueda obtener
     * el valor que se encuentra en la fila y columna especificada.
     * @param objeto El Vector del que se desea obtener el valor.
     * @param row La fila de la que se va a obtener el valor.
     * @param col La columna de la que se va a obtener el valor.
     * @return El valor entero que se encuentra en la posición especificada.
     * <BR>Nota.- La función devolverá <I>0</I> en los siguientes casos:
     * <UL>
     * <LI>El Vector recibido es <I>null</I>.
     * <LI>El objeto que se encuentra en la posición especificada es <I>null</I>.
     * <LI>Ocurrió un error.
     * </UL>
     */
    public int getIntValueAt(java.util.Vector objeto, int row, int col)
    {
        java.util.Vector vecAux;
        try
        {
            if (objeto==null)
                return 0;
            else
            {
                vecAux=(java.util.Vector)objeto.get(row);
                if (vecAux==null)
                    return 0;
                else
                    if (vecAux.get(col)==null)
                        return 0;
                    else
                        return Integer.parseInt(vecAux.get(col).toString());
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return 0;
        }
        catch (NumberFormatException e)
        {
            return 0;
        }
    }
    
    /**
     * Esta función obtiene el valor que se encuentra en la fila y columna
     * especificada en un Vector. El Vector debe estar formado por otros
     * Vector de manera que se forme una matriz de la cual se pueda obtener
     * el valor que se encuentra en la fila y columna especificada.
     * @param objeto El Vector del que se desea obtener el valor.
     * @param row La fila de la que se va a obtener el valor.
     * @param col La columna de la que se va a obtener el valor.
     * @return El valor decimal que se encuentra en la posición especificada.
     * <BR>Nota.- La función devolverá <I>0</I> en los siguientes casos:
     * <UL>
     * <LI>El Vector recibido es <I>null</I>.
     * <LI>El objeto que se encuentra en la posición especificada es <I>null</I>.
     * <LI>Ocurrió un error.
     * </UL>
     */
    public double getDoubleValueAt(java.util.Vector objeto, int row, int col)
    {
        java.util.Vector vecAux;
        try
        {
            if (objeto==null)
                return 0;
            else
            {
                vecAux=(java.util.Vector)objeto.get(row);
                if (vecAux==null)
                    return 0;
                else
                    if (vecAux.get(col)==null)
                        return 0;
                    else
                        return Double.parseDouble(vecAux.get(col).toString());
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return 0;
        }
        catch (NumberFormatException e)
        {
            return 0;
        }
    }
    
    /**
     * Esta función obtiene la cantidad especificada en palabras.
     * <BR>Por ejemplo:
     * <UL>
     * <LI>1560.36: UN MIL QUINIENTOS SESENTA DOLARES 36/100.
     * <LI>0.36: CERO DOLARES 36/100.
     * <LI>941560.36: NOVECIENTOS CUARENTA Y UN MIL QUINIENTOS SESENTA DOLARES 36/100.
     * </UL>
     * @param cantidad La cantidad que se desea convertir a palabras.
     * @param moneda La cadena que contiene la moneda a utilizar.
     * <BR>Si es <I>null</I> la función utilizará de manera predeterminada la moneda "DOLARES".
     * @return La cantidad convertida a palabras.
     * <BR>Nota.- La función devolverá una cadena vacía si ocurre una excepción o si la cantidad es mayor o igual a 1 millón.
     */
    public String getCantidadPalabras(String cantidad, String moneda)
    {
        double dblCan;
        String strRes="";
        try
        {
            dblCan=Double.parseDouble(cantidad);
            strRes=getCantidadPalabras(dblCan, moneda);
        }
        catch (NumberFormatException e)
        {
            strRes="";
        }
        return strRes;
    }
        
    /**
     * Esta función obtiene la cantidad especificada en palabras.
     * <BR>Por ejemplo:
     * <UL>
     * <LI>1560.36: UN MIL QUINIENTOS SESENTA DOLARES 36/100.
     * <LI>0.36: CERO DOLARES 36/100.
     * <LI>941560.36: NOVECIENTOS CUARENTA Y UN MIL QUINIENTOS SESENTA DOLARES 36/100.
     * </UL>
     * @param cantidad La cantidad que se desea convertir a palabras.
     * @param moneda La cadena que contiene la moneda a utilizar.
     * <BR>Si es <I>null</I> la función utilizará de manera predeterminada la moneda "DOLARES".
     * @return La cantidad convertida a palabras.
     * <BR>Nota.- La función devolverá una cadena vacía si ocurre una excepción o si la cantidad es mayor o igual a 1 millón.
     */
    public String getCantidadPalabras(double cantidad, String moneda)
    {
        String strRes="";
        try
        {
            if (cantidad<1000000)
            {
                if (cantidad<=-1)
                    cantidad=cantidad * -1;
                if (moneda==null)
                    moneda="DOLARES";
                String[] aMillares=new String[]{"","UN ", "DOS ","TRES ","CUATRO ","CINCO ","SEIS ","SIETE ","OCHO ","NUEVE "};
                String[] aCentenas=new String[]{"","CIEN ", "DOSCIENTOS ","TRESCIENTOS ","CUATROCIENTOS ","QUINIENTOS ","SEISCIENTOS ","SETECIENTOS ","OCHOCIENTOS ","NOVECIENTOS "};
                String[] aDiezes=new String[]{"", "DIEZ ","VEINTE ","TREINTA ","CUARENTA ","CINCUENTA ","SESENTA ","SETENTA ","OCHENTA ","NOVENTA "}; 
                String[] aDecenas=new String[]{"", "ONCE ","DOCE ","TRECE ","CATORCE ","QUINCE ","DIECISEIS ","DIECISIETE ","DIECIOCHO ", "DIECINUEVE "};
                String[] aUnidades=new String[]{"","UNO ", "DOS ","TRES ","CUATRO ","CINCO ","SEIS ","SIETE ","OCHO ","NUEVE "};
                String palabra="";
                int m_pesos=(int)cantidad;
                String decimales=String.valueOf(cantidad).toString();
                decimales=decimales.substring(decimales.indexOf('.') + 1); 
                decimales=(decimales.length()<2?decimales + "0":decimales.substring(0,2));
                int m_centavos=(int) ((cantidad - m_pesos) * 100 + 1);
                int m_cienmiles=(m_pesos/100000)%10;
                int m_diezmiles=(m_pesos/10000)%10;
                int m_millares=(m_pesos/1000)%10;
                int m_centenas=(m_pesos/100)%10;
                int m_decenas=(m_pesos/10)%10;
                int m_unidades=(m_pesos/1)%10;
                if (m_cienmiles!=0 && m_diezmiles==0 && m_millares==0)
                    palabra+=aCentenas[m_cienmiles] + " MIL "; 
                else
                    palabra+=aCentenas[m_cienmiles] + ( m_cienmiles==1 ?"TO ":""); 
                if (m_diezmiles==1 && m_millares==1)
                    palabra+="ONCE MIL ";
                else 
                    palabra+=aDiezes[m_diezmiles] + (m_diezmiles!=0 && m_millares==0?"MIL ":(m_diezmiles!=0?"Y ":"")); 
                palabra+=aMillares[m_millares] + (m_millares!=0?"MIL ":"");
                palabra+=( m_centenas==1 && m_unidades + m_decenas >0 ?aCentenas[m_centenas].trim() + "TO ":aCentenas[m_centenas]);
                palabra+=(m_decenas==1?aDecenas[m_unidades]:"");
                palabra+=(m_decenas!=1?aDiezes[m_decenas]:"");
                palabra+=(m_unidades>0 && m_decenas>01?"Y ":""); 
                palabra+=(m_unidades==0 && m_decenas==1?aDiezes[1]:""); 
                palabra+=(m_decenas!=1?aUnidades[m_unidades]:"");
                if (cantidad==0 || palabra.equals(""))
                    palabra = "CERO ";
                strRes=palabra + decimales +"/100 " + moneda;
            }
        }
        catch (Exception e)
        {
            strRes="";
        }
        return strRes;
    }
    
    /**
     * Esta función actualiza el campo "ne_ultDoc" de la tabla "tbm_cabTipDoc"
     * con el número de documento especificado.
     * @param padre El formulario padre para el cuadro de mensaje. Puede ser cualquier objeto que
     * herede de la clase <I>Component</I>. Por ejemplo: JFrame, JInternalFrame
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * Esta función recibe la conexión porque está pensada para utilizar transaccionamiento.
     * Es decir, si no se completa toda la transacción habría que hacer un rollback.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param documento El número de documento que se va a establecer para el tipo de documento.
     * @return true: Si se pudo actualizar el campo.
     * <BR>false: En el caso contrario.
     */
    public boolean set_ne_ultDoc_tbm_cabTipDoc(java.awt.Component padre, java.sql.Connection conexion, int empresa, int local, int tipoDocumento, int documento)
    {
        java.sql.Statement stm;
        String strSQL;
        boolean blnRes=true;
        try
        {
            if (conexion!=null)
            {
                stm=conexion.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=" + documento;
                strSQL+=" WHERE co_emp=" + empresa;
                strSQL+=" AND co_loc=" + local;
                strSQL+=" AND co_tipDoc=" + tipoDocumento;
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }
    
    
    
    /*JM*/
    public void setDoubleValueAt(java.util.ArrayList objeto, int row, int col, double datoSetear){
        java.util.ArrayList arlAux;
        try{
            if (objeto==null){}
            else{
                arlAux=(java.util.ArrayList)objeto.get(row);
                if (arlAux==null){}
                else
                    if (arlAux.get(col)==null){}
                    else
                        arlAux.set((col), ""+datoSetear);
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
        }
    }
    
    
    
    
    /**
     * Esta función recostea los items especificados en la empresa grupo.
     * @param padre El formulario padre para el cuadro de mensaje. Puede ser cualquier objeto que
     * herede de la clase <I>Component</I>. Por ejemplo: JFrame, JInternalFrame
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * Esta función recibe la conexión porque está pensada para utilizar transaccionamiento.
     * Es decir, si no se completa toda la transacción habría que hacer un rollback.
     * @param intCodEmpGrp El código de la empresa grupo.
     * @param intCodEmp El código de la empresa en la que se ingresó al sistema.
     * @param strCodItm El listado de códigos de los items a procesar.
     * <BR>Por ejemplo, '1', '4', '5' indica que se debe procesar los items 1, 4 y 5.
     * @return true: Si se pudo recostear los items.
     * <BR>false: En el caso contrario.
     */
    public boolean recostearItm(java.awt.Component padre, Librerias.ZafParSis.ZafParSis objParSis, java.sql.Connection conexion, int intCodEmp, String strCodItm, String strFecDes, String strFecHas, String strFor)
    {
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
        java.util.ArrayList arlDat=new java.util.ArrayList();
        java.util.ArrayList arlReg;
        double dblCosUniIni, dblSalIniUni, dblSalIniVal, dblCosProIni, dblCan, dblCosUni, dblSalUni, dblSalVal, dblCosPro, dblCanDev, dblCosTot;
        int i, intNumDec, intNumIng;
        StringBuffer stb=new StringBuffer();
        boolean blnRes=true;
        try
        {
            if (conexion!=null)
            {
                stm=conexion.createStatement();
                intNumDec=objParSis.getDecimalesBaseDatos();
                dblCosUniIni=0;
                dblSalIniUni=0;
                dblSalIniVal=0;
                dblCosProIni=0;
                //Paso 1: Obtener los saldos iniciales.
                //Armar la sentencia SQL.
                //Esto estuvo vigente hasta el 14_Nov_2007.
//                strSQL="";
//                strSQL+="SELECT a2.nd_cosUni, a2.nd_exi, a2.nd_valExi, a2.nd_cosPro";
//                strSQL+=" FROM tbm_cabMovInv AS a1";
//                strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
//                strSQL+=" AND a2.co_itm=" + strCodItm;
//                strSQL+=" AND a1.ne_secEmp IN (";
//                strSQL+=" SELECT MAX(b1.ne_secEmp)";
//                strSQL+=" FROM tbm_cabMovInv AS b1";
//                strSQL+=" INNER JOIN tbm_detMovInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)";
//                strSQL+=" WHERE b1.co_emp=" + intCodEmp;
//                strSQL+=" AND b2.co_itm=" + strCodItm;
//                strSQL+=" AND b1.fe_doc<'" + this.formatearFecha(strFecDes,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
//                strSQL+=" )";
//                strSQL+=" AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
//                strSQL+=" ORDER BY a1.fe_doc, a1.ne_secEmp,a2.co_reg";
                
                strSQL="";
                strSQL+="SELECT c1.ne_secEmp, c1.co_reg, c1.nd_cosUni, c1.nd_exi, c1.nd_valExi, c1.nd_cospro";
                strSQL+=" FROM (";
                strSQL+=" SELECT b1.ne_secEmp, b1.co_reg, b1.co_itm, b1.nd_cosUni, b1.nd_exi, b1.nd_valExi, b1.nd_cosPro";
                strSQL+=" FROM (";
                strSQL+=" SELECT a1.ne_secEmp, a2.co_reg, a2.co_itm, a2.nd_cosUni, a2.nd_exi, a2.nd_valExi, a2.nd_cosPro";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.fe_doc<'" + this.formatearFecha(strFecDes,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=" AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                strSQL+=" ) AS b1 INNER JOIN (";
                strSQL+=" SELECT a2.co_itm, a1.fe_doc, MAX(a1.ne_secEmp) AS ne_secEmp";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN (";
                strSQL+=" SELECT b2.co_itm, MAX(b1.fe_doc) AS fe_doc";
                strSQL+=" FROM tbm_cabMovInv AS b1";
                strSQL+=" INNER JOIN tbm_detMovInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)";
                strSQL+=" WHERE b1.co_emp=" + intCodEmp;
                strSQL+=" AND b1.fe_doc<'" + this.formatearFecha(strFecDes,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=" AND b1.st_reg IN ('A', 'R', 'C', 'F')";
                strSQL+=" AND (b1.st_tipDev IS NULL OR b1.st_tipDev='C')";
                strSQL+=" AND b2.co_itm=" + strCodItm;
                strSQL+=" GROUP BY b2.co_itm";
                strSQL+=" ) AS a3 ON (a2.co_itm=a3.co_itm AND a1.fe_doc=a3.fe_doc)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.fe_doc<'" + this.formatearFecha(strFecDes,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=" AND a1.st_reg IN ('A', 'R', 'C', 'F')";
                strSQL+=" AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                strSQL+=" AND a2.co_itm=" + strCodItm;
                strSQL+=" GROUP BY a2.co_itm, a1.fe_doc";
                strSQL+=" ) AS b2 ON (b1.co_itm=b2.co_itm AND b1.ne_secEmp=b2.ne_secEmp)";
                strSQL+=" ) AS c1";
                strSQL+=" INNER JOIN (";
                strSQL+=" SELECT a2.co_itm, a1.ne_secEmp, MAX(a2.co_reg) AS co_reg";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.fe_doc<'" + this.formatearFecha(strFecDes,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=" AND a2.co_itm=" + strCodItm;
                strSQL+=" AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                strSQL+=" GROUP BY a2.co_itm, a1.ne_secEmp";
                strSQL+=" ) AS c2 ON (c1.co_itm=c2.co_itm AND c1.ne_secEmp=c2.ne_secEmp AND c1.co_reg=c2.co_reg)";
//                System.out.println("strSQL: " + strSQL);
                rst=stm.executeQuery(strSQL);
                while (rst.next())
                {
                    dblCosUniIni=rst.getDouble("nd_cosUni");
                    dblSalIniUni=rst.getDouble("nd_exi");
                    dblSalIniVal=rst.getDouble("nd_valExi");
                    dblCosProIni=rst.getDouble("nd_cosPro");
                }
                rst.close();
                rst=null;

                //Paso 2: Obtener los registros a actualizar.
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a3.st_cosUniCal, a3.tx_natDoc, a1.st_reg";
                strSQL+=", a2.nd_can, a2.nd_canDev, a2.nd_cosUni, a2.nd_porDes, a2.nd_cosTot, Null AS nd_salUni, Null AS nd_salVal, Null AS nd_cosPro";
//                strSQL+=", a2.nd_can, a2.nd_cosUni, a2.nd_cosTot, Null AS nd_salUni, Null AS nd_salVal, Null AS nd_cosPro";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a2.co_itm=" + strCodItm;
                strSQL+=" AND a1.fe_doc BETWEEN '" + this.formatearFecha(strFecDes,strFor,objParSis.getFormatoFechaBaseDatos()) + "' AND '" + this.formatearFecha(strFecHas,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=" AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                strSQL+=" ORDER BY a1.fe_doc, a1.ne_secEmp, a2.co_reg";
//                System.out.println("strSQL: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar arreglo de datos.
                arlDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    arlReg=new java.util.ArrayList();
                    arlReg.add(INT_REC_COD_EMP,rst.getString("co_emp"));
                    arlReg.add(INT_REC_COD_LOC,rst.getString("co_loc"));
                    arlReg.add(INT_REC_COD_TIP_DOC,rst.getString("co_tipDoc"));
                    arlReg.add(INT_REC_COD_DOC,rst.getString("co_doc"));
                    arlReg.add(INT_REC_COD_REG,rst.getString("co_reg"));
                    arlReg.add(INT_REC_EST_CUC,rst.getString("st_cosUniCal"));
                    arlReg.add(INT_REC_NAT_DOC,rst.getString("tx_natDoc"));
                    arlReg.add(INT_REC_EST_DOC,rst.getString("st_reg"));
                    arlReg.add(INT_REC_CAN,rst.getString("nd_can"));
                    arlReg.add(INT_REC_CAN_DEV,rst.getString("nd_canDev"));
                    arlReg.add(INT_REC_COS_UNI,rst.getString("nd_cosUni"));
                    arlReg.add(INT_REC_POR_DES,rst.getString("nd_porDes"));
                    arlReg.add(INT_REC_COS_TOT,rst.getString("nd_cosTot"));
                    arlReg.add(INT_REC_SAL_UNI,rst.getString("nd_salUni"));
                    arlReg.add(INT_REC_SAL_VAL,rst.getString("nd_salVal"));
                    arlReg.add(INT_REC_COS_PRO,rst.getString("nd_cosPro"));
                    arlDat.add(arlReg);
                }
                rst.close();
                rst=null;
                dblSalUni=dblSalIniUni;
                dblSalVal=dblSalIniVal;
                //Validar para el 2008 que no se tome en cuenta saldos en valores negativos si el saldo en unidades es 0 y el saldo en valores es negativo.
                if (getAnio(strFecDes, strFor)==2008 && getAnio(strFecHas, strFor)==2008)
                {
                    if (dblSalIniUni==0 && dblSalIniVal<0)
                        dblSalVal=0;
                }
                intNumIng=0;
                //Paso 3: Obtener el costo promedio.
                for (i=0;i<arlDat.size();i++)
                {
                    if (this.getStringValueAt(arlDat, i, INT_REC_EST_CUC).equals("N"))
                    {
                        if (this.getStringValueAt(arlDat, i, INT_REC_NAT_DOC).equals("I") || this.getStringValueAt(arlDat, i, INT_REC_NAT_DOC).equals("A"))
                        {
                            if (this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("A") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("R") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("C") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("F"))
                            {
//                                //TEMPORAL HASTA QUE SE BUSQUE UNA MEJOR FORMA DE HACERLO.
//                                if ( (this.getIntValueAt(arlDat, i, INT_REC_COD_EMP)==1 && this.getIntValueAt(arlDat, i, INT_REC_COD_LOC)==1 && this.getIntValueAt(arlDat, i, INT_REC_COD_TIP_DOC)==56 && (this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==542 || this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==543 || this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==544 || this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==546 || this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==548 || this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==549 || this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==550))
//                                || (this.getIntValueAt(arlDat, i, INT_REC_COD_EMP)==2 && this.getIntValueAt(arlDat, i, INT_REC_COD_LOC)==1 && this.getIntValueAt(arlDat, i, INT_REC_COD_TIP_DOC)==56 && (this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==4 || this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==5 || this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==6 || this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==7))
//                                || (this.getIntValueAt(arlDat, i, INT_REC_COD_EMP)==3 && this.getIntValueAt(arlDat, i, INT_REC_COD_LOC)==1 && this.getIntValueAt(arlDat, i, INT_REC_COD_TIP_DOC)==56 && (this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==1))
//                                || (this.getIntValueAt(arlDat, i, INT_REC_COD_EMP)==4 && this.getIntValueAt(arlDat, i, INT_REC_COD_LOC)==1 && this.getIntValueAt(arlDat, i, INT_REC_COD_TIP_DOC)==56 && (this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==7 || this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==8 || this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==9 || this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==10 || this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==11 || this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==12 || this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==13 || this.getIntValueAt(arlDat, i, INT_REC_COD_DOC)==14))
//                                )
//                                {
//                                    System.out.println("No hacer: " + arlDat.get(i).toString());
//                                }
//                                else
//                                {
//                                    dblCan=this.getDoubleValueAt(arlDat, i, INT_REC_CAN) + this.getDoubleValueAt(arlDat, i, INT_REC_CAN_DEV);
                                    //INICIO: Eddye
                                    dblCanDev=this.getDoubleValueAt(arlDat, i, INT_REC_CAN_DEV);
                                    if (dblCanDev!=0)
                                    {
                                        dblCan=this.getDoubleValueAt(arlDat, i, INT_REC_CAN) + dblCanDev;
                                        dblCosTot=dblCan*this.getDoubleValueAt(arlDat, i, INT_REC_COS_UNI)*(1-this.getDoubleValueAt(arlDat, i, INT_REC_POR_DES)/100);
                                    }
                                    else
                                    {
                                        dblCan=this.getDoubleValueAt(arlDat, i, INT_REC_CAN);
                                        dblCosTot=this.getDoubleValueAt(arlDat, i, INT_REC_COS_TOT);
                                    }
                                    //FIN: Eddye
                                    if (dblCan>0)
                                    {
                                        dblSalUni+=dblCan;
//                                        dblSalVal+=this.getDoubleValueAt(arlDat, i, INT_REC_COS_TOT);
                                        dblSalVal+=dblCosTot;
                                        intNumIng++;
                                    }
//                                }
//                                dblSalUni+=this.getDoubleValueAt(arlDat, i, INT_REC_CAN);
//                                dblSalVal+=this.getDoubleValueAt(arlDat, i, INT_REC_COS_TOT);
                            }
                        }
                    }
                }
//------------------------------------------------------------------------------
                if (dblCosUniIni==0 && dblSalUni==0)
                {
                    dblCosPro=0;
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a2.nd_cosUni";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a2.co_itm=" + strCodItm;
                    strSQL+=" AND a1.ne_secEmp IN (";
                    strSQL+=" SELECT MIN(b1.ne_secEmp)";
                    strSQL+=" FROM tbm_cabMovInv AS b1";
                    strSQL+=" INNER JOIN tbm_detMovInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_loc=b3.co_loc AND b1.co_tipDoc=b3.co_tipDoc)";
                    strSQL+=" WHERE b1.co_emp=" + intCodEmp;
                    strSQL+=" AND b2.co_itm=" + strCodItm;
                    strSQL+=" AND b3.st_cosUniCal='N'";
                    strSQL+=" AND b3.tx_natDoc IN ('I', 'A')";
                    strSQL+=" AND b1.st_reg IN ('A','R','C','F')";
                    strSQL+=" AND b1.fe_doc>'" + this.formatearFecha(strFecHas,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=" )";
                    strSQL+=" ORDER BY a2.co_reg";
//                    System.out.println("strSQL: " + strSQL);
                    rst=stm.executeQuery(strSQL);
//                    while (rst.next())
//                    {
//                        dblCosPro=rst.getDouble("nd_cosUni");
//                    }
                   
                    if (rst.next())
                    {
//                        System.out.println("Si hay ingresos...");
//                        while (rst.next())
//                        {
                            dblCosPro=rst.getDouble("nd_cosUni");
//                        }
                    }
                    else
                    {
//                        System.out.println("No hay ingresos...");
                    }
                    rst.close();   
                    rst=null;
                    //Utilizar el costo promedio del grupo.
                    if (dblCosPro==0)
                    {
                        if (arlDat.size()>0)
                        {
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="SELECT a1.nd_cosUniGrp as nd_cosProGrp";   // Se realizo Cambio........
                            strSQL+=" FROM tbm_detMovInv AS a1";
                            strSQL+=" WHERE a1.co_emp=" + this.getStringValueAt(arlDat, 0, INT_REC_COD_EMP);
                            strSQL+=" AND a1.co_loc=" + this.getStringValueAt(arlDat, 0, INT_REC_COD_LOC);
                            strSQL+=" AND a1.co_tipDoc=" + this.getStringValueAt(arlDat, 0, INT_REC_COD_TIP_DOC);
                            strSQL+=" AND a1.co_doc=" + this.getStringValueAt(arlDat, 0, INT_REC_COD_DOC);
                            strSQL+=" AND a1.co_reg=" + this.getStringValueAt(arlDat, 0, INT_REC_COD_REG);
//                            System.out.println("strSQL: " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if (rst.next())
                            {
                                dblCosPro=rst.getDouble("nd_cosProGrp");
                            }
                            rst.close();
                            rst=null;
                        }
                    }
                }
                else
                {
                    if (intNumIng==0)
                    {
                        dblCosPro=dblCosProIni;
                    }
                    else
                    {
                        if (dblSalUni==0)
                            dblCosPro=dblCosUniIni;
                        else
                            dblCosPro=this.redondear(dblSalVal/dblSalUni, intNumDec);
                    }
//                    if (dblSalUni==0)
//                        dblCosPro=0;
//                    else
//                        dblCosPro=this.redondear(dblSalVal/dblSalUni, intNumDec);
                }
//------------------------------------------------------------------------------
//                if (dblSalUni==0)
//                    dblCosPro=0;
//                else
//                    dblCosPro=this.redondear(dblSalVal/dblSalUni, intNumDec);
                dblSalUni=dblSalIniUni;
                dblSalVal=dblSalIniVal;
                //Paso 4: Actualizar los campos.
                for (i=0;i<arlDat.size();i++)
                {
                    dblCan=this.getDoubleValueAt(arlDat, i, INT_REC_CAN);
                    if (this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("A") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("R") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("C") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("F"))
                    {
//                        dblSalUni+=dblCan;
//                        dblSalUni=this.redondear(dblSalUni, intNumDec);
                        dblSalUni=this.redondear(dblSalUni+=dblCan, intNumDec);
//                        if (dblSalUni==0)
//                        {
//                            if (this.getStringValueAt(arlDat, i, INT_REC_EST_CUC).equals("S"))
//                            {
//                                if (i==0)
//                                {
//                                    //Actualizar el costo de los documentos que deben actualizar su costo.
//                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + dblSalIniVal/Math.abs(dblCan));
//                                    //Actualizar el costo total del documento.
//                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + -dblSalIniVal);
//                                }
//                                else
//                                {
//                                    //Actualizar el costo de los documentos que deben actualizar su costo.
//                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + this.getDoubleValueAt(arlDat, i-1, INT_REC_SAL_VAL)/Math.abs(dblCan));
//                                    //Actualizar el costo total del documento.
//                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + -this.getDoubleValueAt(arlDat, i-1, INT_REC_SAL_VAL));
//                                }
//                            }
//                            dblSalVal+=this.getDoubleValueAt(arlDat, i, INT_REC_COS_TOT);
//                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_UNI, "" + dblSalUni);
//                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_VAL, "" + dblSalVal);
//                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_PRO, "0");
//                        }
//                        else
//                        {
                            if (this.getStringValueAt(arlDat, i, INT_REC_EST_CUC).equals("S"))
                            {
                                //Actualizar el costo de los documentos que deben actualizar su costo.
                                ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + dblCosPro);
                                //Actualizar el costo total del documento.
                                ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + dblCan*dblCosPro);
                            }
//                            dblSalVal+=this.getDoubleValueAt(arlDat, i, INT_REC_COS_TOT);
                            dblSalVal+=this.getDoubleValueAt(arlDat, i, INT_REC_COS_TOT);
                            dblSalVal=this.redondear(dblSalVal, intNumDec);
                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_UNI, "" + dblSalUni);
                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_VAL, "" + dblSalVal);
                            //Eddye: 14_Sep_2007
                            if (dblSalUni==0 && dblSalVal==0)
                                ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_PRO, "0");
                            else
                                ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_PRO, "" + dblCosPro);
//                        }
                    }
                    else
                    {
                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_UNI, "" + dblSalUni);
                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_VAL, "" + dblSalVal);
                        //Eddye: 14_Sep_2007
                        if (dblSalUni==0 && dblSalVal==0)
                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_PRO, "0");
                        else
                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_PRO, "" + dblCosPro);
                    }
//----------------------------------------------------------------------------------------------------------------------------
//                    dblCan=this.getDoubleValueAt(arlDat, i, INT_REC_CAN);
//                    if (this.getStringValueAt(arlDat, i, INT_REC_EST_CUC).equals("S"))
//                    {
//                        //Actualizar el costo de los documentos que deben actualizar su costo.
//                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + dblCosPro);
//                        //Actualizar el costo total del documento.
//                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + dblCan*dblCosPro);
//                    }
//                    if (this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("A") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("R") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("C") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("F"))
//                    {
//                        dblSalUni+=dblCan;
//                        dblSalVal+=this.getDoubleValueAt(arlDat, i, INT_REC_COS_TOT);
//                    }
//                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_UNI, "" + dblSalUni);
//                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_VAL, "" + dblSalVal);
//                    if (dblSalUni==0)
//                    {
//                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_PRO, "0");
//                    }
//                    else
//                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_PRO, "" + dblCosPro);
//----------------------------------------------------------------------------------------------------------------------------
                }
                //Paso 5: Crear la tabla virtual que servirá para actualizar la tabla "tbm_detMovInv" con un solo update.
                for (i=0;i<arlDat.size();i++)
                {
                    if (i>0)
                        stb.append(" UNION ALL ");
                    stb.append("SELECT " + this.getStringValueAt(arlDat, i, INT_REC_COD_EMP) + " AS co_emp");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COD_LOC) + " AS co_loc");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COD_TIP_DOC) + " AS co_tipDoc");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COD_DOC) + " AS co_doc");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COD_REG) + " AS co_reg");
                    stb.append(", " + this.redondear(this.getStringValueAt(arlDat, i, INT_REC_COS_UNI), intNumDec) + " AS nd_cosUni");
                    stb.append(", " + this.redondear(this.getStringValueAt(arlDat, i, INT_REC_COS_TOT), intNumDec) + " AS nd_cosTot");
                    stb.append(", " + this.redondear(this.getStringValueAt(arlDat, i, INT_REC_SAL_UNI), intNumDec) + " AS nd_exi");
                    stb.append(", " + this.redondear(this.getStringValueAt(arlDat, i, INT_REC_SAL_VAL), intNumDec) + " AS nd_valExi");
                    stb.append(", " + this.redondear(this.getStringValueAt(arlDat, i, INT_REC_COS_PRO), intNumDec) + " AS nd_cosPro");
                }
                //Paso 6: Actualizar la tabla "tbm_detMovInv".
                if (arlDat.size()>0)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="UPDATE tbm_detMovInv";
                    strSQL+=" SET nd_cosUni=a1.nd_cosUni";
                    strSQL+=", nd_cosTot=a1.nd_cosTot";
                    strSQL+=", nd_exi=a1.nd_exi";
                    strSQL+=", nd_valExi=a1.nd_valExi";
                    strSQL+=", nd_cosPro=a1.nd_cosPro";
                    strSQL+=" FROM (";
                    strSQL+=stb.toString();
                    strSQL+=" ) AS a1";
                    strSQL+=" WHERE tbm_detMovInv.co_emp=a1.co_emp AND tbm_detMovInv.co_loc=a1.co_loc AND tbm_detMovInv.co_tipDoc=a1.co_tipDoc AND tbm_detMovInv.co_doc=a1.co_doc AND tbm_detMovInv.co_reg=a1.co_reg";
//                    System.out.println("strSQL: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                //Paso 7: Actualizar la tabla "tbm_inv" sólo si es necesario.
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT COUNT(*)";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a2.co_itm=" + strCodItm;
                strSQL+=" AND a1.fe_doc>'" + this.formatearFecha(strFecHas,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    if (rst.getInt(1)==0)
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_inv";
//                        strSQL+=" SET nd_cosUni=" + this.redondear(dblCosPro, intNumDec);
                        strSQL+=" SET nd_cosUni=" + this.redondear(this.getStringValueAt(arlDat, arlDat.size()-1, INT_REC_COS_PRO), intNumDec);
                        strSQL+=" WHERE co_emp=" + intCodEmp;
                        strSQL+=" AND co_itm=" + strCodItm;
//                        System.out.println("strSQL: " + strSQL);
                        stm.executeUpdate(strSQL);
                    }
                }
                rst.close();
                rst=null;
                
                stm.close();
                stm=null;
                arlDat.clear();
                arlDat=null;
                stb=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }
    
    public boolean recostearItmGrp2006_2007(java.awt.Component padre, Librerias.ZafParSis.ZafParSis objParSis, java.sql.Connection conexion, int intCodEmp, String strCodItm, String strFecDes, String strFecHas, String strFor)
    {
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
        java.util.ArrayList arlDat=new java.util.ArrayList();
        java.util.ArrayList arlReg;
        double dblCosUniIni, dblSalIniUni, dblSalIniVal, dblCosProIni, dblCan, dblCosUni, dblSalUni, dblSalVal, dblCosPro, dblCanDev, dblCosTot;
        int i, intNumDec, intNumIng;
        StringBuffer stb=new StringBuffer();
        boolean blnRes=true;
        try
        {
            if (conexion!=null)
            {
                stm=conexion.createStatement();
                intNumDec=objParSis.getDecimalesBaseDatos();
                dblCosUniIni=0;
                dblSalIniUni=0;
                dblSalIniVal=0;
                dblCosProIni=0;
                //Paso 1: Obtener los saldos iniciales.
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a2.nd_cosUniGrp AS nd_cosUni, a2.nd_exiGrp AS nd_exi, a2.nd_valExiGrp AS nd_valExi, a2.nd_cosProGrp AS nd_cosPro";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itmAct=a3.co_itm)";
                strSQL+=" WHERE a3.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + strCodItm + ")";
                strSQL+=" AND a1.fe_doc<'" + this.formatearFecha(strFecDes,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=" AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                //Temporal hasta que se busque una mejor manera de hacerlo.
//                    strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=3515 AND a1.co_cli IS NOT NULL)";
//                    strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=3516 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=602 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=603 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=2600 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=1039 AND a1.co_cli IS NOT NULL)";

                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=2853 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=2854 AND a1.co_cli IS NOT NULL)";
//                    strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=446 AND a1.co_cli IS NOT NULL)";
//                    strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=447 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=2105 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=789 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=790 AND a1.co_cli IS NOT NULL)";

                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=2857 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=2858 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=452 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=453 AND a1.co_cli IS NOT NULL)";
//                    strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=2107 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=832 AND a1.co_cli IS NOT NULL)";

                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=3116 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=3117 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=497 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=498 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=2294 AND a1.co_cli IS NOT NULL)";
//                    strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=886 AND a1.co_cli IS NOT NULL)";
//                    strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=887 AND a1.co_cli IS NOT NULL)";
                strSQL+=" ORDER BY a1.fe_doc, a1.ne_secGrp, a2.co_reg";
//                System.out.println("strSQL: " + strSQL);
                rst=stm.executeQuery(strSQL);
                while (rst.next())
                {
                    dblCosUniIni=rst.getDouble("nd_cosUni");
                    dblSalIniUni=rst.getDouble("nd_exi");
                    dblSalIniVal=rst.getDouble("nd_valExi");
                    dblCosProIni=rst.getDouble("nd_cosPro");
                }
                rst.close();
                rst=null;
                //Paso 2: Obtener los registros a actualizar.
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a4.st_cosUniCal, a4.tx_natDoc, a1.st_reg";
                strSQL+=", a2.nd_can, a2.nd_canDev, a2.nd_cosUniGrp AS nd_cosUni, a2.nd_porDes, a2.nd_cosTotGrp AS nd_cosTot, Null AS nd_salUni, Null AS nd_salVal, Null AS nd_cosPro";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itmAct=a3.co_itm)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)";
                strSQL+=" WHERE a3.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + strCodItm + ")";
                strSQL+=" AND a1.fe_doc BETWEEN '" + this.formatearFecha(strFecDes,strFor,objParSis.getFormatoFechaBaseDatos()) + "' AND '" + this.formatearFecha(strFecHas,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=" AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                //Temporal hasta que se busque una mejor manera de hacerlo.
//                    strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=3515 AND a1.co_cli IS NOT NULL)";
//                    strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=3516 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=602 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=603 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=2600 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=1039 AND a1.co_cli IS NOT NULL)";

                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=2853 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=2854 AND a1.co_cli IS NOT NULL)";
//                    strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=446 AND a1.co_cli IS NOT NULL)";
//                    strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=447 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=2105 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=789 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=790 AND a1.co_cli IS NOT NULL)";

                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=2857 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=2858 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=452 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=453 AND a1.co_cli IS NOT NULL)";
//                    strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=2107 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=832 AND a1.co_cli IS NOT NULL)";

                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=3116 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=3117 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=497 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=498 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=2294 AND a1.co_cli IS NOT NULL)";
//                    strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=886 AND a1.co_cli IS NOT NULL)";
//                    strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=887 AND a1.co_cli IS NOT NULL)";
                strSQL+=" ORDER BY a1.fe_doc, a1.ne_secGrp, a2.co_reg";
//                System.out.println("strSQL: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar arreglo de datos.
                arlDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    arlReg=new java.util.ArrayList();
                    arlReg.add(INT_REC_COD_EMP,rst.getString("co_emp"));
                    arlReg.add(INT_REC_COD_LOC,rst.getString("co_loc"));
                    arlReg.add(INT_REC_COD_TIP_DOC,rst.getString("co_tipDoc"));
                    arlReg.add(INT_REC_COD_DOC,rst.getString("co_doc"));
                    arlReg.add(INT_REC_COD_REG,rst.getString("co_reg"));
                    arlReg.add(INT_REC_EST_CUC,rst.getString("st_cosUniCal"));
                    arlReg.add(INT_REC_NAT_DOC,rst.getString("tx_natDoc"));
                    arlReg.add(INT_REC_EST_DOC,rst.getString("st_reg"));
                    arlReg.add(INT_REC_CAN,rst.getString("nd_can"));
                    arlReg.add(INT_REC_CAN_DEV,rst.getString("nd_canDev"));
                    arlReg.add(INT_REC_COS_UNI,rst.getString("nd_cosUni"));
                    arlReg.add(INT_REC_POR_DES,rst.getString("nd_porDes"));
                    arlReg.add(INT_REC_COS_TOT,rst.getString("nd_cosTot"));
                    arlReg.add(INT_REC_SAL_UNI,rst.getString("nd_salUni"));
                    arlReg.add(INT_REC_SAL_VAL,rst.getString("nd_salVal"));
                    arlReg.add(INT_REC_COS_PRO,rst.getString("nd_cosPro"));
                    arlDat.add(arlReg);
                }
                rst.close();
                rst=null;
                dblSalUni=dblSalIniUni;
                dblSalVal=dblSalIniVal;
                intNumIng=0;
                //Paso 3: Obtener el costo promedio.
                for (i=0;i<arlDat.size();i++)
                {
                    //TEMPORAL HASTA QUE SE BUSQUE UNA MEJOR FORMA DE HACERLO: Se cambia el estado de costeo a "S" para el tipo de documento 76=AJUITM.
                    if (this.getIntValueAt(arlDat, i, INT_REC_COD_TIP_DOC)==76)
                    {
                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_EST_CUC, "S");
                    }
                    if (this.getStringValueAt(arlDat, i, INT_REC_EST_CUC).equals("N"))
                    {
                        if (this.getStringValueAt(arlDat, i, INT_REC_NAT_DOC).equals("I") || this.getStringValueAt(arlDat, i, INT_REC_NAT_DOC).equals("A"))
                        {
                            if (this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("A") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("R") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("C") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("F"))
                            {
                                dblCanDev=this.getDoubleValueAt(arlDat, i, INT_REC_CAN_DEV);
                                if (dblCanDev!=0)
                                {
                                    dblCan=this.getDoubleValueAt(arlDat, i, INT_REC_CAN) + dblCanDev;
                                    dblCosTot=dblCan*this.getDoubleValueAt(arlDat, i, INT_REC_COS_UNI)*(1-this.getDoubleValueAt(arlDat, i, INT_REC_POR_DES)/100);
                                }
                                else
                                {
                                    dblCan=this.getDoubleValueAt(arlDat, i, INT_REC_CAN);
                                    dblCosTot=this.getDoubleValueAt(arlDat, i, INT_REC_COS_TOT);
                                }
                                if (dblCan>0)
                                {
                                    dblSalUni+=dblCan;
                                    dblSalVal+=dblCosTot;
                                    intNumIng++;
                                }
                            }
                        }
                    }
                }
                if (intNumIng==0)
                {
                    dblCosPro=dblCosProIni;
                }
                else
                {
                    if (dblSalUni==0)
                        dblCosPro=dblCosUniIni;
                    else
                        dblCosPro=this.redondear(dblSalVal/dblSalUni, intNumDec);
                }
                dblSalUni=dblSalIniUni;
                dblSalVal=dblSalIniVal;
                //Paso 4: Actualizar los campos.
                for (i=0;i<arlDat.size();i++)
                {
                    dblCan=this.getDoubleValueAt(arlDat, i, INT_REC_CAN);
                    if (this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("A") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("R") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("C") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("F"))
                    {
                        dblSalUni=this.redondear(dblSalUni+=dblCan, intNumDec);
                        if (dblSalUni==0)
                        {
                            if (this.getStringValueAt(arlDat, i, INT_REC_EST_CUC).equals("S"))
                            {
                                if (i==0)
                                {
                                    //Actualizar el costo de los documentos que deben actualizar su costo.
                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + dblSalIniVal/Math.abs(dblCan));
                                    //Actualizar el costo total del documento.
                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + -dblSalIniVal);
                                }
                                else
                                {
                                    //Actualizar el costo de los documentos que deben actualizar su costo.
                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + this.getDoubleValueAt(arlDat, i-1, INT_REC_SAL_VAL)/Math.abs(dblCan));
                                    //Actualizar el costo total del documento.
                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + -this.getDoubleValueAt(arlDat, i-1, INT_REC_SAL_VAL));
                                }
                            }
                            dblSalVal+=this.getDoubleValueAt(arlDat, i, INT_REC_COS_TOT);
                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_UNI, "" + dblSalUni);
                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_VAL, "" + dblSalVal);
//                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_PRO, "0");  //17_Dic_2007: Asi estaba al principio.
                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_PRO, "" + dblCosPro);
                        }
                        else
                        {
                            if (this.getStringValueAt(arlDat, i, INT_REC_EST_CUC).equals("S"))
                            {
                                //Actualizar el costo de los documentos que deben actualizar su costo.
                                ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + dblCosPro);
                                //Actualizar el costo total del documento.
                                ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + dblCan*dblCosPro);
                            }
                            dblSalVal+=this.getDoubleValueAt(arlDat, i, INT_REC_COS_TOT);
                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_UNI, "" + dblSalUni);
                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_VAL, "" + dblSalVal);
                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_PRO, "" + dblCosPro);
                        }
                    }
                    else
                    {
                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_UNI, "" + dblSalUni);
                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_VAL, "" + dblSalVal);
                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_PRO, "" + dblCosPro);
                    }
                }
                //Paso 5: Crear la tabla virtual que servirá para actualizar la tabla "tbm_detMovInv" con un solo update.
                for (i=0;i<arlDat.size();i++)
                {
                    if (i>0)
                        stb.append(" UNION ALL ");
                    stb.append("SELECT " + this.getStringValueAt(arlDat, i, INT_REC_COD_EMP) + " AS co_emp");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COD_LOC) + " AS co_loc");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COD_TIP_DOC) + " AS co_tipDoc");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COD_DOC) + " AS co_doc");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COD_REG) + " AS co_reg");
                    stb.append(", " + this.redondear(this.getStringValueAt(arlDat, i, INT_REC_COS_UNI), intNumDec) + " AS nd_cosUni");
                    stb.append(", " + this.redondear(this.getStringValueAt(arlDat, i, INT_REC_COS_TOT), intNumDec) + " AS nd_cosTot");
                    stb.append(", " + this.redondear(this.getStringValueAt(arlDat, i, INT_REC_SAL_UNI), intNumDec) + " AS nd_exi");
                    stb.append(", " + this.redondear(this.getStringValueAt(arlDat, i, INT_REC_SAL_VAL), intNumDec) + " AS nd_valExi");
                    stb.append(", " + this.redondear(this.getStringValueAt(arlDat, i, INT_REC_COS_PRO), intNumDec) + " AS nd_cosPro");
                }
                //Paso 6: Actualizar la tabla "tbm_detMovInv".
                if (arlDat.size()>0)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="UPDATE tbm_detMovInv";
                    strSQL+=" SET nd_cosUniGrp=a1.nd_cosUni";
                    strSQL+=", nd_cosTotGrp=a1.nd_cosTot";
                    strSQL+=", nd_exiGrp=a1.nd_exi";
                    strSQL+=", nd_valExiGrp=a1.nd_valExi";
                    strSQL+=", nd_cosProGrp=a1.nd_cosPro";
                    strSQL+=" FROM (";
                    strSQL+=stb.toString();
                    strSQL+=" ) AS a1";
                    strSQL+=" WHERE tbm_detMovInv.co_emp=a1.co_emp AND tbm_detMovInv.co_loc=a1.co_loc AND tbm_detMovInv.co_tipDoc=a1.co_tipDoc AND tbm_detMovInv.co_doc=a1.co_doc AND tbm_detMovInv.co_reg=a1.co_reg";
//                    System.out.println("strSQL: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                //Paso 7: Actualizar la tabla "tbm_inv" sólo si es necesario.
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT COUNT(*)";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itmAct=a3.co_itm)";
                strSQL+=" WHERE a3.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + strCodItm + ")";
                strSQL+=" AND a1.fe_doc>'" + this.formatearFecha(strFecHas,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    if (rst.getInt(1)==0)
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_inv";
//                        strSQL+=" SET nd_cosUni=" + this.redondear(dblCosPro, intNumDec); //17_Dic_2007: Así estaba al principio.
                        if (dblSalUni==0)
                            strSQL+=" SET nd_cosUni=0";
                        else
                            strSQL+=" SET nd_cosUni=" + this.redondear(dblCosPro, intNumDec);
                        strSQL+=" FROM (";
                        strSQL+=" SELECT co_emp, co_itm";
                        strSQL+=" FROM tbm_equInv";
                        strSQL+=" WHERE co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + strCodItm + ")";
                        strSQL+=" AND co_emp=" + objParSis.getCodigoEmpresaGrupo();
                        strSQL+=" ) AS a1";
                        strSQL+=" WHERE tbm_inv.co_emp=a1.co_emp AND tbm_inv.co_itm=a1.co_itm";
//                        System.out.println("strSQL: " + strSQL);
                        stm.executeUpdate(strSQL);
                    }
                }
                rst.close();
                rst=null;
                
                stm.close();
                stm=null;
                arlDat.clear();
                arlDat=null;
                stb=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }
    
    public boolean recostearItmGrp(java.awt.Component padre, Librerias.ZafParSis.ZafParSis objParSis, java.sql.Connection conexion, int intCodEmp, String strCodItm, String strFecDes, String strFecHas, String strFor)
    {
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
        java.util.ArrayList arlDat=new java.util.ArrayList();
        java.util.ArrayList arlReg;
        double dblCosUniIni, dblSalIniUni, dblSalIniVal, dblCosProIni, dblCan, dblCosUni, dblSalUni, dblSalVal, dblCosPro, dblCanDev, dblCosTot;
        int i, j, intNumDec, intNumIng, intCodTipDoc;
        StringBuffer stb=new StringBuffer();
        int intCodEmpRel=0, intCodLocRel=0, intCodTipDocRel=0, intCodDocRel=0;
        boolean blnDocRelExiLoc=false;
        boolean blnActTbmInv=false;
        boolean blnRes=true;
        try
        {
            if (conexion!=null)
            {
                stm=conexion.createStatement();
                intNumDec=objParSis.getDecimalesBaseDatos();
                dblCosUniIni=0;
                dblSalIniUni=0;
                dblSalIniVal=0;
                dblCosProIni=0;
                //Paso 1: Obtener los saldos iniciales.
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a2.nd_cosUniGrp AS nd_cosUni, a2.nd_exiGrp AS nd_exi, a2.nd_valExiGrp AS nd_valExi, a2.nd_cosProGrp AS nd_cosPro";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itmAct=a3.co_itm)";
                strSQL+=" WHERE a3.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + strCodItm + ")";
                strSQL+=" AND a1.fe_doc<'" + this.formatearFecha(strFecDes,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=" AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                strSQL+=" AND ( (a1.fe_doc<'2009/05/01'"; //Periodo antes del 2009/05/01 (Excluir: compras y ventas entre empresas, transferencias de empresas).
                //Temporal hasta que se busque una mejor manera de hacerlo.
                strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=602 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=603 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=2600 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=1039 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=2853 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=2854 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=2105 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=789 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=790 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=2857 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=2858 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=452 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=453 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=832 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=3116 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=3117 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=497 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=498 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=2294 AND a1.co_cli IS NOT NULL)";
                strSQL+=" ) OR (a1.fe_doc>='2009/05/01') )";  //Periodo después del 2009/05/01 (Considerar todo).
                strSQL+=" ORDER BY a1.fe_doc, a1.ne_secGrp, a2.co_reg";
                rst=stm.executeQuery(strSQL);
                while (rst.next())
                {
                    dblCosUniIni=rst.getDouble("nd_cosUni");
                    dblSalIniUni=rst.getDouble("nd_exi");
                    dblSalIniVal=rst.getDouble("nd_valExi");
                    dblCosProIni=rst.getDouble("nd_cosPro");
                }
                rst.close();
                rst=null;
                //Paso 2: Obtener los registros a actualizar.
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a4.st_cosUniCal, a4.tx_natDoc, a1.st_reg";
                strSQL+=", a2.nd_can, a2.nd_canDev, a2.nd_cosUniGrp AS nd_cosUni, a2.nd_porDes, a2.nd_cosTotGrp AS nd_cosTot, Null AS nd_salUni, Null AS nd_salVal, Null AS nd_cosPro";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itmAct=a3.co_itm)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)";
                strSQL+=" WHERE a3.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + strCodItm + ")";
                if (strFecHas==null)
                {
                    strSQL+=" AND a1.fe_doc>='" + this.formatearFecha(strFecDes,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                }
                else
                {
                    strSQL+=" AND a1.fe_doc BETWEEN '" + this.formatearFecha(strFecDes,strFor,objParSis.getFormatoFechaBaseDatos()) + "' AND '" + this.formatearFecha(strFecHas,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                }
                strSQL+=" AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                strSQL+=" AND ( (a1.fe_doc<'2009/05/01'"; //Periodo antes del 2009/05/01 (Excluir: compras y ventas entre empresas, transferencias de empresas).
                //Temporal hasta que se busque una mejor manera de hacerlo.
                strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=602 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=603 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=2600 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli=1039 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=2853 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=2854 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=2105 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=789 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli=790 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=2857 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=2858 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=452 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=453 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli=832 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=3116 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=3117 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=497 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=498 AND a1.co_cli IS NOT NULL)";
                strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli=2294 AND a1.co_cli IS NOT NULL)";
                strSQL+=" ) OR (a1.fe_doc>='2009/05/01') )";  //Periodo después del 2009/05/01 (Considerar todo).
                strSQL+=" ORDER BY a1.fe_doc, a1.ne_secGrp, a2.co_reg";
                rst=stm.executeQuery(strSQL);
                //Limpiar arreglo de datos.
                arlDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    arlReg=new java.util.ArrayList();
                    arlReg.add(INT_REC_COD_EMP,rst.getString("co_emp"));
                    arlReg.add(INT_REC_COD_LOC,rst.getString("co_loc"));
                    arlReg.add(INT_REC_COD_TIP_DOC,rst.getString("co_tipDoc"));
                    arlReg.add(INT_REC_COD_DOC,rst.getString("co_doc"));
                    arlReg.add(INT_REC_COD_REG,rst.getString("co_reg"));
                    arlReg.add(INT_REC_EST_CUC,rst.getString("st_cosUniCal"));
                    arlReg.add(INT_REC_NAT_DOC,rst.getString("tx_natDoc"));
                    arlReg.add(INT_REC_EST_DOC,rst.getString("st_reg"));
                    arlReg.add(INT_REC_CAN,rst.getString("nd_can"));
                    arlReg.add(INT_REC_CAN_DEV,rst.getString("nd_canDev"));
                    arlReg.add(INT_REC_COS_UNI,rst.getString("nd_cosUni"));
                    arlReg.add(INT_REC_POR_DES,rst.getString("nd_porDes"));
                    arlReg.add(INT_REC_COS_TOT,rst.getString("nd_cosTot"));
                    arlReg.add(INT_REC_SAL_UNI,rst.getString("nd_salUni"));
                    arlReg.add(INT_REC_SAL_VAL,rst.getString("nd_salVal"));
                    arlReg.add(INT_REC_COS_PRO,rst.getString("nd_cosPro"));
                    arlDat.add(arlReg);
                }
                rst.close();
                rst=null;
                intNumIng=0;
                dblSalUni=dblSalIniUni;
                dblSalVal=dblSalIniVal;
                dblCosPro=dblCosProIni;
                //Paso 4: Actualizar los campos.
                for (i=0;i<arlDat.size();i++)
                {
                    //TEMPORAL HASTA QUE SE BUSQUE UNA MEJOR FORMA DE HACERLO: Se cambia el estado de costeo a "S" para el tipo de documento 76=AJUITM.
                    if (this.getIntValueAt(arlDat, i, INT_REC_COD_TIP_DOC)==76)
                    {
                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_EST_CUC, "S");
                    }
                    dblCan=this.getDoubleValueAt(arlDat, i, INT_REC_CAN);
                    if (this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("A") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("R") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("C") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("F"))
                    {
                        dblSalUni=this.redondear(dblSalUni+=dblCan, intNumDec);
                        if (dblSalUni==0)
                        {
                            if (this.getStringValueAt(arlDat, i, INT_REC_EST_CUC).equals("S"))
                            {
                                if (i==0)
                                {
                                    //Actualizar el costo de los documentos que deben actualizar su costo.
                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + dblSalIniVal/Math.abs(dblCan));
                                    //Actualizar el costo total del documento.
                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + -dblSalIniVal);
                                }
                                else
                                {
                                    //Actualizar el costo de los documentos que deben actualizar su costo.
                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + this.getDoubleValueAt(arlDat, i-1, INT_REC_SAL_VAL)/Math.abs(dblCan));
                                    //Actualizar el costo total del documento.
                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + -this.getDoubleValueAt(arlDat, i-1, INT_REC_SAL_VAL));
                                }
                            }
                            dblSalVal+=this.getDoubleValueAt(arlDat, i, INT_REC_COS_TOT);
                            if (dblSalUni==0)
                            {

                            }
                            else
                                dblCosPro=this.redondear(dblSalVal/dblSalUni, intNumDec);
                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_UNI, "" + dblSalUni);
                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_VAL, "" + dblSalVal);
                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_PRO, "" + dblCosPro);
                        }
                        else
                        {
                            if (this.getStringValueAt(arlDat, i, INT_REC_EST_CUC).equals("S"))
                            {
                                intCodTipDoc=this.getIntValueAt(arlDat, i, INT_REC_COD_TIP_DOC);
                                if (intCodTipDoc==3)
                                {
                                    //Obtener la FACVEN a la que aplica la DEVVEN.
                                    //Armar la sentencia SQL.
                                    strSQL="";
                                    strSQL+="SELECT *";
                                    strSQL+=" FROM tbr_cabMovInv";
                                    strSQL+=" WHERE co_emp=" + this.getStringValueAt(arlDat, i, INT_REC_COD_EMP) + " AND co_loc=" + this.getStringValueAt(arlDat, i, INT_REC_COD_LOC) + " AND co_tipDoc=" + this.getStringValueAt(arlDat, i, INT_REC_COD_TIP_DOC) + " AND co_doc=" + this.getStringValueAt(arlDat, i, INT_REC_COD_DOC) + " AND co_tipDocRel=" + 1;
                                    rst=stm.executeQuery(strSQL);
                                    if (rst.next())
                                    {
                                        intCodEmpRel=rst.getInt("co_empRel");
                                        intCodLocRel=rst.getInt("co_locRel");
                                        intCodTipDocRel=rst.getInt("co_tipDocRel");
                                        intCodDocRel=rst.getInt("co_docRel");
                                    }
                                    rst.close();
                                    rst=null;
                                    //Buscar localmente el costo de la factura.
                                    for (j=0;j<arlDat.size();j++)
                                    {
                                        if (this.getStringValueAt(arlDat, j, INT_REC_COD_EMP).equals("" + intCodEmpRel) && this.getStringValueAt(arlDat, j, INT_REC_COD_LOC).equals("" + intCodLocRel) && this.getStringValueAt(arlDat, j, INT_REC_COD_TIP_DOC).equals("" + intCodTipDocRel) && this.getStringValueAt(arlDat, j, INT_REC_COD_DOC).equals("" + intCodDocRel))
                                        {
                                            dblCosPro=this.getDoubleValueAt(arlDat, j, INT_REC_COS_UNI);
                                            blnDocRelExiLoc=true;
                                            break;
                                        }
                                    }
                                    //Si la FACVEN no está localmente buscarla en la base de datos.
                                    if (!blnDocRelExiLoc)
                                    {
                                        //Armar la sentencia SQL.
                                        strSQL="";
                                        strSQL+="SELECT a1.nd_cosUniGrp";
                                        strSQL+=" FROM tbm_detMovInv AS a1";
                                        strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itmAct=a2.co_itm)";
                                        strSQL+=" WHERE a1.co_emp=" + intCodEmpRel + " AND a1.co_loc=" + intCodLocRel + " AND a1.co_tipDoc=" + intCodTipDocRel + " AND a1.co_doc=" + intCodDocRel;
                                        strSQL+=" AND a2.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + strCodItm + ")";
                                        strSQL+=" ORDER BY a1.co_reg";
                                        rst=stm.executeQuery(strSQL);
                                        if (rst.next())
                                        {
                                            dblCosPro=rst.getDouble("nd_cosUniGrp");
                                        }
                                        rst.close();
                                        rst=null;
                                    }
                                    //Actualizar el costo de los documentos que deben actualizar su costo.
                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + dblCosPro);
                                    //Actualizar el costo total del documento.
                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + dblCan*dblCosPro);
                                }
                                else if (intCodTipDoc==4)
                                {
                                    //Obtener la FACCOM a la que aplica la DEVCOM.
                                    //Armar la sentencia SQL.
                                    strSQL="";
                                    strSQL+="SELECT *";
                                    strSQL+=" FROM tbr_cabMovInv";
                                    strSQL+=" WHERE co_emp=" + this.getStringValueAt(arlDat, i, INT_REC_COD_EMP) + " AND co_loc=" + this.getStringValueAt(arlDat, i, INT_REC_COD_LOC) + " AND co_tipDoc=" + this.getStringValueAt(arlDat, i, INT_REC_COD_TIP_DOC) + " AND co_doc=" + this.getStringValueAt(arlDat, i, INT_REC_COD_DOC) + " AND co_tipDocRel=" + 2;
                                    rst=stm.executeQuery(strSQL);
                                    if (rst.next())
                                    {
                                        intCodEmpRel=rst.getInt("co_empRel");
                                        intCodLocRel=rst.getInt("co_locRel");
                                        intCodTipDocRel=rst.getInt("co_tipDocRel");
                                        intCodDocRel=rst.getInt("co_docRel");
                                    }
                                    rst.close();
                                    rst=null;
                                    //Buscar localmente el costo de la O/C.
                                    for (j=0;j<arlDat.size();j++)
                                    {
                                        if (this.getStringValueAt(arlDat, j, INT_REC_COD_EMP).equals("" + intCodEmpRel) && this.getStringValueAt(arlDat, j, INT_REC_COD_LOC).equals("" + intCodLocRel) && this.getStringValueAt(arlDat, j, INT_REC_COD_TIP_DOC).equals("" + intCodTipDocRel) && this.getStringValueAt(arlDat, j, INT_REC_COD_DOC).equals("" + intCodDocRel))
                                        {
                                            dblCosPro=this.getDoubleValueAt(arlDat, j, INT_REC_COS_UNI);
                                            blnDocRelExiLoc=true;
                                            break;
                                        }
                                    }
                                    //Si la FACCOM no está localmente buscarla en la base de datos.
                                    if (!blnDocRelExiLoc)
                                    {
                                        //Armar la sentencia SQL.
                                        strSQL="";
                                        strSQL+="SELECT a1.nd_cosUniGrp";
                                        strSQL+=" FROM tbm_detMovInv AS a1";
                                        strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itmAct=a2.co_itm)";
                                        strSQL+=" WHERE a1.co_emp=" + intCodEmpRel + " AND a1.co_loc=" + intCodLocRel + " AND a1.co_tipDoc=" + intCodTipDocRel + " AND a1.co_doc=" + intCodDocRel;
                                        strSQL+=" AND a2.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + strCodItm + ")";
                                        strSQL+=" ORDER BY a1.co_reg";
                                        rst=stm.executeQuery(strSQL);
                                        if (rst.next())
                                        {
                                            dblCosPro=rst.getDouble("nd_cosUniGrp");
                                        }
                                        rst.close();
                                        rst=null;
                                    }
                                    //Actualizar el costo de los documentos que deben actualizar su costo.
                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + dblCosPro);
                                    //Actualizar el costo total del documento.
                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + dblCan*dblCosPro);
                                }
                                else
                                {
                                    //Actualizar el costo de los documentos que deben actualizar su costo.
                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + dblCosPro);
                                    //Actualizar el costo total del documento.
                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + dblCan*dblCosPro);
                                }
                            }
                            dblSalVal+=this.getDoubleValueAt(arlDat, i, INT_REC_COS_TOT);
                            if (dblSalUni==0)
                                dblCosPro=0;
                            else
                                dblCosPro=this.redondear(dblSalVal/dblSalUni, intNumDec);
                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_UNI, "" + dblSalUni);
                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_VAL, "" + dblSalVal);
                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_PRO, "" + dblCosPro);
                        }
                    }
                    else
                    {
                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_UNI, "" + dblSalUni);
                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_VAL, "" + dblSalVal);
                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_PRO, "" + dblCosPro);
                    }
                }
                //Encerar el costo promedio cuando el saldo en unidades sea igual a cero.
                for (i=0;i<arlDat.size();i++)
                {
                    if (this.getDoubleValueAt(arlDat, i, INT_REC_SAL_UNI)==0)
                    {
                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_PRO, "0");
                    }
                }
                //Paso 5: Crear la tabla virtual que servirá para actualizar la tabla "tbm_detMovInv" con un solo update.
                for (i=0;i<arlDat.size();i++)
                {
                    if (i>0)
                        stb.append(" UNION ALL ");
                    stb.append("SELECT " + this.getStringValueAt(arlDat, i, INT_REC_COD_EMP) + " AS co_emp");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COD_LOC) + " AS co_loc");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COD_TIP_DOC) + " AS co_tipDoc");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COD_DOC) + " AS co_doc");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COD_REG) + " AS co_reg");
                    stb.append(", " + this.redondear(this.getStringValueAt(arlDat, i, INT_REC_COS_UNI), intNumDec) + " AS nd_cosUni");
                    stb.append(", " + this.redondear(this.getStringValueAt(arlDat, i, INT_REC_COS_TOT), intNumDec) + " AS nd_cosTot");
                    stb.append(", " + this.redondear(this.getStringValueAt(arlDat, i, INT_REC_SAL_UNI), intNumDec) + " AS nd_exi");
                    stb.append(", " + this.redondear(this.getStringValueAt(arlDat, i, INT_REC_SAL_VAL), intNumDec) + " AS nd_valExi");
                    stb.append(", " + this.redondear(this.getStringValueAt(arlDat, i, INT_REC_COS_PRO), intNumDec) + " AS nd_cosPro");
                }
                //Paso 6: Actualizar la tabla "tbm_detMovInv".
                if (arlDat.size()>0)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="UPDATE tbm_detMovInv";
                    strSQL+=" SET nd_cosUniGrp=a1.nd_cosUni";
                    strSQL+=", nd_cosTotGrp=a1.nd_cosTot";
                    strSQL+=", nd_exiGrp=a1.nd_exi";
                    strSQL+=", nd_valExiGrp=a1.nd_valExi";
                    strSQL+=", nd_cosProGrp=a1.nd_cosPro";
                    strSQL+=" FROM (";
                    strSQL+=stb.toString();
                    strSQL+=" ) AS a1";
                    strSQL+=" WHERE tbm_detMovInv.co_emp=a1.co_emp AND tbm_detMovInv.co_loc=a1.co_loc AND tbm_detMovInv.co_tipDoc=a1.co_tipDoc AND tbm_detMovInv.co_doc=a1.co_doc AND tbm_detMovInv.co_reg=a1.co_reg";
                    stm.executeUpdate(strSQL);
                }
                //Paso 7: Actualizar la tabla "tbm_inv" sólo si es necesario.
                if (strFecHas==null)
                    blnActTbmInv=true;
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT COUNT(*)";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itmAct=a3.co_itm)";
                    strSQL+=" WHERE a3.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + strCodItm + ")";
                    strSQL+=" AND a1.fe_doc>'" + this.formatearFecha(strFecHas,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                    rst=stm.executeQuery(strSQL);
                    if (rst.next())
                    {
                        if (rst.getInt(1)==0)
                            blnActTbmInv=true;
                        else
                            blnActTbmInv=false;
                    }
                    rst.close();
                    rst=null;
                }
                if (blnActTbmInv)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="UPDATE tbm_inv";
                    if (dblSalUni==0)
                        strSQL+=" SET nd_cosUni=0";
                    else
                        strSQL+=" SET nd_cosUni=" + this.redondear(dblCosPro, intNumDec);
                    strSQL+=" FROM (";
                    strSQL+=" SELECT co_emp, co_itm";
                    strSQL+=" FROM tbm_equInv";
                    strSQL+=" WHERE co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + strCodItm + ")";
                    strSQL+=" AND co_emp=" + objParSis.getCodigoEmpresaGrupo();
                    strSQL+=" ) AS a1";
                    strSQL+=" WHERE tbm_inv.co_emp=a1.co_emp AND tbm_inv.co_itm=a1.co_itm";
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
                arlDat.clear();
                arlDat=null;
                stb=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función obtiene el último día del año y mes especificado.
     * <BR>Por ejemplo:
     *          <CENTER>
     *          <TABLE BORDER=1>
     *              <TR><TD><I>Año</I></TD><TD><I>Mes</I></TD><TD><I>Resultado</I></TD></TR>
     *              <TR><TD>2000</TD><TD>06(Julio)</TD><TD>31</TD></TR>
     *              <TR><TD>2000</TD><TD>01(Febrero)</TD><TD>29</TD></TR>
     *              <TR><TD>2001</TD><TD>01(Febrero)</TD><TD>28</TD></TR>
     *          </TABLE>
     *          </CENTER>
     * @param anio El año del que se desea obtener el último día del mes.
     * @param mes El mes del que se desea obtener el último día del mes.
     * <BR>Nota.- Los meses comienzan desde 0. Por ejemplo: 0=Enero; 1=Febrero, etc.
     * @return El último día del año y mes especificado.
     */
    public int getUltimoDiaMes(int anio, int mes)
    {
        java.util.Calendar cal=java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.YEAR, anio);
        cal.set(java.util.Calendar.MONTH, mes);
        cal.set(java.util.Calendar.DATE, 1);
        return cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
    }
    
    public String[][] getIntervalosAnualesRangoFechas(String strFecDes, String strFecHas, String strFor)
    {
        String strRes[][];
        java.util.Calendar cal=java.util.Calendar.getInstance();
        int intAniDes, intAniHas, i, j;
        try
        {
            cal.setTime(this.parseDate(strFecDes, strFor));
            intAniDes=cal.get(cal.YEAR);
            cal.setTime(this.parseDate(strFecHas, strFor));
            intAniHas=cal.get(cal.YEAR);
            //Asignar el tamaño del arreglo.
            strRes=new String[intAniHas-intAniDes+1][2];
            j=0;
            for (i=intAniDes; i<=intAniHas; i++)
            {
                strRes[j][0]=i + "/01/01";
                strRes[j][1]=i + "/12/31";
                j++;
            }
        }
        catch (Exception e)
        {
            return null;
        }
        return strRes;
    }
    
    /**
     * Esta función obtiene las fecha inicial y final de los meses comprendidos en el rango especificado.
     * @param objeto El ArrayList del que se desea obtener el valor.
     * @param row La fila de la que se va a obtener el valor.
     * @param col La columna de la que se va a obtener el valor.
     * @return La cadena que se encuentra en la posición especificada.
     * <BR>Nota.- La función devolverá <I>Una cadena vacía</I> en los siguientes casos:
     * <UL>
     * <LI>El ArrayList recibido es <I>null</I>.
     * <LI>El objeto que se encuentra en la posición especificada es <I>null</I>.
     * <LI>Ocurrió un error.
     * </UL>
     */
    public String[][] getIntervalosMensualesRangoFechas(String strFecDes, String strFecHas, String strFor)
    {
        String strRes[][];
        java.util.Calendar cal=java.util.Calendar.getInstance();
        int intAniDes, intMesDes, intDiaDes, intAniHas, intMesHas, intDiaHas, intDifAni, i, j, k;
        try
        {
            cal.setTime(this.parseDate(strFecDes, strFor));
            intAniDes=cal.get(cal.YEAR);
            intMesDes=cal.get(cal.MONTH);
            intDiaDes=cal.get(cal.DATE);
            cal.setTime(this.parseDate(strFecHas, strFor));
            intAniHas=cal.get(cal.YEAR);
            intMesHas=cal.get(cal.MONTH);
            intDiaHas=cal.get(cal.DATE);
            //Asignar el tamaño del arreglo.
            strRes=new String[(intAniHas*12+intMesHas)-(intAniDes*12+intMesDes)+1][2];
            k=0;
            intDifAni=intAniHas-intAniDes;
            if (intDifAni==0)
            {
                i=intAniDes;
                for (j=intMesDes;j<=intMesHas;j++)
                {
//                    System.out.print("i, j:" + i + ", " + j + " -->   ");
//                    System.out.print(i + "/" + (j+1) + "/" + "01" + " - ");
//                    System.out.print(i + "/" + (j+1) + "/" + this.getUltimoDiaMes(i, j));
//                    System.out.println();
                    strRes[k][0]=i + "/" + (j+1) + "/" + "01";
                    strRes[k][1]=i + "/" + (j+1) + "/" + this.getUltimoDiaMes(i, j);
                    k++;
                }
            }
            else if (intDifAni==1)
            {
                i=intAniDes;
                for (j=intMesDes;j<12;j++)
                {
//                    System.out.print("i, j:" + i + ", " + j + " -->   ");
//                    System.out.print(i + "/" + (j+1) + "/" + "01" + " - ");
//                    System.out.print(i + "/" + (j+1) + "/" + this.getUltimoDiaMes(i, j));
//                    System.out.println();
                    strRes[k][0]=i + "/" + (j+1) + "/" + "01";
                    strRes[k][1]=i + "/" + (j+1) + "/" + this.getUltimoDiaMes(i, j);
                    k++;
                }
                i=intAniHas;
                for (j=0;j<=intMesHas;j++)
                {
//                    System.out.print("i, j:" + i + ", " + j + " -->   ");
//                    System.out.print(i + "/" + (j+1) + "/" + "01" + " - ");
//                    System.out.print(i + "/" + (j+1) + "/" + this.getUltimoDiaMes(i, j));
//                    System.out.println();
                    strRes[k][0]=i + "/" + (j+1) + "/" + "01";
                    strRes[k][1]=i + "/" + (j+1) + "/" + this.getUltimoDiaMes(i, j);
                    k++;
                }
            }
            else
            {
                i=intAniDes;
                for (j=intMesDes;j<12;j++)
                {
//                    System.out.print("i, j:" + i + ", " + j + " -->   ");
//                    System.out.print(i + "/" + (j+1) + "/" + "01" + " - ");
//                    System.out.print(i + "/" + (j+1) + "/" + this.getUltimoDiaMes(i, j));
//                    System.out.println();
                    strRes[k][0]=i + "/" + (j+1) + "/" + "01";
                    strRes[k][1]=i + "/" + (j+1) + "/" + this.getUltimoDiaMes(i, j);
                    k++;
                }
                for (i=intAniDes+1;i<=intAniHas-1;i++)
                {
                    for (j=1;j<=12;j++)
                    {
//                        System.out.print("i, j:" + i + ", " + j + " -->   ");
//                        System.out.print(i + "/" + j + "/" + "01" + " - ");
//                        System.out.print(i + "/" + j + "/" + this.getUltimoDiaMes(i, j-1));
//                        System.out.println();
                        strRes[k][0]=i + "/" + j + "/" + "01";
                        strRes[k][1]=i + "/" + j + "/" + this.getUltimoDiaMes(i, j-1);
                        k++;
                    }
                }
                i=intAniHas;
                for (j=0;j<=intMesHas;j++)
                {
//                    System.out.print("i, j:" + i + ", " + j + " -->   ");
//                    System.out.print(i + "/" + (j+1) + "/" + "01" + " - ");
//                    System.out.print(i + "/" + (j+1) + "/" + this.getUltimoDiaMes(i, j));
//                    System.out.println();
                    strRes[k][0]=i + "/" + (j+1) + "/" + "01";
                    strRes[k][1]=i + "/" + (j+1) + "/" + this.getUltimoDiaMes(i, j);
                    k++;
                }
            }
        }
        catch (Exception e)
        {
            return null;
        }
        return strRes;
    }
    
    /**
     * Esta función obtiene el año correspondiente a la fecha especificada.
     * @param fecha La cadena que contiene la fecha que se desea utilizar.
     * @param formato El formato en el que se encuentra la fecha. Podría ser "dd/MM/yyyy", "MM/dd/yyyy", "yyyy/MM/dd", etc.
     * @return El año correspondiente a la fecha especificada.
     * <BR>Nota.- Si hay un error al obtener el año la función devolverá "-1".
     */
    public int getAnio(String fecha, String formato)
    {
        java.util.Calendar cal=java.util.Calendar.getInstance();
        int intRes;
        try
        {
            cal.setTime(this.parseDate(fecha, formato));
            intRes=cal.get(cal.YEAR);
            cal=null;
        }
        catch (Exception e)
        {
            intRes=-1;
        }
        return intRes;
    }

    /**
     * Esta función obtiene el mes correspondiente a la fecha especificada.
     * @param fecha La cadena que contiene la fecha que se desea utilizar.
     * @param formato El formato en el que se encuentra la fecha. Podría ser "dd/MM/yyyy", "MM/dd/yyyy", "yyyy/MM/dd", etc.
     * @return El mes correspondiente a la fecha especificada.
     * <BR>Nota.- Si hay un error al obtener el mes la función devolverá "-1".
     */
    public int getMes(String fecha, String formato)
    {
        java.util.Calendar cal=java.util.Calendar.getInstance();
        int intRes;
        try
        {
            cal.setTime(this.parseDate(fecha, formato));
            intRes=cal.get(cal.MONTH);
            cal=null;
        }
        catch (Exception e)
        {
            intRes=-1;
        }
        return intRes;
    }

    /**
     * Esta función obtiene el día correspondiente a la fecha especificada.
     * @param fecha La cadena que contiene la fecha que se desea utilizar.
     * @param formato El formato en el que se encuentra la fecha. Podría ser "dd/MM/yyyy", "MM/dd/yyyy", "yyyy/MM/dd", etc.
     * @return El día correspondiente a la fecha especificada.
     * <BR>Nota.- Si hay un error al obtener el día la función devolverá "-1".
     */
    public int getDia(String fecha, String formato)
    {
        java.util.Calendar cal=java.util.Calendar.getInstance();
        int intRes;
        try
        {
            cal.setTime(this.parseDate(fecha, formato));
            intRes=cal.get(cal.DATE);
            cal=null;
        }
        catch (Exception e)
        {
            intRes=-1;
        }
        return intRes;
    }
 
    /**
     * Esta función recostea los items especificados en la empresa grupo.
     * @param padre El formulario padre para el cuadro de mensaje. Puede ser cualquier objeto que
     * herede de la clase <I>Component</I>. Por ejemplo: JFrame, JInternalFrame
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * Esta función recibe la conexión porque está pensada para utilizar transaccionamiento.
     * Es decir, si no se completa toda la transacción habría que hacer un rollback.
     * @param strFecDes F.
     * @param strCodItm El listado de códigos de los items a procesar.
     * <BR>Por ejemplo, '1', '4', '5' indica que se debe procesar los items 1, 4 y 5.
     * @return true: Si se pudo recostear los items.
     * <BR>false: En el caso contrario.
     */
    public boolean remayorizar(java.awt.Component padre, Librerias.ZafParSis.ZafParSis objParSis, String strFecDes, String strFecHas, String strFor)
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
        int i, j, k;
        String strPer;
        String strFecRan[][];
        boolean blnRes=true;
        try
        {
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
//                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
//                {
//                    //Procesar las cuentas del "Grupo".
//                }
//                else
//                {
                    //Procesar las cuentas de la "Empresa seleccionada".
                    strFecRan=this.getIntervalosMensualesRangoFechas(strFecDes, strFecHas, strFor);
                    //Desmayorizar.
                    for (i=0; i<strFecRan.length; i++)
                    {
                        strPer="" + (this.getMes(strFecRan[i][0],"yyyy/MM/dd") + 1);
                        strPer="" + this.getAnio(strFecRan[i][0],"yyyy/MM/dd") + ((strPer.length()==1)?"0"+strPer:strPer);
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="DELETE FROM tbm_salCta";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_per=" + strPer;
                        stm.executeUpdate(strSQL);
                    }
                    //Mayorizar.
                    for (i=0; i<strFecRan.length; i++)
                    {
                        strPer="" + (this.getMes(strFecRan[i][0],"yyyy/MM/dd") + 1);
                        strPer="" + this.getAnio(strFecRan[i][0],"yyyy/MM/dd") + ((strPer.length()==1)?"0"+strPer:strPer);
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="INSERT INTO tbm_salCta(co_emp, co_cta, co_per, nd_salCta)";
                        strSQL+=" SELECT b1.co_emp, b1.co_cta, " + strPer + " AS co_per, b2.nd_salCta";
                        strSQL+=" FROM tbm_plaCta AS b1";
                        strSQL+=" LEFT OUTER JOIN (";
                        strSQL+=" SELECT a1.co_emp, a2.co_cta, SUM(a2.nd_monDeb-a2.nd_monHab) AS nd_salCta";
                        strSQL+=" FROM tbm_cabDia AS a1";
                        strSQL+=" INNER JOIN tbm_detDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a1.st_reg='A'";
                        strSQL+=" AND (a1.fe_dia BETWEEN '" + strFecRan[i][0] + "' AND '" + strFecRan[i][1] + "')";
                        strSQL+=" GROUP BY a1.co_emp, a2.co_cta";
                        strSQL+=" ) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cta=b2.co_cta)";
                        strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" ORDER BY b1.co_emp, b1.co_cta";
                        stm.executeUpdate(strSQL);
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_salCta";
                        strSQL+=" SET nd_salCta=(CASE WHEN tbm_salCta.nd_salCta IS NULL THEN 0 ELSE tbm_salCta.nd_salCta END) + b1.nd_salCta";
                        strSQL+=" FROM (";
                        strSQL+=" SELECT a1.co_emp, a3.co_ctaRes AS co_cta, " + strPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                        strSQL+=" FROM tbm_plaCta AS a1";
                        strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                        strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a1.tx_niv1 IN ('4', '5', '6', '7', '8')";
                        strSQL+=" AND a2.co_per=" + strPer;
                        strSQL+=" GROUP BY a1.co_emp, a3.co_ctaRes";
                        strSQL+=" ) AS b1";
                        strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                        stm.executeUpdate(strSQL);
                        for (j=6; j>1; j--)
                        {
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="UPDATE tbm_salCta";
                            strSQL+=" SET nd_salCta=b1.nd_salCta";
                            strSQL+=" FROM (";
                            strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + strPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                            strSQL+=" FROM tbm_plaCta AS a1";
                            strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                            strSQL+=" AND a1.ne_niv=" + j;
                            strSQL+=" AND a2.co_per=" + strPer;
                            strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
                            strSQL+=" ) AS b1";
                            strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                            stm.executeUpdate(strSQL);
                        }
                    }
                    stm.close();
                    con.close();
                    stm=null;
                    con=null;
//                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }
    
    /**
     * Obtiene el directorio donde está ubicado el sistema.
     * <BR>Por ejemplo:
     * <UL>
     * <LI>Windows: C:\Zafiro
     * <LI>Linux: /Zafiro
     * </UL>
     * La ruta del sistema es utilizada para determinar la ubicación de otros directorios.
     * <BR>Por ejemplo, si se desea obtener la ruta del directorio de configuración se tendría
     * que agregar "config" a la ruta del sistema. Es decir, RutaSistema + "/config".
     * @return El directorio donde está ubicado el sistema.
     */
    public String getDirectorioSistema()
    {
        String strRes="";
        try
        {
            java.net.URL urlArc=this.getClass().getResource("/Librerias/ZafUtil/ZafUtil.class");
            if (urlArc!=null)
            {
                //Utilizar "decode" porque los espacios en blanco que puede incluir la ruta del archivo son
                //reemplazados con "%20" por el método "getPath()" y eso trae problemas al usar "FileInputStream".
                strRes=java.net.URLDecoder.decode(urlArc.getPath(),"UTF-8");
                strRes=strRes.substring(0, strRes.lastIndexOf("/Zafiro"));
                if (strRes.indexOf("file:")!=-1)
                    strRes=strRes.substring(5);
            }
        }
        catch (Exception e)
        {
            strRes=null;
            System.out.println("strRes: " + strRes);
        }
        return strRes;
    }
    
    
    public void setStringValueAt(java.util.ArrayList objeto, int row, int col, double datoSetear){
        java.util.ArrayList arlAux;
        try{
            if (objeto==null){}
            else{
                arlAux=(java.util.ArrayList)objeto.get(row);
                if (arlAux==null){}
                else
                    if (arlAux.get(col)==null){}
                    else
                        arlAux.set((col), ""+datoSetear);
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
        }
    }
    
    public void setStringValueAt(java.util.ArrayList objeto, int row, int col, String datoSetear){
        java.util.ArrayList arlAux;
        try{
            if (objeto==null){}
            else{
                arlAux=(java.util.ArrayList)objeto.get(row);
                if (arlAux==null){}
                else
                    if (arlAux.get(col)==null){}
                    else
                        arlAux.set((col), datoSetear);
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
        }
    }
    
    /**
     * Esta función determina si se deben presentar todos los clientes (Es decir, leer la tabla tbm_cli) o si sólo se
     * deben presentar los clientes por local (Es decir, leer la tabla tbr_cliLoc).
     * @param objParSis El objeto ZafParSis.
     * @param codigoEmpresa El código de la empresa.
     * @param codigoLocal El código del local.
     * @param codigoUsuario El código del usuario.
     * @return true: Se debe utilizar la tabla "tbm_cli".
     * <BR>false: Se debe utilizar la tabla "tbr_cliLoc".
     * <BR>Nota.- Si el código del usuario corresponde al usuario "Administrador" se devolverá "true".
     */
    public boolean utilizarClientesEmpresa(Librerias.ZafParSis.ZafParSis objParSis, int codigoEmpresa, int codigoLocal, int codigoUsuario)
    {
        java.util.ArrayList arlParEmp, arlParUsr;
        boolean blnRes=true;
        try
        {
            //Si es el usuario Administrador (Código=1) tiene acceso a "Todos los clientes".
            if (codigoUsuario==1)
            {
                blnRes=true;
            }
            else
            {
                arlParEmp=objParSis.getValoresParametroTbrParEmp(codigoEmpresa, 3);
                if (arlParEmp==null)
                {
                    //No se encontró ninguna configuración para la empresa. Se asume la configuración más básica.
                    //Es decir, se utilizará "Clientes por local".
                    blnRes=false;
                }
                else
                {
                    switch (((int)Double.parseDouble(arlParEmp.get(0).toString())))
                    {
                        case 1:
                            blnRes=true;
                            break;
                        case 2:
                            blnRes=false;
                            break;
                        case 3:
                            //Validar el acceso a nivel de usuario.
                            arlParUsr=objParSis.getValoresParametroTbrParUsr(codigoEmpresa, codigoLocal, 3, codigoUsuario);
                            if (arlParUsr==null)
                            {
                                //No se encontró ninguna configuración para el usuario. Se asume la configuración más básica.
                                //Es decir, se utilizará "Clientes por local".
                                blnRes=false;
                            }
                            else
                            {
                                
                                if (Double.parseDouble(arlParUsr.get(0).toString())==1)
                                {
                                    //Se utilizará "Todos los clentes".
                                    blnRes=true;
                                }
                                else
                                {
                                    //Se utilizará "Clientes por local".
                                    blnRes=false;
                                }
                            }
                            break;
                        default:
                            blnRes=false;
                            break;
                    }
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }

    /**
     * Esta función obtiene el valor que se encuentra en la fila y columna
     * especificada en un Vector. El Vector debe estar formado por otros
     * Vector de manera que se forme una matriz de la cual se pueda obtener
     * el valor que se encuentra en la fila y columna especificada.
     * @param objeto El Vector del que se desea obtener el valor.
     * @param row La fila de la que se va a obtener el valor.
     * @param col La columna de la que se va a obtener el valor.
     * @return El valor decimal que se encuentra en la posición especificada.
     * <BR>Nota.- La función devolverá <I>null</I> en los siguientes casos:
     * <UL>
     * <LI>El Vector recibido es <I>null</I>.
     * <LI>El objeto que se encuentra en la posición especificada es <I>null</I>.
     * <LI>Ocurrió un error.
     * </UL>
     */
    public BigDecimal getBigDecimalValueAt(java.util.Vector objeto, int row, int col)
    {
        java.util.Vector vecAux;
        try
        {
            if (objeto==null)
                return null;
            else
            {
                vecAux=(java.util.Vector)objeto.get(row);
                if (vecAux==null)
                    return null;
                else
                    if (vecAux.get(col)==null)
                        return null;
                    else
                        return (new BigDecimal(vecAux.get(col).toString()));
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return null;
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    /**
     * Esta función obtiene el valor que se encuentra en la fila y columna
     * especificada en un ArrayList. El ArrayList debe estar formado por otros
     * ArrayList de manera que se forme una matriz de la cual se pueda obtener
     * el valor que se encuentra en la fila y columna especificada.
     * @param objeto El ArrayList del que se desea obtener el valor.
     * @param row La fila de la que se va a obtener el valor.
     * @param col La columna de la que se va a obtener el valor.
     * @return El valor decimal que se encuentra en la posición especificada.
     * <BR>Nota.- La función devolverá <I>null</I> en los siguientes casos:
     * <UL>
     * <LI>El ArrayList recibido es <I>null</I>.
     * <LI>El objeto que se encuentra en la posición especificada es <I>null</I>.
     * <LI>Ocurrió un error.
     * </UL>
     */
    public BigDecimal getBigDecimalValueAt(java.util.ArrayList objeto, int row, int col)
    {
        java.util.ArrayList arlAux;
        try
        {
            if (objeto==null)
                return null;
            else
            {
                arlAux=(java.util.ArrayList)objeto.get(row);
                if (arlAux==null)
                    return null;
                else
                    if (arlAux.get(col)==null)
                        return null;
                    else
                        return (new BigDecimal(arlAux.get(col).toString()));
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return null;
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    /**
     * Esta función redondea un número a los decimales especificados.
     * @param numero El número que se desea redondear.
     * @param decimales El número de decimales al que se desea redondear.
     * @return El número redondeado.
     * <BR>Nota.- Si se produce algún error se retorna <I>null</I>.
     */
    public BigDecimal redondearBigDecimal(BigDecimal numero, int decimales)
    {
        try
        {
            //La suma "3.65+0.05" debería dar "3.70". Pero Java genera "3.6999999999999997".
            //Para evitar éste problema se debe trabajar con la clase "java.math.BigDecimal".
            return numero.setScale(decimales,BigDecimal.ROUND_HALF_UP);
        }
        catch (NumberFormatException e)
        {
            return null;
        }
        catch (ArithmeticException e)
        {
            return null;
        }
    }

    /**
     * Esta función redondea un número a los decimales especificados.
     * @param numero La cadena que contiene el número que se desea redondear.
     * @param decimales El número de decimales al que se desea redondear.
     * @return El número redondeado.
     * <BR>Nota.- Si se produce algún error se retorna <I>null</I>.
     */
    public BigDecimal redondearBigDecimal(String numero, int decimales)
    {
        try
        {
            //La suma "3.65+0.05" debería dar "3.70". Pero Java genera "3.6999999999999997".
            //Para evitar éste problema se debe trabajar con la clase "java.math.BigDecimal".
            return (new BigDecimal(numero)).setScale(decimales,BigDecimal.ROUND_HALF_UP);
        }
        catch (NumberFormatException e)
        {
            return null;
        }
        catch (ArithmeticException e)
        {
            return null;
        }
    }

    /**
     * Esta función recostea el items especificado en el rango de fechas especificado.
     * @param padre El formulario padre para el cuadro de mensaje. Puede ser cualquier objeto que
     * hereda de la clase <I>Component</I>. Por ejemplo: JFrame, JInternalFrame
     * @param objParSis El objeto ZafParSis.
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * Esta función recibe la conexión porque está pensada para utilizar transaccionamiento.
     * Es decir, si no se completa toda la transacción habría que hacer un rollback.
     * @param intCodEmp El código de la empresa en la que se ingresó al sistema.
     * @param strCodItm El código del item a procesar.
     * @param strFecDes La fecha desde la que se debe recostear el item.
     * @param strFecHas La fecha hasta la que se debe recostear el item.
     * @param strFor El formato en que se encuentran strFecDes y strFecHas. Ejemplo: "dd/MM/yyyy", "yyyy/MM/dd".
     * @return true: Si se pudo recostear el item.
     * <BR>false: En el caso contrario.
     */
    public boolean recostearItm2009RangoFechas(java.awt.Component padre, Librerias.ZafParSis.ZafParSis objParSis, java.sql.Connection conexion, int intCodEmp, String strCodItm, String strFecDes, String strFecHas, String strFor)
    {
        return recostearItm2009(0, padre, objParSis, conexion, intCodEmp, strCodItm, strFecDes, strFecHas, strFor);
    }

    /**
     * Esta función recostea el item especificado desde la fecha especificada. Es decir, el item es
     * recosteado desde el primer movimiento en la fecha especificada hasta el último movimiento que
     * ha tenido dicho item en la última fecha que tuvo movimiento.
     * @param padre El formulario padre para el cuadro de mensaje. Puede ser cualquier objeto que
     * hereda de la clase <I>Component</I>. Por ejemplo: JFrame, JInternalFrame
     * @param objParSis El objeto ZafParSis.
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * Esta función recibe la conexión porque está pensada para utilizar transaccionamiento.
     * Es decir, si no se completa toda la transacción habría que hacer un rollback.
     * @param intCodEmp El código de la empresa en la que se ingresó al sistema.
     * @param strCodItm El código del item a procesar.
     * @param strFecDes La fecha desde la que se debe recostear el item.
     * @param strFor El formato en que se encuentran strFecDes. Ejemplo: "dd/MM/yyyy", "yyyy/MM/dd".
     * @return true: Si se pudo recostear el item.
     * <BR>false: En el caso contrario.
     */
    public boolean recostearItm2009DesdeFecha(java.awt.Component padre, Librerias.ZafParSis.ZafParSis objParSis, java.sql.Connection conexion, int intCodEmp, String strCodItm, String strFecDes, String strFor)
    {
        return recostearItm2009(1, padre, objParSis, conexion, intCodEmp, strCodItm, strFecDes, null, strFor);
    }

    /**
     * Esta función recostea los items especificados en la empresa grupo.
     * @param intTipPro Tipo de proceso. Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Recostear el item en el rango de fechas especificado.
     * <LI>1: Recostear el item desde el documento especificado hasta el ultimo movimiento.
     * </UL>
     * @param padre El formulario padre para el cuadro de mensaje. Puede ser cualquier objeto que
     * hereda de la clase <I>Component</I>. Por ejemplo: JFrame, JInternalFrame
     * @param objParSis El objeto ZafParSis.
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * Esta función recibe la conexión porque está pensada para utilizar transaccionamiento.
     * Es decir, si no se completa toda la transacción habría que hacer un rollback.
     * @param intCodEmp El código de la empresa en la que se ingresó al sistema.
     * @param strCodItm El código del item a procesar.
     * @param strFecDes La fecha desde la que se debe recostear el item.
     * @param strFecHas La fecha hasta la que se debe recostear el item.
     * @param strFor El formato en que se encuentran strFecDes y strFecHas. Ejemplo: "dd/MM/yyyy", "yyyy/MM/dd".
     * @return true: Si se pudo recostear el item.
     * <BR>false: En el caso contrario.
     */
    private boolean recostearItm2009(int intTipPro, java.awt.Component padre, Librerias.ZafParSis.ZafParSis objParSis, java.sql.Connection conexion, int intCodEmp, String strCodItm, String strFecDes, String strFecHas, String strFor)
    {
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
        java.util.ArrayList arlDat=new java.util.ArrayList();
        java.util.ArrayList arlReg;
        BigDecimal bgdSalIniUni, bgdSalIniVal, bgdCan, bgdSalUni, bgdSalVal, bgdCosPro, bgdCosUniUtiDevVen, bgdCosUniRegAnt;
        java.util.Date datFecAux;
        int i, j, intNumDec;
        String strCodLocAux, strCodTipDocAux, strCodDocAux;
        StringBuffer stb=new StringBuffer();
        boolean blnUtiCosUniArl;
        boolean blnRes=true;
        try
        {
            if (conexion!=null)
            {
                stm=conexion.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);
                intNumDec=objParSis.getDecimalesBaseDatos();
                //Paso 1: Obtener los saldos iniciales.
                if (intCodEmp==objParSis.getCodigoEmpresaGrupo())
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a2.nd_exiGrp AS nd_exi, a2.nd_valExiGrp AS nd_valExi";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itmAct=a3.co_itm)";
                    strSQL+=" WHERE a3.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + strCodItm + ")";
                    strSQL+=" AND a1.fe_doc<'" + this.formatearFecha(strFecDes,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=" AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                    strSQL+=" AND ( (a1.fe_doc<'2009/05/01'"; //Periodo antes del 2009/05/01 (Excluir: compras y ventas entre empresas, transferencias de empresas).
                    //Temporal hasta que se busque una mejor manera de hacerlo.
                    strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli IN (603, 2600, 1039) AND a1.co_cli IS NOT NULL)";
                    strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli IN (2854, 2105, 789) AND a1.co_cli IS NOT NULL)";
                    strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli IN (2858, 453, 832) AND a1.co_cli IS NOT NULL)";
                    strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli IN (3117, 498, 2294) AND a1.co_cli IS NOT NULL)";
//                    strSQL+=" ) OR (a1.fe_doc>='2009/05/01') )";  //Periodo después del 2009/05/01 (Considerar todo).
                    strSQL+=" ) OR (a1.fe_doc>='2009/05/01'";  //Periodo después del 2009/05/01 (Considerar todo).
                    //Temporal hasta que se busque una mejor manera de hacerlo.
                    strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli IN (603, 2600, 1039) AND a1.co_cli IS NOT NULL)";
                    strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli IN (2854, 2105, 789) AND a1.co_cli IS NOT NULL)";
                    strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli IN (2858, 453, 832) AND a1.co_cli IS NOT NULL)";
                    strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli IN (3117, 498, 2294) AND a1.co_cli IS NOT NULL)";
                    //Incluir documentos donde CASTEK compró en terminal "L" pero TUVAL vendió en terminal "S".
                    strSQL+=" OR (a1.co_emp=1 AND a1.co_loc=4 AND a1.co_tipDoc=1 AND a1.co_doc=113238)";
                    strSQL+=" OR (a1.co_emp=2 AND a1.co_loc=1 AND a1.co_tipDoc=2 AND a1.co_doc=1195)";
                    strSQL+=" ))";
                    //Excluir los "INBOVA: Ingreso a bodega en valores" y "EGBOVA: Egreso de bodega en valores". En Dic/2016 el Sr.Sensi dijo que ya no se excluyeran.
//                    strSQL+=" AND a1.co_tipDoc NOT IN (140, 141)";
                    strSQL+=" ORDER BY a1.fe_doc, a1.ne_secGrp, a2.co_reg";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a2.nd_exi, a2.nd_valExi";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.fe_doc<'" + this.formatearFecha(strFecDes,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=" AND a2.co_itm=" + strCodItm;
                    strSQL+=" AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                    strSQL+=" ORDER BY a1.fe_doc, a1.ne_secEmp, a2.co_reg";
                    rst=stm.executeQuery(strSQL);
                }
                bgdSalIniUni=BigDecimal.ZERO;
                bgdSalIniVal=BigDecimal.ZERO;
                if (rst.last())
                {
                    bgdSalIniUni=new BigDecimal(rst.getObject("nd_exi")==null?"0":(rst.getString("nd_exi").equals("")?"0":rst.getString("nd_exi"))) ;
                    bgdSalIniVal=new BigDecimal(rst.getObject("nd_valExi")==null?"0":(rst.getString("nd_valExi").equals("")?"0":rst.getString("nd_valExi"))) ;
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
                stm=conexion.createStatement();
                //Paso 2: Obtener los registros a actualizar.
                if (intCodEmp==objParSis.getCodigoEmpresaGrupo())
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a4.st_cosUniCal, a4.tx_natDoc, a1.st_reg";
                    strSQL+=", a2.nd_can, a2.nd_canDev, a2.nd_cosUniGrp AS nd_cosUni, a2.nd_porDes, a2.nd_cosTotGrp AS nd_cosTot, Null AS nd_salUni, Null AS nd_salVal, Null AS nd_cosPro";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itmAct=a3.co_itm)";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)";
                    strSQL+=" WHERE a3.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + strCodItm + ")";
                    if (intTipPro==0)
                    {
                        strSQL+=" AND a1.fe_doc BETWEEN '" + this.formatearFecha(strFecDes,strFor,objParSis.getFormatoFechaBaseDatos()) + "' AND '" + this.formatearFecha(strFecHas,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                    }
                    else
                    {
                        strSQL+=" AND a1.fe_doc>='" + this.formatearFecha(strFecDes,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                    }
                    strSQL+=" AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                    strSQL+=" AND ( (a1.fe_doc<'2009/05/01'"; //Periodo antes del 2009/05/01 (Excluir: compras y ventas entre empresas, transferencias de empresas).
                    //Temporal hasta que se busque una mejor manera de hacerlo.
                    strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli IN (603, 2600, 1039) AND a1.co_cli IS NOT NULL)";
                    strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli IN (2854, 2105, 789) AND a1.co_cli IS NOT NULL)";
                    strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli IN (2858, 453, 832) AND a1.co_cli IS NOT NULL)";
                    strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli IN (3117, 498, 2294) AND a1.co_cli IS NOT NULL)";
//                    strSQL+=" ) OR (a1.fe_doc>='2009/05/01') )";  //Periodo después del 2009/05/01 (Considerar todo).
                    strSQL+=" ) OR (a1.fe_doc>='2009/05/01'";  //Periodo después del 2009/05/01 (Considerar todo).
                    //Temporal hasta que se busque una mejor manera de hacerlo.
                    strSQL+=" AND NOT (a1.co_emp=1 AND a1.co_cli IN (603, 2600, 1039) AND a1.co_cli IS NOT NULL)";
                    strSQL+=" AND NOT (a1.co_emp=2 AND a1.co_cli IN (2854, 2105, 789) AND a1.co_cli IS NOT NULL)";
                    strSQL+=" AND NOT (a1.co_emp=3 AND a1.co_cli IN (2858, 453, 832) AND a1.co_cli IS NOT NULL)";
                    strSQL+=" AND NOT (a1.co_emp=4 AND a1.co_cli IN (3117, 498, 2294) AND a1.co_cli IS NOT NULL)";
                    //Incluir documentos donde CASTEK compró en terminal "L" pero TUVAL vendió en terminal "S".
                    strSQL+=" OR (a1.co_emp=1 AND a1.co_loc=4 AND a1.co_tipDoc=1 AND a1.co_doc=113238)";
                    strSQL+=" OR (a1.co_emp=2 AND a1.co_loc=1 AND a1.co_tipDoc=2 AND a1.co_doc=1195)";
                    strSQL+=" ))";
                    //Excluir los "INBOVA: Ingreso a bodega en valores" y "EGBOVA: Egreso de bodega en valores". En Dic/2016 el Sr.Sensi dijo que ya no se excluyeran.
//                    strSQL+=" AND a1.co_tipDoc NOT IN (140, 141)";
                    strSQL+=" ORDER BY a1.fe_doc, a1.ne_secGrp, a2.co_reg";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    //strSQL+="SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a3.st_cosUniCal, a3.tx_natDoc, a1.st_reg";
                    strSQL+="SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a3.st_cosUniCal, a3.tx_natDoc, (CASE WHEN a2.st_reg='I' THEN 'I' ELSE a1.st_reg END) AS st_reg";
                    strSQL+=", a2.nd_can, a2.nd_canDev, a2.nd_cosUni, a2.nd_porDes, a2.nd_cosTot, Null AS nd_salUni, Null AS nd_salVal, Null AS nd_cosPro";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a2.co_itm=" + strCodItm;
                    if (intTipPro==0)
                    {
                        strSQL+=" AND a1.fe_doc BETWEEN '" + this.formatearFecha(strFecDes,strFor,objParSis.getFormatoFechaBaseDatos()) + "' AND '" + this.formatearFecha(strFecHas,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                    }
                    else
                    {
                        strSQL+=" AND a1.fe_doc>='" + this.formatearFecha(strFecDes,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                    }
                    strSQL+=" AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                    strSQL+=" ORDER BY a1.fe_doc, a1.ne_secEmp, a2.co_reg";
                    rst=stm.executeQuery(strSQL);
                }
                //Limpiar arreglo de datos.
                arlDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    arlReg=new java.util.ArrayList();
                    arlReg.add(INT_REC_COD_EMP,rst.getString("co_emp"));
                    arlReg.add(INT_REC_COD_LOC,rst.getString("co_loc"));
                    arlReg.add(INT_REC_COD_TIP_DOC,rst.getString("co_tipDoc"));
                    arlReg.add(INT_REC_COD_DOC,rst.getString("co_doc"));
                    arlReg.add(INT_REC_COD_REG,rst.getString("co_reg"));
                    arlReg.add(INT_REC_EST_CUC,rst.getString("st_cosUniCal"));
                    arlReg.add(INT_REC_NAT_DOC,rst.getString("tx_natDoc"));
                    arlReg.add(INT_REC_EST_DOC,rst.getString("st_reg"));
                    arlReg.add(INT_REC_CAN,rst.getString("nd_can"));
                    arlReg.add(INT_REC_CAN_DEV,rst.getString("nd_canDev"));
                    arlReg.add(INT_REC_COS_UNI,rst.getString("nd_cosUni"));
                    arlReg.add(INT_REC_POR_DES,rst.getString("nd_porDes"));
                    arlReg.add(INT_REC_COS_TOT,rst.getString("nd_cosTot"));
                    arlReg.add(INT_REC_SAL_UNI,rst.getString("nd_salUni"));
                    arlReg.add(INT_REC_SAL_VAL,rst.getString("nd_salVal"));
                    arlReg.add(INT_REC_COS_PRO,rst.getString("nd_cosPro"));
                    arlDat.add(arlReg);
                }
                rst.close();
                rst=null;
                bgdSalUni=bgdSalIniUni;
                bgdSalVal=bgdSalIniVal;
                if (bgdSalUni.compareTo(BigDecimal.ZERO)==0)
                {
                    bgdCosPro=BigDecimal.ZERO;
                }
                else
                {
                    bgdCosPro=bgdSalVal.divide(bgdSalUni, intNumDec, BigDecimal.ROUND_HALF_UP);
                }
                //Paso 3: Actualizar los campos.
                for (i=0;i<arlDat.size();i++)
                {
                    if (intCodEmp==objParSis.getCodigoEmpresaGrupo())
                    {
                        //TEMPORAL HASTA QUE SE BUSQUE UNA MEJOR FORMA DE HACERLO: Se cambia el estado de costeo a "S" para el tipo de documento 76=AJUITM.
                        if (this.getIntValueAt(arlDat, i, INT_REC_COD_TIP_DOC)==76)
                        {
                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_EST_CUC, "S");
                        }
                    }
                    bgdCan=this.getBigDecimalValueAt(arlDat, i, INT_REC_CAN);
                    //Recalcular el costo promedio sólo cuando el documento está activo.
                    if (this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("A") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("R") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("C") || this.getStringValueAt(arlDat, i, INT_REC_EST_DOC).equals("F"))
                    {
                        bgdSalUni=bgdSalUni.add(bgdCan);
                        if (this.getStringValueAt(arlDat, i, INT_REC_EST_CUC).equals("S"))
                        {
                            bgdCosUniUtiDevVen=bgdCosPro;
                            //Calcular el costo de acuerdo al tipo de documento.
                            switch (this.getIntValueAt(arlDat, i, INT_REC_COD_TIP_DOC))
                            {
                                case 3: //DEVVEN: Devoluciones de ventas.
                                case 229: //DEVVENE: Devolución de ventas (Electrónica)
                                    //Obtener el costo unitario del item en la factura correspondiente a la devolución en ventas.
                                    if (intCodEmp==objParSis.getCodigoEmpresaGrupo())
                                    {
                                        //Armar la sentencia SQL.
                                        strSQL="";
                                        strSQL+="SELECT a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a2.nd_cosUniGrp AS nd_cosUni";
                                        strSQL+=" FROM tbm_cabMovInv AS a1";
                                        strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                        strSQL+=" INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itmAct=a3.co_itm)";
                                        strSQL+=" INNER JOIN (";
                                        strSQL+=" SELECT co_empRel, co_locRel, co_tipDocRel, co_docRel";
                                        strSQL+=" FROM tbr_cabMovInv";
                                        strSQL+=" WHERE co_emp=" + this.getStringValueAt(arlDat, i, INT_REC_COD_EMP);
                                        strSQL+=" AND co_loc=" + this.getStringValueAt(arlDat, i, INT_REC_COD_LOC);
                                        strSQL+=" AND co_tipDoc=" + this.getStringValueAt(arlDat, i, INT_REC_COD_TIP_DOC);
                                        strSQL+=" AND co_doc=" + this.getStringValueAt(arlDat, i, INT_REC_COD_DOC);
                                        strSQL+=" ) AS a4 ON (a2.co_emp=a4.co_empRel AND a2.co_loc=a4.co_locRel AND a2.co_tipDoc=a4.co_tipDocRel AND a2.co_doc=a4.co_docRel)";
                                        strSQL+=" WHERE a3.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + strCodItm + ")";
                                        strSQL+=" ORDER BY a2.co_reg";
                                        rst=stm.executeQuery(strSQL);
                                    }
                                    else
                                    {
                                        //Armar la sentencia SQL.
                                        //Para las devoluciones de ventas el Sistema obtiene el costo unitario de la factura asociada a la devolución.
                                        //En el proceso de unificación de items el campo "co_itm" de la factura se queda con el código viejo y el campo
                                        //"co_itmAct" con el código actual. Al tener la devolución el código actual no encontraría dicho código en la factura.
                                        //Es por eso que para éste caso se utiliza "co_itmAct" en lugar de "co_itm".
                                        //mientras que la DEVVEN queda con el nuevo código. Por eso para la FACVEN se utiliza "co_itmAct".
                                        strSQL="";
                                        strSQL+="SELECT a3.co_loc, a3.co_tipDoc, a3.co_doc, a3.fe_doc, a4.nd_cosUni";
                                        strSQL+=" FROM tbm_detMovInv AS a1";
                                        strSQL+=" INNER JOIN tbr_cabMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                        strSQL+=" INNER JOIN tbm_cabMovInv AS a3 ON (a2.co_empRel=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_tipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc)";
                                        strSQL+=" INNER JOIN tbm_detMovInv AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc)";
                                        strSQL+=" WHERE a1.co_emp=" + this.getStringValueAt(arlDat, i, INT_REC_COD_EMP);
                                        strSQL+=" AND a1.co_loc=" + this.getStringValueAt(arlDat, i, INT_REC_COD_LOC);
                                        strSQL+=" AND a1.co_tipDoc=" + this.getStringValueAt(arlDat, i, INT_REC_COD_TIP_DOC);
                                        strSQL+=" AND a1.co_doc=" + this.getStringValueAt(arlDat, i, INT_REC_COD_DOC);
                                        strSQL+=" AND a1.co_itm=" + strCodItm;
                                        strSQL+=" AND a1.co_itmAct=a4.co_itmAct";
                                        strSQL+=" ORDER BY a4.co_reg";
                                        rst=stm.executeQuery(strSQL);
                                    }
                                    strCodLocAux="";
                                    strCodTipDocAux="";
                                    strCodDocAux="";
                                    datFecAux=null;
                                    while (rst.next())
                                    {
                                        strCodLocAux=rst.getString("co_loc");
                                        strCodTipDocAux=rst.getString("co_tipDoc");
                                        strCodDocAux=rst.getString("co_doc");
                                        datFecAux=rst.getDate("fe_doc");
                                        bgdCosUniUtiDevVen=rst.getBigDecimal("nd_cosUni");
                                    }
                                    rst.close();
                                    rst=null;
                                    //Evaluar si se utiliza el costo unitario que se encuentra en el ArrayList o el que se encuentra en la Base de datos.
                                    if (intTipPro==0)
                                    {
                                        if (datFecAux.compareTo(this.parseDate(strFecDes, strFor))>0 && datFecAux.compareTo(this.parseDate(strFecHas, strFor))<0)
                                            blnUtiCosUniArl=true;
                                        else
                                            blnUtiCosUniArl=false;
                                    }
                                    else
                                    {
                                        if (datFecAux.compareTo(this.parseDate(strFecDes, strFor))>0)
                                            blnUtiCosUniArl=true;
                                        else
                                            blnUtiCosUniArl=false;
                                    }
                                    if (blnUtiCosUniArl)
                                    {
                                        //Utilizar el costo que se encuentra en el ArrayList.
                                        for (j=0;j<arlDat.size();j++)
                                        {
                                            if (this.getStringValueAt(arlDat, j, INT_REC_COD_LOC).equals(strCodLocAux) && this.getStringValueAt(arlDat, j, INT_REC_COD_TIP_DOC).equals(strCodTipDocAux) && this.getStringValueAt(arlDat, j, INT_REC_COD_DOC).equals(strCodDocAux))
                                            {
                                                bgdCosUniUtiDevVen=this.getBigDecimalValueAt(arlDat, j, INT_REC_COS_UNI);
                                                //Actualizar el costo de los documentos que deben actualizar su costo.
                                                ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + bgdCosUniUtiDevVen);
                                                //Actualizar el costo total del documento.
                                                ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + bgdCan.multiply(bgdCosUniUtiDevVen));
                                                break;
                                            }
                                        }
                                    }
                                    else
                                    {
                                        //Utilizar el costo que se encuentra en la Base de datos.
                                        //Actualizar el costo de los documentos que deben actualizar su costo.
                                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + bgdCosUniUtiDevVen);
                                        //Actualizar el costo total del documento.
                                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + bgdCan.multiply(bgdCosUniUtiDevVen));
                                    }
                                    break;
                                case 46: //TRAINV: Transferencias de inventario.
                                case 53: //TRINAJ: Transferencias de inventario.
                                case 58: //TRINDI: Transferencias de inventario.
                                case 60: //TRINNO: Transferencias de inventario.
                                case 66: //TRINCA: Transferencias de inventario.
                                case 85: //TRINMA: Transferencias de inventario.
                                case 96: //TRIADI: Transferencias de inventario.
                                case 97: //TRIACQ: Transferencias de inventario.
                                case 98: //TRIACM: Transferencias de inventario.
                                case 150: //TRINEA: Transferencias de inventario.
                                case 151: //TRINIA: Transferencias de inventario.
                                case 153: //TRINAU: Transferencias de inventario.
                                case 171: //TRIACV: Transferencias de inventario automáticas (Cotizaciones de venta).
                                case 172: //TRINRB: Transferencias de inventario (Reposición desde otras bodegas).
                                case 204: //TRINIM: Transferencias de inventario (Importación).
                                case 234: //TRINCL: Transferencias de inventario (Compras locales).
                                    //Utilizar el costo que se encuentra en el registro anterior. Las transferencias tienen 2 partes. El egreso
                                    //y el ingreso. Había casos donde se transfería todo el stock de una bodega a otra lo cual hacía que el costo
                                    //promedio para la siguiente transacción (ingreso) sea cero. Eso estaba mal ya que debería tomar el costo de
                                    //la transacción anterior (egreso).
                                    if (bgdCosPro.compareTo(BigDecimal.ZERO)==0)
                                    {
                                        if (i==0)
                                        {
                                            //Actualizar el costo de los documentos que deben actualizar su costo.
                                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + BigDecimal.ZERO);
                                            //Actualizar el costo total del documento.
                                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + BigDecimal.ZERO);
                                        }
                                        else
                                        {
                                            //Obtener el costo unitario del registro anterior.
                                            bgdCosUniRegAnt=this.getBigDecimalValueAt(arlDat, i-1, INT_REC_COS_UNI);
                                            if (bgdCosUniRegAnt.compareTo(BigDecimal.ZERO)==0)
                                            {
                                                //Actualizar el costo de los documentos que deben actualizar su costo.
                                                ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + BigDecimal.ZERO);
                                                //Actualizar el costo total del documento.
                                                ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + BigDecimal.ZERO);
                                            }
                                            else
                                            {
                                                //Actualizar el costo de los documentos que deben actualizar su costo.
                                                ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + bgdCosUniRegAnt);
                                                //Actualizar el costo total del documento.
                                                ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + bgdCan.multiply(bgdCosUniRegAnt));
                                            }
                                        }
                                    }
                                    else
                                    {
                                        //Hacer que el costo del ingreso de la transferencia lo tome del costo del egreso de la transferencia.
                                        if (bgdCan.compareTo(BigDecimal.ZERO)==-1)
                                        {
                                            //Actualizar el costo de los documentos que deben actualizar su costo.
                                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + bgdCosPro);
                                            //Actualizar el costo total del documento.
                                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + bgdCan.multiply(bgdCosPro));
                                        }
                                        else
                                        {
                                            //Utilizar el costo unitario del registro anterior.
                                            bgdCosUniRegAnt=this.getBigDecimalValueAt(arlDat, i-1, INT_REC_COS_UNI);
                                            //Actualizar el costo de los documentos que deben actualizar su costo.
                                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + bgdCosUniRegAnt);
                                            //Actualizar el costo total del documento.
                                            ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + bgdCan.multiply(bgdCosUniRegAnt));
                                        }
                                    }
                                    break;
                                default:
                                    //Actualizar el costo de los documentos que deben actualizar su costo.
                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_UNI, "" + bgdCosPro);
                                    //Actualizar el costo total del documento.
                                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_TOT, "" + bgdCan.multiply(bgdCosPro));
                                    break;
                            }
                            //Recalcular el costo promedio.
                            bgdSalVal=redondearBigDecimal(bgdSalVal.add(this.getBigDecimalValueAt(arlDat, i, INT_REC_COS_TOT)), intNumDec);
                            if (bgdSalUni.compareTo(BigDecimal.ZERO)==0)
                            {
                                bgdCosPro=BigDecimal.ZERO;
                            }
                            else
                            {
                                bgdCosPro=bgdSalVal.divide(bgdSalUni, intNumDec, BigDecimal.ROUND_HALF_UP);
                            }
                        }
                        else
                        {
                            //Recalcular el costo promedio.
                            bgdSalVal=redondearBigDecimal(bgdSalVal.add(this.getBigDecimalValueAt(arlDat, i, INT_REC_COS_TOT)), intNumDec);
                            if (bgdSalUni.compareTo(BigDecimal.ZERO)==0)
                            {
                                bgdCosPro=BigDecimal.ZERO;
                            }
                            else
                            {
                                bgdCosPro=bgdSalVal.divide(bgdSalUni, intNumDec, BigDecimal.ROUND_HALF_UP);
                            }
                        }
                    }
                    //Si el documento no está activo se mantienen los saldos en unidades y valores así como el costo promedio.
                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_UNI, "" + bgdSalUni);
                    ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_SAL_VAL, "" + bgdSalVal);
                    if (bgdSalUni.compareTo(BigDecimal.ZERO)==0 && bgdSalVal.compareTo(BigDecimal.ZERO)==0)
                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_PRO, "" + BigDecimal.ZERO);
                    else
                        ((java.util.ArrayList)arlDat.get(i)).set(INT_REC_COS_PRO, "" + bgdCosPro);
                }
                //Paso 4: Crear la tabla virtual que servirá para actualizar la tabla "tbm_detMovInv" con un solo update.
                for (i=0;i<arlDat.size();i++)
                {
                    if (i>0)
                        stb.append(" UNION ALL ");
                    stb.append("SELECT " + this.getStringValueAt(arlDat, i, INT_REC_COD_EMP) + " AS co_emp");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COD_LOC) + " AS co_loc");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COD_TIP_DOC) + " AS co_tipDoc");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COD_DOC) + " AS co_doc");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COD_REG) + " AS co_reg");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COS_UNI) + " AS nd_cosUni");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COS_TOT) + " AS nd_cosTot");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_SAL_UNI) + " AS nd_exi");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_SAL_VAL) + " AS nd_valExi");
                    stb.append(", " + this.getStringValueAt(arlDat, i, INT_REC_COS_PRO) + " AS nd_cosPro");
                }
                //Paso 5: Actualizar la tabla "tbm_detMovInv".
                if (arlDat.size()>0)
                {
                    if (intCodEmp==objParSis.getCodigoEmpresaGrupo())
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_detMovInv";
                        strSQL+=" SET nd_cosUniGrp=a1.nd_cosUni";
                        strSQL+=", nd_cosTotGrp=a1.nd_cosTot";
                        strSQL+=", nd_exiGrp=a1.nd_exi";
                        strSQL+=", nd_valExiGrp=a1.nd_valExi";
                        strSQL+=", nd_cosProGrp=a1.nd_cosPro";
                        strSQL+=" FROM (";
                        strSQL+=stb.toString();
                        strSQL+=" ) AS a1";
                        strSQL+=" WHERE tbm_detMovInv.co_emp=a1.co_emp AND tbm_detMovInv.co_loc=a1.co_loc AND tbm_detMovInv.co_tipDoc=a1.co_tipDoc AND tbm_detMovInv.co_doc=a1.co_doc AND tbm_detMovInv.co_reg=a1.co_reg";
                        stm.executeUpdate(strSQL);
                    }
                    else
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_detMovInv";
                        strSQL+=" SET nd_cosUni=a1.nd_cosUni";
                        strSQL+=", nd_cosTot=a1.nd_cosTot";
                        strSQL+=", nd_exi=a1.nd_exi";
                        strSQL+=", nd_valExi=a1.nd_valExi";
                        strSQL+=", nd_cosPro=a1.nd_cosPro";
                        strSQL+=" FROM (";
                        strSQL+=stb.toString();
                        strSQL+=" ) AS a1";
                        strSQL+=" WHERE tbm_detMovInv.co_emp=a1.co_emp AND tbm_detMovInv.co_loc=a1.co_loc AND tbm_detMovInv.co_tipDoc=a1.co_tipDoc AND tbm_detMovInv.co_doc=a1.co_doc AND tbm_detMovInv.co_reg=a1.co_reg";
                        stm.executeUpdate(strSQL);
                    }
                }
                //Paso 6: Actualizar la tabla "tbm_inv" sólo si es necesario.
                if (intCodEmp==objParSis.getCodigoEmpresaGrupo())
                {
                    if (intTipPro==0)
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT COUNT(*)";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itmAct=a3.co_itm)";
                        strSQL+=" WHERE a3.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + strCodItm + ")";
                        strSQL+=" AND a1.fe_doc>'" + this.formatearFecha(strFecHas,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                        rst=stm.executeQuery(strSQL);
                        if (rst.next())
                        {
                            if (rst.getInt(1)==0)
                            {
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE tbm_inv";
                                strSQL+=" SET nd_valExi=" + this.redondear(this.getStringValueAt(arlDat, arlDat.size()-1, INT_REC_SAL_VAL), intNumDec);
                                strSQL+=", nd_cosUni=" + this.redondear(this.getStringValueAt(arlDat, arlDat.size()-1, INT_REC_COS_PRO), intNumDec);
                                strSQL+=" FROM (";
                                strSQL+=" SELECT co_emp, co_itm";
                                strSQL+=" FROM tbm_equInv";
                                strSQL+=" WHERE co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + strCodItm + ")";
                                strSQL+=" AND co_emp=" + objParSis.getCodigoEmpresaGrupo();
                                strSQL+=" ) AS a1";
                                strSQL+=" WHERE tbm_inv.co_emp=a1.co_emp AND tbm_inv.co_itm=a1.co_itm";
                                stm.executeUpdate(strSQL);
                            }
                        }
                        rst.close();
                        rst=null;
                    }
                }
                else
                {
                    if (intTipPro==0)
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT COUNT(*)";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        strSQL+=" AND a2.co_itm=" + strCodItm;
                        strSQL+=" AND a1.fe_doc>'" + this.formatearFecha(strFecHas,strFor,objParSis.getFormatoFechaBaseDatos()) + "'";
                        rst=stm.executeQuery(strSQL);
                        if (rst.next())
                        {
                            if (rst.getInt(1)==0)
                            {
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE tbm_inv";
                                strSQL+=" SET nd_valExi=" + this.redondear(this.getStringValueAt(arlDat, arlDat.size()-1, INT_REC_SAL_VAL), intNumDec);
                                strSQL+=", nd_cosUni=" + this.redondear(this.getStringValueAt(arlDat, arlDat.size()-1, INT_REC_COS_PRO), intNumDec);
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_itm=" + strCodItm;
                                stm.executeUpdate(strSQL);
                            }
                        }
                        rst.close();
                        rst=null;
                    }
                }
                stm.close();
                stm=null;
                arlDat.clear();
                arlDat=null;
                stb=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }

    /**
     * Esta función obtiene el año correspondiente a la fecha especificada.
     * @param fecha La fecha que se desea utilizar.
     * @return El año correspondiente a la fecha especificada.
     * <BR>Nota.- Si hay un error al obtener el año la función devolverá "-1".
     */
    public int getAnio(java.util.Date fecha)
    {
        java.util.Calendar cal=java.util.Calendar.getInstance();
        int intRes;
        try
        {
            cal.setTime(fecha);
            intRes=cal.get(java.util.Calendar.YEAR);
            cal=null;
        }
        catch (Exception e)
        {
            intRes=-1;
        }
        return intRes;
    }

    /**
     * Esta función obtiene el mes correspondiente a la fecha especificada.
     * @param fecha La fecha que se desea utilizar.
     * @return El mes correspondiente a la fecha especificada.
     * <BR>Nota.- Si hay un error al obtener el mes la función devolverá "-1".
     */
    public int getMes(java.util.Date fecha)
    {
        java.util.Calendar cal=java.util.Calendar.getInstance();
        int intRes;
        try
        {
            cal.setTime(fecha);
            intRes=cal.get(java.util.Calendar.MONTH);
            cal=null;
        }
        catch (Exception e)
        {
            intRes=-1;
        }
        return intRes;
    }

    /**
     * Esta función obtiene el día correspondiente a la fecha especificada.
     * @param fecha La fecha que se desea utilizar.
     * @return El día correspondiente a la fecha especificada.
     * <BR>Nota.- Si hay un error al obtener el día la función devolverá "-1".
     */
    public int getDia(java.util.Date fecha)
    {
        java.util.Calendar cal=java.util.Calendar.getInstance();
        int intRes;
        try
        {
            cal.setTime(fecha);
            intRes=cal.get(java.util.Calendar.DATE);
            cal=null;
        }
        catch (Exception e)
        {
            intRes=-1;
        }
        return intRes;
    }

    /**
     * Esta función valida que el "Número de documento" sea válido. Es decir, que cumpla las siguientes condiciones:
     * <UL>
     * <LI>No esté vacío.
     * <LI>No exista otro documento con el mismo número.
     * </UL>
     * Si el documento no es válido se muestra un mensaje preguntando si desea que el Sistema le asigne el número de documento a utilizar.
     * @param padre El formulario padre para el cuadro de mensaje. Puede ser cualquier objeto que
     * herede de la clase <I>Component</I>. Por ejemplo: JFrame, JInternalFrame
     * @param stringConexion La cadena que contiene información para conectarse a la base de datos.
     * @param usuario El usuario para conectarse a la base de datos.
     * @param clave La clave del usuario para conectarse a la base de datos.
     * @param tabla La tabla donde se validará el "Número de documento".
     * <UL>
     * <LI>1: tbm_cabMovInv.
     * <LI>2: tbm_cabPag.
     * <LI>3: tbm_cabDia.
     * <LI>4: tbm_cabRolPag.
     * </UL>
     * @param codigoEmpresa El código de la empresa.
     * @param codigoLocal El código del local.
     * @param codigoTipoDocumento El código del tipo de documento.
     * @param codigoDocumento El código del documento. Si el documento no está guardado se deberá asignar <I>null</I>.
     * @param numeroDocumento El JTextField donde se encuentra el "Número de documento".
     * @return true: Si el "Número de documento" es válido.
     * <BR>false: En el caso contrario.
     */
    public boolean isNumeroDocumentoValido(java.awt.Component padre, String stringConexion, String usuario, String clave, int tabla, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, Integer codigoDocumento, javax.swing.JTextField numeroDocumento)
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
        boolean blnRes=true;
        try
        {
            //Validar que el "Número de documento" no esté vacío.
            if (numeroDocumento.getText().equals(""))
            {
                if (javax.swing.JOptionPane.showConfirmDialog(padre, "<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>¿Desea que el Sistema le asigne el número de documento?</HTML>", "Mensaje del sistema Zafiro", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
                {
                    con=java.sql.DriverManager.getConnection(stringConexion, usuario, clave);
                    if (con!=null)
                    {
                        stm=con.createStatement();
                        //Obtener el último número de documento ingresado en "tbm_cabTipDoc".
                        strSQL="";
                        strSQL+="SELECT a1.ne_ultDoc";
                        strSQL+=" FROM tbm_cabTipDoc AS a1";
                        strSQL+=" WHERE a1.co_emp=" + codigoEmpresa;
                        strSQL+=" AND a1.co_loc=" + codigoLocal;
                        strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                        rst=stm.executeQuery(strSQL);
                        if (rst.next())
                        {
                            numeroDocumento.setText("" + (rst.getInt("ne_ultDoc")+1));
                        }
                        rst.close();
                        stm.close();
                        con.close();
                        rst=null;
                        stm=null;
                        con=null;
                    }
                }
                blnRes=false;
            }
            else
            {
                con=java.sql.DriverManager.getConnection(stringConexion, usuario, clave);
                if (con!=null)
                {
                    stm=con.createStatement();
                    //Validar que el "Código alterno" no se repita.
                    strSQL="";
                    switch (tabla)
                    {
                        case 1: //tbm_cabMovInv
                            strSQL="";
                            strSQL+="SELECT a1.ne_numDoc";
                            strSQL+=" FROM tbm_cabMovInv AS a1";
                            strSQL+=" WHERE a1.co_emp=" + codigoEmpresa;
                            strSQL+=" AND a1.co_loc=" + codigoLocal;
                            strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                            strSQL+=" AND a1.ne_numdoc=" + numeroDocumento.getText();
                            if (codigoDocumento!=null)
                                strSQL+=" AND a1.co_doc<>" + codigoDocumento.intValue();
                            break;
                        case 2: //tbm_cabPag
                            strSQL="";
                            strSQL+="SELECT a1.ne_numDoc1";
                            strSQL+=" FROM tbm_cabPag AS a1";
                            strSQL+=" WHERE a1.co_emp=" + codigoEmpresa;
                            strSQL+=" AND a1.co_loc=" + codigoLocal;
                            strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                            strSQL+=" AND a1.ne_numDoc1=" + numeroDocumento.getText();
                            if (codigoDocumento!=null)
                                strSQL+=" AND a1.co_doc<>" + codigoDocumento.intValue();
                            break;
                        case 3: //tbm_cabDia
                            strSQL="";
                            strSQL+="SELECT a1.tx_numDia";
                            strSQL+=" FROM tbm_cabDia AS a1";
                            strSQL+=" WHERE a1.co_emp=" + codigoEmpresa;
                            strSQL+=" AND a1.co_loc=" + codigoLocal;
                            strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                            strSQL+=" AND a1.tx_numDia='" + numeroDocumento.getText().replaceAll("'", "''") + "'";
                            if (codigoDocumento!=null)
                                strSQL+=" AND a1.co_dia<>" + codigoDocumento.intValue();
                            break;
                        case 4: //tbm_cabRolPag
                            strSQL="";
                            strSQL+="SELECT a1.ne_numDoc";
                            strSQL+=" FROM tbm_cabRolPag AS a1";
                            strSQL+=" WHERE a1.co_emp=" + codigoEmpresa;
                            strSQL+=" AND a1.co_loc=" + codigoLocal;
                            strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                            strSQL+=" AND a1.ne_numdoc=" + numeroDocumento.getText();
                            if (codigoDocumento!=null)
                                strSQL+=" AND a1.co_doc<>" + codigoDocumento.intValue();
                            break;
                    }
                    rst=stm.executeQuery(strSQL);
                    if (rst.next())
                    {
                        blnRes=false;
                    }
                    rst.close();
                    rst=null;
                    if (blnRes==false)
                    {
                        if (javax.swing.JOptionPane.showConfirmDialog(padre, "<HTML>El número de documento <FONT COLOR=\"blue\">" + numeroDocumento.getText() + "</FONT> ya existe.<BR>¿Desea que el Sistema le asigne el número de documento?</HTML>", "Mensaje del sistema Zafiro", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
                        {
                            //Obtener el último número de documento ingresado en "tbm_cabTipDoc".
                            strSQL="";
                            strSQL+="SELECT a1.ne_ultDoc";
                            strSQL+=" FROM tbm_cabTipDoc AS a1";
                            strSQL+=" WHERE a1.co_emp=" + codigoEmpresa;
                            strSQL+=" AND a1.co_loc=" + codigoLocal;
                            strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                            rst=stm.executeQuery(strSQL);
                            if (rst.next())
                            {
                                numeroDocumento.setText("" + (rst.getInt("ne_ultDoc")+1));
                            }
                            rst.close();
                            rst=null;
                        }
                    }
                    stm.close();
                    con.close();
                    stm=null;
                    con=null;
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }
    
     
      //
     public String remplazarComillaSimple(Object cadena)
    {
        if (cadena==null)
            return "Null";
        if (cadena.toString().equals(""))
            return "Null";
       cadena= cadena.toString().replaceAll("'", "");
       cadena= cadena.toString().trim();
       return  cadena.toString();
    }
    
    
    
    public void setIntValueAt(java.util.ArrayList objeto, int row, int col, Integer datoSetear){
        java.util.ArrayList arlAux;
        try{
            if (objeto==null){}
            else{
                arlAux=(java.util.ArrayList)objeto.get(row);
                if (arlAux==null){}
                else
                    if (arlAux.get(col)==null){}
                    else
                        arlAux.set((col), datoSetear);
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
        }
    }
    
    
    
    /**
     * Esta función permite determinar si el terminal del item necesita ser confirmado. 
     * @param padre El formulario padre para el cuadro de mensaje. Puede ser cualquier objeto que
     * herede de la clase <I>Component</I>. Por ejemplo: JFrame, JInternalFrame
     * @param objParSis El objeto ZafParSis.
     * @param intCodEmp El usuario para conectarse a la base de datos.
     * @param strTerminal La clave del usuario para conectarse a la base de datos.
     * @return true: Si el código todavía no existe.
     * <BR>false: En el caso contrario.
     */
    public String getCfgConfirma(java.awt.Component padre, Librerias.ZafParSis.ZafParSis objParSis,int intCodEmp, int intCodBod, String strTerminal)
    {
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql, strIngEgrFis="";
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
               stmLoc=conLoc.createStatement();
               strSql="";
               strSql+="SELECT st_merIngEgrFisBod FROM tbm_cfgTerInvCon WHERE co_emp="+intCodEmp+" AND co_bod="+intCodBod;
               strSql+=" AND tx_ter='"+strTerminal+"'" ;
               strSql+=" AND st_reg='A'";
               rstLoc=stmLoc.executeQuery(strSql);
               if (rstLoc.next()){
                     strIngEgrFis = rstLoc.getString("st_merIngEgrFisBod");
               }
               rstLoc.close(); 
               stmLoc.close();
               rstLoc=null;
               stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch (java.sql.SQLException e)
        {
            strIngEgrFis="";
            this.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e)
        {
             strIngEgrFis="";
            this.mostrarMsgErr_F1(padre, e);
        }
        return  strIngEgrFis;
    }

    /**
     * Esta función costea los items del documento especificado.
     * @param padre El formulario padre para el cuadro de mensaje. Puede ser cualquier objeto que
     * hereda de la clase <I>Component</I>. Por ejemplo: JFrame, JInternalFrame
     * @param objParSis El objeto ZafParSis.
     * @param con El objeto que contiene la conexión a la base de datos.
     * Esta función recibe la conexión porque está pensada para utilizar transaccionamiento.
     * Es decir, si no se completa toda la transacción habría que hacer un rollback.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intCodTipDoc El código del tipo de documento.
     * @param intCodDoc El código del documento.
     * @return true: Si se pudo costear el item.
     * <BR>false: En el caso contrario.
     */
    public boolean costearDocumento(java.awt.Component padre, Librerias.ZafParSis.ZafParSis objParSis, java.sql.Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc)
    {
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
        java.util.ArrayList arlDat=new java.util.ArrayList();
        java.util.ArrayList arlReg;
        String strCosUniCal;
        int i, intCodItm, intNumDec;
        BigDecimal bgdCan, bgdCosUni, bgdCosTot, bgdSalUni, bgdSalVal, bgdCosPro;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Sólo procesar la devoluciones que son por cantidades.
                if (intCodTipDoc==229) //229=DEVVENE
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.st_tipDev";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc;
                    strSQL+=" AND a1.co_doc=" + intCodDoc;
                    rst=stm.executeQuery(strSQL);
                    if (rst.next())
                    {
                        if (!parseString(rst.getString("st_tipDev")).equals("C"))
                        {
                            rst.close();
                            stm.close();
                            return true;
                        }
                    }
                }
                intNumDec=objParSis.getDecimalesBaseDatos();
                //Paso 1: Obtener los items a costear.
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_reg, a2.st_cosUniCal, a1.co_itm, a1.nd_can, a1.nd_cosUni, a1.nd_cosTot";
                strSQL+=" FROM tbm_detMovInv AS a1";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc;
                strSQL+=" AND a1.co_doc=" + intCodDoc;
                rst=stm.executeQuery(strSQL);
                //Limpiar arreglo de datos.
                arlDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    arlReg=new java.util.ArrayList();
                    arlReg.add(0,rst.getString("co_reg"));
                    arlReg.add(1,rst.getString("st_cosUniCal"));
                    arlReg.add(2,rst.getString("co_itm"));
                    arlReg.add(3,rst.getString("nd_can"));
                    arlReg.add(4,rst.getString("nd_cosUni"));
                    arlReg.add(5,rst.getString("nd_cosTot"));
                    arlDat.add(arlReg);
                }
                rst.close();
                rst=null;
                //Paso 2: Costear los items.
                for (i=0;i<arlDat.size();i++)
                {
                    strCosUniCal=this.getStringValueAt(arlDat, i, 1);
                    intCodItm=this.getIntValueAt(arlDat, i, 2);
                    bgdCan=this.getBigDecimalValueAt(arlDat, i, 3);
                    bgdCosUni=this.getBigDecimalValueAt(arlDat, i, 4);
                    bgdCosTot=this.getBigDecimalValueAt(arlDat, i, 5);
                    //Paso 3: Obtener los saldos iniciales.
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT nd_stkAct, nd_valExi, nd_cosUni";
                    strSQL+=" FROM tbm_inv";
                    strSQL+=" WHERE co_emp=" + intCodEmp;
                    strSQL+=" AND co_itm=" + intCodItm;
                    rst=stm.executeQuery(strSQL);
                    bgdSalUni=BigDecimal.ZERO;
                    bgdSalVal=BigDecimal.ZERO;
                    bgdCosPro=BigDecimal.ZERO;
                    if (rst.next())
                    {
                        bgdSalUni=(bgdSalUni=rst.getBigDecimal("nd_stkAct"))==null?BigDecimal.ZERO:bgdSalUni;
                        bgdSalVal=(bgdSalVal=rst.getBigDecimal("nd_valExi"))==null?BigDecimal.ZERO:bgdSalVal;
                        bgdCosPro=(bgdCosPro=rst.getBigDecimal("nd_cosUni"))==null?BigDecimal.ZERO:bgdCosPro;
                    }
                    rst.close();
                    rst=null;
                    if (strCosUniCal.equals("S"))
                    {
                        //Calcular el costo de acuerdo al tipo de documento.
                        switch (intCodTipDoc)
                        {
                            case 3: //DEVVEN: Devoluciones de ventas.
                            case 229: //DEVVENE: Devolución de ventas (Electrónica)
                                //Armar la sentencia SQL.
                                //Para las devoluciones de ventas el Sistema obtiene el costo unitario de la factura asociada a la devolución.
                                //En el proceso de unificación de items el campo "co_itm" de la factura se queda con el código viejo y el campo
                                //"co_itmAct" con el código actual. Al tener la devolución el código actual no encontraría dicho código en la factura.
                                //Es por eso que para éste caso se utiliza "co_itmAct" en lugar de "co_itm".
                                //mientras que la DEVVEN queda con el nuevo código. Por eso para la FACVEN se utiliza "co_itmAct".
                                strSQL="";
                                strSQL+="SELECT a3.nd_cosUni";
                                strSQL+=" FROM tbm_detMovInv AS a1";
                                strSQL+=" INNER JOIN tbr_cabMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_detMovInv AS a3 ON (a2.co_empRel=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_tipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc)";
                                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                                strSQL+=" AND a1.co_loc=" + intCodLoc;
                                strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc;
                                strSQL+=" AND a1.co_doc=" + intCodDoc;
                                strSQL+=" AND a1.co_itm=" + intCodItm;
                                strSQL+=" AND a1.co_itmAct=a3.co_itmAct";
                                strSQL+=" ORDER BY a3.co_reg";
                                rst=stm.executeQuery(strSQL);
                                if (rst.next())
                                {
                                    bgdCosUni=(bgdCosUni=rst.getBigDecimal("nd_cosUni"))==null?BigDecimal.ZERO:bgdCosUni;
                                    bgdCosTot=this.redondearBigDecimal(bgdCan.multiply(bgdCosUni), intNumDec);
                                }
                                rst.close();
                                rst=null;
                                break;
                            case 46: //TRAINV: Transferencias de inventario.
                            case 53: //TRINAJ: Transferencias de inventario.
                            case 58: //TRINDI: Transferencias de inventario.
                            case 60: //TRINNO: Transferencias de inventario.
                            case 66: //TRINCA: Transferencias de inventario.
                            case 85: //TRINMA: Transferencias de inventario.
                            case 96: //TRIADI: Transferencias de inventario.
                            case 97: //TRIACQ: Transferencias de inventario.
                            case 98: //TRIACM: Transferencias de inventario.
                            case 150: //TRINEA: Transferencias de inventario.
                            case 151: //TRINIA: Transferencias de inventario.
                            case 153: //TRINAU: Transferencias de inventario.
                            case 171: //TRIACV: Transferencias de inventario automáticas (Cotizaciones de venta).
                            case 172: //TRINRB: Transferencias de inventario (Reposición desde otras bodegas).
                            case 204: //TRINIM: Transferencias de inventario (Importación).
                            case 234: //TRINCL: Transferencias de inventario (Compras locales).
                                bgdCosUni=bgdCosPro;
                                bgdCosTot=this.redondearBigDecimal(bgdCan.multiply(bgdCosUni), intNumDec);
                                break;
                            default:
                                bgdCosUni=bgdCosPro;
                                bgdCosTot=this.redondearBigDecimal(bgdCan.multiply(bgdCosUni), intNumDec);
                                break;
                        }
                    }
                    //Calcular saldos.
                    //bgdSalUni=bgdSalUni.add(bgdCan);
                    bgdSalVal=bgdSalVal.add(bgdCosTot);
                    bgdCosPro=(bgdSalUni.compareTo(BigDecimal.ZERO)==0?BigDecimal.ZERO:bgdSalVal.divide(bgdSalUni, intNumDec, BigDecimal.ROUND_HALF_UP));
                    //Paso 5: Actualizar la tabla "tbm_detMovInv".
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="UPDATE tbm_detMovInv";
                    if (strCosUniCal.equals("S"))
                    {
                        strSQL+=" SET nd_cosUni=" + bgdCosUni;
                        strSQL+=", nd_cosTot=" + bgdCosTot;
                        strSQL+=", nd_exi=" + bgdSalUni;
                    }
                    else
                    {
                        strSQL+=" SET nd_exi=" + bgdSalUni;
                    }
                    strSQL+=", nd_valExi=" + bgdSalVal;
                    strSQL+=", nd_cosPro=" + bgdCosPro;
                    strSQL+=" WHERE co_emp=" + intCodEmp;
                    strSQL+=" AND co_loc=" + intCodLoc;
                    strSQL+=" AND co_tipDoc=" + intCodTipDoc;
                    strSQL+=" AND co_doc=" + intCodDoc;
                    strSQL+=" AND co_reg=" + this.getIntValueAt(arlDat, i, 0);
                    stm.executeUpdate(strSQL);
                    switch (intCodTipDoc)
                    {
                        case 46: //TRAINV: Transferencias de inventario.
                        case 53: //TRINAJ: Transferencias de inventario.
                        case 58: //TRINDI: Transferencias de inventario.
                        case 60: //TRINNO: Transferencias de inventario.
                        case 66: //TRINCA: Transferencias de inventario.
                        case 85: //TRINMA: Transferencias de inventario.
                        case 96: //TRIADI: Transferencias de inventario.
                        case 97: //TRIACQ: Transferencias de inventario.
                        case 98: //TRIACM: Transferencias de inventario.
                        case 150: //TRINEA: Transferencias de inventario.
                        case 151: //TRINIA: Transferencias de inventario.
                        case 153: //TRINAU: Transferencias de inventario.
                        case 171: //TRIACV: Transferencias de inventario automáticas (Cotizaciones de venta).
                        case 172: //TRINRB: Transferencias de inventario (Reposición desde otras bodegas).
                        case 204: //TRINIM: Transferencias de inventario (Importación).
                        case 234: //TRINCL: Transferencias de inventario (Compras locales).
                            //No actualizar "tbm_inv" porque una transferencia es un egreso y un ingreso del mismo item. Es decir, no se ven afectados los saldos.
                            break;
                        default:
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="UPDATE tbm_inv";
                            strSQL+=" SET nd_valExi=" + bgdSalVal;
                            strSQL+=", nd_cosUni=" + bgdCosPro;
                            strSQL+=" WHERE co_emp=" + intCodEmp;
                            strSQL+=" AND co_itm=" + intCodItm;
                            stm.executeUpdate(strSQL);
                            break;
                    }
                }
                //Paso 6: Insertar el asiento de diario.
                if (intCodTipDoc==228) //228=FACVENE
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_cabDia (co_emp, co_loc, co_tipDoc, co_dia, tx_numDia, fe_dia, tx_glo, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod)";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, CASE WHEN a1.co_tipDoc=1 THEN 48  WHEN a1.co_tipDoc=228 THEN 235 END AS co_tipDoc";
                    strSQL+=" , a1.co_doc AS co_dia, a1.ne_numDoc AS tx_numDia";
                    strSQL+=" , a1.fe_doc AS fe_dia, Null AS tx_glo, a1.st_reg, a1.fe_ing, a1.fe_ultMod, a1.co_usrIng, a1.co_usrMod";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc;
                    strSQL+=" AND a1.co_doc=" + intCodDoc;
                    stm.executeUpdate(strSQL);
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detDia(co_emp,  co_loc, co_tipDoc, co_dia, co_reg, co_cta, nd_monDeb, nd_monHab, tx_ref, st_regRep)";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, CASE WHEN a1.co_tipDoc=1 THEN 48  WHEN a1.co_tipDoc=228 THEN 235 END AS co_tipDoc";
                    strSQL+=" , a1.co_doc AS co_dia, a2.co_bod AS co_reg, a3.co_ctaExi AS co_cta";
                    strSQL+=" , (CASE WHEN SUM(a2.nd_cosTot)<0 THEN 0 ELSE ABS(SUM(a2.nd_cosTot)) END) AS nd_monDeb";
                    strSQL+=" , (CASE WHEN SUM(a2.nd_cosTot)<0 THEN ABS(SUM(a2.nd_cosTot)) ELSE 0 END) AS nd_monHab";
                    strSQL+=" , Null AS tx_ref, 'P' AS st_regRep";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" LEFT OUTER JOIN tbm_bod AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc;
                    strSQL+=" AND a1.co_doc=" + intCodDoc;
                    strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_bod, a3.co_ctaExi";
                    strSQL+=" UNION ALL";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, CASE WHEN a1.co_tipDoc=1 THEN 48  WHEN a1.co_tipDoc=228 THEN 235 END AS co_tipDoc";
                    strSQL+=" , a1.co_doc AS co_dia, SUM(a2.co_bod)+1 AS co_reg, a3.co_ctaCosVen AS co_cta";
                    strSQL+=" , (CASE WHEN SUM(a2.nd_cosTot)<0 THEN ABS(SUM(a2.nd_cosTot)) ELSE 0 END) AS nd_monDeb";
                    strSQL+=" , (CASE WHEN SUM(a2.nd_cosTot)<0 THEN 0 ELSE ABS(SUM(a2.nd_cosTot)) END) AS nd_monHab";
                    strSQL+=" , Null AS tx_ref, 'P' AS st_regRep";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" LEFT OUTER JOIN tbm_loc AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc;
                    strSQL+=" AND a1.co_doc=" + intCodDoc;
                    strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.co_ctaCosVen";
                    strSQL+=" ORDER BY co_emp, co_loc, co_tipDoc, co_dia, co_reg";
                    stm.executeUpdate(strSQL);
                }
                if (intCodTipDoc==229) //229=DEVVENE
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_cabDia (co_emp, co_loc, co_tipDoc, co_dia, tx_numDia, fe_dia, tx_glo, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod)";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, CASE WHEN a1.co_tipDoc=3 THEN 49  WHEN a1.co_tipDoc=229 THEN 236 END AS co_tipDoc";
                    strSQL+=" , a1.co_doc AS co_dia, a1.ne_numDoc AS tx_numDia";
                    strSQL+=" , a1.fe_doc AS fe_dia, Null AS tx_glo, a1.st_reg, a1.fe_ing, a1.fe_ultMod, a1.co_usrIng, a1.co_usrMod";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc;
                    strSQL+=" AND a1.co_doc=" + intCodDoc;
                    stm.executeUpdate(strSQL);
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detDia(co_emp,  co_loc, co_tipDoc, co_dia, co_reg, co_cta, nd_monDeb, nd_monHab, tx_ref, st_regRep)";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, CASE WHEN a1.co_tipDoc=3 THEN 49  WHEN a1.co_tipDoc=229 THEN 236 END AS co_tipDoc";
                    strSQL+=" , a1.co_doc AS co_dia, SUM(a2.co_bod)+1 AS co_reg, a3.co_ctaCosVen AS co_cta";
                    strSQL+=" , (CASE WHEN SUM(a2.nd_cosTot)<0 THEN ABS(SUM(a2.nd_cosTot)) ELSE 0 END) AS nd_monDeb";
                    strSQL+=" , (CASE WHEN SUM(a2.nd_cosTot)<0 THEN 0 ELSE ABS(SUM(a2.nd_cosTot)) END) AS nd_monHab";
                    strSQL+=" , Null AS tx_ref, 'P' AS st_regRep";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" LEFT OUTER JOIN tbm_loc AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc;
                    strSQL+=" AND a1.co_doc=" + intCodDoc;
                    strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.co_ctaCosVen";
                    strSQL+=" UNION ALL";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, CASE WHEN a1.co_tipDoc=3 THEN 49  WHEN a1.co_tipDoc=229 THEN 236 END AS co_tipDoc";
                    strSQL+=" , a1.co_doc AS co_dia, a2.co_bod AS co_reg, a3.co_ctaExi AS co_cta";
                    strSQL+=" , (CASE WHEN SUM(a2.nd_cosTot)<0 THEN 0 ELSE ABS(SUM(a2.nd_cosTot)) END) AS nd_monDeb";
                    strSQL+=" , (CASE WHEN SUM(a2.nd_cosTot)<0 THEN ABS(SUM(a2.nd_cosTot)) ELSE 0 END) AS nd_monHab";
                    strSQL+=" , Null AS tx_ref, 'P' AS st_regRep";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" LEFT OUTER JOIN tbm_bod AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc;
                    strSQL+=" AND a1.co_doc=" + intCodDoc;
                    strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_bod, a3.co_ctaExi";
                    strSQL+=" ORDER BY co_emp, co_loc, co_tipDoc, co_dia, co_reg";
                    stm.executeUpdate(strSQL);
                }
                /*
                //Paso 7: Actualizar los saldos de las cuentas.
                if (intCodTipDoc==228 || intCodTipDoc==229) //228=FACVENE, 229=DEVVENE
                {
                    //Actualizar las cuentas del asiento de diario.
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="UPDATE tbm_salCta";
                    strSQL+=" SET nd_salCta=nd_salCta+b1.nd_val";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a1.co_emp, a2.co_cta";
                    strSQL+=" , CAST(TO_CHAR(a1.fe_dia, 'yyyymm') AS INTEGER) AS co_per";
                    strSQL+=" , (a2.nd_monDeb-a2.nd_monHab) AS nd_val";
                    strSQL+=" FROM tbm_cabDia AS a1";
                    strSQL+=" INNER JOIN tbm_detDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc;
                    strSQL+=" AND a1.co_dia=" + intCodDoc;
                    strSQL+=" ) AS b1";
                    strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                    stm.executeUpdate(strSQL);
                    //Actualizar la cuenta de resultados.
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="UPDATE tbm_salCta";
                    strSQL+=" SET nd_salCta=c1.nd_salCta";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT b1.co_emp, b3.co_ctaRes AS co_cta, b4.co_per, SUM(b2.nd_salCta) AS nd_salCta";
                    strSQL+=" FROM tbm_plaCta AS b1";
                    strSQL+=" INNER JOIN tbm_salCta AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cta=b2.co_cta)";
                    strSQL+=" INNER JOIN tbm_emp AS b3 ON (b1.co_emp=b3.co_emp)";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" SELECT a1.co_emp, CAST(TO_CHAR(a1.fe_dia, 'yyyymm') AS INTEGER) AS co_per";
                    strSQL+=" FROM tbm_cabDia AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc;
                    strSQL+=" AND a1.co_dia=" + intCodDoc;
                    strSQL+=" ) AS b4 ON (b3.co_emp=b4.co_emp)";
                    strSQL+=" WHERE b1.co_emp=" + intCodEmp;
                    strSQL+=" AND b1.tx_niv1 IN ('4', '5', '6', '7', '8')";
                    strSQL+=" AND b2.co_per=b4.co_per AND b1.tx_tipCta='D'";
                    strSQL+=" GROUP BY b1.co_emp, b3.co_ctaRes, b4.co_per";
                    strSQL+=" ) AS c1";
                    strSQL+=" WHERE tbm_salCta.co_emp=c1.co_emp AND tbm_salCta.co_cta=c1.co_cta AND tbm_salCta.co_per=c1.co_per";
                    stm.executeUpdate(strSQL);
                    //Actualizar las cuentas de cabecera.
                    for (i=5; i>=1; i--)
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_salCta";
                        strSQL+=" SET nd_salCta=c1.nd_salCta";
                        strSQL+=" FROM (";
                        strSQL+=" SELECT b1.co_emp, b1.co_cta, b3.co_per, SUM(b2.nd_salCta) AS nd_salCta";
                        strSQL+=" FROM tbm_plaCta AS b1";
                        strSQL+=" INNER JOIN tbm_salCta AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cta=b2.co_cta)";
                        strSQL+=" INNER JOIN (";
                        strSQL+=" SELECT a1.co_emp, a2.co_cta, a3.tx_codCta, CAST(TO_CHAR(a1.fe_dia, 'yyyymm') AS INTEGER) AS co_per";
                        strSQL+=" FROM tbm_cabDia AS a1";
                        strSQL+=" INNER JOIN tbm_detDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)";
                        strSQL+=" INNER JOIN tbm_plaCta AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta)";
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        strSQL+=" AND a1.co_loc=" + intCodLoc;
                        strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc;
                        strSQL+=" AND a1.co_dia=" + intCodDoc;
                        strSQL+=" ) AS b3 ON (b2.co_emp=b3.co_emp)";
                        strSQL+=" WHERE b1.co_emp=" + intCodEmp;
                        strSQL+=" AND b1.tx_tipCta='C' AND b1.ne_niv=" + i + " AND b2.co_per=b3.co_per";
                        strSQL+=" AND SUBSTRING(b3.tx_codCta, 1, LENGTH(b1.tx_codCta))=b1.tx_codCta";
                        strSQL+=" GROUP BY b1.co_emp, b1.co_cta, b3.co_per";
                        strSQL+=" ) AS c1";
                        strSQL+=" WHERE tbm_salCta.co_emp=c1.co_emp AND tbm_salCta.co_cta=c1.co_cta AND tbm_salCta.co_per=c1.co_per";
                        stm.executeUpdate(strSQL);
                    }
                }
                */
                stm.close();
                stm=null;
                blnRes=costearDocumentoGrupo(padre, objParSis, con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función costea los items del documento especificado para el Grupo.
     * @param padre El formulario padre para el cuadro de mensaje. Puede ser cualquier objeto que
     * hereda de la clase <I>Component</I>. Por ejemplo: JFrame, JInternalFrame
     * @param objParSis El objeto ZafParSis.
     * @param con El objeto que contiene la conexión a la base de datos.
     * Esta función recibe la conexión porque está pensada para utilizar transaccionamiento.
     * Es decir, si no se completa toda la transacción habría que hacer un rollback.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intCodTipDoc El código del tipo de documento.
     * @param intCodDoc El código del documento.
     * @return true: Si se pudo costear el item.
     * <BR>false: En el caso contrario.
     */
    public boolean costearDocumentoGrupo(java.awt.Component padre, Librerias.ZafParSis.ZafParSis objParSis, java.sql.Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc)
    {
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
        java.util.ArrayList arlDat=new java.util.ArrayList();
        java.util.ArrayList arlReg;
        String strCosUniCal;
        int i, intCodItm, intNumDec, intCodItmGrp;
        BigDecimal bgdCan, bgdCosUni, bgdCosTot, bgdSalUni, bgdSalVal, bgdCosPro;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intNumDec=objParSis.getDecimalesBaseDatos();
                //Paso 1: Obtener los items a costear.
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_reg, a2.st_cosUniCal, a1.co_itm, a1.nd_can, a1.nd_cosUniGrp AS nd_cosUni, a1.nd_cosTotGrp AS nd_cosTot";
                strSQL+=" FROM tbm_detMovInv AS a1";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc;
                strSQL+=" AND a1.co_doc=" + intCodDoc;
                rst=stm.executeQuery(strSQL);
                //Limpiar arreglo de datos.
                arlDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    arlReg=new java.util.ArrayList();
                    arlReg.add(0,rst.getString("co_reg"));
                    arlReg.add(1,rst.getString("st_cosUniCal"));
                    arlReg.add(2,rst.getString("co_itm"));
                    arlReg.add(3,rst.getString("nd_can"));
                    arlReg.add(4,rst.getString("nd_cosUni"));
                    arlReg.add(5,rst.getString("nd_cosTot"));
                    arlDat.add(arlReg);
                }
                rst.close();
                rst=null;
                //Paso 2: Costear los items.
                for (i=0;i<arlDat.size();i++)
                {
                    strCosUniCal=this.getStringValueAt(arlDat, i, 1);
                    intCodItm=this.getIntValueAt(arlDat, i, 2);
                    bgdCan=this.getBigDecimalValueAt(arlDat, i, 3);
                    bgdCosUni=this.getBigDecimalValueAt(arlDat, i, 4);
                    bgdCosTot=this.getBigDecimalValueAt(arlDat, i, 5);
                    //Paso 3: Obtener los saldos iniciales.
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT b2.co_itm AS co_itmGrp, b1.nd_stkAct, b2.nd_valExi, b2.nd_cosUni";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a2.co_itmMae, SUM(a1.nd_stkAct) AS nd_stkAct";
                    strSQL+=" FROM tbm_inv AS a1";
                    strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                    strSQL+=" WHERE a2.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + intCodItm + ")";
                    strSQL+=" GROUP BY a2.co_itmMae";
                    strSQL+=" ) AS b1 INNER JOIN (";
                    strSQL+=" SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.nd_valExi, a1.nd_cosUni";
                    strSQL+=" FROM tbm_inv AS a1";
                    strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                    strSQL+=" WHERE a2.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + intCodItm + ") AND a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                    strSQL+=" ) AS b2 ON (b1.co_itmMae=b2.co_itmMae)";
                    rst=stm.executeQuery(strSQL);
                    intCodItmGrp=0;
                    bgdSalUni=BigDecimal.ZERO;
                    bgdSalVal=BigDecimal.ZERO;
                    bgdCosPro=BigDecimal.ZERO;
                    if (rst.next())
                    {
                        intCodItmGrp=rst.getInt("co_itmGrp");
                        bgdSalUni=(bgdSalUni=rst.getBigDecimal("nd_stkAct"))==null?BigDecimal.ZERO:bgdSalUni;
                        bgdSalVal=(bgdSalVal=rst.getBigDecimal("nd_valExi"))==null?BigDecimal.ZERO:bgdSalVal;
                        bgdCosPro=(bgdCosPro=rst.getBigDecimal("nd_cosUni"))==null?BigDecimal.ZERO:bgdCosPro;
                    }
                    rst.close();
                    rst=null;
                    if (strCosUniCal.equals("S"))
                    {
                        //Calcular el costo de acuerdo al tipo de documento.
                        switch (intCodTipDoc)
                        {
                            case 3: //DEVVEN: Devoluciones de ventas.
                            case 229: //DEVVENE: Devolución de ventas (Electrónica)
                                //Armar la sentencia SQL.
                                //Para las devoluciones de ventas el Sistema obtiene el costo unitario de la factura asociada a la devolución.
                                //En el proceso de unificación de items el campo "co_itm" de la factura se queda con el código viejo y el campo
                                //"co_itmAct" con el código actual. Al tener la devolución el código actual no encontraría dicho código en la factura.
                                //Es por eso que para éste caso se utiliza "co_itmAct" en lugar de "co_itm".
                                //mientras que la DEVVEN queda con el nuevo código. Por eso para la FACVEN se utiliza "co_itmAct".
                                strSQL="";
                                strSQL+="SELECT a3.nd_cosUniGrp AS nd_cosUni";
                                strSQL+=" FROM tbm_detMovInv AS a1";
                                strSQL+=" INNER JOIN tbr_cabMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_detMovInv AS a3 ON (a2.co_empRel=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_tipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc)";
                                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                                strSQL+=" AND a1.co_loc=" + intCodLoc;
                                strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc;
                                strSQL+=" AND a1.co_doc=" + intCodDoc;
                                strSQL+=" AND a1.co_itm=" + intCodItm;
                                strSQL+=" AND a1.co_itmAct=a3.co_itmAct";
                                strSQL+=" ORDER BY a3.co_reg";
                                rst=stm.executeQuery(strSQL);
                                if (rst.next())
                                {
                                    bgdCosUni=(bgdCosUni=rst.getBigDecimal("nd_cosUni"))==null?BigDecimal.ZERO:bgdCosUni;
                                    bgdCosTot=this.redondearBigDecimal(bgdCan.multiply(bgdCosUni), intNumDec);
                                }
                                rst.close();
                                rst=null;
                                break;
                            case 46: //TRAINV: Transferencias de inventario.
                            case 53: //TRINAJ: Transferencias de inventario.
                            case 58: //TRINDI: Transferencias de inventario.
                            case 60: //TRINNO: Transferencias de inventario.
                            case 66: //TRINCA: Transferencias de inventario.
                            case 85: //TRINMA: Transferencias de inventario.
                            case 96: //TRIADI: Transferencias de inventario.
                            case 97: //TRIACQ: Transferencias de inventario.
                            case 98: //TRIACM: Transferencias de inventario.
                            case 150: //TRINEA: Transferencias de inventario.
                            case 151: //TRINIA: Transferencias de inventario.
                            case 153: //TRINAU: Transferencias de inventario.
                            case 171: //TRIACV: Transferencias de inventario automáticas (Cotizaciones de venta).
                            case 172: //TRINRB: Transferencias de inventario (Reposición desde otras bodegas).
                            case 204: //TRINIM: Transferencias de inventario (Importación).
                            case 234: //TRINCL: Transferencias de inventario (Compras locales).
                                bgdCosUni=bgdCosPro;
                                bgdCosTot=this.redondearBigDecimal(bgdCan.multiply(bgdCosUni), intNumDec);
                                break;
                            default:
                                bgdCosUni=bgdCosPro;
                                bgdCosTot=this.redondearBigDecimal(bgdCan.multiply(bgdCosUni), intNumDec);
                                break;
                        }
                    }
                    //Calcular saldos.
                    //bgdSalUni=bgdSalUni.add(bgdCan);
                    bgdSalVal=bgdSalVal.add(bgdCosTot);
                    bgdCosPro=(bgdSalUni.compareTo(BigDecimal.ZERO)==0?BigDecimal.ZERO:bgdSalVal.divide(bgdSalUni, intNumDec, BigDecimal.ROUND_HALF_UP));
                    //Paso 5: Actualizar la tabla "tbm_detMovInv".
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="UPDATE tbm_detMovInv";
                    if (strCosUniCal.equals("S"))
                    {
                        strSQL+=" SET nd_cosUniGrp=" + bgdCosUni;
                        strSQL+=", nd_cosTotGrp=" + bgdCosTot;
                        strSQL+=", nd_exiGrp=" + bgdSalUni;
                    }
                    else
                    {
                        strSQL+=" SET nd_exiGrp=" + bgdSalUni;
                    }
                    strSQL+=", nd_valExiGrp=" + bgdSalVal;
                    strSQL+=", nd_cosProGrp=" + bgdCosPro;
                    strSQL+=" WHERE co_emp=" + intCodEmp;
                    strSQL+=" AND co_loc=" + intCodLoc;
                    strSQL+=" AND co_tipDoc=" + intCodTipDoc;
                    strSQL+=" AND co_doc=" + intCodDoc;
                    strSQL+=" AND co_reg=" + this.getIntValueAt(arlDat, i, 0);
                    stm.executeUpdate(strSQL);
                    switch (intCodTipDoc)
                    {
                        case 46: //TRAINV: Transferencias de inventario.
                        case 53: //TRINAJ: Transferencias de inventario.
                        case 58: //TRINDI: Transferencias de inventario.
                        case 60: //TRINNO: Transferencias de inventario.
                        case 66: //TRINCA: Transferencias de inventario.
                        case 85: //TRINMA: Transferencias de inventario.
                        case 96: //TRIADI: Transferencias de inventario.
                        case 97: //TRIACQ: Transferencias de inventario.
                        case 98: //TRIACM: Transferencias de inventario.
                        case 150: //TRINEA: Transferencias de inventario.
                        case 151: //TRINIA: Transferencias de inventario.
                        case 153: //TRINAU: Transferencias de inventario.
                        case 171: //TRIACV: Transferencias de inventario automáticas (Cotizaciones de venta).
                        case 172: //TRINRB: Transferencias de inventario (Reposición desde otras bodegas).
                        case 204: //TRINIM: Transferencias de inventario (Importación).
                        case 234: //TRINCL: Transferencias de inventario (Compras locales).
                            //No actualizar "tbm_inv" porque una transferencia es un egreso y un ingreso del mismo item. Es decir, no se ven afectados los saldos.
                            break;
                        default:
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="UPDATE tbm_inv";
                            strSQL+=" SET nd_valExi=" + bgdSalVal;
                            strSQL+=", nd_cosUni=" + bgdCosPro;
                            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresaGrupo();
                            strSQL+=" AND co_itm=" + intCodItmGrp;
                            stm.executeUpdate(strSQL);
                            break;
                    }
                }
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            this.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }
    
}

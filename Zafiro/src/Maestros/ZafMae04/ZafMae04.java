/*
 * ZafMae04.java   
 * Maestro de Usuarios
 * Created on 14 de marzo de 2018, 9:41
 */
package Maestros.ZafMae04;
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
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Rosa Zambrano
 */
public class ZafMae04 extends javax.swing.JInternalFrame 
{
    //Constantes: Tab Empresas por usuario
    static final int INT_TBL_EMP_USR_LIN=0;
    static final int INT_TBL_EMP_USR_CHKSEL=1;
    static final int INT_TBL_EMP_USR_CODEMP=2;
    static final int INT_TBL_EMP_USR_NOMEMP=3;
    static final int INT_TBL_EMP_USR_CHKVEN=4;    
    static final int INT_TBL_EMP_USR_CHKCOM=5;
    //Constantes: Tab Locales por Usuario
    static final int INT_TBL_LOC_USR_LIN=0;
    static final int INT_TBL_LOC_USR_CHKSEL=1;
    static final int INT_TBL_LOC_USR_CODEMP=2;
    static final int INT_TBL_LOC_USR_CODLOC=3;
    static final int INT_TBL_LOC_USR_NOMLOC=4;
    static final int INT_TBL_LOC_USR_CHKPRE=5;    
    //Constantes: Tab Bodegas por Usuario
    static final int INT_TBL_BOD_USR_LIN=0;
    static final int INT_TBL_BOD_USR_CHKSEL=1;
    static final int INT_TBL_BOD_USR_CODEMP=2;
    static final int INT_TBL_BOD_USR_CODBOD=3;
    static final int INT_TBL_BOD_USR_NOMBOD=4;
    static final int INT_TBL_BOD_USR_CHKPRE=5;        
    
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
    private java.util.Date datFecAux;                //Auxiliar: Para almacenar fechas.
    
    private Vector vecDat, vecCab, vecReg;
    private Vector vecEstCiv;                        //Vector para almacenar el codigo del estado en uso
    private boolean blnHayCam;                       //Determina si hay cambios en el formulario.
    
    private StringBuffer stbSQL;                     //Variable StringBuffer para ejecutar sentencias SQL.
    private String strSQL, strAux;                   //Variable auxiliar de tipo string la cual servira para guardar aquellas cadenas en las cuales me esten enviando algun caracter invalido
    private String strCodUsr, strUsr, strCedUsr;
    private String strCodGrpUsr, strGrpUsr;
    private String strVer=" v0.1.1 ";

    /** Crea una nueva instancia de la clase ZafMae04. */
    public ZafMae04(ZafParSis obj)
    {
        try
        {
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            initComponents();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
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
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objDocLis=new ZafDocLis();
            objTooBar=new MiToolBar(this);
            panBar.add(objTooBar);
            
            //Configurar los Tab.
            configurarTabGen();
            configurarTabEmpUsr();
            configurarTabLocUsr();
            configurarTabBodUsr();
            
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
     * Esta función configura Tab "General" 
     */    
    private boolean configurarTabGen()
    {
        boolean blnRes=true;
        try
        {
            //Configurar Campos
            txtCed.setBackground(objParSis.getColorCamposObligatorios());
            txtNom.setBackground(objParSis.getColorCamposObligatorios());
            txtUsr.setBackground(objParSis.getColorCamposObligatorios());
            txtPwd.setBackground(objParSis.getColorCamposObligatorios());
            txtCodGrpUsr.setBackground(objParSis.getColorCamposObligatorios());
            txtGrpUsr.setBackground(objParSis.getColorCamposObligatorios());
            
            txtDir.setBackground(objParSis.getColorCamposSistema());
            txtTel.setBackground(objParSis.getColorCamposSistema());
            txtCorEle.setBackground(objParSis.getColorCamposSistema());
            
            //Configurar las ZafVenCon.
            configurarVenConUsr();
            configurarVenConTra();
            configurarVenConGrpUsr();
            configurarComboEstCiv();
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
    /**
     * Esta función configura Tab "Empresas por Usuario" 
     */    
    private boolean configurarTabEmpUsr()
    {
        boolean blnRes=true;
        try
        {
            configurarTblDatEmpUsr();
            cargarTblDatEmpUsr();
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }     

     /**
     * Esta función configura el JTable "tblDatEmpUsr".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatEmpUsr()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_EMP_USR_LIN, "");
            vecCab.add(INT_TBL_EMP_USR_CHKSEL, "...");
            vecCab.add(INT_TBL_EMP_USR_CODEMP, "Cod.Emp");
            vecCab.add(INT_TBL_EMP_USR_NOMEMP, "Empresa");
            vecCab.add(INT_TBL_EMP_USR_CHKVEN, "Vendedor");
            vecCab.add(INT_TBL_EMP_USR_CHKCOM, "Comprador");

            objTblModEmpUsr = new ZafTblMod();
            objTblModEmpUsr.setHeader(vecCab);
            tblDatEmpUsr.setModel(objTblModEmpUsr);

            //Configurar JTable: Establecer tipo de selección.
            tblDatEmpUsr.setRowSelectionAllowed(true);
            tblDatEmpUsr.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatEmpUsr);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatEmpUsr.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDatEmpUsr.getColumnModel();
            tcmAux.getColumn(INT_TBL_EMP_USR_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_EMP_USR_CHKSEL).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_EMP_USR_CODEMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_EMP_USR_NOMEMP).setPreferredWidth(250);
            tcmAux.getColumn(INT_TBL_EMP_USR_CHKVEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_EMP_USR_CHKVEN).setPreferredWidth(80);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_EMP_USR_CHKSEL).setResizable(false);

            //new Librerias.ZafColNumerada.ZafColNumerada(tblDatEmpUsr, INT_TBL_EMP_USR_LIN);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatEmpUsr.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDatEmpUsr);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            //objMouMotAdaEmpUsr =new ZafMouMotAda();
            //tblDatEmpUsr.getTableHeader().addMouseMotionListener(objMouMotAdaEmpUsr);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatEmpUsr);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatEmpUsr);
            tcmAux.getColumn(INT_TBL_EMP_USR_LIN).setCellRenderer(objTblFilCab);
   
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatEmpUsr);
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_EMP_USR_CHKSEL);
            vecAux.add("" + INT_TBL_EMP_USR_CHKVEN);
            vecAux.add("" + INT_TBL_EMP_USR_CHKCOM);
            objTblModEmpUsr.setColumnasEditables(vecAux);
            vecAux = null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDatEmpUsr);
            tcmAux.getColumn(INT_TBL_EMP_USR_CHKSEL).setCellRenderer(objTblFilCab);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_EMP_USR_CHKSEL).setCellRenderer(objTblCelRenChk);                    
            tcmAux.getColumn(INT_TBL_EMP_USR_CHKVEN).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_EMP_USR_CHKCOM).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_EMP_USR_CHKSEL).setCellEditor(objTblCelEdiChk);                    
            tcmAux.getColumn(INT_TBL_EMP_USR_CHKVEN).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_EMP_USR_CHKCOM).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk = null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_EMP_USR_CODEMP).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblModEmpUsr.setModoOperacion(objTblModEmpUsr.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            
        }
        catch(Exception e)  {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }
    
    /**
     * Función que carga el listado de empresas para asignar a un usuario.
     */
    private void cargarTblDatEmpUsr() 
    {
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm=con.createStatement();
                //Arma la sentencia.
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.tx_nom as tx_nomEmp ";
                strSQL+=" FROM tbm_emp as a ";
                strSQL+=" WHERE a.co_emp <> 3 ";
                //strSQL+=" AND a.st_reg != 'I' ";         //Muestra todos las empresas, excepto las inactivas. 
                strSQL+=" ORDER BY a.co_emp ";
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                while(rst.next())
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_EMP_USR_LIN,"");
                    vecReg.add(INT_TBL_EMP_USR_CHKSEL, false );
                    vecReg.add(INT_TBL_EMP_USR_CODEMP, rst.getString("co_emp") );
                    vecReg.add(INT_TBL_EMP_USR_NOMEMP, rst.getString("tx_nomEmp") );
                    vecReg.add(INT_TBL_EMP_USR_CHKVEN, false);
                    vecReg.add(INT_TBL_EMP_USR_CHKCOM, false);
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModEmpUsr.setData(vecDat);
                tblDatEmpUsr.setModel(objTblModEmpUsr);
            }
        } 
        catch (SQLException Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  } 
        catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);   } 
    }      
    
    /**
     * Esta función configura Tab "Locales por Usuario" 
     */    
    private boolean configurarTabLocUsr()
    {
        boolean blnRes=true;
        try
        {
            configurarTblDatLocUsr();
            cargarTblDatLocUsr();
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }     

     /**
     * Esta función configura el JTable "tblDatLocUsr".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatLocUsr()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LOC_USR_LIN, "");
            vecCab.add(INT_TBL_LOC_USR_CHKSEL, "...");
            vecCab.add(INT_TBL_LOC_USR_CODEMP, "Cod.Emp");
            vecCab.add(INT_TBL_LOC_USR_CODLOC, "Cod.Loc");
            vecCab.add(INT_TBL_LOC_USR_NOMLOC, "Local");
            vecCab.add(INT_TBL_LOC_USR_CHKPRE, "Pred.");
            
            objTblModLocUsr = new ZafTblMod();
            objTblModLocUsr.setHeader(vecCab);
            tblDatLocUsr.setModel(objTblModLocUsr);

            //Configurar JTable: Establecer tipo de selección.
            tblDatLocUsr.setRowSelectionAllowed(true);
            tblDatLocUsr.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatLocUsr);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatLocUsr.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDatLocUsr.getColumnModel();
            tcmAux.getColumn(INT_TBL_LOC_USR_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_LOC_USR_CHKSEL).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_LOC_USR_CODEMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_USR_CODLOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_USR_NOMLOC).setPreferredWidth(250);
            tcmAux.getColumn(INT_TBL_LOC_USR_CHKPRE).setPreferredWidth(50);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_LOC_USR_CHKSEL).setResizable(false);

            //new Librerias.ZafColNumerada.ZafColNumerada(tblDatLocUsr, INT_TBL_LOC_USR_LIN);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatLocUsr.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDatLocUsr);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            //objMouMotAdaLoc =new ZafVen34.ZafMouMotAda();
            //tblDatLocUsr.getTableHeader().addMouseMotionListener(objMouMotAdaLoc);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatLocUsr);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatLocUsr);
            tcmAux.getColumn(INT_TBL_LOC_USR_LIN).setCellRenderer(objTblFilCab);
   
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatLocUsr);
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_LOC_USR_CHKSEL);
            vecAux.add("" + INT_TBL_LOC_USR_CHKPRE);
            objTblModLocUsr.setColumnasEditables(vecAux);
            vecAux = null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDatLocUsr);
            tcmAux.getColumn(INT_TBL_LOC_USR_CHKSEL).setCellRenderer(objTblFilCab);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_LOC_USR_CHKSEL).setCellRenderer(objTblCelRenChk);                    
            tcmAux.getColumn(INT_TBL_LOC_USR_CHKPRE).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_LOC_USR_CHKSEL).setCellEditor(objTblCelEdiChk);                    
            tcmAux.getColumn(INT_TBL_LOC_USR_CHKPRE).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk = null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_LOC_USR_CODEMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_LOC_USR_CODLOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblModLocUsr.setModoOperacion(objTblModLocUsr.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            
        }
        catch(Exception e)  {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }
    
    /**
     * Función que carga el listado de locales para asignar a un usuario.
     */
    private void cargarTblDatLocUsr() 
    {
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm=con.createStatement();
                //Arma la sentencia.
                strSQL ="";
                strSQL+=" SELECT a.co_emp, b.co_loc, b.tx_nom as tx_nomLoc ";
                strSQL+=" FROM tbm_emp as a ";
                strSQL+=" INNER JOIN tbm_loc as b  ON (b.co_emp=a.co_emp) ";
                strSQL+=" WHERE a.co_emp <> 3 ";
                //strSQL+=" AND a.st_reg != 'I' ";         //Muestra todos los locales, excepto los inactivos. 
                strSQL+=" ORDER BY a.co_emp, b.co_loc ";
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                while(rst.next())
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_LOC_USR_LIN,"");
                    vecReg.add(INT_TBL_LOC_USR_CHKSEL, false );
                    vecReg.add(INT_TBL_LOC_USR_CODEMP, rst.getString("co_emp") );
                    vecReg.add(INT_TBL_LOC_USR_CODLOC, rst.getString("co_loc") );
                    vecReg.add(INT_TBL_LOC_USR_NOMLOC, rst.getString("tx_nomLoc") );
                    vecReg.add(INT_TBL_LOC_USR_CHKPRE, false);
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModLocUsr.setData(vecDat);
                tblDatLocUsr.setModel(objTblModLocUsr);
            }
        } 
        catch (SQLException Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  } 
        catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);   } 
    }

    /**
     * Esta función configura Tab "Bodegas por Usuario" 
     */    
    private boolean configurarTabBodUsr()
    {
        boolean blnRes=true;
        try
        {
            configurarTblDatBodUsr();
            cargarTblDatBodUsr();
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }     

     /**
     * Esta función configura el JTable "tblDatBodUsr".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatBodUsr()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_BOD_USR_LIN, "");
            vecCab.add(INT_TBL_BOD_USR_CHKSEL, "...");
            vecCab.add(INT_TBL_BOD_USR_CODEMP, "Cod.Emp");
            vecCab.add(INT_TBL_BOD_USR_CODBOD, "Cod.Bod");
            vecCab.add(INT_TBL_BOD_USR_NOMBOD, "Bodega");
            vecCab.add(INT_TBL_BOD_USR_CHKPRE, "Pred.");
            
            objTblModBodUsr = new ZafTblMod();
            objTblModBodUsr.setHeader(vecCab);
            tblDatBodUsr.setModel(objTblModBodUsr);

            //Configurar JTable: Establecer tipo de selección.
            tblDatBodUsr.setRowSelectionAllowed(true);
            tblDatBodUsr.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatBodUsr);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatBodUsr.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDatBodUsr.getColumnModel();
            tcmAux.getColumn(INT_TBL_BOD_USR_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_BOD_USR_CHKSEL).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_BOD_USR_CODEMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_USR_CODBOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_USR_NOMBOD).setPreferredWidth(250);
            tcmAux.getColumn(INT_TBL_BOD_USR_CHKPRE).setPreferredWidth(50);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BOD_USR_CHKSEL).setResizable(false);

            //new Librerias.ZafColNumerada.ZafColNumerada(tblDatBodUsr, INT_TBL_BOD_USR_LIN);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatBodUsr.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDatBodUsr);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            //objMouMotAdaBod =new ZafMouMotAda();
            //tblDatBodUsr.getTableHeader().addMouseMotionListener(objMouMotAdaBod);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatBodUsr);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatBodUsr);
            tcmAux.getColumn(INT_TBL_BOD_USR_LIN).setCellRenderer(objTblFilCab);
   
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatBodUsr);
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_BOD_USR_CHKSEL);
            vecAux.add("" + INT_TBL_BOD_USR_CHKPRE);
            objTblModBodUsr.setColumnasEditables(vecAux);
            vecAux = null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDatBodUsr);
            tcmAux.getColumn(INT_TBL_BOD_USR_CHKSEL).setCellRenderer(objTblFilCab);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BOD_USR_CHKSEL).setCellRenderer(objTblCelRenChk);                    
            tcmAux.getColumn(INT_TBL_BOD_USR_CHKPRE).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_BOD_USR_CHKSEL).setCellEditor(objTblCelEdiChk);                    
            tcmAux.getColumn(INT_TBL_BOD_USR_CHKPRE).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk = null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_BOD_USR_CODEMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_BOD_USR_CODBOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblModBodUsr.setModoOperacion(objTblModBodUsr.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            
        }
        catch(Exception e)  {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }
    
    /**
     * Función que carga el listado de bodegas para asignar a un usuario.
     */
    private void cargarTblDatBodUsr() 
    {
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm=con.createStatement();
                //Arma la sentencia.
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_bod, a.tx_nom as tx_nomBod ";
                strSQL+=" FROM tbm_bod as a ";
                strSQL+=" WHERE a.co_emp = "+objParSis.getCodigoEmpresaGrupo();
                //strSQL+=" AND a.st_reg != 'I' ";         //Muestra todos las bodegas, excepto las inactivas. 
                strSQL+=" ORDER BY a.co_emp, a.co_bod ";
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                while(rst.next())
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_BOD_USR_LIN,"");
                    vecReg.add(INT_TBL_BOD_USR_CHKSEL, false );
                    vecReg.add(INT_TBL_BOD_USR_CODEMP, rst.getString("co_emp") );
                    vecReg.add(INT_TBL_BOD_USR_CODBOD, rst.getString("co_bod") );
                    vecReg.add(INT_TBL_BOD_USR_NOMBOD, rst.getString("tx_nomBod") );
                    vecReg.add(INT_TBL_BOD_USR_CHKPRE, false);
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModBodUsr.setData(vecDat);
                tblDatBodUsr.setModel(objTblModBodUsr);
            }
        } 
        catch (SQLException Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  } 
        catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);   } 
    }    
  
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Usuarios".
     */    
    private boolean configurarVenConUsr()
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
    private boolean mostrarVenConUsr(int intTipBus)  
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
                        txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtUsr.setText(vcoUsr.getValueAt(2));
                        txtNom.setText(vcoUsr.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Usuario".
                    if (vcoUsr.buscar("a1.co_usr", txtCodUsr.getText()))
                    {
                        txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtUsr.setText(vcoUsr.getValueAt(2));
                        txtNom.setText(vcoUsr.getValueAt(3));
                    }
                    else
                    {
                        vcoUsr.setCampoBusqueda(0);
                        vcoUsr.setCriterio1(11);
                        vcoUsr.cargarDatos();
                        vcoUsr.setVisible(true);
                        if (vcoUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodUsr.setText(vcoUsr.getValueAt(1));
                            txtUsr.setText(vcoUsr.getValueAt(2));
                            txtNom.setText(vcoUsr.getValueAt(3));
                        }
                        else
                        {
                            txtCodUsr.setText(strCodUsr);
                        }
                    }
                    break;
               case 2: //Búsqueda directa por "Usuario".
                    if (vcoUsr.buscar("a1.tx_usr", txtUsr.getText()))
                    {
                        txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtUsr.setText(vcoUsr.getValueAt(2));
                        txtNom.setText(vcoUsr.getValueAt(3));
                    }
                    else
                    {
                        vcoUsr.setCampoBusqueda(1);
                        vcoUsr.setCriterio1(11);
                        vcoUsr.cargarDatos();
                        vcoUsr.setVisible(true);
                        if (vcoUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodUsr.setText(vcoUsr.getValueAt(1));
                            txtUsr.setText(vcoUsr.getValueAt(2));
                            txtNom.setText(vcoUsr.getValueAt(3));
                        }
                        else
                        {
                            txtUsr.setText(strUsr);
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
    
    private boolean mostrarVenConGrpUsr(int intTipBus)  
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoGrpUsr.setCampoBusqueda(0);
                    vcoGrpUsr.setVisible(true);
                   if (vcoGrpUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodGrpUsr.setText(vcoGrpUsr.getValueAt(1));
                        txtGrpUsr.setText(vcoGrpUsr.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Grupo Usuario".
                    if (vcoGrpUsr.buscar("a1.co_grp", txtCodGrpUsr.getText()))
                    {
                        txtCodGrpUsr.setText(vcoGrpUsr.getValueAt(1));
                        txtGrpUsr.setText(vcoGrpUsr.getValueAt(3));
                    }
                    else
                    {
                        vcoGrpUsr.setCampoBusqueda(1);
                        vcoGrpUsr.setCriterio1(11);
                        vcoGrpUsr.cargarDatos();
                        vcoGrpUsr.setVisible(true);
                        if (vcoGrpUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodGrpUsr.setText(vcoGrpUsr.getValueAt(1));
                            txtGrpUsr.setText(vcoGrpUsr.getValueAt(3));
                        }
                        else
                        {
                            txtCodGrpUsr.setText(strCodGrpUsr);
                        }
                    }
                    break; 
               case 2: //Búsqueda directa por "Grupo Usuario".
                    if (vcoGrpUsr.buscar("a1.tx_desLar", txtGrpUsr.getText()))
                    {
                        txtCodGrpUsr.setText(vcoGrpUsr.getValueAt(1));
                        txtGrpUsr.setText(vcoGrpUsr.getValueAt(3));
                    }
                    else
                    {
                        vcoGrpUsr.setCampoBusqueda(2);
                        vcoGrpUsr.setCriterio1(11);
                        vcoGrpUsr.cargarDatos();
                        vcoGrpUsr.setVisible(true);
                        if (vcoGrpUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodGrpUsr.setText(vcoGrpUsr.getValueAt(1));
                            txtGrpUsr.setText(vcoGrpUsr.getValueAt(3));
                        }
                        else
                        {
                            txtGrpUsr.setText(strGrpUsr);
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
     * mostrar los "Trabajadores".
     */    
    private boolean configurarVenConTra()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_usr");
            arlCam.add("a1.tx_usr");
            arlCam.add("a1.tx_nomUsr");
            arlCam.add("a1.st_reg");
            arlCam.add("a1.tx_ced");
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_dir");
            arlCam.add("a1.tx_tel");
            arlCam.add("a1.tx_corEle");
            arlCam.add("a1.tx_sex");
            arlCam.add("a1.co_estciv");
            arlCam.add("a1.tx_UsrExi");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Usr.");
            arlAli.add("Usuario");
            arlAli.add("Nombre");
            arlAli.add("Estado Usuario");
            arlAli.add("Cédula");
            arlAli.add("Cód.Tra.");
            arlAli.add("Dirección");
            arlAli.add("Telefono");
            arlAli.add("Cor.Ele.");
            arlAli.add("Sexo");
            arlAli.add("Cod.Est.Civ.");
            arlAli.add("Usr.Exi.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("250");
            arlAncCol.add("40");
            arlAncCol.add("60");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
                        
            //Ocultar columnas.
            int intColOcu[]=new int[6];
            intColOcu[0]=7;
            intColOcu[1]=8;
            intColOcu[2]=9;
            intColOcu[3]=10;
            intColOcu[4]=11;
            intColOcu[5]=12;
            
            //Armar la sentencia SQL.
            strSQL ="";
            if(objTooBar.getEstado()=='n'){
                //Cuando la opción es insertar se cargan todos los datos del trabajador
                strSQL+=" SELECT x.* ";
                strSQL+=" FROM (";
                strSQL+="    SELECT 0 as co_usr, a.co_tra, a.tx_ide as tx_ced, initcap(a.tx_nom ||' '||a.tx_ape) as tx_nomUsr, initcap(a.tx_dir) as tx_dir, a.tx_tel1 as tx_tel, a.tx_corele, a.tx_sex, a.co_estciv";
                strSQL+="         ,CASE WHEN a1.tx_Usr IS NULL THEN 'N' ELSE 'S' END tx_UsrExi, 'A' as st_Reg ";
                /* Primera letra del primer nombre + primer apellido */
                strSQL+="         ,(LOWER(SUBSTRING(trim(a.tx_nom), 1, 1)) ||''||LOWER(SUBSTRING(trim(a.tx_ape), 0 , position(' ' in trim(a.tx_ape)) ) ) )as tx_usr ";
                /* Primera letra del primer nombre + primer apellido + segundo apellido */
                strSQL+="         ,(LOWER(SUBSTRING(trim(a.tx_nom), 1, 1)) ||''||LOWER(replace(a.tx_ape, ' ','')) )as strNomUsr2 ";
                 /* Primera letra del primer nombre + segundo nombre + primer apellido + segundo apellido */
                strSQL+="         ,(LOWER(SUBSTRING(trim(a.tx_nom), 1, 1)) ||''||LOWER(SUBSTRING(trim(a.tx_nom), position(' ' in trim(a.tx_nom))+1, position(' ' in trim(a.tx_nom))+1)) ||''|| LOWER(replace(a.tx_ape, ' ','')) )as strNomUsr3";
                strSQL+="    FROM tbm_tra AS a "; 
                strSQL+="    LEFT OUTER JOIN tbm_usr AS a1 ON a.tx_ide=a1.tx_ced ";
                strSQL+="    WHERE a.st_reg LIKE 'A' "; 
                strSQL+=" ) AS x ";
                strSQL+=" ORDER BY x.tx_nomUsr ";
            }
            else {
                strSQL+=" SELECT a.co_usr, 0 as co_tra, a.tx_ced, a.tx_nom as tx_nomUsr, a.tx_dir, a.tx_tel, a.tx_corele, a.tx_sex, a.co_estciv, 'S' AS tx_UsrExi, a.st_Reg, a.tx_usr";
                strSQL+=" FROM tbm_usr as a ";
                strSQL+=" ORDER BY a.tx_nom "; 
            } 
            
            if(objTooBar.getEstado()=='n'){
                vcoTra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Usuarios", strSQL, arlCam, arlAli, arlAncCol);
            }
            else{
                vcoTra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Usuarios", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            }
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoTra.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
    
    private boolean mostrarVenConTra(int intTipBus)  
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    if(objTooBar.getEstado()=='n'){
                        vcoTra.setCampoBusqueda(5);  //co_tra
                    }
                    else{
                        vcoTra.setCampoBusqueda(1);  //tx_ced
                    }
                    
                    vcoTra.setVisible(true);
                   if (vcoTra.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodUsr.setText(vcoTra.getValueAt(1));     //co_usr
                        txtUsr.setText(vcoTra.getValueAt(2));        //tx_usr
                        txtNom.setText(vcoTra.getValueAt(3));        //tx_nomUsr
                        if(objTooBar.getEstado()=='n'){
                            txtCed.setText(vcoTra.getValueAt(5));    //tx_ced
                            txtDir.setText(vcoTra.getValueAt(7));    //tx_dir
                            txtTel.setText(vcoTra.getValueAt(8));    //tx_tel
                            txtCorEle.setText(vcoTra.getValueAt(9)); //tx_corEle
                            txtPwd.setText(vcoTra.getValueAt(2));    //tx_pwd
                            chkEdiPwd.setSelected(true);
                            strAux=(vcoTra.getValueAt(10)==null?"N":vcoTra.getValueAt(10)); //tx_sex
                            cboSex.setSelectedIndex((strAux.equals("M")?1:(strAux.equals("F")?2:0)));
                            objUti.setItemCombo(cboEstCiv, vecEstCiv, vcoTra.getValueAt(11)); //co_estciv
                        }
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Usuario".
                    if (vcoTra.buscar("a1.co_usr", txtCodUsr.getText()))
                    {
                        txtCodUsr.setText(vcoTra.getValueAt(1));
                        txtUsr.setText(vcoTra.getValueAt(2));
                        txtNom.setText(vcoTra.getValueAt(3));
                        if(objTooBar.getEstado()=='n'){
                            txtCed.setText(vcoTra.getValueAt(5));    //tx_ced
                            txtDir.setText(vcoTra.getValueAt(7));    //tx_dir
                            txtTel.setText(vcoTra.getValueAt(8));    //tx_tel
                            txtCorEle.setText(vcoTra.getValueAt(9)); //tx_corEle
                            txtPwd.setText(vcoTra.getValueAt(2));    //tx_pwd
                            chkEdiPwd.setSelected(true);
                            strAux=(vcoTra.getValueAt(10)==null?"N":vcoTra.getValueAt(10)); //tx_sex
                            cboSex.setSelectedIndex((strAux.equals("M")?1:(strAux.equals("F")?2:0)));
                            objUti.setItemCombo(cboEstCiv, vecEstCiv, vcoTra.getValueAt(11)); //co_estciv
                        }
                    }
                    else
                    {
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.setVisible(true);
                        if (vcoTra.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodUsr.setText(vcoTra.getValueAt(1));
                            txtUsr.setText(vcoTra.getValueAt(2));
                            txtNom.setText(vcoTra.getValueAt(3));
                            if(objTooBar.getEstado()=='n'){
                                txtCed.setText(vcoTra.getValueAt(5));    //tx_ced
                                txtDir.setText(vcoTra.getValueAt(7));    //tx_dir
                                txtTel.setText(vcoTra.getValueAt(8));    //tx_tel
                                txtCorEle.setText(vcoTra.getValueAt(9)); //tx_corEle
                                txtPwd.setText(vcoTra.getValueAt(2));    //tx_pwd
                                chkEdiPwd.setSelected(true);
                                strAux=(vcoTra.getValueAt(10)==null?"N":vcoTra.getValueAt(10)); //tx_sex
                                cboSex.setSelectedIndex((strAux.equals("M")?1:(strAux.equals("F")?2:0)));
                                objUti.setItemCombo(cboEstCiv, vecEstCiv, vcoTra.getValueAt(11)); //co_estciv
                            }                            
                        }
                        else
                        {
                            txtCodUsr.setText(strCodUsr);
                        }
                    }
                    break;
               case 2: //Búsqueda directa por "Usuario".
                    if (vcoTra.buscar("a1.tx_usr", txtUsr.getText()))
                    {
                        txtCodUsr.setText(vcoTra.getValueAt(1));
                        txtUsr.setText(vcoTra.getValueAt(2));
                        txtNom.setText(vcoTra.getValueAt(3));
                        if(objTooBar.getEstado()=='n'){
                            txtCed.setText(vcoTra.getValueAt(5));    //tx_ced
                            txtDir.setText(vcoTra.getValueAt(7));    //tx_dir
                            txtTel.setText(vcoTra.getValueAt(8));    //tx_tel
                            txtCorEle.setText(vcoTra.getValueAt(9)); //tx_corEle
                            txtPwd.setText(vcoTra.getValueAt(2));    //tx_pwd
                            chkEdiPwd.setSelected(true);
                            strAux=(vcoTra.getValueAt(10)==null?"N":vcoTra.getValueAt(10)); //tx_sex
                            cboSex.setSelectedIndex((strAux.equals("M")?1:(strAux.equals("F")?2:0)));
                            objUti.setItemCombo(cboEstCiv, vecEstCiv, vcoTra.getValueAt(11)); //co_estciv
                        }                        
                    }
                    else
                    {
                        vcoTra.setCampoBusqueda(2);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.setVisible(true);
                        if (vcoTra.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodUsr.setText(vcoTra.getValueAt(1));
                            txtUsr.setText(vcoTra.getValueAt(2));
                            txtNom.setText(vcoTra.getValueAt(3));
                            if(objTooBar.getEstado()=='n'){
                                txtCed.setText(vcoTra.getValueAt(5));    //tx_ced
                                txtDir.setText(vcoTra.getValueAt(7));    //tx_dir
                                txtTel.setText(vcoTra.getValueAt(8));    //tx_tel
                                txtCorEle.setText(vcoTra.getValueAt(9)); //tx_corEle
                                txtPwd.setText(vcoTra.getValueAt(2));    //tx_pwd
                                chkEdiPwd.setSelected(true);
                                strAux=(vcoTra.getValueAt(10)==null?"N":vcoTra.getValueAt(10)); //tx_sex
                                cboSex.setSelectedIndex((strAux.equals("M")?1:(strAux.equals("F")?2:0)));
                                objUti.setItemCombo(cboEstCiv, vecEstCiv, vcoTra.getValueAt(11)); //co_estciv
                            }                            
                        }
                        else
                        {
                            txtUsr.setText(strUsr);
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
     * Función que configura el combo "Estado Civil"
     * Obtiene los diferentes estados civiles.
     * @return 
     */
    private boolean configurarComboEstCiv() {
        boolean blnRes = true;
        try 
        {
            //Inicializando el vector del Estado Civil
            vecEstCiv = new Vector();
            strSQL="SELECT co_reg, tx_desLar, co_grp FROM tbm_var WHERE co_grp =1 AND st_reg='A' ORDER BY co_reg";
            objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, cboEstCiv, vecEstCiv);
            cboEstCiv.setSelectedIndex(0);
        } 
        catch (Exception e) {   blnRes = false;    objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }          

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panGenDat = new javax.swing.JPanel();
        lblCodUsr = new javax.swing.JLabel();
        txtCodUsr = new javax.swing.JTextField();
        butCodUsr = new javax.swing.JButton();
        lblCed = new javax.swing.JLabel();
        txtCed = new javax.swing.JTextField();
        butCodTra = new javax.swing.JButton();
        lblNom = new javax.swing.JLabel();
        txtNom = new javax.swing.JTextField();
        lblDir = new javax.swing.JLabel();
        txtDir = new javax.swing.JTextField();
        lblTel = new javax.swing.JLabel();
        txtTel = new javax.swing.JTextField();
        lblCorEle = new javax.swing.JLabel();
        txtCorEle = new javax.swing.JTextField();
        lblGrpUsr = new javax.swing.JLabel();
        txtCodGrpUsr = new javax.swing.JTextField();
        txtGrpUsr = new javax.swing.JTextField();
        butGrpUsr = new javax.swing.JButton();
        lblUsr = new javax.swing.JLabel();
        txtUsr = new javax.swing.JTextField();
        lblPwd = new javax.swing.JLabel();
        txtPwd = new javax.swing.JPasswordField();
        chkEdiPwd = new javax.swing.JCheckBox();
        lblSex = new javax.swing.JLabel();
        cboSex = new javax.swing.JComboBox();
        lblEstCiv = new javax.swing.JLabel();
        cboEstCiv = new javax.swing.JComboBox();
        lblEstUsr = new javax.swing.JLabel();
        cboEstReg = new javax.swing.JComboBox();
        panGenObs = new javax.swing.JPanel();
        panGenLblObs = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTxtObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panEmpUsr = new javax.swing.JPanel();
        spnEmpUsr = new javax.swing.JScrollPane();
        tblDatEmpUsr = new javax.swing.JTable();
        panLocUsr = new javax.swing.JPanel();
        spnLocUsr = new javax.swing.JScrollPane();
        tblDatLocUsr = new javax.swing.JTable();
        panBodUsr = new javax.swing.JPanel();
        spnBodUsr = new javax.swing.JScrollPane();
        tblDatBodUsr = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setFont(new java.awt.Font("Bitstream Charter", 0, 10)); // NOI18N
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

        panGen.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panGen.add(lblTit, java.awt.BorderLayout.NORTH);

        panGenDat.setLayout(null);

        lblCodUsr.setText("Código:"); // NOI18N
        lblCodUsr.setToolTipText("Código del cliente/proveedor"); // NOI18N
        panGenDat.add(lblCodUsr);
        lblCodUsr.setBounds(20, 10, 120, 20);

        txtCodUsr.setEditable(false);
        txtCodUsr.setBackground(objParSis.getColorCamposSistema());
        txtCodUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodUsrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodUsrFocusLost(evt);
            }
        });
        txtCodUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodUsrActionPerformed(evt);
            }
        });
        panGenDat.add(txtCodUsr);
        txtCodUsr.setBounds(140, 10, 100, 20);

        butCodUsr.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCodUsr.setText("...");
        butCodUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCodUsrActionPerformed(evt);
            }
        });
        panGenDat.add(butCodUsr);
        butCodUsr.setBounds(240, 10, 24, 20);

        lblCed.setText("Cedula Identidad:");
        panGenDat.add(lblCed);
        lblCed.setBounds(20, 30, 110, 20);

        txtCed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCedFocusLost(evt);
            }
        });
        txtCed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedActionPerformed(evt);
            }
        });
        panGenDat.add(txtCed);
        txtCed.setBounds(140, 30, 130, 20);

        butCodTra.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCodTra.setText("...");
        butCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCodTraActionPerformed(evt);
            }
        });
        panGenDat.add(butCodTra);
        butCodTra.setBounds(270, 30, 24, 20);

        lblNom.setText("Nombres / Apellidos:");
        panGenDat.add(lblNom);
        lblNom.setBounds(20, 50, 116, 20);
        panGenDat.add(txtNom);
        txtNom.setBounds(140, 50, 350, 20);

        lblDir.setText("Dirección:");
        panGenDat.add(lblDir);
        lblDir.setBounds(20, 70, 110, 20);
        panGenDat.add(txtDir);
        txtDir.setBounds(140, 70, 350, 20);

        lblTel.setText("Teléfonos:");
        panGenDat.add(lblTel);
        lblTel.setBounds(20, 90, 110, 20);
        panGenDat.add(txtTel);
        txtTel.setBounds(140, 90, 350, 20);

        lblCorEle.setText("Correo Electrónico:");
        panGenDat.add(lblCorEle);
        lblCorEle.setBounds(20, 110, 120, 20);
        panGenDat.add(txtCorEle);
        txtCorEle.setBounds(140, 110, 350, 20);

        lblGrpUsr.setText("Grupo de Usuario:");
        panGenDat.add(lblGrpUsr);
        lblGrpUsr.setBounds(20, 130, 110, 20);

        txtCodGrpUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodGrpUsrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodGrpUsrFocusLost(evt);
            }
        });
        txtCodGrpUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodGrpUsrActionPerformed(evt);
            }
        });
        panGenDat.add(txtCodGrpUsr);
        txtCodGrpUsr.setBounds(140, 130, 38, 20);

        txtGrpUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtGrpUsrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGrpUsrFocusLost(evt);
            }
        });
        txtGrpUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGrpUsrActionPerformed(evt);
            }
        });
        panGenDat.add(txtGrpUsr);
        txtGrpUsr.setBounds(178, 130, 288, 20);

        butGrpUsr.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butGrpUsr.setText("...");
        butGrpUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGrpUsrActionPerformed(evt);
            }
        });
        panGenDat.add(butGrpUsr);
        butGrpUsr.setBounds(465, 130, 24, 20);

        lblUsr.setText("User:");
        panGenDat.add(lblUsr);
        lblUsr.setBounds(20, 150, 100, 20);

        txtUsr.setBackground(objParSis.getColorCamposSistema());
        txtUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsrFocusLost(evt);
            }
        });
        txtUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsrActionPerformed(evt);
            }
        });
        panGenDat.add(txtUsr);
        txtUsr.setBounds(140, 150, 210, 20);

        lblPwd.setText("Password:");
        panGenDat.add(lblPwd);
        lblPwd.setBounds(20, 170, 90, 20);
        panGenDat.add(txtPwd);
        txtPwd.setBounds(140, 170, 210, 20);

        chkEdiPwd.setToolTipText("Habilita modificar la constraseña.");
        chkEdiPwd.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkEdiPwdStateChanged(evt);
            }
        });
        panGenDat.add(chkEdiPwd);
        chkEdiPwd.setBounds(355, 170, 20, 20);

        lblSex.setText("Sexo:");
        panGenDat.add(lblSex);
        lblSex.setBounds(310, 10, 80, 20);

        cboSex.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Masculino", "Femenino" }));
        cboSex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSexActionPerformed(evt);
            }
        });
        panGenDat.add(cboSex);
        cboSex.setBounds(390, 10, 100, 20);

        lblEstCiv.setText("Estado Civil:");
        panGenDat.add(lblEstCiv);
        lblEstCiv.setBounds(310, 30, 80, 20);

        cboEstCiv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstCivActionPerformed(evt);
            }
        });
        panGenDat.add(cboEstCiv);
        cboEstCiv.setBounds(390, 30, 100, 20);

        lblEstUsr.setText("Estado usuario:");
        panGenDat.add(lblEstUsr);
        lblEstUsr.setBounds(20, 190, 100, 20);

        cboEstReg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Inactivo" }));
        cboEstReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstRegActionPerformed(evt);
            }
        });
        panGenDat.add(cboEstReg);
        cboEstReg.setBounds(140, 190, 120, 20);

        panGen.add(panGenDat, java.awt.BorderLayout.CENTER);

        panGenObs.setPreferredSize(new java.awt.Dimension(34, 70));
        panGenObs.setLayout(new java.awt.BorderLayout());

        panGenLblObs.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenLblObs.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs2);

        panGenObs.add(panGenLblObs, java.awt.BorderLayout.WEST);

        panGenTxtObs.setLayout(new java.awt.GridLayout(2, 1));

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panGenTxtObs.add(spnObs1);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panGenTxtObs.add(spnObs2);

        panGenObs.add(panGenTxtObs, java.awt.BorderLayout.CENTER);

        panGen.add(panGenObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panEmpUsr.setLayout(new java.awt.BorderLayout());

        tblDatEmpUsr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnEmpUsr.setViewportView(tblDatEmpUsr);

        panEmpUsr.add(spnEmpUsr, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Empresas por Usuario", panEmpUsr);

        panLocUsr.setLayout(new java.awt.BorderLayout());

        tblDatLocUsr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnLocUsr.setViewportView(tblDatLocUsr);

        panLocUsr.add(spnLocUsr, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Locales por Usuario", panLocUsr);

        panBodUsr.setLayout(new java.awt.BorderLayout());

        tblDatBodUsr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnBodUsr.setViewportView(tblDatBodUsr);

        panBodUsr.add(spnBodUsr, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Bodegas por Usuario", panBodUsr);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedActionPerformed
        txtCed.transferFocus();
    }//GEN-LAST:event_txtCedActionPerformed

    private void txtUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsrActionPerformed
        txtUsr.transferFocus();
    }//GEN-LAST:event_txtUsrActionPerformed

    private void txtGrpUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGrpUsrFocusLost
        if (txtGrpUsr.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtGrpUsr.getText().equalsIgnoreCase(strGrpUsr))
            {
                if (txtGrpUsr.getText().equals(""))
                {
                    txtCodGrpUsr.setText("");
                    txtGrpUsr.setText("");
                }
                else
                {
                    configurarVenConGrpUsr();
                    mostrarVenConGrpUsr(2);
                }
            }
            else
                txtGrpUsr.setText(strGrpUsr);
        }
    }//GEN-LAST:event_txtGrpUsrFocusLost

    private void txtUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrFocusLost
        if (txtUsr.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtUsr.getText().equalsIgnoreCase(strUsr))
            {
                if (!txtUsr.getText().equals(""))
                {
                    configurarVenConUsr();
                    mostrarVenConUsr(2);
                }
            }
            else
                txtUsr.setText(strUsr);
        }

        //Válida tamaño de user
        if (!txtUsr.getText().equals("")) {
            txtUsr.setText(strUsr); //Se quitan los espacios en blanco y se setea nuevamente el usuario.
            if (txtUsr.getText().length() > 20) {
                mostrarMsgInf("<HTML>El campo <<User>> ha sobrepasado el limite.<BR>Digite caracteres validos y vuelva a intentarlo.</HTML>");
                txtUsr.setText("");
                tabFrm.setSelectedIndex(0);
                txtUsr.requestFocus();
            }
        }  
    }//GEN-LAST:event_txtUsrFocusLost

    private void txtCodGrpUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpUsrFocusLost
        if (txtCodGrpUsr.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtCodGrpUsr.getText().equalsIgnoreCase(strCodGrpUsr))
            {
                if (txtCodGrpUsr.getText().equals(""))
                {
                    txtCodGrpUsr.setText("");
                    txtGrpUsr.setText("");
                }
                else
                {
                    configurarVenConGrpUsr();
                    mostrarVenConGrpUsr(1);
                }
            }
            else
                txtCodGrpUsr.setText(strCodGrpUsr);
        }       
    }//GEN-LAST:event_txtCodGrpUsrFocusLost

    private void butCodUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCodUsrActionPerformed
        configurarVenConUsr();
        mostrarVenConUsr(0);
    }//GEN-LAST:event_butCodUsrActionPerformed

    private void txtCodGrpUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodGrpUsrActionPerformed
        txtCodGrpUsr.transferFocus();
    }//GEN-LAST:event_txtCodGrpUsrActionPerformed

    private void txtGrpUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGrpUsrActionPerformed
        txtGrpUsr.transferFocus();
    }//GEN-LAST:event_txtGrpUsrActionPerformed

    private void butGrpUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGrpUsrActionPerformed
        configurarVenConGrpUsr();                       
        strCodGrpUsr=txtCodGrpUsr.getText();        
        mostrarVenConGrpUsr(0);
    }//GEN-LAST:event_butGrpUsrActionPerformed

    private void cboEstRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstRegActionPerformed
        blnHayCam = true;
    }//GEN-LAST:event_cboEstRegActionPerformed

    private void cboEstCivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstCivActionPerformed
        blnHayCam = true;
    }//GEN-LAST:event_cboEstCivActionPerformed
    
    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
        }
        catch (Exception e)
        {
            dispose();
        }     
    }//GEN-LAST:event_exitForm

    private void txtGrpUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGrpUsrFocusGained
        strGrpUsr=txtGrpUsr.getText();
        txtGrpUsr.selectAll();
    }//GEN-LAST:event_txtGrpUsrFocusGained

    private void txtCodGrpUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpUsrFocusGained
        strCodGrpUsr=txtCodGrpUsr.getText();
        txtCodGrpUsr.selectAll();
    }//GEN-LAST:event_txtCodGrpUsrFocusGained

    private void butCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCodTraActionPerformed
        configurarVenConTra();
        mostrarVenConTra(0);
    }//GEN-LAST:event_butCodTraActionPerformed

    private void txtCodUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodUsrActionPerformed
        txtCodUsr.transferFocus();
    }//GEN-LAST:event_txtCodUsrActionPerformed

    private void txtCodUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodUsrFocusGained
        strCodUsr=txtCodUsr.getText();
        txtCodUsr.selectAll();
    }//GEN-LAST:event_txtCodUsrFocusGained

    private void txtCodUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodUsrFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodUsr.getText().equalsIgnoreCase(strCodUsr))
        {
            if (txtCodUsr.getText().trim().equals("")) {
                limpiarFrm();
            }
            else {
                consultarReg(sqlFil());
            }
        }
        else
            txtCodUsr.setText(strCodUsr);
    }//GEN-LAST:event_txtCodUsrFocusLost

    private void txtCedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCedFocusGained
        strCedUsr=txtCed.getText();
        txtCed.selectAll();
    }//GEN-LAST:event_txtCedFocusGained

    private void txtCedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCedFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCed.getText().equalsIgnoreCase(strCedUsr))
        {
            if (!txtCed.getText().trim().equals("")) {
                txtCodUsr.setText("");
                consultarReg(sqlFil());
            }
        }
        else
            txtCed.setText(strCedUsr);
    }//GEN-LAST:event_txtCedFocusLost

    private void txtUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrFocusGained
        strUsr=txtUsr.getText().trim();
        txtUsr.selectAll();
    }//GEN-LAST:event_txtUsrFocusGained

    private void cboSexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSexActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSexActionPerformed

    private void chkEdiPwdStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkEdiPwdStateChanged
        if(chkEdiPwd.isSelected()){
            txtPwd.setEnabled(true);
        }
        else {
            txtPwd.setEnabled(false);
        }
    }//GEN-LAST:event_chkEdiPwdStateChanged

    private void exitForm() {
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCodTra;
    private javax.swing.JButton butCodUsr;
    private javax.swing.JButton butGrpUsr;
    private javax.swing.JComboBox cboEstCiv;
    private javax.swing.JComboBox cboEstReg;
    private javax.swing.JComboBox cboSex;
    private javax.swing.JCheckBox chkEdiPwd;
    private javax.swing.JLabel lblCed;
    private javax.swing.JLabel lblCodUsr;
    private javax.swing.JLabel lblCorEle;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblEstCiv;
    private javax.swing.JLabel lblEstUsr;
    private javax.swing.JLabel lblGrpUsr;
    private javax.swing.JLabel lblNom;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPwd;
    private javax.swing.JLabel lblSex;
    private javax.swing.JLabel lblTel;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblUsr;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBodUsr;
    private javax.swing.JPanel panEmpUsr;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDat;
    private javax.swing.JPanel panGenLblObs;
    private javax.swing.JPanel panGenObs;
    private javax.swing.JPanel panGenTxtObs;
    private javax.swing.JPanel panLocUsr;
    private javax.swing.JScrollPane spnBodUsr;
    private javax.swing.JScrollPane spnEmpUsr;
    private javax.swing.JScrollPane spnLocUsr;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatBodUsr;
    private javax.swing.JTable tblDatEmpUsr;
    private javax.swing.JTable tblDatLocUsr;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCed;
    private javax.swing.JTextField txtCodGrpUsr;
    private javax.swing.JTextField txtCodUsr;
    private javax.swing.JTextField txtCorEle;
    private javax.swing.JTextField txtDir;
    private javax.swing.JTextField txtGrpUsr;
    private javax.swing.JTextField txtNom;
    private javax.swing.JPasswordField txtPwd;
    private javax.swing.JTextField txtTel;
    private javax.swing.JTextField txtUsr;
    // End of variables declaration//GEN-END:variables

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
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
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
    private void agregarDocLis() 
    {
        txtCodUsr.getDocument().addDocumentListener(objDocLis);
        txtUsr.getDocument().addDocumentListener(objDocLis);
        txtPwd.getDocument().addDocumentListener(objDocLis);
        txtCed.getDocument().addDocumentListener(objDocLis);
        txtNom.getDocument().addDocumentListener(objDocLis);
        txtDir.getDocument().addDocumentListener(objDocLis);
        txtTel.getDocument().addDocumentListener(objDocLis);
        txtCorEle.getDocument().addDocumentListener(objDocLis);
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txtCodGrpUsr.getDocument().addDocumentListener(objDocLis);
        txtGrpUsr.getDocument().addDocumentListener(objDocLis);
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
            txtCodUsr.setText("");
            txtCed.setText("");
            txtNom.setText("");
            txtUsr.setText("");
            txtPwd.setText("");
            txtDir.setText("");
            txtTel.setText("");
            txtCorEle.setText("");
            txtCodGrpUsr.setText("");
            txtGrpUsr.setText("");
            cboSex.setSelectedIndex(0);
            cboEstCiv.setSelectedIndex(0);
            txaObs1.setText("");
            txaObs2.setText("");
            tabFrm.setSelectedIndex(0);        
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }       
    
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
                if(arlDatConUsr.size()>0){
                    if(intIndiceUsr>0){
                        if (blnHayCam || objTblModEmpUsr.isDataModelChanged() || objTblModLocUsr.isDataModelChanged() || objTblModBodUsr.isDataModelChanged()) {
                            if (isRegPro()) {
                                intIndiceUsr=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceUsr=0;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }             
        }
        
        public void clickAnterior() {
            try{
                if(arlDatConUsr.size()>0){
                    if(intIndiceUsr>0){
                        if (blnHayCam || objTblModEmpUsr.isDataModelChanged() || objTblModLocUsr.isDataModelChanged() || objTblModBodUsr.isDataModelChanged()){
                            if (isRegPro()) {
                                intIndiceUsr--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceUsr--;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }                        
        }

        public void clickSiguiente() {
            try{
                if(arlDatConUsr.size()>0){
                    if(intIndiceUsr < arlDatConUsr.size()-1){
                        if (blnHayCam || objTblModEmpUsr.isDataModelChanged() || objTblModLocUsr.isDataModelChanged() || objTblModBodUsr.isDataModelChanged()){
                            if (isRegPro()) {
                                intIndiceUsr++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceUsr++;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }              
        }     
        
        public void clickFin() {
            try{
                if(arlDatConUsr.size()>0){
                    if(intIndiceUsr<arlDatConUsr.size()-1){
                        if (blnHayCam || objTblModEmpUsr.isDataModelChanged() || objTblModLocUsr.isDataModelChanged() || objTblModBodUsr.isDataModelChanged()) {
                            if (isRegPro()) {
                                intIndiceUsr=arlDatConUsr.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceUsr=arlDatConUsr.size()-1;
                            cargarReg();
                        }
                    }
                }
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
                limpiarFrm();

                blnHayCam = false;
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickConsultar() {
            txtCodUsr.setEditable(true);
            txtCodUsr.setEnabled(true);
            txtUsr.setEnabled(true);
            blnHayCam=false;
        }

        public void clickModificar() {
            txtCodUsr.setEnabled(false);
            butCodUsr.setEnabled(false);
            txtCed.setEnabled(false);
            txtUsr.setEnabled(false);
            txtPwd.setEnabled(false);
            txtNom.requestFocus();
            blnHayCam=false;
        }
        public void clickEliminar() {
            //consultarReg(sqlFil());
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
            if (!isCamVal())
                return false;
                  
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeModificar() {
            if (!isCamVal())
                return false;
            
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
            consultarReg(sqlFil());
            return true;
        }          

        public boolean insertar() {
            if (!insertarReg())
                return false;
            //blnHayCam = false;
            return true;
        }

        public boolean modificar() {
            if (!actualizarReg())
                return false;
            //blnHayCam = false;
            return true;
        }
         
        public boolean anular()
        {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }        

        public boolean cancelar() {
            boolean blnRes=true;
            try
            {
                if (blnHayCam || objTblModEmpUsr.isDataModelChanged() || objTblModLocUsr.isDataModelChanged() || objTblModBodUsr.isDataModelChanged())
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
                String strAux = objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado")) {
                    mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }
                
                if (!eliminarReg())
                    return false;
                
                //Desplazarse al siguiente registro si es posible.
                if(intIndiceUsr<arlDatConUsr.size()-1){
                    intIndiceUsr++;
                    cargarReg();
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }                
                blnHayCam=false;
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
            this.setEstado('w');
            return true;
        }

        public boolean afterModificar() {
            blnHayCam = false;
            objTooBar.setEstado('w');
            objTooBar.afterConsultar();

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
    private boolean isCamVal()
    {
        try
        {
            if(objTooBar.getEstado()=='n'){
                //Validar el "Cédula de Identidad".
                if (txtCed.getText().trim().equals(""))
                {
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cedula de Identidad</FONT> es obligatorio.<BR>Escriba o seleccione una identificación y vuelva a intentarlo.</HTML>");
                    txtCed.requestFocus();
                    return false;
                } 
                //Valida "Cédula de Identidad" debe tener 10 digitos.
                if (txtCed.getText().trim().length() != 10)
                {  
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cedula de identidad</FONT> debe contener 10 digitos.<BR>Verifique y vuelva a intentarlo.</HTML>");
                    txtCed.requestFocus();
                    return false;
                } 
                //Valida "Cédula de Identidad" solo valores numéricos.
                if (objUti.isNumero(txtCed.getText().trim()) == false) {
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cedula de identidad</FONT> solo acepta valores numéricos.<BR>Digite caracteres válidos y vuelva a intentarlo.</HTML>");
                    txtCed.selectAll();
                    txtCed.requestFocus();
                    return false;
                }
                //Valida "Cédula de Identidad" si ya existe usuario creado con la misma cédula
                if (!validaExiIdeUsr()) {
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cedula de identidad</FONT> ya existe en el sistema.<BR>Verifique y vuelva a intentarlo.</HTML>");
                    txtCed.selectAll();
                    txtCed.requestFocus();
                    return false;
                }    
                //Valida "Usuario" si ya existe usuario creado con el mismo user
                if (!validaExiTxtUsr()) {
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">User</FONT> ya existe en el sistema.<BR>Verifique que no exista un usuario con el mismo user y vuelva a intentarlo.</HTML>");
                    txtCed.selectAll();
                    txtCed.requestFocus();
                    return false;
                }                   
            }
            
            //Validar "Nombre del usuario"
            if (txtNom.getText().trim().equals("")) {
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre</FONT> es obligatorio.<BR>Escriba un nombre y vuelva a intentarlo.</HTML>");
                txtNom.requestFocus();
                return false;
            }            
            
            //Validar el "Usuario".
            if (txtUsr.getText().trim().equals(""))
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">User</FONT> es obligatorio.<BR>Escriba un usuario y vuelva a intentarlo.</HTML>");
                txtUsr.requestFocus();
                return false;
            }
            
            //Validar el "PassWord".
            if (txtPwd.getText().trim().equals(""))
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"PassWord\">User</FONT> es obligatorio.<BR>Escriba un PassWord y vuelva a intentarlo.</HTML>");
                txtPwd.requestFocus();
                return false;
            }
            
            //Validar el "Código de Grupo de Usuario".
            if (txtCodGrpUsr.getText().trim().equals(""))
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Grupo de Usuario</FONT> es obligatorio.<BR>Asigne un grupo al usuario y vuelva a intentarlo.</HTML>");
                txtCodGrpUsr.requestFocus();
                return false;
            }            
            
            //Valida si el usuario está inactivo, cuando se desee modificar
            if (objTooBar.getEstadoRegistro().equals("Anulado") && objTooBar.getEstado()=='m') {
                if (mostrarMsgYesNo("Para modificar primero debe reactivar el usuario \n ¿Desea reactivarlo?")==0) {
                    return reactivarUsr();
                }
            }
        }
        catch(Exception Evt)
        {  
           return false;
        }
        return true;
    }
    
    /**
     * Esta función valida que no exista un usuario con la misma cedula de identidad
     * @return false: Si existe usuario registrado con la misma cedula
     * <BR>true: En el caso contrario.
     */
    private boolean validaExiIdeUsr()
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
                strSQL+=" SELECT tx_ced FROM tbm_usr WHERE tx_ced='" + txtCed.getText().trim() + "'";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                {
                    blnRes=false;
                }                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;                
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
     * Esta función valida que no exista el mismo usuario
     * @return false: Si existe usuario registrado con el mismo user
     * <BR>true: En el caso contrario.
     */
    private boolean validaExiTxtUsr()
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
                strSQL+=" SELECT tx_usr FROM tbm_usr WHERE tx_usr='" + txtUsr.getText().trim() + "'";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                {
                    blnRes=false;
                }                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;                
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
     * Función que permite obtener los filtros de búsquedas
     * @return 
     */
    private String sqlFil() 
    {
        String strFil= "";
        //Filtro: Por Código de Usuario
        if (!txtCodUsr.getText().equals("")) {
            //Cuando se ingresa el código de usuario, no se realiza búsqueda por demás filtros.
            strFil+=" AND a.co_usr=" + txtCodUsr.getText().replaceAll("'", "''") + "";
        }   
        else //Si no existe código de usuario ingresado, se busca por los demás filtros.
        {
            //Filtro: Por Cédula
            if (!txtCed.getText().equals("")) {
                strFil+=" AND tx_ced LIKE '%" + txtCed.getText().trim() + "%'";
            }
            //Filtro: Por código de Grupo de Usuario
            if (!txtCodGrpUsr.getText().equals("")) {
                strFil+=" AND co_grpUsr = " + txtCodGrpUsr.getText()+ "";
            }
            //Filtro: Por User
            if (!txtUsr.getText().equals("")) {
                strFil+=" AND LOWER(tx_usr) LIKE "+objUti.codificar(txtUsr.getText().trim().toLowerCase())+"";
            }
            //Filtro: Por Nombre
            /*if (!txtNom.getText().equals("")) {
                strFil+=" AND LOWER(tx_nom) LIKE '" + txtNom.getText().replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
            }
            //Filtro: Por Dirección
            if (!txtDir.getText().equals("")) {
                strFil+=" AND LOWER(tx_dir) LIKE '" + txtDir.getText().replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
            }
            //Filtro: Por Teléfono
            if (!txtTel.getText().equals("")) {
                strFil+=" AND tx_tel LIKE '" + txtTel.getText().replaceAll("'", "''") + "%'";
            }
            //Filtro: Por Correo Electrónico 
            if (!txtCorEle.getText().equals("")) {
                strFil+=" AND LOWER(tx_corEle) LIKE '" + txtCorEle.getText().replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
            }   */         
        }
        return strFil;
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg(String strFil)
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
                    objTooBar.setMenSis("Se encontraron " + (arlDatConUsr.size()) + " registros");
                    intIndiceUsr=arlDatConUsr.size()-1;
                    cargarReg();
                }
                else{
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
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
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg(){
        boolean blnRes=false;
        try{
            if (cargarTabGen()){
                if (cargarTabEmpUsr()){
                    if (cargarTabLocUsr()){
                        if (cargarTabBodUsr()){
                            blnRes=true;
                        }
                    }
                }
            }  
            blnHayCam=false;
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar el Tab "General"
     * @return true: Si se pudo cargar el tab.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTabGen(){
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
                    txtCodUsr.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_ced");
                    txtCed.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nom");
                    txtNom.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_dir");
                    txtDir.setText((strAux==null)?"":strAux);    
                    strAux=rst.getString("tx_tel");
                    txtTel.setText((strAux==null)?"":strAux);                        
                    strAux=rst.getString("tx_usr");
                    txtUsr.setText((strAux==null)?"":strAux);                       
                    strAux=rst.getString("tx_pwd");
                    txtPwd.setText((strAux==null)?"":strAux);     
                    strAux=rst.getString("tx_corEle");
                    txtCorEle.setText((strAux==null)?"":strAux);                         
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);    
                    strAux=rst.getString("co_grpUsr");
                    txtCodGrpUsr.setText((strAux==null)?"":strAux);       
                    strAux=rst.getString("tx_nomGrpUsr");
                    txtGrpUsr.setText((strAux==null)?"":strAux);                          
                    
                    objUti.setItemCombo(cboEstCiv, vecEstCiv, rst.getString("co_estCiv"));
                    
                    strAux=(rst.getObject("tx_sex")==null?"N":rst.getString("tx_sex"));
                    cboSex.setSelectedIndex((strAux.equals("M")?1:(strAux.equals("F")?2:0)));

                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A")){
                        strAux="Activo";
                        cboEstReg.setSelectedIndex(0);
                    }
                    else if (strAux.equals("I")){
                        strAux="Anulado";
                        cboEstReg.setSelectedIndex(1);
                    }
                    else{
                        strAux="Otro";
                        cboEstReg.setSelectedIndex(0);
                    }
                    objTooBar.setEstadoRegistro(strAux);
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
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConUsr.size()) );
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

    /**
     * Esta función permite cargar las empresas asignados al usuario
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTabEmpUsr(){
        boolean blnRes=true;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm=con.createStatement();
                //Arma la sentencia.
                strSQL ="";
                strSQL+=" SELECT CASE WHEN a1.co_usr IS NULL THEN 'N' ELSE 'S' END as blnChkSel , a.co_emp, a.tx_nom as tx_nomEmp, a1.st_ven, a1.st_com";
                strSQL+=" FROM tbm_emp as a ";
                strSQL+=" LEFT OUTER JOIN tbr_usrEmp as a1  ON (a.co_emp=a1.co_emp AND a1.co_usr="+ objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR)+")";
                strSQL+=" WHERE a.co_emp <> 3  ";              
                strSQL+=" ORDER BY a.co_emp";
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                while(rst.next())
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_EMP_USR_LIN,"");
                    vecReg.add(INT_TBL_EMP_USR_CHKSEL, (rst.getObject("blnChkSel")==null?false:rst.getObject("blnChkSel").equals("N")?false:rst.getObject("blnChkSel").equals("S")?true:false));
                    vecReg.add(INT_TBL_EMP_USR_CODEMP, rst.getString("co_emp") );
                    vecReg.add(INT_TBL_EMP_USR_NOMEMP, rst.getString("tx_nomEmp") );
                    vecReg.add(INT_TBL_EMP_USR_CHKVEN,(rst.getObject("st_ven")==null?false:rst.getObject("st_ven").equals("N")?false:rst.getObject("st_ven").equals("S")?true:false));
                    vecReg.add(INT_TBL_EMP_USR_CHKCOM,(rst.getObject("st_com")==null?false:rst.getObject("st_com").equals("N")?false:rst.getObject("st_com").equals("S")?true:false));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModEmpUsr.setData(vecDat);
                tblDatEmpUsr.setModel(objTblModEmpUsr);
            }
        } 
        catch (SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  } 
        catch (Exception e){  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   } 
        return blnRes;
    }
    
    /**
     * Esta función permite cargar los locales asignados al usuario
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTabLocUsr(){
        boolean blnRes=true;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm=con.createStatement();
                //Arma la sentencia.
                strSQL ="";
                strSQL+=" SELECT CASE WHEN a2.co_usr IS NULL THEN 'N' ELSE 'S' END as blnChkSel , a.co_emp, a1.co_loc, a1.tx_nom as tx_nomLoc, a2.st_Reg";
                strSQL+=" FROM tbm_emp as a ";
                strSQL+=" INNER JOIN tbm_loc as a1 ON (a.co_emp=a1.co_emp)";
                strSQL+=" LEFT OUTER JOIN tbr_locUsr as a2  ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a2.co_usr="+ objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR)+")";
                strSQL+=" WHERE a.co_emp <> 3  ";              
                strSQL+=" ORDER BY a.co_emp, a1.co_loc ";
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                while(rst.next())
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_LOC_USR_LIN,"");
                    vecReg.add(INT_TBL_LOC_USR_CHKSEL, (rst.getObject("blnChkSel")==null?false:rst.getObject("blnChkSel").equals("N")?false:rst.getObject("blnChkSel").equals("S")?true:false));
                    vecReg.add(INT_TBL_LOC_USR_CODEMP, rst.getString("co_emp") );
                    vecReg.add(INT_TBL_LOC_USR_CODLOC, rst.getString("co_loc") );
                    vecReg.add(INT_TBL_LOC_USR_NOMLOC, rst.getString("tx_nomLoc") );
                    vecReg.add(INT_TBL_LOC_USR_CHKPRE,(rst.getObject("st_Reg")==null?false:rst.getObject("st_Reg").equals("A")?false:rst.getObject("st_Reg").equals("P")?true:false));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModLocUsr.setData(vecDat);
                tblDatLocUsr.setModel(objTblModLocUsr);
            }
        } 
        catch (SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  } 
        catch (Exception e){  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   } 
        return blnRes;
    }   
    
    /**
     * Esta función permite cargar las bodegas asignadas al usuario
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTabBodUsr(){
        boolean blnRes=true;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm=con.createStatement();
                //Arma la sentencia.
                strSQL ="";
                strSQL+=" SELECT CASE WHEN a1.co_usr IS NULL THEN 'N' ELSE 'S' END as blnChkSel , a.co_emp, a.co_bod, a.tx_nom as tx_nomBod, a1.st_Reg";
                strSQL+=" FROM tbm_bod as a ";
                strSQL+=" LEFT OUTER JOIN tbr_bodUsr as a1  ON (a.co_emp=a1.co_emp AND a.co_bod=a1.co_bod AND a1.co_usr="+ objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR)+")";
                strSQL+=" WHERE a.co_emp = "+objParSis.getCodigoEmpresaGrupo();             
                strSQL+=" ORDER BY a.co_emp, a.co_bod";
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                while(rst.next())
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_BOD_USR_LIN,"");
                    vecReg.add(INT_TBL_BOD_USR_CHKSEL, (rst.getObject("blnChkSel")==null?false:rst.getObject("blnChkSel").equals("N")?false:rst.getObject("blnChkSel").equals("S")?true:false));
                    vecReg.add(INT_TBL_BOD_USR_CODEMP, rst.getString("co_emp") );
                    vecReg.add(INT_TBL_BOD_USR_CODBOD, rst.getString("co_bod") );
                    vecReg.add(INT_TBL_BOD_USR_NOMBOD, rst.getString("tx_nomBod") );
                    vecReg.add(INT_TBL_BOD_USR_CHKPRE,(rst.getObject("st_Reg")==null?false:rst.getObject("st_Reg").equals("A")?false:rst.getObject("st_Reg").equals("P")?true:false));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModBodUsr.setData(vecDat);
                tblDatBodUsr.setModel(objTblModBodUsr);
            }
        } 
        catch (SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  } 
        catch (Exception e){  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   } 
        return blnRes;
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
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(){
        boolean blnRes = false;
        try{
             //Obtener la fecha del servidor.
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            if (datFecAux==null)
                return false;   
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (insertarTabGen()){
                    if (insertarTabEmpUsr()){
                        if (insertarTabLocUsr()){
                            if (insertarTabBodUsr()){
                                con.commit();
                                blnRes=true; 
                            }
                            else
                                con.rollback();  
                        }
                        else
                            con.rollback();                            
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;            
        }
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
 
    /**
     * Esta función permite insertar datos del Tab "General"
     * @return true: Si se pudo insertar datos.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarTabGen(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
             
                //Armar sentencia SQL
                stbSQL=new StringBuffer();
                stbSQL.append(" INSERT INTO tbm_usr(co_usr, tx_usr, tx_pwd, tx_pwd1, tx_pwd2, st_usrSis, co_grpUsr"
                             +" , tx_ced, tx_nom, tx_dir, tx_tel, tx_corEle, tx_sex, co_estCiv, tx_obs1, tx_obs2"
                             +" , st_reg, fe_ing, fe_ultMod, st_regRep, ne_diaProActCla, fe_proActCla, tx_comUltActCla"
                             +" , st_usrIngCuaEqu, tx_tipIdeUtiIngSisEsc, tx_tipIdeUtiIngSisWeb)"
                             +" VALUES ("
                             +" (SELECT CASE WHEN MAX (co_usr) IS NULL THEN 1 ELSE MAX(co_usr)+1 END AS co_usr FROM tbm_usr) " //co_usr
                             +" , " + objUti.codificar(txtUsr.getText().trim()) + ""       //tx_usr
                             +" , MD5(" + objUti.codificar(txtPwd.getText().trim()) + ") " //tx_pwd
                             +" , Null " //tx_pwd1
                             +" , Null " //tx_pwd2
                             +" , 'S' "  //st_usrSis
                             +" , " + txtCodGrpUsr.getText() + ""  //co_grpUsr
                             +" , " + objUti.codificar(txtCed.getText().trim()) + ""      //tx_ced
                             +" , " + objUti.codificar(txtNom.getText().trim()) + ""      //tx_nom
                             +" , " + objUti.codificar(txtDir.getText().trim()) + ""      //tx_dir
                             +" , " + objUti.codificar(txtTel.getText().trim()) + ""      //tx_tel
                             +" , " + objUti.codificar(txtCorEle.getText().trim()) + ""   //txtCorEle
                             +" , " + objUti.codificar((cboSex.getSelectedIndex()==1?"M":(cboSex.getSelectedIndex()==2?"F":"N")))+"" //tx_sex
                             +" , " + Integer.parseInt(vecEstCiv.get(cboEstCiv.getSelectedIndex()).toString())+"" //co_estCiv
                             +" , " + objUti.codificar(txaObs1.getText().trim()) + ""        //tx_obs1
                             +" , " + objUti.codificar(txaObs2.getText().trim()) + ""        //tx_obs2
                             +" , " + objUti.codificar((cboEstReg.getSelectedIndex()==0?"A":"I"))+"" //st_reg
                             +" , '" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'" //fe_ing                                     
                             +" , '" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'" //fe_ultMod 
                             +" , 'P' "  //st_regRep
                             +" , 30  "  //ne_diaProActCla
                             +" , Null"  //fe_proActCla                                     
                             +" , Null"  //tx_comUltActCla     
                             +" , 'N' "  //st_usrIngCuaEqu
                             +" , 'M' "  //tx_tipIdeUtiIngSisEsc
                             +" , 'I' "  //tx_tipIdeUtiIngSisWeb
                             +" ); \n");           
                 
                System.out.println("insertarTabGen: " + stbSQL);
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
    /**
     * Esta función permite insertar datos del Tab "Empresas por Usuario"
     * @return true: Si se pudo insertar datos.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarTabEmpUsr(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar sentencia SQL
                stbSQL=new StringBuffer();
                for (int i=0;i<objTblModEmpUsr.getRowCountTrue();i++){
                    if(objTblModEmpUsr.isChecked(i, INT_TBL_EMP_USR_CHKSEL)){
                        stbSQL.append("INSERT INTO tbr_usrEmp (co_emp, co_usr, st_ven, st_com, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod) "
                                     +" VALUES ("
                                     +"   " + objTblModEmpUsr.getValueAt(i,INT_TBL_EMP_USR_CODEMP) + " " //co_emp
                                     +" , (SELECT CASE WHEN MAX (co_usr) IS NULL THEN 1 ELSE MAX(co_usr) END AS co_usr FROM tbm_usr) " //co_usr
                                     +" , " + objUti.codificar(objTblModEmpUsr.isChecked(i, INT_TBL_EMP_USR_CHKVEN)?"S":"N")+"" //st_ven
                                     +" , " + objUti.codificar(objTblModEmpUsr.isChecked(i, INT_TBL_EMP_USR_CHKCOM)?"S":"N")+"" //st_com
                                     +" , 'A'" //st_reg
                                     +" , '" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'" //fe_ing                                     
                                     +" , '" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'" //fe_ultMod 
                                     +" , " + objParSis.getCodigoUsuario()+"" //co_usrIng 
                                     +" , " + objParSis.getCodigoUsuario()+"" //co_usrMod
                                     +" ); \n");      
                    }
                }
                System.out.println("insertarTabEmpUsr: " + stbSQL);
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
    
    /**
     * Esta función permite insertar datos del Tab "Locales por Usuario"
     * @return true: Si se pudo insertar datos.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarTabLocUsr(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar sentencia SQL
                stbSQL=new StringBuffer();
                for (int i=0;i<objTblModLocUsr.getRowCountTrue();i++){
                    if(objTblModLocUsr.isChecked(i, INT_TBL_LOC_USR_CHKSEL)){
                        stbSQL.append("INSERT INTO tbr_locUsr (co_emp, co_loc, co_usr, st_reg) "
                                     +" VALUES ("
                                     +"  " + objTblModLocUsr.getValueAt(i,INT_TBL_LOC_USR_CODEMP) + " " //co_emp
                                     +" , " + objTblModLocUsr.getValueAt(i,INT_TBL_LOC_USR_CODLOC) + " " //co_loc
                                     +" , (SELECT CASE WHEN MAX (co_usr) IS NULL THEN 1 ELSE MAX(co_usr) END AS co_usr FROM tbm_usr) " //co_usr
                                     +" , " + objUti.codificar(objTblModLocUsr.isChecked(i, INT_TBL_LOC_USR_CHKPRE)?"P":"A")+"" //st_reg
                                     +" ); \n");                           
                    }
                }
                System.out.println("insertarTabLocUsr: " + stbSQL);
                stm.executeUpdate(stbSQL.toString());
                stm.close();
                stm=null;
                stbSQL=null;
            }
        }
        catch (java.sql.SQLException e)  {
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
     * Esta función permite insertar datos del Tab "Bodegas por Usuario"
     * @return true: Si se pudo insertar datos.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarTabBodUsr(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar sentencia SQL
                stbSQL=new StringBuffer();
                for (int i=0;i<objTblModBodUsr.getRowCountTrue();i++){
                    if(objTblModBodUsr.isChecked(i, INT_TBL_BOD_USR_CHKSEL)){
                        stbSQL.append("INSERT INTO tbr_bodUsr (co_emp, co_bod, co_usr, st_reg) "
                                     +" VALUES ("
                                     +"  " + objTblModBodUsr.getValueAt(i,INT_TBL_BOD_USR_CODEMP) + " " //co_emp
                                     +" , " + objTblModBodUsr.getValueAt(i,INT_TBL_BOD_USR_CODBOD) + " " //co_bod
                                     +" , (SELECT CASE WHEN MAX (co_usr) IS NULL THEN 1 ELSE MAX(co_usr) END AS co_usr FROM tbm_usr) " //co_usr
                                     +" , " + objUti.codificar(objTblModBodUsr.isChecked(i, INT_TBL_BOD_USR_CHKPRE)?"P":"A")+"" //st_reg
                                     +" ); \n");                           
                    }
                }
                System.out.println("insertarTabBodUsr: " + stbSQL);
                stm.executeUpdate(stbSQL.toString());
                stm.close();
                stm=null;
                stbSQL=null;
            }
        }
        catch (java.sql.SQLException e)  {
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
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg(){
        boolean blnRes = false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (actualizarTabGen()){
                    if (actualizarTabEmpUsr()){
                        if (actualizarTabLocUsr()){
                            if (actualizarTabBodUsr()){
                                con.commit();
                                blnRes=true;  
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;            
        }
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite actualizar datos del Tab "General"
     * @return true: Si se pudo actualizar datos.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarTabGen(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;                
                //Armar sentencia SQL
                stbSQL=new StringBuffer();
                stbSQL.append(" UPDATE tbm_usr SET "
                             +"   tx_nom=" + objUti.codificar(txtNom.getText().trim()) + ""
                             +" , co_grpUsr=" + txtCodGrpUsr.getText() + ""
                             //+" , tx_ced=" + objUti.codificar(txtCed.getText().trim()) + ""
                             //+" , tx_usr=" + objUti.codificar(txtUsr.getText().trim()) + ""
                             +" , tx_dir=" + objUti.codificar(txtDir.getText().trim()) + "" 
                             +" , tx_tel=" + objUti.codificar(txtTel.getText().trim()) + ""
                             +" , tx_corEle=" + objUti.codificar(txtCorEle.getText().trim()) + ""
                             +" , tx_sex=" + objUti.codificar((cboSex.getSelectedIndex()==1?"M":(cboSex.getSelectedIndex()==2?"F":"N")))+""
                             +" , co_estCiv=" + Integer.parseInt(vecEstCiv.get(cboEstCiv.getSelectedIndex()).toString())+""
                             +" , tx_obs1=" + objUti.codificar(txaObs1.getText().trim()) + "" 
                             +" , tx_obs2=" + objUti.codificar(txaObs2.getText().trim()) + ""
                             +" , st_reg=" + objUti.codificar((cboEstReg.getSelectedIndex()==0?"A":"I"))+""
                             +" , fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'"
                             +" , st_regRep='M'"
                             //+" , st_usrIngCuaEqu='N'"
                             //+" , tx_tipIdeUtiIngSisEsc='M'"
                             //+" , tx_tipIdeUtiIngSisWeb='I'"
                             +" WHERE co_usr="+txtCodUsr.getText()+""
                             +" ; \n"); 
                
                if(chkEdiPwd.isSelected()){
                    stbSQL.append(" UPDATE tbm_usr SET tx_pwd=MD5(" + objUti.codificar(txtPwd.getText().trim()) + ") "
                                 //+" , tx_pwd1=MD5(" + objUti.codificar(txtPwd1.getText().trim()) + ") "
                                 //+" , tx_pwd2=MD5(" + objUti.codificar(txtPwd2.getText().trim()) + ") "
                                 //+" , ne_diaProActCla=30"
                                 +" , fe_proActCla='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaBaseDatos()) + "'"
                                 +" , tx_comUltActCla="+ objUti.codificar(objParSis.getNombreComputadoraConDirIP())+ ""
                                 +" WHERE co_usr="+txtCodUsr.getText()+"; \n");
                }
                System.out.println("actualizarTabGen: " + stbSQL);
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
    /**
     * Esta función permite actualizar datos del Tab "Empresas por Usuario"
     * @return true: Si se pudo actualizar datos.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarTabEmpUsr(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar sentencia SQL
                stbSQL=new StringBuffer();
                //Eliminar datos de usuario siempre, esto permite eliminar todos los accesos o concederle los que esten seleccionados.
                stbSQL.append(" DELETE FROM tbr_usrEmp WHERE co_usr=" + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR)+"; \n");
                for (int i=0;i<objTblModEmpUsr.getRowCountTrue();i++){
                    if(objTblModEmpUsr.isChecked(i, INT_TBL_EMP_USR_CHKSEL)){
                        stbSQL.append("INSERT INTO tbr_usrEmp (co_emp, co_usr, st_ven, st_com, st_Reg, co_usrMod, fe_ultMod) "
                                     +" VALUES ("
                                     +"   " + objTblModEmpUsr.getValueAt(i,INT_TBL_EMP_USR_CODEMP) + " " //co_emp
                                     +" , " + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR)+"" //co_usr
                                     +" , " + objUti.codificar(objTblModEmpUsr.isChecked(i, INT_TBL_EMP_USR_CHKVEN)?"S":"N")+"" //st_ven
                                     +" , " + objUti.codificar(objTblModEmpUsr.isChecked(i, INT_TBL_EMP_USR_CHKCOM)?"S":"N")+"" //st_com
                                     +" , 'A'" //st_reg
                                     +" , " + objParSis.getCodigoUsuario()+"" //co_usrMod
                                     +" , '" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'" //fe_ultMod 
                                     +" ); \n");  
                    }              
                }
                System.out.println("actualizarTabEmpUsr: " + stbSQL);
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
    
    /**
     * Esta función permite actualizar datos del Tab "Locales por Usuario"
     * @return true: Si se pudo actualizar datos.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarTabLocUsr(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar sentencia SQL
                stbSQL=new StringBuffer();
                //Eliminar datos de usuario siempre, esto permite eliminar todos los accesos o concederle los que esten seleccionados.
                stbSQL.append(" DELETE FROM tbr_locUsr WHERE co_usr=" + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR)+";\n");
                for (int i=0;i<objTblModLocUsr.getRowCountTrue();i++){
                    if(objTblModLocUsr.isChecked(i, INT_TBL_LOC_USR_CHKSEL)){
                        stbSQL.append("INSERT INTO tbr_locUsr (co_emp, co_loc, co_usr, st_reg) "
                                     +" VALUES ("
                                     +"   " + objTblModLocUsr.getValueAt(i,INT_TBL_LOC_USR_CODEMP) + " " //co_emp
                                     +" , " + objTblModLocUsr.getValueAt(i,INT_TBL_LOC_USR_CODLOC) + " " //co_loc
                                     +" , " + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR)+"" //co_usr
                                     +" , " + objUti.codificar(objTblModLocUsr.isChecked(i, INT_TBL_LOC_USR_CHKPRE)?"P":"A")+"" //st_reg
                                     +" ); \n");  
                    }              
                }
                System.out.println("actualizarTabLocUsr: " + stbSQL);
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
    /**
     * Esta función permite actualizar datos del Tab "Bodegas por Usuario"
     * @return true: Si se pudo actualizar datos.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarTabBodUsr(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar sentencia SQL
                stbSQL=new StringBuffer();
                //Eliminar datos de usuario siempre, esto permite eliminar todos los accesos o concederle los que esten seleccionados.
                stbSQL.append(" DELETE FROM tbr_bodUsr WHERE co_usr=" + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR)+"; \n");
                for (int i=0;i<objTblModBodUsr.getRowCountTrue();i++){
                    if(objTblModBodUsr.isChecked(i, INT_TBL_BOD_USR_CHKSEL)){
                        stbSQL.append("INSERT INTO tbr_bodUsr (co_emp, co_bod, co_usr, st_reg) "
                                     +" VALUES ("
                                     +"   " + objTblModBodUsr.getValueAt(i,INT_TBL_BOD_USR_CODEMP) + " " //co_emp
                                     +" , " + objTblModBodUsr.getValueAt(i,INT_TBL_BOD_USR_CODBOD) + " " //co_bod
                                     +" , " + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR)+"" //co_usr
                                     +" , " + objUti.codificar(objTblModBodUsr.isChecked(i, INT_TBL_BOD_USR_CHKPRE)?"P":"A")+"" //st_reg
                                     +" ); \n");  
                    }              
                }
                System.out.println("actualizarTabBodUsr: " + stbSQL);
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
    
    
    /**
     * Esta función permite eliminar datos
     * @return true: Si se pudo eliminar datos.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar sentencia SQL
                stbSQL=new StringBuffer();
                //Eliminar datos de usuario
                stbSQL.append(" DELETE FROM tbr_usrEmp WHERE co_usr=" + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR)+";");
                stbSQL.append(" DELETE FROM tbr_locUsr WHERE co_usr=" + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR)+";");
                stbSQL.append(" DELETE FROM tbr_bodUsr WHERE co_usr=" + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR)+";");
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

 
    
    
    
}



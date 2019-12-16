/*
 * ZafMae17.java
 *
 * Created on 05 de Noviembre del 2015, 09:05
 */

package Maestros.ZafMae17;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
/**
 *
 * @author  Rosa Zambrano
 */
public class ZafMae17 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable
    static final int INT_TBL_DAT_LIN = 0;                       //Linea
    static final int INT_TBL_DAT_EST_CLI = 1;                   //Si es Cliente
    static final int INT_TBL_DAT_EST_PRO = 2;                   //Si es Proveedor
    static final int INT_TBL_DAT_COD_CLI = 3;                   //Codigo cliente/proveedor.
    static final int INT_TBL_DAT_IDE_CLI = 4;                   //Identificación del cliente/proveedor.
    static final int INT_TBL_DAT_NOM_CLI = 5;                   //Nombre del cliente/proveedor.
    static final int INT_TBL_DAT_DIR_CLI = 6;                   //Dirección del cliente/proveedor.
    static final int INT_TBL_DAT_TEL_CLI = 7;                   //Télefono del cliente/proveedor.
    static final int INT_TBL_DAT_CIU_CLI = 8;                   //Ciudad del cliente/proveedor.
    static final int INT_TBL_DAT_NOM_VEN = 9;                   //Nombre del Vendedor asignado al cliente/proveedor.
    static final int INT_TBL_DAT_EST_REG = 10;                  //Estado del Cliente/Proveedor
    static final int INT_TBL_DAT_ULT_MOV = 11;                  //Fecha de último Movimiento del cliente/proveedor.   

    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafPerUsr objPerUsr;                                //Permisos Usuarios.
    private ZafVenCon vcoCiu;                                   //Ventana de consulta "Ciudad".
    private ZafVenCon vcoVen;                                   //Ventana de consulta.
    private ZafSelFec objSelFec;                                //Selector de Fecha.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private Vector vecDat, vecCab, vecReg, vecEstReg;
    private String strSQL, strAux;
    private String strCodCiu, strCiu;                           //Contenido del campo al obtener el foco.
    private String strCodVen, strVen;                           //Contenido del campo al obtener el foco.
    
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafMae17(ZafParSis obj) 
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
           
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()) 
            {
                initComponents();
                if (!configurarFrm())
                    exitForm();
            }
            else
            {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde EMPRESAS.");
                dispose();
            }
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
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTblCelRenChk = new ZafTblCelRenChk();
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.2 ");
            lblTit.setText(strAux);
            
            
            //Configurar Selector de Fecha. 
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(false);
            objSelFec.setTitulo("Fecha Ultimo Movimiento");
            panFil.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            
            
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
                        
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(726)) 
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(727)) 
            {
                butCer.setVisible(false);
            }
    
            //Configurar las ZafVenCon.
            configurarVenConCiu();
            configurarVenConVen();
            
            //Configurar los JTables.
            configurarTblDat();
   
            //Configurar Combos.
            configurarComboEstReg();
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(12);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_EST_CLI,"Cliente");
            vecCab.add(INT_TBL_DAT_EST_PRO,"Proveedor");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_IDE_CLI,"Identificación");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Nombre");
            vecCab.add(INT_TBL_DAT_DIR_CLI,"Dirección");
            vecCab.add(INT_TBL_DAT_TEL_CLI,"Teléfono");          
            vecCab.add(INT_TBL_DAT_CIU_CLI,"Ciudad");
            vecCab.add(INT_TBL_DAT_NOM_VEN,"Vendedor");
            vecCab.add(INT_TBL_DAT_EST_REG,"Est.Reg.");
            vecCab.add(INT_TBL_DAT_ULT_MOV,"Fec.Últ.Mov.");
             
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_EST_CLI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_EST_PRO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_IDE_CLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_DIR_CLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_TEL_CLI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CIU_CLI).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_NOM_VEN).setPreferredWidth(95);
            tcmAux.getColumn(INT_TBL_DAT_EST_REG).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_ULT_MOV).setPreferredWidth(75);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_EST_CLI).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_EST_PRO).setResizable(false);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);            
            
            //Configurar JTable: Establecer el check en columnas
            tcmAux.getColumn(INT_TBL_DAT_EST_CLI).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_EST_PRO).setCellRenderer(objTblCelRenChk);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
   
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);

            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
            
    
    private boolean configurarComboEstReg() 
    {
        boolean blnRes = true;
        
        try 
        {
            //Configurar el combo "Estado de registro".
            vecEstReg = new Vector();
            vecEstReg.add("");
            vecEstReg.add("A");
            vecEstReg.add("I");
            vecEstReg.add("N");
            cboEstReg.addItem("(Todos)");
            cboEstReg.addItem("Activos");
            cboEstReg.addItem("Inactivos");
            cboEstReg.addItem("Nuevos");
            cboEstReg.setSelectedIndex(0);   
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    private boolean configurarVenConVen() 
    {
        boolean blnRes = true;
        
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_usr");
            arlCam.add("a.tx_usr");
            arlCam.add("a.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Usuario");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("200");
            arlAncCol.add("494");
            
            //Armar la sentencia SQL.
            strSQL = "";                     
            strSQL += " SELECT a.co_usr, a.tx_usr, a.tx_nom ";
            strSQL += " FROM tbm_usr as a";
            strSQL += " INNER JOIN tbr_usrEmp as b ON (a.co_usr=b.co_usr)";
            strSQL += " WHERE (b.st_ven='S' OR b.st_com='S')";
            strSQL += " AND b.co_Emp="+objParSis.getCodigoEmpresa();
            strSQL += " ORDER BY a.tx_nom";
            
            System.out.println("configurarVenConVen: " + strSQL);
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=2;  // Usuario
   
            vcoVen = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Vendedores", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoVen.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            vcoVen.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    private boolean configurarVenConCiu() 
    {
        boolean blnRes = true;
        
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_ciu");
            arlCam.add("a.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("300");
            
            //Armar la sentencia SQL.
            strSQL = "";   
            strSQL += " SELECT a.co_ciu, a.tx_desLar ";
            strSQL += " FROM tbm_ciu as a";
            strSQL += " ORDER BY a.tx_desLar";
            
            System.out.println("configurarVenConCiu: " + strSQL);
   
            vcoCiu = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Ciudades", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoCiu.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
     /**
     * Esta funcion permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se
     * debe hacer una busqueda directa (No se muestra la ventana de consulta a
     * menos que no exista lo que se está buscando) o presentar la ventana de
     * consulta para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de busqueda a realizar.
     * @return true: Si no se presenta ningun problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConVen(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoVen.setCampoBusqueda(2);
                    vcoVen.show();
                    if (vcoVen.getSelectedButton() == vcoVen.INT_BUT_ACE) 
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtVen.setText(vcoVen.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoVen.buscar("a.co_usr", txtCodVen.getText()))
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtVen.setText(vcoVen.getValueAt(3));
                    }
                    else 
                    {
                        vcoVen.setCampoBusqueda(0);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.show();
                        if (vcoVen.getSelectedButton() == vcoVen.INT_BUT_ACE) 
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtVen.setText(vcoVen.getValueAt(3));
                        } 
                        else 
                        {
                            txtCodVen.setText(strCodVen);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoVen.buscar("a.tx_nom", txtVen.getText())) 
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtVen.setText(vcoVen.getValueAt(3));
                    } 
                    else 
                    {
                        vcoVen.setCampoBusqueda(2);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.show();
                        if (vcoVen.getSelectedButton() == vcoVen.INT_BUT_ACE) 
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtVen.setText(vcoVen.getValueAt(3));
                        } 
                        else 
                        {
                            txtVen.setText(strVen);
                        }
                    }
                    break;
            }
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    private boolean mostrarVenConCiu(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCiu.setCampoBusqueda(1);
                    vcoCiu.show();
                    if (vcoCiu.getSelectedButton() == vcoCiu.INT_BUT_ACE) 
                    {
                        txtCodCiu.setText(vcoCiu.getValueAt(1));
                        txtCiu.setText(vcoCiu.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCiu.buscar("a.co_ciu", txtCodCiu.getText()))
                    {
                        txtCodCiu.setText(vcoCiu.getValueAt(1));
                        txtCiu.setText(vcoCiu.getValueAt(2));
                    }
                    else 
                    {
                        vcoCiu.setCampoBusqueda(0);
                        vcoCiu.setCriterio1(11);
                        vcoCiu.cargarDatos();
                        vcoCiu.show();
                        if (vcoCiu.getSelectedButton() == vcoCiu.INT_BUT_ACE) 
                        {
                            txtCodCiu.setText(vcoCiu.getValueAt(1));
                            txtCiu.setText(vcoCiu.getValueAt(2));
                        } 
                        else 
                        {
                            txtCodCiu.setText(strCodCiu);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoCiu.buscar("a.tx_desLar", txtCiu.getText())) 
                    {
                        txtCodCiu.setText(vcoCiu.getValueAt(1));
                        txtCiu.setText(vcoCiu.getValueAt(2));
                    } 
                    else 
                    {
                        vcoCiu.setCampoBusqueda(1);
                        vcoCiu.setCriterio1(11);
                        vcoCiu.cargarDatos();
                        vcoCiu.show();
                        if (vcoCiu.getSelectedButton() == vcoCiu.INT_BUT_ACE) 
                        {
                            txtCodCiu.setText(vcoCiu.getValueAt(1));
                            txtCiu.setText(vcoCiu.getValueAt(2));
                        } 
                        else 
                        {
                            txtCiu.setText(strCiu);
                        }
                    }
                    break;
            }
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que estï¿½ ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podrï¿½a presentar mensajes informativos en un JLabel e ir incrementando
     * un JProgressBar con lo cual el usuario estaría informado en todo momento
     * de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el
     * proceso.
     */
    private class ZafThreadGUI extends Thread
    {

        @Override
        public void run() 
        {

            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);

            if (!cargarDetReg(sqlConFil())) 
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }

            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount() > 0) 
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI = null;

            pgrSis.setValue(0);
            pgrSis.setIndeterminate(false);

        }
    }

    
    
     private class ZafMouMotAda extends MouseMotionAdapter 
     {
        @Override
        public void mouseMoved(MouseEvent evt) 
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_EST_CLI:
                    strMsg="Cliente?";
                    break;
                case INT_TBL_DAT_EST_PRO:
                    strMsg="Proveedor?";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente/proveedor";
                    break;
                case INT_TBL_DAT_IDE_CLI:
                    strMsg="Identificación del cliente/proveedor";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente/proveedor";
                    break;
                case INT_TBL_DAT_DIR_CLI:
                    strMsg="Dirección del cliente/proveedor";
                    break;
                case INT_TBL_DAT_TEL_CLI:
                    strMsg="Teléfono del cliente/proveedor";
                    break;
                 case INT_TBL_DAT_CIU_CLI:
                    strMsg="Ciudad del cliente/proveedor";
                    break;
               case INT_TBL_DAT_NOM_VEN:
                    strMsg="Vendedor";
                    break;
                case INT_TBL_DAT_EST_REG:
                    strMsg="Estado de Registro del cliente ";
                    break;
                 case INT_TBL_DAT_ULT_MOV:
                    strMsg="Fecha de último movimiento del cliente/proveedor ";
                    break;            
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
     
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        chkCli = new javax.swing.JCheckBox();
        chkPro = new javax.swing.JCheckBox();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblCiu = new javax.swing.JLabel();
        txtCodCiu = new javax.swing.JTextField();
        txtCiu = new javax.swing.JTextField();
        butCiu = new javax.swing.JButton();
        lblVen = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        panCli = new javax.swing.JPanel();
        lblCodDes = new javax.swing.JLabel();
        txtNomDes = new javax.swing.JTextField();
        lblCodHas = new javax.swing.JLabel();
        txtNomHas = new javax.swing.JTextField();
        lblEstReg = new javax.swing.JLabel();
        cboEstReg = new javax.swing.JComboBox();
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

        setClosable(true);
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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        panFil.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panFil.setLayout(null);

        chkCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkCli.setSelected(true);
        chkCli.setText("Clientes");
        panFil.add(chkCli);
        chkCli.setBounds(10, 80, 108, 20);

        chkPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkPro.setSelected(true);
        chkPro.setText("Proveedores");
        panFil.add(chkPro);
        chkPro.setBounds(10, 100, 110, 20);

        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setSelected(true);
        optTod.setText("Todos los clientes/proveedores");
        optTod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optTodStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 130, 400, 20);

        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Sólo los  clientes/proveedores que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(10, 150, 400, 20);

        lblCiu.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCiu.setText("Ciudad:");
        panFil.add(lblCiu);
        lblCiu.setBounds(40, 180, 110, 15);

        txtCodCiu.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodCiu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCiuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCiuFocusLost(evt);
            }
        });
        txtCodCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCiuActionPerformed(evt);
            }
        });
        panFil.add(txtCodCiu);
        txtCodCiu.setBounds(150, 180, 40, 20);

        txtCiu.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCiu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCiuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCiuFocusLost(evt);
            }
        });
        txtCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiuActionPerformed(evt);
            }
        });
        panFil.add(txtCiu);
        txtCiu.setBounds(190, 180, 340, 20);

        butCiu.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCiu.setText("...");
        butCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCiuActionPerformed(evt);
            }
        });
        panFil.add(butCiu);
        butCiu.setBounds(530, 180, 20, 20);

        lblVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblVen.setText("Vendedor:");
        panFil.add(lblVen);
        lblVen.setBounds(40, 200, 110, 15);

        txtCodVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVenFocusLost(evt);
            }
        });
        txtCodVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVenActionPerformed(evt);
            }
        });
        panFil.add(txtCodVen);
        txtCodVen.setBounds(150, 200, 40, 20);

        txtVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtVenFocusLost(evt);
            }
        });
        txtVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVenActionPerformed(evt);
            }
        });
        panFil.add(txtVen);
        txtVen.setBounds(190, 200, 340, 20);

        butVen.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFil.add(butVen);
        butVen.setBounds(530, 200, 20, 20);

        panCli.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nombre del Cliente/Proveedor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 11))); // NOI18N
        panCli.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        panCli.setLayout(null);

        lblCodDes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodDes.setText("Desde:");
        panCli.add(lblCodDes);
        lblCodDes.setBounds(12, 16, 44, 20);

        txtNomDes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDesFocusLost(evt);
            }
        });
        panCli.add(txtNomDes);
        txtNomDes.setBounds(56, 16, 170, 20);

        lblCodHas.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodHas.setText("Hasta:");
        panCli.add(lblCodHas);
        lblCodHas.setBounds(250, 14, 44, 20);

        txtNomHas.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomHasFocusGained(evt);
            }
        });
        txtNomHas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomHasActionPerformed(evt);
            }
        });
        panCli.add(txtNomHas);
        txtNomHas.setBounds(290, 14, 190, 20);

        panFil.add(panCli);
        panCli.setBounds(30, 230, 520, 44);

        lblEstReg.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblEstReg.setText("Estado del registro:");
        lblEstReg.setToolTipText("Estado del registro:");
        panFil.add(lblEstReg);
        lblEstReg.setBounds(30, 280, 130, 16);

        cboEstReg.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        cboEstReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstRegActionPerformed(evt);
            }
        });
        panFil.add(cboEstReg);
        cboEstReg.setBounds(160, 280, 152, 20);

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
        tblDat.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
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

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panEst.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panEst.setMinimumSize(new java.awt.Dimension(24, 26));
        panEst.setPreferredSize(new java.awt.Dimension(200, 15));
        panEst.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        panEst.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panEst, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDesFocusLost
        if (txtNomDes.getText().length() > 0) 
        {
            optFil.setSelected(true);
            if (txtNomHas.getText().length() == 0) 
            {
                txtNomHas.setText(txtNomDes.getText());
            }
        }
    }//GEN-LAST:event_txtNomDesFocusLost

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void optTodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optTodStateChanged
        if (optTod.isSelected())
        {
            txtNomDes.setText("");
            txtNomHas.setText("");
            
            txtCodCiu.setText("");
            txtCiu.setText("");
            
            txtCodVen.setText("");
            txtVen.setText("");
            
            txtNomDes.setText("");
            txtNomHas.setText("");
            
            chkCli.setSelected(true);
            chkPro.setSelected(true);            
            
            cboEstReg.setSelectedIndex(0);
            optFil.setSelected(false);
        }
    }//GEN-LAST:event_optTodStateChanged

    private void txtNomHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomHasFocusGained
        txtNomHas.selectAll();
    }//GEN-LAST:event_txtNomHasFocusGained

    private void txtNomDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDesFocusGained
        txtNomDes.selectAll();
    }//GEN-LAST:event_txtNomDesFocusGained

    private void txtNomHasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomHasActionPerformed
        if (txtNomHas.getText().length() > 0) 
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtNomHasActionPerformed

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        if (!txtCodVen.getText().equalsIgnoreCase(strCodVen)) 
        {
            if (txtCodVen.getText().equals(""))
            {
                txtCodVen.setText("");
                txtVen.setText("");
            } 
            else
            {
                mostrarVenConVen(1);
            }
        } 
        else
        {
            txtCodVen.setText(strCodVen);
        }
        
        if (txtCodVen.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodVenFocusLost

    private void cboEstRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstRegActionPerformed
        if (cboEstReg.getSelectedIndex() > 0) 
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_cboEstRegActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) 
        {
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_optFilActionPerformed

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected())
        {
            optFil.setSelected(false);
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        txtCodVen.transferFocus();
    }//GEN-LAST:event_txtCodVenActionPerformed

    private void txtVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenFocusLost
        if (!txtVen.getText().equalsIgnoreCase(strVen)) 
        {
            if (txtVen.getText().equals(""))
            {
                txtCodVen.setText("");
                txtVen.setText("");
            } 
            else
            {
                mostrarVenConVen(2);
            }
        } 
        else
        {
            txtVen.setText(strVen);
        }
        
        if (txtVen.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }   
    }//GEN-LAST:event_txtVenFocusLost

    private void txtVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVenActionPerformed
        txtVen.transferFocus();
    }//GEN-LAST:event_txtVenActionPerformed

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        mostrarVenConVen(0);
    }//GEN-LAST:event_butVenActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
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

    /* Cerrar la aplicación */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) 
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        strCodVen=txtCodVen.getText();
        txtCodVen.selectAll();
    }//GEN-LAST:event_txtCodVenFocusGained

    private void txtVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenFocusGained
        strVen = txtVen.getText();
        txtVen.selectAll();
    }//GEN-LAST:event_txtVenFocusGained

    private void txtCodCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCiuFocusGained
        strCodCiu=txtCodCiu.getText();
        txtCodCiu.selectAll();
    }//GEN-LAST:event_txtCodCiuFocusGained

    private void txtCodCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCiuFocusLost
        if (!txtCodCiu.getText().equalsIgnoreCase(strCodCiu)) 
        {
            if (txtCodCiu.getText().equals(""))
            {
                txtCodCiu.setText("");
                txtCiu.setText("");
            } 
            else
            {
                mostrarVenConCiu(1);
            }
        } 
        else
        {
            txtCodCiu.setText(strCodCiu);
        }
        
        if (txtCodCiu.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodCiuFocusLost

    private void txtCodCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCiuActionPerformed
       txtCodCiu.transferFocus();
    }//GEN-LAST:event_txtCodCiuActionPerformed

    private void txtCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCiuFocusGained
        strCiu = txtCiu.getText();
        txtCiu.selectAll();
    }//GEN-LAST:event_txtCiuFocusGained

    private void txtCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCiuFocusLost
        if (!txtCiu.getText().equalsIgnoreCase(strCiu)) 
        {
            if (txtCiu.getText().equals(""))
            {
                txtCodCiu.setText("");
                txtCiu.setText("");
            } 
            else
            {
                mostrarVenConCiu(2);
            }
        } 
        else
        {
            txtCiu.setText(strCiu);
        }
        
        if (txtCiu.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }   
    }//GEN-LAST:event_txtCiuFocusLost

    private void txtCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiuActionPerformed
        txtCiu.transferFocus();
    }//GEN-LAST:event_txtCiuActionPerformed

    private void butCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCiuActionPerformed
        mostrarVenConCiu(0);
    }//GEN-LAST:event_butCiuActionPerformed
    
    private void exitForm() 
    {
        dispose();
    } 
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    //<editor-fold defaultstate="collapsed" desc=" // Variables declaration - do not modify  ">
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCiu;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butVen;
    private javax.swing.JComboBox cboEstReg;
    private javax.swing.JCheckBox chkCli;
    private javax.swing.JCheckBox chkPro;
    private javax.swing.JLabel lblCiu;
    private javax.swing.JLabel lblCodDes;
    private javax.swing.JLabel lblCodHas;
    private javax.swing.JLabel lblEstReg;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCli;
    private javax.swing.JPanel panEst;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCiu;
    private javax.swing.JTextField txtCodCiu;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtNomDes;
    private javax.swing.JTextField txtNomHas;
    private javax.swing.JTextField txtVen;
    // End of variables declaration//GEN-END:variables
  
    //</editor-fold>
    

    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(String strFil)
    {
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();      
                
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += " SELECT a.st_cli, a.st_prv, a.co_cli, a.tx_ide, a.tx_nom as nomCli, a.tx_dir, a.tx_tel, ";
                strSQL += "        a2.tx_desLar as nomCiu, a1.tx_nom as nomVen, a.st_reg, b.FecUltMov ";
                strSQL += " FROM tbm_cli as a ";
                strSQL += " LEFT OUTER JOIN tbm_usr as a1 ON (a.co_ven = a1.co_usr) ";
                strSQL += " LEFT OUTER JOIN tbm_ciu as a2 ON (a.co_ciu=a2.co_ciu) ";
                strSQL += " LEFT OUTER JOIN (  SELECT MAX(fe_doc) as FecUltMov, co_emp, co_cli FROM tbm_cabMovInv GROUP BY  co_emp, co_cli ) as b ";
                strSQL += " ON (a.co_cli=b.co_cli and a.co_emp=b.co_emp) ";
                strSQL += " WHERE a.co_emp = " + objParSis.getCodigoEmpresa() ;
                strSQL += strFil;
                strSQL += " ORDER BY a.tx_nom ";
                
                System.out.println(strSQL);
                rst = stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_EST_CLI, (rst.getString("st_cli").equals("S")?true:false));
                        vecReg.add(INT_TBL_DAT_EST_PRO, (rst.getString("st_prv").equals("S")?true:false));
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_IDE_CLI,rst.getString("tx_ide"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("nomCli"));
                        vecReg.add(INT_TBL_DAT_DIR_CLI,rst.getString("tx_dir"));
                        vecReg.add(INT_TBL_DAT_TEL_CLI,rst.getString("tx_tel"));
                        vecReg.add(INT_TBL_DAT_CIU_CLI,rst.getString("nomCiu"));
                        vecReg.add(INT_TBL_DAT_NOM_VEN,rst.getString("nomVen"));
                        vecReg.add(INT_TBL_DAT_EST_REG,rst.getString("st_reg"));
                        vecReg.add(INT_TBL_DAT_ULT_MOV,rst.getString("FecUltMov"));
                        vecDat.add(vecReg);
                    }
                }
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
              
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    private String sqlConFil() 
    {
        String sqlFil = "";
        //Fecha Ultimo Movimiento.
        if(objSelFec.isCheckBoxChecked() )
        {
            switch (objSelFec.getTipoSeleccion()) 
            {
                case 0: //Búsqueda por rangos
                    sqlFil += " AND b.FecUltMov BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    sqlFil += " AND b.FecUltMov<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    sqlFil += " AND b.FecUltMov>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 3: //Todo.
                    break;
            }
        }
        
        //Obtener la condición.
        if (txtNomDes.getText().length() > 0 || txtNomHas.getText().length() > 0) 
        {
            sqlFil+=" AND ((a.tx_nom BETWEEN '" + txtNomDes.getText().replaceAll("'", "''") + "' AND '" + txtNomHas.getText().replaceAll("'", "''") + "') OR a.tx_nom LIKE '" + txtNomHas.getText().replaceAll("'", "''") + "%')";
        }
        
        if (cboEstReg.getSelectedIndex() > 0) 
        {
            sqlFil += " AND a.st_reg = '" + vecEstReg.get(cboEstReg.getSelectedIndex()) + "'";
        }
        
        if (!(chkCli.isSelected() && chkPro.isSelected())) 
        {
            if (chkCli.isSelected()) {
                sqlFil += " AND a.st_cli = 'S'";
            }
            if (chkPro.isSelected()) {
                sqlFil += " AND a.st_prv = 'S'";
            }
        }

        if (txtCodVen.getText().length() > 0) {
            sqlFil += " AND a.co_ven = " + txtCodVen.getText().replaceAll("'", "''") + "";
        }
        
        if (txtCodCiu.getText().length() > 0) {
            sqlFil += " AND a.co_ciu = " + txtCodCiu.getText().replaceAll("'", "''") + "";
        }

        return sqlFil;
    }
    
    
}

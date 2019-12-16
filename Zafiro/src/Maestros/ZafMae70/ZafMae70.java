/*
 * ZafMae69.java
 * "Locales por programa"
 * 
 */
package Maestros.ZafMae70;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafToolBar.ZafToolBar;
import java.sql.Connection;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.sql.ResultSet;
import java.sql.DriverManager;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;

/**
 * 
 * @author 
 */
public class ZafMae70 extends javax.swing.JInternalFrame 
{
    //Constantes: Jtable "TblDat"
    static final int INT_TBL_DAT_LIN=0;               
    static final int INT_TBL_DAT_BTN_USR=1;               
    static final int INT_TBL_DAT_COD_USR=2;       
    static final int INT_TBL_DAT_USR=3;            
    static final int INT_TBL_DAT_NOM_USR=4;            
    
    
    //ArrayList: para consultar Documentos
    private ArrayList arlDatCon, arlRegCon;
    private static final int INT_ARL_CON_COD_MNU=0;
    private static final int INT_ARL_CON_COD_EMP=1;
    private static final int INT_ARL_CON_COD_LOC=2;
    private static final int INT_ARL_CON_COD_USR=3;
    
    private int intIndiceCon=0;  
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblBus objTblBus;                              //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                              //JTable de ordenamiento.
    private ZafMouMotAda objMouMotAda;                        //ToolTipText en TableHeader.
    private ZafDocLis objDocLis;
    private ZafTblMod objTblMod;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblLeft;                       //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk, objTblCelRenChkAux, objTblCelRenChkPre;    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk, objTblCelEdiChkAux, objTblCelEdiChkPre;    //Editor: JCheckBox en celda.
    
    private ZafTblCelRenBut objTblCelRenBut;//Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoUsr;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoUsr;           //Editor: JButton en celda.
    
    private ZafVenCon vcoPrg;                               //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoUsr;                                   //Ventana de consulta.
    private MiToolBar objTooBar;
    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnHayCam;                               //Determina si hay cambios en el formulario.
    private boolean blnPre;
    
    private String strCodUsr, strNomUsr;                         //Contenido del campo al obtener el foco.
    
    private String strCodPrg, strNomPrg;
    private String strSQL, strAux;
    private String strVer=" v0.01";
    
    /** Crea una nueva instancia de la clase ZafMae69. */
    public ZafMae70(ZafParSis obj)
    {
        try{
            objParSis=(ZafParSis)obj.clone();

            initComponents();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }    

    /** Configurar el formulario */
    private boolean configurarFrm() 
    {
        boolean blnRes = true;
        try {
            //Título de la ventana
            this.setTitle(objParSis.getNombreMenu() + strVer);
            lblTit.setText(objParSis.getNombreMenu());
            
            //Inicializar objetos.
            objUti = new ZafUtil();
            objDocLis=new ZafDocLis();
            objTooBar = new MiToolBar(this);
            objTooBar.setVisibleAnular(false);
            panBar.add(objTooBar);

            //Configurar objetos.
            txtCodPrg.setBackground(objParSis.getColorCamposObligatorios());
            txtNomPrg.setBackground(objParSis.getColorCamposObligatorios());
            txtCodUsr.setBackground(objParSis.getColorCamposObligatorios());
            txtNomUsr.setBackground(objParSis.getColorCamposObligatorios());
            //Configurar ZafVenCon.
            configurarVenConTipPrg();
            configurarUsuarios();
            //Configurar los JTables.
            configurarTblDat();
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
     /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Vendedores".
     */
    private boolean configurarUsuarios()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_usr");
            arlCam.add("a1.tx_usr");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Usuario");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("120");
            arlAncCol.add("374");
             
            strSQL="";
            strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
            strSQL+=" FROM tbm_usr AS a1 ";
            strSQL+=" WHERE a1.st_reg='A'  ";
            strSQL+=" ORDER BY a1.tx_nom";
            //Armar la sentencia SQL.
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
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarUsuarios(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoUsr.setCampoBusqueda(2);
                    vcoUsr.setVisible(true);
                    if (vcoUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtNomUsr.setText(vcoUsr.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoUsr.buscar("a1.co_usr", txtCodUsr.getText()))
                    {
                        txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtNomUsr.setText(vcoUsr.getValueAt(3));
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
                            txtNomUsr.setText(vcoUsr.getValueAt(3));
                        }
                        else
                        {
                            txtCodUsr.setText(strCodUsr);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoUsr.buscar("a1.tx_nom", txtNomUsr.getText()))
                    {
                        txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtNomUsr.setText(vcoUsr.getValueAt(3));
                    }
                    else
                    {
                        vcoUsr.setCampoBusqueda(2);
                        vcoUsr.setCriterio1(11);
                        vcoUsr.cargarDatos();
                        vcoUsr.setVisible(true);
                        if (vcoUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodUsr.setText(vcoUsr.getValueAt(1));
                            txtNomUsr.setText(vcoUsr.getValueAt(3));
                        }
                        else
                        {
                            txtNomUsr.setText(strNomUsr);
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
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat() {
        boolean blnRes = true;
        try {
            blnPre=false;
            //Configurar JTable: Establecer el modelo.
            vecDat= new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_BTN_USR, "...");
            vecCab.add(INT_TBL_DAT_COD_USR,"Cód.Usr.");
            vecCab.add(INT_TBL_DAT_USR,"Usuario");
            vecCab.add(INT_TBL_DAT_NOM_USR,"Nom.Usr.");
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_BTN_USR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_USR).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_USR).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_NOM_USR).setPreferredWidth(150);
           
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BTN_USR).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_BTN_USR);
            vecAux.add("" + INT_TBL_DAT_COD_USR);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_USR).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;
            
            objTblCelRenLblLeft=new ZafTblCelRenLbl();
            objTblCelRenLblLeft.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_USR).setCellRenderer(objTblCelRenLblLeft);
            tcmAux.getColumn(INT_TBL_DAT_NOM_USR).setCellRenderer(objTblCelRenLblLeft);
            objTblCelRenLblLeft = null;
            
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BTN_USR).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            
            int intColVen[]=new int[3];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            int intColTbl[]=new int[3];
            intColTbl[0]=INT_TBL_DAT_COD_USR;
            intColTbl[1]=INT_TBL_DAT_USR;
            intColTbl[2]=INT_TBL_DAT_NOM_USR;
            
            objTblCelEdiTxtVcoUsr=new ZafTblCelEdiTxtVco(tblDat, vcoUsr, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_USR).setCellEditor(objTblCelEdiTxtVcoUsr);
            objTblCelEdiTxtVcoUsr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoUsr.setCampoBusqueda(0);
                    vcoUsr.setCriterio1(11);
                }
                public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoUsr.isConsultaAceptada()){
                        objTblMod.insertRow();
                    }
                }
            });
            
            
            objTblCelEdiButVcoUsr=new ZafTblCelEdiButVco(tblDat, vcoUsr, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BTN_USR).setCellEditor(objTblCelEdiButVcoUsr);
            objTblCelEdiButVcoUsr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoUsr.isConsultaAceptada()){
                        objTblMod.insertRow();
                    }
                }
            });
            
            
            objTblBus = new ZafTblBus(tblDat);
            objTblOrd = new ZafTblOrd(tblDat);
             

            // Libero los objetos auxiliares.
            tcmAux = null;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /* ToolTips
     * 
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_DAT_BTN_USR:
                    strMsg = "Muestra listado de usuarios";
                    break;
                case INT_TBL_DAT_USR:
                    strMsg = "Usuarios";
                    break;
                case INT_TBL_DAT_NOM_USR:
                    strMsg = "Nombre del usuario";
                    break;
                 
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Programas".
     */
    private boolean configurarVenConTipPrg() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_mnu");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Programa");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("494");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_mnu, a1.tx_nom";
            strSQL+=" FROM tbm_mnuSis AS a1";
            strSQL+=" WHERE (a1.tx_tipMnu='C'or a1.tx_tipMnu='R') and a1.tx_nom!=''";
            strSQL+=" AND a1.st_reg<>'E'";
            strSQL+=" ORDER BY a1.tx_nom";
            vcoPrg = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de programas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoPrg.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            vcoPrg.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcion permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bï¿½squeda determina si se debe hacer
     * una busqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se estï¿½ buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opciï¿½n que desea utilizar.
     * @param intTipBus El tipo de busqueda a realizar.
     * @return true: Si no se presenta ningun problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConPrg(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoPrg.setCampoBusqueda(1);
                    vcoPrg.show();
                    if (vcoPrg.getSelectedButton() == vcoPrg.INT_BUT_ACE) {
                        txtCodPrg.setText(vcoPrg.getValueAt(1));
                        txtNomPrg.setText(vcoPrg.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoPrg.buscar("a1.co_mnu", txtCodPrg.getText())) {
                        txtCodPrg.setText(vcoPrg.getValueAt(1));
                        txtNomPrg.setText(vcoPrg.getValueAt(2));
                    } else {
                        vcoPrg.setCampoBusqueda(0);
                        vcoPrg.setCriterio1(11);
                        vcoPrg.cargarDatos();
                        vcoPrg.show();
                        if (vcoPrg.getSelectedButton() == vcoPrg.INT_BUT_ACE) {
                            txtCodPrg.setText(vcoPrg.getValueAt(1));
                            txtNomPrg.setText(vcoPrg.getValueAt(2));
                        } else {
                            txtCodPrg.setText(strCodPrg);
                        }
                    }

                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoPrg.buscar("a1.tx_nom", txtNomPrg.getText())) {
                        txtCodPrg.setText(vcoPrg.getValueAt(1));
                        txtNomPrg.setText(vcoPrg.getValueAt(2));
                    } else {
                        vcoPrg.setCampoBusqueda(1);
                        vcoPrg.setCriterio1(11);
                        vcoPrg.cargarDatos();
                        vcoPrg.show();
                        if (vcoPrg.getSelectedButton() == vcoPrg.INT_BUT_ACE) {
                            txtCodPrg.setText(vcoPrg.getValueAt(1));
                            txtNomPrg.setText(vcoPrg.getValueAt(2));
                        } else {
                            txtNomPrg.setText(strNomPrg);
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrTipCta = new javax.swing.ButtonGroup();
        bgrNatCta = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        panCabDoc = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodPrg = new javax.swing.JTextField();
        txtNomPrg = new javax.swing.JTextField();
        butPrg = new javax.swing.JButton();
        lblVen1 = new javax.swing.JLabel();
        txtCodUsr = new javax.swing.JTextField();
        txtNomUsr = new javax.swing.JTextField();
        butUsr = new javax.swing.JButton();
        panCenDat = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
        setPreferredSize(new java.awt.Dimension(465, 518));
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
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Información del registro actual");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));

        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panDat.setLayout(new java.awt.BorderLayout());

        panCabDoc.setPreferredSize(new java.awt.Dimension(610, 60));
        panCabDoc.setLayout(null);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc.setText("Programa :");
        panCabDoc.add(lblTipDoc);
        lblTipDoc.setBounds(4, 4, 60, 20);

        txtCodPrg.setMaximumSize(null);
        txtCodPrg.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCodPrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPrgActionPerformed(evt);
            }
        });
        txtCodPrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPrgFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPrgFocusLost(evt);
            }
        });
        panCabDoc.add(txtCodPrg);
        txtCodPrg.setBounds(64, 4, 70, 20);

        txtNomPrg.setMaximumSize(null);
        txtNomPrg.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNomPrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomPrgActionPerformed(evt);
            }
        });
        txtNomPrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomPrgFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomPrgFocusLost(evt);
            }
        });
        panCabDoc.add(txtNomPrg);
        txtNomPrg.setBounds(134, 4, 510, 20);

        butPrg.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butPrg.setText("...");
        butPrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrgActionPerformed(evt);
            }
        });
        panCabDoc.add(butPrg);
        butPrg.setBounds(644, 4, 20, 20);

        lblVen1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblVen1.setText(" Usuario:");
        lblVen1.setToolTipText("Vendedor/Comprador");
        panCabDoc.add(lblVen1);
        lblVen1.setBounds(4, 25, 60, 20);

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
        panCabDoc.add(txtCodUsr);
        txtCodUsr.setBounds(64, 25, 70, 20);

        txtNomUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomUsrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomUsrFocusLost(evt);
            }
        });
        txtNomUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomUsrActionPerformed(evt);
            }
        });
        panCabDoc.add(txtNomUsr);
        txtNomUsr.setBounds(134, 25, 510, 20);

        butUsr.setText("...");
        butUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butUsrActionPerformed(evt);
            }
        });
        panCabDoc.add(butUsr);
        butUsr.setBounds(644, 25, 20, 20);

        panDat.add(panCabDoc, java.awt.BorderLayout.NORTH);

        panCenDat.setAutoscrolls(true);
        panCenDat.setMinimumSize(new java.awt.Dimension(24, 24));
        panCenDat.setPreferredSize(new java.awt.Dimension(454, 404));
        panCenDat.setLayout(new java.awt.BorderLayout());

        spnDat.setBorder(null);
        spnDat.setPreferredSize(new java.awt.Dimension(454, 404));

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
        ));
        spnDat.setViewportView(tblDat);

        panCenDat.add(spnDat, java.awt.BorderLayout.CENTER);

        panDat.add(panCenDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panDat);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents


    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try {
            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
                dispose();
            }
        } 
        catch (Exception e) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

private void butPrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrgActionPerformed
    if (objTooBar.getEstado() == 'n') {
        strCodPrg = txtCodPrg.getText();
        mostrarVenConPrg(0);
        if (!txtCodPrg.getText().equals("")) {
            //Cargar los tipos de documentos sólo si ha cambiado el programa.
            if (!txtCodPrg.getText().equalsIgnoreCase(strCodPrg)) {
                cargarDetReg();
            }
        }
    } else {
        strCodPrg = txtCodPrg.getText();
        mostrarVenConPrg(0);
        if (!txtCodPrg.getText().equals("")) {
        }
    }
}//GEN-LAST:event_butPrgActionPerformed

private void txtNomPrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrgFocusLost
    if (!txtNomPrg.getText().equalsIgnoreCase(strNomPrg)) {
        if (txtNomPrg.getText().equals("")) {
            txtCodPrg.setText("");
            txtNomPrg.setText("");
            objTblMod.removeAllRows();
        } else {
            mostrarVenConPrg(2);
            if (txtCodPrg.getText().equals("")) {
                objTblMod.removeAllRows();
            }
        }
    } else {
        txtNomPrg.setText(strNomPrg);
    }
}//GEN-LAST:event_txtNomPrgFocusLost

private void txtNomPrgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrgFocusGained
    strNomPrg = txtNomPrg.getText();
    txtNomPrg.selectAll();
}//GEN-LAST:event_txtNomPrgFocusGained

private void txtNomPrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomPrgActionPerformed
    txtNomPrg.transferFocus();
}//GEN-LAST:event_txtNomPrgActionPerformed

private void txtCodPrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrgFocusLost
    if (!txtCodPrg.getText().equalsIgnoreCase(strCodPrg)) {
        if (txtCodPrg.getText().equals("")) {
            txtCodPrg.setText("");
            txtNomPrg.setText("");
            objTblMod.removeAllRows();
        } else {
            mostrarVenConPrg(1);
            if (txtCodPrg.getText().equals("")) {
                objTblMod.removeAllRows();
            }
        }
    } else {
        txtCodPrg.setText(strCodPrg);
    }
}//GEN-LAST:event_txtCodPrgFocusLost

private void txtCodPrgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrgFocusGained
    strCodPrg = txtCodPrg.getText();
    txtCodPrg.selectAll();
}//GEN-LAST:event_txtCodPrgFocusGained

private void txtCodPrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrgActionPerformed
    txtCodPrg.transferFocus();
}//GEN-LAST:event_txtCodPrgActionPerformed

    private void txtCodUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodUsrFocusGained
        strCodUsr=txtCodUsr.getText();
        txtCodUsr.selectAll();
        
    }//GEN-LAST:event_txtCodUsrFocusGained

    private void txtCodUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodUsrFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodUsr.getText().equalsIgnoreCase(strCodUsr))
        {
            if (txtCodUsr.getText().equals(""))
            {
                txtCodUsr.setText("");
                txtNomUsr.setText("");
            }
            else
            {
                mostrarUsuarios(1);
            }
        }
        else
        txtCodUsr.setText(strCodUsr);
        //Seleccionar el JRadioButton de filtro si es necesario.

    }//GEN-LAST:event_txtCodUsrFocusLost

    private void txtCodUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodUsrActionPerformed
        txtCodUsr.transferFocus();
    }//GEN-LAST:event_txtCodUsrActionPerformed

    private void txtNomUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomUsrFocusGained
        strNomUsr=txtNomUsr.getText();
        txtNomUsr.selectAll();
        
    }//GEN-LAST:event_txtNomUsrFocusGained

    private void txtNomUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomUsrFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomUsr.getText().equalsIgnoreCase(strNomUsr))
        {
            if (txtNomUsr.getText().equals(""))
            {
                txtCodUsr.setText("");
                txtNomUsr.setText("");
            }
            else
            {
                mostrarUsuarios(2);
            }
        }
        else
        txtNomUsr.setText(strNomUsr);
        //Seleccionar el JRadioButton de filtro si es necesario.

    }//GEN-LAST:event_txtNomUsrFocusLost

    private void txtNomUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomUsrActionPerformed
        txtNomUsr.transferFocus();
    }//GEN-LAST:event_txtNomUsrActionPerformed

    private void butUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butUsrActionPerformed
        mostrarUsuarios(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
       
    }//GEN-LAST:event_butUsrActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() {
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrNatCta;
    private javax.swing.ButtonGroup bgrTipCta;
    private javax.swing.JButton butPrg;
    private javax.swing.JButton butUsr;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen1;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCabDoc;
    private javax.swing.JPanel panCenDat;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panFrm;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodPrg;
    private javax.swing.JTextField txtCodUsr;
    private javax.swing.JTextField txtNomPrg;
    private javax.swing.JTextField txtNomUsr;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podráa utilizar
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
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes = true;
        try {
            txtCodPrg.setText("");
            txtNomPrg.setText("");
            txtCodUsr.setText("");
            txtNomUsr.setText("");
            objTblMod.removeAllRows();
        } 
        catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

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
        txtCodPrg.getDocument().addDocumentListener(objDocLis);
        txtNomPrg.getDocument().addDocumentListener(objDocLis);
    }
    
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro() {

        boolean blnRes = true;
        strAux = "¿Desea guardar los cambios efectuados a este registro?\n";
        strAux += "Si no guarda los cambios perdera toda la informacion que no haya guardado.";
        switch (mostrarMsgCon(strAux)) {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado()) {
                    case 'n': //Insertar
                        blnRes = objTooBar.beforeInsertar();
                        if(blnRes)
                            blnRes = objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes = objTooBar.beforeModificar();
                        if(blnRes)
                            blnRes = objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam = false;
                blnRes = true;
                break;
            case 2: //CANCEL_OPTION
                blnRes = false;
                break;
        }
        return blnRes;
    }

    /**
     *Esta función valida si los campos obligatorios han sido completados correctamente 
     *@return True si los campos obligatorios se llenaron correctamente.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() {
        if(objTooBar.getEstado()=='n'){
            if(txtCodPrg.getText().length()>0 && txtCodUsr.getText().length()>0){
                if(validaExiTipDoc()){
                    tabFrm.setSelectedIndex(0);//ubica el tab correspondiente
                    mostrarMsgInf("<HTML>El programa <FONT COLOR=\"blue\">" + txtNomPrg.getText() + "</FONT> ya fue ingresado.<BR>Seleccione un programa aún no ingresado y vuelva a intentarlo.</HTML>");
                    txtCodPrg.requestFocus();
                    return false;
                }
            }
        }

       
        if (txtCodPrg.getText().equals("")) {
            tabFrm.setSelectedIndex(0);//ubica el tab correspondiente
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Escriba un número de tipo de documento y vuelva a intentarlo.</HTML>");
            txtCodPrg.requestFocus();
            return false;
        }
        
         if (txtCodUsr.getText().equals("")) {
            tabFrm.setSelectedIndex(0);//ubica el tab correspondiente
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Código del usuario</FONT> es obligatorio.<BR>Escriba un código y vuelva a intentarlo.</HTML>");
            txtCodUsr.requestFocus();
            return false;
        }
         

        if (txtNomPrg.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML> El campo <FONT COLOR=\"blue\">Nombre del documento</FONT> es obligatorio.<BR>Escriba un nombre de documento para la cuenta y vuelva a intentarlo.</HTML>");
            txtNomPrg.requestFocus();
            return false;
        }
        return true;
    }    

    /**
     * Esta función se encarga de validar que no exista un tipo de documento ingresado
     * @return true: si existe tipo de documento ingresado
     * <BR> false: En el caso contrario.
     */
    private boolean validaExiTipDoc(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_mnu FROM tbr_usrLocPrgUsr AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_mnu=" + txtCodPrg.getText() + "";
                strSQL+=" AND a1.co_usr=" + txtCodUsr.getText() + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    blnRes=true;
                }
                con.close();
                con=null;
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }
 
    
    
    public class MiToolBar extends ZafToolBar {
        public MiToolBar(javax.swing.JInternalFrame jfrThis) {
            super(jfrThis, objParSis);
        }

        public void clickInicio() {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if (blnHayCam || objTblMod.isDataModelChanged() ) {
                            if (isRegPro()) {
                                intIndiceCon=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=0;
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
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if ( blnHayCam || objTblMod.isDataModelChanged() ){
                            if (isRegPro()) {
                                intIndiceCon--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon--;
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
                if(arlDatCon.size()>0){
                    if(intIndiceCon < arlDatCon.size()-1){
                        if (blnHayCam || objTblMod.isDataModelChanged() ){
                            if (isRegPro()) {
                                intIndiceCon++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon++;
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
                if(arlDatCon.size()>0){
                    if(intIndiceCon<arlDatCon.size()-1){
                        if (blnHayCam || objTblMod.isDataModelChanged() ) {
                            if (isRegPro()) {
                                intIndiceCon=arlDatCon.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=arlDatCon.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }         
        
        public void clickConsultar() {
            switch (objTooBar.getEstado()) {
                case 'c':
                case 'x':
                case 'y':
                case 'z':
                    txtCodPrg.requestFocus();
                    break;
                case 'j':
                    cargarDetReg();
                    break;
            }
            blnHayCam = false;
        }
        
        public void clickInsertar() {
            try {
                if (blnHayCam || objTblMod.isDataModelChanged()) {
                    isRegPro();
                }
                limpiarFrm();
//                cargarDetReg();
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                txtCodPrg.requestFocus();
                //Inicializar las variables que indican cambios.
                objTblMod.setDataModelChanged(false);
                blnHayCam = false;
                blnPre=false;
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickModificar() {
            cargarReg();
            txtCodPrg.setEditable(false);
            txtNomPrg.setEditable(false);
            butPrg.setEnabled(false);
            // objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            // cargarDetReg();

            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
            blnHayCam = false;
        } 
        
        public void clickEliminar() {
            cargarDetReg();
        }    
        
        public void clickAnular() {
            cargarDetReg();
        }  
        
        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickAceptar() {
        }

        public void clickCancelar() {
        }        

        public boolean consultar() {
            consultarReg();
            return true;
        }

        public boolean insertar() {
            if(!validacionUsuariosRepetidos()){
                if (!insertarReg()) {
                    return false;
                }
            }
            else{
                mostrarMsgInf("Existe un Usuario repetido favor revise los datos y vuelva a intentarlo.");
                return false;
            }
            
            return true;
        }

        public boolean modificar() {
            if(!validacionUsuariosRepetidos()){
                if (!actualizarReg()) {
                    return false;
                }
            }
            else{
                mostrarMsgInf("Existe un Usuario repetido favor revise los datos y vuelva a intentarlo.");
                return false;
            }
            
            return true;
        }

        public boolean eliminar() {
            try{
                if (!eliminarReg())
                    return false;
                
                //Desplazarse al siguiente registro si es posible.
                if(intIndiceCon<arlDatCon.size()-1){
                    intIndiceCon++;
                    cargarReg();
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }                  
                blnHayCam=false;
                blnPre=false;
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
                return true;
            }
            return true;
        }

        public boolean anular() {
            return true;
        }

        public boolean vistaPreliminar() {
            return true;
        }

        public boolean imprimir() {
            return true;
        }

        public boolean aceptar() {
            return true;
        }
        
        public boolean cancelar() {
            boolean blnRes=true;
            try{
            if(objTblMod.isDataModelChanged())
                blnHayCam=true;
                if (blnHayCam){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            objTblMod.initRowsState();
            return blnRes;
        }        

        public boolean afterConsultar() {
            return true;
        }
        
        public boolean afterInsertar() {
            blnHayCam = false;
            objTooBar.setEstado('w');
            consultarReg();
            return true;
        }

        public boolean afterModificar() {
            blnHayCam = false;
            objTooBar.setEstado('w');
            cargarReg();
            return true;
        }

        public boolean afterEliminar() {
            blnHayCam = false;
            return true;
        }
        
        public boolean afterAnular() {
            return true;
        }     
        
        public boolean afterVistaPreliminar() {
            return true;
        }
        
        public boolean afterImprimir() {
            return true;
        }     

        public boolean afterAceptar() {
            return true;
        }
        
        public boolean afterCancelar() {
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }        

        public boolean beforeInsertar() {
            if (!isCamVal()) {
                return false;
            }
            return true;
        }

        public boolean beforeModificar() {
            strAux = objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")) {
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }

            if (!isCamVal()) {
                return false;
            }
            return true;
        }

        public boolean beforeEliminar() {
            if (!isCamVal()) {
                return false;
            }
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
    }    
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg()
    {
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null) {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL= "";
                strSQL+=" SELECT a2.co_emp, a2.co_loc, a1.co_mnu, a1.tx_nom,a2.co_usr \n";
                strSQL+=" FROM tbm_mnuSis AS a1  \n";
                strSQL+=" INNER JOIN tbr_usrLocPrgUsr AS a2 ON (a1.co_mnu=a2.co_mnu) \n";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                if( ! txtCodPrg.getText().equals(""))
                    strSQL+=" AND a1.co_mnu=" + txtCodPrg.getText() ;
                if( ! txtCodUsr.getText().equals(""))
                    strSQL+=" AND a2.co_usr=" + txtCodUsr.getText() ;
                
                strSQL+=" GROUP BY a2.co_emp, a2.co_loc, a1.co_mnu, a1.tx_nom, a2.co_usr  \n";
                strSQL+=" ORDER by a1.co_mnu, a2.co_usr  \n";
                rst=stm.executeQuery(strSQL);
                arlDatCon = new ArrayList();
                while(rst.next())
                {
//                    txtCodPrg.setText(rst.getString("co_mnu"));
//                    txtNomPrg.setText(rst.getString("tx_nom"));
                    arlRegCon = new ArrayList();
                    arlRegCon.add(INT_ARL_CON_COD_MNU, rst.getInt("co_mnu"));
                    arlRegCon.add(INT_ARL_CON_COD_EMP, rst.getInt("co_emp"));
                    arlRegCon.add(INT_ARL_CON_COD_LOC, rst.getInt("co_loc"));
                    arlRegCon.add(INT_ARL_CON_COD_USR, rst.getInt("co_usr"));
                    arlDatCon.add(arlRegCon);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
                
                if(arlDatCon.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatCon.size()) + " registros");
                    intIndiceCon=arlDatCon.size()-1;
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
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg() {
        boolean blnRes=false;
        try {
            if (cargarDetReg()) {
                blnRes=true;
            }
            blnHayCam = false;
        } 
        catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }   
     

    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg() {
        boolean blnRes = true;
        int intPosRel;
        try{
            objTblMod.removeAllRows();
            //System.out.println("");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null) {
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a3.co_usr, a3.tx_usr, a3.tx_nom, a1.co_mnu, a2.co_usr as co_usrRel, a2.tx_usr as tx_usrRel, a2.tx_nom as tx_nomRel, \n";
                strSQL+="        a4.co_mnu, a4.tx_nom as tx_nomPrg \n";
                strSQL+=" FROM tbr_usrLocPrgUsr as a1 \n";
                strSQL+=" INNER JOIN tbm_usr as a2 ON (a1.co_usrRel=a2.co_usr) \n";
                strSQL+=" INNER JOIN tbm_usr as a3 ON (a1.co_usr=a3.co_usr) \n";
                strSQL+=" INNER JOIN tbm_mnuSis as a4 ON (a1.co_mnu=a4.co_mnu) \n";
                strSQL+=" WHERE a1.co_emp="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)+" AND \n";
                strSQL+="       a1.co_loc="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)+" AND \n";
                strSQL+="       a1.co_mnu="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_MNU)+" AND \n";
                strSQL+="       a1.co_usr="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_USR)+" \n";
                strSQL+=" ORDER BY a2.tx_nom \n";
                System.out.println("cargarDetReg: " + strSQL);
                rst = stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                objTooBar.setMenSis("Cargando datos...");
                while (rst.next()) {
                    vecReg = new Vector();
                    txtCodPrg.setText(rst.getString("co_mnu"));
                    txtNomPrg.setText(rst.getString("tx_nomPrg"));
                    
                    txtCodUsr.setText(rst.getString("co_usr"));
                    txtNomUsr.setText(rst.getString("tx_nom"));
                    
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_BTN_USR,"" );
                    vecReg.add(INT_TBL_DAT_COD_USR,"" + rst.getObject("co_usrRel")==null?"":rst.getString("co_usrRel"));
                    vecReg.add(INT_TBL_DAT_USR,"" + rst.getObject("tx_usrRel")==null?"":rst.getString("tx_usrRel"));
                    vecReg.add(INT_TBL_DAT_NOM_USR,"" + rst.getObject("tx_nomRel")==null?"":rst.getString("tx_nomRel"));
                    vecDat.add(vecReg);
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
                objTooBar.setMenSis("Listo");
                objTblMod.initRowsState();
                //Mostrar la posición relativa del registro.
                intPosRel = intIndiceCon+1;
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatCon.size()) );   
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg() {
        boolean blnRes = false;
        try {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if(con!=null){
                if(eliminarDet()){
                    if(insertarDet()){
                        con.commit();
                        blnRes=true;
                    }
                    else{
                        con.rollback();
                    }
                }
                else{
                    con.rollback();
                }
                con.close();
                con = null;
            }
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDet() {
        boolean blnRes=true;
        String strUpd="";
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    strSQL="";
                    strSQL+=" INSERT INTO tbr_usrLocPrgUsr(co_emp, co_loc, co_mnu, co_usr, co_usrRel, st_reg) ";                 
                    strSQL+=" VALUES (";
                    strSQL+=" " + objParSis.getCodigoEmpresa() + ",";
                    strSQL+=" " + objParSis.getCodigoLocal() + ",";
                    strSQL+=" " + txtCodPrg.getText() + ", ";//co_mnu
                    strSQL+=" " + txtCodUsr.getText() + ",";//co_usr
                    strSQL+=" " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_USR) + ",";//co_usrRel
                    strSQL+="'A'";//st_reg
                    strSQL+=" );";
                    strUpd+=strSQL;
                }
                System.out.println("insertarDet: " + strUpd);
                stm.executeUpdate(strUpd);
                stm.close();
                stm = null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg() {
        boolean blnRes = false;
        try {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con != null) {
                if(eliminarDet()){
                    if (insertarDet()) {
                        con.commit();
                        blnRes = true;
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con = null;
        } 
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función elimina el registro de la base de datos.
     * @return true: Si se pudo eliminar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg() {
        boolean blnRes = false;
        try {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con != null) {
                if (eliminarDet()) {
                    con.commit();
                    blnRes = true;
                } else {
                    con.rollback();
                }
            }
            con.close();
            con = null;
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite eliminar el detalle de un registro.
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarDet() {
        boolean blnRes = true;
        String strUpd="";
        try {
            if (con!=null) {
                stm=con.createStatement();
                //Armar la sentencia SQL. 
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    strSQL="";
                    strSQL+=" DELETE FROM tbr_usrLocPrgUsr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa()+"";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal()+"";
                    strSQL+=" AND co_mnu=" + txtCodPrg.getText();
                    strSQL+=" AND co_usr=" + txtCodUsr.getText() + "";
                    strSQL+=";";
                    strUpd+=strSQL;
                }    
                stm.executeUpdate(strUpd);
                stm.close();
                stm = null;
            }
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
     * Revisa que no exista el mismo usuario 2 veces en la configuracion
     * @return 
     */

    private boolean validacionUsuariosRepetidos(){
        boolean blnRes=false;
        try{
            for(int i=0;i<objTblMod.getRowCountTrue();i++){
                for(int j=(i+1);j<objTblMod.getRowCountTrue();j++){
                    if(objTblMod.getValueAt(i, INT_TBL_DAT_COD_USR).toString().equals(objTblMod.getValueAt(j, INT_TBL_DAT_COD_USR).toString())){
                        blnRes=true;
                    }
                }
            }
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



}




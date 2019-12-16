/*
 * ZafMae49.java
 *
 * Created on 20 de Junio de 2017, 11:49  
 */
package Maestros.ZafMae49;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import Librerias.ZafVenCon.ZafVenCon; 
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author  Rosa Zambrano
 */
public class ZafMae49 extends javax.swing.JInternalFrame 
{
    //Constantes: Jtable
    private static final int INT_TBL_DAT_LIN=0; 
    private static final int INT_TBL_DAT_VIS =1;
    private static final int INT_TBL_DAT_VAL =2;
    private static final int INT_TBL_DAT_CDI_AUT=3;             //Columna dinámica: Autorización.
    
    //Constantes para manejar Columnas Dinámicas.
    private static final int INT_TBL_DAT_NUM_TOT_CES=3;         //Número total de columnas estáticas.
    private static final int INT_TBL_DAT_NUM_TOT_CDI=2;         //Número de columnas dinámicas por usuario.
    private static final int INT_TBL_DAT_FAC_NUM_CDI=1;         //Factor para calcular total de columnas dinámicas. 
    
    //Arreglos: Datos Tabla
    private ArrayList arlDat, arlReg;
    
    //Arreglos: Datos Usuario
    private ArrayList arlDatUsr, arlRegUsr;
    private static final int INT_ARL_DAT_USR_COD_USR=0;
    private static final int INT_ARL_DAT_USR_NOM_USR=1;
    
    //Arreglos: Datos Configuración Vistos Buenos.
    private ArrayList arlDatCfg, arlRegCfg;
    private static final int INT_ARL_CFG_COD_EMP=0;
    private static final int INT_ARL_CFG_COD_LOC=1;
    private static final int INT_ARL_CFG_COD_TIP_DOC=2;
    private int intIndiceCfg=0;
    
    //Variables
    private ZafTblMod objTblMod;
    private ZafParSis objParSis;
    private MiToolBar objTooBar;
    private ZafUtil objUti;
    private ZafVenCon vcoTipDoc; 
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblNum;//Render: Presentar JLabel en JTable.
    private ZafTblFilCab objTblFilCab;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafPerUsr objPerUsr;
    private ZafDocLis objDocLis;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private java.awt.Color colFonCol;
    private Vector vecCab, vecAux;
    
    boolean blnHayCam=false;
    boolean blnModVisBue=false;  //Permita modificar spin de Vistos Buenos.
    
    private int intNumColEst;    //Número de Columnas Estaticas
    private int intColIni, intColFin;    
    private int intNumColGrpUsrAdi=0;
    private int IntValIni=0;
    
    private String strSQL="", strAux="";
    private String strDesCorTipDoc="",strDesLarTipDoc="";
    
    private String strVersion=" v0.1";
    
    javax.swing.JTextField txtCodTipDoc = new javax.swing.JTextField();

    /** Crea una nueva instancia de la clase ZafMae49. */
    public ZafMae49(ZafParSis obj)
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
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
            //Titulo Programa.                        
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
                 
            //ToolBar
            objTooBar=new MiToolBar(this);
            objTooBar.setVisibleInsertar(true);
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleEliminar(false);
            panBar.add(objTooBar);
            
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(1842))
            {
                objTooBar.setVisibleConsultar(false);
            }
            if (!objPerUsr.isOpcionEnabled(1843))
            {
                objTooBar.setVisibleInsertar(false);   
                objTooBar.setVisibleModificar(false);        
            }
            if (!objPerUsr.isOpcionEnabled(1844))
            {
                objTooBar.setVisibleImprimir(false); 
            }
            if (!objPerUsr.isOpcionEnabled(1845))
            {
                objTooBar.setVisibleVistaPreliminar(false); 
            }
            
            //Configurar objetos.
            txtCodTipDoc.setVisible(false);   
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
                        
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
                   
            //Configurar los JTables.
            configurarTblDat();
            
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
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecCab = new Vector(INT_TBL_DAT_NUM_TOT_CES);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_VIS,"Vistos");
            vecCab.add(INT_TBL_DAT_VAL,"Val.Aut.");
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_VIS).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_VAL).setPreferredWidth(80);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_LIN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_VIS).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_VAL).setResizable(false);

            //Configurar JTable: Ocultar columnas del sistema.
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_VIS, tblDat);
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_VAL);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_VIS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Numeros
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL).setCellRenderer(objTblCelRenLblNum);
            objTblCelRenLblNum=null;
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL, objTblMod.INT_COL_DBL, new Integer(0), null);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            //objTblOrd=new ZafTblOrd(tblDat);  
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);    
	    tipoEdicionJTable(true);
            
            //Edición de la celda Valor a Autorizar
            objTblCelEdiTxt = new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_VAL).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

            //Libero los objetos auxiliares.
            tcmAux=null;
            
            intNumColEst=objTblMod.getColumnCount();
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intColSel=-1;
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            if (intCol>INT_TBL_DAT_NUM_TOT_CES){
                intCol=(intCol-INT_TBL_DAT_NUM_TOT_CES)%INT_TBL_DAT_FAC_NUM_CDI+INT_TBL_DAT_NUM_TOT_CES;
            }
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_VIS:
                    strMsg="Vistos Buenos";
                    break;
                case INT_TBL_DAT_VAL:
                    strMsg="Valor Aprobado";
                    break;
                case INT_TBL_DAT_CDI_AUT:
                    intColSel=tblDat.columnAtPoint(evt.getPoint());

                    if(intColSel%INT_TBL_DAT_NUM_TOT_CDI!=0)  
                    {                    
                        strMsg="Aprobación";
                    }
                    else 
                    {
                         strMsg="Indica si es necesario Visto Bueno Previo para aprobar";
                    } 
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }  
   
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc() 
    {
        boolean blnRes = true;
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_numVisBue");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Num.Vis.Bue.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("20");
            
            //Armar la sentencia SQL.
            strSQL = "";
            strSQL+= getSQLTipoDocumento(false);
            //Ocultar columnas.
            //int intColOcu[] = new int[1];
            //intColOcu[0] = 4;
            vcoTipDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //intColOcu = null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bósqueda determina si se debe hacer
     * una bósqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se estó buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de bósqueda a realizar.
     * @return true: Si no se presentó ningón problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus) 
    {
        boolean blnRes = true;
        String strVisBue="";
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) 
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strVisBue=vcoTipDoc.getValueAt(4).equals("")?"0":vcoTipDoc.getValueAt(4).toString();
                        spnVisBue.setValue(Integer.valueOf(strVisBue));
                        if(objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                        {
                            blnModVisBue=false;
                            limpiarDetFrm();
                            cargarDetReg();
                            blnModVisBue=true;
                        }
                        else
                        {
                            limpiarDetFrm();
                        }
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText())) 
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strVisBue=vcoTipDoc.getValueAt(4).equals("")?"0":vcoTipDoc.getValueAt(4).toString();
                        spnVisBue.setValue(Integer.valueOf(strVisBue));
                        if(objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                        {
                            blnModVisBue=false;
                            limpiarDetFrm();
                            cargarDetReg();
                            blnModVisBue=true;
                        }
                        else
                        {
                            limpiarDetFrm();
                        }
                    }
                    else 
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) 
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strVisBue=vcoTipDoc.getValueAt(4).equals("")?"0":vcoTipDoc.getValueAt(4).toString();
                            spnVisBue.setValue(Integer.valueOf(strVisBue));
                            if(objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                            {
                                blnModVisBue=false;
                                limpiarDetFrm();
                                cargarDetReg();
                                blnModVisBue=true;
                            }
                            else
                            {
                                limpiarDetFrm();
                            }
                        } 
                        else 
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }

                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strVisBue=vcoTipDoc.getValueAt(4).equals("")?"0":vcoTipDoc.getValueAt(4).toString();
                        spnVisBue.setValue(Integer.valueOf(strVisBue));
                        if(objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                        {
                            blnModVisBue=false;
                            limpiarDetFrm();
                            cargarDetReg();
                            blnModVisBue=true;
                        }
                        else
                        {
                            limpiarDetFrm();
                        }
                    } 
                    else 
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strVisBue=vcoTipDoc.getValueAt(4).equals("")?"0":vcoTipDoc.getValueAt(4).toString();
                            spnVisBue.setValue(Integer.valueOf(strVisBue));
                            if(objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                            {
                                blnModVisBue=false;
                                limpiarDetFrm();
                                cargarDetReg();
                                blnModVisBue=true;
                            }
                            else
                            {
                                limpiarDetFrm();
                            }
                        } 
                        else 
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
            }
        }
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
        panGen = new javax.swing.JPanel();
        panTabGenCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblVisBue = new javax.swing.JLabel();
        spnVisBue = new javax.swing.JSpinner();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBar = new javax.swing.JPanel();

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
                formInternalFrameClosing(evt);
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
        lblTit.setPreferredSize(new java.awt.Dimension(138, 18));
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panTabGenCab.setPreferredSize(new java.awt.Dimension(0, 64));
        panTabGenCab.setLayout(null);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc.setText("Tipo de Documento:");
        panTabGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(10, 10, 120, 20);

        txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        panTabGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(130, 10, 90, 20);

        txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        panTabGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(220, 10, 240, 20);

        butTipDoc.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panTabGenCab.add(butTipDoc);
        butTipDoc.setBounds(460, 10, 20, 20);

        lblVisBue.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblVisBue.setText("Vistos Bueno:");
        panTabGenCab.add(lblVisBue);
        lblVisBue.setBounds(10, 30, 84, 14);

        spnVisBue.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnVisBueStateChanged(evt);
            }
        });
        panTabGenCab.add(spnVisBue);
        spnVisBue.setBounds(130, 30, 90, 20);

        panGen.add(panTabGenCab, java.awt.BorderLayout.NORTH);

        panGenDet.setLayout(new java.awt.BorderLayout());

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

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) 
        {
            System.gc();
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
	configurarVenConTipDoc();
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
	 txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) 
        {
            if (txtDesLarTipDoc.getText().equals("")) 
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } 
            else 
            {
                configurarVenConTipDoc();
                mostrarVenConTipDoc(2);
            }
        } 
        else 
            txtDesLarTipDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
	strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) 
        {
            if (txtDesCorTipDoc.getText().equals("")) 
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } 
            else
            {
                configurarVenConTipDoc();
                mostrarVenConTipDoc(1);
            }
        }
        else 
        {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc = txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
	 txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed
    
    private void spnVisBueStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnVisBueStateChanged
        if( (objTooBar.getEstado()=='n' )  || (objTooBar.getEstado()=='m' )) //Modo Insertar y Modificar
        {
            if(blnModVisBue)
            {
                setVisBue();
            }
        }
    }//GEN-LAST:event_spnVisBueStateChanged
      
     /** Cerrar la aplicación. */
    private void exitForm() {
        dispose();
    }  
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVisBue;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panTabGenCab;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JSpinner spnVisBue;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podrá utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
        
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     * @param blnMosBotCan Un valor booleano que deteremina si se debe mostrar el botón "Cancelar".
     * @return La opción que seleccionó el usuario.
     */
    private int mostrarMsgCon(String strMsg, boolean blnMosBotCan)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, (blnMosBotCan==true?javax.swing.JOptionPane.YES_NO_CANCEL_OPTION:javax.swing.JOptionPane.YES_NO_OPTION), javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }
        
    public void abrirCon()
    {
        try
        {
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
        }
        catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
    }
    
     public void cerrarCon()
     {
        try
        {
            con.close();
            con=null;
        }
        catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
     }
        
     public void tipoEdicionJTable(boolean editable) 
     {
         if (editable==true){
             objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
         }else{
             objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
         }
     }
     
     public void setEditable(boolean blnEditable)
     {
        try
        {            
            txtDesCorTipDoc.setEditable(blnEditable);
            txtDesLarTipDoc.setEditable(blnEditable);
            butTipDoc.setEnabled(blnEditable);
        }
        catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e); }
     }
    
    /**
     * Función que generqa las filas de vistos buenos de acuerdo a lo indicado en el spnVisBue
     */ 
    public void setVisBue() 
    {
        String strVis = spnVisBue.getModel().getValue().toString();
        int intVis=Integer.parseInt(strVis.equals("")?"0":strVis);
        int intRows=objTblMod.getRowCount();
        
        if(intVis>0)
        {
            //Valida que los Vis.Bue. sea mayor al indice de filas.
            if(intVis>IntValIni)
            {
                if(intRows>=0)
                {
                    for(int i=0; i<(intVis-intRows); i++)
                    {
                        objTblMod.insertRow();
                        objTblMod.setValueAt( "", (objTblMod.getRowCount()-1) , INT_TBL_DAT_LIN );
                        objTblMod.setValueAt( "Visto Bueno "+(objTblMod.getRowCount()) , (objTblMod.getRowCount()-1) , INT_TBL_DAT_VIS );
                        objTblMod.setValueAt( "0", (objTblMod.getRowCount()-1) , INT_TBL_DAT_VAL );
                        //Columnas Dinámicas (Autorización y Visto Bueno Previo)
                        for(int j=(INT_TBL_DAT_CDI_AUT); j< objTblMod.getColumnCount(); j=j+INT_TBL_DAT_NUM_TOT_CDI)
                        {
                            objTblMod.setValueAt(false , (objTblMod.getRowCount()-1) , j );
                            objTblMod.setValueAt(false , (objTblMod.getRowCount()-1) , (j+1) );
                        }
                    }
                }
            }
            else if(intVis<IntValIni)
            {
                 arlDat =new ArrayList();
                //Guardar Datos
                for(int i = 0; i < objTblMod.getRowCount(); i++) 
                {
                    arlReg= new ArrayList();
                    arlReg.add(INT_TBL_DAT_LIN,"");
                    arlReg.add(INT_TBL_DAT_VIS, objTblMod.getValueAt(i, INT_TBL_DAT_VIS));
                    arlReg.add(INT_TBL_DAT_VAL, objTblMod.getValueAt(i, INT_TBL_DAT_VAL));
                    //Columnas Dinámicas
                    for(int j=(INT_TBL_DAT_CDI_AUT); j< objTblMod.getColumnCount(); j++)
                    {
                        if(objTblMod.getValueAt(i, j)!=null) {
                            arlReg.add(j, objTblMod.getValueAt(i, j));
                        }
                        else {
                            arlReg.add(j, false);
                        }
                    }
                    arlDat.add(arlReg);
                }
                //Borrar Datos
                objTblMod.removeAllRows();
                //Insertar Filas que se guardaron anteriormente.
                for (int i = 0; i < intVis; i++) 
                {
                    objTblMod.insertRow();
                    objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_TBL_DAT_LIN) , (i) , INT_TBL_DAT_LIN );
                    objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_TBL_DAT_VIS) , (i) , INT_TBL_DAT_VIS );
                    objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_TBL_DAT_VAL) , (i) , INT_TBL_DAT_VAL );
                    //Columnas Dinámicas
                    for(int j=(INT_TBL_DAT_CDI_AUT); j< objTblMod.getColumnCount(); j++)
                    {
                        objTblMod.setChecked((objUti.getObjectValueAt(arlDat, i, j).equals(true) ? true:false), i, j);
                    }
                }
            }
            IntValIni=intVis;
        }
        else
        {
            spnVisBue.setValue(new Integer(0) );
            objTblMod.removeAllRows();
            IntValIni=0;
        }
        
    }     
     
    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
    }

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
    }   

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        if (blnHayCam || objTblMod.isDataModelChanged())
        {
            strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
            strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
            switch (mostrarMsgCon(strAux, true))
            {
                case 0: //YES_OPTION
                    switch (objTooBar.getEstado())
                    {
                        case 'n': //Insertar
                            if(objTooBar.beforeInsertar())
                            {
                                if(objTooBar.insertar())
                                {
                                    objTooBar.afterInsertar();
                                    blnRes=true;
                                }
                                else
                                    blnRes=false;
                            }
                            else
                                blnRes=false;
                            break;
                        case 'm': //Modificar
                            if(objTooBar.beforeModificar())
                            {
                                if(objTooBar.modificar())
                                {
                                    objTooBar.afterModificar();
                                    blnRes=true;
                                }
                                else
                                    blnRes=false;
                            }
                            else
                                blnRes=false;
                            break;
                    }
                    break;
                case 1: //NO_OPTION                    
                    objTblMod.setDataModelChanged(false);
                    blnHayCam=false;
                    blnRes=true;
                    break;
                case 2: //CANCEL_OPTION
                    blnRes=false;
                    break;
            }
        }
        return blnRes;
    }
    
    /* Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")) {
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        
        if(objTooBar.getEstado()=='n')
        {
            if(existeConfiguracionTipoDocumento())
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La configuración del tipo de documento <FONT COLOR=\"blue\">" + txtDesCorTipDoc.getText() + "</FONT> ya fue ingresada.<BR>Seleccione un tipo de documento que no tenga configuración y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
                return false;
            }
        }
        
        //Valida que exista seleccionado un visto bueno antes de guardar.
        if(objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
        {
            if(objTblMod.getRowCountTrue()<=0)
            {
                mostrarMsgInf("<HTML>Debe seleccionar al menos un visto bueno.<BR>Seleccione un visto bueno y vuelva a intentarlo.</HTML>");
                return false;
            }
        }
        
        //Valida que NO exista ninguna fila sin visto bueno seleccionado antes de guardar.
        if(objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
        {
            for(int i=0; i< objTblMod.getRowCount(); i++)
            {
                if(!objTblMod.isCheckedAnyColumn(i))
                {
                     mostrarMsgInf("<HTML>Debe seleccionar al menos un usuario en el "+objTblMod.getValueAt(i , INT_TBL_DAT_VIS) +".<BR>Seleccione un usuario y vuelva a intentarlo.</HTML>");
                     return false;
                }
            }
        }
        
        return true;
    } 
    
    private boolean existeConfiguracionTipoDocumento()
    {
        boolean blnRes=false;
        try
        {
            abrirCon();
            if(con!=null)
            {
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a.* , a1.ne_numVisBue FROM tbr_visBueUsrTipDoc as a";
                strSQL+=" INNER JOIN tbm_CabTiPDoc as a1 ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc)";
                strSQL+=" WHERE a.st_Reg NOT IN ('E', 'I')";
                strSQL+=" AND a.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a.co_TipDoc=" + txtCodTipDoc.getText();
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                {
                    blnRes=true;
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                cerrarCon();
            }
        }
        catch(java.sql.SQLException e){    objUti.mostrarMsgErr_F1(this, e);  blnRes=true;      }
        catch(Exception e){   objUti.mostrarMsgErr_F1(this, e);   blnRes=true;  }
        return blnRes;
    }
       
    public void cargarTipoDocPre()
    {
        try
        {
            abrirCon();
            if(con!=null)
            {
                stm = con.createStatement();
                strSQL ="";
                strSQL+= getSQLTipoDocumento(true);
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                {
                    txtCodTipDoc.setText(((rst.getString("co_tipdoc")==null)?"":rst.getString("co_tipdoc")));
                    txtDesCorTipDoc.setText(((rst.getString("tx_descor")==null)?"":rst.getString("tx_descor")));
                    txtDesLarTipDoc.setText(((rst.getString("tx_deslar")==null)?"":rst.getString("tx_deslar")));
                    spnVisBue.setValue(new Integer(rst.getInt("ne_numVisBue")) ); 
                    blnModVisBue=false;
                }
                rst.close();
                stm.close();
                rst = null;
                stm = null;
                cerrarCon();
            }
        }
        catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }
    }
    
    /**
     * Función que retorna la sentencia SQL de tipos de documentos configurados.
     * @param blnPre true: Indica si se desea obtener tipo de documento predeterminado.
     * @return strTipDoc
     */
    public String getSQLTipoDocumento(boolean blnPre)
    {
        String strTipDoc="";
        try
        {
            strTipDoc ="";
            strTipDoc+=" SELECT a.co_tipdoc, a.tx_deslar, a.tx_descor, a.ne_numVisBue";
            strTipDoc+=" FROM tbm_cabTipDoc as a INNER JOIN ";
            if(objParSis.getCodigoUsuario()==1) {
                strTipDoc+=" tbr_tipDocPrg as a1 ";
            }
            else {
                strTipDoc+=" tbr_tipDocUsr as a1 ";
            }
            strTipDoc+=" ON ( a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipdoc = a1.co_tipdoc )";
            strTipDoc+=" WHERE a1.co_emp = "+objParSis.getCodigoEmpresa();
            strTipDoc+=" AND a1.co_loc = "+objParSis.getCodigoLocal();
            strTipDoc+=" AND a1.co_mnu = "+objParSis.getCodigoMenu();
            if(objParSis.getCodigoUsuario()!=1) {
                strTipDoc+=" AND a1.co_usr = "+objParSis.getCodigoUsuario();
            }
            if(blnPre)
            {
                strTipDoc+=" AND a1.st_reg = 'S' ";
            }
            strTipDoc+=" ORDER BY a.co_tipdoc";
        }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }
        return strTipDoc;
    }
        
    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
     public void limpiarFrm()
     {
         limpiarCabFrm();
         limpiarDetFrm();
     }
     
    /**
     * Esta función permite limpiar la cabecera del formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
     public void limpiarCabFrm()
     {
         txtCodTipDoc.setText("");
         txtDesCorTipDoc.setText("");
         txtDesLarTipDoc.setText("");         
         spnVisBue.setValue(new Integer(0));
         blnModVisBue=false;
     }
     
    /**
     * Esta función permite limpiar el detalle del formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
     public void limpiarDetFrm()
     {
         IntValIni=0;
         objTblMod.removeAllRows();
         objTblMod.setDataModelChanged(false);
         eliminaColumnasAdicionadas();
     }
    
     
     //<editor-fold defaultstate="collapsed" desc="/* ToolBar */">
     public class MiToolBar extends ZafToolBar
     {
        public MiToolBar(javax.swing.JInternalFrame jfrThis)
        {
            super(jfrThis, objParSis);
        }	

        //******************************************************************************************************
        public void clickInicio()
        {
            try
            {   
                if(arlDatCfg.size()>0){
                    if(intIndiceCfg>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCfg=0;
                                limpiarDetFrm();
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCfg=0;
                            limpiarDetFrm();
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e) 
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickAnterior() 
        {
           try
            { 
                if(arlDatCfg.size()>0){
                    if(intIndiceCfg>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCfg--;
                                limpiarDetFrm();
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCfg--;
                            limpiarDetFrm();
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e) 
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickSiguiente()
        {
            try
            {  
                if(arlDatCfg.size()>0){
                    if(intIndiceCfg < arlDatCfg.size()-1){
                        if (blnHayCam || objTblMod.isDataModelChanged()) {
                            if (isRegPro()) {
                                intIndiceCfg++;
                                limpiarDetFrm();
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCfg++;
                            limpiarDetFrm();
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e) 
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickFin() 
        {
            try 
            {
                if(arlDatCfg.size()>0){
                    if(intIndiceCfg<arlDatCfg.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCfg=arlDatCfg.size()-1;
                                limpiarDetFrm();
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCfg=arlDatCfg.size()-1;
                            limpiarDetFrm();
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickAnular()  {
            setEditable(false);
            spnVisBue.setEnabled(false);
        }
        
        public void clickEliminar() {
            setEditable(false);
            spnVisBue.setEnabled(false);
	}
        
        public void clickAceptar() {
        }
                
        public void clickCancelar(){
            
        }
                
        public void clickConsultar() {
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
            tipoEdicionJTable(false);
            setEditable(true);
            spnVisBue.setEnabled(false);
            System.out.println("estado: "+getEstado());
            switch (objTooBar.getEstado())
            {
                case 'j':
                    limpiarDetFrm();
                    cargarDetReg();
                    break;
                default:
                    limpiarFrm();
                    cargarTipoDocPre();
                    break;
            }
	}

        public void clickInsertar() 
        {
            try 
            {
                limpiarFrm();
                tipoEdicionJTable(true);
                setEditable(true);
                spnVisBue.setEnabled(true);
                cargarTipoDocPre();  
                cargarDetReg();
                blnModVisBue=true;
                //Inicializar las variables que indican cambios.            
                blnHayCam=false;
                objTblMod.setDataModelChanged(false);
            }
            catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e); }
        }
           
        public void clickModificar()
        {
            tipoEdicionJTable(true);
            setEditable(false);
            limpiarDetFrm();
            spnVisBue.setEnabled(true);
            cargarDetReg();
            blnModVisBue=true;
            //Inicializar las variables que indican cambios.            
            blnHayCam=false;
            objTblMod.setDataModelChanged(false);
        }
        
        public void clickImprimir(){
        }
        
        public void clickVisPreliminar(){
        }
        
        //******************************************************************************************************
        public boolean beforeAnular() {
            return true;
        }
        
        public boolean beforeEliminar() {
            return true;
        }
        
        public boolean beforeAceptar() {
            return true;
        }
        
        public boolean beforeCancelar() {
            return true;
        }
        
        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeInsertar() {
            if(!isCamVal()){
                return false;
            }
            return true;
        }
        
        public boolean beforeModificar() {
            if(!isCamVal()){
                return false;
            }
            return true;
        }
        
        public boolean beforeImprimir() {
            return true;
        }
                
        public boolean beforeVistaPreliminar() {
            return true;
        }

        //******************************************************************************************************
        public boolean afterAnular() {
            return true;
        }
        
        public boolean afterEliminar() {
            return true;
        }
           
        public boolean afterAceptar() {
            return true;
        }
        public boolean afterCancelar() 
        {
            switch (objTooBar.getEstado())
            {
                case 'j':
                    limpiarDetFrm();
                    cargarDetReg();
                    break;
                default:
                    limpiarFrm();
                    break;
            }
            return true;
        }
        
        public boolean afterConsultar() {
            return true;
        }
                
        public boolean afterInsertar() 
        {
            this.setEstado('w');
            limpiarDetFrm();
            consultarReg();
            return true;
        }
        
        public boolean afterModificar() 
        {
            this.setEstado('w');
            limpiarDetFrm();
            consultarReg();
            return true;
        }
        
        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterVistaPreliminar() {
            return true;
        }
   
        
        //******************************************************************************************************
        public boolean anular() 
        {
            try
            {
                strAux=objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado")) 
                {
                    mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                    return false;
                }
                if (strAux.equals("Anulado")) {
                    mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                    return false;
                }

                objTooBar.setEstadoRegistro("Anulado");
                blnHayCam=false;
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
                return false;
            }
            return true;
        }
        
        public boolean eliminar()
        {
            try
            {
                strAux=objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado")) {
                    mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }
                blnHayCam=false;
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
                return false;
            }
            return true;
        }
        
        public boolean aceptar() {
            return true;
        }
                
        public boolean cancelar() 
        {
            boolean blnRes=true;
            try 
            {
                if (blnHayCam || objTblMod.isDataModelChanged()) 
                {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m') {
                        if (!isRegPro())  
                            return false;
                    }
                }
            }
            catch (Exception e) 
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            
            blnHayCam=false;
             
            return blnRes;
        }
        
        public boolean consultar() 
        {        
            if(!consultarReg())
                return false;
           
            return true;
        }
        
        public boolean insertar() 
        {
            if(!insertarReg())
                return false;
            return true;
        }
           
        public boolean modificar() 
        {
            if(!actualizarReg()) 
                return false;
            return true;
        }
        
        public boolean vistaPreliminar(){
            return true;
        }
        
        public boolean imprimir(){
            return true;
        }
                
        public boolean beforeClickInsertar()
        {
            return isRegPro();
        }

        public boolean beforeClickConsultar()
        {
            return isRegPro();
        }

        public boolean beforeClickEliminar()
        {
            return isRegPro();
        }

        public boolean beforeClickAnular()
        {
            return isRegPro();
        }

        public boolean beforeClickImprimir()
        {
            //return isRegPro();
            return true;
        }

        public boolean beforeClickVistaPreliminar()
        {
            //return isRegPro();
            return true;
        }

        public boolean beforeClickCancelar()
        {
            return isRegPro();
        }
        
        //******************************************************************************************************
        protected void finalize() throws Throwable {   System.gc(); super.finalize(); }
  
    }
     
     //</editor-fold>
        
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg()
    {
        boolean blnRes=true;
        try
        {
            abrirCon();
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Arma query
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc";
                strSQL+=" FROM tbr_visBueUsrTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                if (!txtCodTipDoc.getText().equals("")){
                    strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                }
                strSQL+=" AND a1.st_reg NOT IN ('E', 'I') ";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc";
                rst=stm.executeQuery(strSQL);
                arlDatCfg = new ArrayList();
                while(rst.next())
                {
                    arlRegCfg = new ArrayList();
                    arlRegCfg.add(INT_ARL_CFG_COD_EMP,rst.getInt("co_emp"));
                    arlRegCfg.add(INT_ARL_CFG_COD_LOC,rst.getInt("co_loc"));
                    arlRegCfg.add(INT_ARL_CFG_COD_TIP_DOC,rst.getInt("co_tipDoc"));
                    arlDatCfg.add(arlRegCfg);
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                cerrarCon();
                
                if(arlDatCfg.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatCfg.size()) + " registros");
                    intIndiceCfg=arlDatCfg.size()-1;
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
        
    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=false;
        try
        {
            if (cargarCabReg())
            {
                if (cargarDetReg())
                {
                    blnRes=true;
                }
            }
            blnHayCam=false;
            objTblMod.setDataModelChanged(false);            
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
        
    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            abrirCon();
            if (con!=null)
            {
                stm=con.createStatement();
                //Vistos Buenos
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.ne_numVisBue, a2.tx_desCor, a2.tx_DesLar";
                strSQL+=" FROM tbr_visBueUsrTipDoc as a1";
                strSQL+=" INNER JOIN tbm_cabTipDoc as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.st_Reg NOT IN ('E', 'I')";
                strSQL+=" AND a1.co_emp=" + objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_TIP_DOC);
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.ne_numVisBue, a2.tx_desCor, a2.tx_DesLar";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    
                    spnVisBue.setValue(new Integer(rst.getInt("ne_numVisBue")) ); 
                }
                else
                {
                    objTooBar.setEstadoRegistro("Vacío");
                    limpiarFrm();
                    blnRes=false;
                }
                           
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                cerrarCon();
            }
     
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceCfg+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatCfg.size()) );
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
    
    private boolean cargarDetReg()
    {
        boolean blnRes=false;
        try
        {
            if(eliminaColumnasAdicionadas())
            {
                if(obtenerColumnasAdicionarUsrVisBue())
                {
                    if(agregarColTblDat())
                    {
                        setVisBue();
                        if(cargarDatVisBue()){
                            blnRes=true;
                        }
                    }
                }
            }
        }
        catch(Exception e){        blnRes=false;         objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }
   
    /**
     * Función que elimina las columnas adicionadas al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean eliminaColumnasAdicionadas()
    {
        boolean blnRes=true;
        try
        {
            for (int i=(objTblMod.getColumnCount()-1); i>=(intNumColEst); i--)
            {
                objTblMod.removeColumn(tblDat, i);                
            }
        }
        catch(Exception e){  objUti.mostrarMsgErr_F1(this, e);   blnRes=false;      }
        return blnRes;
    
    }
    
    /**
     * Función que carga los usuarios que tienen asignados vistos buenos.
     * @return 
     */ 
    private boolean obtenerColumnasAdicionarUsrVisBue()
    {
        boolean blnRes=true;
        try
        {
            abrirCon();
            if (con!=null)
            {
                stm=con.createStatement();
                /* Agrega columnas automáticamente de acuerdo a la cantidad de usuarios. */
                strSQL =" SELECT COUNT (a.co_usr) as CanUsr ";
                strSQL+=" FROM( ";
                strSQL+="   SELECT b.co_usr FROM tbr_usremp as a  ";
                strSQL+="   INNER JOIN tbm_usr as b ON (b.co_usr=a.co_usr)";
                strSQL+="   WHERE b.st_reg NOT IN ('E','I') ";

                if(objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_EMP) !=objParSis.getCodigoEmpresaGrupo())
                {
                    strSQL+="   AND a.co_emp="+ objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_EMP);
                }

                if( (objTooBar.getEstado()!='x' && objTooBar.getEstado()!='n' && objTooBar.getEstado()!='m') ) //Modificar (Previo) / Insertar
                {
                    strSQL+="   AND b.co_usr IN";
                    strSQL+="   (";
                    strSQL+="     SELECT DISTINCT co_usr FROM tbr_visBueUsrTipDoc";
                    strSQL+="     WHERE st_reg NOT IN ('E', 'I') ";
                    strSQL+="     AND co_emp="+ objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_EMP);
                    strSQL+="     AND co_loc="+ objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_LOC);
                    strSQL+="     AND co_tipDoc="+ objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_TIP_DOC);
                    strSQL+="   )";
                }
                strSQL+="   GROUP BY b.co_usr";
                strSQL+=" ) as a";
                intNumColGrpUsrAdi=0;
                rst=stm.executeQuery(strSQL);
                if(rst.next()) 
                {
                    intNumColGrpUsrAdi=rst.getInt("canUsr");
                }
                rst.close();                         
                rst=null;

                /* Nombres de Usuarios */ 
                strSQL =" SELECT b.co_usr, b.tx_usr, b.tx_nom FROM tbr_usremp as a ";
                strSQL+=" INNER JOIN tbm_usr as b ON (b.co_usr=a.co_usr) ";
                strSQL+=" WHERE b.st_reg NOT IN ('E','I')";
                
                if(objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_EMP) !=objParSis.getCodigoEmpresaGrupo())
                {
                    strSQL+="   AND a.co_emp="+ objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_EMP);
                }

                if( (objTooBar.getEstado()!='x' && objTooBar.getEstado()!='n' && objTooBar.getEstado()!='m') )
                {
                    strSQL+=" AND b.co_usr IN";
                    strSQL+=" (";
                    strSQL+="   SELECT DISTINCT co_usr FROM tbr_visBueUsrTipDoc";
                    strSQL+="   WHERE st_reg NOT IN ('E', 'I') ";
                    strSQL+="   AND co_emp="+objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_EMP);
                    strSQL+="   AND co_loc="+ objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_LOC);
                    strSQL+="   AND co_tipDoc="+ objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_TIP_DOC);
                    strSQL+=" )";
                }
                strSQL+=" GROUP BY b.co_usr, b.tx_usr, b.tx_nom";
                strSQL+=" ORDER BY b.tx_usr ";
                rst=stm.executeQuery(strSQL);
                arlDatUsr =new ArrayList();
                while(rst.next()) 
                {
                    arlRegUsr= new ArrayList();
                    arlRegUsr.add(INT_ARL_DAT_USR_COD_USR, rst.getString("co_usr"));
                    arlRegUsr.add(INT_ARL_DAT_USR_NOM_USR, rst.getString("tx_usr"));
                    arlDatUsr.add(arlRegUsr);
                }
                rst.close();                         
                rst=null;
                stm.close();
                stm=null;
                cerrarCon();
            }
        }
        catch (Exception e){         blnRes = false;     objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
   }
     
    /**
     * Esta función permite agregar columnas al "tblDat" de acuerdo a la cantidad de usuarios".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDat()
    {
        boolean blnRes=true;
        ZafTblHeaGrp objTblHeaGrpUsr = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaGrpUsr.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColVisBue = null;
        javax.swing.table.TableColumn tbc;
        
        try 
        {
            intColIni=objTblMod.getColumnCount();
            int intUsr=0;
            
            for(int i=0; i<(intNumColGrpUsrAdi*INT_TBL_DAT_NUM_TOT_CDI); i++) //Cantidad de usuarios*Número de columnas dinámicas por usuario.
            {
                int intIndColGrp =(intColIni+i*INT_TBL_DAT_FAC_NUM_CDI); //Obtengo Indice de la Columna.
                tbc=new javax.swing.table.TableColumn(intIndColGrp);     //Creo la columna dinamica.
                if(intIndColGrp%INT_TBL_DAT_NUM_TOT_CDI!=0)  
                {                    
                    tbc.setHeaderValue("Apr.");
                }
                else 
                {
                    tbc.setHeaderValue("Vis.Bue.Pre.");
                    intUsr++;
                } 

                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(65);
                tbc.setResizable(true);

                //Configurar JTable: Establecer columnas editables.
                Vector vecAux=new Vector();
                vecAux.add("" + intIndColGrp);
                objTblMod.addColumnasEditables(vecAux);
                vecAux=null;

                //Establecer: Check
                objTblCelRenChk = new ZafTblCelRenChk();
                tbc.setCellRenderer(objTblCelRenChk);

                //Configurar JTable: Establecer color de fondo.
                if(intIndColGrp%INT_TBL_DAT_NUM_TOT_CDI!=0)  
                { 
                    colFonCol=new java.awt.Color(228,228,203);
                    objTblCelRenChk.setBackground(colFonCol);
                }
                
                //Metodos de Validaciones
                objTblCelEdiChk = new ZafTblCelEdiChk();
                tbc.setCellEditor(objTblCelEdiChk);

                objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
                {
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                    {
                    }     
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                    {
                        int intFil= tblDat.getSelectedRow()== -1 ? 0 :tblDat.getSelectedRow();
                        int intCol= tblDat.getSelectedColumn()== -1 ? 0 :tblDat.getSelectedColumn();
                        boolean blnVisBue=true;
                        if(intFil >= 0)
                        {
                            //Valida que exista aunque sea un visto bueno seleccionado en la fila anterior o en la fila visto bueno 1.
                            if(intFil>0)
                            {                                
                                if(!objTblMod.isCheckedAnyColumn(intFil-1))
                                {
                                    blnVisBue=false;
                                    mostrarMsgInf("Es necesario MARCAR el "+objTblMod.getValueAt(intFil-1, INT_TBL_DAT_VIS)+"  aun que sea a un solo usuario. ");
                                    if(intCol%INT_TBL_DAT_NUM_TOT_CDI!=0) //Aprobacion
                                    {
                                        objTblMod.setValueAt( new Boolean(false), intFil, intCol);
                                        objTblMod.setValueAt( new Boolean(false), intFil, intCol+1);
                                    }
                                    else    //Visto Bueno Previo
                                    {
                                        objTblMod.setValueAt( new Boolean(false), intFil, intCol);
                                        objTblMod.setValueAt( new Boolean(false), intFil, intCol-1);
                                    }
                                }
                            }
                            
                            //Si existe marcado aunque sea un usuario en el visto anterior continua con el proceso.
                            if(blnVisBue)
                            {
                                //Valida que siempre que exista Visto bueno previo seleccionado, se seleccione el de Aprobación.
                                if(intCol%INT_TBL_DAT_NUM_TOT_CDI==0)  //Visto Bueno Previo
                                {
                                    if(!objTblMod.getValueAt( intFil , intCol).equals("false"))
                                    {
                                        //Si se marca el Visto Bueno previo, se marque el de aprobación.
                                        if(!objTblMod.getValueAt( intFil , (intCol-1)).equals("true"))
                                        {
                                            objTblMod.setValueAt( new Boolean(true), intFil, intCol-1);
                                        }
                                    }
                                }
                                else  //Aprobación.
                                {
                                     if(!objTblMod.getValueAt( intFil , intCol).equals("true"))
                                     {
                                         //Si se desmarca el de aprobación, se desmarque el Visto Bueno previo.
                                         if(!objTblMod.getValueAt( intFil , (intCol+1)).equals("false"))
                                         {
                                             objTblMod.setValueAt( new Boolean(false), intFil, intCol+1);
                                         }
                                     }
                                }
                            
                                //Valida en cada una de las filas de vistos buenos, que no queden sin marcar aunque sea un usuario
                                if(!objTblMod.isCheckedAnyColumn(intFil))
                                {
                                    mostrarMsgInf("Es necesario MARCAR el "+objTblMod.getValueAt(intFil, INT_TBL_DAT_VIS)+"  aun que sea a un solo usuario. ");
                                    objTblMod.setValueAt( new Boolean(true), intFil, intCol);
                                }
                            }
                        }
                    }
                });
                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(intIndColGrp%INT_TBL_DAT_NUM_TOT_CDI!=0)  
                {
                    objTblHeaColVisBue=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatUsr, intUsr, INT_ARL_DAT_USR_NOM_USR) + "");
                    objTblHeaColVisBue.setHeight(16);
                }
                objTblHeaColVisBue.add(tbc);
                objTblHeaGrpUsr.addColumnGroup(objTblHeaColVisBue);
                
                objTblCelRenChk=null;
                objTblCelEdiChk=null;
            }
            
            objTblCelRenLbl=null;
            intColFin=objTblMod.getColumnCount();
            objTblHeaGrpUsr=null;
            objTblHeaColVisBue=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
        
    /**
     * Esta función permite cargar los datos de vistos buenos.
     * @return true: Si se pudo cargar los datos.
     * <BR>false: En el caso contrario.
     */
    public boolean cargarDatVisBue()
    {
       boolean blnRes=true;
       int intCodUsr=0;
       int intNumFilTblDat=0;
       try
       {
           abrirCon();
           if (con!=null)
           {
               if (objTooBar.getEstado()!='n')
               {
                   stm=con.createStatement();
                   intNumFilTblDat=objTblMod.getRowCountTrue();  
                   for(int IntFil=0; IntFil< intNumFilTblDat; IntFil++)
                   {           
                       //Obtiene Valor Autorizado por Visto Bueno
                       strSQL =" SELECT co_visBue, CASE WHEN nd_valAut IS NULL THEN 0 ELSE nd_valAut END as nd_valAut";
                       strSQL+=" FROM tbr_visBueUsrTipDoc ";
                       strSQL+=" WHERE st_Reg NOT IN ('E', 'I') AND co_emp="+ objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_EMP);
                       strSQL+=" AND co_loc="+ objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_LOC);
                       strSQL+=" AND co_tipdoc="+ objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_TIP_DOC);
                       strSQL+=" AND co_visbue="+(IntFil+1);
                       strSQL+=" GROUP BY co_visBue, nd_valAut";
                       rst = stm.executeQuery(strSQL);
                       if(rst.next())
                       {  
                           objTblMod.setValueAt(rst.getString("nd_valAut"), IntFil, INT_TBL_DAT_VAL);
                       }
                       rst.close();
                       rst=null;

                       //Obtiene configuración de vistos buenos para los usuarios.
                       int intUsr=0;
                       for(int IntCol=(INT_TBL_DAT_CDI_AUT); IntCol< intColFin; IntCol=IntCol+INT_TBL_DAT_NUM_TOT_CDI)
                       {
                           intCodUsr=objUti.getIntValueAt(arlDatUsr, intUsr, INT_ARL_DAT_USR_COD_USR);

                           strSQL =" SELECT co_visBue, st_necVisBuePre";
                           strSQL+=" FROM tbr_visBueUsrTipDoc ";
                           strSQL+=" WHERE st_Reg NOT IN ('E', 'I') AND co_emp="+ objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_EMP);
                           strSQL+=" AND co_loc="+ objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_LOC);
                           strSQL+=" AND co_tipdoc="+ objUti.getIntValueAt(arlDatCfg, intIndiceCfg, INT_ARL_CFG_COD_TIP_DOC);
                           strSQL+=" AND co_visbue="+(IntFil+1);
                           strSQL+=" AND co_usr="+intCodUsr;
                           rst = stm.executeQuery(strSQL);
                           if(rst.next())
                           {  
                               objTblMod.setChecked(true, IntFil, IntCol);

                               if(rst.getString("st_necVisBuePre").equals("S"))
                               {
                                   objTblMod.setChecked(true, IntFil, IntCol+1);
                               }
                               else
                               {
                                   objTblMod.setChecked(false, IntFil, IntCol+1);
                               }
                           }
                           else
                           {
                               objTblMod.setChecked(false, IntFil, IntCol);
                               objTblMod.setChecked(false, IntFil, IntCol+1);
                           }
                           intUsr++;

                           rst.close();
                           rst=null;
                        }  
                   }  
                   stm.close();
                   stm=null;
               }
               cerrarCon();
           }
        }
        catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
        return blnRes;
    }
        
     public boolean insertarReg() 
     {
         if(!guardaCfgVisBue())
            return false;
         return true;
     }

     public boolean actualizarReg() 
     {
        if(!guardaCfgVisBue())
            return false;
        return true;
     }     
        
     /**
     * Esta función guarda la configuración de vistos buenos.
     * Función utilizada para insertar y modificar.
     * @return true: Si se pudo guardar.
     * <BR>false: En el caso contrario.
     */
     public boolean guardaCfgVisBue() 
     {
        boolean blnRes=false;
        String strEstAut="",strEstBue="";
        BigDecimal bgdValAut= new BigDecimal("0");
        int intCodUsr=0;
        try
        {
            abrirCon();
            if (con!=null)
            {
                stm=con.createStatement();
                for(int IntFil=0; IntFil< objTblMod.getRowCountTrue(); IntFil++)
                {        
                    bgdValAut= new BigDecimal(objTblMod.getValueAt(IntFil, INT_TBL_DAT_VAL)==null?"0":objTblMod.getValueAt(IntFil, INT_TBL_DAT_VAL).toString());
                    int intUsr=0;
                    for(int IntCol=(INT_TBL_DAT_CDI_AUT); IntCol< objTblMod.getColumnCount()-1; IntCol=IntCol+INT_TBL_DAT_NUM_TOT_CDI)
                    {
                        intCodUsr=objUti.getIntValueAt(arlDatUsr, intUsr, INT_ARL_DAT_USR_COD_USR);
                        strEstAut=(objTblMod.getValueAt(IntFil, IntCol)==null?"false":objTblMod.getValueAt(IntFil, IntCol).toString());
                        strEstBue=(objTblMod.getValueAt(IntFil, IntCol+1)==null?"false":objTblMod.getValueAt(IntFil, IntCol+1).toString());
                        
                        if(strEstAut.equals("true"))
                        {
                             int intExi=0;
                             //Valida que no existan documentos relacionados al visto bueno.
                             strSQL =" SELECT count(co_usr) as exis FROM tbm_visBueMovInv ";
                             strSQL+=" WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal();
                             strSQL+=" AND co_tipdoc="+txtCodTipDoc.getText()+" AND co_visbue="+(IntFil+1)+" AND co_usr="+intCodUsr+" ";
                             rst = stm.executeQuery(strSQL);
                             if(rst.next()){ intExi=rst.getInt("exis"); }
                             rst.close();
                             rst=null;
                             
                             if(intExi==0)
                             {
                                 strSQL =" DELETE FROM tbr_visBueUsrTipDoc WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal();
                                 strSQL+=" AND co_tipdoc="+txtCodTipDoc.getText()+" AND co_visbue="+(IntFil+1)+" AND co_usr="+intCodUsr+" ";
                                 stm.executeUpdate(strSQL);

                                 strSQL =" INSERT INTO tbr_visBueUsrTipDoc(co_emp, co_loc, co_tipdoc, co_visbue, co_usr, nd_valAut, st_necvisbuepre, st_regrep, st_reg) ";
                                 strSQL+=" VALUES("+objParSis.getCodigoEmpresa()+","+objParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+(IntFil+1);
                                 strSQL+="       ,"+intCodUsr+", "+bgdValAut+", '"+(strEstBue.equals("true")?"S":"N")+"','I', 'A' )";
                                 stm.executeUpdate(strSQL);
                             }
                             else
                             {
                                  strSQL =" UPDATE tbr_visBueUsrTipDoc SET st_necVisBuePre = '"+(strEstBue.equals("true")?"S":"N")+"' ";
                                  strSQL+="        , nd_valAut="+bgdValAut+", st_reg='A', st_regrep='M' ";
                                  strSQL+=" WHERE co_emp="+objParSis.getCodigoEmpresa();
                                  strSQL+=" AND co_loc="+objParSis.getCodigoLocal();
                                  strSQL+=" AND co_tipdoc="+txtCodTipDoc.getText();
                                  strSQL+=" AND co_visbue="+(IntFil+1);
                                  strSQL+=" AND co_usr="+intCodUsr;
                                  stm.executeUpdate(strSQL);
                             }
                        }
                        else //Anula registro.
                        {
                             strSQL =" UPDATE tbr_visBueUsrTipDoc SET st_necvisbuepre = 'N' ,st_reg='I', st_regrep='M' ";
                             strSQL+=" WHERE co_emp="+objParSis.getCodigoEmpresa();
                             strSQL+=" AND co_loc="+objParSis.getCodigoLocal();
                             strSQL+=" AND co_tipdoc="+txtCodTipDoc.getText();
                             strSQL+=" AND co_visbue="+(IntFil+1);
                             strSQL+=" AND co_usr="+intCodUsr;
                             stm.executeUpdate(strSQL);
                        }
                        intUsr++;
                    }
                }

                //Actualiza la cantidad de vistos buenos que tiene el tipo de documento.
                strSQL =" UPDATE tbm_cabtipdoc SET ne_numVisBue="+spnVisBue.getModel().getValue().toString();
                strSQL+=" WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+" AND co_tipdoc="+txtCodTipDoc.getText();
                stm.executeUpdate(strSQL);
                
                stm.close();
                stm=null;
                cerrarCon();
                blnRes=true;
            }
        }
        catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }  
        return blnRes;
     }
     

}


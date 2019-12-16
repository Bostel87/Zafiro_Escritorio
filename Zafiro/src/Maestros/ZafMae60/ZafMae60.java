
/*
 * ZafMae60.java
 *
 * Created on Dec 18, 2010, 2:03:19 PM
 */
package Maestros.ZafMae60;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil; /**/
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import java.util.Vector;
import java.sql.DriverManager;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;

/**
 *
 * @author jayapata
 */
public class ZafMae60 extends javax.swing.JInternalFrame 
{
    //Constantes: Jtable
    static final int INT_TBL_LINEA=0;
    static final int INT_TBL_CHKBOD=1;
    static final int INT_TBL_CODBOD=2;
    static final int INT_TBL_NOMBOD=3;
    static final int INT_TBL_NATURA=4;
    static final int INT_TBL_CHKPRE=5;
    
    //Variables
    private ZafParSis objZafParSis;
    private ZafUtil objUti; 
    private MiToolBar objTooBar;
    private ZafTblMod objTblMod;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenCbo objTblCelRenCmbBox;
    private ZafTblCelEdiCbo objTblCelEdiCmbBox;
    private ZafVenCon vcoPrg;
    private ZafVenCon vcoUsr;
    
    private String strSQL="", strAux="";
    private String strCodUsr="", strUsr="", strNomUsr="";
    private String strCodPrg="", strNomPrg="";
    private String strVersion = " v0.3 ";
    
    javax.swing.JTextField txtCodUsr= new javax.swing.JTextField();

    java.sql.Connection CONN_GLO=null;
    java.sql.Statement  STM_GLO=null;
    java.sql.ResultSet  rstCab=null;
  
    /** Creates new form ZafMae60 */
    public ZafMae60(ZafParSis obj) 
    {
        try
        {
            //Inicializar objetos.
            objZafParSis = (ZafParSis) obj.clone();
            
            initComponents();
            if (!configurarFrm()) 
                exitForm();
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
            objTooBar = new MiToolBar(this);
            //Titulo Programa.
            strAux=objZafParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            
            this.getContentPane().add(objTooBar, "South");
            objTooBar.setVisibleAnular(false);
            
            //Configurar los JTables.
            configurarTblDat();
            
            //Configurar las ZafVenCon.
            configurarVenConPrg();
            configurarVenConUsr();
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarTblDat() 
    {
        boolean blnRes=true;
        try
        {

            Vector vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();

            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CHKBOD, " ");
            vecCab.add(INT_TBL_CODBOD, "Cód.Bod");
            vecCab.add(INT_TBL_NOMBOD, "Nom.Bod");
            vecCab.add(INT_TBL_NATURA, "Naturaleza");
            vecCab.add(INT_TBL_CHKPRE, "Pre.");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer la fila de cabecera.
            new ZafColNumerada(tblDat, INT_TBL_LINEA);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            // objMouMotAda=new ZafMouMotAda();
            // tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CHKBOD).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(350);
            tcmAux.getColumn(INT_TBL_NATURA).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_CHKPRE).setPreferredWidth(50);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_CHKBOD);
            vecAux.add("" + INT_TBL_NATURA);
            vecAux.add("" + INT_TBL_CHKPRE);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            //Configurar JTable: Editor de la tabla.
            new ZafTblEdi(tblDat);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKBOD).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_CHKPRE).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            objTblCelRenCmbBox = new ZafTblCelRenCbo();
            tcmAux.getColumn(INT_TBL_NATURA).setCellRenderer(objTblCelRenCmbBox);

            objTblCelEdiCmbBox = new ZafTblCelEdiCbo(tblDat);
            tcmAux.getColumn(INT_TBL_NATURA).setCellEditor(objTblCelEdiCmbBox);
            objTblCelEdiCmbBox.addItem("");
            objTblCelEdiCmbBox.addItem("Ingreso"); // I
            objTblCelEdiCmbBox.addItem("Egreso");  // E
            objTblCelEdiCmbBox.addItem("Ingreso/Egreso"); // A

            objTblCelEdiCmbBox.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    for (int p = (tblDat.getSelectedRow()); p <= (tblDat.getSelectedRow()); p++) {
                        if (!objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_CHKBOD)) {
                            mostrarMsgInf("<HTML>No se puede escoger el nivel si el reporte no ha sido seleccionado.<BR>Seleccione el reporte y vuelva a escoger el nivel del reporte.<HTML>");
                            objTblCelEdiCmbBox.setSelectedIndex(-1);
                            break;
                        }
                    }
                }
            });

            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKBOD).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_CHKPRE).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNunFil = tblDat.getSelectedRow();

                    if (tblDat.getSelectedColumn() == INT_TBL_CHKPRE) {

                        if (!tblDat.getValueAt(intNunFil, INT_TBL_CHKBOD).toString().equals("true")) {
                            mostrarMsgInf("NO SE PUEDE MARCAR COMO PREDETERMINADO SELECCIONE PRIMERO..");
                            tblDat.setValueAt(new Boolean(false), intNunFil, INT_TBL_CHKPRE);
                        } else {
                            for (int i = 0; i < tblDat.getRowCount(); i++) {
                                if (tblDat.getValueAt(intNunFil, INT_TBL_CHKBOD).toString().equals("true")) {
                                    if (intNunFil != i) {
                                        tblDat.setValueAt(new Boolean(false), i, INT_TBL_CHKPRE);
                                    }
                                }
                            }
                        }

                    }
                }
            });
        }
        catch(Exception e) {   blnRes=false;       objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }

    
    private boolean configurarVenConPrg() 
    {
        boolean blnRes = true;
        try 
        {
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
            strSQL="SELECT a1.co_mnu, a1.tx_nom "+
                   " FROM tbm_mnuSis AS a1 "+
                   " WHERE (a1.tx_tipMnu='C'or a1.tx_tipMnu='R') and a1.tx_nom!='' "+
                   " AND a1.st_reg<>'E' AND a1.tx_acc IS NOT NULL "+
                   " ORDER BY a1.tx_nom ";
            
            vcoPrg = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Programas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoPrg.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            vcoPrg.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
        } 
        catch (Exception e) {      blnRes = false;     objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    

    private boolean configurarVenConUsr() 
    {
        String strSQL="";
        boolean blnRes = true;
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_usr");
            arlCam.add("a1.tx_usr");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Usuario");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("85");
            arlAncCol.add("250");
            
            //Armar la sentencia SQL.
            if (objTooBar.getEstado() == 'c') 
            {
                strSQL = "select a1.co_usr, a1.tx_usr, a1.tx_nom from tbm_usr as a1 where st_reg='A' order by a1.tx_usr";
            } 
            else
            {
                strSQL = " select a1.co_usr, a1.tx_usr, a1.tx_nom  "
                       + " from tbr_perusr as a "
                       + " inner join tbm_usr as a1 on (a1.co_usr=a.co_usr) "
                       + " where a.co_emp=" + objZafParSis.getCodigoEmpresa() + " and a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                       + " and a.co_mnu=" + txtCodPrg.getText() + " and a1.st_reg='A'  "
                       + " order by a1.tx_usr ";
            }
            System.out.println("Holaaaaaa " +strSQL);
            vcoUsr = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Usuarios", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoUsr.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            vcoUsr.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);    
        }
        return blnRes;
    }
   
    private boolean mostrarVenConPrg(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoPrg.setCampoBusqueda(1);
                    vcoPrg.show();
                    if (vcoPrg.getSelectedButton() == vcoPrg.INT_BUT_ACE) 
                    {
                        txtCodPrg.setText(vcoPrg.getValueAt(1));
                        txtNomPrg.setText(vcoPrg.getValueAt(2));
                        txtCodUsr.setText("");
                        txtUsr.setText("");
                        txtNomUsr.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoPrg.buscar("a1.co_mnu", txtCodPrg.getText()))
                    {
                        txtCodPrg.setText(vcoPrg.getValueAt(1));
                        txtNomPrg.setText(vcoPrg.getValueAt(2));
                        txtCodUsr.setText("");
                        txtUsr.setText("");
                        txtNomUsr.setText("");
                    }
                    else 
                    {
                        vcoPrg.setCampoBusqueda(0);
                        vcoPrg.setCriterio1(11);
                        vcoPrg.cargarDatos();
                        vcoPrg.show();
                        if (vcoPrg.getSelectedButton() == vcoPrg.INT_BUT_ACE) 
                        {
                            txtCodPrg.setText(vcoPrg.getValueAt(1));
                            txtNomPrg.setText(vcoPrg.getValueAt(2));
                            txtCodUsr.setText("");
                            txtUsr.setText("");
                            txtNomUsr.setText("");
                        } 
                        else 
                        {
                            txtCodPrg.setText(strCodPrg);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Programa".
                    if (vcoPrg.buscar("a1.tx_nom", txtNomPrg.getText())) 
                    {
                        txtCodPrg.setText(vcoPrg.getValueAt(1));
                        txtNomPrg.setText(vcoPrg.getValueAt(2));
                        txtCodUsr.setText("");
                        txtUsr.setText("");
                        txtNomUsr.setText("");
                    } 
                    else 
                    {
                        vcoPrg.setCampoBusqueda(1);
                        vcoPrg.setCriterio1(11);
                        vcoPrg.cargarDatos();
                        vcoPrg.show();
                        if (vcoPrg.getSelectedButton() == vcoPrg.INT_BUT_ACE) 
                        {
                            txtCodPrg.setText(vcoPrg.getValueAt(1));
                            txtNomPrg.setText(vcoPrg.getValueAt(2));
                            txtCodUsr.setText("");
                            txtUsr.setText("");
                            txtNomUsr.setText("");
                        } 
                        else 
                        {
                            txtNomPrg.setText(strNomPrg);
                        }
                    }
                    break;
            }
        }
        catch (Exception e) {    blnRes = false;      objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }
 
    private boolean mostrarVenConUsr(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoUsr.setCampoBusqueda(1);
                    vcoUsr.show();
                    if (vcoUsr.getSelectedButton() == vcoUsr.INT_BUT_ACE) 
                    {
                        txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtUsr.setText(vcoUsr.getValueAt(2));
                        txtNomUsr.setText(vcoUsr.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoUsr.buscar("a1.co_usr", txtCodUsr.getText()))
                    {
                        txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtUsr.setText(vcoUsr.getValueAt(2));
                        txtNomUsr.setText(vcoUsr.getValueAt(3));
                    }
                    else 
                    {
                        vcoUsr.setCampoBusqueda(0);
                        vcoUsr.setCriterio1(11);
                        vcoUsr.cargarDatos();
                        vcoUsr.show();
                        if (vcoUsr.getSelectedButton() == vcoUsr.INT_BUT_ACE) 
                        {
                            txtCodUsr.setText(vcoUsr.getValueAt(1));
                            txtUsr.setText(vcoUsr.getValueAt(2));
                            txtNomUsr.setText(vcoUsr.getValueAt(3));
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
                        txtNomUsr.setText(vcoUsr.getValueAt(3));
                    } 
                    else 
                    {
                        vcoUsr.setCampoBusqueda(1);
                        vcoUsr.setCriterio1(11);
                        vcoUsr.cargarDatos();
                        vcoUsr.show();
                        if (vcoUsr.getSelectedButton() == vcoUsr.INT_BUT_ACE) 
                        {
                            txtCodUsr.setText(vcoUsr.getValueAt(1));
                            txtUsr.setText(vcoUsr.getValueAt(2));
                            txtNomUsr.setText(vcoUsr.getValueAt(3));
                        } 
                        else 
                        {
                            txtUsr.setText(strUsr);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Nombre".
                    if (vcoUsr.buscar("a1.tx_nom", txtNomUsr.getText())) 
                    {
                        txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtUsr.setText(vcoUsr.getValueAt(2));
                        txtNomUsr.setText(vcoUsr.getValueAt(3));
                    } 
                    else 
                    {
                        vcoUsr.setCampoBusqueda(2);
                        vcoUsr.setCriterio1(11);
                        vcoUsr.cargarDatos();
                        vcoUsr.show();
                        if (vcoUsr.getSelectedButton() == vcoUsr.INT_BUT_ACE) 
                        {
                            txtCodUsr.setText(vcoUsr.getValueAt(1));
                            txtUsr.setText(vcoUsr.getValueAt(2));
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
        catch (Exception e) {    blnRes = false;      objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }

    //<editor-fold defaultstate="collapsed" desc="/* Funciones Generales */">

    //<editor-fold defaultstate="collapsed" desc="/* Conexión */">
    public void abrirCon()
    {
        try
        {
            CONN_GLO=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        }
        catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
    }

    public void CerrarCon()
    {
        try
        {
            CONN_GLO.close();
            CONN_GLO=null;
        }
        catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
    }
    //</editor-fold>

    private void mostrarMsgInf(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Cerrar el formulario.
     */
    private void exitForm()
    {
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }

    /**
     * Esta función determina si los campos son válidos.
     *
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        if(objTooBar.getEstado()!='c')
        {
            if (txtCodPrg.getText().equals("")) 
            {
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
                txtCodPrg.requestFocus();
                mostrarMsgInf("<HTML>TIENE QUE ESCOJER EL PROGRAMA PRIMERO.</HTML>");
                return false;
            }
        }
        return true;
    }
    //</editor-fold>

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panGenTabGen = new javax.swing.JPanel();
        panCabRecChq = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNomPrg = new javax.swing.JTextField();
        txtCodPrg = new javax.swing.JTextField();
        butProg = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtUsr = new javax.swing.JTextField();
        txtNomUsr = new javax.swing.JTextField();
        butUsr = new javax.swing.JButton();
        panDetRecChq = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();

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

        panCen.setLayout(new java.awt.BorderLayout());

        panGenTabGen.setLayout(new java.awt.BorderLayout());

        panCabRecChq.setPreferredSize(new java.awt.Dimension(100, 60));
        panCabRecChq.setLayout(null);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel3.setText("Programa:"); // NOI18N
        panCabRecChq.add(jLabel3);
        jLabel3.setBounds(10, 10, 110, 20);

        txtNomPrg.setBackground(objZafParSis.getColorCamposObligatorios());
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
        panCabRecChq.add(txtNomPrg);
        txtNomPrg.setBounds(190, 10, 230, 20);

        txtCodPrg.setBackground(objZafParSis.getColorCamposObligatorios());
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
        panCabRecChq.add(txtCodPrg);
        txtCodPrg.setBounds(120, 10, 70, 20);

        butProg.setText(".."); // NOI18N
        butProg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butProgActionPerformed(evt);
            }
        });
        panCabRecChq.add(butProg);
        butProg.setBounds(420, 10, 20, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Usuario:"); // NOI18N
        panCabRecChq.add(jLabel5);
        jLabel5.setBounds(10, 30, 110, 20);

        txtUsr.setBackground(objZafParSis.getColorCamposObligatorios());
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
        panCabRecChq.add(txtUsr);
        txtUsr.setBounds(120, 30, 70, 20);

        txtNomUsr.setBackground(objZafParSis.getColorCamposObligatorios());
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
        panCabRecChq.add(txtNomUsr);
        txtNomUsr.setBounds(190, 30, 230, 20);

        butUsr.setText(".."); // NOI18N
        butUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butUsrActionPerformed(evt);
            }
        });
        panCabRecChq.add(butUsr);
        butUsr.setBounds(420, 30, 20, 20);

        panGenTabGen.add(panCabRecChq, java.awt.BorderLayout.NORTH);

        panDetRecChq.setLayout(new java.awt.BorderLayout());

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
        jScrollPane1.setViewportView(tblDat);

        panDetRecChq.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panGenTabGen.add(panDetRecChq, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panGenTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("titulo"); // NOI18N
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    //<editor-fold defaultstate="collapsed" desc="/* Eventos */">
    private void txtNomPrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomPrgActionPerformed
        txtNomPrg.transferFocus();
}//GEN-LAST:event_txtNomPrgActionPerformed

    private void txtNomPrgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrgFocusGained
        strNomPrg=txtNomPrg.getText();
        txtNomPrg.selectAll();
}//GEN-LAST:event_txtNomPrgFocusGained

    private void txtNomPrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrgFocusLost
        if (!txtNomPrg.getText().equalsIgnoreCase(strNomPrg)) 
        {
            if (txtNomPrg.getText().equals(""))
            {
                txtCodPrg.setText("");
                txtNomPrg.setText("");
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
            }
            else
                mostrarVenConPrg(2);
        }
        else
            txtNomPrg.setText(strNomPrg);
}//GEN-LAST:event_txtNomPrgFocusLost

    private void txtCodPrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrgActionPerformed
        txtCodPrg.transferFocus();
}//GEN-LAST:event_txtCodPrgActionPerformed

    private void txtCodPrgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrgFocusGained
        strCodPrg=txtCodPrg.getText();
        txtCodPrg.selectAll();
}//GEN-LAST:event_txtCodPrgFocusGained

    private void txtCodPrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrgFocusLost
        if (!txtCodPrg.getText().equalsIgnoreCase(strCodPrg)) 
        {
            if (txtCodPrg.getText().equals(""))
            {
                txtCodPrg.setText("");
                txtNomPrg.setText("");
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
            }
            else
                mostrarVenConPrg(1);
        }
        else
            txtCodPrg.setText(strCodPrg);
}//GEN-LAST:event_txtCodPrgFocusLost

    private void butProgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butProgActionPerformed
        mostrarVenConPrg(0);
}//GEN-LAST:event_butProgActionPerformed

    private void txtUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrFocusGained
        strUsr=txtUsr.getText();
        txtUsr.selectAll();
    }//GEN-LAST:event_txtUsrFocusGained

    private void txtUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrFocusLost
         if (!txtUsr.getText().equalsIgnoreCase(strUsr))
         {
            if (txtUsr.getText().equals("")) 
            {
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
            }
            else
            {
                if(isCamVal())
                {
                   configurarVenConUsr();
                   mostrarVenConUsr(2);
                }
            }
        }
        else
            txtUsr.setText(strUsr);
    }//GEN-LAST:event_txtUsrFocusLost


    private void butUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butUsrActionPerformed
        configurarVenConUsr();
        if(isCamVal())
        {
            configurarVenConUsr();
            mostrarVenConUsr(0);
        }
    }//GEN-LAST:event_butUsrActionPerformed

    private void txtNomUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomUsrFocusLost
        if (!txtNomUsr.getText().equalsIgnoreCase(strNomUsr)) 
        {
            if (txtNomUsr.getText().equals("")) 
            {
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
            }
            else
            {
                if(isCamVal())
                {
                   configurarVenConUsr();
                   mostrarVenConUsr(3);
                }
            }
        }
        else
           txtNomUsr.setText(strNomUsr);
    }//GEN-LAST:event_txtNomUsrFocusLost

    private void txtNomUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomUsrActionPerformed
        txtNomUsr.transferFocus();
    }//GEN-LAST:event_txtNomUsrActionPerformed

    private void txtUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsrActionPerformed
        txtUsr.transferFocus();
    }//GEN-LAST:event_txtUsrActionPerformed

    private void txtNomUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomUsrFocusGained
        strNomUsr=txtNomUsr.getText();
        txtNomUsr.selectAll();
    }//GEN-LAST:event_txtNomUsrFocusGained

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm();
        
    }//GEN-LAST:event_formInternalFrameClosing
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="/* Variables declaration - do not modify  */">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butProg;
    private javax.swing.JButton butUsr;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panCabRecChq;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panDetRecChq;
    private javax.swing.JPanel panGenTabGen;
    private javax.swing.JPanel panNor;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodPrg;
    private javax.swing.JTextField txtNomPrg;
    private javax.swing.JTextField txtNomUsr;
    private javax.swing.JTextField txtUsr;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
    
     /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objZafParSis);
        }

        public boolean anular()
        {
            return true;
        }

        public void clickAceptar()
        {

        }

        public void clickAnterior()
        {
            try 
            {
                if (rstCab != null) 
                {
                    abrirCon();
                    if (!rstCab.isFirst()) 
                    {
                        rstCab.previous();
                        refrescaDatos(CONN_GLO, rstCab);
                    }
                    CerrarCon();
                }
            } 
            catch (java.sql.SQLException e) {    objUti.mostrarMsgErr_F1(this, e);    } 
            catch (Exception e) {    objUti.mostrarMsgErr_F1(this, e);      }
        }


        public void clickAnular()
        {

        }

        public void clickCancelar()
        {

        }

        public void clickConsultar(){
        }

        public void clickEliminar()
        {

        }

        public void clickFin() 
        {
            try 
            {
                if (rstCab != null)
                {
                    abrirCon();
                    if (!rstCab.isLast()) 
                    {
                        rstCab.last();
                        refrescaDatos(CONN_GLO, rstCab);
                    }
                    CerrarCon();
                }
            }
            catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
            catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
        }

        public void clickImprimir()
        {

        }

        public void clickInicio() 
        {
            try 
            {
                if (rstCab != null) 
                {
                    abrirCon();
                    if (!rstCab.isFirst()) 
                    {
                        rstCab.first();
                        refrescaDatos(CONN_GLO, rstCab);
                    }
                    CerrarCon();
                }
            }
            catch (java.sql.SQLException e) {  objUti.mostrarMsgErr_F1(this, e); }
            catch (Exception e) { objUti.mostrarMsgErr_F1(this, e);  }
        }

        public void clickInsertar() 
        {
            try
            {
                clnTextos();

                setEditable(true);
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                objTblMod.setDataModelChanged(false);
                cargarDat();

                if (rstCab != null) 
                {
                    rstCab.close();
                    rstCab = null;
                }
            }
            catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e); }
        }

        private boolean cargarDat() 
        {
            boolean blnRes = false;
            java.sql.Connection conn;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSQL = "";
            try 
            {
                conn = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    java.util.Vector vecData = new java.util.Vector();

                    strSQL = "select co_bod, tx_nom  from tbm_bod  where co_emp=" + objZafParSis.getCodigoEmpresa() + " and st_reg='A' order by co_bod ";

                    rstLoc = stmLoc.executeQuery(strSQL);
                    while (rstLoc.next()) 
                    {
                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CHKBOD, new Boolean(false));
                        vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                        vecReg.add(INT_TBL_NOMBOD, rstLoc.getString("tx_nom"));
                        vecReg.add(INT_TBL_NATURA, "");
                        vecReg.add(INT_TBL_CHKPRE, new Boolean(false));

                        vecData.add(vecReg);
                    }
                    rstLoc.close();
                    rstLoc = null;

                    objTblMod.setData(vecData);
                    tblDat.setModel(objTblMod);

                    stmLoc.close();
                    stmLoc = null;
                    conn.close();
                    conn = null;

                }
            }
            catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
            catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
            System.gc();
            return blnRes;
        }

        public void clickModificar() 
        {
            try 
            {
                setEditable(true);

                java.awt.Color colBack;
                colBack = txtCodPrg.getBackground();

                bloquea(txtCodPrg, colBack, false);
                bloquea(txtNomPrg, colBack, false);
                bloquea(txtUsr, colBack, false);
                bloquea(txtNomUsr, colBack, false);

                butProg.setEnabled(false);
                butUsr.setEnabled(false);

                objTblMod.setDataModelChanged(false);
            }
            catch(Exception evt){ objUti.mostrarMsgErr_F1(this, evt); }
        }

        public void clickSiguiente() 
        {
            try 
            {
                if (rstCab != null)
                {
                    abrirCon();
                    if (!rstCab.isLast())
                    {
                        rstCab.next();
                        refrescaDatos(CONN_GLO, rstCab);
                    }
                    CerrarCon();
                }
            }
            catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
             catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
        }


        public void clickVisPreliminar()
        {
        }

        public boolean consultar()
        {
            return _consultar(FilSql());
        }

        private boolean _consultar(String strFil)
        {
            boolean blnRes = false;
            try 
            {

                abrirCon();
                if (CONN_GLO != null) 
                {
                    STM_GLO = CONN_GLO.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);

                    /*strSQL =" SELECT co_emp, co_loc, co_mnu, co_usr  FROM  tbr_bodlocprgusr "
                           +" WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " "
                           +" " + strFil + "  GROUP BY co_emp, co_loc, co_mnu, co_usr  ORDER BY co_usr  ";*/
                    
                    strSQL =" SELECT a.co_emp, a.co_loc, a.co_mnu, a.co_usr  "
                           +" FROM  tbr_bodlocprgusr  as a "
                           +" INNER JOIN tbm_usr as a1 ON (a.co_usr=a1.co_usr) "
                           +" WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() +""
                           +" AND a1.st_reg='A' " + strFil + " "
                           +" GROUP BY a.co_emp, a.co_loc, a.co_mnu, a.co_usr "
                           +" ORDER BY a.co_usr ";
                    
                    rstCab = STM_GLO.executeQuery(strSQL);
                    if (rstCab.next()) 
                    {
                        rstCab.last();
                        setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                        refrescaDatos(CONN_GLO, rstCab);
                        blnRes = true;
                    }
                    else 
                    {
                        setMenSis("0 Registros encontrados");
                        clnTextos();
                    }
                    CerrarCon();
                }
            }
            catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
            catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
            System.gc();
            return blnRes;
        }

        private String FilSql() 
        {
            String sqlFiltro = "";

            if(!txtCodPrg.getText().equals(""))
                sqlFiltro = sqlFiltro + " and a.co_mnu="+txtCodPrg.getText();

            if(!txtCodUsr.getText().equals(""))
               sqlFiltro = sqlFiltro + " and a.co_usr="+txtCodUsr.getText();

            return sqlFiltro ;
        }

        public void clnTextos() 
         {
            strCodPrg = "";
            strNomPrg = "";
            strUsr = "";
            strNomUsr = "";
            strCodUsr = "";

            txtCodPrg.setText("");
            txtNomPrg.setText("");

            txtCodUsr.setText("");
            txtUsr.setText("");
            txtNomUsr.setText("");

            objTblMod.removeAllRows();
        }

        private boolean refrescaDatos(java.sql.Connection conn, java.sql.ResultSet rstDatRec) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc, rstLoc02;
            String strSQL = "";
            String strAux = "";
            Vector vecData;
            try 
            {
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();

                    /**********CARGAR DATOS DE CABECERA ***************/
                    strSQL = "select a.co_emp, a.co_loc, a.co_mnu, a2.tx_nom, a.co_usr, a3.tx_usr, a3.tx_nom as usario "
                            + " from  tbr_bodlocprgusr as a "
                            + " inner join tbm_mnusis as a2 on (a2.co_mnu=a.co_mnu) "
                            + " inner join tbm_usr as a3 on (a3.co_usr=a.co_usr)"
                            + " where a.co_emp=" + rstDatRec.getInt("co_emp") + " and a.co_loc=" + rstDatRec.getInt("co_loc") + " "
                            + " and a.co_mnu= " + rstDatRec.getInt("co_mnu") + "  and a.co_usr=" + rstDatRec.getInt("co_usr") + " "
                            + " group by a.co_emp, a.co_loc,  a.co_mnu, a2.tx_nom, a.co_usr, a3.tx_usr, a3.tx_nom   ";
                    rstLoc02=stmLoc.executeQuery(strSQL);
                    if(rstLoc02.next())
                    {
                        txtCodPrg.setText(rstLoc02.getString("co_mnu"));
                        txtNomPrg.setText(rstLoc02.getString("tx_nom"));

                        txtCodUsr.setText(rstLoc02.getString("co_usr"));
                        txtUsr.setText(rstLoc02.getString("tx_usr"));
                        txtNomUsr.setText(rstLoc02.getString("usario"));
                    }
                    rstLoc02.close();
                    rstLoc02=null;

                   
                    /**********CARGAR DATOS DE DETALLE **************/
                    vecData = new Vector();

                    strSQL = "select  a4.co_bod, a4.tx_nom,  a.tx_natbod, a.st_reg  "
                            + " from  tbm_bod as a4 "
                            + " left join tbr_bodlocprgusr as a on (a.co_emp=a4.co_emp and a.co_bod=a4.co_bod and a.co_loc=" + rstDatRec.getInt("co_loc") + "  "
                            + " and a.co_mnu= " + rstDatRec.getInt("co_mnu") + "  and a.co_usr=" + rstDatRec.getInt("co_usr") + "  ) "
                            + " where a4.co_emp=" + rstDatRec.getInt("co_emp") + "  and a4.st_reg='A' order by a4.co_bod ";

                    rstLoc = stmLoc.executeQuery(strSQL);
                    while (rstLoc.next()) 
                    {
                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CHKBOD, new Boolean((rstLoc.getString("st_reg") == null ? false : (rstLoc.getString("st_reg").equals("") ? false : true))));
                        vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                        vecReg.add(INT_TBL_NOMBOD, rstLoc.getString("tx_nom"));
                        vecReg.add(INT_TBL_NATURA, "");


                        if (rstLoc.getString("tx_natbod") != null) 
                        {
                            if(rstLoc.getString("tx_natbod").equals("I"))
                               vecReg.setElementAt(new String("Ingreso"),INT_TBL_NATURA);
                            else if(rstLoc.getString("tx_natbod").equals("E"))
                               vecReg.setElementAt(new String("Egreso"),INT_TBL_NATURA);
                            else if(rstLoc.getString("tx_natbod").equals("A"))
                               vecReg.setElementAt(new String("Ingreso/Egreso"),INT_TBL_NATURA);
                        }
                        vecReg.add(INT_TBL_CHKPRE, new Boolean( (rstLoc.getString("st_reg")==null?false:(rstLoc.getString("st_reg").equals("P")?true:false))  ) );
                        vecData.add(vecReg);
                    }
                    rstLoc.close();
                    rstLoc=null;

                    objTblMod.setData(vecData);
                    tblDat .setModel(objTblMod);

                    /*********************/
                    stmLoc.close();
                    stmLoc = null;

                    strAux = "Activo";
                    objTooBar.setEstadoRegistro(strAux);

                    int intPosRel = rstDatRec.getRow();
                    rstDatRec.last();
                    objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstDatRec.getRow());
                    rstDatRec.absolute(intPosRel);

                    blnRes = true;
                }
            }
            catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
            catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
            return blnRes;
        }


        public boolean eliminar()
        {
            return true;
        }

        /**
         * valida campos requeridos antes de insertar o modificar
         *
         * @return true si esta todo bien false falta algun dato
         */
        private boolean validaCampos() 
        {

            int intExiDatTbl = 0;
            int intExiDatPre = 0;
            int intExiDatNat = 0;

            if (txtCodPrg.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("El campo << Programa >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                txtCodPrg.requestFocus();
                return false;
            }

            if (txtCodUsr.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("El campo << Usuario >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                txtCodUsr.requestFocus();
                return false;
            }

            String strNatura = "";
            for (int i = 0; i < tblDat.getRowCount(); i++) {
                if (tblDat.getValueAt(i, INT_TBL_CHKBOD).toString().equals("true")) {
                    intExiDatTbl = 1;

                    strNatura = tblDat.getValueAt(i, INT_TBL_NATURA) == null ? "" : (tblDat.getValueAt(i, INT_TBL_NATURA).equals("null") ? "" : tblDat.getValueAt(i, INT_TBL_NATURA).toString());
                    if (strNatura.equals("")) {
                        intExiDatNat = 1;
                    }

                    if (tblDat.getValueAt(i, INT_TBL_CHKPRE).toString().equals("true")) {
                        intExiDatPre = 1;
                    }
                }
            }

            if (intExiDatTbl == 0) {
                mostrarMsgInf("NO HAY DATOS EN DETALLE INGRESE DATOS.... ");
                return false;
            }

            if (intExiDatNat == 1) {
                mostrarMsgInf("SELECCIONE LA NATURALEZA DE LA BODEGA.... ");
                return false;
            }

            if (intExiDatPre == 0) {
                mostrarMsgInf("SELECCIONE LA BODEGA PREDETERMINADO.... ");
                return false;
            }

            return true;
        }

        public boolean insertar() 
        {
            boolean blnRes = false;
            java.sql.Connection conn;
            java.sql.Statement stmLoc;
            try 
            {
                if (validaCampos()) 
                {
                    conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                    if (conn != null) {
                        conn.setAutoCommit(false);
                        stmLoc = conn.createStatement();

                        if (getVerificaExit(conn)) 
                        {
                            if (insertarCab(conn))
                            {
                                conn.commit();
                                blnRes = true;
                            } 
                            else 
                                conn.rollback();
                        } 
                        else 
                            conn.rollback();
                        

                        stmLoc.close();
                        stmLoc = null;
                        conn.close();
                        conn = null;
                    }
                }
            }
            catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
            catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
            return blnRes;
        }

        private boolean getVerificaExit(java.sql.Connection conn) 
        {
            boolean blnRes = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSQL = "";
            try 
            {
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();

                    strSQL = "SELECT co_mnu FROM tbr_bodlocprgusr WHERE  "
                            + " co_emp=" + objZafParSis.getCodigoEmpresa() + " and  co_loc=" + objZafParSis.getCodigoLocal() + " and  co_mnu=" + txtCodPrg.getText() + " "
                            + " and co_usr= " + txtCodUsr.getText() + " ";
                    rstLoc = stmLoc.executeQuery(strSQL);
                    if (rstLoc.next()) 
                    {
                        mostrarMsgInf("YA EXISTE DATOS INGRESADOS PARA ESTE USUARIO ");
                        blnRes = false;
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                }
            }
            catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
            catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
            return blnRes;
        }

        private boolean insertarCab(java.sql.Connection conn) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSQL = "";
            String strEst = "", strNat = "";
            String strNatura = "";
            String strCodBod = "";
            try 
            {
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();

                    for (int i = 0; i < tblDat.getRowCount(); i++)
                    {
                        if (tblDat.getValueAt(i, INT_TBL_CHKBOD).toString().equals("true")) 
                        {
                            strEst = "A";

                            strCodBod = tblDat.getValueAt(i, INT_TBL_CODBOD).toString();

                            strNatura = tblDat.getValueAt(i, INT_TBL_NATURA) == null ? "" : (tblDat.getValueAt(i, INT_TBL_NATURA).equals("null") ? "" : tblDat.getValueAt(i, INT_TBL_NATURA).toString());
                            if (strNatura.equals("Ingreso")) 
                            {
                                strNat = "I";
                            }
                            else if (strNatura.equals("Egreso")) 
                            {
                                strNat = "E";
                            } 
                            else if (strNatura.equals("Ingreso/Egreso")) 
                            {
                                strNat = "A";
                            }

                            if (tblDat.getValueAt(i, INT_TBL_CHKPRE).toString().equals("true")) 
                            {
                                strEst = "P";
                            }

                            strSQL = "INSERT INTO tbr_bodlocprgusr(co_emp, co_loc, co_mnu, co_usr, co_bod, tx_natbod, st_reg) "
                                    + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodPrg.getText() + " "
                                    + " ," + txtCodUsr.getText() + ", " + strCodBod + ", '" + strNat + "', '" + strEst + "'   ) ";
                            stmLoc.executeUpdate(strSQL);

                        }
                    }
                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            }
            catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
            catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
            return blnRes;
        }

        public boolean modificar() 
        {
            boolean blnRes = false;
            java.sql.Connection conn;
            try 
            {
                if (validaCampos())
                {
                    conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                    if (conn != null) 
                    {
                        conn.setAutoCommit(false);

                        if (modificarCab(conn)) 
                        {
                            conn.commit();
                            blnRes = true;
                        }
                        else
                        {
                            conn.rollback();
                        }

                        conn.close();
                        conn = null;
                    }

                }
            } 
            catch (java.sql.SQLException Evt) {         blnRes = false;      objUti.mostrarMsgErr_F1(this, Evt);         }
            catch (Exception Evt) {        blnRes = false;             objUti.mostrarMsgErr_F1(this, Evt);       }
            return blnRes;
        }

        private boolean modificarCab(java.sql.Connection conn)
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSQL = "";
            String strEst = "", strNat = "";
            String strNatura = "";
            String strCodBod = "";
            try 
            {
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();

                    strSQL = "DELETE FROM tbr_bodlocprgusr WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " and "
                            + " co_loc=" + objZafParSis.getCodigoLocal() + " and  co_mnu=" + txtCodPrg.getText() + " "
                            + " and co_usr=" + txtCodUsr.getText() + "  ";
                    stmLoc.executeUpdate(strSQL);

                    for (int i = 0; i < tblDat.getRowCount(); i++)
                    {
                        if (tblDat.getValueAt(i, INT_TBL_CHKBOD).toString().equals("true")) {
                            strEst = "A";

                            strCodBod = tblDat.getValueAt(i, INT_TBL_CODBOD).toString();

                            strNatura = tblDat.getValueAt(i, INT_TBL_NATURA) == null ? "" : (tblDat.getValueAt(i, INT_TBL_NATURA).equals("null") ? "" : tblDat.getValueAt(i, INT_TBL_NATURA).toString());
                            if (strNatura.equals("Ingreso")) {
                                strNat = "I";
                            } else if (strNatura.equals("Egreso")) {
                                strNat = "E";
                            } else if (strNatura.equals("Ingreso/Egreso")) {
                                strNat = "A";
                            }

                            if (tblDat.getValueAt(i, INT_TBL_CHKPRE).toString().equals("true")) {
                                strEst = "P";
                            }

                            strSQL = "INSERT INTO tbr_bodlocprgusr(co_emp, co_loc, co_mnu, co_usr, co_bod, tx_natbod, st_reg) "
                                    + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodPrg.getText() + " "
                                    + " ," + txtCodUsr.getText() + ", " + strCodBod + ", '" + strNat + "', '" + strEst + "'   ) ";
                            stmLoc.executeUpdate(strSQL);

                        }
                    }

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            }
            catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
            catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
            return blnRes;
        }

        public boolean cancelar() 
        {
            boolean blnRes = true;

            try 
            {
                if (rstCab != null) 
                {
                    rstCab.close();
                    if (STM_GLO != null) 
                    {
                        STM_GLO.close();
                        STM_GLO = null;
                    }
                    rstCab = null;
                }
            } 
            catch (java.sql.SQLException e) {   objUti.mostrarMsgErr_F1(this, e);     } 
            catch (Exception e) {     objUti.mostrarMsgErr_F1(this, e);   }      
            clnTextos();
            return blnRes;
        }

        public boolean vistaPreliminar()
        {
            return true;
        }

        public boolean aceptar()
        {
            return true;
        }

        public boolean imprimir()
        {
            return true;
        }

        public boolean beforeInsertar(){
            boolean blnRes=true;

            return blnRes;
        }

        public boolean beforeConsultar(){
            boolean blnRes=true;

            return blnRes;
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

        public boolean afterInsertar()
        {
            objTooBar.setEstado('w');
            return true;
        }

        public boolean afterConsultar()
        {
            return true;
        }

        public boolean afterModificar()
        {
            return true;
        }

        public boolean afterEliminar()
        {
            return true;
        }

        public boolean afterAnular()
        {
            return true;
        }

        public boolean afterImprimir()
        {
            return true;
        }

        public boolean afterVistaPreliminar()
        {
            return true;
        }

        public boolean afterAceptar()
        {
            return true;
        }

        public boolean afterCancelar()
        {
            return true;
        }
        
        public void setEditable(boolean editable) 
        {
            if (editable == true) 
            {
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            }
            else 
            {
                objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            }
        }
        
        private void bloquea(javax.swing.JTextField txtFiel, java.awt.Color colBack, boolean blnEst) 
        {
            colBack = txtFiel.getBackground();
            txtFiel.setEditable(blnEst);
            txtFiel.setBackground(colBack);
        }
        

    }
    


}

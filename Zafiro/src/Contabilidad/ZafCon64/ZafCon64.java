/*
 * ZafCon64.java
 *
 * Created on 04 de abril del 2018
 */
package Contabilidad.ZafCon64;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafVenCon.ZafVenCon;
import java.math.BigDecimal;
import java.util.Vector;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author sistemas9
 */
public class ZafCon64 extends javax.swing.JInternalFrame
{
    //Constantes
    private int INT_LINEA       = 0; //0) Línea: Se debe asignar una cadena vacía o null.
    private int INT_VEC_CODCTA  = 1; //1) Código de la cuenta (Sistema).
    private int INT_VEC_NUMCTA  = 2; //2) Número de la cuenta (Preimpreso).
    private int INT_VEC_BOTON   = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
    private int INT_VEC_NOMCTA  = 4; //4) Nombre de la cuenta.
    private int INT_VEC_DEBE    = 5; //5) Debe.
    private int INT_VEC_HABER   = 6; //6) Haber.
    private int INT_VEC_REF     = 7; //7) Referencia: Se debe asignar una cadena vacía o null
    private int INT_VEC_EST_CON = 8; //8) Referencia: Se debe asignar una cadena vacía o null

    //ArrayList: para consultar Asientos de Diario
    private ArrayList arlDatConAsiDia, arlRegConAsiDia;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;  
    private static final int INT_ARL_CON_TXT_USRING=4;
    private static final int INT_ARL_CON_TXT_USRMOD=5;
    private int intIndiceAsiDia=0;   
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private MiToolBar objTooBar;
    private ZafAsiDia objAsiDia;
    private ZafDocLis objDocLis;
    private ZafVenCon vcoTipDoc;
    private ZafRptSis objRptSis;    
    private ZafThreadGUIVisPre objThrGUIVisPre;
    private ZafDatePicker dtpFecDoc;
    private java.util.Date datFecAux;
    private Vector vecReg, vecDat;
    private boolean blnHayCam;                              //Determina si hay cambios en el formulario.
    
    private BigDecimal bdeValCtaBanHab;
    
    private String strDesCorTipDoc, strDesLarTipDoc;        //Contenido del campo al obtener el foco.
    private String strFecDocIni, strEstImpDoc, strNumDia_Glo;
    private String strCtaPadExi; //si proviene desde el programa de otros movimientos bancarios pero de grupo.
    private String strSQL, strAux;
    
    public String strVer=" v0.1.23 ";
	
    /** Crea una nueva instancia de la clase ZafCon64. */
    public ZafCon64(ZafParSis obj)
    {
        try{
            strCtaPadExi="";
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
    
    public ZafCon64(ZafParSis obj, Integer codigoCuenta){
        try{
            strCtaPadExi="";
            objParSis=(ZafParSis)obj.clone();
            objTooBar.setEstado('c');
            objTooBar.consultar();
            objTooBar.setEstado('w');
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /** Crea una nueva instancia de la clase ZafCon64. */
    public ZafCon64(ZafParSis obj, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento, int codigoMenu)
    {
        try{

            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            initComponents();

            txtCodTipDoc.setText(""+codigoTipoDocumento);
            txtCodDoc.setText(""+codigoDocumento);
            objParSis.setCodigoMenu(codigoMenu);

            if (!configurarFrm())
                exitForm();
            agregarDocLis();
            consultarReg();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    public ZafCon64(ZafParSis obj, int codigoEmpresaBancaria, String existePadreGrupo, String codigoCuenta, String numeroCuenta, String nombreCuenta, BigDecimal valorTransferencia){
        try{
            objParSis=(ZafParSis)obj.clone();
            objParSis.setCodigoEmpresa(codigoEmpresaBancaria);
            strCtaPadExi=existePadreGrupo;
            vecDat=new Vector();
            initComponents();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();
            
            objTooBar.clickInsertar();
            objTooBar.setEstado('n');
            lblTit.setText("Transferencia bancaria al exterior...");
            bdeValCtaBanHab=valorTransferencia;

            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, codigoCuenta);
            vecReg.add(INT_VEC_NUMCTA, numeroCuenta); ///para probar///
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, nombreCuenta); ///para probar///
            vecReg.add(INT_VEC_DEBE, new BigDecimal("0"));
            vecReg.add(INT_VEC_HABER, objUti.redondearBigDecimal(bdeValCtaBanHab, objParSis.getDecimalesMostrar()));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_EST_CON, "B");
            vecDat.add(vecReg);
            objAsiDia.setDetalleDiario(vecDat);
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafDatePicker:
            dtpFecDoc=new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCabGen.add(dtpFecDoc);
            dtpFecDoc.setBounds(578, 4, 100, 20);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            objAsiDia=new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
            objAsiDia.inicializar();            
            
            objDocLis=new ZafDocLis();
            panBar.add(objTooBar);
            this.setTitle(objParSis.getNombreMenu() + strVer); //Facturas Importaciones
            lblTit.setText(objParSis.getNombreMenu());
            panGenDet.add(objAsiDia,java.awt.BorderLayout.CENTER);
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            //Ocultar objetos del sistema.
            txtCodTipDoc.setVisible(false);
            
            configurarVenConTipDoc();

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                if(objParSis.getCodigoUsuario()==1){
                    strSQL ="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocPrg";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    if(strCtaPadExi.equals("S"))
                        strSQL+=" AND co_tipdoc=87";
                    else
                        strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                }
                else{
                    strSQL ="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    if(strCtaPadExi.equals("S")){
                        strSQL+=" (";
                        strSQL+=" SELECT co_tipDoc";
                        strSQL+=" FROM tbr_tipDocPrg";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                        strSQL+=" AND co_tipdoc=87";
                        strSQL+=" )";
                    }
                    else{
                        strSQL+=" (";
                        strSQL+=" SELECT co_tipDoc";
                        strSQL+=" FROM tbr_tipDocUsr";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                        strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                        strSQL+=" AND st_reg='S'";
                        strSQL+=" )";
                    }
                }
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    if(objTooBar.getEstado()=='n'){
                        txtNumDia.setText("" + (rst.getInt("ne_ultDoc")+1));
                        txtNumDia.selectAll();
                        txtNumDia.requestFocus();
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
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() +"";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbr_tipDocUsr AS a2 inner join  tbm_cabTipDoc AS a1";
                strSQL+=" ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            }

            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
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
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        txtNumDia.selectAll();
                        txtNumDia.requestFocus();
                    }
                    break;
                case 1: //Búsqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        txtNumDia.selectAll();
                        txtNumDia.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            txtNumDia.selectAll();
                            txtNumDia.requestFocus();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripcián larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        txtNumDia.selectAll();
                        txtNumDia.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            txtNumDia.selectAll();
                            txtNumDia.requestFocus();
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
            blnRes=false;
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
        panCab = new javax.swing.JPanel();
        panCabGen = new javax.swing.JPanel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        lblTipDoc = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        txtDesLarTipDoc = new javax.swing.JTextField();
        lblNumDia = new javax.swing.JLabel();
        txtNumDia = new javax.swing.JTextField();
        lblFecDoc1 = new javax.swing.JLabel();
        panDet = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Información del registro actual");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(100, 68));
        panCab.setLayout(new java.awt.BorderLayout());

        panCabGen.setPreferredSize(new java.awt.Dimension(0, 46));
        panCabGen.setLayout(null);
        panCabGen.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(90, 4, 20, 20);

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
        panCabGen.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(110, 4, 80, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCabGen.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 120, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCabGen.add(lblCodDoc);
        lblCodDoc.setBounds(0, 24, 120, 20);
        panCabGen.add(txtCodDoc);
        txtCodDoc.setBounds(110, 24, 80, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabGen.add(butTipDoc);
        butTipDoc.setBounds(430, 4, 20, 20);

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
        panCabGen.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(190, 4, 240, 20);

        lblNumDia.setText("Número de diario:");
        lblNumDia.setToolTipText("Número de diario");
        panCabGen.add(lblNumDia);
        lblNumDia.setBounds(450, 24, 100, 20);
        panCabGen.add(txtNumDia);
        txtNumDia.setBounds(578, 24, 100, 20);

        lblFecDoc1.setText("Fecha del documento:");
        lblFecDoc1.setToolTipText("Fecha del documento");
        panCabGen.add(lblFecDoc1);
        lblFecDoc1.setBounds(450, 4, 130, 20);

        panCab.add(panCabGen, java.awt.BorderLayout.NORTH);

        panGen.add(panCab, java.awt.BorderLayout.NORTH);

        panDet.setPreferredSize(new java.awt.Dimension(100, 110));
        panDet.setLayout(new java.awt.BorderLayout());

        panGenDet.setLayout(new java.awt.BorderLayout());
        panDet.add(panGenDet, java.awt.BorderLayout.CENTER);

        panGen.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            }
            else   {
                mostrarVenConTipDoc(2);
            }
        }
        else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else   {
                mostrarVenConTipDoc(1);
            }
        }
        else
            txtDesCorTipDoc.setText(strDesCorTipDoc);
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

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
        catch (Exception e)  {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
            ZafCon64_01 objCon64_01=new ZafCon64_01(objParSis);
            this.getParent().add(objCon64_01, javax.swing.JLayeredPane.DEFAULT_LAYER);
            this.dispose();
            objCon64_01.show();
        }
    }//GEN-LAST:event_formInternalFrameOpened
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc1;
    private javax.swing.JLabel lblNumDia;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCabGen;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNumDia;
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
     * Esta función muestra un mensaje de advertencia al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        int intTipCamFec;
        String strFecDocTmp="";
        String strFecAuxTmp="";
        boolean blnExiNumDiaRep;
        
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        
        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el asiento de diario y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        else{
            intTipCamFec=getTipModFecUsr();
            switch(intTipCamFec){
                case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                    if(objParSis.getCodigoUsuario()!=1){
                        if(objTooBar.getEstado()=='n'){//insertar
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            String strMsj="";
                            strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                            strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                            strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                            strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                            strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                            strMsj+="<BR>      El documento se guardará con fecha del día así ud. coloque otra fecha.";
                            strMsj+="<BR>  Está seguro que desea continuar?</HTML>";
                            //mostrarMsgInf("<HTML> " + strMsj + "</HTML>");
                            
                            switch (mostrarMsgCon(strMsj)){
                                case 0: //YES_OPTION
                                    return true;
                                case 1: //NO_OPTION
                                    return false;
                                case 2: //CANCEL_OPTION
                                    return false;
                            }
                            datFecAux=null;
                        }
                        else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            String strMsj="";
                            strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                            strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                            strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                            strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                            strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                            strMsj+="<BR>      El documento se guardará con la fecha inicialmente almacenada así ud. coloque otra fecha.";
                            strMsj+="<BR>  Está seguro que desea continuar?</HTML>";
                            //mostrarMsgInf("<HTML> " + strMsj + "</HTML>");
                            
                            switch (mostrarMsgCon(strMsj)){
                                case 0: //YES_OPTION
                                    return true;
                                case 1: //NO_OPTION
                                    return false;
                                case 2: //CANCEL_OPTION
                                    return false;
                            }

                            return true;
                        }
                    }
                    break;
                case 1://no puede cambiarla para nada
                    if(objTooBar.getEstado()=='n'){//insertar
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) != 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permisos para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) != 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permiso para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }
                    
                    break;
                case 2://la fecha puede ser menor o igual a la q se presenta
                    if(objTooBar.getEstado()=='n'){//insertar
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) > 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha ingresada en el documento es mayor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha posterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) > 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha de modificación del documento es mayor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha posterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }
                    break;
                case 3://la fecha puede ser mayor o igual a la q se presenta
                    if(objTooBar.getEstado()=='n'){//insertar
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) < 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha ingresada en el documento es menor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha anterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) < 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha de modificación del documento es menor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha anterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }
        
        //Validar que el "Cádigo alterno" no se repita.
        if (!txtNumDia.getText().equals(""))
        {   //--- Inicio bloque comentado el 25/Ene/2018 ---
            //Este bloque fue comentado en la version del programa v0.15.8 (01/Nov/2016)
            /*
            strSQL="";
            strSQL+="SELECT a1.tx_numDia";
            strSQL+=" FROM tbm_cabDia AS a1 ";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
            strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
            strSQL+=" AND a1.tx_numDia='" + txtNumDia.getText().replaceAll("'", "''") + "'";
            
            if (objTooBar.getEstado() == 'm')
                strSQL+=" AND a1.co_dia<>" + txtCodDoc.getText();
            
            if (objTooBar.getEstado() == 'n')
            {  if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL))
                {   tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El número de diario <FONT COLOR=\"blue\">" + txtNumDia.getText() + "</FONT> ya existe.<BR>Escriba otro número de diario y vuelva a intentarlo.</HTML>");
                    txtNumDia.selectAll();
                    txtNumDia.requestFocus();
                    return false;
                }
            }
            */
            //--- Fin bloque comentado el 25/Ene/2018 ------
            
            if (objTooBar.getEstado() == 'n' || objTooBar.getEstado() == 'm')
            {  //n = nuevo; m = modificar
               strNumDia_Glo = ""; //Esta variable global es llenada en verificaExisteNumAsientoDiarioRepetido().
               blnExiNumDiaRep = verificaExisteNumAsientoDiarioRepetido(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtNumDia.getText());
                
               if (blnExiNumDiaRep == true)
               {  strAux = "<HTML>El número de diario digitado por el usuario <FONT COLOR=\"blue\">(" + txtNumDia.getText() + ")</FONT> ya existe.<BR>";
                  strAux += "Por tanto, se procedió a asignar otro número disponible <FONT COLOR=\"blue\">(" + strNumDia_Glo + ")</FONT>.</HTML>";
                  mostrarMsgInf(strAux);
                  txtNumDia.setText(strNumDia_Glo);
               }
            }
        } //if (!txtNumDia.getText().equals(""))
        
        //Validar que el "Diario está cuadrado".
        if (!objAsiDia.isDiarioCuadrado())
        {
            mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
            return false;
        }
        //Validar que coincida el valor inicial del asiento con el valor actual.
        if (    (!txtCodTipDoc.getText().equals("30")  &&  !txtCodTipDoc.getText().equals("86")  &&  !txtCodTipDoc.getText().equals("87")   &&  !txtCodTipDoc.getText().equals("88")  &&  !txtCodTipDoc.getText().equals("89")  
                &&  !txtCodTipDoc.getText().equals("113")  &&  !txtCodTipDoc.getText().equals("92")   &&  (!txtCodTipDoc.getText().equals("184"))   &&  (!txtCodTipDoc.getText().equals("288")) &&  (!txtCodTipDoc.getText().equals("194"))   )
                && objUti.redondear(objAsiDia.getMontoInicialDebe(), objParSis.getDecimalesMostrar()) != objUti.redondear(objAsiDia.getMontoDebe(), objParSis.getDecimalesMostrar())  )
        {
            mostrarMsgInf("<HTML>El valor inicial del asiento de diario era <FONT COLOR=\"blue\">" + objAsiDia.getMontoInicialDebe() + "</FONT> y actualmente es de <FONT COLOR=\"blue\">" + objAsiDia.getMontoDebe() + "</FONT>.<BR>El valor actual debe coincidir con el valor inicial del asiento de diario.<BR>Cuadre el valor actual con el valor inicial y vuelva a intentarlo.</HTML>");
            return false;
        }
        return true;
    }
    
    private boolean verificaExisteCtasDetalleAsientoDiario(Connection conexion, int intCodEmp, int intCodLoc, int intCodTipDoc, String strNumDia)
    {
        boolean blnRes = false;
        try
        {
            stm = conexion.createStatement();
            strSQL =  "select count(a.*) as cont_reg ";
            strSQL += "from tbm_detdia as a ";
            strSQL += "inner join ( select co_emp, co_loc, co_tipdoc, co_dia, tx_numdia ";
            strSQL += "             from tbm_cabdia ";
            strSQL += "             where st_reg = 'A' ";
            strSQL += "                and co_emp = " + intCodEmp;
            strSQL += "                and co_loc = " + intCodLoc;
            strSQL += "                and co_tipdoc = " + intCodTipDoc;
            strSQL += "                and tx_numdia = '" + strNumDia + "'";
            strSQL += "           ) as b on a.co_emp = b.co_emp and a.co_loc = b.co_loc and a.co_tipdoc = b.co_tipdoc and a.co_dia = b.co_dia ";
            rst = stm.executeQuery(strSQL);

            if (rst.next())
            {  if (rst.getInt("cont_reg") != 0)
               {  //Si es <> 0, significa que se encontro alguna cuenta en la tabla tbm_detdia
                  blnRes = true;
               }
               else
                  blnRes = false;
            }
            rst.close();
            rst = null;
            stm.close();
            stm = null;
        } 
        catch(Exception e)
        {
           blnRes = false;
        }
        return blnRes;
    } 
    
    private boolean verificaExisteNumAsientoDiarioRepetido(int intCodEmp, int intCodLoc, int intCodTipDoc, String strNumDia)
    {
        boolean blnRes = false, blnConBuc;
        int intAux;
        String strNumDiaAux;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try
        {
            rstLoc = null;
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());

            if (conLoc != null)
            {  stmLoc = conLoc.createStatement();
               blnConBuc = true; //Variable booleana que indica si debe o no continuar en el bucle.
               strNumDiaAux = strNumDia;

               while (blnConBuc == true)
               {  strSQL =  " select co_emp, co_loc, co_tipdoc, co_dia, tx_numdia ";
                  strSQL += " from tbm_cabdia ";
                  strSQL += " where st_reg = 'A'";
                  strSQL += " and co_emp = " + intCodEmp;
                  strSQL += " and co_loc = " + intCodLoc;
                  strSQL += " and co_tipdoc = " + intCodTipDoc;
                  strSQL += " and tx_numdia = '" + strNumDiaAux + "'";

                  if (objTooBar.getEstado() == 'm')
                  {  //m = modificar.
                     strSQL += " and co_dia <> " + txtCodDoc.getText();
                  }

                  rstLoc = stmLoc.executeQuery(strSQL);

                  if (rstLoc.next())
                  {  //Significa que el numero de Diario si existe. Por tanto, se va a obtener un nuevo numero secuencial.
                     blnRes = true;
                     intAux = Integer.parseInt(strNumDiaAux) + 1;
                     strNumDiaAux = Integer.toString(intAux);
                  }
                  else
                  {  //El numero de Diario no existe.
                     blnConBuc = false; //Se debe salir del bucle
                     strNumDia_Glo = strNumDiaAux;
                  }
               }

               rstLoc.close();
               rstLoc = null;
               stmLoc.close();
               stmLoc = null;
               conLoc.close();
               conLoc = null;
            }
        } 
        catch(Exception e)
        {
           blnRes = false;
        }
        return blnRes;
    } 
    
    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            dtpFecDoc.setText("");
            txtCodDoc.setText("");
            txtNumDia.setText("");
            objAsiDia.inicializar();    
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    private boolean inactivarCampos(){
        boolean blnRes=true;
        try{
            int intTipModDoc;
            intTipModDoc=getTipModDocUsr();     
            objAsiDia.setEditable(true);
            switch(intTipModDoc){
                case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                    if(objParSis.getCodigoUsuario()!=1){
                        if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                            dtpFecDoc.setEnabled(false);
                            txtNumDia.setEditable(false);
                            txtNumDia.setBackground(new java.awt.Color(255, 255, 255));
                            objAsiDia.setEditable(false);
                            if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                            }
                            else{
                                String strMsj="";
                                strMsj+="<HTML>EL documento no se puede modificar por las siguientes razones:";
                                strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                                strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                                strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                                strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios al documento.";
                                strMsj+="</HTML>";
                                mostrarMsgInf(strMsj);
                            }
                        }
                    }
                    break;
                case 1://no puede modificar nada, incluyendo fecha del documento
                    if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        dtpFecDoc.setEnabled(false);
                        txtNumDia.setEditable(false);
                        txtNumDia.setBackground(new java.awt.Color(255, 255, 255));
                        objAsiDia.setEditable(false);
                        if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                        }
                        else
                            mostrarMsgInf("<HTML>Ud. no cuenta con ningún tipo de permiso para Modificar.<BR>.Solicite a su Adminsitrador del Sistema dicho permiso y vuelva a intentarlo.</HTML>");
                    }
                    
                    break;
                case 2://modificación parcial la modificación de la fecha dependerá de si se cuenta con este permiso
                    if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        if( ! strEstImpDoc.equals("S")){//si el documento no está impreso se puede modificar, la modif. de fecha depende de tbr_tipDocUsr.ne_tipresmodfecdoc
                            dtpFecDoc.setEnabled(true);
                        }
                        else{//si el documento está impreso no se permite modificar
                            dtpFecDoc.setEnabled(false);
                            txtNumDia.setEditable(false);
                            txtNumDia.setBackground(new java.awt.Color(255, 255, 255));
                            objAsiDia.setEditable(false);
                            if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                            }
                            else
                                mostrarMsgInf("<HTML>El documento consultado no se puede modificar porque ya está impreso.</HTML>");
                        }
                    }
                    break;
                case 3://modificación completa, la modificación de la fecha dependerá de si se cuenta con este permiso
                    dtpFecDoc.setEnabled(true);
                    break;
                default:
                    break;
            }
            objTooBar.setOperacionSeleccionada("n");
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private int getTipModFecUsr(){
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        int intTipModFec=0;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    intTipModFec=4;
                }
                else{
                    strSQL ="";
                    strSQL+=" SELECT ne_tipresmodfecdoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                    rstLoc=stmLoc.executeQuery(strSQL);
                    while(rstLoc.next()){
                        intTipModFec=rstLoc.getInt("ne_tipresmodfecdoc");
                    }
                    stmLoc.close();
                    stmLoc=null;
                    rstLoc.close();
                    rstLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intTipModFec;
    }
    
    private int getTipModDocUsr(){
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        int intTipModTipDocUsr=0;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    intTipModTipDocUsr=3;
                }
                else{
                    strSQL ="";
                    strSQL+=" SELECT ne_tipresmoddoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                    rstLoc=stmLoc.executeQuery(strSQL);
                    while(rstLoc.next()){
                        intTipModTipDocUsr=rstLoc.getInt("ne_tipresmoddoc");
                    }
                    stmLoc.close();
                    stmLoc=null;
                    rstLoc.close();
                    rstLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intTipModTipDocUsr;
    }
    
    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algán cambio que se deba grabar
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
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDia.getDocument().addDocumentListener(objDocLis);
    }   

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        if(objAsiDia.isDiarioCuadrado()){                            
                            blnRes=objTooBar.insertar();
                        }
                        else{
                            mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
                            blnRes=false;
                        }
                        
                        break;
                    case 'm': //Modificar
                        if(objAsiDia.isDiarioCuadrado()){
                            blnRes=objTooBar.modificar();
                        }
                        else{
                            mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
                            blnRes=false;
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

    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegacián
     * que permiten desplazarse al primero, anterior, siguiente y áltimo registro.
     */
    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objParSis);
        }

        public void clickInicio() {
            try{
                if(arlDatConAsiDia.size()>0){
                    if(intIndiceAsiDia>0){
                        if (blnHayCam || objAsiDia.isDiarioModificado()) {
                            if (isRegPro()) {
                                intIndiceAsiDia=0;
                                cargarReg();
                                inactivarCampos();
                            }
                        }
                        else {
                            intIndiceAsiDia=0;
                            cargarReg();
                            inactivarCampos();
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
                if(arlDatConAsiDia.size()>0){
                    if(intIndiceAsiDia>0){
                        if ( blnHayCam || objAsiDia.isDiarioModificado() ){
                            if (isRegPro()) {
                                intIndiceAsiDia--;
                                cargarReg();
                                inactivarCampos();
                            }
                        }
                        else {
                            intIndiceAsiDia--;
                            cargarReg();
                            inactivarCampos();
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
                if(arlDatConAsiDia.size()>0){
                    if(intIndiceAsiDia < arlDatConAsiDia.size()-1){
                        if (blnHayCam || objAsiDia.isDiarioModificado()){
                            if (isRegPro()) {
                                intIndiceAsiDia++;
                                cargarReg();
                                inactivarCampos();
                            }
                        }
                        else {
                            intIndiceAsiDia++;
                            cargarReg();
                            inactivarCampos();
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
                if(arlDatConAsiDia.size()>0){
                    if(intIndiceAsiDia<arlDatConAsiDia.size()-1){
                        if (blnHayCam || objAsiDia.isDiarioModificado() ) {
                            if (isRegPro()) {
                                intIndiceAsiDia=arlDatConAsiDia.size()-1;
                                cargarReg();
                                inactivarCampos();
                            }
                        }
                        else {
                            intIndiceAsiDia=arlDatConAsiDia.size()-1;
                            cargarReg();
                            inactivarCampos();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        } 
        
        public void clickConsultar() 
        {
            txtDesCorTipDoc.setEnabled(true);
            txtDesLarTipDoc.setEnabled(true);
            txtCodDoc.setEnabled(true);
            txtDesCorTipDoc.requestFocus();

            mostrarTipDocPre();
        }

        public void clickInsertar()
        {
            try
            {
                if (blnHayCam)
                {
                    isRegPro();
                }
                limpiarFrm();
                txtCodDoc.setEditable(false);
                dtpFecDoc.setText(objUti.formatearFecha(objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos()),"dd/MM/yyyy"));
                    
                objAsiDia.setEditable(true);    
                mostrarTipDocPre();                
                txtNumDia.requestFocus();
                //Inicializar las variables que indican cambios.
                objAsiDia.setDiarioModificado(false);
                blnHayCam=false;
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickModificar()
        {
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            txtCodDoc.setEditable(false);
            txtNumDia.selectAll();
            txtNumDia.requestFocus();
            objAsiDia.setEditable(true);
            setHabDesCam();
            cargarReg();
            inactivarCampos();
        }        

        public void clickAceptar()
        {            
        }

        public void clickAnular()
        {
        }

        public void clickCancelar()
        {
        }

        public void clickEliminar()
        {
        }

        public void clickImprimir()
        {
        }

        public void clickVisPreliminar() 
        {
        }

        public boolean consultar() 
        {
            consultarReg();
            return true;
        }

        public boolean insertar()
        {
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean modificar()
        {
            if (!actualizarReg())
                return false;
            return true;
        }        

        public boolean eliminar() {
            try{
                if (!eliminarReg())
                    return false;
                
                //Desplazarse al siguiente registro si es posible.
                if(intIndiceAsiDia<arlDatConAsiDia.size()-1){
                    intIndiceAsiDia++;
                    cargarReg();
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }                  
                blnHayCam=false;
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
                return true;
            }
            return true;
        }

        public boolean anular()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }      
        
        public boolean imprimir()
        {
            if (objThrGUIVisPre==null)
            {
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(0);
                objThrGUIVisPre.start();
            }
            return true;
        }
        
        public boolean vistaPreliminar()
        {
            if (objThrGUIVisPre==null)
            {
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(1);
                objThrGUIVisPre.start();
            }
            return true;
        } 
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (blnHayCam || objAsiDia.isDiarioModificado())
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

        public boolean aceptar()
        {
            return true;
        }
        
        public boolean afterConsultar()
        {
            dtpFecDoc.setEnabled(false);
            return true;
        }
        
        public boolean afterInsertar()
        {
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            objTooBar.setEstado('w');
            consultarReg();
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            return true;
        } 
        
        public boolean afterModificar()
        {
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            strFecDocIni=dtpFecDoc.getText();
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
        
        public boolean beforeConsultar()
        {
            return true;
        }
        
        public boolean beforeInsertar()
        {
            if(objParSis.getCodigoMenu()==327)
            {
                if (  (!txtCodTipDoc.getText().equals("30")) && (!txtCodTipDoc.getText().equals("86"))  && (!txtCodTipDoc.getText().equals("87")) &&  (!txtCodTipDoc.getText().equals("88"))  &&  (!txtCodTipDoc.getText().equals("89"))
                       &&  (!txtCodTipDoc.getText().equals("113"))  &&  (!txtCodTipDoc.getText().equals("92"))   &&  (!txtCodTipDoc.getText().equals("184"))  &&  (!txtCodTipDoc.getText().equals("288")) &&  (!txtCodTipDoc.getText().equals("194")) 
                   )
                {
                    mostrarMsgInf("Sólo es posible ingresar documentos del tipo 30=DIGECO, 86=TRBALO, 87=TRBAEX, 88=TRBADA, 89=TRBAPD, 113=TRBAVA, 92=CIECAJ, 184=CIECA2, 288=CIECA3");
                    return false;
                }
            }

            if (!isCamVal())
                return false;

            return true;
        }
        
        public boolean beforeModificar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            if (!isCamVal())
                return false;
            return true;
        }
        
        public boolean beforeEliminar()
        {
            if (  (!txtCodTipDoc.getText().equals("30")) && (!txtCodTipDoc.getText().equals("86"))  && (!txtCodTipDoc.getText().equals("87")) &&  (!txtCodTipDoc.getText().equals("88")) &&  (!txtCodTipDoc.getText().equals("89")) 
                     &&  (!txtCodTipDoc.getText().equals("113")) &&  (!txtCodTipDoc.getText().equals("92"))  &&  (!txtCodTipDoc.getText().equals("184"))  &&  (!txtCodTipDoc.getText().equals("288")) &&  (!txtCodTipDoc.getText().equals("194"))   )
            {
                mostrarMsgInf("Sólo es posible eliminar documentos del tipo 30=DIGECO, 86=TRBALO, 87=TRBAEX, 88=TRBADA, 89=TRBAPD, 113=TRBAVA");
                return false;
            }
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }
        
        public boolean beforeAnular(){
            boolean blnRes=true;
            if (  (!txtCodTipDoc.getText().equals("30")) && (!txtCodTipDoc.getText().equals("86"))  && (!txtCodTipDoc.getText().equals("87")) &&  (!txtCodTipDoc.getText().equals("88")) &&  (!txtCodTipDoc.getText().equals("89")) 
                   &&  (!txtCodTipDoc.getText().equals("113"))  &&  (!txtCodTipDoc.getText().equals("92"))    &&  (!txtCodTipDoc.getText().equals("184"))  &&  (!txtCodTipDoc.getText().equals("288")) &&  (!txtCodTipDoc.getText().equals("194"))  ){
                mostrarMsgInf("Sólo es posible anular documentos del tipo 30=DIGECO, 86=TRBALO, 87=TRBAEX, 88=TRBADA, 89=TRBAPD, 113=TRBAVA");
                blnRes=false;
            }
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Anulado")){
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                blnRes=false;
            }
            return blnRes;
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
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg()
    {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();            

            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.fe_ven";
                strSQL+="      , a1.co_usrIng, a5.tx_usr AS tx_nomUsrIng, a1.co_usrMod, a6.tx_usr AS tx_nomUsrMod";
                strSQL+=" FROM ( (tbm_cabDia AS a1 LEFT OUTER JOIN tbm_usr AS a5 ON a1.co_usrIng=a5.co_usr)";
                strSQL+="         LEFT OUTER JOIN tbm_usr AS a6 ON a1.co_usrMod=a6.co_usr )";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc";
                if(objParSis.getCodigoUsuario()!=1){
                    strSQL+=" INNER JOIN tbr_tipDocUsr AS a4 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_mnu=a4.co_mnu AND a4.co_usr=" + objParSis.getCodigoUsuario() + "";
                }
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc =" + strAux + "";
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_dia='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_dia =" + strAux + "";
                strAux=txtNumDia.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.tx_numDia LIKE '" + strAux.replaceAll("'", "''") + "'";
                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" ORDER BY a1.co_tipDoc, a1.co_dia";
                rst=stm.executeQuery(strSQL);
                arlDatConAsiDia = new ArrayList();
                while(rst.next())
                {
                    arlRegConAsiDia = new ArrayList();
                    arlRegConAsiDia.add(INT_ARL_CON_COD_EMP,   rst.getInt("co_emp"));
                    arlRegConAsiDia.add(INT_ARL_CON_COD_LOC,   rst.getInt("co_loc"));
                    arlRegConAsiDia.add(INT_ARL_CON_COD_TIPDOC,rst.getInt("co_tipDoc"));
                    arlRegConAsiDia.add(INT_ARL_CON_COD_DOC,   rst.getInt("co_dia"));
                    arlRegConAsiDia.add(INT_ARL_CON_TXT_USRING,rst.getString("tx_nomUsrIng"));
                    arlRegConAsiDia.add(INT_ARL_CON_TXT_USRMOD,rst.getString("tx_nomUsrMod"));
                    arlDatConAsiDia.add(arlRegConAsiDia);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
                
                if(arlDatConAsiDia.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatConAsiDia.size()) + " registros");
                    intIndiceAsiDia=arlDatConAsiDia.size()-1;
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
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
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
            if (cargarCabReg()){
                blnRes=true;
                objAsiDia.consultarDiario(objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_EMP)
                                        , objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_LOC)
                                        , objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_TIPDOC)
                                        , objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_DOC) );
            }
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
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
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_dia, a1.tx_numDia, a1.fe_dia, a1.st_reg, a1.st_imp, a1.fe_ven";
                strSQL+=" FROM tbm_cabDia AS a1";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON(a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a1.co_dia=" + objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_DOC);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_dia");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_numDia");
                    txtNumDia.setText((strAux==null)?"":strAux);
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_dia"),"dd/MM/yyyy"));

                    strFecDocIni=dtpFecDoc.getText();
                    strEstImpDoc=rst.getString("st_imp");
                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
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
            intPosRel = intIndiceAsiDia+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConAsiDia.size()) );   
            setHabDesCam();
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
    private boolean insertarReg()
    {
        boolean blnRes=false, blnExiCtasAsiDia;
        int intCodLocPre;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if(strCtaPadExi.equals("S")){
                    intCodLocPre=getCodigoLocalPredeterminado(objParSis.getCodigoEmpresa());
                    if(intCodLocPre!=-1){
                        if (objAsiDia.insertarDiario(con, objParSis.getCodigoEmpresa(), intCodLocPre, Integer.parseInt(txtCodTipDoc.getText()), txtNumDia.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"))){
                            txtCodDoc.setText("" + objAsiDia.getCodigoDiario());
                            con.commit();
                            blnRes=true;
                        }
                        else
                            con.rollback();
                    }
                }
                else{
                    if (objAsiDia.insertarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtNumDia.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy")))
                    {   blnExiCtasAsiDia = verificaExisteCtasDetalleAsientoDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), strNumDia_Glo);

                        if (blnExiCtasAsiDia)
                        {   if (actualizaNumUltDoc_CabTipDoc(con))
                            {  txtCodDoc.setText("" + objAsiDia.getCodigoDiario());
                               con.commit();
                               blnRes=true;
                            }
                            else {
                               con.rollback();
                            }
                        }
                        else
                        {  mostrarMsgInf("No es posible grabar el documento sin ninguna cuenta.\nVerifique esto y vuelva a intentarlo.");
                           con.rollback();
                        }
                    }
                    else
                        con.rollback();
                }
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private int getCodigoLocalPredeterminado(int empresaBanco){
        int intCodLocPre=-1;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT co_loc FROM tbm_loc";
                strSQL+=" WHERE co_emp=" + empresaBanco + "";
                strSQL+=" AND st_reg='P'";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodLocPre=rst.getInt("co_loc");
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intCodLocPre;
    }
    
    private boolean actualizaNumUltDoc_CabTipDoc(Connection con)
    {
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        try
        {
           stmLoc = con.createStatement();

           strSQL =  "UPDATE tbm_cabtipdoc SET ne_ultdoc = " + txtNumDia.getText() + " WHERE co_emp = " + objParSis.getCodigoEmpresa();
           strSQL += " AND co_loc = " + objParSis.getCodigoLocal() + " AND co_tipdoc = " + txtCodTipDoc.getText();
           stmLoc.executeUpdate(strSQL);

           stmLoc.close();
           stmLoc = null;
        }
      
      catch(Exception e)
      {
         blnRes = false;
      }
      
      return blnRes;
    } 
    
    /**
     * Esta función actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (objAsiDia.actualizarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumDia.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "A"))
                {
                    con.commit();
                    blnRes=true;
                }
                else   {
                    con.rollback();
                }
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
     * Esta función elimina el registro de la base de datos.
     * @return true: Si se pudo eliminar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (objAsiDia.eliminarDiario(con, objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_EMP)
                                                , objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_LOC)
                                                , objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_TIPDOC) 
                                                , objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_DOC) ))
                {
                    con.commit();
                    blnRes=true;
                }
                else  {
                    con.rollback();
                }
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)  {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (objAsiDia.anularDiario(con, objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_EMP)
                                              , objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_LOC)
                                              , objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_TIPDOC)
                                              , objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_DOC) ))
                {
                    con.commit();
                    blnRes=true;
                }
                else   {
                    con.rollback();
                }
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
     * Esta función habilita/deshabilita campos de acuerdo al tipo de documento.
     */
    private void setHabDesCam()
    {
        if (  (!txtCodTipDoc.getText().equals("30")) && (!txtCodTipDoc.getText().equals("86"))  && (!txtCodTipDoc.getText().equals("87")) &&  (!txtCodTipDoc.getText().equals("88")) &&  (!txtCodTipDoc.getText().equals("89"))  
                &&  (!txtCodTipDoc.getText().equals("113")) &&  (!txtCodTipDoc.getText().equals("92"))  &&  (!txtCodTipDoc.getText().equals("184")) &&  (!txtCodTipDoc.getText().equals("288"))  &&  (!txtCodTipDoc.getText().equals("194"))     )
        {
            dtpFecDoc.setEnabled(false);
            txtNumDia.setBackground(java.awt.Color.WHITE);
            txtNumDia.setEditable(false);
        }
        else{
            if(  (objTooBar.getEstado()=='x') ||  (objTooBar.getEstado()=='m')  )
            dtpFecDoc.setEnabled(true);
            txtNumDia.setEditable(true);
        }
    }    
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUIVisPre extends Thread{
        private int intIndFun;
        public ZafThreadGUIVisPre(){
            intIndFun=0;
        }
        public void run(){
            switch (intIndFun){
                case 0: //Botón "Imprimir".
                    generarRpt(0);
                    break;
                case 1: //Botón "Vista Preliminar".
                    generarRpt(2);
                    break;
            }
            objThrGUIVisPre=null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
         * la función que debe ejecutar el Thread.
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }


    /**
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {
        boolean blnRes=true;      
        Connection conRpt;
        Statement stmRpt;
        String strSQLRep="", strSQLSubRep="";
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        try
        {
            conRpt=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conRpt!=null){
                objRptSis.cargarListadoReportes();
                objRptSis.setVisible(true);
                if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE){
                    //Obtener la fecha y hora del servidor.
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux==null)
                        return false;
                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");
                    datFecAux=null;
                    intNumTotRpt=objRptSis.getNumeroTotalReportes();
                    for (i=0;i<intNumTotRpt;i++){
                        if (objRptSis.isReporteSeleccionado(i)){
                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i))){
                                case 0:
                                default:
                                    strSQL ="";
                                    strSQL+=" SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_dia";
                                    strSQL+="      , a1.tx_numDia, a1.fe_dia, a1.st_reg, a1.st_imp, a1.tx_glo";
                                    strSQL+="      , a1.co_usrIng, a3.tx_nom AS tx_nomUsrIng, a1.co_usrMod, a4.tx_nom AS tx_nomUsrMod";
                                    strSQL+=" FROM ((tbm_cabDia AS a1 INNER JOIN tbm_usr AS a3 ON a1.co_usrIng=a3.co_usr)";
                                    strSQL+=" INNER JOIN tbm_usr AS a4 ON a1.co_usrMod=a4.co_usr  )";
                                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                                    strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                                    strSQL+=" AND a1.co_emp=" + objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_EMP);
                                    strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_LOC);
                                    strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_TIPDOC);
                                    strSQL+=" AND a1.co_dia=" + objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_DOC);
                                    strSQLRep = strSQL;

                                    strSQL ="";
                                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia";
                                    strSQL+="      , a3.co_cta, a3.tx_codCta, a3.tx_desLar as tx_nomCta,  a2.nd_monDeb, a2.nd_monHab";
                                    strSQL+=" FROM tbm_cabDia AS a1 INNER JOIN tbm_detDia AS a2";
                                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia";
                                    strSQL+=" INNER JOIN tbm_plaCta AS a3 ON a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta";
                                    strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                                    strSQL+=" AND a1.co_emp=" + objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_EMP);
                                    strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_LOC);
                                    strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_TIPDOC);
                                    strSQL+=" AND a1.co_dia=" + objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_DOC);
                                    strSQL+=" ORDER BY a2.co_reg";
                                    strSQLSubRep = strSQL;

                                    strRutRpt = objRptSis.getRutaReporte(i);
                                    strNomRpt = objRptSis.getNombreReporte(i);
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    java.util.Map mapPar = new java.util.HashMap();
                                    mapPar.put("strCamAudRpt", "" + (objUti.getStringValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_TXT_USRING) + " / " + objUti.getStringValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_TXT_USRMOD) + " / " + objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " + strNomRpt + " v0.1    ");
                                    mapPar.put("codUsrImp", "" + objParSis.getCodigoUsuario());
                                    mapPar.put("codEmp", objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_EMP) );
                                    mapPar.put("codLoc", objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_LOC) );
                                    mapPar.put("codTipDoc", objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_TIPDOC) );
                                    mapPar.put("codDia", objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_DOC) );
                                    mapPar.put("nomEmp", objParSis.getNombreEmpresa());
                                    mapPar.put("rucEmp", getRucEmp(objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_EMP)) );
                                    mapPar.put("nomCorTipDoc", txtDesCorTipDoc.getText());
                                    mapPar.put("nomLarTipDoc", txtDesLarTipDoc.getText());
                                    mapPar.put("fecAct", "" + strFecHorSer);
                                    mapPar.put("fecDoc", objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", "dd/MM/yyyy"));
                                    mapPar.put("numDia", txtNumDia.getText());
                                    mapPar.put("numDia", txtNumDia.getText());
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRep", strSQLSubRep);

                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                    
                                    stmRpt = conRpt.createStatement();
                                    strSQL ="";
                                    strSQL+=" UPDATE tbm_cabDia";
                                    strSQL+=" SET st_imp='S'";
                                    strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_EMP);
                                    strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_TIPDOC);
                                    strSQL+=" AND co_dia=" + objUti.getIntValueAt(arlDatConAsiDia, intIndiceAsiDia, INT_ARL_CON_COD_DOC);
                                    stmRpt.executeUpdate(strSQL);
                                    stmRpt.close();
                                    stmRpt=null;

                                    strEstImpDoc = "S";
                                    break;
                            }
                        }
                    }
                }
                conRpt.close();
                conRpt=null;
            }
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private String getRucEmp(int codEmp){
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strRucEmp="";
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSQL ="";
                strSQL+=" SELECT tx_ruc FROM tbm_emp WHERE co_emp=" + codEmp + "";
                rstLoc=stmLoc.executeQuery(strSQL);
                if (rstLoc.next()){
                    strRucEmp=rstLoc.getString("tx_ruc");
                }
                stmLoc.close();
                stmLoc=null;
                rstLoc.close();
                rstLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)  {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strRucEmp;
    }

    
    
}
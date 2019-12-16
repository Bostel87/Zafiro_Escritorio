/*
 * ZafCom76.java
 *
 * Created on March 15, 2012
 */
package Compras.ZafCom76;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.JTextField;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafNotCorEle.ZafNotCorEle;
import java.awt.Color;
/**
 *
 * @author  Gigi
 */
public class ZafCom76 extends javax.swing.JInternalFrame 
{
    //ArrayList para consultar Pedido Embarcado
    private ArrayList arlDatConPedEmb, arlRegConPedEmb;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;  
    private static final int INT_ARL_CON_TXT_USRING=4;  
    private static final int INT_ARL_CON_TXT_USRMOD=5;
    private static final int INT_ARL_CON_COD_IMP=6;  
    private static final int INT_ARL_CON_COD_CTAACT=7;  
    private static final int INT_ARL_CON_COD_CTAPAS=8;  
    private int intIndicePedEmb=0;    
    
    private ArrayList arlDatCabCom76_03, arlRegCabCom76_03;  //ArrayList: Cabecera "ZafCom76_03"
    private ArrayList arlDatDetCom76_03, arlRegDetCom76_03;  //ArrayList: Detalle "ZafCom76_03"
    private ArrayList arlDatFilEliCom76_03;                  //ArrayList: Para las filas que fueron eliminadas en el detalle    
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private MiToolBar objTooBar;
    private ZafDatePicker dtpFecDoc, dtpFecEmb, dtpFecArr;
    private ZafDocLis objDocLis;
    private Date datFecAux;
    private Date datFecEmbIni, datFecEmbFin, datFecArrIni, datFecArrFin;
    private ZafThreadGUIVisPre objThrGUIVisPre;
    private ZafRptSis objRptSis;    
    private ZafVenCon vcoTipDoc, vcoImp, vcoPrv, vcoExp, vcoPto, vcoSeg, vcoForPag;
    private ZafCom76_01 objCom76_01;
    private ZafCom76_02 objCom76_02;
    private ZafCom76_03 objCom76_03;
    private ZafTblMod objTblModCom76_01, objTblModCom76_02; 
    private ZafPerUsr objPerUsr;
    private ZafImp objImp;
    private ZafAsiDia objAsiDia;
    private ZafSegMovInv objSegMovInv;
    private Object objCodSegInsAnt;
    private ZafNotCorEle objNotCorEle;
    
    public boolean blnIsPerModCarPag;    
    private boolean blnHayCam;
    private boolean blnVenConEme=false; //true: Se llama desde otra ventana. false: Consulta directamente desde el programa.
    private Vector vecDat, vecReg;
    
    private int intSig=1;
    private int intTipNotPed;
    private int intCodCtaActCreNotPed, intCodCtaPasCreNotPed;
    private int intCodLocPre;
    private int intNumPedEmbDia;
    private int intCodCtaActMerTra_Pro;
    private int intCodCtaPasMerTra_Pro;
    
    private String strSQL, strAux;
    private String strTipNotPed;
    private String strEstNotPedCer, strSQLUpdNotPedCie;
    private String strCodImp, strNomImp;
    private String strCodExp, strNomExp;
    private String strCodPto, strNomPto;
    private String strCodSeg, strNomSeg;
    private String strCodPag, strNomPag;
    private String strNumDoc, strMonDoc;
    private String strDesCorTipDoc, strDesLarTipDoc, strTipDocNecAutAnu;
    
    private String strVer=" v0.9 ";
        
    /** Creates new form ZafCom76 */
    public ZafCom76(ZafParSis obj) {
        try{
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            blnIsPerModCarPag=false;
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                configurarFrm();
                agregarDocLis();
                objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
                objSegMovInv=new ZafSegMovInv(objParSis, this);                
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    public ZafCom76(ZafParSis obj, int codigoTipoDocumento, int codigoDocumento) {
        try{
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            blnIsPerModCarPag=false;
            blnVenConEme=true;//Se esta llamando desde otro programa
            initComponents();
            configurarFrm();
            agregarDocLis();     
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            
            lblTit.setText("Pedido Embarcado");

            txtCodTipDoc.setText(""+codigoTipoDocumento);
            txtCodDoc.setText(""+codigoDocumento);
            objTooBar.setEstado('c');
            objTooBar.beforeConsultar();
            objTooBar.consultar();
            objTooBar.afterConsultar();
            objTooBar.setEstado('w');
            objTooBar.setVisible(false);
            objSegMovInv=new ZafSegMovInv(objParSis, this);

        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            
            objDocLis=new ZafDocLis();
            panBar.add(objTooBar);
            
            objTooBar.agregarSeparador();
            objTooBar.agregarBoton(butConNotPedLis);
            
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);

            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCabPed.add(dtpFecDoc);
            dtpFecDoc.setBounds(590, 4, 90, 20);
            
            /*Libreria para enviar correos electronicos a usuario.*/
            objNotCorEle = new ZafNotCorEle(objParSis);                   

            //Configurar ZafDatePicker:
            dtpFecEmb=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecEmb.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecEmb.setText("");
            panCabPed.add(dtpFecEmb);
            dtpFecEmb.setBounds(590, 24, 90, 20);
            
            //Configurar ZafDatePicker:
            dtpFecArr=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecArr.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecArr.setText("");
            panCabPed.add(dtpFecArr);
            dtpFecArr.setBounds(590, 44, 90, 20);
            
            txtCodTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodImp.setBackground(objParSis.getColorCamposObligatorios());
            txtNomImp.setBackground(objParSis.getColorCamposObligatorios());
            txtCodExp.setBackground(objParSis.getColorCamposObligatorios());
            txtNomExp2.setBackground(objParSis.getColorCamposObligatorios());
            txtNomExp.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecEmb.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecArr.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtNumPed.setBackground(objParSis.getColorCamposObligatorios());
            
            txtNumFacAdu.setBackground(objParSis.getColorCamposObligatorios());
            txtDiaCre.setBackground(objParSis.getColorCamposObligatorios());
            txtEnvDes.setBackground(objParSis.getColorCamposObligatorios());
            
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtValDoc.setBackground(objParSis.getColorCamposSistema());
            txtValFacConIva.setBackground(objParSis.getColorCamposSistema());
            txtPesTotKgr.setBackground(objParSis.getColorCamposSistema());
            txtCodTipDoc.setVisible(false);
            txtCodTipDoc.setEditable(false);
            txtCodExp.setVisible(false);
            txtCodExp.setEditable(false);
                    
            configurarVenConTipDoc();
            configurarVenConImp();
            configurarVenConPrv();
            configurarVenConExp();
            configurarVenConPto();
            configurarVenConSeg();
            configurarVenConForPag();

            //tabla de cargos a pagar
            objCom76_01=new ZafCom76_01(objParSis);
            panCarPagImp.add(objCom76_01,java.awt.BorderLayout.CENTER);

            //tabla de items y sus valores
            objCom76_02=new ZafCom76_02(objParSis, objCom76_01);
            panDetPagImp.add(objCom76_02,java.awt.BorderLayout.CENTER);
            objCom76_01.setObjectoCom76_02(objCom76_02);

            dtpFecDoc.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    //txtDesCorTipDocFocusLost(evt);
                    objCom76_02.setFechaDocumento(dtpFecDoc.getText());
                }
            });

            dtpFecEmb.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    //txtDesCorTipDocFocusLost(evt);
                    objCom76_02.setFechaEmbarque(dtpFecEmb.getText());
                }
            });


            vecDat=new Vector();    //Almacena los datos

            objCom76_02.addCom76_02Listener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeInsertar(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                }
                public void afterInsertar(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                }
                public void beforeEditarCelda(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                }
                public void afterEditarCelda(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    txtValDoc.setText("" + objCom76_02.getValorTotalCosto());
                    txtValFacConIva.setText("" + objCom76_02.getValorTotalFacturaConIva());
                    txtPesTotKgr.setText("" + objCom76_02.getCalcularPesoTotal());
                }
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                }
                public void afterConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                }
            });
             
             
            objAsiDia=new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                }
            });

            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
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
        txtCodImp.getDocument().addDocumentListener(objDocLis);
        txtNomImp.getDocument().addDocumentListener(objDocLis);
        txtCodExp.getDocument().addDocumentListener(objDocLis);
        txtNomExp2.getDocument().addDocumentListener(objDocLis);
        txtEmpExp.getDocument().addDocumentListener(objDocLis);
        txtCodPto.getDocument().addDocumentListener(objDocLis);
        txtNomPto.getDocument().addDocumentListener(objDocLis);
        txtCodSeg.getDocument().addDocumentListener(objDocLis);
        txtNomSeg.getDocument().addDocumentListener(objDocLis);
        txtCodForPag.getDocument().addDocumentListener(objDocLis);
        txtNomForPag.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtValDoc.getDocument().addDocumentListener(objDocLis);
        txtValFacConIva.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtPesTotKgr.getDocument().addDocumentListener(objDocLis);
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
    }     

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        panCabPed = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblNomImp = new javax.swing.JLabel();
        txtCodImp = new javax.swing.JTextField();
        txtNomImp = new javax.swing.JTextField();
        butImp = new javax.swing.JButton();
        lblExp = new javax.swing.JLabel();
        txtCodExp = new javax.swing.JTextField();
        txtNomExp = new javax.swing.JTextField();
        txtNomExp2 = new javax.swing.JTextField();
        butExp = new javax.swing.JButton();
        lblEmpExp = new javax.swing.JLabel();
        txtEmpExp = new javax.swing.JTextField();
        lblPto = new javax.swing.JLabel();
        txtCodPto = new javax.swing.JTextField();
        txtNomPto = new javax.swing.JTextField();
        butPto = new javax.swing.JButton();
        lblSeg = new javax.swing.JLabel();
        txtCodSeg = new javax.swing.JTextField();
        txtNomSeg = new javax.swing.JTextField();
        butSeg = new javax.swing.JButton();
        lblForPag = new javax.swing.JLabel();
        txtCodForPag = new javax.swing.JTextField();
        txtNomForPag = new javax.swing.JTextField();
        butForPag = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lblFecEmb = new javax.swing.JLabel();
        lblFecArr = new javax.swing.JLabel();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblNumPed = new javax.swing.JLabel();
        txtNumPed = new javax.swing.JTextField();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblValDoc = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        lblPesTotKgr = new javax.swing.JLabel();
        txtPesTotKgr = new javax.swing.JTextField();
        lblValFacConIva = new javax.swing.JLabel();
        txtValFacConIva = new javax.swing.JTextField();
        butConNotPedLis = new javax.swing.JButton();
        optTmFob = new javax.swing.JRadioButton();
        optTmCfr = new javax.swing.JRadioButton();
        optPzaFob = new javax.swing.JRadioButton();
        optPzaCfr = new javax.swing.JRadioButton();
        chkModSolFecEmbArr = new javax.swing.JCheckBox();
        chkCerNotPed = new javax.swing.JCheckBox();
        chkFleApr = new javax.swing.JCheckBox();
        chkCerPedEmb = new javax.swing.JCheckBox();
        chkCreCta = new javax.swing.JCheckBox();
        panDatFac = new javax.swing.JPanel();
        lblNumFacAdu = new javax.swing.JLabel();
        txtNumFacAdu = new javax.swing.JTextField();
        lblEnvDes = new javax.swing.JLabel();
        txtEnvDes = new javax.swing.JTextField();
        lblDiaCre = new javax.swing.JLabel();
        txtDiaCre = new javax.swing.JTextField();
        lblForEnv = new javax.swing.JLabel();
        cboForEnv = new javax.swing.JComboBox();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JSplitPane();
        panCarPagImp = new javax.swing.JPanel();
        panDetPagImp = new javax.swing.JPanel();
        panObs = new javax.swing.JPanel();
        panGenLblObs = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTxtObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panAsiDia = new javax.swing.JPanel();
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
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setAutoscrolls(true);

        panGen.setAutoscrolls(true);
        panGen.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(100, 246));
        panCab.setLayout(new java.awt.BorderLayout());

        panCabPed.setPreferredSize(new java.awt.Dimension(0, 206));
        panCabPed.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCabPed.add(lblTipDoc);
        lblTipDoc.setBounds(4, 4, 110, 20);
        panCabPed.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(70, 4, 32, 20);

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
        panCabPed.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(102, 4, 78, 20);

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
        panCabPed.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(180, 4, 258, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabPed.add(butTipDoc);
        butTipDoc.setBounds(438, 4, 20, 20);

        lblNomImp.setText("Importador:");
        lblNomImp.setToolTipText("Cuenta");
        panCabPed.add(lblNomImp);
        lblNomImp.setBounds(4, 24, 90, 20);

        txtCodImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodImpActionPerformed(evt);
            }
        });
        txtCodImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodImpFocusLost(evt);
            }
        });
        panCabPed.add(txtCodImp);
        txtCodImp.setBounds(102, 24, 78, 20);

        txtNomImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomImpActionPerformed(evt);
            }
        });
        txtNomImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomImpFocusLost(evt);
            }
        });
        panCabPed.add(txtNomImp);
        txtNomImp.setBounds(180, 24, 258, 20);

        butImp.setText("...");
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panCabPed.add(butImp);
        butImp.setBounds(438, 24, 20, 20);

        lblExp.setText("Exportador:");
        lblExp.setToolTipText("Proveedor");
        panCabPed.add(lblExp);
        lblExp.setBounds(4, 44, 80, 20);

        txtCodExp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodExpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodExpFocusLost(evt);
            }
        });
        txtCodExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodExpActionPerformed(evt);
            }
        });
        panCabPed.add(txtCodExp);
        txtCodExp.setBounds(70, 44, 30, 20);
        panCabPed.add(txtNomExp);
        txtNomExp.setBounds(102, 44, 78, 20);

        txtNomExp2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomExp2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomExp2FocusLost(evt);
            }
        });
        txtNomExp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomExp2ActionPerformed(evt);
            }
        });
        panCabPed.add(txtNomExp2);
        txtNomExp2.setBounds(180, 44, 258, 20);

        butExp.setText("...");
        butExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpActionPerformed(evt);
            }
        });
        panCabPed.add(butExp);
        butExp.setBounds(438, 44, 20, 20);

        lblEmpExp.setText("Empaque:");
        lblEmpExp.setToolTipText("Código del documento");
        panCabPed.add(lblEmpExp);
        lblEmpExp.setBounds(4, 64, 110, 20);
        panCabPed.add(txtEmpExp);
        txtEmpExp.setBounds(102, 64, 336, 20);

        lblPto.setText("Puerto:");
        lblPto.setToolTipText("Proveedor");
        panCabPed.add(lblPto);
        lblPto.setBounds(4, 84, 100, 20);

        txtCodPto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPtoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPtoFocusLost(evt);
            }
        });
        txtCodPto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPtoActionPerformed(evt);
            }
        });
        panCabPed.add(txtCodPto);
        txtCodPto.setBounds(102, 84, 78, 20);

        txtNomPto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomPtoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomPtoFocusLost(evt);
            }
        });
        txtNomPto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomPtoActionPerformed(evt);
            }
        });
        panCabPed.add(txtNomPto);
        txtNomPto.setBounds(180, 84, 258, 20);

        butPto.setText("...");
        butPto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPtoActionPerformed(evt);
            }
        });
        panCabPed.add(butPto);
        butPto.setBounds(438, 84, 20, 20);

        lblSeg.setText("Seguro:");
        lblSeg.setToolTipText("Proveedor");
        panCabPed.add(lblSeg);
        lblSeg.setBounds(4, 104, 100, 20);

        txtCodSeg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodSegFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodSegFocusLost(evt);
            }
        });
        txtCodSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodSegActionPerformed(evt);
            }
        });
        panCabPed.add(txtCodSeg);
        txtCodSeg.setBounds(102, 104, 78, 20);

        txtNomSeg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomSegFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomSegFocusLost(evt);
            }
        });
        txtNomSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomSegActionPerformed(evt);
            }
        });
        panCabPed.add(txtNomSeg);
        txtNomSeg.setBounds(180, 104, 258, 20);

        butSeg.setText("...");
        butSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSegActionPerformed(evt);
            }
        });
        panCabPed.add(butSeg);
        butSeg.setBounds(438, 104, 20, 20);

        lblForPag.setText("Pago:");
        lblForPag.setToolTipText("Proveedor");
        panCabPed.add(lblForPag);
        lblForPag.setBounds(4, 124, 100, 20);

        txtCodForPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodForPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodForPagFocusLost(evt);
            }
        });
        txtCodForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodForPagActionPerformed(evt);
            }
        });
        panCabPed.add(txtCodForPag);
        txtCodForPag.setBounds(102, 124, 78, 20);

        txtNomForPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomForPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomForPagFocusLost(evt);
            }
        });
        txtNomForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomForPagActionPerformed(evt);
            }
        });
        panCabPed.add(txtNomForPag);
        txtNomForPag.setBounds(180, 124, 258, 20);

        butForPag.setText("...");
        butForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butForPagActionPerformed(evt);
            }
        });
        panCabPed.add(butForPag);
        butForPag.setBounds(438, 124, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCabPed.add(lblFecDoc);
        lblFecDoc.setBounds(460, 4, 130, 20);

        lblFecEmb.setText("Fecha de embarque:");
        lblFecEmb.setToolTipText("Número alterno 1");
        panCabPed.add(lblFecEmb);
        lblFecEmb.setBounds(460, 24, 130, 20);

        lblFecArr.setText("Fecha de arribo:");
        lblFecArr.setToolTipText("Número alterno 1");
        panCabPed.add(lblFecArr);
        lblFecArr.setBounds(460, 44, 130, 20);

        lblNumDoc.setText("Número de documento:");
        lblNumDoc.setToolTipText("Número alterno 1");
        panCabPed.add(lblNumDoc);
        lblNumDoc.setBounds(460, 64, 130, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setToolTipText("Número de egreso");
        txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocFocusLost(evt);
            }
        });
        panCabPed.add(txtNumDoc);
        txtNumDoc.setBounds(590, 64, 90, 20);

        lblNumPed.setText("Número de pedido:");
        lblNumPed.setToolTipText("Número alterno 1");
        panCabPed.add(lblNumPed);
        lblNumPed.setBounds(460, 84, 124, 20);

        txtNumPed.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumPed.setToolTipText("Número de egreso");
        panCabPed.add(txtNumPed);
        txtNumPed.setBounds(590, 84, 90, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCabPed.add(lblCodDoc);
        lblCodDoc.setBounds(460, 104, 130, 20);

        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCabPed.add(txtCodDoc);
        txtCodDoc.setBounds(590, 104, 90, 20);

        lblValDoc.setText("Valor del documento:");
        lblValDoc.setToolTipText("Valor del documento");
        panCabPed.add(lblValDoc);
        lblValDoc.setBounds(460, 124, 130, 20);

        txtValDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValDocActionPerformed(evt);
            }
        });
        txtValDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValDocFocusLost(evt);
            }
        });
        panCabPed.add(txtValDoc);
        txtValDoc.setBounds(590, 124, 90, 20);

        lblPesTotKgr.setText("Peso(kg):");
        panCabPed.add(lblPesTotKgr);
        lblPesTotKgr.setBounds(460, 144, 130, 20);

        txtPesTotKgr.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCabPed.add(txtPesTotKgr);
        txtPesTotKgr.setBounds(590, 144, 90, 20);

        lblValFacConIva.setText("Valor Factura con IVA:");
        panCabPed.add(lblValFacConIva);
        lblValFacConIva.setBounds(460, 164, 130, 20);

        txtValFacConIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCabPed.add(txtValFacConIva);
        txtValFacConIva.setBounds(590, 164, 90, 20);

        butConNotPedLis.setText("Nota de Pedido");
        butConNotPedLis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConNotPedLisActionPerformed(evt);
            }
        });
        panCabPed.add(butConNotPedLis);
        butConNotPedLis.setBounds(560, 30, 120, 20);

        optTmFob.setText("TM FOB");
        optTmFob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTmFobActionPerformed(evt);
            }
        });
        panCabPed.add(optTmFob);
        optTmFob.setBounds(2, 146, 74, 14);

        optTmCfr.setText("TM CFR");
        optTmCfr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTmCfrActionPerformed(evt);
            }
        });
        panCabPed.add(optTmCfr);
        optTmCfr.setBounds(75, 146, 74, 14);

        optPzaFob.setText("PZA FOB");
        optPzaFob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optPzaFobActionPerformed(evt);
            }
        });
        panCabPed.add(optPzaFob);
        optPzaFob.setBounds(154, 146, 74, 14);

        optPzaCfr.setText("PZA CFR");
        optPzaCfr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optPzaCfrActionPerformed(evt);
            }
        });
        panCabPed.add(optPzaCfr);
        optPzaCfr.setBounds(235, 146, 74, 14);

        chkModSolFecEmbArr.setText("Modificar sólo fecha embarque y arribo");
        panCabPed.add(chkModSolFecEmbArr);
        chkModSolFecEmbArr.setBounds(2, 160, 270, 16);

        chkCerNotPed.setText("Cerrar Nota de Pedido");
        chkCerNotPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCerNotPedActionPerformed(evt);
            }
        });
        panCabPed.add(chkCerNotPed);
        chkCerNotPed.setBounds(270, 160, 180, 14);

        chkFleApr.setText("Cambiar sólo el valor del flete aproximado");
        panCabPed.add(chkFleApr);
        chkFleApr.setBounds(2, 176, 270, 14);

        chkCerPedEmb.setText("Cerrar Pedido Embarcado");
        panCabPed.add(chkCerPedEmb);
        chkCerPedEmb.setBounds(270, 176, 180, 14);

        chkCreCta.setText("Crear cuentas");
        panCabPed.add(chkCreCta);
        chkCreCta.setBounds(320, 145, 106, 14);

        panCab.add(panCabPed, java.awt.BorderLayout.CENTER);

        panDatFac.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la Factura"));
        panDatFac.setPreferredSize(new java.awt.Dimension(100, 54));
        panDatFac.setLayout(null);

        lblNumFacAdu.setText("Número factura Aduana:");
        panDatFac.add(lblNumFacAdu);
        lblNumFacAdu.setBounds(24, 14, 129, 14);
        panDatFac.add(txtNumFacAdu);
        txtNumFacAdu.setBounds(170, 10, 150, 20);

        lblEnvDes.setText("Envío desde:");
        panDatFac.add(lblEnvDes);
        lblEnvDes.setBounds(24, 30, 72, 14);
        panDatFac.add(txtEnvDes);
        txtEnvDes.setBounds(170, 30, 150, 20);

        lblDiaCre.setText("Días de crédito:");
        panDatFac.add(lblDiaCre);
        lblDiaCre.setBounds(340, 10, 118, 14);
        panDatFac.add(txtDiaCre);
        txtDiaCre.setBounds(450, 10, 130, 20);

        lblForEnv.setText("Forma de envío:");
        panDatFac.add(lblForEnv);
        lblForEnv.setBounds(340, 30, 90, 14);

        cboForEnv.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--- Seleccione envío --", "Land", "Sea", "Air" }));
        panDatFac.add(cboForEnv);
        cboForEnv.setBounds(450, 30, 130, 20);

        panCab.add(panDatFac, java.awt.BorderLayout.SOUTH);

        panGen.add(panCab, java.awt.BorderLayout.NORTH);

        panDet.setAutoscrolls(true);
        panDet.setLayout(new java.awt.BorderLayout());

        spnDet.setDividerLocation(258);
        spnDet.setOneTouchExpandable(true);

        panCarPagImp.setLayout(new java.awt.BorderLayout());
        spnDet.setLeftComponent(panCarPagImp);

        panDetPagImp.setLayout(new java.awt.BorderLayout());
        spnDet.setRightComponent(panDetPagImp);

        panDet.add(spnDet, java.awt.BorderLayout.CENTER);

        panObs.setPreferredSize(new java.awt.Dimension(100, 62));
        panObs.setLayout(new java.awt.BorderLayout());

        panGenLblObs.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenLblObs.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs2);

        panObs.add(panGenLblObs, java.awt.BorderLayout.WEST);

        panGenTxtObs.setLayout(new java.awt.GridLayout(2, 1));

        spnObs1.setViewportView(txaObs1);

        panGenTxtObs.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panGenTxtObs.add(spnObs2);

        panObs.add(panGenTxtObs, java.awt.BorderLayout.CENTER);

        panDet.add(panObs, java.awt.BorderLayout.SOUTH);

        panGen.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de diario", panAsiDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

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
    catch (Exception e){
        dispose();
    }
}//GEN-LAST:event_exitForm

private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
    txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
    strDesCorTipDoc=txtDesCorTipDoc.getText();
    txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
        if (txtDesCorTipDoc.getText().equals("")) {
            txtCodTipDoc.setText("");
            txtDesLarTipDoc.setText("");
        } else {
            mostrarVenConTipDoc(1);
        }
    } else
        txtDesCorTipDoc.setText(strDesCorTipDoc);
}//GEN-LAST:event_txtDesCorTipDocFocusLost

private void txtCodImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodImpActionPerformed
    txtCodImp.transferFocus();
}//GEN-LAST:event_txtCodImpActionPerformed

private void txtCodImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusGained
    strCodImp=txtCodImp.getText();
    txtCodImp.selectAll();
}//GEN-LAST:event_txtCodImpFocusGained

private void txtCodImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtCodImp.getText().equalsIgnoreCase(strCodImp)) {
        if (txtCodImp.getText().equals("")) {
            txtNomImp.setText("");
        } else {
            mostrarVenConImp(1);

        }
    } else
        txtCodImp.setText(strCodImp);
}//GEN-LAST:event_txtCodImpFocusLost

private void txtNomImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomImpActionPerformed
    txtNomImp.transferFocus();
}//GEN-LAST:event_txtNomImpActionPerformed

private void txtNomImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusGained
    strNomImp=txtNomImp.getText();
    txtNomImp.selectAll();
}//GEN-LAST:event_txtNomImpFocusGained

private void txtNomImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtNomImp.getText().equalsIgnoreCase(strNomImp)) {
        if (txtNomImp.getText().equals("")) {
            txtCodImp.setText("");
        } else {
            mostrarVenConImp(2);
        }
    } else
        txtNomImp.setText(strNomImp);
}//GEN-LAST:event_txtNomImpFocusLost

private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
    txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
    strDesLarTipDoc=txtDesLarTipDoc.getText();
    txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
        if (txtDesLarTipDoc.getText().equals("")) {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
        } else {
            mostrarVenConTipDoc(2);
        }
    } else
        txtDesLarTipDoc.setText(strDesLarTipDoc);
}//GEN-LAST:event_txtDesLarTipDocFocusLost

private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
    mostrarVenConTipDoc(0);
}//GEN-LAST:event_butTipDocActionPerformed

private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
    mostrarVenConImp(0);
}//GEN-LAST:event_butImpActionPerformed

private void txtNumDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusGained
    strNumDoc=txtNumDoc.getText();
    txtNumDoc.selectAll();
}//GEN-LAST:event_txtNumDocFocusGained

private void txtNumDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtNumDoc.getText().equalsIgnoreCase(strNumDoc)) {
        //actualizarGlo();
    }
}//GEN-LAST:event_txtNumDocFocusLost

private void txtValDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValDocActionPerformed
    txtValDoc.transferFocus();
}//GEN-LAST:event_txtValDocActionPerformed

private void txtValDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValDocFocusGained
    strMonDoc=txtValDoc.getText();
    txtValDoc.selectAll();
}//GEN-LAST:event_txtValDocFocusGained

private void txtValDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValDocFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtValDoc.getText().equalsIgnoreCase(strMonDoc)){
    }
}//GEN-LAST:event_txtValDocFocusLost

private void txtCodExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodExpActionPerformed
    txtCodExp.transferFocus();
}//GEN-LAST:event_txtCodExpActionPerformed

private void txtCodExpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusGained
    strCodExp=txtCodExp.getText();
    txtCodExp.selectAll();
}//GEN-LAST:event_txtCodExpFocusGained

private void txtCodExpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (txtCodExp.isEditable()){
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodExp.getText().equalsIgnoreCase(strCodExp)){
            if (txtCodExp.getText().equals("")){
                txtNomExp2.setText("");
            }
            else
            {
                mostrarVenConExp(1);
            }
        }
        else
            txtCodExp.setText(strCodExp);
    }
}//GEN-LAST:event_txtCodExpFocusLost

private void txtNomExp2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomExp2ActionPerformed
    txtNomExp2.transferFocus();
}//GEN-LAST:event_txtNomExp2ActionPerformed

private void txtNomExp2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExp2FocusGained
    strNomExp=txtNomExp2.getText();
    txtNomExp2.selectAll();
}//GEN-LAST:event_txtNomExp2FocusGained

private void txtNomExp2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExp2FocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtNomExp2.getText().equalsIgnoreCase(strNomExp)) {
        if (txtNomExp2.getText().equals("")) {
            txtCodExp.setText("");
        } else {
            mostrarVenConExp(2);
        }
    } 
    else
        txtNomExp2.setText(strNomExp);
}//GEN-LAST:event_txtNomExp2FocusLost

private void butExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpActionPerformed
    mostrarVenConExp(0);
}//GEN-LAST:event_butExpActionPerformed

private void txtCodPtoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPtoActionPerformed
    txtCodPto.transferFocus();
}//GEN-LAST:event_txtCodPtoActionPerformed

private void txtCodPtoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPtoFocusGained
    strCodPto=txtCodPto.getText();
    txtCodPto.selectAll();
}//GEN-LAST:event_txtCodPtoFocusGained

private void txtCodPtoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPtoFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (txtCodPto.isEditable()){
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodPto.getText().equalsIgnoreCase(strCodPto)){
            if (txtCodPto.getText().equals("")){
                txtNomPto.setText("");
            }
            else
            {
                mostrarVenConPto(1);
            }
        }
        else
            txtCodPto.setText(strCodPto);
    }
}//GEN-LAST:event_txtCodPtoFocusLost

private void txtNomPtoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomPtoActionPerformed
    txtNomPto.transferFocus();
}//GEN-LAST:event_txtNomPtoActionPerformed

private void txtNomPtoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPtoFocusGained
    strNomPto=txtNomPto.getText();
    txtNomPto.selectAll();
}//GEN-LAST:event_txtNomPtoFocusGained

private void txtNomPtoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPtoFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtNomPto.getText().equalsIgnoreCase(strNomPto)) {
        if (txtNomPto.getText().equals("")) {
            txtCodPto.setText("");
        } else {
            mostrarVenConPto(2);
        }
    } 
    else
        txtNomPto.setText(strNomPto);
}//GEN-LAST:event_txtNomPtoFocusLost

private void butPtoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPtoActionPerformed
    mostrarVenConPto(0);
}//GEN-LAST:event_butPtoActionPerformed

private void txtCodSegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodSegActionPerformed
    txtCodSeg.transferFocus();
}//GEN-LAST:event_txtCodSegActionPerformed

private void txtCodSegFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodSegFocusGained
    strCodSeg=txtCodSeg.getText();
    txtCodSeg.selectAll();
}//GEN-LAST:event_txtCodSegFocusGained

private void txtCodSegFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodSegFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (txtCodSeg.isEditable()){
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodSeg.getText().equalsIgnoreCase(strCodSeg)){
            if (txtCodSeg.getText().equals("")){
                txtNomSeg.setText("");
            }
            else
            {
                mostrarVenConSeg(1);
            }
        }
        else
            txtCodSeg.setText(strCodSeg);
    }
}//GEN-LAST:event_txtCodSegFocusLost

private void txtNomSegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomSegActionPerformed
    txtNomSeg.transferFocus();
}//GEN-LAST:event_txtNomSegActionPerformed

private void txtNomSegFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomSegFocusGained
    strNomSeg=txtNomSeg.getText();
    txtNomSeg.selectAll();
}//GEN-LAST:event_txtNomSegFocusGained

private void txtNomSegFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomSegFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtNomSeg.getText().equalsIgnoreCase(strNomSeg)) {
        if (txtNomSeg.getText().equals("")) {
            txtCodSeg.setText("");
        } else {
            mostrarVenConSeg(2);
        }
    } 
    else
        txtNomSeg.setText(strNomSeg);
}//GEN-LAST:event_txtNomSegFocusLost

private void butSegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSegActionPerformed
    mostrarVenConSeg(0);
}//GEN-LAST:event_butSegActionPerformed

private void txtCodForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodForPagActionPerformed
    txtCodForPag.transferFocus();
}//GEN-LAST:event_txtCodForPagActionPerformed

private void txtCodForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusGained
    strCodPag=txtCodForPag.getText();
    txtCodForPag.selectAll();
}//GEN-LAST:event_txtCodForPagFocusGained

private void txtCodForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (txtCodForPag.isEditable()){
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodForPag.getText().equalsIgnoreCase(strCodPag)){
            if (txtCodForPag.getText().equals("")){
                txtNomForPag.setText("");
            }
            else
            {
                mostrarVenConPag(1);
            }
        }
        else
            txtCodForPag.setText(strCodPag);
    }
}//GEN-LAST:event_txtCodForPagFocusLost

private void txtNomForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomForPagActionPerformed
    txtNomForPag.transferFocus();
}//GEN-LAST:event_txtNomForPagActionPerformed

private void txtNomForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForPagFocusGained
    strNomPag=txtNomForPag.getText();
    txtNomForPag.selectAll();
}//GEN-LAST:event_txtNomForPagFocusGained

private void txtNomForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForPagFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtNomForPag.getText().equalsIgnoreCase(strNomPag)) {
        if (txtNomForPag.getText().equals("")) {
            txtCodForPag.setText("");
        } else {
            mostrarVenConPag(2);
        }
    } 
    else
        txtNomForPag.setText(strNomPag);
}//GEN-LAST:event_txtNomForPagFocusLost

private void butForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butForPagActionPerformed
    mostrarVenConPag(0);
}//GEN-LAST:event_butForPagActionPerformed

private void optTmFobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTmFobActionPerformed
    if(optTmFob.isSelected()){
        optTmFob.setSelected(true);//siempre que se da click se selecciona.
        optTmCfr.setSelected(false);
        optPzaFob.setSelected(false);
        optPzaCfr.setSelected(false);
    }
    else
        optTmFob.setSelected(true);//siempre que se da click se selecciona.
    intTipNotPed=1;
    objCom76_01.setTipoNotaPedido(intTipNotPed);
    objCom76_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
    
    //NUEVO
    objCom76_01.calculaTotalFletes();
    objCom76_02.regenerarCalculos();    
    
}//GEN-LAST:event_optTmFobActionPerformed

private void optTmCfrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTmCfrActionPerformed
    if(optTmCfr.isSelected()){
        optTmFob.setSelected(false);
        optTmCfr.setSelected(true);//siempre que se da click se selecciona.
        optPzaFob.setSelected(false);
        optPzaCfr.setSelected(false);
    }
    else
        optTmCfr.setSelected(true);//siempre que se da click se selecciona.
    intTipNotPed=2;
    objCom76_01.setTipoNotaPedido(intTipNotPed);
    objCom76_01.setValorFleteInactivo();
    objCom76_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
    
    //NUEVO
    objCom76_01.setFleteItem(new BigDecimal(BigInteger.ZERO));
    objCom76_01.calculaTotalFletes();
    objCom76_02.regenerarCalculos();    
    
}//GEN-LAST:event_optTmCfrActionPerformed

private void optPzaFobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optPzaFobActionPerformed
    if(optPzaFob.isSelected()){
        optTmFob.setSelected(false);
        optTmCfr.setSelected(false);
        optPzaFob.setSelected(true);//siempre que se da click se selecciona.
        optPzaCfr.setSelected(false);
    }
    else
        optPzaFob.setSelected(true);//siempre que se da click se selecciona.
    intTipNotPed=3;
    objCom76_01.setTipoNotaPedido(intTipNotPed);
    objCom76_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
    
    //NUEVO
    objCom76_01.calculaTotalFletes();
    //objCom76_02.seteaColumnaPiezas();
    objCom76_02.regenerarCalculos();    
    
}//GEN-LAST:event_optPzaFobActionPerformed

private void optPzaCfrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optPzaCfrActionPerformed
    if(optPzaCfr.isSelected()){
        optTmFob.setSelected(false);
        optTmCfr.setSelected(false);
        optPzaFob.setSelected(false);
        optPzaCfr.setSelected(true);//siempre que se da click se selecciona.
    }
    else
        optPzaCfr.setSelected(true);//siempre que se da click se selecciona.
    intTipNotPed=4;
    objCom76_01.setTipoNotaPedido(intTipNotPed);
    objCom76_01.setValorFleteInactivo();
    objCom76_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
    
    //NUEVO
    objCom76_01.setFleteItem(new BigDecimal(BigInteger.ZERO));
    objCom76_01.calculaTotalFletes();
    //objCom76_02.seteaColumnaPiezas();
    objCom76_02.regenerarCalculos();    
    
}//GEN-LAST:event_optPzaCfrActionPerformed

private void butConNotPedLisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConNotPedLisActionPerformed
    if(arlDatCabCom76_03==null){//crea el objeto
        arlDatCabCom76_03=new ArrayList();
        arlDatDetCom76_03=new ArrayList();
        arlDatCabCom76_03.clear();
        arlDatDetCom76_03.clear();        
    }
    else{ //reutiliza el objeto antes creado
        //se envia la informacion de ZafCom76 a ZafCom76_03
        objCom76_03.setNotasPedidoSeleccionadas(arlDatCabCom76_03);//envia los datos de la cabecera a Com76_03
        objCom76_03.setDetalleSeleccionado(arlDatDetCom76_03);//devuelve los datos del detalle a Com76_03
    }

    if(objCom76_03.getBotonPresionado()==0){//no se ha presionado ningun boton, este caso se va a dar cuando se consulte una nota de embarque, solo en ese caso no se ha presionado ese boton
        //se envia la informacion de ZafCom76 a ZafCom76_03
        objCom76_03.setNotasPedidoSeleccionadas(arlDatCabCom76_03);//envia los datos de la cabecera a Com76_03
        objCom76_03.setDetalleSeleccionado(arlDatDetCom76_03);//devuelve los datos del detalle a Com76_03
    }

    objCom76_03.setVisible(true);
    if(objCom76_03.getBotonPresionado()==1){//se presiono el boton ACEPTAR
        //Se coloca la informacion en los arreglos
        arlDatCabCom76_03=objCom76_03.getCabeceraSeleccionada();//devuelve los datos de la cabecera
        arlDatDetCom76_03=objCom76_03.getDetalleSeleccionado();//devuelve los datos del detalle
        //la informacion de los arreglos se la coloca en los campos y en las tablas respectivas
        setCabeceraPedidoEmbarcado();
        setCargosPagarPedidoEmbarcado();
        setDetallePedidoEmbarcado();
        objCom76_02.setTipoNotaPedido(intTipNotPed);
        objCom76_02.regenerarCalculos();
        txtValDoc.setText("" + objCom76_02.getValorTotalCosto());
        txtValFacConIva.setText("" + objCom76_02.getValorTotalFacturaConIva());
        txtPesTotKgr.setText("" + objCom76_02.getCalcularPesoTotal());
        objCom76_01.setFleteItem(objCom76_02.getValorTotalFlete());
        objCom76_01.setValorFleteTotal(objCom76_02.getValorTotalFlete());
        txtNumPed.setText(objCom76_03.getNotaPedidoSeleccionado());
    }

}//GEN-LAST:event_butConNotPedLisActionPerformed

    private void chkCerNotPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCerNotPedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkCerNotPedActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butConNotPedLis;
    private javax.swing.JButton butExp;
    private javax.swing.JButton butForPag;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butPto;
    private javax.swing.JButton butSeg;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JComboBox cboForEnv;
    private javax.swing.JCheckBox chkCerNotPed;
    private javax.swing.JCheckBox chkCerPedEmb;
    private javax.swing.JCheckBox chkCreCta;
    private javax.swing.JCheckBox chkFleApr;
    private javax.swing.JCheckBox chkModSolFecEmbArr;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblDiaCre;
    private javax.swing.JLabel lblEmpExp;
    private javax.swing.JLabel lblEnvDes;
    private javax.swing.JLabel lblExp;
    private javax.swing.JLabel lblFecArr;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecEmb;
    private javax.swing.JLabel lblForEnv;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblNomImp;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblNumFacAdu;
    private javax.swing.JLabel lblNumPed;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPesTotKgr;
    private javax.swing.JLabel lblPto;
    private javax.swing.JLabel lblSeg;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValDoc;
    private javax.swing.JLabel lblValFacConIva;
    private javax.swing.JRadioButton optPzaCfr;
    private javax.swing.JRadioButton optPzaFob;
    private javax.swing.JRadioButton optTmCfr;
    private javax.swing.JRadioButton optTmFob;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCabPed;
    private javax.swing.JPanel panCarPagImp;
    private javax.swing.JPanel panDatFac;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panDetPagImp;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenLblObs;
    private javax.swing.JPanel panGenTxtObs;
    private javax.swing.JPanel panObs;
    private javax.swing.JSplitPane spnDet;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodExp;
    private javax.swing.JTextField txtCodForPag;
    private javax.swing.JTextField txtCodImp;
    private javax.swing.JTextField txtCodPto;
    private javax.swing.JTextField txtCodSeg;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDiaCre;
    private javax.swing.JTextField txtEmpExp;
    private javax.swing.JTextField txtEnvDes;
    private javax.swing.JTextField txtNomExp;
    private javax.swing.JTextField txtNomExp2;
    private javax.swing.JTextField txtNomForPag;
    private javax.swing.JTextField txtNomImp;
    private javax.swing.JTextField txtNomPto;
    private javax.swing.JTextField txtNomSeg;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtNumFacAdu;
    private javax.swing.JTextField txtNumPed;
    public javax.swing.JTextField txtPesTotKgr;
    public javax.swing.JTextField txtValDoc;
    public javax.swing.JTextField txtValFacConIva;
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
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
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
                            blnRes=objTooBar.insertar();
                            objTooBar.afterInsertar();
                        }
                        break;
                    case 'm': //Modificar
                        if(objTooBar.beforeModificar()){
                            blnRes=objTooBar.modificar();
                            objTooBar.afterModificar();
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
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm(){
        boolean blnRes=true;
        try{
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodImp.setText("");
            txtNomImp.setText("");
            txtCodExp.setText("");
            txtNomExp2.setText("");
            txtNomExp.setText("");
            txtEmpExp.setText("");
            txtCodPto.setText("");
            txtNomPto.setText("");
            txtCodSeg.setText("");
            txtNomSeg.setText("");
            txtCodForPag.setText("");
            txtNomForPag.setText("");
            dtpFecDoc.setText("");
            dtpFecEmb.setText("");
            dtpFecArr.setText("");
            txtNumDoc.setText("");
            txtNumPed.setText("");
            txtCodDoc.setText("");
            txtValDoc.setText("");
            txtValFacConIva.setText("");
            txtPesTotKgr.setText("");
            optTmFob.setSelected(false);
            optTmCfr.setSelected(false);
            optPzaFob.setSelected(false);
            optPzaCfr.setSelected(false);
            txtCodDoc.setEditable(false);
            txtValDoc.setEditable(false);
            txtValFacConIva.setEditable(false);
            txtPesTotKgr.setEditable(false);
            txaObs1.setText("");
            txaObs2.setText("");
            
            txtNumFacAdu.setText("");
            txtDiaCre.setText("");
            txtEnvDes.setText("");
            cboForEnv.setSelectedIndex(0);
            
            objCom76_01.limpiarTablaCom76_01();
            objCom76_02.limpiarTablaCom76_02();
            objCom76_01.setBlnHayCamCamCom76_01(false);
            objCom76_02.setBlnHayCamCamCom76_02(false);

            if(arlDatCabCom76_03!=null){
                arlDatCabCom76_03=null;
                arlDatCabCom76_03.clear();
            }
            if(arlDatDetCom76_03!=null){
                arlDatDetCom76_03=null;
                arlDatDetCom76_03.clear();
            }

            chkCerNotPed.setSelected(false);
            chkFleApr.setSelected(false);
            chkModSolFecEmbArr.setSelected(false);
            chkFleApr.setSelected(false);
            chkCreCta.setEnabled(false);
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
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
            super(ifrFrm, objParSis);
        }

        public void clickInicio() {
            try{
                if(arlDatConPedEmb.size()>0){
                    if(intIndicePedEmb>0){
                        if (blnHayCam || (objCom76_01.isBlnHayCamCom76_01())  || (objCom76_02.isBlnHayCamCom76_02())  ) {
                            if (isRegPro()) {
                                intIndicePedEmb=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndicePedEmb=0;
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
                if(arlDatConPedEmb.size()>0){
                    if(intIndicePedEmb>0){
                        if (blnHayCam || (objCom76_01.isBlnHayCamCom76_01())  || (objCom76_02.isBlnHayCamCom76_02()) ){
                            if (isRegPro()) {
                                intIndicePedEmb--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndicePedEmb--;
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
                if(arlDatConPedEmb.size()>0){
                    if(intIndicePedEmb < arlDatConPedEmb.size()-1){
                        if (blnHayCam || (objCom76_01.isBlnHayCamCom76_01())  || (objCom76_02.isBlnHayCamCom76_02())){
                            if (isRegPro()) {
                                intIndicePedEmb++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndicePedEmb++;
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
                if(arlDatConPedEmb.size()>0){
                    if(intIndicePedEmb<arlDatConPedEmb.size()-1){
                        if (blnHayCam || (objCom76_01.isBlnHayCamCom76_01())  || (objCom76_02.isBlnHayCamCom76_02()) ) {
                            if (isRegPro()) {
                                intIndicePedEmb=arlDatConPedEmb.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndicePedEmb=arlDatConPedEmb.size()-1;
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
            try{
                if ((blnHayCam) || (objCom76_01.isBlnHayCamCom76_01())  || (objCom76_02.isBlnHayCamCom76_02())   ){
                    isRegPro();
                }
                limpiarFrm();
                txtEmpExp.setText("El adecuado");
                mostrarTipDocPre();
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());

                dtpFecDoc.setEnabled(true);
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));

                dtpFecEmb.setEnabled(true);
                dtpFecEmb.setText("");

                dtpFecArr.setEnabled(true);
                dtpFecArr.setText("");

                objCom76_02.setFechaDocumento(dtpFecDoc.getText());
                objCom76_02.setFechaEmbarque(dtpFecEmb.getText());
                txtDesCorTipDoc.selectAll();
                txtDesCorTipDoc.requestFocus();
                objCom76_01.inicializar();
                objCom76_01.generarDatosCargoPagar();
                objCom76_01.setEditable(true);
                objCom76_02.inicializar();
                strTipNotPed="";
                intTipNotPed=0;
                strCodExp="";

                objCom76_03=new ZafCom76_03(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, strTipNotPed, strCodExp, objCom76_02, objTooBar.getEstado());

                objTblModCom76_01=objCom76_01.getTblModCom76_01();
                objTblModCom76_02=objCom76_02.getTblModCom76_02();

                objCom76_02.setModoOperacionTooBar(objTooBar.getEstado());
                objCom76_03.setModoOperacionTooBar(objTooBar.getEstado());
                
                chkFleApr.setEnabled(false);
                chkModSolFecEmbArr.setEnabled(false);
                chkCreCta.setEnabled(true);

                //Inicializar las variables que indican cambios.
                blnHayCam=false;

                objTblModCom76_01.isDataModelChanged();
                butConNotPedLis.setEnabled(true);
                
                blnIsPerModCarPag=false;
                if(objParSis.getCodigoUsuario()==1){
                    blnIsPerModCarPag=true;
                }
                else{
                    if((objPerUsr.isOpcionEnabled(4354))){//Modificar Cargos a Pagar
                        blnIsPerModCarPag=true;
                    }
                }
                objCom76_01.setBlnIsPerModCarPag(blnIsPerModCarPag);

            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickConsultar() {
            objTblModCom76_01=objCom76_01.getTblModCom76_01();
            objTblModCom76_02=objCom76_02.getTblModCom76_02();
        }

        public void clickModificar() {
            if(!chkCerPedEmb.isSelected()){
                
                chkCerPedEmb.setEnabled(true);
                txtCodTipDoc.setEditable(false);
                txtDesCorTipDoc.setEditable(false);
                txtDesLarTipDoc.setEditable(false);
                butTipDoc.setEnabled(false);
                dtpFecDoc.setEnabled(false);
    //            dtpFecEmb.setEnabled(false);
    //            dtpFecArr.setEnabled(false);
                objCom76_02.setFechaDocumento(dtpFecDoc.getText());
                objCom76_02.setFechaEmbarque(dtpFecEmb.getText());
                objCom76_01.setEditable(true);

                txtNumDoc.setEditable(false);
                txtCodDoc.setEditable(false);
                txtValDoc.setEditable(false);
                txtValFacConIva.setEditable(false);
                txtPesTotKgr.setEditable(false);

                txtCodImp.setEditable(false);
                txtNomImp.setEditable(false);
                butImp.setEnabled(false);

                txtCodExp.setEditable(false);
                txtNomExp2.setEditable(false);
                txtNomExp.setEditable(false);
                butExp.setEnabled(false);


                optTmFob.setEnabled(false);
                optTmCfr.setEnabled(false);
                optPzaFob.setEnabled(false);
                optPzaCfr.setEnabled(false);

                if(optTmFob.isSelected()){
                    strTipNotPed="1";//ne_tipnotped
                    intTipNotPed=1;//ne_tipnotped
                } 
                else if(optTmCfr.isSelected()){
                    strTipNotPed="2"; //ne_tipnotped
                    intTipNotPed=2;//ne_tipnotped
                }                            
                else if(optPzaFob.isSelected()){
                    strTipNotPed="3"; //ne_tipnotped
                    intTipNotPed=3;//ne_tipnotped
                }
                else if(optPzaCfr.isSelected()){
                    strTipNotPed="4"; //ne_tipnotped
                    intTipNotPed=4;//ne_tipnotped
                }            

                strCodExp=txtCodExp.getText();

                objCom76_03=new ZafCom76_03(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, strTipNotPed, strCodExp, objCom76_02, objTooBar.getEstado());

                objCom76_01.setTipoNotaPedido(intTipNotPed);
                objCom76_02.setTipoNotaPedido(intTipNotPed);

                objCom76_02.setModoOperacionTooBar(objTooBar.getEstado());
                objCom76_03.setModoOperacionTooBar(objTooBar.getEstado());

                butConNotPedLis.setEnabled(true);

                chkFleApr.setEnabled(false);
                chkModSolFecEmbArr.setEnabled(false);

                
                blnIsPerModCarPag=false;
                if(objParSis.getCodigoUsuario()==1){
                    chkFleApr.setEnabled(true);
                    chkModSolFecEmbArr.setEnabled(true);
                    blnIsPerModCarPag=true;
                }
                else{
                    if((objPerUsr.isOpcionEnabled(4352))){  //Ficha "General": Casilla "Cambiar sólo el valor del flete aproximado"
                        chkFleApr.setEnabled(true);
                    }
                    if((objPerUsr.isOpcionEnabled(4353))){  //Ficha "General": Casilla "Modificar sólo fecha embarque y arribo"
                        chkModSolFecEmbArr.setEnabled(true);
                    }
                    if((objPerUsr.isOpcionEnabled(4354))){//Modificar Cargos a Pagar
                        blnIsPerModCarPag=true;
                    }
                }
                objCom76_01.setBlnIsPerModCarPag(blnIsPerModCarPag);
                chkCreCta.setEnabled(false);
            }
            else{
                objTblModCom76_01.setModoOperacion(objTblModCom76_01.INT_TBL_NO_EDI);
                objTblModCom76_02.setModoOperacion(objTblModCom76_01.INT_TBL_NO_EDI);
                mostrarMsgInf("<HTML>El Pedido Embarcado fue cerrado.<BR>No se puede modificar un Pedido Embarcado que se encuetra cerrado.<BR>Por favor verifique y vuelva a intentarlo.</HTML>");
            }
        }

        public void clickEliminar() {
            
        }

        public void clickAnular() {
            cargarCabReg();
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickAceptar() {
        }

        public void clickCancelar() {
        }

        public boolean insertar() {
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean consultar() {
            consultarReg();
            return true;
        }

        public boolean modificar() {
            if (!actualizarReg())
                return false;
            return true;
        }

        public boolean eliminar() {
            try{
                if (!eliminarReg())
                    return false;
                
                //Desplazarse al siguiente registro si es posible.
                if(intIndicePedEmb<arlDatConPedEmb.size()-1){
                    intIndicePedEmb++;
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

        public boolean anular() {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        public boolean imprimir() {
            return true;
        }

        public boolean vistaPreliminar() {
            if (objThrGUIVisPre==null){
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(1);
                objThrGUIVisPre.start();
            }
            return true;
        }

        public boolean aceptar() {
            return true;
        }

        public boolean cancelar() {
            boolean blnRes=true;
            try{
                if ((blnHayCam) || (objCom76_01.isBlnHayCamCom76_01())  || (objCom76_02.isBlnHayCamCom76_02())   ){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
            }
            catch (Exception e)  {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }

        public boolean beforeInsertar() {
            arlDatFilEliCom76_03=objCom76_02.getArregloFilasEliminadas();

            if (!isCamVal())
                return false;

            return true;
        }

        public boolean beforeConsultar() {
//            objTblModCom76_01=objCom76_01.getTblModCom76_01();
//            objTblModCom76_02=objCom76_02.getTblModCom76_02();
            
            arlDatFilEliCom76_03=objCom76_02.getArregloFilasEliminadas();
            return true;
        }

        public boolean beforeModificar() {
            boolean blnRes=true;
            arlDatFilEliCom76_03=objCom76_02.getArregloFilasEliminadas();

            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                blnRes=false;
            }
            
            if (!isCamVal())
                return false;

            return blnRes;
        }

        public boolean beforeEliminar() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            if(isIngresoImportacion()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El Pedido Embarcado tiene un <FONT COLOR=\"blue\">Ingreso por Importación </FONT>asociado.<BR>No se puede anular el documento, verifique y vuelva a intentarlo.</HTML>");
                return false;
            }
            
//            if(isFacturaImportacion()){
//                tabFrm.setSelectedIndex(0);
//                mostrarMsgInf("<HTML>El Pedido Embarcado tiene una <FONT COLOR=\"blue\">Factura de Importación</FONT> asociada.<BR>No se puede anular el documento, verifique y vuelva a intentarlo.</HTML>");
//                return false;
//            }
            
            
            return true;
        }

        public boolean beforeAnular() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")){
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            
            if(isIngresoImportacion()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El Pedido Embarcado tiene un <FONT COLOR=\"blue\">Ingreso por Importación </FONT>asociado.<BR>No se puede anular el documento, verifique y vuelva a intentarlo.</HTML>");
                return false;
            }
            
//            if(isFacturaImportacion()){
//                tabFrm.setSelectedIndex(0);
//                mostrarMsgInf("<HTML>El Pedido Embarcado tiene una <FONT COLOR=\"blue\">Factura de Importación</FONT> asociada.<BR>No se puede anular el documento, verifique y vuelva a intentarlo.</HTML>");
//                return false;
//            }
            
            if(isCuentaAsociadaSaldo()){
                mostrarMsgInf("<HTML>Existe(n) cuenta(s) asociada(s) con saldo en el Pedido Embarcado.<BR>Verifique saldos y vuelva a intentarlo.</HTML>");
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

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean afterInsertar() {
            this.setEstado('w');
            blnHayCam=false;
            objTooBar.setEstado('w');
            consultarReg();
            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar() {
            return true;
        }

        public boolean afterModificar() {
            blnHayCam=false;
            objTooBar.setEstado('w');
            cargarReg();
            objTooBar.afterConsultar();
            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterAnular() {
            cargarReg();
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }


        
    }

    
    /**
     * Esta funcián permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_imp, a1.co_ctaAct, a1.co_ctaPas";
                strSQL+="      , a1.co_usrIng, a2.tx_usr AS tx_nomUsrIng, a1.co_usrMod, a3.tx_usr AS tx_nomUsrMod";
                strSQL+=" FROM (  (tbm_cabPedEmbImp AS a1 INNER JOIN tbm_usr AS a2 ON a1.co_usrIng=a2.co_usr)";
                strSQL+="          INNER JOIN tbm_usr AS a3 ON a1.co_usrMod=a3.co_usr";
                strSQL+=" )";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_detPedEmbImp AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc AND a1.co_doc=a5.co_doc)";
                strSQL+=" INNER JOIN tbm_inv AS a6 ON (a1.co_emp=a6.co_emp AND a5.co_itm=a6.co_itm)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                
                if(!blnVenConEme){
                    strSQL+=" AND a6.tx_codAlt like '%S'"; //Mostrar Solo items con Terminal S
                }

                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc = " + strAux + "";
                strAux=txtCodImp.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_imp = " + strAux + "";
                strAux=txtCodExp.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_exp = " + strAux + "";
                strAux=txtCodPto.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_ciupueemb = " + strAux + "";
                strAux=txtCodSeg.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_seg = " + strAux + "";
                strAux=txtCodForPag.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_forpag = " + strAux + "";
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                if (dtpFecEmb.isFecha())
                    strSQL+=" AND a1.fe_emb='" + objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                if (dtpFecArr.isFecha())
                    strSQL+=" AND a1.fe_arr='" + objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";

                strAux=txtNumDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc = " + strAux.replaceAll("'", "''") + "";

                strAux=txtNumPed.getText();
//                if (!strAux.equals(""))
//                    strSQL+=" AND a1.tx_numDoc2 = '" + strAux.replaceAll("'", "''") + "'";
                if (!strAux.equals("")){
                    strSQL+=" AND (  UPPER(a1.tx_numdoc2) LIKE '%" + strAux.replaceAll("'", "''") + "%'";
                    strSQL+="        OR LOWER(a1.tx_numdoc2) LIKE '%" + strAux.replaceAll("'", "''") + "%'";
                    strSQL+=" )";
                }

                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc = " + strAux.replaceAll("'", "''") + "";

                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_imp, a1.co_ctaAct, a1.co_ctaPas";
                strSQL+="        , a1.co_usrIng, a2.tx_usr, a1.co_usrMod, a3.tx_usr";
                strSQL+=" ORDER BY a1.fe_doc, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                System.out.println("consultarReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatConPedEmb = new ArrayList();
                while(rst.next())
                {
                    arlRegConPedEmb = new ArrayList();
                    arlRegConPedEmb.add(INT_ARL_CON_COD_EMP,   rst.getInt("co_emp"));
                    arlRegConPedEmb.add(INT_ARL_CON_COD_LOC,   rst.getInt("co_loc"));
                    arlRegConPedEmb.add(INT_ARL_CON_COD_TIPDOC,rst.getInt("co_tipDoc"));
                    arlRegConPedEmb.add(INT_ARL_CON_COD_DOC,   rst.getInt("co_doc"));
                    arlRegConPedEmb.add(INT_ARL_CON_TXT_USRING,rst.getString("tx_nomUsrIng"));
                    arlRegConPedEmb.add(INT_ARL_CON_TXT_USRMOD,rst.getString("tx_nomUsrMod"));
                    arlRegConPedEmb.add(INT_ARL_CON_COD_IMP,   rst.getString("co_imp"));
                    arlRegConPedEmb.add(INT_ARL_CON_COD_CTAACT,rst.getString("co_ctaAct"));
                    arlRegConPedEmb.add(INT_ARL_CON_COD_CTAPAS,rst.getString("co_ctaPas"));
                    arlDatConPedEmb.add(arlRegConPedEmb);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
                
                if(arlDatConPedEmb.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatConPedEmb.size()) + " registros");
                    intIndicePedEmb=arlDatConPedEmb.size()-1;
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
    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            if (cargarCabReg()){
                if (cargarCarPagImp()){
                    if (cargarDetRegItmVal()){
                        if(optTmFob.isSelected()){
                            strTipNotPed="1";//ne_tipnotped
                            intTipNotPed=1;//ne_tipnotped
                        } 
                        else if(optTmCfr.isSelected()){
                            strTipNotPed="2"; //ne_tipnotped
                            intTipNotPed=2;//ne_tipnotped
                        }                            
                        else if(optPzaFob.isSelected()){
                            strTipNotPed="3"; //ne_tipnotped
                            intTipNotPed=3;//ne_tipnotped
                        }
                        else if(optPzaCfr.isSelected()){
                            strTipNotPed="4"; //ne_tipnotped
                            intTipNotPed=4;//ne_tipnotped
                        }
                        strCodExp=txtCodExp.getText();
                        objCom76_03=new ZafCom76_03(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, strTipNotPed, strCodExp, objCom76_02, objTooBar.getEstado());

                        if(setearArreglosCabeceraDetalle()){                           
                            
                            objCom76_02.setBlnHayCamCamCom76_02(false);
                            objCom76_01.setBlnHayCamCamCom76_01(false);
                        }
                    }
                }
            }
            objCom76_01.setTipoNotaPedido(intTipNotPed);
            objCom76_02.setTipoNotaPedido(intTipNotPed);
            
            objCom76_02.setBlnHayCamCamCom76_02(false);
            objCom76_01.setBlnHayCamCamCom76_01(false);
            
            blnHayCam=false;
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta funcián permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg(){
        int intPosRel;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.fe_emb, a1.fe_arr, a1.ne_numdoc, a1.tx_numDoc2, a1.ne_tipnotped ";
                strSQL+="      , a1.co_imp, a1.co_exp, a1.co_ciupueemb, a1.co_seg, a1.co_forpag ";
                strSQL+="      , a1.tx_tipemp, a1.nd_valdoc, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1";
                strSQL+="      , a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc";
                strSQL+="      , a3.co_emp AS co_imp, a3.tx_nom AS tx_nomImp";
                strSQL+="      , a5.co_exp, a5.tx_nom AS tx_nomExp, a5.tx_nom2 AS tx_aliExp";
                strSQL+="      , a6.co_ciu, a6.tx_deslar AS tx_nomPueEmb";
                strSQL+="      , a7.co_seg, a7.tx_deslar AS tx_nomSeg";
                strSQL+="      , a8.co_forpag, a8.tx_deslar AS tx_nomForPag";
                strSQL+="      , a1.tx_numdoc3, a1.tx_direnv, a1.ne_forenv, a1.ne_diacre";
                strSQL+="      , a1.co_ctaAct, a1.co_ctaPas, a1.st_cie";
                strSQL+=" FROM tbm_cabPedEmbImp AS a1";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_imp=a3.co_emp)";
                strSQL+=" INNER JOIN tbm_expImp AS a5 ON(a1.co_exp=a5.co_exp)";
                strSQL+=" LEFT OUTER JOIN tbm_ciu AS a6 ON(a1.co_ciupueemb=a6.co_ciu)";
                strSQL+=" LEFT OUTER JOIN tbm_segimp AS a7 ON(a1.co_seg=a7.co_seg)";
                strSQL+=" LEFT OUTER JOIN tbm_forpagimp AS a8 ON(a1.co_forpag=a8.co_forpag)";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);     
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);     
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);     
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);             
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_natDoc");
                    intSig=(strAux.equals("I")?1:-1);
                    strAux=rst.getString("co_imp");
                    txtCodImp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomImp");
                    txtNomImp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_exp");
                    txtCodExp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomExp");
                    txtNomExp2.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_aliExp");
                    txtNomExp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_tipemp");
                    txtEmpExp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_ciupueemb");
                    txtCodPto.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomPueEmb");
                    txtNomPto.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_seg");
                    txtCodSeg.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomSeg");
                    txtNomSeg.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_forpag");
                    txtCodForPag.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomForPag");
                    txtNomForPag.setText((strAux==null)?"":strAux);
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    dtpFecEmb.setText(objUti.formatearFecha(rst.getDate("fe_emb"),"dd/MM/yyyy"));
                    dtpFecArr.setText(objUti.formatearFecha(rst.getDate("fe_arr"),"dd/MM/yyyy"));
                    datFecEmbIni=rst.getDate("fe_emb");
                    datFecArrIni=rst.getDate("fe_arr");
                    strAux=rst.getString("ne_numdoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_numDoc2");
                    txtNumPed.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getObject("nd_valdoc")==null?"0":(rst.getString("nd_valdoc").equals("")?"0":rst.getString("nd_valdoc"));
                    txtValDoc.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));
                    BigDecimal bgdPorIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                    BigDecimal bgdValDoc=BigDecimal.valueOf(Double.valueOf(strAux));
                    txtValFacConIva.setText("" + (objUti.redondearBigDecimal( bgdValDoc.multiply(bgdPorIva), objParSis.getDecimalesMostrar())) );
                    strAux=rst.getObject("nd_pestotkgr")==null?"0":(rst.getString("nd_pestotkgr").equals("")?"0":rst.getString("nd_pestotkgr"));
                    txtPesTotKgr.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));

                    strAux=rst.getString("ne_tipnotped");
                    if(strAux.equals("1")){
                        optTmFob.setSelected(true);
                        optTmCfr.setSelected(false);
                        optPzaFob.setSelected(false);
                        optPzaCfr.setSelected(false);
                    }                        
                    else if(strAux.equals("2")){
                        optTmFob.setSelected(false);
                        optTmCfr.setSelected(true);
                        optPzaFob.setSelected(false);
                        optPzaCfr.setSelected(false);
                    }
                    else if(strAux.equals("3")){
                        optTmFob.setSelected(false);
                        optTmCfr.setSelected(false);
                        optPzaFob.setSelected(true);
                        optPzaCfr.setSelected(false);
                    }
                    else if(strAux.equals("4")){
                        optTmFob.setSelected(false);
                        optTmCfr.setSelected(false);
                        optPzaFob.setSelected(false);
                        optPzaCfr.setSelected(true);
                    }

                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));
                    
                    strAux=rst.getString("tx_numdoc3");
                    txtNumFacAdu.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_direnv");
                    txtEnvDes.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getObject("ne_forenv")==null?"0":(rst.getString("ne_forenv").equals("")?"0":rst.getString("ne_forenv"));
                    cboForEnv.setSelectedIndex(Integer.parseInt(strAux));
                    
                    strAux=rst.getString("ne_diacre");
                    txtDiaCre.setText((strAux==null)?"":strAux);
                    
                    chkCreCta.setSelected(false);
                    strAux=(rst.getObject("co_ctaAct")==null?"":rst.getString("co_ctaAct"));
                    if(! strAux.equals(""))
                        chkCreCta.setSelected(true);
                    
                    strAux=(rst.getObject("co_ctaPas")==null?"":rst.getString("co_ctaPas"));
                    if(!strAux.equals(""))
                        chkCreCta.setSelected(true);
                    
                    strAux=(rst.getObject("st_cie")==null?"":rst.getString("st_cie"));
                    if(strAux.equals("S"))
                        chkCerPedEmb.setSelected(true);
                    else
                        chkCerPedEmb.setSelected(false);
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
            intPosRel = intIndicePedEmb+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConPedEmb.size()) );            
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
     * Esta funcián permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetRegItmVal(){
        boolean blnRes=true;
        strEstNotPedCer="";
        try{
            objTblModCom76_02.removeAllRows();
            if (!txtCodTipDoc.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor as tx_uniMed, CASE WHEN a1.nd_pesitmkgr IS NULL THEN 0 ELSE a1.nd_pesitmkgr END AS nd_pesitmkgr";
                    strSQL+="      , b1.co_emp, b1.co_locrel, b1.co_tipdocrel, b1.co_docrel, b1.co_regrel";
                    strSQL+="      , CASE WHEN a5.co_parara IS NULL THEN 0 ELSE a5.co_parara END AS co_parara, CASE WHEN a5.tx_par IS NULL THEN '' ELSE a5.tx_par END AS tx_par";
                    strSQL+="      , CASE WHEN a5.tx_des IS NULL THEN '' ELSE a5.tx_des END AS tx_des";
                    strSQL+="      , CASE WHEN b1.nd_ara IS NULL THEN 0 ELSE b1.nd_ara END AS nd_ara";
                    strSQL+="      , b1.nd_can";
                    strSQL+="      , b2.nd_can AS nd_canNotPed";
                    //strSQL+=" , ((b2.nd_can*0.10)+b2.nd_can) AS nd_canNotPed";//Alfredo dijo que se puede ingresar hasta el 10% del valor de la nota de pedido adicional
                    strSQL+="      , b1.nd_preUni, b1.nd_valTotFobCfr";
                    strSQL+="      , b1.nd_canTonMet, b1.nd_valFle, b1.nd_valCfr, CASE WHEN b1.nd_valTotAra IS NULL THEN 0 ELSE b1.nd_valTotAra END AS nd_valTotAra";
                    strSQL+="      , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg, CASE WHEN b2.nd_canUtiPedEmb IS NULL THEN 0 ELSE b2.nd_canUtiPedEmb END AS nd_canUtiPedEmb";
                    strSQL+="      , d1.st_cie";
                    strSQL+=" FROM (tbm_detPedEmbImp AS b1 LEFT OUTER JOIN tbm_detNotPedImp AS b2";
                    strSQL+="       ON b1.co_emp=b2.co_emp AND b1.co_locRel=b2.co_loc AND b1.co_tipDocRel=b2.co_tipDoc AND b1.co_docRel=b2.co_doc AND b1.co_regRel=b2.co_reg";
                    strSQL+="       LEFT OUTER JOIN tbm_cabNotPedImp AS d1 ON b2.co_emp=d1.co_emp AND b2.co_loc=d1.co_loc AND b2.co_tipDoc=d1.co_tipDoc AND b2.co_doc=d1.co_doc";
                    strSQL+=" )";
                    strSQL+=" INNER JOIN";
                    strSQL+=" (";
                    strSQL+=" 	((tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
                    strSQL+=" 	  LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
                    strSQL+=" 	)";
                    strSQL+=" 	LEFT OUTER JOIN(tbm_grpInvImp AS a4 INNER JOIN tbm_parAraImp AS a5 ON a4.co_parAra=a5.co_parAra";
                    strSQL+=" 		INNER JOIN tbm_vigParAraImp AS a6 ON a5.co_parAra=a6.co_parAra";
                    strSQL+=" 	) ON a1.co_grpImp=a4.co_grp";
                    strSQL+=" ) ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                    strSQL+=" WHERE b1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND b1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                    strSQL+=" AND b1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);
                    strSQL+=" AND b1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                    strSQL+=" GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor, a1.nd_pesitmkgr";
                    strSQL+="        , b1.co_emp, b1.co_locrel, b1.co_tipdocrel, b1.co_docrel, b1.co_regrel";
                    strSQL+="        , a5.co_parara, a5.tx_par, a5.tx_des, b1.nd_ara";
                    strSQL+="        , b1.nd_can, b2.nd_can, b1.nd_preUni, b1.nd_valTotFobCfr, b1.nd_canTonMet, b1.nd_valFle, b1.nd_valCfr, b1.nd_valTotAra";
                    strSQL+="        , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg, b2.nd_canUtiPedEmb, d1.st_cie";
                    strSQL+=" ORDER BY b1.co_reg, a1.tx_codAlt";
                    System.out.println("cargarDetRegItmVal: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(objCom76_02.INT_TBL_DAT_LIN,"");
                        vecReg.add(objCom76_02.INT_TBL_DAT_COD_ITM_MAE,      rst.getString("co_itmMae"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_COD_ITM,          rst.getString("co_itm"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_COD_ALT_ITM,      rst.getString("tx_codAlt"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_COD_LET_ITM,      rst.getString("tx_codAlt2"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_BUT_ITM,          null);
                        vecReg.add(objCom76_02.INT_TBL_DAT_NOM_ITM,          rst.getString("tx_nomItm"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_UNI_MED,          rst.getString("tx_uniMed"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_PES,              rst.getString("nd_pesitmkgr"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_COD_ARA,          rst.getString("co_parara"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_NOM_ARA,          rst.getString("tx_par"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_DES_ARA,          rst.getString("tx_des"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_POR_ARA,          rst.getString("nd_ara"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_CAN_TON_MET,      rst.getString("nd_canTonMet"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_CAN_NOT_PED,     (rst.getObject("nd_canNotPed")==null?new BigDecimal("0"):(rst.getString("nd_canNotPed").equals("")?new BigDecimal("0"):rst.getBigDecimal("nd_canNotPed"))));
                        vecReg.add(objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB,  rst.getString("nd_can"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_CAN_PEN_NOT_PED,((rst.getObject("nd_canNotPed")==null?new BigDecimal("0"):(rst.getString("nd_canNotPed").equals("")?new BigDecimal("0"):rst.getBigDecimal("nd_canNotPed"))).subtract(rst.getBigDecimal("nd_canUtiPedEmb"))));
                        vecReg.add(objCom76_02.INT_TBL_DAT_CAN_UTI_PED_EMB,  rst.getString("nd_canUtiPedEmb"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_PRE_UNI,          rst.getString("nd_preUni"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_TOT_FOB_CFR,      rst.getString("nd_valTotFobCfr"));                        
                        vecReg.add(objCom76_02.INT_TBL_DAT_VAL_FLE,          rst.getString("nd_valFle"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_VAL_CFR,          rst.getString("nd_valCfr"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_VAL_CFR_ARA,      "0");
                        vecReg.add(objCom76_02.INT_TBL_DAT_TOT_ARA,          rst.getString("nd_valTotAra"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_TOT_GAS,          rst.getString("nd_valTotGas"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_TOT_COS,          rst.getString("nd_valTotCos"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_COS_UNI,          rst.getString("nd_cosUni"));                        
                        vecReg.add(objCom76_02.INT_TBL_DAT_COD_EMP_REL,      rst.getString("co_emp"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_COD_LOC_REL,      rst.getString("co_locrel"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_COD_TIP_DOC_REL,  rst.getString("co_tipdocrel"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_COD_DOC_REL,      rst.getString("co_docrel"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_COD_REG_REL,      rst.getString("co_regrel"));
                        vecReg.add(objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB_AUX,rst.getString("nd_can"));
                        vecDat.add(vecReg);
                        
                        if(strEstNotPedCer.equals(""))
                            strEstNotPedCer=rst.getObject("st_cie")==null?"":rst.getString("st_cie");
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    if(objCom76_02.asignarVectorModeloDatos(vecDat)){
                        vecDat.clear();
                    }
                    else{
                        mostrarMsgInf("Los datos del detalle no se pudieron cargar correctamente");
                    }
                    
                    if(strEstNotPedCer.equals("S"))
                        chkCerNotPed.setSelected(true);
                    else
                        chkCerNotPed.setSelected(false);
                    
                    objCom76_02.regenerarCalculoCfrAra();
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
     * Esta funcián permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCarPagImp(){
        boolean blnRes=true;
        BigDecimal bgdValFleSav=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValAraSav=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValGasSav=new BigDecimal(BigInteger.ZERO);
        Object objValFleSav=null;
        Object objValAraSav=null;
        Object objValGasSav=null;
        String strTipCarPag="";
        try{
            objTblModCom76_01.removeAllRows();
            if (!txtCodTipDoc.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.co_carpag, a1.nd_valcarpag";
                    strSQL+="      , a2.tx_nom AS tx_nomCarPag, a2.tx_tipcarpag";
                    strSQL+=" FROM tbm_carPagPedEmbImp AS a1 INNER JOIN tbm_carPagImp AS a2";
                    strSQL+=" ON a1.co_carpag=a2.co_carpag";
                    strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                    strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);
                    strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                    strSQL+=" ORDER BY a1.co_reg";
                    System.out.println("cargarCarPagImp: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(objCom76_01.INT_TBL_DAT_LIN,"");
                        vecReg.add(objCom76_01.INT_TBL_DAT_TIP_CAR_PAG_IMP,    rst.getString("tx_tipcarpag"));
                        vecReg.add(objCom76_01.INT_TBL_DAT_COD_CAR_PAG_IMP,    rst.getString("co_carpag"));
                        vecReg.add(objCom76_01.INT_TBL_DAT_NOM_CAR_PAG_IMP,    rst.getString("tx_nomCarPag"));
                        vecReg.add(objCom76_01.INT_TBL_DAT_VAL_CAR_PAG_IMP,    rst.getString("nd_valcarpag"));
                        vecReg.add(objCom76_01.INT_TBL_DAT_VAL_CAR_PAG_IMP_AUX,rst.getString("nd_valcarpag"));
                        vecReg.add(objCom76_01.INT_TBL_DAT_TIP_NOT_PED,        "");

                        if(optTmFob.isSelected())
                            vecReg.setElementAt("1", objCom76_01.INT_TBL_DAT_TIP_NOT_PED);
                        else if(optTmCfr.isSelected())
                            vecReg.setElementAt("2", objCom76_01.INT_TBL_DAT_TIP_NOT_PED);
                        else if(optPzaFob.isSelected())
                            vecReg.setElementAt("3", objCom76_01.INT_TBL_DAT_TIP_NOT_PED);
                        else if(optPzaCfr.isSelected())
                            vecReg.setElementAt("4", objCom76_01.INT_TBL_DAT_TIP_NOT_PED);


                        strTipCarPag=rst.getObject("tx_tipcarpag")==null?"":rst.getString("tx_tipcarpag");
                        if(strTipCarPag.equals("F")){
                            //bgdValFleSav=bgdValFleSav.add(new BigDecimal(rst.getObject("nd_valcarpag")==null?"0":(rst.getString("nd_valcarpag").equals("")?"0":rst.getString("nd_valcarpag"))));
                            objValFleSav=rst.getObject("nd_valCarPag");
                            if((objValFleSav==null) || (objValFleSav.equals("")) ){                                
                            }
                            else{
                                bgdValFleSav=bgdValFleSav.add(new BigDecimal(objValFleSav.toString()));
                            }
                        }
                        else if(strTipCarPag.equals("A")){
                            //bgdValAraSav=bgdValAraSav.add(new BigDecimal(rst.getObject("nd_valcarpag")==null?"0":(rst.getString("nd_valcarpag").equals("")?"0":rst.getString("nd_valcarpag"))));
                            objValAraSav=rst.getObject("nd_valcarpag");
                            if((objValAraSav==null) || (objValAraSav.equals("")) ){                                
                            }
                            else{
                                bgdValAraSav=bgdValAraSav.add(new BigDecimal(objValAraSav.toString()));
                            }                            
                        }
                        else if(strTipCarPag.equals("G")){
                            //bgdValGasSav=bgdValGasSav.add(new BigDecimal(rst.getObject("nd_valcarpag")==null?"0":(rst.getString("nd_valcarpag").equals("")?"0":rst.getString("nd_valcarpag"))));
                            objValGasSav=rst.getObject("nd_valcarpag");
                            if((objValGasSav==null) || (objValGasSav.equals("")) ){                                
                            }
                            else{
                                bgdValGasSav=bgdValGasSav.add(new BigDecimal(objValGasSav.toString()));
                            }
                        }
                        vecDat.add(vecReg);
                    }

                    if(bgdValFleSav.compareTo(BigDecimal.ZERO)>=0)
                        objCom76_01.setFleteItem(bgdValFleSav);
                    if(bgdValAraSav.compareTo(BigDecimal.ZERO)>=0)
                        objCom76_01.setDerechosArancelarios(bgdValAraSav);
                    if(bgdValGasSav.compareTo(BigDecimal.ZERO)>=0)
                        objCom76_01.setTotalGastos(bgdValGasSav);

                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    if(objCom76_01.asignarVectorModeloDatos(vecDat)){
                        objCom76_01.calculaTotalFletes();
                        objCom76_01.calculaTotalAranceles();
                        objCom76_01.calculaTotalGastos();
                        vecDat.clear();
                    }
                    else{
                        mostrarMsgInf("Los datos del detalle no se pudieron cargar correctamente");
                    }
                }
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
    private boolean insertarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            objCodSegInsAnt=null;
            if(con!=null){
                if(insertar_tbmCabPedEmbImp()){
                    if(insertar_tbmDetPedEmbImp()){
                        if(modificar_tbmCabNotPedImp()){
                            if(modificar_tbmDetNotPedImp()){
                                if(insertar_tbmCarPagPedEmbImp()){
                                    if(getCodSegNotPedImp()){
                                        if(objSegMovInv.setSegMovInvCompra(con, objCodSegInsAnt, 2, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))){
                                            con.commit();
                                            blnRes=true;
                                            enviarCorElePreVta();
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
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCabPedEmbImp(){
        boolean blnRes=true;
        int intUltReg=-1;
        intNumPedEmbDia=-1;
        try{
            txtPesTotKgr.setText(""+objCom76_02.calcularV20PesoTotal());

            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;

                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT (CASE WHEN MAX(a1.co_doc)+1 IS NULL THEN 0 ELSE MAX(a1.co_doc)+1 END )  AS co_doc";
                strSQL+=" FROM tbm_cabPedEmbImp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intUltReg=rst.getInt("co_doc");
                }
                if (intUltReg==-1) 
                    return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);
                
                strSQL="";
                strSQL+=" SELECT (COUNT(a1.co_tipDoc)) AS ne_numDocPedEmb";
                strSQL+=" FROM tbm_cabPedEmbImp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND CAST(fe_ing AS DATE)=CURRENT_DATE";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intNumPedEmbDia=rst.getInt("ne_numDocPedEmb");
                }
                intNumPedEmbDia++;                
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" INSERT INTO tbm_cabPedEmbImp(";
                strSQL+="     co_emp, co_loc, co_tipdoc, co_doc, fe_doc, fe_emb, fe_arr, ne_numdoc, tx_numDoc2";
                strSQL+="   , ne_tipnotped, co_imp, co_exp, co_ciupueemb, co_seg, co_forpag";
                strSQL+="   , tx_tipemp, nd_valdoc, nd_pestotkgr, st_imp, tx_obs1, tx_obs2";
                strSQL+="   , st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod";
                strSQL+="   , tx_numdoc3, tx_direnv, ne_forenv, ne_diacre, st_cie";
                strSQL+=" )";
                strSQL+=" VALUES (";
                strSQL+="  " + objParSis.getCodigoEmpresa(); //co_emp
                strSQL+=", " + objParSis.getCodigoLocal();   //co_loc
                strSQL+=", " + txtCodTipDoc.getText();       //co_tipdoc                
                strSQL+=", " + txtCodDoc.getText();          //co_doc
                strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_doc
                strSQL+=", '" + objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_emb
                strSQL+=", '" + objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_arr
                strSQL+=", " + objUti.codificar(txtNumDoc.getText(),2) + ""; //ne_numdoc
                strSQL+=", " + objUti.codificar(txtNumPed.getText(),1) + ""; //tx_numDoc2
                if(optTmFob.isSelected())
                    strSQL+=",'1'"; //ne_tipnotped
                else if(optTmCfr.isSelected())
                    strSQL+=",'2'"; //ne_tipnotped
                else if(optPzaFob.isSelected())
                    strSQL+=",'3'"; //ne_tipnotped
                else if(optPzaCfr.isSelected())
                    strSQL+=",'4'"; //ne_tipnotped                
                strSQL+=", " + objUti.codificar(txtCodImp.getText()) + ""; //co_imp
                strSQL+=", " + objUti.codificar(txtCodExp.getText()) + ""; //co_exp
                strSQL+=", " + objUti.codificar(txtCodPto.getText()) + ""; //co_ciupueemb
                strSQL+=", " + objUti.codificar(txtCodSeg.getText()) + ""; //co_seg
                strSQL+=", " + objUti.codificar(txtCodForPag.getText()) + ""; //co_forpag
                strSQL+=", " + objUti.codificar(txtEmpExp.getText()) + ""; //tx_tipemp
                strSQL+=", " + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3) + ""; //nd_valdoc
                strSQL+=", " + objUti.codificar(txtPesTotKgr.getText()) + ""; //nd_pestotkgr
                strSQL+=", 'N'"; //st_imp
                strSQL+=", " + objUti.codificar(txaObs1.getText()) + ""; //tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText()) + ""; //tx_obs2
                strSQL+=", 'A'";//st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'"; //fe_ing
                strSQL+=", '" + strAux + "'"; //fe_ultMod
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usring
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usrmod
                strSQL+="," + objUti.codificar(txtNumFacAdu.getText()) + "";//tx_numdoc3
                strSQL+="," + objUti.codificar(txtEnvDes.getText()) + "";//tx_direnv
                strSQL+="," + cboForEnv.getSelectedIndex() + "";//ne_forenv
                strSQL+="," + objUti.codificar(txtDiaCre.getText()) + "";//ne_diacre
                if(chkCerPedEmb.isSelected())
                    strSQL+=",'S'";//st_cie
                else
                    strSQL+=",'N'";//st_cie
                strSQL+=");";
                //Actualiza el último número de documento.
                strSQL+=" UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();       
                strSQL+=" ;";
                System.out.println("insertar: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
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
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmDetPedEmbImp(){
        boolean blnRes=true;
        String strSQLIns="";
        BigDecimal bgdVarValTmp=new BigDecimal("0");
        int j=1;
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblModCom76_02.getRowCountTrue(); i++){
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detpedembimp(";
                    strSQL+="        co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel,";
                    strSQL+="        co_docrel, co_regrel, co_itm, co_exp, nd_ara, nd_can, nd_preuni, nd_valtotfobcfr,";
                    strSQL+="        nd_canTonMet, nd_valfle, nd_valcfr, nd_valtotara, nd_valtotgas, nd_valtotcos,";
                    strSQL+="        nd_cosuni)";
                    strSQL+=" VALUES (";
                    strSQL+=" " + objParSis.getCodigoEmpresa(); //co_emp
                    strSQL+=", " + objParSis.getCodigoLocal();  //co_loc
                    strSQL+=", " + txtCodTipDoc.getText();      //co_tipdoc
                    strSQL+=", " + txtCodDoc.getText();         //co_doc
                    strSQL+=", " + j; //co_reg
                    strSQL+=", " + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_LOC_REL);     //co_locrel
                    strSQL+=", " + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_TIP_DOC_REL); //co_tipdocrel
                    strSQL+=", " + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_DOC_REL);     //co_docrel
                    strSQL+=", " + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_REG_REL);     //co_regrel
                    strSQL+=", " + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_ITM);         //co_itm
                    strSQL+=", " + objUti.codificar(txtCodExp.getText()) + ""; //co_exp
                    
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_POR_ARA)==null?"0":(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_POR_ARA).toString().equals("")?"0":objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_POR_ARA).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_ara
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB)==null?"0":(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB).toString().equals("")?"0":objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_can
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_PRE_UNI)==null?"0":(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_PRE_UNI).toString().equals("")?"0":objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_PRE_UNI).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_preuni
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_TOT_FOB_CFR).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valtotfobcfr
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_TON_MET).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_canTonMet
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_VAL_FLE)==null?"0":(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_VAL_FLE).toString().equals("")?"0":objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_VAL_FLE).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valfle
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_VAL_CFR)==null?"0":(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_VAL_CFR).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valcfr
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_TOT_ARA)==null?"0":(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_TOT_ARA).toString().equals("")?"0":objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_TOT_ARA).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valtotara
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_TOT_GAS)==null?"0":(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_TOT_GAS).toString().equals("")?"0":objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_TOT_GAS).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valtotgas
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_TOT_COS)==null?"0":(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_TOT_COS).toString().equals("")?"0":objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_TOT_COS).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valtotcos
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COS_UNI)==null?"0":(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COS_UNI).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_cosuni
                    strSQL+=");";
                    strSQLIns+=strSQL;
                    j++;                    
                }
                System.out.println("insertar_tbmDetPedEmbImp: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
            }
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
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean modificar_tbmDetNotPedImp(){
        boolean blnRes=true;
        String strSQLIns="";
        BigDecimal bgdVarValTmp=new BigDecimal("0");
        BigDecimal bgdVarUtiNotPedAnt=new BigDecimal("0");
        int intCodEmpFilEstEli=-1, intCodLocFilEstEli=-1, intCodTipDocFilEstEli=-1, intCodDocFilEstEli=-1, intCodRegFilEstEli=-1;
        BigDecimal bgdVarValTmpFilEli=new BigDecimal("0");
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblModCom76_02.getRowCountTrue(); i++){
                    //Armar la sentencia SQL.
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB_AUX)==null?"0":(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB_AUX).toString().equals("")?"0":objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB_AUX).toString()), objParSis.getDecimalesBaseDatos());
                    bgdVarUtiNotPedAnt=objUti.redondearBigDecimal(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB)==null?"0":(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB).toString().equals("")?"0":objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB).toString()), objParSis.getDecimalesBaseDatos());

                    strSQL="";
                    strSQL+="UPDATE tbm_detNotPedImp";
                    if( (objTooBar.getEstado()=='z') || (objTooBar.getEstado()=='a') || (objTooBar.getEstado()=='y') || (objTooBar.getEstado()=='e')  ){
                        strSQL+=" SET nd_canUtiPedEmb=((CASE WHEN nd_canUtiPedEmb IS NULL THEN 0 ELSE nd_canUtiPedEmb END)-" + bgdVarValTmp + ")";
                    }
                    else{
                        strSQL+=" SET nd_canUtiPedEmb=((CASE WHEN nd_canUtiPedEmb IS NULL THEN 0 ELSE nd_canUtiPedEmb END)-" + bgdVarValTmp + "+" + bgdVarUtiNotPedAnt + ")";
                    }
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_LOC_REL) + "";
                    strSQL+=" AND co_tipDoc=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_TIP_DOC_REL) + "";
                    strSQL+=" AND co_doc=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_DOC_REL) + "";
                    strSQL+=" AND co_reg=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_REG_REL) + "";
                    strSQL+=";";
                    strSQLIns+=strSQL;
                }

               for(int h=0; h<arlDatFilEliCom76_03.size(); h++){
                   intCodEmpFilEstEli=objUti.getIntValueAt(arlDatFilEliCom76_03, h, objCom76_02.INT_CFE_COD_EMP);
                   intCodLocFilEstEli=objUti.getIntValueAt(arlDatFilEliCom76_03, h, objCom76_02.INT_CFE_COD_LOC);
                   intCodTipDocFilEstEli=objUti.getIntValueAt(arlDatFilEliCom76_03, h, objCom76_02.INT_CFE_COD_TIP_DOC);
                   intCodDocFilEstEli=objUti.getIntValueAt(arlDatFilEliCom76_03, h, objCom76_02.INT_CFE_COD_DOC);
                   intCodRegFilEstEli=objUti.getIntValueAt(arlDatFilEliCom76_03, h, objCom76_02.INT_CFE_COD_REG);
                   bgdVarValTmpFilEli=objUti.getBigDecimalValueAt(arlDatFilEliCom76_03, h, objCom76_02.INT_CFE_CAN_PED_EMB_AUX);
                   //bgdVarValCanUtiPedEmb=objUti.getBigDecimalValueAt(arlDatFilEliCom76_03, h, objCom76_02.INT_CFE_CAN_UTI_PED_EMB);

                   strSQL="";
                   strSQL+=" UPDATE tbm_detNotPedImp";
                   strSQL+=" SET nd_canUtiPedEmb=((CASE WHEN nd_canUtiPedEmb IS NULL THEN 0 ELSE nd_canUtiPedEmb END)-" + bgdVarValTmpFilEli + ")";
                   strSQL+=" WHERE co_emp=" + intCodEmpFilEstEli + "";
                   strSQL+=" AND co_loc=" + intCodLocFilEstEli + "";
                   strSQL+=" AND co_tipDoc=" + intCodTipDocFilEstEli + "";
                   strSQL+=" AND co_doc=" + intCodDocFilEstEli + "";
                   strSQL+=" AND co_reg=" + intCodRegFilEstEli + "";
                   strSQL+=" ;";
                   strSQLIns+=strSQL;
               }
                System.out.println("modificar_tbmDetNotPedImp: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
            }
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
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean modificar_tbmCabNotPedImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                if(chkCerNotPed.isSelected()){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="" + getQueryUpdate_tbmCabNotPedImp();
                    System.out.println("modificar_tbmCabNotPedImp: " + strSQL);
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm=null;
                }
            }
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
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCarPagPedEmbImp(){
        boolean blnRes=true;
        String strSQLIns="";
        Object objVarValTmp=null;
        int j=1;
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblModCom76_01.getRowCountTrue(); i++){
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_carPagPedEmbImp(";
                    strSQL+="          co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_carPag, nd_valCarPag)";
                    strSQL+=" VALUES (";
                    strSQL+=" " + objParSis.getCodigoEmpresa(); //co_emp
                    strSQL+=", " + objParSis.getCodigoLocal(); //co_loc
                    strSQL+=", " + txtCodTipDoc.getText(); //co_tipdoc
                    strSQL+=", " + txtCodDoc.getText(); //co_doc
                    strSQL+=", " + j; //co_reg
                    strSQL+=", " + objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_COD_CAR_PAG_IMP); //co_carpag
                    //objVarValTmp=objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).equals("")?null:objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_VAL_CAR_PAG_IMP);
                    objVarValTmp=objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_VAL_CAR_PAG_IMP);
                    strSQL+=", " + objVarValTmp; //nd_valcarpag
                    strSQL+=");";
                    strSQLIns+=strSQL;
                    j++;
                }
                System.out.println("insertar_tbmCarPagPedEmbImp: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
            }
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
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCarPagPedEmbImp(){
        boolean blnRes=true;
        String strSQLIns="";
        Object objVarValTmp=null;
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblModCom76_01.getRowCountTrue(); i++){
                    objVarValTmp=objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_VAL_CAR_PAG_IMP);
                    //Armar la sentencia SQL.
                    strSQL ="";
                    //Actualiza
                    strSQL+=" UPDATE tbm_carPagPedEmbImp ";
                    strSQL+=" SET nd_valCarPag="+objVarValTmp;
                    strSQL+=" WHERE co_emp="+ objParSis.getCodigoEmpresa(); //co_emp
                    strSQL+=" AND co_loc="+ objParSis.getCodigoLocal();     //co_loc
                    strSQL+=" AND co_tipDoc="+ txtCodTipDoc.getText();      //co_tipDoc
                    strSQL+=" AND co_doc="+ txtCodDoc.getText();            //co_doc
                    strSQL+=" AND co_carpag=" + objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_COD_CAR_PAG_IMP)+";"; //co_carpag
                    
                    //Inserta solo los que no existen
                    strSQL+=" INSERT INTO tbm_carPagPedEmbImp(co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_carPag, nd_valCarPag)";
                    strSQL+=" (SELECT " + objParSis.getCodigoEmpresa();
                    strSQL+="       , " + objParSis.getCodigoLocal();
                    strSQL+="       , " + txtCodTipDoc.getText();
                    strSQL+="       , " + txtCodDoc.getText();
                    strSQL+="       , " + objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_COD_CAR_PAG_IMP); //co_reg
                    strSQL+="       , " + objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_COD_CAR_PAG_IMP); //co_carpag
                    strSQL+="       , " + objVarValTmp; //nd_valcarpag
                    strSQL+="  WHERE CASE WHEN EXISTS ";
                    strSQL+="  ( SELECT * FROM tbm_carPagPedEmbImp ";
                    strSQL+="    WHERE co_emp=" + objParSis.getCodigoEmpresa();     //co_emp
                    strSQL+="    AND co_loc=" + objParSis.getCodigoLocal();         //co_loc
                    strSQL+="    AND co_tipDoc=" + txtCodTipDoc.getText();          //co_tipDoc
                    strSQL+="    AND co_doc=" + txtCodDoc.getText();                //co_doc
                    strSQL+="    AND co_carPag=" + objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_COD_CAR_PAG_IMP)+" "; //co_carpag
                    strSQL+="   ) THEN FALSE ELSE TRUE END";    
                    strSQL+=");";    
                    strSQLIns+=strSQL;                    
                }
                System.out.println("actualizar_tbmCarPagPedEmbImp: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
            }
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
     * Esta función permite modificar el valor del Flete Aproximado
     * @return true: Si se pudo insertar el valor del flete.
     * <BR>false: En el caso contrario.
     */
    private boolean modifica_fleAprTbmCarPagPedEmbImp(){
        boolean blnRes=true;
        BigDecimal bgdVarValTmp=new BigDecimal("0");
        String strTipCarPagImp="";
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblModCom76_01.getRowCountTrue(); i++){
                    
                    strTipCarPagImp=objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_TIP_CAR_PAG_IMP)==null?"":objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_TIP_CAR_PAG_IMP).toString();
                    if(strTipCarPagImp.equals("O")){
                        bgdVarValTmp=objUti.redondearBigDecimal(objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_VAL_CAR_PAG_IMP)==null?"0":(objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).toString().equals("")?"0":objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).toString()), objParSis.getDecimalesBaseDatos());
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_carPagPedEmbImp";
                        strSQL+=" SET nd_valCarPag=" + bgdVarValTmp + "";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() +"";
                        strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND co_tipdoc=" + txtCodTipDoc.getText() + "";
                        strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                        strSQL+=" AND co_carPag=" + objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_COD_CAR_PAG_IMP) + "";                    
                        strSQL+=";";
                        System.out.println("modifica_fleAprTbmCarPagPedEmbImp: " + strSQL);
                        stm.executeUpdate(strSQL);
                        stm.close();
                        stm=null;
                    }
                }
            }
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

    private boolean actualizarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            
            if (con!=null){                                
                if(  (chkModSolFecEmbArr.isSelected()) ||  (chkFleApr.isSelected())   ){
                    if((chkModSolFecEmbArr.isSelected())){
                        if(actualizar_tbmCabPedEmbImp_FecEmbArr()){
                            con.commit();
                            blnRes=true;
                            if(isChangeFecha()){
                                enviarCorEleDptoVta();
                            }
                        }
                        else
                            con.rollback();
                    }
                    else if(chkFleApr.isSelected()){
                        if(modifica_fleAprTbmCarPagPedEmbImp()){
                             con.commit();
                             blnRes=true;
                        }
                        else
                            con.rollback();
                    }
                }
                else{
                    if(actualizar_tbmCabPedEmbImp()){
                        if(eliminar_tbmDetPedEmbImp()){
                            //if(eliminar_tbmCarPagPedmbImp()){
                                if(insertar_tbmDetPedEmbImp()){
                                    if(modificar_tbmCabNotPedImp()){
                                        if(modificar_tbmDetNotPedImp()){
                                            //if (insertar_tbmCarPagPedEmbImp()){
                                                if (actualizar_tbmCarPagPedEmbImp()){
                                                    con.commit();
                                                    blnRes=true;
                                                    if(isChangeFecha()){
                                                        enviarCorEleDptoVta();
                                                    } 
                                                }
                                                else
                                                    con.rollback();
                                            //}
                                            //else
                                            //    con.rollback();                                                    
                                        }
                                        else
                                            con.rollback();
                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                    con.rollback();
                            //}
                            //else
                            //    con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabPedEmbImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;

                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabPedEmbImp";
                strSQL+=" SET ne_numdoc=" + objUti.codificar(txtNumDoc.getText(),2) + "";
                strSQL+=", tx_numDoc2=" + objUti.codificar(txtNumPed.getText(),1) + "";

                if(optTmFob.isSelected())
                    strSQL+=", ne_tipnotped='1'";//ne_tipnotped
                else if(optTmCfr.isSelected())
                    strSQL+=", ne_tipnotped='2'";//ne_tipnotped
                else if(optPzaFob.isSelected())
                    strSQL+=", ne_tipnotped='3'"; //ne_tipnotped
                else if(optPzaCfr.isSelected())
                    strSQL+=", ne_tipnotped='4'";//ne_tipnotped
                
                strSQL+=", co_imp=" + objUti.codificar(txtCodImp.getText()) + "";
                strSQL+=", co_exp=" + objUti.codificar(txtCodExp.getText()) + "";
                strSQL+=", co_ciupueemb=" + objUti.codificar(txtCodPto.getText()) + "";
                strSQL+=", co_seg=" + objUti.codificar(txtCodSeg.getText()) + "";
                strSQL+=", co_forpag=" + objUti.codificar(txtCodForPag.getText()) + "";
                strSQL+=", fe_emb='" + objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", fe_arr='" + objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", tx_tipemp=" + objUti.codificar(txtEmpExp.getText()) + "";
                strSQL+=", nd_valdoc=" + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3) + "";
                strSQL+=", nd_pestotkgr=" + objUti.codificar(txtPesTotKgr.getText()) + "";               
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText()) + "";
                strSQL+=", tx_obs2=" + objUti.codificar(txaObs2.getText()) + "";
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", fe_ultmod='" + strAux + "'";
                strSQL+=", co_usrmod=" + objParSis.getCodigoUsuario() + "";
                strSQL+=", tx_numdoc3=" + objUti.codificar(txtNumFacAdu.getText()) + "";
                strSQL+=", tx_direnv=" + objUti.codificar(txtEnvDes.getText()) + "";
                strSQL+=", ne_forenv=" + cboForEnv.getSelectedIndex() + "";
                strSQL+=", ne_diacre=" + objUti.codificar(txtDiaCre.getText()) + "";
                
                if(chkCerPedEmb.isSelected())
                    strSQL+=", st_cie='S'";//st_cie
                else
                    strSQL+=", st_cie='N'";//st_cie
                
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                strSQL+=";";

                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
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
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabPedEmbImp_FecEmbArr(){
        boolean blnRes=true;
        try{
            if (con!=null){
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;

                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabPedEmbImp";
                strSQL+=" SET ";
                strSQL+=" fe_emb='" + objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", fe_arr='" + objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", fe_ultmod='" + strAux + "'";
                strSQL+=", co_usrmod=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                strSQL+=";";

                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
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
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmDetPedEmbImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" DELETE FROM tbm_detPedEmbImp";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                strSQL+=";";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
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
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmCarPagPedmbImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE FROM tbm_carPagPedEmbImp";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                strSQL+=";";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
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
     * Esta función obtiene la descripción larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripción larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la función devuelve una cadena vacía.
     */
    private String getEstReg(String estado)
    {
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0))
            {
                case 'A':
                    estado="Activo";
                    break;
                case 'I':
                    estado="Anulado";
                    break;
                case 'P':
                    estado="Pendiente de autorizar";
                    break;
                case 'D':
                    estado="Autorización denegada";
                    break;
                case 'R':
                    estado="Pendiente de impresión";
                    break;
                case 'C':
                    estado="Pendiente confirmación de inventario";
                    break;
                case 'F':
                    estado="Existen diferencias de inventario";
                    break;
                default:
                    estado="Desconocido";
                    break;
            }
        return estado;
    }
    

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }

    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        if (txtDesCorTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        if (txtNomImp.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Importador</FONT> es obligatorio.<BR>Seleccione un Importador y vuelva a intentarlo.</HTML>");
            txtNomImp.requestFocus();
            return false;
        }
        if (txtNomExp2.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Exportador</FONT> es obligatorio.<BR>Seleccione un Exportador y vuelva a intentarlo.</HTML>");
            txtNomExp2.requestFocus();
            return false;
        }
        if (txtNumDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Escriba un Número de documento y vuelva a intentarlo.</HTML>");
            txtNumDoc.requestFocus();
            return false;
        }
        if (txtNumPed.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de pedido</FONT> es obligatorio.<BR>Escriba un Número de pedido y vuelva a intentarlo.</HTML>");
            txtNumPed.requestFocus();
            return false;
        }
                
        //Validar que se ingresen solo pedidos con items Terminal 'S' de compras locales
        if(isExisteItemTerminalDiferente()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Existe uno o varios items cuyo terminal es diferente a 'S'<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;
        }

        if(objTooBar.getEstado()=='n'){

            if(!objTblModCom76_01.isDataModelChanged()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La tabla de Cargos a Pagar no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                return false;
            }

            if(!objTblModCom76_02.isDataModelChanged()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La tabla de Items y valores no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                return false;
            }

            if(objTblModCom76_02.getRowCountTrue()<=0){
                objTblModCom76_02.removeEmptyRows();
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La tabla de Items y valores no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                return false;
            }
        }

        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        
        //Validar el "Fecha del documento".
        if (!dtpFecEmb.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de embarque</FONT> es obligatorio.<BR>Escriba o seleccione una fecha de embarque y vuelva a intentarlo.</HTML>");
            dtpFecEmb.requestFocus();
            return false;
        }
        //Validar el "Fecha del documento".
        if (!dtpFecArr.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de arribo</FONT> es obligatorio.<BR>Escriba o seleccione una fecha de arribo y vuelva a intentarlo.</HTML>");
            dtpFecArr.requestFocus();
            return false;
        }
        
        //si es TM-FOB o TM-CFR deben tener datos cada fila de las piezas, en PZA-FOB o PZA-CFR no deben tener valores.
        BigDecimal bgdCanPza=new BigDecimal("0");
        if( (optTmFob.isSelected())  || (optTmCfr.isSelected())  ){
            for(int i=0; i<objTblModCom76_02.getRowCountTrue(); i++){
                bgdCanPza=new BigDecimal(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB)==null?"0":(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB).toString().equals("")?"0":objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB).toString()));
                
                if(bgdCanPza.compareTo(new BigDecimal("0"))<=0){
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El valor de la(s) <FONT COLOR=\"blue\">pieza(s)</FONT> es obligatorio.<BR>Escriba el(los) valor(es) y vuelva a intentarlo.</HTML>");
                    return false;                    
                }
            }
        }
        
        if(cboForEnv.getSelectedIndex()==0){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Forma de envío</FONT> es obligatorio.<BR>Seleccione una Forma de envío y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        if(txtNumFacAdu.getText().length()<=0){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de factura de Aduana</FONT> es obligatorio.<BR>Escriba un Número de factura de Aduana y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        if (txtDiaCre.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Días de crédito</FONT> es obligatorio.<BR>Escriba el número de días de crédito y vuelva a intentarlo.</HTML>");
            return false;
        }        
       
        if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m')  ){
            if(!chkModSolFecEmbArr.isSelected()){
                if(isIngresoImportacion()){
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El Pedido Embarcado tiene un <FONT COLOR=\"blue\">Ingreso por Importación </FONT>asociado.<BR>Seleccione un Pedido Embarcado diferente y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
        }
        
        
        if(isExisteItemRepetido()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Un item fue ingresado varias veces.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;  
        }
        
//        if(chkCerPedEmb.isSelected()){
//            if(isValNulCarPag_PedEmbCer()){
//                tabFrm.setSelectedIndex(0);
//                mostrarMsgInf("<HTML>El Pedido Embarcado no puede ser cerrado porque existen valores pendientes de ingresar en Cargos a Pagar.<BR>Verifique y vuelva a intentarlo.</HTML>");
//                return false;
//            }            
//        }

        if(chkCerPedEmb.isSelected()){
            if(!objCom76_01.calculaTotalFletes()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Existen valores nulos en los rubros de Flete.<BR>Verifique los valores ingresados y haga la corrección respectiva<BR>si no se realizan los cambios, los valores no se calcularán correctamente.</HTML>");
                return false;
            }

            if(!objCom76_01.calculaTotalAranceles()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Existen valores nulos en los rubros de Arancel.<BR>Verifique los valores ingresados y haga la corrección respectiva<BR>si no se realizan los cambios, los valores no se calcularán correctamente.</HTML>");
                return false;
            }

            if(!objCom76_01.calculaTotalGastos()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Existen valores nulos en los rubros de Gasto.<BR>Verifique los valores ingresados y haga la corrección respectiva<BR>si no se realizan los cambios, los valores no se calcularán correctamente.</HTML>");
                return false;
            }
            
            //Valida el valor de Tot.Fob/Cfr y el Total de Gastos registrados en el panel de Cargos a Pagar
//            if(objCom76_02.getValorTotalFobCfr().compareTo(objCom76_01.getTotalGastos())<0){//Si el valor de Tot.Fob/Cfr es menor, no debe permitir guardar
//                tabFrm.setSelectedIndex(0);
//                mostrarMsgInf("<HTML>El valor de la Factura(Tot.Fob/Cfr) es menor al Total de Gastos(Cargos a Pagar).<BR>Verifique los valores ingresados y vuelva a intentarlo.</HTML>");
//                return false;
//            }

            //Valida el valor de la sumas de todos los DxP asociados al Pedido y el Total de Gastos registrados en el panel de Cargos a Pagar
            if(objCom76_02.getValorTotalFobCfr().compareTo(getValTotDxP())<0){//Si el valor de Tot.Fob/Cfr es menor, no debe permitir guardar
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El valor de la Factura(Tot.Fob/Cfr) es menor al Total de Gastos(Cargos a Pagar).<BR>Verifique los valores ingresados y vuelva a intentarlo.</HTML>");
                return false;
            }            
        }
        
        if ((objUti.redondearBigDecimal(objCom76_02.getValorTotalArancel(), objParSis.getDecimalesMostrar())).compareTo((objUti.redondearBigDecimal(objCom76_01.getValorTotalArancel(), objParSis.getDecimalesMostrar())))!=0){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El valor de Total de Aranceles del Cargo a Pagar es diferente al valor de Total de Aranceles del detalle.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;
        }

        return true;
    }
    
    /**
     * Función que permite determinar si algún item tiene un terminal que no debe estar asociado al tipo de documento(Ejm: 'I' sólo para INIMPO ; 'S' sólo para INCOLO
     * @return true: Si el terminal del item no corresponde al tipo de documento
     * <BR> false: Caso contrario
     */
    private boolean isExisteItemTerminalDiferente(){
        boolean blnRes=false;
        String strCodAltItmTer="";
        int intCodAltItmTer=0;
        int intTipTerItm_regTblDat_i=0, intTipTerItm_regTblDat_s=0;
        try{
            for(int i=0; i<objTblModCom76_02.getRowCountTrue(); i++){
                strCodAltItmTer=objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_ALT_ITM)==null?"":objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_ALT_ITM).toString();
                intCodAltItmTer=(strCodAltItmTer.length()-1);
                
                if(strCodAltItmTer.charAt(intCodAltItmTer) == 'I'){
                    intTipTerItm_regTblDat_i++;
                }
                else if(strCodAltItmTer.charAt(intCodAltItmTer) == 'S'){
                    intTipTerItm_regTblDat_s++;
                }
                
                if( (intTipTerItm_regTblDat_i!=0)  && (intTipTerItm_regTblDat_s!=0) ){
                    break;
                }                
            }
            
            if(intTipTerItm_regTblDat_i>0){//debería ser "cero" porque el INBOCL sólo maneja terminales 'S' y esta variable refleja que hay items con terminales 'I' en la tabla 
                blnRes=true;
            }            
        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    /**
     * Esta función anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if(anular_tbmCabPedEmbImp()){
                    if(modificar_tbmDetNotPedImp()){
                        if(anular_tbmPlaCta()){
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
     * Esta función anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if(eliminar_tbmCabPedEmbImp()){
                    if(modificar_tbmDetNotPedImp()){
                        con.commit();
                        blnRes=true;
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
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anular_tbmCabPedEmbImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabPedEmbImp";
                strSQL+=" SET st_reg='I'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);

                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
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
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmCabPedEmbImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabPedEmbImp";
                strSQL+=" SET st_reg='E'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
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
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=", CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
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
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=", CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
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

                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);
                    strTipDocNecAutAnu=rst.getString("st_necautanudoc");
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
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
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
            arlCam.add("a1.tx_natDoc");
            arlCam.add("a1.st_necautanudoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Nec.Aut.Anu.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1 ";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbr_tipDocUsr AS a3 inner join  tbm_cabTipDoc AS a1 ";
                strSQL+=" ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a3.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=5;
            intColOcu[1]=6;
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
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
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE){
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));

                        if (objTooBar.getEstado()=='n'){
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));

                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
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
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
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
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
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


    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConImp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_ruc");
            arlCam.add("a2.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód. Emp.");
            arlAli.add("Nombre");
            arlAli.add("Ruc");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("130");
            arlAncCol.add("60");
            arlAncCol.add("80");

            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_emp, a1.tx_nom, a1.tx_ruc, a1.tx_dir";
            strSQL+=" FROM tbm_emp AS a1";
            strSQL+=" WHERE a1.st_reg NOT IN('I','E') AND a1.co_emp<>" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" ORDER BY a1.co_emp";
            vcoImp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de importadores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoImp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);

        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConImp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoImp.setCampoBusqueda(2);
                    vcoImp.show();
                    if (vcoImp.getSelectedButton()==vcoImp.INT_BUT_ACE){
                        txtCodImp.setText(vcoImp.getValueAt(1));
                        txtNomImp.setText(vcoImp.getValueAt(2));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoImp.buscar("a1.co_emp", txtCodImp.getText())){
                        txtCodImp.setText(vcoImp.getValueAt(1));
                        txtNomImp.setText(vcoImp.getValueAt(2));
                    }
                    else{
                        vcoImp.setCampoBusqueda(0);
                        vcoImp.setCriterio1(11);
                        vcoImp.cargarDatos();
                        vcoImp.show();
                        if (vcoImp.getSelectedButton()==vcoImp.INT_BUT_ACE){
                            txtCodImp.setText(vcoImp.getValueAt(1));
                            txtNomImp.setText(vcoImp.getValueAt(2));
                        }
                        else{
                            txtCodImp.setText(strCodPag);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoImp.buscar("a1.tx_nom", txtNomImp.getText())){
                        txtCodImp.setText(vcoImp.getValueAt(1));
                        txtNomImp.setText(vcoImp.getValueAt(2));
                    }
                    else{
                        vcoImp.setCampoBusqueda(2);
                        vcoImp.setCriterio1(11);
                        vcoImp.cargarDatos();
                        vcoImp.show();
                        if (vcoImp.getSelectedButton()==vcoImp.INT_BUT_ACE){
                            txtCodImp.setText(vcoImp.getValueAt(1));
                            txtNomImp.setText(vcoImp.getValueAt(2));
                        }
                        else{
                            txtNomImp.setText(strNomPag);
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
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConPrv()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_prv");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_prv, a1.tx_nom";
            strSQL+=" FROM tbm_prvExtImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_nom";
            vcoPrv=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoPrv.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }








    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConExp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_exp");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_nom2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            arlAli.add("Nombre alterno");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("300");
            arlAncCol.add("150");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_exp, a1.tx_nom, a1.tx_nom2";
            strSQL+=" FROM tbm_expImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            vcoExp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de exportadores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoExp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConExp(int intTipBus)
    {
        boolean blnRes=true;
        String strSQLTmp="";
        try{
            if(txtCodImp.getText().length()>0){

                strSQLTmp="";
                strSQLTmp+=" ORDER BY a1.tx_nom";
                vcoExp.setCondicionesSQL(strSQLTmp);
                
                switch (intTipBus){
                    case 0: //Mostrar la ventana de consulta.
                        vcoExp.setCampoBusqueda(1);
                        vcoExp.show();
                        if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp2.setText(vcoExp.getValueAt(2));
                            txtNomExp.setText(vcoExp.getValueAt(3));
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".
                        if (vcoExp.buscar("a1.co_exp", txtCodExp.getText()))
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp2.setText(vcoExp.getValueAt(2));
                            txtNomExp.setText(vcoExp.getValueAt(3));
                        }
                        else
                        {
                            vcoExp.setCampoBusqueda(0);
                            vcoExp.setCriterio1(11);
                            vcoExp.cargarDatos();
                            vcoExp.show();
                            if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                            {
                                txtCodExp.setText(vcoExp.getValueAt(1));
                                txtNomExp2.setText(vcoExp.getValueAt(2));
                                txtNomExp.setText(vcoExp.getValueAt(3));
                            }
                            else
                            {
                                txtCodExp.setText(strCodExp);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".
                        if (vcoExp.buscar("a1.tx_nom", txtNomExp2.getText()))
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp2.setText(vcoExp.getValueAt(2));
                            txtNomExp.setText(vcoExp.getValueAt(3));
                        }
                        else
                        {
                            vcoExp.setCampoBusqueda(2);
                            vcoExp.setCriterio1(11);
                            vcoExp.cargarDatos();
                            vcoExp.show();
                            if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                            {
                                txtCodExp.setText(vcoExp.getValueAt(1));
                                txtNomExp2.setText(vcoExp.getValueAt(2));
                                txtNomExp.setText(vcoExp.getValueAt(3));
                            }
                            else
                            {
                                txtNomExp2.setText(strNomExp);
                            }
                        }
                        break;
                }
            }
             else{
                mostrarMsgInf("Se debe seleccionar el Importador previamente.");
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
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConPto()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_ciupueemb");
            arlCam.add("tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_ciu AS co_ciupueemb, a1.tx_desLar";
            strSQL+=" FROM tbm_ciu AS a1 LEFT OUTER JOIN tbm_expImp AS a2";
            strSQL+=" ON a1.co_ciu=a2.co_ciu AND a2.st_reg='A'";
            strSQL+=" WHERE a1.st_reg='A' ";
            strSQL+=" ORDER BY a1.tx_desLar";

            vcoPto=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de puertos de embarque", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoPto.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConPto(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoPto.setCampoBusqueda(2);
                    vcoPto.show();
                    if (vcoPto.getSelectedButton()==vcoPto.INT_BUT_ACE){
                        txtCodPto.setText(vcoPto.getValueAt(1));
                        txtNomPto.setText(vcoPto.getValueAt(2));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoPto.buscar("a1.co_ciupueemb", txtCodPto.getText())){
                        txtCodPto.setText(vcoPto.getValueAt(1));
                        txtNomPto.setText(vcoPto.getValueAt(2));
                    }
                    else{
                        vcoPto.setCampoBusqueda(0);
                        vcoPto.setCriterio1(11);
                        vcoPto.cargarDatos();
                        vcoPto.show();
                        if (vcoPto.getSelectedButton()==vcoPto.INT_BUT_ACE){
                            txtCodPto.setText(vcoPto.getValueAt(1));
                            txtNomPto.setText(vcoPto.getValueAt(2));
                        }
                        else{
                            txtCodPto.setText(strCodPto);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoPto.buscar("a1.tx_desLar", txtNomPto.getText())){
                        txtCodPto.setText(vcoPto.getValueAt(1));
                        txtNomPto.setText(vcoPto.getValueAt(2));
                    }
                    else{
                        vcoPto.setCampoBusqueda(2);
                        vcoPto.setCriterio1(11);
                        vcoPto.cargarDatos();
                        vcoPto.show();
                        if (vcoPto.getSelectedButton()==vcoPto.INT_BUT_ACE){
                            txtCodPto.setText(vcoPto.getValueAt(1));
                            txtNomPto.setText(vcoPto.getValueAt(2));
                        }
                        else{
                            txtNomPto.setText(strNomPto);
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
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConSeg()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_seg");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_seg, a1.tx_desLar";
            strSQL+=" FROM tbm_segImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_desLar";
            vcoSeg=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de seguros", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoSeg.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConSeg(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoSeg.setCampoBusqueda(2);
                    vcoSeg.show();
                    if (vcoSeg.getSelectedButton()==vcoSeg.INT_BUT_ACE){
                        txtCodSeg.setText(vcoSeg.getValueAt(1));
                        txtNomSeg.setText(vcoSeg.getValueAt(2));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoSeg.buscar("a1.co_seg", txtCodSeg.getText())){
                        txtCodSeg.setText(vcoSeg.getValueAt(1));
                        txtNomSeg.setText(vcoSeg.getValueAt(2));
                    }
                    else{
                        vcoSeg.setCampoBusqueda(0);
                        vcoSeg.setCriterio1(11);
                        vcoSeg.cargarDatos();
                        vcoSeg.show();
                        if (vcoSeg.getSelectedButton()==vcoSeg.INT_BUT_ACE){
                            txtCodSeg.setText(vcoSeg.getValueAt(1));
                            txtNomSeg.setText(vcoSeg.getValueAt(2));
                        }
                        else{
                            txtCodSeg.setText(strCodSeg);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoSeg.buscar("a1.tx_desLar", txtNomSeg.getText())){
                        txtCodSeg.setText(vcoSeg.getValueAt(1));
                        txtNomSeg.setText(vcoSeg.getValueAt(2));
                    }
                    else{
                        vcoSeg.setCampoBusqueda(2);
                        vcoSeg.setCriterio1(11);
                        vcoSeg.cargarDatos();
                        vcoSeg.show();
                        if (vcoSeg.getSelectedButton()==vcoSeg.INT_BUT_ACE){
                            txtCodSeg.setText(vcoSeg.getValueAt(1));
                            txtNomSeg.setText(vcoSeg.getValueAt(2));
                        }
                        else{
                            txtNomSeg.setText(strNomSeg);
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
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConForPag()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_forPag");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_forPag, a1.tx_desLar";
            strSQL+=" FROM tbm_forPagImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_desLar";
            vcoForPag=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de forma de pago", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoForPag.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConPag(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoForPag.setCampoBusqueda(2);
                    vcoForPag.show();
                    if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE){
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoForPag.buscar("a1.co_forPag", txtCodForPag.getText())){
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                    }
                    else{
                        vcoForPag.setCampoBusqueda(0);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.show();
                        if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE){
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtNomForPag.setText(vcoForPag.getValueAt(2));
                        }
                        else{
                            txtCodForPag.setText(strCodPag);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoForPag.buscar("a1.tx_desLar", txtNomForPag.getText())){
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                    }
                    else{
                        vcoForPag.setCampoBusqueda(2);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.show();
                        if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE){
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtNomForPag.setText(vcoForPag.getValueAt(2));
                        }
                        else{
                            txtNomForPag.setText(strNomPag);
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




    public JTextField getTxtPesTotKgs() {
        return txtPesTotKgr;
    }

    public void setTxtPesTotKgs(String strPesTotKgs) {
        this.txtPesTotKgr.setText(strPesTotKgs);
    }

    private boolean setCabeceraPedidoEmbarcado(){
        boolean blnRes=true;
        String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="";
        try{
            if(arlDatCabCom76_03.size()>0){
                strCodEmp=objUti.getStringValueAt(arlDatCabCom76_03, 0, objCom76_03.INT_TBL_ARL_DAT_COD_EMP);
                strCodLoc=objUti.getStringValueAt(arlDatCabCom76_03, 0, objCom76_03.INT_TBL_ARL_DAT_COD_LOC);
                strCodTipDoc=objUti.getStringValueAt(arlDatCabCom76_03, 0, objCom76_03.INT_TBL_ARL_DAT_COD_TIP_DOC);
                strCodDoc=objUti.getStringValueAt(arlDatCabCom76_03, 0, objCom76_03.INT_TBL_ARL_DAT_COD_DOC);

                txtCodExp.setText(objUti.getStringValueAt(arlDatCabCom76_03, 0, objCom76_03.INT_TBL_ARL_DAT_COD_EXP));
                txtNomExp2.setText(objUti.getStringValueAt(arlDatCabCom76_03, 0, objCom76_03.INT_TBL_ARL_DAT_NOM_EXP));
                txtNomExp.setText(objUti.getStringValueAt(arlDatCabCom76_03, 0, objCom76_03.INT_TBL_ARL_DAT_NOM_ALT_EXP));

                strAux=objUti.getStringValueAt(arlDatCabCom76_03, 0, objCom76_03.INT_TBL_ARL_DAT_TIP_NOT_PED);
                if(strAux.equals("1")){
                    optTmFob.setSelected(true);                optTmCfr.setSelected(false);
                    optPzaFob.setSelected(false);              optPzaCfr.setSelected(false);
                    intTipNotPed=1;
                }
                else if(strAux.equals("2")){
                    optTmFob.setSelected(false);                optTmCfr.setSelected(true);
                    optPzaFob.setSelected(false);               optPzaCfr.setSelected(false);
                    intTipNotPed=2;
                }
                else if(strAux.equals("3")){
                    optTmFob.setSelected(false);                optTmCfr.setSelected(false);
                    optPzaFob.setSelected(true);                optPzaCfr.setSelected(false);
                    intTipNotPed=3;
                }
                else if(strAux.equals("4")){
                    optTmFob.setSelected(false);                optTmCfr.setSelected(false);
                    optPzaFob.setSelected(false);               optPzaCfr.setSelected(true);
                    intTipNotPed=4;
                }

                objCom76_01.setTipoNotaPedido(intTipNotPed);
                objCom76_01.setValorFleteInactivo();
                objCom76_02.setTipoNotaPedido(intTipNotPed);

                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.ne_numdoc, a1.ne_numdoc AS tx_numDoc2, a1.ne_tipnotped";
                    strSQL+="      , a1.co_imp, a1.co_prv, a1.co_exp, a1.co_ciupueemb, a1.co_seg, a1.co_forpag, a1.co_mesemb";
                    strSQL+="      , a1.tx_tipemp, a1.nd_valdoc, a1.nd_pestotkgr, a1.st_notpedlis, a1.st_imp, a1.tx_obs1";
                    strSQL+="      , a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc";
                    strSQL+="      , a3.co_emp AS co_imp, a3.tx_nom AS tx_nomImp";
                    strSQL+="      , a4.co_prv, a4.tx_nom AS tx_nomPrvExt";
                    strSQL+="      , a5.co_exp, a5.tx_nom AS tx_nomExp";
                    strSQL+="      , a6.co_ciu, a6.tx_deslar AS tx_nomPueEmb";
                    strSQL+="      , a7.co_seg, a7.tx_deslar AS tx_nomSeg";
                    strSQL+="      , a8.co_forpag, a8.tx_deslar AS tx_nomForPag";
                    strSQL+="      , a9.co_mesemb, a9.tx_deslar AS tx_nomMesEmb, a1.st_cie";
                    strSQL+=" FROM tbm_cabNotPedImp AS a1";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_imp=a3.co_emp)";
                    strSQL+=" INNER JOIN tbm_prvExtImp AS a4 ON(a1.co_prv=a4.co_prv)";
                    strSQL+=" INNER JOIN tbm_expImp AS a5 ON(a1.co_exp=a5.co_exp)";
                    strSQL+=" LEFT OUTER JOIN tbm_ciu AS a6 ON(a1.co_ciupueemb=a6.co_ciu)";
                    strSQL+=" LEFT OUTER JOIN tbm_segimp AS a7 ON(a1.co_seg=a7.co_seg)";
                    strSQL+=" LEFT OUTER JOIN tbm_forpagimp AS a8 ON(a1.co_forpag=a8.co_forpag)";
                    strSQL+=" LEFT OUTER JOIN tbm_mesEmbImp AS a9 ON(a1.co_mesemb=a9.co_mesemb)";
                    strSQL+=" WHERE a1.co_emp=" + strCodEmp;
                    strSQL+=" AND a1.co_loc=" + strCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + strCodTipDoc;
                    strSQL+=" AND a1.co_doc=" + strCodDoc;
                    strSQL+=" AND a1.st_cie='N'";
                    rst=stm.executeQuery(strSQL);
                    if (rst.next()){
                        strAux=rst.getString("co_imp");
                        txtCodImp.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("tx_nomImp");
                        txtNomImp.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("tx_tipemp");
                        txtEmpExp.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("co_ciupueemb");
                        txtCodPto.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("tx_nomPueEmb");
                        txtNomPto.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("co_seg");
                        txtCodSeg.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("tx_nomSeg");
                        txtNomSeg.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("co_forpag");
                        txtCodForPag.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("tx_nomForPag");
                        txtNomForPag.setText((strAux==null)?"":strAux);
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
        catch (java.sql.SQLException e)  {
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
     * Esta funcián permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean setCargosPagarPedidoEmbarcado(){
        boolean blnRes=true;
        BigDecimal bgdValFleSav=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValAraSav=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValGasSav=new BigDecimal(BigInteger.ZERO);
        
        Object objValFleSav=null;
        Object objValAraSav=null;
        Object objValGasSav=null;
        
        String strTipCarPag="";

        String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="";

        BigDecimal bgdValFleTbl=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValAraTbl=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValGasTbl=new BigDecimal(BigInteger.ZERO);
        String strCodCarPagTbl="", strCodCarPagSav="";
        try{
            
            if(arlDatCabCom76_03.size()==1){
                objCom76_01.getTblModCom76_01().removeAllRows();
                strCodEmp=objUti.getStringValueAt(arlDatCabCom76_03, 0, objCom76_03.INT_TBL_ARL_DAT_COD_EMP);
                strCodLoc=objUti.getStringValueAt(arlDatCabCom76_03, 0, objCom76_03.INT_TBL_ARL_DAT_COD_LOC);
                strCodTipDoc=objUti.getStringValueAt(arlDatCabCom76_03, 0, objCom76_03.INT_TBL_ARL_DAT_COD_TIP_DOC);
                strCodDoc=objUti.getStringValueAt(arlDatCabCom76_03, 0, objCom76_03.INT_TBL_ARL_DAT_COD_DOC);

                if (!txtCodTipDoc.getText().equals("")){
                    con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (con!=null){
                        stm=con.createStatement();
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_carpag, a1.nd_valcarpag";
                        strSQL+=", a2.tx_nom AS tx_nomCarPag, a2.tx_tipcarpag";
                        strSQL+=" FROM tbm_carPagNotPedImp AS a1 INNER JOIN tbm_carPagImp AS a2";
                        strSQL+=" ON a1.co_carpag=a2.co_carpag";
                        strSQL+=" WHERE a1.co_emp=" + strCodEmp + "";
                        strSQL+=" AND a1.co_loc=" + strCodLoc + "";
                        strSQL+=" AND a1.co_tipDoc IN(" + strCodTipDoc + ")";
                        strSQL+=" AND a1.co_doc IN(" + strCodDoc + ")";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_carpag, a1.nd_valcarpag, a2.tx_nom, a2.tx_tipcarpag";
                        strSQL+=" ORDER BY a1.co_carpag";
                        System.out.println("cargarCarPagImp: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        //Limpiar vector de datos.
                        vecDat.clear();
                        //Obtener los registros.
                        while (rst.next()){
                            vecReg=new Vector();
                            vecReg.add(objCom76_01.INT_TBL_DAT_LIN,"");
                            vecReg.add(objCom76_01.INT_TBL_DAT_TIP_CAR_PAG_IMP,    rst.getString("tx_tipcarpag"));
                            vecReg.add(objCom76_01.INT_TBL_DAT_COD_CAR_PAG_IMP,    rst.getString("co_carpag"));
                            strCodCarPagSav=rst.getString("co_carpag");
                            vecReg.add(objCom76_01.INT_TBL_DAT_NOM_CAR_PAG_IMP,    rst.getString("tx_nomCarPag"));
                            vecReg.add(objCom76_01.INT_TBL_DAT_VAL_CAR_PAG_IMP,    rst.getObject("nd_valcarpag"));
                            vecReg.add(objCom76_01.INT_TBL_DAT_VAL_CAR_PAG_IMP_AUX,rst.getObject("nd_valcarpag"));
                            vecReg.add(objCom76_01.INT_TBL_DAT_TIP_NOT_PED,        "");

                            if(optTmFob.isSelected())
                                vecReg.setElementAt("1", objCom76_01.INT_TBL_DAT_TIP_NOT_PED);
                            else if(optTmCfr.isSelected())
                                vecReg.setElementAt("2", objCom76_01.INT_TBL_DAT_TIP_NOT_PED);
                            else if(optPzaFob.isSelected())
                                vecReg.setElementAt("3", objCom76_01.INT_TBL_DAT_TIP_NOT_PED);
                            else if(optPzaCfr.isSelected())
                                vecReg.setElementAt("4", objCom76_01.INT_TBL_DAT_TIP_NOT_PED);

                            strTipCarPag=rst.getObject("tx_tipcarpag")==null?"":rst.getString("tx_tipcarpag");
                            if(strTipCarPag.equals("F")){
                                //objValFleSav=rst.getString("nd_valcarpag").equals("")?null:rst.getObject("nd_valcarpag");
                                objValFleSav=rst.getObject("nd_valcarpag");
                                if(objValFleSav==null){
                                }
                                else{
                                    bgdValFleSav=bgdValFleSav.add((new BigDecimal(objValFleSav.toString())));
                                }
                            }
                            else if(strTipCarPag.equals("A")){
                                //bgdValAraSav=bgdValAraSav.add(new BigDecimal(rst.getObject("nd_valcarpag")==null?"0":(rst.getString("nd_valcarpag").equals("")?"0":rst.getString("nd_valcarpag"))));
                                //objValAraSav=(rst.getObject("nd_valcarpag").toString().equals("")?null:rst.getObject("nd_valcarpag"));
                                objValAraSav=rst.getObject("nd_valcarpag");
                                if(objValAraSav==null){
                                }
                                else{
                                    bgdValAraSav=bgdValAraSav.add((new BigDecimal(objValAraSav.toString())));
                                }
                            }
                            else if(strTipCarPag.equals("G")){
                                //objValGasSav=rst.getString("nd_valcarpag").equals("")?null:rst.getObject("nd_valcarpag");
                                objValGasSav=rst.getObject("nd_valcarpag");
                                if(objValGasSav==null){
                                }
                                else{
                                    bgdValGasSav=bgdValGasSav.add((new BigDecimal(objValGasSav.toString())));
                                }
                            }

                            vecDat.add(vecReg);
                        }
                        objCom76_01.setFleteItem(bgdValFleSav);
                        objCom76_01.setDerechosArancelarios(bgdValAraSav);
                        objCom76_01.setTotalGastos(bgdValGasSav);
                        rst.close();
                        stm.close();
                        con.close();
                        rst=null;
                        stm=null;
                        con=null;
                        if(objCom76_01.asignarVectorModeloDatos(vecDat)){
                            objCom76_01.calculaTotalFletes();
                            objCom76_01.calculaTotalAranceles();
                            objCom76_01.calculaTotalGastos();
                            vecDat.clear();
                        }
                        else{
                            mostrarMsgInf("Los datos del detalle no se pudieron cargar correctamente");
                        }
                    }
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


    private boolean setDetallePedidoEmbarcado(){
        boolean blnRes=true;
        try{
            for(int k=0; k<arlDatDetCom76_03.size(); k++){
                vecReg=new Vector();
                vecReg.add(objCom76_02.INT_TBL_DAT_LIN,"");
                vecReg.add(objCom76_02.INT_TBL_DAT_COD_ITM_MAE,    objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_COD_ITM_MAE));
                vecReg.add(objCom76_02.INT_TBL_DAT_COD_ITM,        objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_COD_ITM));
                vecReg.add(objCom76_02.INT_TBL_DAT_COD_ALT_ITM,    objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_COD_ALT_ITM));
                vecReg.add(objCom76_02.INT_TBL_DAT_COD_LET_ITM,    objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_COD_LET_ITM));
                vecReg.add(objCom76_02.INT_TBL_DAT_BUT_ITM,        null);
                vecReg.add(objCom76_02.INT_TBL_DAT_NOM_ITM,        objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_NOM_ITM));
                vecReg.add(objCom76_02.INT_TBL_DAT_UNI_MED,        "");
                vecReg.add(objCom76_02.INT_TBL_DAT_PES,            objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_PES));
                vecReg.add(objCom76_02.INT_TBL_DAT_COD_ARA,        objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_COD_ARA));
                vecReg.add(objCom76_02.INT_TBL_DAT_NOM_ARA,        objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_NOM_ARA));
                vecReg.add(objCom76_02.INT_TBL_DAT_DES_ARA,        objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_DES_ARA));
                vecReg.add(objCom76_02.INT_TBL_DAT_POR_ARA,        objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_POR_ARA));
                vecReg.add(objCom76_02.INT_TBL_DAT_CAN_TON_MET,    objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_CAN_TON_MET));
                vecReg.add(objCom76_02.INT_TBL_DAT_CAN_NOT_PED,        objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_CAN_NOT_PED));
                vecReg.add(objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB,    objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_CAN_PED_EMB));                
                vecReg.add(objCom76_02.INT_TBL_DAT_CAN_PEN_NOT_PED,objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_CAN_PEN_NOT_PED));
                vecReg.add(objCom76_02.INT_TBL_DAT_CAN_UTI_PED_EMB,objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_CAN_UTI_PED_EMB));
                vecReg.add(objCom76_02.INT_TBL_DAT_PRE_UNI,        objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_PRE_UNI));
                vecReg.add(objCom76_02.INT_TBL_DAT_TOT_FOB_CFR,    objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_TOT_FOB_CFR));                
                vecReg.add(objCom76_02.INT_TBL_DAT_VAL_FLE,        objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_VAL_FLE));
                vecReg.add(objCom76_02.INT_TBL_DAT_VAL_CFR,        objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_VAL_CFR));
                vecReg.add(objCom76_02.INT_TBL_DAT_VAL_CFR_ARA,    objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_VAL_CFR_ARA));
                vecReg.add(objCom76_02.INT_TBL_DAT_TOT_ARA,        objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_TOT_ARA));
                vecReg.add(objCom76_02.INT_TBL_DAT_TOT_GAS,        objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_TOT_GAS));
                vecReg.add(objCom76_02.INT_TBL_DAT_TOT_COS,        objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_TOT_COS));
                vecReg.add(objCom76_02.INT_TBL_DAT_COS_UNI,        objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_COS_UNI));
                vecReg.add(objCom76_02.INT_TBL_DAT_COD_EMP_REL,    objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_COD_EMP));
                vecReg.add(objCom76_02.INT_TBL_DAT_COD_LOC_REL,    objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_COD_LOC));
                vecReg.add(objCom76_02.INT_TBL_DAT_COD_TIP_DOC_REL,objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_COD_TIP_DOC));
                vecReg.add(objCom76_02.INT_TBL_DAT_COD_DOC_REL,    objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_COD_DOC));
                vecReg.add(objCom76_02.INT_TBL_DAT_COD_REG_REL,    objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_COD_REG));
                vecReg.add(objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB_AUX,objUti.getStringValueAt(arlDatDetCom76_03, k, objCom76_03.INT_TBL_ARL_DET_CAN_PED_EMB_AUX));
                vecDat.add(vecReg);


            }

            if(objCom76_02.asignarVectorModeloDatos(vecDat)){
                vecDat.clear();
            }
            else{
                mostrarMsgInf("Los datos del detalle no se pudieron cargar correctamente");
            }
            objCom76_02.regenerarCalculoCfrAra();

        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;



    }

    /**
     * Función que permite llenar el arreglo con datos consultados(Para cabecera)
     * @return true: Si se pudo efectuar la operación de carga de datos
     * <BR> false: Caso contrario.
     */
    private boolean setearArreglosCabeceraDetalle(){
        boolean blnRes=true;
        String strTipNotPedDb="";
        try{

            if(arlDatCabCom76_03==null){//crea el objeto
                arlDatCabCom76_03=new ArrayList();
            }
            arlDatCabCom76_03.clear();

            if(arlDatDetCom76_03==null){//crea el objeto
                arlDatDetCom76_03=new ArrayList();
            }
            arlDatDetCom76_03.clear();

            if(optTmFob.isSelected())
                strTipNotPedDb="1";
            else if(optTmCfr.isSelected())
                strTipNotPedDb="2";
            else if(optPzaFob.isSelected())
                strTipNotPedDb="3";
            else if(optPzaCfr.isSelected())
                strTipNotPedDb="4";

            for(int i=0; i<objTblModCom76_02.getRowCountTrue(); i++){
                arlRegCabCom76_03=new ArrayList();
                arlRegCabCom76_03.add(objCom76_03.INT_TBL_ARL_DAT_COD_EMP,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_EMP_REL));
                arlRegCabCom76_03.add(objCom76_03.INT_TBL_ARL_DAT_COD_LOC,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_LOC_REL));
                arlRegCabCom76_03.add(objCom76_03.INT_TBL_ARL_DAT_COD_TIP_DOC,      objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_TIP_DOC_REL));
                arlRegCabCom76_03.add(objCom76_03.INT_TBL_ARL_DAT_DES_COR_TIP_DOC,  "");
                arlRegCabCom76_03.add(objCom76_03.INT_TBL_ARL_DAT_DES_LAR_TIP_DOC,  "");
                arlRegCabCom76_03.add(objCom76_03.INT_TBL_ARL_DAT_COD_DOC,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_DOC_REL));
                arlRegCabCom76_03.add(objCom76_03.INT_TBL_ARL_DAT_NUM_DOC,      "");
                arlRegCabCom76_03.add(objCom76_03.INT_TBL_ARL_DAT_NUM_PED,      "");
                arlRegCabCom76_03.add(objCom76_03.INT_TBL_ARL_DAT_FEC_DOC,          "");
                arlRegCabCom76_03.add(objCom76_03.INT_TBL_ARL_DAT_COD_EXP,          "");
                arlRegCabCom76_03.add(objCom76_03.INT_TBL_ARL_DAT_NOM_EXP,          "");
                arlRegCabCom76_03.add(objCom76_03.INT_TBL_ARL_DAT_NOM_ALT_EXP,      "");
                arlRegCabCom76_03.add(objCom76_03.INT_TBL_ARL_DAT_VAL_DOC,          "");
                arlRegCabCom76_03.add(objCom76_03.INT_TBL_ARL_DAT_TIP_NOT_PED,      strTipNotPedDb);
                arlDatCabCom76_03.add(arlRegCabCom76_03);
            }
            objCom76_03.setNotasPedidoSeleccionadas(arlDatCabCom76_03);//envia los datos de la cabecera a Com76_03

            for(int i=0; i<objTblModCom76_02.getRowCountTrue(); i++){
                arlRegDetCom76_03=new ArrayList();
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_COD_ITM_MAE,      objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_ITM_MAE).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_COD_ITM,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_ITM).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_COD_ALT_ITM,      objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_ALT_ITM).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_COD_LET_ITM,      objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_LET_ITM).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_NOM_ITM,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_NOM_ITM).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_CAN_TON_MET,      objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_TON_MET).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_CAN_NOT_PED,      objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_NOT_PED).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_CAN_PED_EMB,      objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_CAN_PEN_NOT_PED,  objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_PEN_NOT_PED).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_CAN_UTI_PED_EMB,  objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_UTI_PED_EMB).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_COD_EMP,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_EMP_REL));
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_COD_LOC,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_LOC_REL));
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_COD_TIP_DOC,      objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_TIP_DOC_REL));
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_COD_DOC,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_DOC_REL));
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_COD_REG,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_REG_REL));
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_PES,              objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_PES).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_COD_ARA,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_ARA).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_NOM_ARA,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_NOM_ARA).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_DES_ARA,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_DES_ARA).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_POR_ARA,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_POR_ARA).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_PRE_UNI,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_PRE_UNI).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_TOT_FOB_CFR,      objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_TOT_FOB_CFR).toString());
                
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_VAL_FLE,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_VAL_FLE).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_VAL_CFR,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_VAL_CFR).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_VAL_CFR_ARA,      objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_VAL_CFR_ARA).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_TOT_ARA,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_TOT_ARA).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_TOT_GAS,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_TOT_GAS).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_TOT_COS,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_TOT_COS).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_COS_UNI,          objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COS_UNI).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_CAN_PED_EMB_AUX,  objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB_AUX).toString());
                arlRegDetCom76_03.add(objCom76_03.INT_TBL_ARL_DET_EST_REG,          "");

                arlDatDetCom76_03.add(arlRegDetCom76_03);
            }
            objCom76_03.setDetalleSeleccionado(arlDatDetCom76_03);//devuelve los datos del detalle a Com76_03
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        strAux="";
        String strSQLRep="", strSQLSubRepAra="", strSQLSubRepItm="", strSQLSubRepCarPag="";
        Connection conRpt;
        Statement stmRpt;
        ResultSet rstRpt;
        String strNomPaiEmbPto="";
        String strValDocPal="0";
        String strRutImg="";
        String strValFle="";
        try
        {
            conRpt =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
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

                            stmRpt=conRpt.createStatement();
                            strSQL="";
                            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.fe_emb, a1.ne_numdoc, a1.tx_numDoc2, a1.ne_tipnotped";
                            strSQL+="      , a1.co_imp, a1.co_exp, a1.co_ciupueemb, a1.co_seg, a1.co_forpag";
                            strSQL+="      , a1.tx_tipemp, a1.nd_valdoc, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1";
                            strSQL+="      , a1.tx_obs2, a1.st_reg, a1.tx_tipemp, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc";
                            strSQL+="      , a3.co_emp AS co_imp, a3.tx_nom AS tx_nomImp, a3.tx_ruc AS tx_rucEmp";
                            //strSQL+=" 	, a3.tx_dir AS tx_dirEmp, a3.tx_tel AS tx_telEmp, a3.tx_fax AS tx_faxEmp";
                            
                            if( (Integer.parseInt(objRptSis.getCodigoReporte(i))==455) || (Integer.parseInt(objRptSis.getCodigoReporte(i))==456) ){
                                strSQL+=" 	, (SELECT d1.tx_dir FROM tbm_emp AS d1 WHERE d1.co_emp=1) AS tx_dirEmp";//siempre se muestra la informacion de TUVAL
                                strSQL+=" 	, (SELECT d1.tx_tel FROM tbm_emp AS d1 WHERE d1.co_emp=1) AS tx_telEmp";//siempre se muestra la informacion de TUVAL
                                strSQL+=" 	, (SELECT d1.tx_fax FROM tbm_emp AS d1 WHERE d1.co_emp=1) AS tx_faxEmp";//siempre se muestra la informacion de TUVAL
                            }
                            else
                                strSQL+=" 	, a3.tx_dir AS tx_dirEmp, a3.tx_tel AS tx_telEmp, a3.tx_fax AS tx_faxEmp";
                            

                            
                            strSQL+=" 	, CAST('ECUADOR' AS CHARACTER VARYING) AS tx_paiEmp";
                            strSQL+="   , a5.co_exp";
                            if(Integer.parseInt(objRptSis.getCodigoReporte(i))==421)//seguro
                                strSQL+=" , a5.tx_nom2 AS tx_nomExp";
                            else
                                strSQL+=" , a5.tx_nom AS tx_nomExp";
                            
                            strSQL+=" , a5.tx_dir AS tx_dirExp, a5.tx_tel1 AS tx_telExp1";
                            strSQL+=" , a5.tx_tel2 AS tx_telExp2, a5.tx_fax AS tx_faxExp, a5.tx_perCon AS tx_perConExp";
                            strSQL+=" , a7.co_seg, a7.tx_deslar AS tx_nomSeg";
                            strSQL+=" , a8.co_forpag, a8.tx_deslar AS tx_nomForPag";
                            strSQL+=" , a11.co_pai, a11.tx_desCor AS tx_desCorPaiExp, a11.tx_desLar AS tx_desLarPaiExp";
                            strSQL+=" , a13.co_ciu, a13.tx_desCor AS tx_desCorPueEmb, a13.tx_deslar AS tx_desLarPueEmb";
                            strSQL+=" , a14.co_seg, a14.tx_deslar AS tx_nomSeg";
                            strSQL+=" , a15.co_forpag, a15.tx_deslar AS tx_nomForPag";
                            strSQL+=" , a16.co_pai, a16.tx_desCor AS tx_desCorPaiPto, a16.tx_desLar AS tx_desLarPaiPto";
                            strSQL+=" , a1.tx_numdoc3, a1.tx_direnv, a1.ne_forenv";
                            strSQL+=" , CASE WHEN a1.ne_forenv=1 THEN 'LAND' WHEN a1.ne_forenv=2 THEN 'SEA' WHEN a1.ne_forenv=3 THEN 'AIR' END AS tx_forEnv";
                            strSQL+=" , a1.ne_diacre, (a1.fe_emb + a1.ne_diacre) AS fe_pag";
                            strSQL+=" FROM tbm_cabPedEmbImp AS a1";
                            strSQL+=" LEFT OUTER JOIN tbm_ciu AS a13 ON(a1.co_ciupueemb=a13.co_ciu)";
                            strSQL+=" LEFT OUTER JOIN tbm_pai AS a16 ON(a13.co_pai=a16.co_pai)";
                            strSQL+=" LEFT OUTER JOIN tbm_segImp AS a14 ON(a1.co_seg=a14.co_seg)";
                            strSQL+=" LEFT OUTER JOIN tbm_forpagimp AS a15 ON(a1.co_forpag=a15.co_forpag)";
                            strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                            strSQL+=" LEFT OUTER JOIN tbm_emp AS a3 ON (a1.co_imp=a3.co_emp)";
                            strSQL+=" LEFT OUTER JOIN tbm_expImp AS a5 ON(a1.co_exp=a5.co_exp)";
                            strSQL+=" LEFT OUTER JOIN tbm_ciu AS a10 ON (a5.co_ciu=a10.co_ciu)";
                            strSQL+=" LEFT OUTER JOIN tbm_pai AS a11 ON(a10.co_pai=a11.co_pai)";
                            strSQL+=" LEFT OUTER JOIN tbm_segimp AS a7 ON(a1.co_seg=a7.co_seg)";
                            strSQL+=" LEFT OUTER JOIN tbm_forpagimp AS a8 ON(a1.co_forpag=a8.co_forpag)";
                            strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                            strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                            strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);
                            strSQL+=" AND a1.co_doc=" + + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                            strSQLRep=strSQL;
                            rstRpt=stmRpt.executeQuery(strSQLRep);
                            if(rstRpt.next()){
                                strNomPaiEmbPto=rstRpt.getObject("tx_desLarPaiPto")==null?"":rstRpt.getString("tx_desLarPaiPto");
                            }
                            rstRpt.close();
                            rstRpt=null;
                            stmRpt.close();
                            stmRpt=null;                            

                            strSQL ="";
                            strSQL+=" SELECT b1.co_parara, b1.tx_par, CAST ((b1.tx_des || b1.tx_tna) AS CHARACTER VARYING) AS tx_des FROM(";
                            strSQL+=" 	SELECT CASE WHEN a5.co_parara IS NULL THEN 0 ELSE a5.co_parara END AS co_parara";
                            strSQL+=" 	     , CASE WHEN a5.tx_par IS NULL THEN '' ELSE a5.tx_par END AS tx_par";
                            strSQL+=" 	     , CASE WHEN a5.tx_des IS NULL THEN '' ELSE a5.tx_des END AS tx_des";
                            strSQL+=" 	     , CASE WHEN a5.tx_destna IS NULL THEN '' ELSE '  TNAN:'||a6.nd_ara END AS tx_tna";
                            strSQL+=" 	 FROM (tbm_detpedEmbimp AS b1 INNER JOIN tbm_cabPedEmbImp AS c1";
                            strSQL+=" 		ON b1.co_emp=c1.co_emp AND b1.co_loc=c1.co_loc AND b1.co_tipDoc=c1.co_tipDoc AND b1.co_doc=c1.co_doc)";
                            strSQL+=" 	 INNER JOIN";
                            strSQL+=" 	 (";
                            strSQL+=" 	 ((tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
                            strSQL+=" 	 LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
                            strSQL+=" 	 )";
                            strSQL+=" 	 LEFT OUTER JOIN(tbm_grpInvImp AS a4 INNER JOIN tbm_parAraImp AS a5 ON a4.co_parAra=a5.co_parAra";
                            strSQL+=" 		INNER JOIN tbm_vigParAraImp AS a6 ON a5.co_parAra=a6.co_parAra";
                            strSQL+=" 			)";
                            strSQL+=" 	  ON a1.co_grpImp=a4.co_grp";
                            strSQL+=" 	 )";
                            strSQL+=" 	 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                            strSQL+="    WHERE b1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                            strSQL+="    AND b1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                            strSQL+="    AND b1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);
                            strSQL+="    AND b1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                            strSQL+="    AND c1.st_reg='A'";
                            strSQL+="    AND c1.fe_doc BETWEEN a6.fe_vigDes";
                            strSQL+="    AND (CASE WHEN a6.fe_vigHas IS NULL THEN CURRENT_DATE ELSE a6.fe_vigHas END)";
                            strSQL+="    GROUP BY a5.co_parara, a5.tx_par, a5.tx_des,a5.tx_destna, a6.nd_ara";
                            strSQL+="    ORDER BY a5.co_parara";
                            strSQL+=" ) AS b1";                            
                            strSQLSubRepAra=strSQL;

                            strSQL ="";
                            strSQL+=" SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.nd_pesitmkgr";
                            strSQL+="      , CASE WHEN a5.co_parara IS NULL THEN 0 ELSE a5.co_parara END AS co_parara";
                            strSQL+="      , CASE WHEN a5.tx_par IS NULL THEN '' ELSE a5.tx_par END AS tx_par";
                            strSQL+="      , CASE WHEN a5.tx_des IS NULL THEN '' ELSE a5.tx_des END AS tx_des";
                            strSQL+="      , CASE WHEN b1.nd_ara IS NULL THEN 0 ELSE b1.nd_ara END AS nd_ara";
                            if( (optTmFob.isSelected()) || optTmCfr.isSelected() )
                                strSQL+=" , ROUND(b1.nd_can,4) AS nd_can";
                            else
                                strSQL+=" , ROUND(b1.nd_can,0) AS nd_can";
                            strSQL+="     , b1.nd_preUni, b1.nd_valTotFobCfr";
                            if( (optTmFob.isSelected())  ||  (optTmCfr.isSelected()))
                                strSQL+=" , b1.nd_canTonMet";
                            else
                                strSQL+=" , b1.nd_can AS nd_canTonMet";
                            strSQL+="     , b1.nd_valFle, b1.nd_valCfr, b1.nd_valTotAra";
                            strSQL+="     , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg";
                            strSQL+=" FROM tbm_detPedEmbImp AS b1 INNER JOIN";
                            strSQL+=" (";
                            strSQL+=" ((tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
                            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
                            strSQL+=" )";
                            strSQL+=" LEFT OUTER JOIN(tbm_grpInvImp AS a4 INNER JOIN tbm_parAraImp AS a5 ON a4.co_parAra=a5.co_parAra";
                            strSQL+=" 	INNER JOIN tbm_vigParAraImp AS a6 ON a5.co_parAra=a6.co_parAra";
                            strSQL+=" 		)";
                            strSQL+="  ON a1.co_grpImp=a4.co_grp";
                            strSQL+=" )";
                            strSQL+=" ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                            strSQL+=" WHERE b1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                            strSQL+=" AND b1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                            strSQL+=" AND b1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);
                            strSQL+=" AND b1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                            
                            if(  (Integer.parseInt(objRptSis.getCodigoReporte(i))!=455) && (Integer.parseInt(objRptSis.getCodigoReporte(i))!=456)  )//packing list
                                strSQL+=" AND (CASE WHEN a5.co_parara IS NULL THEN 0 ELSE a5.co_parara END)=$P!{co_parAraItm}";                            
                            
                            strSQL+=" GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.nd_pesitmkgr";
                            strSQL+="        , a5.co_parara, a5.tx_par, a5.tx_des, b1.nd_ara";
                            strSQL+="        , b1.nd_can, b1.nd_preUni, b1.nd_valTotFobCfr, b1.nd_canTonMet, b1.nd_valFle, b1.nd_valCfr, b1.nd_valTotAra";
                            strSQL+="        , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg";
                            strSQL+=" ORDER BY b1.co_reg, a1.tx_codAlt";
                            strSQLSubRepItm=strSQL;

                            strSQL="";
                            strSQL+=" SELECT a2.co_carPag, a2.tx_nom AS tx_nomCarPag, a2.tx_tipCarPag, a1.nd_valCarPag, a2.ne_ubi";
                            strSQL+="      , CASE WHEN a2.tx_tipCarPag='O' THEN a1.nd_valCarPag ELSE 0 END AS nd_valFleAprCFR";
                            strSQL+="      , CASE WHEN a2.tx_tipCarPag='F' THEN a1.nd_valCarPag ELSE 0 END AS nd_valFleFOB";
                            strSQL+=" FROM tbm_carPagPedEmbImp AS a1 INNER JOIN tbm_carPagImp AS a2";
                            strSQL+=" ON a1.co_carPag=a2.co_carPag";
                            strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                            strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                            strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);
                            strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                            strSQL+=" AND a2.st_reg IN('A')";
                            strSQL+=" GROUP BY a2.co_carPag, a2.tx_nom, a2.tx_tipCarPag, a1.nd_valCarPag, a2.ne_ubi";
                            strSQL+=" ORDER BY a2.ne_ubi";
                            strSQLSubRepCarPag=strSQL;
                            
                            stmRpt=conRpt.createStatement();
                            strSQL="";
                            strSQL+=" SELECT a2.co_carPag, a2.tx_nom AS tx_nomCarPag, a2.tx_tipCarPag, a1.nd_valCarPag, a2.ne_ubi";
                            strSQL+="      , CASE WHEN a2.tx_tipCarPag='O' THEN a1.nd_valCarPag ELSE 0 END AS nd_valFleAprCFR";
                            strSQL+="      , CASE WHEN a2.tx_tipCarPag='F' THEN a1.nd_valCarPag ELSE 0 END AS nd_valFleFOB";
                            strSQL+=" FROM tbm_carPagPedEmbImp AS a1 INNER JOIN tbm_carPagImp AS a2";
                            strSQL+=" ON a1.co_carPag=a2.co_carPag";
                            strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                            strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                            strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);
                            strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                            strSQL+=" AND a2.st_reg IN('A')  "; 
//                            if( (optTmFob.isSelected()) || optPzaFob.isSelected() )           //Se comento porque Michelle solicita siempre se imprima el Flete Aproximado y ya no Flete dependiendo del tipo FOB ó CFR
//                                strSQL+=" AND a2.tx_tipCarPag IN('F')";                       //Se comento porque Michelle solicita siempre se imprima el Flete Aproximado y ya no Flete dependiendo del tipo FOB ó CFR
//                            else if ( (optTmCfr.isSelected()) || optPzaCfr.isSelected() )     //Se comento porque Michelle solicita siempre se imprima el Flete Aproximado y ya no Flete dependiendo del tipo FOB ó CFR
                                strSQL+=" AND a2.tx_tipCarPag IN('O')";
                            strSQL+=" GROUP BY a2.co_carPag, a2.tx_nom, a2.tx_tipCarPag, a1.nd_valCarPag, a2.ne_ubi";
                            strSQL+=" ORDER BY a2.ne_ubi";
                            rstRpt=stmRpt.executeQuery(strSQL);
                            strValFle="0";
                            if(rstRpt.next()){
                                
                                //Michelle Sánchez solicita que el valor de la impresión se tome del campo de "Flete Aproximado", indiferente que sea FOB o CFR(Esquema anterior si validaba) 
                                
//                                if( (optTmFob.isSelected()) || optPzaFob.isSelected() ){
//                                    if(rstRpt.getString("tx_tipCarPag").equals("F"))
//                                        strValFle=rstRpt.getObject("nd_valFleFOB")==null?"0":rstRpt.getString("nd_valFleFOB").equals("")?"0":rstRpt.getString("nd_valFleFOB");
//                                }
//                                else if( (optTmCfr.isSelected()) || optPzaCfr.isSelected() ){
//                                    if(rstRpt.getString("tx_tipCarPag").equals("O"))
                                        strValFle=rstRpt.getObject("nd_valFleAprCFR")==null?"0":rstRpt.getString("nd_valFleAprCFR").equals("")?"0":rstRpt.getString("nd_valFleAprCFR");
//                                }
                            }                            
                            
                            rstRpt.close();
                            rstRpt=null;
                            stmRpt.close();
                            stmRpt=null;

                            //Cantidad en palabras.
                            try{
                                Librerias.ZafUtil.ZafNumLetra numero;
                                //double cantidad= objUti.redondear(txtValDoc.getText(), objParSis.getDecimalesMostrar());
                                double cantidad= objUti.redondear(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_TOT_FOB_CFR).toString()), objParSis.getDecimalesMostrar());
                                String decimales=String.valueOf(cantidad).toString();
                                decimales=decimales.substring(decimales.indexOf('.') + 1);
                                decimales=(decimales.length()<2?decimales + "0":decimales.substring(0,2));
                                int deci= Integer.parseInt(decimales);
                                int m_pesos=(int)cantidad;
                                numero = new Librerias.ZafUtil.ZafNumLetra(m_pesos);
                                strValDocPal = numero.convertirLetras(m_pesos);
                                strValDocPal = strValDocPal + " "+decimales+"/100  DOLARES";
                                strValDocPal=strValDocPal.toUpperCase();
                                numero=null;
                            }
                            catch(Exception e){
                                objUti.mostrarMsgErr_F1(this, e);
                            }

                            strRutImg=objRptSis.getRutaReporte(i);
                            
                            java.util.Map mapPar = new java.util.HashMap();
                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i))){
                                case 422://nota de pedido incompleta(se envia al seguro y a ....)
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    mapPar.put("strCamAudRpt", "" + (objUti.getStringValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_TXT_USRING) + " / " + objUti.getStringValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_TXT_USRMOD) + " / " + objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                    mapPar.put("codEmp", objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP) );
                                    mapPar.put("codLoc", objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC) );
                                    mapPar.put("codTipDoc", objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC) );
                                    mapPar.put("codDoc", objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC) );
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRepAra", strSQLSubRepAra);
                                    mapPar.put("strSQLSubRepItm", strSQLSubRepItm);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("imgCodImp", txtCodImp.getText());
                                    mapPar.put("NomPaiEmbPto", strNomPaiEmbPto);
                                    //el valor me sale mal en el reporte asi q mejor lo envio por parametro
                                    
                                    if( (optTmFob.isSelected()) || (optTmCfr.isSelected()) ){
                                        mapPar.put("totMts", new BigDecimal(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_TON_MET).toString())));//SUMA DE LA CANTIDAD
                                    }
                                    else{
                                        mapPar.put("totMts", new BigDecimal(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB)==null?"0":(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB).toString().equals("")?"0":objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB).toString())));//SUMA DE LA CANTIDAD
                                    }
                                    
                                    mapPar.put("fleApr", new BigDecimal("0"));
                                    mapPar.put("cfrApr", new BigDecimal(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_VAL_CFR)==null?"0":(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_VAL_CFR).toString())));//SUMA DE LA CANTIDAD
                                    mapPar.put("fobApr", new BigDecimal("0"));
                                    mapPar.put("valTotPal", strValDocPal);
                                    //para colocar en la columna de cantidad
                                    if( (optTmFob.isSelected()) || optTmCfr.isSelected() )
                                        mapPar.put("medCan", "TM");
                                    else if( (optPzaFob.isSelected()) || optPzaCfr.isSelected() )
                                        mapPar.put("medCan", "PCS");
                                    else
                                        mapPar.put("medCan", "");
                                    //para colocar en el pie de pagina
                                    if( (optTmFob.isSelected()) || optPzaFob.isSelected() )
                                        mapPar.put("cfrFob", "fob");
                                    else if( (optTmCfr.isSelected()) || optPzaCfr.isSelected() )
                                        mapPar.put("cfrFob", "cfr");
                                    else
                                        mapPar.put("cfrFob", "");

                                    mapPar.put("imgRut", strRutImg);

                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);

                                    break;
                                case 421://nota de pedido incompleta(se envia al seguro y a ....)
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    mapPar.put("strCamAudRpt", "" + (objUti.getStringValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_TXT_USRING) + " / " + objUti.getStringValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_TXT_USRMOD) + " / " + objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                    mapPar.put("codEmp", objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP) );
                                    mapPar.put("codLoc", objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC) );
                                    mapPar.put("codTipDoc", objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC) );
                                    mapPar.put("codDoc", objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC) );
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRepAra", strSQLSubRepAra);
                                    mapPar.put("strSQLSubRepItm", strSQLSubRepItm);
                                    mapPar.put("strSQLSubRepCarPag", strSQLSubRepCarPag);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("imgCodImp", txtCodImp.getText());
                                    mapPar.put("NomPaiEmbPto", strNomPaiEmbPto);
                                    //el valor me sale mal en el reporte asi q mejor lo envio por parametro
                                    mapPar.put("totMts", new BigDecimal(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB)==null?"0":(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB).toString().equals("")?"0":objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB).toString())));//SUMA DE LA CANTIDAD
                                    mapPar.put("fleApr", new BigDecimal("0"));
                                    mapPar.put("cfrApr", new BigDecimal(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_VAL_CFR)==null?"0":(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_VAL_CFR).toString())));//SUMA DE LA CANTIDAD
                                    mapPar.put("fobApr", new BigDecimal("0"));
                                    mapPar.put("valTotPal", strValDocPal);
                                    //para colocar en la columna de cantidad
                                    if( (optTmFob.isSelected()) || optTmCfr.isSelected() )
                                        mapPar.put("medCan", "TM");
                                    else if( (optPzaFob.isSelected()) || optPzaCfr.isSelected() )
                                        mapPar.put("medCan", "PCS");
                                    else
                                        mapPar.put("medCan", "");
                                    //para colocar en el pie de pagina
                                    if( (optTmFob.isSelected()) || optPzaFob.isSelected() )
                                        mapPar.put("cfrFob", "fob");
                                    else if( (optTmCfr.isSelected()) || optPzaCfr.isSelected() )
                                        mapPar.put("cfrFob", "cfr");
                                    else
                                        mapPar.put("cfrFob", "");

                                    mapPar.put("imgRut", strRutImg);

                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);

                                    break;

                                case 455://packing list
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRepItm", strSQLSubRepItm);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("imgCodImp", txtCodImp.getText());
                                     //el valor me sale mal en el reporte asi q mejor lo envio por parametro
                                    if( (optTmFob.isSelected()) || (optTmCfr.isSelected()) ){
                                        mapPar.put("totMts", new BigDecimal(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_TON_MET).toString())));//SUMA DE LA CANTIDAD
                                    }
                                    else{
                                        mapPar.put("totMts", new BigDecimal(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB)==null?"0":(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB).toString().equals("")?"0":objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB).toString())));//SUMA DE LA CANTIDAD
                                    }                                    
                                    //para colocar en la columna de cantidad
                                    if( (optTmFob.isSelected()) || optTmCfr.isSelected() )
                                        mapPar.put("medCan", "TM");
                                    else if( (optPzaFob.isSelected()) || optPzaCfr.isSelected() )
                                        mapPar.put("medCan", "PCS");
                                    else
                                        mapPar.put("medCan", "");
                                    mapPar.put("imgRut", strRutImg);
                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                    break;
                                    
                                case 456://factura
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRepItm", strSQLSubRepItm);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("imgCodImp", txtCodImp.getText());
                                     //el valor me sale mal en el reporte asi q mejor lo envio por parametro
                                    if( (optTmFob.isSelected()) || (optTmCfr.isSelected()) ){
                                        mapPar.put("totMts", new BigDecimal(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_TON_MET).toString())));//SUMA DE LA CANTIDAD
                                    }
                                    else{
                                        mapPar.put("totMts", new BigDecimal(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB)==null?"0":(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB).toString().equals("")?"0":objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_CAN_PZA_PED_EMB).toString())));//SUMA DE LA CANTIDAD
                                    }                                    
                                    //para colocar en la columna de cantidad
                                    if( (optTmFob.isSelected()) || optTmCfr.isSelected() )
                                        mapPar.put("medCan", "TM");
                                    else if( (optPzaFob.isSelected()) || optPzaCfr.isSelected() )
                                        mapPar.put("medCan", "PCS");
                                    else
                                        mapPar.put("medCan", "");
                                    mapPar.put("imgRut", strRutImg);                                    
                                    mapPar.put("valFle", new BigDecimal(strValFle));
                                    
                                    if( (optTmFob.isSelected()) || optPzaFob.isSelected() )
                                        mapPar.put("isFobCfr", "FOB");
                                    else if( (optTmCfr.isSelected()) || optPzaCfr.isSelected() )
                                        mapPar.put("isFobCfr", "CFR");
                                    
                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                    break;                                    
                                    
                                default:
                                    break;

                            }
                        }
                    }
                }
                conRpt.close();
                conRpt=null;
            }

        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    private boolean isIngresoImportacion(){
        boolean blnRes=false;
        Connection conIngImp;
        Statement stmIngImp;
        ResultSet rstIngImp;
        try{
            if(arlDatCabCom76_03.size()>0){
                conIngImp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conIngImp!=null){
                    stmIngImp=conIngImp.createStatement();
                    strSQL ="";
                    strSQL+=" SELECT *FROM tbm_cabMovInv AS a1";
                    strSQL+=" WHERE a1.co_emprelpedembimp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND a1.co_locrelpedembimp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                    strSQL+=" AND a1.co_tipdocrelpedembimp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);
                    strSQL+=" AND a1.co_docrelpedembimp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                    strSQL+=" AND a1.st_reg NOT IN('I', 'E')";
                    rstIngImp=stmIngImp.executeQuery(strSQL);
                    if(rstIngImp.next())
                        blnRes=true;

                    conIngImp.close();
                    conIngImp=null;
                    stmIngImp.close();
                    stmIngImp=null;
                    rstIngImp.close();
                    rstIngImp=null;
                }
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

    
    private boolean isFacturaImportacion(){
        boolean blnRes=false;
        Connection conIngImp;
        Statement stmIngImp;
        ResultSet rstIngImp;
        try{
            if(arlDatCabCom76_03.size()>0){
                conIngImp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conIngImp!=null){
                    stmIngImp=conIngImp.createStatement();
                    strSQL="";
                    strSQL+="SELECT a3.co_empRelPedEmbImp AS co_empPedEmb, a3.co_locRelPedEmbImp AS co_locPedEmb, a3.co_tipDocRelPedEmbImp AS co_tipPedEmb";
                    strSQL+=", a3.co_docRelPedEmbImp AS co_docPedEmb";
                    strSQL+=", a3.co_empRelDia AS co_empFacImp, a3.co_locRelDia AS co_locFacImp, a3.co_tipDocRelDia AS co_tipFacImp, a3.co_diaRelDia AS co_diaFacImp";
                    strSQL+=" FROM tbm_cabDia AS a1";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON(a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" INNER JOIN tbm_datGenImp AS a3 ";
                    strSQL+=" ON(a1.co_emp=a3.co_empRelDia AND a1.co_loc=a3.co_locrelDia AND a1.co_tipDoc=a3.co_tipdocrelDia AND a1.co_dia=a3.co_diarelDia)";
                    strSQL+=" WHERE a3.co_empRelPedEmbImp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND a3.co_locRelPedEmbImp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                    strSQL+=" AND a3.co_tipDocRelPedEmbImp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);
                    strSQL+=" AND a3.co_docRelPedEmbImp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_tipDoc, a1.co_dia";                    
                    rstIngImp=stmIngImp.executeQuery(strSQL);
                    if(rstIngImp.next())
                        blnRes=true;

                    conIngImp.close();
                    conIngImp=null;
                    stmIngImp.close();
                    stmIngImp=null;
                    rstIngImp.close();
                    rstIngImp=null;
                }
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
    
    private String getQueryUpdate_tbmCabNotPedImp(){
        boolean blnRes=true;
        strSQLUpdNotPedCie="";
        String strCodDocSelAct="", strCodDocSelAnt="";
        try{
            for(int i=(objTblModCom76_02.getRowCountTrue()-1); i>=0; i--){
                strCodDocSelAct=objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_DOC_REL).toString();
                if(i==(objTblModCom76_02.getRowCountTrue()-1)){
                    strSQL="";
                    strSQL+="UPDATE tbm_cabNotPedImp";
                    strSQL+=" SET st_cie='S'";
                    strSQL+=" WHERE co_emp=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_EMP_REL) + "";
                    strSQL+=" AND co_loc=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_LOC_REL) + "";
                    strSQL+=" AND co_tipDoc=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_TIP_DOC_REL) + "";
                    strSQL+=" AND co_doc IN(" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_DOC_REL) + ")";
                    strSQL+=";";
                    strSQLUpdNotPedCie+=strSQL;
                }
                else{
                    if(i==0)
                        strCodDocSelAnt=objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_DOC_REL).toString();
                    else
                        strCodDocSelAnt=objTblModCom76_02.getValueAt((i-1), objCom76_02.INT_TBL_DAT_COD_DOC_REL).toString();

                    if(!strCodDocSelAct.equals(strCodDocSelAnt)){
                        strSQL="";
                        strSQL+="UPDATE tbm_cabNotPedImp";
                        strSQL+=" SET st_cie='S'";
                        strSQL+=" WHERE co_emp=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_EMP_REL) + "";
                        strSQL+=" AND co_loc=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_LOC_REL) + "";
                        strSQL+=" AND co_tipDoc=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_TIP_DOC_REL) + "";
                        strSQL+=" AND co_doc IN(" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_DOC_REL) + ")";
                        strSQL+=";";
                        strSQLUpdNotPedCie+=strSQL;
                    }  
                }
                
              
            }
        }
        catch(Exception e){
            blnRes=false;
        }
        return strSQLUpdNotPedCie;
    }
    
    private boolean isChangeFecha(){
        boolean blnRes=false;
        try{
            datFecEmbFin=objUti.parseDate(dtpFecEmb.getText(), "dd/MM/yyyy");
            datFecArrFin=objUti.parseDate(dtpFecArr.getText(), "dd/MM/yyyy");
            
            if( (datFecEmbIni.compareTo(datFecEmbFin)!=0)  ||  (datFecArrIni.compareTo(datFecArrFin)!=0)  ){
                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean enviarCorEleDptoVta(){
        boolean blnRes=false;
        int intCodCfgCorEle=15;
        try{
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
            
            String strSbjMsg="Sistema Zafiro:   ";
            String strMsg="";
            strMsg+="<html>";
            strMsg+="<body>";
            strMsg+="<h3>Se ha modificado el " + objParSis.getNombreMenu() + "<hr></h3>";
            strMsg+="<table id='objTable' bgcolor='#ffffcc' width='120%' border='1'>";
            strMsg+="<tr bgcolor='#E3CEF6'>";
            strMsg+="	<td rowspan='2'>Datos</td>";
            strMsg+="	<td colspan='2'>" + objParSis.getNombreMenu() + " # " + txtNumPed.getText() + "</td>";
            strMsg+="</tr>";
            strMsg+="<tr bgcolor='#E3CEF6'>";
            strMsg+="	<td>Anterior</td>";
            strMsg+="	<td>Actual</td>";
            strMsg+="</tr>";
            strMsg+="<tr bgcolor='#E3CEF6'>";
            strMsg+="	<td>Fecha de embarque:</td>";
            strMsg+="	<td>" + objUti.formatearFecha(datFecEmbIni, "dd/MM/yyyy") + "</td>";
            strMsg+="	<td>" + dtpFecEmb.getText() + "</td>";
            strMsg+="</tr>";
            strMsg+="<tr bgcolor='#E3CEF6'>";
            strMsg+="	<td>Fecha de arribo:</td>";
            strMsg+="	<td>" + objUti.formatearFecha(datFecArrIni, "dd/MM/yyyy") + "</td>";
            strMsg+="	<td>" + dtpFecArr.getText() + "</td>";
            strMsg+="</tr>";
            strMsg+="<tr bgcolor='#BCA9F5'>";
            strMsg+="	<td>Modificado:</td>";
            strMsg+="	<td>" + objParSis.getNombreUsuario() + "</td>";
            strMsg+="	<td>" + strAux + "</td>";
            strMsg+="</tr>";
            strMsg+="</table>";
            strMsg+="</body>";
            strMsg+="</html> ";

            //Envía el correo.
            if(strMsg.length()>0){
                if(objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodCfgCorEle, strMsg, strSbjMsg )){ 
                    //mostrarMsgInf("<HTML>Correo de notificación de Cambio de fecha.</HTML>");
                    blnRes=true;
                }
            }         
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Función que permite conocer si el Pedido que se está embarcando ya tiene cuentas de activo y pasivo asociadas
     * @return true: Si existen cuentas asociadas.
     * <BR>false: En el caso contrario.
     */    
    private boolean isCuentaNotaPedido(){
        boolean blnRes=false;
        String strCodDocSelAct="", strCodDocSelAnt="";
        try{
            if(con!=null){
                stm=con.createStatement();
                
                for(int i=(objTblModCom76_02.getRowCountTrue()-1); i>=0; i--){
                    strCodDocSelAct=objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_DOC_REL).toString();
                    if(i==(objTblModCom76_02.getRowCountTrue()-1)){//un solo item
                        strSQL="";
                        strSQL+=" SELECT a1.co_imp, a1.co_ctaAct, a1.co_ctaPas FROM tbm_cabNotPedImp AS a1";
                        strSQL+=" WHERE co_emp=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_EMP_REL) + "";
                        strSQL+=" AND co_loc=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_LOC_REL) + "";
                        strSQL+=" AND co_tipDoc=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_TIP_DOC_REL) + "";
                        strSQL+=" AND co_doc IN(" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_DOC_REL) + ")";
                        strSQL+=" AND a1.co_ctaAct IS NOT NULL AND a1.co_ctaPas IS NOT NULL";
                        strSQL+=";";
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            intCodCtaActCreNotPed=rst.getInt("co_ctaAct");
                            intCodCtaPasCreNotPed=rst.getInt("co_ctaPas");
                            blnRes=true;
                            break;
                        }
                    }
                    else{
                        if(i==0)
                            strCodDocSelAnt=objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_DOC_REL).toString();
                        else
                            strCodDocSelAnt=objTblModCom76_02.getValueAt((i-1), objCom76_02.INT_TBL_DAT_COD_DOC_REL).toString();

                        if(!strCodDocSelAct.equals(strCodDocSelAnt)){
                            strSQL="";
                            strSQL+=" SELECT a1.co_imp, a1.co_ctaAct, a1.co_ctaPas FROM tbm_cabNotPedImp AS a1";
                            strSQL+=" WHERE co_emp=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_EMP_REL) + "";
                            strSQL+=" AND co_loc=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_LOC_REL) + "";
                            strSQL+=" AND co_tipDoc=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_TIP_DOC_REL) + "";
                            strSQL+=" AND co_doc IN(" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_DOC_REL) + ")";
                            strSQL+=" AND a1.co_ctaAct IS NOT NULL AND a1.co_ctaPas IS NOT NULL";
                            strSQL+=";";
                            rst=stm.executeQuery(strSQL);
                            if(rst.next()){
                                intCodCtaActCreNotPed=rst.getInt("co_ctaAct");
                                intCodCtaPasCreNotPed=rst.getInt("co_ctaPas");
                                blnRes=true;
                                break;
                            }
                        }  
                    }
                }
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
    
    
    
    /**
     * Función que permite conocer si el Importador de la Nota de Pedido y Pedido Embarcado es el mismo
     * @return true: Si el importador es el mismo en ambas instancias
     * <BR>false: En el caso contrario.
     */    
    private boolean isEqualImportador(){
        boolean blnRes=true;
//        String strCodDocSelAct="", strCodDocSelAnt="";
//        try{
//            if(con!=null){
//                stm=con.createStatement();
//                
//                for(int i=(objTblModCom76_02.getRowCountTrue()-1); i>=0; i--){
//                    strCodDocSelAct=objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_DOC_REL).toString();
//                    if(i==(objTblModCom76_02.getRowCountTrue()-1)){//un solo item
//                        strSQL="";
//                        strSQL+=" SELECT a1.co_imp, a1.co_ctaAct, a1.co_ctaPas FROM tbm_cabNotPedImp AS a1";
//                        strSQL+=" WHERE co_emp=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_EMP_REL) + "";
//                        strSQL+=" AND co_loc=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_LOC_REL) + "";
//                        strSQL+=" AND co_tipDoc=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_TIP_DOC_REL) + "";
//                        strSQL+=" AND co_doc IN(" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_DOC_REL) + ")";
//                        strSQL+=" AND co_imp NOT IN(" + txtCodImp.getText() + ")";
//                        strSQL+=" AND a1.co_ctaAct IS NOT NULL AND a1.co_ctaPas IS NOT NULL";
//                        strSQL+=";";
//                        System.out.println("Fallo por cuenta ya creada: " + strSQL);
//                        rst=stm.executeQuery(strSQL);
//                        if(rst.next()){
//                            System.out.println("ENTRO!!!!");
//                            blnRes=false;
//                            break;
//                        }
//                    }
//                    else{
//                        if(i==0)
//                            strCodDocSelAnt=objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_DOC_REL).toString();
//                        else
//                            strCodDocSelAnt=objTblModCom76_02.getValueAt((i-1), objCom76_02.INT_TBL_DAT_COD_DOC_REL).toString();
//
//                        if(!strCodDocSelAct.equals(strCodDocSelAnt)){
//                            strSQL="";
//                            strSQL+=" SELECT a1.co_imp, a1.co_ctaAct, a1.co_ctaPas FROM tbm_cabNotPedImp AS a1";
//                            strSQL+=" WHERE co_emp=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_EMP_REL) + "";
//                            strSQL+=" AND co_loc=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_LOC_REL) + "";
//                            strSQL+=" AND co_tipDoc=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_TIP_DOC_REL) + "";
//                            strSQL+=" AND co_doc IN(" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_DOC_REL) + ")";
//                            strSQL+=" AND co_imp NOT IN(" + txtCodImp.getText() + ")";
//                            strSQL+=";";
//                            System.out.println("2 - Fallo por cuenta ya creada: " + strSQL);
//                            rst=stm.executeQuery(strSQL);
//                            if(rst.next()){
//                                System.out.println("2 - ENTRO!!!!");
//                                blnRes=false;
//                                break;
//                            }
//                        }  
//                    }
//                }
//            }
//        }
//        catch(java.sql.SQLException e){
//            objUti.mostrarMsgErr_F1(this, e);
//            blnRes=false;
//        }
//        catch(Exception e){
//            objUti.mostrarMsgErr_F1(this, e);
//            blnRes=false;
//        }
        return blnRes;
    }
    
    
    
    /**
     * Función que permite conocer si la cuenta creada en la Nota de P
     * @return 
     */
    private boolean isCuentaAsociadaSaldo(){
        boolean blnRes=false;
        Connection conPedEmb;
        Statement stmPedEmb;
        ResultSet rstPedEmb;
        try{
            conPedEmb=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conPedEmb!=null){
                stmPedEmb=conPedEmb.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_salcta";
                strSQL+=" FROM tbm_cabPedEmbImp AS a1 INNER JOIN tbm_plaCta AS a2";
                strSQL+=" ON a1.co_imp=a2.co_emp AND a1.co_ctaAct=a2.co_cta";
                strSQL+=" INNER JOIN tbm_salCta AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc="+ objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                strSQL+=" AND a2.st_reg='A'";
                strSQL+=" AND a3.nd_salcta<>0";
                strSQL+=" UNION";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_salcta";
                strSQL+=" FROM tbm_cabPedEmbImp AS a1 INNER JOIN tbm_plaCta AS a2";
                strSQL+=" ON a1.co_imp=a2.co_emp AND a1.co_ctaPas=a2.co_cta";
                strSQL+=" INNER JOIN tbm_salCta AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                strSQL+=" AND a2.st_reg='A'";
                strSQL+=" AND a3.nd_salcta<>0";
                rstPedEmb=stmPedEmb.executeQuery(strSQL);
                if(rstPedEmb.next())
                    blnRes=true;
                conPedEmb.close();
                conPedEmb=null;
                stmPedEmb.close();
                stmPedEmb=null;
                rstPedEmb.close();
                rstPedEmb=null;
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

    /**
     * Esta función permite anular un registro.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anular_tbmPlaCta(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_plaCta";
                strSQL+=" SET st_reg='I'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_IMP);
                strSQL+=" AND co_cta=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_CTAACT);
                strSQL+=";";
                strSQL+="UPDATE tbm_plaCta";
                strSQL+=" SET st_reg='I'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_IMP);
                strSQL+=" AND co_cta=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_CTAPAS);
                strSQL+=";";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
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
     * Función que permite determinar si algún item fue ingresado varias veces en el modelo de datos
     * @return true: Si el item fue ingresado varias veeces
     * <BR> false: Caso contrario
     */
    private boolean isExisteItemRepetido(){
        boolean blnRes=false;
        try{
            for(int i=0; i<objTblModCom76_02.getRowCountTrue(); i++){
                for(int j=(i+1); j<objTblModCom76_02.getRowCountTrue(); j++){
                    if(objTblModCom76_02.compareStringCells(i, objCom76_02.INT_TBL_DAT_COD_ITM, j, objCom76_02.INT_TBL_DAT_COD_ITM)){
                        blnRes=true;
                        break;
                    }

                }
                if(blnRes)
                    break;
            }
            
        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite validar si el precio de venta está acorde al margen de utilidad según 
     * costos de importaciones(Nota de Pedido, Pedido Embarcado, Ingreso por Importación).
     * @return 
     */
    private boolean enviarCorElePreVta(){
        boolean blnRes=false;
        int intCodCfgCorEle=14;
        int intNumFil=0;
        Connection conValPreVta;
        Statement stmValPreVta;
        ResultSet rstValPreVta;
        BigDecimal bgdPreVtaItm=new BigDecimal("0");
        //BigDecimal bgdPreVtaDscItm=new BigDecimal("0"); //precio de venta menos el 25%
        BigDecimal bgdMarUtiItm=new BigDecimal("0");
        BigDecimal bgdCosUniItm=new BigDecimal("0");      //Costo unitario del item
        BigDecimal bgdCosUniInsPed=new BigDecimal("0");   //Costo unitario de la instancia del pedido
        BigDecimal bgdRelCosUniMarPreVta=new BigDecimal("0");//para la formula    Costo Unitario /    (   1 - (Margen precio vta/100)    )
        String strSbjMsg="" + intNumPedEmbDia + ") " + txtNumPed.getText() + " (" + objParSis.getNombreMenu() + ")";
        boolean blnEstExiPreFueMar=false;//Para saber si existen items que esten fuera del margen del precio de venta
        try{
            conValPreVta=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conValPreVta!=null){
                stmValPreVta=conValPreVta.createStatement();
                
                String strMsg="";
                strMsg+="<html>";
                strMsg+="<body>";
                strMsg+="<h3>Revisar precios<hr></h3>";
                strMsg+="<table id='objTable' bgcolor='#F2E0F7' width='100%' border='1'>";
                strMsg+="<tr bgcolor='#F2E0F7'>";
                strMsg+="	<th colspan='7'>Pedido # " + txtNumPed.getText() + "</th>";
                strMsg+="</tr>";
                
                strMsg+="<tr bgcolor='#F2E0F7'>";
                strMsg+="	<th>Nº</th>";
                strMsg+="	<th>Cod.Alt.Itm.</th>";
                strMsg+="	<th>Cod.Let.Itm.</th>";
                strMsg+="	<th>Nom.Itm.</th>";
                strMsg+="	<th>Cos.Uni.Ped.</th>";
                strMsg+="	<th>Pre.Vta.</th>";
                strMsg+="	<th>Cos.Uni.Itm.</th>";
                strMsg+="	<th>Pre.Sug.</th>";
                strMsg+="	<th>Mar.Pre.Vta.M&iacuten.</th>";                        
                strMsg+="</tr>";
                
                //Se ordena por código alterno, para enviar el correo ordenado.
                objCom76_02.ordenarDatPorCodAlt();
                objTblModCom76_02.setModoOperacion(objTblModCom76_02.INT_TBL_NO_EDI);
                objTblModCom76_02.removeEmptyRows();                   
                
                for(int i=0; i<objTblModCom76_02.getRowCountTrue(); i++){
                    intNumFil++;
                    strSQL ="";
                    strSQL+=" SELECT a1.nd_preVta1, a1.nd_marUti, a1.nd_cosUni FROM tbm_inv AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_itm=" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_ITM) + "";
                    strSQL+=";";
                    rstValPreVta=stmValPreVta.executeQuery(strSQL);
                    if(rstValPreVta.next()){
                        bgdPreVtaItm=objUti.redondearBigDecimal(rstValPreVta.getBigDecimal("nd_preVta1"), objParSis.getDecimalesMostrar());
                        //bgdPreVtaDscItm=objUti.redondearBigDecimal(bgdPreVtaItm.subtract((bgdPreVtaItm.multiply(new BigDecimal("0.25")))), objParSis.getDecimalesMostrar());
                        bgdMarUtiItm=objUti.redondearBigDecimal(rstValPreVta.getBigDecimal("nd_marUti"),objParSis.getDecimalesMostrar());
                        bgdCosUniItm=objUti.redondearBigDecimal(rstValPreVta.getBigDecimal("nd_cosUni"), objParSis.getDecimalesMostrar());
                    }
                    bgdCosUniInsPed=objUti.redondearBigDecimal((new BigDecimal(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COS_UNI)==null?"0":(objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COS_UNI).toString()))), objParSis.getDecimalesMostrar());
                    //obtencion de la formula
                    bgdRelCosUniMarPreVta=objUti.redondearBigDecimal((bgdCosUniInsPed.divide((   (new BigDecimal("1")).subtract(   bgdMarUtiItm.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)        )        ), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)), objParSis.getDecimalesMostrar());

                    if(bgdRelCosUniMarPreVta.compareTo(objUti.redondearBigDecimal( (bgdPreVtaItm.subtract((bgdPreVtaItm.multiply(new BigDecimal("0.25")))).multiply(new BigDecimal("1.005"))), objParSis.getDecimalesMostrar()))>0){//bgdPreVtaDscItm      por debajo del margen  - rojo
                        blnEstExiPreFueMar=true;
                        strMsg+="<tr bgcolor='#FF4000'>";
                        strMsg+="	<td>" + intNumFil + "</td>";
                        strMsg+="	<td>" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_ALT_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_LET_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_NOM_ITM) + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniInsPed + "</td>";
                        strMsg+="	<td align=right>" + bgdPreVtaItm + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniItm + "</td>";
                        strMsg+="	<td align=right>" + (bgdRelCosUniMarPreVta.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)) + "</td>";
                        strMsg+="	<td align=right>" + bgdMarUtiItm + "</td>";                        
                        strMsg+="</tr>";
                    }
                    else if(bgdRelCosUniMarPreVta.compareTo(objUti.redondearBigDecimal( (bgdPreVtaItm.subtract((bgdPreVtaItm.multiply(new BigDecimal("0.25")))).multiply(new BigDecimal("0.995"))), objParSis.getDecimalesMostrar()))<0){//bgdPreVtaDscItm       por encima del margen - amarillo
                        blnEstExiPreFueMar=true;
                        strMsg+="<tr bgcolor='#FFFF00'>";
                        strMsg+="	<td>" + intNumFil + "</td>";
                        strMsg+="	<td>" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_ALT_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_LET_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_NOM_ITM) + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniInsPed + "</td>";
                        strMsg+="	<td align=right>" + bgdPreVtaItm + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniItm + "</td>";
                        strMsg+="	<td align=right>" + (bgdRelCosUniMarPreVta.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)) + "</td>";
                        strMsg+="	<td align=right>" + bgdMarUtiItm + "</td>";                        
                        strMsg+="</tr>";
                    }
                    else{//son iguales  - verde
                        blnEstExiPreFueMar=true;
                        strMsg+="<tr bgcolor='#D0F5A9'>";
                        strMsg+="	<td>" + intNumFil + "</td>";
                        strMsg+="	<td>" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_ALT_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_COD_LET_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModCom76_02.getValueAt(i, objCom76_02.INT_TBL_DAT_NOM_ITM) + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniInsPed + "</td>";
                        strMsg+="	<td align=right>" + bgdPreVtaItm + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniItm + "</td>";
                        strMsg+="	<td align=right>" + (bgdRelCosUniMarPreVta.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)) + "</td>";
                        strMsg+="	<td align=right>" + bgdMarUtiItm + "</td>";                        
                        strMsg+="</tr>";
                    }
                }
                strMsg+="</table>";
                strMsg+="</body>";
                strMsg+="</html> ";

                if(strMsg.length()>0){
                    if(blnEstExiPreFueMar){                    
                        if(objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodCfgCorEle, strMsg, strSbjMsg )){
                            mostrarMsgInf("<HTML>Estimado usuario, <FONT COLOR=\"blue\">REVISAR PRECIOS</FONT></HTML>");
                            blnRes=true;
                        }
                    }
                }
                objTblModCom76_02.setModoOperacion(objTblModCom76_02.INT_TBL_INS);
                conValPreVta.close();
                conValPreVta=null;
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
    
    /**
     * Función que permite determinar si existe código de seguimiento asociado a la nota de pedido (instancia anterior)
     * @return true Si existe seguimiento para la instancia anterior al Pedido Embarcado(Instancia anterior: Nota de Pedido)
     * <BR> false Caso contrario
     */
    private boolean getCodSegNotPedImp(){
        boolean blnRes=false;
        Object objCodSegNotPed=null;
        Object objCodSegPedEmb=null;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT *FROM(";
                strSQL+=" 	SELECT a3.co_seg AS co_segNotPed, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL+=" 	FROM (tbm_cabNotPedImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" 		ON a1.co_emp=a3.co_empRelCabNotPedImp AND a1.co_loc=a3.co_locRelCabNotPedImp";
                strSQL+=" 		AND a1.co_tipDoc=a3.co_tipDocRelCabNotPedImp AND a1.co_doc=a3.co_docRelCabNotPedImp)";
                strSQL+=" 	INNER JOIN tbm_detNotPedImp AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN(";
                strSQL+=" 	SELECT a3.co_seg AS co_segPedEmb, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL+=" 	, a2.co_locRel, a2.co_tipdocRel, a2.co_docRel, a2.co_regRel";
                strSQL+=" 	FROM (tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" 		ON a1.co_emp=a3.co_empRelCabPedEmbImp AND a1.co_loc=a3.co_locRelCabPedEmbImp";
                strSQL+=" 		AND a1.co_tipDoc=a3.co_tipDocRelCabPedEmbImp AND a1.co_doc=a3.co_docRelCabPedEmbImp)";
                strSQL+=" 	INNER JOIN tbm_detPedEmbImp AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 	AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" 	AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" 	AND a1.co_doc=" + txtCodDoc.getText() + "";
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_locRel AND b1.co_tipDoc=b2.co_tipDocRel AND b1.co_doc=b2.co_docRel AND b1.co_reg=b2.co_regRel";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    objCodSegNotPed=rst.getObject("co_segNotPed");
                    objCodSegPedEmb=rst.getObject("co_segPedEmb");
                }
                
                if(objCodSegPedEmb==null){//no existe seguimiento por tanto se debe ingresar 
                    blnRes=true;
                    objCodSegInsAnt=objCodSegNotPed;
                }
                else{//ya existe en el seguimiento la nota de pedido por tanto no se debe hacer nada
                    //blnRes=false; no es necesario porque desde el inicio está en "false"
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que permite conocer si existe un Valor de Cargo a Pagar con valores nulos, cuando el Pedido Embarcado se está cerrando
     * @return true Si existen valores nulos 
     * <BR> false Caso contrario
     */
    private boolean isValNulCarPag_PedEmbCer(){
        boolean blnRes=false;
        Object objValCarPag=null;
        int intCodReg=-1;
        String strTipCarPagValNul="";
        
        try{            
            for(int i=0; i<objTblModCom76_01.getRowCountTrue(); i++){
                objValCarPag=objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_VAL_CAR_PAG_IMP);
                strTipCarPagValNul=objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_TIP_CAR_PAG_IMP)==null?"":objTblModCom76_01.getValueAt(i, objCom76_01.INT_TBL_DAT_TIP_CAR_PAG_IMP).toString();                
                intCodReg=i;
                
                if(strTipCarPagValNul.equals("G")){
                    if(chkCerPedEmb.isSelected()){
                        if(objValCarPag==null){
                            blnRes=true;
                            objTblModCom76_01.setBackgroundIncompleteRows(Color.RED);
                            //break;
                        }
                    }
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=true;
        }
        return blnRes;
    }
    
    /**
     * Función que permite obtener el Código de Local Predeterminado
     * @param conexion Conexión a la base de datos
     * @param codigoEmpresa Código de la empresa
     * @return int El código de local predeterminada
     */
    public boolean getCodLocPre(Connection conexion, int codigoEmpresa){
        boolean blnRes=true;
        intCodLocPre=-1;
        try{
            if(conexion!=null){
                stm=conexion.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.tx_nom, a1.tx_ruc, a1.tx_dir, a2.co_loc";
                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbm_loc AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp";
                strSQL+=" WHERE a1.st_reg NOT IN('I','E')";
                strSQL+=" AND a1.co_emp=" + codigoEmpresa + " AND a2.st_reg='P'";
                strSQL+=" GROUP BY a1.co_emp, a1.tx_nom, a1.tx_ruc, a1.tx_dir, a2.co_loc";
                strSQL+=" ORDER BY a1.co_emp";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodLocPre=rst.getInt(("co_loc"));
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Función que permite generar el diario de la provisión
     * @param conexion Conexión a la base de datos
     * @return true Si se pudo efectuar la operación
     * <BR> false Caso contrario
     */
//    private boolean generaDiarioProvision(){
//        boolean blnRes=false;
//        try{
//            if(con!=null){
//                if(chkCerPedEmb.isSelected()){
//                    objAsiDia.inicializar();
//                    if(getCodLocPre(con, Integer.parseInt(txtCodImp.getText()))){
//                        if(objImp.getCodDocPro(con, Integer.parseInt(txtCodImp.getText()), intCodLocPre, objImp.INT_COD_TIP_DOC_PRO)){
//                            if(getCodCtaPedInsExi()){
//                                /*if(objAsiDia.generarDiario(con, txtCodImp.getText(), ("" + intCodLocPre), ("" + objImp.INT_COD_TIP_DOC_PRO), ("" + objImp.getIntCodDocPro())
//                                    , ("" + objParSis.getCodigoEmpresa()), ("" + objParSis.getCodigoLocal()), txtCodTipDoc.getText(), txtCodDoc.getText()
//                                    , objCodSegInsAnt, intCodCtaActMerTra_Pro, txtNumPed.getText(), getValGenDiaPro(), getValGenDiaPro()
//                                    )){
//                                    if(objAsiDia.insertarDiarioProvision(con, Integer.parseInt(txtCodImp.getText()), intCodLocPre, objImp.INT_COD_TIP_DOC_PRO, objImp.getIntCodDocPro()
//                                            , objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()) )){
//                                        blnRes=true;
//                                    }
//                                }*/ 
//                            }                          
//                        }
//                    }
//                }
//                else
//                    blnRes=true;
//            }
//        }
//        catch(Exception e){
//            objUti.mostrarMsgErr_F1(null, e);
//            blnRes=false;
//        }
//        return blnRes;
//    }
    
    /**
     * Función que permite obtener el código de la cuenta de activo del pedido.
     * Si existe el Pedido Embarcado obtiene la cuenta de activo de esta instancia, sino obtiene la cuenta de activo de la Nota de Pedido
     * @param conexion
     * @return 
     */
//    private boolean getCodCtaPedInsExi(){
//        boolean blnRes=true;
//        try{
//            if(con!=null){
//                stm=con.createStatement();
//                strSQL="";
//                strSQL+=" SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.co_ctaAct, c1.co_ctaPas";
//                strSQL+="      , c1.co_empEmb, c1.co_locEmb, c1.co_tipDocEmb, c1.co_docEmb, c1.co_ctaActEmb, c1.co_ctaPasEmb, c1.tx_numDoc2Emb";
//                strSQL+=" FROM(";
//                strSQL+=" 	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg, b1.co_imp, b1.co_ctaAct, b1.co_ctaPas, b1.tx_numDoc2";
//                strSQL+=" 	     , b2.co_emp AS co_empEmb, b2.co_loc AS co_locEmb, b2.co_tipDoc AS co_tipDocEmb";
//                strSQL+=" 	     , b2.co_doc AS co_docEmb, b2.co_reg AS co_regEmb, b2.co_imp AS co_impEmb, b2.co_ctaAct AS co_ctaActEmb";
//                strSQL+=" 	     , b2.co_ctaPas AS co_ctaPasEmb, b2.tx_numDoc2 AS tx_numDoc2Emb";
//                strSQL+=" 	FROM(";
//                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.co_imp, a1.co_ctaAct, a1.co_ctaPas, a1.tx_numDoc2";
//                strSQL+=" 		FROM tbm_cabNotPedImp AS a1 INNER JOIN tbm_detNotPedImp AS a2";
//                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
//                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                strSQL+=" 		AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
//                strSQL+=" 	) AS b1";
//                strSQL+=" 	LEFT OUTER JOIN(";
//                strSQL+=" 		SELECT  a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.co_imp, a1.co_ctaAct, a1.co_ctaPas, a1.tx_numDoc2";
//                strSQL+=" 		      , a2.co_locRel, a2.co_tipDocRel, a2.co_docRel, a2.co_regRel";
//                strSQL+=" 		FROM tbm_cabPedEmbImp AS a1 INNER JOIN tbm_detPedEmbImp AS a2";
//                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
//                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                strSQL+=" 		AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
//                strSQL+=" 		AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
//                strSQL+="		AND a1.co_doc=" + txtCodDoc.getText() + "";
//                strSQL+=" 	) AS b2";
//                strSQL+=" 	ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_locRel AND b1.co_tipDoc=b2.co_tipDocRel AND b1.co_doc=b2.co_docRel AND b1.co_reg=b2.co_regRel";
//                strSQL+=" 	WHERE b2.co_emp IS NOT NULL";
//                strSQL+=" ) AS c1";
//                strSQL+=" GROUP BY c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.co_ctaAct, c1.co_ctaPas";
//                strSQL+="        , c1.co_empEmb, c1.co_locEmb, c1.co_tipDocEmb, c1.co_docEmb, c1.co_ctaActEmb, c1.co_ctaPasEmb, c1.tx_numDoc2Emb";
//                rst=stm.executeQuery(strSQL);
//                if(rst.next()){
//                    if(rst.getObject("co_ctaActEmb")==null){
//                        intCodCtaActMerTra_Pro=rst.getInt("co_ctaAct");//Cuenta de Activo de Pedido Embarcado
//                        intCodCtaPasMerTra_Pro=rst.getInt("co_ctaPas");//Cuenta de Pasivo de Pedido Embarcado
//                    }
//                    else{
//                        intCodCtaActMerTra_Pro=rst.getInt("co_ctaActEmb");//Cuenta de Activo de Nota de Pedido
//                        intCodCtaPasMerTra_Pro=rst.getInt("co_ctaPasEmb");//Cuenta de Pasivo de Nota de Pedido
//                    }
//                }
//                stm.close();
//                stm=null;
//                rst.close();
//                rst=null;
//            }
//        }
//        catch(java.sql.SQLException e){
//            objUti.mostrarMsgErr_F1(null, e);
//            blnRes=false;
//        }
//        catch(Exception e){
//            objUti.mostrarMsgErr_F1(null, e);
//            blnRes=false;
//        }
//        return blnRes;
//    }
    
    /**
     * Función que permite obtener el valor Total Fob/Cfr
     * @return BigDecimal el valor
     */
//    private BigDecimal getValGenDiaPro(){
//        BigDecimal bgdTotFobCfr=BigDecimal.ZERO;
//        BigDecimal bgdValTotGas=BigDecimal.ZERO;
//        BigDecimal bgdValPro=BigDecimal.ZERO;
//        try{
//            //bgdTotFobCfr=objUti.redondearBigDecimal(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objCom76_02.getTblTot().getValueAt(0, objCom76_02.INT_TBL_DAT_TOT_FOB_CFR).toString()), objParSis.getDecimalesMostrar());
//            bgdTotFobCfr=getValTotDxP();
//            bgdValTotGas=objCom76_01.getTotalGastos();
//            bgdValPro=bgdValTotGas.subtract(bgdTotFobCfr);
//        }
//        catch(Exception e){
//            objUti.mostrarMsgErr_F1(null, e);
//        }
//        return bgdValPro;
//    }
    
    /**
     * Función que permite 
     * @return 
     */
    private BigDecimal getValTotDxP(){
        BigDecimal bgdValTotDxP=BigDecimal.ZERO;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL="";
                strSQL+=" SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.fe_doc";
                strSQL+=" , c1.tx_numDoc2, SUM(c1.nd_monDebDxP) AS nd_valTotDxP";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc";
                strSQL+=" 	     , b1.tx_numDoc2, b1.co_imp, b1.co_ctaAct, b1.co_ctaPas, b1.st_reg";
                strSQL+=" 	     , b2.co_emp AS co_empDxP, b2.co_loc AS co_locDxP, b2.co_tipDoc AS co_tipDocDxP";
                strSQL+=" 	     , b2.co_doc AS co_docDxP, b2.co_reg AS co_regDxP, b2.st_reg AS st_regDxP";
                strSQL+=" 	     , b2.co_cta AS co_ctaDxP, b2.nd_monDeb AS nd_monDebDxP, b2.nd_monHab AS nd_monHabDxP";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
                strSQL+=" 		     , a1.tx_numDoc2, a1.co_imp, a1.co_ctaAct, a1.co_ctaPas, a1.st_reg";
                strSQL+=" 		FROM tbm_cabPedEmbImp AS a1";
                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="               AND a1.co_tipDoc=" + txtCodTipDoc.getText() + " AND a1.co_doc=" + txtCodDoc.getText() + "";
                strSQL+=" 	) AS b1";
                strSQL+=" 	INNER JOIN(";
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.co_reg, a1.st_reg";
                strSQL+=" 		     , a1.nd_valIva, a3.co_cta, a3.nd_monDeb, a3.nd_monHab";
                strSQL+=" 		FROM tbm_cabMovInv AS a1 INNER JOIN tbm_cabDia AS a2";
                strSQL+=" 		INNER JOIN tbm_detDia AS a3";
                strSQL+=" 		ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_dia=a3.co_dia";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_dia";
                strSQL+=" 		WHERE a1.co_tipDoc=" + objImp.INT_COD_TIP_DOC_DOC_POR_PAG + "";
                strSQL+=" 		ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.co_reg";
                strSQL+=" 	) AS b2";
                strSQL+=" 	ON b1.co_imp=b2.co_emp AND b1.co_ctaAct=b2.co_cta";
                strSQL+=" ) AS c1";
                strSQL+=" GROUP BY c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.fe_doc, c1.tx_numDoc2";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    bgdValTotDxP=rst.getBigDecimal("nd_valTotDxP");
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
        }
        return bgdValTotDxP;
    }
    
    
    
}


package RecursosHumanos.ZafRecHum58;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Maestros.ZafMae07.ZafMae07_01;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Seguimiento de Novedades de recursos humanos.
 * @author  Roberto Flores
 * Guayaquil 14/03/2013
 */
public class ZafRecHum58 extends javax.swing.JInternalFrame
{
    
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_COD_NOV=1;
    private static final int INT_TBL_DAT_FEC_NOV=2;
    private static final int INT_TBL_DAT_COD_REM=3;
    private static final int INT_TBL_DAT_REM=4;
    private static final int INT_TBL_DAT_COD_DES=5;
    private static final int INT_TBL_DAT_DES=6;
    private static final int INT_TBL_DAT_DES_TIP_NOV=7;
    private static final int INT_TBL_DAT_ASU=8;
    private static final int INT_TBL_DAT_MSJ=9;
    private static final int INT_TBL_DAT_BUT_NOV=10;
    private static final int INT_TBL_DAT_CHK_LEI=11;
    private static final int INT_TBL_DAT_FEC_LEI=12;
    private static final int INT_TBL_DAT_NUM_REEN=13;
    private static final int INT_TBL_DAT_COD_DES_REEN=14;
    private static final int INT_TBL_DAT_DES_REEN=15;
    private static final int INT_TBL_DAT_BUT_DES_REEN=16;
    private static final int INT_TBL_DAT_BUT_HIS_REEN=17;
    private static final int INT_TBL_DAT_CHK_PEN=18;
    private static final int INT_TBL_DAT_CHK_ANL=19;
    private static final int INT_TBL_DAT_CHK_TER=20;
    private static final int INT_TBL_DAT_FEC_ANL_TER=21;
    private static final int INT_TBL_DAT_OBS_NOV_FIN=22;
    private static final int INT_TBL_DAT_BUT_OBS_NOV_FIN=23;
    
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                            //Editor: Editor del JTable.
    private ZafThreadGUI objThrGUI;
    
    private ZafTblCelEdiChk zafTblCelEdiChkLab;                             //Editor de Check Box para campo Laborable
    private ZafTblCelRenChk zafTblCelRenChkLab;                             //Renderer de Check Box para campo Laborable
    
    private ZafTblCelRenChk zafTblCelRenChkAnl;                             //Renderer de Check Box para campo Laborable
    private ZafTblCelRenChk zafTblCelRenChkTer;                             //Renderer de Check Box para campo Laborable
    
    private ZafTblCelEdiChk objTblCelEdiChkAnl;                             //Editor de Check Box 
    private ZafTblCelEdiChk objTblCelEdiChkTer;                             //Editor de Check Box 
    
    private ZafMouMotAda objMouMotAda;                                      //ToolTipText en TableHeader.
//    private ZafMouMotAdaMovReg objMouMotAdaMovReg;                        //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                      //PopupMenu: Establecer PopupMenú en JTable.
    
    private ZafTblCelRenBut objTblCelRenBut;                                //Render: Presentar JButton en JTable.

    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;

    private String strCodTra = "";
    private String strNomTra = "";

    static final int INT_TBL_DAT_NUM_TOT_CDI=22;                            //Número total de columnas dinámicas.
    
    private ZafPerUsr objPerUsr;
    private ZafTblBus objTblBus;

    private String strVersion="v1.04";
        
    private ZafVenCon vcoUsrRem;                                            //Ventana de consulta "Usuario".
    private ZafVenCon vcoUsrDes;                                            //Ventana de consulta "Usuario".
    private ZafVenCon vcoTipNovRecHum;                                      //Ventana de consulta "Tipo de Novedad".
    private String strDesCorUsrDes, strDesLarUsrDes;                        //Contenido del campo al obtener el foco.
    private String strDesCorUsrRem, strDesLarUsrRem;                        //Contenido del campo al obtener el foco.
    
    private String strUsrDes="";
    private String strNomUsrDes="";
    private String strCodUsrDes="";
    
    private String strUsrRem="";
    private String strNomUsrRem="";
    private String strCodUsrRem="";
    
    private ZafRecHum58_01 objRecHum58_01;
    private ZafRecHum58_02 objRecHum58_02;
    public int intPosAct;
    private ZafTblCelEdiButDlg objTblCelEdiButDlg;              //Editor: JButton en celda.
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    
    private String strCodTipNov="";
    private String strDesLarTipNov="";
    
    private boolean blnTiePer=false;
    
    private ZafThreadGUIRpt objThrGUIRpt;
    private ZafRptSis objRptSis;
    
    private String strSql="";
    
    private boolean blnMnuIni=false;
    
    /** Crea una nueva instancia de la clase ZafRecHum58. */
    public ZafRecHum58(ZafParSis obj)
    {
        try
        {
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            
            initComponents();
            
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, obj);
            
            butCon.setVisible(false);
            butGua.setVisible(false);
            butCer.setVisible(false);
            
            if(objPerUsr.isOpcionEnabled(3476)){
                butCon.setVisible(true);
            }
            if(objPerUsr.isOpcionEnabled(3477)){
                butGua.setVisible(true);
            }
            if(objPerUsr.isOpcionEnabled(3478)){
                butCer.setVisible(true);
            }
            
            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e)
        {
            {objUti.mostrarMsgErr_F1(this, e);}
        }catch(Exception e){
            e.printStackTrace();
            {objUti.mostrarMsgErr_F1(this, e);}
        }
    }
    
    /** Crea una nueva instancia de la clase ZafRecHum58. */
    public ZafRecHum58(ZafParSis obj, boolean blnMnuIni)
    {
        try
        {
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            
            initComponents();
            
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, obj);
            
            if (!configurarFrm())
                exitForm();
            
            if(objParSis.getCodigoUsuario()!=101){
                txtUsrDes.setText(""+objParSis.getNombreUsuario());
            }
            mostrarVenConUsrDes(1);
            optTod.setSelected(false);
            optFil.setSelected(true);
            jChkNovAnl.setSelected(false);
            jChkNovTer.setSelected(false);
            
             if (objThrGUI==null) {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
                this.blnMnuIni=blnMnuIni;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            {objUti.mostrarMsgErr_F1(this, e);}
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        jlblRem = new javax.swing.JLabel();
        jlblDes = new javax.swing.JLabel();
        jlblRee = new javax.swing.JLabel();
        jChkNovPen = new javax.swing.JCheckBox();
        jChkNovTer = new javax.swing.JCheckBox();
        panCab = new javax.swing.JPanel();
        jChkNovAnl = new javax.swing.JCheckBox();
        jlblTip = new javax.swing.JLabel();
        jspNumRee = new javax.swing.JSpinner();
        txtCodUsrRem = new javax.swing.JTextField();
        txtUsrRem = new javax.swing.JTextField();
        txtDesLarUsrRem = new javax.swing.JTextField();
        butUsrRem = new javax.swing.JButton();
        txtCodUsrDes = new javax.swing.JTextField();
        txtUsrDes = new javax.swing.JTextField();
        txtDesLarUsrDes = new javax.swing.JTextField();
        butUsrDes = new javax.swing.JButton();
        txtCodTipNov = new javax.swing.JTextField();
        txtDesTipNov = new javax.swing.JTextField();
        butTipNov = new javax.swing.JButton();
        jRBOr = new javax.swing.JRadioButton();
        jRBAnd = new javax.swing.JRadioButton();
        panRpt = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtNovPen = new javax.swing.JTextField();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
        butVisPre = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

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
            }
        });

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        lblTit.setPreferredSize(new java.awt.Dimension(138, 18));
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panFrm.setAutoscrolls(true);
        panFrm.setPreferredSize(new java.awt.Dimension(475, 311));
        panFrm.setLayout(new java.awt.BorderLayout());

        tabFrm.setPreferredSize(new java.awt.Dimension(475, 311));

        panFil.setLayout(null);

        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setSelected(true);
        optTod.setText("Todas las novedades");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 110, 330, 20);

        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Sólo las novedades que cumplan con el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(10, 130, 370, 20);

        jlblRem.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jlblRem.setText("Remitente:"); // NOI18N
        panFil.add(jlblRem);
        jlblRem.setBounds(40, 150, 60, 20);

        jlblDes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jlblDes.setText("Destinatario:"); // NOI18N
        panFil.add(jlblDes);
        jlblDes.setBounds(40, 190, 60, 20);

        jlblRee.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jlblRee.setText("Reenvios:"); // NOI18N
        panFil.add(jlblRee);
        jlblRee.setBounds(40, 230, 100, 20);

        jChkNovPen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jChkNovPen.setSelected(true);
        jChkNovPen.setText("Mostrar las novedades pendientes");
        jChkNovPen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChkNovPenActionPerformed(evt);
            }
        });
        panFil.add(jChkNovPen);
        jChkNovPen.setBounds(40, 250, 420, 23);

        jChkNovTer.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jChkNovTer.setSelected(true);
        jChkNovTer.setText("Mostrar las novedades terminadas");
        jChkNovTer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChkNovTerActionPerformed(evt);
            }
        });
        panFil.add(jChkNovTer);
        jChkNovTer.setBounds(40, 290, 420, 23);

        panCab.setPreferredSize(new java.awt.Dimension(0, 130));
        panCab.setLayout(null);
        panFil.add(panCab);
        panCab.setBounds(0, 0, 690, 110);

        jChkNovAnl.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jChkNovAnl.setSelected(true);
        jChkNovAnl.setText("Mostrar las novedades anuladas");
        jChkNovAnl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChkNovAnlActionPerformed(evt);
            }
        });
        panFil.add(jChkNovAnl);
        jChkNovAnl.setBounds(40, 270, 420, 23);

        jlblTip.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jlblTip.setText("Tipo:"); // NOI18N
        panFil.add(jlblTip);
        jlblTip.setBounds(40, 210, 70, 20);

        jspNumRee.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jspNumRee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jspNumReeMouseClicked(evt);
            }
        });
        panFil.add(jspNumRee);
        jspNumRee.setBounds(140, 230, 60, 20);

        txtCodUsrRem.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panFil.add(txtCodUsrRem);
        txtCodUsrRem.setBounds(110, 150, 32, 20);
        txtCodUsrRem.setVisible(false);

        txtUsrRem.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtUsrRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsrRemActionPerformed(evt);
            }
        });
        txtUsrRem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsrRemFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsrRemFocusLost(evt);
            }
        });
        panFil.add(txtUsrRem);
        txtUsrRem.setBounds(140, 150, 60, 20);

        txtDesLarUsrRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarUsrRemActionPerformed(evt);
            }
        });
        txtDesLarUsrRem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarUsrRemFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarUsrRemFocusLost(evt);
            }
        });
        panFil.add(txtDesLarUsrRem);
        txtDesLarUsrRem.setBounds(200, 150, 250, 20);

        butUsrRem.setText(".."); // NOI18N
        butUsrRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butUsrRemActionPerformed(evt);
            }
        });
        panFil.add(butUsrRem);
        butUsrRem.setBounds(450, 150, 20, 20);

        txtCodUsrDes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panFil.add(txtCodUsrDes);
        txtCodUsrDes.setBounds(110, 190, 32, 20);
        txtCodUsrDes.setVisible(false);

        txtUsrDes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtUsrDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsrDesActionPerformed(evt);
            }
        });
        txtUsrDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsrDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsrDesFocusLost(evt);
            }
        });
        panFil.add(txtUsrDes);
        txtUsrDes.setBounds(140, 190, 60, 20);

        txtDesLarUsrDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarUsrDesActionPerformed(evt);
            }
        });
        txtDesLarUsrDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarUsrDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarUsrDesFocusLost(evt);
            }
        });
        panFil.add(txtDesLarUsrDes);
        txtDesLarUsrDes.setBounds(200, 190, 250, 20);

        butUsrDes.setText(".."); // NOI18N
        butUsrDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butUsrDesActionPerformed(evt);
            }
        });
        panFil.add(butUsrDes);
        butUsrDes.setBounds(450, 190, 20, 20);

        txtCodTipNov.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodTipNov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTipNovActionPerformed(evt);
            }
        });
        txtCodTipNov.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTipNovFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTipNovFocusLost(evt);
            }
        });
        panFil.add(txtCodTipNov);
        txtCodTipNov.setBounds(140, 210, 60, 20);

        txtDesTipNov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesTipNovActionPerformed(evt);
            }
        });
        txtDesTipNov.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesTipNovFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesTipNovFocusLost(evt);
            }
        });
        panFil.add(txtDesTipNov);
        txtDesTipNov.setBounds(200, 210, 250, 20);

        butTipNov.setText(".."); // NOI18N
        butTipNov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipNovActionPerformed(evt);
            }
        });
        panFil.add(butTipNov);
        butTipNov.setBounds(450, 210, 20, 20);

        jRBOr.setSelected(true);
        jRBOr.setText("O");
        jRBOr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBOrActionPerformed(evt);
            }
        });
        panFil.add(jRBOr);
        jRBOr.setBounds(200, 170, 140, 23);

        jRBAnd.setText("Y");
        jRBAnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBAndActionPerformed(evt);
            }
        });
        panFil.add(jRBAnd);
        jRBAnd.setBounds(110, 170, 90, 23);

        tabFrm.addTab("Filtro", null, panFil, "Filtro");

        panRpt.setLayout(new java.awt.BorderLayout());

        jPanel2.setMinimumSize(new java.awt.Dimension(560, 30));
        jPanel2.setPreferredSize(new java.awt.Dimension(560, 40));
        jPanel2.setLayout(null);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel7.setText("Novedades pendientes de meses anteriores:"); // NOI18N
        jPanel2.add(jLabel7);
        jLabel7.setBounds(20, 10, 230, 20);

        txtNovPen.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel2.add(txtNovPen);
        txtNovPen.setBounds(240, 10, 70, 20);
        txtNovPen.setEditable(false);

        panRpt.add(jPanel2, java.awt.BorderLayout.PAGE_START);

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
        scrTbl.setViewportView(tblDat);

        panRpt.add(scrTbl, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(320, 42));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butGua.setText("Guardar");
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butImp.setText("Imprimir");
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panBot.add(butImp);

        butVisPre.setText("Vista preliminar");
        butVisPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisPreActionPerformed(evt);
            }
        });
        panBot.add(butVisPre);

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

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 17));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(700, 450));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed

        if (objThrGUI==null) {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
        
        
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblMod.isDataModelChanged())
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                if(guardarDat()){
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                    if (objThrGUI==null) {
                        objThrGUI=new ZafThreadGUI();
                        objThrGUI.start();
                    }
                }else{
                    if(!blnTiePer){
                        mostrarMsgErr("No dispone de permisos para anular o dar por terminada la novedad.");
                    }else{
                        mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                    }
                    
                    if (objThrGUI==null) {
                        objThrGUI=new ZafThreadGUI();
                        objThrGUI.start();
                    }
                    
                }
            }
        }
        else
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
    }//GEN-LAST:event_butGuaActionPerformed
   
    private boolean guardarDat(){
        boolean blnRes=false;
        java.sql.Connection con = null; 
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSql;
        this.blnTiePer=false;
        
        try{
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());

            if(con!=null){

                con.setAutoCommit(false);
                stmLoc=con.createStatement();

                for(int i=0; i<tblDat.getRowCount();i++){

                    if(tblDat.getValueAt(i, INT_TBL_DAT_LIN).toString().compareTo("M")==0){
                      String strStReg="";
                        boolean blnAln=(Boolean)tblDat.getValueAt(i, INT_TBL_DAT_CHK_ANL);
                        boolean blnTer;
                        if(blnAln){
                            strStReg="I";
                        }else{
                            blnTer=(Boolean)tblDat.getValueAt(i, INT_TBL_DAT_CHK_TER);
                             if(blnTer){
                                 strStReg="T";
                             }
                        }
                        
                        /*
                         * usuario final que anula/termina la novedad
                         */
                        String strFil="";
                        strSql="";
                        strSql="select case  co_usrdesree is null when true then co_usrdes else co_usrdesree end as co_usranlter,";
                        strSql+=" case  co_usrdesree is null when true then 1 else 2 end as intCas,";
                        strSql+=" co_usrdes,co_usrdesree";
                        strSql+=" from tbm_novrechum";
                        strSql+=" where co_nov="+tblDat.getValueAt(i, INT_TBL_DAT_COD_NOV).toString();
                        System.out.println("strCon : " + strSql);
                        rstLoc=stmLoc.executeQuery(strSql);
                        
                        if(rstLoc.next()){
                            if(rstLoc.getString("intCas").compareTo("1")==0){
                                if(rstLoc.getInt("co_usrdes")==objParSis.getCodigoUsuario()){
                                    strFil=" and co_usrdes = " + rstLoc.getInt("co_usranlter");
                                    strSql="";
                                    strSql+="update tbm_novrechum set st_reg = " + objUti.codificar(strStReg) + " , ";
                                    strSql+=" fe_ultmod = current_timestamp , co_usrmod = " + objParSis.getCodigoUsuario();
                                    strSql+=" , tx_obsfin = " + objUti.codificar(tblDat.getValueAt(i, INT_TBL_DAT_OBS_NOV_FIN)) + " , ";
                                    strSql+=" fe_fin = current_timestamp";
                                    strSql+=" where co_nov = " + tblDat.getValueAt(i, INT_TBL_DAT_COD_NOV).toString();
                                    strSql+= " " + strFil;
                                    System.out.println("updateTbm_NovRecHum: " + strSql);
                                    stmLoc.executeUpdate(strSql);
                                    blnRes=true;
                                    blnTiePer=true;
                                }
                            }else{
                                if(rstLoc.getInt("co_usrdesree")==objParSis.getCodigoUsuario()){
                                    strFil=" and co_usrdesree = " + rstLoc.getInt("co_usranlter");
                                    strSql="";
                                    strSql+="update tbm_novrechum set st_reg = " + objUti.codificar(strStReg) + " , ";
                                    strSql+=" fe_ultmod = current_timestamp , co_usrmod = " + objParSis.getCodigoUsuario();
                                    strSql+=" , tx_obsfin = " + objUti.codificar(tblDat.getValueAt(i, INT_TBL_DAT_OBS_NOV_FIN)) + " , ";
                                    strSql+=" fe_fin = current_timestamp";
                                    strSql+=" where co_nov = " + tblDat.getValueAt(i, INT_TBL_DAT_COD_NOV).toString();
                                    strSql+= " " + strFil;
                                    System.out.println("updateTbm_NovRecHum: " + strSql);
                                    stmLoc.executeUpdate(strSql);
                                    blnRes=true;
                                    blnTiePer=true;
                                }
                            }
                        }else{
                            blnTiePer=false;
                        }
                    }
                }
                if(blnRes){
                    con.commit();
                }
            }
        }catch(java.sql.SQLException Evt) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }catch(Exception Evt) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }finally {
            try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
            try{rstLoc.close();rstLoc=null;}catch(Throwable ignore){}
            try{con.close();con=null;}catch(Throwable ignore){}
        }
    return blnRes;
}
    
    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged

   }//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected()) {
            txtCodUsrRem.setText("");
            txtUsrRem.setText("");
            txtDesLarUsrRem.setText("");
            txtCodTipNov.setText("");
            txtDesTipNov.setText("");
            txtUsrDes.setText("");
            txtCodUsrDes.setText("");
            txtDesLarUsrDes.setText("");
        }
        optTod.setSelected(true);
        optFil.setSelected(false);
        jChkNovPen.setSelected(true);
        jChkNovAnl.setSelected(true);
        jChkNovTer.setSelected(true);
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optFilItemStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) {
            txtCodUsrRem.setText("");
            txtUsrRem.setText("");
            txtDesLarUsrRem.setText("");
            txtCodUsrDes.setText("");
            txtUsrDes.setText("");
            txtDesLarUsrDes.setText("");
            txtCodTipNov.setText("");
            txtDesTipNov.setText("");
            
        }
        optTod.setSelected(false);
        optFil.setSelected(true);
    }//GEN-LAST:event_optFilActionPerformed

    
    
    private void jChkNovTerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChkNovTerActionPerformed
        // TODO add your handling code here:
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_jChkNovTerActionPerformed

    private void jChkNovPenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChkNovPenActionPerformed
        // TODO add your handling code here:
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_jChkNovPenActionPerformed

    private void jChkNovAnlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChkNovAnlActionPerformed
        // TODO add your handling code here:
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_jChkNovAnlActionPerformed

    private void txtUsrRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsrRemActionPerformed
        // TODO add your handling code here:
        txtUsrRem.transferFocus();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtUsrRemActionPerformed

    private void txtUsrRemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrRemFocusGained
        // TODO add your handling code here:
        strUsrRem=txtUsrRem.getText();
        txtUsrRem.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtUsrRemFocusGained

    private void txtUsrRemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrRemFocusLost
        // TODO add your handling code here:
        if (txtUsrRem.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtUsrRem.getText().equalsIgnoreCase(strDesCorUsrRem)) {
                if (txtUsrRem.getText().equals("")) {
                    txtCodUsrRem.setText("");
                    txtDesLarUsrRem.setText("");
                } else {
                    mostrarVenConUsr(1);
                }
            } else {
                txtUsrRem.setText(strDesCorUsrRem);
            }
        }
    }//GEN-LAST:event_txtUsrRemFocusLost

    private void txtDesLarUsrRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarUsrRemActionPerformed
        // TODO add your handling code here:
        txtDesLarUsrRem.transferFocus();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtDesLarUsrRemActionPerformed

    private void txtDesLarUsrRemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarUsrRemFocusGained
        // TODO add your handling code here:
        strDesLarUsrRem = txtDesLarUsrRem.getText();
        txtDesLarUsrRem.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtDesLarUsrRemFocusGained

    private void txtDesLarUsrRemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarUsrRemFocusLost
        // TODO add your handling code here:
        if (txtDesLarUsrRem.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarUsrRem.getText().equalsIgnoreCase(strDesLarUsrRem)) {
                if (txtDesLarUsrRem.getText().equals("")) {
                    txtCodUsrRem.setText("");
                    txtUsrRem.setText("");
                } else {
                    mostrarVenConUsr(2);
                }
            } else {
                txtDesLarUsrRem.setText(strDesLarUsrRem);
            }
        }
    }//GEN-LAST:event_txtDesLarUsrRemFocusLost

    private void butUsrRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butUsrRemActionPerformed
        // TODO add your handling code here:
        
        vcoUsrRem=null;
        configurarVenConUsrRem();
        mostrarVenConUsr(0);
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_butUsrRemActionPerformed

    private void butUsrDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butUsrDesActionPerformed
        // TODO add your handling code here:
        vcoUsrDes=null;
        configurarVenConUsrDes();
        mostrarVenConUsrDes(0);
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_butUsrDesActionPerformed

    private void txtDesLarUsrDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarUsrDesFocusLost
        // TODO add your handling code here:
        if (txtDesLarUsrDes.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarUsrDes.getText().equalsIgnoreCase(strDesLarUsrDes)) {
                if (txtDesLarUsrDes.getText().equals("")) {
                    txtCodUsrDes.setText("");
                    txtUsrDes.setText("");
                } else {
                    mostrarVenConUsrDes(2);
                }
            } else {
                txtDesLarUsrDes.setText(strDesLarUsrDes);
            }
        }
    }//GEN-LAST:event_txtDesLarUsrDesFocusLost

    private void txtDesLarUsrDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarUsrDesFocusGained
        // TODO add your handling code here:
        strDesLarUsrDes = txtDesLarUsrDes.getText();
        txtDesLarUsrDes.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtDesLarUsrDesFocusGained

    private void txtDesLarUsrDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarUsrDesActionPerformed
        // TODO add your handling code here:
        txtDesLarUsrDes.transferFocus();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtDesLarUsrDesActionPerformed

    private void txtUsrDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrDesFocusLost
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (txtUsrDes.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtUsrDes.getText().equalsIgnoreCase(strDesCorUsrDes)) {
                if (txtUsrDes.getText().equals("")) {
                    txtCodUsrDes.setText("");
                    txtDesLarUsrDes.setText("");
                } else {
                    mostrarVenConUsrDes(1);
                }
            } else {
                txtUsrDes.setText(strDesCorUsrRem);
            }
        }
    }//GEN-LAST:event_txtUsrDesFocusLost

    private void txtUsrDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrDesFocusGained
        // TODO add your handling code here:
         strUsrDes=txtUsrDes.getText();
        txtUsrDes.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtUsrDesFocusGained

    private void txtUsrDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsrDesActionPerformed
        // TODO add your handling code here:
        txtUsrDes.transferFocus();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtUsrDesActionPerformed

    private void jspNumReeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jspNumReeMouseClicked
        // TODO add your handling code here:
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_jspNumReeMouseClicked

    private void txtCodTipNovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTipNovActionPerformed
        //consultarRepDep();
        txtCodTipNov.transferFocus();
    }//GEN-LAST:event_txtCodTipNovActionPerformed

    private void txtCodTipNovFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTipNovFocusGained
        // TODO add your handling code here:
        strCodTipNov = txtCodTipNov.getText();
        txtCodTipNov.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodTipNovFocusGained

    private void txtCodTipNovFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTipNovFocusLost
        // TODO add your handling code here:
        if (!txtCodTipNov.getText().equalsIgnoreCase(strCodTipNov)) {
            if (txtCodTipNov.getText().equals("")) {
                txtCodTipNov.setText("");
                txtDesTipNov.setText("");
            } else {
                BuscarTipNov("a1.co_tipnov", txtCodTipNov.getText(), 0);
            }
        } else {
            txtCodTipNov.setText(strCodTipNov);
        }
    }//GEN-LAST:event_txtCodTipNovFocusLost

    public void BuscarTipNov(String campo,String strBusqueda,int tipo){
        
        vcoTipNovRecHum.setTitle("Listado de Departamentos");
        if(vcoTipNovRecHum.buscar(campo, strBusqueda )) {
            txtCodTipNov.setText(vcoTipNovRecHum.getValueAt(1));
            txtDesTipNov.setText(vcoTipNovRecHum.getValueAt(2));
        }else{
            vcoTipNovRecHum.setCampoBusqueda(tipo);
            vcoTipNovRecHum.cargarDatos();
            vcoTipNovRecHum.show();
            if (vcoTipNovRecHum.getSelectedButton()==vcoTipNovRecHum.INT_BUT_ACE) {
                txtCodTipNov.setText(vcoTipNovRecHum.getValueAt(1));
                txtCodTipNov.setText(vcoTipNovRecHum.getValueAt(2));
        }else{
                txtCodTipNov.setText(strCodTipNov);
                txtDesTipNov.setText(strDesLarTipNov);
  }}}
    
    private void txtDesTipNovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesTipNovActionPerformed

        // TODO add your handling code here:
        txtDesTipNov.transferFocus();
    }//GEN-LAST:event_txtDesTipNovActionPerformed

    private void txtDesTipNovFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesTipNovFocusGained
        // TODO add your handling code here:
        strDesLarTipNov=txtDesTipNov.getText();
        txtDesTipNov.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtDesTipNovFocusGained

    private void txtDesTipNovFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesTipNovFocusLost
        // TODO add your handling code here:
        if (txtDesTipNov.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesTipNov.getText().equalsIgnoreCase(strDesLarTipNov))
            {
                if (txtDesTipNov.getText().equals(""))
                {
                    txtCodTipNov.setText("");
                    txtDesTipNov.setText("");
                }
                else
                {
                    mostrarVenConTipNov(2);
                }
            }
            else
            txtDesTipNov.setText(strDesLarTipNov);
        }
    }//GEN-LAST:event_txtDesTipNovFocusLost

    private void butTipNovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipNovActionPerformed
        //        strCodTipNov=txtCodTipNov.getText();
        mostrarVenConTipNov(0);
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_butTipNovActionPerformed

    private void jRBAndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBAndActionPerformed
        // TODO add your handling code here:
        jRBOr.setSelected(Boolean.FALSE);
        jRBAnd.setSelected(Boolean.TRUE);
    }//GEN-LAST:event_jRBAndActionPerformed

    private void jRBOrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBOrActionPerformed
        // TODO add your handling code here:
        jRBOr.setSelected(Boolean.TRUE);
        jRBAnd.setSelected(Boolean.FALSE);
    }//GEN-LAST:event_jRBOrActionPerformed

    private void cargarRepote(int intTipo){
       if (objThrGUIRpt==null)
        {
            objThrGUIRpt=new ZafThreadGUIRpt();
            objThrGUIRpt.setIndFunEje(intTipo);
            objThrGUIRpt.start();
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
    private class ZafThreadGUIRpt extends Thread
    {
        private int intIndFun;

        public ZafThreadGUIRpt()
        {
            intIndFun=0;
        }

        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                   // objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                  //  objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                 //   objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                   //google objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUIRpt=null;
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
        String strRutRpt, strNomRpt, strFecHorSer="", strCamAudRpt = "";
        int i, intNumTotRpt;
        boolean blnRes=true;
        String strSql = "", sqlFil = "", sqlAux = "";
        String strFilFec="", strFilFec1="";
        String strEstConf="";
        String strAux="", strAux1="";
        try
        {
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
            {
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {

                            default:

                                //Inicializar los parametros que se van a pasar al reporte.

                                //Obtener la fecha y hora del servidor.
                                Date datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                                if (datFecAux!=null){
                                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                                }

                                switch (objSelFec.getTipoSeleccion()){
                                    case 0: //Búsqueda por rangos
                                        strFilFec+=" and date(a.fe_ing) BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                                        strFilFec1+=" and date(b.fe_ing) BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                                        break;
                                    case 1: //Fechas menores o iguales que "Hasta".
                                        strFilFec+=" and date(a.fe_ing)<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                                        strFilFec1+=" and date(b.fe_ing)<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                                        break;
                                    case 2: //Fechas mayores o iguales que "Desde".
                                        strFilFec+=" and date(a.fe_ing)>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                                        strFilFec1+=" and date(b.fe_ing)>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                                        break;
                                    case 3: //Todo.
                                        break;
                                }
                                
                                if(optFil.isSelected()){
                    
                                    if(jChkNovPen.isSelected()) { if(strEstConf.equals("")) strEstConf+="'P'"; else strEstConf+=",'P'"; }
                                    if(jChkNovAnl.isSelected()) { if(strEstConf.equals("")) strEstConf+="'I'"; else strEstConf+=",'I'"; }
                                    if(jChkNovTer.isSelected()) { if(strEstConf.equals("")) strEstConf+="'T'"; else strEstConf+=",'T'"; }

                                    if(!strEstConf.equals("")){
                                        strAux+=" AND a.st_reg IN ("+strEstConf+") ";
                                        strAux1+=" AND b.st_reg IN ("+strEstConf+") ";
                                    }
                                        
                                }
                                
                                String strCoTipNov="";
                                if(txtCodTipNov.getText().length()>0){
                                    strCoTipNov=" and a.co_tipnov = " + txtCodTipNov.getText();
                                }

                                
                                int intNeNumRee=0;
                                String strNeNumReeFil="";
                                String strNeNumReeFil1="";
                                
                                if(Integer.parseInt(jspNumRee.getValue().toString())>0){
                                    strNeNumReeFil=" and a.ne_numree = " + jspNumRee.getValue().toString();
                                    strNeNumReeFil1=" and b.ne_numree = " + jspNumRee.getValue().toString();
                                }
                                
                                
                                String strUsrReeFil="";
                                String strUsrDesFil="";
                                
                                String strUsrReeFil1="";
                                String strUsrDesFil1="";
                                
                                if(txtCodUsrRem.getText().length()>0){
                                    if(txtCodUsrDes.getText().length()>0){
                                        if(jRBAnd.isSelected()){
                                            strUsrReeFil = " and (a.co_usrrem = " + txtCodUsrRem.getText() + " and a.co_usrdes = " + txtCodUsrDes.getText() +")";
                                            strUsrReeFil1 = " and (b.co_usrrem = " + txtCodUsrRem.getText() + " and b.co_usrdes = " + txtCodUsrDes.getText() +")";
                                        }
                                        if(jRBOr.isSelected()){
                                            strUsrReeFil = " and (a.co_usrrem = " + txtCodUsrRem.getText() + " or a.co_usrdes = " + txtCodUsrDes.getText() +")";
                                            strUsrReeFil1 = " and (b.co_usrrem = " + txtCodUsrRem.getText() + " or b.co_usrdes = " + txtCodUsrDes.getText() +")";
                                        }
                                    }else{
                                        strUsrReeFil = " and a.co_usrrem = " + txtCodUsrRem.getText();
                                        strUsrReeFil1 = " and b.co_usrrem = " + txtCodUsrRem.getText();
                                    }
                                }
                                
                                if(txtCodUsrDes.getText().length()>0){
                                    if(txtCodUsrRem.getText().length()>0){
                                        if(jRBAnd.isSelected()){
                                            strUsrDesFil = " and (a.co_usrdes = " + txtCodUsrDes.getText() + " and a.co_usrrem = " + txtCodUsrRem.getText() +")";
                                            strUsrDesFil1 = " and (b.co_usrdes = " + txtCodUsrDes.getText() + " and b.co_usrrem = " + txtCodUsrRem.getText() +")";
                                        }
                                        if(jRBOr.isSelected()){
                                            strUsrDesFil = " and (a.co_usrdes = " + txtCodUsrDes.getText() + " or a.co_usrrem = " + txtCodUsrRem.getText() +")";
                                            strUsrDesFil1 = " and (b.co_usrdes = " + txtCodUsrDes.getText() + " or b.co_usrrem = " + txtCodUsrRem.getText() +")";
                                        }
                                    }else{
                                        strUsrDesFil = " and a.co_usrdes = " + txtCodUsrDes.getText();
                                        strUsrDesFil1 = " and b.co_usrdes = " + txtCodUsrDes.getText();
                                    }
                                }
                               
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                strCamAudRpt = "" + objParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v1.0    ";

                                strSql="";
                                strSql+="select distinct a.co_tipnov, a.tx_deslar from tbm_tipnovrechum a ";
                                strSql+="inner join tbm_novrechum b on (b.co_tipnov=a.co_tipnov)";
                                strSql+="where a.st_reg = 'A' ";
                                strSql+= strFilFec1 + " ";
                                strSql+= strAux1 + " ";
                                strSql+=strCoTipNov + " ";
                                strSql+=strNeNumReeFil1 + " ";
                                strSql+=strUsrReeFil1 + " ";
                                strSql+=strUsrDesFil1 + " ";
                                strSql+="order by a.co_tipnov";

                                String strSqlDet=" select a.co_nov, a.fe_ing, b.tx_usr as tx_usrrem, c.tx_usr as tx_usrdes, a.tx_asu, a.tx_men from tbm_novrechum a ";
                                strSqlDet+="inner join tbm_usr b on (b.co_usr=a.co_usrrem) ";
                                strSqlDet+="inner join tbm_usr c on (c.co_usr=a.co_usrdes) ";
                                strSqlDet+="where co_tipnov=$P{co_tipnov} ";
                                strSqlDet+=strFilFec + " ";
                                strSqlDet+=strAux + " ";
                                strSqlDet+=strCoTipNov + " ";
                                strSqlDet+=strNeNumReeFil + " ";
                                strSqlDet+=strUsrReeFil + " ";
                                strSqlDet+=strUsrDesFil + " ";
                                strSqlDet+="order by a.co_nov";
                                
                                System.out.println("vista reporte zafrechum58:" +strSql);
                                
                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("strsql", strSql);
                                mapPar.put("strsqldet", strSqlDet);
                                mapPar.put("empresa", objParSis.getNombreEmpresa());
                                mapPar.put("fecha", datFecAux);
                                mapPar.put("strCamAudRpt", strCamAudRpt);
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                mapPar.put("strfilfec", strFilFec);
                                //mapPar.put("prueba",vecDataCon);
                                /*java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("coemp", new Integer(objZafParSis.getCodigoEmpresa()) );
                                mapPar.put("strAux",  sqlAux );
                                mapPar.put("nomusr", strNomUsr );
                                mapPar.put("fecimp", strFecHorSer );
                                mapPar.put("SUBREPORT_DIR", strRutRpt );
                                mapPar.put("strCamAudRpt", "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                 */
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        // TODO add your handling code here:

        cargarRepote(1);

        //         java.sql.Connection conIns;
        //         String DIRECCION_REPORTE="C:/zafiro/reportes_impresos/ZafRecHum09/ZafRecHum09.jrxml";
        //         String DIRECCION_REPORTE_DETALLE="C:/zafiro/reportes_impresos/ZafRecHum09/ZafCxC59_01.jrxml";
        //         String sqlAux="";
        //         String strNomUsr="";
        //         String strFecHorSer="";
        //         try{
            //             conIns =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            //            if(conIns!=null){
                //
                //                if(System.getProperty("os.name").equals("Linux")){
                    //                   DIRECCION_REPORTE="//zafiro/reportes_impresos/ZafRecHum09/ZafRecHum09.jrxml";
                    //                   DIRECCION_REPORTE_DETALLE="//zafiro/reportes_impresos/ZafRecHum09/ZafCxC59_01.jrxml";
                    //                }
                //
                //                JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECCION_REPORTE);
                //                JasperDesign jasperDesign2 = JasperManager.loadXmlDesign(DIRECCION_REPORTE_DETALLE);
                //
                //                JasperReport jasperReport2 = JasperManager.compileReport(jasperDesign);
                //                JasperReport subReport = JasperManager.compileReport(jasperDesign2);
                //
                //
                //                 switch (objSelFec.getTipoSeleccion())
                //                 {
                    //                    case 0: //Búsqueda por rangos
                    //                        sqlAux+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    //                        break;
                    //                    case 1: //Fechas menores o iguales que "Hasta".
                    //                        sqlAux+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    //                        break;
                    //                    case 2: //Fechas mayores o iguales que "Desde".
                    //                        sqlAux+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    //                        break;
                    //                    case 3: //Todo.
                    //                        break;
                    //                 }
                //
                //                  if(!(txtCodLoc.getText().equals(""))){
                    //                      sqlAux+=" AND a.co_loc="+txtCodLoc.getText()+" ";
                    //                  }else{
                    //                     sqlAux+=" AND a.co_loc in ( "+strSqlLoc+" ) ";
                    //                  }
                //
                //                  if(!(txtCodTipDoc.getText().equals(""))){
                    //                      sqlAux+=" AND a.co_tipdoc="+txtCodTipDoc.getText()+" ";
                    //                  }
                //
                //
                //                 if(!(txtCodUsr.getText().equals(""))){
                    //                     sqlAux+=" AND a.co_usring="+txtCodUsr.getText()+" ";
                    //                 }
                //
                //
                //                 //Obtener la fecha y hora del servidor.
                //                    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                //                    if (datFecAux!=null){
                    //                      strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                    //                      datFecAux=null;
                    //                    }
                //                    strNomUsr=objZafParSis.getNombreUsuario();
                //
                //                   Map parameters = new HashMap();
                //
                //                   parameters.put("coemp", ""+objZafParSis.getCodigoEmpresa() );
                //                   parameters.put("strAux",  sqlAux );
                //                   parameters.put("nomusr", strNomUsr );
                //                   parameters.put("fecimp", strFecHorSer );
                //                   parameters.put("SUBREPORT", subReport);
                //
                //                  JasperPrint report = JasperFillManager.fillReport(jasperReport2, parameters, conIns);
                //                  JasperViewer.viewReport(report, false);
                //
                //                  conIns.close();
                //                  conIns=null;
                //
                //        }}catch (JRException e){  System.out.println("Excepción: " + e.toString()); }
        //          catch(java.sql.SQLException ex) { System.out.println("Error al conectarse a la base"); }
    }//GEN-LAST:event_butVisPreActionPerformed

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        // TODO add your handling code here:

        cargarRepote(0);

        //         java.sql.Connection conIns;
        //         String DIRECCION_REPORTE="C:/zafiro/reportes_impresos/ZafRecHum09/ZafRecHum09.jrxml";
        //         String DIRECCION_REPORTE_DETALLE="C:/zafiro/reportes_impresos/ZafRecHum09/ZafCxC59_01.jrxml";
        //         String sqlAux="";
        //         String strNomUsr="";
        //         String strFecHorSer="";
        //         try{
            //             conIns =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            //            if(conIns!=null){
                //
                //                if(System.getProperty("os.name").equals("Linux")){
                    //                   DIRECCION_REPORTE="//zafiro/reportes_impresos/ZafRecHum09/ZafRecHum09.jrxml";
                    //                   DIRECCION_REPORTE_DETALLE="//zafiro/reportes_impresos/ZafRecHum09/ZafCxC59_01.jrxml";
                    //                }
                //
                //                JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECCION_REPORTE);
                //                JasperDesign jasperDesign2 = JasperManager.loadXmlDesign(DIRECCION_REPORTE_DETALLE);
                //
                //                JasperReport jasperReport2 = JasperManager.compileReport(jasperDesign);
                //                JasperReport subReport = JasperManager.compileReport(jasperDesign2);
                //
                //
                //                 switch (objSelFec.getTipoSeleccion())
                //                 {
                    //                    case 0: //Búsqueda por rangos
                    //                        sqlAux+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    //                        break;
                    //                    case 1: //Fechas menores o iguales que "Hasta".
                    //                        sqlAux+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    //                        break;
                    //                    case 2: //Fechas mayores o iguales que "Desde".
                    //                        sqlAux+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    //                        break;
                    //                    case 3: //Todo.
                    //                        break;
                    //                 }
                //
                //                  if(!(txtCodLoc.getText().equals(""))){
                    //                      sqlAux+=" AND a.co_loc="+txtCodLoc.getText()+" ";
                    //                  }else {
                    //                     sqlAux+=" AND a.co_loc in ( "+strSqlLoc+" ) ";
                    //                  }
                //
                //                  if(!(txtCodTipDoc.getText().equals(""))){
                    //                      sqlAux+=" AND a.co_tipdoc="+txtCodTipDoc.getText()+" ";
                    //                  }
                //
                //
                //                 if(!(txtCodUsr.getText().equals(""))){
                    //                     sqlAux+=" AND a.co_usring="+txtCodUsr.getText()+" ";
                    //                 }
                //
                //
                //                   //Obtener la fecha y hora del servidor.
                //                    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                //                    if (datFecAux!=null){
                    //                      strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                    //                      datFecAux=null;
                    //                    }
                //                    strNomUsr=objZafParSis.getNombreUsuario();
                //
                //                   Map parameters = new HashMap();
                //
                //                   parameters.put("coemp", ""+objZafParSis.getCodigoEmpresa() );
                //                   parameters.put("strAux",  sqlAux );
                //                   parameters.put("nomusr", strNomUsr );
                //                   parameters.put("fecimp", strFecHorSer );
                //                   parameters.put("SUBREPORT", subReport);
                //
                //                  JasperPrint report = JasperFillManager.fillReport(jasperReport2, parameters, conIns);
                //                  JasperManager.printReport(report,false);
                //
                //                  conIns.close();
                //                  conIns=null;
                //
                //        }}catch (JRException e){  System.out.println("Excepción: " + e.toString()); }
        //          catch(java.sql.SQLException ex) { System.out.println("Error al conectarse a la base"); }
    }//GEN-LAST:event_butImpActionPerformed

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butTipNov;
    private javax.swing.JButton butUsrDes;
    private javax.swing.JButton butUsrRem;
    private javax.swing.JButton butVisPre;
    private javax.swing.JCheckBox jChkNovAnl;
    private javax.swing.JCheckBox jChkNovPen;
    private javax.swing.JCheckBox jChkNovTer;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton jRBAnd;
    private javax.swing.JRadioButton jRBOr;
    private javax.swing.JLabel jlblDes;
    private javax.swing.JLabel jlblRee;
    private javax.swing.JLabel jlblRem;
    private javax.swing.JLabel jlblTip;
    private javax.swing.JSpinner jspNumRee;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodTipNov;
    private javax.swing.JTextField txtCodUsrDes;
    private javax.swing.JTextField txtCodUsrRem;
    private javax.swing.JTextField txtDesLarUsrDes;
    private javax.swing.JTextField txtDesLarUsrRem;
    private javax.swing.JTextField txtDesTipNov;
    private javax.swing.JTextField txtNovPen;
    private javax.swing.JTextField txtUsrDes;
    private javax.swing.JTextField txtUsrRem;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {

            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            if(strAux==null){
                this.setTitle("Seguimiento de novedades de recursos humanos… " + strVersion);
            }else{
                this.setTitle(strAux + " " +strVersion);
            }
            
            lblTit.setText(strAux);
            //Configurar las ZafVenCon.
            configurarVenConUsrRem();
            configurarVenConUsrDes();
            configurarVenConTipNovRecHum();

            //Configurar los JTables.
            configuraTbl();
            agregarColTblDat();
            
            
             //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setTitulo("Rango de fechas");
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setCheckBoxChecked(false);
            panCab.add(objSelFec);
            objSelFec.setBounds(4, 20, 472, 72);

            //*****************************************************************************
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
//            txtFecDoc.setHoy();
            
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            
            Librerias.ZafDate.ZafDatePicker dtePckPag =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
            int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if(fecDoc!=null){
                objFec.set(java.util.Calendar.DAY_OF_MONTH, 01);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
//            java.util.Calendar cal=java.util.Calendar.getInstance();
            
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
            dtePckPag.setDia(01);
            String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
            objSelFec.setFechaDesde(objUti.formatearFecha(fe1, "dd/MM/yyyy") );
            
            objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
            dtePckPag.setDia(objFecPagActual.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
            fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
            fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
            objSelFec.setFechaHasta(objUti.formatearFecha(fe1, "dd/MM/yyyy") );

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configuraTbl(){
        
        boolean blnRes=false;
        
        try{
 
        //Configurar JTable: Establecer el modelo.
        vecCab = new Vector();
        vecCab.clear();
        vecCab.add(INT_TBL_DAT_LIN,"");
        vecCab.add(INT_TBL_DAT_COD_NOV,"Código");
        vecCab.add(INT_TBL_DAT_FEC_NOV,"Fecha");
        vecCab.add(INT_TBL_DAT_COD_REM,"Cód.Rem.");
        vecCab.add(INT_TBL_DAT_REM,"Remitente");
        vecCab.add(INT_TBL_DAT_COD_DES,"Cód.Des.");
        vecCab.add(INT_TBL_DAT_DES,"Destinatario");
        vecCab.add(INT_TBL_DAT_DES_TIP_NOV,"Tip.Nov.");
        vecCab.add(INT_TBL_DAT_ASU,"Asunto");
        vecCab.add(INT_TBL_DAT_MSJ,"Mensaje");
        vecCab.add(INT_TBL_DAT_BUT_NOV,"");
        vecCab.add(INT_TBL_DAT_CHK_LEI,"Leída");
        vecCab.add(INT_TBL_DAT_FEC_LEI,"Fecha");
        vecCab.add(INT_TBL_DAT_NUM_REEN,"Núm.Ree.");
        vecCab.add(INT_TBL_DAT_COD_DES_REEN,"Cód.Des.");
        vecCab.add(INT_TBL_DAT_DES_REEN,"Destinatario");
        vecCab.add(INT_TBL_DAT_BUT_DES_REEN,"");
        vecCab.add(INT_TBL_DAT_BUT_HIS_REEN,"His.Ree.");
        vecCab.add(INT_TBL_DAT_CHK_PEN,"Pendiente");
        vecCab.add(INT_TBL_DAT_CHK_ANL,"Anulada");
        vecCab.add(INT_TBL_DAT_CHK_TER,"Terminada");
        vecCab.add(INT_TBL_DAT_FEC_ANL_TER,"Fecha");
        vecCab.add(INT_TBL_DAT_OBS_NOV_FIN,"Observación");
        vecCab.add(INT_TBL_DAT_BUT_OBS_NOV_FIN,"");
        

        objTblMod=new ZafTblMod();
        objTblMod.setHeader(vecCab);
        
        //Configurar JTable: Establecer el modelo de la tabla.
        tblDat.setModel(objTblMod);
        
        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
        
        //Configurar JTable: Establecer el msenú de contexto.
        objTblPopMnu=new ZafTblPopMnu(tblDat);
        
        //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        
        //Configurar JTable: Establecer el ancho de las columnas.
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_DAT_COD_NOV).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DAT_FEC_NOV).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DAT_COD_REM).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_DAT_REM).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DAT_COD_DES).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_DAT_DES).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DAT_DES_TIP_NOV).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_DAT_ASU).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_DAT_MSJ).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_DAT_BUT_NOV).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_DAT_CHK_LEI).setPreferredWidth(40);
        tcmAux.getColumn(INT_TBL_DAT_FEC_LEI).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DAT_NUM_REEN).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_DAT_COD_DES_REEN).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_DAT_DES_REEN).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DAT_BUT_DES_REEN).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_DAT_BUT_HIS_REEN).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DAT_CHK_PEN).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DAT_CHK_ANL).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DAT_CHK_TER).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DAT_FEC_ANL_TER).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DAT_FEC_ANL_TER).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DAT_BUT_OBS_NOV_FIN).setPreferredWidth(20);
        
        
        //Configurar JTable: Ocultar columnas del sistema.
        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REM, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DES, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DES_REEN, tblDat);
        
        //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
        tblDat.getTableHeader().setReorderingAllowed(false);

        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        objMouMotAda=new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

        //Configurar JTable: Establecer columnas editables.
        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_DAT_BUT_NOV);
        vecAux.add("" + INT_TBL_DAT_BUT_DES_REEN);
        vecAux.add("" + INT_TBL_DAT_BUT_HIS_REEN);
        vecAux.add("" + INT_TBL_DAT_CHK_ANL);
        vecAux.add("" + INT_TBL_DAT_CHK_TER);
        vecAux.add("" + INT_TBL_DAT_BUT_OBS_NOV_FIN);
        objTblMod.addColumnasEditables(vecAux);
        vecAux=null;

        //Configurar JTable: Editor de la tabla.
        objTblEdi=new ZafTblEdi(tblDat);
        
        zafTblCelRenChkLab = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_DAT_CHK_LEI).setCellRenderer(zafTblCelRenChkLab);
        
        zafTblCelEdiChkLab = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_DAT_CHK_LEI).setCellEditor(zafTblCelEdiChkLab);
        
        zafTblCelRenChkLab = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_DAT_CHK_PEN).setCellRenderer(zafTblCelRenChkLab);
        zafTblCelEdiChkLab = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_DAT_CHK_PEN).setCellEditor(zafTblCelEdiChkLab);
        
        //Configurar JTable: Renderizar celdas.
            zafTblCelRenChkAnl=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_ANL).setCellRenderer(zafTblCelRenChkAnl);
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkAnl=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_ANL).setCellEditor(objTblCelEdiChkAnl);
            objTblCelEdiChkAnl.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strLin="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    strLin=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN).toString();

                        if(strLin.equals("S")){
                            objTblCelEdiChkAnl.setCancelarEdicion(true);
                            objTblCelEdiChkTer.setCancelarEdicion(true);
                        }
                        else{
                            tblDat.setValueAt(Boolean.FALSE, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_PEN);
                            tblDat.setValueAt(Boolean.FALSE, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_TER);
                        }
                }
            });
            

            zafTblCelRenChkTer=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_TER).setCellRenderer(zafTblCelRenChkTer);
            objTblCelEdiChkTer=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_TER).setCellEditor(objTblCelEdiChkTer);
            objTblCelEdiChkTer.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strLin="";
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                        strLin=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN).toString();
                        if(strLin.equals("S")){
                            objTblCelEdiChkAnl.setCancelarEdicion(true);
                            objTblCelEdiChkTer.setCancelarEdicion(true);

                        }
                        else{
                             tblDat.setValueAt(Boolean.FALSE, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_PEN);
                                tblDat.setValueAt(Boolean.FALSE, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_ANL);
                        }
                }
            });
            
        Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_NOV).setCellRenderer(zafTblDocCelRenBut);
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_NOV).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                          case INT_TBL_DAT_BUT_NOV:

                         break;
                       }
                     }
                    }
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {

                         case INT_TBL_DAT_BUT_NOV:

                             llamarPantNov( String.valueOf(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_NOV).toString()));
                             String strSql="";
                             java.sql.Connection conn;
                             java.sql.Statement stmLoc, stmAux;
                             java.sql.ResultSet rstLoc;
                             boolean blnRes=false;
                             try {
                                conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                                if(conn!=null){
                                    conn.setAutoCommit(false);
                                    stmLoc=conn.createStatement();
                                    stmAux=conn.createStatement();

                                        strSql="";
                                        strSql+="select co_usrdes from tbm_novrechum where co_nov = " + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_NOV).toString();
                                        System.out.println("strsql: " + strSql);
                                        rstLoc=stmAux.executeQuery(strSql);
                                        if(rstLoc.next()){
                                            String strCoUsrDes=rstLoc.getString("co_usrdes");
                                            if(Integer.valueOf(strCoUsrDes)==objParSis.getCodigoUsuario()){
                                                strSql="";
                                                strSql+="update tbm_novrechum set st_lec = 'S', fe_lec = current_timestamp where co_nov = " + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_NOV).toString();
                                                strSql+=" and co_usrdes = " + objParSis.getCodigoUsuario();
                                                strSql+=" and st_lec is null";
                                                System.out.println("updateTbm_novrechum : " + strSql);
                                                stmLoc.executeUpdate(strSql);
                                                blnRes=true;
                                                if(blnRes){
                                                    conn.commit();
                                                    if (objThrGUI==null) {
                                                        objThrGUI=new ZafThreadGUI();
                                                        objThrGUI.start();
                                                    }
                                                }else{
                                                    conn.rollback();
                                                }
                                                
                                                
                                                
                                                
                                            }else{

                                            }
                                        }
//                                    }
                                    stmLoc.close();
                                    stmLoc=null;
                                    conn.close();
                                    conn=null;
                                }
                            } catch(java.sql.SQLException Evt) { Evt.printStackTrace();objUti.mostrarMsgErr_F1(ZafRecHum58.this, Evt);  }
                             catch(Exception Evt) {  Evt.printStackTrace(); objUti.mostrarMsgErr_F1(ZafRecHum58.this, Evt);  }
                             break;
                        }
                }}
           });
            
        objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_DES_REEN).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
        
        objRecHum58_01=new ZafRecHum58_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButDlg=new ZafTblCelEdiButDlg(objRecHum58_01);
            tcmAux.getColumn(INT_TBL_DAT_BUT_DES_REEN).setCellEditor(objTblCelEdiButDlg);
            objTblCelEdiButDlg.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                int intCol;
                
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intCol = tblDat.getSelectedRow();
                    if (intFilSel!=-1)
                    {

                        intPosAct=intFilSel;
                            
                        objRecHum58_01.setParDlg(objParSis,ZafRecHum58.this, Integer.valueOf(tblDat.getValueAt(intCol, INT_TBL_DAT_COD_NOV).toString()));
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            
          objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS_REEN).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
        
        objRecHum58_02=new ZafRecHum58_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButDlg=new ZafTblCelEdiButDlg(objRecHum58_02);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS_REEN).setCellEditor(objTblCelEdiButDlg);
            objTblCelEdiButDlg.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                int intCol;
                
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    if (intFilSel!=-1)
                    {

                        intPosAct=intFilSel;
                            
                        objRecHum58_02.setParDlg(objParSis,ZafRecHum58.this, tblDat.getValueAt(intFilSel, INT_TBL_DAT_COD_NOV).toString());
                        objRecHum58_02.cargarDatos();
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
        
            
        zafTblDocCelRenBut = new ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_DAT_BUT_OBS_NOV_FIN).setCellRenderer(zafTblDocCelRenBut);

        ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_DAT_BUT_OBS_NOV_FIN, "Observación") {
            public void butCLick() {
                int intSelFil = tblDat.getSelectedRow();
                String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_DAT_OBS_NOV_FIN) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_DAT_OBS_NOV_FIN).toString());
                ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum58.this), true, strObs);
                zafMae07_01.show();
                if (zafMae07_01.getAceptar()) {
                    tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_DAT_OBS_NOV_FIN);
                }
            }
        };

       //Configurar JTable: Editor de búsqueda.
       objTblBus=new ZafTblBus(tblDat);
             
       objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
       blnRes=true;
                
        }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
           
        return blnRes;
    }
    
    private void llamarPantNov(String strCodNov ){
        RecursosHumanos.ZafRecHum57.ZafRecHum57 obj1 = new  RecursosHumanos.ZafRecHum57.ZafRecHum57(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), 3465, strCodNov);
       this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
       obj1.show();
    }

    private boolean agregarColTblDat()
    {

        int i, intNumFil, intNumColTblDat;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpEmp=null;
        java.awt.Color colFonCol;
        boolean blnRes=true;

        try
        {

            intNumFil=objTblMod.getRowCountTrue();
            intNumColTblDat=objTblMod.getColumnCount();

            for (i=0; i<1; i++)
            {
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Novedades");
                objTblHeaColGrpEmp.setHeight(16);
//                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_NOV+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FEC_NOV+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_REM+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_REM+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_DES+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_DES+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_DES_TIP_NOV+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_ASU+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_MSJ+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_NOV+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Estado de las novedades");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_LEI+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FEC_LEI+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NUM_REEN+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_DES_REEN+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_DES_REEN+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_DES_REEN+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_HIS_REEN+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_PEN+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_ANL+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_TER+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FEC_ANL_TER+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_OBS_NOV_FIN+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_OBS_NOV_FIN+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
            }
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría ut
     * ilizar
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
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    private void setLocationRelativeTo(Object object) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    private class ZafThreadGUI extends Thread{
    public void run(){

        lblMsgSis.setText("Obteniendo datos...");
        pgrSis.setIndeterminate(true);

        if (!cargarDat())
        {
            //Inicializar objetos si no se pudo cargar los datos.
            //lblMsgSis.setText("Listo");
            pgrSis.setValue(0);
            butCon.setText("Consultar");
            consultarCantidadNovedadesPendientes();
        }

        //Establecer el foco en el JTable sólo cuando haya datos.
        if (tblDat.getRowCount()>0)
        {
            tabFrm.setSelectedIndex(1);
            tblDat.setRowSelectionInterval(0, 0);
            tblDat.requestFocus();
            butImp.setEnabled(true);
            butVisPre.setEnabled(true);
        }else{
            butImp.setEnabled(false);
            butVisPre.setEnabled(false);
            txtNovPen.setText("");
        }
        
        objThrGUI=null;
    }
}
    
    /**
 * Se encarga de cargar la informacion en la tabla
 * @return  true si esta correcto y false  si hay algun error
 */
private boolean cargarDat(){
  boolean blnRes=false;
  java.sql.Connection conn=null;
  java.sql.Statement stmLoc=null;
  java.sql.Statement stmLocAux=null;
  java.sql.ResultSet rstLoc = null;
  java.sql.ResultSet rstLocAux = null;
//  String strSql="";
  String strFilFec="";
  String sqlFil = "";
  String strAux="";
  String strEstConf="";
  Vector vecDataCon;
  Vector vecData;
  Vector vecFecCon;
  try{
//   
      conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
//          
      if(conn!=null){
//          
//          
          stmLoc=conn.createStatement();
          stmLocAux=conn.createStatement();
          vecDataCon = new Vector();
          vecData = new Vector();
          vecFecCon = new Vector();
          

            switch (objSelFec.getTipoSeleccion()){
                case 0: //Búsqueda por rangos
                    strFilFec+=" WHERE date(a.fe_ing) BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    strFilFec+=" WHERE date(a.fe_ing)<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    strFilFec+=" WHERE date(a.fe_ing)>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                    break;
                case 3: //Todo.
                    break;
            }

          strSql="";
          strSql+="select a.*, b.tx_usr as tx_usrrem, c.tx_usr as tx_usrdes, d.tx_usr as tx_usrdesree ,e.tx_deslar, e.co_tipnov FROM tbm_novrechum AS a";
          strSql+=" inner join tbm_usr b on (b.co_usr=a.co_usrrem)";
          strSql+=" inner join tbm_usr c on (c.co_usr=a.co_usrdes)";
          strSql+=" inner join tbm_tipnovrechum e on (e.co_tipnov=a.co_tipnov)";
          strSql+=" left join tbm_usr d on (d.co_usr=a.co_usrdesree)";          

          
          if(optFil.isSelected()){
                    
                    if(jChkNovPen.isSelected()) { if(strEstConf.equals("")) strEstConf+="'P'"; else strEstConf+=",'P'"; }
                    if(jChkNovAnl.isSelected()) { if(strEstConf.equals("")) strEstConf+="'I'"; else strEstConf+=",'I'"; }
                    if(jChkNovTer.isSelected()) { if(strEstConf.equals("")) strEstConf+="'T'"; else strEstConf+=",'T'"; }

                    if(!strEstConf.equals(""))
                        strAux+=" AND a.st_reg IN ("+strEstConf+") ";
          }
          
          objPerUsr=new ZafPerUsr(objParSis);
          
          if(!blnMnuIni){
              
          
          
              if(!objPerUsr.isOpcionEnabled(3483)){
                  vcoUsrDes=null;
                  vcoUsrRem=null;
                  configurarVenConUsrRem();
                  configurarVenConUsrDes();
                  if(jRBOr.isSelected()){
                      sqlFil+= " and (a.co_usrrem  = " + txtCodUsrRem.getText() + " or  (a.co_usrdes  = " + txtCodUsrDes.getText() +  " and a.co_usrdesree is null)) ";
                      sqlFil+= " or (co_usrdesree is null or co_usrdesree ="+txtCodUsrDes.getText()+") ";
                  }else{
                      sqlFil+= " and (a.co_usrrem  = " + txtCodUsrRem.getText() + " and  a.co_usrdes  = " + txtCodUsrDes.getText() + ") ";
                  }
              }else{
                  vcoUsrDes=null;
                  vcoUsrRem=null;
                  configurarVenConUsrRem();
                  configurarVenConUsrDes();
                  if(txtUsrRem.getText().compareTo("")!=0){
                    sqlFil+= " and a.co_usrrem  = " + txtCodUsrRem.getText() + " ";
                    }
                  if(txtUsrDes.getText().compareTo("")!=0){
                      sqlFil+= " and a.co_usrdes  = " + txtCodUsrDes.getText() + " ";
                  }
              }

              if(sqlFil.compareTo("")==0){

              }else{
                  if(objParSis.getCodigoUsuario()!=1){
                      sqlFil+="and (co_usrdesree ="+objParSis.getCodigoUsuario()+")";
                  }
              }


              if(txtCodTipNov.getText().compareTo("")!=0){
                  sqlFil+= " and a.co_tipnov  = " + txtCodTipNov.getText() + " ";
              }

              if(optFil.isSelected()){
                  if(Integer.valueOf(jspNumRee.getValue().toString())==0){
                      
                  }else{
                      sqlFil+= " and a.ne_numree  = " + jspNumRee.getValue().toString() + " ";
                    
                  }
                }
          }else{
              
              String strSql1="";
              strSql1+="select * from tbr_perusr where co_emp="+objParSis.getCodigoEmpresa()+" and co_usr= " +objParSis.getCodigoUsuario()+" and co_mnu=3483";
              rstLoc=stmLoc.executeQuery(strSql1);
              boolean blnTiePer=false;
              
              if(rstLoc.next()){
                  blnTiePer=false;
              }else{
                  blnTiePer=true;
                }
              
              txtCodUsrRem.setText("");
              txtUsrRem.setText("");
              txtDesLarUsrRem.setText("");
              if(!blnTiePer){
//                  vcoUsrDes=null;
//                  vcoUsrRem=null;
//                  configurarVenConUsrRem();
//                  configurarVenConUsrDes();
//                  if(jRBOr.isSelected()){
//                      sqlFil+= " and (a.co_usrrem  = " + txtCodUsrRem.getText() + " or  (a.co_usrdes  = " + txtCodUsrDes.getText() +  " and a.co_usrdesree is null)) ";
//                      sqlFil+= " or (co_usrdesree is null or co_usrdesree ="+txtCodUsrDes.getText()+") ";
//                  }else{
//                      sqlFil+= " and (a.co_usrrem  = " + txtCodUsrRem.getText() + " and  a.co_usrdes  = " + txtCodUsrDes.getText() + ") ";
//                  }
              }else{
//                  vcoUsrDes=null;
//                  vcoUsrRem=null;
//                  configurarVenConUsrRem();**
//                  configurarVenConUsrDes();
                  
                  
              }
              
              if(txtUsrRem.getText().compareTo("")!=0){
                      sqlFil+= " and a.co_usrrem  = " + txtCodUsrRem.getText() + " ";
                  }
              if(txtUsrDes.getText().length()>0){
                  sqlFil+= " and (a.co_usrdes  = " + txtCodUsrDes.getText() + " ";
              }
              
              if(sqlFil.compareTo("")==0){

              }else{
                  if(objParSis.getCodigoUsuario()!=1){
                      sqlFil+="or (co_usrdesree ="+objParSis.getCodigoUsuario()+"))";
                  }
              }


              if(txtCodTipNov.getText().compareTo("")!=0){
                  sqlFil+= " and a.co_tipnov  = " + txtCodTipNov.getText() + " ";
              }

              if(optFil.isSelected()){
                  if(Integer.valueOf(jspNumRee.getValue().toString())==0){

                  }else{
                      sqlFil+= " and a.ne_numree  = " + jspNumRee.getValue().toString() + " ";
                  }
              }
          }

          strSql+=" " + strFilFec +" " + strAux + " " + sqlFil + " order by a.co_nov";
          
          System.out.println("q consul: " + strSql);
          rstLoc=stmLoc.executeQuery(strSql);
          vecDat = new Vector();
        
          while(rstLoc.next()){
              
              java.util.Vector vecReg = new java.util.Vector();
              String strStReg=rstLoc.getString("st_reg");
              
              if(strStReg.compareTo("I")==0 || strStReg.compareTo("T")==0) {
                  vecReg.add(INT_TBL_DAT_LIN,"S");
              }else{
                  vecReg.add(INT_TBL_DAT_LIN,"");
              }
              
              vecReg.add(INT_TBL_DAT_COD_NOV,rstLoc.getString("co_nov"));
              vecReg.add(INT_TBL_DAT_FEC_NOV,rstLoc.getString("fe_ing").substring(0,10));
              vecReg.add(INT_TBL_DAT_COD_REM,rstLoc.getString("co_usrrem"));
              vecReg.add(INT_TBL_DAT_REM,rstLoc.getString("tx_usrrem"));
              vecReg.add(INT_TBL_DAT_COD_DES,rstLoc.getString("co_usrdes"));
              vecReg.add(INT_TBL_DAT_DES,rstLoc.getString("tx_usrdes"));
              vecReg.add(INT_TBL_DAT_DES_TIP_NOV,rstLoc.getString("tx_deslar"));
              vecReg.add(INT_TBL_DAT_ASU,rstLoc.getString("tx_asu"));
              vecReg.add(INT_TBL_DAT_MSJ,rstLoc.getString("tx_men"));
              vecReg.add(INT_TBL_DAT_BUT_NOV,"...");
              String strStLec=rstLoc.getString("st_lec");
              if(strStLec==null){
                  vecReg.add(INT_TBL_DAT_CHK_LEI,Boolean.FALSE);
              }else{
                  vecReg.add(INT_TBL_DAT_CHK_LEI,Boolean.TRUE);
              }
              
              vecReg.add(INT_TBL_DAT_FEC_LEI,rstLoc.getString("fe_lec"));
              vecReg.add(INT_TBL_DAT_NUM_REEN,rstLoc.getString("ne_numree"));
              vecReg.add(INT_TBL_DAT_COD_DES_REEN,rstLoc.getString("co_usrdesree"));
              vecReg.add(INT_TBL_DAT_DES_REEN,rstLoc.getString("tx_usrdesree"));
              vecReg.add(INT_TBL_DAT_BUT_DES_REEN,"...");
              vecReg.add(INT_TBL_DAT_BUT_HIS_REEN,"...");
              
              
              if(strStReg.compareTo("P")==0){
                  vecReg.add(INT_TBL_DAT_CHK_PEN,Boolean.TRUE);
                  vecReg.add(INT_TBL_DAT_CHK_ANL,Boolean.FALSE);
                  vecReg.add(INT_TBL_DAT_CHK_TER,Boolean.FALSE);
              }else if(strStReg.compareTo("I")==0){
                  vecReg.add(INT_TBL_DAT_CHK_PEN,Boolean.FALSE);
                  vecReg.add(INT_TBL_DAT_CHK_ANL,Boolean.TRUE);
                  vecReg.add(INT_TBL_DAT_CHK_TER,Boolean.FALSE);
              }else{
                  vecReg.add(INT_TBL_DAT_CHK_PEN,Boolean.FALSE);
                  vecReg.add(INT_TBL_DAT_CHK_ANL,Boolean.FALSE);
                  vecReg.add(INT_TBL_DAT_CHK_TER,Boolean.TRUE);
              }
              vecReg.add(INT_TBL_DAT_FEC_ANL_TER,rstLoc.getString("fe_fin"));
              vecReg.add(INT_TBL_DAT_OBS_NOV_FIN,rstLoc.getString("tx_obsfin"));
              vecReg.add(INT_TBL_DAT_BUT_OBS_NOV_FIN,"...");
              
              vecDataCon.add(vecReg);
            }

            objTblMod.setData(vecDataCon);
            tblDat .setModel(objTblMod);   
            

          if(vecDataCon.size()==0){
              mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
              lblMsgSis.setText("Listo");
          }else{
              lblMsgSis.setText("Listo");
              //Asignar vectores al modelo.
              //objTblMod.setData(vecDat);
              //tblDat.setModel(objTblMod);
              tabFrm.setSelectedIndex(1);
              lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
          }
                
          pgrSis.setIndeterminate(false);
          vecDat.clear();
      }
  }catch(java.sql.SQLException Evt) { blnRes=false;  Evt.printStackTrace();objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  Evt.printStackTrace(); objUti.mostrarMsgErr_F1(this, Evt);  }
  finally {
        try{rstLoc.close();}catch(Throwable ignore){}
        try{rstLocAux.close();}catch(Throwable ignore){}
        try{stmLoc.close();}catch(Throwable ignore){}
        try{stmLocAux.close();}catch(Throwable ignore){}
        try{conn.close();}catch(Throwable ignore){}
    }
    System.gc();
    return blnRes;
}

private boolean consultarCantidadNovedadesPendientes(){
    boolean blnRes=false;
  java.sql.Connection conn=null;
  java.sql.Statement stmLoc=null;
  java.sql.ResultSet rstLoc = null;
  String strSql="";
  try{
      conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
      if(conn!=null){
          stmLoc=conn.createStatement();
          strSql="";
          Calendar cal = Calendar.getInstance();
          strSql+="select count(st_reg) as ne_numpen FROM tbm_novrechum AS a";
          strSql+=" WHERE EXTRACT(MONTH FROM FE_ING)<"+(cal.get(Calendar.MONTH)+1);
          strSql+=" AND EXTRACT(YEAR FROM FE_ING)<="+cal.get(Calendar.YEAR);
          strSql+=" AND st_reg = 'P'";
          
          if(!blnMnuIni){
              if(!objPerUsr.isOpcionEnabled(3483)){
                vcoUsrDes=null;
                vcoUsrRem=null;
                configurarVenConUsrRem();
                configurarVenConUsrDes();
 
                if(jRBOr.isSelected()){
                    strSql+= " and (a.co_usrrem  = " + txtCodUsrRem.getText() + " or  a.co_usrdes  = " + txtCodUsrDes.getText() + ") ";
                }else{
                    strSql+= " and (a.co_usrrem  = " + txtCodUsrRem.getText() + " and  a.co_usrdes  = " + txtCodUsrDes.getText() + ") ";
                }
            }else{
                vcoUsrDes=null;
                vcoUsrRem=null;
                configurarVenConUsrRem();
                configurarVenConUsrDes();
                if(txtUsrRem.getText().compareTo("")!=0){
                    strSql+= " and a.co_usrrem  = " + txtCodUsrRem.getText() + " ";
                }
                if(txtUsrDes.getText().compareTo("")!=0){
                    strSql+= " and a.co_usrdes  = " + txtCodUsrDes.getText() + " ";
                }
            }
          }
          
          
          System.out.println("q consul: " + strSql);
          rstLoc=stmLoc.executeQuery(strSql);
          
          if(rstLoc.next()){
              txtNovPen.setText(""+rstLoc.getString("ne_numpen"));
          }
      }
  }catch(java.sql.SQLException Evt) { blnRes=false;  Evt.printStackTrace();objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  Evt.printStackTrace(); objUti.mostrarMsgErr_F1(this, Evt);  }
  finally {
        try{rstLoc.close();}catch(Throwable ignore){}
        try{stmLoc.close();}catch(Throwable ignore){}
        try{conn.close();}catch(Throwable ignore){}
    }
    System.gc();
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
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_NOV:
                    strMsg="Código de la novedad";
                    break;
                case INT_TBL_DAT_FEC_NOV:
                    strMsg="Fecha de ingreso de la novedad";
                    break;    
                case INT_TBL_DAT_COD_REM:
                    strMsg="Código del remitente";
                    break;    
                case INT_TBL_DAT_REM:
                    strMsg="Remitente";
                    break;
                case INT_TBL_DAT_COD_DES:
                    strMsg="Código del destinatario";
                    break;
                case INT_TBL_DAT_DES:
                    strMsg="Destinatario";
                    break;
                case INT_TBL_DAT_ASU:
                    strMsg="Asunto";
                    break;
                case INT_TBL_DAT_DES_TIP_NOV:
                    strMsg="Tipo de novedad";
                    break;
                case INT_TBL_DAT_MSJ:
                    strMsg="Mensaje";
                    break;
                case INT_TBL_DAT_BUT_NOV:
                    strMsg="Muestra la Novedad";
                    break;
                case INT_TBL_DAT_CHK_LEI:
                    strMsg="";
                    break;
                case INT_TBL_DAT_FEC_LEI:
                    strMsg="Fecha de lectura de la novedad";
                    break;
                case INT_TBL_DAT_NUM_REEN:
                    strMsg="Número de reenvios";
                    break;
                case INT_TBL_DAT_COD_DES_REEN:
                    strMsg="Código del destinatario";
                    break;
                case INT_TBL_DAT_DES_REEN:
                    strMsg="Destinatario";
                    break;
                case INT_TBL_DAT_BUT_DES_REEN:
                    strMsg="Muestra el \"Destinatario\"";
                    break;
                case INT_TBL_DAT_BUT_HIS_REEN:
                    strMsg="Muestra el \"Historial de reenvios\"";
                    break;
                case INT_TBL_DAT_CHK_PEN:
                    strMsg="Novedad Pendiente";
                    break;
                case INT_TBL_DAT_CHK_ANL:
                    strMsg="Novedad anulada";
                    break;
                case INT_TBL_DAT_CHK_TER:
                    strMsg="Novedad terminada";
                    break;
                case INT_TBL_DAT_FEC_ANL_TER:
                        strMsg="Fecha en la que se anuló/terminó la novedad";
                    break;
                case INT_TBL_DAT_OBS_NOV_FIN:
                        strMsg="Observación de la novedad";
                    break;
                case INT_TBL_DAT_BUT_OBS_NOV_FIN:
                        strMsg="Muestra la \"Observación\"";
                    break;
                default:
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Usuarios".
     */
    private boolean configurarVenConUsrRem() {
        boolean blnRes = true;
        java.sql.ResultSet rst = null,rstAux=null;
        java.sql.Statement stm = null,stmAux=null;
        java.sql.Connection con=null;
        String strSQL="";
        String strSql="";
        try {
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
            arlAncCol.add("30");
            arlAncCol.add("80");
            arlAncCol.add("300");
            //Armar la sentencia SQL.
            strSQL = "";
            if(objParSis.getCodigoUsuario()==1){
                strSQL += "SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                strSQL += " FROM tbm_usr AS a1";
                strSQL += " WHERE a1.st_usrSis='S'";
                strSQL += " AND a1.st_reg='A'";
                strSQL += " ORDER BY a1.tx_usr";
            }else{
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement();
                    stmAux=con.createStatement();
                    
                    strSql="";
                    strSql="select * from  tbr_perUsr a ";
                    strSql+=" where co_mnu = 3483 and co_usr = " + objParSis.getCodigoUsuario();
                    System.out.println("sqlCon: " + strSql);
                    rst=stm.executeQuery(strSql);
                    System.out.println("sqlCon: " + strSql);
                        rst=stm.executeQuery(strSql);
                        if(rst.next()){

                            strSQL += "SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                            strSQL += " FROM tbm_usr AS a1";
                            strSQL += " WHERE a1.st_usrSis='S'";
                            strSQL += " AND a1.st_reg='A'";
                            strSQL += " ORDER BY a1.tx_usr";
                            txtCodUsrRem.setText("");
                            txtUsrRem.setText("");
                            txtDesLarUsrRem.setText("");
                        }else{
                            strSql="";
                            strSql="select * from tbm_usr where co_usr = " + objParSis.getCodigoUsuario();
                            rstAux=stmAux.executeQuery(strSql);
                            if(rstAux.next()){
                                txtCodUsrRem.setText(""+rstAux.getInt("co_usr"));
                                txtUsrRem.setText(""+rstAux.getString("tx_usr"));
                                txtDesLarUsrRem.setText(""+rstAux.getString("tx_nom"));

//                                txtCodUsrDes.setText(""+rstAux.getInt("co_usr"));
//                                txtUsrDes.setText(""+rstAux.getString("tx_usr"));
//                                txtDesLarUsrDes.setText(""+rstAux.getString("tx_nom"));
                            }
                            strSQL=strSql;
                        }
                }
            }
            
            vcoUsrRem = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de usuarios", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoUsrRem.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }finally {
                try{stm.close();stm=null;}catch(Throwable ignore){}
                try{stmAux.close();stmAux=null;}catch(Throwable ignore){}
                try{rst.close();rst=null;}catch(Throwable ignore){}
                try{rstAux.close();rstAux=null;}catch(Throwable ignore){}
                try{con.close();con=null;}catch(Throwable ignore){}
            }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Usuarios".
     */
    private boolean configurarVenConUsrDes() {
        boolean blnRes = true;
        java.sql.ResultSet rst = null,rstAux=null;
        java.sql.Statement stm = null,stmAux=null;
        java.sql.Connection con=null;
        String strSQL="";
        String strSql="";
        try {
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
            arlAncCol.add("30");
            arlAncCol.add("80");
            arlAncCol.add("300");
            //Armar la sentencia SQL.
            strSQL = "";
            if(objParSis.getCodigoUsuario()==1){
                strSQL += "SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                strSQL += " FROM tbm_usr AS a1";
                strSQL += " WHERE a1.st_usrSis='S'";
                strSQL += " AND a1.st_reg='A'";
                strSQL += " ORDER BY a1.tx_usr";
            }else{
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement();
                    stmAux=con.createStatement();
                    
                    strSql="";
                    strSql="select * from  tbr_perUsr a ";
                    strSql+=" where co_mnu = 3483 and co_usr = " + objParSis.getCodigoUsuario();
                    System.out.println("sqlCon: " + strSql);
                    rst=stm.executeQuery(strSql);
                        rst=stm.executeQuery(strSql);
                        if(rst.next()){
                            strSQL += "SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                            strSQL += " FROM tbm_usr AS a1";
                            strSQL += " WHERE a1.st_usrSis='S'";
                            strSQL += " AND a1.st_reg='A'";
                            strSQL += " ORDER BY a1.tx_usr";
                            txtCodUsrDes.setText("");
                            txtUsrDes.setText("");
                            txtDesLarUsrDes.setText("");
                        }else{
                            strSql="";
                            strSql="select * from tbm_usr where co_usr = " + objParSis.getCodigoUsuario();
                            rstAux=stmAux.executeQuery(strSql);
                            if(rstAux.next()){
                                txtCodUsrDes.setText(""+rstAux.getInt("co_usr"));
                                txtUsrDes.setText(""+rstAux.getString("tx_usr"));
                                txtDesLarUsrDes.setText(""+rstAux.getString("tx_nom"));
                            }
                            strSQL=strSql;
                        }
                }
                
            }
            vcoUsrDes = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de usuarios", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoUsrDes.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
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
    private boolean mostrarVenConUsr(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoUsrRem.setCampoBusqueda(1);
                    vcoUsrRem.show();
                    if (vcoUsrRem.getSelectedButton() == vcoUsrRem.INT_BUT_ACE) {
                        txtCodUsrRem.setText(vcoUsrRem.getValueAt(1));
                        txtUsrRem.setText(vcoUsrRem.getValueAt(2));
                        txtDesLarUsrRem.setText(vcoUsrRem.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoUsrRem.buscar("a1.tx_usr", txtUsrRem.getText())) {
                        txtCodUsrRem.setText(vcoUsrRem.getValueAt(1));
                        txtUsrRem.setText(vcoUsrRem.getValueAt(2));
                        txtDesLarUsrRem.setText(vcoUsrRem.getValueAt(3));
                    } else {
                        vcoUsrRem.setCampoBusqueda(1);
                        vcoUsrRem.setCriterio1(11);
                        vcoUsrRem.cargarDatos();
                        vcoUsrRem.show();
                        if (vcoUsrRem.getSelectedButton() == vcoUsrRem.INT_BUT_ACE) {
                            txtCodUsrRem.setText(vcoUsrRem.getValueAt(1));
                            txtUsrRem.setText(vcoUsrRem.getValueAt(2));
                            txtDesLarUsrRem.setText(vcoUsrRem.getValueAt(3));
                        } else {
                            txtUsrRem.setText(strUsrRem);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoUsrRem.buscar("a1.tx_nom", txtDesLarUsrRem.getText())) {
                        txtCodUsrRem.setText(vcoUsrRem.getValueAt(1));
                        txtUsrRem.setText(vcoUsrRem.getValueAt(2));
                        txtDesLarUsrRem.setText(vcoUsrRem.getValueAt(3));
                    } else {
                        vcoUsrRem.setCampoBusqueda(2);
                        vcoUsrRem.setCriterio1(11);
                        vcoUsrRem.cargarDatos();
                        vcoUsrRem.show();
                        if (vcoUsrRem.getSelectedButton() == vcoUsrRem.INT_BUT_ACE) {
                            txtCodUsrRem.setText(vcoUsrRem.getValueAt(1));
                            txtUsrRem.setText(vcoUsrRem.getValueAt(2));
                            txtDesLarUsrRem.setText(vcoUsrRem.getValueAt(3));
                        } else {
                            txtDesLarUsrRem.setText(strDesLarUsrRem);
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
    private boolean mostrarVenConUsrDes(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoUsrDes.setCampoBusqueda(1);
                    vcoUsrDes.show();
                    if (vcoUsrDes.getSelectedButton() == vcoUsrDes.INT_BUT_ACE) {
                        txtCodUsrDes.setText(vcoUsrDes.getValueAt(1));
                        txtUsrDes.setText(vcoUsrDes.getValueAt(2));
                        txtDesLarUsrDes.setText(vcoUsrDes.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoUsrDes.buscar("a1.tx_usr", txtUsrDes.getText())) {
                        txtCodUsrDes.setText(vcoUsrDes.getValueAt(1));
                        txtUsrDes.setText(vcoUsrDes.getValueAt(2));
                        txtDesLarUsrDes.setText(vcoUsrDes.getValueAt(3));
                    } else {
                        vcoUsrDes.setCampoBusqueda(1);
                        vcoUsrDes.setCriterio1(11);
                        vcoUsrDes.cargarDatos();
//                        vcoUsrDes.show();
                        if (vcoUsrDes.getSelectedButton() == vcoUsrDes.INT_BUT_ACE) {
                            txtCodUsrDes.setText(vcoUsrDes.getValueAt(1));
                            txtUsrDes.setText(vcoUsrDes.getValueAt(2));
                            txtDesLarUsrDes.setText(vcoUsrDes.getValueAt(3));
                        } else {
                            txtUsrDes.setText(strUsrDes);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoUsrDes.buscar("a1.tx_nom", txtDesLarUsrDes.getText())) {
                        txtCodUsrDes.setText(vcoUsrDes.getValueAt(1));
                        txtUsrDes.setText(vcoUsrDes.getValueAt(2));
                        txtDesLarUsrDes.setText(vcoUsrDes.getValueAt(3));
                    } else {
                        vcoUsrDes.setCampoBusqueda(2);
                        vcoUsrDes.setCriterio1(11);
                        vcoUsrDes.cargarDatos();
                        vcoUsrDes.show();
                        if (vcoUsrDes.getSelectedButton() == vcoUsrDes.INT_BUT_ACE) {
                            txtCodUsrDes.setText(vcoUsrDes.getValueAt(1));
                            txtUsrDes.setText(vcoUsrDes.getValueAt(2));
                            txtDesLarUsrDes.setText(vcoUsrDes.getValueAt(3));
                        } else {
                            txtDesLarUsrDes.setText(strDesLarUsrDes);
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
    private boolean mostrarVenConTipNov(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipNovRecHum.setCampoBusqueda(2);
                    vcoTipNovRecHum.setVisible(true);
                    if (vcoTipNovRecHum.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodTipNov.setText(vcoTipNovRecHum.getValueAt(1));
                        txtDesTipNov.setText(vcoTipNovRecHum.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo".
                    if (vcoTipNovRecHum.buscar("a1.co_nov", txtCodTipNov.getText())) {
                        txtCodTipNov.setText(vcoTipNovRecHum.getValueAt(1));
                        txtDesTipNov.setText(vcoTipNovRecHum.getValueAt(2));
                    } else {
                        vcoTipNovRecHum.setCampoBusqueda(1);
                        vcoTipNovRecHum.setCriterio1(11);
                        vcoTipNovRecHum.cargarDatos();
                        vcoTipNovRecHum.show();
                        if (vcoTipNovRecHum.getSelectedButton() == vcoTipNovRecHum.INT_BUT_ACE) {
                            txtCodTipNov.setText(vcoTipNovRecHum.getValueAt(1));
                            txtDesTipNov.setText(vcoTipNovRecHum.getValueAt(2));
                        } else {
                            txtDesTipNov.setText(strDesLarTipNov);
                        }
                    }
                    break;
                 case 2: //Búsqueda directa por "Descripción Larga".
                     vcoTipNovRecHum.setCampoBusqueda(2);
                        vcoTipNovRecHum.setCriterio1(11);
                    if (vcoTipNovRecHum.buscar("a1.tx_deslar", txtDesTipNov.getText())) {
                        txtCodTipNov.setText(vcoTipNovRecHum.getValueAt(1));
                        txtDesTipNov.setText(vcoTipNovRecHum.getValueAt(2));
                    } else {
                        vcoTipNovRecHum.setCampoBusqueda(1);
                        vcoTipNovRecHum.setCriterio1(11);
                        vcoTipNovRecHum.cargarDatos();
                        vcoTipNovRecHum.show();
                        if (vcoTipNovRecHum.getSelectedButton() == vcoTipNovRecHum.INT_BUT_ACE) {
                            txtCodTipNov.setText(vcoTipNovRecHum.getValueAt(1));
                            txtDesTipNov.setText(vcoTipNovRecHum.getValueAt(2));
                        } else {
                            txtDesTipNov.setText(strDesLarTipNov);
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
     * mostrar los "Usuarios".
     */
    private boolean configurarVenConTipNovRecHum() {
        boolean blnRes = true;
        String strSQL="";
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tipnov");
            arlCam.add("a1.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción Larga");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("200");
            //Armar la sentencia SQL.
            strSQL = "";
            strSQL += "select * from tbm_tipnovrechum";
            strSQL += " order by co_tipnov asc";
            vcoTipNovRecHum = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de usuarios", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoTipNovRecHum.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
}


package RecursosHumanos.ZafRecHum75;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Maestros.ZafMae07.ZafMae07_01;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Autorización de novedades extémporaneas...
 * @author  Roberto Flores
 * Guayaquil 09/10/2013
 */
public class ZafRecHum75 extends javax.swing.JInternalFrame
{
    
    private static final int INT_TBL_DAT_FAL_LIN=0;
    private static final int INT_TBL_DAT_FAL_COD_EMP=1;
    private static final int INT_TBL_DAT_FAL_NOM_EMP=2;
    private static final int INT_TBL_DAT_FAL_COD_TRA=3;
    private static final int INT_TBL_DAT_FAL_NOM_APE_TRA=4;
    private static final int INT_TBL_DAT_FAL_FE_DIA_FAL=5;
    private static final int INT_TBL_DAT_FAL_FE_DIA_JUS_FAL=6;
    private static final int INT_TBL_DAT_FAL_CHK_SOL=7;
    private static final int INT_TBL_DAT_FAL_OBS_SOL=8;
    private static final int INT_TBL_DAT_FAL_BUT_OBS_SOL=9;
    private static final int INT_TBL_DAT_FAL_CHK_AUT=10;
    private static final int INT_TBL_DAT_FAL_CHK_DEN=11;
    private static final int INT_TBL_DAT_FAL_OBS_AUT_DEN=12;
    private static final int INT_TBL_DAT_FAL_BUT_OBS_AUT_DEN=13;
    
    private static final int INT_TBL_DAT_IEPRG_LIN=0;
    private static final int INT_TBL_DAT_IEPRG_COD_EMP=1;
    private static final int INT_TBL_DAT_IEPRG_NOM_EMP=2;
    private static final int INT_TBL_DAT_IEPRG_COD_LOC=3;
    private static final int INT_TBL_DAT_IEPRG_COD_TIP_DOC=4;
    private static final int INT_TBL_DAT_IEPRG_DES_COR_TIP_DOC=5;
    private static final int INT_TBL_DAT_IEPRG_COD_DOC=6;
    private static final int INT_TBL_DAT_IEPRG_NUM_DOC=7;
    private static final int INT_TBL_DAT_IEPRG_FEC_DOC=8;
    private static final int INT_TBL_DAT_IEPRG_COD_TRA=9;
    private static final int INT_TBL_DAT_IEPRG_NOM_APE_TRA=10;
    private static final int INT_TBL_DAT_IEPRG_BUT_IEPRG=11;
    private static final int INT_TBL_DAT_IEPRG_CHK_SOL=12;
    private static final int INT_TBL_DAT_IEPRG_OBS_SOL=13;
    private static final int INT_TBL_DAT_IEPRG_BUT_OBS_SOL=14;
    private static final int INT_TBL_DAT_IEPRG_CHK_AUT=15;
    private static final int INT_TBL_DAT_IEPRG_CHK_DEN=16;
    private static final int INT_TBL_DAT_IEPRG_OBS_AUT_DEN=17;
    private static final int INT_TBL_DAT_IEPRG_BUT_OBS_AUT_DEN=18;
    
    
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblModFA;
    private ZafTblMod objTblModIEPRG;
    private ZafTblEdi objTblEdi;                                            //Editor: Editor del JTable.
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                                //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                                //Editor: JTextField en celda.
//    private ZafTblCelEdiChk zafTblCelEdiChkSel;                             //Editor de Check Box
//    private ZafTblCelRenChk zafTblCelRenChkSel;                             //Renderer de Check Box 
    private ZafTblCelEdiChk zafTblCelEdiChkSolFA;                             //Editor de Check Box
    private ZafTblCelRenChk zafTblCelRenChkSolFA;                             //Renderer de Check Box 
    private ZafTblCelEdiChk zafTblCelEdiChkAutFA;                             //Editor de Check Box
    private ZafTblCelRenChk zafTblCelRenChkAutFA;                             //Renderer de Check Box 
    private ZafTblCelEdiChk zafTblCelEdiChkDenFA;                             //Editor de Check Box
    private ZafTblCelRenChk zafTblCelRenChkDenFA;                             //Renderer de Check Box 
    
    private ZafTblCelEdiChk zafTblCelEdiChkSolIEPRG;                             //Editor de Check Box
    private ZafTblCelRenChk zafTblCelRenChkSolIEPRG;                             //Renderer de Check Box 
    private ZafTblCelEdiChk zafTblCelEdiChkAutIEPRG;                             //Editor de Check Box
    private ZafTblCelRenChk zafTblCelRenChkAutIEPRG;                             //Renderer de Check Box 
    private ZafTblCelEdiChk zafTblCelEdiChkDenIEPRG;                             //Editor de Check Box
    private ZafTblCelRenChk zafTblCelRenChkDenIEPRG;                             //Renderer de Check Box 
    
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;
    
    private ZafMouMotAdaFal objMouMotAdaFA;                                      //ToolTipText en TableHeader.
    private ZafMouMotAdaIEPRG objMouMotAdaIEPRG;                                      //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                      //PopupMenu: Establecer PopupMenú en JTable.
    private ZafVenCon vcoEmp;                                               //Ventana de consulta.
    private ZafVenCon vcoOfi;                                               //Ventana de consulta.
    private ZafVenCon vcoTra;
    

    private static final int INT_TBL_DAT_NUM_TOT_CDI=16;                   //Número total de columnas dinámicas.
    
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;

    private String strCodEmp, strNomEmp;
    private String strCodOfi = "";
    private String strDesLarOfi = "";
    private String strCodTra = "";
    private String strNomTra = "";
    
    private ZafPerUsr objPerUsr;
    private ZafTblBus objTblBus;
    
    private boolean blnCon;                                    //true: Continua la ejecución del hilo.
    
    private String strVersion="v1.0";
    
    private boolean blnPas2=false;
    private boolean blnPas3=false;
    private boolean blnPas4=false;
    private boolean blnPas5=false;
        
    /** Crea una nueva instancia de la clase ZafRecHum52. */
    public ZafRecHum75(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            
            butCon.setVisible(false);
            butGua.setVisible(false);
            butCer.setVisible(false);
            
            if(objPerUsr.isOpcionEnabled(3759)){
                butCon.setVisible(true);
            }
            if(objPerUsr.isOpcionEnabled(3760)){
                butGua.setVisible(true);
            }
            if(objPerUsr.isOpcionEnabled(3761)){
                butCer.setVisible(true);
            }

            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e)
        {
            {objUti.mostrarMsgErr_F1(this, e);}
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
        jLabel4 = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtCodOfi = new javax.swing.JTextField();
        txtNomOfi = new javax.swing.JTextField();
        butOfi = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        jChkBIEPRG = new javax.swing.JCheckBox();
        jChkBFal = new javax.swing.JCheckBox();
        panRptFal = new javax.swing.JPanel();
        spnDatFal = new javax.swing.JScrollPane();
        tblDatFal = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panRptIEPrg = new javax.swing.JPanel();
        spnDatIEPrg = new javax.swing.JScrollPane();
        tblDatIEPrg = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
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
        optTod.setText("Todos los empleados");
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
        optFil.setText("Sólo los empleados que cumplan el criterio seleccionado");
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

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel4.setText("Empresa:"); // NOI18N
        panFil.add(jLabel4);
        jLabel4.setBounds(40, 150, 100, 20);

        txtCodEmp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(140, 150, 60, 20);

        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(200, 150, 250, 20);

        butEmp.setText(".."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(450, 150, 20, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Oficina:"); // NOI18N
        panFil.add(jLabel5);
        jLabel5.setBounds(40, 170, 100, 20);

        txtCodOfi.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodOfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodOfiActionPerformed(evt);
            }
        });
        txtCodOfi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodOfiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodOfiFocusLost(evt);
            }
        });
        panFil.add(txtCodOfi);
        txtCodOfi.setBounds(140, 170, 60, 20);

        txtNomOfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomOfiActionPerformed(evt);
            }
        });
        txtNomOfi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomOfiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomOfiFocusLost(evt);
            }
        });
        panFil.add(txtNomOfi);
        txtNomOfi.setBounds(200, 170, 250, 20);

        butOfi.setText(".."); // NOI18N
        butOfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butOfiActionPerformed(evt);
            }
        });
        panFil.add(butOfi);
        butOfi.setBounds(450, 170, 20, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Empleado:"); // NOI18N
        panFil.add(jLabel6);
        jLabel6.setBounds(40, 190, 100, 20);

        txtCodTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTraActionPerformed(evt);
            }
        });
        txtCodTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTraFocusLost(evt);
            }
        });
        panFil.add(txtCodTra);
        txtCodTra.setBounds(140, 190, 60, 20);

        txtNomTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraActionPerformed(evt);
            }
        });
        txtNomTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTraFocusLost(evt);
            }
        });
        panFil.add(txtNomTra);
        txtNomTra.setBounds(200, 190, 250, 20);

        butTra.setText(".."); // NOI18N
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(450, 190, 20, 20);

        jChkBIEPRG.setSelected(true);
        jChkBIEPRG.setText("Ingresos/Egregos programados");
        panFil.add(jChkBIEPRG);
        jChkBIEPRG.setBounds(10, 90, 320, 23);

        jChkBFal.setSelected(true);
        jChkBFal.setText("Faltas");
        panFil.add(jChkBFal);
        jChkBFal.setBounds(10, 70, 150, 23);

        tabFrm.addTab("Filtro", null, panFil, "Filtro");

        panRptFal.setLayout(new java.awt.BorderLayout());

        tblDatFal.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDatFal.setViewportView(tblDatFal);

        panRptFal.add(spnDatFal, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Faltas", panRptFal);

        panRptIEPrg.setLayout(new java.awt.BorderLayout());

        tblDatIEPrg.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDatIEPrg.setViewportView(tblDatIEPrg);

        panRptIEPrg.add(spnDatIEPrg, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Ingresos/Egresos programados", panRptIEPrg);

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

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

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
        Connection con=null;
        boolean blnFA = true , blnIEPRG = true;
        try{
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(con!=null){
                
                if (objTblModFA.isDataModelChanged())
                {
                    if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
                    {

                        if(guardarDatFA(con)){
                            mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                            con.commit();
                            //Inicializo el estado de las filas.
                            objTblModFA.initRowsState();
                            objTblModFA.setDataModelChanged(false);
                        }
                        else{
                            mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                        }
                    }
                }
                else{
                    blnFA=false;
                }

                if (objTblModIEPRG.isDataModelChanged())
                {
                    if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
                    {
                        if(guardarDatIEPRG(con)){
                            mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                            con.commit();
                            //Inicializo el estado de las filas.
                            objTblModIEPRG.initRowsState();
                            objTblModIEPRG.setDataModelChanged(false);
                        }
                        else{
                            mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                            con.rollback();
                        }
                    }
                }
                else{
//                    mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
                    blnIEPRG=false;
                }
            }
        }catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }finally{
            try{con.close();con=null;}catch(Throwable ignore){}
        }
        
        
        
            
    }//GEN-LAST:event_butGuaActionPerformed
   
    private boolean guardarDatIEPRG(Connection con){
        boolean blnRes=true;
        java.sql.Statement stmLoc = null;
        java.sql.Statement stmLocAux = null;
        java.sql.ResultSet rstLoc = null;
        String strSql="";
        
        try{
            if(con!=null){
                stmLoc=con.createStatement();
                stmLocAux=con.createStatement();
                int intFilSel=tblDatIEPrg.getSelectedRow();
                
                String strFeDes=null;
                
                
                
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSql+="SELECT DISTINCT fe_has FROM tbm_feccorrolpag WHERE ne_ani=EXTRACT(YEAR FROM current_timestamp) AND ne_mes=EXTRACT(MONTH FROM current_timestamp) AND ne_per=2;";              
                }else{
              
                    strSql+="SELECT DISTINCT fe_has FROM tbm_feccorrolpag WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND ne_ani=EXTRACT(YEAR FROM current_timestamp) AND ne_mes=EXTRACT(MONTH FROM current_timestamp) AND ne_per=2";
                }

                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    strFeDes=rstLoc.getString("fe_has");
                }

                if(objPerUsr.isOpcionEnabled(3764)){
                    
                    
                for(int intFil=0; intFil<tblDatIEPrg.getRowCount();intFil++){
                    
                        if(objTblModIEPRG.getValueAt(intFil, INT_TBL_DAT_IEPRG_LIN).toString().compareTo("M") ==0){
                            
                            strSql="";
                            strSql+="select st_solNovExt , fe_solNovExt " +"\n";
                            strSql+="from tbm_cabingegrprgtra a " +"\n";
                            strSql+="WHERE a.co_emp= "+objTblModIEPRG.getValueAt(intFil, INT_TBL_DAT_IEPRG_COD_EMP).toString()+"\n";
                            strSql+="AND a.co_egr= "+objTblModIEPRG.getValueAt(intFil, INT_TBL_DAT_IEPRG_COD_DOC).toString()+"\n";
                            strSql+="AND a.co_tra="+objTblModIEPRG.getValueAt(intFil, INT_TBL_DAT_IEPRG_COD_TRA).toString();

                            System.out.println("veryfiedblnPas2: " + strSql);
                            rstLoc=stmLoc.executeQuery(strSql);
                            while(rstLoc.next()){
                                String strStAutRecHum=rstLoc.getString("st_solNovExt");
                                if(strStAutRecHum==null){
                                    String strSol=null;
                                    String strFeSolNovExt="";
                                    String strCoUsrSolNovExt="";
                                    String strTxComSolNovExt="";
                                    if((Boolean)objTblModIEPRG.getValueAt(intFil, INT_TBL_DAT_IEPRG_CHK_SOL)){
                                        strSol="S";
                                        strFeSolNovExt="current_timestamp";
                                        strCoUsrSolNovExt=String.valueOf(objParSis.getCodigoUsuario());
                                        strTxComSolNovExt=objParSis.getDireccionIP();
                                    }else{
                                        strFeSolNovExt=null;
                                        strCoUsrSolNovExt=null;
                                        strTxComSolNovExt=null;
                                    }
                                        strSql="";
                                        strSql+="update tbm_cabingegrprgtra  set st_solNovExt="+objUti.codificar(strSol)+" , fe_solNovExt = "+strFeSolNovExt+" , co_usrSolNovExt = "+strCoUsrSolNovExt+" ,  \n";
                                        strSql+=" tx_comSolNovExt = " + objUti.codificar(strTxComSolNovExt)+" , \n";
                                        strSql+=" tx_obsSolNovExt = "+objUti.codificar(objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_OBS_SOL))+" \n";
                                        strSql+="WHERE co_emp= "+objTblModIEPRG.getValueAt(intFil, INT_TBL_DAT_IEPRG_COD_EMP).toString()+"\n";
                                        strSql+="AND co_egr= "+objTblModIEPRG.getValueAt(intFil, INT_TBL_DAT_IEPRG_COD_DOC).toString()+"\n";
                                        strSql+="AND co_tra="+objTblModIEPRG.getValueAt(intFil, INT_TBL_DAT_IEPRG_COD_TRA).toString();
                                        System.out.println("updateblnPas2: " + strSql);
                                        stmLocAux.executeUpdate(strSql);
                                    }
                            }
                        }
                }
            }

                if(objPerUsr.isOpcionEnabled(3765)){
                    
               
                    
                    for(int intFil=0; intFil<tblDatIEPrg.getRowCount();intFil++){
                        
                        if(objTblModIEPRG.getValueAt(intFil, INT_TBL_DAT_IEPRG_LIN).toString().compareTo("M") ==0){

                                strSql="";
                                strSql+="select st_autNovExt , fe_autNovExt " +"\n";
                                strSql+="from tbm_cabingegrprgtra a " +"\n";
                                strSql+="WHERE a.co_emp= "+objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_COD_EMP).toString()+"\n";
                                strSql+="AND a.co_egr= "+objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_COD_DOC).toString()+"\n";
                                strSql+="AND a.co_tra="+objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_COD_TRA).toString();
                                System.out.println("veryfiedblnPas2: " + strSql);
                                rstLoc=stmLoc.executeQuery(strSql);
                                while(rstLoc.next()){
                                    String strStAutGer=rstLoc.getString("st_autNovExt");
                                    if(strStAutGer==null){
                            
                                if( ((Boolean)tblDatIEPrg.getValueAt(intFil, INT_TBL_DAT_IEPRG_CHK_AUT)==true || (Boolean)tblDatIEPrg.getValueAt(intFil, INT_TBL_DAT_IEPRG_CHK_DEN)==true)){//&& tblDat.getValueAt(i, INT_TBL_DAT_NUMDOC).toString()!=null
                                            strSql="";
                                            String strAutDen="";

                                            if((Boolean)objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_CHK_AUT)){
                                                strAutDen="S";
                                            }else if((Boolean)objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_CHK_DEN)){
                                                strAutDen="D";
                                            }
                                            strSql+="update tbm_cabingegrprgtra  set st_autNovExt= "+objUti.codificar(strAutDen)+" , \n";
                                            strSql+="fe_autNovExt = current_timestamp , co_usrAutNovExt = "+objParSis.getCodigoUsuario()+" ,  \n";
                                            strSql+=" tx_comAutNovExt = " + objUti.codificar(objParSis.getDireccionIP())+" , \n";
                                            strSql+=" tx_obsAutNovExt = "+objUti.codificar(objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_OBS_AUT_DEN))+" , \n";
    //                                        strSql+=" fe_utiPag = current_timestamp"+" \n";
                                            strSql+=" fe_utiPag = "+objUti.codificar(strFeDes)+" \n";
                                            strSql+="WHERE co_emp= "+objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_COD_EMP).toString()+"\n";
                                            strSql+="AND co_egr= "+objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_COD_DOC).toString()+"\n";
                                            strSql+="AND co_tra="+objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_COD_TRA).toString();
                                            System.out.println("updateblnPas2: " + strSql);
                                            stmLocAux.executeUpdate(strSql);
                                        }
                                }else{
//                                        blnRes=false;
//                                        mostrarMsgInf("NO PUEDE REALIZAR MODIFICACIONES A UN REGISTRO QUE YA ESTA AUTORIZADO/DENEGADO");
                                    }
                            }
                        }
                    }  
                }
            }
        }catch(java.sql.SQLException Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }catch(Exception Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }finally {
            try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
            try{stmLocAux.close();stmLocAux=null;}catch(Throwable ignore){}
            try{rstLoc.close();rstLoc=null;}catch(Throwable ignore){}
            
        }
    return blnRes;
}
    
    private boolean guardarDatFA(Connection con){
        boolean blnRes=true;
        java.sql.Statement stmLoc = null;
        java.sql.Statement stmLocAux = null;
        java.sql.ResultSet rstLoc = null;
        String strSql="";
        
        try{
            if(con!=null){
                stmLoc=con.createStatement();
                stmLocAux=con.createStatement();
                int intFilSel=tblDatFal.getSelectedRow();
                
                String strFeDes=null;
                
                
                
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSql+="SELECT DISTINCT fe_has FROM tbm_feccorrolpag WHERE ne_ani=EXTRACT(YEAR FROM current_timestamp) AND ne_mes=EXTRACT(MONTH FROM current_timestamp) AND ne_per=2;";
                }else{
                    strSql+="SELECT DISTINCT fe_has FROM tbm_feccorrolpag WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND ne_ani=EXTRACT(YEAR FROM current_timestamp) AND ne_mes=EXTRACT(MONTH FROM current_timestamp) AND ne_per=2";
                }

                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    strFeDes=rstLoc.getString("fe_has");
                }
 
                if(objPerUsr.isOpcionEnabled(3762)){
                    
                for(int intFil=0; intFil<tblDatFal.getRowCount();intFil++){
                    
                        if(objTblModFA.getValueAt(intFil, INT_TBL_DAT_FAL_LIN).toString().compareTo("M") ==0){
                            
                            strSql="";
                            strSql+="select st_solNovExt , fe_solNovExt " +"\n";
                            strSql+="from tbm_cabconasitra " +"\n";
                            strSql+="WHERE co_tra ="+objTblModFA.getValueAt(intFil, INT_TBL_DAT_FAL_COD_TRA).toString()+"\n";
                            strSql+="AND fe_dia = "+objUti.codificar(objTblModFA.getValueAt(intFil, INT_TBL_DAT_FAL_FE_DIA_FAL).toString());
                            

                            System.out.println("veryfiedblnPas2: " + strSql);
                            rstLoc=stmLoc.executeQuery(strSql);
                            while(rstLoc.next()){
                                String strStAutRecHum=rstLoc.getString("st_solNovExt");
                                if(strStAutRecHum==null){
                                    String strSol=null;
                                    String strFeSolNovExt="";
                                    String strCoUsrSolNovExt="";
                                    String strTxComSolNovExt="";
                                    if((Boolean)objTblModFA.getValueAt(intFil, INT_TBL_DAT_FAL_CHK_SOL)){
                                        strSol="S";
                                        strFeSolNovExt="current_timestamp";
                                        strCoUsrSolNovExt=String.valueOf(objParSis.getCodigoUsuario());
                                        strTxComSolNovExt=objParSis.getDireccionIP();
                                    }else{
                                        strFeSolNovExt=null;
                                        strCoUsrSolNovExt=null;
                                        strTxComSolNovExt=null;
                                    }

                                    strSql="";
                                    strSql+="update tbm_cabconasitra  set st_solNovExt="+objUti.codificar(strSol)+" , fe_solNovExt = "+strFeSolNovExt+" , co_usrSolNovExt = "+strCoUsrSolNovExt+" ,  \n";
                                    strSql+=" tx_comSolNovExt = " + objUti.codificar(strTxComSolNovExt)+" , \n";
                                    strSql+=" tx_obsSolNovExt = "+objUti.codificar(objTblModFA.getValueAt(intFil, INT_TBL_DAT_FAL_OBS_SOL))+" \n";
                                    strSql+="WHERE co_tra = "+objTblModFA.getValueAt(intFil, INT_TBL_DAT_FAL_COD_TRA).toString() +"\n";
                                    strSql+="AND fe_dia = "+objUti.codificar(objTblModFA.getValueAt(intFil, INT_TBL_DAT_FAL_FE_DIA_FAL));
                                    System.out.println("updateblnPas2: " + strSql);
                                    stmLocAux.executeUpdate(strSql);
                                }
                            }
                        }
                }
            }
                
                if(objPerUsr.isOpcionEnabled(3763)){
                    
                    for(int intFil=0; intFil<tblDatFal.getRowCount();intFil++){

                        if(objTblModFA.getValueAt(intFil, INT_TBL_DAT_FAL_LIN).toString().compareTo("M") ==0){

                                strSql="";
                                strSql+="select st_autNovExt , fe_autNovExt " +"\n";
                                strSql+="from tbm_cabconasitra " +"\n";
                                strSql+="WHERE co_tra="+objTblModFA.getValueAt(intFilSel, INT_TBL_DAT_FAL_COD_TRA).toString();
                                strSql+="AND fe_dia="+objUti.codificar(objTblModFA.getValueAt(intFilSel, INT_TBL_DAT_FAL_FE_DIA_FAL).toString());
                                System.out.println("veryfiedblnPas2: " + strSql);
                                rstLoc=stmLoc.executeQuery(strSql);
                                while(rstLoc.next()){
                                    String strStAutGer=rstLoc.getString("st_autNovExt");
                                    if(strStAutGer==null){
                            
                                        if( ((Boolean)tblDatFal.getValueAt(intFil, INT_TBL_DAT_FAL_CHK_AUT)==true || (Boolean)tblDatFal.getValueAt(intFil, INT_TBL_DAT_FAL_CHK_DEN)==true)){//&& tblDat.getValueAt(i, INT_TBL_DAT_NUMDOC).toString()!=null
                                                    strSql="";
                                                    String strAutDen="";

                                                    if((Boolean)objTblModFA.getValueAt(intFil, INT_TBL_DAT_FAL_CHK_AUT)){
                                                        strAutDen="S";
                                                    }else if((Boolean)objTblModFA.getValueAt(intFil, INT_TBL_DAT_FAL_CHK_DEN)){
                                                        strAutDen="D";
                                                    }
                                                    strSql+="update tbm_cabconasitra  set st_autNovExt= "+objUti.codificar(strAutDen)+" , \n";
                                                    strSql+="fe_autNovExt = current_timestamp , co_usrAutNovExt = "+objParSis.getCodigoUsuario()+" ,  \n";
                                                    strSql+="tx_comAutNovExt = " + objUti.codificar(objParSis.getDireccionIP())+" , \n";
                                                    strSql+="tx_obsAutNovExt = "+objUti.codificar(objTblModFA.getValueAt(intFil, INT_TBL_DAT_FAL_OBS_AUT_DEN))+" , \n";
            //                                        strSql+=" fe_utiPag = current_timestamp"+" \n";
                                                    strSql+="fe_utiPagJusFal = "+objUti.codificar(strFeDes)+" \n";
                                                    strSql+="WHERE co_tra = "+objTblModFA.getValueAt(intFil, INT_TBL_DAT_FAL_COD_TRA).toString() +"\n";
                                                    strSql+="AND fe_dia = "+objUti.codificar(objTblModFA.getValueAt(intFil, INT_TBL_DAT_FAL_FE_DIA_FAL));
                                                    System.out.println("updateblnPas2: " + strSql);
                                                    stmLocAux.executeUpdate(strSql);
                                                }
                                    }else{
//                                        blnRes=false;
//                                        mostrarMsgInf("NO PUEDE REALIZAR MODIFICACIONES A UN REGISTRO QUE YA ESTA AUTORIZADO/DENEGADO");
                                    }
                                }
                        }
                    }  
                }
            }
        }catch(java.sql.SQLException Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }catch(Exception Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }finally {
            try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
            try{stmLocAux.close();stmLocAux=null;}catch(Throwable ignore){}
            try{rstLoc.close();rstLoc=null;}catch(Throwable ignore){}
            
        }
    return blnRes;
}
    
    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged

   }//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected()) {
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodOfi.setText("");
            txtNomOfi.setText("");
            txtCodTra.setText("");
            txtNomTra.setText("");
        }
        optTod.setSelected(true);
        optFil.setSelected(false);
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optFilItemStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) {
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodOfi.setText("");
            txtNomOfi.setText("");
            txtCodTra.setText("");
            txtNomTra.setText("");
            
        }
        optTod.setSelected(false);
        optFil.setSelected(true);
    }//GEN-LAST:event_optFilActionPerformed

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        strCodEmp = txtCodEmp.getText();
        txtCodEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp)) {
            if (txtCodEmp.getText().equals("")) {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            } else {
                BuscarEmp("a1.co_emp", txtCodEmp.getText(), 0);
            }
        } else {
            txtCodEmp.setText(strCodEmp);
        }
   }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
   }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
    if (txtNomEmp.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp))
            {
                if (txtNomEmp.getText().equals(""))
                {
                    txtCodEmp.setText("");
                    txtNomEmp.setText("");
                }
                else
                {
                    mostrarVenConEmp(2);
                }
            }
            else
                txtNomEmp.setText(strNomEmp);
        }
   }//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        strCodEmp=txtCodEmp.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtCodOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodOfiActionPerformed
        txtCodOfi.transferFocus();
    }//GEN-LAST:event_txtCodOfiActionPerformed

    private void txtCodOfiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodOfiFocusGained
        // TODO add your handling code here:
        strCodOfi = txtCodOfi.getText();
        txtCodOfi.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodOfiFocusGained

    private void txtCodOfiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodOfiFocusLost
        // TODO add your handling code here:
        if (!txtCodOfi.getText().equalsIgnoreCase(strCodOfi)) {
            if (txtCodOfi.getText().equals("")) {
                txtCodOfi.setText("");
                txtNomOfi.setText("");
            } else {
                BuscarOfi("a1.co_ofi", txtCodOfi.getText(), 0);
            }
        } else {
            txtCodOfi.setText(strCodOfi);
        }
    }//GEN-LAST:event_txtCodOfiFocusLost

    public void BuscarEmp(String campo,String strBusqueda,int tipo){
        
        vcoEmp.setTitle("Listado de Empresas");
        if(vcoEmp.buscar(campo, strBusqueda )) {
            txtCodEmp.setText(vcoEmp.getValueAt(1));
            txtNomEmp.setText(vcoEmp.getValueAt(2));
        }else{
            vcoEmp.setCampoBusqueda(tipo);
            vcoEmp.cargarDatos();
            vcoEmp.show();
            if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE) {
                txtCodEmp.setText(vcoEmp.getValueAt(1));
                txtNomEmp.setText(vcoEmp.getValueAt(2));
        }else{
                txtCodEmp.setText(strCodEmp);
                txtNomEmp.setText(strNomEmp);
  }}}
    
    public void BuscarOfi(String campo,String strBusqueda,int tipo){
        
        vcoOfi.setTitle("Listado de Oficinas");
        if(vcoOfi.buscar(campo, strBusqueda )) {
            txtCodOfi.setText(vcoOfi.getValueAt(1));
            txtNomOfi.setText(vcoOfi.getValueAt(2));
        }else{
            vcoOfi.setCampoBusqueda(tipo);
            vcoOfi.cargarDatos();
            vcoOfi.show();
            if (vcoOfi.getSelectedButton()==vcoOfi.INT_BUT_ACE) {
                txtCodOfi.setText(vcoOfi.getValueAt(1));
                txtNomOfi.setText(vcoOfi.getValueAt(2));
        }else{
                txtCodOfi.setText(strCodOfi);
                txtNomOfi.setText(strDesLarOfi);
  }}}
    
    public void BuscarTra(String campo,String strBusqueda,int tipo){
        
        vcoTra.setTitle("Listado de Empleados");
        if(vcoTra.buscar(campo, strBusqueda )) {
            txtCodTra.setText(vcoTra.getValueAt(1));
            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        }else{
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE) {
                txtCodTra.setText(vcoTra.getValueAt(1));
                txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        }else{
                txtCodTra.setText(strCodTra);
                txtNomTra.setText(strNomTra);
  }}}
    
    private void txtNomOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomOfiActionPerformed
        // TODO add your handling code here:
        txtNomOfi.transferFocus();
    }//GEN-LAST:event_txtNomOfiActionPerformed

    private void txtNomOfiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomOfiFocusGained
        // TODO add your handling code here:
        strDesLarOfi=txtNomOfi.getText();
        txtNomOfi.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
        
    }//GEN-LAST:event_txtNomOfiFocusGained

    private void txtNomOfiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomOfiFocusLost
        // TODO add your handling code here:
        if (txtNomOfi.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomOfi.getText().equalsIgnoreCase(strDesLarOfi))
            {
                if (txtNomOfi.getText().equals(""))
                {
                    txtCodOfi.setText("");
                    txtNomOfi.setText("");
                }
                else
                {
                    mostrarVenConOfi(2);
                }
            }
            else
                txtNomOfi.setText(strDesLarOfi);
        }
    }//GEN-LAST:event_txtNomOfiFocusLost

    private void butOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butOfiActionPerformed
        strCodOfi=txtCodOfi.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConOfi(0);
        
    }//GEN-LAST:event_butOfiActionPerformed
    
    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        txtCodTra.transferFocus();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void txtCodTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusGained
        // TODO add your handling code here:
        strCodTra = txtCodTra.getText();
        txtCodTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodTraFocusGained

    private void txtCodTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusLost
        // TODO add your handling code here:
        if (!txtCodTra.getText().equalsIgnoreCase(strCodTra)) {
            if (txtCodTra.getText().equals("")) {
                txtCodTra.setText("");
                txtNomTra.setText("");
            } else {
                BuscarTra("a1.co_tra", txtCodTra.getText(), 0);
            }
        } else {
            txtCodTra.setText(strCodTra);
        }
    }//GEN-LAST:event_txtCodTraFocusLost

    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        // TODO add your handling code here:
        txtNomTra.transferFocus();
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        // TODO add your handling code here:
        strNomTra=txtNomTra.getText();
        txtNomTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
        // TODO add your handling code here:
        if (txtNomTra.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomTra.getText().equalsIgnoreCase(strNomTra))
            {
                if (txtNomTra.getText().equals(""))
                {
                    txtCodTra.setText("");
                    txtNomTra.setText("");
                }
                else
                {
                    mostrarVenConTra(2);
                }
            }
            else
                txtNomTra.setText(strNomTra);
        }
    }//GEN-LAST:event_txtNomTraFocusLost

    private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed

        strCodTra=txtCodTra.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConTra(0);
    }//GEN-LAST:event_butTraActionPerformed

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
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butOfi;
    private javax.swing.JButton butTra;
    private javax.swing.JCheckBox jChkBFal;
    private javax.swing.JCheckBox jChkBIEPRG;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRptFal;
    private javax.swing.JPanel panRptIEPrg;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDatFal;
    private javax.swing.JScrollPane spnDatIEPrg;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatFal;
    private javax.swing.JTable tblDatIEPrg;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodOfi;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomOfi;
    private javax.swing.JTextField txtNomTra;
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
            this.setTitle(strAux + " " + strVersion);
            lblTit.setText(strAux);
            
            //Configurar las ZafVenCon.
            configurarVenConOfi();
            configurarVenConTra();
            configurarVenConEmp();

            //Configurar los JTables.
            configuraTblFal();
            agregarColTblDatFal();
            
            configuraTblIEPRG();
            agregarColTblDatIEPRG();
            
             //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setTitulo("Rango de fechas");
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setCheckBoxChecked(false);
            objSelFec.setBounds(4, 20, 472, 72);

            //*****************************************************************************
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setHoy();
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
            int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if(fecDoc!=null){
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(java.util.Calendar.DATE, -30 );

            dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");

            objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );
            //*******************************************************************************
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configuraTblIEPRG(){
        
        boolean blnRes=false;
        
        try{
        
            //Configurar JTable: Establecer el modelo.
            vecCab = new Vector();
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_IEPRG_LIN,"");
            vecCab.add(INT_TBL_DAT_IEPRG_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_IEPRG_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_DAT_IEPRG_COD_LOC,"Cód.Loc.");
//            vecCab.add(INT_TBL_DAT_IEPRG_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_IEPRG_COD_TIP_DOC,"Cód.Rub.");
//            vecCab.add(INT_TBL_DAT_IEPRG_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_IEPRG_DES_COR_TIP_DOC,"Rubro");
            vecCab.add(INT_TBL_DAT_IEPRG_COD_DOC,"Cód.Doc.");
//            vecCab.add(INT_TBL_DAT_IEPRG_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_IEPRG_NUM_DOC,"Cód.Egr.");
            
            vecCab.add(INT_TBL_DAT_IEPRG_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_IEPRG_COD_TRA,"Código");
            vecCab.add(INT_TBL_DAT_IEPRG_NOM_APE_TRA,"Empleado");
            vecCab.add(INT_TBL_DAT_IEPRG_BUT_IEPRG,"");
            vecCab.add(INT_TBL_DAT_IEPRG_CHK_SOL,"Solicitar");
            vecCab.add(INT_TBL_DAT_IEPRG_OBS_SOL,"Observación");
            vecCab.add(INT_TBL_DAT_IEPRG_BUT_OBS_SOL,"");
            
            vecCab.add(INT_TBL_DAT_IEPRG_CHK_AUT,"Autorizar");
            vecCab.add(INT_TBL_DAT_IEPRG_CHK_DEN,"Denegar");
            vecCab.add(INT_TBL_DAT_IEPRG_OBS_AUT_DEN,"Observación");
            vecCab.add(INT_TBL_DAT_IEPRG_BUT_OBS_AUT_DEN,"");

            objTblModIEPRG=new ZafTblMod();
            objTblModIEPRG.setHeader(vecCab);
        
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//            objTblMod.setColumnDataType(INT_TBL_DAT_ND_VALALM, objTblMod.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatIEPrg.setModel(objTblModIEPRG);

            //Configurar JTable: Establecer tipo de selección.
            tblDatIEPrg.setRowSelectionAllowed(true);
            tblDatIEPrg.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDatIEPrg,INT_TBL_DAT_IEPRG_LIN);

            //Configurar JTable: Establecer el msenú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatIEPrg);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatIEPrg.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatIEPrg.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_COD_EMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_NOM_EMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_COD_TIP_DOC).setPreferredWidth(80);
            
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_DES_COR_TIP_DOC).setPreferredWidth(140);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_COD_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_FEC_DOC).setPreferredWidth(80);
            
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_COD_TRA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_NOM_APE_TRA).setPreferredWidth(220);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_BUT_IEPRG).setPreferredWidth(20);
            

            tcmAux.getColumn(INT_TBL_DAT_IEPRG_CHK_SOL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_OBS_SOL).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_BUT_OBS_SOL).setPreferredWidth(20);
            
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_CHK_AUT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_CHK_DEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_OBS_AUT_DEN).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_BUT_OBS_AUT_DEN).setPreferredWidth(20);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModIEPRG.addSystemHiddenColumn(INT_TBL_DAT_IEPRG_COD_EMP, tblDatIEPrg);
            objTblModIEPRG.addSystemHiddenColumn(INT_TBL_DAT_IEPRG_COD_LOC, tblDatIEPrg);
            objTblModIEPRG.addSystemHiddenColumn(INT_TBL_DAT_IEPRG_COD_DOC, tblDatIEPrg);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_COD_LOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_COD_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_COD_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_COD_TRA).setCellRenderer(objTblCelRenLbl);
            //objTblCelRenLbl=null;
        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatIEPrg.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaIEPRG=new ZafMouMotAdaIEPRG();
            tblDatIEPrg.getTableHeader().addMouseMotionListener(objMouMotAdaIEPRG);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_IEPRG_BUT_IEPRG);
            vecAux.add("" + INT_TBL_DAT_IEPRG_CHK_SOL);
            vecAux.add("" + INT_TBL_DAT_IEPRG_BUT_OBS_SOL);
            vecAux.add("" + INT_TBL_DAT_IEPRG_CHK_AUT);
            vecAux.add("" + INT_TBL_DAT_IEPRG_CHK_DEN);
            vecAux.add("" + INT_TBL_DAT_IEPRG_BUT_OBS_AUT_DEN);
            objTblModIEPRG.addColumnasEditables(vecAux);
            vecAux=null;

            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDatIEPrg);
            
            
            objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_BUT_IEPRG).setCellRenderer(objTblCelRenBut);
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_BUT_IEPRG).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDatIEPrg.getSelectedRow();
                    intColSel=tblDatIEPrg.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {

                          case INT_TBL_DAT_IEPRG_BUT_IEPRG:

                         break;

                       }
                     }
                    }
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel=tblDatIEPrg.getSelectedColumn();
                    intFilSel=tblDatIEPrg.getSelectedRow();
                    if (intFilSel!=-1)
                    {

                        llamarPantIngEgrPrg(tblDatIEPrg.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_COD_EMP).toString() , 
                            tblDatIEPrg.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_NUM_DOC).toString(),
                            tblDatIEPrg.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_COD_TRA).toString()
                            );
                    }
                 }
           });

            zafTblCelRenChkSolIEPRG = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_CHK_SOL).setCellRenderer(zafTblCelRenChkSolIEPRG);
            zafTblCelEdiChkSolIEPRG = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_CHK_SOL).setCellEditor(zafTblCelEdiChkSolIEPRG);
            zafTblCelEdiChkSolIEPRG.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDatIEPrg.getSelectedRow();
                    java.sql.Connection con = null;
                    java.sql.Statement stm = null;
                    java.sql.ResultSet rst = null;
                    String strSql="";
                    String strBlqIni="";
                    intFilSel=tblDatIEPrg.getSelectedRow();
                    try{
                        con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                        if(con!=null){
                                con.setAutoCommit(false);
                                stm=con.createStatement();
                                strSql+="select case (a.st_solNovExt is null or a.st_solNovExt='D') when true then false::boolean else true::boolean end as blnSol " +"\n";
                                strSql+="from tbm_cabingegrprgtra a " +"\n";
                                strSql+="WHERE a.co_emp= "+objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_COD_EMP).toString()+"\n";
                                strSql+="AND a.co_egr= "+objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_COD_DOC).toString()+"\n";
                                strSql+="AND a.co_tra="+objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_COD_TRA).toString();
                                rst=stm.executeQuery(strSql);
                                while(rst.next()){
                                    boolean bln =(Boolean)rst.getBoolean("blnSol"); 
                                    if(!(bln)){//falso no esta autorizado //verdadero esta autorizado
                                        strBlqIni="B";
                                    }else{
                                        strBlqIni="";
                                    }
                                }
                            }
                        
                        Object objSol = objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_CHK_SOL);
                        boolean blnSol=(Boolean)objSol;
                        
                        if(blnSol){
                            if(!objPerUsr.isOpcionEnabled(3764)){
                                zafTblCelEdiChkSolIEPRG.setCancelarEdicion(true);
                            }
//                            zafTblCelEdiChkSol.setCancelarEdicion(true);
                            Object objAut = objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_CHK_AUT);
                            boolean blnAut=(Boolean)objAut;

                            Object objDen = objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_CHK_DEN);
                            boolean blnDen=(Boolean)objDen;
                            
                            if(blnAut||blnDen){
                                zafTblCelEdiChkSolIEPRG.setCancelarEdicion(true);
                            }
                            
                            zafTblCelEdiChkAutIEPRG.setCancelarEdicion(true);
                            zafTblCelEdiChkDenIEPRG.setCancelarEdicion(true);
                        }else{
                            if(blnSol &&strBlqIni.compareTo("B")==0){
                                zafTblCelEdiChkSolIEPRG.setCancelarEdicion(true);
                                zafTblCelEdiChkAutIEPRG.setCancelarEdicion(true);
                                zafTblCelEdiChkDenIEPRG.setCancelarEdicion(true);
                            }else{
                                if(objPerUsr.isOpcionEnabled(3764)){
                                    zafTblCelEdiChkAutIEPRG.setCancelarEdicion(true);
                                    zafTblCelEdiChkDenIEPRG.setCancelarEdicion(true);
                                }else{
                                    zafTblCelEdiChkSolIEPRG.setCancelarEdicion(true);
                                    zafTblCelEdiChkAutIEPRG.setCancelarEdicion(true);
                                    zafTblCelEdiChkDenIEPRG.setCancelarEdicion(true);
                                }
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    finally {
                        try{rst.close();rst=null;}catch(Throwable ignore){}
                        try{stm.close();stm=null;}catch(Throwable ignore){}
                        try{con.close();con=null;}catch(Throwable ignore){}
                    }

                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(!(Boolean)objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_CHK_SOL)){
                        objTblModIEPRG.setValueAt("", intFilSel, INT_TBL_DAT_IEPRG_OBS_SOL);
                    }
                    
                }
            });
            
            
            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_BUT_OBS_SOL).setCellRenderer(zafTblDocCelRenBut);

            ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDatIEPrg, INT_TBL_DAT_IEPRG_BUT_OBS_SOL, "Observación") {
                public void butCLick() {
                    int intSelFil = tblDatIEPrg.getSelectedRow();
                    String strObs = (tblDatIEPrg.getValueAt(intSelFil, INT_TBL_DAT_IEPRG_OBS_SOL) == null ? "" : tblDatIEPrg.getValueAt(intSelFil, INT_TBL_DAT_IEPRG_OBS_SOL).toString());
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum75.this), true, strObs);
                    zafMae07_01.show();
                    if (zafMae07_01.getAceptar()) {
                        tblDatIEPrg.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_DAT_IEPRG_OBS_SOL);
                    }
                }
            };
            
            zafTblCelRenChkAutIEPRG = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_CHK_AUT).setCellRenderer(zafTblCelRenChkAutIEPRG);
            zafTblCelEdiChkAutIEPRG = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_CHK_AUT).setCellEditor(zafTblCelEdiChkAutIEPRG);
            zafTblCelEdiChkAutIEPRG.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    java.sql.Connection con = null;
                    java.sql.Statement stm = null;
                    java.sql.ResultSet rst = null;
                    String strSql="";
                    String strBlqIni="";
                    intFilSel=tblDatIEPrg.getSelectedRow();
                    
                    try{
                        con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                        if(con!=null){
                                con.setAutoCommit(false);
                                stm=con.createStatement();
                                strSql+="select case (a.st_autNovExt is not null) when true then true::boolean else false::boolean end as blnAut " +"\n";
                                strSql+="from tbm_cabingegrprgtra a " +"\n";
                                strSql+="WHERE a.co_emp= "+objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_COD_EMP).toString()+"\n";
                                strSql+="AND a.co_egr= "+objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_COD_DOC).toString()+"\n";
                                strSql+="AND a.co_tra="+objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_COD_TRA).toString();
                                rst=stm.executeQuery(strSql);
                                while(rst.next()){
                                    boolean bln =(Boolean)rst.getBoolean("blnAut"); 
                                    if((bln)){//falso no esta autorizado //verdadero esta autorizado
                                        strBlqIni="B";
                                    }else{
                                        strBlqIni="";
                                    }
                                }
                            }
                        
                        Object objAut = objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_CHK_AUT);
                        boolean blnAut=(Boolean)objAut;
                        
                        Object objDen = objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_CHK_DEN);
                        boolean blnDen=(Boolean)objDen;
                        
                        if( strBlqIni.compareTo("B")==0){
                            zafTblCelEdiChkSolIEPRG.setCancelarEdicion(true);
                            zafTblCelEdiChkAutIEPRG.setCancelarEdicion(true);
                            zafTblCelEdiChkDenIEPRG.setCancelarEdicion(true);
                        }else{
                            if(objPerUsr.isOpcionEnabled(3765)){
//                                boolean bln = (Boolean)tblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKAUTRRHH);
                                boolean bln = ((Boolean)tblDatIEPrg.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_CHK_SOL));
                                if(bln){
                                    blnPas2=true;
                                    if(!blnPas2){
                                        zafTblCelEdiChkAutIEPRG.setCancelarEdicion(true);
                                        zafTblCelEdiChkDenIEPRG.setCancelarEdicion(true);
                                    }else{
                                        blnPas3=true;
                                        blnPas2=false;
                                    }
                                }else{
                                    zafTblCelEdiChkAutIEPRG.setCancelarEdicion(true);
                                    zafTblCelEdiChkDenIEPRG.setCancelarEdicion(true);
                                }
                            }else{
                                zafTblCelEdiChkAutIEPRG.setCancelarEdicion(true);
                                zafTblCelEdiChkDenIEPRG.setCancelarEdicion(true);
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    finally {
                        try{rst.close();rst=null;}catch(Throwable ignore){}
                        try{stm.close();stm=null;}catch(Throwable ignore){}
                        try{con.close();con=null;}catch(Throwable ignore){}
                    }
                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDatIEPrg.getSelectedRow();
                    objTblModIEPRG.setValueAt(false, intFilSel, INT_TBL_DAT_IEPRG_CHK_DEN);
                    objTblModIEPRG.setValueAt(true, intFilSel, INT_TBL_DAT_IEPRG_CHK_AUT);
                }
            });
            
            
            zafTblCelRenChkDenIEPRG = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_CHK_DEN).setCellRenderer(zafTblCelRenChkDenIEPRG);
            zafTblCelEdiChkDenIEPRG = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_IEPRG_CHK_DEN).setCellEditor(zafTblCelEdiChkDenIEPRG);
            zafTblCelEdiChkDenIEPRG.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    java.sql.Connection con = null;
                    java.sql.Statement stm = null;
                    java.sql.ResultSet rst = null;
                    String strSql="";
                    String strBlqIni="";
                    intFilSel=tblDatIEPrg.getSelectedRow();
                    
                    try{
                        con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                        if(con!=null){
                                con.setAutoCommit(false);
                                stm=con.createStatement();
                                strSql+="select case (a.st_autNovExt is not null) when true then true::boolean else false::boolean end as blnAut " +"\n";
                                strSql+="from tbm_cabingegrprgtra a " +"\n";
                                strSql+="WHERE a.co_emp= "+objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_COD_EMP).toString()+"\n";
                                strSql+="AND a.co_egr= "+objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_COD_DOC).toString()+"\n";
                                strSql+="AND a.co_tra="+objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_COD_TRA).toString();
                                rst=stm.executeQuery(strSql);
                                while(rst.next()){
                                    boolean bln =(Boolean)rst.getBoolean("blnAut"); 
                                    if((bln)){//falso no esta autorizado //verdadero esta autorizado
                                        strBlqIni="B";
                                    }else{
                                        strBlqIni="";
                                    }
                                }
                            }
                        
                        Object objAut = objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_CHK_AUT);
                        boolean blnAut=(Boolean)objAut;
                        
                        Object objDen = objTblModIEPRG.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_CHK_DEN);
                        boolean blnDen=(Boolean)objDen;
                        
                        if(strBlqIni.compareTo("B")==0){
                            zafTblCelEdiChkSolIEPRG.setCancelarEdicion(true);
                            zafTblCelEdiChkAutIEPRG.setCancelarEdicion(true);
                            zafTblCelEdiChkDenIEPRG.setCancelarEdicion(true);
                        }else{
                            if(objPerUsr.isOpcionEnabled(3765)){
                                boolean bln = ((Boolean)tblDatIEPrg.getValueAt(intFilSel, INT_TBL_DAT_IEPRG_CHK_SOL));
                                if(bln){
                                    blnPas2=true;
                                    if(!blnPas2){
                                        zafTblCelEdiChkAutIEPRG.setCancelarEdicion(true);
                                        zafTblCelEdiChkDenIEPRG.setCancelarEdicion(true);
                                    }else{
                                        blnPas3=true;
                                        blnPas2=false;
                                    }
                                }else{
                                    zafTblCelEdiChkAutIEPRG.setCancelarEdicion(true);
                                    zafTblCelEdiChkDenIEPRG.setCancelarEdicion(true);
                                }
                            }else{
                                zafTblCelEdiChkAutIEPRG.setCancelarEdicion(true);
                                zafTblCelEdiChkDenIEPRG.setCancelarEdicion(true);
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    finally {
                        try{rst.close();rst=null;}catch(Throwable ignore){}
                        try{stm.close();stm=null;}catch(Throwable ignore){}
                        try{con.close();con=null;}catch(Throwable ignore){}
                    }
                    
                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDatIEPrg.getSelectedRow();
                    objTblModIEPRG.setValueAt(false, intFilSel, INT_TBL_DAT_IEPRG_CHK_AUT);
                    objTblModIEPRG.setValueAt(true, intFilSel, INT_TBL_DAT_IEPRG_CHK_DEN);
                }
            });
            
            zafTableColBut_uni = new ZafTableColBut_uni(tblDatIEPrg, INT_TBL_DAT_IEPRG_BUT_OBS_AUT_DEN, "Observación") {
                public void butCLick() {
                    int intSelFil = tblDatIEPrg.getSelectedRow();
                    String strObs = (tblDatIEPrg.getValueAt(intSelFil, INT_TBL_DAT_IEPRG_OBS_AUT_DEN) == null ? "" : tblDatIEPrg.getValueAt(intSelFil, INT_TBL_DAT_IEPRG_OBS_AUT_DEN).toString());
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum75.this), true, strObs);
                    zafMae07_01.show();
                    if (zafMae07_01.getAceptar()) {
                        tblDatIEPrg.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_DAT_IEPRG_OBS_AUT_DEN);
                    }
                }
            };

            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatIEPrg);

            //Configurar JTable: Establecer el modo de operación.
            objTblModIEPRG.setModoOperacion(objTblModIEPRG.INT_TBL_EDI);
            blnRes=true;
                
        }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
           
        return blnRes;
    }
    
    private void llamarPantIngEgrPrg(String strCodEmp, String strCodEgr, String strCodTra){
        RecursosHumanos.ZafRecHum34.ZafRecHum34 obj1 = new  RecursosHumanos.ZafRecHum34.ZafRecHum34(objParSis, strCodEmp, strCodEgr, strCodTra );
       this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
       obj1.show();
    }
    
    private boolean configuraTblFal(){
        
        boolean blnRes=false;
        
        try{
        
            //Configurar JTable: Establecer el modelo.
            vecCab = new Vector();
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_FAL_LIN,"");
            vecCab.add(INT_TBL_DAT_FAL_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_FAL_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_DAT_FAL_COD_TRA,"Código");
            vecCab.add(INT_TBL_DAT_FAL_NOM_APE_TRA,"Empleado");
            vecCab.add(INT_TBL_DAT_FAL_FE_DIA_FAL,"Fec.Fal.");
            vecCab.add(INT_TBL_DAT_FAL_FE_DIA_JUS_FAL,"Fec.Jus.Fal.");
            vecCab.add(INT_TBL_DAT_FAL_CHK_SOL,"Solicitar");
            vecCab.add(INT_TBL_DAT_FAL_OBS_SOL,"Observación");
            vecCab.add(INT_TBL_DAT_FAL_BUT_OBS_SOL,"");
            vecCab.add(INT_TBL_DAT_FAL_CHK_AUT,"Autorizar");
            vecCab.add(INT_TBL_DAT_FAL_CHK_DEN,"Denegar");
            vecCab.add(INT_TBL_DAT_FAL_OBS_AUT_DEN,"Observación");
            vecCab.add(INT_TBL_DAT_FAL_BUT_OBS_AUT_DEN,"");

            objTblModFA=new ZafTblMod();
            objTblModFA.setHeader(vecCab);
        
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//            objTblMod.setColumnDataType(INT_TBL_DAT_ND_VALALM, objTblMod.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatFal.setModel(objTblModFA);

            //Configurar JTable: Establecer tipo de selección.
            tblDatFal.setRowSelectionAllowed(true);
            tblDatFal.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDatFal,INT_TBL_DAT_FAL_LIN);

            //Configurar JTable: Establecer el msenú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatFal);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatFal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatFal.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_FAL_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_FAL_COD_EMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FAL_NOM_EMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FAL_COD_TRA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FAL_NOM_APE_TRA).setPreferredWidth(220);
            tcmAux.getColumn(INT_TBL_DAT_FAL_FE_DIA_FAL).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FAL_FE_DIA_JUS_FAL).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FAL_CHK_SOL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FAL_OBS_SOL).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_FAL_BUT_OBS_SOL).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_FAL_CHK_AUT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FAL_CHK_DEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FAL_OBS_AUT_DEN).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_FAL_BUT_OBS_AUT_DEN).setPreferredWidth(20);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModFA.addSystemHiddenColumn(INT_TBL_DAT_FAL_COD_EMP, tblDatFal);
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_OFI, tblDatFal);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_FAL_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FAL_COD_TRA).setCellRenderer(objTblCelRenLbl);
            //objTblCelRenLbl=null;
        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatFal.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaFA=new ZafMouMotAdaFal();
            tblDatFal.getTableHeader().addMouseMotionListener(objMouMotAdaFA);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_FAL_CHK_SOL);
            vecAux.add("" + INT_TBL_DAT_FAL_BUT_OBS_SOL);
            vecAux.add("" + INT_TBL_DAT_FAL_CHK_AUT);
            vecAux.add("" + INT_TBL_DAT_FAL_CHK_DEN);
            vecAux.add("" + INT_TBL_DAT_FAL_BUT_OBS_AUT_DEN);
//            if(objPerUsr.isOpcionEnabled(3764)){
//                vecAux.add("" + INT_TBL_DAT_FAL_CHK_SOL);
//                vecAux.add("" + INT_TBL_DAT_FAL_BUT_OBS_SOL);
//            }
//            if(objPerUsr.isOpcionEnabled(3765)){
//                vecAux.add("" + INT_TBL_DAT_FAL_CHK_AUT);
//                vecAux.add("" + INT_TBL_DAT_FAL_CHK_DEN);
//                vecAux.add("" + INT_TBL_DAT_FAL_BUT_OBS_AUT_DEN);
//                
//            }
            
            if(vecAux.size()>0){
                objTblModFA.addColumnasEditables(vecAux);
                vecAux=null;
            }
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDatFal);

            zafTblCelRenChkSolFA = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_FAL_CHK_SOL).setCellRenderer(zafTblCelRenChkSolFA);
            zafTblCelEdiChkSolFA = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_FAL_CHK_SOL).setCellEditor(zafTblCelEdiChkSolFA);
            zafTblCelEdiChkSolFA.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDatFal.getSelectedRow();
                    java.sql.Connection con = null;
                    java.sql.Statement stm = null;
                    java.sql.ResultSet rst = null;
                    String strSql="";
                    String strBlqIni="";
                    intFilSel=tblDatFal.getSelectedRow();
                    try{
                        con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                        if(con!=null){
                                con.setAutoCommit(false);
                                stm=con.createStatement();
                                strSql+="select case (a.st_solNovExt is null or a.st_solNovExt='D') when true then false::boolean else true::boolean end as blnSol " +"\n";
                                strSql+="from tbm_cabconasitra a " +"\n";
                                strSql+="WHERE a.co_tra = "+objTblModFA.getValueAt(intFilSel, INT_TBL_DAT_FAL_COD_TRA).toString() +"\n";
                                strSql+="AND a.fe_dia = "+objTblModFA.getValueAt(intFilSel, INT_TBL_DAT_FAL_FE_DIA_FAL).toString();
                                rst=stm.executeQuery(strSql);
                                while(rst.next()){
                                    boolean bln =(Boolean)rst.getBoolean("blnSol"); 
                                    if(!(bln)){//falso no esta autorizado //verdadero esta autorizado
                                        strBlqIni="B";
                                    }else{
                                        strBlqIni="";
                                    }
                                }
                            }
                        
                        Object objSol = objTblModFA.getValueAt(intFilSel, INT_TBL_DAT_FAL_CHK_SOL);
                        boolean blnSol=(Boolean)objSol;
                        
                        if(blnSol){
                            if(!objPerUsr.isOpcionEnabled(3762)){
                                zafTblCelEdiChkSolFA.setCancelarEdicion(true);
                            }
//                            zafTblCelEdiChkSol.setCancelarEdicion(true);
                            Object objAut = objTblModFA.getValueAt(intFilSel, INT_TBL_DAT_FAL_CHK_AUT);
                            boolean blnAut=(Boolean)objAut;

                            Object objDen = objTblModFA.getValueAt(intFilSel, INT_TBL_DAT_FAL_CHK_DEN);
                            boolean blnDen=(Boolean)objDen;
                            
                            if(blnAut||blnDen){
                                zafTblCelEdiChkSolFA.setCancelarEdicion(true);
                            }
                            
                            zafTblCelEdiChkAutFA.setCancelarEdicion(true);
                            zafTblCelEdiChkDenFA.setCancelarEdicion(true);
                        }else{
                            if(blnSol &&strBlqIni.compareTo("B")==0){
                                zafTblCelEdiChkSolFA.setCancelarEdicion(true);
                                zafTblCelEdiChkAutFA.setCancelarEdicion(true);
                                zafTblCelEdiChkDenFA.setCancelarEdicion(true);
                            }else{
                                if(objPerUsr.isOpcionEnabled(3762)){
                                    zafTblCelEdiChkAutFA.setCancelarEdicion(true);
                                    zafTblCelEdiChkDenFA.setCancelarEdicion(true);
                                }else{
                                    zafTblCelEdiChkSolFA.setCancelarEdicion(true);
                                    zafTblCelEdiChkAutFA.setCancelarEdicion(true);
                                    zafTblCelEdiChkDenFA.setCancelarEdicion(true);
                                }
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    finally {
                        try{rst.close();rst=null;}catch(Throwable ignore){}
                        try{stm.close();stm=null;}catch(Throwable ignore){}
                        try{con.close();con=null;}catch(Throwable ignore){}
                    }

                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(!(Boolean)objTblModFA.getValueAt(intFilSel, INT_TBL_DAT_FAL_CHK_SOL)){
                        objTblModFA.setValueAt("", intFilSel, INT_TBL_DAT_FAL_OBS_SOL);
                    }
                    
                }
            });
            
            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_FAL_BUT_OBS_SOL).setCellRenderer(zafTblDocCelRenBut);

            ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDatFal, INT_TBL_DAT_FAL_BUT_OBS_SOL, "Observación") {
                public void butCLick() {
                    int intSelFil = tblDatFal.getSelectedRow();
                    String strObs = (tblDatFal.getValueAt(intSelFil, INT_TBL_DAT_FAL_OBS_SOL) == null ? "" : tblDatFal.getValueAt(intSelFil, INT_TBL_DAT_FAL_OBS_SOL).toString());
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum75.this), true, strObs);
                    zafMae07_01.show();

                    if (zafMae07_01.getAceptar()) {
                        tblDatFal.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_DAT_FAL_OBS_SOL);
                    }

                }
            };
            
            zafTblCelRenChkAutFA = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_FAL_CHK_AUT).setCellRenderer(zafTblCelRenChkAutFA);
            zafTblCelEdiChkAutFA = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_FAL_CHK_AUT).setCellEditor(zafTblCelEdiChkAutFA);
            zafTblCelEdiChkAutFA.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    java.sql.Connection con = null;
                    java.sql.Statement stm = null;
                    java.sql.ResultSet rst = null;
                    String strSql="";
                    String strBlqIni="";
                    intFilSel=tblDatFal.getSelectedRow();
                    
                    try{
                        con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                        if(con!=null){
                                con.setAutoCommit(false);
                                stm=con.createStatement();
                                strSql+="select case (a.st_autNovExt is not null) when true then true::boolean else false::boolean end as blnAut " +"\n";
                                strSql+="from tbm_cabconasitra a " +"\n";
                                strSql+="WHERE a.co_tra = "+objTblModFA.getValueAt(intFilSel, INT_TBL_DAT_FAL_COD_TRA).toString() + "\n";
                                strSql+="AND a.fe_dia = "+objUti.codificar(objTblModFA.getValueAt(intFilSel, INT_TBL_DAT_FAL_FE_DIA_FAL));
                                rst=stm.executeQuery(strSql);
                                while(rst.next()){
                                    boolean bln =(Boolean)rst.getBoolean("blnAut"); 
                                    if((bln)){//falso no esta autorizado //verdadero esta autorizado
                                        strBlqIni="B";
                                    }else{
                                        strBlqIni="";
                                    }
                                }
                            }
                        
                        Object objAut = objTblModFA.getValueAt(intFilSel, INT_TBL_DAT_FAL_CHK_AUT);
                        boolean blnAut=(Boolean)objAut;
                        
                        Object objDen = objTblModFA.getValueAt(intFilSel, INT_TBL_DAT_FAL_CHK_DEN);
                        boolean blnDen=(Boolean)objDen;
                        
                        if( strBlqIni.compareTo("B")==0){
                            zafTblCelEdiChkSolFA.setCancelarEdicion(true);
                            zafTblCelEdiChkAutFA.setCancelarEdicion(true);
                            zafTblCelEdiChkDenFA.setCancelarEdicion(true);
                        }else{
                            if(objPerUsr.isOpcionEnabled(3763)){
//                                boolean bln = (Boolean)tblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKAUTRRHH);
                                boolean bln = ((Boolean)tblDatFal.getValueAt(intFilSel, INT_TBL_DAT_FAL_CHK_SOL));
                                if(bln){
                                    blnPas2=true;
                                    if(!blnPas2){
                                        zafTblCelEdiChkAutFA.setCancelarEdicion(true);
                                        zafTblCelEdiChkDenFA.setCancelarEdicion(true);
                                    }else{
                                        blnPas3=true;
                                        blnPas2=false;
                                    }
                                }else{
                                    zafTblCelEdiChkAutFA.setCancelarEdicion(true);
                                    zafTblCelEdiChkDenFA.setCancelarEdicion(true);
                                }
                            }else{
                                zafTblCelEdiChkAutFA.setCancelarEdicion(true);
                                zafTblCelEdiChkDenFA.setCancelarEdicion(true);
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    finally {
                        try{rst.close();rst=null;}catch(Throwable ignore){}
                        try{stm.close();stm=null;}catch(Throwable ignore){}
                        try{con.close();con=null;}catch(Throwable ignore){}
                    }
                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDatFal.getSelectedRow();
                    objTblModFA.setValueAt(false, intFilSel, INT_TBL_DAT_FAL_CHK_DEN);
                    objTblModFA.setValueAt(true, intFilSel, INT_TBL_DAT_FAL_CHK_AUT);
                }
            });
            
            zafTblCelRenChkDenFA = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_FAL_CHK_DEN).setCellRenderer(zafTblCelRenChkDenFA);
            zafTblCelEdiChkDenFA = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_FAL_CHK_DEN).setCellEditor(zafTblCelEdiChkDenFA);
            zafTblCelEdiChkDenFA.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    java.sql.Connection con = null;
                    java.sql.Statement stm = null;
                    java.sql.ResultSet rst = null;
                    String strSql="";
                    String strBlqIni="";
                    intFilSel=tblDatFal.getSelectedRow();
                    try{
                        con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                        if(con!=null){
                            con.setAutoCommit(false);
                            stm=con.createStatement();
                            strSql+="select case (a.st_autNovExt is not null) when true then true::boolean else false::boolean end as blnAut " +"\n";
                            strSql+="from tbm_cabconasitra a " +"\n";
                            strSql+="WHERE a.co_tra = "+objTblModFA.getValueAt(intFilSel, INT_TBL_DAT_FAL_COD_TRA).toString() + "\n";
                            strSql+="AND a.fe_dia = "+objUti.codificar(objTblModFA.getValueAt(intFilSel, INT_TBL_DAT_FAL_FE_DIA_FAL));
                            rst=stm.executeQuery(strSql);
                            while(rst.next()){
                                boolean bln =(Boolean)rst.getBoolean("blnAut"); 
                                if((bln)){//falso no esta autorizado //verdadero esta autorizado
                                    strBlqIni="B";
                                }else{
                                    strBlqIni="";
                                }
                            }
                        }
                        
                        Object objAut = objTblModFA.getValueAt(intFilSel, INT_TBL_DAT_FAL_CHK_AUT);
                        boolean blnAut=(Boolean)objAut;
                        
                        Object objDen = objTblModFA.getValueAt(intFilSel, INT_TBL_DAT_FAL_CHK_DEN);
                        boolean blnDen=(Boolean)objDen;
                        
                        if( strBlqIni.compareTo("B")==0){
                            zafTblCelEdiChkSolFA.setCancelarEdicion(true);
                            zafTblCelEdiChkAutFA.setCancelarEdicion(true);
                            zafTblCelEdiChkDenFA.setCancelarEdicion(true);
                        }else{
                            if(objPerUsr.isOpcionEnabled(3763)){
//                                boolean bln = (Boolean)tblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKAUTRRHH);
                                boolean bln = ((Boolean)tblDatFal.getValueAt(intFilSel, INT_TBL_DAT_FAL_CHK_SOL));
                                if(bln){
                                    blnPas2=true;
                                    if(!blnPas2){
                                        zafTblCelEdiChkAutFA.setCancelarEdicion(true);
                                        zafTblCelEdiChkDenFA.setCancelarEdicion(true);
                                    }else{
                                        blnPas3=true;
                                        blnPas2=false;
                                    }
                                }else{
                                    zafTblCelEdiChkAutFA.setCancelarEdicion(true);
                                    zafTblCelEdiChkDenFA.setCancelarEdicion(true);
                                }
                            }else{
                                zafTblCelEdiChkAutFA.setCancelarEdicion(true);
                                zafTblCelEdiChkDenFA.setCancelarEdicion(true);
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    finally {
                        try{rst.close();rst=null;}catch(Throwable ignore){}
                        try{stm.close();stm=null;}catch(Throwable ignore){}
                        try{con.close();con=null;}catch(Throwable ignore){}
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDatFal.getSelectedRow();
                    objTblModFA.setValueAt(false, intFilSel, INT_TBL_DAT_FAL_CHK_AUT);
                    objTblModFA.setValueAt(true, intFilSel, INT_TBL_DAT_FAL_CHK_DEN);
                }
            });
            
            
            zafTableColBut_uni = new ZafTableColBut_uni(tblDatFal, INT_TBL_DAT_FAL_BUT_OBS_AUT_DEN, "Observación") {
                public void butCLick() {
                    int intSelFil = tblDatFal.getSelectedRow();
                    String strObs = (tblDatFal.getValueAt(intSelFil, INT_TBL_DAT_FAL_OBS_AUT_DEN) == null ? "" : tblDatFal.getValueAt(intSelFil, INT_TBL_DAT_FAL_OBS_AUT_DEN).toString());
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum75.this), true, strObs);
                    zafMae07_01.show();
                    if (zafMae07_01.getAceptar()) {
                        tblDatFal.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_DAT_FAL_OBS_AUT_DEN);
                    }
                }
            };

            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatFal);
                
            //Configurar JTable: Establecer el modo de operación.
            objTblModFA.setModoOperacion(objTblModFA.INT_TBL_EDI);
            blnRes=true;
                
        }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
           
        return blnRes;
    }

    
    private boolean agregarColTblDatFal(){

        int i, intNumFil, intNumColTblDat;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDatFal.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpEmp=null;
        java.awt.Color colFonCol;
        boolean blnRes=true;

        try
        {
            intNumFil=objTblModFA.getRowCountTrue();
            intNumColTblDat=objTblModFA.getColumnCount();

            for (i=0; i<1; i++){
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDatFal.getColumnModel().getColumn(INT_TBL_DAT_FAL_LIN+i*INT_TBL_DAT_NUM_TOT_CDI));

                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Paso 1: Faltas");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                
                objTblHeaColGrpEmp.add(tblDatFal.getColumnModel().getColumn(INT_TBL_DAT_FAL_COD_EMP+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatFal.getColumnModel().getColumn(INT_TBL_DAT_FAL_NOM_EMP+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatFal.getColumnModel().getColumn(INT_TBL_DAT_FAL_COD_TRA+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatFal.getColumnModel().getColumn(INT_TBL_DAT_FAL_NOM_APE_TRA+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatFal.getColumnModel().getColumn(INT_TBL_DAT_FAL_FE_DIA_FAL+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatFal.getColumnModel().getColumn(INT_TBL_DAT_FAL_FE_DIA_JUS_FAL+i*INT_TBL_DAT_NUM_TOT_CDI));
                
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Paso 2: Solicitud");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                
                objTblHeaColGrpEmp.add(tblDatFal.getColumnModel().getColumn(INT_TBL_DAT_FAL_CHK_SOL+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatFal.getColumnModel().getColumn(INT_TBL_DAT_FAL_OBS_SOL+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatFal.getColumnModel().getColumn(INT_TBL_DAT_FAL_BUT_OBS_SOL+i*INT_TBL_DAT_NUM_TOT_CDI));
                
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Paso 3: Autorización");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                
                objTblHeaColGrpEmp.add(tblDatFal.getColumnModel().getColumn(INT_TBL_DAT_FAL_CHK_AUT+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatFal.getColumnModel().getColumn(INT_TBL_DAT_FAL_CHK_DEN+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatFal.getColumnModel().getColumn(INT_TBL_DAT_FAL_OBS_AUT_DEN+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatFal.getColumnModel().getColumn(INT_TBL_DAT_FAL_BUT_OBS_AUT_DEN+i*INT_TBL_DAT_NUM_TOT_CDI));
            }
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColTblDatIEPRG(){

        int i, intNumFil, intNumColTblDat;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDatIEPrg.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpEmp=null;
        java.awt.Color colFonCol;
        boolean blnRes=true;

        try
        {
            intNumFil=objTblModIEPRG.getRowCountTrue();
            intNumColTblDat=objTblModIEPRG.getColumnCount();

            for (i=0; i<1; i++){
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_LIN+i*INT_TBL_DAT_NUM_TOT_CDI));
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Paso 1: Ingresos/Egresos programados");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_COD_EMP+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_NOM_EMP+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_COD_LOC+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_COD_TIP_DOC+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_DES_COR_TIP_DOC+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_COD_DOC+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_NUM_DOC+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_FEC_DOC+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_COD_TRA+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_NOM_APE_TRA+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_BUT_IEPRG+i*INT_TBL_DAT_NUM_TOT_CDI));
                
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Paso 2: Solicitud");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_CHK_SOL+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_OBS_SOL+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_BUT_OBS_SOL+i*INT_TBL_DAT_NUM_TOT_CDI));
                
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Paso 3: Autorización");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_CHK_AUT+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_CHK_DEN+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_OBS_AUT_DEN+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDatIEPrg.getColumnModel().getColumn(INT_TBL_DAT_IEPRG_BUT_OBS_AUT_DEN+i*INT_TBL_DAT_NUM_TOT_CDI));
            }
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    public static java.sql.Time SparseToTime(String hora){
        int h, m, s;

        h = Integer.parseInt(hora.charAt(0)+""+hora.charAt(1)) ;
        m = Integer.parseInt(hora.charAt(3)+""+hora.charAt(4)) ;
        s = Integer.parseInt(hora.charAt(6)+""+hora.charAt(7)) ;
        return (new java.sql.Time(h,m,s));
    }
    
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

        
        if(jChkBFal.isSelected()){
            if (!cargarDatFAL())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                //lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        
        if(jChkBIEPRG.isSelected()){
            if (!cargarDatIEPRG())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                //lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }

        //Establecer el foco en el JTable sólo cuando haya datos.
        if (tblDatFal.getRowCount()>0 )
        {
            tabFrm.setSelectedIndex(1);
            tblDatFal.setRowSelectionInterval(0, 0);
            tblDatFal.requestFocus();
        }

        objThrGUI=null;
    }
}
    
/**
 * Se encarga de cargar la informacion de los Ingresos/Egresos programados en la tabla
 * @return  true si esta correcto y false  si hay algun error
 */
private boolean cargarDatIEPRG(){
  boolean blnRes=false;
  java.sql.Connection conn=null;
  java.sql.Statement stmLoc=null;
  java.sql.Statement stmLocAux=null;
  java.sql.ResultSet rstLoc = null;
  java.sql.ResultSet rstLocAux = null;
  String strSql="";
  String strFilFec="";
  String sqlFilEmp = "";
  Vector vecDataCon;
  Vector vecData;
  Vector vecFecCon;
  try{
   
      conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
          
      if(conn!=null){
          
          
          stmLoc=conn.createStatement();
          stmLocAux=conn.createStatement();
          vecDataCon = new Vector();
          vecData = new Vector();
          vecFecCon = new Vector();
          
          if(txtCodEmp.getText().compareTo("")!=0){
              sqlFilEmp+= " and a.co_emp  = " + txtCodEmp.getText() + " ";
          }

          if(txtCodTra.getText().compareTo("")!=0){
              sqlFilEmp+= " AND a.co_tra  = " + txtCodTra.getText() + " ";
          }

          String strOfi="";
          if(txtCodOfi.getText().compareTo("")!=0){
              sqlFilEmp+= " AND b.co_ofi  = " + txtCodOfi.getText() + " ";
          }
          
          String strEmp="";
          if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
              strEmp=" and a.co_emp = "+ objParSis.getCodigoEmpresa();
          }
          
          String strFeDes=null;
          strSql="";
          
          
          
          strSql="";
          if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
              strSql+="SELECT DISTINCT fe_has FROM tbm_feccorrolpag WHERE ne_ani=EXTRACT(YEAR FROM current_timestamp) AND ne_mes=EXTRACT(MONTH FROM current_timestamp) AND ne_per=2;";
              rstLoc=stmLoc.executeQuery(strSql);
              while(rstLoc.next()){
                  strFeDes=rstLoc.getString("fe_has");
              }
              
              String[] arrFeDes = strFeDes.split("-");
              
              String strFeCorHas="";
              Calendar cal = Calendar.getInstance();
              cal.set(Calendar.YEAR, Integer.parseInt(arrFeDes[0]));
              cal.set(Calendar.MONTH, Integer.parseInt(arrFeDes[1]));
              strFeCorHas=cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)-1) + "-" + cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
              
              strSql="";
              strSql+="SELECT a.co_emp, d.tx_nom as empresa, NULL as co_loc, a.co_egr as co_doc, a.co_egr as num_doc , a.co_tra, (c.tx_ape || ' ' || c.tx_nom) as empleado, a.co_rub as coTipDoc, " + "\n";
              strSql+="e.tx_nom as tipDoc , a.fe_doc, a.fe_ing, a.fe_utipag, a.st_solnovext, case when a.st_solnovext is null then false::boolean else true::boolean end as blnSol, "+"\n";
              strSql+="a.tx_obssolnovext, a.fe_solnovext, a.co_usrsolnovext, " + "\n";
              strSql+="a.tx_comSolNovExt, st_autNovExt, case when (a.st_autNovExt is null or a.st_autNovExt='D') then false::boolean else true::boolean end as blnAut, "+"\n";
              strSql+="case when (a.st_autNovExt is null or a.st_autNovExt='S') then false::boolean else true::boolean end as blnDen, "+"\n";
              strSql+="tx_obsAutNovExt, fe_autNovExt, co_usrAutNovExt , tx_comAutNovExt " + "\n";
              strSql+="FROM tbm_cabingegrprgtra a " + "\n";
              strSql+="INNER JOIN tbm_traemp b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.st_reg=a.st_reg) " + "\n";
              strSql+="INNER JOIN tbm_tra c on (c.co_tra=b.co_tra) " + "\n";
              strSql+="INNER JOIN tbm_emp d on (d.co_emp=b.co_emp) " + "\n";
              strSql+="INNER JOIN tbm_rubrolpag e on (e.co_rub=a.co_rub) " + "\n";
              strSql+="LEFT OUTER JOIN tbm_ofi f on (f.co_ofi=b.co_ofi) \n";
//              strSql+="AND date(a.fe_ing) BETWEEN '2013-10-29' AND '2013-10-31' " + "\n";
              strSql+="WHERE (date(a.fe_ing) > " + objUti.codificar(strFeDes) +  " \n";
//              strSql+="WHERE (date(a.fe_ing) > (select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(YEAR FROM current_timestamp) and ne_mes=EXTRACT(MONTH FROM current_timestamp) and ne_per=2)";
              strSql+="AND date(a.fe_ing) <= "+objUti.codificar(strFeCorHas)+") " + "\n";
              strSql+="AND a.st_reg='A' " + "\n";
              strSql+=" " + sqlFilEmp + "\n";
              strSql+=" " + strEmp + "\n";
              strSql+="ORDER BY empresa , co_egr";
              
          }else{
              
              strSql+="SELECT DISTINCT fe_has FROM tbm_feccorrolpag WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND ne_ani=EXTRACT(YEAR FROM current_timestamp) AND ne_mes=EXTRACT(MONTH FROM current_timestamp) AND ne_per=2";
              rstLoc=stmLoc.executeQuery(strSql);
              while(rstLoc.next()){
                  strFeDes=rstLoc.getString("fe_has");
              }
              
              String[] arrFeDes = strFeDes.split("-");
              
              String strFeCorHas="";
              Calendar cal = Calendar.getInstance();
              cal.set(Calendar.YEAR, Integer.parseInt(arrFeDes[0]));
              cal.set(Calendar.MONTH, Integer.parseInt(arrFeDes[1]));
              strFeCorHas=cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)-1) + "-" + cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
              
              strSql="";
              strSql+="SELECT a.co_emp, d.tx_nom as empresa, NULL as co_loc, a.co_egr as co_doc, a.co_egr as num_doc , a.co_tra, (c.tx_ape || ' ' || c.tx_nom) as empleado, a.co_rub as coTipDoc, " + "\n";
              strSql+="e.tx_nom as tipDoc , a.fe_doc, a.fe_ing, a.fe_utipag, a.st_solnovext, case when a.st_solnovext is null then false::boolean else true::boolean end as blnSol, "+"\n";
              strSql+="a.tx_obssolnovext, a.fe_solnovext, a.co_usrsolnovext, " + "\n";
              strSql+="a.tx_comSolNovExt, st_autNovExt, case when (a.st_autNovExt is null or a.st_autNovExt='D') then false::boolean else true::boolean end as blnAut, "+"\n";
              strSql+="case when (a.st_autNovExt is null or a.st_autNovExt='S') then false::boolean else true::boolean end as blnDen, "+"\n";
              strSql+="tx_obsAutNovExt, fe_autNovExt, co_usrAutNovExt , tx_comAutNovExt " + "\n";
              strSql+="FROM tbm_cabingegrprgtra a " + "\n";
              strSql+="INNER JOIN tbm_traemp b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.st_reg=a.st_reg) " + "\n";
              strSql+="INNER JOIN tbm_tra c on (c.co_tra=b.co_tra) " + "\n";
              strSql+="INNER JOIN tbm_emp d on (d.co_emp=b.co_emp) " + "\n";
              strSql+="INNER JOIN tbm_rubrolpag e on (e.co_rub=a.co_rub) " + "\n";
              strSql+="LEFT OUTER JOIN tbm_ofi f on (f.co_ofi=b.co_ofi) \n";
              strSql+="WHERE a.co_emp= "+objParSis.getCodigoEmpresa()+ "\n";
//              strSql+="AND date(a.fe_ing) BETWEEN '2013-10-29' AND '2013-10-31' " + "\n";
              strSql+="AND (date(a.fe_ing) > (select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(YEAR FROM current_timestamp) and ne_mes=EXTRACT(MONTH FROM current_timestamp) and ne_per=2)";
              strSql+="AND date(a.fe_ing) <= "+objUti.codificar(strFeCorHas)+") " + "\n";
              strSql+="AND a.st_reg='A' " + "\n";
              strSql+=" " + sqlFilEmp + "\n";
              strSql+=" " + strEmp + "\n";
              strSql+="ORDER BY empresa , co_egr";
          }
          
              
          System.out.println("q consul: " + strSql);
          rstLoc=stmLoc.executeQuery(strSql);
              
          vecDat = new Vector();
          while(rstLoc.next()){

                java.util.Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_DAT_IEPRG_LIN,"");
                vecReg.add(INT_TBL_DAT_IEPRG_COD_EMP,rstLoc.getInt("co_emp"));
                vecReg.add(INT_TBL_DAT_IEPRG_NOM_EMP,rstLoc.getString("empresa"));
                vecReg.add(INT_TBL_DAT_IEPRG_COD_LOC,null);
                vecReg.add(INT_TBL_DAT_IEPRG_COD_TIP_DOC,rstLoc.getInt("coTipDoc"));
                vecReg.add(INT_TBL_DAT_IEPRG_DES_COR_TIP_DOC,rstLoc.getString("tipDoc"));
                
                vecReg.add(INT_TBL_DAT_IEPRG_COD_DOC,rstLoc.getInt("co_doc"));
                vecReg.add(INT_TBL_DAT_IEPRG_NUM_DOC,rstLoc.getInt("num_doc"));
                vecReg.add(INT_TBL_DAT_IEPRG_FEC_DOC,rstLoc.getString("fe_doc"));
                
                vecReg.add(INT_TBL_DAT_IEPRG_COD_TRA,rstLoc.getInt("co_tra"));
                vecReg.add(INT_TBL_DAT_IEPRG_NOM_APE_TRA,rstLoc.getString("empleado"));
                
                vecReg.add(INT_TBL_DAT_IEPRG_BUT_IEPRG,"...");
                
                vecReg.add(INT_TBL_DAT_IEPRG_CHK_SOL,rstLoc.getBoolean("blnSol"));
                vecReg.add(INT_TBL_DAT_IEPRG_OBS_SOL,rstLoc.getString("tx_obssolnovext"));
                vecReg.add(INT_TBL_DAT_IEPRG_BUT_OBS_SOL,"...");
                
//                boolean blnAutDen=rstLoc.getBoolean("blnAutDen");
//                if(blnAutDen){
//                    vecReg.add(INT_TBL_DAT_IEPRG_CHK_AUT,true);
//                    vecReg.add(INT_TBL_DAT_IEPRG_CHK_DEN,false);
//                }else{
//                    vecReg.add(INT_TBL_DAT_IEPRG_CHK_AUT,false);
//                    vecReg.add(INT_TBL_DAT_IEPRG_CHK_DEN,true);
//                }
                
                vecReg.add(INT_TBL_DAT_IEPRG_CHK_AUT,rstLoc.getBoolean("blnAut"));
                vecReg.add(INT_TBL_DAT_IEPRG_CHK_DEN,rstLoc.getBoolean("blnDen"));
                
                vecReg.add(INT_TBL_DAT_IEPRG_OBS_AUT_DEN,rstLoc.getString("tx_obsautnovext"));
                vecReg.add(INT_TBL_DAT_IEPRG_BUT_OBS_AUT_DEN,"...");
                
                vecDataCon.add(vecReg);
            }

            objTblModIEPRG.setData(vecDataCon);
            tblDatIEPrg .setModel(objTblModIEPRG);          
            lblMsgSis.setText("Listo");
            tabFrm.setSelectedIndex(1);
//            lblMsgSis.setText("Se encontraron " + tblDatFal.getRowCount() + " registros");
            pgrSis.setIndeterminate(false);
            vecDat.clear();
      }
  }catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  Evt.printStackTrace();objUti.mostrarMsgErr_F1(this, Evt);  }
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

/**
 * Se encarga de cargar la informacion de los Ingresos/Egresos programados en la tabla
 * @return  true si esta correcto y false  si hay algun error
 */
private boolean cargarDatFAL(){
  boolean blnRes=false;
  java.sql.Connection conn=null;
  java.sql.Statement stmLoc=null;
  java.sql.Statement stmLocAux=null;
  java.sql.ResultSet rstLoc = null;
  java.sql.ResultSet rstLocAux = null;
  String strSql="";
  String strFilFec="";
  String sqlFilEmp = "";
  Vector vecDataCon;
  Vector vecData;
  Vector vecFecCon;
  try{
   
      conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
          
      if(conn!=null){
          
          
          stmLoc=conn.createStatement();
          stmLocAux=conn.createStatement();
          vecDataCon = new Vector();
          vecData = new Vector();
          vecFecCon = new Vector();
          
          if(txtCodEmp.getText().compareTo("")!=0){
              sqlFilEmp+= " and b.co_emp  = " + txtCodEmp.getText() + " ";
          }

          if(txtCodTra.getText().compareTo("")!=0){
              sqlFilEmp+= " AND a.co_tra  = " + txtCodTra.getText() + " ";
          }

          String strOfi="";
          if(txtCodOfi.getText().compareTo("")!=0){
              sqlFilEmp+= " AND b.co_ofi  = " + txtCodOfi.getText() + " ";
          }
          
          String strEmp="";
          if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
              strEmp=" and b.co_emp = "+ objParSis.getCodigoEmpresa();
          }
          
          String strFeDes=null;
          strSql="";
          
          
          
          strSql="";
          if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
              strSql+="SELECT DISTINCT fe_has FROM tbm_feccorrolpag WHERE ne_ani=EXTRACT(YEAR FROM current_timestamp) AND ne_mes=EXTRACT(MONTH FROM current_timestamp) AND ne_per=2;";
              rstLoc=stmLoc.executeQuery(strSql);
              if(rstLoc.next()){
                  strFeDes=rstLoc.getString("fe_has");
              }
              
              String[] arrFeDes = strFeDes.split("-");
              
              String strFeCorHas="";
              Calendar cal = Calendar.getInstance();
              cal.set(Calendar.YEAR, Integer.parseInt(arrFeDes[0]));
              cal.set(Calendar.MONTH, Integer.parseInt(arrFeDes[1]));
              strFeCorHas=cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)-1) + "-" + cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
              
              strSql="";
              strSql+="SELECT b.co_emp, d.tx_nom as empresa, a.co_tra, (c.tx_ape || ' ' || c.tx_nom) as empleado, a.fe_dia, a.fe_jusfal,  " + "\n";
              strSql+="a.fe_utiPagJusFal, a.st_solnovext, case when a.st_solnovext is null then false::boolean else true::boolean end as blnSol,  " + "\n";
              strSql+="a.tx_obssolnovext, a.fe_solnovext, a.co_usrsolnovext,  " + "\n";
              strSql+="a.tx_comSolNovExt, st_autNovExt, case when (a.st_autNovExt is null or a.st_autNovExt='D') then false::boolean else true::boolean end as blnAut,  " + "\n";
              strSql+="case when (a.st_autNovExt is null or a.st_autNovExt='S') then false::boolean else true::boolean end as blnDen,  " + "\n";
              strSql+="tx_obsAutNovExt, fe_autNovExt, co_usrAutNovExt , tx_comAutNovExt  " + "\n";
              strSql+="FROM tbm_cabconasitra a " + "\n";
              strSql+="INNER JOIN tbm_traemp b on (b.co_tra=a.co_tra and b.st_reg='A')  " + "\n";
              strSql+="INNER JOIN tbm_tra c on (c.co_tra=b.co_tra)  " + "\n";
              strSql+="INNER JOIN tbm_emp d on (d.co_emp=b.co_emp)  " + "\n";
              strSql+="WHERE (date(a.fe_jusfal) > " + objUti.codificar(strFeDes) +  " \n";
              strSql+="AND date(a.fe_jusfal) <= "+objUti.codificar(strFeCorHas)+") " + "\n";
              strSql+=" " + sqlFilEmp + "\n";
              strSql+=" " + strEmp + "\n";
              strSql+="ORDER BY empresa, fe_dia, empleado " + "\n";
              
              
          }else{
              
              strSql+="SELECT DISTINCT fe_has FROM tbm_feccorrolpag WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND ne_ani=EXTRACT(YEAR FROM current_timestamp) AND ne_mes=EXTRACT(MONTH FROM current_timestamp) AND ne_per=2";
              rstLoc=stmLoc.executeQuery(strSql);
              if(rstLoc.next()){
                  strFeDes=rstLoc.getString("fe_has");
              }
              
              String[] arrFeDes = strFeDes.split("-");
              
              String strFeCorHas="";
              Calendar cal = Calendar.getInstance();
              cal.set(Calendar.YEAR, Integer.parseInt(arrFeDes[0]));
              cal.set(Calendar.MONTH, Integer.parseInt(arrFeDes[1]));
              strFeCorHas=cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)-1) + "-" + cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
              
              strSql="";
              strSql+="SELECT b.co_emp, d.tx_nom as empresa, a.co_tra, (c.tx_ape || ' ' || c.tx_nom) as empleado, a.fe_dia, a.fe_jusfal,  " + "\n";
              strSql+="a.fe_utiPagJusFal, a.st_solnovext, case when a.st_solnovext is null then false::boolean else true::boolean end as blnSol,  " + "\n";
              strSql+="a.tx_obssolnovext, a.fe_solnovext, a.co_usrsolnovext,  " + "\n";
              strSql+="a.tx_comSolNovExt, st_autNovExt, case when (a.st_autNovExt is null or a.st_autNovExt='D') then false::boolean else true::boolean end as blnAut,  " + "\n";
              strSql+="case when (a.st_autNovExt is null or a.st_autNovExt='S') then false::boolean else true::boolean end as blnDen,  " + "\n";
              strSql+="tx_obsAutNovExt, fe_autNovExt, co_usrAutNovExt , tx_comAutNovExt  " + "\n";
              strSql+="FROM tbm_cabconasitra a " + "\n";
              strSql+="INNER JOIN tbm_traemp b on (b.co_tra=a.co_tra and b.st_reg='A')  " + "\n";
              strSql+="INNER JOIN tbm_tra c on (c.co_tra=b.co_tra)  " + "\n";
              strSql+="INNER JOIN tbm_emp d on (d.co_emp=b.co_emp)  " + "\n";
              strSql+="WHERE (date(a.fe_jusfal) > (select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(YEAR FROM current_timestamp) and ne_mes=EXTRACT(MONTH FROM current_timestamp) and ne_per=2)";
              strSql+="AND date(a.fe_jusfal) <= "+objUti.codificar(strFeCorHas)+") " + "\n";
              strSql+=" " + sqlFilEmp + "\n";
              strSql+=" " + strEmp + "\n";
              strSql+="ORDER BY empresa, fe_dia, empleado " + "\n";
          }
          
          System.out.println("q consul: " + strSql);
          rstLoc=stmLoc.executeQuery(strSql);
              
          vecDat = new Vector();
          while(rstLoc.next()){

                java.util.Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_DAT_FAL_LIN,"");
                vecReg.add(INT_TBL_DAT_FAL_COD_EMP,rstLoc.getInt("co_emp"));
                vecReg.add(INT_TBL_DAT_FAL_NOM_EMP,rstLoc.getString("empresa"));
                vecReg.add(INT_TBL_DAT_FAL_COD_TRA,rstLoc.getInt("co_tra"));
                vecReg.add(INT_TBL_DAT_FAL_NOM_APE_TRA,rstLoc.getString("empleado"));
                vecReg.add(INT_TBL_DAT_FAL_FE_DIA_FAL,rstLoc.getString("fe_dia"));
                vecReg.add(INT_TBL_DAT_FAL_FE_DIA_JUS_FAL,rstLoc.getString("fe_jusfal"));
                
                vecReg.add(INT_TBL_DAT_FAL_CHK_SOL,rstLoc.getBoolean("blnSol"));
                vecReg.add(INT_TBL_DAT_FAL_OBS_SOL,rstLoc.getString("tx_obssolnovext"));
                vecReg.add(INT_TBL_DAT_FAL_BUT_OBS_SOL,"...");
                
//                boolean blnAutDen=rstLoc.getBoolean("blnAutDen");
//                if(blnAutDen){
//                    vecReg.add(INT_TBL_DAT_IEPRG_CHK_AUT,true);
//                    vecReg.add(INT_TBL_DAT_IEPRG_CHK_DEN,false);
//                }else{
//                    vecReg.add(INT_TBL_DAT_IEPRG_CHK_AUT,false);
//                    vecReg.add(INT_TBL_DAT_IEPRG_CHK_DEN,true);
//                }
                
                vecReg.add(INT_TBL_DAT_FAL_CHK_AUT,rstLoc.getBoolean("blnAut"));
                vecReg.add(INT_TBL_DAT_FAL_CHK_DEN,rstLoc.getBoolean("blnDen"));
                
                vecReg.add(INT_TBL_DAT_FAL_OBS_AUT_DEN,rstLoc.getString("tx_obsautnovext"));
                vecReg.add(INT_TBL_DAT_FAL_BUT_OBS_AUT_DEN,"...");
                
                vecDataCon.add(vecReg);
            }

            objTblModFA.setData(vecDataCon);
            tblDatFal .setModel(objTblModFA);          
            lblMsgSis.setText("Listo");
            tabFrm.setSelectedIndex(1);
//            lblMsgSis.setText("Se encontraron " + tblDatFal.getRowCount() + " registros");
            pgrSis.setIndeterminate(false);
            vecDat.clear();
      }
  }catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  Evt.printStackTrace();objUti.mostrarMsgErr_F1(this, Evt);  }
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

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaFal extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDatFal.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_FAL_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_FAL_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_FAL_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;    
                case INT_TBL_DAT_FAL_COD_TRA:
                    strMsg="Código del empleado";
                    break;
                case INT_TBL_DAT_FAL_NOM_APE_TRA:
                    strMsg="Nombres y apellidos del empleado";
                    break;
                case INT_TBL_DAT_FAL_FE_DIA_FAL:
                    strMsg="Fecha de la falta";
                    break;
                case INT_TBL_DAT_FAL_FE_DIA_JUS_FAL:
                    strMsg="Fecha de justificación de la falta";
                    break;
                case INT_TBL_DAT_FAL_CHK_SOL:
                    strMsg="Solicitar";
                    break;
                case INT_TBL_DAT_FAL_OBS_SOL:
                    strMsg="Observación de la solicitud";
                    break;
                case INT_TBL_DAT_FAL_BUT_OBS_SOL:
                    strMsg="Muestra la \"Observación de la solicitud\"";
                    break;
                case INT_TBL_DAT_FAL_CHK_AUT:
                    strMsg="Autorizar";
                    break;
                case INT_TBL_DAT_FAL_CHK_DEN:
                    strMsg="Denegar";
                    break;
                case INT_TBL_DAT_FAL_OBS_AUT_DEN:
                    strMsg="Observación de la autorización";
                    break;
                case INT_TBL_DAT_FAL_BUT_OBS_AUT_DEN:
                    strMsg="Muestra la \"Observación de la autorización\"";
                    break;
                default:
                    break;
            }
            tblDatFal.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    private class ZafMouMotAdaIEPRG extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDatFal.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_IEPRG_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_IEPRG_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_IEPRG_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_DAT_IEPRG_COD_LOC:
                    strMsg="Código del local";
                    break;                    
                case INT_TBL_DAT_IEPRG_COD_TIP_DOC:
//                    strMsg="Código del tipo de documento";
                    strMsg="Código del rubro";
                    break;
                case INT_TBL_DAT_IEPRG_DES_COR_TIP_DOC:
//                    strMsg="Descripción corta del tipo de documento";
                    strMsg="Rubro";
                    break;
                case INT_TBL_DAT_IEPRG_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_IEPRG_NUM_DOC:
//                    strMsg="Número del documento";
                    strMsg="Código del egreso";
                    break;
                case INT_TBL_DAT_IEPRG_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_IEPRG_COD_TRA:
                    strMsg="Código del empleado";
                    break;
                case INT_TBL_DAT_IEPRG_NOM_APE_TRA:
                    strMsg="Nombres y apellidos del empleado";
                    break;
                case INT_TBL_DAT_IEPRG_BUT_IEPRG:
                    strMsg="Muestra el \"Ingreso/Egreso programado\"";
                    break;
                case INT_TBL_DAT_IEPRG_CHK_SOL:
                    strMsg="Solicitar";
                    break;
                case INT_TBL_DAT_IEPRG_OBS_SOL:
                    strMsg="Observación de la solicitud";
                    break;
                case INT_TBL_DAT_IEPRG_BUT_OBS_SOL:
                    strMsg="Muestra la \"Observación de la solicitud\"";
                    break;
                case INT_TBL_DAT_IEPRG_CHK_AUT:
                    strMsg="Autorizar";
                    break;
                case INT_TBL_DAT_IEPRG_CHK_DEN:
                    strMsg="Denegar";
                    break;
                case INT_TBL_DAT_IEPRG_OBS_AUT_DEN:
                    strMsg="Observación de la autorización";
                    break;
                case INT_TBL_DAT_IEPRG_BUT_OBS_AUT_DEN:
                    strMsg="Muestra la \"Observación de la autorización\"";
                    break;
                default:
                    break;
            }
            tblDatFal.getTableHeader().setToolTipText(strMsg);
        }
    }

     private boolean mostrarVenConEmp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else{
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else{
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else{
                            txtNomEmp.setText(strNomEmp);
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
     
     private boolean mostrarVenConTra(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.show();
                    if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoTra.buscar("a1.co_tra", txtCodTra.getText())){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    else{
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                        }
                        else{
                            txtCodTra.setText(strCodTra);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTra.buscar("a1.tx_ape", txtNomTra.getText())){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    else{
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                        }
                        else{
                            txtNomTra.setText(strNomTra);
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

    private boolean configurarVenConEmp(){
        boolean blnRes=true;
        String strTitVenCon="";
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            
            //Armar la sentencia SQL.            
            if(objParSis.getCodigoUsuario()==1){
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }else{
                    strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp in ("+objParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
                
            }
            else{
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp in ("+objParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
            }
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
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
    private boolean mostrarVenConOfi(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoOfi.setCampoBusqueda(2);
                    vcoOfi.setVisible(true);
                    if (vcoOfi.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    //vcoDep.setCampoBusqueda(0);
                    vcoOfi.setVisible(true);
                    if (vcoOfi.buscar("a1.co_ofi", txtCodOfi.getText()))
                    {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(3));
                    }
                    else
                    {
                        vcoOfi.setCampoBusqueda(1);
                        vcoOfi.setCriterio1(11);
                        vcoOfi.cargarDatos();
                        vcoOfi.setVisible(true);
                        if (vcoOfi.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            
                            txtCodOfi.setText(vcoOfi.getValueAt(1));
                            txtNomOfi.setText(vcoOfi.getValueAt(3));
                        }
                        else
                        {
                            txtNomOfi.setText(strDesLarOfi);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoOfi.setCampoBusqueda(2);
                    //vcoDep.setVisible(true);
                    if (vcoOfi.buscar("a1.tx_nom", txtNomOfi.getText()))
                    {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(3));
                    }
                    else
                    {
                        vcoOfi.setCampoBusqueda(2);
                        vcoOfi.setCriterio1(11);
                        vcoOfi.cargarDatos();
                        vcoOfi.setVisible(true);
                        if (vcoOfi.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodOfi.setText(vcoOfi.getValueAt(1));
                            txtNomOfi.setText(vcoOfi.getValueAt(3));
                        }
                        else
                        {
                            txtNomOfi.setText(strDesLarOfi);
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
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConOfi()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_ofi");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            
            String strSQL="select co_ofi, tx_nom from tbm_ofi where st_reg='A'";

            vcoOfi=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado Departamentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoOfi.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
     * mostrar los "Empleados".
     */
    private boolean configurarVenConTra()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ape");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            
            String strSQL="";
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' "+
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }else{
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' and co_emp = "+ objParSis.getCodigoEmpresa() + " " +
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }
            
            vcoTra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
//            vcoTra.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
//            vcoTra.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
}
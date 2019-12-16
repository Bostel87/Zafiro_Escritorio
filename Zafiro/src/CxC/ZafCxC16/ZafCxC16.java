/*
 * CxC.ZafCxC15  ne_mod
 *
 * Created on 06 de Abril de 2011, 21:10 PM
 */  
package CxC.ZafCxC16;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author  javier Ayapata         lblCli
 */
public class ZafCxC16 extends javax.swing.JInternalFrame
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN1=0;                        //Línea
    static final int INT_TBL_DAT_COD_EMP1=1;                    //Código de la empresa.
    static final int INT_TBL_DAT_COD_LOC1=2;                    //Código del local.
    static final int INT_TBL_DAT_COD_CLI1=3;                    //Código del cliente.
    static final int INT_TBL_DAT_NOM_CLI1=4;                    //Nombre del cliente.
    static final int INT_TBL_DAT_IDE_CLI1=5;                    //Nombre del cliente.
    static final int INT_TBL_DAT_VAL_PEN1=6;                   //Valor Pendiente.
    static final int INT_TBL_DAT_VAL_VEN1=7;                   //Valor por vencer.
    static final int INT_TBL_DAT_VAL_30D1=8;                   //Valor vencido (0-30 días).
    static final int INT_TBL_DAT_VAL_60D1=9;                   //Valor vencido (31-60 días).
    static final int INT_TBL_DAT_VAL_90D1=10;                   //Valor vencido (61-90 días).
    static final int INT_TBL_DAT_VAL_MAS1=11;                   //Valor vencido (Más de 90 días).

    static final int INT_TBL_DAT_LIN=0;                        //Línea
    static final int INT_TBL_DAT_COD_EMP=1;                    //Código de la empresa.
    static final int INT_TBL_DAT_COD_LOC=2;                    //Código del local.
    static final int INT_TBL_DAT_COD_CLI=3;                    //Código del cliente.
    static final int INT_TBL_DAT_NOM_CLI=4;                    //Nombre del cliente.
    static final int INT_TBL_DAT_COD_TIP_DOC=5;                //Código del Tipo de Documento.
    static final int INT_TBL_DAT_DEC_TIP_DOC=6;                //Descripción corta del Tipo de Documento.
    static final int INT_TBL_DAT_DEL_TIP_DOC=7;                //Descripción larga del Tipo de Documento.
    static final int INT_TBL_DAT_COD_DOC=8;                    //Código del documento.
    static final int INT_TBL_DAT_COD_REG=9;                    //Código del registro.
    static final int INT_TBL_DAT_NUM_DOC=10;                   //Número de documento.
    static final int INT_TBL_DAT_FEC_DOC=11;                   //Fecha del documento.
    static final int INT_TBL_DAT_DIA_CRE=12;                   //Días de crédito.
    static final int INT_TBL_DAT_FEC_VEN=13;                   //Fecha de vencimiento.
    static final int INT_TBL_DAT_POR_RET=14;                   //Porcentaje de retención.
    static final int INT_TBL_DAT_VAL_DOC=15;                   //Valor del documento.
    static final int INT_TBL_DAT_VAL_ABO=16;                   //Valor del Abono.
    static final int INT_TBL_DAT_VAL_PEN=17;                   //Valor Pendiente.
    static final int INT_TBL_DAT_VAL_VEN=18;                   //Valor por vencer.
    static final int INT_TBL_DAT_VAL_30D=19;                   //Valor vencido (0-30 días).
    static final int INT_TBL_DAT_VAL_60D=20;                   //Valor vencido (31-60 días).
    static final int INT_TBL_DAT_VAL_90D=21;                   //Valor vencido (61-90 días).
    static final int INT_TBL_DAT_VAL_MAS=22;                   //Valor vencido (Más de 90 días).
    static final int INT_TBL_DAT_COD_BAN=23;                   //Código del Banco.
    static final int INT_TBL_DAT_NOM_BAN=24;                   //Nombre del Banco.
    static final int INT_TBL_DAT_NUM_CTA=25;                   //Número de cuenta.
    static final int INT_TBL_DAT_NUM_CHQ=26;                   //Número de cheque.
    static final int INT_TBL_DAT_FEC_REC_CHQ=27;               //Fecha de recepción del cheque.
    static final int INT_TBL_DAT_FEC_VEN_CHQ=28;               //Fecha de vencimiento del cheque.
    static final int INT_TBL_DAT_VAL_CHQ=29;                   //Valor del cheque.    
      
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod, objTblMod1;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafMouMotAda1 objMouMotAda1;
    private ZafTblTot objTblTot, objTblTot1;                        //JTable de totales.
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private String strCodEmp, strDesLarEmp, strDesLarTipDoc;             //Contenido del campo al obtener el foco.
    private String strCodLoc, strNomLoc;                        //Contenido del campo al obtener el foco.
    private String strTipDoc;        //Contenido del campo al obtener el foco.
    private ZafVenCon vcoCli;                                   //Ventana de consulta "Cliente".
    private ZafVenCon vcoTipDoc;                                //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoLoc;                                   //Ventana de consulta "Local".
    private ZafVenCon vcoEmp;                                   //Ventana de consulta "Local".
    private ZafDatePicker dtpFecDoc;
    private ZafVenCon objVenConVen;
    private ZafPerUsr objPerUsr;
    private String strCodCli, strIdeCli, strDesLarCli;
    private ZafDatePicker txtFecDoc;

    String strVersion=" v 0.11 ";
    String strSqlLoc="";

    JLabel lblNomfec = new JLabel();

    String strCodVen="";    
    String strDesVen="";
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCxC16(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();

            lblEmp.setVisible(false);
            txtCodEmp.setVisible(false);
            txtDesLarEmp.setVisible(false);
            butEmp.setVisible(false);

            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                lblEmp.setVisible(true);
                txtCodEmp.setVisible(true);
                txtDesLarEmp.setVisible(true);
                butEmp.setVisible(true);

                java.awt.Color color = txtCodCli.getBackground();
                txtCodCli.setEditable(false);
                txtCodCli.setBackground(color);

                lblVen.setVisible(false);
                txtCodVen.setVisible(false);
                txtNomVen.setVisible(false);
                butVen.setVisible(false);
            }

            if (objParSis.getCodigoMenu()!=321){
                optTod.setText("Todos los proveedores");
                optFil.setText("Sólo los proveedores que cumplan el criterio seleccionado");
                lblCli.setText("Proveedor:");
                panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre de proveedor"));
                
                lblVen.setVisible(false);
                txtCodVen.setVisible(false);
                txtNomVen.setVisible(false);
                butVen.setVisible(false);                
            }
            objPerUsr=new ZafPerUsr(objParSis); 
           switch (objParSis.getCodigoMenu())
            {
                case 321: //Listado de antiguedad de saldos de clientes
                    //Habilitar/Inhabilitar las opciones según el perfil del usuario.
                    if (!objPerUsr.isOpcionEnabled(672))
                    {
                        butCon.setVisible(false);
                    }
                    if (!objPerUsr.isOpcionEnabled(673))
                    {
                        butCer.setVisible(false);
                    }
                    if (!objPerUsr.isOpcionEnabled(3942))
                    {
                        chkVenCli.setSelected(false);
                        chkVenCli.setEnabled(false);
                    }
                    if (!objPerUsr.isOpcionEnabled(3943))
                    {
                        chkVenRel.setSelected(false);
                        chkVenRel.setEnabled(false);
                    }
                    if (!objPerUsr.isOpcionEnabled(3944))
                    {
                        chkPre.setSelected(false);
                        chkPre.setEnabled(false);
                    }
                    break;
           }

             lblNomfec.setFont(new java.awt.Font("SansSerif", 0, 11));
             lblNomfec.setText("Fecha de Corte:");

              txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
              txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
              txtFecDoc.setText("");
              panFil.add(txtFecDoc);
              panFil.add(lblNomfec);
              lblNomfec.setBounds(503, 36, 92, 10);// Coordenadas para la fecha de corte
              txtFecDoc.setBounds(594, 32, 92, 20);


            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion );
            lblTit.setText(strAux);
            

        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
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
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblCli = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtDesLarEmp = new javax.swing.JTextField();
        lblEmp = new javax.swing.JLabel();
        butEmp = new javax.swing.JButton();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        txtDesCorTipDoc = new javax.swing.JTextField();
        chkMosRet = new javax.swing.JCheckBox();
        chkMosDocVen = new javax.swing.JCheckBox();
        chkMosDeb = new javax.swing.JCheckBox();
        chkMosCre = new javax.swing.JCheckBox();
        lblLoc = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtDesLarLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        panNomCli = new javax.swing.JPanel();
        lblNomCliDes = new javax.swing.JLabel();
        txtNomCliDes = new javax.swing.JTextField();
        lblNomCliHas = new javax.swing.JLabel();
        txtNomCliHas = new javax.swing.JTextField();
        txtCodCli = new javax.swing.JTextField();
        txtIdeCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblVen = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        chkVenCli = new javax.swing.JCheckBox();
        chkVenRel = new javax.swing.JCheckBox();
        chkPre = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        sppPagRea = new javax.swing.JSplitPane();
        panPagReaReg = new javax.swing.JPanel();
        spnDat1 = new javax.swing.JScrollPane();
        tblDat1 = new javax.swing.JTable();
        spnTot1 = new javax.swing.JScrollPane();
        tblTot1 = new javax.swing.JTable();
        panPagReaMovReg = new javax.swing.JPanel();
        chkDatFacMosMovReg = new javax.swing.JCheckBox();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
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
        setTitle("Título de la ventana"); // NOI18N
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
        lblTit.setText("Título de la ventana"); // NOI18N
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setSelected(true);
        optTod.setText("Todos los clientes"); // NOI18N
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
        optTod.setBounds(10, 130, 400, 20);

        bgrFil.add(optFil);
        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Sólo los clientes que cumplan el criterio seleccionado"); // NOI18N
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(10, 150, 400, 20);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCli.setText("Cliente:"); // NOI18N
        lblCli.setToolTipText("Beneficiario"); // NOI18N
        panFil.add(lblCli);
        lblCli.setBounds(30, 200, 120, 20);

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
        txtCodEmp.setBounds(70, 10, 56, 20);

        txtDesLarEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarEmpActionPerformed(evt);
            }
        });
        txtDesLarEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarEmpFocusLost(evt);
            }
        });
        panFil.add(txtDesLarEmp);
        txtDesLarEmp.setBounds(120, 10, 360, 20);

        lblEmp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblEmp.setText("Empresa:"); // NOI18N
        lblEmp.setToolTipText("Beneficiario"); // NOI18N
        panFil.add(lblEmp);
        lblEmp.setBounds(10, 10, 60, 20);

        butEmp.setText("..."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(480, 10, 20, 20);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc.setText("Tipo de Documento:"); // NOI18N
        lblTipDoc.setToolTipText("Beneficiario"); // NOI18N
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(30, 180, 120, 20);
        panFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(122, 140, 0, 0);

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
        panFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(207, 180, 430, 20);

        butTipDoc.setText("..."); // NOI18N
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panFil.add(butTipDoc);
        butTipDoc.setBounds(637, 180, 20, 20);

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
        panFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(150, 180, 56, 20);

        chkMosRet.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkMosRet.setSelected(true);
        chkMosRet.setText("Mostrar las Retenciones"); // NOI18N
        panFil.add(chkMosRet);
        chkMosRet.setBounds(420, 60, 200, 22);

        chkMosDocVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkMosDocVen.setText("Mostrar sólo los documentos vencidos"); // NOI18N
        panFil.add(chkMosDocVen);
        chkMosDocVen.setBounds(420, 80, 250, 22);

        chkMosDeb.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkMosDeb.setSelected(true);
        chkMosDeb.setText("Mostrar los Débitos"); // NOI18N
        chkMosDeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDebActionPerformed(evt);
            }
        });
        panFil.add(chkMosDeb);
        chkMosDeb.setBounds(240, 80, 160, 22);

        chkMosCre.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkMosCre.setSelected(true);
        chkMosCre.setText("Mostrar los Créditos"); // NOI18N
        chkMosCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosCreActionPerformed(evt);
            }
        });
        panFil.add(chkMosCre);
        chkMosCre.setBounds(240, 60, 160, 22);

        lblLoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblLoc.setText("Local:"); // NOI18N
        lblLoc.setToolTipText("Beneficiario"); // NOI18N
        panFil.add(lblLoc);
        lblLoc.setBounds(10, 30, 55, 20);

        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });
        txtCodLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLocFocusLost(evt);
            }
        });
        panFil.add(txtCodLoc);
        txtCodLoc.setBounds(70, 30, 56, 20);

        txtDesLarLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarLocActionPerformed(evt);
            }
        });
        txtDesLarLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarLocFocusLost(evt);
            }
        });
        panFil.add(txtDesLarLoc);
        txtDesLarLoc.setBounds(120, 30, 360, 20);

        butLoc.setText("..."); // NOI18N
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFil.add(butLoc);
        butLoc.setBounds(480, 30, 20, 20);

        panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre de cliente"));
        panNomCli.setLayout(null);

        lblNomCliDes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNomCliDes.setText("Desde:"); // NOI18N
        panNomCli.add(lblNomCliDes);
        lblNomCliDes.setBounds(12, 20, 44, 20);

        txtNomCliDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusLost(evt);
            }
        });
        panNomCli.add(txtNomCliDes);
        txtNomCliDes.setBounds(56, 20, 260, 20);

        lblNomCliHas.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNomCliHas.setText("Hasta:"); // NOI18N
        panNomCli.add(lblNomCliHas);
        lblNomCliHas.setBounds(330, 20, 40, 20);

        txtNomCliHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusLost(evt);
            }
        });
        panNomCli.add(txtNomCliHas);
        txtNomCliHas.setBounds(380, 20, 240, 20);

        panFil.add(panNomCli);
        panNomCli.setBounds(30, 240, 630, 52);

        txtCodCli.setToolTipText("Código del cliente/proveedor");
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        panFil.add(txtCodCli);
        txtCodCli.setBounds(150, 200, 56, 20);

        txtIdeCli.setToolTipText("Identificación del cliente/proveedor");
        txtIdeCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdeCliActionPerformed(evt);
            }
        });
        txtIdeCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusLost(evt);
            }
        });
        panFil.add(txtIdeCli);
        txtIdeCli.setBounds(207, 200, 100, 20);

        txtDesLarCli.setToolTipText("Nombre del cliente/proveedor");
        txtDesLarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCliActionPerformed(evt);
            }
        });
        txtDesLarCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusLost(evt);
            }
        });
        panFil.add(txtDesLarCli);
        txtDesLarCli.setBounds(307, 200, 330, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(637, 200, 20, 20);

        lblVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblVen.setText("Vendedor :");
        panFil.add(lblVen);
        lblVen.setBounds(30, 220, 100, 20);

        txtCodVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVenActionPerformed(evt);
            }
        });
        txtCodVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVenFocusLost(evt);
            }
        });
        panFil.add(txtCodVen);
        txtCodVen.setBounds(150, 220, 56, 20);

        txtNomVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVenActionPerformed(evt);
            }
        });
        txtNomVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVenFocusLost(evt);
            }
        });
        panFil.add(txtNomVen);
        txtNomVen.setBounds(207, 220, 430, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFil.add(butVen);
        butVen.setBounds(637, 220, 20, 20);

        chkVenCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkVenCli.setText("Ventas clientes");
        chkVenCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkVenCliActionPerformed(evt);
            }
        });
        panFil.add(chkVenCli);
        chkVenCli.setBounds(10, 60, 170, 22);

        chkVenRel.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkVenRel.setText("Ventas a empresas relacionadas");
        chkVenRel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkVenRelActionPerformed(evt);
            }
        });
        panFil.add(chkVenRel);
        chkVenRel.setBounds(10, 80, 230, 20);

        chkPre.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkPre.setText("Préstamos");
        chkPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPreActionPerformed(evt);
            }
        });
        panFil.add(chkPre);
        chkPre.setBounds(10, 100, 240, 20);

        tabFrm.addTab("Filtro", panFil);

        jPanel1.setLayout(new java.awt.BorderLayout());

        sppPagRea.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppPagRea.setResizeWeight(0.5);
        sppPagRea.setOneTouchExpandable(true);

        panPagReaReg.setLayout(new java.awt.BorderLayout());

        spnDat1.setPreferredSize(new java.awt.Dimension(353, 403));

        tblDat1.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDat1.setViewportView(tblDat1);

        panPagReaReg.add(spnDat1, java.awt.BorderLayout.CENTER);

        spnTot1.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTot1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot1.setViewportView(tblTot1);

        panPagReaReg.add(spnTot1, java.awt.BorderLayout.SOUTH);

        sppPagRea.setTopComponent(panPagReaReg);

        panPagReaMovReg.setMinimumSize(new java.awt.Dimension(22, 22));
        panPagReaMovReg.setPreferredSize(new java.awt.Dimension(453, 403));
        panPagReaMovReg.setLayout(new java.awt.BorderLayout());

        chkDatFacMosMovReg.setText("Mostrar los creditos/debitos del cliente seleccionado"); // NOI18N
        chkDatFacMosMovReg.setPreferredSize(new java.awt.Dimension(269, 20));
        chkDatFacMosMovReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDatFacMosMovRegActionPerformed(evt);
            }
        });
        panPagReaMovReg.add(chkDatFacMosMovReg, java.awt.BorderLayout.NORTH);

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
        spnDat.setViewportView(tblDat);

        panPagReaMovReg.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot.setViewportView(tblTot);

        panPagReaMovReg.add(spnTot, java.awt.BorderLayout.SOUTH);

        sppPagRea.setBottomComponent(panPagReaMovReg);

        jPanel1.add(sppPagRea, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", jPanel1);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar"); // NOI18N
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado."); // NOI18N
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butCer.setText("Cerrar"); // NOI18N
        butCer.setToolTipText("Cierra la ventana."); // NOI18N
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

        lblMsgSis.setText("Listo"); // NOI18N
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

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:
        if(optTod.isSelected())
        {
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodCli.setText("");
            txtDesLarCli.setText("");
            txtNomCliDes.setText("");
            txtNomCliHas.setText("");
            txtIdeCli.setText("");
            txtCodVen.setText("");
            txtNomVen.setText("");
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void txtNomCliHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusLost
        // TODO add your handling code here:
        if (txtNomCliHas.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtNomCliHasFocusLost

    private void txtNomCliHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusGained
        // TODO add your handling code here:
        txtNomCliHas.selectAll();
    }//GEN-LAST:event_txtNomCliHasFocusGained

    private void txtNomCliDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusLost
        // TODO add your handling code here:
        if (txtNomCliDes.getText().length()>0)
        {
            optFil.setSelected(true);
            if (txtNomCliHas.getText().length()==0)
                txtNomCliHas.setText(txtNomCliDes.getText());
        }
    }//GEN-LAST:event_txtNomCliDesFocusLost

    private void txtNomCliDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusGained
        // TODO add your handling code here:
        txtNomCliDes.selectAll();
    }//GEN-LAST:event_txtNomCliDesFocusGained

    private void chkMosCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosCreActionPerformed
        // TODO add your handling code here:
        if (!chkMosDeb.isSelected())
        {
            chkMosCre.setSelected(true);
        }
    }//GEN-LAST:event_chkMosCreActionPerformed

    private void chkMosDebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDebActionPerformed
        // TODO add your handling code here:
        if (!chkMosCre.isSelected())
        {
            chkMosDeb.setSelected(true);
        }
    }//GEN-LAST:event_chkMosDebActionPerformed

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        // TODO add your handling code here:
        mostrarVenConLoc(0);
    }//GEN-LAST:event_butLocActionPerformed

    private void txtDesLarLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarLocFocusLost
        // TODO add your handling code here:
        if (txtDesLarLoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarLoc.getText().equalsIgnoreCase(strNomLoc))
            {
                if (txtDesLarLoc.getText().equals(""))
                {
                    txtCodLoc.setText("");
                    txtDesLarLoc.setText("");
                }
                else
                {
                    if(!mostrarVenConLoc(2)){
                        txtCodLoc.setText("");
                        txtDesLarLoc.setText("");
                    }
                }
            }
            else
                txtDesLarLoc.setText(strNomLoc);
        }
    }//GEN-LAST:event_txtDesLarLocFocusLost

    private void txtDesLarLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarLocFocusGained
        // TODO add your handling code here:
        strNomLoc=txtDesLarLoc.getText();
        txtDesLarLoc.selectAll();
    }//GEN-LAST:event_txtDesLarLocFocusGained

    private void txtDesLarLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarLocActionPerformed
        // TODO add your handling code here:
        txtDesLarLoc.transferFocus();
    }//GEN-LAST:event_txtDesLarLocActionPerformed

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        // TODO add your handling code here:
        if (txtCodLoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc))
            {
                if (txtCodLoc.getText().equals(""))
                {
                    txtCodLoc.setText("");
                    txtDesLarLoc.setText("");
                }
                else
                {
                   if(!mostrarVenConLoc(1)){
                        txtCodLoc.setText("");
                        txtDesLarLoc.setText("");
                    }
                }
            }
            else
                txtCodLoc.setText(strCodLoc);
        }
    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        // TODO add your handling code here:
        strCodLoc=txtCodLoc.getText();
        txtCodLoc.selectAll();
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        // TODO add your handling code here:
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        // TODO add your handling code here:
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(1);
            }
        }
        else
        {
            txtDesCorTipDoc.setText(strTipDoc);
        }
        
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtDesCorTipDoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }

        
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        // TODO add your handling code here:
        strTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        // TODO add your handling code here:
        txtDesCorTipDoc.transferFocus();        
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        // TODO add your handling code here:
            mostrarVenConTipDoc(0);
           if (txtDesCorTipDoc.getText().length()>0)
        {
            optFil.setSelected(true);//bostel
        }
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        // TODO add your handling code here:
         //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");//bostel
            }
            else
            {
                mostrarVenConTipDoc(2);
            }
        }
        else
        {
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
        
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtDesLarTipDoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }

    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        // TODO add your handling code here:
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        // TODO add your handling code here:
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged

    }//GEN-LAST:event_optFilItemStateChanged

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtDesLarEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarEmpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarEmp.getText().equalsIgnoreCase(strDesLarEmp))
        {
            if (txtDesLarEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtDesLarEmp.setText("");
            }
            else
            {
                mostrarVenConEmp(2);
            }
        }
        else
        {
            txtDesLarEmp.setText(strDesLarEmp);
            
        }        
    }//GEN-LAST:event_txtDesLarEmpFocusLost

    private void txtDesLarEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarEmpFocusGained
        strDesLarEmp=txtDesLarEmp.getText();
        txtDesLarEmp.selectAll();
    }//GEN-LAST:event_txtDesLarEmpFocusGained

    private void txtDesLarEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarEmpActionPerformed
        txtDesLarEmp.transferFocus();
    }//GEN-LAST:event_txtDesLarEmpActionPerformed

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp))
        {
            if (txtCodEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtDesLarEmp.setText("");
            }
            else
            {
                mostrarVenConEmp(1);
            }
        }
        else
        {
            txtCodEmp.setText(strCodEmp);
        }
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        strCodEmp=txtCodEmp.getText();
        txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged

    }//GEN-LAST:event_optTodItemStateChanged

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (isCamVal()){ // Para que aparezca la ventana cuando los filtros por ventas a clienes, relacionada y prestamos no estan activas
            if (butCon.getText().equals("Consultar"))
            {                
                if (objThrGUI==null)
                {
                    objThrGUI=new ZafThreadGUI();
                    objThrGUI.start();
                }
            }
       }

    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        //javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
}//GEN-LAST:event_txtCodCliActionPerformed

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
}//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)) {
            if (txtCodCli.getText().equals("")) {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtDesLarCli.setText("");
            } else {
                mostrarVenConCli(1);
            }
        } else
            txtCodCli.setText(strCodCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtCodCliFocusLost

    private void txtIdeCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdeCliActionPerformed
        txtIdeCli.transferFocus();
}//GEN-LAST:event_txtIdeCliActionPerformed

    private void txtIdeCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeCliFocusGained
        strIdeCli=txtIdeCli.getText();
        txtIdeCli.selectAll();
}//GEN-LAST:event_txtIdeCliFocusGained

    private void txtIdeCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtIdeCli.getText().equalsIgnoreCase(strIdeCli)) {
            if (txtIdeCli.getText().equals("")) {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtDesLarCli.setText("");
            } else {
                mostrarVenConCli(2);
            }
        } else
            txtIdeCli.setText(strIdeCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtIdeCliFocusLost

    private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
        txtDesLarCli.transferFocus();
}//GEN-LAST:event_txtDesLarCliActionPerformed

    private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
        strDesLarCli=txtDesLarCli.getText();
        txtDesLarCli.selectAll();
}//GEN-LAST:event_txtDesLarCliFocusGained

    private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli)) {
            if (txtDesLarCli.getText().equals("")) {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtDesLarCli.setText("");
            } else {
                mostrarVenConCli(3);
            }
        } else
            txtDesLarCli.setText(strDesLarCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtDesLarCliFocusLost

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butCliActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:

             configurarFrm();
             configurarFrm1();
          ///Configurar Ventana de Cliente///
            configurarVenConCli();
            configurarVenConTipDoc();
            configurarVenConLoc();
            configurarVenConEmp();
            configurarVenConVendedor();           
            


    }//GEN-LAST:event_formInternalFrameOpened

    private void chkDatFacMosMovRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDatFacMosMovRegActionPerformed
        // TODO add your handling code here:
//        if(objParSis.getCodigoMenu()==645) {
//            if (chkDatFacMosMovReg.isSelected()) {
//                System.out.println("---Esta activo el chk de movreg--- y se cargan los datos---menu=645");
//                cargarDetRegCorFec();
//            } else
//                objTblMod.removeAllRows();
//        }
//        ///else
//        if(objParSis.getCodigoMenu()==321) {
            if (chkDatFacMosMovReg.isSelected()) {
                if(strEstFec.equals("[ERROR]"))
                 cargarDetRegCli(strBusIni);
                else
                   cargarDetRegCorFecCli(strBusIni);
            } else
                objTblMod.removeAllRows();
//        }
}//GEN-LAST:event_chkDatFacMosMovRegActionPerformed

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        // TODO add your handling code here:
        txtCodVen.transferFocus();
    }//GEN-LAST:event_txtCodVenActionPerformed

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        // TODO add your handling code here:
        strCodVen=txtCodVen.getText();
        txtCodVen.selectAll();
    }//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        // TODO add your handling code here:
        if (!txtCodVen.getText().equalsIgnoreCase(strCodVen)) {
            if(txtCodVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }else
            BuscarVendedor("a.co_usr",txtCodVen.getText(),0);
        }else
        txtCodVen.setText(strCodVen);
    }//GEN-LAST:event_txtCodVenFocusLost

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        // TODO add your handling code here:
        txtNomVen.transferFocus();
    }//GEN-LAST:event_txtNomVenActionPerformed

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        // TODO add your handling code here:
        strDesVen=txtNomVen.getText();
        txtNomVen.selectAll();
    }//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        // TODO add your handling code here:
        if (!txtNomVen.getText().equalsIgnoreCase(strDesVen)) {
            if(txtNomVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }else
            BuscarVendedor("a.tx_nom",txtNomVen.getText(),1);
        }else
        txtNomVen.setText(strDesVen);
    }//GEN-LAST:event_txtNomVenFocusLost

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        // TODO add your handling code here:
        objVenConVen.setTitle("Listado de Clientes");
        objVenConVen.setCampoBusqueda(1);
        objVenConVen.show();
        if (objVenConVen.getSelectedButton()==objVenConVen.INT_BUT_ACE) {
            txtCodVen.setText(objVenConVen.getValueAt(1));
            txtNomVen.setText(objVenConVen.getValueAt(2));
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butVenActionPerformed

    private void chkVenCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkVenCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkVenCliActionPerformed

    private void chkVenRelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkVenRelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkVenRelActionPerformed

    private void chkPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkPreActionPerformed

 public void BuscarVendedor(String campo,String strBusqueda,int tipo){
  objVenConVen.setTitle("Listado de Vendedores");
  if(objVenConVen.buscar(campo, strBusqueda )) {
      txtCodVen.setText(objVenConVen.getValueAt(1));
      txtNomVen.setText(objVenConVen.getValueAt(2));
      optFil.setSelected(true);
  }else{
        objVenConVen.setCampoBusqueda(tipo);
        objVenConVen.cargarDatos();
        objVenConVen.show();
        if (objVenConVen.getSelectedButton()==objVenConVen.INT_BUT_ACE) {
            txtCodVen.setText(objVenConVen.getValueAt(1));
            txtNomVen.setText(objVenConVen.getValueAt(2));
            optFil.setSelected(true);
        }else{
            txtCodVen.setText(strCodVen);
            txtNomVen.setText(strDesVen);
  }}}   
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butVen;
    private javax.swing.JCheckBox chkDatFacMosMovReg;
    private javax.swing.JCheckBox chkMosCre;
    private javax.swing.JCheckBox chkMosDeb;
    private javax.swing.JCheckBox chkMosDocVen;
    private javax.swing.JCheckBox chkMosRet;
    private javax.swing.JCheckBox chkPre;
    private javax.swing.JCheckBox chkVenCli;
    private javax.swing.JCheckBox chkVenRel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNomCliDes;
    private javax.swing.JLabel lblNomCliHas;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JPanel panPagReaMovReg;
    private javax.swing.JPanel panPagReaReg;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnDat1;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JScrollPane spnTot1;
    private javax.swing.JSplitPane sppPagRea;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDat1;
    private javax.swing.JTable tblTot;
    private javax.swing.JTable tblTot1;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtDesLarEmp;
    private javax.swing.JTextField txtDesLarLoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtIdeCli;
    private javax.swing.JTextField txtNomCliDes;
    private javax.swing.JTextField txtNomCliHas;
    private javax.swing.JTextField txtNomVen;
    // End of variables declaration//GEN-END:variables
   
    //estos tres metodos me sirven para calcular los dias  que han transcurrido de una fecha a la actual
    /**
     * Esta función permite calcular el numero de dias transcurridos entre 2 fechas.
     *@param intAniIni,intMesIni,intDiaIni,intAniFin,intMesFin,intDiaFin recibe 6 enteros que representan el anio inicial y final, mes inicial y final, dia inicial y final
     *@return intNumDia: Retorna el numero de dias.
     * 
     */
    

   /**
     * Esta función permite verificar si un anio es bisiesto o no
     *@param intAni recibe 1 entero que representan el anio que queremos verificar 
     *@return true: Booleano que indica si es bisiesto
     * <Br> false: Si no es bisiesto
     */
    private boolean blnSiBis(int intAni){
    int intMod1,intMod2,intMod3;
    intMod1=intAni%4;
    intMod2=intAni%100;
    intMod3=intAni%400;
    if(intMod1==0){
        if(intMod2==0){
            if(intMod3==0){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }else{
        return false;
    }
}
    /**
     * Esta función calcula el numero de dias que han transcurrido en un año hasta la fecha dada
     *@param anio,mes,dia recibe 3 enteros que representan el anio, el mes y el dia  respectivamente 
     *@return numDia: entero con el numero de dias que han transcurrido en ese año hasta ese dia de ese mes
     */

    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
           
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(30);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            
            if (objParSis.getCodigoMenu()==321){
                vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
                vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente");                
            } else{
                vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Prv.");
                vecCab.add(INT_TBL_DAT_NOM_CLI,"Proveedor");                                
            }
            
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_DIA_CRE,"Día.Cre.");
            vecCab.add(INT_TBL_DAT_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_DAT_POR_RET,"Por.Ret.");
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Doc.");            
            vecCab.add(INT_TBL_DAT_VAL_ABO,"Val.Abo.");
            vecCab.add(INT_TBL_DAT_VAL_PEN,"Val.Pen.");
            vecCab.add(INT_TBL_DAT_VAL_VEN,"Val.Por.Ven.");
            vecCab.add(INT_TBL_DAT_VAL_30D,"0-30");
            vecCab.add(INT_TBL_DAT_VAL_60D,"31-60");
            vecCab.add(INT_TBL_DAT_VAL_90D,"61-90");
            vecCab.add(INT_TBL_DAT_VAL_MAS,"+90");
            vecCab.add(INT_TBL_DAT_COD_BAN,"Cód.Ban.");
            vecCab.add(INT_TBL_DAT_NOM_BAN,"Banco");
            vecCab.add(INT_TBL_DAT_NUM_CTA,"Núm.Cta.");
            vecCab.add(INT_TBL_DAT_NUM_CHQ,"Núm.Chq.");
            vecCab.add(INT_TBL_DAT_FEC_REC_CHQ,"Fec.Rec.Chq.");
            vecCab.add(INT_TBL_DAT_FEC_VEN_CHQ,"Fec.Ven.Chq.");
            vecCab.add(INT_TBL_DAT_VAL_CHQ,"Val.Chq.");

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
          

             //Configurar JTable: Establecer la fila de cabecera.
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_DAT_LIN);


            //Configurar JTable: Establecer el menú de contexto.
            new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            new ZafTblOrd(tblDat);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(10);//antes estaba 30
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(20);//antes estaba 30
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(140);//antes estaba 70
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEC_TIP_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);//antes 80            
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FEC_VEN).setPreferredWidth(95);
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_ABO).setPreferredWidth(1);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VAL_VEN).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_30D).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_60D).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_90D).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_MAS).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_COD_BAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_BAN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CHQ).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_REC_CHQ).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_VEN_CHQ).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(95);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CHQ).setPreferredWidth(95);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
           
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_DAT_COD_CLI);
            arlColHid.add(""+INT_TBL_DAT_NOM_CLI);
            arlColHid.add(""+INT_TBL_DAT_COD_EMP);
            arlColHid.add(""+INT_TBL_DAT_COD_TIP_DOC);
            arlColHid.add(""+INT_TBL_DAT_COD_DOC);
            arlColHid.add(""+INT_TBL_DAT_COD_REG);
            arlColHid.add(""+INT_TBL_DAT_VAL_ABO);
            arlColHid.add(""+INT_TBL_DAT_DEL_TIP_DOC);
            arlColHid.add(""+INT_TBL_DAT_VAL_DOC);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;


            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            new ZafTblBus(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();//inincializo el renderizador
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_ABO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_VEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_30D).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_60D).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_90D).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_MAS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblDat.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLis());
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_VAL_DOC, INT_TBL_DAT_VAL_ABO, INT_TBL_DAT_VAL_PEN, INT_TBL_DAT_VAL_VEN, INT_TBL_DAT_VAL_30D, INT_TBL_DAT_VAL_60D, INT_TBL_DAT_VAL_90D, INT_TBL_DAT_VAL_MAS, INT_TBL_DAT_VAL_CHQ};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
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



  private boolean configurarFrm1()
    {
        boolean blnRes=true;
        try
        {
      //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN1,"");
            vecCab.add(INT_TBL_DAT_COD_EMP1,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC1,"Cód.Loc.");
            
            if (objParSis.getCodigoMenu()==321){
                vecCab.add(INT_TBL_DAT_COD_CLI1,"Cód.Cli.");
                vecCab.add(INT_TBL_DAT_NOM_CLI1,"Cliente");                
            } else{
                vecCab.add(INT_TBL_DAT_COD_CLI1,"Cód.Prv.");
                vecCab.add(INT_TBL_DAT_NOM_CLI1,"Proveedor");                                
            }
            
            vecCab.add(INT_TBL_DAT_IDE_CLI1,"IDE");
            vecCab.add(INT_TBL_DAT_VAL_PEN1,"Val.Pen.");
            vecCab.add(INT_TBL_DAT_VAL_VEN1,"Val.Por.Ven.");
            vecCab.add(INT_TBL_DAT_VAL_30D1,"0-30");
            vecCab.add(INT_TBL_DAT_VAL_60D1,"31-60");
            vecCab.add(INT_TBL_DAT_VAL_90D1,"61-90");
            vecCab.add(INT_TBL_DAT_VAL_MAS1,"+90");
        
            objTblMod1=new ZafTblMod();
            objTblMod1.setHeader(vecCab);
            tblDat1.setModel(objTblMod1);
            //Configurar JTable: Establecer tipo de selección.
            tblDat1.setRowSelectionAllowed(true);
            tblDat1.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            
               //Configurar JTable: Establecer la fila de cabecera.
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat1,INT_TBL_DAT_LIN1);


            //Configurar JTable: Establecer el menú de contexto.
            new ZafTblPopMnu(tblDat1);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            new ZafTblOrd(tblDat1);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat1.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN1).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP1).setPreferredWidth(10);//antes estaba 30
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC1).setPreferredWidth(20);//antes estaba 30
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI1).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI1).setPreferredWidth(350);//antes estaba 70
            tcmAux.getColumn(INT_TBL_DAT_IDE_CLI1).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN1).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VAL_VEN1).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_30D1).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_60D1).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_90D1).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_MAS1).setPreferredWidth(95);//antes estaba 60
           
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat1.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.

            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_DAT_COD_EMP1);
            arlColHid.add(""+INT_TBL_DAT_IDE_CLI1);
            objTblMod1.setSystemHiddenColumns(arlColHid, tblDat1);
            arlColHid=null;


            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda1=new ZafMouMotAda1();
            tblDat1.getTableHeader().addMouseMotionListener(objMouMotAda1);
            //Configurar JTable: Editor de búsqueda.
            new ZafTblBus(tblDat1);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI1).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();//inincializo el renderizador
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN1).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_VEN1).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_30D1).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_60D1).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_90D1).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_MAS1).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblDat.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLis());
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={ INT_TBL_DAT_VAL_PEN1, INT_TBL_DAT_VAL_VEN1, INT_TBL_DAT_VAL_30D1, INT_TBL_DAT_VAL_60D1, INT_TBL_DAT_VAL_90D1, INT_TBL_DAT_VAL_MAS1 };
            objTblTot1=new ZafTblTot(spnDat1, spnTot1, tblDat1, tblTot1, intCol);
            //Libero los objetos auxiliares.


             //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsm=tblDat1.getSelectionModel();
            lsm.addListSelectionListener(new ZafLisSelLisDatFac());




            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


     /**
     * Esta clase implementa la interface "ListSelectionListener" para determinar
     * cambios en la selección. Es decir, cada vez que se selecciona una fila
     * diferente en el JTable se ejecutará el "ListSelectionListener".
     */
    private class ZafLisSelLisDatFac implements javax.swing.event.ListSelectionListener
    {
        public void valueChanged(javax.swing.event.ListSelectionEvent e)
        {
            javax.swing.ListSelectionModel lsm=(javax.swing.ListSelectionModel)e.getSource();
            int intMax=lsm.getMaxSelectionIndex();
            String strAux;
            if (!lsm.isSelectionEmpty())
            {
              if (chkDatFacMosMovReg.isSelected()) {
                if(strEstFec.equals("[ERROR]"))
                 cargarDetRegCli(strBusIni);
                else
                   cargarDetRegCorFecCli(strBusIni);
               } else
                objTblMod.removeAllRows();

            }
            else
                objTblMod.removeAllRows();
        }
    }

private boolean configurarVenConVendedor() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("a.co_usr");
        arlCam.add("a.tx_nom");
        ArrayList arlAli=new ArrayList();
        arlAli.add("Código");
        arlAli.add("Nombre.");
        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("70");
        arlAncCol.add("470");
        //Armar la sentencia SQL.
        String  strSQL="";
        strSQL="select a.co_usr, a.tx_nom  from tbr_usremp as b" +
        " inner join tbm_usr as a on (a.co_usr=b.co_usr) " +
        " where b.co_emp="+objParSis.getCodigoEmpresa()+" and b.st_ven='S' and a.st_reg not in ('I')  order by a.tx_nom";

        objVenConVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
   return blnRes;
 }
    
    /*
     * MODIFICADO EFLORESA 2012-04-04
     * SE HACE LA CONSULTA SOLO LOS CLIENTES DEL LOCAL SELECCIONADO O EMPRESA/GRUPO
     * 
     */    
   private boolean configurarVenConCli() {
        boolean blnRes=true;
        try {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");
            /*if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT ' ' AS co_cli, b2.tx_ide, b2.tx_nom, b2.tx_dir";
                strSQL+=" FROM (";
                strSQL+=" SELECT a2.co_emp, MAX(a2.co_cli) AS co_cli, a2.tx_ide";
                strSQL+=" FROM (";
                strSQL+=" SELECT MIN(co_emp) AS co_emp, tx_ide";
                strSQL+=" FROM tbm_cli";
                strSQL+=" GROUP BY tx_ide";
                strSQL+=" ) AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide)";
                strSQL+=" GROUP BY a2.co_emp, a2.tx_ide";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN tbm_cli AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cli=b2.co_cli)";
                strSQL+=" AND b2.st_cli='S' AND b2.st_reg='A'";
                strSQL+=" ORDER BY b2.tx_nom";
            }
            else {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_cli='S' AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_nom";
            }*/
            
            //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
            if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))            {
                //Armar la sentencia SQL.
                strSQL=" SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir " + 
                       " FROM tbm_cli AS a1 " +
                       " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " " +
                       " AND a1.st_reg='A' " +
//                       " and a1.st_cli='S' " +
                       ((objParSis.getCodigoMenu()==321) ? " and a1.st_cli='S' " : " and a1.st_prv='S' ") +
                       " ORDER BY a1.tx_nom";
            }else {
                //Armar la sentencia SQL.                
                strSQL=" select a.co_cli, a.tx_ide, a.tx_nom, a.tx_dir " +
                       " from tbm_cli a, tbr_cliloc b " +
                       " where a.co_emp = b.co_emp " +
                       " and a.co_cli = b.co_cli " +
                       " and a.co_emp=" + objParSis.getCodigoEmpresa() + " " +
                       " and b.co_loc=" +objParSis.getCodigoLocal() + " " + 
                       " and a.st_reg='A' " +
//                       " and a.st_cli='S' " + 
                       ((objParSis.getCodigoMenu()==321) ? " and a.st_cli='S' " : " and a.st_prv='S' ") +
                       " order by a.tx_nom ";   
                
//                strSQL= " select distinct a.co_cli, a.tx_ide, a.tx_nom, a.tx_dir " +
//                        " from tbr_locprgusr c " +
//                        " inner join tbr_cliloc b on (b.co_emp = c.co_emprel and b.co_loc = c.co_locrel) " +
//                        " inner join tbm_cli a on (a.co_emp = b.co_emp and a.co_cli = b.co_cli) " +
//                        " where c.co_emp=" + objParSis.getCodigoEmpresa() + " " +
//                        " and c.co_loc=" +objParSis.getCodigoLocal() + " " + 
//                        " and c.co_usr=" + objParSis.getCodigoUsuario() + " " +
//                        " and c.co_mnu=" + objParSis.getCodigoMenu() + " " +
//                        " and a.st_reg='A' " +
//                        ((objParSis.getCodigoMenu()==321) ? " and a.st_cli='S' " : " and a.st_prv='S' ") +
//                        " order by a.tx_nom ";                
            }            
            
            vcoCli=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, JLabel.RIGHT);
            vcoCli.setConfiguracionColumna(2, JLabel.RIGHT);
        }catch (Exception e) {
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
            arlCam.add("a1.tx_natDoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc ";
            strSQL+=" FROM tbm_cabTipDoc AS a1 ";
            strSQL+=" WHERE ";
            strSQL+=" a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
            
            if (objParSis.getCodigoMenu()==321){
                strSQL+=" AND a1.ne_mod IN (1,3)" ;
            } else{
                strSQL+=" AND a1.ne_mod IN (2,4)" ;                
            }
                
                
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=5;
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Locales". 
     * 
     * MOFICADO EFLORESA 2012-04-03
     * MOSTRAR LOCALES DE ACUERDO A TIPO DE USUARIO
     * 
     */
    private boolean configurarVenConLoc() {
        boolean blnRes=true;
        try {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_loc");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("492");
                        
            /*Query mejorado para consultar LOCALES filtrado por local y empresa*/            
            //Armar la sentencia SQL.            
           
                /*strSQL="";
                strSQL="";
                strSQL+="SELECT a1.co_loc, a1.tx_nom";
                strSQL+=" FROM tbm_loc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg IN ('A', 'P')";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc";

                strSqlLoc= "select * from ( "+strSQL+" ) as x ";*/
                
                //Cargar el listado de locales de acuerdo al usuario.
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los locales.
                if (objParSis.getCodigoUsuario()==1) {
                strSQL="SELECT a1.co_loc,a1.tx_nom " +
                                " FROM tbm_loc AS a1 " +
                                " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " " +
                                " ORDER BY a1.st_reg DESC, a1.co_loc";
                }else {
                strSQL= "SELECT a1.co_loc, a1.tx_nom " + 
                                " FROM tbm_loc AS a1 " +
                                " INNER JOIN tbr_locUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc) " +
                                " WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + " "  +
                                " AND a2.co_usr=" + objParSis.getCodigoUsuario() + " " +
                                " AND a2.st_reg IN ('A', 'P') " +
                                " ORDER BY a2.st_reg DESC, a2.co_loc";
                }                
            
            vcoLoc=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, "Listado de locales", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoLoc.setConfiguracionColumna(1, JLabel.RIGHT);
        }catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Empresas".
     */
    private boolean configurarVenConEmp()
    {
        boolean blnRes=true;
        try
        {
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
            arlAncCol.add("492");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_emp, a1.tx_nom";
            strSQL+=" FROM tbm_emp AS a1  where a1.co_emp != "+objParSis.getCodigoEmpresaGrupo()+" ";
            strSQL+=" ORDER BY a1.co_emp ";
            
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de locales", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /*
    * @modificado EFLORESA 2012-03-23
    * Se agrega al filtro de Mostrar documentos vencidos (chkMosDocVen)
    * que tome la fecha parametrizada o que tome la fecha del sistema segun sea el caso.
    */    
    private String filtro(){
        String strAux="", strAux1="";
        String strAuxCreDeb="";
        
        try{
//            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
//                if(!txtCodLoc.getText().equals("")){
//                    strAux+=" and a.co_loc="+txtCodLoc.getText()+" ";
//                }
//            } else{
//                strAux+=" and a.co_loc="+objParSis.getCodigoLocal()+" ";
//            }
           // filtro de ventas por clientes, empresas relacionada y prestamos             
             if (chkVenCli.isSelected())
                {
                    strAux1+="cli.co_empGrp IS NULL";
                }
                if (chkVenRel.isSelected())
                {
                    strAux1+=(strAux1.equals("")?"": " OR ") + "a2.co_cat IN (3, 4) AND cli.co_empGrp IS NOT NULL";
                }
                if (chkPre.isSelected())
                {
                    strAux1+=(strAux1.equals("")?"": " OR ") + "a2.co_cat IN (23)";
                }
                
                strAux+=" AND (" + strAux1 + ")";    
            
            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                if(!txtCodLoc.getText().equals("")){
                     strAux+=" and a.co_loc="+txtCodLoc.getText()+" ";
                 }
            } else{
                if(!txtCodLoc.getText().equals("")){
                     strAux+=" and a.co_loc="+txtCodLoc.getText()+" ";
                 } else {
                     strAux+=" and a.co_loc="+objParSis.getCodigoLocal()+" ";                    
                }
            }
            
            if (objParSis.getCodigoMenu()==321){
                if (chkMosCre.isSelected()){
                    strAuxCreDeb+="  a1.mo_pag > 0  ";
                }
                
                if (chkMosDeb.isSelected()){
                   if( !strAuxCreDeb.equals("") )   strAuxCreDeb+=" or a1.mo_pag < 0  ";
                   else  strAuxCreDeb+=" a1.mo_pag < 0  ";
                }               
            } else{
                if (chkMosCre.isSelected()){
                    strAuxCreDeb+="  a1.mo_pag < 0  ";
                }
                
                if (chkMosDeb.isSelected()){
                   if( !strAuxCreDeb.equals("") )   strAuxCreDeb+=" or a1.mo_pag > 0  ";
                   else  strAuxCreDeb+=" a1.mo_pag > 0  ";
                }                               
            }
            
            if ( !strAuxCreDeb.equals("") ) strAux+=" and ( "+strAuxCreDeb+" ) ";
            if (!chkMosRet.isSelected()) strAux+=" and a1.nd_porret = 0 ";
            
            //if(chkMosDocVen.isSelected()) strAux+=" and (current_date - a1.fe_ven ) >= 0 ";
            
            String fecdesde = objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd");
            if (chkMosDocVen.isSelected()) if (fecdesde.equals("[ERROR]")) strAux+=" and (current_date - a1.fe_ven ) >= 0 "; else strAux+=" and ('"+fecdesde+"' - a1.fe_ven ) >= 0 ";

            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                if (!txtCodEmp.getText().equals(""))  strAux+=" and a1.co_emp ="+txtCodEmp.getText()+" ";

                if (optFil.isSelected()){
                   if(!txtCodTipDoc.getText().equals("")) 
                       strAux+=" and a.co_tipdoc="+txtCodTipDoc.getText()+" ";
                   else {
                       if (objParSis.getCodigoMenu()==321){
                           strAux+=" AND a2.ne_mod IN (1,3) ";
                       } else{
                           strAux+=" AND a2.ne_mod IN (2,4) ";
                       }
                   }                       

                   if (!txtIdeCli.getText().equals("")) strAux+=" and cli.tx_ide = '"+txtIdeCli.getText()+"'   ";

                   if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0){
                       strAux+="  AND (  ((LOWER(cli.tx_nom) BETWEEN LOWER('"+txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "') AND LOWER('" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') ) OR LOWER(cli.tx_nom) LIKE LOWER('" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%') ) ) ";
                   }
                } else{
                    if (objParSis.getCodigoMenu()==321){
                        strAux+=" AND a2.ne_mod IN (1,3) ";
                    } else{
                        strAux+=" AND a2.ne_mod IN (2,4) ";
                    }                    
                 }
            } else{
                strAux+="   and a.co_emp = "+objParSis.getCodigoEmpresa()+" ";

                if (optFil.isSelected()){
                    if (!txtCodTipDoc.getText().equals("")) 
                        strAux+=" and a.co_tipdoc="+txtCodTipDoc.getText()+" ";
                    else {
                        if (objParSis.getCodigoMenu()==321){
                            strAux+=" AND a2.ne_mod IN (1,3) ";
                        } else{
                            strAux+=" AND a2.ne_mod IN (2,4) ";
                        }
                    }                       

                    if (!txtCodCli.getText().equals("")) strAux+=" and a.co_cli="+txtCodCli.getText()+" ";

                    if (!txtCodVen.getText().equals("")) strAux+=" and a.co_com="+txtCodVen.getText()+" ";

                    if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0){
                        strAux+="  AND (  ((LOWER(cli.tx_nom) BETWEEN LOWER('"+txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "') AND LOWER('" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') ) OR LOWER(cli.tx_nom) LIKE LOWER('" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%') ) ) ";
                    }
                } else{
                    if (objParSis.getCodigoMenu()==321){
                        strAux+=" AND a2.ne_mod IN (1,3) ";
                    } else{
                        strAux+=" AND a2.ne_mod IN (2,4) ";
                    }
                }

           }
    } catch (Exception e){
        strAux="";
        objUti.mostrarMsgErr_F1(this, e);
    }          

    return strAux;
  }




private boolean cargarDetReg(String strAux){
boolean blnRes=true;
java.sql.Statement stmLoc;
java.sql.ResultSet rstLoc;
String strSql = "";
try
{
   butCon.setText("Detener");
   lblMsgSis.setText("Obteniendo datos...");

        java.sql.Connection con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
        if (con!=null)
        {

            stmLoc=con.createStatement();

            strBusIni=strAux;
            
         
          
                strSql =" select "
                + " case when dias < 0 then valpen else 0 end as valporven   "
                + ",case when dias between 0 and 30 then valpen else 0 end as val_1_30 "
                + ",case when dias between 31 and 60 then valpen else 0 end as val_31_60 "
                + ",case when dias between 61 and 90 then valpen else 0 end as val_61_90 "
                + " ,case when dias  > 90 then valpen else 0 end as val_mas_90 "
                + " ,* from ( "
                + " select cli.tx_ide,  "
                + " a1.co_banchq, a3.tx_deslar as nomban, a1.tx_numctachq, a1.tx_numchq, a1.fe_recchq, a1.fe_venchq, a1.nd_monchq, "+
                "  a.co_emp, a.co_loc, a.co_cli, a.tx_nomcli, a.co_tipdoc, a2.tx_descor, a2.tx_deslar,  a.co_doc, a1.co_reg, a.ne_numdoc, "
                +" a.fe_doc, a1.ne_diacre, a1.fe_ven, a1.nd_porret "
                + "  ,a1.mo_pag,  (a1.mo_pag+a1.nd_abo) as valpen "
                + "  , (current_date - a1.fe_ven )  as dias  "
                + " from tbm_cabmovinv as a  "
                + " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
                + " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc ) "
                + " inner join tbm_cli as cli on (cli.co_emp=a.co_emp and cli.co_cli=a.co_cli ) "
                + " left join tbm_var as a3 on (a3.co_reg=a1.co_banchq ) "
                + " where  a.st_reg not in ('E','I') and a1.st_reg in ('A','C') "
                + "  "+strAux+"  "
                + " and (a1.mo_pag+a1.nd_abo) != 0   order by  a.tx_nomcli, a1.fe_ven "
                + " ) as x   ";
            

                strSql ="  select co_emp, co_loc, co_cli, tx_nomcli, tx_ide, sum(valpen) as valpen, sum(valporven) as valporven, "
                + "  sum(val_1_30) as val_1_30,  sum(val_31_60) as val_31_60, sum(val_61_90) as val_61_90 "
                + " ,  sum(val_mas_90) as val_mas_90  "
                + " from ( "+strSql+" ) as x  group by co_emp, co_loc, co_cli, tx_nomcli, tx_ide ORDER BY tx_nomcli ";


             System.out.println("ZafCxC16.cargarDetReg: "+ strSql );


            rstLoc=stmLoc.executeQuery(strSql);
            //Limpiar vector de datos.
            vecDat.clear();

            lblMsgSis.setText("Cargando datos...");

            while (rstLoc.next())
                {

                        vecReg=new Vector();

                            vecReg.add(INT_TBL_DAT_LIN1,"");
                            vecReg.add(INT_TBL_DAT_COD_EMP1, rstLoc.getString("co_emp") );
                            vecReg.add(INT_TBL_DAT_COD_LOC1, rstLoc.getString("co_loc") );
                            vecReg.add(INT_TBL_DAT_COD_CLI1, rstLoc.getString("co_cli") );
                            vecReg.add(INT_TBL_DAT_NOM_CLI1, rstLoc.getString("tx_nomcli") );
                            vecReg.add(INT_TBL_DAT_IDE_CLI1, rstLoc.getString("tx_ide") );
                            vecReg.add(INT_TBL_DAT_VAL_PEN1, rstLoc.getString("valpen") );

                            vecReg.add(INT_TBL_DAT_VAL_VEN1, rstLoc.getString("valporven") );
                            vecReg.add(INT_TBL_DAT_VAL_30D1, rstLoc.getString("val_1_30") );
                            vecReg.add(INT_TBL_DAT_VAL_60D1, rstLoc.getString("val_31_60") );
                            vecReg.add(INT_TBL_DAT_VAL_90D1, rstLoc.getString("val_61_90") );
                            vecReg.add(INT_TBL_DAT_VAL_MAS1, rstLoc.getString("val_mas_90") );

                        vecDat.add(vecReg);

             }
             objTblMod1.setData(vecDat);
             tblDat1.setModel(objTblMod1);
             vecDat.clear();

              objTblTot1.calcularTotales();

            rstLoc.close();
            stmLoc.close();
            con.close();
            rstLoc=null;
            stmLoc=null;
            con=null;

           lblMsgSis.setText("Se encontraron " + tblDat1.getRowCount() + " registros.");
           pgrSis.setValue(0);
           butCon.setText("Consultar");

  }}catch (java.sql.SQLException e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
    catch (Exception e){  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
return blnRes;
}



String strBusIni="";

private boolean cargarDetRegCli(String strAux){
boolean blnRes=true;
java.sql.Statement stmLoc;
java.sql.ResultSet rstLoc;
String strSql = "";
try
{
  
        java.sql.Connection con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
        if (con!=null)
        {

            stmLoc=con.createStatement();


         
           String strBusIde= objTblMod1.getValueAt(tblDat1.getSelectedRow(), INT_TBL_DAT_IDE_CLI1 ).toString();


           // System.out.println("---> "+ fecdesde );


                strSql =" select "
                + " case when dias < 0 then valpen else 0 end as valporven   "
                + ",case when dias between 0 and 30 then valpen else 0 end as val_1_30 "
                + ",case when dias between 31 and 60 then valpen else 0 end as val_31_60 "
                + ",case when dias between 61 and 90 then valpen else 0 end as val_61_90 "
                + " ,case when dias  > 90 then valpen else 0 end as val_mas_90 "
                + " ,* from ( "
                + " select cli.tx_ide , "
                + " a1.co_banchq, a3.tx_deslar as nomban, a1.tx_numctachq, a1.tx_numchq, a1.fe_recchq, a1.fe_venchq, a1.nd_monchq, "+
                "  a.co_emp, a.co_loc, a.co_cli, a.tx_nomcli, a.co_tipdoc, a2.tx_descor, a2.tx_deslar,  a.co_doc, a1.co_reg, a.ne_numdoc, "
                +" a.fe_doc, a1.ne_diacre, a1.fe_ven, a1.nd_porret "
                + "  ,a1.mo_pag,  (a1.mo_pag+a1.nd_abo) as valpen "
                + "  , (current_date - a1.fe_ven )  as dias  "
                + " from tbm_cabmovinv as a  "
                + " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
                + " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc ) "
                + " inner join tbm_cli as cli on (cli.co_emp=a.co_emp and cli.co_cli=a.co_cli ) "
                + " left join tbm_var as a3 on (a3.co_reg=a1.co_banchq ) "
                + " where  a.st_reg not in ('E','I') and a1.st_reg in ('A','C') "
                + "  "+strAux+"  "
                + " and (a1.mo_pag+a1.nd_abo) != 0   order by  a.tx_nomcli, a1.fe_ven "
                + " ) as x   ";


                strSql ="select * from ( "+strSql+" ) as x  WHERE tx_ide = '"+strBusIde+"' ORDER BY fe_doc ";

             System.out.println("ZafCxC16.cargarDetRegCli: "+ strSql );


            rstLoc=stmLoc.executeQuery(strSql);
            //Limpiar vector de datos.
            vecDat.clear();

            lblMsgSis.setText("Cargando datos...");

            while (rstLoc.next())
                {

                        vecReg=new Vector();

                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_EMP, rstLoc.getString("co_emp") );
                            vecReg.add(INT_TBL_DAT_COD_LOC, rstLoc.getString("co_loc") );
                            vecReg.add(INT_TBL_DAT_COD_CLI, rstLoc.getString("co_cli") );
                            vecReg.add(INT_TBL_DAT_NOM_CLI, rstLoc.getString("tx_nomcli") );
                            vecReg.add(INT_TBL_DAT_COD_TIP_DOC, rstLoc.getString("co_tipdoc") );
                            vecReg.add(INT_TBL_DAT_DEC_TIP_DOC, rstLoc.getString("tx_descor") );
                            vecReg.add(INT_TBL_DAT_DEL_TIP_DOC, rstLoc.getString("tx_deslar") );
                            vecReg.add(INT_TBL_DAT_COD_DOC, rstLoc.getString("co_doc") );
                            vecReg.add(INT_TBL_DAT_COD_REG, rstLoc.getString("co_reg") );
                            vecReg.add(INT_TBL_DAT_NUM_DOC, rstLoc.getString("ne_numdoc") );
                            vecReg.add(INT_TBL_DAT_FEC_DOC, rstLoc.getString("fe_doc") );
                            vecReg.add(INT_TBL_DAT_DIA_CRE, rstLoc.getString("ne_diacre") );
                            vecReg.add(INT_TBL_DAT_FEC_VEN, rstLoc.getString("fe_ven") );
                            vecReg.add(INT_TBL_DAT_POR_RET, rstLoc.getString("nd_porret") );
                            vecReg.add(INT_TBL_DAT_VAL_DOC, rstLoc.getString("mo_pag") );
                            vecReg.add(INT_TBL_DAT_VAL_ABO, "" );
                            vecReg.add(INT_TBL_DAT_VAL_PEN, rstLoc.getString("valpen") );

                            vecReg.add(INT_TBL_DAT_VAL_VEN, rstLoc.getString("valporven") );
                            vecReg.add(INT_TBL_DAT_VAL_30D, rstLoc.getString("val_1_30") );
                            vecReg.add(INT_TBL_DAT_VAL_60D, rstLoc.getString("val_31_60") );
                            vecReg.add(INT_TBL_DAT_VAL_90D, rstLoc.getString("val_61_90") );
                            vecReg.add(INT_TBL_DAT_VAL_MAS, rstLoc.getString("val_mas_90") );

                            vecReg.add(INT_TBL_DAT_COD_BAN, rstLoc.getString("co_banchq") );
                            vecReg.add(INT_TBL_DAT_NOM_BAN, rstLoc.getString("nomban") );
                            vecReg.add(INT_TBL_DAT_NUM_CTA, rstLoc.getString("tx_numctachq") );
                            vecReg.add(INT_TBL_DAT_NUM_CHQ, rstLoc.getString("tx_numchq") );

                            vecReg.add(INT_TBL_DAT_FEC_REC_CHQ, rstLoc.getString("fe_recchq") );
                            vecReg.add(INT_TBL_DAT_FEC_VEN_CHQ, rstLoc.getString("fe_venchq") );
                            vecReg.add(INT_TBL_DAT_VAL_CHQ, rstLoc.getString("nd_monchq") );


                        vecDat.add(vecReg);

             }
             objTblMod.setData(vecDat);
             tblDat.setModel(objTblMod);
             vecDat.clear();

              objTblTot.calcularTotales();

            rstLoc.close();
            stmLoc.close();
            con.close();
            rstLoc=null;
            stmLoc=null;
            con=null;

        

  }}catch (java.sql.SQLException e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
    catch (Exception e){  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
return blnRes;
}

    /*
     * @modified EFLORESA 2012-03-23
     * Se modifica la columna DIAS del select para que se haga con la fecha de la condicion
     * y no con la fecha del sistema.
     */
    private boolean cargarDetRegCorFec(String strAux){
        boolean blnRes=true;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql = "";
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            Connection con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stmLoc=con.createStatement();
                strBusIni=strAux;
                String  fecdesde =  objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                strSql =" select"
                    + " case when dias < 0 then valpen else 0 end as valporven,"
                    + " case when dias between 0 and 30 then valpen else 0 end as val_1_30,"
                    + " case when dias between 31 and 60 then valpen else 0 end as val_31_60,"
                    + " case when dias between 61 and 90 then valpen else 0 end as val_61_90,"
                    + " case when dias  > 90 then valpen else 0 end as val_mas_90,"
                    + " * "
                    + " from ( select * ,( mo_pag+ sumabodet) as valpen"
                    + " from ( select x.* ,"
                    + " case when x1.sumabodet is null then 0 else x1.sumabodet end as sumabodet"
                    + " from ( select cli.tx_ide,"
                    + " a1.co_banchq, a3.tx_deslar as nomban, a1.tx_numctachq, a1.tx_numchq, a1.fe_recchq, a1.fe_venchq, a1.nd_monchq,"
                    + " a.co_emp, a.co_loc, a.co_cli, a.tx_nomcli, a.co_tipdoc, a2.tx_descor, a2.tx_deslar,  a.co_doc, a1.co_reg, a.ne_numdoc,"
                    + " a.fe_doc, a1.ne_diacre, a1.fe_ven, a1.nd_porret, a1.mo_pag, "
                    + " ('"+fecdesde+"' - a1.fe_ven ) as dias"
                    //+ " (current_date - a1.fe_ven ) as dias"
                    + " from tbm_cabmovinv as a"
                    + " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
                    + " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc ) "
                    + " inner join tbm_cli as cli on (cli.co_emp=a.co_emp and cli.co_cli=a.co_cli ) "
                    + " left join tbm_var as a3 on (a3.co_reg=a1.co_banchq ) "
                    + " where a.st_reg not in ('E','I') "
                    + " and a1.st_reg in ('A','C') "
                    + " and a.fe_doc <='"+fecdesde+"' "
                    + " "+strAux+"  "
                    + " ) as x "
                    + " left join ( " 
                    + " select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet " 
                    + " from tbm_detpag as x1  " 
                    + " inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) " 
                    + " where x2.st_reg NOT IN ('E','I')   and  x1.st_reg NOT IN ('E','I')  " 
                    + " AND x2.fe_doc <= '"+fecdesde+"' " 
                    + " group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  " 
                    + " ) as x1 on (x.co_emp=x1.co_emp and x.co_loc=x1.co_locpag and x.co_tipdoc=x1.co_tipdocpag and x.co_doc=x1.co_docpag and x.co_reg=x1.co_regpag )  " 
                    + " ) as x where ( mo_pag + sumabodet ) != 0 "
                    + " order by tx_nomcli, fe_ven " 
                    + " ) as x  ";

                strSql =" select co_emp, co_loc, co_cli, tx_nomcli, tx_ide, sum(valpen) as valpen, sum(valporven) as valporven,"
                    + " sum(val_1_30) as val_1_30,  sum(val_31_60) as val_31_60, sum(val_61_90) as val_61_90,"
                    + " sum(val_mas_90) as val_mas_90  "
                    + " from ( "+strSql+" ) as x  group by co_emp, co_loc, co_cli, tx_nomcli, tx_ide ORDER BY tx_nomcli ";

                System.out.println("ZafCxC16.cargarDetRegCorFec: "+ strSql );

                rstLoc=stmLoc.executeQuery(strSql);
                //Limpiar vector de datos.
                vecDat.clear();

                lblMsgSis.setText("Cargando datos...");

                while (rstLoc.next()){
                    vecReg=new Vector();

                    vecReg.add(INT_TBL_DAT_LIN1,"");
                    vecReg.add(INT_TBL_DAT_COD_EMP1, rstLoc.getString("co_emp") );
                    vecReg.add(INT_TBL_DAT_COD_LOC1, rstLoc.getString("co_loc") );
                    vecReg.add(INT_TBL_DAT_COD_CLI1, rstLoc.getString("co_cli") );
                    vecReg.add(INT_TBL_DAT_NOM_CLI1, rstLoc.getString("tx_nomcli") );
                    vecReg.add(INT_TBL_DAT_IDE_CLI1, rstLoc.getString("tx_ide") );
                    vecReg.add(INT_TBL_DAT_VAL_PEN1, rstLoc.getString("valpen") );

                    vecReg.add(INT_TBL_DAT_VAL_VEN1, rstLoc.getString("valporven") );
                    vecReg.add(INT_TBL_DAT_VAL_30D1, rstLoc.getString("val_1_30") );
                    vecReg.add(INT_TBL_DAT_VAL_60D1, rstLoc.getString("val_31_60") );
                    vecReg.add(INT_TBL_DAT_VAL_90D1, rstLoc.getString("val_61_90") );
                    vecReg.add(INT_TBL_DAT_VAL_MAS1, rstLoc.getString("val_mas_90") );

                    vecDat.add(vecReg);
                }
                objTblMod1.setData(vecDat);
                tblDat1.setModel(objTblMod1);
                vecDat.clear();

                objTblTot1.calcularTotales();

                rstLoc.close();
                stmLoc.close();
                con.close();
                rstLoc=null;
                stmLoc=null;
                con=null;

                lblMsgSis.setText("Se encontraron " + tblDat1.getRowCount() + " registros.");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }catch (SQLException e){ 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e); 
        }catch (Exception e){  
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e); 
        }
        return blnRes;
    }

    /*
     * @modified EFLORESA 2012-03-23
     * Se modifica la columna DIAS del select para que se haga con la fecha de la condicion
     * y no con la fecha del sistema.
     */    
    private boolean cargarDetRegCorFecCli(String strAux){
        boolean blnRes=true;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql = "";
        try{
            Connection con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stmLoc=con.createStatement();
                String  fecdesde =  objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                String strBusIde= objTblMod1.getValueAt(tblDat1.getSelectedRow(), INT_TBL_DAT_IDE_CLI1 ).toString();
                strSql =" select "
                + " case when dias < 0 then valpen else 0 end as valporven, "
                + " case when dias between 0 and 30 then valpen else 0 end as val_1_30, "
                + " case when dias between 31 and 60 then valpen else 0 end as val_31_60, "
                + " case when dias between 61 and 90 then valpen else 0 end as val_61_90, "
                + " case when dias  > 90 then valpen else 0 end as val_mas_90, "
                + " * "
                + " from ( select * ,( mo_pag+ sumabodet) as valpen from ( " 
                + " select x.* ,  case when x1.sumabodet is null then 0 else x1.sumabodet end as sumabodet from ( "
                + " select cli.tx_ide , "
                + " a1.co_banchq, a3.tx_deslar as nomban, a1.tx_numctachq, a1.tx_numchq, a1.fe_recchq, a1.fe_venchq, a1.nd_monchq, "
                + " a.co_emp, a.co_loc, a.co_cli, a.tx_nomcli, a.co_tipdoc, a2.tx_descor, a2.tx_deslar,  a.co_doc, a1.co_reg, a.ne_numdoc, "
                + " a.fe_doc, a1.ne_diacre, a1.fe_ven, a1.nd_porret, a1.mo_pag, "
                //+ " (current_date - a1.fe_ven )  as dias  "
                + " ('"+fecdesde+"' - a1.fe_ven )  as dias  "
                + " from tbm_cabmovinv as a  "
                + " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
                + " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc ) "
                + " inner join tbm_cli as cli on (cli.co_emp=a.co_emp and cli.co_cli=a.co_cli ) "
                + " left join tbm_var as a3 on (a3.co_reg=a1.co_banchq ) "
                + " where  a.st_reg not in ('E','I') and a1.st_reg in ('A','C') and a.fe_doc <='"+fecdesde+"' "
                + " "+strAux+" "
                + " ) as x "
                + " left join ( " 
                + " select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet " 
                + " from tbm_detpag as x1  " 
                + " inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) " 
                + " where x2.st_reg NOT IN ('E','I')   and  x1.st_reg NOT IN ('E','I')  " 
                + " AND x2.fe_doc <= '"+fecdesde+"' " 
                + " group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  " 
                + " ) as x1 on (x.co_emp=x1.co_emp and x.co_loc=x1.co_locpag and x.co_tipdoc=x1.co_tipdocpag and x.co_doc=x1.co_docpag and x.co_reg=x1.co_regpag )  " 
                + " ) as x where ( mo_pag + sumabodet ) != 0 order by  tx_nomcli, fe_ven " 
                + " ) as x  ";

                strSql ="select * from ( "+strSql+" ) as x  WHERE tx_ide = '"+strBusIde+"' ORDER BY fe_doc ";

                System.out.println("ZafCxC16.cargarDetRegCorFecCli: "+ strSql );

                rstLoc=stmLoc.executeQuery(strSql);
                //Limpiar vector de datos.
                vecDat.clear();

                lblMsgSis.setText("Cargando datos...");

                while (rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_EMP, rstLoc.getString("co_emp") );
                    vecReg.add(INT_TBL_DAT_COD_LOC, rstLoc.getString("co_loc") );
                    vecReg.add(INT_TBL_DAT_COD_CLI, rstLoc.getString("co_cli") );
                    vecReg.add(INT_TBL_DAT_NOM_CLI, rstLoc.getString("tx_nomcli") );
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC, rstLoc.getString("co_tipdoc") );
                    vecReg.add(INT_TBL_DAT_DEC_TIP_DOC, rstLoc.getString("tx_descor") );
                    vecReg.add(INT_TBL_DAT_DEL_TIP_DOC, rstLoc.getString("tx_deslar") );
                    vecReg.add(INT_TBL_DAT_COD_DOC, rstLoc.getString("co_doc") );
                    vecReg.add(INT_TBL_DAT_COD_REG, rstLoc.getString("co_reg") );
                    vecReg.add(INT_TBL_DAT_NUM_DOC, rstLoc.getString("ne_numdoc") );
                    vecReg.add(INT_TBL_DAT_FEC_DOC, rstLoc.getString("fe_doc") );
                    vecReg.add(INT_TBL_DAT_DIA_CRE, rstLoc.getString("ne_diacre") );
                    vecReg.add(INT_TBL_DAT_FEC_VEN, rstLoc.getString("fe_ven") );
                    vecReg.add(INT_TBL_DAT_POR_RET, rstLoc.getString("nd_porret") );
                    vecReg.add(INT_TBL_DAT_VAL_DOC, rstLoc.getString("mo_pag") );
                    vecReg.add(INT_TBL_DAT_VAL_ABO, "" );
                    vecReg.add(INT_TBL_DAT_VAL_PEN, rstLoc.getString("valpen") );

                    vecReg.add(INT_TBL_DAT_VAL_VEN, rstLoc.getString("valporven") );
                    vecReg.add(INT_TBL_DAT_VAL_30D, rstLoc.getString("val_1_30") );
                    vecReg.add(INT_TBL_DAT_VAL_60D, rstLoc.getString("val_31_60") );
                    vecReg.add(INT_TBL_DAT_VAL_90D, rstLoc.getString("val_61_90") );
                    vecReg.add(INT_TBL_DAT_VAL_MAS, rstLoc.getString("val_mas_90") );

                    vecReg.add(INT_TBL_DAT_COD_BAN, rstLoc.getString("co_banchq") );
                    vecReg.add(INT_TBL_DAT_NOM_BAN, rstLoc.getString("nomban") );
                    vecReg.add(INT_TBL_DAT_NUM_CTA, rstLoc.getString("tx_numctachq") );
                    vecReg.add(INT_TBL_DAT_NUM_CHQ, rstLoc.getString("tx_numchq") );

                    vecReg.add(INT_TBL_DAT_FEC_REC_CHQ, rstLoc.getString("fe_recchq") );
                    vecReg.add(INT_TBL_DAT_FEC_VEN_CHQ, rstLoc.getString("fe_venchq") );
                    vecReg.add(INT_TBL_DAT_VAL_CHQ, rstLoc.getString("nd_monchq") );

                    vecDat.add(vecReg);
                }
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();

                objTblTot.calcularTotales();

                rstLoc.close();
                stmLoc.close();
                con.close();
                rstLoc=null;
                stmLoc=null;
                con=null;
            }
        }catch (SQLException e){ 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e); 
        }catch (Exception e){  
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }

    private String FilSql() 
    {
        String strFilSql = "";
        String strFecSis, strFecIni, strFecsisFor, strFecVen;
        java.util.Date datFecSer, datFecVen, datFecAux;
        boolean blnRes=true;        
        int intCodEmp, intCodEmpGrp;
                
        datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
        intCodEmpGrp=objParSis.getCodigoEmpresaGrupo();
        
        if (datFecSer==null)
            blnRes=false;
        
        datFecAux=datFecSer;
                
        strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
        strFecsisFor=objUti.formatearFecha(datFecAux, "yyyy/MM/dd");
        //Obtener la condición.
       
        //Condicion para filtro por cliente
        if (txtCodCli.getText().length()>0)
        {
            strFilSql+=" AND x3.co_cli= " + txtCodCli.getText();
        }
            
        //Condicion para filtro por nombres desde y hasta//
        if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
            strFilSql+=" AND ((LOWER(x3.tx_nomcli) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(x3.tx_nomcli) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

        if (!(txtDesCorTipDoc.getText().equals("")))
        {
            strFilSql+=" AND x3.co_tipdoc= " + txtCodTipDoc.getText();
        }
        if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
        {
            //Condicion para filtro por empresa
            if (txtCodEmp.getText().length()>0)
                strFilSql+=" AND x3.co_emp=" + txtCodEmp.getText();
        }
        else
        {
            strFilSql+=" AND x3.co_emp=" + objParSis.getCodigoEmpresa();
            
            //Condicion para filtro por cLocal
            if (txtCodLoc.getText().length()>0)
                strFilSql+=" AND x3.co_loc= " + txtCodLoc.getText();
            else
                strFilSql+=" AND x3.co_loc= " + objParSis.getCodigoLocal();
            
        }
        ///FILTRO PARA MOSTRAR CREDITOS Y DEBITOS///
        if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
        {
            if (chkMosCre.isSelected())
                strFilSql+=" AND x4.tx_natDoc='I'";
            else
                strFilSql+=" AND x4.tx_natDoc='E'";
        }
        ///FILTRO PARA DOCUMENTOS VENCIDOS///
        if (chkMosDocVen.isSelected())
        {
            strFecVen=objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
            strFilSql+=" AND x4.fe_ven<='" + strFecVen + "'";
        }
        ///FILTRO PARA MOSTRAR RETENCIONES///
        if (!chkMosRet.isSelected())
        {
            strFilSql+=" AND (x4.nd_porRet IS NULL OR x4.nd_porRet=0)";
        }
        return strFilSql;
    }
    
  private boolean isCamVal()
    {
        int intNumFilTblLoc, i, j;
        //Validar que esté seleccionada al menos un local.
        
        i=0;
        
        
      if ( (!chkVenCli.isSelected()) && (!chkVenRel.isSelected()) && (!chkPre.isSelected()) )
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El siguiente filtro es obligatorio:<BR><UL><LI>Ventas a clientes<LI>Ventas a empresas relacionadas<LI>Préstamos</UL>Seleccione al menos una de las opciones y vuelva a intentarlo.</HTML>");
           chkVenCli.requestFocus();
            return false;
        }
       
        return true;
    }
  
    
    
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        //JOptionPane oppMsg=new JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        //JOptionPane oppMsg=new JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        //JOptionPane oppMsg=new JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this,strMsg,strTit,JOptionPane.ERROR_MESSAGE);
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
 private boolean mostrarVenConCli(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            
            //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
            if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))            {
                //Armar la sentencia SQL.
                strSQL=" SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir " + 
                       " FROM tbm_cli AS a1 " +
                       " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " " +
                       " AND a1.st_reg='A' " +
                       ((objParSis.getCodigoMenu()==321) ? " and a1.st_cli='S' " : " and a1.st_prv='S' ") +
                       " ORDER BY a1.tx_nom";
            } else {
                //Armar la sentencia SQL.
                strSQL=" select a.co_cli, a.tx_ide, a.tx_nom, a.tx_dir " +
                       " from tbm_cli a, tbr_cliloc b " +
                       " where a.co_emp = b.co_emp " +
                       " and a.co_cli = b.co_cli " +
                       " and a.co_emp=" + objParSis.getCodigoEmpresa() + " " +
                       ((!txtCodLoc.getText().equals("")) ? " and b.co_loc="+txtCodLoc.getText() : " and b.co_loc="+objParSis.getCodigoLocal()) +
                       " and a.st_reg='A' " +
                       ((objParSis.getCodigoMenu()==321) ? " and a.st_cli='S' " : " and a.st_prv='S' ") +
                       " order by a.tx_nom ";
            }
            
            vcoCli.setSentenciaSQL(strSQL);
            
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.setVisible(true);
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Identificación".
                    if (vcoCli.buscar("a1.tx_ide", txtIdeCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(1);
                        vcoCli.setCriterio1(7);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtIdeCli.setText(strIdeCli);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarCli.setText(strDesLarCli);
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
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
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));///txtCodTipDoc   ///txtCodTipDoc
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        //intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);

                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_descor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));   ///txtCodTipDoc
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                       // intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
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
                           // intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_deslar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        //intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
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
                           // intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
                default:
                    txtDesCorTipDoc.requestFocus();
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConLoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {

          if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo() ) {

              if(!txtCodEmp.getText().equals("")){
               strSqlLoc="SELECT a1.co_loc, a1.tx_nom FROM tbm_loc AS a1 "
               + " WHERE a1.co_emp="+txtCodEmp.getText()+" AND a1.st_reg IN ('A', 'P') "
               + " ORDER BY a1.co_emp, a1.co_loc ";

               vcoLoc.setSentenciaSQL( strSqlLoc );
               vcoLoc.cargarDatos();
               
              }else{
                 mostrarMsgInf("SELECCIONE UN EMPRESA");
                 return false;
              }

          }

            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.


                    

                    vcoLoc.setCampoBusqueda(1);
                    vcoLoc.show();
                    if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE)
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtDesLarLoc.setText(vcoLoc.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtDesLarLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(0);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.show();
                        if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtDesLarLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtCodLoc.setText(strCodLoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoLoc.buscar("a1.tx_nom", txtDesLarLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtDesLarLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(1);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.show();
                        if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtDesLarLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtDesLarLoc.setText(strNomLoc);
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConEmp(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(1);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtDesLarEmp.setText(vcoEmp.getValueAt(2));
                        txtCodLoc.setText("");
                        txtDesLarLoc.setText("");
                        strCodLoc="";
                        strNomLoc="";

                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtDesLarEmp.setText(vcoEmp.getValueAt(2));
                        txtCodLoc.setText("");
                        txtDesLarLoc.setText("");
                        strCodLoc="";
                        strNomLoc="";
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoLoc.getValueAt(1));
                            txtDesLarEmp.setText(vcoLoc.getValueAt(2));
                            txtCodLoc.setText("");
                            txtDesLarLoc.setText("");
                            strCodLoc="";
                            strNomLoc="";
                        }
                        else
                        {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoEmp.buscar("a1.tx_nom", txtDesLarEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtDesLarEmp.setText(vcoEmp.getValueAt(2));
                        txtCodLoc.setText("");
                        txtDesLarLoc.setText("");
                        strCodLoc="";
                        strNomLoc="";
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtDesLarEmp.setText(vcoEmp.getValueAt(2));
                            txtCodLoc.setText("");
                            txtDesLarLoc.setText("");
                            strCodLoc="";
                            strNomLoc="";
                        }
                        else
                        {
                            txtDesLarEmp.setText(strDesLarEmp);
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
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    String  strEstFec="";
    private class ZafThreadGUI extends Thread{
        public void run(){

            pgrSis.setIndeterminate(true);

            /*Funcion para cargar datos de clientes por EMPRESA*/

            strEstFec =  objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd");

            if(strEstFec.equals("[ERROR]")){
                System.out.println(" NO FECHA ");
                if (!cargarDetReg(filtro())) {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }
            }else {
                System.out.println(" SI FECHA ");
                if (!cargarDetRegCorFec(filtro())){
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }

            }
            pgrSis.setIndeterminate(false);

            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat1.getRowCount()>0) {
                tabFrm.setSelectedIndex(1);
                tblDat1.setRowSelectionInterval(0, 0);
                tblDat1.requestFocus();
            }
            objThrGUI=null;
            //  }
        }
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
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DEC_TIP_DOC:
                    strMsg="Tipo de documento";
                    break;
                case INT_TBL_DAT_DEL_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_COD_REG:
                    strMsg="Código del registro";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_DIA_CRE:
                    strMsg="Días de crédito";
                    break;
                case INT_TBL_DAT_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_DAT_POR_RET:
                    strMsg="Porcentaje de retención";
                    break;
                case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_DAT_VAL_ABO:
                    strMsg="Abono realizado";
                    break;
                case INT_TBL_DAT_VAL_PEN:
                    strMsg="Valor Pendiente";
                    break;                    
                case INT_TBL_DAT_VAL_VEN:
                    strMsg="Valor por vencer";
                    break;
                case INT_TBL_DAT_VAL_30D:
                    strMsg="Valor vencido (0-30 días)";
                    break;
                case INT_TBL_DAT_VAL_60D:
                    strMsg="Valor vencido (31-60 días)";
                    break;
                case INT_TBL_DAT_VAL_90D:
                    strMsg="Valor vencido (61-90 días)";
                    break;
                case INT_TBL_DAT_VAL_MAS:
                    strMsg="Valor vencido (Más de 90 días)";
                    break;
                case INT_TBL_DAT_COD_BAN:
                    strMsg="Código del Banco";
                    break;
                case INT_TBL_DAT_NOM_BAN:
                    strMsg="Nombre del Banco";
                    break;
                case INT_TBL_DAT_NUM_CTA:
                    strMsg="Número de cuenta";
                    break;
                case INT_TBL_DAT_NUM_CHQ:
                    strMsg="Número de cheque";
                    break;
                case INT_TBL_DAT_FEC_REC_CHQ:
                    strMsg="Fecha de recepción del cheque";
                    break;
                case INT_TBL_DAT_FEC_VEN_CHQ:
                    strMsg="Fecha de vencimiento del cheque";
                    break;
                
                case INT_TBL_DAT_VAL_CHQ:
                    strMsg="Valor del cheque";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }



   private class ZafMouMotAda1 extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat1.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN1:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_EMP1:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC1:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_COD_CLI1:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_DAT_NOM_CLI1:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_DAT_IDE_CLI1:
                    strMsg="Identificacion del cliente";
                    break;
                case INT_TBL_DAT_VAL_PEN1:
                    strMsg="Valor Pendiente";
                    break;
                case INT_TBL_DAT_VAL_VEN1:
                    strMsg="Valor por vencer";
                    break;
                case INT_TBL_DAT_VAL_30D1:
                    strMsg="Valor vencido (0-30 días)";
                    break;
                case INT_TBL_DAT_VAL_60D1:
                    strMsg="Valor vencido (31-60 días)";
                    break;
                case INT_TBL_DAT_VAL_90D1:
                    strMsg="Valor vencido (61-90 días)";
                    break;
                case INT_TBL_DAT_VAL_MAS1:
                    strMsg="Valor vencido (Más de 90 días)";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat1.getTableHeader().setToolTipText(strMsg);
        }
    }


}
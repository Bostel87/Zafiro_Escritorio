/*
 * ZafCom67.java
 *
 * Created on 25 de junio de 2013, 10:10 PM
 */
package Compras.ZafCom67;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.text.ParseException;
import java.util.Vector;
import java.util.ArrayList;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.event.EventListenerList;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author  Alex Morán
 */
public class ZafCom67 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_ITM_MAE=1;                 //Código maestro del item.
    static final int INT_TBL_DAT_COD_ITM=2;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT_ITM=3;                 //Código del item (Alterno).
    static final int INT_TBL_DAT_NOM_ITM=4;                     //Nombre del item.
    static final int INT_TBL_DAT_DES_COR_UNI_MED=5;             //Descripción corta de la unidad de medida.
    static final int INT_TBL_DAT_PES_KGR=6;                     //Peso en kilogramos.
    static final int INT_TBL_DAT_UBI_ITM=7;                     //Ubicación.
    static final int INT_TBL_DAT_CHK_UBI=8;                     //Estado impresión orden.
    static final int INT_TBL_DAT_COD_LET_ITM=9;                 //Código del item (Alterno II).
    static final int INT_TBL_DAT_STK_CON=10;                    //Stock consolidado.
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblDer;                 //Render: Presentar JLabel en JTable (Derecha).
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
    private ZafVenCon vcoUniMed;                                //Ventana de consulta "Unidad de medida".
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                    //true: Continua la ejecución del hilo.
    private String strCodAlt, strCodLetItm, strNomItm;                       //Contenido del campo al obtener el foco.
    private ZafPerUsr objPerUsr;                               //Objeto que almacena el perfil del usuario.
    private JFormattedTextField jFormattedTextField1;
    private boolean blnMarTodCanTbl = true;
    private String strMsgItmUbi;
    private ZafTblPopMnu ZafTblPopMnu;
    
    String strCodBod = "", strNomBod = "";
    ZafVenCon vcoBodUsr;
    
   /** Creates new form ZafCom67 */
    public ZafCom67(Librerias.ZafParSis.ZafParSis obj) 
    {
        try 
        {
            //Inicializar objetos.
            objParSis=(Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();
            
            lblUbi.setVisible(false);
            txtUbi.setVisible(false);  
            
//            MaskFormatter mask = new MaskFormatter("U-##-U-#U");
//            mask.setPlaceholderCharacter(' ');
//            jFormattedTextField1 = new JFormattedTextField();
//            
//            panUbi.add(jFormattedTextField1);
//            jFormattedTextField1.setBounds(80, 30, 80, 20);
//
//            jFormattedTextField1.setFormatterFactory(new DefaultFormatterFactory(mask));
//            jFormattedTextField1.setText("a-01-a-1a");
//            jFormattedTextField1.setFocusLostBehavior(JFormattedTextField.PERSIST);
//
//        } catch (ParseException ex) {
//            Logger.getLogger(ZafCom67.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (CloneNotSupportedException e) {
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
        bgrFilUbi = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBod = new javax.swing.JButton();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtCodLetItm = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        chkSolStk = new javax.swing.JCheckBox();
        panDesHasItm = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        panCodAltItmTer = new javax.swing.JPanel();
        lblCodAltItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        panUbi = new javax.swing.JPanel();
        optItmTodUbi = new javax.swing.JRadioButton();
        optItmSinUbi = new javax.swing.JRadioButton();
        optItmConUbi = new javax.swing.JRadioButton();
        lblUbi = new javax.swing.JLabel();
        txtUbi = new javax.swing.JTextField();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGuardar = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        lblBod.setText("Bodega:");
        panFil.add(lblBod);
        lblBod.setBounds(4, 4, 120, 20);

        txtCodBod.setBackground(objParSis.getColorCamposObligatorios());
        txtCodBod.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodFocusLost(evt);
            }
        });
        txtCodBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodActionPerformed(evt);
            }
        });
        panFil.add(txtCodBod);
        txtCodBod.setBounds(124, 4, 92, 20);

        txtNomBod.setBackground(objParSis.getColorCamposObligatorios());
        txtNomBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodFocusLost(evt);
            }
        });
        txtNomBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodActionPerformed(evt);
            }
        });
        panFil.add(txtNomBod);
        txtNomBod.setBounds(216, 4, 444, 20);

        butBod.setText("jButton2");
        butBod.setPreferredSize(new java.awt.Dimension(20, 20));
        butBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodActionPerformed(evt);
            }
        });
        panFil.add(butBod);
        butBod.setBounds(660, 4, 20, 20);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 24, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 44, 390, 20);

        lblItm.setText("Item:");
        panFil.add(lblItm);
        lblItm.setBounds(24, 64, 70, 20);
        panFil.add(txtCodItm);
        txtCodItm.setBounds(88, 64, 56, 20);

        txtCodAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltFocusLost(evt);
            }
        });
        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });
        panFil.add(txtCodAlt);
        txtCodAlt.setBounds(144, 64, 92, 20);

        txtCodLetItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLetItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLetItmFocusLost(evt);
            }
        });
        txtCodLetItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLetItmActionPerformed(evt);
            }
        });
        panFil.add(txtCodLetItm);
        txtCodLetItm.setBounds(236, 64, 64, 20);

        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });
        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        panFil.add(txtNomItm);
        txtNomItm.setBounds(300, 64, 340, 20);

        butItm.setText(".."); // NOI18N
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(640, 64, 20, 20);

        chkSolStk.setText("Mostrar sólo los items con stock");
        chkSolStk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolStkActionPerformed(evt);
            }
        });
        panFil.add(chkSolStk);
        chkSolStk.setBounds(360, 136, 308, 20);

        panDesHasItm.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panDesHasItm.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panDesHasItm.add(lblCodAltDes);
        lblCodAltDes.setBounds(12, 20, 48, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panDesHasItm.add(txtCodAltDes);
        txtCodAltDes.setBounds(60, 20, 100, 20);

        lblCodAltHas.setText("Hasta:");
        panDesHasItm.add(lblCodAltHas);
        lblCodAltHas.setBounds(168, 20, 48, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panDesHasItm.add(txtCodAltHas);
        txtCodAltHas.setBounds(216, 20, 100, 20);

        panFil.add(panDesHasItm);
        panDesHasItm.setBounds(20, 84, 328, 52);

        panCodAltItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panCodAltItmTer.setLayout(null);

        lblCodAltItmTer.setText("Terminan con:");
        panCodAltItmTer.add(lblCodAltItmTer);
        lblCodAltItmTer.setBounds(12, 20, 100, 20);

        txtCodAltItmTer.setToolTipText("<HTML>\nSi desea consultar más de un terminal separe cada terminal por medio de una coma.\n<BR><FONT COLOR=\"blue\">Por ejemplo:</FONT> S,L,T\n</HTML>");
        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panCodAltItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(112, 20, 184, 20);

        panFil.add(panCodAltItmTer);
        panCodAltItmTer.setBounds(360, 84, 308, 52);

        panUbi.setBorder(javax.swing.BorderFactory.createTitledBorder("Ubicación"));
        panUbi.setLayout(null);

        bgrFilUbi.add(optItmTodUbi);
        optItmTodUbi.setSelected(true);
        optItmTodUbi.setText("Todos");
        optItmTodUbi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optItmTodUbiItemStateChanged(evt);
            }
        });
        panUbi.add(optItmTodUbi);
        optItmTodUbi.setBounds(10, 20, 120, 23);

        bgrFilUbi.add(optItmSinUbi);
        optItmSinUbi.setText("Sin ubicación");
        optItmSinUbi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optItmSinUbiItemStateChanged(evt);
            }
        });
        panUbi.add(optItmSinUbi);
        optItmSinUbi.setBounds(10, 40, 120, 23);

        bgrFilUbi.add(optItmConUbi);
        optItmConUbi.setText("Con ubicación");
        optItmConUbi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optItmConUbiItemStateChanged(evt);
            }
        });
        panUbi.add(optItmConUbi);
        optItmConUbi.setBounds(10, 60, 120, 23);

        lblUbi.setText("Comienzan con:");
        panUbi.add(lblUbi);
        lblUbi.setBounds(170, 40, 100, 14);
        panUbi.add(txtUbi);
        txtUbi.setBounds(170, 60, 100, 20);

        panFil.add(panUbi);
        panUbi.setBounds(20, 136, 328, 90);

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

        butGuardar.setText("Guardar");
        butGuardar.setToolTipText("Guarda los cambios realizados.");
        butGuardar.setEnabled(false);
        butGuardar.setPreferredSize(new java.awt.Dimension(92, 25));
        butGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuardarActionPerformed(evt);
            }
        });
        panBot.add(butGuardar);

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

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        //Validar que el programa sólo funcione en la empresa grupo
        if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()) {
            mostrarMsgInf("Este programa sólo funciona en la empresa grupo.\nIngrese a la empresa grupo y vuelva a intentarlo.");
            dispose();
        }
        
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        if (objTblMod.isDataModelChanged()) 
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0) 
            {
                if (actualizarDet())
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                else
                    mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifíquelo a su administrador del sistema.");
            }
        }
        else
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
    }//GEN-LAST:event_butGuardarActionPerformed

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
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void chkSolStkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSolStkActionPerformed
        if (chkSolStk.isSelected())
          optFil.setSelected(true);
    }//GEN-LAST:event_chkSolStkActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtCodLetItm.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
            txtCodAltItmTer.setText("");
            strCodAlt="";
            strCodLetItm="";
            strNomItm="";
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
    }//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAlt.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodLetItm.setText("");
                txtNomItm.setText("");
            } 
            else
            {
                mostrarVenConItm(1);
            }
        } 
        else
          txtCodAlt.setText(strCodAlt);

        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodAltFocusLost

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodLetItm.setText("");
                txtNomItm.setText("");
            } 
            else
            {
                mostrarVenConItm(3);
            }
        }
        else
           txtNomItm.setText(strNomItm);

        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0) 
        {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
              txtCodAltHas.setText(txtCodAltDes.getText());
        }
    }//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
    }//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if ( txtCodAltHas.getText().length()>0)
          optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltHasFocusLost

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
    }//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length()>0)
           optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
        txtCodBod.transferFocus();
    }//GEN-LAST:event_txtCodBodActionPerformed

    private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
        strCodBod=txtCodBod.getText();
        txtCodBod.selectAll();
    }//GEN-LAST:event_txtCodBodFocusGained

    private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
        if (!txtCodBod.getText().equalsIgnoreCase(strCodBod)) 
        {
            if (txtCodBod.getText().equals("")) 
            {
                txtCodBod.setText("");
                txtNomBod.setText("");
            }
            else
            BuscarBod("a.co_bod",txtCodBod.getText(),0);
        }
        else
          txtCodBod.setText(strCodBod);
    }//GEN-LAST:event_txtCodBodFocusLost

    private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
        txtNomBod.transferFocus();
    }//GEN-LAST:event_txtNomBodActionPerformed

    private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
        strNomBod=txtNomBod.getText();
        txtNomBod.selectAll();
    }//GEN-LAST:event_txtNomBodFocusGained

    private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
        if (!txtNomBod.getText().equalsIgnoreCase(strNomBod)) 
        {
            if (txtNomBod.getText().equals("")) 
            {
                txtCodBod.setText("");
                txtNomBod.setText("");
            }
            else
                BuscarBod("a.tx_nom",txtNomBod.getText(),1);
        }
        else
           txtNomBod.setText(strNomBod);
    }//GEN-LAST:event_txtNomBodFocusLost

    public void BuscarBod(String campo,String strBusqueda,int tipo)
    {
        vcoBodUsr.setTitle("Listado de Bodegas");
        if (vcoBodUsr.buscar(campo, strBusqueda)) 
        {
            txtCodBod.setText(vcoBodUsr.getValueAt(1));
            txtNomBod.setText(vcoBodUsr.getValueAt(2));
            strCodBod = vcoBodUsr.getValueAt(1);
            strNomBod = vcoBodUsr.getValueAt(2);
        }
        else
        {
            vcoBodUsr.setCampoBusqueda(tipo);
            vcoBodUsr.cargarDatos();
            vcoBodUsr.show();
            if (vcoBodUsr.getSelectedButton() == vcoBodUsr.INT_BUT_ACE) 
            {
                txtCodBod.setText(vcoBodUsr.getValueAt(1));
                txtNomBod.setText(vcoBodUsr.getValueAt(2));
                strCodBod = vcoBodUsr.getValueAt(1);
                strNomBod = vcoBodUsr.getValueAt(2);
            } 
            else 
            {
                txtCodBod.setText(strCodBod);
                txtNomBod.setText(strNomBod);
            }
        }
    }
 
    private void butBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodActionPerformed
        vcoBodUsr.setTitle("Listado de Bodegas");
        vcoBodUsr.setCampoBusqueda(1);
        vcoBodUsr.show();
        if (vcoBodUsr.getSelectedButton() == vcoBodUsr.INT_BUT_ACE) 
        {
            txtCodBod.setText(vcoBodUsr.getValueAt(1));
            txtNomBod.setText(vcoBodUsr.getValueAt(2));
            strCodBod = vcoBodUsr.getValueAt(1);
            strNomBod = vcoBodUsr.getValueAt(2);
        }
    }//GEN-LAST:event_butBodActionPerformed

    private void optItmTodUbiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optItmTodUbiItemStateChanged
        if (optItmTodUbi.isSelected()) 
        {
            lblUbi.setVisible(false);
            txtUbi.setVisible(false);            
        }        
    }//GEN-LAST:event_optItmTodUbiItemStateChanged

    private void optItmSinUbiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optItmSinUbiItemStateChanged
        if (optItmSinUbi.isSelected()) 
        {
            lblUbi.setVisible(false);
            txtUbi.setVisible(false);            
        }              
    }//GEN-LAST:event_optItmSinUbiItemStateChanged

    private void optItmConUbiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optItmConUbiItemStateChanged
        if (optItmConUbi.isSelected()) 
        {
            lblUbi.setVisible(true);
            txtUbi.setVisible(true);            
        }                 
    }//GEN-LAST:event_optItmConUbiItemStateChanged

    private void txtCodLetItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLetItmActionPerformed
        txtCodLetItm.transferFocus();
    }//GEN-LAST:event_txtCodLetItmActionPerformed

    private void txtCodLetItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLetItmFocusGained
        strCodLetItm=txtCodLetItm.getText();
        txtCodLetItm.selectAll();
    }//GEN-LAST:event_txtCodLetItmFocusGained

    private void txtCodLetItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLetItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodLetItm.getText().equalsIgnoreCase(strCodLetItm))
        {
            if (txtCodLetItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodLetItm.setText("");
                txtNomItm.setText("");
            } 
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
           txtCodLetItm.setText(strCodLetItm);

        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodLetItmFocusLost

    private boolean configurarVenConBodUsr() 
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_bod");
            arlCam.add("a.tx_nom");

            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Bod");

            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("350");

            //Armar la sentencia SQL.   a7.nd_stkTot,
            String Str_Sql="";

             //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
            if (objParSis.getCodigoUsuario()==1) 
            {
                //Armar la sentencia SQL.
                Str_Sql=" SELECT co_bod, tx_nom FROM ( SELECT a2.co_bod, a2.tx_nom "+
                        " FROM tbm_emp AS a1 "+
                        " INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) "+
                        " WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo()+" "+
                        " ORDER BY a1.co_emp, a2.co_bod  ) as a ";
            } 
            else 
            {
                //Armar la sentencia SQL.
                Str_Sql="";
                Str_Sql+=" SELECT a1.co_bod AS co_bod, a1.tx_nom AS tx_nom ";
                Str_Sql+=" FROM tbm_emp AS a3 INNER JOIN tbm_bod AS a1";
                Str_Sql+=" ON (a3.co_emp=a1.co_emp)";
                Str_Sql+=" INNER JOIN tbr_bodLocPrgUsr AS a2";
                Str_Sql+=" ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                Str_Sql+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                Str_Sql+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                Str_Sql+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                Str_Sql+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                Str_Sql+=" ORDER BY a3.co_emp, a1.co_bod";                
            }

            vcoBodUsr=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;

            vcoBodUsr.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.ButtonGroup bgrFilUbi;
    private javax.swing.JButton butBod;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGuardar;
    private javax.swing.JButton butItm;
    private javax.swing.JCheckBox chkSolStk;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblCodAltItmTer;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblUbi;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optItmConUbi;
    private javax.swing.JRadioButton optItmSinUbi;
    private javax.swing.JRadioButton optItmTodUbi;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCodAltItmTer;
    private javax.swing.JPanel panDesHasItm;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panUbi;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtCodLetItm;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNomItm;
    private javax.swing.JTextField txtUbi;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm() 
    {
        boolean blnRes = true;
        try 
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.8");
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodItm.setVisible(false);
            //Configurar las ZafVenCon.
            configurarVenConItm();
            //Configurar los JTables.
            configurarTblDat();
            configurarVenConBodUsr();
        }
        catch(Exception e) {   blnRes=false;      objUti.mostrarMsgErr_F1(this, e);     }
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
            vecDat = new Vector();      //Almacena los datos
            vecCab = new Vector(8);     //Almacena las cabeceras
            
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE, "Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ITM, "Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Nombre");
            vecCab.add(INT_TBL_DAT_DES_COR_UNI_MED, "Uni.Med.");
            vecCab.add(INT_TBL_DAT_PES_KGR, "Peso(Kg)");
            vecCab.add(INT_TBL_DAT_UBI_ITM, "Ubicación");
            vecCab.add(INT_TBL_DAT_CHK_UBI, "Imp.Ord.");
            vecCab.add(INT_TBL_DAT_COD_LET_ITM, "Cód.Let.Itm.");
            vecCab.add(INT_TBL_DAT_STK_CON, "Stock");
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_PES_KGR, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_STK_CON, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            
//            //Configurar JTable: Establecer tipo de selección.
//            tblDat.setCellSelectionEnabled(true);
//            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(250);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setPreferredWidth(47);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_UBI_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CHK_UBI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setPreferredWidth(60);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            
//            if (objParSis.getCodigoUsuario() != 1) {
//                //2990: Ficha "Reporte": Tabla->Mostrar "Costo unitario".
//                if (!objPerUsr.isOpcionEnabled(2990))
//                {
//                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COS_UNI, tblDat);
//                }
//            }
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus = new ZafTblBus(tblDat);
            
            new ZafTblOrd(tblDat);
            
            ZafTblPopMnu = new ZafTblPopMnu(tblDat);
            ZafTblPopMnu.setEliminarFilaVisible(false);
            ZafTblPopMnu.setInsertarFilasVisible(false);
            ZafTblPopMnu.setInsertarFilaVisible(false);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            if (objParSis.getCodigoUsuario()==1) {
                vecAux.add("" + INT_TBL_DAT_UBI_ITM);
                vecAux.add("" + INT_TBL_DAT_CHK_UBI);
                butGuardar.setEnabled(true);
            } else {
                //2688: Tabla->Modificar "Ubicación del item".
                if (objPerUsr.isOpcionEnabled(2688)){ 
                    vecAux.add("" + INT_TBL_DAT_UBI_ITM);
                    vecAux.add("" + INT_TBL_DAT_CHK_UBI);
                    butGuardar.setEnabled(true); 
                }
            }
            
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_UBI_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET_ITM).setCellRenderer(objTblCelRenLbl);
            
            objTblCelRenLblDer=new ZafTblCelRenLbl();
            objTblCelRenLblDer.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setCellRenderer(objTblCelRenLblDer);
            
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setCellRenderer(objTblCelRenLblNum);
            
            ZafTblCelEdiTxtFor objTblCelEdiTxtFor = new ZafTblCelEdiTxtFor();
            tcmAux.getColumn(INT_TBL_DAT_UBI_ITM).setCellEditor(objTblCelEdiTxtFor);
            
//            objTblCelEdiTxtFor.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    verificarUbiItm();
//                }
//            });
                    
            objTblCelEdiTxtFor = null;            
            
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_UBI).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_UBI).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            //Configurar JTable: Establecer los listener para el TableHeader.
             tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                 public void mouseClicked(java.awt.event.MouseEvent evt) {
                     tblDatMouseClicked(evt);
                 }
             });
    
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux = null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {
        int i, intNumFil;
        try {
            intNumFil = tblDat.getRowCount();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.

            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_DAT_CHK_UBI) {
                if (blnMarTodCanTbl) {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++) {
                        tblDat.setValueAt(Boolean.FALSE, i, INT_TBL_DAT_CHK_UBI);                            
                    }
                    blnMarTodCanTbl = false;
                }else {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++) {
                        tblDat.setValueAt(Boolean.TRUE, i, INT_TBL_DAT_CHK_UBI);                            
                    }
                    blnMarTodCanTbl = true;
                }
            }

        }         
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e); 
        }
    }

    private void verificarUbiItm() {
        int intNumFil, i;
        intNumFil=objTblMod.getRowCountTrue();
        for (i=0; i<intNumFil; i++) {
            if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")) {
                if (objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_UBI_ITM).toString().equals(objTblMod.getValueAt(i, INT_TBL_DAT_UBI_ITM).toString())){
                    mostrarMsgInf("Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.");
                    break;                     
                }
                
            }
            
        }
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg() 
    {
        boolean blnRes = true;
        try 
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                //Obtener la condición.
                strConSQL = "";
                
                if (txtCodItm.getText().length()>0)
                    strConSQL+=" AND a1.co_itm=" + txtCodItm.getText();
                
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
                    strConSQL+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                
                if (txtCodAltItmTer.getText().length()>0) {
                    strConSQL+=getConSQLAdiCamTer("a1.tx_codAlt", txtCodAltItmTer.getText());
                }
                
                if (optItmSinUbi.isSelected()) {
                    strConSQL+=" AND (a5.tx_ubi is null OR a5.tx_ubi = '') ";
                } else {
                    if (optItmConUbi.isSelected()) {
                        if (txtUbi.getText().equals("")) {
                            strConSQL+=" AND a5.tx_ubi is not null AND a5.tx_ubi != ''";
                        } else {
                            strConSQL+=" AND a5.tx_ubi like '" + txtUbi.getText() + "%'";                            
                        }
                    }
                }
                
                if (chkSolStk.isSelected())
                    strConSQL+=" AND a3.nd_stkAct > 0";
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.tx_codAlt2, a4.co_reg, a4.tx_desCor, a1.st_ser, a3.nd_stkAct, a1.nd_pesItmKgr, a5.tx_ubi, a5.st_impord";
                strSQL+=" FROM tbm_inv AS a1";
                strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                strSQL+=" LEFT OUTER JOIN (";
                strSQL+=" SELECT b1.co_itmMae, SUM(b2.nd_stkAct) AS nd_stkAct";
                strSQL+=" FROM tbm_equInv AS b1";
                strSQL+=" INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                strSQL+=" INNER JOIN tbr_bodempbodgrp b3 on (b3.co_emp = b2.co_emp and b3.co_bod = b2.co_bod)";
                strSQL+=" WHERE b3.co_empgrp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND b3.co_bodgrp = " + txtCodBod.getText();                
                strSQL+=" GROUP BY b1.co_itmMae";
                strSQL+=" ) AS a3 ON (a2.co_itmMae=a3.co_itmMae)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a4 ON (a1.co_uni=a4.co_reg)";
                strSQL+=" LEFT OUTER JOIN (";
                strSQL+=" SELECT  DISTINCT b1.co_itmMae, b2.tx_ubi, b2.st_impOrd";
                strSQL+=" FROM tbm_equInv AS b1";
                strSQL+=" INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                strSQL+=" INNER JOIN tbr_bodempbodgrp b3 on (b3.co_emp = b2.co_emp and b3.co_bod = b2.co_bod)";
                strSQL+=" WHERE b3.co_empgrp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND b3.co_bodgrp = " + txtCodBod.getText();                
                strSQL+=" ) AS a5 ON (a2.co_itmMae=a5.co_itmMae)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" AND a1.st_ser='N'";
                strSQL+=strConSQL;
                strSQL+=" ORDER BY a1.tx_codAlt";
                
                System.out.println(strSQL);
                
                rst=stm.executeQuery(strSQL);
                
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
                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE, rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_ITM, rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM, rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM, rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_DES_COR_UNI_MED, rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_PES_KGR, rst.getString("nd_pesItmKgr"));
                        vecReg.add(INT_TBL_DAT_UBI_ITM, rst.getString("tx_ubi"));
                        vecReg.add(INT_TBL_DAT_CHK_UBI, rst.getString("st_impOrd").equals("S")?Boolean.TRUE:Boolean.FALSE);
                        vecReg.add(INT_TBL_DAT_COD_LET_ITM, rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_STK_CON, rst.getString("nd_stkAct"));
                        
                        vecDat.add(vecReg);
                    }
                    else 
                    {
                        break;
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
     * Esta función permite actualizar los registros del detalle.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
    */
    private boolean actualizarDet()
    {
        int intNumFil, i;
        boolean blnRes = true;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            
            if (con != null) 
            {
                stm = con.createStatement();
                //Obtener la fecha del servidor.
                intNumFil=objTblMod.getRowCountTrue();
                for (i=0; i<intNumFil;i++) 
                {
                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M"))
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_invBod";
                        strSQL+=" SET tx_ubi = "  + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_UBI_ITM),1);
                        strSQL+=" , st_impOrd = '"  + ((objTblMod.getValueAt(i, INT_TBL_DAT_CHK_UBI).toString().equals("true"))?"S":"N") + "'";
                        strSQL+=" FROM (";
                        strSQL+=" SELECT a1.co_emp, a2.co_itm, a3.co_bod";
                        strSQL+=" FROM tbm_inv AS a1";
                        strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                        strSQL+=" INNER JOIN tbm_invbod AS a3 ON (a3.co_emp=a2.co_emp AND a3.co_itm=a2.co_itm)";
                        strSQL+=" INNER JOIN tbr_bodempbodgrp a4 on (a4.co_emp = a3.co_emp and a4.co_bod = a3.co_bod)";
                        strSQL+=" WHERE a2.co_itmMae = " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE);
                        strSQL+=" AND a4.co_empgrp = " + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a4.co_bodgrp = " + txtCodBod.getText();
                        strSQL+=" ) AS b1";
                        strSQL+=" WHERE tbm_invBod.co_emp=b1.co_emp AND tbm_invBod.co_itm=b1.co_itm AND tbm_invBod.co_bod=b1.co_bod";
                        stm.executeUpdate(strSQL);
                    }
                }
                
                if (isUbiDup()) {                    
                    con.commit();
                    //Inicializo el estado de las filas.
                    objTblMod.initRowsState();
                    objTblMod.setDataModelChanged(false);
                } else {
                    mostrarMsgInf(strMsgItmUbi);
                    con.rollback(); 
                    blnRes = false;
                }
                
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
    
    private boolean isUbiDup() 
    {
        boolean blnRes = true;
        int i, intNumTotFil;
        
        try 
        {
            if (con != null)
            {
                intNumTotFil = objTblMod.getRowCountTrue();
                
                stm = con.createStatement();
                //Armar la sentencia SQL.
                strSQL ="SELECT DISTINCT co_itmmae, tx_codalt, tx_nomitm, a2.tx_ubi";
                strSQL +=" FROM tbm_inv AS a0";
                strSQL +=" INNER JOIN tbm_equInv AS a1 ON (a1.co_emp = a0.co_emp AND a1.co_itm = a0.co_itm)";
                strSQL +=" INNER JOIN tbm_invBod AS a2 ON (a2.co_emp = a1.co_emp AND a2.co_itm = a1.co_itm)";
                strSQL +=" INNER JOIN tbr_bodempbodgrp AS a3 ON (a3.co_emp = a2.co_emp and a3.co_bod = a2.co_bod)";
                strSQL +=" INNER JOIN (";
                strSQL +=" SELECT tx_ubi, COUNT(DISTINCT co_itmMae)";
                strSQL +=" FROM tbm_equInv AS b1";
                strSQL +=" INNER JOIN tbm_invBod AS b2 ON (b2.co_emp = b1.co_emp AND b2.co_itm = b1.co_itm)";
                strSQL +=" INNER JOIN tbr_bodempbodgrp AS b3 ON (b3.co_emp = b2.co_emp and b3.co_bod = b2.co_bod)";
                strSQL +=" WHERE b3.co_empgrp = " + objParSis.getCodigoEmpresa();
                strSQL +=" AND b3.co_bodgrp = " + txtCodBod.getText();
                strSQL +=" AND tx_ubi IS NOT NULL AND tx_ubi != ''";
                strSQL +=" GROUP BY tx_ubi";
                strSQL +=" HAVING COUNT(DISTINCT b1.co_itmMae) > 1";
                strSQL +=" ) AS a4 ON (a4.tx_ubi = A2.tx_ubi)";
                strSQL +=" WHERE a3.co_empgrp = " + objParSis.getCodigoEmpresa();
                strSQL +=" AND a3.co_bodgrp = " + txtCodBod.getText();
                rst=stm.executeQuery(strSQL);
                
                strMsgItmUbi="<HTML>No se pueden guardar los cambios porque existen items con la misma ubicación:";
                while (rst.next()) {
                    strMsgItmUbi+="<BR><FONT COLOR=\"blue\">" + rst.getString("tx_codalt") + ": </FONT><FONT COLOR=\"red\">" + rst.getString("tx_ubi") + "</FONT>";
                    blnRes = false;
                }
                strMsgItmUbi+="<BR>Revise las ubicaciones y vuelva a intentarlo.</HTML>";
                
                rst.close();
                stm.close();
                rst=null;
                stm=null;
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
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
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
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg) {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()
    {
        boolean blnRes = true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("d1.co_itm");
            arlCam.add("d1.tx_codAlt");
            arlCam.add("d1.tx_codAlt2");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.Itm.");
            arlAli.add("Cód.Let.Itm.");
            arlAli.add("Item");
            arlAli.add("Uni.Med.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("60");
            arlAncCol.add("300");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_codAlt";
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(3, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
        }
        catch (Exception e) {     blnRes=false;    objUti.mostrarMsgErr_F1(this, e);  }
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
    private boolean mostrarVenConItm(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.setVisible(true);
                    if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodLetItm.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodLetItm.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodLetItm.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Codigo alterno 2".
                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodLetItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodLetItm.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodLetItm.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodLetItm.setText(strCodLetItm);
                        }
                    }
                    break;   
                case 3: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodLetItm.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(3);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodLetItm.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtNomItm.setText(strNomItm);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)  {    blnRes=false;      objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }
    
    /**
     * Esta función obtiene la condición SQL adicional para los campos que "Terminan con".
     * La cadena recibida es separada para formar la condición que se agregará la sentencia SQL.
     * Por ejemplo: 
     * Si strCam="a2.tx_codAlt" y strCad="I, S, L" el resultado sería "AND (a2.tx_codalt LIKE '%I' OR a2.tx_codalt LIKE '%S' OR a2.tx_codalt LIKE '%L')"
     * @param strCam El campo que se utilizará para la condición.
     * @param strCad La cadena que se separará para formar la condición.
     * @return La cadena que contiene la condición SQL .
     */
    private String getConSQLAdiCamTer(String strCam, String strCad)
    {
        byte i;
        String strRes="";
        try
        {
            if (strCad.length()>0)
            {
                java.util.StringTokenizer stkAux=new java.util.StringTokenizer(strCad, ",", false);
                i=0;
                while (stkAux.hasMoreTokens())
                {
                    if (i==0)
                        strRes+=" AND (LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    else
                        strRes+=" OR LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    i++;
                }
                strRes+=")";
            }
        }
        catch (java.util.NoSuchElementException e)
        {
            strRes="";
        }
        return strRes;
    }
    
    private boolean validarDat() 
    {
        boolean blnRes=true;

        if(txtCodBod.getText().equals(""))
        {
           mostrarMsgInf("LA BODEGA ES OBLIGATORIA...");
           txtCodBod.requestFocus();
           return false;
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
    private class ZafThreadGUI extends Thread 
    {
        public void run() 
        {
            if(validarDat()) 
            {
                if (!cargarDetReg()) 
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }
                //Establecer el foco en el JTable sólo cuando haya datos.
                if (tblDat.getRowCount()>0) 
                {
                    tabFrm.setSelectedIndex(1);
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.requestFocus();
                }
            }
            objThrGUI=null;
        }
    }
    
    
    public class ZafTblCelEdiTxtFor extends javax.swing.DefaultCellEditor 
    {
        JFormattedTextField ftf;
        protected EventListenerList objEveLisLis=new EventListenerList();
        
        public ZafTblCelEdiTxtFor() 
        {
            super(new JFormattedTextField());
            ftf = (JFormattedTextField) getComponent();
            MaskFormatter maskformatter = null;

            try
            {
                maskformatter = new MaskFormatter("U-##-U-##U");
                maskformatter.setPlaceholderCharacter(' ');
            } 
            catch (ParseException e) 
            {
                System.err.println("Formato no válido: " + e.getMessage());
                System.exit(-1);
            }

            ftf.setFormatterFactory(new DefaultFormatterFactory(maskformatter));
            ftf.setValue(null);
    //        ftf.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
            ftf.setFocusLostBehavior(JFormattedTextField.PERSIST);
            ftf.setBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0));
        }

        @Override
        public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column) {
            JFormattedTextField ftf = (JFormattedTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
            System.out.println("getTableCellEditorComponent: " + value);
            ftf.setValue(value);
            return ftf;
        }

        @Override
        public Object getCellEditorValue() {
            JFormattedTextField ftf = (JFormattedTextField) getComponent();
            try {
                ftf.commitEdit();
            } catch (ParseException e) {
                System.out.println("Exception:" + e.getMessage() + " Texto: " + ftf.getText() ); 
                ftf.setValue(null);
            }

            System.out.println("getCellEditorValue: " + ftf.getValue());
            System.out.println("getCellEditorTexto: " + ftf.getText());
            return ftf.getValue();
        }
        
//        /**
//         * Esta función adiciona el listener que controlará los eventos de edición.
//         * @param listener El objeto que implementa los métodos de la interface "ZafTableListener".
//         */
//        public void addTableEditorListener(ZafTableListener listener)
//        {
//            objEveLisLis.add(ZafTableListener.class, listener);
//        }        
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
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg="Código maestro del item";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del Item";
                    break;
                case INT_TBL_DAT_DES_COR_UNI_MED:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_PES_KGR:
                    strMsg="Peso(Kg)";
                    break;
                case INT_TBL_DAT_UBI_ITM:
                    strMsg="Ubicación";
                    break;
                case INT_TBL_DAT_CHK_UBI:
                    strMsg="Imprime en orden de despacho";
                    break;
                case INT_TBL_DAT_COD_LET_ITM:
                    strMsg="Código en letras del item";
                    break;
                case INT_TBL_DAT_STK_CON:
                    strMsg="Stock";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}
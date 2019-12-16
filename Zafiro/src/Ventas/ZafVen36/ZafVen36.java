package Ventas.ZafVen36;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafUtil.ZafLocPrgUsr;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPrintPage;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import javax.print.PrintService;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.icepdf.ri.common.MyAnnotationCallback;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.PropertiesManager;

/**
 *
 * @author Tony Sanginez
 */
public class ZafVen36 extends javax.swing.JInternalFrame {

    //Constantes: Columnas del JTable.
    private static final int INT_TBL_DAT_LIN = 0;                         //Línea.
    private static final int INT_TBL_DAT_CHK_SEL = 1;                     //Selección.
    private static final int INT_TBL_DAT_COD_EMP = 2;                     //Código de la empresa.
    private static final int INT_TBL_DAT_COD_LOC = 3;                     //Código del local.
    private static final int INT_TBL_DAT_COD_TIP_DOC = 4;                 //Código del tipo de documento.
    private static final int INT_TBL_DAT_DEC_TIP_DOC = 5;                 //Descripción corta del tipo de documento.
    private static final int INT_TBL_DAT_DEL_TIP_DOC = 6;                 //Descripción larga del tipo de documento.
    private static final int INT_TBL_DAT_COD_DOC = 7;                     //Código del documento (Sistema).
    private static final int INT_TBL_DAT_NUM_DOC = 8;                     //Número del documento (Preimpreso).
    private static final int INT_TBL_DAT_FEC_DOC = 9;                     //Fecha del documento.
    private static final int INT_TBL_DAT_COD_CLI = 10;                    //Código del cliente.
    private static final int INT_TBL_DAT_NOM_CLI = 11;                    //Nombre del cliente.
    private static final int INT_TBL_DAT_TOT_DOC = 12;                    //Total del documento.
    private static final int INT_TBL_DAT_EST_DOC = 13;                    //Estado del documento.

    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafTblTot objTblTot;                                //JTable de totales.
    private ZafVenCon vcoLoc;                                   //Ventana de consulta.
    private ZafVenCon vcoTipDoc;                                //Ventana de consulta.
    private ZafVenCon vcoCli;                                   //Ventana de consulta.
    private ZafVenCon vcoVen;                                   //Ventana de consulta.
    private ZafVenCon vcoForImp;                                //Ventana de consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecEstReg;                                   //Estado del registro.
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodLoc, strNomLoc;                        //Contenido del campo al obtener el foco.
    private String strDesCorTipDoc, strDesLarTipDoc;            //Contenido del campo al obtener el foco.
    private String strNomForImp, strCodForImp;                                //Contenido del campo al obtener el foco.
    private String strCodCli, strDesLarCli;                     //Contenido del campo al obtener el foco.
    private String strCodVen, strNomVen;                        //Contenido del campo al obtener el foco.
    private ZafLocPrgUsr objLocPrgUsr;                          //Objeto que almacena los locales por usuario y programa.
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
    private boolean blnMarTodChkTblSel = true;                                 //Marcar todas las casillas de verificación del JTable de bodegas.
    private String strVersion = "v0.2";
    private boolean ctrl = false;
    private String keyHit = "", strDesForPagSri;
    private boolean booFormatA4 = false;
    private boolean booFormatA5 = true;
    private StringBuffer sb = new StringBuffer("");
    private int intCont = 0;
    private String strRutRpt, strNomRpt, strLogo;
    private String strSQLGlo;
    private List<InputStream> listPdfs = new ArrayList<InputStream>();
    private List<String> listPdfsDir = new ArrayList<String>();
    
    public ZafVen36(ZafParSis obj) {
        try {
            KeyboardFocusManager.getCurrentKeyboardFocusManager()
                    .addKeyEventDispatcher(new KeyEventDispatcher() {
                        @Override
                        public boolean dispatchKeyEvent(KeyEvent e) {

                            if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                                ctrl = true;
                            } else if (e.getKeyCode() == KeyEvent.VK_H) {
                                keyHit = KeyEvent.getKeyText(e.getKeyCode());
                            } else {
                                sb = new StringBuffer("");
                            }
                            if (ctrl) {
                                sb.append("ctrl ");
                            }
                            sb.append(keyHit);
                            ctrl = false;
                            keyHit = "";
                            if (intCont == 0) {
                                if (!sb.toString().startsWith("c")) {
                                    sb = new StringBuffer("");
                                }
                                if (sb.toString().contains("ctrl") && sb.toString().contains("H")) {
                                    intCont++;
                                    sb = new StringBuffer("");
                                    if (booFormatA5) {
                                        sb = new StringBuffer("");
                                        mostrarMsg("Se cambió a formato A4.");
                                        booFormatA4 = true;
                                        booFormatA5 = false;
                                        intCont = 0;
                                    } else {
                                        sb = new StringBuffer("");
                                        mostrarMsg("Se cambió a formato A5.");
                                        booFormatA4 = false;
                                        booFormatA5 = true;
                                        intCont = 0;
                                    }
                                }
                            } else {
                                sb = new StringBuffer("");
                            }
                            return false;
                        }
                    });
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
            } else {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde EMPRESAS.");
                dispose();
            }
        } catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
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
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblEstReg = new javax.swing.JLabel();
        cboEstReg = new javax.swing.JComboBox();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblLoc = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        lblVen = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        lblLoc1 = new javax.swing.JLabel();
        txtCodForImp = new javax.swing.JTextField();
        txtNomForImp = new javax.swing.JTextField();
        butForImp = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butVisPre = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
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

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 140, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(10, 160, 400, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(20, 10, 140, 20);

        txtCodTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        panFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(140, 10, 32, 20);

        txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(170, 10, 56, 20);

        txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        panFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(230, 10, 360, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panFil.add(butTipDoc);
        butTipDoc.setBounds(590, 10, 20, 20);

        lblEstReg.setText("Estado del documento:");
        lblEstReg.setToolTipText("Estado del documento");
        panFil.add(lblEstReg);
        lblEstReg.setBounds(30, 250, 150, 20);
        panFil.add(cboEstReg);
        cboEstReg.setBounds(180, 250, 264, 20);

        lblCli.setText("Cliente/Proveedor:");
        lblCli.setToolTipText("Beneficiario");
        panFil.add(lblCli);
        lblCli.setBounds(30, 210, 120, 20);

        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        panFil.add(txtCodCli);
        txtCodCli.setBounds(180, 210, 56, 20);

        txtDesLarCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusLost(evt);
            }
        });
        txtDesLarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCliActionPerformed(evt);
            }
        });
        panFil.add(txtDesLarCli);
        txtDesLarCli.setBounds(240, 210, 360, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(600, 210, 20, 20);

        lblLoc.setText("Local:");
        lblLoc.setToolTipText("Local");
        panFil.add(lblLoc);
        lblLoc.setBounds(30, 190, 120, 20);

        txtCodLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLocFocusLost(evt);
            }
        });
        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });
        panFil.add(txtCodLoc);
        txtCodLoc.setBounds(180, 190, 56, 20);

        txtNomLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLocFocusLost(evt);
            }
        });
        txtNomLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomLocActionPerformed(evt);
            }
        });
        panFil.add(txtNomLoc);
        txtNomLoc.setBounds(240, 190, 360, 20);

        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFil.add(butLoc);
        butLoc.setBounds(600, 190, 20, 20);

        lblVen.setText("Vendedor/Comprador:");
        lblVen.setToolTipText("Vendedor/Comprador");
        panFil.add(lblVen);
        lblVen.setBounds(30, 230, 150, 20);

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
        txtCodVen.setBounds(180, 230, 56, 20);

        txtNomVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVenFocusLost(evt);
            }
        });
        txtNomVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVenActionPerformed(evt);
            }
        });
        panFil.add(txtNomVen);
        txtNomVen.setBounds(240, 230, 360, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFil.add(butVen);
        butVen.setBounds(600, 230, 20, 20);

        lblLoc1.setText("Formato de impresión:");
        lblLoc1.setToolTipText("Local");
        panFil.add(lblLoc1);
        lblLoc1.setBounds(20, 30, 160, 20);

        txtCodForImp.setBackground(objParSis.getColorCamposObligatorios());
        txtCodForImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodForImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodForImpFocusLost(evt);
            }
        });
        txtCodForImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodForImpActionPerformed(evt);
            }
        });
        panFil.add(txtCodForImp);
        txtCodForImp.setBounds(170, 30, 56, 20);

        txtNomForImp.setBackground(objParSis.getColorCamposObligatorios());
        txtNomForImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomForImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomForImpFocusLost(evt);
            }
        });
        txtNomForImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomForImpActionPerformed(evt);
            }
        });
        panFil.add(txtNomForImp);
        txtNomForImp.setBounds(230, 30, 360, 20);

        butForImp.setText("...");
        butForImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butForImpActionPerformed(evt);
            }
        });
        panFil.add(butForImp);
        butForImp.setBounds(590, 30, 20, 20);

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
        tblDat.setToolTipText("");
        tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMouseClicked(evt);
            }
        });
        tblDat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDatKeyPressed(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

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

        panRpt.add(spnTot, java.awt.BorderLayout.SOUTH);

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

        butVisPre.setText("Vista Preliminar");
        butVisPre.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butVisPre.setPreferredSize(new java.awt.Dimension(150, 25));
        butVisPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisPreActionPerformed(evt);
            }
        });
        panBot.add(butVisPre);

        butImp.setText("Imprimir");
        butImp.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butImp.setPreferredSize(new java.awt.Dimension(92, 25));
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panBot.add(butImp);

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

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        mostrarVenConLoc(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length() > 0) {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butLocActionPerformed

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomLoc.getText().equalsIgnoreCase(strNomLoc)) {
            if (txtNomLoc.getText().equals("")) {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            } else {
                mostrarVenConLoc(2);
            }
        } else {
            txtNomLoc.setText(strNomLoc);
        }
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length() > 0) {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomLocFocusLost

    private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
        strNomLoc = txtNomLoc.getText();
        txtNomLoc.selectAll();
    }//GEN-LAST:event_txtNomLocFocusGained

    private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
        txtNomLoc.transferFocus();
    }//GEN-LAST:event_txtNomLocActionPerformed

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc)) {
            if (txtCodLoc.getText().equals("")) {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            } else {
                mostrarVenConLoc(1);
            }
        } else {
            txtCodLoc.setText(strCodLoc);
        }
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length() > 0) {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        strCodLoc = txtCodLoc.getText();
        txtCodLoc.selectAll();
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length() > 0) {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butCliActionPerformed

    private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli)) {
            if (txtDesLarCli.getText().equals("")) {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
            } else {
                mostrarVenConCli(2);
            }
        } else {
            txtDesLarCli.setText(strDesLarCli);
        }
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length() > 0) {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesLarCliFocusLost

    private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
        strDesLarCli = txtDesLarCli.getText();
        txtDesLarCli.selectAll();
    }//GEN-LAST:event_txtDesLarCliFocusGained

    private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
        txtDesLarCli.transferFocus();
    }//GEN-LAST:event_txtDesLarCliActionPerformed

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)) {
            if (txtCodCli.getText().equals("")) {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
            } else {
                mostrarVenConCli(1);
            }
        } else {
            txtCodCli.setText(strCodCli);
        }
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length() > 0) {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli = txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(2);
            }
        } else {
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodTipDoc.getText().length() > 0) {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc = txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodTipDoc.getText().length() > 0) {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(1);
                txtCodForImp.setText("");
                txtNomForImp.setText("");
            }
        } else {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
            txtCodForImp.setText("");
            txtNomForImp.setText("");
        }
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodTipDoc.getText().length() > 0) {
            optFil.setSelected(true);
            txtCodForImp.setText("");
            txtNomForImp.setText("");
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc = txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected()) {
            txtCodLoc.setText("");
            txtNomLoc.setText("");
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodVen.setText("");
            txtNomVen.setText("");
            txtCodCli.setText("");
            txtDesLarCli.setText("");
            txtCodForImp.setText("");
            txtNomForImp.setText("");
            cboEstReg.setSelectedIndex(0);
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        //Abrir la opción seleccionada al presionar ENTER.
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            evt.consume();
            //();
        }
    }//GEN-LAST:event_tblDatKeyPressed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar")) {
            blnCon = true;
            if (objThrGUI == null) {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }
        } else {
            blnCon = false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
        //Abrir la opción seleccionada al dar doble click.
        if (evt.getClickCount() == 2) {
            // abrirFrm();
        }
    }//GEN-LAST:event_tblDatMouseClicked

    /**
     * Cerrar la aplicación.
     */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        txtCodVen.transferFocus();
}//GEN-LAST:event_txtCodVenActionPerformed

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        strCodVen = txtCodVen.getText();
        txtCodVen.selectAll();
}//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodVen.getText().equalsIgnoreCase(strCodVen)) {
            if (txtCodVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            } else {
                mostrarVenConVen(1);
            }
        } else {
            txtCodVen.setText(strCodVen);
        }
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length() > 0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtCodVenFocusLost

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        txtNomVen.transferFocus();
}//GEN-LAST:event_txtNomVenActionPerformed

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        strNomVen = txtNomVen.getText();
        txtNomVen.selectAll();
}//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomVen.getText().equalsIgnoreCase(strNomVen)) {
            if (txtNomVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            } else {
                mostrarVenConVen(2);
            }
        } else {
            txtNomVen.setText(strNomVen);
        }
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length() > 0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtNomVenFocusLost

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        mostrarVenConVen(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length() > 0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butVenActionPerformed

    private void txtCodForImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForImpFocusGained
        strCodForImp = txtCodForImp.getText();
        txtCodForImp.selectAll();
    }//GEN-LAST:event_txtCodForImpFocusGained

    private void txtCodForImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForImpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodForImp.getText().equalsIgnoreCase(strCodForImp)) {
            if (txtCodForImp.getText().equals("")) {
                txtCodForImp.setText("");
                txtNomForImp.setText("");
            } else {
                configurarVenConForImp();
                mostrarVenConForImp(1);
            }
        } else {
            txtCodForImp.setText(strCodForImp);
        }
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodForImp.getText().length() > 0) {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodForImpFocusLost

    private void txtCodForImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodForImpActionPerformed
        txtCodForImp.transferFocus();
    }//GEN-LAST:event_txtCodForImpActionPerformed

    private void txtNomForImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForImpFocusGained
        strNomForImp = txtNomForImp.getText();
        txtNomForImp.selectAll();
    }//GEN-LAST:event_txtNomForImpFocusGained

    private void txtNomForImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForImpFocusLost
        if (!txtNomForImp.getText().equalsIgnoreCase(strNomForImp)) {
            if (txtNomForImp.getText().equals("")) {
                txtCodForImp.setText("");
                txtNomForImp.setText("");
            } else {
                configurarVenConForImp();
                mostrarVenConForImp(2);
            }
        } else {
            txtNomForImp.setText(strNomForImp);
        }
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodForImp.getText().length() > 0) {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomForImpFocusLost

    private void txtNomForImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomForImpActionPerformed
        txtNomForImp.transferFocus();
    }//GEN-LAST:event_txtNomForImpActionPerformed

    private void butForImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butForImpActionPerformed
        if (txtCodTipDoc.getText().length() > 0) {
            configurarVenConForImp();
            mostrarVenConForImp(0);
            if (txtNomForImp.getText().length() > 0) {
                optFil.setSelected(true);
            }
        } else {
            mostrarMsgInf("Seleccione un tipo de documento.");
        }

    }//GEN-LAST:event_butForImpActionPerformed

    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        if (validarFiltros()) {
            if (tblDat != null) {
                generarRpt(2);
            }
        }
    }//GEN-LAST:event_butVisPreActionPerformed

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        if (validarFiltros()) {
            if (tblDat != null) {
                String strMsg = "¿Está seguro que desea continuar?";
                if (JOptionPane.showConfirmDialog(this, strMsg, "Impresión directa", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
                    generarRpt(1);
                }
            }
        }
    }//GEN-LAST:event_butImpActionPerformed

    /**
     * Cerrar la aplicación.
     */
    private void exitForm() {
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butForImp;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butVen;
    private javax.swing.JButton butVisPre;
    private javax.swing.JComboBox cboEstReg;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblEstReg;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblLoc1;
    private javax.swing.JLabel lblMsgSis;
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
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodForImp;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomForImp;
    private javax.swing.JTextField txtNomLoc;
    private javax.swing.JTextField txtNomVen;
    // End of variables declaration//GEN-END:variables

    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            //Configurar ZafSelFec:
            objSelFec = new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setTitulo("Fecha del documento");
            panFil.add(objSelFec);
            objSelFec.setBounds(4, 60, 472, 72);
            //Inicializar objetos.
            objUti = new ZafUtil();
            //Obtener los locales por Usuario y Programa.
            objLocPrgUsr = new ZafLocPrgUsr(objParSis);
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodTipDoc.setVisible(false);
            //Configurar Combos.
            configurarComboEstReg();
            //Configurar las ZafVenCon.
            configurarVenConLoc();
            configurarVenConTipDoc();
            configurarVenConCli();
            configurarVenConVen();

            //Configurar los JTables.
            configurarTblDat();
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarComboEstReg() {
        boolean blnRes = true;
        try {
            //Configurar el combo "Estado de registro".
            vecEstReg = new Vector();
            vecEstReg.add("");
            vecEstReg.add("A");
            vecEstReg.add("I");
            cboEstReg.addItem("(Todos)");
            cboEstReg.addItem("A: Activo");
            cboEstReg.addItem("I: Anulado");
            cboEstReg.setSelectedIndex(0);
        } catch (Exception e) {
            blnRes = false;
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
    private boolean configurarTblDat() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(14);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_CHK_SEL, "");
            vecCab.add(INT_TBL_DAT_COD_EMP, "Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC, "Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC, "Código");
            vecCab.add(INT_TBL_DAT_DEC_TIP_DOC, "Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEL_TIP_DOC, "Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC, "Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC, "Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC, "Fec.Doc.");
            vecCab.add(INT_TBL_DAT_COD_CLI, "Cód.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_CLI, "Cliente/Proveedor");
            vecCab.add(INT_TBL_DAT_TOT_DOC, "Total");
            vecCab.add(INT_TBL_DAT_EST_DOC, "Est.Doc.");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK_SEL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DEC_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(157);
            tcmAux.getColumn(INT_TBL_DAT_EST_DOC).setPreferredWidth(50);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHK_SEL).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblSelTodMouseClicked(evt);
                }
            });

            //Configurar JTable: Editor de búsqueda.
            objTblBus = new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_DAT_TOT_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK_SEL);
            //vecAux.add("" + INT_TBL_DAT_);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_EST_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            //Configurar Jtable: Renderizar checkbox
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_SEL).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;
            tblDat.setEditingColumn(INT_TBL_DAT_CHK_SEL);
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_SEL).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                }
            });
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd = new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[] = {INT_TBL_DAT_TOT_DOC};
            objTblTot = new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);

            //Libero los objetos auxiliares.
            tcmAux = null;
            setEditable(true);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void setEditable(boolean editable) {
        if (editable == true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        } else {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }

    private String sqlConFil() {
        String sqlFil = "", strTipDoc = "";

        switch (objSelFec.getTipoSeleccion()) {
            case 0: //Búsqueda por rangos
                sqlFil += " AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 1: //Fechas menores o iguales que "Hasta".
                sqlFil += " AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 2: //Fechas mayores o iguales que "Desde".
                sqlFil += " AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 3: //Todo.
                break;
        }

        if (txtCodLoc.getText().length() > 0) {
            sqlFil += " AND a1.co_loc=" + txtCodLoc.getText();
        }
        if (txtCodTipDoc.getText().length() > 0) {
            sqlFil += " AND a1.co_tipDoc=" + txtCodTipDoc.getText();
        }
        if (txtCodCli.getText().length() > 0) {
            sqlFil += " AND a1.co_cli=" + txtCodCli.getText();
        }
        if (txtCodVen.getText().length() > 0) {
            sqlFil += " AND a1.co_com=" + txtCodVen.getText();
        }

        if (cboEstReg.getSelectedIndex() > 0) {
            sqlFil += " AND a1.st_reg='" + vecEstReg.get(cboEstReg.getSelectedIndex()) + "'";
        }

        //Tipos de Documentos
        if (objParSis.getCodigoUsuario() == 1) {
            strTipDoc = " select b.co_tipdoc from tbr_tipdocprg b";
            strTipDoc += " where b.co_emp=" + objParSis.getCodigoEmpresa();
            strTipDoc += " and b.co_loc=" + objParSis.getCodigoLocal();
            strTipDoc += " and b.co_mnu=" + objParSis.getCodigoMenu();
        } else {
            strTipDoc = " select b.co_tipdoc from tbr_tipDocUsr b";
            strTipDoc += " where b.co_emp=" + objParSis.getCodigoEmpresa();
            strTipDoc += " and b.co_loc=" + objParSis.getCodigoLocal();
            strTipDoc += " and b.co_mnu=" + objParSis.getCodigoMenu();
            strTipDoc += " and b.co_usr=" + objParSis.getCodigoUsuario();
        }
        sqlFil += " AND a1.co_tipDoc IN (" + strTipDoc + ")";

        return sqlFil;
    }

    /**
     * Esta función permite consultar los registros de acuerdo al criterio
     * seleccionado.
     *
     * @param strConSQL Filtros de Consulta
     * @return true: Si se pudo realizar la consulta de los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(String strConSQL) {
        int intCodEmp;
        int intTipLoc = 1; //Tipo de Consulta para generar query de locales. 1=Código Local; 2=Todos los datos del local.
        boolean blnRes = true;
        try {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp = objParSis.getCodigoEmpresa();
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();

                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objParSis.getCodigoUsuario() == 1) {
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                    strSQL += ", a1.co_cli, a1.tx_nomCli, a1.co_forPag, a1.tx_desForPag, a1.nd_sub, a1.nd_valIva, a1.nd_tot, a1.st_reg";
                    strSQL += ", a1.fe_ing, a1.co_usrIng, a4.tx_usr AS a4_tx_usr, a1.tx_comIng, a1.fe_ultMod, a1.co_usrMod, a5.tx_usr AS a5_tx_usr, a1.tx_comMod";
                    strSQL += " FROM tbm_cabMovInv AS a1";
                    strSQL += " INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL += " LEFT OUTER JOIN tbm_usr AS a4 ON (a1.co_usrIng=a4.co_usr)";
                    strSQL += " LEFT OUTER JOIN tbm_usr AS a5 ON (a1.co_usrMod=a5.co_usr)";
                    strSQL += " WHERE a1.co_emp=" + intCodEmp;
                    strSQL += strConSQL;
                    strSQL += " ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.fe_doc, a1.ne_numDoc";
                    rst = stm.executeQuery(strSQL);
                } else {
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL += " SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                    strSQL += " ,a1.co_cli, a1.tx_nomCli, a1.co_forPag, a1.tx_desForPag, a1.nd_sub, a1.nd_valIva, a1.nd_tot, a1.st_reg";
                    strSQL += " ,a1.fe_ing, a1.co_usrIng, a4.tx_usr AS a4_tx_usr, a1.tx_comIng, a1.fe_ultMod, a1.co_usrMod, a5.tx_usr AS a5_tx_usr, a1.tx_comMod";
                    strSQL += " FROM tbm_cabMovInv AS a1";
                    strSQL += " INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL += " LEFT OUTER JOIN tbm_usr AS a4 ON (a1.co_usrIng=a4.co_usr)";
                    strSQL += " LEFT OUTER JOIN tbm_usr AS a5 ON (a1.co_usrMod=a5.co_usr)";
                    strSQL += " WHERE a1.co_emp=" + intCodEmp;

                    //Valida si el usuario tiene acceso a locales.
                    if ((objLocPrgUsr.validaLocUsr())) {
                        strSQL += " AND a1.co_loc in (" + objLocPrgUsr.cargarLocUsr(intTipLoc) + ")";
                    } else {
                        strSQL += " AND a1.co_loc not in (" + objLocPrgUsr.cargarLoc(intTipLoc) + ")";
                    }
                    strSQL += strConSQL;
                    strSQL += " ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.fe_doc, a1.ne_numDoc";
                    rst = stm.executeQuery(strSQL);
                }
                System.out.println("cargarDetReg: " + strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()) {
                    if (blnCon) {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LIN, "");
                        vecReg.add(INT_TBL_DAT_CHK_SEL, "");
                        vecReg.add(INT_TBL_DAT_COD_EMP, rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC, rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC, rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DEC_TIP_DOC, rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DEL_TIP_DOC, rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_COD_DOC, rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC, rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC, rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_COD_CLI, rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI, rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_TOT_DOC, rst.getString("nd_tot"));
                        vecReg.add(INT_TBL_DAT_EST_DOC, rst.getString("st_reg"));
                        vecDat.add(vecReg);
                    } else {
                        break;
                    }
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
                vecDat.clear();
                //Calcular totales.
                objTblTot.calcularTotales();
                if (blnCon) {
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                } else {
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                }
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
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
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Locales".
     */
    private boolean configurarVenConLoc() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_loc");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("400");

            strSQL = "";

            if (objParSis.getCodigoUsuario() == 1) {
                strSQL = objLocPrgUsr.cargarLoc(2);
            } else {
                strSQL = objLocPrgUsr.cargarLocUsr(2);
            }

            //System.out.println("ConfigurarVenConLoc: "+strSQL);
            vcoLoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de locales", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoLoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if (objParSis.getCodigoUsuario() == 1) {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL += " FROM tbm_cabTipDoc AS a1";
                strSQL += " INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " ORDER BY a1.tx_desCor";
            } else {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL += " FROM tbm_cabTipDoc AS a1";
                strSQL += " INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL += " ORDER BY a1.tx_desCor";
            }
            vcoTipDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Clientes/Proveedores".
     */
    private boolean configurarVenConCli() {
        boolean blnRes = true;
        int intTipLoc = 1; //Tipo de Consulta para generar query de locales. 1=Código Local; 2=Todos los datos del local.
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");

            if (objParSis.getCodigoUsuario() == 1) {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL += " FROM tbm_cli AS a1";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.st_reg='A'";
                strSQL += " ORDER BY a1.tx_nom";
            } else {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += " SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL += " FROM tbm_cli AS a1";
                strSQL += " INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                //Valida si el usuario tiene acceso a locales.
                if ((objLocPrgUsr.validaLocUsr())) {
                    strSQL += " AND a2.co_loc in (" + objLocPrgUsr.cargarLocUsr(intTipLoc) + ")";
                } else {
                    strSQL += " AND a2.co_loc not in (" + objLocPrgUsr.cargarLoc(intTipLoc) + ")";
                }
                strSQL += " AND a1.st_reg='A'";
                strSQL += " ORDER BY a1.tx_nom";
            }

            //System.out.println("configurarVenConCli: "+strSQL);
            vcoCli = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCli.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Vendedores".
     */
    private boolean configurarVenConVen() {
        boolean blnRes = true;
        int intTipLoc = 1; //Tipo de Consulta para generar query de locales. 1=Código Local; 2=Todos los datos del local.

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
            arlAncCol.add("50");
            arlAncCol.add("120");
            arlAncCol.add("374");

            //Armar la sentencia SQL.
            strSQL = "";
            strSQL += " SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
            strSQL += " FROM tbm_usr AS a1";
            strSQL += " INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)";
            strSQL += " INNER JOIN tbr_locusr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_usr=a3.co_usr)";
            strSQL += " WHERE a1.st_reg='A' ";
            if (objParSis.getCodigoMenu() == 232) {
                strSQL += " AND a2.st_ven='S'";
            } else {
                strSQL += " AND a2.st_com='S'";
            }

            if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) {
                strSQL += " AND a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                if (objParSis.getCodigoUsuario() != 1) {
                    //Valida si el usuario tiene acceso a locales.
                    if ((objLocPrgUsr.validaLocUsr())) {
                        strSQL += " AND a3.co_loc in (" + objLocPrgUsr.cargarLocUsr(intTipLoc) + ")";
                    } else {
                        strSQL += " AND a3.co_loc not in (" + objLocPrgUsr.cargarLoc(intTipLoc) + ")";
                    }
                }
            }

            strSQL += " GROUP BY a1.co_usr, a1.tx_usr, a1.tx_nom ";
            strSQL += " ORDER BY a1.tx_nom";
            //System.out.println("configurarVenConVen: " + strSQL);

            vcoVen = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de vendedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoVen.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConLoc(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoLoc.setCampoBusqueda(2);
                    vcoLoc.setVisible(true);
                    if (vcoLoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText())) {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    } else {
                        vcoLoc.setCampoBusqueda(0);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        } else {
                            txtCodLoc.setText(strCodLoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoLoc.buscar("a1.tx_nom", txtNomLoc.getText())) {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    } else {
                        vcoLoc.setCampoBusqueda(2);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        } else {
                            txtNomLoc.setText(strNomLoc);
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.setVisible(true);
                    if (vcoTipDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText())) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    } else {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        } else {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText())) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    } else {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        } else {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCli(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.setVisible(true);
                    if (vcoCli.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText())) {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    } else {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        } else {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText())) {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    } else {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        } else {
                            txtDesLarCli.setText(strDesLarCli);
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConVen(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoVen.setCampoBusqueda(2);
                    vcoVen.setVisible(true);
                    if (vcoVen.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoVen.buscar("a1.co_usr", txtCodVen.getText())) {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    } else {
                        vcoVen.setCampoBusqueda(0);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtNomVen.setText(vcoVen.getValueAt(3));
                        } else {
                            txtCodVen.setText(strCodVen);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoVen.buscar("a1.tx_nom", txtNomVen.getText())) {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    } else {
                        vcoVen.setCampoBusqueda(2);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtNomVen.setText(vcoVen.getValueAt(3));
                        } else {
                            txtNomVen.setText(strNomVen);
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
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que está ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podría presentar mensajes informativos en un JLabel e ir incrementando un
     * JProgressBar con lo cual el usuario estaría informado en todo momento de
     * lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase ya
     * que si no sólo se apreciaría los cambios cuando ha terminado todo el
     * proceso.
     */
    private class ZafThreadGUI extends Thread {

        public void run() {
            if (validarFiltros()) {
                if (!cargarDetReg(sqlConFil())) {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }
                //Establecer el foco en el JTable sólo cuando haya datos.
                if (tblDat.getRowCount() > 0) {
                    tabFrm.setSelectedIndex(1);
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.requestFocus();
                }
            }
            objThrGUI = null;
        }
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg = "Código del local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg = "Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DEC_TIP_DOC:
                    strMsg = "Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DEL_TIP_DOC:
                    strMsg = "Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg = "Código del documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg = "Número del documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg = "Fecha del documento";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg = "Código del cliente/proveedor";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg = "Nombre del cliente/proveedor";
                    break;
                case INT_TBL_DAT_TOT_DOC:
                    strMsg = "Total del documento";
                    break;
                case INT_TBL_DAT_EST_DOC:
                    strMsg = "Estado del documento";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera
     * del JTable. Se utiliza ésta función especificamente para marcar todas las
     * casillas de verificación de la columna que indica la bodega seleccionada
     * en el el JTable de bodegas.
     */
    private void tblSelTodMouseClicked(java.awt.event.MouseEvent evt) {
        int i, intNumFil;
        try {
            intNumFil = objTblMod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblDat.columnAtPoint(evt.getPoint()) == INT_TBL_DAT_CHK_SEL) {
                if (blnMarTodChkTblSel) {
                    //Mostrar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        objTblMod.setChecked(true, i, INT_TBL_DAT_CHK_SEL);
                    }
                    blnMarTodChkTblSel = false;
                } else {
                    //Ocultar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        objTblMod.setChecked(false, i, INT_TBL_DAT_CHK_SEL);
                    }
                    blnMarTodChkTblSel = true;
                }
//                //Mostrar las columnas "Cód.Bod." y "Bodega" sólo si hay seleccionada más de una bodega.
//                if (objTblModBod.getRowCountChecked(INT_TBL_BOD_CHK)>1)
//                {
//                    //Mostrar columnas.
//                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                }
//                else
//                {
//                    //Ocultar columnas.
//                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                }
            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConForImp() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_rpt");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_nomrpt");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción");
            arlAli.add("Reporte");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("334");
            arlAncCol.add("120");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if (objParSis.getCodigoUsuario() == 1) {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += " SELECT distinct a1.co_rpt, a1.tx_descor, a1.tx_nomrpt, a1.tx_rutabsrpt,a1.tx_rutrelrpt";
                strSQL += " from tbm_rptsis as a1";
                strSQL += " inner join tbr_rptSisUsr as a2 on (a1.co_rpt=a2.co_rpt)";
                strSQL += " inner join tbm_cabtipdoc as a3 on (a2.co_emp=a3.co_emp and a2.co_loc=a3.co_loc and a2.co_mnu=a3.co_mnu)";
                strSQL += " where a3.co_tipdoc=" + txtCodTipDoc.getText();
                strSQL += " and a3.co_emp=" + objParSis.getCodigoEmpresa();
                //strSQL+=" and a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL += " and a3.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " and a2.st_reg='S' ";
                strSQL += " ORDER BY a1.tx_desCor";
            } else {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += " SELECT distinct a1.co_rpt, a1.tx_descor, a1.tx_nomrpt, a1.tx_rutabsrpt,a1.tx_rutrelrpt";
                strSQL += " from tbm_rptsis as a1";
                strSQL += " inner join tbr_rptSisUsr as a2 on (a1.co_rpt=a2.co_rpt)";
                strSQL += " inner join tbm_cabtipdoc as a3 on (a2.co_emp=a3.co_emp and a2.co_loc=a3.co_loc and a2.co_mnu=a3.co_mnu)";
                strSQL += " where a3.co_tipdoc=" + txtCodTipDoc.getText();
                strSQL += " and a3.co_emp=" + objParSis.getCodigoEmpresa();
                //strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL += " and a3.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " and a2.st_reg='S' ";
                strSQL += " ORDER BY a1.tx_desCor";
            }
            strSQLGlo = strSQL;
            cargarRutas();
            vcoForImp = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de los formatos de impresión", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoForImp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConForImp(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoForImp.setCampoBusqueda(1);
                    vcoForImp.setVisible(true);
                    if (vcoForImp.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        txtCodForImp.setText(vcoForImp.getValueAt(1));
                        txtNomForImp.setText(vcoForImp.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoForImp.buscar("a1.co_rpt", txtCodForImp.getText())) {
                        txtCodForImp.setText(vcoForImp.getValueAt(1));
                        txtNomForImp.setText(vcoForImp.getValueAt(3));
                    } else {
                        vcoForImp.setCampoBusqueda(1);
                        vcoForImp.setCriterio1(11);
                        vcoForImp.cargarDatos();
                        vcoForImp.setVisible(true);
                        if (vcoForImp.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodForImp.setText(vcoForImp.getValueAt(1));
                            txtNomForImp.setText(vcoForImp.getValueAt(3));
                        } else {
                            txtCodForImp.setText(strCodForImp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoForImp.buscar("a1.tx_desCor", txtNomForImp.getText())) {
                        txtCodForImp.setText(vcoForImp.getValueAt(1));
                        txtNomForImp.setText(vcoForImp.getValueAt(3));
                    } else {
                        vcoForImp.setCampoBusqueda(2);
                        vcoForImp.setCriterio1(11);
                        vcoForImp.cargarDatos();
                        vcoForImp.setVisible(true);
                        if (vcoForImp.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodForImp.setText(vcoForImp.getValueAt(1));
                            txtNomForImp.setText(vcoForImp.getValueAt(3));
                        } else {
                            txtNomForImp.setText(strNomForImp);
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

    private Boolean validarFiltros() {
        if (txtCodTipDoc.getText().length() == 0) {
            txtCodTipDoc.requestFocus();
            txtCodTipDoc.selectAll();
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de Documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            return false;
        } else if (txtCodForImp.getText().length() == 0) {
            txtCodForImp.requestFocus();
            txtCodForImp.selectAll();
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Formato Impresión</FONT> es obligatorio.<BR>Escriba o seleccione un formato y vuelva a intentarlo.</HTML>");
            return false;
        }
        return true;
    }
    

    /**
     * Esta función permite generar el reporte de acuerdo al criterio
     * seleccionado.
     *
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
    private boolean generarRpt(int intTipRpt) {
        String strRutRpt, strNomRpt, strLogo = "";
        int intNumFil;
        boolean blnRes = true;
        boolean blnEnt = false;
        try {
            listPdfs = new ArrayList<InputStream>();
            intNumFil = objTblMod.getRowCountTrue();
            for (int i = 0; i < intNumFil; i++) {
                if (objTblMod.getValueAt(i, INT_TBL_DAT_CHK_SEL).equals(true)) {
                    blnEnt = true;
                    strRutRpt = this.strRutRpt;
                    strNomRpt = this.strNomRpt;
                    strLogo = this.strLogo;
                    Map mapPar = new HashMap();
                    if (txtCodTipDoc.getText().toString().equals("1")) {
                        mapPar.put("codEmp", Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP).toString()));
                        mapPar.put("codLoc", Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC).toString()));
                        mapPar.put("codTipDoc", Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString()));
                        mapPar.put("codDoc", Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC).toString()));
                    } else if (txtCodTipDoc.getText().toString().equals("228")) {
                        if (objParSis.getCodigoEmpresa() == 1) {
                            strLogo += "Logos/logTuv.png";
                        } else if (objParSis.getCodigoEmpresa() == 2) {
                            strLogo += "Logos/logCas.png";
                        } else {
                            strLogo += "Logos/logDim.png";
                        }
                        mapPar.put("codEmp", Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP).toString()));
                        mapPar.put("codLoc", Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC).toString()));
                        mapPar.put("codTipDoc", Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString()));
                        mapPar.put("codDoc", Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC).toString()));
                        mapPar.put("ambiente", 2); //1 = Pruebas; 2 = Produccion
                        mapPar.put("DesForPagSri", strDesForPagSri);
                        mapPar.put("RUTA_LOGO", strLogo);
                    } else if (txtCodTipDoc.getText().toString().equals("229")) {
                        mapPar.put("codEmp", Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP).toString()));
                        mapPar.put("codLoc", Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC).toString()));
                        mapPar.put("codTipDoc", Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString()));
                        mapPar.put("codDoc", Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC).toString()));
                        mapPar.put("LOGO_DIR", strLogo);
                        mapPar.put("SUBREPORT_DIR", strRutRpt);
                    }
                    generarPdfs(strRutRpt, strNomRpt, mapPar);
                }
            }
            if (blnEnt) {
                final String strRutaTemp = "/tmp/PdfsGrupo/";
                Random rnd = new Random();
                int intCodArcPdfTod = rnd.nextInt();
                OutputStream out = new FileOutputStream(new File(strRutaTemp + "ArcPdf" + intCodArcPdfTod + ".pdf"));
                doMerge(listPdfs, out, strRutaTemp + "ArcPdf" + intCodArcPdfTod + ".pdf", intTipRpt);
            } else {
                mostrarMsgInf("No ha seleccionado ningún registro.");
            }
            if (con != null) {
                con.close();
                con = null;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public void mostrarMsg(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void cargarRutas() {
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        Connection conn;
        try {
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) {
                stmLoc = conn.createStatement();
                rstLoc = stmLoc.executeQuery(strSQLGlo);
                while (rstLoc.next()) {
                    if (txtCodForImp.getText().equals(rstLoc.getString("co_rpt"))) {
                        if (objParSis.getCodigoUsuario()==41) { //pperez
                            strRutRpt = "/Zafiro/" + rstLoc.getString("tx_rutrelrpt");
                            strLogo = "/Zafiro/" + rstLoc.getString("tx_rutrelrpt");
                        }else{
                            strRutRpt = rstLoc.getString("tx_rutabsrpt") + rstLoc.getString("tx_rutrelrpt");
                            strLogo = rstLoc.getString("tx_rutabsrpt") + rstLoc.getString("tx_rutrelrpt");
                        }
                        strNomRpt = rstLoc.getString("tx_nomrpt");
                        break;
                    } else {
                        if (objParSis.getCodigoUsuario()==41) { //pperez
                            strRutRpt = "/Zafiro/" + rstLoc.getString("tx_rutrelrpt");
                            strLogo = "/Zafiro/" + rstLoc.getString("tx_rutrelrpt");
                        }else{
                            strRutRpt = rstLoc.getString("tx_rutabsrpt") + rstLoc.getString("tx_rutrelrpt");
                            strLogo = rstLoc.getString("tx_rutabsrpt") + rstLoc.getString("tx_rutrelrpt");
                        }
                        strNomRpt = rstLoc.getString("tx_nomrpt");
                    }
                }
                stmLoc.close();
                stmLoc = null;
                rstLoc.close();
                rstLoc = null;
                conn.close();
                conn = null;
            }

        } catch (java.sql.SQLException Evt) {
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    /* Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param rutaReporte La ruta del reporte a generar.
     * @param nombreReporte El nombre del reporte a generar.
     * @param parametros Los parámetros que se van a pasar al reporte a generar.
     * @param tipoReporte El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */

    public boolean generarReporteA5(String rutaReporte, String nombreReporte, java.util.Map parametros, int tipoReporte) {
        boolean blnRes = true;
        try {
            //Generar el reporte.
            //Obtener la conexión sólo si es necesario.
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            }
            if (con != null) {
                String DIRECCION_REPORTE_GUIA = "";
                String strNumImp = "";
                final String strRutaTemp = "/tmp/";
                final String DIRECCION_REPORTE_GUIA_VP;
                final String strNumImpVp;
                final java.util.Map parametrosVp;
                final String nombreReporteVp;
                final String strDireccionNuevaVp;
                DIRECCION_REPORTE_GUIA = rutaReporte + nombreReporte;
                //System.out.println(DIRECCION_REPORTE_GUIA);
                String strDireccionNueva = strRutaTemp + nombreReporte;
                strDireccionNuevaVp = strDireccionNueva;
                DIRECCION_REPORTE_GUIA_VP = DIRECCION_REPORTE_GUIA;
                strNumImp = parametros.get("codTipDoc").toString() + parametros.get("codDoc").toString();
                strNumImpVp = strNumImp;
                parametrosVp = parametros;
                nombreReporteVp = nombreReporte;
                File filCarp = new File(strRutaTemp);
                if (!filCarp.exists()) {
                    filCarp.mkdir();
                }
                switch (tipoReporte) {
                    case 0: //Impresión directa.
                        //net.sf.jasperreports.engine.JasperPrintManager.printReport(objJasPri, false);
                        //net.sf.jasperreports.engine.JasperPrintManager.printReport(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + "2.pdf"), false);
                        break;
                    case 1: //Impresión directa (Cuadro de dialogo de impresión).
                        String fileName = DIRECCION_REPORTE_GUIA;
                        String fileNumber = strNumImp;
                        String printerName = "";//Ej:sistemas_inmaconsa";
                        String jobName = "PDF Print Job";
                        String DireccionExpo = strDireccionNueva;
                        printPDF(fileName, fileNumber, printerName, jobName, parametros, con, DireccionExpo);
                        break;
                    case 2: //Vista preliminar.
                        File pdfA4 = null;
                        File pdfA5 = null;

                        JasperPrint reportGuiaRem = JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parametros, con);
                        int intCantPaginas = 0;
                        if (reportGuiaRem.getPages() != null && reportGuiaRem.getPages().size() > 0) {
                            intCantPaginas = reportGuiaRem.getPages().size();
                        }
                        JasperExportManager.exportReportToPdfFile(reportGuiaRem, strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
                        pdfA4 = new File(strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
                        Document docu = new Document(PageSize.A4, 0, 0, 0, 0);
                        Rectangle recA5 = new Rectangle(PageSize.A5);
                        float floRecA5 = recA5.getHeight() - 35;
                        Rectangle recA52 = new Rectangle(recA5.getWidth(), floRecA5);
                        docu.setPageSize(recA52);
                        PdfReader reader = new PdfReader(strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
                        FileOutputStream fos = new FileOutputStream(strDireccionNueva.replace(".jasper", strNumImp + "vp.pdf"));
                        PdfWriter writer = PdfWriter.getInstance(docu, fos);
                        pdfA5 = new File(strDireccionNueva.replace(".jasper", strNumImp + "vp.pdf"));
                        docu.open();
                        PdfContentByte cb = writer.getDirectContent();
                        PdfImportedPage page = null;
                        //Se reduce la medida escalandolo de a4 a a5
                        float Scale = 0.67f;
                        for (int i = 0; i < intCantPaginas; i++) {
                            if (i > 0) {
                                docu.newPage();
                            }
                            page = writer.getImportedPage(reader, i + 1);
                            cb.addTemplate(page, Scale, 0, 0, Scale, 0, 0);
                        }
                        docu.close();
                        pdfA4.delete();
                        SwingController controller = new SwingController();
                        controller.setIsEmbeddedComponent(true);
                        PropertiesManager properties = new PropertiesManager(
                                System.getProperties(),
                                ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
                        // read/store the font cache.
                        //controller.
                        ResourceBundle messageBundle = ResourceBundle.getBundle(
                                PropertiesManager.DEFAULT_MESSAGE_BUNDLE);
                        new FontPropertiesManager(properties, System.getProperties(), messageBundle);
                        properties.set(PropertiesManager.PROPERTY_PRINT_MEDIA_SIZE_WIDTH, "148");
                        properties.set(PropertiesManager.PROPERTY_PRINT_MEDIA_SIZE_HEIGHT, "210");
                        properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.25");
                        SwingViewBuilder factory = new SwingViewBuilder(controller, properties);
                
                        // add interactive mouse link annotation support via callback
                        controller.getDocumentViewController().setAnnotationCallback(
                                new MyAnnotationCallback(controller.getDocumentViewController()));
                        JPanel viewerComponentPanel = factory.buildViewerPanel();
                        JFrame applicationFrame = new JFrame();
                        //applicationFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        applicationFrame.getContentPane().add(viewerComponentPanel);
                        // Now that the GUI is all in place, we can try openning a PDF
                        controller.openDocument(strDireccionNueva.replace(".jasper", strNumImp + "vp.pdf"));
                        applicationFrame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                File file = new File(strDireccionNuevaVp.replace(".jasper", strNumImpVp + "vp.pdf"));
                                if (file.exists()) {
                                    file.delete();
                                    file = null;
                                }
                                File file2 = new File(strDireccionNuevaVp.replace(".jasper", strNumImpVp + ".pdf"));
                                if (file2.exists()) {
                                    file2.delete();
                                    file2 = null;
                                }
                            }
                        });

                        // add the window event callback to dispose the controller and
                        // currently open document.
                        applicationFrame.addWindowListener(controller);

                        // show the component
                        applicationFrame.pack();
                        applicationFrame.setVisible(true);
                        break;
                }
                con.close();
                con = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (net.sf.jasperreports.engine.JRException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Metodo para imprimir directamente
     *
     * @param filePath
     * @param fileNumber
     * @param printerName
     * @param jobName
     * @param parametros
     * @param con
     */
    public void printPDF(String filePath, String fileNumber, String printerName, String jobName, java.util.Map parametros, Connection con, String strDireccionExportar) {
        FileInputStream fileInputStream = null;
        File pdfA4 = null;
        File pdfA5 = null;
        try {
            JasperPrint reportGuiaRem = JasperFillManager.fillReport(filePath, parametros, con);
            int intCantPaginas = 0;
            if (reportGuiaRem.getPages() != null && reportGuiaRem.getPages().size() > 0) {
                intCantPaginas = reportGuiaRem.getPages().size();
            }
            JasperExportManager.exportReportToPdfFile(reportGuiaRem, strDireccionExportar.replace(".jasper", fileNumber + ".pdf"));
            pdfA4 = new File(strDireccionExportar.replace(".jasper", fileNumber + ".pdf"));
            Document docu = new Document(PageSize.A4, 0, 0, 0, 0);
            Rectangle recA5 = new Rectangle(PageSize.A5);
            float floRecA5 = recA5.getHeight() - 35;
            Rectangle recA52 = new Rectangle(recA5.getWidth(), floRecA5);
            docu.setPageSize(recA52);
            PdfReader reader = new PdfReader(strDireccionExportar.replace(".jasper", fileNumber + ".pdf"));
            FileOutputStream fos = new FileOutputStream(strDireccionExportar.replace(".jasper", fileNumber + "2.pdf"));
            PdfWriter writer = PdfWriter.getInstance(docu, fos);
            pdfA5 = new File(strDireccionExportar.replace(".jasper", fileNumber + "2.pdf"));
            docu.open();
            PdfContentByte cb = writer.getDirectContent();
            PdfImportedPage page = null;
            //Se reduce la medida escalandolo de a4 a a5
            float Scale = 0.67f;
            for (int i = 0; i < intCantPaginas; i++) {
                if (i > 0) {
                    docu.newPage();
                }
                page = writer.getImportedPage(reader, i + 1);
                cb.addTemplate(page, Scale, 0, 0, Scale, 0, 0);
            }
            docu.close();
            fileInputStream = new FileInputStream(strDireccionExportar.replace(".jasper", fileNumber + "2.pdf"));
            byte[] pdfContent = new byte[fileInputStream.available()];
            fileInputStream.read(pdfContent, 0, fileInputStream.available());
            ByteBuffer buffer = ByteBuffer.wrap(pdfContent);
            final PDFFile pdfFile = new PDFFile(buffer);
            PDFPrintPage pages = new PDFPrintPage(pdfFile);
            PrinterJob printJob = PrinterJob.getPrinterJob();
            if (printJob.printDialog()) {//Devuelve true si el usuario decide imprimir
                System.out.println(printJob.getPrintService().getName());
                printJob.getPrintService().getName();
                PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
                printJob.setJobName(jobName);
                Book book = new Book();
                book.append(pages, pageFormat, pdfFile.getNumPages());
                printJob.setPageable(book);
                Paper paper = new Paper();
                if (System.getProperty("os.name").equals("Linux")) {
                    paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight() - 200);
                } else {
                    paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight() - 90);
                }
                pageFormat.setPaper(paper);
                PrintService[] printServices = PrinterJob.lookupPrintServices();
                for (int count = 0; count < printServices.length; ++count) {
                    if (printerName.equalsIgnoreCase(printJob.getPrintService().getName())) {
                        printJob.setPrintService(printServices[count]);
                        break;
                    }
                }
                printJob.print();
            }
            fileInputStream.close();
            pdfA4.delete();
            pdfA5.delete();
        } catch (FileNotFoundException ex) {
            if (pdfA4 != null) {
                pdfA4.delete();
            }
            if (pdfA5 != null) {
                pdfA5.delete();
            }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } catch (JRException ex) {
            if (pdfA4 != null) {
                pdfA4.delete();
            }
            if (pdfA5 != null) {
                pdfA5.delete();
            }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } catch (IOException ex) {
            if (pdfA4 != null) {
                pdfA4.delete();
            }
            if (pdfA5 != null) {
                pdfA5.delete();
            }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } catch (DocumentException ex) {
            if (pdfA4 != null) {
                pdfA4.delete();
            }
            if (pdfA5 != null) {
                pdfA5.delete();
            }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } catch (PrinterException ex) {
            if (pdfA4 != null) {
                pdfA4.delete();
            }
            if (pdfA5 != null) {
                pdfA5.delete();
            }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } finally {
            try {
                fileInputStream.close();
                if (pdfA4 != null) {
                    pdfA4.delete();
                }
                if (pdfA5 != null) {
                    pdfA5.delete();
                }
            } catch (IOException ex) {
                if (pdfA4 != null) {
                    pdfA4.delete();
                }
                if (pdfA5 != null) {
                    pdfA5.delete();
                }
                ex.printStackTrace();
                objUti.mostrarMsgErr_F1(this, ex);
            }
        }
    }

    public boolean generarPdfs(String rutaReporte, String nombreReporte, Map parametros) {
        boolean blnRes = true;
        try {
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            }
            if (con != null) {
                String DIRECCION_REPORTE_GUIA = "";
                String strNumImp = "";
                final String strRutaTemp = "/tmp/PdfsGrupo/";
                DIRECCION_REPORTE_GUIA = rutaReporte + nombreReporte;
                String strDireccionNueva = strRutaTemp + nombreReporte;
                strNumImp = parametros.get("codTipDoc").toString() + parametros.get("codDoc").toString();
                File filCarp = new File(strRutaTemp);
                if (!filCarp.exists()) {
                    filCarp.mkdir();
                }
                File pdfA4 = null;
                File pdfA5 = null;

                JasperPrint reportGuiaRem = JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parametros, con);
                int intCantPaginas = 0;
                if (reportGuiaRem.getPages() != null && reportGuiaRem.getPages().size() > 0) {
                    intCantPaginas = reportGuiaRem.getPages().size();
                }
                JasperExportManager.exportReportToPdfFile(reportGuiaRem, strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
                pdfA4 = new File(strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
                Document docu = new Document(PageSize.A4, 0, 0, 0, 0);
                Rectangle recA5 = new Rectangle(PageSize.A5);
                float floRecA5 = recA5.getHeight() - 35;
                Rectangle recA52 = new Rectangle(recA5.getWidth(), floRecA5);
                docu.setPageSize(recA52);
                PdfReader reader = new PdfReader(strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
                FileOutputStream fos = new FileOutputStream(strDireccionNueva.replace(".jasper", strNumImp + "vp.pdf"));
                PdfWriter writer = PdfWriter.getInstance(docu, fos);
                pdfA5 = new File(strDireccionNueva.replace(".jasper", strNumImp + "vp.pdf"));
                listPdfs.add(new FileInputStream(pdfA5));
                listPdfsDir.add(strDireccionNueva.replace(".jasper", strNumImp + "vp.pdf"));
                docu.open();
                PdfContentByte cb = writer.getDirectContent();
                PdfImportedPage page = null;
                //Se reduce la medida escalandolo de a4 a a5
                float Scale = 0.67f;
                for (int i = 0; i < intCantPaginas; i++) {
                    if (i > 0) {
                        docu.newPage();
                    }
                    page = writer.getImportedPage(reader, i + 1);
                    cb.addTemplate(page, Scale, 0, 0, Scale, 0, 0);
                }
                docu.close();
                pdfA4.delete();
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (net.sf.jasperreports.engine.JRException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void doMerge(List<InputStream> list, OutputStream outputStream, final String strDirArcPdf, int tipoReporte)
            throws DocumentException, IOException {

        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        Rectangle recA5 = new Rectangle(PageSize.A5);
        float floRecA5 = recA5.getHeight() - 35;
        Rectangle recA52 = new Rectangle(recA5.getWidth(), floRecA5);
        document.setPageSize(recA52);
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        float Scale = 0.67f;
        for (InputStream in : list) {
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                PdfImportedPage page = writer.getImportedPage(reader, i);
                cb.addTemplate(page, 0, 0);
            }
        }
        for (InputStream in : list) {
            in.close();
        }
        outputStream.flush();
        document.close();
        outputStream.close();
        for (String strDirFil : listPdfsDir) {
            File objFilEli = new File(strDirFil);
            objFilEli.delete();
        }
        listPdfsDir = new ArrayList<String>();

        switch (tipoReporte) {
            case 0: //Impresión directa.
                //net.sf.jasperreports.engine.JasperPrintManager.printReport(objJasPri, false);
                //net.sf.jasperreports.engine.JasperPrintManager.printReport(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + "2.pdf"), false);
                break;
            case 1: //Impresión directa (Cuadro de dialogo de impresión).
                String printerName = "";//Ej:sistemas_inmaconsa";
                String jobName = "PDF Print Job";
                FileInputStream fileInputStream = null;
                fileInputStream = new FileInputStream(strDirArcPdf);
                byte[] pdfContent = new byte[fileInputStream.available()];
                fileInputStream.read(pdfContent, 0, fileInputStream.available());
                ByteBuffer buffer = ByteBuffer.wrap(pdfContent);
                final PDFFile pdfFile = new PDFFile(buffer);
                PDFPrintPage pages = new PDFPrintPage(pdfFile);
                PrinterJob printJob = PrinterJob.getPrinterJob();
                if (printJob.printDialog()) {//Devuelve true si el usuario decide imprimir
                    System.out.println(printJob.getPrintService().getName());
                    printJob.getPrintService().getName();
                    PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
                    printJob.setJobName(jobName);
                    Book book = new Book();
                    book.append(pages, pageFormat, pdfFile.getNumPages());
                    printJob.setPageable(book);
                    Paper paper = new Paper();
                    if (System.getProperty("os.name").equals("Linux")) {
                        paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight() - 200);
                    } else {
                        paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight() - 90);
                    }
                    pageFormat.setPaper(paper);
                    PrintService[] printServices = PrinterJob.lookupPrintServices();
                    for (int count = 0; count < printServices.length; ++count) {
                        if (printerName.equalsIgnoreCase(printJob.getPrintService().getName())) {
                            try {
                                printJob.setPrintService(printServices[count]);
                                break;
                            } catch (PrinterException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    try {
                        printJob.print();
                    } catch (PrinterException ex) {
                        ex.printStackTrace();
                    }
                }
                fileInputStream.close();
                break;
            case 2: //Vista preliminar.
                SwingController controller = new SwingController();
                controller.setIsEmbeddedComponent(true);
                PropertiesManager properties = new PropertiesManager(
                        System.getProperties(),
                        ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
                ResourceBundle messageBundle = ResourceBundle.getBundle(
                        PropertiesManager.DEFAULT_MESSAGE_BUNDLE);
                new FontPropertiesManager(properties, System.getProperties(), messageBundle);
                properties.set(PropertiesManager.PROPERTY_PRINT_MEDIA_SIZE_WIDTH, "148");
                properties.set(PropertiesManager.PROPERTY_PRINT_MEDIA_SIZE_HEIGHT, "210");
                properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.25");
                SwingViewBuilder factory = new SwingViewBuilder(controller, properties);
                controller.getDocumentViewController().setAnnotationCallback(
                        new MyAnnotationCallback(controller.getDocumentViewController()));
                JPanel viewerComponentPanel = factory.buildViewerPanel();
                JFrame applicationFrame = new JFrame();
                applicationFrame.getContentPane().add(viewerComponentPanel);
                controller.openDocument(strDirArcPdf);
                applicationFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                applicationFrame.getContentPane().add(viewerComponentPanel);
                applicationFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        File file = new File(strDirArcPdf);
                        if (file.exists()) {
                            file.delete();
                            file = null;
                        }
                    }
                });

                applicationFrame.addWindowListener(controller);
                applicationFrame.pack();
                applicationFrame.setVisible(true);
                break;
        }
    }
}

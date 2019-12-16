
package RecursosHumanos.ZafRecHum84;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Descuento de vacaciones
 *
 * @author Tony Sanginez
 */
public class ZafRecHum84 extends javax.swing.JInternalFrame {

    private Librerias.ZafParSis.ZafParSis objParSis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
    private ZafUtil objUti;
    private ZafThreadGUI objThrGUI;
    private ZafSelFec objSelFec;
    private static final int INT_TBL_LINEA = 0;                      //Índice de columna Linea.
    private static final int INT_TBL_CODEMP = 1;                            //Codigo Empresa
    private static final int INT_TBL_NOMEMP = 2;                            // Nombre Empresa
    private static final int INT_TBL_FECHA = 3;                            //Fecha
    private static final int INT_TBL_CODTRA = 4;                            //Codigo Trabajador
    private static final int INT_TBL_NOMTRA = 5;                            // Nombre Trabajador
    private static final int INT_TBL_CHKJUSFAL = 6;                         //Justificacion de falta
    private static final int INT_TBL_OBS = 7;                            //Observación de la justificación.
    private static final int INT_TBL_ESTJUSFAL = 8;                         //Estado en el que se carga el dia al que se le va a justificar.
    private static final int INT_TBL_ESTAPLVAC = 9;                     //Estado aplica a vacaciones. (No se justifica Sabados y domingos pero si se los contabiliza)
    private static final int INT_TBL_NEDIA     =10;                     //Dia de la fecha
    private ArrayList arlColHid = new ArrayList();
    private String strVersion = " v1.2 ";
    private Vector vecCab = new Vector();                                  //Vector que contiene la cabecera del Table.
    private Vector vecDat = new Vector();
    private String strCodEmp, strNomEmp;
    private String strCodDep = "";
    private String strDesLarDep = "";
    private String strCodTra = "";
    private String strNomTra = "";
    private ZafVenCon vcoEmp;                                   //Ventana de consulta.
    private ZafVenCon vcoDep;                                   //Ventana de consulta.
    private ZafVenCon vcoTra;
    private ZafPerUsr       objPerUsr;
    /**
     * Creates new form ZafRecHum80
     */
    public ZafRecHum84(Librerias.ZafParSis.ZafParSis obj) {

        try {
             objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();
            objPerUsr=new ZafPerUsr(objParSis);
            this.setTitle(objParSis.getNombreMenu() + "  " + strVersion + " ");
            lblTit.setText(objParSis.getNombreMenu());

            objUti = new ZafUtil();

            configuraTbl();
            
            //Configurar las ZafVenCon.
            configurarVenConDep();
            configurarVenConTra();
            configurarVenConEmp();
            //Configurar ZafSelFec:
            objSelFec = new ZafSelFec();
            objSelFec.setTitulo("Rango de fechas");
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setCheckBoxChecked(false);
            objSelFec.setFlechaDerechaHabilitada(false);
            objSelFec.setFlechaDerechaSeleccionada(false);
            objSelFec.setFlechaIzquierdaHabilitada(false);
            objSelFec.setFlechaIzquierdaSeleccionada(false);
            panCab.add(objSelFec);
            //objSelFec.setBounds(4, 20, 472, 72);
            objSelFec.setBounds(4, 20, 582, 72);

            //*****************************************************************************
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setHoy();
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
            int fecDoc[] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if (fecDoc != null) {
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(java.util.Calendar.DATE, -14);

            dtePckPag.setAnio(objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH) + 1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(), "dd/MM/yyyy", "yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha, "yyyy/MM/dd");

            objSelFec.setFechaDesde(objUti.formatearFecha(fe1, "dd/MM/yyyy"));
            setDiasVac();
            txtDia.setEditable(false);
            //*******************************************************************************
            butCon.setVisible(false);
            butGua.setVisible(false);
            butCerr.setVisible(false);
            
            if(objPerUsr.isOpcionEnabled(3913)){
                butCon.setVisible(true);
            }
            if(objPerUsr.isOpcionEnabled(4141)){
                butGua.setVisible(true);
            }        
            if(objPerUsr.isOpcionEnabled(3914)){
                butCerr.setVisible(true);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }  
    }

    private boolean configuraTbl() {
 boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(9);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CODEMP, "Cód.Emp.");
            vecCab.add(INT_TBL_NOMEMP, "Nom.Emp.");
            vecCab.add(INT_TBL_FECHA, "Fecha");
            vecCab.add(INT_TBL_CODTRA, "Cód.Tra.");
            vecCab.add(INT_TBL_NOMTRA, "Nom.Tra.");
            vecCab.add(INT_TBL_CHKJUSFAL, "Justificar");
            vecCab.add(INT_TBL_OBS, "Observación");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            new ZafColNumerada(tblDat, INT_TBL_LINEA);
            //Configurar JTable: Establecer el menú de contexto.
            new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();

            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOMEMP).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_FECHA).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_CODTRA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NOMTRA).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_CHKJUSFAL).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_OBS).setPreferredWidth(200);
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_CHKJUSFAL);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            //Configurar JTable: Editor de la tabla.
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

            Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), true, true);
            objTblCelRenLbl = null;

            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKJUSFAL).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            tcmAux.getColumn(INT_TBL_CHKJUSFAL).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilSel = tblDat.getSelectedRow();
                    if (objTblMod.getValueAt(intFilSel, INT_TBL_ESTJUSFAL).toString().equals("S")) {
                        objTblCelEdiChk.setCancelarEdicion(true);
                    }
                    //verificarCasos(intFilSel);
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1) {
                        if (!verificarCasos(intFilSel)) {
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_CHKJUSFAL);
                        }
                    }
                }
            });
            objTblCelRenChk = null;

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            ZafMouMotAda objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            new ZafTblBus(tblDat);
            //Configurar JTable: Renderizar celdas.

            //Libero los objetos auxiliares.
            new ZafTblOrd(tblDat);

            arlColHid.add("" + INT_TBL_CODEMP);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);

            ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpAmeSur = new ZafTblHeaColGrp("");
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODEMP));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NOMEMP));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECHA));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODTRA));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NOMTRA));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CHKJUSFAL));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_OBS));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur = null;
            objTblHeaGrp = null;
            arlColHid = null;
            tcmAux = null;
            setEditable(true);

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public void setEditable(boolean editable) {
        if (editable == true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        } else {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        panCab = new javax.swing.JPanel();
        txtDia = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCodDep = new javax.swing.JTextField();
        txtNomDep = new javax.swing.JTextField();
        butDep = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtCodTra.setBackground(objParSis.getColorCamposObligatorios());
        txtNomTra = new javax.swing.JTextField();
        txtNomTra.setBackground(objParSis.getColorCamposObligatorios());
        butTra = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        chkVacPag = new javax.swing.JCheckBox();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCerr = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("titulo"); // NOI18N
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel4.setText("Empresa:"); // NOI18N
        panFil.add(jLabel4);
        jLabel4.setBounds(40, 140, 100, 20);

        txtCodEmp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(140, 140, 60, 20);

        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(200, 140, 250, 20);

        butEmp.setText(".."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(450, 140, 20, 20);

        panCab.setPreferredSize(new java.awt.Dimension(0, 130));
        panCab.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                panCabMouseMoved(evt);
            }
        });
        panCab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panCabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panCabMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panCabMousePressed(evt);
            }
        });
        panCab.setLayout(null);
        panCab.add(txtDia);
        txtDia.setBounds(520, 40, 60, 20);

        jLabel1.setText("Días");
        panCab.add(jLabel1);
        jLabel1.setBounds(480, 40, 40, 20);

        panFil.add(panCab);
        panCab.setBounds(0, 0, 690, 130);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Departamento:"); // NOI18N
        panFil.add(jLabel5);
        jLabel5.setBounds(40, 160, 100, 20);

        txtCodDep.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDepFocusLost(evt);
            }
        });
        txtCodDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDepActionPerformed(evt);
            }
        });
        panFil.add(txtCodDep);
        txtCodDep.setBounds(140, 160, 60, 20);

        txtNomDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDepFocusLost(evt);
            }
        });
        txtNomDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDepActionPerformed(evt);
            }
        });
        panFil.add(txtNomDep);
        txtNomDep.setBounds(200, 160, 250, 20);

        butDep.setText(".."); // NOI18N
        butDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDepActionPerformed(evt);
            }
        });
        panFil.add(butDep);
        butDep.setBounds(450, 160, 20, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Empleado:"); // NOI18N
        panFil.add(jLabel6);
        jLabel6.setBounds(40, 180, 100, 20);

        txtCodTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTraFocusLost(evt);
            }
        });
        txtCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTraActionPerformed(evt);
            }
        });
        panFil.add(txtCodTra);
        txtCodTra.setBounds(140, 180, 60, 20);

        txtNomTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTraFocusLost(evt);
            }
        });
        txtNomTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraActionPerformed(evt);
            }
        });
        panFil.add(txtNomTra);
        txtNomTra.setBounds(200, 180, 250, 20);

        butTra.setText(".."); // NOI18N
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(450, 180, 20, 20);

        tabGen.addTab("Filtro", null, panFil, "Filtro");

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel2.setText("Vacaciones Pagadas");
        jPanel2.add(jLabel2);

        chkVacPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkVacPagActionPerformed(evt);
            }
        });
        jPanel2.add(chkVacPag);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

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

        jPanel1.add(spnDat, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Reporte", jPanel1);

        getContentPane().add(tabGen, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        butCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(90, 23));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        jPanel5.add(butCon);

        butGua.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(90, 23));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        jPanel5.add(butGua);

        butCerr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCerr.setText("Cerrar");
        butCerr.setPreferredSize(new java.awt.Dimension(90, 23));
        butCerr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerrActionPerformed(evt);
            }
        });
        jPanel5.add(butCerr);

        jPanel3.add(jPanel5, java.awt.BorderLayout.EAST);

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

        jPanel3.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    public void consultarRepTra() {
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try {
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null && txtCodTra.getText().compareTo("") != 0) {
                stmLoc = conn.createStatement();
                strSql = "SELECT tx_ape , tx_nom from tbm_tra where co_tra =  " + txtCodTra.getText();
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    txtNomTra.setText(rstLoc.getString("tx_ape") + " " + rstLoc.getString("tx_nom"));
                    txtNomTra.setHorizontalAlignment(2);
                    txtNomTra.setEnabled(false);
                } else {
                    mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                    txtNomTra.setText("");
                    txtCodTra.setText("");
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
            }
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
            txtCodTra.setText("");
            txtNomTra.setText("");
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
            txtCodTra.setText("");
            txtNomTra.setText("");
        }
    }

    public void consultarRepEmp() {
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try {
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null && txtCodEmp.getText().compareTo("") != 0) {
                stmLoc = conn.createStatement();
                strSql = "SELECT co_emp , tx_nom from tbm_emp where co_emp =  " + txtCodEmp.getText() + " and st_reg like 'A' and co_emp not in (0) ";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    txtNomEmp.setText(rstLoc.getString("tx_nom"));
                    txtNomEmp.setHorizontalAlignment(2);
                    txtNomEmp.setEnabled(false);
                } else {
                    mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                    txtNomEmp.setText("");
                    txtCodEmp.setText("");
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
            }
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    public void consultarRepDep() {

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try {
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());

            if (conn != null && txtCodDep.getText().compareTo("") != 0) {
                stmLoc = conn.createStatement();
                strSql = "SELECT co_dep,tx_deslar from tbm_dep where co_dep =  " + txtCodDep.getText();
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    txtNomDep.setText(rstLoc.getString("tx_deslar"));
                    txtNomDep.setHorizontalAlignment(2);
                    txtNomDep.setEnabled(false);
                } else {
                    mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                    txtNomDep.setText("");
                    txtCodDep.setText("");
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
            }
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
            txtCodDep.setText("");
            txtNomDep.setText("");
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
            txtCodDep.setText("");
            txtNomDep.setText("");

        }
    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     *
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    
    public void BuscarEmp(String campo, String strBusqueda, int tipo) {
        vcoEmp.setTitle("Listado de Empresas");
        if (vcoEmp.buscar(campo, strBusqueda)) {
            txtCodEmp.setText(vcoEmp.getValueAt(1));
            txtNomEmp.setText(vcoEmp.getValueAt(2));
        } else {
            vcoEmp.setCampoBusqueda(tipo);
            vcoEmp.cargarDatos();
            vcoEmp.show();
            if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) {
                txtCodEmp.setText(vcoEmp.getValueAt(1));
                txtNomEmp.setText(vcoEmp.getValueAt(2));
            } else {
                txtCodEmp.setText(strCodEmp);
                txtNomEmp.setText(strNomEmp);
            }
        }
    }

    private void butCerrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerrActionPerformed
        exitForm();
}//GEN-LAST:event_butCerrActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
       //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (validarFiltros()) {
            if (butCon.getText().equals("Consultar")) {
                if (objThrGUI==null) {
                    objThrGUI=new ZafThreadGUI();
                    objThrGUI.start();
                }
            }
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
              if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?") == 0) {
            if (validarInicioContrato()) {
                if (guardarDat()) {
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                    cargarDetReg("");
                } else {
                    mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                }
            }else{
                mostrarMsgErr("No se puede realizar la operación ya que el empleado seleccionado aun no cumple el año para la fecha seleccionada.");
            }
        }
    }//GEN-LAST:event_butGuaActionPerformed

    private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed
        configurarVenConTra();
        strCodTra = txtCodTra.getText();
        mostrarVenConTra(0);
    }//GEN-LAST:event_butTraActionPerformed

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
        if (txtNomTra.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomTra.getText().equalsIgnoreCase(strNomTra)) {
                if (txtNomTra.getText().equals("")) {
                    txtCodTra.setText("");
                    txtNomTra.setText("");
                } else {
                    mostrarVenConTra(2);
                }
            } else {
                txtNomTra.setText(strNomTra);
            }
        }
    }//GEN-LAST:event_txtNomTraFocusLost

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        strNomTra = txtNomTra.getText();
        txtNomTra.selectAll();
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        txtNomTra.transferFocus();
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtCodTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusLost
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

    private void txtCodTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusGained
        strCodTra = txtCodTra.getText();
        txtCodTra.selectAll();
    }//GEN-LAST:event_txtCodTraFocusGained

    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        //consultarRepTra();
        txtCodTra.transferFocus();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void butDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDepActionPerformed
        strCodDep = txtCodDep.getText();
        mostrarVenConDep(0);
    }//GEN-LAST:event_butDepActionPerformed

    private void txtNomDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusLost
        if (txtNomDep.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomDep.getText().equalsIgnoreCase(strDesLarDep)) {
                if (txtNomDep.getText().equals("")) {
                    txtCodDep.setText("");
                    txtNomDep.setText("");
                } else {
                    mostrarVenConDep(2);
                }
            } else {
                txtNomDep.setText(strDesLarDep);
            }
        }
    }//GEN-LAST:event_txtNomDepFocusLost

    private void txtNomDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusGained
        strDesLarDep = txtNomDep.getText();
        txtNomDep.selectAll();
    }//GEN-LAST:event_txtNomDepFocusGained

    private void txtNomDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDepActionPerformed
        txtNomDep.transferFocus();
    }//GEN-LAST:event_txtNomDepActionPerformed

    private void txtCodDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusLost
        if (!txtCodDep.getText().equalsIgnoreCase(strCodDep)) {
            if (txtCodDep.getText().equals("")) {
                txtCodDep.setText("");
                txtNomDep.setText("");
            } else {
                BuscarDep("a1.co_dep", txtCodDep.getText(), 0);
            }
        } else {
            txtCodDep.setText(strCodDep);
        }
    }//GEN-LAST:event_txtCodDepFocusLost

    private void txtCodDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusGained
        strCodDep = txtCodDep.getText();
        txtCodDep.selectAll();
    }//GEN-LAST:event_txtCodDepFocusGained

    private void txtCodDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDepActionPerformed
        //consultarRepDep();
        txtCodDep.transferFocus();
    }//GEN-LAST:event_txtCodDepActionPerformed

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        strCodEmp = txtCodEmp.getText();
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        if (txtNomEmp.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp)) {
                if (txtNomEmp.getText().equals("")) {
                    txtCodEmp.setText("");
                    txtNomEmp.setText("");
                } else {
                    mostrarVenConEmp(2);
                }
            } else {
                txtNomEmp.setText(strNomEmp);
            }
        }
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strNomEmp = txtNomEmp.getText();
        txtNomEmp.selectAll();
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        //txtNomEmp.transferFocus();
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

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

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        //txtCodEmp.selectAll();
        strCodEmp = txtCodEmp.getText();
        txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        //consultarRepEmp();
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void panCabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panCabMouseEntered
        setDiasVac();
    }//GEN-LAST:event_panCabMouseEntered

    private void panCabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panCabMouseExited
        setDiasVac();
    }//GEN-LAST:event_panCabMouseExited

    private void panCabMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panCabMousePressed
        setDiasVac();
    }//GEN-LAST:event_panCabMousePressed

    private void panCabMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panCabMouseMoved
        setDiasVac();
    }//GEN-LAST:event_panCabMouseMoved

    private void chkVacPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkVacPagActionPerformed
        if (chkVacPag.isSelected()) {
            mostrarMsgInf("Al seleccionar está opción no se generará ningun egreso.");    
        }
    }//GEN-LAST:event_chkVacPagActionPerformed

    public void BuscarDep(String campo, String strBusqueda, int tipo) {

        vcoDep.setTitle("Listado de Departamentos");
        if (vcoDep.buscar(campo, strBusqueda)) {
            txtCodDep.setText(vcoDep.getValueAt(1));
            txtNomDep.setText(vcoDep.getValueAt(3));
        } else {
            vcoDep.setCampoBusqueda(tipo);
            vcoDep.cargarDatos();
            vcoDep.show();
            if (vcoDep.getSelectedButton() == vcoDep.INT_BUT_ACE) {
                txtCodDep.setText(vcoDep.getValueAt(1));
                txtNomDep.setText(vcoDep.getValueAt(3));
            } else {
                txtCodDep.setText(strCodDep);
                txtNomDep.setText(strDesLarDep);
            }
        }
    }

    public void BuscarTra(String campo, String strBusqueda, int tipo) {

        vcoTra.setTitle("Listado de Empleados");
        if (vcoTra.buscar(campo, strBusqueda)) {
            txtCodTra.setText(vcoTra.getValueAt(1));
            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        } else {
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                txtCodTra.setText(vcoTra.getValueAt(1));
                txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
            } else {
                txtCodTra.setText(strCodTra);
                txtNomTra.setText(strNomTra);
            }
        }
    }
    private class ZafThreadGUI extends Thread {
        
        public void run() {
            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);
            if (!cargarDetReg(sqlConFil())) {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount() > 0) {
                tabGen.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI = null;

            pgrSis.setValue(0);
            pgrSis.setIndeterminate(false);
        }
    }
    /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de
     * salidad.
     */
    private void exitForm() {

        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCerr;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butDep;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butTra;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkVacPag;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodDep;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtDia;
    private javax.swing.JTextField txtNomDep;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomTra;
    // End of variables declaration//GEN-END:variables

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {

            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_LINEA:
                    strMsg = "";
                    break;
                case INT_TBL_CODEMP:
                    strMsg = "Código de empresa";
                    break;
                case INT_TBL_NOMEMP:
                    strMsg = "Nombre empresa";
                    break;
                case INT_TBL_FECHA:
                    strMsg = "Fecha a justificar";
                    break;
                case INT_TBL_CODTRA:
                    strMsg = "Código del empleado";
                    break;
                case INT_TBL_NOMTRA:
                    strMsg = "Nombres y Apellidos del empleado";
                    break;
                case INT_TBL_CHKJUSFAL:
                    strMsg = "¿Justificar vacaciones?";
                    break;
                case INT_TBL_OBS:
                    strMsg = "Observación";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    protected void finalize() throws Throwable {
        System.gc();
        super.finalize();
    }

    private boolean mostrarVenConEmp(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
//                        txtCodCli.setEditable(true);
//                        txtNomCli.setEditable(true);
//                        butCli.setEnabled(true);
//                        vcoLoc.limpiar();
//                        vcoCli.limpiar();
//                        vcoVen.limpiar();
//                        txtCodLoc.setText("");
//                        txtNomLoc.setText("");
//                        txtCodCli.setText("");
//                        txtRucCli.setText("");
//                        txtNomCli.setText("");
//                        txtCodVen.setText("");
//                        txtAliVen.setText("");
//                        txtNomVen.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())) {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
//                        txtCodCli.setEditable(true);
//                        txtNomCli.setEditable(true);
//                        butCli.setEnabled(true);
//                        vcoLoc.limpiar();
//                        vcoCli.limpiar();
//                        vcoVen.limpiar();
//                        txtCodLoc.setText("");
//                        txtNomLoc.setText("");
//                        txtCodCli.setText("");
//                        txtRucCli.setText("");
//                        txtNomCli.setText("");
//                        txtCodVen.setText("");
//                        txtAliVen.setText("");
//                        txtNomVen.setText("");
                    } else {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
//                            txtCodCli.setEditable(true);
//                            txtNomCli.setEditable(true);
//                            butCli.setEnabled(true);
//                            vcoLoc.limpiar();
//                            vcoCli.limpiar();
//                            vcoVen.limpiar();
//                            txtCodLoc.setText("");
//                            txtNomLoc.setText("");
//                            txtCodCli.setText("");
//                            txtRucCli.setText("");
//                            txtNomCli.setText("");
//                            txtCodVen.setText("");
//                            txtAliVen.setText("");
//                            txtNomVen.setText("");
                        } else {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText())) {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
//                        txtCodCli.setEditable(true);
//                        txtNomCli.setEditable(true);
//                        butCli.setEnabled(true);
//                        vcoLoc.limpiar();
//                        vcoCli.limpiar();
//                        vcoVen.limpiar();
//                        txtCodLoc.setText("");
//                        txtNomLoc.setText("");
//                        txtCodCli.setText("");
//                        txtRucCli.setText("");
//                        txtNomCli.setText("");
//                        txtCodVen.setText("");
//                        txtAliVen.setText("");
//                        txtNomVen.setText("");

                    } else {
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
//                            txtCodCli.setEditable(true);
//                            txtNomCli.setEditable(true);
//                            butCli.setEnabled(true);
//                            vcoLoc.limpiar();
//                            vcoCli.limpiar();
//                            vcoVen.limpiar();
//                            txtCodLoc.setText("");
//                            txtNomLoc.setText("");
//                            txtCodCli.setText("");
//                            txtRucCli.setText("");
//                            txtNomCli.setText("");
//                            txtCodVen.setText("");
//                            txtAliVen.setText("");
//                            txtNomVen.setText("");

                        } else {
                            txtNomEmp.setText(strNomEmp);
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

    private boolean configurarVenConEmp() {
        boolean blnRes = true;
        String strTitVenCon = "";
        String strSQL = "";
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.

            if (objParSis.getCodigoUsuario() == 1) {
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.tx_nom";
                    strSQL += " FROM tbm_emp AS a1";
                    strSQL += " WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL += " AND a1.st_reg NOT IN('I','E')";
                    strSQL += " ORDER BY a1.co_emp";
                } else {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.tx_nom";
                    strSQL += " FROM tbm_emp AS a1";
                    strSQL += " WHERE a1.co_emp in (" + objParSis.getCodigoEmpresa() + ")" + "";
                    strSQL += " AND a1.st_reg NOT IN('I','E')";
                    strSQL += " ORDER BY a1.co_emp";
                }

            } else {
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.tx_nom";
                    strSQL += " FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL += " ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL += " WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL += " AND a1.st_reg NOT IN('I','E')";
                    strSQL += " ORDER BY a1.co_emp";
                } else {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.tx_nom";
                    strSQL += " FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL += " ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL += " WHERE a1.co_emp in (" + objParSis.getCodigoEmpresa() + ")" + "";
                    strSQL += " AND a1.st_reg NOT IN('I','E')";
                    strSQL += " ORDER BY a1.co_emp";
                }
            }
            vcoEmp = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
     * @return true: Si no se presentó ningún problema. <BR>false: En el caso
     * contrario.
     */
    private boolean mostrarVenConDep(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoDep.setCampoBusqueda(2);
                    vcoDep.setVisible(true);
                    if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));

                        /*if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoDep.getValueAt(7))))
                         {
                            
                         if (tooBar.getEstado()=='n')
                         {
                         //strAux=vcoTipDoc.getValueAt(4);
                         //txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                         }
                         //intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                         //strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                         //txtNumDoc1.selectAll();
                         //txtNumDoc1.requestFocus();
                         }*/
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    //vcoDep.setCampoBusqueda(0);
                    vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.co_dep", txtCodDep.getText())) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));

                        /*if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7))))
                         {
                         txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                         txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                         txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                         if (objTooBar.getEstado()=='n')
                         {
                         strAux=vcoTipDoc.getValueAt(4);
                         txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                         }
                         intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                         strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                         txtNumDoc1.selectAll();
                         txtNumDoc1.requestFocus();
                         }
                         else
                         {
                         txtDesCorTipDoc.setText(strDesCorTipDoc);
                         }*/
                    } else {
                        vcoDep.setCampoBusqueda(1);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {

                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                            /*if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7))))
                             {
                             txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                             txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                             txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                             if (objTooBar.getEstado()=='n')
                             {
                             strAux=vcoTipDoc.getValueAt(4);
                             txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                             }
                             intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                             strMerIngEgrF3isBodTipDoc=vcoTipDoc.getValueAt(6);
                             txtNumDoc1.selectAll();
                             txtNumDoc1.requestFocus();
                             }
                             else
                             {
                             txtDesCorTipDoc.setText(strDesCorTipDoc);
                             }*/
                        } else {
                            txtNomDep.setText(strDesLarDep);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoDep.setCampoBusqueda(2);
                    //vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.tx_desLar", txtNomDep.getText())) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                        //if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoDep.getValueAt(4))))
                        //{
                        //txtCodTipDoc.setText(vcoDep.getValueAt(1));
                        //txtDesCorTipDoc.setText(vcoDep.getValueAt(2));
                        //txtDesLarTipDoc.setText(vcoDep.getValueAt(3));
                        //if (tooBar.getEstado()=='n')
                        //{
                        //strAux=vcoTipDoc.getValueAt(4);
                        //txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        //}
                            /*intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                         strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                         txtNumDoc1.selectAll();
                         txtNumDoc1.requestFocus();*/
                        //}
                        //else
                        //{
                        //txtNomDep.setText(strNomDep);
                        //}
                    } else {
                        vcoDep.setCampoBusqueda(2);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            //if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoDep.getValueAt(3))))
                            //{

                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));

                            //txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            //txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            //if (tooBar.getEstado()=='n')
                            //{
                                    /*strAux=vcoTipDoc.getValueAt(4);
                             txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));*/
                            //}
                                /*intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                             strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                             txtNumDoc1.selectAll();
                             txtNumDoc1.requestFocus();*/
                            //}
//                            else
//                            {
//                                txtNomDep.setText(strNomDep);
//                            }
                        } else {
                            txtNomDep.setText(strDesLarDep);
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConDep() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_dep");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción corta");
            arlAli.add("Descripción larga");
            arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            arlAncCol.add("110");
            arlAncCol.add("40");

            String strSQL = "";
            if (objParSis.getCodigoUsuario() == 1) {
                strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where st_reg like 'A' order by co_dep";
            } else {
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                 "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
                strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr=" + objParSis.getCodigoUsuario() + " "
                        + "and co_mnu=" + objParSis.getCodigoMenu() + ")";
            }

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            vcoDep = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado Departamentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoDep.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoDep.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Empleados".
     */
    private boolean configurarVenConTra() {
        boolean blnRes = true;
        String strSQL = "",sqlFilEmp="";
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ape");
            arlCam.add("a1.tx_nom");
            //arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            //arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            //arlAncCol.add("40");

            if(txtCodEmp.getText().compareTo("")!=0){
              sqlFilEmp+= " and b.co_emp  = " + txtCodEmp.getText() + " ";
            }
             String strDep="";
            if(txtCodDep.getText().compareTo("")!=0){
                strDep+= " AND b.co_dep  = " + txtCodDep.getText() + " ";
            }
            
            if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                strSQL = "select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where a.st_reg like 'A' and b.st_reg like 'A' " + sqlFilEmp + strDep 
                        + "order by (a.tx_ape || ' ' || a.tx_nom)";
            } else {
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                 "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
                strSQL = "select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where a.st_reg like 'A' and b.st_reg like 'A' and co_emp = " + objParSis.getCodigoEmpresa() + " " + strDep 
                        + " order by (a.tx_ape || ' ' || a.tx_nom)";
            }

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            //intColOcu[0]=3;
            vcoTra = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    private String sqlConFil(){
   String sqlFil="";
        if(!txtCodEmp.getText().equals(""))
            sqlFil+=" AND a.co_emp="+txtCodEmp.getText();

        if(!txtCodDep.getText().equals(""))
            sqlFil+=" AND a.co_dep="+txtCodDep.getText();

        if(!txtCodTra.getText().equals(""))
            sqlFil+=" AND a.co_tra="+txtCodTra.getText();

       if(objSelFec.isCheckBoxChecked() ){
         switch (objSelFec.getTipoSeleccion())
         {
                    case 0: //Búsqueda por rangos
                        sqlFil+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        sqlFil+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        sqlFil+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
        }
    }
      return sqlFil;
    }
    
    private boolean cargarDetReg(String strFil) {
        boolean blnRes = true;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.Statement stmLocAux;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        String strFilFec="";
        String sqlFilEmp = "";
        Vector vecDataCon;
        try{
      butCon.setText("Detener");
      conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
      if(conn!=null){   
          
          stmLoc=conn.createStatement();
          stmLocAux=conn.createStatement();
          vecDataCon = new Vector();
         
          switch (objSelFec.getTipoSeleccion()){
              case 0: //Búsqueda por rangos
                  strFilFec+=" and e.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                  break;
              case 1: //Fechas menores o iguales que "Hasta".
                  strFilFec+=" and e.fe_dia<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                  break;
              case 2: //Fechas mayores o iguales que "Desde".
                  strFilFec+=" and e.fe_dia>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                  break;
              case 3: //Todo.
                  break;
          }          

          if(txtCodEmp.getText().compareTo("")!=0){
              sqlFilEmp+= " and d.co_emp  = " + txtCodEmp.getText() + " ";
          }
          if(txtCodTra.getText().compareTo("")!=0){
              sqlFilEmp+= " AND d.co_tra  = " + txtCodTra.getText() + " ";
          }
          String strDep="";
          if(txtCodDep.getText().compareTo("")!=0){
              strDep+= " AND b.co_dep  = " + txtCodDep.getText() + " ";
          }
          
          String strEmp="";
          if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
              strEmp=" and b.co_emp = "+ objParSis.getCodigoEmpresa();
          }
          String strDepAut="";
              
//          strSql=" select * from (";
//          strSql+="select d.co_emp,tx_empresa,d.co_tra,d.co_ofi,d.co_dep,d.co_hor,empleado,e.fe_dia, e.ho_ent as horaEntradaEstablecida,e.ho_sal as horaSalidaEstablecida," + "\n";
//          strSql+=" f.ho_ent as ho_entEmp, f.ho_sal as ho_salEmp," + "\n";
//          strSql+=" (case st_jusFal when 'S' then true else false end) as blnJusFal,";
//          strSql+=" st_jusFal , ho_entJus, ho_salJus, co_motJusFal, g.tx_deslar , tx_obsJusFal," + "\n";
//          strSql+=" (case ((f.ho_ent is null and f.ho_sal is null)) when true then true else false end) as blnTieFal" + "\n";
//          strSql+=" from (" + "\n";
//          strSql+=" select fe_inicon, b.co_emp, b.co_tra, b.co_dep,b.co_ofi,b.co_hor,(a.tx_ape || ' ' || a.tx_nom) as empleado, c.tx_nom as tx_empresa from tbm_tra a" + "\n";
//          strSql+=" inner join tbm_traemp b on (a.co_tra=b.co_tra and b.st_reg='A' " + strEmp + strDep +")" + "\n";
//          strSql+=" inner join tbm_emp c on (b.co_emp=c.co_emp)" + "\n";
//          strSql+=" order by (a.tx_ape || ' ' || a.tx_nom)) as d" + "\n";
//          strSql+=" left outer join tbm_callab e on (e.co_hor=d.co_hor and e.st_dialab='S' " + strFilFec+ " )" + "\n";
//          strSql += " LEFT OUTER JOIN tbm_cabconasitra f on (f.co_tra=d.co_tra and f.fe_dia=e.fe_dia)" + "\n";
//          strSql+=" left outer join tbm_motjusconasi g on (f.co_motJusFal=g.co_motjus) " + "\n";
//          strSql+=" " + strDepAut+ "\n";
//          strSql+=" where e.fe_dia >= fe_inicon " + "\n";
//          strSql+=" " + sqlFilEmp+ "\n";
//          strSql+=" AND NOT (EXTRACT(DOW FROM e.fe_dia) IN (6,0))" + "\n";
//          strSql+=" group by d.co_emp,tx_empresa,d.co_ofi,d.co_tra,d.co_hor,d.co_dep,empleado,e.fe_dia, e.ho_ent,e.ho_sal," + "\n";
//          strSql+=" f.ho_ent,f.ho_sal,st_jusFal,f.ho_saljus,f.ho_entjus,f.co_motjusfal,g.tx_deslar,f.tx_obsjusfal" + "\n";
//          strSql+=" order by  empleado,e.fe_dia " + " ) x " ;//+ strSqlFalta;
          strSql=" select * from (";
          strSql+="select d.co_emp,tx_empresa,d.co_tra,d.co_ofi,d.co_dep,d.co_hor,empleado,e.fe_dia, " + "\n";
          strSql+=" (case st_jusFal when 'S' then true else false end) as blnJusFal,";
          strSql+=" st_jusFal , co_motJusFal,  tx_obsJusFal," + "\n";
          strSql+=" (case ((f.ho_ent is null and f.ho_sal is null)) when true then true else false end) as blnTieFal," + "\n";
          strSql+=" (case when (EXTRACT(DOW FROM e.fe_dia) IN (6)) then 'N' else e.st_dialab end) as stAplVac, EXTRACT(DOW FROM e.fe_dia) as neDia" + "\n";
          strSql+=" from (" + "\n";
          strSql+=" select fe_inicon, b.co_emp, b.co_tra, b.co_dep,b.co_ofi,b.co_hor,(a.tx_ape || ' ' || a.tx_nom) as empleado, c.tx_nom as tx_empresa from tbm_tra a" + "\n";
          strSql+=" inner join tbm_traemp b on (a.co_tra=b.co_tra and b.st_reg='A' " + strEmp + strDep +")" + "\n";
          strSql+=" inner join tbm_emp c on (b.co_emp=c.co_emp)" + "\n";
          strSql+=" order by (a.tx_ape || ' ' || a.tx_nom)) as d" + "\n";
          strSql+=" left outer join tbm_callab e on (1=1 " + strFilFec+ " )" + "\n";
          strSql += " LEFT OUTER JOIN tbm_cabconasitra f on (f.co_tra=d.co_tra and f.fe_dia=e.fe_dia)" + "\n";
          strSql+=" left outer join tbm_motjusconasi g on (f.co_motJusFal=g.co_motjus) " + "\n";
          strSql+=" " + strDepAut+ "\n";
          strSql+=" where e.fe_dia >= fe_inicon " + "\n";
          strSql+=" " + sqlFilEmp+ "\n";
          strSql+=" group by d.co_emp,tx_empresa,d.co_ofi,d.co_tra,d.co_hor,d.co_dep,empleado,e.fe_dia," + "\n";
          strSql+=" f.ho_ent,f.ho_sal,st_jusFal,f.co_motjusfal,f.tx_obsjusfal,e.st_dialab" + "\n";
          strSql+=" order by  empleado,e.fe_dia " + " ) x " ;//+ strSqlFalta;
          System.out.println(strSql);
          rstLoc=stmLoc.executeQuery(strSql);
              
          vecDat = new Vector();
            rstLoc=stmLoc.executeQuery(strSql);
            while(rstLoc.next()){
                java.util.Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_LINEA,"");
                vecReg.add(INT_TBL_CODEMP, rstLoc.getInt("co_emp"));
                vecReg.add(INT_TBL_NOMEMP, rstLoc.getString("tx_empresa"));
                vecReg.add(INT_TBL_FECHA, rstLoc.getString("fe_dia"));
                vecReg.add(INT_TBL_CODTRA, rstLoc.getInt("co_tra"));
                vecReg.add(INT_TBL_NOMTRA, rstLoc.getString("empleado"));
                if (rstLoc.getBoolean("blnJusFal")) {
                    vecReg.add(INT_TBL_CHKJUSFAL, Boolean.FALSE);
                    vecReg.add(INT_TBL_OBS, rstLoc.getString("tx_obsJusFal"));
                    vecReg.add(INT_TBL_ESTJUSFAL, "S");
                    vecReg.add(INT_TBL_ESTAPLVAC, rstLoc.getString("stAplVac"));
                    vecReg.add(INT_TBL_NEDIA, rstLoc.getInt("neDia"));
                    
                }else{
                    if (rstLoc.getString("stAplVac").equals("N") && (rstLoc.getInt("neDia")==6 || rstLoc.getInt("neDia")==0)) {
                        vecReg.add(INT_TBL_CHKJUSFAL, Boolean.FALSE);
                    }else{
                        vecReg.add(INT_TBL_CHKJUSFAL, Boolean.TRUE);
                    }
                    vecReg.add(INT_TBL_OBS, rstLoc.getString("tx_obsJusFal"));
                    vecReg.add(INT_TBL_ESTJUSFAL, "N");
                    vecReg.add(INT_TBL_ESTAPLVAC, rstLoc.getString("stAplVac"));
                    vecReg.add(INT_TBL_NEDIA, rstLoc.getInt("neDia"));
                }
                
                
                vecDataCon.add(vecReg);
            }

            objTblMod.setData(vecDataCon);
            tblDat .setModel(objTblMod);          
          
              lblMsgSis.setText("Listo");
              tabGen.setSelectedIndex(1);
              butCon.setText("Consultar");
              lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
          pgrSis.setIndeterminate(false);
          vecDat.clear();
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
    
       private static int diferenciaFecha(Date fechaHasta, Date fechaDesde) {
        long difMs = fechaHasta.getTime() - fechaDesde.getTime();
        long dias = difMs / (1000 * 60 * 60 * 24);
        return (int) dias;
    }
       private void setDiasVac(){
        if (objSelFec.getFechaDesde()!=null ) {
                    if (objSelFec.getFechaHasta()!=null) {
                    int intNumDias;
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date datFecHasta;
                        try {
                            datFecHasta = formatter.parse(objSelFec.getFechaHasta());
                            Date datFecDesde = formatter.parse(objSelFec.getFechaDesde());
                        intNumDias= diferenciaFecha(datFecHasta, datFecDesde);
                        intNumDias=intNumDias+1; //Se suma un dia para contar el dia actual.
                        txtDia.setText(String.valueOf(intNumDias));
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                    
                    }
                }
        }
       
       private boolean guardarDat(){
        boolean blnRes=true;
        java.sql.Connection con = null; 
        java.sql.Statement stmLoc = null , stmLocAux = null;
        java.sql.ResultSet rstLoc = null , rstLocAux = null;
        String strSql="";
        try{
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
      
            if(con!=null){
                
                con.setAutoCommit(false);
                stmLoc=con.createStatement();
                stmLocAux=con.createStatement();
                int intCon=0;
                int intCoTraSel=0;
                for(int i=0; i<tblDat.getRowCount();i++){
                    if (objTblMod.getValueAt(i, INT_TBL_CHKJUSFAL).equals(true)) {
                                            //El codigo de motivo es 13 ya que es el de Vacaciones.
                        String strCoMot=String.valueOf(13);
                        
                        intCoTraSel=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_CODTRA).toString());

                        String strJusFal="";
                        strJusFal=objUti.codificar("S");
                        
                        strSql="";
                        strSql+="select * from tbm_cabconasitra";
                        strSql+=" where co_tra=" + tblDat.getValueAt(i, INT_TBL_CODTRA).toString();
                        strSql+=" and fe_dia="+objUti.codificar(tblDat.getValueAt(i, INT_TBL_FECHA));
                        rstLoc=stmLoc.executeQuery(strSql);
                        String strFecDesObs =obtenerFechasDesdeHasta(Integer.valueOf(tblDat.getValueAt(i, INT_TBL_CODTRA).toString()),0);
                        String strFecHasObs =obtenerFechasDesdeHasta(Integer.valueOf(tblDat.getValueAt(i, INT_TBL_CODTRA).toString()),1);
                        if(rstLoc.next()){
                            strSql="";
                            strSql+="UPDATE tbm_cabconasitra SET";
                            strSql+=" ho_entjus= Null , ";
                            strSql+=" ho_saljus= Null , ";
                            strSql+=" st_jusfal= "+ strJusFal +" , ";
                            strSql+=" co_motjusfal= "+ strCoMot  +" , ";
                            strSql+=" tx_obsjusfal= "+ objUti.codificar("Vacaciones desde el " + strFecDesObs + " hasta el " + strFecHasObs) +" , ";
                            strSql+=" fe_jusfal= current_timestamp" + " , ";
                            strSql+=" co_usrjusfal= " + objParSis.getCodigoUsuario() +" , ";
                            strSql+=" tx_comjusfal= " + objUti.codificar(objParSis.getDireccionIP());
                            strSql+=" WHERE";
                            strSql+=" co_tra= " + objTblMod.getValueAt(i, INT_TBL_CODTRA).toString();
                            strSql+=" and fe_dia= " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_FECHA));
                            stmLoc.executeUpdate(strSql);
                        }else{
                            strSql="";
                            strSql+="INSERT INTO tbm_cabconasitra(";
                            strSql+=" co_tra, fe_dia, st_jusfal, tx_obsjusfal, fe_jusfal,";
                            strSql+=" co_usrjusfal, tx_comjusfal,  ho_entjus,";
                            strSql+=" ho_saljus, co_motjusfal)";
                            strSql+=" VALUES (";
                            strSql+= objTblMod.getValueAt(i, INT_TBL_CODTRA).toString() + " , ";
                            strSql+= objUti.codificar(objTblMod.getValueAt(i, INT_TBL_FECHA)) + " , ";
                            strSql+= strJusFal + " , ";
                            strSql+= objUti.codificar("Vacaciones desde el " + strFecDesObs + " hasta el " + strFecHasObs) + " , ";
                            strSql+=" current_timestamp" + " , ";
                            strSql+= objParSis.getCodigoUsuario() + " , ";
                            strSql+= objUti.codificar(objParSis.getDireccionIP()) + " , ";
                            strSql+= " Null , ";
                            strSql+= "Null , ";
                            strSql+= strCoMot  +")";
                            stmLoc.executeUpdate(strSql);
                        }
                    }
                }
                if (!chkVacPag.isSelected()) {
                if (!insertaEgr()) {
                    blnRes=false;
                }
                }
                if(blnRes){
                    con.commit();
                    //new RRHHDao(objUti, objParSis).callServicio9();
                    //Inicializo el estado de las filas.
                    objTblMod.initRowsState();
                    objTblMod.setDataModelChanged(false);
                }else
                    con.rollback();
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
        try{stmLocAux.close();stmLocAux=null;}catch(Throwable ignore){}
        try{rstLoc.close();rstLoc=null;}catch(Throwable ignore){}
        try{rstLocAux.close();rstLocAux=null;}catch(Throwable ignore){}
        try{con.close();}catch(Throwable ignore){}
        }
    return blnRes;
}
       private boolean verificarCasos(int intFilSel){
        if (objTblMod.getValueAt(intFilSel, INT_TBL_ESTJUSFAL).toString()!=null) {
            if (objTblMod.getValueAt(intFilSel, INT_TBL_ESTJUSFAL).toString().equals("S")) {
                mostrarMsgInf("El registro que intenta seleccionar ya se encuentra justificado.");
                return false;
            }
        }
           if (objTblMod.getValueAt(intFilSel, INT_TBL_ESTAPLVAC).toString()!=null) {
              if (objTblMod.getValueAt(intFilSel, INT_TBL_ESTAPLVAC).toString().equals("N") && 
                      Integer.valueOf(objTblMod.getValueAt(intFilSel, INT_TBL_NEDIA).toString())==6) {
                mostrarMsgInf("El registro que intenta seleccionar es del día Sabado por lo que no se puede justificar.\nEn todo caso será contabilizado como vacaciones si es que en el filtro de fecha se colocó como tal.");
                return false;
            }else if (objTblMod.getValueAt(intFilSel, INT_TBL_ESTAPLVAC).toString().equals("N") && 
                      Integer.valueOf(objTblMod.getValueAt(intFilSel, INT_TBL_NEDIA).toString())==0) {
                   mostrarMsgInf("El registro que intenta seleccionar es del día Domingo por lo que no se puede justificar.\nEn todo caso será contabilizado como vacaciones si es que en el filtro de fecha se colocó como tal.");
                   return false;
               }
 
           }
    return true;
    }
       /**
        * 
        * @param intCodTra Codigo trabajador
        * @param intTip 0=Desde 1=Hasta
        * @return 
        */
       private String obtenerFechasDesdeHasta(int intCodTra,int intTip){
       String strFec="",strFecTemp="";
       for(int i=0; i<tblDat.getRowCount();i++){
          if (objTblMod.getValueAt(i, INT_TBL_CHKJUSFAL).equals(true) && tblDat.getValueAt(i, INT_TBL_CODTRA).toString().compareTo(String.valueOf(intCodTra))==0) {
              strFecTemp=tblDat.getValueAt(i, INT_TBL_FECHA).toString();//01/Ene/2016 - 02/Ene/2016
              if (intTip==0) {//Fecha Desde
                  if (strFec.equals("")) {
                      strFec=tblDat.getValueAt(i, INT_TBL_FECHA).toString();//01/Ene/2016
                  }else{
                      String intTipTemp= compararFechas(strFec, strFecTemp);//01/Ene/2016 02/Ene/2016
                      if (intTipTemp.equals("0")) {
                          strFec=strFec;
                      }else if (intTipTemp.equals("1")) {
                          strFec=strFecTemp;
                      }
                  }
              }else if (intTip==1) {
                  if (strFec.equals("")) {
                      strFec=tblDat.getValueAt(i, INT_TBL_FECHA).toString();//01/Ene/2016
                  }else{
                      String intTipTemp= compararFechas(strFec, strFecTemp);//01/Ene/2016 02/Ene/2016
                      if (intTipTemp.equals("0")) {
                          strFec=strFecTemp;
                      }else if (intTipTemp.equals("1")) {
                          strFec=strFec;
                      }
                  }
              }
          }
       }
       return strFec;
       }
       
     private String compararFechas(String fecha1, String fechaTemp) {
        String resultado = "";
        try {
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaDate1 = formateador.parse(fecha1);
            Date fechaDate2 = formateador.parse(fechaTemp);
            if (fechaDate1.before(fechaDate2)) {
                resultado = "0";// La fecha uno es menor
            } else {
                if (fechaDate2.before(fechaDate1)) {
                    resultado = "1";//La fecha 1 es mayor
                } else {
                    resultado = "-1";// Fechas son iguales
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultado;
    }
     
    private Boolean insertaEgr() throws ParseException {
        String strSql;
        int intCodTraTemp = -1;
        Boolean blnVerTra = true;
        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null, stmLocAux = null;
        java.sql.ResultSet rstLoc = null, rstLocAux = null;
        try {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());

            if (con != null) {
                con.setAutoCommit(true);
                stmLoc = con.createStatement();
                stmLocAux = con.createStatement();
                for (int i = 0; i < tblDat.getRowCount(); i++) {
                    if (objTblMod.getValueAt(i, INT_TBL_CHKJUSFAL).equals(true)) {
                        if (intCodTraTemp == -1) {
                            intCodTraTemp = Integer.valueOf(objTblMod.getValueAt(i, INT_TBL_CODTRA).toString());

                        } else {
                            if (intCodTraTemp == Integer.valueOf(objTblMod.getValueAt(i, INT_TBL_CODTRA).toString())) {
                                blnVerTra = false;
                            } else {
                                intCodTraTemp = Integer.valueOf(objTblMod.getValueAt(i, INT_TBL_CODTRA).toString());
                                blnVerTra = true;
                            }
                        }
                        if (blnVerTra) {
                            blnVerTra = false;
                            String strFecDes = obtenerFechasDesdeHasta(Integer.valueOf(tblDat.getValueAt(i, INT_TBL_CODTRA).toString()), 0);
                            String strFecHas = obtenerFechasDesdeHasta(Integer.valueOf(tblDat.getValueAt(i, INT_TBL_CODTRA).toString()), 1);
                            
                            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar cal = new GregorianCalendar();
                            cal.setTime(formateador.parse(strFecDes));
                            if (verificaFecha(Integer.valueOf(tblDat.getValueAt(i, INT_TBL_CODEMP).toString()), strFecDes, strFecHas)) {
                                int intNumDias;
                                if (strFecDes.equals(strFecHas)) {
                                    intNumDias=1;
                                }else{
                                    intNumDias = contarDias(strFecDes, strFecHas)+1;
                                }
                                

                                strSql = "";
                                strSql += "SELECT a.* , b.co_emp as co_empTra ,round ((((" + (1) + ")*(nd_valrub/30))*0.75),2) as nd_valRubDescIESS, round ((((nd_valrub/30))*(" + (intNumDias) + ")),2) as nd_valEgr FROM tbh_suetra a " + "\n";
                                strSql += "INNER JOIN tbm_traemp b ON(b.co_tra=a.co_tra AND b.st_reg='A') " + "\n";
                                strSql += "WHERE a.co_tra=" + intCodTraTemp + "\n";
                                strSql += "AND a.co_rub=1 " + "\n";
                                strSql += "ORDER BY co_his DESC " + "\n";
                                strSql += "LIMIT 1;";
                                rstLocAux = stmLocAux.executeQuery(strSql);
                                if (rstLocAux.next()) {
                                    strSql = "";
                                    int intCoEgr = 0;
                                    String strSQL = "SELECT case when (Max(co_egr)+1) is null then 1 else Max(co_egr)+1 end as co_egr  FROM tbm_cabingegrprgtra WHERE "
                                            + " co_emp=" + rstLocAux.getString("co_empTra");
                                    rstLoc = stmLoc.executeQuery(strSQL);
                                    if (rstLoc.next()) {
                                        intCoEgr = rstLoc.getInt("co_egr");
                                    }
                                    String strTx_tipEgrPrg = "D";
                                    String strTx_tipCuo = "M";
                                    String strNeNumCuo = "1"; //registo activo
                                    String strSt_Reg = "A"; //registo activo

                                    strSql = "";
                                    strSql += "INSERT INTO tbm_cabingegrprgtra (co_emp , co_egr , co_tra , co_rub , fe_doc , " + "\n";
                                    strSql += "tx_tipegrprg , fe_pricuo , fe_ultcuo , nd_valegrprg , ne_numcuo , tx_tipcuo,tx_obs1 , st_reg , fe_ing , co_usring ) " + "\n";
                                    strSql += "VALUES ( " + "\n";
                                    strSql += rstLocAux.getString("co_empTra") + " , \n";
                                    strSql += intCoEgr + " , \n";
                                    strSql += intCodTraTemp + " , \n";
                                    strSql += "17" + " , \n";
                                    strSql += "current_date" + " , \n";
                                    strSql += objUti.codificar(strTx_tipEgrPrg) + " , \n";

                                    String strFePriCuo = cal.get(Calendar.YEAR) + "-" + ((cal.get(Calendar.MONTH)) + 1) + "-" + "01";
                                    strSql += objUti.codificar(strFePriCuo) + " , \n";
                                    strSql += objUti.codificar(strFePriCuo) + " , \n";
                                    strSql += rstLocAux.getString("nd_valEgr") + " , \n";
                                    strSql += objUti.codificar(strNeNumCuo) + " , \n";
                                    strSql += objUti.codificar(strTx_tipCuo) + " , \n";
                                    strSql += objUti.codificar("Liquidación Vacaciones: Desde " + strFecDes + " Hasta " + strFecHas) + " , \n";
                                    strSql += objUti.codificar(strSt_Reg) + " , \n";
                                    strSql += "current_timestamp" + " , \n";
                                    strSql += objParSis.getCodigoUsuario() + " );";
                                    stmLoc.executeUpdate(strSql); 

                                    strSql = "";
                                    strSql += "INSERT INTO tbm_detingegrprgtra (co_emp , co_egr , co_reg , fe_cuo , nd_valcuo , st_reg)" + "\n";
                                    strSql += "VALUES ( " + "\n";
                                    strSql += rstLocAux.getString("co_empTra") + " , \n";
                                    strSql += intCoEgr + " , 1 , \n";
                                    strSql += objUti.codificar(strFePriCuo) + " , \n";
                                    strSql += rstLocAux.getString("nd_valEgr") + " , \n";
                                    strSql += objUti.codificar(strSt_Reg) + " );";
                                    stmLoc.executeUpdate(strSql);
                                }
                            } else {
                                //Cuando esta dentro de dos periodos.
                                int intNumDias = contarDias(strFecDes, strFecHas);
                                String strFecCor = devuelveFechaCorte(Integer.valueOf(tblDat.getValueAt(i, INT_TBL_CODEMP).toString()), strFecDes);
                                int intNumDiaPriCuo = contarDias(strFecDes, strFecCor);
                                int intNumDiaUltCuo = intNumDias - intNumDiaPriCuo;
                                strSql = "";
                                strSql += "SELECT a.* , b.co_emp as co_empTra,round ((((" + (1) + ")*(nd_valrub/30))*0.75),2) as nd_valRubDescIESS, round ((((nd_valrub/30))*(" + (intNumDias) + ")),2) as nd_valEgr,round ((((nd_valrub/30))*(" + (intNumDiaPriCuo) + ")),2) as nd_valPriCuo,round ((((nd_valrub/30))*(" + (intNumDiaUltCuo) + ")),2) as nd_valUltCuo FROM tbh_suetra a " + "\n";
                                strSql += "INNER JOIN tbm_traemp b ON(b.co_tra=a.co_tra AND b.st_reg='A') " + "\n";
                                //                            strSql+="SELECT * , round (((("+(intCon-3)+")*(nd_valrub/30))*0.75),2) as nd_valRubDescIESS FROM tbh_suetra a "+"\n";
                                strSql += "WHERE a.co_tra=" + intCodTraTemp + "\n";
                                strSql += "AND a.co_rub=1 " + "\n";
                                strSql += "ORDER BY co_his DESC " + "\n";
                                strSql += "LIMIT 1;";
                                rstLocAux = stmLocAux.executeQuery(strSql);
                                if (rstLocAux.next()) {
                                    strSql = "";
                                    int intCoEgr = 0;
                                    String strSQL = "SELECT case when (Max(co_egr)+1) is null then 1 else Max(co_egr)+1 end as co_egr  FROM tbm_cabingegrprgtra WHERE "
                                            + " co_emp=" + rstLocAux.getString("co_empTra");
                                    rstLoc = stmLoc.executeQuery(strSQL);
                                    if (rstLoc.next()) {
                                        intCoEgr = rstLoc.getInt("co_egr");
                                    }
                                    String strTx_tipEgrPrg = "D";
                                    String strTx_tipCuo = "D";
                                    String strNeNumCuo = "2"; //registo activo
                                    String strSt_Reg = "A"; //registo activo

                                    strSql = "";
                                    strSql += "INSERT INTO tbm_cabingegrprgtra (co_emp , co_egr , co_tra , co_rub , fe_doc , " + "\n";
                                    strSql += "tx_tipegrprg , fe_pricuo , fe_ultcuo , nd_valegrprg , ne_numcuo , tx_tipcuo,tx_obs1 , st_reg , fe_ing , co_usring ) " + "\n";
                                    strSql += "VALUES ( " + "\n";
                                    strSql += rstLocAux.getString("co_empTra") + " , \n";
                                    strSql += intCoEgr + " , \n";
                                    strSql += intCodTraTemp + " , \n";
                                    strSql += "17" + " , \n";
                                    strSql += "current_date" + " , \n";
                                    strSql += objUti.codificar(strTx_tipEgrPrg) + " , \n";

                                    String strFePriCuo = cal.get(Calendar.YEAR) + "-" + ((cal.get(Calendar.MONTH)) + 1) + "-" + "01";
                                    String strFeUltCuo = cal.get(Calendar.YEAR) + "-" + ((cal.get(Calendar.MONTH)) + 2) + "-" + "01";
                                    strSql += objUti.codificar(strFePriCuo) + " , \n";
                                    strSql += objUti.codificar(strFeUltCuo) + " , \n";
                                    strSql += rstLocAux.getString("nd_valEgr") + " , \n";
                                    strSql += objUti.codificar(strNeNumCuo) + " , \n";
                                    strSql += objUti.codificar(strTx_tipCuo) + " , \n";
                                    strSql += objUti.codificar("Liquidación Vacaciones: Desde " + strFecDes + " Hasta " + strFecHas) + " , \n";
                                    strSql += objUti.codificar(strSt_Reg) + " , \n";
                                    strSql += "current_timestamp" + " , \n";
                                    strSql += objParSis.getCodigoUsuario() + " );";
                                    String strValPriCuo = rstLocAux.getString("nd_valPriCuo");
                                    String strValUltcuo = rstLocAux.getString("nd_valUltCuo");
                                    stmLoc.executeUpdate(strSql);
                                    for (int j = 0; j < 2; j++) {
                                        strSql = "";
                                        strSql += "INSERT INTO tbm_detingegrprgtra (co_emp , co_egr , co_reg , fe_cuo , nd_valcuo , st_reg)" + "\n";
                                        strSql += "VALUES ( " + "\n";
                                        strSql += rstLocAux.getString("co_empTra") + " , \n";
                                        strSql += intCoEgr + " , " + (j + 1) + " , \n";
                                        if (j == 0) {
                                            strSql += objUti.codificar(strFePriCuo) + " , \n";
                                            strSql += strValPriCuo + " , \n";
                                        } else {
                                            strSql += objUti.codificar(strFeUltCuo) + " , \n";
                                            strSql += strValUltcuo + " , \n";
                                        }

                                        strSql += objUti.codificar(strSt_Reg) + " );";
                                        stmLoc.executeUpdate(strSql);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        }
        return true;
    }
    
       private Boolean verificaFecha(int intCodEmp, String strFecDes, String strFecHas) {
        Boolean blnRes = true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql;
        int intAnio, intMes;
        try {
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = new GregorianCalendar();
            cal.setTime(formateador.parse(strFecDes));
            intAnio = cal.get(Calendar.YEAR);
            intMes = cal.get(Calendar.MONTH) + 1;
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            strSql = "";
            strSql += " select count(0) as cont from  (";
            strSql += " SELECT (GENERATE_SERIES(0, a1.ne_numDiaPer) + a1.fe_des) AS fe_dia";
            strSql += " FROM";
            strSql += " (";
            strSql += " SELECT";
            strSql += " (SELECT fe_des FROM tbm_feccorrolpag WHERE co_emp=" + intCodEmp + " AND ne_ani=" + intAnio + " AND ne_mes=" + intMes + " AND ne_per=1) AS fe_des";
            strSql += " , (SELECT CAST(EXTRACT(DAYS FROM ( (SELECT CAST(fe_has AS TIMESTAMP) FROM tbm_feccorrolpag WHERE co_emp=" + intCodEmp + " AND ne_ani=" + intAnio + " AND ne_mes=" + intMes + " AND ne_per=2)-(SELECT CAST(fe_des AS TIMESTAMP) FROM tbm_feccorrolpag WHERE co_emp=" + intCodEmp + " AND ne_ani=" + intAnio + " AND ne_mes=" + intMes + " AND ne_per=1))) AS INT2)) AS ne_numDiaPer";
            strSql += " ) as a1) AS a2";
            strSql += " where " + objUti.codificar(strFecDes) + " in (a2.fe_dia) or " + objUti.codificar(strFecHas) + " in (a2.fe_dia)";
            rstLoc = stmLoc.executeQuery(strSql);
            int intCont = 0;
            if (rstLoc.next()) {
                intCont = rstLoc.getInt("cont");
            }
            if (intCont == 2) {
                blnRes = true;
            } else {
                if (strFecDes.equals(strFecHas)) {
                    blnRes=true;
                }else{
                    blnRes = false;
                }            
            }
            rstLoc.close();
            rstLoc = null;
            conLoc.close();
            stmLoc.close();
            conLoc = null;
            stmLoc = null;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    private int contarDias(String strFecDes, String strFecHas) {
        int intNumDias = 0;
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date datFecHasta;
        try {
            strFecHas = strFecHas.replace("-", "/");
            strFecDes = strFecDes.replace("-", "/");

            datFecHasta = formatter.parse(strFecHas);
            Date datFecDesde = formatter.parse(strFecDes);
            if (strFecDes.equals(strFecHas)) {
                intNumDias=1;
            }else{
                intNumDias = diferenciaFecha(datFecHasta, datFecDesde);
            }
            
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return intNumDias;
    }
       
       private String devuelveFechaCorte(int intCodEmp, String strFecDes) {
        String strFecCor = "";
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql;
        int intAnio, intMes;
        try {
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = new GregorianCalendar();
            cal.setTime(formateador.parse(strFecDes));
            intAnio = cal.get(Calendar.YEAR);
            intMes = cal.get(Calendar.MONTH) + 1;
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            strSql = "";
            strSql += " SELECT fe_has FROM tbm_feccorrolpag ";
            strSql += " WHERE co_emp= " + intCodEmp + " ";
            strSql += " AND ne_ani= " + intAnio + " ";
            strSql += " AND ne_mes= " + intMes + " ";
            strSql += " AND ne_per= 2 ";
            rstLoc = stmLoc.executeQuery(strSql);
            if (rstLoc.next()) {
                strFecCor = rstLoc.getString("fe_has");
            }
            rstLoc.close();
            rstLoc = null;
            conLoc.close();
            stmLoc.close();
            conLoc = null;
            stmLoc = null;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strFecCor;
    }
       private Boolean validarFiltros(){
        if(txtCodTra.getText().length()==0){
            txtCodTra.requestFocus();
            txtCodTra.selectAll();
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Empleado</FONT> es obligatorio.<BR>Escriba o seleccione una empresa y vuelva a intentarlo.</HTML>");
            return false;
        }
           return true;
       }
       private Boolean validarInicioContrato(){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql;
        Boolean blnRes=false;
        int intNumDiasCon=0;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            strSql = "";
            strSql += "select "+objUti.codificar(objSelFec.getFechaDesde())+" , fe_inicon,("+objUti.codificar(objSelFec.getFechaDesde())+"-fe_inicon)/365 as anio,"+objUti.codificar(objSelFec.getFechaDesde())+"-fe_inicon as dias FROM tbm_traemp ";
            strSql += " WHERE co_tra= " + txtCodTra.getText() + " ";
            strSql += " AND st_reg= 'A' ";
            System.out.println(strSql);
            rstLoc = stmLoc.executeQuery(strSql);
            if (rstLoc.next()) {
                intNumDiasCon = rstLoc.getInt("dias");
            }
            if (intNumDiasCon>=365) {
                blnRes= true;
            }
            rstLoc.close();
            rstLoc = null;
            conLoc.close();
            stmLoc.close();
            conLoc = null;
            stmLoc = null;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
           return blnRes;
       }
}

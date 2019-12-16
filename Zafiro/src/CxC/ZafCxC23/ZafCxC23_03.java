/*
 * ZafCxC23_03.java
 *
 * Created on 26 de febrero de 2008, 12:56 PM
 */
package CxC.ZafCxC23;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafToolBar.ZafToolBar;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCxC23_03 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    static final int INT_TBL_RCO_LIN=0;                         //Línea
    static final int INT_TBL_RCO_COD=1;                         //Referencia comercial: Código.
    static final int INT_TBL_RCO_NOM=2;                         //Referencia comercial: Nombre.
    static final int INT_TBL_RCO_TIE=3;                         //Referencia comercial: Tiempo.
    static final int INT_TBL_RCO_CUP=4;                         //Referencia comercial: Cupo.
    static final int INT_TBL_RCO_PLA=5;                         //Referencia comercial: Plazo.
    static final int INT_TBL_RCO_FOR_PAG=6;                     //Referencia comercial: Forma de pago.
    static final int INT_TBL_RCO_CHK_PRO=7;                     //Referencia comercial: Casilla de verificación (Protestos).
    static final int INT_TBL_RCO_NUM_PRO=8;                     //Referencia comercial: Número de protestos.
    static final int INT_TBL_RCO_OB1=9;                         //Referencia comercial: Observación1.

    static final int INT_TBL_RBA_LIN=0;                         //Línea
    static final int INT_TBL_RBA_COD=1;                         //Referencia bancaria: Código.
    static final int INT_TBL_RBA_NOM=2;                         //Referencia bancaria: Nombre.
    static final int INT_TBL_RBA_FEC_APE=3;                     //Referencia bancaria: Fecha de apertura.
    static final int INT_TBL_RBA_SAL=4;                         //Referencia bancaria: Saldos.
    static final int INT_TBL_RBA_CHK_PRO=5;                     //Referencia bancaria: Casilla de verificación (Protestos).
    static final int INT_TBL_RBA_NUM_PRO=6;                     //Referencia bancaria: Número de protestos.
    static final int INT_TBL_RBA_CHK_CDI=7;                     //Referencia bancaria: Casilla de verificación (Créditos directos).
    static final int INT_TBL_RBA_MON_CDI=8;                     //Referencia bancaria: Monto (Créditos directos).
    static final int INT_TBL_RBA_CHK_CIN=9;                     //Referencia bancaria: Casilla de verificación (Créditos indirectos).
    static final int INT_TBL_RBA_MON_CIN=10;                    //Referencia bancaria: Monto (Créditos indirectos).
    static final int INT_TBL_RBA_OB1=11;                        //Referencia bancaria: Observación1.
    
    //Variables generales.
    private ZafDatePicker dtpFecDoc;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblModRefCom, objTblModRefBan;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private MiToolBar objTooBar;                                //Barra de botones.
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL, strAux, strSQLCon;
    private Vector vecEstReg;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private boolean blnHayCam;                                  //Determina si hay cambios en el formulario.
    //Variables de la clase.
    private Integer intCodEmp;
    private Integer intCodCli;
    private String strIdeCli;
    
    /** Crea una nueva instancia de la clase ZafCxC23_03. */
    public ZafCxC23_03(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            /*
             * Hacer que se configure el formulario luego de ser creado. Fue necesario configurar el JInternalFrame
             * en un Thread porque si el usuario abría un JDialog y luego se cambiaba a otra
             * ventana fuera de Zafiro ocurría que el JDialog quedaba atrás de la ventana principal
             * de Zafiro. Se analizó el problema y se concluyo que el problema se presentaba porque al configurar
             * el JDialog se le enviaba el padre y al parecer mientras no se termine de construir
             * el objeto JInternalFrame por completo el método "this" devuelve null. Por eso la estrategia fue poner un Thread que
             * se esté ejecutando hasta que detecte que el objeto JInternalFrame ha sido creado. Es decir, hasta que
             * sea diferente a null.
            */
            objThrGUI=new ZafThreadGUI();
            objThrGUI.setIndFunEje(-1);
            objThrGUI.start();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /**
     * Este constructor crea una instancia de la clase ZafCxC23_03 y al mismo tiempo carga 
     * automáticamente la solicitud de crédito del cliente especificado de acuerdo al código
     * de la empresa y el código del cliente.
     * @param obj El objeto ZafParSis.
     * @param codigoEmpresa El código de la empresa.
     * @param codigoCliente El código del cliente.
     */
    public ZafCxC23_03(ZafParSis obj, Integer codigoEmpresa, Integer codigoCliente)
    {
        this(obj);
        intCodEmp=codigoEmpresa;
        intCodCli=codigoCliente;
    }
    
    /**
     * Este constructor crea una instancia de la clase ZafCxC23_03 y al mismo tiempo carga 
     * automáticamente la solicitud de crédito del cliente especificado de acuerdo a la identificación del cliente.
     * @param obj El objeto ZafParSis.
     * @param identificacionCliente La identificación del cliente.
     */
    public ZafCxC23_03(ZafParSis obj, String identificacionCliente)
    {
        this(obj);
        strIdeCli=identificacionCliente;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrTipForPag = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblCodSolCre = new javax.swing.JLabel();
        txtCodSolCre = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        txtDesLarCli = new javax.swing.JTextField();
        lblFecSolCre = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtIdeCli = new javax.swing.JTextField();
        lblPro = new javax.swing.JLabel();
        txtNomPro = new javax.swing.JTextField();
        lblTipActEmp = new javax.swing.JLabel();
        txtTipActEmp = new javax.swing.JTextField();
        lblEmp = new javax.swing.JLabel();
        txtNomEmp = new javax.swing.JTextField();
        panGenDet = new javax.swing.JPanel();
        panRefCom = new javax.swing.JPanel();
        spnDatRefCom = new javax.swing.JScrollPane();
        tblDatRefCom = new javax.swing.JTable();
        panRefBan = new javax.swing.JPanel();
        spnDatRefBan = new javax.swing.JScrollPane();
        tblDatRefBan = new javax.swing.JTable();
        panRefBurCre = new javax.swing.JPanel();
        spnRefBurCre = new javax.swing.JScrollPane();
        txaRefBurCre = new javax.swing.JTextArea();
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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 64));
        panGenCab.setLayout(null);

        lblCodSolCre.setText("Código:");
        lblCodSolCre.setToolTipText("Código de la solicitud");
        panGenCab.add(lblCodSolCre);
        lblCodSolCre.setBounds(208, 4, 100, 20);

        txtCodSolCre.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtCodSolCre);
        txtCodSolCre.setBounds(308, 4, 100, 20);

        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Cliente");
        panGenCab.add(lblCli);
        lblCli.setBounds(0, 24, 100, 20);

        txtDesLarCli.setToolTipText("Nombre del cliente/proveedor");
        panGenCab.add(txtDesLarCli);
        txtDesLarCli.setBounds(256, 24, 408, 20);

        lblFecSolCre.setText("Fecha:");
        lblFecSolCre.setToolTipText("Fecha de la solicitud");
        panGenCab.add(lblFecSolCre);
        lblFecSolCre.setBounds(416, 4, 100, 20);

        txtCodCli.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodCli.setToolTipText("Código del cliente/proveedor");
        panGenCab.add(txtCodCli);
        txtCodCli.setBounds(100, 24, 56, 20);

        txtIdeCli.setToolTipText("Identificación del cliente/proveedor");
        panGenCab.add(txtIdeCli);
        txtIdeCli.setBounds(156, 24, 100, 20);

        lblPro.setText("Propietario:");
        lblPro.setToolTipText("Propietario de la empresa cliente");
        panGenCab.add(lblPro);
        lblPro.setBounds(0, 44, 100, 20);
        panGenCab.add(txtNomPro);
        txtNomPro.setBounds(100, 44, 220, 20);

        lblTipActEmp.setText("Actividad:");
        lblTipActEmp.setToolTipText("Tipo de actividad de la empresa cliente");
        panGenCab.add(lblTipActEmp);
        lblTipActEmp.setBounds(344, 44, 100, 20);
        panGenCab.add(txtTipActEmp);
        txtTipActEmp.setBounds(444, 44, 220, 20);

        lblEmp.setText("Empresa:");
        lblEmp.setToolTipText("Empresa");
        panGenCab.add(lblEmp);
        lblEmp.setBounds(0, 4, 100, 20);
        panGenCab.add(txtNomEmp);
        txtNomEmp.setBounds(100, 4, 100, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenDet.setLayout(new java.awt.GridLayout(3, 1));

        panRefCom.setBorder(javax.swing.BorderFactory.createTitledBorder("Referencias comerciales"));
        panRefCom.setLayout(new java.awt.BorderLayout());

        tblDatRefCom.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDatRefCom.setViewportView(tblDatRefCom);

        panRefCom.add(spnDatRefCom, java.awt.BorderLayout.CENTER);

        panGenDet.add(panRefCom);

        panRefBan.setBorder(javax.swing.BorderFactory.createTitledBorder("Referencias bancarias"));
        panRefBan.setLayout(new java.awt.BorderLayout());

        tblDatRefBan.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDatRefBan.setViewportView(tblDatRefBan);

        panRefBan.add(spnDatRefBan, java.awt.BorderLayout.CENTER);

        panGenDet.add(panRefBan);

        panRefBurCre.setBorder(javax.swing.BorderFactory.createTitledBorder("Referencias Buró de crédito"));
        panRefBurCre.setLayout(new java.awt.BorderLayout());

        txaRefBurCre.setLineWrap(true);
        spnRefBurCre.setViewportView(txaRefBurCre);

        panRefBurCre.add(spnRefBurCre, java.awt.BorderLayout.CENTER);

        panGenDet.add(panRefBurCre);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

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
                //Cerrar la conexión si está abierta.
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrTipForPag;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCodSolCre;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblFecSolCre;
    private javax.swing.JLabel lblPro;
    private javax.swing.JLabel lblTipActEmp;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panRefBan;
    private javax.swing.JPanel panRefBurCre;
    private javax.swing.JPanel panRefCom;
    private javax.swing.JScrollPane spnDatRefBan;
    private javax.swing.JScrollPane spnDatRefCom;
    private javax.swing.JScrollPane spnRefBurCre;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatRefBan;
    private javax.swing.JTable tblDatRefCom;
    private javax.swing.JTextArea txaRefBurCre;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodSolCre;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtIdeCli;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomPro;
    private javax.swing.JTextField txtTipActEmp;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(516, 4, 148, 20);
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            objTooBar.setVisibleInsertar(false);
            objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleImprimir(false);
            objTooBar.setVisibleVistaPreliminar(false);
            panBar.add(objTooBar);
            strAux="Resumen de la Solicitud de crédito";
            this.setTitle(strAux + " v0.3");
            lblTit.setText(strAux);
            //Configurar objetos.
//            txtCodSolCre.setBackground(objParSis.getColorCamposSistema());
//            txtNom.setBackground(objParSis.getColorCamposObligatorios());
            //Configurar los JTables.
            configurarTblDatRefCom();
            configurarTblDatRefBan();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblDatRefCom".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatRefCom()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(10);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_RCO_LIN,"");
            vecCab.add(INT_TBL_RCO_COD,"Código");
            vecCab.add(INT_TBL_RCO_NOM,"Nombre");
            vecCab.add(INT_TBL_RCO_TIE,"Tiempo");
            vecCab.add(INT_TBL_RCO_CUP,"Cupo");
            vecCab.add(INT_TBL_RCO_PLA,"Plazo");
            vecCab.add(INT_TBL_RCO_FOR_PAG,"Forma de pago");
            vecCab.add(INT_TBL_RCO_CHK_PRO,"Protestos");
            vecCab.add(INT_TBL_RCO_NUM_PRO,"Núm.Pro.");
            vecCab.add(INT_TBL_RCO_OB1,"Obs.");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModRefCom=new ZafTblMod();
            objTblModRefCom.setHeader(vecCab);
            tblDatRefCom.setModel(objTblModRefCom);
            //Configurar JTable: Establecer tipo de selección.
            tblDatRefCom.setRowSelectionAllowed(true);
            tblDatRefCom.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatRefCom);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatRefCom.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatRefCom.getColumnModel();
            tcmAux.getColumn(INT_TBL_RCO_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RCO_NOM).setPreferredWidth(157);
            tcmAux.getColumn(INT_TBL_RCO_TIE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_RCO_CUP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_RCO_PLA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_RCO_FOR_PAG).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_RCO_CHK_PRO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_RCO_NUM_PRO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_RCO_OB1).setPreferredWidth(60);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatRefCom.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModRefCom.addSystemHiddenColumn(INT_TBL_RCO_COD, tblDatRefCom);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblDatRefCom.getTableHeader().addMouseMotionListener(new ZafMouMotAdaRCo());
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatRefCom);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatRefCom);
            tcmAux.getColumn(INT_TBL_RBA_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_RCO_CHK_PRO).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_RCO_NUM_PRO).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatRefCom);
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
    
    /**
     * Esta función configura el JTable "tblDatRefBan".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatRefBan()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(12);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_RBA_LIN,"");
            vecCab.add(INT_TBL_RBA_COD,"Código");
            vecCab.add(INT_TBL_RBA_NOM,"Nombre");
            vecCab.add(INT_TBL_RBA_FEC_APE,"Apertura");
            vecCab.add(INT_TBL_RBA_SAL,"Saldo");
            vecCab.add(INT_TBL_RBA_CHK_PRO,"Protestos");
            vecCab.add(INT_TBL_RBA_NUM_PRO,"Núm.Pro.");
            vecCab.add(INT_TBL_RBA_CHK_CDI,"Cré.Dir.");
            vecCab.add(INT_TBL_RBA_MON_CDI,"Mon.Cré.Dir.");
            vecCab.add(INT_TBL_RBA_CHK_CIN,"Cré.Ind.");
            vecCab.add(INT_TBL_RBA_MON_CIN,"Mon.Cré.Ind.");
            vecCab.add(INT_TBL_RBA_OB1,"Obs.");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModRefBan=new ZafTblMod();
            objTblModRefBan.setHeader(vecCab);
            tblDatRefBan.setModel(objTblModRefBan);
            //Configurar JTable: Establecer tipo de selección.
            tblDatRefBan.setRowSelectionAllowed(true);
            tblDatRefBan.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatRefBan);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatRefBan.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatRefBan.getColumnModel();
            tcmAux.getColumn(INT_TBL_RBA_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RBA_NOM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_RBA_FEC_APE).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_RBA_SAL).setPreferredWidth(67);
            tcmAux.getColumn(INT_TBL_RBA_CHK_PRO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_RBA_NUM_PRO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_RBA_CHK_CDI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_RBA_MON_CDI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_RBA_CHK_CIN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_RBA_MON_CIN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_RBA_OB1).setPreferredWidth(60);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatRefBan.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModRefBan.addSystemHiddenColumn(INT_TBL_RBA_COD, tblDatRefBan);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblDatRefBan.getTableHeader().addMouseMotionListener(new ZafMouMotAdaRba());
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatRefBan);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatRefBan);
            tcmAux.getColumn(INT_TBL_RBA_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_RBA_CHK_PRO).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_RBA_CHK_CDI).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_RBA_CHK_CIN).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_RBA_NUM_PRO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_RBA_MON_CDI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_RBA_MON_CIN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatRefBan);
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
                if (!rstCab.isFirst())
                {
                    if (blnHayCam || objTblModRefCom.isDataModelChanged() || objTblModRefBan.isDataModelChanged())
                    {
                        if (isRegPro())
                        {
                            rstCab.previous();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.previous();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickAnular()
        {
            
        }

        public void clickCancelar()
        {

        }

        public void clickConsultar()
        {
            switch (objTooBar.getEstado())
            {
                case 'c':
                case 'x':
                case 'y':
                case 'z':
                    txtCodSolCre.requestFocus();
                    break;
                case 'j':
                    break;
            }
            //Inicializar las variables que indican cambios.
            blnHayCam=false;
        }

        public void clickEliminar()
        {
            
        }

        public void clickFin()
        {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam || objTblModRefCom.isDataModelChanged() || objTblModRefBan.isDataModelChanged())
                    {
                        if (isRegPro())
                        {
                            rstCab.last();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.last();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickImprimir()
        {
            
        }

        public void clickInicio()
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam || objTblModRefCom.isDataModelChanged() || objTblModRefBan.isDataModelChanged())
                    {
                        if (isRegPro())
                        {
                            rstCab.first();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.first();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInsertar()
        {
            
        }

        public void clickModificar()
        {
            
        }

        public void clickSiguiente()
        {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam || objTblModRefCom.isDataModelChanged() || objTblModRefBan.isDataModelChanged())
                    {
                        if (isRegPro())
                        {
                            rstCab.next();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.next();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickVisPreliminar() 
        {
        }

        public boolean consultar()
        {
            consultarReg();
            return true;
        }

        public boolean eliminar()
        {
            return true;
        }

        public boolean insertar()
        {
            return true;
        }

        public boolean modificar()
        {
            return true;
        }
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (blnHayCam || objTblModRefCom.isDataModelChanged() || objTblModRefBan.isDataModelChanged())
                {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                    {
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                limpiarFrm();
                //Inicializar las variables que indican cambios.
                objTblModRefCom.setDataModelChanged(false);
                objTblModRefBan.setDataModelChanged(false);
                blnHayCam=false;
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
        
        public boolean beforeInsertar()
        {
            return true;
        }
        
        public boolean beforeConsultar()
        {
            return true;
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
            blnHayCam=false;
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg()
    {
//        int intCodEmp;
        boolean blnRes=true;
        try
        {
//            intCodEmp=objParSis.getCodigoEmpresa();
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_sol";
                strSQL+=" FROM tbm_solCre AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                if (intCodEmp!=null)
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strAux=txtCodSolCre.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_sol LIKE '" + strAux.replaceAll("'", "''") + "'";
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_sol='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtCodCli.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_cli LIKE '" + strAux.replaceAll("'", "''") + "'";
                strAux=txtIdeCli.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a2.tx_ide LIKE '" + strAux.replaceAll("'", "''") + "'";
                strAux=txtDesLarCli.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND LOWER(a2.tx_nom) LIKE '" + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "'";
                strSQL+=" ORDER BY a1.co_emp, a1.co_sol";
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                    strSQLCon=strSQL;
                }
                else
                {
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
        boolean blnRes=true;
        try
        {
            if (cargarCabReg())
            {
                cargarDetReg();
            }
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
                strSQL+="SELECT a1.co_emp, a2.tx_nom, a1.co_sol, a1.co_cli, a3.tx_ide, a3.tx_nom AS a3_tx_nom, a1.fe_sol, a3.tx_nomPro, a3.tx_tipActEmp, a3.tx_obsInfBurCre, a1.st_reg";
                strSQL+=" FROM tbm_solCre AS a1";
                strSQL+=" INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp)";
                strSQL+=" INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_sol=" + rstCab.getString("co_sol");
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strAux=rst.getString("tx_nom");
                    txtNomEmp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_sol");
                    txtCodSolCre.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_cli");
                    txtCodCli.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_ide");
                    txtIdeCli.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("a3_tx_nom");
                    txtDesLarCli.setText((strAux==null)?"":strAux);
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_sol"),"dd/MM/yyyy"));
                    strAux=rst.getString("tx_nomPro");
                    txtNomPro.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_tipActEmp");
                    txtTipActEmp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obsInfBurCre");
                    txaRefBurCre.setText((strAux==null)?"":strAux);
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
            intPosRel=rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        try
        {
//            objTooBar.setMenSis("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Listado de "Referencias comerciales".
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_ref, a1.tx_nom, a1.tx_tie, a1.tx_cupCre, a1.tx_plaCre, a1.tx_forPag, a1.st_pro, a1.ne_numPro, a1.tx_obs1";
                strSQL+=" FROM tbm_refComSolCre AS a1";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_sol=" + rstCab.getString("co_sol");
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.co_emp, a1.co_sol, a1.co_ref";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
//                objTooBar.setMenSis("Cargando datos...");
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_RCO_LIN,"");
                    vecReg.add(INT_TBL_RCO_COD,rst.getString("co_ref"));
                    vecReg.add(INT_TBL_RCO_NOM,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_RCO_TIE,rst.getString("tx_tie"));
                    vecReg.add(INT_TBL_RCO_CUP,rst.getString("tx_cupCre"));
                    vecReg.add(INT_TBL_RCO_PLA,rst.getString("tx_plaCre"));
                    vecReg.add(INT_TBL_RCO_FOR_PAG,rst.getString("tx_forPag"));
                    if (rst.getString("st_pro").equals("S"))
                        vecReg.add(INT_TBL_RCO_CHK_PRO,new Boolean(true));
                    else
                        vecReg.add(INT_TBL_RCO_CHK_PRO,null);
                    vecReg.add(INT_TBL_RCO_NUM_PRO,rst.getString("ne_numPro"));
                    vecReg.add(INT_TBL_RCO_OB1,rst.getString("tx_obs1"));
                    vecDat.add(vecReg);
                }
                rst.close();
                //Asignar vectores al modelo.
                objTblModRefCom.setData(vecDat);
                tblDatRefCom.setModel(objTblModRefCom);
                //Listado de "Referencias bancarias".
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_ref, a1.tx_nom, a1.fe_apeCta, a1.tx_salProCta, a1.st_pro, a1.ne_numPro, a1.st_creDir, a1.tx_monCreDir, a1.st_creInd, a1.tx_monCreInd, a1.tx_obs1";
                strSQL+=" FROM tbm_refBanSolCre AS a1";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_sol=" + rstCab.getString("co_sol");
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.co_emp, a1.co_sol, a1.co_ref";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
//                objTooBar.setMenSis("Cargando datos...");
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_RBA_LIN,"");
                    vecReg.add(INT_TBL_RBA_COD,rst.getString("co_ref"));
                    vecReg.add(INT_TBL_RBA_NOM,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_RBA_FEC_APE,rst.getString("fe_apeCta"));
                    vecReg.add(INT_TBL_RBA_SAL,rst.getString("tx_salProCta"));
                    if (rst.getString("st_pro").equals("S"))
                        vecReg.add(INT_TBL_RBA_CHK_PRO,new Boolean(true));
                    else
                        vecReg.add(INT_TBL_RBA_CHK_PRO,null);
                    vecReg.add(INT_TBL_RBA_NUM_PRO,rst.getString("ne_numPro"));
                    if (rst.getString("st_creDir").equals("S"))
                        vecReg.add(INT_TBL_RBA_CHK_CDI,new Boolean(true));
                    else
                        vecReg.add(INT_TBL_RBA_CHK_CDI,null);
                    vecReg.add(INT_TBL_RBA_MON_CDI,rst.getString("tx_monCreDir"));
                    if (rst.getString("st_creInd").equals("S"))
                        vecReg.add(INT_TBL_RBA_CHK_CIN,new Boolean(true));
                    else
                        vecReg.add(INT_TBL_RBA_CHK_CIN,null);
                    vecReg.add(INT_TBL_RBA_MON_CIN,rst.getString("tx_monCreInd"));
                    vecReg.add(INT_TBL_RBA_OB1,rst.getString("tx_obs1"));
                    vecDat.add(vecReg);
                }
                rst.close();
                //Asignar vectores al modelo.
                objTblModRefBan.setData(vecDat);
                tblDatRefBan.setModel(objTblModRefBan);
                vecDat.clear();
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
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtCodSolCre.setText("");
            dtpFecDoc.setText("");
            txtCodCli.setText("");
            txtIdeCli.setText("");
            txtDesLarCli.setText("");
            objTblModRefCom.removeAllRows();
            objTblModRefBan.removeAllRows();
            txaRefBurCre.setText("");
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
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
                        blnRes=objTooBar.beforeInsertar();
                        if (blnRes)
                            blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
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
     * Esta función determina si el objeto "JInternalFrame" ya fue creado.
     * Es decir que esta función devuelve true sólo cuando "this" es diferente a null.
     * @return true: Si el objeto "JInternalFrame" ya fue creado.
     * <BR>false: En el caso contrario.
     */
    public synchronized boolean isObjIfrCre()
    {
        if (this==null)
            return false;
        else
            return true;
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
        private int intIndFun;

        public ZafThreadGUI()
        {
            
        }

        public void run()
        {
            switch (intIndFun)
            {
                case -1:
                    while (true)
                    {
                        try
                        {
                            sleep(50);
                        }
                        catch (InterruptedException e)
                        {
                            System.out.println("Excepción: " + e.toString());
                        }
                        if (isObjIfrCre())
                        {
                            configurarFrm();
                            if (intCodCli!=null)
                            {
                                //Cargar los datos para el cliente especificado.
                                txtCodCli.setText(intCodCli.toString());
                                objTooBar.setEstado('c');
                                objTooBar.consultar();
                                objTooBar.setEstado('w');
                            }
                            else if (strIdeCli!=null)
                            {
                                //Cargar los datos para el cliente especificado.
                                txtIdeCli.setText(strIdeCli);
                                objTooBar.setEstado('c');
                                objTooBar.consultar();
                                objTooBar.setEstado('w');
                            }
                            break;
                        }
                    }
                    break;
            }
            objThrGUI=null;
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
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaRCo extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDatRefCom.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_RCO_NOM:
                    strMsg="Nombre de la referencia comercial";
                    break;
                case INT_TBL_RCO_TIE:
                    strMsg="Tiempo de ser cliente con la referencia comerial";
                    break;
                case INT_TBL_RCO_CUP:
                    strMsg="Cupo concedido por la referencia comercial";
                    break;
                case INT_TBL_RCO_PLA:
                    strMsg="Plazo concedido por la referencia comercial";
                    break;
                case INT_TBL_RCO_FOR_PAG:
                    strMsg="Forma de pago concedida por la referencia comercial";
                    break;
                case INT_TBL_RCO_CHK_PRO:
                    strMsg="¿Tiene protestos con la referencia comercial?";
                    break;
                case INT_TBL_RCO_NUM_PRO:
                    strMsg="Número de protestos con la referencia comercial";
                    break;
                case INT_TBL_RCO_OB1:
                    strMsg="Observación1";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDatRefCom.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaRba extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDatRefBan.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_RBA_NOM:
                    strMsg="Nombre de la referencia bancaria";
                    break;
                case INT_TBL_RBA_FEC_APE:
                    strMsg="Fecha de apertura de la cuenta";
                    break;
                case INT_TBL_RBA_SAL:
                    strMsg="Saldo promedio de la cuenta";
                    break;
                case INT_TBL_RBA_CHK_PRO:
                    strMsg="¿Tiene protestos con la referencia bancaria?";
                    break;
                case INT_TBL_RBA_NUM_PRO:
                    strMsg="Número de protestos con la referencia bancaria";
                    break;
                case INT_TBL_RBA_CHK_CDI:
                    strMsg="¿Tiene créditos directos con la referencia bancaria?";
                    break;
                case INT_TBL_RBA_MON_CDI:
                    strMsg="Monto de créditos directos con la referencia bancaria";
                    break;
                case INT_TBL_RBA_CHK_CIN:
                    strMsg="¿Tiene créditos indirectos con la referencia bancaria?";
                    break;
                case INT_TBL_RBA_MON_CIN:
                    strMsg="Monto de créditos indirectos con la referencia bancaria";
                    break;
                case INT_TBL_RBA_OB1:
                    strMsg="Observación1";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDatRefBan.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}
/*
 * ZafCxC78.java
 *
 * Created on 14 de abril de 2008, 12:09 PM
 */
package CxC.ZafCxC78; 
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  JoseMario
 */
public class ZafCxC78 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_LOC_LIN=0;                         //Línea.
    static final int INT_TBL_LOC_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_LOC_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_LOC_NOM_EMP=3;                     //Nombre de la empresa.
    static final int INT_TBL_LOC_COD_LOC=4;                     //Código del local.
    static final int INT_TBL_LOC_NOM_LOC=5;                     //Nombre del local.

    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_EMP=1;                     //Código de la empresa.
    static final int INT_TBL_DAT_COD_LOC=2;                     //Código del local.
    static final int INT_TBL_DAT_NOM_LOC=3;                    //Nombre del cliente.
    static final int INT_TBL_DAT_NEGOCIACION=4;                    //Nombre del cliente.
    static final int INT_TBL_DAT_CONTADO=5;                    //Nombre del cliente.
    static final int INT_TBL_DAT_CHEQUE=6;                    //Nombre del cliente.
    static final int INT_TBL_DAT_CREDITO=7;                    //Valor del documento.
    static final int INT_TBL_DAT_TARJETA=8;                    //Valor del documento.
    static final int INT_TBL_DAT_TOTAL=9;                    //Valor del documento.
    static final int INT_TBL_DAT_BUT_DOC=10;                    //Botón del documento.
    
     
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblModLoc;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelEdiButGen objTblCelEdiButGen;              //Editor: JButton en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenCon vcoCli;                                   //Ventana de consulta.
    private ZafVenCon vcoForPag;                                //Ventana de consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux, strSql;
    private String strFecDoc;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private Vector vecEstReg;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodCli, strIdeCli, strDesLarCli;          //Contenido del campo al obtener el foco.
    private String strCodForPag, strForPag;                     //Contenido del campo al obtener el foco.
    private String strVersion=" v0.01";
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    //Variables de la clase.
    private int intCodEmp;                                      //Código de la empresa.
    private int intCodLoc;                                      //Código del local.
    private int intCodTipDoc;                                   //Código del tipo de documento.
    private int intCodDoc;                                      //Código del docuemnto.
    private int intCodAut;                                      //Código de la autorización.
    
    private ZafTblOrd  objTblOrdLoc;                  //JTable de ordenamiento.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCxC78(ZafParSis obj) 
    {
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
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
        panLoc = new javax.swing.JPanel();
        chkMosLocIna = new javax.swing.JCheckBox();
        spnLoc = new javax.swing.JScrollPane();
        tblLoc = new javax.swing.JTable();
        panFec = new javax.swing.JPanel();
        panRpt = new javax.swing.JPanel();
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

        panFil.setLayout(new java.awt.BorderLayout());

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Locales"));
        panLoc.setPreferredSize(new java.awt.Dimension(10, 92));
        panLoc.setLayout(new java.awt.BorderLayout());

        chkMosLocIna.setText("Mostrar locales inactivos");
        chkMosLocIna.setPreferredSize(new java.awt.Dimension(145, 14));
        chkMosLocIna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosLocInaActionPerformed(evt);
            }
        });
        panLoc.add(chkMosLocIna, java.awt.BorderLayout.SOUTH);

        tblLoc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnLoc.setViewportView(tblLoc);

        panLoc.add(spnLoc, java.awt.BorderLayout.CENTER);

        panFil.add(panLoc, java.awt.BorderLayout.NORTH);

        panFec.setLayout(null);
        panFil.add(panFec, java.awt.BorderLayout.CENTER);

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
        tblDat.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
        tblDat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDatKeyPressed(evt);
            }
        });
        tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMouseClicked(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(452, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
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
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        //Abrir la opción seleccionada al presionar ENTER.
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            abrirFrm();
            evt.setKeyCode(0);
        }
    }//GEN-LAST:event_tblDatKeyPressed

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

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
        //Abrir la opción seleccionada al dar doble click.
        if (evt.getClickCount()==2)
        {
            abrirFrm();
        }
    }//GEN-LAST:event_tblDatMouseClicked

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

    private void chkMosLocInaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosLocInaActionPerformed
        cargarLoc();
    }//GEN-LAST:event_chkMosLocInaActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JCheckBox chkMosLocIna;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFec;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblLoc;
    private javax.swing.JTable tblTot;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            panFec.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            //Configurar objetos. 
            //Configurar el combo "Estado de registro".
             
            
            
            //Configurar los JTables.
            configurarTblDat();
        
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                configurarTblLoc();
                cargarLoc();
            }
            else
            {
                panLoc.setVisible(false);
                 
            }
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

     
    
    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_NOM_LOC,"Local");
            vecCab.add(INT_TBL_DAT_NEGOCIACION,"Negociación");
            vecCab.add(INT_TBL_DAT_CONTADO,"Contado");
            vecCab.add(INT_TBL_DAT_CHEQUE,"Cheque");
            vecCab.add(INT_TBL_DAT_CREDITO,"Crédito");
            vecCab.add(INT_TBL_DAT_TARJETA,"Tar.Cre.");
            vecCab.add(INT_TBL_DAT_TOTAL,"Total");
            vecCab.add(INT_TBL_DAT_BUT_DOC,"");           
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_LOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_NEGOCIACION).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CONTADO).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CHEQUE).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CREDITO).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_TARJETA).setPreferredWidth(60);  
            tcmAux.getColumn(INT_TBL_DAT_TOTAL).setPreferredWidth(60);  
            
            tcmAux.getColumn(INT_TBL_DAT_BUT_DOC).setPreferredWidth(20);
            
            
           
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
 
             
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_DOC);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_NEGOCIACION).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CONTADO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CHEQUE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CREDITO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TARJETA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TOTAL).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_DOC).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_DOC).setCellEditor(objTblCelEdiButGen);            
//            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    abrirFrm();
//                }
//            });
            objTblCelEdiButGen=null;
            
            ButDoc objButDoc = new ButDoc(tblDat, INT_TBL_DAT_BUT_DOC); //*****
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            int intCol[]={INT_TBL_DAT_NEGOCIACION, INT_TBL_DAT_CONTADO, INT_TBL_DAT_CHEQUE, INT_TBL_DAT_CREDITO, INT_TBL_DAT_TARJETA, INT_TBL_DAT_TOTAL};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
             
            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
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
    private ZafTblTot objTblTot;                                //JTable de totales.
     
    
     
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int i;
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strConSQL="";
                //Obtener los locales a consultar.
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    int intNumFilTblBod=objTblModLoc.getRowCountTrue();
                    i=0;
                    strAux="";
                    for (int j=0; j<intNumFilTblBod; j++)
                    {
                        if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHK))
                        {
                            if (i==0)
                                strAux+=" (a1.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP) + " AND a1.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                            else
                                strAux+=" OR (a1.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP) + " AND a1.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                            i++;
                        }
                    }
                    if (!strAux.equals(""))
                    {
                        strConSQL+=" AND (" + strAux + ")";
                    }
                }
                strFecDoc="";
                switch (objSelFec.getTipoSeleccion())
                {
                    case 0: //Búsqueda por rangos
                        strFecDoc+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strFecDoc+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strFecDoc+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }
//                strFecDoc=strConSQL;
                 
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    //Obtener los datos del "Grupo".
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.tx_nom \n";
                    strSQL+=" FROM tbm_loc as a1 \n";
                    strSQL+=" WHERE 1=1 "+ strConSQL+" \n";
                    strSQL+=" ORDER BY a1.co_emp, a1.co_loc \n";
                }
                else{
                    //Obtener los datos de la "Empresa seleccionada".
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.tx_nom \n";
                    strSQL+=" FROM tbm_loc as a1 \n";
                    strSQL+=" INNER JOIN tbr_locPrg as a2 ON (a1.co_emp=a2.co_empRel AND a1.co_loc=a2.co_locRel) ";
                    strSQL+=" WHERE a2.co_emp="+objParSis.getCodigoEmpresa()+" AND a2.co_loc="+objParSis.getCodigoLocal()+" AND  \n";
                    strSQL+="       a2.co_mnu="+objParSis.getCodigoMenu()+" AND a2.st_reg IN ('A','P')  ";
                    strSQL+=" ORDER BY a1.co_emp, a1.co_loc  \n";
                }
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()){
                    if (blnCon){
                        if(getValores(con, rst.getInt("co_emp"), rst.getInt("co_loc"))){
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
                            vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                            vecReg.add(INT_TBL_DAT_NOM_LOC,rst.getString("tx_nom"));
                            vecReg.add(INT_TBL_DAT_NEGOCIACION, dblNegociacion);
                            vecReg.add(INT_TBL_DAT_CONTADO,dblContado);
                            vecReg.add(INT_TBL_DAT_CHEQUE,dblCheque);
                            vecReg.add(INT_TBL_DAT_CREDITO,dblCredito);
                            vecReg.add(INT_TBL_DAT_TARJETA,dblTarjeta);
                            vecReg.add(INT_TBL_DAT_TOTAL,dblTotal);
                            vecReg.add(INT_TBL_DAT_BUT_DOC,null);
                            vecDat.add(vecReg);
                        }
                        
                    }
                    else{
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
                objTblTot.calcularTotales(); 
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
     * 0=Negociación;1=Contado;2=Cheque;3=Crédito;4=Tarjeta de crédito 
     * QUERY PARA OBTENER EL VALOR PAGADO CON "24=INCA; 183=INCA2; 18=DEPO; 39=DEPO2; 78=DEPOC; 79=DEPOD; 174=CVDCDB; 25=RETE; 
     *		26=RFIV30; 27=RFIV70; 73=RETE2%; 84=RFIV100; 195=COBTCM; 216=COBTCD; 247=RFIV10; 248=RFIV20".
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @return 
     */ 
    
    private double dblNegociacion=0, dblContado=0, dblCheque=0, dblCredito=0, dblTarjeta=0, dblTotal=0;
    
    private boolean getValores(java.sql.Connection conExt, int CodEmp, int CodLoc){
        boolean blnRes=false;  
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                dblNegociacion=0; dblContado=0; dblCheque=0; dblCredito=0; dblTarjeta=0;dblTotal=0;
                stmLoc=conExt.createStatement();
                strSql="";
                strSql+=" SELECT b4.ne_tipForPag, SUM(b3.nd_aboModCxC) as nd_aboModCxC \n";
                strSql+=" FROM ( \n";
                strSql+="       SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_cli, a1.co_forPag \n";
                strSql+="       FROM tbm_cabMovInv AS a1 \n";
                strSql+="       INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND \n";
                strSql+="                                          a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  \n";
                strSql+="       INNER JOIN tbm_inv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                strSql+="       WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_tipDoc IN (1, 228) AND a1.fe_doc>='2012/07/01'  AND  \n";
                strSql+="             a1.st_reg IN ('A','R','C','F') AND a3.st_ser<>'O'   ";
                strSql+="       GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_cli, a1.co_forPag \n";
                strSql+=" ) AS b1  \n";
                strSql+=" INNER JOIN tbm_pagMovInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND \n";
                strSql+="                                    b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)  \n";
                strSql+=" INNER JOIN ( \n";
                strSql+="       SELECT a1.co_emp, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag, SUM(a2.nd_abo) AS nd_aboModCxC \n";
                strSql+="       FROM tbm_cabPag AS a1 \n";
                strSql+="       INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND \n";
                strSql+="                                       a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  \n";
                strSql+="       INNER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND  \n";
                strSql+="                                          a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc) \n";
                strSql+="       WHERE a1.co_emp="+CodEmp+" AND a1.co_tipDoc IN (24, 183, 287, 18, 39, 78, 79, 174, 25, 26, 27, 73, 84, 247, 248, 195, 216) AND a1.st_reg='A' AND  \n";
                strSql+="             a2.st_reg='A' AND a3.st_reg IN ('A','R','C','F') "+strFecDoc+" \n";
                strSql+="       GROUP BY a1.co_emp, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag \n";
                strSql+=" ) AS b3 ON (b2.co_emp=b3.co_emp AND b2.co_loc=b3.co_locPag AND b2.co_tipDoc=b3.co_tipDocPag AND \n";
                strSql+="             b2.co_doc=b3.co_docPag AND b2.co_reg=b3.co_regPag) \n";
                strSql+=" INNER JOIN tbm_cabForPag AS b4 ON (b1.co_emp=b4.co_emp AND b1.co_forPag=b4.co_forPag) \n";
                strSql+=" INNER JOIN tbm_cli AS b5 ON (b1.co_emp=b5.co_emp AND b1.co_cli=b5.co_cli) \n";
                strSql+=" WHERE b2.st_reg IN ('A','C') AND b5.co_empGrp IS NULL  AND  \n";
                strSql+="       ((b5.co_emp=1 AND b5.co_cli<>3938) OR (b5.co_emp=2 AND b5.co_cli<>3080) OR (b5.co_emp=4 AND b5.co_cli<>3370))    \n";
                strSql+=" GROUP BY b4.ne_tipForPag \n";
                strSql+=" ORDER BY b4.ne_tipForPag \n";
                System.out.println("getValor: \n" + strSql); 
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    switch(rstLoc.getInt("ne_tipForPag")){
                        case 0: dblNegociacion=rstLoc.getDouble("nd_aboModCxC"); break;
                        case 1: dblContado=rstLoc.getDouble("nd_aboModCxC"); break;
                        case 2: dblCheque=rstLoc.getDouble("nd_aboModCxC"); break;
                        case 3: dblCredito=rstLoc.getDouble("nd_aboModCxC"); break;
                        case 4: dblTarjeta=rstLoc.getDouble("nd_aboModCxC"); break;
                    }
                }
                dblTotal=dblNegociacion+dblContado+dblCheque+dblCredito+dblTarjeta;
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
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
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    private boolean abrirFrm()
    {
        boolean blnRes=true;
        try
        {
            if (tblDat.getSelectedColumn()==INT_TBL_DAT_BUT_DOC)
            {
                invocarClase("CxC.ZafCxC78.ZafCxC78_01");
            }       
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean invocarClase(String clase)
    {
        int intFilSel;
        boolean blnRes=true;
        try
        {
            //Obtener el constructor de la clase que se va a invocar.
            Class objVen=Class.forName(clase);
            Class objCla[]=new Class[4];
            objCla[0]=objParSis.getClass();
            objCla[1]=new Integer(0).getClass();
            objCla[2]=new Integer(0).getClass();
            objCla[3]=new String().getClass() ;
            java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
            intFilSel=tblDat.getSelectedRow();
            //Inicializar el constructor que se obtuvo.
            Object objObj[]=new Object[4];
            objObj[0]=objParSis;
            objObj[1]=new Integer(objTblMod.getValueAt(intFilSel,INT_TBL_DAT_COD_EMP).toString());
            objObj[2]=new Integer(objTblMod.getValueAt(intFilSel,INT_TBL_DAT_COD_LOC).toString());
            objObj[3]=strFecDoc;
            javax.swing.JInternalFrame ifrVen;
            ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
            this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
            ifrVen.show();
        }
        catch (ClassNotFoundException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (NoSuchMethodException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (SecurityException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (InstantiationException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalAccessException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalArgumentException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (java.lang.reflect.InvocationTargetException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
     
    
    private class ButDoc extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButDoc(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Factura de Venta.");
           
        }
        @Override
        public void butCLick() {
            int intCol = tblDat.getSelectedRow();
            String strCodEmp = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_EMP  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_EMP  ).toString());
            String strCodLoc = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_LOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_LOC  ).toString());
            String strFec = strFecDoc;
          // OBTENER FACTURAS SEGUN LA COTIZACION PUEDE TENER MUCHAS FACTURAS
           llamarDoc(strCodEmp, strCodLoc, strFec);
        }
    }
    
    private void llamarDoc(String strCodEmp, String strCodLoc, String strCodCot){
        try{
            ZafCxC78_01 obj1 = new  ZafCxC78_01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis,Integer.parseInt(strCodEmp),Integer.parseInt(strCodLoc) ,strCodCot);
        //    this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj1.show();
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
      
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblLocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModLoc.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblLoc.columnAtPoint(evt.getPoint())==INT_TBL_LOC_CHK)
            {
                if (blnMarTodChkTblEmp)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(true, i, INT_TBL_LOC_CHK);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(false, i, INT_TBL_LOC_CHK);
                    }
                    blnMarTodChkTblEmp=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
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
    private class ZafThreadGUI extends Thread
    {
        public void run()
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
            objThrGUI=null;
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
                case INT_TBL_DAT_NOM_LOC:
                    strMsg="Nombre del Local";
                    break;
                case INT_TBL_DAT_NEGOCIACION:
                    strMsg="Forma de pago: Negociación";
                    break;
                case INT_TBL_DAT_CONTADO:
                    strMsg="Forma de pago: Contado";
                    break;
                case INT_TBL_DAT_CHEQUE:
                    strMsg="Forma de pago: Cheque";
                    break;
                case INT_TBL_DAT_CREDITO:
                    strMsg="Forma de pago: Crédito";
                    break;
                case INT_TBL_DAT_TARJETA:
                    strMsg="Forma de pago: Tarjeta de crédito";
                    break;    
                case INT_TBL_DAT_TOTAL:
                    strMsg="Total";
                    break;       
                case INT_TBL_DAT_BUT_DOC:
                    strMsg="Muestra el Listado de valores cobrados por cliente y forma de pago";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
     
    
    
    /**
     * Esta función configura el JTable "tblLoc".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblLoc()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LOC_LIN,"");
            vecCab.add(INT_TBL_LOC_CHK,"");
            vecCab.add(INT_TBL_LOC_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_LOC_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_LOC_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_LOC_NOM_LOC,"Local");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModLoc=new ZafTblMod();
            objTblModLoc.setHeader(vecCab);
            tblLoc.setModel(objTblModLoc);
            //Configurar JTable: Establecer tipo de selección.
            tblLoc.setRowSelectionAllowed(true);
            tblLoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblLoc);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblLoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblLoc.getColumnModel();
            tcmAux.getColumn(INT_TBL_LOC_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_LOC_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_LOC_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOM_EMP).setPreferredWidth(221);
            tcmAux.getColumn(INT_TBL_LOC_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOM_LOC).setPreferredWidth(221);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_LOC_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblLoc.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblModLoc.addSystemHiddenColumn(INT_TBL_LOC_COD_EMP, tblLoc);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblLoc.getTableHeader().addMouseMotionListener(new ZafMouMotAdaLoc());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblLoc.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblLocMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_LOC_CHK);
            objTblModLoc.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblLoc);
            tcmAux.getColumn(INT_TBL_LOC_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_LOC_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_LOC_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_LOC_COD_LOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblLoc);
            tcmAux.getColumn(INT_TBL_LOC_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiChk.isChecked())
                    {
//                        objTblMod.setValueAt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_PEN), tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    }
                    else
                    {
//                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    }
                }
            });

            //Ordenar tabla
            objTblOrdLoc=new ZafTblOrd(tblLoc);
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblLoc.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModLoc.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * Esta función permite consultar los locales de acuerdo al siguiente criterio:
     * <UL>
     * <LI>Si se ingresa a la empresa "Grupo" se muestran todos los locales.
     * <LI>Si se ingresa a cualquier otra empresa se muestran sólo los locales pertenecientes a la empresa seleccionada.
     * </UL>
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarLoc()
    {
         
        boolean blnRes=true;
        try
        {
            
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los locales.
                if (objParSis.getCodigoUsuario()==1){
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom AS tx_nomLoc \n";
                    strSQL+=" FROM tbm_emp AS a1 \n";
                    strSQL+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp) \n";
                    strSQL+=" INNER JOIN tbr_locPrg as a3 ON (a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel) \n";
                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){ 
                        strSQL+=" WHERE a3.co_emp="+objParSis.getCodigoEmpresa()+" AND a3.co_loc="+objParSis.getCodigoLocal()+" AND  ";
                        strSQL+="       a3.co_mnu="+objParSis.getCodigoMenu()+" AND a3.st_reg IN ('A','P')  \n";
                        if (!chkMosLocIna.isSelected())
                            strSQL+=" AND a2.st_reg IN ('A', 'P') \n";
                    }
                    if (!chkMosLocIna.isSelected())
                        strSQL+=" AND a2.st_reg IN ('A', 'P') \n";
                    strSQL+=" GROUP BY a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom  \n";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc \n";
                }
                else{
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom AS tx_nomLoc \n";
                    strSQL+=" FROM tbm_emp AS a1 \n";
                    strSQL+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp) \n";
                    strSQL+=" INNER JOIN tbr_locPrg as a3 ON (a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel) \n";
                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){ 
                        strSQL+=" WHERE a3.co_emp="+objParSis.getCodigoEmpresa()+" AND a3.co_loc="+objParSis.getCodigoLocal()+" AND  ";
                        strSQL+="       a3.co_mnu="+objParSis.getCodigoMenu()+" AND a3.st_reg IN ('A','P')  \n";
                        if (!chkMosLocIna.isSelected())
                            strSQL+=" AND a2.st_reg IN ('A', 'P') \n";
                    }
                    if (!chkMosLocIna.isSelected())
                        strSQL+=" AND a2.st_reg IN ('A', 'P') \n";
                    strSQL+=" GROUP BY a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom  \n";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc \n";
                }
                System.out.println("locales " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LOC_LIN,"");
                    vecReg.add(INT_TBL_LOC_CHK,Boolean.TRUE);
                    vecReg.add(INT_TBL_LOC_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_LOC_NOM_EMP,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_LOC_COD_LOC,rst.getString("co_loc"));
                    vecReg.add(INT_TBL_LOC_NOM_LOC,rst.getString("tx_nomLoc"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModLoc.setData(vecDat);
                tblLoc.setModel(objTblModLoc);
                vecDat.clear();
                blnMarTodChkTblEmp=false;
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
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaLoc extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblLoc.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_LOC_LIN:
                    strMsg="";
                    break;
                case INT_TBL_LOC_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_LOC_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_LOC_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_LOC_NOM_LOC:
                    strMsg="Nombre del local";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblLoc.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}

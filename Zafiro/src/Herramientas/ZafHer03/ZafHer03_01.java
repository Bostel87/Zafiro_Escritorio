/*
 * ZafHer03_01.java
 *
 * Created on 5 de marzo de 2006, 07:35 PM
 */

package Herramientas.ZafHer03;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;
import java.math.BigDecimal;

/**
 *
 * @author  Eddye Lino
 */
public class ZafHer03_01 extends javax.swing.JDialog
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_REG=1;                     //Código del registro.
    static final int INT_TBL_DAT_COD_CON=2;                     //Código del control.
    static final int INT_TBL_DAT_DEC_CON=3;                     //Descripción corta del control.
    static final int INT_TBL_DAT_DEL_CON=4;                     //Descripción larga del control.
    static final int INT_TBL_DAT_BUT_MOT=5;                     //Botón: Explicación.
    static final int INT_TBL_DAT_CHK_AUT=6;                     //Casilla: Autorizar.
    static final int INT_TBL_DAT_CHK_DEN=7;                     //Casilla: Denegar.
    static final int INT_TBL_DAT_OBS_AUT=8;                     //Observación del que autoriza.
    static final int INT_TBL_DAT_PER_AUT=9;                     //Permiso de autorización (S=Si; N=No).
    static final int INT_TBL_DAT_PAR1=10;                       //Parámetro 1.
    static final int INT_TBL_DAT_PAR2=11;                       //Parámetro 2.
    static final int INT_TBL_DAT_CLA_MOT=12;                    //Clase que muestra el motivo por el que no se cumplió el control.
    static final int INT_TBL_DAT_FUN_VER=13;                    //Función que verifica si el usuario puede autorizar/denegar el control.
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChkAut;                 //Editor: JCheckBox en celda.
    private ZafTblCelEdiChk objTblCelEdiChkDen;                 //Editor: JCheckBox en celda.
    private ZafTblCelEdiButGen objTblCelEdiButGen;              //Editor: JButton en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private java.util.Date datFecAux;                           //Auxiliar: Para almacenar fechas.
    private ArrayList arlParDlg;                                //Parametros a pasar al JDialog que se llama desde el JTable.
    private CxC.ZafCxC23.ZafCxC23_04 objCxC23_04;
    //Variables de la clase.
    private int intOpcSelDlg;                                   //Opción seleccionada en el JDialog.
    private int intCodEmp;                                      //Código de la empresa.
    private int intCodLoc;                                      //Código del local.
    private int intCodTipDoc;                                   //Código del tipo de documento.
    private int intCodDoc;                                      //Código del docuemnto.
    private int intCodAut;                                      //Código de la autorización.
    private int intCodCli;                                      //Código del cliente.
    private String strIdeCli;                                   //Identificación del cliente.
    private String strEstDoc;                                   //Estado del documento.
    private String strEstValDoc;                                //Estado de validez del documento.
    
    /**
     * Este constructor crea una nueva instancia de la clase ZafHer03_01.
     * @param parent El formulario padre
     * @param modal true: Modal
     * <BR>false: No modal.
     * @param obj El objeto ZafParSis.
     */
    public ZafHer03_01(java.awt.Frame parent, boolean modal, ZafParSis obj)
    {
        super(parent, modal);
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
        objCxC23_04=new CxC.ZafCxC23.ZafCxC23_04(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, true);
        intOpcSelDlg=0;
        if (!configurarFrm())
            exitForm();
    }
    
    /**
     * Este constructor crea una nueva instancia de la clase ZafHer03_01.
     * @param parent El formulario padre
     * @param modal true: Modal
     * <BR>false: No modal.
     * @param obj El objeto ZafParSis.
     * @param codEmp Código de la empresa.
     * @param codLoc Código del local.
     * @param codCot Código de la cotización de ventas.
     */
    public ZafHer03_01(java.awt.Frame parent, boolean modal, ZafParSis obj, int codEmp, int codLoc, int codCot)
    {
        super(parent, modal);
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
        objCxC23_04=new CxC.ZafCxC23.ZafCxC23_04(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, true);
        intOpcSelDlg=0;
        if (!configurarFrm())
            exitForm();
        intCodEmp=codEmp;
        intCodLoc=codLoc;
        intCodDoc=codCot;
        cargarReg();
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
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenTot = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butObsCxC = new javax.swing.JButton();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenDet.setLayout(new java.awt.BorderLayout());

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

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        panGenTot.setPreferredSize(new java.awt.Dimension(34, 70));
        panGenTot.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setToolTipText("Observación del solicitante");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setToolTipText("Observación del que autoriza");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panGenTot.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setPreferredSize(new java.awt.Dimension(106, 48));
        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panGenTot.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panGen.add(panGenTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butObsCxC.setText("Observación CxC");
        butObsCxC.setToolTipText("Observación CxC");
        butObsCxC.setPreferredSize(new java.awt.Dimension(92, 25));
        butObsCxC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butObsCxCActionPerformed(evt);
            }
        });
        panBot.add(butObsCxC);

        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBot.add(butAce);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Cierra el cuadro de dialogo");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(600, 400));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    public boolean GuardoOk()
    {
        boolean blnRes=false;
        try
        {
            if (revisarEstadoCotizacionAutorizada())
            {
                blnRes=true;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private String strSql;
     
    private boolean revisarEstadoCotizacionAutorizada()
    {
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        boolean blnRes=false;
        try
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null)
            {
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql=" SELECT st_reg FROM tbm_cabCotVen WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_cot="+intCodDoc;
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                {
                    if(rstLoc.getString("st_reg").equals("U"))
                    {
                        blnRes=true;
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
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
    
    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        if (isCamVal())
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                ZafHer03_00 objHer03_00=new ZafHer03_00(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, 2);
                objHer03_00.setVisible(true);
                if (objHer03_00.getOpcSelDlg()==ZafHer03_00.INT_OPC_ACE)
                {
                    if (objHer03_00.isUsrVal())
                    {
                        if (objThrGUI==null)
                        {
                            objThrGUI=new ZafThreadGUI();
                            objThrGUI.setIndFunEje(0);
                            objThrGUI.start();
                        }
                    }
                    else
                    {
                        dispose();
                    }
                }
            }
        }
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        intOpcSelDlg=0;
        dispose();
    }//GEN-LAST:event_butCanActionPerformed

private void butObsCxCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butObsCxCActionPerformed
    if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
    {
        objCxC23_04.setParDlg(intCodEmp, intCodLoc, strIdeCli);
    }
    else
    {
        objCxC23_04.setParDlg(intCodEmp, intCodLoc, intCodCli);
    }
    objCxC23_04.cargarReg();
    objCxC23_04.setVisible(true);
}//GEN-LAST:event_butObsCxCActionPerformed
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        intOpcSelDlg=0;
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butObsCxC;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenTot;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    // End of variables declaration//GEN-END:variables
 
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux="Controles a autorizar/denegar";
            this.setTitle(strAux + " v0.14");
            lblTit.setText(strAux);
            arlParDlg=new ArrayList();
            //Configurar objetos.
            txaObs1.setBackground(objParSis.getColorCamposSistema());
            txaObs1.setEditable(false);
            //Configurar los JTables.
            configurarTblDat();
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
            vecCab=new Vector(14);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_COD_CON,"Cód.Con.");
            vecCab.add(INT_TBL_DAT_DEC_CON,"Control");
            vecCab.add(INT_TBL_DAT_DEL_CON,"Control");
            vecCab.add(INT_TBL_DAT_BUT_MOT,"");
            vecCab.add(INT_TBL_DAT_CHK_AUT,"Autorizar");
            vecCab.add(INT_TBL_DAT_CHK_DEN,"Denegar");
            vecCab.add(INT_TBL_DAT_OBS_AUT,"Observación");
            vecCab.add(INT_TBL_DAT_PER_AUT,"Per.Aut.");
            vecCab.add(INT_TBL_DAT_PAR1,"Par1.");
            vecCab.add(INT_TBL_DAT_PAR2,"Par2.");
            vecCab.add(INT_TBL_DAT_CLA_MOT,"Cla.Mot.");
            vecCab.add(INT_TBL_DAT_FUN_VER,"Fun.Ver.");
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
            tcmAux.getColumn(INT_TBL_DAT_DEC_CON).setPreferredWidth(278);
            tcmAux.getColumn(INT_TBL_DAT_BUT_MOT).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_OBS_AUT).setPreferredWidth(120);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BUT_MOT).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CON, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DEL_CON, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PER_AUT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PAR1, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PAR2, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CLA_MOT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FUN_VER, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_MOT);
            vecAux.add("" + INT_TBL_DAT_CHK_AUT);
            vecAux.add("" + INT_TBL_DAT_CHK_DEN);
            vecAux.add("" + INT_TBL_DAT_OBS_AUT);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_MOT).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_OBS_AUT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_OBS_AUT).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    validarAutCon();
                }
            });
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_MOT).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    intFilSel=tblDat.getSelectedRow();
                    if (intFilSel!=-1)
                    {
                        invocarClase(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CLA_MOT).toString());
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            objTblCelEdiChkAut=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setCellEditor(objTblCelEdiChkAut);
            objTblCelEdiChkAut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    validarAutCon();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiChkAut.isChecked())
                    {
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_DEN);
                    }
                }
            });
            objTblCelEdiChkDen=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setCellEditor(objTblCelEdiChkDen);
            objTblCelEdiChkDen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    validarAutCon();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiChkDen.isChecked())
                    {
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_AUT);
                    }
                }
            });
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
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
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    public boolean cargarReg()
    {
        boolean blnRes=false;
        try
        {
            if (cargarCabReg())
            {
                if (cargarDetReg())
                {
                    //Establecer el foco en el JTable sólo cuando haya datos.
                    if (tblDat.getRowCount()>0)
                    {
                        tblDat.setRowSelectionInterval(0, 0);
                        tblDat.requestFocus();
                    }
                    blnRes=true;
                }
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
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_aut, a1.tx_obs1, a1.tx_obs2";
                strSQL+=" FROM tbm_cabAutCotVen AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_cot=" + intCodDoc;
                strSQL+=" AND a1.co_aut=(";
                strSQL+=" SELECT MAX(b1.co_aut) AS co_aut";
                strSQL+=" FROM tbm_cabAutCotVen AS b1";
                strSQL+=" WHERE b1.co_emp=" + intCodEmp;
                strSQL+=" AND b1.co_loc=" + intCodLoc;
                strSQL+=" AND b1.co_cot=" + intCodDoc;
                strSQL+=" )";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    intCodAut=rst.getInt("co_aut");
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                }
                else
                {
                    limpiarFrm();
                    blnRes=false;
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_reg, a1.co_regNeg, a2.tx_desCor, a2.tx_desLar, a1.st_reg, a1.tx_obs1, a3.co_usr, a3.nd_par1, a3.nd_par2, a2.tx_claMot, a2.tx_funVerAut";
                strSQL+=" FROM tbm_detAutCotVen AS a1";
                strSQL+=" INNER JOIN tbm_regNeg AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_regNeg=a2.co_reg)";
                strSQL+=" LEFT OUTER JOIN tbm_autDoc AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_reg=a3.co_reg AND a3.co_usr=" + objParSis.getCodigoUsuario() + ")";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_cot=" + intCodDoc;
                strSQL+=" AND a1.co_aut=" + intCodAut;
                strSQL+=" AND a1.st_cum='N'";
                strSQL+=" ORDER by a1.co_reg";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("co_reg"));
                    vecReg.add(INT_TBL_DAT_COD_CON,rst.getString("co_regNeg"));
                    vecReg.add(INT_TBL_DAT_DEC_CON,rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DEL_CON,rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_BUT_MOT,null);
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                    {
                        vecReg.add(INT_TBL_DAT_CHK_AUT,Boolean.TRUE);
                        vecReg.add(INT_TBL_DAT_CHK_DEN,null);
                    }
                    else if (strAux.equals("D"))
                    {
                        vecReg.add(INT_TBL_DAT_CHK_AUT,null);
                        vecReg.add(INT_TBL_DAT_CHK_DEN,Boolean.TRUE);
                    }
                    else
                    {
                        vecReg.add(INT_TBL_DAT_CHK_AUT,null);
                        vecReg.add(INT_TBL_DAT_CHK_DEN,null);
                    }
                    vecReg.add(INT_TBL_DAT_OBS_AUT,rst.getString("tx_obs1"));
                    strAux=rst.getString("co_usr");
                    if (strAux==null)
                        vecReg.add(INT_TBL_DAT_PER_AUT,"N");
                    else
                        vecReg.add(INT_TBL_DAT_PER_AUT,"S");
                    vecReg.add(INT_TBL_DAT_PAR1,rst.getString("nd_par1"));
                    vecReg.add(INT_TBL_DAT_PAR2,rst.getString("nd_par2"));
                    vecReg.add(INT_TBL_DAT_CLA_MOT,rst.getString("tx_claMot"));
                    vecReg.add(INT_TBL_DAT_FUN_VER,rst.getString("tx_funVerAut"));
                    vecDat.add(vecReg);
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
                if (insertarHisCotVen())
                {
                    if (actualizarCab())
                    {
                        if (actualizarDet())
                        {
                            if (actualizarTbmCabCotVen())
                            {
                                if (actualizarTbmPedOtrBodCotVen())
                                {
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
     * Esta función permite insertar el histórico de las cotizaciones de venta.
     * @return true: Si se pudo insertar el histórico.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarHisCotVen()
    {
        int intCodHis=1;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT MAX(co_his) AS co_his FROM tbh_cabCotVen";
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_cot=" + intCodDoc;
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    intCodHis=rst.getInt(1)+1;
                }
                rst.close();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbh_cabCotVen (co_emp, co_loc, co_cot, co_his, fe_cot, co_cli, co_ven, tx_ate, tx_numPed, co_forPag, nd_sub, nd_porIva, nd_valIva";
                strSQL+=", nd_valDes, nd_tot, ne_val, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod, tx_obsSolAut, tx_obsAutSol, st_aut, st_regRep, fe_his, co_usrHis)";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, " + intCodHis + " AS co_his, a1.fe_cot, a1.co_cli, a1.co_ven, a1.tx_ate, a1.tx_numPed, a1.co_forPag, a1.nd_sub, a1.nd_porIva, a1.nd_valIva";
                strSQL+=", a1.nd_valDes, a1.nd_tot, a1.ne_val, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultMod, a1.co_usrIng, a1.co_usrMod, a1.tx_obsSolAut, a1.tx_obsAutSol, a1.st_aut, a1.st_regRep";
                strSQL+=", " + objParSis.getFuncionFechaHoraBaseDatos() + " AS fe_his";
                strSQL+=", " + objParSis.getCodigoUsuario() + " AS co_usrHis";
                strSQL+=" FROM tbm_cabCotVen AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_cot=" + intCodDoc;
                stm.executeUpdate(strSQL);
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbh_detCotVen (co_emp, co_loc, co_cot, co_his, co_reg, co_itm, tx_codAlt, tx_codAlt2, tx_nomItm, co_bod, nd_can, nd_preUni, nd_porDes";
                strSQL+=", st_ivaVen, nd_preCom, nd_porDesPreCom, co_prv, st_regRep, fe_his, co_usrHis)";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, " + intCodHis + " AS co_his, a1.co_reg, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.co_bod, a1.nd_can, a1.nd_preUni, a1.nd_porDes";
                strSQL+=", a1.st_ivaVen, a1.nd_preCom, a1.nd_porDesPreCom, a1.co_prv, a1.st_regRep";
                strSQL+=", " + objParSis.getFuncionFechaHoraBaseDatos() + " AS fe_his";
                strSQL+=", " + objParSis.getCodigoUsuario() + " AS co_usrHis";
                strSQL+=" FROM tbm_detCotVen AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_cot=" + intCodDoc;
                stm.executeUpdate(strSQL);
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbh_pagCotVen (co_emp, co_loc, co_cot, co_his, co_reg, ne_diaCre, fe_ven, nd_porRet, mo_pag, ne_diaGra, co_tipRet, st_regRep, fe_his, co_usrHis)";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, " + intCodHis + " AS co_his, a1.co_reg, a1.ne_diaCre, a1.fe_ven, a1.nd_porRet, a1.mo_pag, a1.ne_diaGra, a1.co_tipRet, a1.st_regRep";
                strSQL+=", " + objParSis.getFuncionFechaHoraBaseDatos() + " AS fe_his";
                strSQL+=", " + objParSis.getCodigoUsuario() + " AS co_usrHis";
                strSQL+=" FROM tbm_pagCotVen AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_cot=" + intCodDoc;
                stm.executeUpdate(strSQL);
                stm.close();
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
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCab()
    {
        int intNumFil, intNumAut, intNumDen;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabAutCotVen";
                strSQL+=" SET tx_obs2=" + objUti.codificar(txaObs2.getText());
                intNumAut=0;
                intNumDen=0;
                strEstValDoc="";
                intNumFil=objTblMod.getRowCountTrue();
                for (int i=0; i<intNumFil; i++)
                {
                    if (objTblMod.isChecked(i, INT_TBL_DAT_CHK_AUT))
                    {
                        intNumAut++;
                        //Se determina si fue autorizado el control "13: Días de validez de la cotización"
                        if (objTblMod.getValueAt(i, INT_TBL_DAT_COD_CON).toString().equals("13"))
                        {
                            strEstValDoc="A";
                        }
                    }
                    else if (objTblMod.isChecked(i, INT_TBL_DAT_CHK_DEN))
                    {
                        intNumDen++;
                        //Se determina si fue autorizado el control "13: Días de validez de la cotización"
                        if (objTblMod.getValueAt(i, INT_TBL_DAT_COD_CON).toString().equals("13"))
                        {
                            strEstValDoc="D";
                        }
                    }
                    else
                    {
                        //Se determina si fue autorizado el control "13: Días de validez de la cotización"
                        if (objTblMod.getValueAt(i, INT_TBL_DAT_COD_CON).toString().equals("13"))
                        {
                            strEstValDoc="P";
                        }
                    }
                }
                if (intNumAut==intNumFil)
                {
                    strSQL+=", st_reg='A'";
                    strEstDoc="A";
                }
                else if (intNumDen==intNumFil)
                {
                    strSQL+=", st_reg='D'";
                    strEstDoc="D";
                }
                else
                {
                    strEstDoc="P";
                }
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_cot=" + intCodDoc;
                strSQL+=" AND co_aut=" + intCodAut;
                stm.executeUpdate(strSQL);
                stm.close();
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
     * Esta función permite actualizar el detalle de un registro.
     * @return true: Si se pudo actualizar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDet()
    {
        int i;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                for (i=0;i<objTblMod.getRowCountTrue();i++)
                {
                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M"))
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_detAutCotVen";
                        strSQL+=" SET co_usrAut=" + objParSis.getCodigoUsuario();
                        strSQL+=", fe_aut=" + objParSis.getFuncionFechaHoraBaseDatos();
                        strSQL+=", tx_comAut='" + objParSis.getNombreComputadoraConDirIP() + "'";
                        strSQL+=", tx_obs1=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_OBS_AUT));
                        if (objTblMod.isChecked(i, INT_TBL_DAT_CHK_AUT))
                            strSQL+=", st_reg='A'";
                        else if (objTblMod.isChecked(i, INT_TBL_DAT_CHK_DEN))
                            strSQL+=", st_reg='D'";
                        else
                            strSQL+=", st_reg='P'";
                        strSQL+=" WHERE co_emp=" + intCodEmp;
                        strSQL+=" AND co_loc=" + intCodLoc;
                        strSQL+=" AND co_cot=" + intCodDoc;
                        strSQL+=" AND co_aut=" + intCodAut;
                        strSQL+=" AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG);
                        stm.executeUpdate(strSQL);
                    }
                }
                stm.close();
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
     * Esta función permite actualizar la tabla "tbm_cabCotVen".
     * @return true: Si se pudo actualizar la tabla.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarTbmCabCotVen()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabCotVen";
                if (strEstDoc.equals("A"))
                    strSQL+=" SET st_reg='U'";
                else if (strEstDoc.equals("D"))
                    strSQL+=" SET st_reg='D'";
                else
                    strSQL+=" SET st_reg='P'";
                if (strEstValDoc.equals("A"))
                {
                    //Al autorizar se asigna a "fe_val" la fecha actual.
                    strSQL+=", fe_val='" + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaBaseDatos()) + "'";
                }
                else if (strEstValDoc.equals("D") || strEstValDoc.equals("P"))
                {
                    //Al denegar se asigna a "fe_val" la fecha actual menos 1 dia.
                    java.util.Calendar cal=java.util.Calendar.getInstance();
                    cal.setTime(objParSis.getFechaHoraServidorIngresarSistema());
                    cal.add(java.util.Calendar.DATE, -1);
                    strSQL+=", fe_val='" + objUti.formatearFecha(cal.getTime(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    cal=null;
                }
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_cot=" + intCodDoc;
                stm.executeUpdate(strSQL);
                stm.close();
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
     * Esta función permite actualizar la tabla "tbm_pedOtrBodCotVen".
     * @return true: Si se pudo actualizar la tabla.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarTbmPedOtrBodCotVen()
    {
        int intNumFil;
        boolean blnRes=true;
        try
        {
            intNumFil=objTblMod.getRowCountTrue();
            for (int i=0; i<intNumFil; i++)
            {
                if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M"))
                {
                    //16: Pedidos a otras bodegas que necesitan autorización.
                    if (objTblMod.getValueAt(i, INT_TBL_DAT_COD_CON).toString().equals("16"))
                    {
                        if (con!=null)
                        {
                            stm=con.createStatement();
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="UPDATE tbm_pedOtrBodCotVen";
                            if (objTblMod.isChecked(i, INT_TBL_DAT_CHK_AUT))
                            {
                                strSQL+=" SET nd_canAut=nd_can";
                            }
                            else
                            {
                                strSQL+=" SET nd_canAut=NULL";
                            }
                            strSQL+=" WHERE co_emp=" + intCodEmp;
                            strSQL+=" AND co_loc=" + intCodLoc;
                            strSQL+=" AND co_cot=" + intCodDoc;
                            strSQL+=" AND st_necAut='S'";
                            stm.executeUpdate(strSQL);
                            stm.close();
                            stm=null;
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
            objTblMod.removeAllRows();
            txaObs1.setText("");
            txaObs2.setText("");
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        int intNumFilTbl, i, j;
        //Validar que esté seleccionada al menos un control.
        intNumFilTbl=objTblMod.getRowCountTrue();
        i=0;
        for (j=0; j<intNumFilTbl; j++)
        {
            if (objTblMod.isChecked(j, INT_TBL_DAT_CHK_AUT) || objTblMod.isChecked(j, INT_TBL_DAT_CHK_DEN))
            {
                i++;
                break;
            }
        }
        if (i==0)
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar al menos un control.<BR>Seleccione un control y vuelva a intentarlo.</HTML>");
            tblDat.requestFocus();
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
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Esta función establece los parámetros que debe utilizar el JDialog.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intCodTipDoc El código del tipo de documento.
     * @param intCodDoc El código de la cotización/documento.
     * @param intCodCli El código del cliente.
     * @param strIdeCli La identificación del cliente.
     */
    public void setParDlg(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodCli, String strIdeCli)
    {
        this.intCodEmp=intCodEmp;
        this.intCodLoc=intCodLoc;
        this.intCodTipDoc=intCodTipDoc;
        this.intCodDoc=intCodDoc;
        this.intCodCli=intCodCli;
        this.strIdeCli=strIdeCli;
    }
    
    /**
     * Esta función obtiene la opción que seleccionó el usuario en el JDialog.
     * Puede devolver uno de los siguientes valores:
     * <UL>
     * <LI>0: Click en el botón Cancelar.
     * <LI>1: Click en el botón Aceptar.
     * </UL>
     * <BR>Nota.- La opción predeterminada es el botón Cancelar.
     * @return La opción seleccionada por el usuario.
     */
    public int getOpcSelDlg()
    {
        return intOpcSelDlg;
    }
    
    private boolean invocarClase(String clase)
    {
        boolean blnRes=true;
        try
        {
            //Obtener el constructor de la clase que se va a invocar.
            Class objVen=Class.forName(clase);
            Class objCla[]=new Class[1];
            objCla[0]=arlParDlg.getClass();
            java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
            //Inicializar el objeto que tiene los parámetros que se van pasar al JDialog.
            arlParDlg.clear();
            arlParDlg.add(javax.swing.JOptionPane.getFrameForComponent(this));
            arlParDlg.add(objParSis);
            arlParDlg.add("" + intCodEmp);
            arlParDlg.add("" + intCodLoc);
            arlParDlg.add(null);
            arlParDlg.add("" + intCodDoc);
            //Inicializar el constructor que se obtuvo.
            Object objObj[]=new Object[1];
            objObj[0]=arlParDlg;
            javax.swing.JDialog dlgVen;
            dlgVen=(javax.swing.JDialog)objCon.newInstance(objObj);
            dlgVen.setVisible(true);
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
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función determina si el usuario está permitido a autorizar el control 11.
     * @return true: Si el usuario está autorizado.
     * <BR>false: En el caso contrario.
     */
    private boolean isPermitidoAutCon11()
    {
        BigDecimal bgdMonCre=BigDecimal.ZERO, bgdValTotPen=BigDecimal.ZERO, bgdValDoc=BigDecimal.ZERO, bgdMonMaxPer;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.nd_monCre";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_cli=" + intCodCli;
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    bgdMonCre=rst.getBigDecimal("nd_monCre");
                }
                rst.close();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT -SUM(a2.mo_pag+a2.nd_abo) AS nd_totPen";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_cli=" + intCodCli;
                strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    bgdValTotPen=((bgdValTotPen=rst.getBigDecimal("nd_totPen"))==null?BigDecimal.ZERO:bgdValTotPen);
                }
                rst.close();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.nd_tot";
                strSQL+=" FROM tbm_cabCotVen AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_cot=" + intCodDoc;
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    bgdValDoc=rst.getBigDecimal("nd_tot");
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
            bgdMonMaxPer=bgdMonCre.multiply(BigDecimal.ONE.add(new BigDecimal(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_PAR1).toString()).divide(new BigDecimal("100"))));
            if (bgdMonMaxPer.compareTo(bgdValTotPen.add(bgdValDoc))<0)
            {
                mostrarMsgInf("<HTML>Usted no puede autorizar éste control por el siguiente motivo:<BR>Monto de crédito del cliente: <FONT COLOR=\"blue\">" + objUti.redondearBigDecimal(bgdMonCre, objParSis.getDecimalesMostrar())  + "</FONT><BR>Porcentaje que usted puede autorizar: <FONT COLOR=\"blue\">" + objUti.redondearBigDecimal(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_PAR1).toString(), objParSis.getDecimalesMostrar()) + "</FONT><BR>Valor pendiente del cliente: <FONT COLOR=\"blue\">" + objUti.redondearBigDecimal(bgdValTotPen, objParSis.getDecimalesMostrar()) + "</FONT><BR>Valor de la cotización: <FONT COLOR=\"blue\">" + objUti.redondearBigDecimal(bgdValDoc, objParSis.getDecimalesMostrar()) + "</FONT><BR>Sólo puede autorizar hasta " + objUti.redondearBigDecimal(bgdMonMaxPer, objParSis.getDecimalesMostrar()) + " y lo pendiente suma " +  objUti.redondearBigDecimal(bgdValTotPen.add(bgdValDoc), objParSis.getDecimalesMostrar()) + ".</HTML>");
                blnRes=false;
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
     * Esta función determina si el usuario está permitido a autorizar el control 12.
     * @return true: Si el usuario está autorizado.
     * <BR>false: En el caso contrario.
     */
    private boolean isPermitidoAutCon12()
    {
        int intDiaGraCli=0, intDiaGraUsr=0, intNumTotDocPen=0;;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                intDiaGraUsr=(new BigDecimal(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_PAR1).toString())).intValue();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a3.ne_diaGra, COUNT(*) AS ne_numTotDocPen";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_catTipDocSis AS a5 ON (a4.co_cat=a5.co_cat)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_cli=" + intCodCli;
                strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<0";
                strSQL+=" AND (a2.fe_ven + a3.ne_diaGra + " + intDiaGraUsr + ")<=" + objParSis.getFuncionFechaHoraBaseDatos();
                strSQL+=" AND a5.co_cat=3";
                strSQL+=" GROUP BY a3.ne_diaGra";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    intDiaGraCli=rst.getInt("ne_diaGra");
                    intNumTotDocPen=rst.getInt("ne_numTotDocPen");
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
            if (intNumTotDocPen>0)
            {
                mostrarMsgInf("<HTML>Usted no puede autorizar éste control por el siguiente motivo:<BR>Días de gracia del cliente: <FONT COLOR=\"blue\">" + intDiaGraCli  + "</FONT><BR>Días de gracia que usted puede autorizar: <FONT COLOR=\"blue\">" + intDiaGraUsr + "</FONT><BR>Hay " + intNumTotDocPen + " documentos que usted no puede autorizar.</HTML>");
                blnRes=false;
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
     * Esta función valida si el usuario puede autorizar/denegar el control. Además, valida si se puede modificar
     * la observación.
     * @return true: Si el usuario puede autorizar/denegar el control.
     * <BR>false: En el caso contrario.
     */
    private boolean validarAutCon()
    {
        boolean blnRes=true;
        try
        {
            int intFilSel=tblDat.getSelectedRow();
            if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_PER_AUT).equals("S"))
            {
                if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_PAR1)!=null || objTblMod.getValueAt(intFilSel,INT_TBL_DAT_PAR2)!=null)
                {
                    //Verificar si puede autorizar.
                    switch (Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_CON).toString()))
                    {
                        case 11: //Problemas de crédito
                            if (!isPermitidoAutCon11())
                            {
                                switch (tblDat.getSelectedColumn())
                                {
                                    case INT_TBL_DAT_CHK_AUT:
                                        objTblCelEdiChkAut.setCancelarEdicion(true);
                                        break;
                                    case INT_TBL_DAT_CHK_DEN:
                                        objTblCelEdiChkDen.setCancelarEdicion(true);
                                        break;
                                    default:
                                        objTblCelEdiTxt.setCancelarEdicion(true);
                                        break;
                                }
                            }
                            break;
                        case 12: //Tiene documentos vencidos
                            if (!isPermitidoAutCon12())
                            {
                                switch (tblDat.getSelectedColumn())
                                {
                                    case INT_TBL_DAT_CHK_AUT:
                                        objTblCelEdiChkAut.setCancelarEdicion(true);
                                        break;
                                    case INT_TBL_DAT_CHK_DEN:
                                        objTblCelEdiChkDen.setCancelarEdicion(true);
                                        break;
                                    default:
                                        objTblCelEdiTxt.setCancelarEdicion(true);
                                        break;
                                }
                            }
                            break;
                    }
                }
            }
            else
            {
                switch (tblDat.getSelectedColumn())
                {
                    case INT_TBL_DAT_CHK_AUT:
                        mostrarMsgInf("Usted no tiene permiso para autorizar este control.");
                        objTblCelEdiChkAut.setCancelarEdicion(true);
                        break;
                    case INT_TBL_DAT_CHK_DEN:
                        mostrarMsgInf("Usted no tiene permiso para denegar este control.");
                        objTblCelEdiChkDen.setCancelarEdicion(true);
                        break;
                    default:
                        mostrarMsgInf("Usted no tiene permiso para modificar este control.");
                        objTblCelEdiTxt.setCancelarEdicion(true);
                        break;
                }
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
    private class ZafThreadGUI extends Thread
    {
        private int intIndFun;
        
        public ZafThreadGUI()
        {
            intIndFun=0;
        }
        
        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Guardar los cambios.
                    if (actualizarReg())
                    {
                        mostrarMsgInf("La operación AUTORIZAR/DENEGAR se realizó con éxito.");
                        intOpcSelDlg=1;
                        dispose();
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
                case INT_TBL_DAT_BUT_MOT:
                    strMsg="Motivo por el que no se cumplió el control";
                    break;
                case INT_TBL_DAT_CHK_AUT:
                    strMsg="Autorizar documento";
                    break;
                case INT_TBL_DAT_CHK_DEN:
                    strMsg="Denegar documento";
                    break;
                case INT_TBL_DAT_OBS_AUT:
                    strMsg="Observación del que autoriza";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}

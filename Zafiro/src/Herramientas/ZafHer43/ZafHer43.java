/*
 *
 * Created on 13 de agosto de 2008, 10:26
 */
package Herramientas.ZafHer43;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  jayapata
 */
public class ZafHer43 extends javax.swing.JInternalFrame
{
    //Constantes: Columnas del JTable.
    static final int INT_TBL_DAT_LIN=0;
    static final int INT_TBL_DAT_CHK=1;
    static final int INT_TBL_DAT_COD_EMP=2;
    static final int INT_TBL_DAT_COD_LOC=3;
    static final int INT_TBL_DAT_COD_TIP_DOC=4;
    static final int INT_TBL_DAT_DEC_TIP_DOC=5;
    static final int INT_TBL_DAT_DEL_TIP_DOC=6;
    static final int INT_TBL_DAT_COD_DOC=7;
    static final int INT_TBL_DAT_NUM_DOC=8;
    static final int INT_TBL_DAT_FEC_DOC=9;
    static final int INT_TBL_DAT_COD_CLI=10;
    static final int INT_TBL_DAT_NOM_CLI=11;
    static final int INT_TBL_DAT_VAL_DOC=12;
    static final int INT_TBL_DAT_VAL_PEN=13;
    //Variables generales.
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblTot objTblTotDocFac;
    private String strAux;
    private Vector vecAux;
    private Vector vecCab=new Vector();
    private boolean blnMarTodChkTblDat=true;
    private ZafPerUsr objPerUsr;
    
    /**
     * Crea una nueva instancia de la clase ZafHer43.
     * @param obj El objeto ZafParSis.
     */
    public ZafHer43(ZafParSis obj)
    {
        try
        {
            initComponents();
            
            //Inicializar objetos.
            this.objParSis=(ZafParSis)obj.clone();
            if (!configurarFrm())
                exitForm();
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

        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
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
            }
        });

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panRpt.setLayout(new java.awt.BorderLayout());

        spnDat.setPreferredSize(new java.awt.Dimension(454, 350));

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

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(454, 18));

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

        tabGen.addTab("General", panRpt);

        getContentPane().add(tabGen, java.awt.BorderLayout.CENTER);
        tabGen.getAccessibleContext().setAccessibleName("General\n");

        panBar.setPreferredSize(new java.awt.Dimension(320, 54));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(290, 35));
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

        butGua.setText("Guardar");
        butGua.setToolTipText("Guarda los cambios realizados.");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
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

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (objThrGUI==null)
        {
            objThrGUI=new ZafThreadGUI();
            objThrGUI.start();
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        java.sql.Connection conn;
        try
        {
            conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conn!=null)
            {
                System.out.print("la base es jjj : "+conn.getMetaData().getURL());
                conn.setAutoCommit(false);
                if (actualizarDet(conn))
                {
                    conn.commit();
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                }
                else
                    conn.rollback();
                conn.close();
                conn=null;
            }
        }
        catch(java.sql.SQLException Evt)
        {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch(Exception Evt)
        {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
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
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti = new ZafUtil();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.5");
            lblTit.setText(objParSis.getNombreMenu());
            //Configurar objetos.
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(1966))
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(1967))
            {
                butGua.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(1968))
            {
                butCer.setVisible(false);
            }
            //Configurar los JTables.
            configurarTabla();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarTabla()
    {
        boolean blnRes=true;
        try
        {
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente");
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Doc");
            vecCab.add(INT_TBL_DAT_VAL_PEN,"Val.Pen");
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
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEC_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setPreferredWidth(22);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(198);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setPreferredWidth(60);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_DAT_COD_LOC);
            arlColHid.add(""+INT_TBL_DAT_COD_TIP_DOC);
            arlColHid.add(""+INT_TBL_DAT_COD_DOC);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (objPerUsr.isOpcionEnabled(1967))
            {
                //Configurar JTable: Establecer los listener para el TableHeader.
                tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt)
                    {
                        tblDatMouseClicked(evt);
                    }
                });
                //Configurar JTable: Establecer columnas editables.
                vecAux=new Vector();
                vecAux.add("" + INT_TBL_DAT_CHK);
                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;
            }
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk=null;
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_VAL_PEN};
            objTblTotDocFac=new ZafTblTot(spnTot, spnTot , tblDat, tblTot, intCol);
            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=false;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        Vector vecData;
        String strPerUsu="";
        try
        {
            conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                vecData = new Vector();
                String strAux="";
                System.out.println("Codigo empresa "+objParSis.getCodigoEmpresa());
                System.out.println("Codigo Grupo Empresa "+objParSis.getCodigoEmpresaGrupo());
                if(objParSis.getCodigoUsuario()==1){
                    if (!(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()))
                    {
                       strAux=" a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+" AND ";
                       /*strPerUsu= " a.co_tipdoc in (select prg.co_tipdoc from tbr_tipdocprg prg where prg.co_emp=a.co_emp and prg.co_loc=a.co_loc and prg.co_mnu=1965 and a.co_tipdoc=prg.co_tipdoc)";                
                    }else{*/
                    }
                    strPerUsu= " a.co_tipdoc in (select prg.co_tipdoc from tbr_tipdocprg prg where  prg.co_mnu=1965 and a.co_tipdoc=prg.co_tipdoc and prg.co_emp="+objParSis.getCodigoEmpresa()+" and prg.co_loc="+objParSis.getCodigoLocal()+")";                
                    
                }else{
                    if (!(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()))
                    {
                       strAux=" a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+" AND ";
                    }                    
                   strPerUsu= " a.co_tipdoc in (select tusr.co_tipdoc from tbr_tipdocusr tusr where  tusr.co_mnu=1965 and a.co_tipdoc=tusr.co_tipdoc and tusr.co_usr="+objParSis.getCodigoUsuario()+" and tusr.co_emp="+objParSis.getCodigoEmpresa()+" and tusr.co_loc="+objParSis.getCodigoLocal()+")";
                }

                
                strSql="SELECT  a.st_excDocConVenCon, a.co_emp, a.co_loc, a.co_tipdoc, a2.tx_descor, a2.tx_deslar, a.co_doc, a.ne_numdoc, a.fe_doc, a.co_cli, a.tx_nomcli, abs(a.nd_tot) as nd_tot " +
                " ,(sum(abs(a1.mo_pag))-sum(abs(a1.nd_abo ))) as valpensinrete   "+
                " FROM tbm_cabmovinv AS a " +
                " INNER JOIN tbm_pagMovInv AS a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc AND a1.co_doc=a.co_doc) " +
                " INNER JOIN tbm_cabtipdoc AS a2 ON (a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipDoc=a.co_tipDoc ) " +
                " inner join tbm_cli as cli on (cli.co_emp=a.co_Emp and cli.co_cli=a.co_cli and ( cli.ne_diagra<=0  or cli.ne_diagra = null )) "+        
                " WHERE "+strAux+" " +
                //" a.co_tipdoc = 1 "+
                strPerUsu+" "+
                " AND a.st_reg NOT IN ('I','E') AND a.ST_IMP IN ('S') AND a1.st_reg IN ('A','C') " +
                " AND (a1.nd_porret=0 or a1.nd_porret is null ) and (a1.ne_diacre=0 or a1.ne_diacre is null) " +
                " AND (a1.nd_abo+a1.mo_pag) < 0  " +
                " GROUP BY a.st_excDocConVenCon, a.co_emp, a.co_loc, a.co_tipdoc, a2.tx_descor, a2.tx_deslar, a.co_doc, a.ne_numdoc, a.fe_doc, a.co_cli, a.tx_nomcli, a.nd_tot " +
                " ORDER BY a.co_emp, a.co_loc, a.ne_numdoc ";
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next())
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_CHK, new Boolean( (rstLoc.getString("st_excDocConVenCon")==null?true:(rstLoc.getString("st_excDocConVenCon").equals("S")?false:true)) )  );
                    vecReg.add(INT_TBL_DAT_COD_EMP, rstLoc.getString("co_emp") );
                    vecReg.add(INT_TBL_DAT_COD_LOC, rstLoc.getString("co_loc") );
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC, rstLoc.getString("co_tipdoc") );
                    vecReg.add(INT_TBL_DAT_DEC_TIP_DOC, rstLoc.getString("tx_descor") );
                    vecReg.add(INT_TBL_DAT_DEL_TIP_DOC, rstLoc.getString("tx_deslar") );
                    vecReg.add(INT_TBL_DAT_COD_DOC, rstLoc.getString("co_doc") );
                    vecReg.add(INT_TBL_DAT_NUM_DOC, rstLoc.getString("ne_numdoc") );
                    vecReg.add(INT_TBL_DAT_FEC_DOC, rstLoc.getString("fe_doc") );
                    vecReg.add(INT_TBL_DAT_COD_CLI, rstLoc.getString("co_cli") );
                    vecReg.add(INT_TBL_DAT_NOM_CLI, rstLoc.getString("tx_nomcli") );
                    vecReg.add(INT_TBL_DAT_VAL_DOC, rstLoc.getString("nd_tot") );
                    vecReg.add(INT_TBL_DAT_VAL_PEN, rstLoc.getString("valpensinrete") );
                    vecData.add(vecReg);
                }
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);
                objTblTotDocFac.calcularTotales();    
                objTblTotDocFac.setValueAt("" +  objTblTotDocFac.getValueAt(0,INT_TBL_DAT_VAL_DOC),0,0); 
                rstLoc.close(); 
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conn.close();
                conn=null;
                pgrSis.setValue(0);
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                pgrSis.setIndeterminate(false);
                blnRes=true;
            }
        }
        catch (java.sql.SQLException Evt)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;          
    }

    private boolean actualizarDet(java.sql.Connection conn)
    {
        boolean blnRes=false;  
        java.sql.Statement stmLoc;
        String strSql="";
        try
        {
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                for (int i=0; i < tblDat.getRowCount(); i++)
                {
                    strSql="UPDATE tbm_cabmovinv SET " +
                    "  st_excDocConVenCon='"+ (tblDat.getValueAt(i, INT_TBL_DAT_CHK)==null?"N":(tblDat.getValueAt(i, INT_TBL_DAT_CHK).toString().equals("true")?"N":"S") )  +"' "+
                    " ,fe_autExcDocConVenCon="+objParSis.getFuncionFechaHoraBaseDatos()+" "+
                    " ,co_usrAutExcDocConVenCon="+objParSis.getCodigoUsuario()+" "+
                    " ,tx_comAutExcDocConVenCon='"+objParSis.getNombreComputadoraConDirIP()+"' "+
                    " WHERE co_emp="+tblDat.getValueAt(i, INT_TBL_DAT_COD_EMP).toString()+" AND co_loc="+tblDat.getValueAt(i, INT_TBL_DAT_COD_LOC).toString()+" " +
                    " AND co_tipdoc="+tblDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString()+" AND co_doc="+tblDat.getValueAt(i, INT_TBL_DAT_COD_DOC).toString()+" ";
                    stmLoc.executeUpdate(strSql); 
                }
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch (java.sql.SQLException Evt)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }
    
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblDatMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblMod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_DAT_CHK)
            {
                if (blnMarTodChkTblDat)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(true, i, INT_TBL_DAT_CHK);
                    }
                    blnMarTodChkTblDat=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(false, i, INT_TBL_DAT_CHK);
                    }
                    blnMarTodChkTblDat=true;
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
            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);    
            cargarDetReg();
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
                case INT_TBL_DAT_CHK:
                    strMsg="<HTML><FONT COLOR=\"blue\">Marcado: </FONT>Incluye el documento en los controles de ventas de contado.<BR><FONT COLOR=\"blue\">Desmarcado: </FONT>Excluye el documento de los controles de ventas de contado.</HTML>";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DEC_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DEL_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_DAT_VAL_PEN:
                    strMsg="Valor pendiente";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

}

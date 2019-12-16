
package Compras.ZafCom70;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author jayapata
 */
public class ZafCom70_01 extends javax.swing.JDialog 
{
    //Constantes.
    static final int INT_TBL_LINEA = 0;
    static final int INT_TBL_CODEMP = 1;
    static final int INT_TBL_CODLOC = 2;
    static final int INT_TBL_CODTIPDOC = 3;
    static final int INT_TBL_DESCORTIPDOC = 4;
    static final int INT_TBL_DESLARTIPDOC = 5;
    static final int INT_TBL_CODDOC = 6;
    static final int INT_TBL_NUMDOC = 7;
    static final int INT_TBL_FECDOC = 8;
    static final int INT_TBL_CODCLIPRO = 9;
    static final int INT_TBL_NOMCLIPRO = 10;
    static final int INT_TBL_BUTGUIA = 11;
    static final int INT_TBL_TIPGUIA = 12;

    //Variables
    private ZafTblMod objTblMod;
    private ZafTblCelRenBut objTblCelRenButDG;
    private ZafTblCelEdiButGen objTblCelEdiButGenDG;
    private ZafThreadBus objThrBus;
    ZafParSis objZafParSis;
    ZafUtil objUti;
    
    Vector vecCab = new Vector();
    public boolean blnAcepta = false;
    private String Str_RegSel[];
    String strCodCli = "";
    String strDesCli = "";
    String strCodVen = "";
    String strDesVen = "";
    String strSqlBus = "";
    String strDesCorTipDoc = "";
    String strDesLarTipDoc = "";
    String strCodTipDoc = "";
    
    javax.swing.JTextField txtCodTipDoc = new javax.swing.JTextField();


    /**
     * Creates new form pantalladialogo
     */
    public ZafCom70_01(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis ZafParSis, String strSql) 
    {
        super(parent, modal);
        try 
        {
            this.objZafParSis = ZafParSis;
            objUti = new ZafUtil();
            initComponents();
            this.setTitle(objZafParSis.getNombreMenu());
            lblTit.setText(objZafParSis.getNombreMenu());

            objTblCelEdiButGenDG = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();

            strSqlBus = strSql;

        } 
        catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e);  }
    }

    public void setEditable(boolean editable) 
    {
        if (editable == true) 
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }
        else 
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }

    private void cargarVenCons() 
    {
        configurarTabla();
        cargarDatBus();
    }

    private void cargarDatBus()
    {
        if (objThrBus == null)
        {
            objThrBus = new ZafThreadBus();
            objThrBus.start();
        }
    }

    private class ZafThreadBus extends Thread 
    {
        public void run() 
        {
            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);
            consultarDatBus(strSqlBus);
            objThrBus = null;
        }
    }

    private boolean consultarDatBus(String sqlBus)
    {
        boolean blnRes = false;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        Vector vecData;
        try 
        {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) 
            {
                stmLoc = conn.createStatement();
                vecData = new Vector();

                //System.out.println("consultarDatBus: " + sqlBus);
                rstLoc = stmLoc.executeQuery(sqlBus);
                while (rstLoc.next()) 
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp"));
                    vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc"));
                    vecReg.add(INT_TBL_CODTIPDOC, rstLoc.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_DESCORTIPDOC, rstLoc.getString("tx_descor"));
                    vecReg.add(INT_TBL_DESLARTIPDOC, rstLoc.getString("tx_deslar"));
                    vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc"));
                    vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc"));
                    vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc"));
                    vecReg.add(INT_TBL_CODCLIPRO, rstLoc.getString("co_clides"));
                    vecReg.add(INT_TBL_NOMCLIPRO, rstLoc.getString("tx_nomclides"));
                    vecReg.add(INT_TBL_BUTGUIA, "");
                    vecReg.add(INT_TBL_TIPGUIA, rstLoc.getString("st_tipguirem"));

                    vecData.add(vecReg);
                }
                objTblMod.setData(vecData);
                tblDat.setModel(objTblMod);
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;

                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);

                blnRes = true;
            }
        }
        catch (java.sql.SQLException Evt) {   blnRes = false;  objUti.mostrarMsgErr_F1(this, Evt);        } 
        catch (Exception Evt) {    blnRes = false;    objUti.mostrarMsgErr_F1(this, Evt);        }
        return blnRes;
    }

    private boolean configurarTabla() 
    {
        boolean blnRes = false;
        try 
        {
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CODEMP, "Cod.Emp.");
            vecCab.add(INT_TBL_CODLOC, "Cod.Loc.");
            vecCab.add(INT_TBL_CODTIPDOC, "Cod.Tip.Doc.");
            vecCab.add(INT_TBL_DESCORTIPDOC, "Des.Cor.Tip.Doc.");
            vecCab.add(INT_TBL_DESLARTIPDOC, "Des.Lar.Tip.Doc.");
            vecCab.add(INT_TBL_CODDOC, "Cod.Doc.");
            vecCab.add(INT_TBL_NUMDOC, "Num.Doc.");
            vecCab.add(INT_TBL_FECDOC, "Fec.Doc.");
            vecCab.add(INT_TBL_CODCLIPRO, "Cod.Cli/Pro.");
            vecCab.add(INT_TBL_NOMCLIPRO, "Nom.Cli/Pro.");
            vecCab.add(INT_TBL_BUTGUIA, "");
            vecCab.add(INT_TBL_TIPGUIA, "");

            objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            ZafMouMotAda objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Columnas Ocultas
            ArrayList arlColHid = new ArrayList();
            arlColHid.add("" + INT_TBL_CODEMP);
            arlColHid.add("" + INT_TBL_CODLOC);
            arlColHid.add("" + INT_TBL_CODTIPDOC);
            arlColHid.add("" + INT_TBL_CODDOC);
            arlColHid.add("" + INT_TBL_BUTGUIA);
            arlColHid.add("" + INT_TBL_TIPGUIA);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid = null;

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DESCORTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DESLARTIPDOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_CODCLIPRO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NOMCLIPRO).setPreferredWidth(185);
            tcmAux.getColumn(INT_TBL_BUTGUIA).setPreferredWidth(25);

            objTblCelRenButDG = new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTGUIA).setCellRenderer(objTblCelRenButDG);
            objTblCelRenButDG.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    switch (objTblCelRenButDG.getColumnRender()) {
                        case INT_TBL_BUTGUIA:
                            objTblCelRenButDG.setText("...");
                            break;
                    }
                }
            });

            //Configurar JTable: Editor de celdas.
            tcmAux.getColumn(INT_TBL_BUTGUIA).setCellEditor(objTblCelEdiButGenDG);
            objTblCelEdiButGenDG.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_BUTGUIA:
                                break;

                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_BUTGUIA:

                                llamarPantGuia(objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                );

                                break;
                        }
                    }
                }
            });

            Str_RegSel = new String[5];
            tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (!((tblDat.getSelectedColumn() == -1) || (tblDat.getSelectedRow() == -1))) {
                        if (evt.getClickCount() == 2) {

                            Str_RegSel[0] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODEMP).toString();
                            Str_RegSel[1] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODLOC).toString();
                            Str_RegSel[2] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODTIPDOC).toString();
                            Str_RegSel[3] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODDOC).toString();
                            Str_RegSel[4] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_TIPGUIA).toString();

                            blnAcepta = true;
                            dispose();
                        }
                    }
                }
            });

            //    //Configurar JTable: Establecer columnas editables.
            //    Vector vecAux=new Vector();
            //       vecAux.add("" + INT_TBL_BUTGUIA);
            //    objTblMod.setColumnasEditables(vecAux);
            //    vecAux=null;
            //    //Configurar JTable: Editor de la tabla.
            //    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);

            tcmAux = null;

            setEditable(true);
            blnRes = true;

        } 
        catch (Exception e) {   blnRes = false;  objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }

    private void llamarPantGuia(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        Compras.ZafCom23.ZafCom23 obj1 = new Compras.ZafCom23.ZafCom23(objZafParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, 0);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }

    public String GetCamSel(int Idx) 
    {
        if (!(Str_RegSel == null)) {
            if (Idx <= 0 || Idx > Str_RegSel.length) {
                return "El parametro debe ser entre 1 y " + Integer.toString(Str_RegSel.length);
            } else {
                return Str_RegSel[Idx - 1];
            }
        } else {
            return "";
        }
    }

    public boolean acepta() 
    {
        return blnAcepta;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panDat = new javax.swing.JPanel();
        tabFil = new javax.swing.JTabbedPane();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panTooBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblTit.setText("Título de la ventana");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panDat.setLayout(new java.awt.BorderLayout());

        panDet.setLayout(new java.awt.BorderLayout());

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
        spnDet.setViewportView(tblDat);

        panDet.add(spnDet, java.awt.BorderLayout.CENTER);

        tabFil.addTab("Datos", panDet);

        panDat.add(tabFil, java.awt.BorderLayout.CENTER);

        getContentPane().add(panDat, java.awt.BorderLayout.CENTER);

        panTooBar.setLayout(new java.awt.BorderLayout());

        butAce.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butAce.setText("Aceptar");
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBot.add(butAce);

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        panTooBar.add(panBot, java.awt.BorderLayout.EAST);

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

        panTooBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panTooBar, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(600, 300));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        if (!((tblDat.getSelectedColumn() == -1) || (tblDat.getSelectedRow() == -1))) 
        {
            Str_RegSel[0] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODEMP).toString();
            Str_RegSel[1] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODLOC).toString();
            Str_RegSel[2] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODTIPDOC).toString();
            Str_RegSel[3] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODDOC).toString();
            Str_RegSel[4] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_TIPGUIA).toString();

            blnAcepta = true;
        }
        dispose();

    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        cerrarVen();
    }//GEN-LAST:event_butCanActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        cargarVenCons();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        cerrarVen();
    }//GEN-LAST:event_formWindowClosing

    private void cerrarVen() 
    {
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == 0) 
        {
            System.gc();
            dispose();
        }
    }

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) 
            {
                case INT_TBL_LINEA:
                    strMsg = "";
                    break;
                case INT_TBL_CODLOC:
                    strMsg = "Código del local";
                    break;
                case INT_TBL_CODTIPDOC:
                    strMsg = "Código del tipo de documento";
                    break;
                case INT_TBL_DESCORTIPDOC:
                    strMsg = "Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DESLARTIPDOC:
                    strMsg = "Descripción larga del tipo de documento";
                    break;
                case INT_TBL_CODDOC:
                    strMsg = "Código del documento";
                    break;
                case INT_TBL_NUMDOC:
                    strMsg = "Número de documento";
                    break;
                case INT_TBL_FECDOC:
                    strMsg = "Fecha del documento";
                    break;
                case INT_TBL_CODCLIPRO:
                    strMsg = "Código del cliente/proveedor";
                    break;
                case INT_TBL_NOMCLIPRO:
                    strMsg = "Nombre del cliente/proveedor";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panTit;
    private javax.swing.JPanel panTooBar;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JTabbedPane tabFil;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

}

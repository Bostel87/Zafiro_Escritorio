/*
 * pantalladialogo.java
 *
 * Created on 13 de agosto de 2008, 10:45
 */
package Ventas.ZafVen27;

import java.util.Vector;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;

/**
 *
 * @author jayapata
 */
public class ZafVen27_01 extends javax.swing.JDialog 
{
    Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    ZafUtil objUti;
    private ZafThreadGUI objThrGUI;

    final int INT_TBL_LINEA = 0;
    final int INT_TBL_NUMFAC = 1;
    final int INT_TBL_CODLOC = 2;
    final int INT_TBL_CODTIPDOC = 3;
    final int INT_TBL_DCOTIPDOC = 4;
    final int INT_TBL_DCATIPDOC = 5;
    final int INT_TBL_CODDOC = 6;
    final int INT_TBL_NUMDOC = 7;
    final int INT_TBL_FECDOC = 8;
    final int INT_TBL_CODCLI = 9;
    final int INT_TBL_DESCLI = 10;
    final int INT_TBL_FETAUT = 11;

    Vector vecCab = new Vector();
    ZafVenCon objVenConCLi;
    ZafVenCon objVenConVen;
    String strCodCli = "";
    String strDesCli = "";
    String strCodSol = "";
    String strDesSol = "";
    private String Str_RegSel[];
    public boolean blnAcepta = false;

    /**
     * Creates new form pantalladialogo
     */
    public ZafVen27_01(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis ZafParSis) 
    {
        super(parent, modal);
        try
        {
            this.objZafParSis = ZafParSis;
            objUti = new ZafUtil();
            initComponents();
            this.setTitle(objZafParSis.getNombreMenu());
            lblTit.setText(objZafParSis.getNombreMenu());

        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private void configurarFrm() 
    {
        configuraTbl();
        configurarVenConClientes();
        configurarVenConVendedor();
    }

    private boolean configurarVenConClientes() 
    {
        boolean blnRes = true;
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_nom");
            arlCam.add("a.tx_dir");
            arlCam.add("a.tx_tel");
            arlCam.add("a.tx_ide");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Cli.");
            arlAli.add("Dirección");
            arlAli.add("Telefono");
            arlAli.add("RUC/CI");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("180");
            arlAncCol.add("120");
            arlAncCol.add("80");
            arlAncCol.add("100");

            //Armar la sentencia SQL.
            String strSQL;
            strSQL = "SELECT a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide FROM tbr_cliloc as a1 "
                    + " INNER JOIN tbm_cli as a ON(a.co_emp=a1.co_emp AND a.co_cli=a1.co_cli) "
                    + " WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objZafParSis.getCodigoLocal() + " "
                    + " AND a.st_cli='S' AND a.st_reg NOT IN('I','T')  order by a.tx_nom ";

            objVenConCLi = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConVendedor()
    {
        boolean blnRes = true;
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_usr");
            arlCam.add("a.tx_nom");
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre.");
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
            String strSQL = "";
            strSQL = "select a.co_usr, a.tx_nom  from tbr_usremp as b"
                    + " inner join tbm_usr as a on (a.co_usr=b.co_usr) "
                    + " where b.co_emp=" + objZafParSis.getCodigoEmpresa() + " and b.st_ven='S' and a.st_reg not in ('I')  order by a.tx_nom";

            objVenConVen = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configuraTbl()
    {
        boolean blnRes = false;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_NUMFAC, "Factura.");
            vecCab.add(INT_TBL_CODLOC, "Cod.Loc.");
            vecCab.add(INT_TBL_CODTIPDOC, "Cod.TipDoc.");
            vecCab.add(INT_TBL_DCOTIPDOC, "Tip.CorDoc.");
            vecCab.add(INT_TBL_DCATIPDOC, "Des.LarTipDoc.");
            vecCab.add(INT_TBL_CODDOC, "Cod.Doc.");
            vecCab.add(INT_TBL_NUMDOC, "Num.Doc.");
            vecCab.add(INT_TBL_FECDOC, "Fec.Doc.");
            vecCab.add(INT_TBL_CODCLI, "Cod.Cli.");
            vecCab.add(INT_TBL_DESCLI, "Des.Cli");
            vecCab.add(INT_TBL_FETAUT, "Fec.Aut");

            objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_NUMFAC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DCOTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DESCLI).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_FETAUT).setPreferredWidth(80);

            //Columnas Ocultas
            ArrayList arlColHid = new ArrayList();
            arlColHid.add("" + INT_TBL_CODTIPDOC);
            arlColHid.add("" + INT_TBL_CODDOC);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid = null;

            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);

            Str_RegSel = new String[4];

            tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (!((tblDat.getSelectedColumn() == -1) || (tblDat.getSelectedRow() == -1))) {
                        if (evt.getClickCount() == 2) {

                            Str_RegSel[0] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODLOC).toString();
                            Str_RegSel[1] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODTIPDOC).toString();
                            Str_RegSel[2] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODDOC).toString();
                            Str_RegSel[3] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_NUMDOC).toString();
                            blnAcepta = true;
                            dispose();
                        }
                    }
                }
            });

            blnRes = true;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        butradgrp = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        panTabGenDat = new javax.swing.JPanel();
        radTotDoc = new javax.swing.JRadioButton();
        radCriSel = new javax.swing.JRadioButton();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butBusCli = new javax.swing.JButton();
        lblSol = new javax.swing.JLabel();
        txtCodSol = new javax.swing.JTextField();
        txtDesSol = new javax.swing.JTextField();
        butBusSol = new javax.swing.JButton();
        panTbl = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBut = new javax.swing.JPanel();
        panSubBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butLim = new javax.swing.JButton();
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

        panTit.setPreferredSize(new java.awt.Dimension(100, 24));

        lblTit.setText("titulo");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panFrm.setLayout(new java.awt.BorderLayout());

        panTabGen.setLayout(new java.awt.BorderLayout());

        panTabGenDat.setPreferredSize(new java.awt.Dimension(100, 85));
        panTabGenDat.setLayout(null);

        butradgrp.add(radTotDoc);
        radTotDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        radTotDoc.setSelected(true);
        radTotDoc.setText("Todos los Documentos");
        radTotDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radTotDocActionPerformed(evt);
            }
        });
        panTabGenDat.add(radTotDoc);
        radTotDoc.setBounds(10, 0, 180, 20);

        butradgrp.add(radCriSel);
        radCriSel.setFont(new java.awt.Font("SansSerif", 0, 11));
        radCriSel.setText("Todos los Documentos que cumplan el criterio especificado");
        radCriSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radCriSelActionPerformed(evt);
            }
        });
        panTabGenDat.add(radCriSel);
        radCriSel.setBounds(10, 20, 320, 20);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli.setText("Cliente:");
        panTabGenDat.add(lblCli);
        lblCli.setBounds(40, 40, 60, 20);

        txtCodCli.setFont(new java.awt.Font("SansSerif", 0, 11));
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
        panTabGenDat.add(txtCodCli);
        txtCodCli.setBounds(100, 40, 60, 20);

        txtNomCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        panTabGenDat.add(txtNomCli);
        txtNomCli.setBounds(160, 40, 260, 20);

        butBusCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        butBusCli.setText("...");
        butBusCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusCliActionPerformed(evt);
            }
        });
        panTabGenDat.add(butBusCli);
        butBusCli.setBounds(420, 40, 20, 20);

        lblSol.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblSol.setText("Solicitante:");
        panTabGenDat.add(lblSol);
        lblSol.setBounds(40, 60, 60, 20);

        txtCodSol.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtCodSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodSolActionPerformed(evt);
            }
        });
        txtCodSol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodSolFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodSolFocusLost(evt);
            }
        });
        panTabGenDat.add(txtCodSol);
        txtCodSol.setBounds(100, 60, 60, 20);

        txtDesSol.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtDesSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesSolActionPerformed(evt);
            }
        });
        txtDesSol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesSolFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesSolFocusLost(evt);
            }
        });
        panTabGenDat.add(txtDesSol);
        txtDesSol.setBounds(160, 60, 260, 20);

        butBusSol.setFont(new java.awt.Font("SansSerif", 0, 11));
        butBusSol.setText("...");
        butBusSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusSolActionPerformed(evt);
            }
        });
        panTabGenDat.add(butBusSol);
        butBusSol.setBounds(420, 60, 20, 20);

        panTabGen.add(panTabGenDat, java.awt.BorderLayout.NORTH);

        panTbl.setLayout(new java.awt.BorderLayout());

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
        scrTbl.setViewportView(tblDat);

        panTbl.add(scrTbl, java.awt.BorderLayout.CENTER);

        panTabGen.add(panTbl, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panTabGen);

        panFrm.add(tabGen, java.awt.BorderLayout.CENTER);
        tabGen.getAccessibleContext().setAccessibleName("General");

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBut.setLayout(new java.awt.BorderLayout());

        butCon.setFont(new java.awt.Font("SansSerif", 0, 11));
        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panSubBot.add(butCon);

        butLim.setFont(new java.awt.Font("SansSerif", 0, 11));
        butLim.setText("Limpiar");
        butLim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLimActionPerformed(evt);
            }
        });
        panSubBot.add(butLim);

        butAce.setFont(new java.awt.Font("SansSerif", 0, 11));
        butAce.setText("Aceptar");
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panSubBot.add(butAce);

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11));
        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSubBot.add(butCan);

        panBut.add(panSubBot, java.awt.BorderLayout.EAST);

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

        panBut.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBut, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-650)/2, (screenSize.height-400)/2, 650, 400);
    }// </editor-fold>//GEN-END:initComponents

private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
    if (objThrGUI == null) 
    {
        objThrGUI = new ZafThreadGUI();
        objThrGUI.start();
    }
}//GEN-LAST:event_butConActionPerformed

    private String sqlConFil() 
    {
        String sqlFil = "";

        if (radCriSel.isSelected()) 
        {
            if (!txtCodCli.getText().equals("")) {
                sqlFil += " AND a.co_cli=" + txtCodCli.getText();
            }

            if (!txtCodSol.getText().equals("")) {
                sqlFil += " AND a.co_usrsol=" + txtCodSol.getText();
            }

        }

        return sqlFil;
    }

    private class ZafThreadGUI extends Thread 
    {
        public void run() 
        {
            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);
            consultarDat(sqlConFil());
            objThrGUI = null;
        }
    }

    private boolean consultarDat(String sqlFil) 
    {
        boolean blnRes = false;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        Vector vecData;
        try 
        {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) {
                stmLoc = conn.createStatement();
                vecData = new Vector();
                if (objZafParSis.getCodigoMenu() == 1888) 
                {
                   strSql = "select * from ( SELECT "
                            + " ( select  sum(x1.nd_cansolnodevprv) as  cansolstk   from tbm_cabsoldevven as x "
                            + " inner join tbm_detsoldevven as x1 on (x1.co_emp=x.co_emp and x1.co_loc=x.co_loc and x1.co_tipdoc=x.co_tipdoc and x1.co_doc=x.co_doc )  "
                            + " where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc   "
                            + " ) as   cansolstk,"
                            + " "
                            + " CASE WHEN ( "
                            + " select sum((x1.nd_candev - x1.nd_canvolfac)) as dat from  tbm_cabsoldevven as x "
                            + " inner join tbm_detsoldevven as x1 on (x1.co_emp=x.co_emp and x1.co_loc=x.co_loc and x1.co_tipdoc=x.co_tipdoc and x1.co_doc=x.co_doc ) "
                            + " where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc "
                            + " ) != 0  THEN 'N' ELSE 'S' END as st_volfac, "
                            //+ " (SELECT sum((y2.nd_can - (y1.nd_candev - y1.nd_canvolfac) - y2.nd_cancon)) as nd_canconguirem "
                            /*MODIFICADO POR EL PROYECTO TRANSFERENCIAS 19-07-2016 */
                            //+ " (SELECT sum((y2.nd_can - (y1.nd_candev - y1.nd_canvolfac) - y2.nd_cancon)) as nd_canconguirem "
                            + " (SELECT "
                                + " case when y2.co_tipdoc=102 then "
                                //+ "	sum((y2.nd_can - (y1.nd_candev - y1.nd_canvolfac) - y2.nd_cancon)) "
                                + "	sum((abs(det.nd_can) - (y1.nd_candev - y1.nd_canvolfac) - abs(det.nd_cancon))) "                           
                                + " else "
                                + "	 sum((abs(det.nd_can) - (y1.nd_candev - y1.nd_canvolfac) - abs(det.nd_cancon))) "
                                + " end as nd_canconguirem "                            
                            /*MODIFICADO POR EL PROYECTO TRANSFERENCIAS 19-07-2016 */                                                            
                            
                            + " FROM tbm_detsoldevven as y1 "
                            + " INNER JOIN tbm_detguirem as y2 on (y2.co_emprel=y1.co_emp  and y2.co_locrel=y1.co_locrel and y2.co_tipdocrel=y1.co_tipdocrel and y2.co_docrel=y1.co_docrel and y2.co_regrel=y1.co_regrel ) "
                            /*MODIFICADO POR EL PROYECTO TRANSFERENCIAS 19-07-2016 */
                            + " INNER JOIN tbm_detmovinv as det"
                            + " on (y1.co_emp=det.co_emp  and y1.co_locrel=det.co_loc and y1.co_tipdocrel=det.co_tipdoc and y1.co_docrel=det.co_doc and y1.co_regrel=det.co_reg )"
                            /*MODIFICADO POR EL PROYECTO TRANSFERENCIAS 19-07-2016 */

                            
                            + " WHERE y1.co_emp=a.co_emp and y1.co_loc=a.co_loc and y1.co_tipdoc=a.co_tipdoc and y1.co_doc=a.co_doc and y1.nd_candev > 0 and y2.st_meregrfisbod = 'S' group by y2.co_tipdoc"
                           

                            + " union  \n"

		            + "  SELECT case when a5.co_tipdoc=102 then  \n"
                            + "  sum((a5.nd_can - (y1.nd_candev - y1.nd_canvolfac) - a5.nd_cancon))  \n"
                            + "  else  \n"
                            + "  sum((abs(detres.nd_can) - (y1.nd_candev - y1.nd_canvolfac) - abs(detres.nd_cancon)))  \n"
                            + "  end as nd_canconguirem  \n"
                            + "  FROM tbm_detsoldevven as y1  \n"
                            + "    INNER JOIN tbm_detmovinv as det on (y1.co_emp=det.co_emp  and y1.co_locrel=det.co_loc and y1.co_tipdocrel=det.co_tipdoc and y1.co_docrel=det.co_doc and y1.co_regrel=det.co_reg )  \n"
                            + "  INNER JOIN tbm_cabsegmovinv as s  \n"
                            + "  on (s.co_emprelcabmovinv =det.co_emp and s.co_locrelcabmovinv=det.co_loc and s.co_tipdocrelcabmovinv=det.co_tipdoc and s.co_docrelcabmovinv=det.co_doc)  \n"
                            + "  INNER JOIN tbm_cabsegmovinv as s2  \n"
                            +"  on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271)  \n"
                            + "  INNER JOIN tbm_cabguirem as a3  \n"
                            + "  on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem )  \n"
                            + "  INNER JOIN tbm_detguirem as a5  \n"
                            + "  on(a5.co_emp=a3.co_emp and a5.co_loc=a3.co_loc and a5.co_tipdoc=a3.co_tipdoc and a5.co_doc=a3.co_doc and a5.co_reg=y1.co_reg)  \n"
                            + "  INNER JOIN tbm_detmovinv as detres  \n"
                            + "  on (detres.co_emp=a5.co_emprel  and detres.co_loc=a5.co_locrel  and detres.co_tipdoc=a5.co_tipdocrel and detres.co_doc=a5.co_docrel and detres.co_reg=a5.co_regrel and detres.co_tipdoc=294)  \n"
                            + "  WHERE y1.co_emp=a.co_emp  \n"
                            + "  and y1.co_loc=a.co_loc  \n"
                            + "  and y1.co_tipdoc=a.co_tipdoc  \n"
                            + "  and y1.co_doc=a.co_doc  \n"
                            //+ "  and y1.co_reg=a1.co_reg  \n"
                            + "  and y1.nd_candev > 0  \n"
                            + "  and a5.st_meregrfisbod = 'S'  \n"
                            + "   group by a5.co_tipdoc  \n"                           
                           
                           
                            + ") as nd_canconguirem, "
                            + " a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli, a.fe_doc,  a.ne_numdoc, a3.ne_numdoc as numfac "
                            + " ,a.fe_aut, a.st_impguiremaut  FROM tbm_cabsoldevven AS a "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                            + " INNER JOIN tbm_cabmovinv AS a3 ON(a3.co_emp=a.co_emp AND a3.co_loc=a.co_locrel AND a3.co_tipdoc=a.co_tipdocrel AND a3.co_doc=a.co_docrel) "
                            + "  WHERE a.co_Emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " AND a.co_tipdoc IN ( "
                            + "  SELECT b.co_tipdoc FROM tbr_tipdocdetprg as b WHERE b.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND "
                            + " b.co_loc = " + objZafParSis.getCodigoLocal() + " AND  b.co_mnu =" + objZafParSis.getCodigoMenu() + " ) "
                            + " AND a.st_reg ='A'  AND a.st_aut ='A' AND a.st_recMerDev='N' AND a.st_tipdev='C' AND a.st_meraceingsis = 'N' " + sqlFil + " ORDER BY a.ne_numdoc "
                            + " ) as x where CASE WHEN ((st_impguiremaut = 'S') or (st_impguiremaut = 'N' and nd_canconguirem < 0)) THEN st_volfac='N' ELSE cansolstk > 0 END ";
                }
                if (objZafParSis.getCodigoMenu() == 1898) 
                {
                    strSql = "SELECT  a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli, a.fe_doc,  a.ne_numdoc , a3.ne_numdoc as numfac "
                            + " ,a.fe_aut FROM tbm_cabsoldevven AS a "
                            + " INNER JOIN  tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc ) "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                            + " INNER JOIN tbm_cabmovinv AS a3 ON(a3.co_emp=a.co_emp AND a3.co_loc=a.co_locrel AND a3.co_tipdoc=a.co_tipdocrel AND a3.co_doc=a.co_docrel) "
                            + "  WHERE a.co_Emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " AND a.co_tipdoc IN ( "
                            + "  SELECT b.co_tipdoc FROM tbr_tipdocdetprg as b WHERE b.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND "
                            + " b.co_loc = " + objZafParSis.getCodigoLocal() + " AND  b.co_mnu =" + objZafParSis.getCodigoMenu() + " ) "
                            + " AND a.st_reg ='A'  AND a.st_aut ='A' and a.st_revTecMerDev='N' AND a.st_tipdev='C'  AND a.st_revtec='S' AND a1.st_revtec='S' AND a1.nd_canrec > 0  " + sqlFil + " "
                            + " GROUP BY a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc,  a2.tx_descor, a2.tx_deslar,  a.co_cli, a.tx_nomcli, a.fe_doc,  a.ne_numdoc, a3.ne_numdoc,a.fe_aut  ORDER BY a.ne_numdoc ";
                }
                if (objZafParSis.getCodigoMenu() == 1908)
                {
                    strSql = "SELECT  a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli, a.fe_doc,  a.ne_numdoc, a3.ne_numdoc as numfac "
                            + " ,a.fe_aut FROM tbm_cabsoldevven AS a "
                            + " INNER JOIN  tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc ) "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                            + " INNER JOIN tbm_cabmovinv AS a3 ON(a3.co_emp=a.co_emp AND a3.co_loc=a.co_locrel AND a3.co_tipdoc=a.co_tipdocrel AND a3.co_doc=a.co_docrel) "
                            + "  WHERE a.co_Emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " AND a.co_tipdoc IN ( "
                            + "  SELECT b.co_tipdoc FROM tbr_tipdocdetprg as b WHERE b.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND "
                            + " b.co_loc = " + objZafParSis.getCodigoLocal() + " AND  b.co_mnu =" + objZafParSis.getCodigoMenu() + " ) "
                            + " AND a.st_reg ='A'  AND a.st_aut ='A' AND a.st_RevBodMerDev='N' "
                            + " AND a.st_tipdev='C'  AND ( ( a1.st_revtec='N' AND a1.nd_canrec > 0 )  "
                            + " OR  ( a1.st_revtec='S' AND a1.nd_canrevtecace > 0 )  "
                            + " OR  ( a1.st_revtec='S' AND a1.nd_canrevtecrec > 0 )  )"
                            + "  " + sqlFil + " "
                            + " GROUP BY a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc,  a2.tx_descor, a2.tx_deslar,  a.co_cli, a.tx_nomcli, a.fe_doc,  a.ne_numdoc, a3.ne_numdoc,a.fe_aut  ORDER BY a.ne_numdoc ";
                }

                if (objZafParSis.getCodigoMenu() == 1918)
                {
                    String strAux = "";
                    strAux = " and a.co_loc=" + objZafParSis.getCodigoLocal() + " ";
                    strSql = "select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli, a.fe_doc,  "
                            + " a.ne_numdoc, a3.ne_numdoc as numfac  ,a.fe_aut  from( "
                            + " select * from( select "
                            + " ( select  sum(x1.nd_cansolnodevprv) as  cansolstk   from tbm_cabsoldevven as x "
                            + " inner join tbm_detsoldevven as x1 on (x1.co_emp=x.co_emp and x1.co_loc=x.co_loc and x1.co_tipdoc=x.co_tipdoc and x1.co_doc=x.co_doc )  "
                            + " where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc   "
                            + " ) as   cansolstk,"
                            + " CASE WHEN ( "
                            + " select sum((x1.nd_candev - x1.nd_canvolfac)) as dat from  tbm_cabsoldevven as x "
                            + " inner join tbm_detsoldevven as x1 on (x1.co_emp=x.co_emp and x1.co_loc=x.co_loc and x1.co_tipdoc=x.co_tipdoc and x1.co_doc=x.co_doc ) "
                            + " where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc "
                            + " ) != 0  THEN 'N' ELSE 'S' END as st_volfac,"
                                
                            /*MODIFICADO POR EL PROYECTO TRANSFERENCIAS 19-07-2016 */
                            //+ " (SELECT sum((y2.nd_can - (y1.nd_candev - y1.nd_canvolfac) - y2.nd_cancon)) as nd_canconguirem "
                            + " (SELECT "
                                + " case when y2.co_tipdoc=102 then "
                                + "	sum((y2.nd_can - (y1.nd_candev - y1.nd_canvolfac) - y2.nd_cancon)) "
                                + " else "
                                + "	 sum((abs(det.nd_can) - (y1.nd_candev - y1.nd_canvolfac) - abs(det.nd_cancon))) "
                                + " end as nd_canconguirem "                            
                            /*MODIFICADO POR EL PROYECTO TRANSFERENCIAS 19-07-2016 */                                                            
                            
                            + " FROM tbm_detsoldevven as y1 "
                            + " INNER JOIN tbm_detguirem as y2 on (y2.co_emprel=y1.co_emp  and y2.co_locrel=y1.co_locrel and y2.co_tipdocrel=y1.co_tipdocrel and y2.co_docrel=y1.co_docrel and y2.co_regrel=y1.co_regrel ) "
                            /*MODIFICADO POR EL PROYECTO TRANSFERENCIAS 19-07-2016 */
                            + " INNER JOIN tbm_detmovinv as det"
                            + " on (y1.co_emp=det.co_emp  and y1.co_locrel=det.co_loc and y1.co_tipdocrel=det.co_tipdoc and y1.co_docrel=det.co_doc and y1.co_regrel=det.co_reg )"
                            /*MODIFICADO POR EL PROYECTO TRANSFERENCIAS 19-07-2016 */
                            + " WHERE y1.co_emp=a.co_emp and y1.co_loc=a.co_loc and y1.co_tipdoc=a.co_tipdoc and y1.co_doc=a.co_doc and y1.co_reg=a1.co_reg and y1.nd_candev > 0 and y2.st_meregrfisbod = 'S' group by y2.co_tipdoc"
                            

                            + " union  \n"

		            + "  SELECT case when a5.co_tipdoc=102 then  \n"
                            + "  sum((a5.nd_can - (y1.nd_candev - y1.nd_canvolfac) - a5.nd_cancon))  \n"
                            + "  else  \n"
                            + "  sum((abs(detres.nd_can) - (y1.nd_candev - y1.nd_canvolfac) - abs(detres.nd_cancon)))  \n"
                            + "  end as nd_canconguirem  \n"
                            + "  FROM tbm_detsoldevven as y1  \n"
                            + "    INNER JOIN tbm_detmovinv as det on (y1.co_emp=det.co_emp  and y1.co_locrel=det.co_loc and y1.co_tipdocrel=det.co_tipdoc and y1.co_docrel=det.co_doc and y1.co_regrel=det.co_reg )  \n"
                            + "  INNER JOIN tbm_cabsegmovinv as s  \n"
                            + "  on (s.co_emprelcabmovinv =det.co_emp and s.co_locrelcabmovinv=det.co_loc and s.co_tipdocrelcabmovinv=det.co_tipdoc and s.co_docrelcabmovinv=det.co_doc)  \n"
                            + "  INNER JOIN tbm_cabsegmovinv as s2  \n"
                            +"  on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271)  \n"
                            + "  INNER JOIN tbm_cabguirem as a3  \n"
                            + "  on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem )  \n"
                            + "  INNER JOIN tbm_detguirem as a5  \n"
                            + "  on(a5.co_emp=a3.co_emp and a5.co_loc=a3.co_loc and a5.co_tipdoc=a3.co_tipdoc and a5.co_doc=a3.co_doc and a5.co_reg=y1.co_reg)  \n"
                            + "  INNER JOIN tbm_detmovinv as detres  \n"
                            + "  on (detres.co_emp=a5.co_emprel  and detres.co_loc=a5.co_locrel  and detres.co_tipdoc=a5.co_tipdocrel and detres.co_doc=a5.co_docrel and detres.co_reg=a5.co_regrel and detres.co_tipdoc=294)  \n"
                            + "  WHERE y1.co_emp=a.co_emp  \n"
                            + "  and y1.co_loc=a.co_loc  \n"
                            + "  and y1.co_tipdoc=a.co_tipdoc  \n"
                            + "  and y1.co_doc=a.co_doc  \n"
                            + "  and y1.co_reg=a1.co_reg  \n"
                            + "  and y1.nd_candev > 0  \n"
                            + "  and a5.st_meregrfisbod = 'S'  \n"
                            + "   group by a5.co_tipdoc  \n"                       
                            
                            
                            + " ) as nd_canconguirem, "
                            
                            
                            + " a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc , a.co_locrel, a.co_tipdocrel, a.co_docrel, a.co_cli, a.tx_nomcli, a.fe_doc,  a.ne_numdoc, a.fe_aut "
                            + " ,a.st_tipDev, a.st_RevBodMerDev, a.st_meraceingsis, a1.nd_canRevBodAce , a.st_impguiremaut "
                            + " FROM tbm_cabsoldevven AS a "
                            + " INNER JOIN  tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc ) "
                            + " WHERE a.co_Emp=" + objZafParSis.getCodigoEmpresa() + "   " + strAux + "  " + sqlFil
                            + " AND a.co_tipdoc IN (   SELECT b.co_tipdoc FROM tbr_tipdocdetprg as b WHERE b.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND "
                            + "  b.co_loc = " + objZafParSis.getCodigoLocal() + " AND  b.co_mnu =" + objZafParSis.getCodigoMenu() + " ) "
                            + " AND a.st_reg ='A'  AND a.st_aut ='A' AND a.st_meraceingsis='N' "
                            + " ) AS x WHERE "
                            + " CASE WHEN ((st_impguiremaut = 'S') or (st_impguiremaut = 'N' and nd_canconguirem < 0)) THEN "
                            + " CASE WHEN x.st_volfac IN ('N') THEN "
                            + "                CASE WHEN x.st_tipDev  IN ('C') THEN  x.st_RevBodMerDev='S' AND x.st_meraceingsis='N' AND x.nd_canRevBodAce > 0  ELSE x.st_meraceingsis='N' END "
                            + "       ELSE x.st_meraceingsis='N' END "
                            + " ELSE  CASE  WHEN x.cansolstk = 0 THEN x.st_meraceingsis='N'  "
                            + "       ELSE    "
                            + "  CASE WHEN x.st_tipDev  IN ('C') THEN  x.st_RevBodMerDev='S' AND x.st_meraceingsis='N' AND x.nd_canRevBodAce > 0  ELSE x.st_meraceingsis='N' END "
                            + "  END END  "
                            + " ) AS a "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                            + " INNER JOIN tbm_cabmovinv AS a3 ON(a3.co_emp=a.co_emp AND a3.co_loc=a.co_locrel AND a3.co_tipdoc=a.co_tipdocrel AND a3.co_doc=a.co_docrel) "
                            + " GROUP BY a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc,  a2.tx_descor, a2.tx_deslar,  a.co_cli, a.tx_nomcli, a.fe_doc,  a.ne_numdoc,"
                            + " a3.ne_numdoc,a.fe_aut ORDER BY a.ne_numdoc ";
                }
                if (objZafParSis.getCodigoMenu() == 1928) 
                {
                    strSql = "SELECT  a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli, a.fe_doc,  a.ne_numdoc, a3.ne_numdoc as numfac "
                            + " ,a.fe_aut FROM tbm_cabsoldevven AS a "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                            + " INNER JOIN tbm_cabmovinv AS a3 ON(a3.co_emp=a.co_emp AND a3.co_loc=a.co_locrel AND a3.co_tipdoc=a.co_tipdocrel AND a3.co_doc=a.co_docrel) "
                            + "  WHERE a.co_Emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " AND a.co_tipdoc IN ( "
                            + "  SELECT b.co_tipdoc FROM tbr_tipdocdetprg as b WHERE b.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND "
                            + " b.co_loc = " + objZafParSis.getCodigoLocal() + " AND  b.co_mnu =" + objZafParSis.getCodigoMenu() + " ) "
                            + " AND a.st_reg ='A'  AND a.st_aut ='A' "
                            + " AND a.st_revBodMerDev='S' "
                            + " AND a.st_exiMerRec='S' AND a.st_MerRecDevCli='N'  AND a.st_tipDev = 'C'  "
                            + ""
                            + " " + sqlFil + " ORDER BY a.ne_numdoc ";
                }
                System.out.println("ZafVen27_01.consultarDat: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while (rstLoc.next())
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_NUMFAC, rstLoc.getString("numfac"));
                    vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc"));
                    vecReg.add(INT_TBL_CODTIPDOC, rstLoc.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_DCOTIPDOC, rstLoc.getString("tx_descor"));
                    vecReg.add(INT_TBL_DCATIPDOC, rstLoc.getString("tx_deslar"));
                    vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc"));
                    vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc"));
                    vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc"));
                    vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli"));
                    vecReg.add(INT_TBL_DESCLI, rstLoc.getString("tx_nomcli"));
                    vecReg.add(INT_TBL_FETAUT, rstLoc.getString("fe_aut"));

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
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }


private void butBusCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusCliActionPerformed
    objVenConCLi.setTitle("Listado de Clientes");
    objVenConCLi.setCampoBusqueda(1);
    objVenConCLi.show();
    if (objVenConCLi.getSelectedButton() == objVenConCLi.INT_BUT_ACE) 
    {
        txtCodCli.setText(objVenConCLi.getValueAt(1));
        txtNomCli.setText(objVenConCLi.getValueAt(2));
        radCriSel.setSelected(true);
    }
}//GEN-LAST:event_butBusCliActionPerformed

private void radCriSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radCriSelActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_radCriSelActionPerformed

private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
    cerrarVen();
}//GEN-LAST:event_butCanActionPerformed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    cerrarVen();
}//GEN-LAST:event_formWindowClosing

private void butBusSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusSolActionPerformed
    objVenConVen.setTitle("Listado de Clientes");
    objVenConVen.setCampoBusqueda(1);
    objVenConVen.show();
    if (objVenConVen.getSelectedButton() == objVenConVen.INT_BUT_ACE) {
        txtCodSol.setText(objVenConVen.getValueAt(1));
        txtDesSol.setText(objVenConVen.getValueAt(2));
        radCriSel.setSelected(true);
    }
}//GEN-LAST:event_butBusSolActionPerformed

private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
    txtCodCli.transferFocus();
}//GEN-LAST:event_txtCodCliActionPerformed

private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
    txtNomCli.transferFocus();
}//GEN-LAST:event_txtNomCliActionPerformed

private void txtCodSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodSolActionPerformed
    txtCodSol.transferFocus();
}//GEN-LAST:event_txtCodSolActionPerformed

private void txtDesSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesSolActionPerformed
    txtDesSol.transferFocus();
}//GEN-LAST:event_txtDesSolActionPerformed

private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
    strCodCli = txtCodCli.getText();
    txtCodCli.selectAll();
}//GEN-LAST:event_txtCodCliFocusGained

private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
    strDesCli = txtNomCli.getText();
    txtNomCli.selectAll();
}//GEN-LAST:event_txtNomCliFocusGained

private void txtCodSolFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodSolFocusGained
    strCodSol = txtCodSol.getText();
    txtCodSol.selectAll();
}//GEN-LAST:event_txtCodSolFocusGained

private void txtDesSolFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesSolFocusGained
    strDesSol = txtDesSol.getText();
    txtDesSol.selectAll();
}//GEN-LAST:event_txtDesSolFocusGained

private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
    if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)) {
        if (txtCodCli.getText().equals("")) {
            txtCodCli.setText("");
            txtNomCli.setText("");
        } else {
            BuscarCliente("a.co_cli", txtCodCli.getText(), 0);
        }
    } else {
        txtCodCli.setText(strCodCli);
    }

}//GEN-LAST:event_txtCodCliFocusLost

private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
    if (!txtNomCli.getText().equalsIgnoreCase(strDesCli)) {
        if (txtNomCli.getText().equals("")) {
            txtCodCli.setText("");
            txtNomCli.setText("");
        } else {
            BuscarCliente("a.tx_nom", txtNomCli.getText(), 1);
        }
    } else {
        txtNomCli.setText(strDesCli);
    }
}//GEN-LAST:event_txtNomCliFocusLost

private void txtCodSolFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodSolFocusLost
    if (!txtCodSol.getText().equalsIgnoreCase(strCodSol)) {
        if (txtCodSol.getText().equals("")) {
            txtCodSol.setText("");
            txtDesSol.setText("");
        } else {
            BuscarVendedor("a.co_usr", txtCodSol.getText(), 0);
        }
    } else {
        txtCodSol.setText(strCodSol);
    }
}//GEN-LAST:event_txtCodSolFocusLost

private void txtDesSolFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesSolFocusLost
    if (!txtDesSol.getText().equalsIgnoreCase(strDesSol)) {
        if (txtDesSol.getText().equals("")) {
            txtCodSol.setText("");
            txtDesSol.setText("");
        } else {
            BuscarVendedor("a.tx_nom", txtDesSol.getText(), 1);
        }
    } else {
        txtDesSol.setText(strDesSol);
    }
}//GEN-LAST:event_txtDesSolFocusLost

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    configurarFrm();
}//GEN-LAST:event_formWindowOpened

private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
    if (!((tblDat.getSelectedColumn() == -1) || (tblDat.getSelectedRow() == -1))) 
    {
        Str_RegSel[0] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODLOC).toString();
        Str_RegSel[1] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODTIPDOC).toString();
        Str_RegSel[2] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODDOC).toString();
        Str_RegSel[3] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_NUMDOC).toString();
        blnAcepta = true;
    }
    dispose();
}//GEN-LAST:event_butAceActionPerformed

private void radTotDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radTotDocActionPerformed
    strCodCli = "";
    strDesCli = "";
    strCodSol = "";
    strDesSol = "";

    txtCodCli.setText("");
    txtNomCli.setText("");
    txtCodSol.setText("");
    txtDesSol.setText("");
}//GEN-LAST:event_radTotDocActionPerformed

private void butLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimActionPerformed
    objTblMod.removeAllRows();
}//GEN-LAST:event_butLimActionPerformed

    public void BuscarCliente(String campo, String strBusqueda, int tipo) 
    {
        objVenConCLi.setTitle("Listado de Clientes");
        if (objVenConCLi.buscar(campo, strBusqueda)) {
            txtCodCli.setText(objVenConCLi.getValueAt(1));
            txtNomCli.setText(objVenConCLi.getValueAt(2));
            radCriSel.setSelected(true);
        } else {
            objVenConCLi.setCampoBusqueda(tipo);
            objVenConCLi.cargarDatos();
            objVenConCLi.show();
            if (objVenConCLi.getSelectedButton() == objVenConCLi.INT_BUT_ACE) {
                txtCodCli.setText(objVenConCLi.getValueAt(1));
                txtNomCli.setText(objVenConCLi.getValueAt(2));
                radCriSel.setSelected(true);
            } else {
                txtCodCli.setText(strCodCli);
                txtNomCli.setText(strDesCli);
            }
        }
    }

    public void BuscarVendedor(String campo, String strBusqueda, int tipo)
    {
        objVenConVen.setTitle("Listado de Vendedores");
        if (objVenConVen.buscar(campo, strBusqueda)) {
            txtCodSol.setText(objVenConVen.getValueAt(1));
            txtDesSol.setText(objVenConVen.getValueAt(2));
            radCriSel.setSelected(true);
        } else {
            objVenConVen.setCampoBusqueda(tipo);
            objVenConVen.cargarDatos();
            objVenConVen.show();
            if (objVenConVen.getSelectedButton() == objVenConVen.INT_BUT_ACE) {
                txtCodSol.setText(objVenConVen.getValueAt(1));
                txtDesSol.setText(objVenConVen.getValueAt(2));
                radCriSel.setSelected(true);
            } else {
                txtCodSol.setText(strCodSol);
                txtDesSol.setText(strDesSol);
            }
        }
    }

    private void cerrarVen() 
    {
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == 0) {
            System.gc();
            blnAcepta = false;
            dispose();
        }
    }

    public boolean acepta() 
    {
        return blnAcepta;
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butBusCli;
    private javax.swing.JButton butBusSol;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butLim;
    private javax.swing.ButtonGroup butradgrp;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblSol;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTabGenDat;
    private javax.swing.JPanel panTbl;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JRadioButton radCriSel;
    private javax.swing.JRadioButton radTotDoc;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodSol;
    private javax.swing.JTextField txtDesSol;
    private javax.swing.JTextField txtNomCli;
    // End of variables declaration//GEN-END:variables

    protected void finalize() throws Throwable {
        System.gc();
        super.finalize();
    }

}

package Compras.ZafCom44;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JRException;
import java.util.Vector;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author jayapata
 */
public class ZafCom44 extends javax.swing.JInternalFrame 
{
    //Constantes
    private static final int INT_TBL_LINEA = 0;
    private static final int INT_TBL_CODEMP = 1;
    private static final int INT_TBL_CODLOC = 2;
    private static final int INT_TBL_CODTIPDOC = 3;
    private static final int INT_TBL_CODDOC = 4;
    private static final int INT_TBL_NUMDOC = 5;
    private static final int INT_TBL_FECDOC = 6;
    private static final int INT_TBL_NOMCLI = 7;
    
    //Variables
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafVenCon vcoTipDoc; 
    private ZafVenCon vcoBodUsr; 
    private ZafTblFilCab objTblFilCab;
    private ZafTblPopMnu objTblPopMnu;                                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafThreadGUI objThrGUI;
    private Vector vecReg;
    
    javax.swing.JTextField txtCodTipDoc = new javax.swing.JTextField();
    StringBuffer stbDocRelEmpRem;
    StringBuffer stbDocRelEmpLoc;
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    private String strSQL="";
    private String strCodTipDoc = "", strDesCorTipDoc = "", strDesLarTipDoc = "";
    private String strCodBod = "", strNomBod = "";
    private String strVersion = " v0.3 ";


    /**
     * Creates new form ZafCom44
     */
    public ZafCom44(Librerias.ZafParSis.ZafParSis obj)
    {
        try 
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();

            objUti = new ZafUtil();

            initComponents();

        } 
        catch (CloneNotSupportedException e) {       objUti.mostrarMsgErr_F1(this, e);      }
    }

    private boolean configurarTblDatVenConBodUsr() 
    {
        boolean blnRes = true;
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_bod");
            arlCam.add("a.tx_nom");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Bod");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("350");

            //Armar la sentencia SQL.   a7.nd_stkTot,
            strSQL = "";
            strSQL = " SELECT co_bod, tx_nom "
                   + " FROM "
                   + " ( "
                   + "    SELECT a2.co_bodgrp as co_bod, a1.tx_nom "
                   + "    FROM tbr_bodlocprgusr as a "
                   + "    INNER JOIN tbr_bodEmpBodGrp as a2 ON (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) "
                   + "    INNER JOIN tbm_bod as a1 ON (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) "
                   + "    WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + ""
                   + "    AND a.co_loc=" + objParSis.getCodigoLocal() + ""
                   + "    AND a.co_usr=" + objParSis.getCodigoUsuario() + ""
                   + "    AND a.co_mnu=" + objParSis.getCodigoMenu() + ""
                   + " ) as a";

            vcoBodUsr = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

            vcoBodUsr.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e) {       blnRes = false;     objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }

    private boolean configurarTblDatVenConTipDoc() 
    {
        boolean blnRes = true;
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("85");
            arlAncCol.add("105");
            arlAncCol.add("350");

            //Armar la sentencia SQL.
            
            strSQL = "";
            if (objParSis.getCodigoUsuario() == 1) 
            {
                strSQL = " SELECT a.co_tipdoc,a.tx_descor,a.tx_deslar "
                       + " FROM tbr_tipdocprg as b "
                       + " LEFT OUTER JOIN tbm_cabtipdoc as a ON (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)"
                       + " WHERE b.co_emp=" + objParSis.getCodigoEmpresa() + ""
                       + " AND b.co_loc = " + objParSis.getCodigoLocal() + " "
                       + " AND b.co_mnu = " + objParSis.getCodigoMenu();
            } 
            else 
            {
                strSQL = " SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar "
                       + " FROM tbr_tipDocUsr AS a1 "
                       + " INNER JOIN tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"
                       + " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + ""
                       + " AND a1.co_loc=" + objParSis.getCodigoLocal() + ""
                       + " AND a1.co_mnu=" + objParSis.getCodigoMenu() + ""
                       + " AND a1.co_usr=" + objParSis.getCodigoUsuario();
            }

            vcoTipDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        } 
        catch (Exception e) {       blnRes = false;         objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }

    private void configurarTblDat()
    {
        try 
        {
            Vector vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CODEMP, "Cód.Emp.");
            vecCab.add(INT_TBL_CODLOC, "Cód.Loc.");
            vecCab.add(INT_TBL_CODTIPDOC, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_CODDOC, "Cód.Doc.");
            vecCab.add(INT_TBL_NUMDOC, "Num.Doc");
            vecCab.add(INT_TBL_FECDOC, "Fec.Doc");
            vecCab.add(INT_TBL_NOMCLI, "Nom.Cli");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);

            lblTit.setText(objParSis.getNombreMenu());
            this.setTitle(objParSis.getNombreMenu() +  strVersion);

            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_LINEA).setCellRenderer(objTblFilCab);
            
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(430);

            objTblMod.addSystemHiddenColumn(INT_TBL_CODEMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODLOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODTIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODDOC, tblDat);

            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            
            setEditable(true);

        } 
        catch (Exception e) {        objUti.mostrarMsgErr_F1(this, e);     }
    }

    public void setEditable(boolean editable) 
    {
        if (editable == true)
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        else 
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
    }


    public void cargarBodPre() 
    {
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try 
        {
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null)
            {
                stmLoc = conn.createStatement();

                strSql = " SELECT co_bod, tx_nom "
                       + " FROM "
                       + " ( "
                       + "      SELECT a2.co_bodgrp as co_bod, a1.tx_nom "
                       + "      FROM tbr_bodlocprgusr as a "
                       + "      INNER JOIN tbr_bodEmpBodGrp as a2 ON (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) "
                       + "      INNER JOIN tbm_bod as a1 on (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) "
                       + "      WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + ""
                       + "      AND a.co_loc=" + objParSis.getCodigoLocal() + ""
                       + "      AND a.co_usr=" + objParSis.getCodigoUsuario() + ""
                       + "      AND a.co_mnu=" + objParSis.getCodigoMenu() + ""
                       + "      AND a.st_reg='S' "
                       + " ) as a";

                System.out.println("" + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) 
                {
                    txtCodBod.setText(rstLoc.getString("co_bod"));
                    txtNomBod.setText(rstLoc.getString("tx_nom"));
                    strCodBod = rstLoc.getString("co_bod");
                    strNomBod = rstLoc.getString("tx_nom");
                }
                rstLoc.close();
                stmLoc.close();
                stmLoc = null;
                rstLoc = null;
                conn.close();
                conn = null;
            }
        } 
        catch (java.sql.SQLException e) {      objUti.mostrarMsgErr_F1(this, e);    } 
        catch (Exception Evt) {      objUti.mostrarMsgErr_F1(this, Evt);       }
    }

    public void cargarTipoDoc() 
    {
        try 
        {
            java.sql.Connection conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            java.sql.Statement stmPrede = conn.createStatement();
            java.sql.ResultSet rstPrede;

            String sqlPrede = "";

            if (objParSis.getCodigoUsuario() == 1)
            {
                sqlPrede = " SELECT  a.co_tipdoc, a.tx_deslar, a.tx_descor, "
                         + "         case when (a.ne_ultdoc+1) is null then 1 else a.ne_ultdoc+1 end as numDoc  "
                         + " FROM tbr_tipdocprg as a1 "
                         + " INNER JOIN tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"
                         + " WHERE a1.co_emp = " + objParSis.getCodigoEmpresa() + ""
                         + " AND a1.co_loc = " + objParSis.getCodigoLocal() + ""
                         + " AND a1.co_mnu = " + objParSis.getCodigoMenu() + ""
                         + " AND a1.st_reg = 'S'";
            } 
            else 
            {
                sqlPrede = " SELECT a.co_tipdoc, a.tx_deslar, a.tx_descor, "
                         + "        case when (a.ne_ultdoc+1) is null then 1 else a.ne_ultdoc+1 end as numDoc  "
                         + " FROM tbr_tipDocUsr as a1 "
                         + " INNER JOIN tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"
                         + " WHERE a1.co_emp = " + objParSis.getCodigoEmpresa() + ""
                         + " AND a1.co_loc = " + objParSis.getCodigoLocal() + ""
                         + " AND a1.co_mnu = " + objParSis.getCodigoMenu() + ""
                         + " AND a1.co_usr = " + objParSis.getCodigoUsuario() + ""
                         + " AND a1.st_reg = 'S'";
            }

            rstPrede = stmPrede.executeQuery(sqlPrede);

            if (rstPrede.next())
            {
                txtCodTipDoc.setText(((rstPrede.getString("co_tipdoc") == null) ? "" : rstPrede.getString("co_tipdoc")));
                txtDesCorTipDoc.setText(((rstPrede.getString("tx_descor") == null) ? "" : rstPrede.getString("tx_descor")));
                txtDesLarTipDoc.setText(((rstPrede.getString("tx_deslar") == null) ? "" : rstPrede.getString("tx_deslar")));
                strCodTipDoc = rstPrede.getString("co_tipdoc");
                strDesCorTipDoc = rstPrede.getString("tx_descor");
                strDesLarTipDoc = rstPrede.getString("tx_deslar");
            }
            stmPrede.close();
            rstPrede.close();
            conn.close();
            stmPrede = null;
            rstPrede = null;
            conn = null;
        }
        catch (java.sql.SQLException e) {       objUti.mostrarMsgErr_F1(this, e);     } 
        catch (Exception Evt) {       objUti.mostrarMsgErr_F1(this, Evt);      }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        TabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBod = new javax.swing.JButton();
        panNomGuiRem = new javax.swing.JPanel();
        lblGuiRemiDes = new javax.swing.JLabel();
        txtGuiRemiDes = new javax.swing.JTextField();
        lblGuiRemiHas = new javax.swing.JLabel();
        txtGuiRemiHas = new javax.swing.JTextField();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panEst = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(100, 120));
        panGenCab.setLayout(null);

        lblTipDoc.setText("Tipo de Documento:");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(10, 10, 110, 20);

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
        panGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(130, 10, 70, 20);

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
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(200, 10, 230, 20);

        butTipDoc.setText(".."); // NOI18N
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(430, 10, 20, 20);

        lblBod.setText("Bodega:");
        panGenCab.add(lblBod);
        lblBod.setBounds(10, 30, 110, 20);

        txtCodBod.setBackground(objParSis.getColorCamposObligatorios());
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
        panGenCab.add(txtCodBod);
        txtCodBod.setBounds(130, 30, 70, 20);

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
        panGenCab.add(txtNomBod);
        txtNomBod.setBounds(200, 30, 230, 20);

        butBod.setText("jButton2");
        butBod.setPreferredSize(new java.awt.Dimension(20, 20));
        butBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodActionPerformed(evt);
            }
        });
        panGenCab.add(butBod);
        butBod.setBounds(430, 30, 20, 20);

        panNomGuiRem.setBorder(javax.swing.BorderFactory.createTitledBorder("Número de Guia"));
        panNomGuiRem.setLayout(null);

        lblGuiRemiDes.setText("Desde:");
        panNomGuiRem.add(lblGuiRemiDes);
        lblGuiRemiDes.setBounds(12, 20, 44, 20);

        txtGuiRemiDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtGuiRemiDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGuiRemiDesFocusLost(evt);
            }
        });
        panNomGuiRem.add(txtGuiRemiDes);
        txtGuiRemiDes.setBounds(56, 20, 268, 20);

        lblGuiRemiHas.setText("Hasta:");
        panNomGuiRem.add(lblGuiRemiHas);
        lblGuiRemiHas.setBounds(336, 20, 44, 20);

        txtGuiRemiHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtGuiRemiHasFocusGained(evt);
            }
        });
        panNomGuiRem.add(txtGuiRemiHas);
        txtGuiRemiHas.setBounds(380, 20, 268, 20);

        panGenCab.add(panNomGuiRem);
        panNomGuiRem.setBounds(0, 60, 660, 52);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

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

        butImp.setText("Imprimir");
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

        panEst.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panEst.setMinimumSize(new java.awt.Dimension(24, 26));
        panEst.setPreferredSize(new java.awt.Dimension(200, 15));
        panEst.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        panEst.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panEst, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panGen.add(panBar, java.awt.BorderLayout.SOUTH);

        TabFrm.addTab("General", panGen);

        getContentPane().add(TabFrm, java.awt.BorderLayout.CENTER);
        TabFrm.getAccessibleContext().setAccessibleName("General");

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarTblDat();
        configurarTblDatVenConTipDoc();
        configurarTblDatVenConBodUsr();
        cargarTipoDoc();
        cargarBodPre();
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
        txtCodBod.transferFocus();
}//GEN-LAST:event_txtCodBodActionPerformed

    private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
        strCodBod = txtCodBod.getText();
        txtCodBod.selectAll();
}//GEN-LAST:event_txtCodBodFocusGained

    private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
        if (!txtCodBod.getText().equalsIgnoreCase(strCodBod)) 
        {
            if (txtCodBod.getText().equals("")) {
                txtCodBod.setText("");
                txtNomBod.setText("");
            } 
            else 
            {
                BuscarBod("a.co_bod", txtCodBod.getText(), 0);
            }
        }
        else 
        {
            txtCodBod.setText(strCodBod);
        }
}//GEN-LAST:event_txtCodBodFocusLost

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc = txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) 
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else
            {
                BuscarTipDoc("a.tx_descor", txtDesCorTipDoc.getText(), 1);
            }
        } 
        else 
        {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
}//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc = txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) 
        {
            if (txtDesLarTipDoc.getText().equals("")) 
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } 
            else 
            {
                BuscarTipDoc("a.tx_deslar", txtDesLarTipDoc.getText(), 2);
            }
        }
        else 
        {
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
}//GEN-LAST:event_txtDesLarTipDocFocusLost
    private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
        txtNomBod.transferFocus();
}//GEN-LAST:event_txtNomBodActionPerformed

    private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
        strNomBod = txtNomBod.getText();
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
            {
                BuscarBod("a.tx_nom", txtNomBod.getText(), 1);
            }
        } 
        else 
        {
            txtNomBod.setText(strNomBod);
        }
}//GEN-LAST:event_txtNomBodFocusLost

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

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        vcoTipDoc.setTitle("Listado de Tipo de Documentos");
        vcoTipDoc.setCampoBusqueda(1);
        vcoTipDoc.show();
        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) 
        {
            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
            strCodTipDoc = vcoTipDoc.getValueAt(1);
        }
}//GEN-LAST:event_butTipDocActionPerformed

    private void txtGuiRemiDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGuiRemiDesFocusGained
        txtGuiRemiDes.selectAll();
}//GEN-LAST:event_txtGuiRemiDesFocusGained

    private void txtGuiRemiDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGuiRemiDesFocusLost
        if (txtGuiRemiDes.getText().length() > 0) 
        {
            if (txtGuiRemiHas.getText().length() == 0) 
            {
                txtGuiRemiHas.setText(txtGuiRemiDes.getText());
            }
        }
}//GEN-LAST:event_txtGuiRemiDesFocusLost

    private void txtGuiRemiHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGuiRemiHasFocusGained
        txtGuiRemiHas.selectAll();
}//GEN-LAST:event_txtGuiRemiHasFocusGained

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm(null);
    }//GEN-LAST:event_formInternalFrameClosing

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        String strMsg = "¿Está Seguro que desea imprimit las Guías de Remisión ?";
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == 0) 
        {
            realizarImpGuia();
        }
    }//GEN-LAST:event_butImpActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (isCamVal()) 
        {
            if (butCon.getText().equals("Consultar")) 
            {
                blnCon = true;
                if (objThrGUI == null) 
                {
                    objThrGUI = new ZafThreadGUI();
                    objThrGUI.start();
                }
            }
            else 
            {
                blnCon = false;
            }
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt)
    {                          
        String strTit, strMsg;
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) 
        {
            dispose();
        }
    }  
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabFrm;
    private javax.swing.JButton butBod;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblGuiRemiDes;
    private javax.swing.JLabel lblGuiRemiHas;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panEst;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panNomGuiRem;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtGuiRemiDes;
    private javax.swing.JTextField txtGuiRemiHas;
    private javax.swing.JTextField txtNomBod;
    // End of variables declaration//GEN-END:variables

    
    public void BuscarBod(String campo, String strBusqueda, int tipo) 
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

    public void BuscarTipDoc(String campo, String strBusqueda, int tipo) 
    {
        vcoTipDoc.setTitle("Listado de Tipos de Documentos");
        if (vcoTipDoc.buscar(campo, strBusqueda))
        {
            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
            strCodTipDoc = vcoTipDoc.getValueAt(1);
            strDesCorTipDoc = vcoTipDoc.getValueAt(2);
            strDesLarTipDoc = vcoTipDoc.getValueAt(3);
        } 
        else 
        {
            vcoTipDoc.setCampoBusqueda(tipo);
            vcoTipDoc.cargarDatos();
            vcoTipDoc.show();
            if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) 
            {
                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                strCodTipDoc = vcoTipDoc.getValueAt(1);
                strDesCorTipDoc = vcoTipDoc.getValueAt(2);
                strDesLarTipDoc = vcoTipDoc.getValueAt(3);
            } 
            else 
            {
                txtCodTipDoc.setText(strCodTipDoc);
                txtDesCorTipDoc.setText(strDesCorTipDoc);
                txtDesLarTipDoc.setText(strDesLarTipDoc);
            }
        }
    }
    
    
    private class ZafThreadGUI extends Thread
    {
        @Override
        public void run()
        {
            if (!cargarDetReg(sqlConFil()))
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
    
    private String sqlConFil() 
    {
        String sqlFil="";
        String strTipDoc = "";
                       
        if (objParSis.getCodigoUsuario() == 1) 
        {
            strTipDoc = " SELECT a.co_tipdoc "
                      + " FROM tbr_tipdocprg as b "
                      + " INNER JOIN tbm_cabtipdoc as a ON (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)"
                      + " WHERE b.co_emp=" + objParSis.getCodigoEmpresa() + ""
                      + " AND b.co_loc = " + objParSis.getCodigoLocal() + ""
                      + " AND b.co_mnu = " + objParSis.getCodigoMenu();
        }
        else 
        {
            strTipDoc = " SELECT a.co_tipdoc "
                      + " FROM tbr_tipDocUsr AS a1 "
                      + " INNER JOIN tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"
                      + " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + ""
                      + " AND a1.co_loc=" + objParSis.getCodigoLocal() + ""
                      + " AND a1.co_mnu=" + objParSis.getCodigoMenu() + ""
                      + " AND a1.co_usr=" + objParSis.getCodigoUsuario();
        }
        sqlFil+=" AND a.co_tipdoc in ( " + strTipDoc + " )" ;
        
        if (txtCodBod.getText().length() > 0 || txtNomBod.getText().length() > 0) 
        {
            sqlFil+=" AND ( a6.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp= " + txtCodBod.getText() + "  ) ";
        }
        
        if (txtGuiRemiDes.getText().length() > 0 || txtGuiRemiHas.getText().length() > 0) 
        {
            sqlFil+=" AND ( ( a.ne_numdoc BETWEEN " + txtGuiRemiDes.getText() + " AND " + txtGuiRemiHas.getText() + " ) OR  a.ne_numdoc LIKE '" + txtGuiRemiHas.getText() + "%' )";
        }
        
        System.out.println("Filtro: "+sqlFil);
        return sqlFil;
    }
    
    private boolean isCamVal() 
    {
        if ( !(txtDesCorTipDoc.getText().length() > 0 || txtDesLarTipDoc.getText().length() > 0)) 
        {
            mostrarMsgInf("<HTML>Debe elegir un tipo de documento.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        
        if ( !(txtCodBod.getText().length() > 0 || txtNomBod.getText().length() > 0)) 
        {
            mostrarMsgInf("<HTML>Debe elegir una bodega.</HTML>");
            txtCodBod.requestFocus();
            return false;
        }
               
        return true;
    }
    
    private boolean cargarDetReg(String strFil)
    {
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        try 
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null)
            {
                stmLoc = conLoc.createStatement();

                Vector vecData = new Vector();

                strSQL = " SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numdoc, a.fe_doc, a.tx_nomclides "
                       + " FROM tbm_cabguirem as a  "
                       + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) "
                       + " WHERE a.st_reg in('A') AND a.ne_numDoc>0  "
                       + "  " + strFil + ""
                       + " ORDER BY a.ne_numdoc ";

                System.out.println("cargarDetReg: " + strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecData.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                
                while (rstLoc.next()) 
                {
                     if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp"));
                        vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc"));
                        vecReg.add(INT_TBL_CODTIPDOC, rstLoc.getString("co_tipdoc"));
                        vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc"));
                        vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc"));
                        vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc"));
                        vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nomclides"));

                        vecData.add(vecReg);
                    }
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc=null;

                //Asignar vectores al modelo.
                objTblMod.setData(vecData);
                tblDat.setModel(objTblMod);
                vecData.clear();
              
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        }
        catch (java.sql.SQLException e) {     blnRes = false;        objUti.mostrarMsgErr_F1(this, e);      }
        catch (Exception e)  {      blnRes = false;         objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }
    
    private boolean realizarImpGuia() 
    {
        boolean blnRes = true;
        java.sql.Connection conLoc;
        try 
        {
            conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());

            if (conLoc != null) 
            {

                for (int i = 0; i < tblDat.getRowCount(); i++) 
                {
                    System.out.println("INT_TBL_CODEMP--> " + tblDat.getValueAt(i, INT_TBL_CODEMP).toString());
                    System.out.println("INT_TBL_CODLOC--> " + tblDat.getValueAt(i, INT_TBL_CODLOC).toString());
                    System.out.println("INT_TBL_CODTIPDOC--> " + tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString());
                    System.out.println("INT_TBL_CODDOC--> " + tblDat.getValueAt(i, INT_TBL_CODDOC).toString());

                    impresionGuiaRemAutFac(conLoc, tblDat.getValueAt(i, INT_TBL_CODEMP).toString(), tblDat.getValueAt(i, INT_TBL_CODLOC).toString(), tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString(), tblDat.getValueAt(i, INT_TBL_CODDOC).toString());

                }
                conLoc.close();
                conLoc = null;
            }
        } 
        catch (java.sql.SQLException e) {     blnRes = false;        objUti.mostrarMsgErr_F1(this, e);      }
        catch (Exception e)  {      blnRes = false;         objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }

    public boolean impresionGuiaRemAutFac(java.sql.Connection conn, String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc)
    {
        String DIRECCION_REPORTE_GUIA = "C://zafiro//reportes_impresos/RptGuiRem.jrxml";
        try 
        {
            if (conn != null) 
            {
                String strImpDirectaGuia = "printbodega";

                if (System.getProperty("os.name").equals("Linux")) 
                {
                    DIRECCION_REPORTE_GUIA = "//zafiro//reportes_impresos/RptGuiRem.jrxml";
                }
                else 
                {
                    DIRECCION_REPORTE_GUIA = "C://zafiro//reportes_impresos/RptGuiRem.jrxml";
                }

                Map parameters = new HashMap();
                parameters.put("co_emp", intCodEmp);
                parameters.put("co_loc", intCodLoc);
                parameters.put("co_tipdoc", intCodTipDoc);
                parameters.put("co_doc", intCodDoc);

                javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet = new javax.print.attribute.HashPrintRequestAttributeSet();
                objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                JasperDesign jasperDesignGuiaRem = JRXmlLoader.load(DIRECCION_REPORTE_GUIA);
                JasperReport jasperReportGuiaRem = JasperCompileManager.compileReport(jasperDesignGuiaRem);
                JasperPrint reportGuiaRem = JasperFillManager.fillReport(jasperReportGuiaRem, parameters, conn);
                javax.print.attribute.standard.PrinterName printerName = new javax.print.attribute.standard.PrinterName(strImpDirectaGuia, null);
                javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet = new javax.print.attribute.HashPrintServiceAttributeSet();
                printServiceAttributeSet.add(printerName);
                net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp = new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                objJRPSerExp.exportReport();

            }
        } 
        catch (JRException e) {     objUti.mostrarMsgErr_F1(this, e);      }
        return true;
    }
    

    
    
}

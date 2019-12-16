/*
 * ZafCom19_01
 * 
 * Created on 1 de octubre de 2008, 15:44
 */
package Compras.ZafCom19;

import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;

/**
 *
 * @author jayapata
 */
public class ZafCom19_01 extends javax.swing.JDialog 
{
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    Librerias.ZafParSis.ZafParSis objZafParSis;
    private ZafSelFec objSelFec;
    private ZafThreadGUI objThrGUI;
    private ZafThreadBus objThrBus;
    ZafUtil objUti;
    final int INT_TBL_LINEA = 0;
    final int INT_TBL_CODEMP = 1;
    final int INT_TBL_CODLOC = 2;
    final int INT_TBL_CODTIPDOC = 3;
    final int INT_TBL_DESCORTIPDOC = 4;
    final int INT_TBL_DESLARTIPDOC = 5;
    final int INT_TBL_CODDOC = 6;
    final int INT_TBL_NUMDOC = 7;
    final int INT_TBL_NUMGUIDES = 8;
    final int INT_TBL_MOT_TRA = 9;
    final int INT_TBL_FECDOC = 10;
    final int INT_TBL_CODCLIPRO = 11;
    final int INT_TBL_NOMCLIPRO = 12;
    final int INT_TBL_CODBODGUIA = 13;
    final int INT_TBL_COD_EMP_COM_REL = 14;
    Vector vecCab = new Vector();
    ZafVenCon objVenConCLi;
    ZafVenCon objVenConVen;
    ZafVenCon objVenConTipdoc;
    String strCodCli = "";
    String strDesCli = "";
    String strCodVen = "";
    String strDesVen = "";
    String strSqlBus = "";
    String strDesCorTipDoc = "";
    String strDesLarTipDoc = "";
    String strCodTipDoc = "";
    javax.swing.JTextField txtCodTipDoc = new javax.swing.JTextField();
    private String Str_RegSel[];
    public boolean blnAcepta = false;

    /**
     * Creates new form pantalladialogo
     */
    public ZafCom19_01(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis ZafParSis, String strSql, String strCodBod, String strNomBod)
    {
        super(parent, modal);
        try
        {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) ZafParSis.clone();

            objUti = new ZafUtil();
            initComponents();
            this.setTitle(objZafParSis.getNombreMenu());
            lblTit.setText(objZafParSis.getNombreMenu());
            txtCodBod.setText(strCodBod);
            txtNomBod.setText(strNomBod);
            strSqlBus = strSql;
            
            //Configurar ZafSelFec
            objSelFec = new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(true);
            panGenCab.add(objSelFec);
            objSelFec.setBounds(4, 30, 472, 72);

            //******************************************************************************* 
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setHoy();
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
            int fecDoc[] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if (fecDoc != null) 
            {
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(java.util.Calendar.DATE, -30);

            dtePckPag.setAnio(objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH) + 1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(), "dd/MM/yyyy", "yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha, "yyyy/MM/dd");

            objSelFec.setFechaDesde(objUti.formatearFecha(fe1, "dd/MM/yyyy"));

            //******************************************************************************* 
            java.awt.Color colBack = txtCodBod.getBackground();
            txtCodBod.setEditable(false);
            txtCodBod.setBackground(colBack);
            txtNomBod.setEditable(false);
            txtNomBod.setBackground(colBack);

            if (objZafParSis.getCodigoMenu() == 1974)  //Ingresos provisionales de mercadería a Bodega
            {
                chkDocPenCon.setVisible(false);
                chkDocConTot.setSelected(true);
            }
        } 
        catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
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
        configurarVenConClientes();
        configurarVenConVendedor();
        configurarVenConTipDoc();

        if (!(strSqlBus.equals(""))) 
        {
            cargarDatBus();
        }
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
            if (conn != null) {
                stmLoc = conn.createStatement();
                vecData = new Vector();

                System.out.println("" + sqlBus);
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
                    vecReg.add(INT_TBL_NUMGUIDES, rstLoc.getString("ne_numorddes"));
                    vecReg.add(INT_TBL_MOT_TRA, rstLoc.getString("tx_motMovInv"));
                    vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc"));
                    vecReg.add(INT_TBL_CODCLIPRO, rstLoc.getString("co_cli"));
                    vecReg.add(INT_TBL_NOMCLIPRO, rstLoc.getString("tx_nomcli"));
                    vecReg.add(INT_TBL_CODBODGUIA, rstLoc.getString("co_ptopar"));
                    vecReg.add(INT_TBL_COD_EMP_COM_REL, rstLoc.getString("co_empComRel"));
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
                tabFil.setSelectedIndex(1);

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
                    + " AND  a.st_reg NOT IN('I','T')  order by a.tx_nom ";

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

    private boolean configurarVenConTipDoc()
    {
        boolean blnRes = true;
        try {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("110");
            arlAncCol.add("350");

            //Armar la sentencia SQL.   
            String Str_Sql = "";
            Str_Sql = "Select a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocdetprg as b "
                    + " inner join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)"
                    + " where   b.co_emp=" + objZafParSis.getCodigoEmpresa() + " and "
                    + " b.co_loc = " + objZafParSis.getCodigoLocal() + " and "
                    + " b.co_mnu = " + objZafParSis.getCodigoMenu();

            objVenConTipdoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

            objVenConTipdoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
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
            vecCab.add(INT_TBL_NUMGUIDES, "Ord.Des.");
            vecCab.add(INT_TBL_MOT_TRA, "Mot.Tra.");
            vecCab.add(INT_TBL_FECDOC, "Fec.Doc.");
            vecCab.add(INT_TBL_CODCLIPRO, "Cod.Cli./Pro.");
            vecCab.add(INT_TBL_NOMCLIPRO, "Nom.Cli./Pro.");
            vecCab.add(INT_TBL_CODBODGUIA, "Cod.Bod.");
            vecCab.add(INT_TBL_COD_EMP_COM_REL, "Cod.Emp.Rea.Com.Rel.");


            objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

            new ZafTblOrd(tblDat);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            ZafMouMotAda objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            /* Columnas Ocultas */
            ArrayList arlColHid = new ArrayList();
            arlColHid.add("" + INT_TBL_CODLOC);
            arlColHid.add("" + INT_TBL_CODTIPDOC);
            arlColHid.add("" + INT_TBL_CODDOC);

            if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915)) 
            {
                // arlColHid.add(""+INT_TBL_NUMDOC);
            }
            else 
            {
                arlColHid.add("" + INT_TBL_NUMGUIDES);
            }

            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid = null;

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODTIPDOC).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_DESCORTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DESLARTIPDOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CODDOC).setPreferredWidth(50); 
            tcmAux.getColumn(INT_TBL_MOT_TRA).setPreferredWidth(90); 
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_CODCLIPRO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NOMCLIPRO).setPreferredWidth(185);
            tcmAux.getColumn(INT_TBL_CODBODGUIA).setPreferredWidth(30);
            //tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(0);

            //Ocultar la columna.
            tcmAux.getColumn(INT_TBL_NUMDOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_NUMDOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_NUMDOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_NUMDOC).setResizable(false);

            tcmAux.getColumn(INT_TBL_COD_EMP_COM_REL).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_COD_EMP_COM_REL).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_COD_EMP_COM_REL).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_COD_EMP_COM_REL).setWidth(0);
            tcmAux.getColumn(INT_TBL_COD_EMP_COM_REL).setResizable(false);


            tcmAux = null;

            Str_RegSel = new String[9];
            tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (!((tblDat.getSelectedColumn() == -1) || (tblDat.getSelectedRow() == -1))) {
                        if (evt.getClickCount() == 2) {

                            Str_RegSel[0] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODLOC).toString();
                            Str_RegSel[1] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODTIPDOC).toString();
                            Str_RegSel[2] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODDOC).toString();
                            Str_RegSel[3] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_NUMDOC).toString();
                            Str_RegSel[4] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODEMP).toString();
                            Str_RegSel[5] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODLOC).toString();
                            Str_RegSel[6] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODBODGUIA).toString();
                            Str_RegSel[7] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_COD_EMP_COM_REL).toString();
                            Str_RegSel[8] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_MOT_TRA).toString();

                            blnAcepta = true;
                            dispose();
                        }
                    }
                }
            });


            tblDat.getTableHeader().setReorderingAllowed(false);

            setEditable(true);
            blnRes = true;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public String GetCamSel(int Idx) {
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

    public boolean acepta() {
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
        panRpt = new javax.swing.JPanel();
        tabFil = new javax.swing.JTabbedPane();
        panFilGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        panGenFil = new javax.swing.JPanel();
        chkDocPenCon = new javax.swing.JCheckBox();
        chkDocConPar = new javax.swing.JCheckBox();
        chkDocConTot = new javax.swing.JCheckBox();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblTipdoc = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butBusTipDoc = new javax.swing.JButton();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblVen = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        panFilDat = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBot = new javax.swing.JPanel();
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

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblTit.setText("Título de la ventana");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panRpt.setLayout(new java.awt.BorderLayout());

        panFilGen.setPreferredSize(new java.awt.Dimension(100, 90));
        panFilGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(120, 100));
        panGenCab.setRequestFocusEnabled(false);
        panGenCab.setLayout(null);

        lblBod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblBod.setText("Bodega:");
        panGenCab.add(lblBod);
        lblBod.setBounds(10, 10, 50, 15);

        txtCodBod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panGenCab.add(txtCodBod);
        txtCodBod.setBounds(60, 6, 60, 20);

        txtNomBod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panGenCab.add(txtNomBod);
        txtNomBod.setBounds(120, 6, 290, 20);

        panFilGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenFil.setPreferredSize(new java.awt.Dimension(110, 240));
        panGenFil.setRequestFocusEnabled(false);
        panGenFil.setLayout(null);

        chkDocPenCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkDocPenCon.setSelected(true);
        chkDocPenCon.setText("Mostrar los documentos que están pendientes de confirmar");
        chkDocPenCon.setPreferredSize(new java.awt.Dimension(20, 20));
        chkDocPenCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDocPenConActionPerformed(evt);
            }
        });
        panGenFil.add(chkDocPenCon);
        chkDocPenCon.setBounds(0, 6, 320, 20);

        chkDocConPar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkDocConPar.setSelected(true);
        chkDocConPar.setText("Mostrar los documentos que están confirmados parcialmente");
        chkDocConPar.setPreferredSize(new java.awt.Dimension(20, 20));
        chkDocConPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDocConParActionPerformed(evt);
            }
        });
        panGenFil.add(chkDocConPar);
        chkDocConPar.setBounds(0, 24, 320, 20);

        chkDocConTot.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkDocConTot.setText("Mostrar los documentos que están confirmados totalmente");
        chkDocConTot.setPreferredSize(new java.awt.Dimension(20, 20));
        chkDocConTot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDocConTotActionPerformed(evt);
            }
        });
        panGenFil.add(chkDocConTot);
        chkDocConTot.setBounds(0, 44, 320, 20);

        buttonGroup1.add(optTod);
        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setSelected(true);
        optTod.setText("Todos los Documentos");
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panGenFil.add(optTod);
        optTod.setBounds(0, 70, 320, 20);

        buttonGroup1.add(optFil);
        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Solo los documentos que cumplan el criterio seleccionado");
        panGenFil.add(optFil);
        optFil.setBounds(0, 90, 320, 20);

        lblTipdoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipdoc.setText("Tipo de Documento:");
        panGenFil.add(lblTipdoc);
        lblTipdoc.setBounds(30, 110, 100, 20);

        txtDesCorTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        panGenFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(130, 110, 50, 20);

        txtDesLarTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        panGenFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(180, 110, 280, 20);

        butBusTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butBusTipDoc.setText("jButton2");
        butBusTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusTipDocActionPerformed(evt);
            }
        });
        panGenFil.add(butBusTipDoc);
        butBusTipDoc.setBounds(460, 110, 20, 20);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCli.setText("Cliente/Proveedor:");
        panGenFil.add(lblCli);
        lblCli.setBounds(30, 130, 100, 20);

        txtCodCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        panGenFil.add(txtCodCli);
        txtCodCli.setBounds(130, 130, 50, 20);

        txtNomCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        panGenFil.add(txtNomCli);
        txtNomCli.setBounds(180, 130, 280, 20);

        butCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panGenFil.add(butCli);
        butCli.setBounds(460, 130, 20, 20);

        lblVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblVen.setText("Vend / Comp:");
        panGenFil.add(lblVen);
        lblVen.setBounds(30, 150, 100, 20);

        txtCodVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVenActionPerformed(evt);
            }
        });
        txtCodVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVenFocusLost(evt);
            }
        });
        panGenFil.add(txtCodVen);
        txtCodVen.setBounds(130, 150, 50, 20);

        txtNomVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVenActionPerformed(evt);
            }
        });
        txtNomVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVenFocusLost(evt);
            }
        });
        panGenFil.add(txtNomVen);
        txtNomVen.setBounds(180, 150, 280, 20);

        butVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panGenFil.add(butVen);
        butVen.setBounds(460, 150, 20, 20);

        panFilGen.add(panGenFil, java.awt.BorderLayout.CENTER);

        tabFil.addTab("General", panFilGen);

        panFilDat.setLayout(new java.awt.BorderLayout());

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

        panFilDat.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFil.addTab("Datos", panFilDat);

        panRpt.add(tabFil, java.awt.BorderLayout.CENTER);

        getContentPane().add(panRpt, java.awt.BorderLayout.CENTER);

        panBot.setLayout(new java.awt.BorderLayout());

        butCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panSubBot.add(butCon);

        butLim.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butLim.setText("Limpiar");
        butLim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLimActionPerformed(evt);
            }
        });
        panSubBot.add(butLim);

        butAce.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butAce.setText("Aceptar");
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panSubBot.add(butAce);

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSubBot.add(butCan);

        panBot.add(panSubBot, java.awt.BorderLayout.EAST);

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

        panBot.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBot, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(600, 450));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (objThrGUI == null) 
        {
            objThrGUI = new ZafThreadGUI();
            objThrGUI.start();
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimActionPerformed
        objTblMod.removeAllRows();
    }//GEN-LAST:event_butLimActionPerformed

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        if (!((tblDat.getSelectedColumn() == -1) || (tblDat.getSelectedRow() == -1))) 
        {
            Str_RegSel[0] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODLOC).toString();
            Str_RegSel[1] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODTIPDOC).toString();
            Str_RegSel[2] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODDOC).toString();
            Str_RegSel[3] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_NUMDOC).toString();
            Str_RegSel[4] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODEMP).toString();
            Str_RegSel[5] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODLOC).toString();                               
            Str_RegSel[6] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODBODGUIA).toString();
            Str_RegSel[7] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_COD_EMP_COM_REL).toString();
            Str_RegSel[8] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_MOT_TRA).toString();

            blnAcepta = true;
        }
        dispose();
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        cerrarVen();
    }//GEN-LAST:event_butCanActionPerformed

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        strCodCli = "";
        strDesCli = "";
        strCodVen = "";
        strDesVen = "";

        txtCodCli.setText("");
        txtNomCli.setText("");
        txtCodVen.setText("");
        txtNomVen.setText("");

        strDesCorTipDoc = "";
        strDesLarTipDoc = "";
        strCodTipDoc = "";
        txtCodTipDoc.setText("");
        txtDesCorTipDoc.setText("");
        txtDesLarTipDoc.setText("");
    }//GEN-LAST:event_optTodActionPerformed

    private void chkDocConTotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDocConTotActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkDocConTotActionPerformed

    private void chkDocPenConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDocPenConActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkDocPenConActionPerformed

    private void chkDocConParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDocConParActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkDocConParActionPerformed

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        txtCodVen.transferFocus();
    }//GEN-LAST:event_txtCodVenActionPerformed

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        strCodVen = txtCodVen.getText();
        txtCodVen.selectAll();
    }//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        if (!txtCodVen.getText().equalsIgnoreCase(strCodVen)) {
            if (txtCodVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            } else {
                BuscarVendedor("a.co_usr", txtCodVen.getText(), 0);
            }
        } else {
            txtCodVen.setText(strCodVen);
        }
    }//GEN-LAST:event_txtCodVenFocusLost

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli = txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

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

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc = txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                BuscarTipDoc("a.tx_descor", txtDesCorTipDoc.getText(), 1);
            }
        } else {
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
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                BuscarTipDoc("a.tx_deslar", txtDesLarTipDoc.getText(), 2);
            }
        } else {
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        txtNomCli.transferFocus();
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        strDesCli = txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

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

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        txtNomVen.transferFocus();
    }//GEN-LAST:event_txtNomVenActionPerformed

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        strDesVen = txtNomVen.getText();
        txtNomVen.selectAll();
    }//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        if (!txtNomVen.getText().equalsIgnoreCase(strDesVen)) {
            if (txtNomVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            } else {
                BuscarVendedor("a.tx_nom", txtNomVen.getText(), 1);
            }
        } else {
            txtNomVen.setText(strDesVen);
        }
    }//GEN-LAST:event_txtNomVenFocusLost

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        objVenConVen.setTitle("Listado de Clientes");
        objVenConVen.setCampoBusqueda(1);
        objVenConVen.show();
        if (objVenConVen.getSelectedButton() == objVenConVen.INT_BUT_ACE) {
            txtCodVen.setText(objVenConVen.getValueAt(1));
            txtNomVen.setText(objVenConVen.getValueAt(2));
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butVenActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        objVenConCLi.setTitle("Listado de Clientes");
        objVenConCLi.setCampoBusqueda(1);
        objVenConCLi.show();
        if (objVenConCLi.getSelectedButton() == objVenConCLi.INT_BUT_ACE) {
            txtCodCli.setText(objVenConCLi.getValueAt(1));
            txtNomCli.setText(objVenConCLi.getValueAt(2));
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butCliActionPerformed

    private void butBusTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusTipDocActionPerformed
        objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
        objVenConTipdoc.setCampoBusqueda(1);
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton() == objVenConTipdoc.INT_BUT_ACE) {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));

            strCodTipDoc = objVenConTipdoc.getValueAt(1);
            strDesCorTipDoc = objVenConTipdoc.getValueAt(2);
            strDesLarTipDoc = objVenConTipdoc.getValueAt(3);

            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butBusTipDocActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        cargarVenCons();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        cerrarVen();
    }//GEN-LAST:event_formWindowClosing

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

    private String sqlConFil() 
    {
        String sqlFil = "";
        String strPenCon = "";

        if (objZafParSis.getCodigoMenu() == 286) //Confirmación de Ingreso
        {
            if (chkDocPenCon.isSelected()) {
                strPenCon = "'P'";
            }
        }

        if (objZafParSis.getCodigoMenu() == 2205) //Confirmación de Egreso
        {
            if (chkDocPenCon.isSelected()) {
                strPenCon = "'P'";
            }
        }

        if (objZafParSis.getCodigoMenu() == 2915) //Confirmación de Egreso (Cliente Retira)
        {
            if (chkDocPenCon.isSelected()) {
                strPenCon = "'P'";
            }
        }

        if (chkDocConPar.isSelected()) {
            strPenCon += (strPenCon.equals("") ? "'E'" : ",'E'");
        }
        if (chkDocConTot.isSelected()) {
            strPenCon += (strPenCon.equals("") ? "'C'" : ",'C'");
        }

        if (objZafParSis.getCodigoMenu() == 286) //Confirmación de Ingreso
        {
            if (!(strPenCon.equals(""))) {
                sqlFil += " AND a.st_conInv IN(" + strPenCon + ") ";
            } else {
                sqlFil += " AND a.st_conInv IN('P','E','C') ";
            }
        }

        if (objZafParSis.getCodigoMenu() == 2205) //Confirmación de Egreso
        {
            if (!(strPenCon.equals(""))) {
                sqlFil += " AND a.st_conInv IN(" + strPenCon + ") ";
            } else {
                sqlFil += " AND a.st_conInv IN('P','E','C') ";
            }
        }

        if (objZafParSis.getCodigoMenu() == 2915) //Confirmación de Egreso (Cliente Retira)
        {
            if (!(strPenCon.equals(""))) {
                sqlFil += " AND a.st_conInv IN(" + strPenCon + ") ";
            } else {
                sqlFil += " AND a.st_conInv IN('P','E','C') ";
            }
        }

        if (objZafParSis.getCodigoMenu() == 1974) //Ingresos provisionales de mercadería a Bodega
        {
            if (!(strPenCon.equals(""))) {
                sqlFil += " AND a.st_conInv IN(" + strPenCon + ") ";
            } else {
                sqlFil += " AND a.st_conInv IN('C','E','F') ";
            }
        }


        if (objZafParSis.getCodigoMenu() == 2063) //Confirmación de salida temporal de mercadería
        {
            if (optFil.isSelected()) {

                if (!txtCodCli.getText().equals("")) {
                    sqlFil += " AND a3.co_cli=" + txtCodCli.getText();
                }

                if (!txtNomCli.getText().equals("")) {
                    sqlFil += " AND a3.tx_nomcli lIKE '" + txtNomCli.getText() + "'";
                }

                if (!txtCodVen.getText().equals("")) {
                    sqlFil += " AND a.co_usrsol=" + txtCodVen.getText();
                }

            }
        } 
        else 
        {
            if (optFil.isSelected()) 
            {
                if (objZafParSis.getCodigoMenu() == 286)  //Confirmación de Ingreso
                {
                    if (!txtCodCli.getText().equals("")) {
                        sqlFil += " AND a.co_cli=" + txtCodCli.getText();
                    }

                    if (!txtNomCli.getText().equals("")) {
                        sqlFil += " AND a.tx_nomcli lIKE '" + txtNomCli.getText() + "'";
                    }

                    if (!txtCodVen.getText().equals("")) {
                        sqlFil += " AND a.co_com=" + txtCodVen.getText();
                    }
                }

                if (objZafParSis.getCodigoMenu() == 2205) //Confirmación de Egreso
                {
                    if (!txtCodCli.getText().equals("")) {
                        sqlFil += " AND a.co_clides=" + txtCodCli.getText();
                    }

                    if (!txtNomCli.getText().equals("")) {
                        sqlFil += " AND a.tx_nomclides lIKE '" + txtNomCli.getText() + "'";
                    }
                    //if(!txtCodVen.getText().equals(""))
                    //   sqlFil+=" AND a.co_com="+txtCodVen.getText();
                }

                if (objZafParSis.getCodigoMenu() == 2915) //Confirmación de Egreso (Cliente Retira)
                {
                    if (!txtCodCli.getText().equals("")) {
                        sqlFil += " AND a.co_clides=" + txtCodCli.getText();
                    }

                    if (!txtNomCli.getText().equals("")) {
                        sqlFil += " AND a.tx_nomclides lIKE '" + txtNomCli.getText() + "'";
                    }

                    //if(!txtCodVen.getText().equals(""))
                    //   sqlFil+=" AND a.co_com="+txtCodVen.getText();
                }

            }
        }


        if (objSelFec.isCheckBoxChecked())
        {
            switch (objSelFec.getTipoSeleccion()) 
            {
                case 0: //Búsqueda por rangos
                    sqlFil += " AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    sqlFil += " AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    sqlFil += " AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 3: //Todo.
                    break;
            }
        }
        return sqlFil;
    }

    private boolean consultarDat(String sqlFil) 
    {
        boolean blnRes = false;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "", strAux = "";
        Vector vecData;
        try 
        {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) 
            {
                stmLoc = conn.createStatement();
                vecData = new Vector();

                strAux = "SELECT b.co_tipdoc FROM tbr_tipdocdetprg as b WHERE b.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND "
                        + " b.co_loc = " + objZafParSis.getCodigoLocal() + " AND  b.co_mnu =" + objZafParSis.getCodigoMenu() + " ";
                
                if (optFil.isSelected()) 
                {
                    if (!(txtCodTipDoc.getText().equals(""))) {
                        strAux = txtCodTipDoc.getText();
                    }
                }

                if (objZafParSis.getCodigoMenu() == 2063) //Confirmación de salida temporal de mercadería
                { 
                    strSql = "SELECT -1 AS co_empComRel,a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  ,a2.tx_descor, a2.tx_deslar, a.ne_numdoc, a.fe_doc, a.co_cli, a3.tx_nom as tx_nomcli , 0.0 as nd_tot  "
                            + " , '' as ne_numorddes  FROM tbm_cabsolsaltemmer AS a "
                            + " inner join tbm_detsolsaltemmer as a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc ) "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                            + " left JOIN tbm_cli AS a3 ON(a3.co_emp=a.co_emp AND a3.co_cli=a.co_cli ) "
                            + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " AND  a.co_tipdoc IN( " + strAux + " )  AND a1.co_bod=" + txtCodBod.getText() + " "
                            + " AND  a.st_aut='A' and  a.st_conTotMerEgr='N'  AND a.st_reg NOT IN('I','E')"
                            + " " + sqlFil + "  group by a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  ,a2.tx_descor, a2.tx_deslar, a.ne_numdoc, a.fe_doc, a.co_cli, a3.tx_nom, nd_tot "
                            + " ORDER BY a.ne_numdoc ";
                }
                else if (objZafParSis.getCodigoMenu() == 2073) //Confirmación de ingreso de mercadería que salió temporalmente
                {  
                    strSql = "SELECT -1 AS co_empComRel,a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  ,a2.tx_descor, a2.tx_deslar, a.ne_numdoc, a.fe_doc, a.co_cli, a3.tx_nom as tx_nomcli , 0.0 as nd_tot  "
                            + " , '' as ne_numorddes  FROM tbm_cabsolsaltemmer AS a "
                            + " inner join tbm_detsolsaltemmer as a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc ) "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                            + " left JOIN tbm_cli AS a3 ON(a3.co_emp=a.co_emp AND a3.co_cli=a.co_cli ) "
                            + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " AND  a.co_tipdoc IN( " + strAux + " )  AND a1.co_bod=" + txtCodBod.getText() + " "
                            + " AND  a.st_aut='A' and  a.st_conTotMerEgr='S' and  a.st_conTotMerIng='N'  AND a.st_reg NOT IN('I','E')"
                            + " " + sqlFil + "  group by a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  ,a2.tx_descor, a2.tx_deslar, a.ne_numdoc, a.fe_doc, a.co_cli, a3.tx_nom, nd_tot ";

                }
                else if (objZafParSis.getCodigoMenu() == 2205) //Confirmación de Egreso
                {
                    strSql = "SELECT *, CASE WHEN x.co_tipDocRel IN(1,228,124,238,127,242,125,126,166,225,206,153,58,96,97,98) THEN 'VENTA' ELSE 'REPOSICIÓN' END AS tx_motMovInv"
                            + " from ( "
                            + " SELECT   -1 AS co_empComRel,a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor , a3.tx_nom as tx_deslar,  a.ne_numdoc, a.fe_doc, a.co_clides as co_cli,"
                            + " a.tx_nomclides as tx_nomcli, null as nd_tot, a.co_ptopar, case when a.ne_numorddes is null then 0 else a.ne_numorddes end as ne_numorddes  "
                            + " ,( "
                            + " select count( x.co_reg ) from tbm_detguirem as x "
                            + " INNER JOIN tbm_detmovinv AS x1 ON(x1.co_emp=x.co_emprel AND x1.co_loc=x.co_locrel AND x1.co_tipdoc=x.co_tipdocrel and x1.co_doc=x.co_docrel and x1.co_reg=x.co_regrel )   "
                            + " where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc= a.co_doc "
                            + " and x1.st_cliretemprel is null  ) as exitcr "
                            + " , a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel"
                            + " FROM  tbm_cabguirem as a "
                            + "  INNER JOIN tbm_detguirem AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                            + "  INNER JOIN tbm_loc AS a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc) "
                            + "  INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a.co_emp AND a4.co_bod=a.co_ptopar ) "
                            + "  INNER JOIN tbm_cabTipDoc AS a5 ON (a5.co_emp=a.co_emp AND a5.co_loc=a.co_loc AND a5.co_tipDoc=a.co_tipDoc) "
                            + "  INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) "
                            + "  WHERE  a.co_tipdoc in ( " + strAux + "  )  AND ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=" + txtCodBod.getText() + ") "
                            + "  AND a.st_reg NOT IN('I','E')  and case when a.ne_numdoc > 0 then a.st_tieguisec='N' else 1=1 end    " + sqlFil + "  "
                            + "  GROUP BY  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor, a3.tx_nom,  a.ne_numdoc, a.fe_doc, a.co_clides, a.tx_nomclides  ,a.co_ptopar ,a.ne_numorddes  "
                            + " , a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel"
                            + "  ORDER BY a.fe_doc "
                            + "   ) as x  where   ( ne_numdoc > 0 or ne_numorddes > 0 ) and  case when ne_numdoc = 0 then exitcr > 0 else exitcr=0 end  ";
                }
                else if (objZafParSis.getCodigoMenu() == 2915) //Confirmación de Egreso (Cliente Retira)
                {
                    strSql = "SELECT *, CASE WHEN x.co_tipDocRel IN(1,228,124,238,127,242,125,126,166,225,206,153,58,96,97,98) THEN 'VENTA' ELSE 'REPOSICIÓN' END AS tx_motMovInv "
                            + " from ( "
                            + " SELECT   a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor , a3.tx_nom as tx_deslar,  a.ne_numdoc, a.fe_doc, a.co_clides as co_cli,"
                            + " a.tx_nomclides as tx_nomcli, null as nd_tot, a.co_ptopar, case when a.ne_numorddes is null then 0 else a.ne_numorddes end as ne_numorddes  "
                            + " ,( "
                            + " select count( x.co_reg ) from tbm_detguirem as x "
                            + " INNER JOIN tbm_detmovinv AS x1 ON(x1.co_emp=x.co_emprel AND x1.co_loc=x.co_locrel AND x1.co_tipdoc=x.co_tipdocrel and x1.co_doc=x.co_docrel and x1.co_reg=x.co_regrel )   "
                            + " INNER JOIN tbr_detmovinv as x4 ON (x4.co_emp=x1.co_emp and x4.co_loc=x1.co_loc and x4.co_tipdoc=x1.co_tipdoc and x4.co_doc=x1.co_doc and x4.co_reg=x1.co_reg and x4.st_reg= 'A' ) "
                            + " INNER JOIN tbm_detmovinv as x5 ON (x5.co_emp=x4.co_emprel and x5.co_loc=x4.co_locrel and x5.co_tipdoc=x4.co_tipdocrel and x5.co_doc=x4.co_docrel and x5.co_reg=x4.co_regrel ) "
                            + " INNER JOIN tbr_detmovinv as x6 ON (x6.co_emprel=x5.co_emp and x6.co_locrel=x5.co_loc and x6.co_tipdocrel=x5.co_tipdoc and x6.co_docrel=x5.co_doc and x6.co_regrel=x5.co_reg and "
                            + " (x6.co_emp!=x1.co_emp or x6.co_loc!=x1.co_loc or x6.co_tipdoc!=x1.co_tipdoc or x6.co_doc!=x1.co_doc) and x6.st_reg='A' ) "
                            + " INNER JOIN tbm_detmovinv as x7 ON (x7.co_emp=x6.co_emp and x7.co_loc=x6.co_loc and x7.co_tipdoc=x6.co_tipdoc and x7.co_doc=x6.co_doc and x7.co_reg=x6.co_reg ) "
                            + " INNER JOIN tbm_cabmovinv as x8 ON (x8.co_emp=x7.co_emp and x8.co_loc=x7.co_loc and x8.co_tipdoc=x7.co_tipdoc and x8.co_doc=x7.co_doc ) "
                            + " where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc= a.co_doc "
                            + " and x1.st_cliretemprel ='S' and x8.ne_numdoc > 0 ) as exitcr "
                            //se agrega por cliente retira 
                            + ", a2.co_empRel AS co_empOrdDes, a2.co_locRel AS co_locOrdDes, a2.co_tipDocRel AS co_tipDocOrdDes, a2.co_docRel AS co_docOrdDes"
                            + ", b1.co_empRel AS co_empVenRel, b1.co_locRel AS co_locVenRel, b1.co_tipDocRel AS co_tipDocVenRel, b1.co_docRel AS co_docVenRel, b1.co_regRel AS co_regVenRel /*Empresa que vende a la relacionada*/" //Empresa que vende a la relacionada
                            + "	, b2.co_empRel AS co_empComRel, b2.co_locRel AS co_locComRel, b2.co_tipDocRel AS co_tipDocComRel, b2.co_docRel AS co_docComRel, b2.co_regRel AS co_regComRel /*Empresa que compra a la relacionada, esta empresa es la que esta realizando la venta al cliente*/" //Empresa que compra a la relacionada, esta empresa es la que esta realizando la venta al cliente
                            //fin 
                            + " , a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel"
                            + " FROM  tbm_cabguirem as a "
                            //se agrega por cliente retira        
                            + " INNER JOIN ("
                            + " tbm_detguirem AS a2 "
                            + " INNER JOIN tbr_detMovInv AS b1 ON a2.co_empRel=b1.co_emp AND a2.co_locRel=b1.co_loc AND a2.co_tipDocRel=b1.co_tipDoc AND a2.co_docRel=b1.co_doc AND a2.co_regRel=b1.co_reg"
                            + " INNER JOIN tbr_detMovInv AS b2 ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc AND b1.co_reg=b2.co_reg"
                            + " )"
                            + //fin
                            "  ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                            + "  INNER JOIN tbm_loc AS a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc) "
                            + "  INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a.co_emp AND a4.co_bod=a.co_ptopar ) "
                            + "  INNER JOIN tbm_cabTipDoc AS a5 ON (a5.co_emp=a.co_emp AND a5.co_loc=a.co_loc AND a5.co_tipDoc=a.co_tipDoc) "
                            + "  INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) "
                            + "  WHERE  a.co_tipdoc in ( " + strAux + "  )  AND ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=" + txtCodBod.getText() + ") "
                            + "  AND a.st_reg NOT IN('I','E')  and case when a.ne_numdoc > 0 then a.st_tieguisec='N' else 1=1 end    " + sqlFil + "  "
                            + "  GROUP BY  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor, a3.tx_nom,  a.ne_numdoc, a.fe_doc, a.co_clides, a.tx_nomclides  ,a.co_ptopar ,a.ne_numorddes  "
                            + //se agrega por cliente retira
                            ", a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel"
                            + ", b1.co_empRel, b1.co_locRel, b1.co_tipDocRel, b1.co_docRel, b1.co_regRel"
                            + ", b2.co_empRel, b2.co_locRel, b2.co_tipDocRel, b2.co_docRel, b2.co_regRel"
                            + " , a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel"
                            + //fin  
                            "  ORDER BY a.fe_doc "
                            + "   ) as x  where   ( ne_numdoc > 0 or ne_numorddes > 0 ) and exitcr > 0 ";
                } 
                else if (objZafParSis.getCodigoMenu() == 1974) //Ingresos provisionales de mercadería a Bodega
                {
                    strSql = "SELECT -1 AS co_empComRel, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor , a3.tx_nom as tx_deslar,  a.ne_numdoc, a.fe_doc, a.co_clides as co_cli,"
                            + " a.tx_nomclides as tx_nomcli, null as nd_tot, a.co_ptopar, a.ne_numorddes as ne_numorddes "
                            + " FROM  tbm_cabguirem as a "
                            + "  INNER JOIN tbm_detguirem AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                            + "  INNER JOIN tbm_loc AS a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc) "
                            + "  INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a.co_emp AND a4.co_bod=a.co_ptopar ) "
                            + "  INNER JOIN tbm_cabTipDoc AS a5 ON (a5.co_emp=a.co_emp AND a5.co_loc=a.co_loc AND a5.co_tipDoc=a.co_tipDoc) "
                            + "  INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) "
                            + "  WHERE  a.co_tipdoc in ( " + strAux + "  )  AND ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=" + txtCodBod.getText() + ") "
                            + "  AND a.st_reg NOT IN('I','E') and a.ne_numdoc > 0  AND a.st_tieguisec='N'  " + sqlFil + "  "
                            + "  GROUP BY  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor, a3.tx_nom,  a.ne_numdoc, a.fe_doc, a.co_clides, a.tx_nomclides  ,a.co_ptopar, a.ne_numorddes  "
                            + "  ORDER BY a.ne_numdoc ";
                }
                else if (objZafParSis.getCodigoMenu() == 286)//Confirmación de Ingreso
                {
                    strSql = "SELECT -1 AS co_empComRel,a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor, a3.tx_nom as tx_deslar , a.ne_numdoc, a.fe_doc, a.co_cli, a.tx_nomcli , a2.co_bod as co_ptopar "
                            + " ,a2.tx_nombodorgdes , '' as ne_numorddes, '' AS tx_motMovInv "
                            + " FROM  tbm_cabmovinv as a "
                            + " INNER JOIN tbm_detmovinv AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                            + " INNER JOIN tbm_loc AS a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc) "
                            + " INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a.co_emp AND a4.co_bod=a2.co_bod ) "
                            + " INNER JOIN tbm_cabTipDoc AS a5 ON (a5.co_emp=a.co_emp AND a5.co_loc=a.co_loc AND a5.co_tipDoc=a.co_tipDoc) "
                            + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a2.co_bod) "
                            + " WHERE a.co_tipdoc in ( " + strAux + "  ) AND a.st_reg not in ('E','I') AND ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + ""
                            + " AND a6.co_bodGrp=" + txtCodBod.getText() + ")  "
                            + " AND ( a.co_locrelsoldevven IS NULL OR a.co_locrelsoldevven=0 ) "
                            + " AND ( a.co_tipdocrelsoldevven IS NULL OR a.co_tipdocrelsoldevven=0 )  "
                            + " AND ( a.co_docrelsoldevven IS NULL OR a.co_docrelsoldevven=0 )   "
                            + " AND  a2.nd_can > 0  and  a2.st_meringegrfisbod='S'   " + sqlFil + "  "
                            + " GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor, a3.tx_nom , a.ne_numdoc, a.fe_doc, a.co_cli, a.tx_nomcli, a2.co_bod ,a2.tx_nombodorgdes  "
                            + " ORDER BY a.fe_Doc,a.ne_numdoc ";
                }
                else 
                {
                    strSql = "SELECT -1 AS co_empComRel,a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor, a3.tx_nom as tx_deslar , a.ne_numdoc, a.fe_doc, a.co_cli, a.tx_nomcli , a2.co_bod as co_ptopar "
                            + " ,a2.tx_nombodorgdes , '' as ne_numorddes, '' AS tx_motMovInv "
                            + " FROM  tbm_cabmovinv as a "
                            + " INNER JOIN tbm_detmovinv AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                            + " INNER JOIN tbm_loc AS a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc) "
                            + " INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a.co_emp AND a4.co_bod=a2.co_bod ) "
                            + " INNER JOIN tbm_cabTipDoc AS a5 ON (a5.co_emp=a.co_emp AND a5.co_loc=a.co_loc AND a5.co_tipDoc=a.co_tipDoc) "
                            + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a2.co_bod) "
                            + " WHERE a.st_reg not in ('E','I')   and ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=" + txtCodBod.getText() + ")  "
                            + " AND ( a.co_locrelsoldevven IS NULL OR a.co_locrelsoldevven=0 ) "
                            + " AND ( a.co_tipdocrelsoldevven IS NULL OR a.co_tipdocrelsoldevven=0 )  "
                            + " AND ( a.co_docrelsoldevven IS NULL OR a.co_docrelsoldevven=0 )   "
                            + " AND  a2.nd_can > 0  and  a2.st_meringegrfisbod='S'   " + sqlFil + "  "
                            + " GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor, a3.tx_nom , a.ne_numdoc, a.fe_doc, a.co_cli, a.tx_nomcli, a2.co_bod ,a2.tx_nombodorgdes  "
                            + " ORDER BY a.fe_Doc,a.ne_numdoc ";
                }
                System.out.println("consultarDat(): CódigoMenu=> " + objZafParSis.getCodigoMenu() + " SQL=> " + strSql);
                
                rstLoc = stmLoc.executeQuery(strSql);
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
                    vecReg.add(INT_TBL_NUMGUIDES, rstLoc.getString("ne_numorddes"));
                    vecReg.add(INT_TBL_MOT_TRA, rstLoc.getString("tx_motMovInv"));
                    vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc"));
                    vecReg.add(INT_TBL_CODCLIPRO, rstLoc.getString("co_cli"));
                    if (rstLoc.getInt("co_tipdoc") == 46)
                    {
                        vecReg.add(INT_TBL_NOMCLIPRO, rstLoc.getString("tx_nombodorgdes"));
                    } 
                    else
                    {
                        vecReg.add(INT_TBL_NOMCLIPRO, rstLoc.getString("tx_nomcli"));
                    }
                    vecReg.add(INT_TBL_CODBODGUIA, rstLoc.getString("co_ptopar"));
                    vecReg.add(INT_TBL_COD_EMP_COM_REL, rstLoc.getString("co_empComRel"));

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

                tabFil.setSelectedIndex(1);

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

    public void BuscarTipDoc(String campo, String strBusqueda, int tipo) {
        objVenConTipdoc.setTitle("Listado de Tipos Documentos");
        if (objVenConTipdoc.buscar(campo, strBusqueda)) {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
            strCodTipDoc = objVenConTipdoc.getValueAt(1);
            strDesCorTipDoc = objVenConTipdoc.getValueAt(2);
            strDesLarTipDoc = objVenConTipdoc.getValueAt(3);
            optFil.setSelected(true);
        } else {
            objVenConTipdoc.setCampoBusqueda(tipo);
            objVenConTipdoc.cargarDatos();
            objVenConTipdoc.show();
            if (objVenConTipdoc.getSelectedButton() == objVenConTipdoc.INT_BUT_ACE) {
                txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
                txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
                txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
                strCodTipDoc = objVenConTipdoc.getValueAt(1);
                strDesCorTipDoc = objVenConTipdoc.getValueAt(2);
                strDesLarTipDoc = objVenConTipdoc.getValueAt(3);
                optFil.setSelected(true);
            } else {
                txtCodTipDoc.setText(strCodTipDoc);
                txtDesCorTipDoc.setText(strDesCorTipDoc);
                txtDesLarTipDoc.setText(strDesLarTipDoc);
            }
        }
    }

    public void BuscarCliente(String campo, String strBusqueda, int tipo) {
        objVenConCLi.setTitle("Listado de Clientes");
        if (objVenConCLi.buscar(campo, strBusqueda)) {
            txtCodCli.setText(objVenConCLi.getValueAt(1));
            txtNomCli.setText(objVenConCLi.getValueAt(2));
            optFil.setSelected(true);
        } else {
            objVenConCLi.setCampoBusqueda(tipo);
            objVenConCLi.cargarDatos();
            objVenConCLi.show();
            if (objVenConCLi.getSelectedButton() == objVenConCLi.INT_BUT_ACE) {
                txtCodCli.setText(objVenConCLi.getValueAt(1));
                txtNomCli.setText(objVenConCLi.getValueAt(2));
                optFil.setSelected(true);
            } else {
                txtCodCli.setText(strCodCli);
                txtNomCli.setText(strDesCli);
            }
        }
    }

    public void BuscarVendedor(String campo, String strBusqueda, int tipo) {
        objVenConVen.setTitle("Listado de Vendedores");
        if (objVenConVen.buscar(campo, strBusqueda)) {
            txtCodVen.setText(objVenConVen.getValueAt(1));
            txtNomVen.setText(objVenConVen.getValueAt(2));
            optFil.setSelected(true);
        } else {
            objVenConVen.setCampoBusqueda(tipo);
            objVenConVen.cargarDatos();
            objVenConVen.show();
            if (objVenConVen.getSelectedButton() == objVenConVen.INT_BUT_ACE) {
                txtCodVen.setText(objVenConVen.getValueAt(1));
                txtNomVen.setText(objVenConVen.getValueAt(2));
                optFil.setSelected(true);
            } else {
                txtCodVen.setText(strCodVen);
                txtNomVen.setText(strDesVen);
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
            dispose();
        }
    }

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
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
                case INT_TBL_NUMGUIDES:
                    strMsg = "Número de Orden de Despacho";
                    break;
                case INT_TBL_MOT_TRA:
                    strMsg = "Motivo por el cual se realizó la transferencia";
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
    private javax.swing.JButton butBusTipDoc;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butLim;
    private javax.swing.JButton butVen;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkDocConPar;
    private javax.swing.JCheckBox chkDocConTot;
    private javax.swing.JCheckBox chkDocPenCon;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipdoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFilDat;
    private javax.swing.JPanel panFilGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenFil;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFil;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomVen;
    // End of variables declaration//GEN-END:variables
}

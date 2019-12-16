/*
 * ZafCom23_02.java
 * 
 */
package Compras.ZafCom23;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author sistemas9
 */
public class ZafCom23_02 extends javax.swing.JDialog 
{
    private static final int INT_TBL_LINEA = 0;
    private static final int INT_TBL_CODEMP = 1;
    private static final int INT_TBL_CODLOC = 2;
    private static final int INT_TBL_TIPDOC = 3;
    private static final int INT_TBL_DCTIDO = 4;
    private static final int INT_TBL_DLTIDO = 5;
    private static final int INT_TBL_CODDOC = 6;
    private static final int INT_TBL_NUMDOC = 7;
    private static final int INT_TBL_FECDOC = 8;
    private static final int INT_TBL_BUTVIS = 9;
    
    private ZafParSis objParSis;
    private ZafTblMod objTblMod;
    private ZafUtil objUti;
    private ZafThreadGUI objThrGUI;
    private String strSQL = "";
    public boolean blnEst = false;
    javax.swing.JInternalFrame JIntFra;

    /**
     * Creates new form ZafCom23_02
     */
    public ZafCom23_02(java.awt.Frame parent, boolean modal,  Librerias.ZafParSis.ZafParSis obj, javax.swing.JInternalFrame JIntFraPri,  String strSQLAux) 
    {
        super(parent, modal);
        try 
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            JIntFra = JIntFraPri;
            initComponents();
            objUti = new ZafUtil();
            strSQL = strSQLAux;
            lblTit.setText("Solicitudes de Devolucion");
        } 
        catch (CloneNotSupportedException e){   objUti.mostrarMsgErr_F1(this, e);      }   
    }
    
    public void Configura_ventana_consulta() 
    {
        Configurartabla();
        if (objThrGUI == null)
        {
            objThrGUI = new ZafThreadGUI();
            objThrGUI.start();
        }
    }
    
    private boolean Configurartabla() 
    {
        boolean blnRes = false;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            //Almacena las cabeceras
            Vector vecCab = new Vector();
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CODEMP, "Cód.Emp");
            vecCab.add(INT_TBL_CODLOC, "Cód.Loc");
            vecCab.add(INT_TBL_TIPDOC, "Cód.Tip.Doc");
            vecCab.add(INT_TBL_DCTIDO, "Tip.Doc.");
            vecCab.add(INT_TBL_DLTIDO, "Tipo Documento");
            vecCab.add(INT_TBL_CODDOC, "Cód.Doc.");
            vecCab.add(INT_TBL_NUMDOC, "Num.Doc.");
            vecCab.add(INT_TBL_FECDOC, "Fec.Doc.");
            vecCab.add(INT_TBL_BUTVIS, "");

            objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);

            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_BUTVIS);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_TIPDOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DCTIDO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CODDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(80);

            /*Columnas que van a ser ocultas */
            ArrayList arlColHid = new ArrayList();
            arlColHid.add("" + INT_TBL_DLTIDO);
            arlColHid.add("" + INT_TBL_CODDOC);
            arlColHid.add("" + INT_TBL_BUTVIS);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid = null;

            tblDat.getTableHeader().setReorderingAllowed(false);

            tcmAux = null;
            setEditable(true);
            blnRes = true;
        }
        catch (Exception e) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        panTabGenCen = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panSur = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
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

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("jLabel1");
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        panTabGen.setLayout(new java.awt.BorderLayout());

        panTabGenCen.setLayout(new java.awt.BorderLayout());

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
        jScrollPane1.setViewportView(tblDat);

        panTabGenCen.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panTabGen.add(panTabGenCen, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panSur.setLayout(new java.awt.BorderLayout());

        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        jPanel8.add(butCan);

        panSur.add(jPanel8, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo"); // NOI18N
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

        panSur.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panSur, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(350, 350));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        String strMsg = "¿Está seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == 0) 
        {
            blnEst = false;
            System.gc();
            dispose();
        }
    }//GEN-LAST:event_formWindowClosing

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        String strMsg = "¿Está seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == 0) 
        {
            blnEst = false;
            System.gc();
            dispose();
        }
    }//GEN-LAST:event_butCanActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Configura_ventana_consulta();
    }//GEN-LAST:event_formWindowOpened

    private class ZafThreadGUI extends Thread 
    {
        public void run() 
        {
            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);
            consultar();
            objThrGUI = null;
        }
    }

    private void consultar()
    {
        java.sql.Connection conn;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try 
        {
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null)
            {
                stm = conn.createStatement();

                Vector vecData = new Vector();
                rst = stm.executeQuery(strSQL);
                while (rst.next()) 
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_CODEMP, rst.getString("co_emp"));
                    vecReg.add(INT_TBL_CODLOC, rst.getString("co_loc"));
                    vecReg.add(INT_TBL_TIPDOC, rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBL_DCTIDO, rst.getString("tx_DesCor"));
                    vecReg.add(INT_TBL_DLTIDO, rst.getString("tx_DesLar"));
                    vecReg.add(INT_TBL_CODDOC, rst.getString("co_Doc"));
                    vecReg.add(INT_TBL_NUMDOC, rst.getString("ne_numDoc"));
                    vecReg.add(INT_TBL_FECDOC, rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_BUTVIS, "");
                    vecData.add(vecReg);
                }
                objTblMod.setData(vecData);
                tblDat.setModel(objTblMod);

                rst.close();
                rst = null;
                stm.close();
                stm = null;
                conn.close();
                conn = null;
            }
            lblMsgSis.setText("Listo");
            pgrSis.setValue(0);
            pgrSis.setIndeterminate(false);
        } 
        catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); } 
        catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e); }
    }

    //<editor-fold defaultstate="collapsed" desc="Variables declaration - do not modify">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panSur;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTabGenCen;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JTabbedPane tabGen;
    public javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
 //</editor-fold>  

}

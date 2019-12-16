/*
 * ZafCom23_01.java
 * 
 */
package Compras.ZafCom23;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author jayapata
 */
public class ZafCom23_01 extends javax.swing.JDialog 
{
    final int INT_TBL_LINEA = 0;
    final int INT_TBL_CHKDOC = 1;
    final int INT_TBL_NUMDOC = 2;
    final int INT_TBL_CODLOC = 3;
    final int INT_TBL_TIPDOC = 4;
    final int INT_TBL_DCTIDO = 5;
    final int INT_TBL_DLTIDO = 6;
    final int INT_TBL_CODDOC = 7;
    final int INT_TBL_FECDOC = 8;
    final int INT_TBL_BUTVIS = 9;
    
    private ZafParSis objParSis;
    private ZafTblMod objTblMod;
    private ZafUtil objUti;
    private ZafThreadGUI objThrGUI;
    private String strSQL = "";
    public boolean blnAcepta = false;
    public boolean blnEst = false;
    javax.swing.JTextField txtCodTipDoc = new javax.swing.JTextField();
    javax.swing.JInternalFrame JIntFra;

    /**
     * Creates new form ZafCom23_01
     */
    public ZafCom23_01(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis obj, String strCodBod, javax.swing.JInternalFrame JIntFraPri, String strSqlPri) 
    {
        super(parent, modal);
        try 
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            JIntFra = JIntFraPri;
            initComponents();
            objUti = new ZafUtil();
            strSQL = strSqlPri;
            lblTit.setText(objParSis.getNombreMenu());
        } 
        catch (CloneNotSupportedException e) { objUti.mostrarMsgErr_F1(this, e);  }
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
            vecCab.add(INT_TBL_CHKDOC, "");
            vecCab.add(INT_TBL_NUMDOC, "");
            vecCab.add(INT_TBL_CODLOC, "");
            vecCab.add(INT_TBL_TIPDOC, "");
            vecCab.add(INT_TBL_DCTIDO, "Tip.Doc.");
            vecCab.add(INT_TBL_DLTIDO, "");
            vecCab.add(INT_TBL_CODDOC, "Num.Doc.");
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
            vecAux.add("" + INT_TBL_CHKDOC);
            vecAux.add("" + INT_TBL_BUTVIS);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DCTIDO).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_CODDOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(100);

            /* Aqui se agrega las columnas que van a ser ocultas */
            ArrayList arlColHid = new ArrayList();
            arlColHid.add("" + INT_TBL_CHKDOC);
            arlColHid.add("" + INT_TBL_NUMDOC);
            arlColHid.add("" + INT_TBL_CODLOC);
            arlColHid.add("" + INT_TBL_TIPDOC);
            arlColHid.add("" + INT_TBL_CODLOC);
            arlColHid.add("" + INT_TBL_DLTIDO);
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

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-350)/2, (screenSize.height-350)/2, 350, 350);
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
                    vecReg.add(INT_TBL_CHKDOC, "");
                    vecReg.add(INT_TBL_NUMDOC, "");
                    vecReg.add(INT_TBL_CODLOC, "");
                    vecReg.add(INT_TBL_TIPDOC, "");
                    vecReg.add(INT_TBL_DCTIDO, rst.getString("tx_descor"));
                    vecReg.add(INT_TBL_DLTIDO, "");
                    vecReg.add(INT_TBL_CODDOC, rst.getString("ne_numdoc"));
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

/*
 * ZafCom51_01.java
 *
 * 
 */
package Compras.ZafCom51;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import java.sql.DriverManager;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
/**
 *
 * @author jayapata
 */
public class ZafCom51_01 extends javax.swing.JDialog 
{
    //Constantes: Jtable
    private static final int INT_TBL_LINEA = 0;
    private static final int INT_TBL_CODSEG = 1;
    private static final int INT_TBL_FECDOC = 2;
    private static final int INT_TBL_CODEMP = 3;
    private static final int INT_TBL_CODLOC = 4;
    private static final int INT_TBL_CODTIPDOC = 5;
    private static final int INT_TBL_DESCOR = 6;
    private static final int INT_TBL_DESLAR = 7;
    private static final int INT_TBL_CODDOC = 8;
    private static final int INT_TBL_NUMDOC = 9;
    private static final int INT_TBL_NUMORD = 10;
    private static final int INT_TBL_CANPENTOT = 11;
    private static final int INT_TBL_CANPENBOD = 12;
    private static final int INT_TBL_CANPENDES = 13;
    private static final int INT_TBL_CODCLI = 14;
    private static final int INT_TBL_NOMCLI = 15;
    
    //Variables Generales.
    private ZafParSis objZafParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafMouMotAda objMouMotAda;
    private ZafTblTot objTblTot;
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafThreadGUI objThrGUI;

    private boolean blnMosOrdDes;
    private String strSQL = "";


    /**
     * Creates new form ZafCom51_01
     */
    public ZafCom51_01(java.awt.Frame parent, boolean modal, ZafParSis objParsis, String strSqlAux, boolean blnMosOrdDes ) 
    {
        super(parent, modal);
        try 
        {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) objParsis.clone();
            strSQL = strSqlAux;
            this.blnMosOrdDes=blnMosOrdDes;
            initComponents();
            this.setTitle("" + objZafParSis.getNombreMenu());
            lblTit.setText("Listado de documentos pendientes de confirmar");
            objUti = new ZafUtil();

            configurarFrm();
        }
        catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }
    }


    private boolean configurarFrm() 
    {
        boolean blnres = false;

        Vector vecCab = new Vector();    //Almacena las cabeceras
        vecCab.clear();

        vecCab.add(INT_TBL_LINEA, "");
        vecCab.add(INT_TBL_CODSEG, "Cod.Seg.");
        vecCab.add(INT_TBL_FECDOC, "Fec.Doc");
        vecCab.add(INT_TBL_CODEMP, "Cod.Emp ");
        vecCab.add(INT_TBL_CODLOC, "Cod.Loc");
        vecCab.add(INT_TBL_CODTIPDOC, "Cod.Tip.Doc");
        vecCab.add(INT_TBL_DESCOR, "Des.Cor");
        vecCab.add(INT_TBL_DESLAR, "Des.Lar");
        vecCab.add(INT_TBL_CODDOC, "Cod.Doc");
        vecCab.add(INT_TBL_NUMDOC, "Num.Doc");
        vecCab.add(INT_TBL_NUMORD, "Num.Ord.Des");
        vecCab.add(INT_TBL_CANPENTOT, "Can.Pen.Tot");
        vecCab.add(INT_TBL_CANPENBOD, "Can.Pen.Bod");
        vecCab.add(INT_TBL_CANPENDES, "Can.Pen.Des");
        vecCab.add(INT_TBL_CODCLI, "Cod.Cli");
        vecCab.add(INT_TBL_NOMCLI, "Nom.Cli");
        
        objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        //Configurar JTable: Establecer la fila de cabecera.
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        objMouMotAda = new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

        //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
        objTblMod.setColumnDataType(INT_TBL_CANPENTOT, objTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_CANPENBOD, objTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_CANPENDES, objTblMod.INT_COL_DBL, new Integer(0), null);

        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
        tblDat.getTableHeader().setReorderingAllowed(false);
        
        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CODSEG).setPreferredWidth(55);
        tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(75);
        tcmAux.getColumn(INT_TBL_DESCOR).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DESLAR).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(75);
        tcmAux.getColumn(INT_TBL_NUMORD).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(40);
        tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_CANPENTOT).setPreferredWidth(40);
        tcmAux.getColumn(INT_TBL_CANPENBOD).setPreferredWidth(75);
        tcmAux.getColumn(INT_TBL_CANPENDES).setPreferredWidth(75);
        
        //Columnas Ocultas
        objTblMod.addSystemHiddenColumn(INT_TBL_CODEMP, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_CODLOC, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_CODTIPDOC, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_CODDOC, tblDat);
        if(!blnMosOrdDes){
            objTblMod.addSystemHiddenColumn(INT_TBL_NUMORD, tblDat);
        }
        //if(objZafParSis.getCodigoUsuario() != 1)  //Cantidad Pendiente Total
          objTblMod.addSystemHiddenColumn(INT_TBL_CANPENTOT, tblDat);

        //Configurar JTable: Editor de la tabla.
        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

        objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        // objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
        tcmAux.getColumn(INT_TBL_CANPENTOT).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_CANPENBOD).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_CANPENDES).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl = null;

        int intCol[] = {INT_TBL_CANPENTOT, INT_TBL_CANPENBOD, INT_TBL_CANPENDES};
        objTblTot = new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);

        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        
        //Configurar JTable: Editor de búsqueda.
        objTblBus=new ZafTblBus(tblDat);
        
        //Configurar JTable: Establecer la clase que controla el ordenamiento.
        objTblOrd=new ZafTblOrd(tblDat);

        return blnres;
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
                case INT_TBL_CODSEG:
                    strMsg = "Código de Seguimiento";
                    break;                          
                case INT_TBL_FECDOC:
                    strMsg = "Fecha documento. ";
                    break;
                case INT_TBL_DESCOR:
                    strMsg = "Descripcion corta del tipo documento.";
                    break;
                case INT_TBL_DESLAR:
                    strMsg = "Descripcion larga del tipo documento.";
                    break;
                case INT_TBL_NUMDOC:
                    strMsg = "Número de documento. ";
                    break;
                case INT_TBL_NUMORD:
                    strMsg = "Número de orden de despacho. ";
                    break;
                case INT_TBL_CANPENTOT:
                    strMsg = "¨Cantidad Total pendiente de confirmar.";
                    break;
                case INT_TBL_CANPENBOD:
                    strMsg = "¨Cantidad Total pendiente de confirmar (Bodega).";
                    break;
                case INT_TBL_CANPENDES:
                    strMsg = "¨Cantidad Total pendiente de confirmar (Despacho).";
                    break;
                case INT_TBL_CODCLI:
                    strMsg = "Codigo cliente/proveedor. ";
                    break;
                case INT_TBL_NOMCLI:
                    strMsg = "Nombre del cliente/proveedor..";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panReg = new javax.swing.JPanel();
        panGen = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBut = new javax.swing.JPanel();
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

        lblTit.setText("Listado de documentos pendientes de confirmar");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panReg.setLayout(new java.awt.BorderLayout());

        panDat.setLayout(new java.awt.BorderLayout());

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

        panDat.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot.setViewportView(tblTot);

        panDat.add(spnTot, java.awt.BorderLayout.SOUTH);

        panGen.addTab("General", panDat);

        panReg.add(panGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panReg, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCan.setText("Cancelar");
        butCan.setPreferredSize(new java.awt.Dimension(90, 23));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBut.add(butCan);

        panBar.add(panBut, java.awt.BorderLayout.EAST);

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
        setBounds((screenSize.width-550)/2, (screenSize.height-400)/2, 550, 400);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (objThrGUI == null) 
        {
            objThrGUI = new ZafThreadGUI();
            objThrGUI.start();
        }
    }//GEN-LAST:event_formWindowOpened

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        exitForm();
    }//GEN-LAST:event_butCanActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        exitForm();
    }//GEN-LAST:event_formWindowClosing

    protected void finalize() throws Throwable 
    {
        System.gc();
        super.finalize();
    }
        
    private void exitForm()
    {
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }

    private void MensajeInf(String strMensaje)
    {
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panDat;
    private javax.swing.JTabbedPane panGen;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panReg;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    // End of variables declaration//GEN-END:variables


private class ZafThreadGUI extends Thread 
    {
        public void run() 
        {
            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);
            cargarDat();
            objThrGUI = null;
        }
    }
  
    private boolean cargarDat() 
    {
        boolean blnRes = false;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try 
        {
            conn = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) 
            {
                stmLoc = conn.createStatement();
                java.util.Vector vecData = new java.util.Vector();

                strSql=strSQL;
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next())
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_CODSEG, rstLoc.getString("co_seg"));
                    vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc"));
                    vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp"));
                    vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc"));
                    vecReg.add(INT_TBL_CODTIPDOC, rstLoc.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_DESCOR, rstLoc.getString("tx_descor"));
                    vecReg.add(INT_TBL_DESLAR, rstLoc.getString("tx_deslar"));
                    vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc"));
                    vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc"));
                    vecReg.add(INT_TBL_NUMORD, rstLoc.getString("ne_numOrdDes"));
                    vecReg.add(INT_TBL_CANPENTOT, rstLoc.getString("nd_canPenTot"));
                    vecReg.add(INT_TBL_CANPENBOD, rstLoc.getString("nd_canPenBod"));
                    vecReg.add(INT_TBL_CANPENDES, rstLoc.getString("nd_canPenDes"));
                    vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli"));
                    vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nomcli"));
                    
                    vecData.add(vecReg);
                }
                rstLoc.close();
                rstLoc=null;

                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);


                objTblTot.calcularTotales();
                // objTblTot.setValueAt("" + INT_TBL_CANPENTOT );

                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;

                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);

            }
        }
        catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        System.gc();
        return blnRes;
    }
     
     
     

}

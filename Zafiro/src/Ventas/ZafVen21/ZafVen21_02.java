/*
 * ZafVen21_02.java
 *
 * 
 */
  
package Ventas.ZafVen21;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;
import java.util.Vector;

/**
 *  
 * @author  jayapata
 */
public class ZafVen21_02 extends javax.swing.JDialog 
{  
    private static final int INT_TBL_LINEA=0; 
    private static final int INT_TBL_CODEMP=1;
    private static final int INT_TBL_CODLOC=2;
    private static final int INT_TBL_COTIDO=3;
    private static final int INT_TBL_CODDOC=4;
    private static final int INT_TBL_ITMALT=5;
    private static final int INT_TBL_ITMALT2=6;
    private static final int INT_TBL_NOMITM=7;
    private static final int INT_TBL_CANGUI=8;
    private static final int INT_TBL_CANCON=9;
    private static final int INT_TBL_CANNUNENV=10;
    private static final int INT_TBL_CANPEN=11;
    
    private ZafParSis objZafParSis;
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLblCanPen, objTblCelRenLblCanNEnv;
    private ZafUtil objUti;
    private ZafThreadGUI objThrGUI;
    public boolean blnAcepta = false;
    public boolean blnEst=false;
     
    String strSql02="";
            
    javax.swing.JInternalFrame JIntFra;

    java.awt.Color colFonColCanPen =new java.awt.Color(41,12,214);
    java.awt.Color colFonColCanNoEnv =new java.awt.Color(224,79,14);

    
    /** Creates new form ZafVen21_02 */
    public ZafVen21_02(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis obj,  javax.swing.JInternalFrame JIntFraPri, String strSql2 ) 
    {
        super(parent, modal);
        try
        {	
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    JIntFra=JIntFraPri;

            objTblCelRenLblCanPen = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLblCanNEnv = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        
            initComponents();
	    objUti = new ZafUtil();
 
            strSql02 = strSql2;

            lblTit.setText(objZafParSis.getNombreMenu());
                
        }
        catch (CloneNotSupportedException e){      objUti.mostrarMsgErr_F1(this, e);    }
    }
    
    
    public void Configura_ventana_consulta()
    {
        Configurartabla();    

        if (objThrGUI==null) 
        {
            objThrGUI=new ZafThreadGUI();
            objThrGUI.start();
        }
    }
     
    private boolean Configurartabla() 
    {
        boolean blnRes=false;
        try
        {
            //Configurar JTable: Establecer el modelo.
            Vector vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"");
	    vecCab.add(INT_TBL_CODEMP,"Cod.Emp");
            vecCab.add(INT_TBL_CODLOC,"Cod.Loc");
            vecCab.add(INT_TBL_COTIDO,"CodTipDoc");
            vecCab.add(INT_TBL_CODDOC,"Cod.Doc");
            vecCab.add(INT_TBL_ITMALT,"Cod.Itm");
            vecCab.add(INT_TBL_ITMALT2,"Cod.Alt.2");
            vecCab.add(INT_TBL_NOMITM,"Nom.Itm.");
            vecCab.add(INT_TBL_CANGUI, "Cantidad");
            vecCab.add(INT_TBL_CANCON,"Can.Con");
            vecCab.add(INT_TBL_CANNUNENV, "Can.No.Env.");
            vecCab.add(INT_TBL_CANPEN,"Can.Pen");

	    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
               
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);


            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            ZafMouMotAda objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            objTblMod.setColumnDataType(INT_TBL_CANGUI, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANCON, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANNUNENV, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANPEN, objTblMod.INT_COL_DBL, new Integer(0), null);

	    //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            //vecAux.add("" + INT_TBL_BUTVIS);
	    objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
	    
	    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();

	     //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_ITMALT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_ITMALT2).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_CANGUI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CANCON).setPreferredWidth(60);
             tcmAux.getColumn(INT_TBL_CANNUNENV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CANPEN).setPreferredWidth(60);

            Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();

            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_CANGUI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANCON).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLblCanPen.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLblCanPen.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCanPen.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblCanPen.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_CANPEN).setCellRenderer(objTblCelRenLblCanPen);
            objTblCelRenLblCanPen.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter()
            {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) 
                {
                    objTblCelRenLblCanPen.setBackground(java.awt.Color.WHITE);
                    objTblCelRenLblCanPen.setForeground(colFonColCanPen);
                }
            });

            objTblCelRenLblCanNEnv.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLblCanNEnv.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCanNEnv.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblCanNEnv.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_CANNUNENV).setCellRenderer(objTblCelRenLblCanNEnv);
            objTblCelRenLblCanNEnv.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
            public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    objTblCelRenLblCanNEnv.setBackground(java.awt.Color.WHITE);
                    objTblCelRenLblCanNEnv.setForeground(colFonColCanNoEnv);
            }
           });


            //Columnas Ocultas
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODEMP);
            arlColHid.add(""+INT_TBL_CODLOC);
            arlColHid.add(""+INT_TBL_COTIDO);
            arlColHid.add(""+INT_TBL_CODDOC);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;

	    tblDat.getTableHeader().setReorderingAllowed(false);
       
	    tcmAux=null;
	     
	    setEditable(true);
	    blnRes=true;
	}
        catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
        return blnRes;
    }
      
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_CODEMP:
                    strMsg="Código de empresa";
                    break;
                case INT_TBL_CODLOC:
                    strMsg="Código de local ";
                    break;
                case INT_TBL_COTIDO:
                    strMsg="Tipo de Documento ";
                    break;
                case INT_TBL_CODDOC:
                    strMsg="Codigo de Documento ";
                    break;
                case INT_TBL_ITMALT:
                    strMsg="Código alterno del item ";
                    break;
                case INT_TBL_ITMALT2:
                    strMsg="Código alterno 2 del item ";
                    break;                    
                case INT_TBL_NOMITM:
                    strMsg="Nombre del item ";
                    break;
                 case INT_TBL_CANGUI:
                    strMsg="Cantidad ";
                    break;
               case INT_TBL_CANCON:
                    strMsg="Cantidad confirmada. ";
                    break;
                case INT_TBL_CANNUNENV:
                    strMsg="Cantidad que no se despacho ";
                    break;
                 case INT_TBL_CANPEN:
                    strMsg="Cantidad pendiente de despacho ";
                    break;

                default:
                    strMsg=" ";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
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
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        panTabGenCen = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panSur = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
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

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la Ventana");
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
        spnDat.setViewportView(tblDat);

        panTabGenCen.add(spnDat, java.awt.BorderLayout.CENTER);

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
        panBot.add(butCan);

        panSur.add(panBot, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo"); // NOI18N
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

        panSur.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panSur, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(482, 282));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        blnEst=false;
        System.gc();
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        blnEst=false;
        System.gc();
        dispose();
    }//GEN-LAST:event_butCanActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
	Configura_ventana_consulta();
    }//GEN-LAST:event_formWindowOpened
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panSur;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTabGenCen;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabGen;
    public javax.swing.JTable tblDat;
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
    
    private void cargarDat() 
    {
        java.sql.Connection conn;
        try 
        {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) 
            {
                if (!consultar(conn, strSql02)) 
                {

                }

                conn.close();
                conn = null;

                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);

            }
        }
        catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e);   }
        catch (Exception e) {   objUti.mostrarMsgErr_F1(this, e);          }
    }

    private boolean consultar(java.sql.Connection conn, String strSql) 
    {
        boolean blnRes = false;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try 
        {
            if (conn != null)
            {
                stm = conn.createStatement();

                Vector vecData = new Vector();
                rst = stm.executeQuery(strSql);
                while (rst.next()) 
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_CODEMP, "");
                    vecReg.add(INT_TBL_CODLOC, "");
                    vecReg.add(INT_TBL_COTIDO, "");
                    vecReg.add(INT_TBL_CODDOC, "");
                    vecReg.add(INT_TBL_ITMALT, rst.getString("tx_codalt"));
                    vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codalt2"));
                    vecReg.add(INT_TBL_NOMITM, rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_CANGUI, rst.getString("nd_can"));
                    vecReg.add(INT_TBL_CANCON, rst.getString("nd_canCon"));
                    vecReg.add(INT_TBL_CANNUNENV, rst.getString("nd_canNunRec"));
                    vecReg.add(INT_TBL_CANPEN, rst.getString("nd_canTotPen"));

                    vecData.add(vecReg);
                    blnRes = true;
                }
                objTblMod.setData(vecData);
                tblDat.setModel(objTblMod);

                rst.close();
                rst = null;
                stm.close();
                stm = null;
            }
        }
        catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);          }
        return blnRes;
    }

}

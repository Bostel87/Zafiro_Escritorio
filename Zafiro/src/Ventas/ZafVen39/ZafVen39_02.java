/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafVen42_09.java
 *
 * Created on Ago 01, 2017, 3:40:00 PM
 */

package Ventas.ZafVen39;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author JoseMario
 */
public class ZafVen39_02 extends javax.swing.JInternalFrame {

    private Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    
    private ZafUtil objUti;

    final private int INT_TBL_LINEA=0;
    final private int INT_TBL_COD_EMP=1;
    final private int INT_TBL_COD_LOC=2;
    final private int INT_TBL_COD_TIP_DOC=3;
    final private int INT_TBL_COD_DOC=4;
    final private int INT_TBL_NUM_DOC=5;
    final private int INT_TBL_FEC_DOC=6;
    final private int INT_TBL_COD_CLI=7;
    final private int INT_TBL_NOM_CLI=8;
    final private int INT_TBL_COD_FOR_PAG=9;
    final private int INT_TBL_NOM_FOR_PAG=10;
    final private int INT_TBL_SUB=11;
    final private int INT_TBL_IVA=12;
    final private int INT_TBL_TOT=13;
    
     
    private ZafThreadGUI objThrGUI;
    public boolean blnAcepta = false;
    public boolean blnEst=false;

     private ZafTblTot objTblTot;                                //JTable de totales.
    javax.swing.JInternalFrame JIntFra; 
    private int  intCodVen, intCodCli=-1;
    private String strCodEmpLoc, strFecDoc;
    private boolean blnIsMostrarClientes, blnIsPorVendedor;
    public String strVersion="v 0.02";
        
    /** Creates new form ZafVen21_01 */
    
    // 
    public ZafVen39_02(Librerias.ZafParSis.ZafParSis obj,  javax.swing.JInternalFrame JIntFraPri,String CodEmpLoc, boolean IsPorVendedor, boolean MostrarClientes, int CodVen,int CodCli, String FecDoc) 
    {
        try
        {
	this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	JIntFra=JIntFraPri;
        initComponents();
	objUti = new ZafUtil();
        
         
      
        strCodEmpLoc=CodEmpLoc;
        blnIsMostrarClientes=MostrarClientes;
        blnIsPorVendedor=IsPorVendedor;
        intCodVen= CodVen;
        intCodCli = CodCli;
        strFecDoc=FecDoc;
        
       
        lblTit.setText(objZafParSis.getNombreMenu());
       }
        catch (CloneNotSupportedException e){           objUti.mostrarMsgErr_F1(this, e);      }
    }



    public void Configura_ventana_consulta(){
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
	    vecCab.add(INT_TBL_COD_EMP,"Cod.Emp");
            vecCab.add(INT_TBL_COD_LOC,"Cod.Loc");
            vecCab.add(INT_TBL_COD_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_COD_DOC,"Cod.Doc");           
            vecCab.add(INT_TBL_NUM_DOC,"Num.Fac.");
            vecCab.add(INT_TBL_FEC_DOC,"Fec.doc.");
            vecCab.add(INT_TBL_COD_CLI, "Cód.Cli.");
            vecCab.add(INT_TBL_NOM_CLI,"Cliente");
            vecCab.add(INT_TBL_COD_FOR_PAG,"Cód.For.Pag.");
            vecCab.add(INT_TBL_NOM_FOR_PAG,"Forma de Pago");
            vecCab.add(INT_TBL_SUB,"Subtotal");
            vecCab.add(INT_TBL_IVA,"IVA");
            vecCab.add(INT_TBL_TOT,"Total");
            
	    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);

            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);

            objTblMod.setColumnDataType(INT_TBL_SUB, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_IVA, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_TOT, objTblMod.INT_COL_DBL, new Integer(0), null);

	    

            tblDat.getTableHeader().setReorderingAllowed(false);
            ZafMouMotAda objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


	    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();

	     //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_COD_TIP_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_COD_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_NUM_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_COD_CLI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NOM_CLI).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_COD_FOR_PAG).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NOM_FOR_PAG).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_SUB).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_IVA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_TOT).setPreferredWidth(70);
            
            
             
            Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();

            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_SUB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_IVA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_TOT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;


             /* Aqui se agrega las columnas que van
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_COD_EMP);
            arlColHid.add(""+INT_TBL_COD_LOC);
            arlColHid.add(""+INT_TBL_COD_TIP_DOC);
            arlColHid.add(""+INT_TBL_NUM_DOC);
            arlColHid.add(""+INT_TBL_COD_FOR_PAG);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;



	    tblDat.getTableHeader().setReorderingAllowed(false);

	    tcmAux=null;
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_SUB, INT_TBL_IVA, INT_TBL_TOT };
            objTblTot=new ZafTblTot(panDat, panTot, tblDat, tblTot, intCol);

	    setEditable(true);
	    blnRes=true;
	 }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
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

                if (!consultar(conn)) 
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


    private String strSql;
    private boolean consultar(java.sql.Connection conn)
    {
        boolean blnRes = false;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try{
            if(conn!=null){
                stm=conn.createStatement();
                Vector vecData = new Vector();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_loc,  a1.fe_cot, a1.co_cot, a1.co_cli, a3.tx_nom as tx_nomCli, a1.co_forPag, a2.tx_des as tx_nomForPag,  \n";
                strSql+="         ROUND((a1.nd_sub ),2) as nd_sub,  ROUND((a1.nd_valIva ),2) as nd_valIva,  ROUND((a1.nd_tot ),2) as nd_tot \n";
                strSql+=" FROM tbm_cabCotVen as a1 \n";
                strSql+=" INNER JOIN tbm_cabForPag as a2 ON (a1.co_emp=a2.co_emp AND a1.co_forPag=a2.co_forPag) \n ";
                strSql+=" INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) ";
                strSql+=" WHERE a1.st_reg NOT IN ('I') ";
                strSql+=strCodEmpLoc;
                strSql+=strFecDoc;
                if(blnIsPorVendedor){
                    strSql+=" AND a1.co_ven="+intCodVen;
                }
                else{
                    strSql+=" AND a1.co_usrIng="+intCodVen;
                }
                if(intCodCli>0){
                    strSql+=" AND a1.co_cli = "+intCodCli;
                }
                strSql+=" ORDER BY a1.co_cot ";
                System.out.println("ZafVen39_02: " + strSql);
                rst = stm.executeQuery(strSql);
                while(rst.next()){
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_COD_EMP, rst.getString("co_emp") );
                    vecReg.add(INT_TBL_COD_LOC, rst.getString("co_loc") );
                    vecReg.add(INT_TBL_COD_TIP_DOC, "" );
                    vecReg.add(INT_TBL_COD_DOC, rst.getString("co_cot") );                 
                    vecReg.add(INT_TBL_NUM_DOC, "" );
                    vecReg.add(INT_TBL_FEC_DOC, rst.getString("fe_cot")  );
                    vecReg.add(INT_TBL_COD_CLI, rst.getString("co_cli") );
                    vecReg.add(INT_TBL_NOM_CLI, rst.getString("tx_nomCli") );
                    vecReg.add(INT_TBL_COD_FOR_PAG, rst.getString("co_forPag") );
                    vecReg.add(INT_TBL_NOM_FOR_PAG, rst.getString("tx_nomForPag") );
                    vecReg.add(INT_TBL_SUB, rst.getString("nd_sub") );
                    vecReg.add(INT_TBL_IVA, rst.getString("nd_valIva") );
                    vecReg.add(INT_TBL_TOT, rst.getString("nd_tot") );
                    vecData.add(vecReg);
                    blnRes=true;
                }
                objTblMod.setData(vecData);
                tblDat.setModel(objTblMod);
                objTblTot.calcularTotales();
                rst.close();
                rst=null;
                stm.close();
                stm = null;
            }
        }
        catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);          }
        return blnRes;
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
        panDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panSur = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
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
        panDat.setViewportView(tblDat);

        panTabGenCen.add(panDat, java.awt.BorderLayout.CENTER);

        panTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        panTot.setViewportView(tblTot);

        panTabGenCen.add(panTot, java.awt.BorderLayout.SOUTH);

        panTabGen.add(panTabGenCen, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panSur.setLayout(new java.awt.BorderLayout());

        butCan.setText("Cerrar");
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

        setBounds(0, 0, 350, 300);
    }// </editor-fold>//GEN-END:initComponents

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        System.gc();
        dispose();
}//GEN-LAST:event_butCanActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Configura_ventana_consulta();

    }//GEN-LAST:event_formInternalFrameOpened


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panCen;
    private javax.swing.JScrollPane panDat;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panSur;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTabGenCen;
    private javax.swing.JScrollPane panTot;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JTabbedPane tabGen;
    public javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    // End of variables declaration//GEN-END:variables




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
                 
                case INT_TBL_NUM_DOC:
                    strMsg="Numero del documento. ";
                    break;
                case INT_TBL_FEC_DOC:
                    strMsg="Fecha del Documento. ";
                    break;
                case INT_TBL_COD_CLI:
                    strMsg="Código del Cliente. ";
                    break;
                case INT_TBL_NOM_CLI:
                    strMsg="Nombre del Cliente. ";
                    break;
                case INT_TBL_COD_FOR_PAG:
                    strMsg="Código de la FOrma de Pago. ";
                    break;
                case INT_TBL_NOM_FOR_PAG:
                    strMsg="Nombre de la Forma de Pago. ";
                    break;
                case INT_TBL_SUB:
                    strMsg="Subtotal. ";
                    break;
                case INT_TBL_IVA:
                    strMsg="IVA ";
                    break;
                    
                case INT_TBL_TOT:
                    strMsg="Total. ";
                    break;
                default:
                    strMsg=" ";
                    break;

            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

}

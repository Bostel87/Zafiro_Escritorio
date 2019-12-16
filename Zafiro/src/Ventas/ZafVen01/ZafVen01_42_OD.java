/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafVen42_09.java
 *
 * Created on Ago 01, 2017, 3:40:00 PM
 */

package Ventas.ZafVen01;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author JoseMario
 */
public class ZafVen01_42_OD extends javax.swing.JInternalFrame {

    Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButDG1;
    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG1;
    ZafUtil objUti;

    final private int INT_TBL_LINEA=0;
    final private int INT_TBL_CODEMP=1;
    final private int INT_TBL_CODLOC=2;
    final private int INT_TBL_COTIDO=3;
    final private int INT_TBL_CODDOC=4;
    final private int INT_TBL_NUMOD=5;
    final private int INT_TBL_FECDOC=6;
    final private int INT_TBL_DATDOCORG=7;
    final private int INT_TBL_BUTVIS=8;

    private ZafThreadGUI objThrGUI;
    public boolean blnAcepta = false;
    public boolean blnEst=false;

    javax.swing.JInternalFrame JIntFra; 
    private int intCodEmp,intCodLoc,intCodCot;
    public String strVersion="v 0.01";
        
    /** Creates new form ZafVen21_01 */
    public ZafVen01_42_OD(Librerias.ZafParSis.ZafParSis obj,  javax.swing.JInternalFrame JIntFraPri,int CodEmp, int CodLoc, int CodCot) 
    {
        try
        {
	this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	JIntFra=JIntFraPri;
        initComponents();
	objUti = new ZafUtil();
        
        intCodEmp=CodEmp;
        intCodLoc=CodLoc;
        intCodCot=CodCot;

        objTblCelRenButDG1=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
        objTblCelEdiButGenDG1=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
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
	    vecCab.add(INT_TBL_CODEMP,"Cod.Emp");
            vecCab.add(INT_TBL_CODLOC,"Cod.Loc");
            vecCab.add(INT_TBL_COTIDO,"CodTipDoc");
            vecCab.add(INT_TBL_CODDOC,"Cod.Doc");           
            vecCab.add(INT_TBL_NUMOD,"Num.OD.");
            vecCab.add(INT_TBL_FECDOC,"Fec.doc.");
            vecCab.add(INT_TBL_DATDOCORG,"Obs.Doc.Ori");
            vecCab.add(INT_TBL_BUTVIS,"..");

	    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);

            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);

            objTblMod.setColumnDataType(INT_TBL_DATDOCORG, objTblMod.INT_COL_DBL, new Integer(0), null);

	    //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_BUTVIS);
	    objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            tblDat.getTableHeader().setReorderingAllowed(false);
            ZafMouMotAda objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


	    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();

	     //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(30);
           
            tcmAux.getColumn(INT_TBL_NUMOD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DATDOCORG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BUTVIS).setPreferredWidth(25);


            tcmAux.getColumn(INT_TBL_BUTVIS).setCellRenderer(objTblCelRenButDG1);

            objTblCelRenButDG1.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    switch (objTblCelRenButDG1.getColumnRender())
                    {
                       case INT_TBL_BUTVIS:
                               objTblCelRenButDG1.setText("...");
                       break;
                    }
                }
            });
            tcmAux.getColumn(INT_TBL_BUTVIS).setCellEditor(objTblCelEdiButGenDG1);

            objTblCelEdiButGenDG1.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                         case INT_TBL_BUTVIS:

                         break;

                       }
                     }
                    }
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {

                           case INT_TBL_BUTVIS:

                               llamarPantFactura( objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString()
                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString()
                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_COTIDO).toString()
                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                 );


                           break;

                        }
                }}
           });



            Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();

            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DATDOCORG).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl=null;


             /* Aqui se agrega las columnas que van
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODEMP);
            arlColHid.add(""+INT_TBL_CODLOC);
            arlColHid.add(""+INT_TBL_COTIDO);
            arlColHid.add(""+INT_TBL_CODDOC);
            //arlColHid.add(""+INT_TBL_BUTVIS);
//            arlColHid.add(""+INT_TBL_DATDOCORG);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;



	    tblDat.getTableHeader().setReorderingAllowed(false);

	    tcmAux=null;

	    setEditable(true);
	    blnRes=true;
	 }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
        return blnRes;
      }

   
    private void llamarPantFactura(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc){
        int intCodMnu=3497;
        Compras.ZafCom23.ZafCom23 obj = new Compras.ZafCom23.ZafCom23(objZafParSis, intCodMnu, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc) ;
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
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
        try 
        {
            if(conn!=null)
            {
                stm=conn.createStatement();
                Vector vecData = new Vector();
                strSql="";
                strSql+=" SELECT a3.* \n";
                strSql+=" FROM tbm_cabSegMovInv as a1 \n";
                strSql+=" INNER JOIN ( \n";
                strSql+="       SELECT a1.co_seg \n";
                strSql+="       FROM tbm_cabSegMovInv as a1  \n";
                strSql+="       INNER JOIN tbm_cabCotVen as a2 ON (a1.co_empRelCabCotVen=a2.co_emp AND a1.co_locRelCabCotVen=a2.co_loc AND  \n";
                strSql+="                                          a1.co_cotRelCabCotVen=a2.co_cot)  \n";
                strSql+="       WHERE a2.co_emp="+intCodEmp+" AND a2.co_loc="+intCodLoc+" AND a2.co_cot="+intCodCot+"  \n";
                strSql+=" ) AS a2 ON (a1.co_seg=a2.co_seg)  \n";
                strSql+=" INNER JOIN tbm_cabGuiRem as a3 ON (a1.co_empRelCabGuiRem=a3.co_emp AND a1.co_locRelCabGuiRem=a3.co_loc AND \n";
                strSql+="                                    a1.co_tipDocRelCabGuiRem=a3.co_tipDoc AND a1.co_docRelCabGuiRem=a3.co_doc)  \n";
                strSql+=" WHERE tx_datDocOriGuiRem like 'COT%'  \n";
                strSql+=" \n";
                rst = stm.executeQuery(strSql);
                while(rst.next())
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_CODEMP, rst.getString("co_emp") );
                    vecReg.add(INT_TBL_CODLOC, rst.getString("co_loc") );
                    vecReg.add(INT_TBL_COTIDO, rst.getString("co_tipdoc") );
                    vecReg.add(INT_TBL_CODDOC, rst.getString("co_doc") );                 
                    vecReg.add(INT_TBL_NUMOD, rst.getString("ne_numOrdDes") );
                    vecReg.add(INT_TBL_FECDOC, rst.getString("fe_doc")  );
                    vecReg.add(INT_TBL_DATDOCORG, rst.getString("tx_datDocOriGuiRem") );
                    vecReg.add(INT_TBL_BUTVIS, ""  );
                    vecData.add(vecReg);
                    blnRes=true;
                }
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);

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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panSur = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

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
        jScrollPane1.setViewportView(tblDat);

        panTabGenCen.add(jScrollPane1, java.awt.BorderLayout.CENTER);

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

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-350)/2, (screenSize.height-300)/2, 350, 300);
    }// </editor-fold>//GEN-END:initComponents

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed

        blnEst=false;
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
                 
                case INT_TBL_NUMOD:
                    strMsg="Numero de la Orden de despacho. ";
                    break;
                case INT_TBL_FECDOC:
                    strMsg="Fecha del Documento. ";
                    break;
                case INT_TBL_DATDOCORG:
                    strMsg="Observacion Documento Origen.  ";
                    break;
                case INT_TBL_BUTVIS:
                    strMsg="Permite ver la Orden de Despacho.  ";
                   break;
                default:
                    strMsg=" ";
                    break;

            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

}

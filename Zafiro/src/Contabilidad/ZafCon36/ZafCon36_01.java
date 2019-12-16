/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCon36_01.java
 *
 * Created on 29/11/2011, 09:38:58 PM
 */

package Contabilidad.ZafCon36;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;


/**
 *
 * @author Javier
 */
public class ZafCon36_01 extends javax.swing.JInternalFrame {
   ZafParSis objZafParSis;
   ZafUtil objUti;

   private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;

    final int INT_TBL_LINEA = 0;
    final int INT_TBL_DOCDES =1;  // DOCUMENTO DESDE
    final int INT_TBL_DOCHAS = 2;  // DOCUMENTO HASTA
    final int INT_TBL_FACCAD = 3; // FECHA  DE CADUCIDAD
    final int INT_TBL_FECDES = 4;  // FECHA DESDE CUANDO SE UTILIZO
    final int INT_TBL_FECHAS = 5;  // FECHA HASTA CUANDO SE UTILIZO
    final int INT_TBL_ULTDOC = 6;  // ULTIMO DOCUMENTO INGRESADO
    final int INT_TBL_TODOCUTI = 7;  // TOTAL DE DOCUMENTO UTILIZADO
    final int INT_TBL_DOCSOB = 8;  // DOCUMENTOS SOBRANTES
    final int INT_TBL_TOTDIA = 9;  // TOTAL DE DIAS QUE SE UTILIZO

 private Vector vecDat, vecReg;
 

    public ZafCon36_01(ZafParSis objParsis, int intCodEmp, int  intCodLoc, int intCodTipDoc, int intCodTipDocGrp, int intCodBodGrp) {
         try{
          this.objZafParSis = (Librerias.ZafParSis.ZafParSis) objParsis.clone();

          vecDat=new Vector();

          initComponents();
          this.setTitle(""+objZafParSis.getNombreMenu() );
          //lblTit.setText(""+objZafParSis.getNombreMenu() );
          objUti = new ZafUtil();

          configurarForm();

          cargarReg(intCodEmp, intCodLoc , intCodTipDoc, intCodTipDocGrp, intCodBodGrp );
          

        }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }
    }


 private boolean cargarReg( int intCodEmp, int  intCodLoc, int intCodTipDoc, int intCodTipDocGrp, int intCodBodGrp ){
 boolean blnRes=true;
 java.sql.Connection conn;
  try{
    conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
    if (conn!=null){
        
        if(cargarCabReg(conn, intCodEmp, intCodLoc, intCodTipDoc)){
          if(cargarDetReg(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodTipDocGrp, intCodBodGrp)){
              
        }}

    conn.close();
    conn=null;
  
}}catch (java.sql.SQLException e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
  catch (Exception e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
return blnRes;
}


private boolean cargarCabReg(java.sql.Connection conn, int intCodEmp, int  intCodLoc, int intCodTipDoc){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
     if (conn!=null){
        stmLoc=conn.createStatement();


       strSql="select a.co_emp, a.co_loc, a.co_tipdoc, a.tx_descor, a.tx_deslar, a1.tx_nom as nomloc, a2.tx_nom as nomemp "
       + " from tbm_cabtipdoc as a "
       + " inner join tbm_loc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc) "
       + " inner join tbm_emp as a2 on (a2.co_emp=a.co_emp ) "
       + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" ";

       //System.out.println("-->"+ strSql );

       rstLoc=stmLoc.executeQuery(strSql);
       while (rstLoc.next()){

           txtCodEmp.setText(rstLoc.getString("co_emp"));
           txtNomEmp.setText(rstLoc.getString("nomemp"));
           txtCodLoc.setText(rstLoc.getString("co_loc"));
           txtNomLoc.setText(rstLoc.getString("nomloc"));
           txtCodTipDoc.setText(rstLoc.getString("co_tipdoc"));
           txtDesCorTipDoc.setText(rstLoc.getString("tx_descor"));
           txtDesLarTipDoc.setText(rstLoc.getString("tx_deslar"));

        }
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;

}}catch (java.sql.SQLException e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
  catch (Exception e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
return blnRes;
}


private boolean cargarDetReg(java.sql.Connection conn, int intCodEmp, int  intCodLoc, int intCodTipDoc, int intCodTipDocGrp, int intCodBodGrp){
 boolean blnRes=true;
  java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc, rstLoc01;
 String strSql="";
 try{
    if (conn!=null){
        stmLoc=conn.createStatement();
        stmLoc01=conn.createStatement();


       strSql="select a.co_emp, a.co_loc, a.co_tipdoc, a.ne_numdocdes, a.ne_numdochas, a.tx_feccadfac from tbm_datautsri as a "
        + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" "
        + " and a.st_reg='A' order by ne_numdocdes ";

       //System.out.println("-->"+ strSql );

       rstLoc=stmLoc.executeQuery(strSql);
       vecDat.clear();
       while (rstLoc.next()){

           

            vecReg=new Vector();
            vecReg.add(INT_TBL_LINEA,"");


            vecReg.add(INT_TBL_DOCDES, rstLoc.getString("ne_numdocdes"));
            vecReg.add(INT_TBL_DOCHAS, rstLoc.getString("ne_numdochas"));
            vecReg.add(INT_TBL_FACCAD,  rstLoc.getString("tx_feccadfac"));


            if(intCodTipDocGrp==4){ //PARA GUIAS


                strSql="select *, (fechas-fecdes) as dias, ( "+rstLoc.getInt("ne_numdochas")+" - ultdocuti ) as totdocsob from ( "
                + " select min(a.fe_doc) as fecdes, max(a.fe_doc) as fechas, max(a.ne_numdoc) as ultdocuti, count(a.ne_numdoc) as totdocuti  from tbm_cabguirem as a "
                + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar)  "
                + " where  ( a6.co_empGrp=0 AND a6.co_bodGrp=( "+intCodBodGrp+" ) ) and a.ne_numdoc between "+rstLoc.getInt("ne_numdocdes")+" and "+rstLoc.getInt("ne_numdochas")+" "
                + " ) as x  ";

                
            }else if(intCodTipDocGrp==5){  // PARA RETENCIONES
                strSql="select *, (fechas-fecdes) as dias, ( "+rstLoc.getInt("ne_numdochas")+" - ultdocuti ) as totdocsob from ( "
                + " select min(a.fe_doc) as fecdes, max(a.fe_doc) as fechas, max(a.ne_numdoc1) as ultdocuti, count(a.ne_numdoc1) as totdocuti from tbm_cabpag as a "
                + " where a.co_emp="+rstLoc.getInt("co_emp")+" and a.co_loc="+rstLoc.getInt("co_loc")+" and a.co_tipdoc="+rstLoc.getInt("co_tipdoc")+" "
                + " and a.ne_numdoc1 between "+rstLoc.getInt("ne_numdocdes")+" and "+rstLoc.getInt("ne_numdochas")+" "
                + "  ) as x  ";
            }else{
                    strSql="select *, (fechas-fecdes) as dias, ( "+rstLoc.getInt("ne_numdochas")+" - ultdocuti ) as totdocsob from ( "
                    + " select min(a.fe_doc) as fecdes, max(a.fe_doc) as fechas, max(a.ne_numdoc) as ultdocuti, count(a.ne_numdoc) as totdocuti from tbm_cabmovinv as a "
                    + " where a.co_emp="+rstLoc.getInt("co_emp")+" and a.co_loc="+rstLoc.getInt("co_loc")+" and a.co_tipdoc="+rstLoc.getInt("co_tipdoc")+" "
                    + " and a.ne_numdoc between "+rstLoc.getInt("ne_numdocdes")+" and "+rstLoc.getInt("ne_numdochas")+" "
                    + "  ) as x  ";

            }

            rstLoc01=stmLoc01.executeQuery(strSql);
            if (rstLoc01.next()){

                vecReg.add(INT_TBL_FECDES, rstLoc01.getString("fecdes"));
                vecReg.add(INT_TBL_FECHAS,  rstLoc01.getString("fechas"));
                vecReg.add(INT_TBL_ULTDOC,  rstLoc01.getString("ultdocuti"));
                vecReg.add(INT_TBL_TODOCUTI,  rstLoc01.getString("totdocuti"));
                vecReg.add(INT_TBL_DOCSOB,   rstLoc01.getString("totdocsob"));
                vecReg.add(INT_TBL_TOTDIA,  rstLoc01.getString("dias"));

            }else{

                vecReg.add(INT_TBL_FECDES, "");
                vecReg.add(INT_TBL_FECHAS,  "");
                vecReg.add(INT_TBL_ULTDOC,  "");
                vecReg.add(INT_TBL_TODOCUTI,   "");
                vecReg.add(INT_TBL_DOCSOB,   "");
                vecReg.add(INT_TBL_TOTDIA, "");

            }
            rstLoc01.close();
            rstLoc01=null;


            vecDat.add(vecReg);

    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
    stmLoc01.close();
    stmLoc01=null;
  
    //Asignar vectores al modelo.
    objTblMod.setData(vecDat);
    tblDat.setModel(objTblMod);
    vecDat.clear();

    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");

}}catch (java.sql.SQLException e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
  catch (Exception e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
return blnRes;
}



    
 private boolean configurarForm(){
   boolean blnres=false;

    Vector vecCab=new Vector();
    vecCab.clear();

    vecCab.add(INT_TBL_LINEA,"");
    vecCab.add(INT_TBL_DOCDES,"Desde. ");
    vecCab.add(INT_TBL_DOCHAS,"Hasta");
    vecCab.add(INT_TBL_FACCAD,"Caducidad");
    vecCab.add(INT_TBL_FECDES,"Fec.Des");
    vecCab.add(INT_TBL_FECHAS,"Fec.Has.");
    vecCab.add(INT_TBL_ULTDOC,"Ult.Doc.Uti");
    vecCab.add(INT_TBL_TODOCUTI,"Utilizado");
    vecCab.add(INT_TBL_DOCSOB,"Sobrantes");
    vecCab.add(INT_TBL_TOTDIA,"Días");
  
    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

    //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
    ZafMouMotAda objMouMotAda=new ZafMouMotAda();
    tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


     //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//    objTblMod.setColumnDataType(INT_TBL_STKACT, objTblMod.INT_COL_DBL, new Integer(0), null);
   
    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    tblDat.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_DOCDES).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_DOCHAS).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_FACCAD).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_FECDES).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_FECHAS).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_ULTDOC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_TODOCUTI).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_DOCSOB).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_TOTDIA).setPreferredWidth(70);
   
     ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
     objTblHeaGrp.setHeight(16*2);



            ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Datos del SRI " );
            objTblHeaColGrpAmeSur.setHeight(16);

            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DOCDES));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DOCHAS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FACCAD));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;

            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Utilizado " );
            objTblHeaColGrpAmeSur.setHeight(16);

            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECDES));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECHAS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_ULTDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TODOCUTI));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DOCSOB));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TOTDIA));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;




    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

  objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

     return blnres;
}





    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabFrm = new javax.swing.JTabbedPane();
        TabPanGen = new javax.swing.JPanel();
        PanDetDet = new javax.swing.JPanel();
        SrcTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        PanDetCab = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtCodLoc = new javax.swing.JTextField();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        PanPie = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butcerr = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();
        PanCab = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();

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
            }
        });

        TabPanGen.setLayout(new java.awt.BorderLayout());

        PanDetDet.setLayout(new java.awt.BorderLayout());

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
        SrcTbl.setViewportView(tblDat);

        PanDetDet.add(SrcTbl, java.awt.BorderLayout.CENTER);

        PanDetCab.setPreferredSize(new java.awt.Dimension(684, 80));
        PanDetCab.setLayout(null);

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel1.setText("Tipo de Documento:");
        PanDetCab.add(jLabel1);
        jLabel1.setBounds(10, 50, 110, 20);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel2.setText("Empresa:");
        PanDetCab.add(jLabel2);
        jLabel2.setBounds(10, 10, 80, 20);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel3.setText("Local:");
        PanDetCab.add(jLabel3);
        jLabel3.setBounds(10, 30, 80, 20);

        txtCodTipDoc.setEditable(false);
        PanDetCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(130, 50, 50, 20);

        txtCodLoc.setEditable(false);
        PanDetCab.add(txtCodLoc);
        txtCodLoc.setBounds(130, 30, 50, 20);

        txtCodEmp.setEditable(false);
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        PanDetCab.add(txtCodEmp);
        txtCodEmp.setBounds(130, 10, 50, 20);

        txtNomEmp.setEditable(false);
        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        PanDetCab.add(txtNomEmp);
        txtNomEmp.setBounds(180, 10, 330, 20);

        txtNomLoc.setEditable(false);
        PanDetCab.add(txtNomLoc);
        txtNomLoc.setBounds(180, 30, 330, 20);

        txtDesLarTipDoc.setEditable(false);
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        PanDetCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(260, 50, 250, 20);

        txtDesCorTipDoc.setEditable(false);
        PanDetCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(180, 50, 80, 20);

        PanDetDet.add(PanDetCab, java.awt.BorderLayout.PAGE_START);

        TabPanGen.add(PanDetDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", TabPanGen);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

        PanPie.setLayout(new java.awt.BorderLayout());

        butcerr.setFont(new java.awt.Font("SansSerif", 0, 11));
        butcerr.setText("Cerrar");
        butcerr.setPreferredSize(new java.awt.Dimension(90, 23));
        butcerr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butcerrActionPerformed(evt);
            }
        });
        jPanel5.add(butcerr);

        PanPie.add(jPanel5, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
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

        PanPie.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanPie, java.awt.BorderLayout.SOUTH);

        lblTit.setText("Histórico de reposición de papeleria");
        PanCab.add(lblTit);

        getContentPane().add(PanCab, java.awt.BorderLayout.NORTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-703)/2, (screenSize.height-363)/2, 703, 363);
    }// </editor-fold>//GEN-END:initComponents

    private void butcerrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butcerrActionPerformed
        // TODO add your handling code here:
        exitForm();
}//GEN-LAST:event_butcerrActionPerformed




     private void exitForm(){

     String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }

    }


     
    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:

        exitForm();
        
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanCab;
    private javax.swing.JPanel PanDetCab;
    private javax.swing.JPanel PanDetDet;
    private javax.swing.JPanel PanPie;
    private javax.swing.JScrollPane SrcTbl;
    private javax.swing.JPanel TabPanGen;
    private javax.swing.JButton butcerr;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomLoc;
    // End of variables declaration//GEN-END:variables


private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LINEA:
            strMsg="";
            break;
        case INT_TBL_DOCDES:
            strMsg="Documento desde.";
            break;
        case INT_TBL_DOCHAS:
            strMsg="Documento hasta.";
            break;

        case INT_TBL_FACCAD:
            strMsg="Fecha de caducidad ";
            break;

        case INT_TBL_FECDES:
            strMsg="Fecha desde";
            break;
        case INT_TBL_FECHAS:
            strMsg="Fecha hasta ";
            break;
        case INT_TBL_ULTDOC:
            strMsg="Ultimo documento utilizado.";
            break;
        case INT_TBL_TODOCUTI:
            strMsg="Número total de documentos utilizados.";
            break;
        case INT_TBL_DOCSOB:
            strMsg="Número total de documentos sobrantes.";
            break;
        case INT_TBL_TOTDIA:
            strMsg="Número total de dias que se utilizaron los documentos";
            break;
     

        default:
            strMsg="";
            break;
    }
    tblDat.getTableHeader().setToolTipText(strMsg);
}
}

}

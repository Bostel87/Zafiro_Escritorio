/*
 * ZafCxC08_01.java
 *
 * Created on 22 de septiembre de 2005, 9:08
 */

package CxC.ZafCxC08;
import Librerias.ZafParSis.ZafParSis;
import java.util.Vector;
/**
 *
 * @author  jsalazar
 */
public class ZafCxC08_01 extends javax.swing.JDialog {
    private ZafParSis objZafParSis;
    private javax.swing.JDialog dlgThis;
    private Librerias.ZafUtil.ZafUtil objUti;        
    private Librerias.ZafUtil.ZafCliente_dat objCliente;
    private Librerias.ZafUtil.ZafTipDoc objTipDoc;
    private Vector vecDat,vecCab;
    private Vector vecObj,vecData;
    //Editores de tabla
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;        //Render: Presentar JLabel en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt;        //Editor: JTextField en celda.
    private Librerias.ZafColNumerada.ZafColNumerada objColNum;
    private ZafMouMotAda objMouMotAda;
    
    private boolean ViveHilo;//true = Si vive el hilo de el progressbar, false=muere
    private boolean ViveHiloCon ;//true = Si vive el hilo de la cunsulta, false=muere
    //Constantes
    private final int INT_TBL_LINEA     = 0;
    private final int INT_TBL_COD_BCO   = 1;    
    private final int INT_TBL_NOM_BCO   = 2;
    private final int INT_TBL_NUM_CTA   = 3;
    private final int INT_TBL_OBJ_DOC   = 4;
    private final int INT_TBL_NUM_CHQ   = 5; 
    private final int INT_TBL_BUT_CHQ   = 6;
    private final int INT_TBL_FEC_REC   = 7;
    private final int INT_TBL_FEC_VEN   = 8;
    private final int INT_TBL_VAL_CHQ   = 9;
    //VECTOR OBJ_CHQ
    private final int INT_VEC_TIP_DOC   = 0;
    private final int INT_VEC_COD_DOC   = 1;
    private final int INT_VEC_COD_REG   = 2;
  
    
    /** Creates new form ZafCxC08_01 */
    public ZafCxC08_01(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    public ZafCxC08_01(java.awt.Frame parent,ZafParSis obj, Librerias.ZafUtil.ZafTipDoc parTipDoc, Librerias.ZafUtil.ZafCliente_dat parCliente) {
        super(parent, true);
        initComponents();
        try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            dlgThis = this;
            objTipDoc  = parTipDoc;
            objUti     = new Librerias.ZafUtil.ZafUtil();
            objCliente = parCliente;
            txtNomCli.setEnabled(false);
            txtCodCli.setEnabled(false);
            if (!Configurar_tabla())
                exitForm();
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(dlgThis, e);
      }
    }       
    private void exitForm(){
        dispose();
    }
    private boolean Configurar_tabla()
    {
        boolean blnRes=false;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            
            vecCab.add(INT_TBL_LINEA,"");
            vecCab.add(INT_TBL_COD_BCO,"Cod. Banco");
            vecCab.add(INT_TBL_NOM_BCO,"Banco");            
            vecCab.add(INT_TBL_NUM_CTA,"Num. Cuenta.");
            vecCab.add(INT_TBL_OBJ_DOC,"");
            vecCab.add(INT_TBL_NUM_CHQ,"Num. Cheque");
            vecCab.add(INT_TBL_BUT_CHQ,"");            
            vecCab.add(INT_TBL_FEC_REC,"Fecha Recepcion");
            vecCab.add(INT_TBL_FEC_VEN,"Fecha Vencimiento");
            vecCab.add(INT_TBL_VAL_CHQ,"Valor");
            
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);

            tblDat.setModel(objTblMod);   
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_NUM_CHQ, objTblMod.INT_COL_INT, new Integer(0), null);
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_NUM_CHQ);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposSistema());
                  

            //Configurar JTable: Establecer el menú de contexto.
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            //Configurar JTable: Establecer el ancho de las columnas.
            
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(20);          
            tcmAux.getColumn(INT_TBL_COD_BCO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NOM_BCO).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_NUM_CTA).setPreferredWidth(70);            
            tcmAux.getColumn(INT_TBL_NUM_CHQ).setPreferredWidth(70);            
            tcmAux.getColumn(INT_TBL_FEC_REC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_FEC_VEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_VAL_CHQ).setPreferredWidth(70);
 
            
            //Configurar JTable: Ocultar columnas del sistema.
            tcmAux.getColumn(INT_TBL_BUT_CHQ).setWidth(0);
            tcmAux.getColumn(INT_TBL_BUT_CHQ).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_BUT_CHQ).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_BUT_CHQ).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_BUT_CHQ).setResizable(false);                         
            //codigo retencion
            tcmAux.getColumn(INT_TBL_OBJ_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_OBJ_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_OBJ_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_OBJ_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_OBJ_DOC).setResizable(false);                         
            
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);                                        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            tblDat.getTableHeader().setReorderingAllowed(false);            
            objMouMotAda=new ZafMouMotAda();            
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_NUM_CHQ);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
          //Configurar JTable: Renderizar celdas.


            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_COD_BCO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NOM_BCO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NUM_CTA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FEC_REC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FEC_VEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VAL_CHQ).setCellRenderer(objTblCelRenLbl);            
            objTblCelRenLbl=null;
           
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("######",true,true);
            tcmAux.getColumn(INT_TBL_NUM_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_VAL_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt();
            tcmAux.getColumn(INT_TBL_COD_BCO).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_NOM_BCO).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_NUM_CTA).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_NUM_CHQ).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_FEC_REC).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_FEC_VEN).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_VAL_CHQ).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt=null;
            
            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            
            objTblCelEdiTxt=null;
            //Libero los objetos auxiliares.
            tcmAux=null;
             
            //Configurar JTable: Centrar columnas.
            blnRes=true;    
        }catch(Exception e) {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(dlgThis,e);
        }
        return blnRes;                        
    }    
    public void setCliente(int intCodCli){
        objCliente.setCliente(intCodCli);
        txtCodCli.setText(""+intCodCli);
        txtNomCli.setText(objCliente.getNombre());
    }
    public void setCheque(int intChq){
        if (intChq>0) txtChq.setText(""+intChq);
    }
    public void clear(){
        txtNomCli.setText("");
        txtChq.setText("");
        vecDat.removeAllElements();
        objTblMod.setData(vecDat);
        tblDat.setModel(objTblMod);                                         
    }    
    public Vector visualiza(int intColCon){  
        ViveHiloCon= true;
        consulta(intColCon);     
        return vecData;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNomCli = new javax.swing.JTextField();
        txtChq = new javax.swing.JTextField();
        butCon = new javax.swing.JButton();
        txtCodCli = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        getContentPane().setLayout(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel1.setLayout(null);

        jLabel1.setText("Cliente:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 10, 70, 15);

        jLabel2.setText("Cheque #");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 37, 70, 15);

        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });

        jPanel1.add(txtNomCli);
        txtNomCli.setBounds(140, 0, 310, 21);

        txtChq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChqActionPerformed(evt);
            }
        });

        jPanel1.add(txtChq);
        txtChq.setBounds(80, 35, 55, 21);

        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });

        jPanel1.add(butCon);
        butCon.setBounds(140, 35, 90, 20);

        jPanel1.add(txtCodCli);
        txtCodCli.setBounds(80, 0, 60, 21);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(30, 20, 550, 60);

        jPanel2.setLayout(null);

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

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(0, 10, 520, 130);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(30, 100, 550, 150);

        jPanel3.setLayout(null);

        butAce.setText("Aceptar");
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });

        jPanel3.add(butAce);
        butAce.setBounds(200, 0, 90, 25);

        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });

        jPanel3.add(butCan);
        butCan.setBounds(303, 0, 100, 25);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(20, 260, 450, 30);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-600)/2, (screenSize.height-325)/2, 600, 325);
    }//GEN-END:initComponents

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        // TODO add your handling code here:
        if (tblDat.getSelectedRow()>=0){            
            if (((Vector)tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_OBJ_DOC)).size()>0 ){
                vecData=(java.util.Vector)vecDat.get(tblDat.getSelectedRow());
            }else {
                vecData=null;
            }
            Cancela();
        }else{
            MensajeInf("Seleccione el cheque.");
        }
    }//GEN-LAST:event_butAceActionPerformed

    private void MensajeInf(String strMensaje){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Zafiro.- Cheque Protestados";
            obj.showMessageDialog(new javax.swing.JInternalFrame(),strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        vecData=null;
        Cancela();
    }//GEN-LAST:event_butCanActionPerformed
    private void Cancela(){
        ViveHilo=false;
        ViveHiloCon = false;        
        dispose();
    }

    private void txtChqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChqActionPerformed
        // TODO add your handling code here:
        if(butCon.getText().equals("Consultar"))
            ExeCon();                
    }//GEN-LAST:event_txtChqActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:
        if(butCon.getText().equals("Detener"))
            ViveHiloCon=false;
        else
            ExeCon();        
    }//GEN-LAST:event_butConActionPerformed
    private void ExeCon(){
        
        Act_But(false);
        ViveHilo=true;
        ViveHiloCon = true;
        
        cargarCon Consultar = new cargarCon();
        Consultar.start();
        
    }
    private void Act_But(boolean Activo){
        if(butCon.getText().equals("Consultar"))
            butCon.setText("Detener");
        else
            butCon.setText("Consultar");
        txtChq.setEnabled(Activo);
        butAce.setEnabled(Activo);
        butCan.setEnabled(Activo);
    }
    
    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomCliActionPerformed
    
    /**
     * @param args the command line arguments
     */
    class cargarCon extends Thread{
        public void run(){
            consulta(INT_TBL_NUM_CHQ);
        }
    }
    private void consulta(int intColumnaConsulta)
    {
            try{
                java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                java.sql.ResultSet rst,rstAux;
                
                try{
                    if (con!=null){
                        java.sql.Statement stm = con.createStatement();
                        java.sql.Statement stmAux = con.createStatement();
                        String strSQL="";
                        vecDat=null;
                        vecDat=new Vector();
                        if (intColumnaConsulta==INT_TBL_NUM_CHQ)
                            strSQL = " select co_banchq, tx_deslar, tx_numctachq, tx_numchq, fe_recchq,fe_venchq, sum(nd_monchq) as nd_monchq "+
                                     " from tbm_pagmovinv as pag left outer join tbm_var as var on (pag.co_banchq = var.co_reg )left outer join tbm_cabmovinv as cab on (cab.co_emp = pag.co_emp and cab.co_loc = pag.co_loc and cab.co_tipdoc = pag.co_tipdoc and cab.co_doc  =pag.co_doc) left outer join tbm_cli as cli on (cab.co_emp = cli.co_emp and cab.co_cli = cli.co_cli) "+
                                     " where cab.co_emp = " + objZafParSis.getCodigoEmpresa()+" and cab.co_loc = " + objZafParSis.getCodigoLocal() + " and tx_numchq = '" + txtChq.getText() + "' and cab.co_cli="+ txtCodCli.getText() + " and co_prochq ISNULL and cab.st_reg='A' and pag.st_reg='A'" +
                                     " Group By co_banchq,tx_deslar,tx_numctachq,tx_numchq,fe_recchq,fe_venchq"+
                                     " Order by co_banchq, tx_numchq";
                        else
                            strSQL = " select co_banchq, tx_deslar, tx_numctachq, tx_numchq, fe_recchq,fe_venchq, sum(nd_monchq) as nd_monchq "+
                                     " from tbm_pagmovinv as pag left outer join tbm_var as var on (pag.co_banchq = var.co_reg )left outer join tbm_cabmovinv as cab on (cab.co_emp = pag.co_emp and cab.co_loc = pag.co_loc and cab.co_tipdoc = pag.co_tipdoc and cab.co_doc  =pag.co_doc) left outer join tbm_cli as cli on (cab.co_emp = cli.co_emp and cab.co_cli = cli.co_cli) "+
                                     " where cab.co_emp = " + objZafParSis.getCodigoEmpresa()+" and cab.co_loc = " + objZafParSis.getCodigoLocal() + " and tx_numchq NOTNULL and cab.co_cli="+ txtCodCli.getText() + " and co_prochq ISNULL and cab.st_reg='A' and pag.st_reg='A'" +
                                     " Group By co_banchq,tx_deslar,tx_numctachq,tx_numchq,fe_recchq,fe_venchq"+
                                     " Order by co_banchq, tx_numchq";
                         rst = stm.executeQuery(strSQL);
//                         System.out.println(strSQL);
//                         System.out.println("num:"+objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL));
                         if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)<=0) {
                             MensajeInf("No tiene cheque "+ txtChq.getText() +" asociado a " + txtNomCli.getText() + "\n para protestar");
                             ViveHiloCon= false;
                         }
                         while (rst.next() && ViveHiloCon){    
                            strSQL = " Select co_tipdoc,co_doc,co_reg "+
                                     " from tbm_pagmovinv  "+
                                     " where co_emp = " + objZafParSis.getCodigoEmpresa()+" and co_loc = " + objZafParSis.getCodigoLocal()+" and co_banchq="+rst.getInt("co_banchq")+" and tx_numctachq='"+rst.getString("tx_numctachq")+"' and tx_numchq = '" + rst.getString("tx_numchq") + "' and st_reg='A'" +
                                     " order by co_tipdoc,co_doc,co_reg";
//                            System.out.println(String.valueOf(ViveHiloCon)+" "+strSQL);
                            rstAux = stmAux.executeQuery(strSQL);
                            vecObj = new Vector();
                            while (rstAux.next() && ViveHiloCon){
                                Vector vecTmp = new Vector();
                                vecTmp.add(INT_VEC_TIP_DOC,new Integer(rstAux.getInt("co_tipdoc")));
                                vecTmp.add(INT_VEC_COD_DOC,new Integer(rstAux.getInt("co_doc")));
                                vecTmp.add(INT_VEC_COD_REG,new Integer(rstAux.getInt("co_reg")));                                
                                vecObj.add(vecTmp);
                            }                            
                            rstAux.close();
                            stmAux.close();
                            Vector vecReg = new Vector();
                            vecReg.add(INT_TBL_LINEA,"");
                            vecReg.add(INT_TBL_COD_BCO,new Integer(rst.getInt("co_banchq")));
                            vecReg.add(INT_TBL_NOM_BCO,rst.getString("tx_deslar"));
                            vecReg.add(INT_TBL_NUM_CTA,rst.getString("tx_numctachq"));
                            vecReg.add(INT_TBL_OBJ_DOC,vecObj);
                            vecReg.add(INT_TBL_NUM_CHQ,rst.getString("tx_numchq"));
                            vecReg.add(INT_TBL_BUT_CHQ,"");                            
                            vecReg.add(INT_TBL_FEC_REC,rst.getString("fe_recchq"));
                            vecReg.add(INT_TBL_FEC_VEN,rst.getString("fe_venchq"));
                            vecReg.add(INT_TBL_VAL_CHQ,new Double(rst.getDouble("nd_monchq")));
                            vecDat.add(vecReg);
                            vecObj = null;
                        }
                        objTblMod.setData(vecDat);
                        tblDat.setModel(objTblMod);                         
                        rst.close();
                        stm.close();
                        con.close();
//                        System.out.println("row: "+tblDat.getRowCount());
                        if (tblDat.getRowCount()>1 )  dlgThis.show();
                        if (tblDat.getRowCount()==1) vecData = (java.util.Vector)vecDat.get(0); 
                        
                    }                    
                    ViveHilo=false;
                    ViveHiloCon = false;                            
                    Act_But(true);
                }catch(java.sql.SQLException e){
                    ViveHilo=false;
                    ViveHiloCon = false;        
                    objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);                    
                }
            }catch(Exception e){
                ViveHilo=false;
                ViveHiloCon = false;        
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            }        
    }
    public static void main(String args[]) {
        new ZafCxC08_01(new javax.swing.JFrame(), true).show();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtChq;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtNomCli;
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
                case INT_TBL_COD_BCO:
                    strMsg="Codigo Banco";
                    break;
                case INT_TBL_NOM_BCO:
                    strMsg="Banco";
                    break;
                case INT_TBL_NUM_CTA:
                    strMsg="Numero Cuenta";
                    break;
                case INT_TBL_NUM_CHQ:
                    strMsg="Numero Cheque";
                    break;
                case INT_TBL_FEC_REC:
                    strMsg="Fecha Recepcion";
                    break;
                case INT_TBL_FEC_VEN:
                    strMsg="Fecha Vencimiento";
                    break;
                case INT_TBL_VAL_CHQ:
                    strMsg="Valor del Cheque";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    } 

}

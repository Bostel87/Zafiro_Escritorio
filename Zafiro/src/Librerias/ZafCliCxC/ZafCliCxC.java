
package Librerias.ZafCliCxC;
import java.sql.*;
import java.util.Vector;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

/**
 * Clase zafConsulta:
 * <p>
 * Realiza una consulta utilizando la fuente de origen de datos OBDC, para lo
 * cual es necesario crear un odbc de usuario que se conecte a la base que queremos
 * consultar
 * </p>
 * @author Idi Reyes Marcillo
 * @since 01/Julio/2004
 * @see <strong>Como Usar zafConsulta?</strong><br> Por ejemplo:<p>
 *    Queremos realizar una consulta de la tabla tbl_Clientes, en la base db_Principal,
 *    y los campos "codCli , nomCli, dirCli"; pero queremos que se presenten en pantalla con los
 *    nombre:"Codigo,Nombre del Cliente,Direccion" respectivamente;
 *    Somos el usuario: ireyes y password: code24.
 *    Y tenemos creado un origen de datos (odbc) que se llama dsnDb_Principal
 *     </p><pre>
 *
 *    public class miframe extends javax.swing.JFrame {
 *        private JButton butCon;
 *        private JTextField txtCod, txtNom;
 *        private utileria.zafConsulta conCli;
 *        public miframe() {
 *            ...
 *            ...
 *            ...
 *            butCon.addActionListener(new java.awt.event.ActionListener() {
 *            public void actionPerformed(java.awt.event.ActionEvent evt) {
 *                conCli = new zafConsulta(this,"Codigo,Nombre del Cliente,Direccion", "codCli , nomCli, dirCli" ,"tblClientes","","dsnDb_Principal","ireyes","code24");
 *                conCli.show();
 *                txtCod.setText(objcon.GetCamSel(1));
 *                txtNom.setText(objcon.GetCamSel(2));
 *            }
 *            });
 *
 *        }
 *    }</pre>
 */
public class ZafCliCxC extends javax.swing.JDialog {

    private int int_co_cli=0;
    private String  str_Nomcli = "";
    private int Var_Rows=0;
    private int Var_Cols=0;
    private int VarCurReg=0; //Current Register regstro actual
    private Librerias.ZafParSis.ZafParSis objZafParSis;
    private String strSql, strCount;
    private Librerias.ZafUtil.ZafUtil objUtil;
    private java.util.Vector vecDatos;
    
    //Constantes  Columnas de la Tabla:
    final int INT_TBL_LINEA    =0;            //Lïnea
    final int INT_TBL_SELECT   =1;            //CHEKBOX PARA SELECCIONAR
    final int INT_TBL_TIPDOC   =2;            //TIPO DE DOCUMENTO 
    final int INT_TBL_NOMDOC   =3;            //NOMBRE DEL DOCUMENTO
    final int INT_TBL_CO_DOC   =4;            //NUMERO DEL DOCUMENTO
    final int INT_TBL_NUMDOC   =5;            //NUMERO DEL DOCUMENTO
    final int INT_TBL_FE_DOC   =6;            //fECHA DE DOCUMENTO
    final int INT_TBL_DIACRE   =7;            //DIAS DE CREDITO
    final int INT_TBL_FE_VEN   =8;           //FECHA DE VENCIMIENTO 
    final int INT_TBL_RETENC   =9;           //PORCENTAJE DE RETENCION
    final int INT_TBL_VALDOC   =10;           //VALOR DE DOCUMENTO
    final int INT_TBL_VALPEN   =11;           //VALOR PENDIENTE
    final int INT_TBL_CO_REGPAG   =12;       //CODIGO DEL REGISTRO DEL PAGO (CO_REG)
    
    private boolean ViveHilo;//true = Si vive el hilo de el progressbar, false=muere
    private boolean ViveHiloCon ;//true = Si vive el hilo de la cunsulta, false=muere
    
    public ZafCliCxC(javax.swing.JFrame parent, int int_co_cli, Librerias.ZafParSis.ZafParSis objZafParSis) {
        super(parent, true);
        this.int_co_cli = int_co_cli;
        this. objZafParSis = objZafParSis;
        //this.setDefaultLookAndFeelDecorated(true);
        objUtil = new Librerias.ZafUtil.ZafUtil ();
        vecDatos = new java.util.Vector ();
        
        
        /* 
         * Preparando el string para el Query
         */
        strSql =  "select  pag.co_tipdoc, tipdoc.tx_descor,                            " +
                         " pag.co_reg, cab.co_doc, cab.ne_numDoc, cab.fe_doc      ,    " +
                         " pag.ne_diacre, pag.fe_ven      ,    " +
                         " pag.nd_porret, abs( pag.mo_pag ) as mo_pag , " + 
                         " (abs(pag.mo_pag) - pag.nd_abo) as nd_valpen           " +
                     "from tbm_pagmovinv as pag, tbm_cabmovinv as cab, tbm_cabtipdoc as tipdoc " +
                     "where                                                                    " +
                     " pag.co_emp =   "  + objZafParSis.getCodigoEmpresa() +   "   and   "  +  
                     " pag.co_emp = cab.co_emp and      " +
                     " pag.co_emp = tipdoc.co_emp and   " +
                     " pag.co_loc =   "  + objZafParSis.getCodigoLocal()   +   "   and   " +
                     " pag.co_loc = cab.co_loc                              and   " +
                     " pag.co_loc = tipdoc.co_loc                           and   " + 
                     " pag.co_tipDoc = cab.co_tipdoc                        and   " +
                     " pag.co_tipDoc = tipdoc.co_tipdoc                     and   " +
                     " pag.co_doc     = cab.co_doc                          and   " +
                     " (abs(pag.mo_pag) - pag.nd_abo) > 0                   and   " + 
                     " pag.st_reg in ('C','A')                              and   " +
                     " cab.co_cli = " + int_co_cli  + 
                     " order by tipdoc.tx_descor, pag.co_doc, pag.co_reg " ;                      
                    
        strCount = "select count(a1.ne_diaCre) as cuenta  from (" + strSql +  ") as a1";
        initComponents();
        
        Librerias.ZafColNumerada.ZafColNumerada ObjNumerador = new Librerias.ZafColNumerada.ZafColNumerada(tblCon,0);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                Cancela();
            }
        });
        ResizeTbl();
        ExeCon();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        panNor = new javax.swing.JPanel();
        spnCon = new javax.swing.JScrollPane();
        tblCon = new javax.swing.JTable();
        panSur = new javax.swing.JPanel();
        panSurSur = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panSurNor = new javax.swing.JPanel();
        prgCon = new javax.swing.JProgressBar();
        lblSts = new javax.swing.JLabel();
        lblSts2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        panNor.setLayout(new java.awt.GridLayout(3, 2));

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        tblCon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Linea", "Seleccionar", "Tipo de Doc", "Nombre de Doc", "CO_DOC", "Numero de Doc", "Fecha de Doc", "Dias de Credito", "Vence", "Retención", "Valor del Doc", "Pendiente", "co_regPag"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCon.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        spnCon.setViewportView(tblCon);

        getContentPane().add(spnCon, java.awt.BorderLayout.CENTER);

        panSur.setLayout(new java.awt.BorderLayout());

        panSurSur.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butAce.setMnemonic('A');
        butAce.setText("Aceptar");
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });

        panSurSur.add(butAce);

        butCan.setMnemonic('C');
        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });

        panSurSur.add(butCan);

        panSur.add(panSurSur, java.awt.BorderLayout.CENTER);

        panSurNor.setLayout(new java.awt.BorderLayout());

        prgCon.setBorder(null);
        prgCon.setStringPainted(true);
        panSurNor.add(prgCon, java.awt.BorderLayout.NORTH);

        panSurNor.add(lblSts, java.awt.BorderLayout.WEST);

        lblSts2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        panSurNor.add(lblSts2, java.awt.BorderLayout.CENTER);

        panSur.add(panSurNor, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panSur, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-600)/2, (screenSize.height-250)/2, 600, 250);
    }//GEN-END:initComponents
            
    private void ExeCon(){
        Act_But(false);
        prgCon.setVisible(true);
        ViveHilo=true;
        ViveHiloCon = true;
        
        cargarCon Consultar = new cargarCon();
        Consultar.start();
    }

    /*
     *Actualiza el estado de los botones
     */
    
       
    private void Act_But(boolean Activo){
        butAce.setEnabled(Activo);
        butCan.setEnabled(Activo);
    }
    
    /**Cargar Consulta en el Jtable
     */
    class cargarCon extends Thread{
        public void run(){
            
            // Realiza la Consulta segun los campos seleccionados y muestra en el grid
               try{

                   Connection con=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

                   try{//odbc,usuario,password


                    if (con!=null) {
                        
                        //Consultando el nombre de el cliente 
                        Statement stmNomCli=con.createStatement();

                        ResultSet rstNomCli=stmNomCli.executeQuery("Select tx_nom from tbm_cli where co_cli = " + int_co_cli );
                        if(rstNomCli.next())
                            str_Nomcli = (rstNomCli.getString("tx_nom")==null)?"":rstNomCli.getString("tx_nom");
                        rstNomCli.close();
                        stmNomCli.close();

                        //Calculando cantidad de registros del query
                        Statement stmCount=con.createStatement();
                        lblSts.setText("Calculando Cantidad total de resultados");
                        ResultSet rstCount=stmCount.executeQuery(strCount);
                        if(rstCount.next())
                            Var_Rows = rstCount.getInt("cuenta");
                        rstCount.close();
                        stmCount.close();

                        //Realizando el Query
                        Statement stmQuery = con.createStatement();
                        lblSts.setText("Ejecutando Consulta");
                        ResultSet rstQuery = stmQuery.executeQuery(strSql);
                        lblSts.setText("");

                        prgCon.setMaximum(Var_Rows);
                        Progreso ObjPrg = new Progreso();
                        ObjPrg.start();

                        for(int i=0 ; rstQuery.next() && ViveHiloCon; i++ ){
                          ((javax.swing.table.DefaultTableModel)tblCon.getModel()).addRow(new java.util.Vector());    
                         
                           tblCon.setValueAt((rstQuery.getString("co_tipdoc")==null)?"":rstQuery.getString("co_tipdoc"), i, INT_TBL_TIPDOC);
                           tblCon.setValueAt((rstQuery.getString("tx_descor")==null)?"":rstQuery.getString("tx_descor"), i, INT_TBL_NOMDOC);
                           tblCon.setValueAt(new Integer(rstQuery.getInt("co_doc")), i, INT_TBL_CO_DOC);
                           tblCon.setValueAt(new Integer(rstQuery.getInt("ne_numDoc")), i, INT_TBL_NUMDOC);
                           tblCon.setValueAt((rstQuery.getString("fe_doc")==null)?"":rstQuery.getString("fe_doc"), i, INT_TBL_FE_DOC );
                           tblCon.setValueAt(new Integer(rstQuery.getInt("ne_diacre")), i, INT_TBL_DIACRE);
                           tblCon.setValueAt((rstQuery.getString("fe_ven")==null)?"":rstQuery.getString("fe_ven"), i, INT_TBL_FE_VEN);
                           tblCon.setValueAt(new Double(rstQuery.getDouble("nd_porret")), i, INT_TBL_RETENC);
                           tblCon.setValueAt(new Double(rstQuery.getDouble("mo_pag")), i, INT_TBL_VALDOC);
                           tblCon.setValueAt(new Double(rstQuery.getDouble("nd_valpen")), i, INT_TBL_VALPEN);
                           tblCon.setValueAt(new Integer(rstQuery.getInt("co_reg")), i, INT_TBL_CO_REGPAG);
                            
                           VarCurReg = i+1;
                           ObjPrg.resume();
                        }

                        ViveHilo=false;
                        ViveHiloCon=false;
                        ObjPrg.resume();

                        tblCon.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                        tblCon.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

                        //Agregando el listener para cuando el usuario de click en un dia
//                        tblCon.addMouseListener(new java.awt.event.MouseAdapter() {
//                            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                                if(! ((tblCon.getSelectedColumn()==-1) || (tblCon.getSelectedRow() ==-1)) )
//                                    if(evt.getClickCount()==2){
////                                        for(int i=0; i<=cboCam.getItemCount()-1;i++)
////                                            Str_RegSel[i]= (tblCon.getValueAt(tblCon.getSelectedRow(), i )==null)?"":tblCon.getValueAt(tblCon.getSelectedRow(), i ).toString();
//
//                                            dispose();
//                                    }
//                            }
//                        });
//
//                        /*
//                         *   POner aki codigo para manejar enter
//                         *
//                         */
//
//                        javax.swing.KeyStroke enter = javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER,0);
//
//
//                        /*  Registrando el KeyBoard Listener para la tecla enter
//                         *  para ke se agregue una fila cuando la tecla enter es presionada 
//                         */
//
//
//                        tblCon.registerKeyboardAction( new java.awt.event.ActionListener(){
//                            public void actionPerformed(java.awt.event.ActionEvent evt){
//                                if(! ((tblCon.getSelectedColumn()==-1) || (tblCon.getSelectedRow() ==-1)) ){
////                                        for(int i=0; i<=cboCam.getItemCount()-1;i++)
////                                            Str_RegSel[i]= (tblCon.getValueAt(tblCon.getSelectedRow(), i )==null)?"":tblCon.getValueAt(tblCon.getSelectedRow(), i ).toString();
//                                            dispose();
//                                }
//                            }
//                        }, "enter", enter,javax.swing.JComponent.WHEN_FOCUSED );
//

                        if(VarCurReg==0)
                            lblSts.setText("No se encontro ninguna coincidencia");
                        else{
                            lblSts.setText("Todos los registros Cargados");
                            tblCon.requestFocus();
                            tblCon.setRowSelectionInterval(0,0);
                            tblCon.changeSelection(0, 0, false, false);
                        }
                        lblSts2.setText("Total de Registros : " + VarCurReg);


                        VarCurReg=0;
                        prgCon.setValue(0);
                        prgCon.setVisible(false);
                        prgCon.setString("0 %");

                        con.close();
                        Act_But(true);

                    }
                }              
               catch(SQLException Evt)
               {    
                    try{
                        con.rollback();
                        con.close();
                    }catch(SQLException Evts){
                        objUtil.mostrarMsgErr_F1(new javax.swing.JFrame(), Evts);
                    }

                    objUtil.mostrarMsgErr_F1(new javax.swing.JFrame(), Evt);
                }

                catch(Exception Evt)
                {
                    objUtil.mostrarMsgErr_F1(new javax.swing.JFrame(), Evt);
                 }                       
           }
           catch(SQLException Evt)
           {    
                objUtil.mostrarMsgErr_F1(new javax.swing.JFrame(), Evt);
            }            

        }
    }
    
    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        Cancela();
    }//GEN-LAST:event_butCanActionPerformed

    public java.util.Vector getDatos(){
        return vecDatos;
    }
    private void Cancela(){
        //Str_RegSel = null;
        dispose();
    }
    
    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed

        for(int i= 0 ; i< tblCon.getRowCount() ; i ++ ){
            //tblCon.getValueAt(
            if(tblCon.getValueAt(i, INT_TBL_SELECT)!=null && !tblCon.getValueAt(i, INT_TBL_SELECT).toString().trim().equals("")){
                       
                if(tblCon.getValueAt(i, INT_TBL_SELECT).toString().equals("true")){ 
                    java.util.Vector vecRowActual = new java.util.Vector();

                    vecRowActual.addElement( new Object() ) ;
                    vecRowActual.addElement( "" + int_co_cli ) ;
                    vecRowActual.addElement( new Integer(tblCon.getValueAt(i, INT_TBL_CO_REGPAG).toString())  ) ;
                    vecRowActual.addElement( new Object() ) ;
                    vecRowActual.addElement( str_Nomcli ) ;
                    vecRowActual.addElement( new Object() ) ;
                    vecRowActual.addElement( tblCon.getValueAt(i, INT_TBL_TIPDOC).toString() ) ;
                    vecRowActual.addElement( tblCon.getValueAt(i, INT_TBL_NOMDOC).toString() ) ;
                    vecRowActual.addElement( new Integer(tblCon.getValueAt(i, INT_TBL_CO_DOC).toString()) ) ;
                    vecRowActual.addElement( new Integer(tblCon.getValueAt(i, INT_TBL_NUMDOC).toString()) ) ;
                    vecRowActual.addElement( tblCon.getValueAt(i, INT_TBL_FE_DOC).toString() ) ;
                    vecRowActual.addElement( new Integer(tblCon.getValueAt(i, INT_TBL_DIACRE).toString()) ) ;
                    vecRowActual.addElement( tblCon.getValueAt(i, INT_TBL_FE_VEN).toString() ) ;
                    vecRowActual.addElement( new Double(tblCon.getValueAt(i, INT_TBL_RETENC).toString()) ) ;
                    vecRowActual.addElement( new Double(tblCon.getValueAt(i, INT_TBL_VALDOC).toString()) ) ;
                    vecRowActual.addElement( new Double(tblCon.getValueAt(i, INT_TBL_VALPEN).toString()) ) ;

                    vecDatos.addElement(vecRowActual);
                }

            }
        }
            
        dispose();
       
    }//GEN-LAST:event_butAceActionPerformed

    class Progreso extends Thread{
        
        public void run(){
            
            int porcent;
            while(ViveHilo) {
                lblSts.setText("Cargando datos en la tabla...");
                
                lblSts2.setText("Registros : "+ VarCurReg + " de " + Var_Rows);
                
                prgCon.setValue(VarCurReg);
                //Porcentaje de completado de consulta
                porcent = (VarCurReg==0)? 0 : (VarCurReg*100/Var_Rows);
                
                prgCon.setString(Integer.toString(porcent) + " %");
                suspend();
            }
            
        }
    }
    
        public void ResizeTbl() {
        //Asignando tamaÃ±o y definiendo ke no se pueda cambiar el tamaÃ±o
        tblCon.getTableHeader().setReorderingAllowed(false);
        
        tblCon.getColumnModel().getColumn(INT_TBL_LINEA).setPreferredWidth(20);
        
        tblCon.getColumnModel().getColumn(INT_TBL_SELECT).setPreferredWidth(30);
        
        tblCon.getColumnModel().getColumn(INT_TBL_TIPDOC).setPreferredWidth(50);
        
        tblCon.getColumnModel().getColumn(INT_TBL_NOMDOC).setPreferredWidth(100);
        
        tblCon.getColumnModel().getColumn(INT_TBL_NUMDOC).setPreferredWidth(50);
        
        tblCon.getColumnModel().getColumn(INT_TBL_FE_DOC).setPreferredWidth(100);
        
        tblCon.getColumnModel().getColumn(INT_TBL_DIACRE).setPreferredWidth(30);
        
        tblCon.getColumnModel().getColumn(INT_TBL_FE_VEN).setPreferredWidth(100);
        
        tblCon.getColumnModel().getColumn(INT_TBL_RETENC).setPreferredWidth(50);
        
        tblCon.getColumnModel().getColumn(INT_TBL_VALDOC).setPreferredWidth(50);
        
        tblCon.getColumnModel().getColumn(INT_TBL_VALPEN).setPreferredWidth(50);

        tblCon.getColumnModel().getColumn(INT_TBL_CO_REGPAG).setWidth(0);
        tblCon.getColumnModel().getColumn(INT_TBL_CO_REGPAG).setMaxWidth(0);
        tblCon.getColumnModel().getColumn(INT_TBL_CO_REGPAG).setMinWidth(0);
        tblCon.getColumnModel().getColumn(INT_TBL_CO_REGPAG).setPreferredWidth(0);

        tblCon.getColumnModel().getColumn(INT_TBL_CO_DOC).setWidth(0);
        tblCon.getColumnModel().getColumn(INT_TBL_CO_DOC).setMaxWidth(0);
        tblCon.getColumnModel().getColumn(INT_TBL_CO_DOC).setMinWidth(0);
        tblCon.getColumnModel().getColumn(INT_TBL_CO_DOC).setPreferredWidth(0);
        
    }    

    
 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JLabel lblSts;
    private javax.swing.JLabel lblSts2;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panSur;
    private javax.swing.JPanel panSurNor;
    private javax.swing.JPanel panSurSur;
    private javax.swing.JProgressBar prgCon;
    private javax.swing.JScrollPane spnCon;
    private javax.swing.JTable tblCon;
    // End of variables declaration//GEN-END:variables
    
}


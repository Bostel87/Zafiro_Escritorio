/*
 * ZafCxC08_02.java
 *
 * Created on 22 de septiembre de 2005, 9:09
 */

package CxC.ZafCxC08;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;

/**
 *
 * @author  jsalazar
 */
public class ZafCxC08_02 extends javax.swing.JDialog {
    private ZafParSis objZafParSis;
    private ZafUtil objUti;
    private Vector vecCab,vecDat,vecData;
    //Editores de tabla
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;        //Render: Presentar JLabel en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt;        //Editor: JTextField en celda.
    private Librerias.ZafColNumerada.ZafColNumerada objColNum;
    
    //Constantes
    private final int INT_TBL_LINEA    = 0;
    private final int INT_TBL_TIP_DOC  = 1;
    private final int INT_TBL_NOM_DOC  = 2;
    private final int INT_TBL_NUM_DOC  = 3;
    private final int INT_TBL_FEC_DOC  = 4;
    private final int INT_TBL_FEC_VEN  = 5;
    private final int INT_TBL_VAL_DOC  = 6;
    private final int INT_TBL_NOM_CLI  = 7;
    //VECTOR OBJ_CHQ
    private final int INT_VEC_TIP_DOC   = 0;
    private final int INT_VEC_COD_DOC   = 1;
    private final int INT_VEC_COD_REG   = 2;
    
    /** Creates new form ZafCxC08_02 */
    public ZafCxC08_02(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    public ZafCxC08_02(ZafParSis obj, Vector vecpar) {
        super(new java.awt.Frame(), true);
        initComponents();
        this.setTitle("Cheques Protestados.- Documento Relacionados");
        objZafParSis = obj;
        vecData = vecpar;
        objUti = new ZafUtil();
        if (!Configurar_tabla())
            dispose();
        cargarDatos();
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
            vecCab.add(INT_TBL_TIP_DOC,"Cod. Doc.");
            vecCab.add(INT_TBL_NOM_DOC,"Documento");            
            vecCab.add(INT_TBL_NUM_DOC,"Num. Documento");
            vecCab.add(INT_TBL_FEC_DOC,"Fec. Documento");
            vecCab.add(INT_TBL_FEC_VEN,"Fec. Vecimiento");
            vecCab.add(INT_TBL_VAL_DOC," Valor");            
            vecCab.add(INT_TBL_NOM_CLI,"Cliente");
            
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);

            tblDat.setModel(objTblMod);   
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//            objTblMod.setColumnDataType(INT_TBL_NUM_CHQ, objTblMod.INT_COL_INT, new Integer(0), null);
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
//            arlAux.add("" + INT_TBL_NUM_CHQ);
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
            tcmAux.getColumn(INT_TBL_TIP_DOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_NOM_DOC).setPreferredWidth(125);
            tcmAux.getColumn(INT_TBL_NUM_DOC).setPreferredWidth(75);                        
            tcmAux.getColumn(INT_TBL_FEC_DOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_FEC_VEN).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_VAL_DOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_NOM_CLI).setPreferredWidth(125);            
 
                       
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);                                        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            tblDat.getTableHeader().setReorderingAllowed(false);            
//            objMouMotAda=new ZafMouMotAda();            
//            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
//            vecAux.add("" + INT_TBL_NUM_CHQ);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
          //Configurar JTable: Renderizar celdas.


            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NOM_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FEC_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FEC_VEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NOM_CLI).setCellRenderer(objTblCelRenLbl);            
            objTblCelRenLbl=null;
           
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("######",true,true);
            tcmAux.getColumn(INT_TBL_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

             
            //Configurar JTable: Centrar columnas.
            blnRes=true;    
        }catch(Exception e) {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(this,e);
        }
        return blnRes;                        
    }    
    private void cargarDatos()
    {
        if (vecData!=null){
//            Vector vecTmp = (Vector) vecData.get(INT_TBL_OBJ_DOC);
            Vector vecTmp = vecData;
            String strSQL;
            
            try{
                java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                try{
                    if (con!=null){
                        java.sql.Statement stm = con.createStatement();
                        vecDat = new Vector();
                        for (int i=0;i<vecTmp.size();i++)  {
                            strSQL = " select tx_descor,tx_deslar,ne_numdoc,cab.fe_ing,fe_ven,nd_monchq,tx_nom "+
                                     " from tbm_pagmovinv as pag left outer join tbm_cabmovinv as cab on (cab.co_emp = pag.co_emp and cab.co_loc = pag.co_loc and cab.co_tipdoc = pag.co_tipdoc and cab.co_doc = pag.co_doc)left outer join tbm_cabtipdoc as tipdoc on (cab.co_emp = tipdoc.co_emp and cab.co_loc = tipdoc.co_loc and cab.co_tipdoc = tipdoc.co_tipdoc) left outer join tbm_cli as cli on (cab.co_emp = cli.co_emp and cab.co_cli = cli.co_cli) " +
                                     " where cab.co_emp =" + objZafParSis.getCodigoEmpresa() +" and cab.co_loc = "+ objZafParSis.getCodigoLocal() +" and cab.co_tipdoc = " + ((Vector)vecTmp.get(i)).get(INT_VEC_TIP_DOC).toString() + " and cab.co_doc = " + ((Vector)vecTmp.get(i)).get(INT_VEC_COD_DOC).toString() + " and pag.co_reg="+ ((Vector)vecTmp.get(i)).get(INT_VEC_COD_REG).toString() +" and cab.st_reg='A' and pag.st_reg='A'" ;
                            System.out.println(strSQL);
                            java.sql.ResultSet rst = stm.executeQuery(strSQL);                        
                            while (rst.next()){
                                Vector vecReg =  new Vector();
                                vecReg.add(INT_TBL_LINEA,"");
                                vecReg.add(INT_TBL_TIP_DOC,rst.getString("tx_descor"));
                                vecReg.add(INT_TBL_NOM_DOC,rst.getString("tx_deslar"));
                                vecReg.add(INT_TBL_NUM_DOC,rst.getString("ne_numdoc"));
                                vecReg.add(INT_TBL_FEC_DOC,rst.getString("fe_ing"));
                                vecReg.add(INT_TBL_FEC_VEN,rst.getString("fe_ven"));
                                vecReg.add(INT_TBL_VAL_DOC,rst.getString("nd_monchq"));
                                vecReg.add(INT_TBL_NOM_CLI,rst.getString("tx_nom"));
                                vecDat.add(vecReg);
                            }                     
                            rst.close();
                        }
                        objTblMod.setData(vecDat);
                        tblDat.setModel(objTblMod);                                                 
                        stm.close();
                        con.close();
                    }
                }catch(java.sql.SQLException e){
                    objUti.mostrarMsgErr_F1(this,e);
                }
            }catch(Exception e){
                objUti.mostrarMsgErr_F1(this,e);
            }
            
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        butCer = new javax.swing.JButton();

        getContentPane().setLayout(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
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

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 32, 700, 140);

        butCer.setText("Cerrar");
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });

        getContentPane().add(butCer);
        butCer.setBounds(625, 190, 80, 25);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-740)/2, (screenSize.height-250)/2, 740, 250);
    }//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_butCerActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new ZafCxC08_02(new javax.swing.JFrame(), true).show();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
    
}

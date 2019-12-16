
package Librerias.ZafConRecHum.ZafConRecHumNotVenCon;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;
import java.util.Vector;
/**
 * Clase nueva para la notificación de empleados que se les vence el contrato.
 * @author Roberto Flores
 * Guayaquil 05/03/2013
 */
public class ZafConRecHumNotVenCon_01 extends javax.swing.JDialog {
    
    //Constantes: Columnas del JTable:
    private final int INT_TBL_LIN=0;                        //LINEA
    private final int INT_TBL_CODEMP=1;                     //CODIGO DE EMPRESA
    private final int INT_TBL_EMP=2;                        //EMPRESA
    private final int INT_TBL_CODTRA=3;                     //CODIGO DEL EMPLEADO
    private final int INT_TBL_NOMTRA=4;                     //EMPLEADO
    private final int INT_TBL_TIPO_VEN=5;                   //TIPO DE VENCIMIENTO
    private final int INT_TBL_FECHA_VEN=6;                  //FECHA DE VENCIMIENTO
    private final int INT_TBL_DAT_BUT_ANE=7;                //BOTON DE ANEXO AL MAESTRO DE EMPLEADOS    
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;

    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private String strAux;
    private ArrayList arlDat;
    
    javax.swing.JDesktopPane dskGen;
    
    private String strVersion="v1.05";
    
    /** Creates new form ZafConRecHum_01 */
    public ZafConRecHumNotVenCon_01(java.awt.Frame parent, boolean modal, java.util.ArrayList vector , ZafParSis obj,  javax.swing.JDesktopPane dskGe) {  //(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        objParSis=obj;
        initComponents();
        arlDat=vector;
        dskGen=dskGe;
        configurarFrm();
        cargarDatos();  
    }
       
    
       /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux="Vencimientos de Contratos " + strVersion;  //objParSis.getNombreMenu();
            this.setTitle(strAux);
            lblTit.setText(strAux);
            //Configurar objetos.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();

            vecCab.add(INT_TBL_LIN,"");
            vecCab.add(INT_TBL_CODEMP,"Cód. Emp.");
            vecCab.add(INT_TBL_EMP,"Empresa");
            vecCab.add(INT_TBL_CODTRA,"Cód. Tra");
            vecCab.add(INT_TBL_NOMTRA,"Empleado");
            vecCab.add(INT_TBL_TIPO_VEN,"Tip. Con.");
            vecCab.add(INT_TBL_FECHA_VEN,"Fec. Ven.");
            vecCab.add(INT_TBL_DAT_BUT_ANE,"...");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
             tblDat.setRowSelectionAllowed(true);
             tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            new ZafColNumerada(tblDat,INT_TBL_LIN);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
           
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_EMP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CODTRA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NOMTRA).setPreferredWidth(180);
            tcmAux.getColumn(INT_TBL_TIPO_VEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_FECHA_VEN).setPreferredWidth(70);
            
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE).setPreferredWidth(20);
            
            tblDat.getTableHeader().setReorderingAllowed(false);           
            
            /* Aqui se agrega las columnas que van a ser ocultas*/
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODEMP);
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                arlColHid.add(""+INT_TBL_EMP);
            }
            arlColHid.add(""+INT_TBL_CODTRA);
//            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                arlColHid.add(""+INT_TBL_DAT_BUT_ANE);
//            }
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            
             Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE).setCellRenderer(zafTblDocCelRenBut);
//            
//            //Configurar JTable: Editor de celdas.
//            objTblCelEdiButGen=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
//            tcmAux.getColumn(INT_TBL_BUT_ANE).setCellEditor(objTblCelEdiButGen);
//            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                int intFilSel, intColSel;
//                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    
//                    }
//                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                     intFilSel=tblDat.getSelectedRow();
//                        if (intFilSel!=-1)
//                        {
//                            llamarRepNovEmp(objZafParSis,
//                                    objTblMod.getValueAt(intFilSel, INT_TBL_CODTRA).toString(),
//                                    objTblMod.getValueAt(intFilSel, INT_TBL_NOMTRA).toString(),
//                                    String.valueOf(cboPerAAAA.getSelectedIndex()),
//                                    String.valueOf(cboPerMes.getSelectedIndex()),
//                                    String.valueOf(cboPer.getSelectedIndex())
//                                    );
//                        }
//                 }
//                    
//           });
            
            //Configurar JTable: Editor de la tabla. 
            new ZafTblEdi(tblDat);  

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private void cargarDatos(){
        vecDat.clear();
        for(int i=0; i<arlDat.size(); i++){
          
            vecReg=new Vector();
            vecReg.add(INT_TBL_LIN,"");
            vecReg.add(INT_TBL_CODEMP, objUti.getStringValueAt(arlDat, i, 0) );
            vecReg.add(INT_TBL_EMP, objUti.getStringValueAt(arlDat, i, 1) );
            vecReg.add(INT_TBL_CODTRA, objUti.getStringValueAt(arlDat, i, 2) );
            vecReg.add(INT_TBL_NOMTRA, objUti.getStringValueAt(arlDat, i, 3) ); 
            vecReg.add(INT_TBL_TIPO_VEN, objUti.getStringValueAt(arlDat, i, 4) ); 
            vecReg.add(INT_TBL_FECHA_VEN, objUti.getStringValueAt(arlDat, i, 5) );
            vecReg.add(INT_TBL_DAT_BUT_ANE, objUti.getStringValueAt(arlDat, i, 6) );
            vecDat.add(vecReg);
        }
        objTblMod.setData(vecDat);  
        tblDat.setModel(objTblMod);
        vecDat.clear();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        jPanel2.add(lblTit, java.awt.BorderLayout.NORTH);

        jTabbedPane1.setMinimumSize(new java.awt.Dimension(53, 50));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(459, 300));

        jPanel1.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 24));
        panGenCab.setLayout(null);
        jPanel1.add(panGenCab, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

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

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("General", jPanel1);

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.BorderLayout());

        butCan.setText("Cancelar");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        jPanel4.add(butCan);

        panBot.add(jPanel4, java.awt.BorderLayout.EAST);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        jPanel2.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        setBounds(800, 600, 465, 410);
    }// </editor-fold>//GEN-END:initComponents
   
    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
            dispose();
    }//GEN-LAST:event_butCanActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
    
}
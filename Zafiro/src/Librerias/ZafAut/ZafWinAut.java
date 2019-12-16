/*
 * ZafWinAut2.java
 *
 * Created on 15 de noviembre de 2005, 17:28
 */

package Librerias.ZafAut;

import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafUtil.ZafUtil;
import java.awt.Color;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  ireyes
 */
public class ZafWinAut extends javax.swing.JDialog {
    
    final private int INT_TBL_LINEA    = 0 ;            //fecha de documento
    final private int INT_TBL_DAT_CHK_AUT = 1;
    final private int INT_TBL_DESCOR   = 2 ;            //CODIGO ALTERNO DE ITEM
    final private int INT_TBL_OBS1     = 3 ;            //Observacion de la regla
    final private int INT_TBL_CO_REG   = 4 ;            //Codigo de regla (Hide)
    
    
    /**
     * retorna un entero 
     *-1 ocurrio algun error 
     * 2 Solicita autorizacion
     * 3 Cancela la grabacion.. El usuario no quiere hacer ninguna accion.
     */    
    private int INT_RESUTADO   = 1 ;//RESPUESTA DEL USUARIO
    
    private java.util.Vector vecLstCtrls ; 
    
    private Librerias.ZafPopupMenu.ZafPopupMenu objPopUpMnu ;
    
    private String strVersion="v0.11";
    private ZafUtil objUti;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private ZafTblMod objTblMod;
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblFilCab objTblFilCab;
    private ZafMouMotAda objMouMotAda;
    private ZafTblOrd objTblOrd;                  //JTable de ordenamiento.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    
    /** Creates new form ZafWinAut2 */
    public ZafWinAut(java.awt.Frame parent, boolean modal, java.util.Vector vecLstCrtls) {
        super(parent, modal);
        this.vecLstCtrls = vecLstCrtls;
        objUti=new ZafUtil();
        initComponents();
        lblVersion.setText(strVersion);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                INT_RESUTADO=3;
                dispose();
            }
        });
               
        pack();
        spnMotivos.setColumnHeader(null);
        //objPopUpMnu = new Librerias.ZafPopupMenu.ZafPopupMenu(tblMot);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-400)/2, (screenSize.height-400)/2, 400, 400);
        configurarTblDat();
        tblMot.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); 
        cargarRegistros();
    }
    
   
    
    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(25);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"");
            vecCab.add(INT_TBL_DAT_CHK_AUT,"");
            vecCab.add(INT_TBL_DESCOR,"Descripción");
            vecCab.add(INT_TBL_OBS1,"Observación");
            vecCab.add(INT_TBL_CO_REG,"Cód.Reg.");
           
 
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblMot.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblMot.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); 
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblMot.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblMot.getColumnModel();
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DESCOR).setPreferredWidth(300);
            tcmAux.getColumn(INT_TBL_OBS1).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_CO_REG).setPreferredWidth(100); 
       
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblMot.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
             
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblMot.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblMot);
            tcmAux.getColumn(INT_TBL_LINEA).setCellRenderer(objTblFilCab);
            
            
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_OBS1);
            arlColHid.add(""+INT_TBL_CO_REG);
            objTblMod.setSystemHiddenColumns(arlColHid, tblMot);   
            arlColHid=null;
            
            
              
            //Cheks 
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setCellRenderer(objTblCelRenChk);  
            objTblCelRenChk.addTblCelRenListener(new ZafTblCelRenAdapter(){
                int intFil = -1;
                public void beforeRender(ZafTblCelRenEvent evt){
                    intFil = objTblCelRenChk.getRowRender();
                    if(  objUti.parseString(objTblMod.getValueAt(intFil, INT_TBL_DESCOR)).substring(0, 5).equals("Wendy")  ) {
                         objTblCelRenChk.setBackground(Color.YELLOW);
                    }
                    else{
                        objTblCelRenChk.setBackground(Color.WHITE);
                    }
                }
            });
//            objTblCelRenChk=null;
             



//            
//            java.util.Vector vecColNme = new java.util.Vector();
//            vecColNme.add("");
//            vecColNme.add("");
//            vecColNme.add("");
//            vecColNme.add("");
//            vecColNme.add("");
//            tblMot.setModel(new javax.swing.table.DefaultTableModel(vecLstCtrls, vecColNme)
//            {
//                Class[] types = new Class [] {
//                     java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
//                };
//
//                boolean[] canEdit = new boolean [] {
//                    false, false, false , false, false
//                };
//
//                public Class getColumnClass(int columnIndex) {
//                    return types [columnIndex];
//                }
//
//                public boolean isCellEditable(int rowIndex, int columnIndex) {
//                    return canEdit [columnIndex];
//                }
//            }        
//            );        

            //Agregamos el Listener para la seleccion de columnas en la tabla para ver los datos de la regla
            javax.swing.ListSelectionModel LsmCol = tblMot.getColumnModel().getSelectionModel();
            LsmCol.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                    javax.swing.ListSelectionModel lsm = (javax.swing.ListSelectionModel)e.getSource();

                    if (!lsm.isSelectionEmpty()) {

                        /*  Selecciono alguna columna
                         *   utilizamos lsm.getMaxSelectionIndex() y lsm.getMinSelectionIndex()
                         *   para saber que rango de columnas se selecciono y actualizar los DatePicker
                         */

                        int intCol = lsm.getMaxSelectionIndex();
                        int intRow = tblMot.getSelectedRow();                
                        if(intRow != -1){
                            //txtDesLar.setText(tblMot.getValueAt(intRow , INT_TBL_DESLAR) + "" );
                            txtObs1.setText(tblMot.getValueAt(intRow , INT_TBL_OBS1) + "" );
                        }
                    }
                }
            });
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DESCOR).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                int intFil = -1;
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLbl.getRowRender();
                    if(  objUti.parseString(objTblMod.getValueAt(intFil, INT_TBL_DESCOR)).substring(0, 5).equals("Wendy")  ) {
                         objTblCelRenLbl.setBackground(Color.YELLOW);
                    }
                    else{
                        objTblCelRenLbl.setBackground(Color.WHITE);
                    }
                }
            });
            
            
   
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
//            objTblOrd=new ZafTblOrd(tblMot);
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
      
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblVersion = new javax.swing.JLabel();
        spnMotivos = new javax.swing.JScrollPane();
        tblMot = new javax.swing.JTable();
        panSur = new javax.swing.JPanel();
        panSurNor = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        spnDesPar = new javax.swing.JScrollPane();
        txtObs1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        spnDesMot = new javax.swing.JScrollPane();
        txtDesLar = new javax.swing.JTextArea();
        panSurSur = new javax.swing.JPanel();
        butSolAut = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Listado de controles");

        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("El documento no se puede grabar por los siguientes motivos:");
        jPanel3.add(jLabel1, java.awt.BorderLayout.SOUTH);

        lblVersion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVersion.setText("Version");
        jPanel3.add(lblVersion, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.NORTH);

        tblMot.setModel(new javax.swing.table.DefaultTableModel(
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
        spnMotivos.setViewportView(tblMot);

        getContentPane().add(spnMotivos, java.awt.BorderLayout.CENTER);

        panSur.setLayout(new java.awt.BorderLayout());

        panSurNor.setLayout(new java.awt.GridLayout(2, 1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setPreferredSize(new java.awt.Dimension(32, 70));
        jPanel2.setLayout(new java.awt.BorderLayout());

        spnDesPar.setPreferredSize(new java.awt.Dimension(20, 40));

        txtObs1.setEditable(false);
        txtObs1.setPreferredSize(new java.awt.Dimension(0, 40));
        spnDesPar.setViewportView(txtObs1);

        jPanel2.add(spnDesPar, java.awt.BorderLayout.CENTER);

        panSurNor.add(jPanel2);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Observación"));
        jPanel1.setLayout(new java.awt.BorderLayout());

        spnDesMot.setViewportView(txtDesLar);

        jPanel1.add(spnDesMot, java.awt.BorderLayout.CENTER);

        panSurNor.add(jPanel1);

        panSur.add(panSurNor, java.awt.BorderLayout.NORTH);

        butSolAut.setText("Solicitar Autorización");
        butSolAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSolAutActionPerformed(evt);
            }
        });
        panSurSur.add(butSolAut);

        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSurSur.add(butCan);

        panSur.add(panSurSur, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panSur, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void butSolAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSolAutActionPerformed
        // TODO add your handling code here:
        INT_RESUTADO = 2;
        dispose();
    }//GEN-LAST:event_butSolAutActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        INT_RESUTADO = 3;
        dispose();
    }//GEN-LAST:event_butCanActionPerformed
    /**
     * Obtiene la respuesta que usuario dio a la ventana
     *-1 = Ocurrio algun error
     * 2 = Solicita autorizacion
     * 3 = Cancela la ventana
     */ 
    public int getResultado(){
       return INT_RESUTADO;
    }
    
    public java.util.Vector getVecCtls(){
        return vecLstCtrls;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        ZafWinAut objWinAut = new ZafWinAut(new javax.swing.JFrame(), true, new java.util.Vector());
        objWinAut.show();
        objWinAut = null;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.JButton butSolAut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblVersion;
    private javax.swing.JPanel panSur;
    private javax.swing.JPanel panSurNor;
    private javax.swing.JPanel panSurSur;
    private javax.swing.JScrollPane spnDesMot;
    private javax.swing.JScrollPane spnDesPar;
    private javax.swing.JScrollPane spnMotivos;
    private javax.swing.JTable tblMot;
    private javax.swing.JTextArea txtDesLar;
    private javax.swing.JTextArea txtObs1;
    // End of variables declaration//GEN-END:variables
    
    public String getObservacion1()
    {
        return txtDesLar.getText();
    }
    
    
    
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblMot.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_LINEA: strMsg=""; break;
                case INT_TBL_DAT_CHK_AUT: strMsg="Control necesita autorización"; break;
                case INT_TBL_DESCOR: strMsg="Nombre del control..."; break;
                case INT_TBL_OBS1: strMsg="Observación..."; break;
//                case INT_TBL_CO_REG: strMsg="Nombre del Local"; break;
                default: strMsg=""; break;
                        
            }
            tblMot.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
    private boolean cargarRegistros(){
        boolean blnRes=true;
        try{
            objTblMod.setData(vecLstCtrls);
            tblMot.setModel(objTblMod);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

}

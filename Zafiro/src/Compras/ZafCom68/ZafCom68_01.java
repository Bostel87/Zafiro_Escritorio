/*
 * ZafVen01_01.java
 *
 * Created on 9 de marzo de 2006, 9:12
 *
 * v0.1 
 */
package Compras.ZafCom68;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;
/**
 *
 * @author  Ingrid
 */
public class ZafCom68_01 extends javax.swing.JDialog {
    
    
    //Constantes: Columnas del JTable:    
    //Variable
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    public String strObs;
    private boolean blnCon;                             //true: Continua la ejecucián del hilo.
    //Variables de la clase.
   
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_CHK=1;
    final int INT_TBL_DAT_COD_REG=2;
    final int INT_TBL_DAT_NOM_PED=3;
    
    private Vector vecReg, vecDat, vecCab, vecAux;
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL;
    private int intCodPrv;
    private int intCodBen;
    private int intCodCta;
    private String strFecFilDoc;
    private int intFilFec;
    private int intExiFec;
    private String strRstFec;
    private String strAux;


    private ArrayList arlReg, arlDat;
    final int INT_ARL_COD_REG=0;
    final int INT_ARL_NOM_PED=1;
    final int INT_ARL_EST_ELI=2;


    
    
    public ZafCom68_01(java.awt.Frame parent, boolean modal, ZafParSis obj) {
        super(parent, modal);
            initComponents();
            //Inicializar objetos.
            objParSis=obj;
            arlDat=new ArrayList();
            if(configurarFrm()){}
                //cargarPedidos();
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panGen = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        butEli = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 12)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Listado de pedidos");
        PanFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenDet.setLayout(new java.awt.BorderLayout());

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

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        PanFrm.add(panGen, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.BorderLayout());

        butEli.setText("Eliminar");
        butEli.setPreferredSize(new java.awt.Dimension(92, 25));
        butEli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEliActionPerformed(evt);
            }
        });
        jPanel1.add(butEli);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Si presiona cancelar se borrará lo que ha ingresado");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        jPanel1.add(butCan);

        panBot.add(jPanel1, java.awt.BorderLayout.EAST);

        jPanel2.setLayout(new java.awt.BorderLayout());
        panBot.add(jPanel2, java.awt.BorderLayout.WEST);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-517)/2, (screenSize.height-258)/2, 517, 258);
    }// </editor-fold>//GEN-END:initComponents

    private void butEliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEliActionPerformed
        // TODO add your handling code here:        
        String strLin="";
        for(int i=0; i<objTblMod.getRowCountTrue(); i++){
            strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
            if(strLin.equals("M")){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    strAux="<HTML>Está seguro que desea eliminar el Pedido <FONT COLOR=\"blue\">" + objTblMod.getValueAt(i, INT_TBL_DAT_NOM_PED) + "</FONT>.<BR>Se borrarán todos los datos relacionados al pedido seleccionado<BR>Está seguro que desea continuar con la eliminación del pedido?</HTML>";
                    switch (mostrarMsgCon(strAux)){
                        case 0: //YES_OPTION
                            arlReg=new ArrayList();
                            arlReg.add(INT_ARL_COD_REG, objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG));
                            arlReg.add(INT_ARL_NOM_PED, objTblMod.getValueAt(i, INT_TBL_DAT_NOM_PED));
                            arlReg.add(INT_ARL_EST_ELI, "S");
                            arlDat.add(arlReg);
                            System.out.println("POR YES");
                            break;
                        case 1: //NO_OPTION
                            System.out.println("POR NO");
                            break;
                        case 2: //CANCEL_OPTION
                            System.out.println("POR CANCEL");
                            break;
                    }
                }
            }       
        }
        System.out.println("arlDat:  " + arlDat.toString());
        dispose();

        
    }//GEN-LAST:event_butEliActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        strFecFilDoc="";
        dispose();
    }//GEN-LAST:event_butCanActionPerformed
   

       
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butEli;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
       
    
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(4);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_NOM_PED,"Pedido");

            objUti=new ZafUtil();
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_PED).setPreferredWidth(120);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
                        
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
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
    
    public boolean cargarPedidos(ArrayList arlDat, String registros){
        boolean blnRes=true;
        int intCodRegArl=-1;
        int intCodRegDb=-1;
        String strEstPedEli="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT co_reg, tx_nom";
                strSQL+=" FROM tbm_nompedcurcfgimp";
                strSQL+=" WHERE tx_nom NOT IN('')";
                if(!registros.equals(""))
                    strSQL+=" AND co_reg IN(" + registros + ")";
                strSQL+=" ORDER BY tx_nom";
                System.out.println("SQL cargarPedidos: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_CHK,"");
                    intCodRegDb=rst.getInt("co_reg");
                    vecReg.add(INT_TBL_DAT_COD_REG,"" + intCodRegDb);
                    vecReg.add(INT_TBL_DAT_NOM_PED,rst.getString("tx_nom"));

                    for(int k=0; k<arlDat.size(); k++){
                        intCodRegArl=objUti.getIntValueAt(arlDat, k, INT_ARL_COD_REG);
                        strEstPedEli=objUti.getStringValueAt(arlDat, k, INT_ARL_EST_ELI);
                        if(intCodRegDb==intCodRegArl){
                            if(strEstPedEli.equals("S")){//si fue marcado para ser eliminado
                                vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK);
                            }
                        }
                    }
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    

    /**
     * Esta funcián muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }


    public ArrayList getEstadoPedido(){
        return arlDat;
    }


}

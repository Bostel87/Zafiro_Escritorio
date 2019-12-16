/*
 * ZafCom46.java
 *
 * Created on 9 de marzo de 2006, 9:12
 *
 * v0.1 
 */
package Compras.ZafCom46;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
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
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
/**
 *
 * @author  Ingrid
 */
public class ZafCom46_01 extends javax.swing.JDialog {
    
    
    //Constantes: Columnas del JTable:    
    //Variable
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    
    final int INT_TBL_DAT_HIS_LIN=0;
    final int INT_TBL_DAT_HIS_COD_EMP=1;
    final int INT_TBL_DAT_HIS_COD_HIS=2;
    final int INT_TBL_DAT_HIS_COD_ITM=3;
    final int INT_TBL_DAT_HIS_COD_ALT_ITM=4;
    final int INT_TBL_DAT_HIS_NOM_ITM=5;
    final int INT_TBL_DAT_HIS_COD_BOD=6;
    final int INT_TBL_DAT_HIS_NOM_BOD=7;
    final int INT_TBL_DAT_HIS_STK=8;
    final int INT_TBL_DAT_HIS_CAN_ING_BOD=9;
    final int INT_TBL_DAT_HIS_CAN_EGR_BOD=10;
    final int INT_TBL_DAT_HIS_CAN_SOB=11;
    final int INT_TBL_DAT_HIS_FEC_HIS=12;
    final int INT_TBL_DAT_HIS_COD_USR_HIS=13;
    final int INT_TBL_DAT_HIS_ALI_USR_HIS=14;
    final int INT_TBL_DAT_HIS_NOM_USR_HIS=15;
    
    
    private Vector vecReg, vecDat, vecCab, vecAux;
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblFilCab objTblFilCab;
    
    private int intCodItmGrp;
    private int intCodBodGrp;
    private String strSQL;
    private ZafTblCelRenLbl objTblCelRenLbl;


    
    public ZafCom46_01(java.awt.Frame parent, boolean modal, ZafParSis obj) {
        super(parent, modal);
        try{
            initComponents();
              //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            if(configurarFrm()){
            }
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
        
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
        butCer = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 12)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Listado de documentos no guardados(Otro usuario realizó cambios mientras ud. los tenía en pantalla)");
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

        butCer.setText("Cerrar");
        butCer.setToolTipText("Si presiona cancelar se borrará lo que ha ingresado");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        jPanel1.add(butCer);

        panBot.add(jPanel1, java.awt.BorderLayout.EAST);

        jPanel2.setLayout(new java.awt.BorderLayout());
        panBot.add(jPanel2, java.awt.BorderLayout.WEST);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-663)/2, (screenSize.height-430)/2, 663, 430);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        // TODO add your handling code here:
        dispose();
}//GEN-LAST:event_butCerActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butCer;
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
            vecCab=new Vector(16);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_HIS_LIN,"");
            vecCab.add(INT_TBL_DAT_HIS_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_HIS_COD_HIS,"Cód.His.");
            vecCab.add(INT_TBL_DAT_HIS_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_HIS_COD_ALT_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_HIS_NOM_ITM,"Item");
            vecCab.add(INT_TBL_DAT_HIS_COD_BOD,"Cód.Bod.");
            vecCab.add(INT_TBL_DAT_HIS_NOM_BOD,"Bodega");
            vecCab.add(INT_TBL_DAT_HIS_STK,"Stock");
            vecCab.add(INT_TBL_DAT_HIS_CAN_ING_BOD,"Can.Pnd.Ing");
            vecCab.add(INT_TBL_DAT_HIS_CAN_EGR_BOD,"Can.Pnd.Egr.");
            vecCab.add(INT_TBL_DAT_HIS_CAN_SOB,"Can.Sob.");
            vecCab.add(INT_TBL_DAT_HIS_FEC_HIS,"Fec.His.");
            vecCab.add(INT_TBL_DAT_HIS_COD_USR_HIS,"Cód.Usr.His.");
            vecCab.add(INT_TBL_DAT_HIS_ALI_USR_HIS,"Usuario");
            vecCab.add(INT_TBL_DAT_HIS_NOM_USR_HIS,"Nom.Usr.His.");
            
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
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_HIS_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_HIS_COD_HIS).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_HIS_COD_ITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_HIS_COD_ALT_ITM).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NOM_ITM).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_DAT_HIS_COD_BOD).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NOM_BOD).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_HIS_STK).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CAN_ING_BOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CAN_EGR_BOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CAN_SOB).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_HIS_FEC_HIS).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_DAT_HIS_COD_USR_HIS).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ALI_USR_HIS).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NOM_USR_HIS).setPreferredWidth(20);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_HIS_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_HIS_COD_HIS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_HIS_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_HIS_COD_BOD, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_HIS_CAN_ING_BOD, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_HIS_CAN_EGR_BOD, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_HIS_COD_USR_HIS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_HIS_NOM_USR_HIS, tblDat);

            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setCellRenderer(objTblFilCab);
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_HIS_STK).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CAN_ING_BOD).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CAN_EGR_BOD).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CAN_SOB).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;       
            
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
    


    public boolean cargarHistorico(){
        boolean blnRes=true;
        Connection conHis;
        Statement stmHis;
        ResultSet rstHis;
        try{
            conHis=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conHis!=null){
                stmHis=conHis.createStatement();
                strSQL="";
                strSQL+="SELECT a2.tx_codAlt, a2.tx_nomitm, ";
                strSQL+="        a1.co_emp, a1.co_bod, a1.co_itm, a1.co_his, a1.nd_stkact, a1.nd_stkmin, a1.nd_stkmax, ";
                strSQL+="        a1.nd_promenvtaman, a1.nd_stkexc, a1.nd_stkminsug, a1.tx_obsstkminsug, a1.nd_nummesrep, ";
                strSQL+="        a1.nd_repmin, a1.nd_repmax, a1.nd_caningbod, a1.nd_canegrbod, a1.nd_cansob, ";
                strSQL+="        a1.fe_ultMod, a1.co_usrMod,";
                strSQL+="        a3.tx_nom AS  tx_nomBod, a4.tx_usr AS tx_usrHis, a4.tx_nom AS tx_nomUsrHis";
                strSQL+="   FROM tbm_inv AS a2 INNER JOIN (tbh_invbod AS a1 LEFT OUTER JOIN tbm_usr AS a4 ON a1.co_usrMod=a4.co_usr)";
                strSQL+="   ON a2.co_emp=a1.co_emp AND a2.co_itm=a1.co_itm";
                strSQL+="   INNER JOIN tbm_bod AS a3";
                strSQL+="   ON a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod";
                strSQL+="   WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " /*AND a1.nd_cansob>0*/";
                strSQL+="   AND a1.co_itm=" + intCodItmGrp + " AND a1.co_bod=" + intCodBodGrp + "";
                strSQL+="   GROUP BY a2.tx_codAlt, a2.tx_nomitm, ";
                strSQL+="        a1.co_emp, a1.co_bod, a1.co_itm, a1.co_his, a1.nd_stkact, a1.nd_stkmin, a1.nd_stkmax, ";
                strSQL+="        a1.nd_promenvtaman, a1.nd_stkexc, a1.nd_stkminsug, a1.tx_obsstkminsug, a1.nd_nummesrep, ";
                strSQL+="        a1.nd_repmin, a1.nd_repmax, a1.nd_caningbod, a1.nd_canegrbod, a1.nd_cansob, ";
                strSQL+="        a1.fe_ultMod, a1.co_usrMod, a3.tx_nom, a4.tx_usr, a4.tx_nom";
                strSQL+="   ORDER BY a1.co_his";
                System.out.println("cargarHistorico: " + strSQL);
                rstHis=stmHis.executeQuery(strSQL);
                vecDat.clear();//Limpiar vector de datos.
                while(rstHis.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_HIS_LIN,"");
                    vecReg.add(INT_TBL_DAT_HIS_COD_EMP,     "" + rstHis.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_HIS_COD_HIS,     "" + rstHis.getString("co_his"));
                    vecReg.add(INT_TBL_DAT_HIS_COD_ITM,     "" + rstHis.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_HIS_COD_ALT_ITM, "" + rstHis.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_DAT_HIS_NOM_ITM,     "" + rstHis.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_DAT_HIS_COD_BOD,     "" + rstHis.getString("co_bod"));
                    vecReg.add(INT_TBL_DAT_HIS_NOM_BOD,     "Sob." + rstHis.getString("tx_nomBod"));
                    vecReg.add(INT_TBL_DAT_HIS_STK,         "" + rstHis.getString("nd_stkact"));
                    vecReg.add(INT_TBL_DAT_HIS_CAN_ING_BOD, "" + rstHis.getString("nd_caningbod"));
                    vecReg.add(INT_TBL_DAT_HIS_CAN_EGR_BOD, "" + rstHis.getString("nd_canegrbod"));
                    vecReg.add(INT_TBL_DAT_HIS_CAN_SOB,     "" + rstHis.getString("nd_cansob"));
                    vecReg.add(INT_TBL_DAT_HIS_FEC_HIS,     "" + rstHis.getString("fe_ultMod"));
                    vecReg.add(INT_TBL_DAT_HIS_COD_USR_HIS, "" + rstHis.getString("co_usrMod"));
                    vecReg.add(INT_TBL_DAT_HIS_ALI_USR_HIS, "" + rstHis.getString("tx_usrHis"));
                    vecReg.add(INT_TBL_DAT_HIS_NOM_USR_HIS, "" + rstHis.getString("tx_nomUsrHis"));
                    vecDat.add(vecReg);
                }
                conHis.close();
                conHis=null;
                stmHis.close();
                stmHis=null;
            }
            //Asignar vectores al modelo.
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
            vecDat.clear();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public int getCodigoBodegaGrupo() {
        return intCodBodGrp;
    }

    public void setCodigoBodegaGrupo(int intCodBodGrp) {
        this.intCodBodGrp = intCodBodGrp;
    }

    public int getCodigoItemGrupo() {
        return intCodItmGrp;
    }

    public void setCodigoItemGrupo(int intCodItmGrp) {
        this.intCodItmGrp = intCodItmGrp;
    }



    



}
/*
 * ZafVen01_01.java
 *
 * Created on 9 de marzo de 2006, 9:12
 *
 * v0.1 
 */
package CxC.ZafCxC76;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 *
 * @author  Ingrid Lino
 */
public class ZafCxC76_02 extends javax.swing.JDialog {
    
    
    //Constantes: Columnas del JTable:    
    //Variable
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    private int intOpcSelDlg;
    public String strObs;
    private boolean blnCon;                             //true: Continua la ejecuci�n del hilo.
    //Variables de la clase.
    private String strObsIni;
    
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_CHK=1;
    final int INT_TBL_DAT_COD_EMP=2;
    final int INT_TBL_DAT_COD_LOC=3;
    final int INT_TBL_DAT_COD_TIP_DOC=4;
    final int INT_TBL_DAT_DES_COR_TIP_DOC=5;
    final int INT_TBL_DAT_DES_LAR_TIP_DOC=6;
    final int INT_TBL_DAT_COD_DOC=7;
    final int INT_TBL_DAT_COD_REG=8;
    final int INT_TBL_DAT_NUM_DOC=9;
    final int INT_TBL_DAT_FEC_DOC=10;
    final int INT_TBL_DAT_DIA_CRE=11;
    final int INT_TBL_DAT_FEC_VEN=12;
    final int INT_TBL_DAT_POR_RET=13;
    final int INT_TBL_DAT_VAL_DOC=14;
    final int INT_TBL_DAT_VAL_PND=15;
    final int INT_TBL_DAT_VAL_DIS=16;
    //final int INT_TBL_DAT_VAL_APL=16;
    final int INT_TBL_DAT_VAL_APL_USR=17;
    final int INT_TBL_DAT_VAL_APL_USR_AUX=18;
    final int INT_TBL_DAT_VAL_APL_USR_AUX_MOD_TBL=19;
    final int INT_TBL_DAT_COD_EMP_REL=20;
    final int INT_TBL_DAT_COD_LOC_REL=21;
    final int INT_TBL_DAT_COD_TIP_DOC_REL=22;
    final int INT_TBL_DAT_COD_DOC_REL=23;
    final int INT_TBL_DAT_COD_REG_REL=24;
    final int INT_TBL_DAT_COD_CLI=25;
    final int INT_TBL_DAT_INT_SIG=26;
    
    private Vector vecReg, vecDat, vecCab, vecAux;
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL;

    private String strPanDiaLogEdi;
//    private int intExiFilSel;
    private BigDecimal bdeValApl;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblCelEdiTxt objTblCelEdiTxt;

//
    ArrayList arlRegIni, arlDatIni;
    final int INT_TBL_ARL_INI_COD_EMP_FAC=0;
    final int INT_TBL_ARL_INI_COD_LOC_FAC=1;
    final int INT_TBL_ARL_INI_COD_TIP_DOC_FAC=2;
    final int INT_TBL_ARL_INI_COD_DOC_FAC=3;
    final int INT_TBL_ARL_INI_COD_REG_FAC=4;
    final int INT_TBL_ARL_INI_VAL_DIS=5;




    ArrayList arlReg, arlDat;
    final int INT_TBL_ARL_COD_EMP_FAC=0;
    final int INT_TBL_ARL_COD_LOC_FAC=1;
    final int INT_TBL_ARL_COD_TIP_DOC_FAC=2;
    final int INT_TBL_ARL_COD_DOC_FAC=3;
    final int INT_TBL_ARL_COD_REG_FAC=4;
    final int INT_TBL_ARL_VAL_APL_FAC=5;
    final int INT_TBL_ARL_VAL_APL_FAC_AUX=6;
    final int INT_TBL_ARL_VAL_APL_FAC_AUX_MOD_TBL=7;
    final int INT_TBL_ARL_COD_EMP_COB=8;
    final int INT_TBL_ARL_COD_LOC_COB=9;
    final int INT_TBL_ARL_COD_TIP_DOC_COB=10;
    final int INT_TBL_ARL_COD_DOC_COB=11;
    final int INT_TBL_ARL_COD_REG_COB=12;
    final int INT_TBL_ARL_EST_ELI=13;




    

    private int intButSelDlg;                                   //Botón seleccionado en el JDialog.

    public static final int INT_BUT_CAN=0;                      /**Un valor para getSelectedButton: Indica "Botón Cancelar".*/
    public static final int INT_BUT_ACE=1;                      /**Un valor para getSelectedButton: Indica "Botón Aceptar".*/

    private char chrOpcTooBar;
    private int intSig;

    private BigDecimal bdeValAplUsrMod;
    private String strCodCliValDis;

    private String strEditable;
    
    public ZafCxC76_02(java.awt.Frame parent, boolean modal, ZafParSis obj) {
         super(parent, modal);
         try{
            initComponents();
              //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            arlDat=new ArrayList();
            arlDatIni=new ArrayList();

            if( ! configurarFrm()){
                dispose();
            }

            intButSelDlg=INT_BUT_CAN;
            

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
      jPanel3 = new javax.swing.JPanel();
      lblTit = new javax.swing.JLabel();
      jLabel1 = new javax.swing.JLabel();
      txtValAplTot = new javax.swing.JTextField();
      jLabel2 = new javax.swing.JLabel();
      txtValSelTot = new javax.swing.JTextField();
      jLabel3 = new javax.swing.JLabel();
      txtTotDif = new javax.swing.JTextField();
      panGen = new javax.swing.JPanel();
      panGenDet = new javax.swing.JPanel();
      spnDat = new javax.swing.JScrollPane();
      tblDat = new javax.swing.JTable();
      panBar = new javax.swing.JPanel();
      panBot = new javax.swing.JPanel();
      jPanel1 = new javax.swing.JPanel();
      butAce = new javax.swing.JButton();
      butCan = new javax.swing.JButton();
      jPanel2 = new javax.swing.JPanel();

      setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
      addWindowListener(new java.awt.event.WindowAdapter() {
         public void windowClosing(java.awt.event.WindowEvent evt) {
            exitForm(evt);
         }
      });

      PanFrm.setLayout(new java.awt.BorderLayout());

      jPanel3.setPreferredSize(new java.awt.Dimension(100, 85));
      jPanel3.setLayout(null);

      lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 12)); // NOI18N
      lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      lblTit.setText("Listado de facturas a las que aplica el valor depositado");
      jPanel3.add(lblTit);
      lblTit.setBounds(0, 2, 690, 16);

      jLabel1.setText("Valor que se puede aplicar:");
      jPanel3.add(jLabel1);
      jLabel1.setBounds(10, 26, 150, 14);

      txtValAplTot.setEditable(false);
      jPanel3.add(txtValAplTot);
      txtValAplTot.setBounds(160, 22, 120, 20);

      jLabel2.setText("Valor aplicado:");
      jPanel3.add(jLabel2);
      jLabel2.setBounds(10, 44, 150, 14);

      txtValSelTot.setEditable(false);
      jPanel3.add(txtValSelTot);
      txtValSelTot.setBounds(160, 42, 120, 20);

      jLabel3.setText("Diferencia:");
      jPanel3.add(jLabel3);
      jLabel3.setBounds(10, 64, 150, 14);

      txtTotDif.setEditable(false);
      jPanel3.add(txtTotDif);
      txtTotDif.setBounds(160, 62, 120, 20);

      PanFrm.add(jPanel3, java.awt.BorderLayout.NORTH);

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

      butAce.setText("Aceptar");
      butAce.setPreferredSize(new java.awt.Dimension(92, 25));
      butAce.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butAceActionPerformed(evt);
         }
      });
      jPanel1.add(butAce);

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
      setBounds((screenSize.width-759)/2, (screenSize.height-450)/2, 759, 450);
   }// </editor-fold>//GEN-END:initComponents

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        // TODO add your handling code here:
        if(chrOpcTooBar=='n'){
            if(isCuadradoValorAplicado()){
                if(cambiarValorDisponible()){
                    intButSelDlg=INT_BUT_ACE;
                    dispose();
                }
            }
            else{
                mostrarMsgInf("<HTML>El valor aplicado es diferente al que se debe aplicar.<BR>Cuadre la suma de las facturas con el valor a aplicar,<BR>caso contrario cancele la operación.</HTML>");
            }
        }
        else{
            dispose();
            intButSelDlg=INT_BUT_CAN;
        }

    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        exitForm(null);
    }//GEN-LAST:event_butCanActionPerformed

    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        // TODO add your handling code here:
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexi�n si est� abierta.
                intButSelDlg=INT_BUT_CAN;
                dispose();
            }
        }
        catch (Exception e)
        {
            intButSelDlg=INT_BUT_CAN;
            dispose();
        }
    }//GEN-LAST:event_exitForm
   
    
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JPanel PanFrm;
   private javax.swing.JButton butAce;
   private javax.swing.JButton butCan;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JPanel jPanel2;
   private javax.swing.JPanel jPanel3;
   private javax.swing.JLabel lblTit;
   private javax.swing.JPanel panBar;
   private javax.swing.JPanel panBot;
   private javax.swing.JPanel panGen;
   private javax.swing.JPanel panGenDet;
   private javax.swing.JScrollPane spnDat;
   private javax.swing.JTable tblDat;
   private javax.swing.JTextField txtTotDif;
   private javax.swing.JTextField txtValAplTot;
   private javax.swing.JTextField txtValSelTot;
   // End of variables declaration//GEN-END:variables
       
    
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(27);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_DIA_CRE,"Día.Cré.");
            vecCab.add(INT_TBL_DAT_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_DAT_POR_RET,"Por.Ret.");
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Doc.");
            vecCab.add(INT_TBL_DAT_VAL_PND,"Val.Pen.");
            vecCab.add(INT_TBL_DAT_VAL_DIS,"Val.Dis.");
            //vecCab.add(INT_TBL_DAT_VAL_APL,"Val.Apl.");
            vecCab.add(INT_TBL_DAT_VAL_APL_USR,"Val.Apl.Usr.");
            vecCab.add(INT_TBL_DAT_VAL_APL_USR_AUX,"Val.Apl.Usr.Aux.");
            vecCab.add(INT_TBL_DAT_VAL_APL_USR_AUX_MOD_TBL,"Val.Apl.Usr.Aux.Mod.Tbl.");
            vecCab.add(INT_TBL_DAT_COD_EMP_REL,"Cód.Emp.Rel.");
            vecCab.add(INT_TBL_DAT_COD_LOC_REL,"Cód.Loc.Rel.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC_REL,"Cód.Tip.Doc.Rel.");
            vecCab.add(INT_TBL_DAT_COD_DOC_REL,"Cód.Doc.Rel.");
            vecCab.add(INT_TBL_DAT_COD_REG_REL,"Cód.Reg.Rel.");
            
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_INT_SIG,"Int.Sig.");






            objUti=new ZafUtil();
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selecci�n.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el men� de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(67);
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FEC_VEN).setPreferredWidth(67);
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PND).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DIS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_APL_USR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_APL_USR_AUX).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_APL_USR_AUX_MOD_TBL).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_REL).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_REL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_REL).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_REL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_REL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_INT_SIG).setPreferredWidth(30);

            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_APL_USR, objTblMod.INT_COL_DBL, new Integer(0), null);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            vecAux.add("" + INT_TBL_DAT_VAL_APL_USR);

            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
                        
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bdeTxtTotApl=new BigDecimal("0");
                BigDecimal bdeTxtTotSel=new BigDecimal("0");
                BigDecimal bdeTxtTotDif=new BigDecimal("0");
                BigDecimal bdeValSelUsr=new BigDecimal("0");//el valor que el usuario va seleccionando
                BigDecimal bdeValDisReg=new BigDecimal("0");
                int intFil=-1;

                String strCodEmpArl="";           String strCodLocArl="";          String strCodTipDocArl="";
                String strCodDocArl="";           String strCodRegArl="";          BigDecimal bdeValDis=new BigDecimal("0");

                BigDecimal bdeValSelUsrNotChk=new BigDecimal("0");

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    bdeTxtTotApl=new BigDecimal(txtValAplTot.getText());
                    bdeTxtTotSel=new BigDecimal(txtValSelTot.getText());
                    bdeTxtTotDif=bdeTxtTotApl.subtract(bdeTxtTotSel);

                    bdeValDisReg=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_VAL_DIS)==null?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_VAL_DIS).toString());

                    if(bdeValDisReg.compareTo(new BigDecimal("0"))==0){
                        objTblCelEdiChk.setCancelarEdicion(true);
                    }
                    else{
                        objTblCelEdiChk.setCancelarEdicion(false);
                        if( ! objTblMod.isChecked(intFil, INT_TBL_DAT_CHK)){//es porq se va a adicionar el valor a lo q ya esta acumulado seleccionado
                            if(bdeTxtTotSel.compareTo(bdeTxtTotApl)<0){
                                objTblCelEdiChk.setCancelarEdicion(false);
                            }
                            else{
                                objTblCelEdiChk.setCancelarEdicion(true);
                                mostrarMsgInf("<HTML>El valor requerido está completo.<BR>Si desea agregar otro valor, quite la selección de alguna factura ya seleccionada</HTML>");
                                objTblMod.setChecked(false, intFil, INT_TBL_DAT_CHK);
                            }
                        }
                        else{//esta chequeado
                            bdeValSelUsrNotChk=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_VAL_APL_USR)==null?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_VAL_APL_USR).toString());
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bdeValSelUsr=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_VAL_DIS)==null?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_VAL_DIS).toString());
                    if(objTblMod.isChecked(intFil, INT_TBL_DAT_CHK)){
                        if(bdeValSelUsr.compareTo(bdeTxtTotDif)<=0){//si es menor o igual al valor se aplica el valor seleccionado
                            if(bdeValSelUsr.compareTo(bdeValDisReg)<=0){
                                objTblMod.setValueAt(bdeValSelUsr, intFil, INT_TBL_DAT_VAL_APL_USR);
                                calculaValorSeleccionado();//calcula el valor total seleccionado y la diferencia
                            }
                            else{
                                objTblMod.setValueAt(bdeValDisReg, tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL_USR);
                                calculaValorSeleccionado();
                            }

                        }
                        else{//se aplica solo lo faltante para completar el valor requerido
                            if(bdeTxtTotDif.compareTo(bdeValDisReg)<=0){
                                objTblMod.setValueAt(bdeTxtTotDif, intFil, INT_TBL_DAT_VAL_APL_USR);
                                calculaValorSeleccionado();//calcula el valor total seleccionado y la diferencia
                            }
                            else{
                                objTblMod.setValueAt(bdeValDisReg, intFil, INT_TBL_DAT_VAL_APL_USR);
                                calculaValorSeleccionado();//calcula el valor total seleccionado y la diferencia
                            }


                        }
                    }
                    else{
                        objTblMod.setValueAt("0", intFil, INT_TBL_DAT_VAL_APL_USR);
                        calculaValorSeleccionado();//calcula el valor total seleccionado y la diferencia

                    }
                }

            });

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PND).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DIS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_APL_USR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_APL_USR_AUX).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_APL_USR_AUX_MOD_TBL).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //para el campo de texto de observacion
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_VAL_APL_USR).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bdeValAplNew=new BigDecimal("0");                BigDecimal bdeValAplOld=new BigDecimal("0");
                BigDecimal bdeSumValApl=new BigDecimal("0");                BigDecimal bdeSumValSel=new BigDecimal("0");
                BigDecimal bdeSumValDif=new BigDecimal("0");                BigDecimal bdeValDisReg=new BigDecimal("0");

                String strCodEmpArl="";           String strCodLocArl="";          String strCodTipDocArl="";
                String strCodDocArl="";           String strCodRegArl="";          BigDecimal bdeValDis=new BigDecimal("0");

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bdeValAplOld=new BigDecimal(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL_USR)==null?"0":(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL_USR).toString().equals("")?"0":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL_USR).toString()));
                    bdeSumValApl=new BigDecimal(txtValAplTot.getText());
                    bdeSumValSel=new BigDecimal(txtValSelTot.getText());
                    bdeSumValDif=(bdeSumValApl.subtract(bdeSumValSel).add(bdeValAplOld));

                    bdeValDisReg=new BigDecimal(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_DIS)==null?"0":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_DIS).toString());

                    if(bdeSumValDif.compareTo(new BigDecimal("0"))==0){
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }
                    else
                        objTblCelEdiTxt.setCancelarEdicion(false);


                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bdeValAplNew=new BigDecimal(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL_USR)==null?"0":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL_USR).toString());

                    if(bdeValAplNew.compareTo(new BigDecimal("0"))>0){
                        if(bdeValAplNew.compareTo(bdeSumValDif)<=0){//si es menor o igual al valor se aplica el valor seleccionado
//                            if(bdeValDisReg.compareTo(bdeValAplNew)>=0){
                                objTblMod.setValueAt(bdeValAplNew, tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL_USR);
                                objTblMod.setChecked(true, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                                calculaValorSeleccionado();//calcula el valor total seleccionado y la diferencia
//                            }
//                            else{
//                                objTblMod.setValueAt(bdeValAplNew, tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL_USR);
//                                objTblMod.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
//                            }
                        }
                        else{//se aplica solo lo faltante para completar el valor requerido
                            if(bdeSumValDif.compareTo(new BigDecimal("0"))>0){
//                                if(bdeValDisReg.compareTo(bdeSumValDif)>=0){
                                    objTblMod.setValueAt(bdeSumValDif, tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL_USR);
                                    objTblMod.setChecked(true, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                                    calculaValorSeleccionado();//calcula el valor total seleccionado y la diferencia
//                                }
//                                else{
//                                    objTblMod.setValueAt(bdeSumValDif, tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL_USR);
//                                    objTblMod.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
//                                }
                            }
                            else{
                                if(bdeValAplOld.compareTo(new BigDecimal("0"))==0){

                                }
                                else{
                                    objTblMod.setChecked(true, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                                    objTblMod.setValueAt(bdeValAplOld, tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL_USR);
                                    calculaValorSeleccionado();//calcula el valor total seleccionado y la diferencia
                                }


                            }
                        }
                    }
                    else{
                        mostrarMsgInf("<HTML>El valor ingresado es cero.<BR>No se puede ingresar valor cero.<BR>Vuelva a intentarlo ingresando un valor diferente a cero</HTML>");
                        if(bdeValAplOld.compareTo(new BigDecimal("0"))==0){
                        }
                        else{
                            objTblMod.setChecked(true, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                            objTblMod.setValueAt(bdeValAplOld, tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL_USR);
                            calculaValorSeleccionado();//calcula el valor total seleccionado y la diferencia

                        }
                    }
                }
            });



            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_VAL_APL_USR_AUX, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_VAL_APL_USR_AUX_MOD_TBL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG_REL, tblDat);

            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CLI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_INT_SIG, tblDat);

            




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
     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }


    private boolean calculaValorSeleccionado(){
        boolean blnRes=false;
        BigDecimal bdeValApl=new BigDecimal("0");
        BigDecimal bdeValSel=new BigDecimal("0");
        BigDecimal bdeValAcu=new BigDecimal("0");
        try{
            bdeValApl=new BigDecimal(txtValAplTot.getText());//valor maximo que se debe completar
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    bdeValSel=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_USR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_USR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_USR).toString()));
                    bdeValAcu=bdeValAcu.add(bdeValSel);
                }
                    
            }
            txtValSelTot.setText("" + bdeValAcu);
            txtTotDif.setText("" + (bdeValApl.subtract(bdeValAcu)));
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;

    }


    
    public boolean getFacturasAplicables(char opcion, int codEmpRel, int codLocRel, int codTipDocRel, int codDocRel, int codRegRel, int codigoCliente, BigDecimal valorAplicar, String codigoTipoDocumentoCabecera, String codigoDocumentoCabecera){
        boolean blnRes=true;
        int intCodUsr;
        vecDat.clear();
        chrOpcTooBar=opcion;
        String strChkSel="";

        try{
            txtValAplTot.setText("" + valorAplicar);
            txtValSelTot.setText("" + new BigDecimal("0"));
            txtTotDif.setText("" + new BigDecimal("0"));

            intCodUsr=objParSis.getCodigoUsuario();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                System.out.println("chrOpcTooBar: " + chrOpcTooBar);
                switch(chrOpcTooBar){
                    case 'n':
                        strSQL="";
                        strSQL+=" SELECT a1.co_emp AS co_empRel, a1.co_loc AS co_locRel, a1.co_tipDoc AS co_tipDocRel, a1.co_doc AS co_docRel, a2.co_reg AS co_regRel, a1.ne_numDoc, a1.fe_doc";

                        strSQL+=" ,a1.co_emp , a1.co_loc , a1.co_tipDoc, a1.co_doc, a2.co_reg";

                        strSQL+=" , a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.mo_pag, (a2.mo_pag + a2.nd_abo) AS nd_valPnd";
                        strSQL+=" , a3.tx_desCor, a3.tx_desLar, a3.tx_natdoc, CAST(0 AS NUMERIC(18,6)) AS nd_abo";
                        strSQL+=" FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2";
                        strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        if(objUti.utilizarClientesEmpresa(objParSis, codEmpRel, codLocRel, intCodUsr)){
                            strSQL+=" INNER JOIN tbm_cli AS a4";
                            strSQL+=" ON a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli";
                            strSQL+=" WHERE a1.co_emp=" + codEmpRel + "";
                        }
                        else{
                            strSQL+=" INNER JOIN tbm_cli AS a4";
                            strSQL+=" ON a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli";
                            strSQL+=" INNER JOIN tbr_cliLoc AS a5";
                            strSQL+=" ON a4.co_emp=a5.co_emp AND a4.co_cli=a5.co_cli";
                            strSQL+=" WHERE a1.co_emp=" + codEmpRel + "";
                            strSQL+=" AND a5.co_loc=" + codLocRel + "";
                        }
                        strSQL+=" AND a1.st_reg NOT IN('I','E') AND a2.st_reg IN('A','C')";
                        strSQL+=" AND (a2.mo_pag + a2.nd_abo) < 0  /*AND a2.nd_porRet=0*/";
                        strSQL+=" AND a1.co_cli=" + codigoCliente + "";
                        break;
                    default:
                        strSQL="";
                        strSQL+="SELECT b2.nd_porRet, a6.co_emp AS co_empRel, a6.co_locPag AS co_locRel, a6.co_tipDocPag AS co_tipDocRel, ";
                        strSQL+=" a6.co_docPag AS co_docRel, a6.co_regPag AS co_regRel,";
                        strSQL+=" a6.co_emp, a6.co_locreldepcliregdirban AS co_loc, a6.co_tipdocreldepcliregdirban AS co_tipDoc";
                        strSQL+=" , a6.co_docreldepcliregdirban AS co_doc, a6.co_regreldepcliregdirban AS co_reg,";
                        strSQL+=" a3.tx_desCor, a3.tx_desLar, a3.tx_natdoc";
                        strSQL+=" , b3.ne_numDoc, b3.fe_doc, a1.co_cta, a4.tx_codCta, a4.tx_desLar AS tx_nomCta";
                        strSQL+=" , a2.co_cli, a5.tx_nom AS tx_nomCli, a6.nd_abo, a2.tx_obs1";
                        strSQL+="  , b2.ne_diaCre, b2.fe_ven, b2.mo_pag, (b2.mo_pag + b2.nd_abo) AS nd_valPnd";
                        strSQL+=" FROM  tbm_cabPag AS a1 INNER JOIN (tbm_detPag AS a6";
                        strSQL+=" 		 INNER JOIN tbm_cabTipDoc AS a3";
                        strSQL+=" 		 ON a6.co_emp=a3.co_emp AND a6.co_locPag=a3.co_loc AND a6.co_tipDocPag=a3.co_tipDoc";
                        strSQL+=" 		 INNER JOIN tbm_pagMovInv AS b2";
                        strSQL+=" 		 ON a6.co_emp=b2.co_emp AND a6.co_locPag=b2.co_loc AND a6.co_tipDocPag=b2.co_tipDoc";
                        strSQL+=" 		 AND a6.co_docPag=b2.co_doc AND a6.co_regPag=b2.co_reg";
                        strSQL+=" 		 INNER JOIN tbm_cabMovInv AS b3 ON b2.co_emp=b3.co_emp AND b2.co_loc=b3.co_loc AND b2.co_tipDoc=b3.co_tipDoc";
                        strSQL+=" 		 AND b2.co_doc=b3.co_doc";
                        strSQL+=" 	)";
                        strSQL+=" ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                        strSQL+=" INNER JOIN (   tbm_depCliRegDirBanPag AS a2";
                        strSQL+=" 			 INNER JOIN (tbm_cabPag AS b1 INNER JOIN tbm_plaCta AS a4 ON b1.co_emp=a4.co_emp AND b1.co_cta=a4.co_cta)";
                        strSQL+=" 			 ON a2.co_emp=b1.co_emp AND a2.co_loc=b1.co_loc AND a2.co_tipDoc=b1.co_tipDoc";
                        strSQL+=" 			 AND a2.co_doc=b1.co_doc)";
                        strSQL+=" ON a6.co_emp=a2.co_emp AND a6.co_locreldepcliregdirban=a2.co_loc AND a6.co_tipdocreldepcliregdirban=a2.co_tipDoc";
                        strSQL+=" AND a6.co_docreldepcliregdirban=a2.co_doc AND a6.co_regreldepcliregdirban=a2.co_reg";
                        strSQL+=" INNER JOIN tbm_cli AS a5";
                        strSQL+=" ON a2.co_emp=a5.co_emp AND a2.co_cli=a5.co_cli";
                        strSQL+=" WHERE a6.co_emp=" + codEmpRel + "";
                        strSQL+=" AND a6.co_locreldepcliregdirban=" + codLocRel + "";
                        strSQL+=" AND a6.co_tipdocreldepcliregdirban=" + codTipDocRel + "";
                        strSQL+=" AND a6.co_docreldepcliregdirban=" + codDocRel + "";
                        strSQL+=" AND a6.co_regreldepcliregdirban=" + codRegRel + "";

                        strSQL+=" AND a1.co_tipdoc=" + codigoTipoDocumentoCabecera + "";
                        strSQL+=" AND a1.co_doc=" + codigoDocumentoCabecera + "";


                        strSQL+=" AND a2.co_cli IS NOT NULL AND a2.st_reg='A' AND a1.st_reg NOT IN('E') AND a6.st_reg NOT IN('E')";
                        strSQL+="  GROUP BY b2.nd_porRet,a6.co_emp, a6.co_locPag, a6.co_tipDocPag, a6.co_docPag, a6.co_regPag,";
                        strSQL+=" a6.co_emp, a6.co_locreldepcliregdirban, a6.co_tipdocreldepcliregdirban";
                        strSQL+=" , a6.co_docreldepcliregdirban, a6.co_regreldepcliregdirban,";
                        strSQL+="  a3.tx_desCor, a3.tx_desLar, a3.tx_natdoc";
                        strSQL+=" , b3.ne_numDoc, b3.fe_doc, a1.co_cta, a4.tx_codCta, a4.tx_desLar";
                        strSQL+=" , a2.co_cli, a5.tx_nom, a6.nd_abo, a2.tx_obs1";
                        strSQL+=" , b2.ne_diaCre, b2.fe_ven, b2.mo_pag, b2.nd_abo";
                        strSQL+=" ORDER BY b3.ne_numDoc, a6.co_regPag";
                        break;
                }

                System.out.println("getFacturasAplicables: " + strSQL);
                rst=stm.executeQuery(strSQL);

                String strCodEmpRst="";
                String strCodLocRst="";
                String strCodTipDocRst="";
                String strCodDocRst="";
                String strCodRegRst="";

                String strCodEmpArl="";
                String strCodLocArl="";
                String strCodTipDocArl="";
                String strCodDocArl="";
                String strCodRegArl="";
                BigDecimal bdeValDis=new BigDecimal("0");

                //se las puso por separado porq al ejecutar el .jar en la maquina de Ed, salia error en las lineas de rst.getBigDecimal(.....
                BigDecimal bdeMonPagBde=new BigDecimal("0");
                BigDecimal bdeValPndBde=new BigDecimal("0");
                BigDecimal bdeValAboBde=new BigDecimal("0");
                BigDecimal bdeAux=new BigDecimal("0");

                while(rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,             "");
                    vecReg.add(INT_TBL_DAT_CHK,             null);

                    strCodEmpRst=rst.getObject("co_emp")==null?"":rst.getString("co_emp");
                    strCodLocRst=rst.getObject("co_loc")==null?"":rst.getString("co_loc");
                    strCodTipDocRst=rst.getObject("co_tipDoc")==null?"":rst.getString("co_tipDoc");
                    strCodDocRst=rst.getObject("co_doc")==null?"":rst.getString("co_doc");
                    strCodRegRst=rst.getObject("co_reg")==null?"":rst.getString("co_reg");

                    vecReg.add(INT_TBL_DAT_COD_EMP,         "" + rst.getObject("co_empRel")==null?"":rst.getString("co_empRel"));
                    vecReg.add(INT_TBL_DAT_COD_LOC,         "" + rst.getObject("co_locRel")==null?"":rst.getString("co_locRel"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     "" + rst.getObject("co_tipDocRel")==null?"":rst.getString("co_tipDocRel"));
                    vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, "" + rst.getObject("tx_desCor")==null?"":rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, "" + rst.getObject("tx_desLar")==null?"":rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_COD_DOC,         "" + rst.getObject("co_docRel")==null?"":rst.getString("co_docRel"));
                    vecReg.add(INT_TBL_DAT_COD_REG,         "" + rst.getObject("co_regRel")==null?"":rst.getString("co_regRel"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC,         "" + rst.getObject("ne_numDoc")==null?"":rst.getString("ne_numDoc"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC,         "" + rst.getObject("fe_doc")==null?"":rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_DAT_DIA_CRE,         "" + rst.getObject("ne_diaCre")==null?"":rst.getString("ne_diaCre"));
                    vecReg.add(INT_TBL_DAT_FEC_VEN,         "" + rst.getObject("fe_ven")==null?"":rst.getString("fe_ven"));
                    
                    bdeAux = new BigDecimal(rst.getObject("nd_porRet")==null?"0":(rst.getString("nd_porRet")));
                    bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());
                    vecReg.add(INT_TBL_DAT_POR_RET,         "" + bdeAux);

                    bdeMonPagBde=new BigDecimal(rst.getObject("mo_pag")==null?"0":(rst.getString("mo_pag")));
                    bdeMonPagBde=bdeMonPagBde.abs();
                    vecReg.add(INT_TBL_DAT_VAL_DOC,         "" + bdeMonPagBde);

                    bdeValPndBde=new BigDecimal(rst.getObject("nd_valPnd")==null?"0":(rst.getString("nd_valPnd")));
                    bdeValPndBde=bdeValPndBde.abs();

                    bdeValAboBde=new BigDecimal(rst.getObject("nd_abo")==null?"0":(rst.getString("nd_abo")));
                    bdeValAboBde=bdeValAboBde.abs();

                    vecReg.add(INT_TBL_DAT_VAL_PND,         "" + bdeValPndBde);
                    vecReg.add(INT_TBL_DAT_VAL_DIS,         "" + bdeValPndBde);
                    vecReg.add(INT_TBL_DAT_VAL_APL_USR,     "" + bdeValAboBde);
                    vecReg.add(INT_TBL_DAT_VAL_APL_USR_AUX, "0");
                    vecReg.add(INT_TBL_DAT_VAL_APL_USR_AUX_MOD_TBL, "");
                    vecReg.add(INT_TBL_DAT_COD_EMP_REL,     "" + codEmpRel);
                    vecReg.add(INT_TBL_DAT_COD_LOC_REL,     "" + codLocRel);
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC_REL, "" + codTipDocRel);
                    vecReg.add(INT_TBL_DAT_COD_DOC_REL,     "" + codDocRel);
                    vecReg.add(INT_TBL_DAT_COD_REG_REL,     "" + codRegRel);
                    vecReg.add(INT_TBL_DAT_COD_CLI,         "" + codigoCliente);
                    intSig=(rst.getString("tx_natDoc").equals("I")?-1:1);
                    vecReg.add(INT_TBL_DAT_INT_SIG,         "" + intSig);
                    bdeValAplUsrMod=new BigDecimal("0");
                    strCodEmpArl="";
                    strCodLocArl="";
                    strCodTipDocArl="";
                    strCodDocArl="";
                    strCodRegArl="";
                    bdeValDis=new BigDecimal("0");

                    for(int k=0; k<arlDatIni.size(); k++){
                        strCodEmpArl=objUti.getStringValueAt(arlDatIni, k, INT_TBL_ARL_INI_COD_EMP_FAC);
                        strCodLocArl=objUti.getStringValueAt(arlDatIni, k, INT_TBL_ARL_INI_COD_LOC_FAC);
                        strCodTipDocArl=objUti.getStringValueAt(arlDatIni, k, INT_TBL_ARL_INI_COD_TIP_DOC_FAC);
                        strCodDocArl=objUti.getStringValueAt(arlDatIni, k, INT_TBL_ARL_INI_COD_DOC_FAC);
                        strCodRegArl=objUti.getStringValueAt(arlDatIni, k, INT_TBL_ARL_INI_COD_REG_FAC);
                        bdeValDis=objUti.getBigDecimalValueAt(arlDatIni, k, INT_TBL_ARL_INI_VAL_DIS);

                        if(   (strCodEmpArl.equals(strCodEmpRst)) && (strCodLocArl.equals(strCodLocRst))  && (strCodTipDocArl.equals(strCodTipDocRst))  && (strCodDocArl.equals(strCodDocRst)) && (strCodRegArl.equals(strCodRegRst))  ){
                            vecReg.setElementAt(bdeValDis, INT_TBL_DAT_VAL_DIS);
                        }
                    }

                    if(chrOpcTooBar=='n'){
                    }
                    else{
                        vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK);
                    }

                    vecDat.add(vecReg);
                }

                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
            objTblMod.removeAllRows();

            //Asignar vectores al modelo.
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
            objTblMod.initRowsState();

            calculaValorSeleccionado();


            String strArlCodEmp="", strTblCodEmp="";
            String strArlCodLoc="", strTblCodLoc="";
            String strArlCodTipDoc="", strTblCodTipDoc="";
            String strArlCodDoc="", strTblCodDoc="";
            String strArlCodReg="", strTblCodReg="";
            String strArlValApl="";

            String strArlCodEmpCob="", strTblCodEmpCob="";
            String strArlCodLocCob="", strTblCodLocCob="";
            String strArlCodTipDocCob="", strTblCodTipDocCob="";
            String strArlCodDocCob="", strTblCodDocCob="";
            String strArlCodRegCob="", strTblCodRegCob="";

            String strEstRegArl="";

            for(int k=0; k<arlDat.size(); k++){
                strArlCodEmp=objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_EMP_FAC);
                strArlCodLoc=objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_LOC_FAC);
                strArlCodTipDoc=objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_TIP_DOC_FAC);
                strArlCodDoc=objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_DOC_FAC);
                strArlCodReg=objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_REG_FAC);
                strArlValApl=objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_VAL_APL_FAC);
                strArlCodEmpCob=objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_EMP_COB);
                strArlCodLocCob=objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_LOC_COB);
                strArlCodTipDocCob=objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_TIP_DOC_COB);
                strArlCodDocCob=objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_DOC_COB);
                strArlCodRegCob=objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_REG_COB);

                strEstRegArl=objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_EST_ELI);
                if( ! strEstRegArl.equals("E")){
                    for(int j=0; j<objTblMod.getRowCountTrue(); j++){
                        strTblCodEmp=objTblMod.getValueAt(j, INT_TBL_DAT_COD_EMP).toString();
                        strTblCodLoc=objTblMod.getValueAt(j, INT_TBL_DAT_COD_LOC).toString();
                        strTblCodTipDoc=objTblMod.getValueAt(j, INT_TBL_DAT_COD_TIP_DOC).toString();
                        strTblCodDoc=objTblMod.getValueAt(j, INT_TBL_DAT_COD_DOC).toString();
                        strTblCodReg=objTblMod.getValueAt(j, INT_TBL_DAT_COD_REG).toString();

                        strTblCodEmpCob=objTblMod.getValueAt(j, INT_TBL_DAT_COD_EMP_REL).toString();
                        strTblCodLocCob=objTblMod.getValueAt(j, INT_TBL_DAT_COD_LOC_REL).toString();
                        strTblCodTipDocCob=objTblMod.getValueAt(j, INT_TBL_DAT_COD_TIP_DOC_REL).toString();
                        strTblCodDocCob=objTblMod.getValueAt(j, INT_TBL_DAT_COD_DOC_REL).toString();
                        strTblCodRegCob=objTblMod.getValueAt(j, INT_TBL_DAT_COD_REG_REL).toString();

                        if(  (strArlCodEmp.equals(strTblCodEmp)) && (strArlCodLoc.equals(strTblCodLoc)) &&(strArlCodTipDoc.equals(strTblCodTipDoc)) && (strArlCodDoc.equals(strTblCodDoc)) && (strArlCodReg.equals(strTblCodReg))            &&            (strArlCodEmpCob.equals(strTblCodEmpCob)) && (strArlCodLocCob.equals(strTblCodLocCob)) &&(strArlCodTipDocCob.equals(strTblCodTipDocCob)) && (strArlCodDocCob.equals(strTblCodDocCob)) && (strArlCodRegCob.equals(strTblCodRegCob))          ){
                            objTblMod.setChecked(true, j, INT_TBL_DAT_CHK);
                            objTblMod.setValueAt(strArlValApl, j, INT_TBL_DAT_VAL_APL_USR);
                            objTblMod.setValueAt(strArlValApl, j, INT_TBL_DAT_VAL_APL_USR_AUX);
                            break;
                        }
                    }
                }
            }


            vecDat.clear();
            //objTblMod.initRowsState();
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



    public boolean isEditable(String editable){
        boolean blnRes=true;
        try{
            strPanDiaLogEdi=editable;
            if(strPanDiaLogEdi.equals("N")){
                objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                butCan.setVisible(false);
            }
            else if(strPanDiaLogEdi.equals("S")){
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                butCan.setVisible(true);
            }
            else{
                objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                butCan.setVisible(false);
            }
        }
        catch(Exception e){
            blnRes=false;
        }
        return blnRes;
    }

    private boolean isCuadradoValorAplicado(){
        boolean blnRes=false;
        BigDecimal bdeValSel=new BigDecimal("0");
        BigDecimal bdeValAcu=new BigDecimal("0");
        BigDecimal bdeValTotAplUsr=new BigDecimal("0");
        try{
            bdeValApl=new BigDecimal(txtValAplTot.getText());
            bdeValTotAplUsr=new BigDecimal(txtValSelTot.getText());
            System.out.println("bdeValTotAplUsr: " + bdeValTotAplUsr);


            if(bdeValTotAplUsr.compareTo(new BigDecimal("0"))==0){
                blnRes=true;
            }
            else{
                if(objTblMod.getRowCountTrue()>0){
                    for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                        if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                            bdeValSel=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_USR)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_USR).toString());
                            bdeValAcu=bdeValAcu.add(bdeValSel);
                        }
                    }
                    if(bdeValAcu.compareTo(bdeValApl)==0){
                        blnRes=true;
                    }
                    else{
                        blnRes=false;
                    }
                }
                else
                    blnRes=true;
            }

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;

    }


    /**
     * Esta función obtiene las filas que han sido seleccionadas por el usuario en el JTable.
     * @return Un arreglo de enteros que contiene la(s) fila(s) seleccionadas.
     */
    public int[] getFilasSeleccionadas()
    {
        int i=0, j=0;
        int intRes[];
        for (i=0; i<objTblMod.getRowCountTrue(); i++)
        {
            if (objTblMod.isChecked(i, INT_TBL_DAT_CHK))
            {
                j++;
            }
        }
        intRes=new int[j];
        j=0;
        for (i=0; i<objTblMod.getRowCountTrue(); i++)
        {
            if (objTblMod.isChecked(i, INT_TBL_DAT_CHK))
            {
                intRes[j]=i;
                j++;
            }
        }
        return intRes;
    }

    

    /**
     * Esta función establece la fila especificada como una fila procesada. Lo que hace internamente la función
     * es almacenar una letra "P" en la primera columna del JTable. A través de éste valor es posible determinar
     * si la fila ya fue procesada anteriormente o si todavía no ha sido procesada.
     * @param fila La fila que se marcará como procesada.
     */
    public void setFilaProcesada(int fila)
    {
        objTblMod.setValueAt("P", fila, INT_TBL_DAT_LIN);
    }



    /**
     * Esta función obtiene el valor que se encuentra en la posición especificada.
     * @param row La fila de la que se desea obtener el valor.
     * @param col La columna de la que se desea obtener el valor.
     * @return El valor que se encuentra en la posición especificada.
     */
    public String getValueAt(int row, int col)
    {
        if (row!=-1)
            return objUti.parseString(objTblMod.getValueAt(row, col));
        else
            return "";
    }


    /**
     * Esta función obtiene la opción que seleccionó el usuario en el JDialog.
     * Puede devolver uno de los siguientes valores:
     * <UL>
     * <LI>0: Click en el botón Cancelar (INT_BUT_CAN).
     * <LI>1: Click en el botón Aceptar (INT_BUT_ACE).
     * </UL>
     * <BR>Nota.- La opción predeterminada es el botón Cancelar.
     * @return La opción seleccionada por el usuario.
     */
    public int getSelectedButton()
    {
        return intButSelDlg;
    }

    public ArrayList getArregloDatosFacturasAplicadas() {
        return arlDat;
    }

    public void setArregloDatosFacturasAplicadas(ArrayList arlDat) {
        this.arlDat = arlDat;
    }



    public boolean cargarDatosIniciales(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if(cargarClientesIniciales()){
                    if(cargarValorDisponibleInicial()){

                    }
                }

                con.close();
                con=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean cargarClientesIniciales(){
        boolean blnRes=true;
        strCodCliValDis="0";
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc,a2.co_cli";
                strSQL+=" FROM ( (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" 	INNER JOIN tbm_plaCta AS a4 ON a1.co_emp=a4.co_emp AND a1.co_cta=a4.co_cta)";
                strSQL+=" INNER JOIN tbm_depCliRegDirBanPag AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                    strSQL+=" INNER JOIN tbm_cli AS a5";
                    strSQL+=" ON a2.co_emp=a5.co_emp AND a2.co_cli=a5.co_cli";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                }
                else{
                    strSQL+=" INNER JOIN tbm_cli AS a5";
                    strSQL+=" ON a2.co_emp=a5.co_emp AND a2.co_cli=a5.co_cli";
                    strSQL+=" INNER JOIN tbr_cliLoc AS a6";
                    strSQL+=" ON a5.co_emp=a6.co_emp AND a5.co_cli=a6.co_cli";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a6.co_loc=" + objParSis.getCodigoLocal() + "";
                }
                strSQL+=" AND a2.co_cli IS NOT NULL AND a2.st_valApl='N' AND a2.st_reg='A'";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a2.co_cli";
                rst=stm.executeQuery(strSQL);
                for(int i=0; rst.next(); i++){
                    if(i==0)
                        strCodCliValDis="" + rst.getString("co_cli");
                    else
                        strCodCliValDis+="," + rst.getString("co_cli");

                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



    private boolean cargarValorDisponibleInicial(){
        boolean blnRes=true;
        arlDatIni.clear();
        BigDecimal bdeValPndBde=new BigDecimal("0");
        try{            
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc";
                strSQL+=" , a2.ne_diaCre, a2.fe_ven, a2.mo_pag, (a2.mo_pag + a2.nd_abo) AS nd_valPnd";
                strSQL+=" , a3.tx_desCor, a3.tx_desLar, a3.tx_natdoc";
                strSQL+=" FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoLocal())){
                    strSQL+=" INNER JOIN tbm_cli AS a4";
                    strSQL+=" ON a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                }
                else{
                    strSQL+=" INNER JOIN tbm_cli AS a4";
                    strSQL+=" ON a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli";
                    strSQL+=" INNER JOIN tbr_cliLoc AS a5";
                    strSQL+=" ON a4.co_emp=a5.co_emp AND a4.co_cli=a5.co_cli";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a5.co_loc=" + objParSis.getCodigoLocal() + "";
                }
                strSQL+=" AND a1.st_reg NOT IN('I','E') AND a2.st_reg IN('A','C')";
                strSQL+=" AND (a2.mo_pag + a2.nd_abo) < 0  /*AND a2.nd_porRet=0*/";
                strSQL+=" AND a1.co_cli IN(" + strCodCliValDis + ")";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegIni=new ArrayList();
                    arlRegIni.add(INT_TBL_ARL_INI_COD_EMP_FAC,      "" + rst.getString("co_emp"));
                    arlRegIni.add(INT_TBL_ARL_INI_COD_LOC_FAC,      "" + rst.getString("co_loc"));
                    arlRegIni.add(INT_TBL_ARL_INI_COD_TIP_DOC_FAC,  "" + rst.getString("co_tipDoc"));
                    arlRegIni.add(INT_TBL_ARL_INI_COD_DOC_FAC,      "" + rst.getString("co_doc"));
                    arlRegIni.add(INT_TBL_ARL_INI_COD_REG_FAC,      "" + rst.getString("co_reg"));

                    bdeValPndBde=new BigDecimal(rst.getObject("nd_valPnd")==null?"0":rst.getString("nd_valPnd"));
                    bdeValPndBde=bdeValPndBde.abs();

                    arlRegIni.add(INT_TBL_ARL_INI_VAL_DIS,          "" + bdeValPndBde);
                    arlDatIni.add(arlRegIni);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }

        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean cambiarValorDisponible(){
        boolean blnRes=true;
        String strCodEmpArl="",  strCodLocArl="", strCodTipDocArl="",  strCodDocArl="", strCodRegArl="";
        BigDecimal bdeValDisTmpArl=new BigDecimal("0");
        BigDecimal bdeValSelTblTmp=new BigDecimal("0");
        BigDecimal bdeValSelTblTmpAux=new BigDecimal("0");
        //arreglos
        String strCodEmpArlTmp="";           String strCodLocArlTmp="";            String strCodTipDocArlTmp="";
        String strCodDocArlTmp="";           String strCodRegArlTmp="";            String strCodEmpCobArlTmp="";
        String strCodLocCobArlTmp="";        String strCodTipDocCobArlTmp="";      String strCodDocCobArlTmp="";
        String strCodRegCobArlTmp="";
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
//                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    String strCodEmpTbl="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP).toString();
                    String strCodLocTbl="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC).toString();
                    String strCodTipDocTbl="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString();
                    String strCodDocTbl="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC).toString();
                    String strCodRegTbl="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG).toString();
                    bdeValSelTblTmp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_USR)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_USR).toString());
                    bdeValSelTblTmpAux=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_USR_AUX)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_USR_AUX).toString());

                    String strCodEmpCobTblTmp="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP_REL).toString();
                    String strCodLocCobTblTmp="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC_REL).toString();
                    String strCodTipDocCobTblTmp="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC_REL).toString();
                    String strCodDocCobTblTmp="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC_REL).toString();
                    String strCodRegCobTblTmp="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG_REL).toString();

                    for(int k=0; k<arlDatIni.size(); k++){
                        strCodEmpArl=objUti.getStringValueAt(arlDatIni, k, INT_TBL_ARL_INI_COD_EMP_FAC);
                        strCodLocArl=objUti.getStringValueAt(arlDatIni, k, INT_TBL_ARL_INI_COD_LOC_FAC);
                        strCodTipDocArl=objUti.getStringValueAt(arlDatIni, k, INT_TBL_ARL_INI_COD_TIP_DOC_FAC);
                        strCodDocArl=objUti.getStringValueAt(arlDatIni, k, INT_TBL_ARL_INI_COD_DOC_FAC);
                        strCodRegArl=objUti.getStringValueAt(arlDatIni, k, INT_TBL_ARL_INI_COD_REG_FAC);
                        bdeValDisTmpArl=objUti.getBigDecimalValueAt(arlDatIni, k, INT_TBL_ARL_INI_VAL_DIS);

                        if(   (strCodEmpArl.equals(strCodEmpTbl)) && (strCodLocArl.equals(strCodLocTbl))  && (strCodTipDocArl.equals(strCodTipDocTbl))  && (strCodDocArl.equals(strCodDocTbl)) && (strCodRegArl.equals(strCodRegTbl))  ){
                            objUti.setStringValueAt(arlDatIni, k, INT_TBL_ARL_INI_VAL_DIS, "" + ((bdeValDisTmpArl.add(bdeValSelTblTmpAux)).subtract(bdeValSelTblTmp)));
                        }
                    }

                    //quitar ese dato del arreglo que se va a guardar en las tablas de la db
                    for(int k=0; k<arlDat.size();k++){
                        strCodEmpArlTmp="" + objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_EMP_FAC);
                        strCodLocArlTmp="" + objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_LOC_FAC);
                        strCodTipDocArlTmp="" + objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_TIP_DOC_FAC);
                        strCodDocArlTmp="" + objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_DOC_FAC);
                        strCodRegArlTmp="" + objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_REG_FAC);
                        strCodEmpCobArlTmp="" + objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_EMP_COB);
                        strCodLocCobArlTmp="" + objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_LOC_COB);
                        strCodTipDocCobArlTmp="" + objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_TIP_DOC_COB);
                        strCodDocCobArlTmp="" + objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_DOC_COB);
                        strCodRegCobArlTmp="" + objUti.getStringValueAt(arlDat, k, INT_TBL_ARL_COD_REG_COB);

                        if( (strCodEmpTbl.equals(strCodEmpArlTmp)) && (strCodEmpTbl.equals(strCodEmpArlTmp))  && (strCodLocTbl.equals(strCodLocArlTmp)) && (strCodTipDocTbl.equals(strCodTipDocArlTmp)) && (strCodDocTbl.equals(strCodDocArlTmp)) && (strCodRegTbl.equals(strCodRegArlTmp)) && (strCodEmpCobTblTmp.equals(strCodEmpCobArlTmp)) && (strCodLocCobTblTmp.equals(strCodLocCobArlTmp)) && (strCodTipDocCobTblTmp.equals(strCodTipDocCobArlTmp)) && (strCodDocCobTblTmp.equals(strCodDocCobArlTmp)) && (strCodRegCobTblTmp.equals(strCodRegCobArlTmp))   ){
                            objUti.setStringValueAt(arlDat, k, INT_TBL_ARL_EST_ELI, "E");
                        }
                    }
//                }

            }

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public boolean setEditable(String editable){
        boolean blnRes=false;
        try{
            strEditable=editable;
            if(strEditable.equals("S")){
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            }
            else{
                objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
}
/*
 * ZafCxP24_03.java
 *
 * Created on 04 de abril del 2018
 *
 */
package CxP.ZafCxP24;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafUtil.ZafUtil;
/**
 *
 * @author sistemas9
 */
public class ZafCxP24_03 extends javax.swing.JDialog 
{        
    //Constantes: Columnas del JTable
    static final int INT_TBL_DAT_LIN=0;
    static final int INT_TBL_DAT_COD_EMP=1;
    static final int INT_TBL_DAT_COD_LOC=2;
    static final int INT_TBL_DAT_COD_TIP_DOC=3;
    static final int INT_TBL_DAT_COD_DOC=4;
    static final int INT_TBL_DAT_COD_REG=5;
    static final int INT_TBL_DAT_COD_EMP_REL=6;
    static final int INT_TBL_DAT_COD_LOC_REL=7;
    static final int INT_TBL_DAT_COD_TIP_DOC_REL=8;
    static final int INT_TBL_DAT_DES_COR_TIP_DOC_REL=9;
    static final int INT_TBL_DAT_DES_LAR_TIP_DOC_REL=10;
    static final int INT_TBL_DAT_COD_DOC_REL=11;
    static final int INT_TBL_DAT_NUM_DOC=12;
    static final int INT_TBL_DAT_FEC_DOC=13; 
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafCxP24 objCon64;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private Vector vecReg, vecDat, vecCab;
    private String strSQL;

    public ZafCxP24_03(java.awt.Frame parent, boolean modal, ZafParSis obj, int codEmpresa, int codLocal, int codTipoDocumento, int codDocumento, int codRegistro, char estadoOperacion) {
        super(parent, modal);
        try{
            initComponents();
              //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objCon64= new ZafCxP24(objParSis);
            if( ! configurarFrm()){
                dispose();
            }
            if( ! cargarDocumentosAsociados(codEmpresa, codLocal, codTipoDocumento, codDocumento, codRegistro, estadoOperacion)){
                dispose();
            }
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(14);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_COD_EMP_REL,"Cód.Emp.Rel.");
            vecCab.add(INT_TBL_DAT_COD_LOC_REL,"Cód.Loc.Rel.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC_REL,"Cód.Tip.Doc.Rel.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC_REL,"Tip.Doc.Rel.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC_REL,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC_REL,"Cód.Doc.Rel.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");

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
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_REL).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_REL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_REL).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC_REL).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC_REL).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_REL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(67);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            //tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
           
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
                        
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

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
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panGen = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        panAce = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        PanFrm.setLayout(new java.awt.BorderLayout());

        panTit.setPreferredSize(new java.awt.Dimension(100, 25));
        panTit.setLayout(null);

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 12)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Listado de documentos generados");
        panTit.add(lblTit);
        lblTit.setBounds(0, 2, 690, 20);

        PanFrm.add(panTit, java.awt.BorderLayout.NORTH);

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
        panAce.add(butAce);

        panBot.add(panAce, java.awt.BorderLayout.EAST);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(700, 450));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        dispose();
    }//GEN-LAST:event_butAceActionPerformed

    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
        }
        catch (Exception e)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butAce;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panAce;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panTit;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podr�a utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean cargarDocumentosAsociados(int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento, int codigoRegistro, char estado){
        boolean blnRes=true;
        int intCodEmpRel=codigoEmpresa, intCodLocRel=codigoLocal, intCodTipDocRel=codigoTipoDocumento, intCodDocRel=codigoDocumento;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a5.tx_desCor, a5.tx_desLar, CAST(a4.ne_numDoc AS CHARACTER VARYING) AS ne_numDoc, a4.fe_doc";
                strSQL+="      , a3.co_empRelCabMovInv AS co_empRel, a3.co_locRelCabMovInv AS co_locRel, a3.co_tipDocRelCabMovInv AS co_tipDocRel, a3.co_docRelCabMovInv AS co_docRel";
                strSQL+=" FROM tbm_cabotrmovban AS a1 INNER JOIN tbm_detotrmovban AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_docgenotrmovban AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                strSQL+=" INNER JOIN tbm_cabMovInv AS a4";
                strSQL+=" ON a3.co_empRelCabMovInv=a4.co_emp AND a3.co_locRelCabMovInv=a4.co_loc AND a3.co_tipDocRelCabMovInv=a4.co_tipDoc AND a3.co_docRelCabMovInv=a4.co_doc";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a5 ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + intCodEmpRel + "";
                strSQL+=" AND a1.co_loc=" + intCodLocRel + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDocRel + "";
                strSQL+=" AND a1.co_doc=" + intCodDocRel + "";
                strSQL+=" AND a2.co_reg=" + codigoRegistro + "";
                if(estado=='n')
                    strSQL+=" AND a1.st_reg NOT IN('E','I') AND a4.st_reg NOT IN('E','I')";
                else
                    strSQL+=" AND a1.st_reg NOT IN('E') AND a4.st_reg NOT IN('E', 'I')";
                strSQL+=" AND a3.co_empRelCabMovInv IS NOT NULL";
                strSQL+=" UNION";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a5.tx_desCor, a5.tx_desLar, CAST(a4.ne_numDoc1 AS CHARACTER VARYING) AS ne_numDoc, a4.fe_doc";
                strSQL+="      , a3.co_empRelCabPag AS co_empRel, a3.co_locRelCabPag AS co_locRel, a3.co_tipDocRelCabPag AS co_tipDocRel, a3.co_docRelCabPag AS co_docRel";
                strSQL+=" FROM tbm_cabotrmovban AS a1 INNER JOIN tbm_detotrmovban AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_docgenotrmovban AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                strSQL+=" INNER JOIN tbm_cabPag AS a4";
                strSQL+=" ON a3.co_empRelCabPag=a4.co_emp AND a3.co_locRelCabPag=a4.co_loc AND a3.co_tipDocRelCabPag=a4.co_tipDoc AND a3.co_docRelCabPag=a4.co_doc";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a5 ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + intCodEmpRel + "";
                strSQL+=" AND a1.co_loc=" + intCodLocRel + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDocRel + "";
                strSQL+=" AND a1.co_doc=" + intCodDocRel + "";
                strSQL+=" AND a2.co_reg=" + codigoRegistro + "";
                if(estado=='n')
                    strSQL+=" AND a1.st_reg NOT IN('E','I') AND a4.st_reg NOT IN('E','I')";
                else
                    strSQL+=" AND a1.st_reg NOT IN('E') AND a4.st_reg NOT IN('E', 'I')";
                strSQL+=" AND a3.co_empRelCabPag IS NOT NULL";
                strSQL+=" UNION";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a5.tx_desCor, a5.tx_desLar, a4.tx_numDia AS ne_numDoc, a4.fe_dia AS fe_doc";
                strSQL+="      , a3.co_empRelCabDia AS co_empRel, a3.co_locRelCabDia AS co_locRel, a3.co_tipDocRelCabDia AS co_tipDocRel, a3.co_docRelCabDia AS co_docRel";
                strSQL+=" FROM tbm_cabotrmovban AS a1 INNER JOIN tbm_detotrmovban AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_docgenotrmovban AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                strSQL+=" INNER JOIN tbm_cabDia AS a4";
                strSQL+=" ON a3.co_empRelCabDia=a4.co_emp AND a3.co_locRelCabDia=a4.co_loc AND a3.co_tipDocRelCabDia=a4.co_tipDoc AND a3.co_docRelCabDia=a4.co_dia";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a5 ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + intCodEmpRel + "";
                strSQL+=" AND a1.co_loc=" + intCodLocRel + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDocRel + "";
                strSQL+=" AND a1.co_doc=" + intCodDocRel + "";
                strSQL+=" AND a2.co_reg=" + codigoRegistro + "";
                if(estado=='n')
                    strSQL+=" AND a1.st_reg NOT IN('E','I') AND a4.st_reg NOT IN('E','I')";
                else
                    strSQL+=" AND a1.st_reg NOT IN('E') AND a4.st_reg NOT IN('E')";
                strSQL+=" AND a3.co_empRelCabDia IS NOT NULL";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,                 "");
                    vecReg.add(INT_TBL_DAT_COD_EMP,             "" + rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC,             "" + rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,         "" + rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBL_DAT_COD_DOC,             "" + rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_COD_REG,             "" + rst.getString("co_reg"));
                    vecReg.add(INT_TBL_DAT_COD_EMP_REL,         "" + rst.getString("co_empRel"));
                    vecReg.add(INT_TBL_DAT_COD_LOC_REL,         "" + rst.getString("co_locRel"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC_REL,     "" + rst.getString("co_tipDocRel"));
                    vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC_REL, "" + rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC_REL, "" + rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_COD_DOC_REL,         "" + rst.getString("co_docRel"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC,             "" + rst.getString("ne_numDoc"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC,             "" + rst.getString("fe_doc"));
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
            vecDat.clear();
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


}


package Compras.ZafCom47;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import java.util.ArrayList;

public class ZafCom47_02 extends javax.swing.JInternalFrame
{
    //Constantes: JTable "TblDat"
    static final int INT_TBL_LIN= 0 ;
    static final int INT_TBL_CODEMP=1;
    static final int INT_TBL_CODLOC=2;
    static final int INT_TBL_CODTID=3;
    static final int INT_TBL_CODDOC=4;
    static final int INT_TBL_TXDSTI=5;
    static final int INT_TBL_NUMDOC=6;
    static final int INT_TBL_FECDOC=7;
    static final int INT_TBL_NOMCLI=8;
    static final int INT_TBL_BUTDOC=9;

    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblCelRenBut objTblCelRenBut;
    private Vector vecDat, vecCab, vecReg;

    private String strSQL="";
    private String strCodEmpGuia="", strCodLocGuia="";
    private String strCodTipdocGuia="", strCodDocGuia="";
    int inTipConfEI=0;

    /** Creates new form ZafCom47_02 */
    public ZafCom47_02(ZafParSis objZafParsis, String strCodEmp, String strCodLoc, String strCodTipdoc, String strCodDoc ) 
    {
        try
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) objZafParsis.clone();
            objUti = new ZafUtil();

            strCodEmpGuia=strCodEmp;
            strCodLocGuia=strCodLoc;
            strCodTipdocGuia=strCodTipdoc;
            strCodDocGuia=strCodDoc;

            initComponents();
            this.setTitle(objParSis.getNombreMenu() );
        }
        catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }
    }

    private boolean configurarFrm(){
     boolean blnres=false;
        vecDat=new Vector();
        vecCab=new Vector();    //Almacena las cabeceras
        vecCab.clear();

        vecCab.add(INT_TBL_LIN,"");
        vecCab.add(INT_TBL_CODEMP,"Cód.Emp ");
        vecCab.add(INT_TBL_CODLOC,"Cód.Loc");
        vecCab.add(INT_TBL_CODTID,"Cód.Tip.Doc");
        vecCab.add(INT_TBL_CODDOC,"Cód.Doc");
        vecCab.add(INT_TBL_TXDSTI,"Des.TipDoc");
        vecCab.add(INT_TBL_NUMDOC,"Num.Doc");
        vecCab.add(INT_TBL_FECDOC,"Fec.Doc");
        vecCab.add(INT_TBL_NOMCLI,"Nom.Cli");
        vecCab.add(INT_TBL_BUTDOC,"..");

        objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        //Configurar JTable: Establecer la fila de cabecera.
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LIN);

        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        tblDat.getTableHeader().setReorderingAllowed(false);
        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_TXDSTI).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(90);
        tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(200);
        tcmAux.getColumn(INT_TBL_BUTDOC).setPreferredWidth(25);

        ArrayList arlColHid=new ArrayList();

         arlColHid.add(""+INT_TBL_CODEMP);
         arlColHid.add(""+INT_TBL_CODLOC);
         arlColHid.add(""+INT_TBL_CODDOC);
         arlColHid.add(""+INT_TBL_CODTID);

        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;


        //Configurar JTable: Establecer columnas editables.
        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_BUTDOC);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;
        //Configurar JTable: Editor de la tabla.
        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

        objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_BUTDOC).setCellRenderer(objTblCelRenBut);
        objTblCelRenBut=null;
        new ButDoc(tblDat, INT_TBL_BUTDOC);   //*****

        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

         return blnres;
    }

    private class ButDoc extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButDoc(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Documento.");
        }
        public void butCLick() {
           int intCol = tblDat.getSelectedRow();
            mostrarVentanaOrdDes( tblDat.getValueAt(intCol, INT_TBL_CODEMP).toString()
                    ,tblDat.getValueAt(intCol, INT_TBL_CODLOC).toString()
                    ,tblDat.getValueAt(intCol, INT_TBL_CODTID).toString()
                    ,tblDat.getValueAt(intCol, INT_TBL_CODDOC).toString()
                    );
        }
    }

    private void mostrarVentanaOrdDes(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){
           Compras.ZafCom23.ZafCom23 obj1 = new  Compras.ZafCom23.ZafCom23(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc );
           this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
           obj1.show();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFrm = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCer = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                exitForm(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        lblTit.setText("Listado de Guías remisión");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panFrm.setLayout(new java.awt.BorderLayout());

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

        panFrm.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Datos", panFrm);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);
        tabFrm.getAccessibleContext().setAccessibleName("Datos");

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 523, 358);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
}//GEN-LAST:event_butCerActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
        cargarReg();
    }//GEN-LAST:event_formInternalFrameOpened

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }        
    }//GEN-LAST:event_exitForm



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panTit;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.tx_descor, a1.ne_numdoc, a1.fe_doc, a1.tx_nomclides " +
                        " FROM tbr_cabguirem as a " +
                        " INNER JOIN tbm_cabguirem as a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
                        " INNER JOIN tbm_cabtipdoc as a2 ON (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc ) " +
                        " WHERE a.co_emp="+strCodEmpGuia+" AND a.co_locrel="+strCodLocGuia+" AND a.co_tipdocrel="+strCodTipdocGuia+" AND a.co_docrel="+strCodDocGuia+" ";

                //Limpiar vector de datos.
                vecDat.clear();
                rst=stm.executeQuery(strSQL);
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LIN,"");
                    vecReg.add(INT_TBL_CODEMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_CODLOC,rst.getString("co_loc"));
                    vecReg.add(INT_TBL_CODTID,rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_CODDOC,rst.getString("co_doc"));
                    vecReg.add(INT_TBL_TXDSTI,rst.getString("tx_descor"));
                    vecReg.add(INT_TBL_NUMDOC,rst.getString("ne_numdoc"));
                    vecReg.add(INT_TBL_FECDOC,rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_NOMCLI,rst.getString("tx_nomclides"));
                    vecReg.add(INT_TBL_BUTDOC,"..");
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
    
}

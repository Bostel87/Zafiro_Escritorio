
package Compras.ZafCom47;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class ZafCom47_01 extends javax.swing.JInternalFrame 
{
    //Constantes: JTable "TblDat"
    static final int INT_TBL_LIN= 0 ;
    static final int INT_TBL_CODEMP= 1 ;
    static final int INT_TBL_CODLOC= 2 ;
    static final int INT_TBL_CODTID= 3 ;
    static final int INT_TBL_CODDOC= 4 ;
    static final int INT_TBL_TXDSTI= 5 ;
    static final int INT_TBL_NUMDOC= 6 ;
    static final int INT_TBL_FECDOC= 7 ;
    static final int INT_TBL_BUTDOC= 8 ;
    static final int INT_TBL_CODLOCREL= 9 ;
    static final int INT_TBL_CODTIDREL= 10 ;
    static final int INT_TBL_CODDOCREL= 11 ;
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;       
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblEdi objTblEdi;                              //Editor: Editor del JTable.
    private Vector vecDat, vecCab, vecReg;

    private int inTipConfEI=0;
    private String strCodEmpGuia="", strCodLocGuia="";
    private String strCodTipdocGuia="", strCodDocGuia="", strCodRegGuia="";
    private String strSQL="";    

    /** Creates new form ZafCom47_01 */
    public ZafCom47_01(ZafParSis objParsis, String strCodEmp, String strCodLoc, String strCodTipdoc, String strCodDoc, int inTipConf ) 
    {
        try
        {
            this.objParSis = (ZafParSis) objParsis.clone();
            objUti = new ZafUtil();

            strCodEmpGuia=strCodEmp;
            strCodLocGuia=strCodLoc;
            strCodTipdocGuia=strCodTipdoc;
            strCodDocGuia=strCodDoc;
            inTipConfEI=inTipConf;

            initComponents();

            this.setTitle(objParSis.getNombreMenu() );
        }
        catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }
    }

    /** Creates new form ZafCom47_01 */
    public ZafCom47_01(ZafParSis objParsis, String strCodEmp, String strCodLoc, String strCodTipdoc, String strCodDoc, String strCodReg, int inTipConf ) 
    {
        try
        {
            this.objParSis = (ZafParSis) objParsis.clone();
            objUti = new ZafUtil();

            strCodEmpGuia=strCodEmp;
            strCodLocGuia=strCodLoc;
            strCodTipdocGuia=strCodTipdoc;
            strCodDocGuia=strCodDoc;
            strCodRegGuia=strCodReg;
            inTipConfEI=inTipConf;

            initComponents();

            this.setTitle(objParSis.getNombreMenu() );
        }
        catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }
    }
    
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(18);  //Almacena las cabeceras
            vecCab.clear();

            vecCab.add(INT_TBL_LIN,"");
            vecCab.add(INT_TBL_CODEMP,"Cód.Emp ");
            vecCab.add(INT_TBL_CODLOC,"Cód.Loc");
            vecCab.add(INT_TBL_CODTID,"Cód.Tip.Doc");
            vecCab.add(INT_TBL_CODDOC,"Cód.Doc");
            vecCab.add(INT_TBL_TXDSTI,"Des.TipDoc");
            vecCab.add(INT_TBL_NUMDOC,"Num.Doc");
            vecCab.add(INT_TBL_FECDOC,"Fec.Doc");
            vecCab.add(INT_TBL_BUTDOC,"..");
            vecCab.add(INT_TBL_CODLOCREL,"..");
            vecCab.add(INT_TBL_CODTIDREL,"..");
            vecCab.add(INT_TBL_CODDOCREL,"..");

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
            tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_TXDSTI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_BUTDOC).setPreferredWidth(25);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_CODDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODTID, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODLOCREL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODTIDREL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODDOCREL, tblDat);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_BUTDOC);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);      

            objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTDOC).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            new ButDoc(tblDat, INT_TBL_BUTDOC); 

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

    private class ButDoc extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButDoc(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Documento.");
        }
        public void butCLick() {
            int intCol = tblDat.getSelectedRow();
            llamarVentana( tblDat.getValueAt(intCol, INT_TBL_CODEMP).toString()
                          ,tblDat.getValueAt(intCol, INT_TBL_CODLOC).toString()
                          ,tblDat.getValueAt(intCol, INT_TBL_CODTID).toString()
                          ,tblDat.getValueAt(intCol, INT_TBL_CODDOC).toString()
                          ,tblDat.getValueAt(intCol, INT_TBL_CODLOCREL).toString()
                          ,tblDat.getValueAt(intCol, INT_TBL_CODTIDREL).toString()
                          ,tblDat.getValueAt(intCol, INT_TBL_CODDOCREL).toString()
                        );
        }
    }

    private void llamarVentana(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodLocRel, String strCodTipDocRel, String strCodDocRel ){
        Compras.ZafCom19.ZafCom19 obj1 = new  Compras.ZafCom19.ZafCom19(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, strCodLocRel, strCodTipDocRel, strCodDocRel, inTipConfEI );
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

        lblTit.setText("Listado de Confirmaciones");
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
                if(inTipConfEI==1){
                    strSQL+=" SELECT * FROM ( "
                           +"    select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor,  a3.ne_numdoc, a3.fe_doc,  a.co_locrelguirem as co_locrel,  a.co_tipdocrelguirem as co_tipdocrel, a.co_docrelguirem as co_docrel  "
                           +"    from  tbm_detingegrmerbod as a  "
                           +"    inner join tbm_cabingegrmerbod as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc )   "
                           +"    inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc )  "
                           +"    where a.co_emp="+strCodEmpGuia+" and a.co_locrelguirem="+strCodLocGuia+"  and a.co_tipdocrelguirem= "+strCodTipdocGuia+"  "
                           +"    and a.co_docrelguirem = "+strCodDocGuia+ ((!strCodRegGuia.equals(""))?" and a.co_regrelguirem = " + strCodRegGuia : " ")
                           +"    and a3.st_reg not in ('E','I') "
                           +"    group by a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor,  a3.ne_numdoc, a3.fe_doc , a.co_locrelguirem,  a.co_tipdocrelguirem, a.co_docrelguirem  "
                           +"  union all  "
                           +"    select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor,  a3.ne_numdoc, a3.fe_doc,  x.co_loc as co_locrel,  x.co_tipdoc as co_tipdocrel, x.co_doc as co_docrel "
                           +"    from ( "
                           +"           select a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc "
                           +"           from tbr_detguirem as a2   "
                           +"           inner join tbm_detguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc and a3.co_reg=a2.co_reg )  "
                           +"           inner join tbm_cabguirem as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc  )  "
                           +"           inner join tbm_cabtipdoc as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc)  "
                           +"           where a2.co_emp="+strCodEmpGuia+" and a2.co_locrel="+strCodLocGuia+" and a2.co_tipdocrel= "+strCodTipdocGuia+" and a2.co_docrel= "+strCodDocGuia
                           +"           "+ ((!strCodRegGuia.equals(""))?" and a2.co_regrel = " + strCodRegGuia : " ")        
                           +"           and a4.st_reg='A'  "
                           +"           group by a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a5.tx_descor, a4.ne_numdoc, a4.fe_doc  "
                           +"           order by a4.ne_numdoc "
                           +"    ) as x  "
                           +"    inner join tbm_detingegrmerbod as a on (a.co_emp=x.co_emp and a.co_locrelguirem=x.co_loc and a.co_tipdocrelguirem=x.co_tipdoc and a.co_docrelguirem=x.co_doc ) "
                           +"    inner join tbm_cabingegrmerbod as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc )   "
                           +"    inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc )  "
                           +"    where a3.st_reg not in ('E','I')   "
                           +"    group by a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor,  a3.ne_numdoc, a3.fe_doc , x.co_loc,  x.co_tipdoc, x.co_doc "
                           +" ) AS x  ORDER BY ne_numdoc  ";
                }
                if(inTipConfEI==2){
                    strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor,  a3.ne_numdoc, a3.fe_doc, a.co_locrel,  a.co_tipdocrel, a.co_docrel"
                           +" FROM tbm_detingegrmerbod as a " 
                           +" INNER JOIN tbm_cabingegrmerbod as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
                           +" INNER JOIN tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) " 
                           +" WHERE a.co_emp="+strCodEmpGuia+" AND a.co_locrel="+strCodLocGuia+" AND a.co_tipdocrel="+strCodTipdocGuia+" " 
                           +" AND a.co_docrel = "+strCodDocGuia+ ((!strCodRegGuia.equals(""))?" AND a.co_regrel = " + strCodRegGuia : " ") 
                           +" AND a3.st_reg not in ('E','I') " 
                           +" GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor, a3.ne_numdoc, a3.fe_doc, a.co_locrel,  a.co_tipdocrel, a.co_docrel ";
                }

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
                    vecReg.add(INT_TBL_BUTDOC,"..");
                    vecReg.add(INT_TBL_CODLOCREL,rst.getString("co_locrel"));
                    vecReg.add(INT_TBL_CODTIDREL,rst.getString("co_tipdocrel"));
                    vecReg.add(INT_TBL_CODDOCREL,rst.getString("co_docrel"));
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
        catch (java.sql.SQLException e)     {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
}

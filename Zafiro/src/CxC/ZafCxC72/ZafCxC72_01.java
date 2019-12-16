/*
 * Created on 13 de agosto de 2008, 10:26
 */

package CxC.ZafCxC72;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
/**
 *
 * @author  jayapata 
 */
public class ZafCxC72_01 extends javax.swing.JInternalFrame {
    Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButDG;
    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG;
    private ZafMouMotAda objMouMotAda;
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
    private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt;

    ZafUtil objUti;
    String strFormatoFecha="d/m/y";

      // TABLA DE CAMPOS2
    final int INT_TBL_LINEA  =0;
    final int INT_TBL_CANSOL =1;
    final int INT_TBL_NDIAPO =2;
    final int INT_TBL_FECPOS =3;
    final int INT_TBL_TXTOBS =4;
    final int INT_TBL_BUTOBS =5;
    final int INT_TBL_AUTPOS =6;
    final int INT_TBL_DENPOS =7;
    final int INT_TBL_ADIPOS =8;
    final int INT_TBL_AFEPOS =9;
    final int INT_TBL_TXTOBA =10;
    final int INT_TBL_BUTOBA =11;

    Vector vecCab=new Vector(); 
    private Vector  vecDat, vecReg;
    
    int CodEmp;
    int CodLoc;
    int CodTipDoc;
    int CodDoc;
    int CodReg;
    int CodPos;

    
    /** Creates new form ListadoSolDevVenAut */
    public ZafCxC72_01(Librerias.ZafParSis.ZafParSis obj, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodReg, int intCodPos ) {
       try{
	  this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	   initComponents();
	   
           CodEmp=intCodEmp;
           CodLoc=intCodLoc;
           CodTipDoc=intCodTipDoc;
           CodDoc=intCodDoc;
           CodReg=intCodReg;
           CodPos=intCodPos;
           
           this.setTitle(objZafParSis.getNombreMenu()+"  v0.1 ");
           lblTit.setText(objZafParSis.getNombreMenu());
            
	    objUti = new ZafUtil();

            vecDat=new Vector();

            objTblCelEdiButGenDG=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();


            configurarForm();
            
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }   
         
            
            
	 }catch (CloneNotSupportedException e){
               objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    
    
    
       
 private boolean configurarForm(){
  boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL_LINEA,"");
    vecCab.add(INT_TBL_CANSOL,"Cancelar");
    vecCab.add(INT_TBL_NDIAPO,"Dias");
    vecCab.add(INT_TBL_FECPOS,"Fecha");
    vecCab.add(INT_TBL_TXTOBS,"Observacion");
    vecCab.add(INT_TBL_BUTOBS,"");
    vecCab.add(INT_TBL_AUTPOS,"Autorizacion");
    vecCab.add(INT_TBL_DENPOS,"Denegar");
    vecCab.add(INT_TBL_ADIPOS,"Dias");
    vecCab.add(INT_TBL_AFEPOS,"Fecha");
    vecCab.add(INT_TBL_TXTOBA,"Observacion");
    vecCab.add(INT_TBL_BUTOBA,"");
   
    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);

     //Configurar JTable: Establecer tipo de selección.
    tblDat.setRowSelectionAllowed(true);
    tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

     //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
     objMouMotAda=new ZafMouMotAda();
     tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

     /* Aqui se agrega las columnas que van
             ha hacer ocultas
         * */
        ArrayList arlColHid=new ArrayList();
       

        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;


    objTblMod.setColumnDataType(INT_TBL_ADIPOS, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_NDIAPO, objTblMod.INT_COL_DBL, new Integer(0), null);



    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    tblDat.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_NDIAPO).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_FECPOS).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_TXTOBS).setPreferredWidth(120);
    tcmAux.getColumn(INT_TBL_BUTOBS).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_AUTPOS).setPreferredWidth(35);
    tcmAux.getColumn(INT_TBL_CANSOL).setPreferredWidth(35);
    tcmAux.getColumn(INT_TBL_DENPOS).setPreferredWidth(35);
    tcmAux.getColumn(INT_TBL_TXTOBA).setPreferredWidth(120);
    tcmAux.getColumn(INT_TBL_BUTOBA).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_ADIPOS).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_AFEPOS).setPreferredWidth(70);
 


    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();

       vecAux.add("" + INT_TBL_BUTOBS);
       vecAux.add("" + INT_TBL_BUTOBA);
    objTblMod.setColumnasEditables(vecAux);
    vecAux=null;
    //Configurar JTable: Editor de la tabla.





    objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();//inincializo el renderizador
    objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
    objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
    objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
    tcmAux.getColumn(INT_TBL_ADIPOS).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_NDIAPO).setCellRenderer(objTblCelRenLbl);
    objTblCelRenLbl=null;



     objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
     tcmAux.getColumn(INT_TBL_NDIAPO).setCellEditor(objTblCelEdiTxt);
     tcmAux.getColumn(INT_TBL_TXTOBS).setCellEditor(objTblCelEdiTxt);
     tcmAux.getColumn(INT_TBL_TXTOBA).setCellEditor(objTblCelEdiTxt);
     objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){


      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
      }
      });

     objTblCelEdiTxt=null;




        objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_AUTPOS).setCellRenderer(objTblCelRenChk);
        tcmAux.getColumn(INT_TBL_CANSOL).setCellRenderer(objTblCelRenChk);
        tcmAux.getColumn(INT_TBL_DENPOS).setCellRenderer(objTblCelRenChk);
        objTblCelRenChk=null;


        objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_CANSOL).setCellEditor(objTblCelEdiChk);
        objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {


            }
        });
       objTblCelEdiChk=null;


        objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_AUTPOS).setCellEditor(objTblCelEdiChk);
        objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
           
            }
        });
        objTblCelEdiChk=null;


        objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_DENPOS).setCellEditor(objTblCelEdiChk);
        objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
             
            }
        });
       objTblCelEdiChk=null;



            objTblCelRenButDG=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTOBS).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_BUTOBA).setCellRenderer(objTblCelRenButDG);
            objTblCelRenButDG.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    switch (objTblCelRenButDG.getColumnRender())
                    {
                      case INT_TBL_BUTOBS:
                            objTblCelRenButDG.setText("...");
                       break;
                       case INT_TBL_BUTOBA:
                            objTblCelRenButDG.setText("...");
                       break;
                    }
                }
            });


          //Configurar JTable: Editor de celdas.

            tcmAux.getColumn(INT_TBL_BUTOBS).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_BUTOBA).setCellEditor(objTblCelEdiButGenDG);
            objTblCelEdiButGenDG.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                          case INT_TBL_BUTOBS:
                          break;

                          case INT_TBL_BUTOBA:
                          break;

                        
                       }
                     }
                    }
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel=tblDat.getSelectedColumn();
                    int intCol = tblDat.getSelectedRow();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                          case INT_TBL_BUTOBS:

                            llamarVenObs( (objTblMod.getValueAt(intFilSel, INT_TBL_TXTOBS)==null?"":objTblMod.getValueAt(intFilSel, INT_TBL_TXTOBS).toString()) ,intFilSel, INT_TBL_TXTOBS );

                           break;

                           case INT_TBL_BUTOBA:

                               llamarVenObs( (objTblMod.getValueAt(intFilSel, INT_TBL_TXTOBA)==null?"":objTblMod.getValueAt(intFilSel, INT_TBL_TXTOBA).toString()) ,intFilSel, INT_TBL_TXTOBA );

                           break;

                         

                        }
                }}
           });


    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);



     ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
     objTblHeaGrp.setHeight(16*2);

     ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" SOLICITUD  " );
       objTblHeaColGrpAmeSur.setHeight(16);


            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CANSOL));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NDIAPO));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECPOS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TXTOBS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTOBS));

         objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
         objTblHeaColGrpAmeSur=null;


         objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("AUTORIZACION ");
         objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_AUTPOS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DENPOS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_ADIPOS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_AFEPOS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TXTOBA));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTOBA));
         objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
         objTblHeaColGrpAmeSur=null;



     objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
     tcmAux.getColumn(INT_TBL_NDIAPO).setCellEditor(objTblCelEdiTxt);
     objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

      }
      });



    tcmAux.getColumn(INT_TBL_FECPOS).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));
    tcmAux.getColumn(INT_TBL_AFEPOS).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));



    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
    tcmAux=null;


     return blnres;
}



    



private void llamarVenObs(String strObs, int intFil, int intCol){
 Maestros.ZafMae07.ZafMae07_01 obj1 = new  Maestros.ZafMae07.ZafMae07_01(javax.swing.JOptionPane.getFrameForComponent(this), true , strObs );
 obj1.show();
 if(obj1.getAceptar())
   tblDat.setValueAt( obj1.getObser(), intFil, intCol );
 obj1=null;
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
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panFilTabGen = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBot = new javax.swing.JPanel();
        panButPanBut = new javax.swing.JPanel();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        panTit.setFont(new java.awt.Font("SansSerif", 1, 12));
        panTit.setPreferredSize(new java.awt.Dimension(46, 24));

        lblTit.setText("Resumen de la solicitud de devolución de ventas.");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        panFilTabGen.setLayout(new java.awt.BorderLayout());

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
        scrTbl.setViewportView(tblDat);

        panFilTabGen.add(scrTbl, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Filtro", panFilTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panBot.setLayout(new java.awt.BorderLayout());

        butCer.setFont(new java.awt.Font("SansSerif", 0, 11));
        butCer.setText("Cerrar");
        butCer.setPreferredSize(new java.awt.Dimension(79, 23));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panButPanBut.add(butCer);

        panBot.add(panButPanBut, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBot.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBot, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-339)/2, 700, 339);
    }// </editor-fold>//GEN-END:initComponents

    
    
private class ZafThreadGUI extends Thread
{
 public void run(){

    lblMsgSis.setText("Obteniendo datos...");
    pgrSis.setIndeterminate(true);    

      consultarDat();
    
   
     
   objThrGUI=null;
}
}


    
    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        // TODO add your handling code here:
        cerrarVen();
    }//GEN-LAST:event_butCerActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        cerrarVen();
    }//GEN-LAST:event_formInternalFrameClosing
    
private void cerrarVen(){
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
    System.gc();
    dispose();
}}

    
    
    
private boolean consultarDat(){
 boolean blnRes=false;
 java.sql.Connection conn;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
   conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn!=null){
      stmLoc=conn.createStatement();
          
      strSql="select a.ne_diasolpos, (a1.fe_venchq+a.ne_diasolpos)  as fe_solpos , a.tx_obssolpos,  a.st_solpos, a.ne_diaautpos, (a1.fe_venchq+a.ne_diaautpos) as fe_autpos, a.tx_obsautpos "
      + " from tbm_posChqRecDoc as a  "
      + " INNER JOIN tbm_detrecdoc AS a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc AND a1.co_reg=a.co_reg  ) "
      + " where a.co_emp="+CodEmp+" and a.co_loc="+CodLoc+" and a.co_tipdoc="+CodTipDoc+" and a.co_doc="+CodDoc+" "
      + " and a.co_reg="+CodReg+" "
      //+ " and a.co_pos!= "+CodPos+" "
      + " and a.st_solpos = 'A' order by a.co_pos ";

      vecDat.clear();

     // System.out.println(" --> "+strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){
            vecReg=new Vector();
            vecReg.add(INT_TBL_LINEA,"");
            vecReg.add(INT_TBL_CANSOL, new Boolean(  (rstLoc.getString("st_solpos")==null?false:(rstLoc.getString("st_solpos").equals("C")?true:false)) ) );
            vecReg.add(INT_TBL_NDIAPO, rstLoc.getString("ne_diasolpos") );
            vecReg.add(INT_TBL_FECPOS, (rstLoc.getDate("fe_solpos")==null?"":objUti.formatearFecha(rstLoc.getDate("fe_solpos"), "dd/MM/yyyy")) );
            vecReg.add(INT_TBL_TXTOBS, rstLoc.getString("tx_obssolpos") );
            vecReg.add(INT_TBL_BUTOBS, "" );
            vecReg.add(INT_TBL_AUTPOS, new Boolean( (rstLoc.getString("st_solpos")==null?false:(rstLoc.getString("st_solpos").equals("A")?true:false)) ) );
            vecReg.add(INT_TBL_DENPOS, new Boolean( (rstLoc.getString("st_solpos")==null?false:(rstLoc.getString("st_solpos").equals("D")?true:false)) ) );
            vecReg.add(INT_TBL_ADIPOS, rstLoc.getString("ne_diaautpos") );
            vecReg.add(INT_TBL_AFEPOS, (rstLoc.getDate("fe_autpos")==null?"":objUti.formatearFecha(rstLoc.getDate("fe_autpos"), "dd/MM/yyyy")) );

            vecReg.add(INT_TBL_TXTOBA, rstLoc.getString("tx_obsautpos") );
            vecReg.add(INT_TBL_BUTOBA, "" );

        vecDat.add(vecReg);
       }
         objTblMod.setData(vecDat);
         tblDat .setModel(objTblMod);
      rstLoc.close(); 
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
    conn.close();
    conn=null;
     lblMsgSis.setText("Listo");
     pgrSis.setValue(0);
     pgrSis.setIndeterminate(false);    
    blnRes=true;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;          
}   
    
    
      
    
    

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panButPanBut;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panFilTabGen;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
    
    

private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LINEA:
            strMsg="";
            break;
         
            case INT_TBL_CANSOL:
            strMsg="Cancelar.";
            break;
            case INT_TBL_NDIAPO:
            strMsg="Dias de postergacion.";
            break;
            case INT_TBL_FECPOS:
            strMsg="Fecha de postergacion.";
            break;

            case INT_TBL_TXTOBS:
            strMsg="Observacion de la solicitud de postergacion.";
            break;
            case INT_TBL_BUTOBS:
            strMsg="";
            break;
            case INT_TBL_AUTPOS:
            strMsg="Autorizacion";
            break;

            case INT_TBL_DENPOS:
            strMsg="Denegar.";
            break;
            case INT_TBL_ADIPOS:
            strMsg="Dias de la postergacion autorizada";
            break;
            case INT_TBL_AFEPOS:
            strMsg="Fecha de postergacion autorizada";
            break;

            case INT_TBL_TXTOBA:
            strMsg="Observacion de la autorizacion.";
            break;
            case INT_TBL_BUTOBA:
            strMsg="";
            break;




        default:
            strMsg="";
            break;
    }
    tblDat.getTableHeader().setToolTipText(strMsg);
}
}





    protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }




    
    
}

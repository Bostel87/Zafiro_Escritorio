/*
 * ZafCom12.java
 *
 * Created on 20 de agosto de 2005, 11:38 PM        
 */
package CxP.ZafCxP08;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafSelFec.ZafSelFec;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
    
/**  
 *
 * @author  Javier Ayapata    
 */
public class ZafCxP08 extends javax.swing.JInternalFrame 
{       
//Variables generales.
private ZafSelFec objSelDat;
private ZafParSis objParSis;
private ZafUtil objUti;
private ZafTblMod objTblMod, objTblModSal;
private ZafThreadGUI objThrGUI;
private String  strAux;

ZafVenCon objVenConTipdoc; //*****************
private Vector vecRegVenCom, vecRegCbEfPag,vecRegCbRet, vecRegDif ;
private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
 
 String strVersion = " v 2.4";

 String strCodTipDoc="";
 String strNomTipDoc="";
 java.util.Vector vecLoc;

 StringBuffer strBufAnioVen;
 StringBuffer strBufAnioSal;
 StringBuffer strBufVentas;
 StringBuffer strBufCbEfe;
 StringBuffer strBufCbRet;
 StringBuffer strBufSalIni;
 StringBuffer strBufSalFin;
 StringBuffer strBufSalInc;
 StringBuffer strBufCompras;

 boolean blnEstBufAniVen=false;
 boolean blnEstBufAniSal=false;
 boolean blnEstBufVen=false;
 boolean blnEstBufCbEf=false;
 boolean blnEstBufCbRet=false;
 boolean blnEstBufSalIni=false;
 boolean blnEstBufSalFin=false;
 boolean blnEstBufSalInc=false;
 boolean blnEstBufCom = false;



/** Crea una nueva instancia de la clase ZafIndRpt. */
 public ZafCxP08(ZafParSis obj){
   try{
     this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
    initComponents();
    //Inicializar objetos.
  
    objUti=new ZafUtil();
    strAux=objParSis.getNombreMenu();
    this.setTitle(strAux + " "+ strVersion );
    lblTit.setText(strAux);

    objSelDat=new ZafSelFec();
    objSelDat.setCheckBoxVisible(true);
    objSelDat.setCheckBoxChecked(true);
    objSelDat.setTitulo("Fecha del documento");
    objSelDat.setCheckBoxVisible(true);
    PanCab.add(objSelDat);
    objSelDat.setBounds(4, 35, 560, 80);

    if( ! ((objParSis.getCodigoUsuario()==1) || (objParSis.getCodigoUsuario()==24)) )
         PanDet01.setVisible(false);
   }catch (CloneNotSupportedException e){
               objUti.mostrarMsgErr_F1(this, e);
        }
}
        

public void Configura_ventana_consulta(){
  configurarFrm();
  configurarVenConTipDoc();
}

private boolean configurarVenConTipDoc(){
 boolean blnRes=true;
 try{
    ArrayList arlCam=new ArrayList();
    arlCam.add("a.co_tipdoc");
    arlCam.add("a.tx_descor");
    arlCam.add("a.tx_deslar");

    ArrayList arlAli=new ArrayList();
    arlAli.add("Código");
    arlAli.add("Alias");
    arlAli.add("Descripción");

    ArrayList arlAncCol=new ArrayList();
    arlAncCol.add("85");
    arlAncCol.add("105");
    arlAncCol.add("350");

    //Armar la sentencia SQL.   a7.nd_stkTot,
    String Str_Sql="";
    Str_Sql="Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b " +
    " left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
    " where   b.co_emp = " + objParSis.getCodigoEmpresa() + " and " +
    " b.co_loc = " + objParSis.getCodigoLocal()   + " and " +
    " b.co_mnu ="+objParSis.getCodigoMenu();  

    objVenConTipdoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
    arlCam=null;
    arlAli=null;
    arlAncCol=null;

    objVenConTipdoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
 }catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}

private boolean configurarFrm(){
 boolean blnRes=true;
 try{
    //Inicializar objetos.
    Vector vecDat=new Vector();    //Almacena los datos
    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();
    vecCab.add(0," ");
    vecCab.add(1," Total ");
    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);
    tblDat.setRowSelectionAllowed(false);
    tblDat.setCellSelectionEnabled(true);
    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    tblDat.getTableHeader().setReorderingAllowed(false);

    vecCab.clear();
    vecCab.add(0," ");
    vecCab.add(1," Total ");
    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDatSal.setModel(objTblMod);
    tblDatSal.setRowSelectionAllowed(false);
    tblDatSal.setCellSelectionEnabled(true);
    tblDatSal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    tblDatSal.getTableHeader().setReorderingAllowed(false);

    vecLoc =  new  java.util.Vector() ; //Vector que contiene el codigo de tipos de documentos
    String strSql="";
    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
      strSql = "select * from ( select 0 as co_emp , 'Todos' union all select co_emp  , tx_nom from tbm_emp where co_emp !="+objParSis.getCodigoEmpresa()+" and st_reg not in ('I') ) as x order by co_emp  ";
    else 
       strSql = "select 0, 'Todos' union all select co_loc , tx_nom from tbm_loc where co_emp="+objParSis.getCodigoEmpresa()+" and st_reg not in ('I') ";
    

    objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(),
    objParSis.getClaveBaseDatos(), strSql, cboLocEmp, vecLoc);


    cboLocEmp.setSelectedIndex(0);
    setEditable(false);
  

 }catch(Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
return blnRes;
}

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        butGrpOpt = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        PanCab = new javax.swing.JPanel();
        cboLocEmp = new javax.swing.JComboBox();
        lblLoc = new javax.swing.JLabel();
        PanDet = new javax.swing.JPanel();
        PanDet01 = new javax.swing.JPanel();
        srcTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        PanDet02 = new javax.swing.JPanel();
        srcTblSal = new javax.swing.JScrollPane();
        tblDatSal = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        chkEscComp = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
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
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(new java.awt.BorderLayout());

        PanCab.setPreferredSize(new java.awt.Dimension(0, 120));
        PanCab.setLayout(null);

        cboLocEmp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        PanCab.add(cboLocEmp);
        cboLocEmp.setBounds(90, 5, 200, 20);

        lblLoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblLoc.setText("Empresa/Local:");
        PanCab.add(lblLoc);
        lblLoc.setBounds(4, 5, 100, 20);

        panFil.add(PanCab, java.awt.BorderLayout.NORTH);

        PanDet.setLayout(new java.awt.BorderLayout());

        PanDet01.setPreferredSize(new java.awt.Dimension(452, 100));
        PanDet01.setLayout(new java.awt.BorderLayout());

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
        srcTbl.setViewportView(tblDat);

        PanDet01.add(srcTbl, java.awt.BorderLayout.CENTER);

        PanDet.add(PanDet01, java.awt.BorderLayout.NORTH);

        PanDet02.setLayout(new java.awt.BorderLayout());

        tblDatSal.setModel(new javax.swing.table.DefaultTableModel(
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
        srcTblSal.setViewportView(tblDatSal);

        PanDet02.add(srcTblSal, java.awt.BorderLayout.CENTER);

        PanDet.add(PanDet02, java.awt.BorderLayout.CENTER);

        panFil.add(PanDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panFil);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.BorderLayout());

        chkEscComp.setSelected(true);
        chkEscComp.setText("Excluir ventas entre compañias.");
        panBot.add(chkEscComp, java.awt.BorderLayout.CENTER);

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        jPanel3.add(butCon);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        jPanel3.add(butCer);

        panBot.add(jPanel3, java.awt.BorderLayout.EAST);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    
    
      
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
       
    }//GEN-LAST:event_formInternalFrameOpened

   
   
    
    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
       if (butCon.getText().equals("Consultar")){
            if (objThrGUI==null){
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }            
       }
       
    }//GEN-LAST:event_butConActionPerformed



private class ZafThreadGUI extends Thread {
  public void run(){

  butCon.setText("Detener");
  lblMsgSis.setText("Procesando datos...");
  pgrSis.setIndeterminate(true);

  strBufVentas = new StringBuffer();
  strBufSalInc = new StringBuffer();
  strBufAnioVen = new StringBuffer();
  strBufAnioSal = new StringBuffer();
  strBufCbEfe = new StringBuffer();
  strBufCbRet = new StringBuffer();
  strBufSalIni = new StringBuffer();
  strBufSalFin = new StringBuffer();
  strBufCompras = new StringBuffer();

  blnEstBufVen=false;
  blnEstBufAniVen=false;
  blnEstBufCbEf=false;
  blnEstBufCbRet=false;
  blnEstBufAniSal=false;
  blnEstBufSalIni=false;
  blnEstBufSalFin=false;
  blnEstBufSalInc=false;
  blnEstBufCom=false;

 
 if(objParSis.getCodigoMenu()==1026){ // CxC

    if( (objParSis.getCodigoUsuario()==1) || (objParSis.getCodigoUsuario()==24)  ){
       if(confirgurarTblsCxC(true)){
         if(_cargarDatosVentas( strFecDesBusVenCom, strFecHasBusVenCom )){
           if(_cargarDatosSaldoVentas( strFecDesBusSalVen, strFecHasBusSalVen, true )){
        }}}
    }else {
         if(confirgurarTblsCxC(true)){
           if(_cargarDatosSaldoVentas( strFecDesBusSalVen, strFecHasBusSalVen, false )){

         }}
     }
 }



 if(objParSis.getCodigoMenu()==519){  // CxP
        if(confirgurarTblsCxC(false)){
          if(_cargarDatosComCxP( strFecDesBusVenCom, strFecHasBusVenCom )){

          }
       }
 }


//          if(txtCodtipdoc.getText().trim().equals("2")){
//           if (cargarDatosMenosDevCom()){
//                if(cargarDatosSaldoCom()){
//                    lblMsgSis.setText("Listo");
//                    pgrSis.setIndeterminate(false);
//                    pgrSis.setValue(0);
//                    butCon.setText("Consultar");
//           }}}


       strBufVentas=null;
       strBufSalInc=null;
       strBufAnioVen=null;
       strBufAnioSal=null;
       strBufCbEfe=null;
       strBufCbRet=null;
       strBufSalIni=null;
       strBufSalFin=null;
       strBufCompras = null;

       lblMsgSis.setText("Listo");
       pgrSis.setIndeterminate(false);
       pgrSis.setValue(0);
       butCon.setText("Consultar");

  objThrGUI=null;
 }
}
    
     
     
     
     
     
     
      
         
       
      
     
     
     private boolean Ratios_otro(){
        double bldSalFin=0; 
        double bldVenMes=0; 
        double bldRes=0; 
        int intDias=0;
        int intCon=2;
        int intDiaMes=0; 
        for(int i=tblDatSal.getColumnCount()-2 ; i>0; i--){

            
            tblDatSal.setValueAt( ""+valor_Ratio(intCon) , 5, i );  
            tblDatSal.setValueAt( "" , 4, i ); 
            
            tblDatSal.setValueAt( "" , 0, i ); 
            tblDatSal.setValueAt( "" , 1, i ); 
            
            intCon++;
         
         }
         
        
        tblDatSal.setValueAt( "" , 5, 1 );  
        tblDatSal.setValueAt( "" , 5, 2 );  
        
        tblDatSal.setValueAt( "" , 0, tblDatSal.getColumnCount()-1 ); 
        tblDatSal.setValueAt( "" , 1, tblDatSal.getColumnCount()-1 ); 
            
            
       return true;
     }
     
     
     
    
     
      
      
      
     
//
//     private boolean cargarDatosMenosDevCom(){
//        boolean blnRes=false;
//         String strSqlAux3="";
//        int intval3=0;
//         try{
//
//         String fecdesde="2006-05-22",fechasta="2006-05-22";
//         int intMesDes=-1, intMesHas=-1;
//         int intAniDes=0, intAniHas=01;
//
//         if (objSelDat.chkIsSelected())
//                    {
//                        switch (objSelDat.getTypeSeletion())
//                        {
//                            case 1: //Búsqueda por rangos
//                                  intval3=1;
//
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                //   System.out.println(">> "+intMesDes + " <> "+ intMesHas + " <>" + intAniDes  + " <> " + intAniHas  );
//
//
//                                  break;
//                            case 2: //Fechas mayores o iguales a "Desde".
//                                   intval3=2;
//
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                   intAniHas=intAniDes;
//
//                                   fecdesde =  objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = "12/31/"+String.valueOf(objSelDat.getYearSelected());
//
//
//                             //   System.out.println("22 >> "+ intMesDes + " <>" + intAniDes + " -- " + fecdesde  );
//
//                               break;
//                            case 3: //Fechas menores o iguales a "Hasta".
//                                  intval3=3;
//
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                   intAniDes=intAniHas;
//
//                                   fechasta =  objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = "01/01/"+String.valueOf(objSelDat.getYearSelected());
//
//                                // System.out.println(">> "+ intMesHas + " <>" + intAniHas  + " -- " + fechasta );
//
//
//                                break;
//                            case 4: //Todo.
//                                System.out.println("Error.........");
//                                intval3=4;
//                                break;
//
//                        }
//                    }
//
//
//
//
//
//
//           if(intval3==4) return true;
//
//
//             vecDatOrg.clear();
//             con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos() );
//             if (con!=null)
//               {
//                   stm=con.createStatement();
//
//                 vecRegCom=new Vector();
//                 vecRegPag=new Vector();
//                 vecRegRet=new Vector();
//                 vecRegDif=new Vector();
//
//
//           vecCabOrg.clear();
//           vecCabOrg.add(" ");
//           StringBuffer stbins=new StringBuffer();
//           int x=0;
//
//
//
//           if(intval3==2){
//           int hasta= 11;
//           int desde = intMesDes;
//           for(int y=intAniDes; y<=intAniHas; y++){
//           //  if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+"");
//                   if (x>0)
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//          if(intval3==3){
//           int hasta=intMesHas;
//           int desde = 0;
//           for(int y=intAniDes; y<=intAniHas; y++){
//            // if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+"");
//                   if (x>0)
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//
//          if(intval3==1){
//           int hasta=11;
//           int desde = intMesDes;
//           for(int y=intAniDes; y<=intAniHas; y++){
//             if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+"");
//
//
//
//                   if (x>0)
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//        vecCabOrg.add("Total");
//
//
//          String strSqlAux="a.co_emp="+objParSis.getCodigoEmpresa()+" and ";
//          String strSqlAux2=" cabmov.co_emp="+objParSis.getCodigoEmpresa()+" and ";
//          if(local.getSelectedIndex()>0) {
//            strSqlAux = strSqlAux+ "   a.co_loc = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//            strSqlAux2 = strSqlAux2+"  cabmov.co_loc = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//          }
//
//
//
//
//           strSqlAux3="";
//           String strSqlAuxExcComp = "";
//           String strSqlAuxTuv = "";
//           String strSqlAuxCas="";
//           String strSqlAuxNos="";
//           String strSqlAuxDim="";
//
//          if(chkEscComp.isSelected()){
//            strSqlAux3 = " " +
//            "  AND NOT (cabmov.co_emp=1 AND cabmov.co_cli in ( 3516, 602, 603, 2600, 1039 ) ) "+
//            "  AND NOT (cabmov.co_emp=2 AND cabmov.co_cli in ( 2854, 446, 2105, 789 ) ) " +
//            "  AND NOT ( cabmov.co_emp=3 AND cabmov.co_cli in ( 2858, 453, 2107, 832 ) )  " +
//            "  AND NOT (cabmov.co_emp=4 AND cabmov.co_cli in ( 3117,498, 2294, 886 )  ) ";
//
//            strSqlAuxTuv = "  AND NOT (cabmov.co_emp=1 AND cabmov.co_cli in ( 3516, 602, 603, 2600, 1039 ) ) ";
//
//            strSqlAuxCas=" AND NOT (cabmov.co_emp=2 AND cabmov.co_cli in ( 2854, 446, 2105, 789 ) ) ";
//
//            strSqlAuxNos="  AND NOT ( cabmov.co_emp=3 AND cabmov.co_cli in ( 2858, 453, 2107, 832 ) ) ";
//
//            strSqlAuxDim=" AND NOT (cabmov.co_emp=4 AND cabmov.co_cli in ( 3117,498, 2294, 886 )  ) ";
//
//          }
//
//           if(objParSis.getCodigoEmpresa()==1){  strSqlAuxExcComp=strSqlAuxTuv;  }
//           if(objParSis.getCodigoEmpresa()==2){  strSqlAuxExcComp=strSqlAuxCas; }
//           if(objParSis.getCodigoEmpresa()==3){  strSqlAuxExcComp=strSqlAuxNos; }
//           if(objParSis.getCodigoEmpresa()==4){  strSqlAuxExcComp=strSqlAuxDim; }
//
//
//
//          if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
//             strSqlAux =" ";
//             strSqlAux2 =" ";
//             strSqlAuxExcComp=strSqlAux3;
//          }
//
//          String strSqlCompras= " Select extract(year from cabmov.fe_doc) as anio1 , extract(month from cabmov.fe_doc) as mes1  ,  sum(cabmov.nd_tot) as valor_total  from tbm_cabmovinv as cabmov" +
//          " WHERE "+strSqlAux2+"  cabmov.st_reg not in ('I','E') and cabmov.fe_doc between '"+fecdesde+"' and '"+fechasta+"'  and cabmov.co_tipdoc in ("+txtCodtipdoc.getText()+",4) " +
//          " "+strSqlAuxExcComp+ "  GROUP BY extract(year from cabmov.fe_doc), extract(month from cabmov.fe_doc)  ";
//
//
//          String strSqlPagos= " SELECT anio2, mes2, abs(sum(abono)) as valor FROM ( " +
//          " SELECT  extract(year from a.fe_dia) as anio2, extract(month from a.fe_dia) as mes2, d2.co_emp, d2.co_loc, d2.co_tipdoc, d2.co_dia, d2.co_reg  , sum(detpag.nd_abo) as abono " +
//          " FROM tbm_cabdia as a  "+
//          " inner join tbm_detdia as d2 on (d2.co_emp=a.co_emp and d2.co_loc=a.co_loc and d2.co_tipdoc=a.co_tipdoc and d2.co_dia=a.co_dia  )  "+
//          " inner join tbm_placta as d3 on (d3.co_emp=d2.co_emp and d3.co_cta=d2.co_cta and d3.tx_codcta='2.01.03.01.01')  "+
//          " inner join tbm_detpag as detpag on (detpag.co_emp=a.co_emp and detpag.co_loc=a.co_loc and detpag.co_tipdoc=a.co_tipdoc and detpag.co_doc=a.co_dia )  "+
//          " inner join tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag )  "+
//          " inner join tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc )     "+
//          " WHERE  "+strSqlAux+"   a.st_reg not in ('I','E')  and a.co_tipdoc in (32)  and a.fe_dia between '"+fecdesde+"' and '"+fechasta+"'  "+
//          " "+strSqlAuxExcComp+ " GROUP BY extract(year from a.fe_dia), extract(month from a.fe_dia), d2.co_emp, d2.co_loc, d2.co_tipdoc, d2.co_dia, d2.co_reg " +
//          " ) AS a GROUP BY anio2, mes2 ";
//
//
//          String strSqlRete= " SELECT anio2, mes2, abs(sum(abono)) as valor2 from ( "+
//          "  SELECT  extract(year from a.fe_dia) as anio2, extract(month from a.fe_dia) as mes2, d2.co_emp, d2.co_loc, d2.co_tipdoc, d2.co_dia, d2.co_reg  , sum(detpag.nd_abo) as abono   "+
//          "  FROM tbm_cabdia as a   "+
//          "  inner join tbm_detdia as d2 on (d2.co_emp=a.co_emp and d2.co_loc=a.co_loc and d2.co_tipdoc=a.co_tipdoc and d2.co_dia=a.co_dia  )  "+
//          "  inner join tbm_placta as d3 on (d3.co_emp=d2.co_emp and d3.co_cta=d2.co_cta and d3.tx_codcta='2.01.03.01.01')   "+
//          "     inner join tbm_detpag as detpag on (detpag.co_emp=a.co_emp and detpag.co_loc=a.co_loc and detpag.co_tipdoc=a.co_tipdoc and detpag.co_doc=a.co_dia )  "+
//          "     inner join tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag )  "+
//          "     inner join tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc )      "+
//          "  where  "+strSqlAux+"   a.st_reg not in ('I','E')  and a.co_tipdoc in (33)  and a.fe_dia between '"+fecdesde+"' and '"+fechasta+"'  "+
//          " "+strSqlAuxExcComp+" GROUP BY extract(year from a.fe_dia), extract(month from a.fe_dia), d2.co_emp, d2.co_loc, d2.co_tipdoc, d2.co_dia, d2.co_reg    "+
//          " ) as a  GROUP BY anio2, mes2 ";
//
//
//
//          String strSql="SELECT  anio1, mes1, "+
//          " case when valor_total is null then 0 else abs(valor_total) end as valor_total, "+
//          " case when valor is null then 0 else abs(valor) end as valor, "+
//          " case when valor2 is null then 0 else abs(valor2) end as valor2 "+
//          " FROM ( " +
//          "   SELECT * FROM ( " +
//                 " "+strSqlCompras+" "+
//          " ) AS x LEFT JOIN " +
//               " ( "+strSqlPagos+" ) AS y ON(y.anio2=x.anio1 and y.mes2=x.mes1) " +
//          " LEFT JOIN ( "+strSqlRete+" ) AS y2 ON(y2.anio2=x.anio1 and y2.mes2=x.mes1) " +
//          " ) AS x ORDER BY anio1, mes1 ";
//
//
//
//          String sql2 = "select mes, round(valor_total,2), round(valor,2), round(valor2,2), (valor_total-(valor+valor2)) as diferencia  from (  select * from ( "+ stbins.toString() +" ) as x ";
//          sql2 = sql2 + " left join (" +
//          " select * from ( " + strSql + " "+
//          "" +
//          " ) as t ) as t on(t.anio1=x.anio and t.mes1=x.mes)  ) as x order by anio,mes";
//
//         System.out.println(""+ sql2 );
//
//         rst=stm.executeQuery(sql2);
//
//
//                    vecRegCom.add("Compras");
//                    vecRegPag.add("Pagos");
//                    vecRegRet.add("Retención");
//                    vecRegDif.add("Diferencia");
//
//                    double dlbCom=0;
//                    double dlbPag=0;
//                    double dlbRet=0;
//                    double dlbDif=0;
//
//
//                    while (rst.next())
//                    {
//                        vecRegCom.add( new Double(rst.getDouble(2)));
//                        vecRegPag.add( new Double(rst.getDouble(3)));
//                        vecRegRet.add( new Double(rst.getDouble(4)));
//                        vecRegDif.add( new Double(rst.getDouble(5)));
//
//                        dlbCom +=rst.getDouble(2);
//                        dlbPag +=rst.getDouble(3);
//                        dlbRet +=rst.getDouble(4);
//                        dlbDif +=rst.getDouble(5);
//
//                    }
//
//                        dlbCom = objUti.redondear(dlbCom,2);
//                        dlbPag = objUti.redondear(dlbPag,2);
//                        dlbRet = objUti.redondear(dlbRet,2);
//                        dlbDif = objUti.redondear(dlbDif,2);
//
//
//                        vecRegCom.add(new Double(dlbCom));
//                        vecRegPag.add(new Double(dlbPag));
//                        vecRegRet.add(new Double(dlbRet));
//                        vecRegDif.add(new Double(dlbDif));
//
//
//                        java.util.Vector vecData = new java.util.Vector();
//                        vecData.add(vecRegCom);
//                        vecData.add(vecRegPag);
//                        vecData.add(vecRegRet);
//                        vecData.add(vecRegDif);
//
//                          vecDatOrg.add(vecRegCom);
//                          vecDatOrg.add(vecRegPag);
//                          vecDatOrg.add(vecRegRet);
//                          vecDatOrg.add(vecRegDif);
//
//                    con.close();
//                    con=null;
//
//                     Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod2=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
//                     objTblMod2.setHeader(vecCabOrg);
//                     tblDat.setModel(objTblMod2);
//
//
//
//
//
//                        for(int i=1; i<tblDat.getColumnCount(); i++){
//                             objTblCelRenLbl=null;
//                             javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
//                             objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
//                             objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
//                             objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//                             objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
//                             objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
//                             tcmAux.getColumn( i ).setCellRenderer(objTblCelRenLbl);
//                             tcmAux=null;
//                        }
//
//
//
//                     objTblMod2.setData(vecData);
//                     tblDat.setModel(objTblMod2);
//
//
//                    new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
//
//                    blnRes=true;
//
//
//
//        }}
//        catch (java.sql.SQLException e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);
//        }
//
//        return blnRes;
//    }
//
//


int intNumColTblVen=0;

private boolean configurarTblVentas(int intAniDes, int intAniHas, int intMesDes, int intMesHas ){
 boolean blnRes=true;
 try{
    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    intNumColTblVen=0;

    vecCab.add(intNumColTblVen," Inicial ");
  
    for(int anio=intAniDes; anio<=intAniHas; anio++){
     for(int mes=intMesDes; mes<=intMesHas; mes++){
         intNumColTblVen++;
         if( mes==1)  vecCab.add(intNumColTblVen,"Enero"+anio+" ");
         if( mes==2)  vecCab.add(intNumColTblVen,"Febrero"+anio+" ");
         if( mes==3)  vecCab.add(intNumColTblVen,"Marzo"+anio+" ");
         if( mes==4)  vecCab.add(intNumColTblVen,"Abril"+anio+" ");
         if( mes==5)  vecCab.add(intNumColTblVen,"Mayo"+anio+" ");
         if( mes==6)  vecCab.add(intNumColTblVen,"Junio"+anio+"");
         if( mes==7)  vecCab.add(intNumColTblVen,"Julio"+anio+" ");
         if( mes==8)  vecCab.add(intNumColTblVen,"Agosto"+anio+" ");
         if( mes==9)  vecCab.add(intNumColTblVen,"Septiembre"+anio+" ");
         if( mes==10) vecCab.add(intNumColTblVen,"Octubre"+anio+" ");
         if( mes==11) vecCab.add(intNumColTblVen,"Noviembre"+anio+" ");
         if( mes==12) vecCab.add(intNumColTblVen,"Diciembre"+anio+"");

         if(blnEstBufAniVen) strBufAnioVen.append(" UNION ALL ");
         strBufAnioVen.append(" SELECT "+anio+" AS anio, "+mes+" AS mes  ");
         blnEstBufAniVen=true;
      }
    }
    intNumColTblVen++;
    vecCab.add(intNumColTblVen," Total ");

    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);
    tblDat.setRowSelectionAllowed(false);
    tblDat.setCellSelectionEnabled(true);
    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    tblDat.getTableHeader().setReorderingAllowed(false);

    new ZafTblPopMnu(tblDat);
    
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();


    objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();//inincializo el renderizador
    objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
    objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
    objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

    // CONFIGURACION DE CELDA 
    tcmAux.getColumn(0).setPreferredWidth(100);
    for(int col=1; col<=intNumColTblVen; col++){
       tcmAux.getColumn(col).setPreferredWidth(100);
       objTblMod.setColumnDataType(col, objTblMod.INT_COL_DBL, new Integer(0), null);
       tcmAux.getColumn(col).setCellRenderer(objTblCelRenLbl);
    }

    objTblCelRenLbl=null;
    tcmAux=null;

 }catch(Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
return blnRes;
}


int intNumColTblSal=0;

private boolean configurarTblSaldos(int intAniDes, int intAniHas, int intMesDes, int intMesHas, boolean blnResAnio ){
 boolean blnRes=true;
 int intMesDesLoc=intMesDes;
 int intMesHasLoc=12;
 boolean blnIngFor=false;
 try{
    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();
    intNumColTblSal=0;

    vecCab.add(intNumColTblSal," Inicial ");

    if(blnResAnio) intAniDes=intAniDes-1;

    if(intAniDes==intAniHas){
        intMesDesLoc=intMesDes;
        intMesHasLoc=intMesHas;
    }

    for(int anio=intAniDes; anio<=intAniHas; anio++){
     if(anio==intAniHas){ 
        intMesDesLoc=1;
        intMesHasLoc=intMesHas;
      }else{                 
         if(blnIngFor) intMesDesLoc=1;
         intMesHasLoc=12;
      }
      blnIngFor=true;
     for(int mes=intMesDesLoc; mes<=intMesHasLoc; mes++){
         intNumColTblSal++;
         if( mes==1)  vecCab.add(intNumColTblSal,"Enero"+anio+" ");
         if( mes==2)  vecCab.add(intNumColTblSal,"Febrero"+anio+" ");
         if( mes==3)  vecCab.add(intNumColTblSal,"Marzo"+anio+" ");
         if( mes==4)  vecCab.add(intNumColTblSal,"Abril"+anio+" ");
         if( mes==5)  vecCab.add(intNumColTblSal,"Mayo"+anio+" ");
         if( mes==6)  vecCab.add(intNumColTblSal,"Junio"+anio+"");
         if( mes==7)  vecCab.add(intNumColTblSal,"Julio"+anio+" ");
         if( mes==8)  vecCab.add(intNumColTblSal,"Agosto"+anio+" ");
         if( mes==9)  vecCab.add(intNumColTblSal,"Septiembre"+anio+" ");
         if( mes==10) vecCab.add(intNumColTblSal,"Octubre"+anio+" ");
         if( mes==11) vecCab.add(intNumColTblSal,"Noviembre"+anio+" ");
         if( mes==12) vecCab.add(intNumColTblSal,"Diciembre"+anio+"");

         if(blnEstBufAniSal) strBufAnioSal.append(" UNION ALL ");
         strBufAnioSal.append(" SELECT "+anio+" AS anio, "+mes+" AS mes, "
         + " date('"+anio+"-"+mes+"-01')-1 AS fecha_ini, date('"+anio+"-"+mes+"-01') AS fecha_act, date(date('"+anio+"-"+mes+"-01')+ interval '1 month')-1 as fecha_fin   ");
         blnEstBufAniSal=true;
         
      }
    }
    intNumColTblSal++;
    vecCab.add(intNumColTblSal," Total ");

    objTblModSal=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblModSal.setHeader(vecCab);
    tblDatSal.setModel(objTblModSal);
    tblDatSal.setRowSelectionAllowed(false);
    tblDatSal.setCellSelectionEnabled(true);
    tblDatSal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    tblDatSal.getTableHeader().setReorderingAllowed(false);

    new ZafTblPopMnu(tblDatSal);

    ArrayList arlColHid=new ArrayList();
    for(int i=1; i<(intNumColTblSal-intNumColTblVen)+1; i++)
        arlColHid.add(""+i);
    objTblModSal.setSystemHiddenColumns(arlColHid, tblDatSal);
    arlColHid=null;
    
    objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();//inincializo el renderizador
    objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
    objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
    objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

    javax.swing.table.TableColumnModel tcmAux=tblDatSal.getColumnModel();
    //Tamaño de las celdas
    tcmAux.getColumn(0).setPreferredWidth(100);
    for(int col=1; col<=intNumColTblSal; col++){
       tcmAux.getColumn(col).setPreferredWidth(100);
       objTblModSal.setColumnDataType(col, objTblModSal.INT_COL_DBL, new Integer(0), null);
       tcmAux.getColumn(col).setCellRenderer(objTblCelRenLbl);
    }
    tcmAux=null;

 }catch(Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
return blnRes;
}




String strFecDesBusVenCom="";
String strFecHasBusVenCom="";
String strFecDesBusSalVen="";
String strFecHasBusSalVen="";
int intAniDesSal=0;
int intMesDesSal=0;

private boolean confirgurarTblsCxC(boolean blnResAnio ){
    boolean blnRes=false;
    try{
     String fecdesde="2006-05-22",fechasta="2006-05-22", fecdesdeSal="2006-05-22";
     int intMesDes=0, intMesHas=0;
     int intAniDes=0, intAniHas=0;
     
        if(objSelDat.isCheckBoxChecked() ){
          switch (objSelDat.getTipoSeleccion())
          {
                    case 0: //Búsqueda por rangos
                        fecdesde = objUti.formatearFecha(objSelDat.getFechaDesde(),objSelDat.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos());
                        fechasta = objUti.formatearFecha(objSelDat.getFechaHasta(),objSelDat.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos());
                        intMesDes = objUti.getMes(fecdesde, "yyyy-MM-dd")+1;
                        intMesHas = objUti.getMes(fechasta, "yyyy-MM-dd")+1;
                        intAniDes = objUti.getAnio(fecdesde, "yyyy-MM-dd");
                        intAniHas = objUti.getAnio(fechasta, "yyyy-MM-dd");
                        fecdesdeSal=objUti.getAnio(fecdesde, "yyyy-MM-dd")-1+"-"+(objUti.getMes(fecdesde, "yyyy-MM-dd")+1)+"-01";
                        break;
                    case 1: //Fechas menor o igual que "Hasta".
                        fechasta =  objUti.formatearFecha(objSelDat.getFechaHasta(),"dd/MM/yyyy","yyyy-MM-dd");
                        fecdesde =  objUti.getAnio(fechasta, "yyyy-MM-dd")+"-01-01";
                        intMesDes = 1;
                        intMesHas = objUti.getMes(fechasta, "yyyy-MM-dd")+1;
                        intAniDes = objUti.getAnio(fechasta, "yyyy-MM-dd");
                        intAniHas = objUti.getAnio(fechasta, "yyyy-MM-dd");
                        fecdesdeSal=objUti.getAnio(fecdesde, "yyyy-MM-dd")-1+"-"+(objUti.getMes(fecdesde, "yyyy-MM-dd")+1)+"-01";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        fecdesde =  objUti.formatearFecha(objSelDat.getFechaDesde(),"dd/MM/yyyy","yyyy-MM-dd");
                        fechasta =  objUti.getAnio(fecdesde, "yyyy-MM-dd")+"-12-31";
                        intMesDes = objUti.getMes(fecdesde, "yyyy-MM-dd")+1;
                        intMesHas = 12;
                        intAniDes = objUti.getAnio(fecdesde, "yyyy-MM-dd");
                        intAniHas = objUti.getAnio(fecdesde, "yyyy-MM-dd");
                        fecdesdeSal=objUti.getAnio(fecdesde, "yyyy-MM-dd")-1+"-"+(objUti.getMes(fecdesde, "yyyy-MM-dd")+1)+"-01";
                        break;
                    case 3: //Todo.
                        fechasta =  objUti.formatearFecha(objSelDat.getFechaHasta(),"dd/MM/yyyy","yyyy-MM-dd");
                        fecdesde =  objUti.getAnio(fechasta, "yyyy-MM-dd")+"-01-01";
                        intAniDes = objUti.getAnio(fechasta, "yyyy-MM-dd");
                        intAniHas = objUti.getAnio(fechasta, "yyyy-MM-dd");
                        fechasta =  objUti.getAnio(fecdesde, "yyyy-MM-dd")+"-12-31";
                        intMesDes = 1;
                        intMesHas = 12;
                        fecdesdeSal=objUti.getAnio(fecdesde, "yyyy-MM-dd")-1+"-"+(objUti.getMes(fecdesde, "yyyy-MM-dd")+1)+"-01";
                        break;
        }}

     if(configurarTblVentas(intAniDes,  intAniHas,  intMesDes,  intMesHas)){
       if(configurarTblSaldos(intAniDes,  intAniHas,  intMesDes,  intMesHas, blnResAnio )){

         strFecDesBusVenCom = fecdesde;
         strFecHasBusVenCom = fechasta;

         intAniDesSal=intAniDes;
         intMesDesSal=intMesDes;
         strFecDesBusSalVen = fecdesdeSal;
         strFecHasBusSalVen = fechasta;

         blnRes=true;

     }}

  }catch (Exception e){  objUti.mostrarMsgErr_F1(this, e);    }
  return blnRes;
}




private String _getSqlFiltro(){
  String strFilSql="";
  String strFilAux="";
  int intCodLocBus=0;
  try{
    if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){

         intCodLocBus = Integer.parseInt(vecLoc.get(cboLocEmp.getSelectedIndex()).toString());
         if(intCodLocBus!=0)
            strFilAux = " AND a.co_loc="+intCodLocBus+" ";

          strFilSql= " AND (   a.co_emp="+objParSis.getCodigoEmpresa()+"  "+strFilAux+"  )  ";
     }
    if(chkEscComp.isSelected())  strFilSql += " AND  a.st_tipcta = 'E' ";

  }catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);    }
  return strFilSql;
}


private String _getStrFilSqlSalInc(java.sql.Connection conn){
  String strFilSql="";
  String strFilAux="";
  int intCodLocBus=0;
  int intCodEmpBus=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strCta="";
  String strTipDoc="";
  boolean blnEst=false;
  try{
     stmLoc=conn.createStatement();

     if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){

         intCodEmpBus = Integer.parseInt(vecLoc.get(cboLocEmp.getSelectedIndex()).toString());
         if(intCodEmpBus!=0)  strFilAux = "   co_emp = "+intCodEmpBus+" ";
         else strFilAux = " co_emp != "+objParSis.getCodigoEmpresaGrupo()+"  ";

         strFilSql= " AND ( ";
         strSql="SELECT co_emp FROM tbm_emp WHERE  "+strFilAux+"  ORDER BY co_emp ";
         rstLoc=stmLoc.executeQuery(strSql);
         while(rstLoc.next()){

             strCta = _getCtaEmpPrgUsr(conn, rstLoc.getInt("co_emp")  );

             strTipDoc ="  181, 182 "; // select co_tipdoc from tbm_cabtipdoc where co_emp="+rstLoc.getInt("co_emp")+" and  co_cat in ( 9,10,11,19,20 )  group by co_tipdoc ";

             if(blnEst) strFilSql +=" OR ";
             strFilSql += " (  a.co_emp="+rstLoc.getInt("co_emp")+" AND a.co_tipdoc IN( "+strTipDoc+" ) AND  a1.co_cta IN( "+strCta+" )   )  ";
             blnEst=true;
         }
         rstLoc.close();
         rstLoc=null;
         strFilSql += "  )  ";


     }else{
         intCodLocBus = Integer.parseInt(vecLoc.get(cboLocEmp.getSelectedIndex()).toString());
         if(intCodLocBus!=0)
            strFilAux = " AND a.co_loc="+intCodLocBus+" ";

          strCta = _getCtaEmpPrgUsr(conn, objParSis.getCodigoEmpresa() );

          strFilSql= " AND (  "
          + " ( a.co_emp="+objParSis.getCodigoEmpresa()+"  "+strFilAux+" AND a.co_tipdoc IN (  "
          + " 181, 182  "
          //+ "   select co_tipdoc from tbm_cabtipdoc where co_emp="+objParSis.getCodigoEmpresa()+" and  co_cat in ( 9,10,11,19,20 )  group by co_tipdoc "
          + "  ) AND a1.co_cta  IN( "+strCta+" )   )  ) ";
     }

     stmLoc.close();
     stmLoc=null;


  }catch(java.sql.SQLException e)  {  objUti.mostrarMsgErr_F1(this, e);  }
   catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);    }
  return strFilSql;
}



private boolean _cargarDatosSaldoVentas(String strFecDes, String strFecHas , boolean blnEstPres ){
  boolean blnRes=false;
  java.sql.Connection conn;
  try{
      conn=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos() );
      if(conn!=null){

          blnEstBufVen=false;
          strBufVentas=null;
          strBufVentas=new StringBuffer();
                  
          if(_cargarDatSalIniFin(conn , _getSqlFiltro() ) ){
           if(_cargarDatSaldoIncobrable(conn, _getStrFilSqlSalInc(conn) )){
             if( _cargarDatVentasSal(conn, strFecDes, strFecHas, strFilSqlVen(conn) ) ){
              if(_cargarTblSaldos(conn, blnEstPres )){

                  Ratios(blnEstPres);
                 
              }
          }}}

  }}catch(java.sql.SQLException e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}


private boolean Ratios(boolean blnEstPres){
  boolean blnRes=true;
  int intCon=2;
  try{
    for(int i=tblDatSal.getColumnCount()-2 ; i>0; i--){

        tblDatSal.setValueAt( ""+valor_Ratio(intCon) , 6, i );   //  ratio
        tblDatSal.setValueAt( "" , 5, i );  //  se limpia dias del mes
        if(!blnEstPres){
            tblDatSal.setValueAt( "" , 0, i );   //  se limpia saldo inicial
            tblDatSal.setValueAt( "" , 3, i );   // se limpia saldo incobrable
            tblDatSal.setValueAt( "" , 1, i );   // se limpia ventas
        }
        intCon++;
    }
    tblDatSal.setValueAt( "" , 6, 1 );  //  se limpia ratio
    tblDatSal.setValueAt( "" , 6, 2 );  //  se limpia ratio
 
    if(!blnEstPres){
      tblDatSal.setValueAt( "" , 0, tblDatSal.getColumnCount()-1 );  //  se limpia saldo inicial
      tblDatSal.setValueAt( "" , 3, tblDatSal.getColumnCount()-1 );   // se limpia saldo incobrable
      tblDatSal.setValueAt( "" , 1, tblDatSal.getColumnCount()-1 );   // se limpia ventas
    }
        

  }catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
 return blnRes;
}

private int valor_Ratio(int valor){
  double bldSalFin=0;
  double bldVenMes=0;
  double bldRes=0;
  int intDias=0;
  int intCon=0;
  int intDiaMes=0;
  try{
  for(int i=tblDatSal.getColumnCount()-valor ; i>0; i--){

     if(intCon==0) bldSalFin = Double.parseDouble(tblDatSal.getValueAt( 4, i).toString());  // saldo final

       intDiaMes = Integer.parseInt(tblDatSal.getValueAt( 5, i).toString());     // dias del mes 
       bldVenMes = Double.parseDouble(tblDatSal.getValueAt( 1, i).toString());   //  ventas
       bldRes = bldSalFin/bldVenMes;
       if(bldRes<0.95) {
          bldRes= bldRes*intDiaMes;
          intDias =  intDias + ((int) (Math.round(bldRes)));
          break;
        }else {
           bldSalFin=bldSalFin-bldVenMes;
           intDias=intDias+intDiaMes;
        }
        intCon=1;
     }

  }catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }
   return intDias;
 }





private boolean _cargarTblSaldos(java.sql.Connection conn, boolean blnEstPres ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  double dlbTotSalIni=0;
  double dlbTotVenSal=0, dlbTotSalFin=0, dlbTotSalCob=0, dlbTotSalInc=0;
  boolean blnEstSalIni=true;
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();

        Vector vecRegSalIni=new Vector();
        Vector vecRegSalInc=new Vector();
        Vector vecRegSalVen =new Vector();
        Vector vecRegSalFin=new Vector();
        Vector vecRegSalCob=new Vector();
        Vector vecRegEspcio =new Vector();
        Vector vecRegSalRat =new Vector();

       if(blnEstPres){
            vecRegSalIni.add("(+)Saldo Inicial ");
            vecRegSalVen.add("(+)Ventas");
            vecRegSalCob.add("(=)Cobros ");
            vecRegSalInc.add("(-)Saldo Incobrable");
            vecRegSalFin.add("(-)Saldo Final");
            vecRegEspcio.add(" ");
            vecRegSalRat.add("Ratios ");
         }else{
            vecRegSalIni.add(" ");
            vecRegSalInc.add(" ");
            vecRegSalVen.add(" ");
            vecRegSalFin.add("CxC");
            vecRegSalCob.add("Cobros ");
            vecRegEspcio.add(" ");
            vecRegSalRat.add("Ratios ");
         }
        
        /***************************/ 
        strSql="SELECT x.anio, x.mes, x1.val_salini, x4.val_SalInc,  x2.val_ven, x3.val_SalFin  , ( ( x1.val_salini  + x2.val_ven ) - ( x3.val_SalFin + x4.val_SalInc  ) ) as cobros "
        + " ,case when  fecha_fin > current_date  then  abs((fecha_ini-current_date)) else  abs(fecha_ini-fecha_fin)  end as dias  "
        + " FROM ( "+strBufAnioSal.toString()+" ) AS x "
        + " INNER JOIN ( "+strBufSalIni.toString()+" ) AS x1 ON ( x1.anio=x.anio and x1.mes=x.mes )"
        + " INNER JOIN ( "+strBufSalInc.toString()+" ) AS x4 ON ( x4.anio=x.anio and x4.mes=x.mes )"
        + " INNER JOIN ( "+strBufVentas.toString()+" ) AS x2 ON ( x2.anio=x.anio and x2.mes=x.mes )"
        + " INNER JOIN ( "+strBufSalFin.toString()+" ) AS x3 ON ( x3.anio=x.anio and x3.mes=x.mes ) ";
        System.out.println(" uno --> "+strBufVentas.toString() );
        //System.out.println("--> "+strSql);
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

           vecRegSalIni.add( new Double(rstLoc.getDouble("val_salini")));
           vecRegSalVen.add( new Double(rstLoc.getDouble("val_ven")));
           vecRegSalFin.add( new Double(rstLoc.getDouble("val_SalFin")));
           vecRegSalInc.add( new Double(rstLoc.getDouble("val_SalInc")));
           vecRegSalCob.add( new Double(rstLoc.getDouble("cobros")));
           vecRegEspcio.add( rstLoc.getString("dias") );
           vecRegSalRat.add("");

           if( rstLoc.getInt("anio") >= intAniDesSal ){
            if( rstLoc.getInt("mes") >= intMesDesSal ){

               if(blnEstSalIni) dlbTotSalIni =rstLoc.getDouble("val_salini");
               blnEstSalIni=false;
               dlbTotVenSal +=rstLoc.getDouble("val_ven");
               dlbTotSalFin =rstLoc.getDouble("val_SalFin");
               dlbTotSalCob +=rstLoc.getDouble("cobros");
               dlbTotSalInc += rstLoc.getDouble("val_SalInc");  // dlbTotSalInc = rstLoc.getDouble("val_SalInc");
           
           }}

        }

       vecRegSalIni.add( new Double(dlbTotSalIni));
       vecRegSalVen.add( new Double(dlbTotVenSal));
       vecRegSalFin.add( new Double(dlbTotSalFin));
       vecRegSalInc.add( new Double(dlbTotSalInc) );
       vecRegSalCob.add( new Double(dlbTotSalCob));
       vecRegEspcio.add("");
       vecRegSalRat.add("");

       java.util.Vector vecData = new java.util.Vector();
       vecData.add(vecRegSalIni);
       vecData.add(vecRegSalVen);
       vecData.add(vecRegSalCob);
       vecData.add(vecRegSalInc);
       vecData.add(vecRegSalFin);
       vecData.add(vecRegEspcio);
       vecData.add(vecRegSalRat);

       objTblModSal.setData(vecData);
       tblDatSal.setModel(objTblModSal);

        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;

  }}catch(java.sql.SQLException e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}



private boolean _cargarDatSalIniFin(java.sql.Connection conn, String strFilSql  ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  double dbValSalIni=0;
  double dbValSalFin=0;
  double dbValSalObtSql=0;
  boolean blnEstBusFec=false;
  String strFilSqlFec="";
  try{
     if(conn!=null){
      stmLoc=conn.createStatement();

     // String strSalExc="T";

         strSql="SELECT * FROM (  "+strBufAnioSal.toString()+ " ) AS x ";
         // System.out.println("_cargarDatSalIniFin --> "+ strSql );
         rstLoc=stmLoc.executeQuery(strSql);
         strSql="";
         while(rstLoc.next()){
             dbValSalIni=0;

             dbValSalIni = SaldoIni(conn, rstLoc.getString("anio"), rstLoc.getString("mes"), strFilSql );

            if( dbValSalIni != 0 ){

                  dbValSalFin = SaldoFin(conn, rstLoc.getString("anio"), rstLoc.getString("mes"), strFilSql );
                
            }else{

                 if(blnEstBusFec) strFilSqlFec =" AND a.fe_dia  between  '"+rstLoc.getString("fecha_act")+"' and '"+rstLoc.getString("fecha_fin")+"' ";
                 else strFilSqlFec = " AND a.fe_dia <= '" + rstLoc.getString("fecha_fin") + "' ";
                 blnEstBusFec=true;

                  dbValSalObtSql = SaldoIniFinal(conn, rstLoc.getString("fecha_act"), rstLoc.getString("fecha_fin"), strFilSqlFec );

                  dbValSalIni=dbValSalFin;
                  dbValSalFin+=dbValSalObtSql;

//                  strSqlIns+=" ; insert into tbm_salctacli "+
//                 " select '"+strSalExc+"', "+dblSalFin+", "+(dblSalFin+dblVal)+", "+objParSis.getCodigoEmpresa()+" , "+vecLoc.elementAt(local.getSelectedIndex()).toString()+", '"+rstLoc.getString("anio")+rstLoc.getString("mes")+"' , "+rstLoc.getString("anio")+", "+rstLoc.getString("mes");
               
          }

            if(blnEstBufSalIni) strBufSalIni.append(" UNION ALL ");
            strBufSalIni.append("SELECT "+rstLoc.getString("anio")+" AS anio ,"+rstLoc.getString("mes")+" AS MES ,"+dbValSalIni+" AS val_SalIni ");
            blnEstBufSalIni=true;

            if(blnEstBufSalFin) strBufSalFin.append(" UNION ALL ");
            strBufSalFin.append("SELECT "+rstLoc.getString("anio")+" AS anio ,"+rstLoc.getString("mes")+" AS MES ,"+dbValSalFin+" AS val_SalFin ");
            blnEstBufSalFin=true;
         }
         rstLoc.close();
         rstLoc=null;

         //System.out.println("----> "+ strSqlIns );
        // stmLoc.executeUpdate(strSql);

     stmLoc.close();
     stmLoc=null;

  }}catch(java.sql.SQLException e)  { blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
    catch (Exception e){  blnRes=false; objUti.mostrarMsgErr_F1(this, e);    }
  return blnRes;
}


private double SaldoIniFinal(java.sql.Connection conn, String strFecDes, String strFecHas, String strFilSqlFec ){
 double dblValor=0;
  String strFilAux="";
  int intCodLocBus=0;
  int intCodEmpBus=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strCta="";
  String strTipDoc="";
  boolean blnEst=false;
  String strFilSqlEmpCtaSolDia="";
  String strFilSqlEmpCtaSolCabMov="";
  String strFilSqlEmpCtaSolRet="";
  String strFilSqlEmpCtaSinRet="";
 try{
     stmLoc=conn.createStatement();
     
     if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){

         strFilSqlEmpCtaSolDia= " AND ( ";
         strFilSqlEmpCtaSolCabMov= " AND ( ";
         strFilSqlEmpCtaSolRet= " AND ( ";
         strFilSqlEmpCtaSinRet= " AND ( ";

         intCodEmpBus = Integer.parseInt(vecLoc.get(cboLocEmp.getSelectedIndex()).toString());
         if(intCodEmpBus!=0)  strFilAux = "   co_emp = "+intCodEmpBus+" ";
         else strFilAux = " co_emp != "+objParSis.getCodigoEmpresaGrupo()+"  ";

         strSql="SELECT co_emp FROM tbm_emp WHERE "+strFilAux+" ORDER BY co_emp ";
         rstLoc=stmLoc.executeQuery(strSql);
         while(rstLoc.next()){

             strCta = _getCtaEmpPrgUsr(conn, rstLoc.getInt("co_emp") );

             strTipDoc =" select co_tipdoc from tbm_cabtipdoc where co_emp="+rstLoc.getInt("co_emp")+" and  co_cat in ( 12 )  group by co_tipdoc ";

             if(blnEst) {
                 strFilSqlEmpCtaSolDia +=" OR ";
                 strFilSqlEmpCtaSolCabMov +=" OR ";
                 strFilSqlEmpCtaSolRet +=" OR ";
                 strFilSqlEmpCtaSinRet +=" OR ";
             }
             strFilSqlEmpCtaSolDia += " (  a.co_emp="+rstLoc.getInt("co_emp")+" AND  b.co_cta IN( "+strCta+" )   )  ";
             strFilSqlEmpCtaSolCabMov += " (  a.co_emp="+rstLoc.getInt("co_emp")+" AND  b.co_cta IN( "+strCta+" )   )  ";
             strFilSqlEmpCtaSolRet += " (  a.co_emp="+rstLoc.getInt("co_emp")+" AND a.co_tipdoc IN( "+strTipDoc+" ) AND  b.co_cta IN( "+strCta+" )   )  ";
             strFilSqlEmpCtaSinRet += " (  a.co_emp="+rstLoc.getInt("co_emp")+" AND a.co_tipdoc IN( "+strTipDoc+" ) AND  b.co_cta NOT IN( "+strCta+" )   )  ";
             blnEst=true;
         }
         rstLoc.close();
         rstLoc=null;
         strFilSqlEmpCtaSolDia += "  )  ";
         strFilSqlEmpCtaSolCabMov += "  )  ";
         strFilSqlEmpCtaSolRet += "  )  ";
         strFilSqlEmpCtaSinRet += "  )  ";

     }else{
         intCodLocBus = Integer.parseInt(vecLoc.get(cboLocEmp.getSelectedIndex()).toString());
         if(intCodLocBus!=0)
            strFilAux = " AND a.co_loc="+intCodLocBus+" ";


           strTipDoc ="  select co_tipdoc from tbm_cabtipdoc where co_emp="+objParSis.getCodigoEmpresa()+" and  co_cat in ( 12 )  group by co_tipdoc ";

           strCta = _getCtaEmpPrgUsr(conn, objParSis.getCodigoEmpresa() );

          strFilSqlEmpCtaSolDia= " AND ( ( a.co_emp="+objParSis.getCodigoEmpresa()+"  "+strFilAux+"  AND b.co_cta  IN( "+strCta+" ) ) ) ";
          strFilSqlEmpCtaSolCabMov= " AND ( ( a.co_emp="+objParSis.getCodigoEmpresa()+"  "+strFilAux+"  AND b.co_cta  IN( "+strCta+" ) ) ) ";
          strFilSqlEmpCtaSolRet= " AND ( ( a.co_emp="+objParSis.getCodigoEmpresa()+"  "+strFilAux+"  AND b.co_cta  IN( "+strCta+" )  AND a.co_tipdoc IN ("+strTipDoc+")  ) ) ";
          strFilSqlEmpCtaSinRet= " AND ( ( a.co_emp="+objParSis.getCodigoEmpresa()+"  "+strFilAux+"  AND b.co_cta  IN( "+strCta+" )  AND a.co_tipdoc NOT IN ("+strTipDoc+")  ) ) ";

     }
     
     if(chkEscComp.isSelected()){
         strFilSqlEmpCtaSolCabMov += " AND  a1.co_empgrp is null ";
         strFilSqlEmpCtaSolRet += " AND  a1.co_empgrp is null ";
         strFilSqlEmpCtaSinRet += " AND  a1.co_empgrp is null ";
     }

    // dblValor= SaldoIniFinEmp(conn,  strFecDes,  strFecHas, strFilSqlEmpCtaSolDia, strFilSqlEmpCtaSolCabMov, strFilSqlEmpCtaSolRet, strFilSqlEmpCtaSinRet ,strFilSqlFec  );
     dblValor= SaldoIniFinEmp(conn,  strFilSqlEmpCtaSolDia, strFilSqlFec  );

 }catch(java.sql.SQLException e)  {  objUti.mostrarMsgErr_F1(this, e);  }
   catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);    }
 return dblValor;
}



private double SaldoIniFinEmp(java.sql.Connection conn, String strFilSqlEmpCtaSolDia, String strSqlAuxFec  ){
  String sqlQuerySaldo="";
  double dblValor=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  try{
   if(conn!=null){
      stmLoc=conn.createStatement();

   sqlQuerySaldo="SELECT sum( (b.nd_mondeb - b.nd_monhab) ) as saldo   FROM tbm_cabdia as a   "
   + " INNER JOIN tbm_detdia as b ON(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia)  "
   + " WHERE a.st_reg not in ('E','I') "
   + " "+strFilSqlEmpCtaSolDia+"  "+strSqlAuxFec+"";  
   //System.out.println("sqlQuerySaldo -> "+sqlQuerySaldo);
   rstLoc=stmLoc.executeQuery(sqlQuerySaldo);
   while(rstLoc.next()){
       dblValor=rstLoc.getDouble("saldo");
   }
   rstLoc.close();
   rstLoc=null;
   stmLoc.close();
   stmLoc=null;

  }}catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
   catch (Exception e){   objUti.mostrarMsgErr_F1(this, e);  }
 return dblValor;
}



private double SaldoIniFinEmp_anterior(java.sql.Connection conn, String strFecDes, String strFecHas, String strFilSqlEmpCtaSolDia, String strFilSqlEmpCtaSolCabMov, String strFilSqlEmpCtaSolRet, String strFilSqlEmpCtaSinRet  ){
  String sqlQuerySaldo="";
  double dblValor=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  try{
   if(conn!=null){
      stmLoc=conn.createStatement();

   sqlQuerySaldo="SELECT sum(saldo) as saldo FROM ( " +
   "  /*******************SOLO AFECTE HA DIARIO Y NO AFECTA TABLA   tbm_cabmovinv   tbm_cabpag *********************************/ "
   + "  SELECT (sum(nd_mondeb - nd_monhab) ) as saldo FROM (  "
   + "  select co_emp, co_loc, co_tipdoc, co_dia, co_reg  from ( "
   + "   SELECT * FROM( "
   + "    select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.co_doc as a1, detpag.co_doc as a2 "
   + "    FROM tbm_cabdia as a "
   + "    INNER JOIN tbm_detdia as b ON(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "
   + "    LEFT JOIN tbm_cabmovinv as cabmov on (cabmov.co_emp=a.co_emp and cabmov.co_loc=a.co_loc and cabmov.co_tipdoc=a.co_tipdoc and cabmov.co_doc=a.co_dia  ) "
   + "    LEFT JOIN tbm_cabpag as detpag on (detpag.co_emp=a.co_emp and detpag.co_loc=a.co_loc and detpag.co_tipdoc=a.co_tipdoc and detpag.co_doc=a.co_dia ) "
   + "      WHERE a.st_reg not in ('E','I')  "
   + "  "+strFilSqlEmpCtaSolDia+" "      //AND a.co_emp=1 and b.co_cta in ( 29, 30, 31, 2690, 2691 ) "
   + "      AND a.fe_dia  between  '"+strFecDes+"' and '"+strFecHas+"' "
   + "   ) AS x WHERE a1 is null and a2 is null "
   + "   ) as x "
   + "   GROUP BY co_emp, co_loc, co_tipdoc, co_dia, co_reg "
   + "  ) AS a "
   + "  INNER JOIN tbm_detdia AS b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "
   + " /****************************************************/ "
   + "  UNION ALL  "
   +"  /*******************SOLO CABMOVINV*********************************/ "
   +"  select (sum(nd_mondeb - nd_monhab) ) as saldo from ( "
   +"  select co_emp, co_loc, co_tipdoc, co_dia, co_reg from ( "
   +"   select * from ( "
   +"    select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.tx_nomcli as tx_nomcli1, "
   +"    cabmov.co_cli as cocli1, a1.co_empgrp "
   +"     from tbm_cabdia as a "
   +"        inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "
   +"        inner join tbm_cabmovinv as cabmov on (cabmov.co_emp=a.co_emp and cabmov.co_loc=a.co_loc and cabmov.co_tipdoc=a.co_tipdoc and cabmov.co_doc=a.co_dia  ) "
   +"        INNER JOIN tbm_cli AS a1 ON (a1.co_emp=cabmov.co_emp and a1.co_cli=cabmov.co_cli ) "
   +"        where a.st_reg not in ('E','I') "
   +"    "+strFilSqlEmpCtaSolCabMov+"  "   //  AND a.co_emp=1 and b.co_cta in ( 29, 30, 31, 2690, 2691 )  and a1.co_empgrp is null "
   +"       and a.fe_dia   between  '"+strFecDes+"' and '"+strFecHas+"'   "
   +"   ) as x "
   +"   ) as x "
   +"   group by co_emp, co_loc, co_tipdoc, co_dia, co_reg "
   +"   ) as a "
   +"   inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "
   + " /****************************************************/ "
   + " UNION ALL  "
   +"  /*******************SOLO RETE  *********************************/ "
   +"   select sum(saldo) from ( "
   +"   select "
   +"   case when  abs(saldo) in (abs(abono)) then saldo else (saldo + abs(abono)) end as saldo "
   +"   from ( "
   +"   select co_dia, (sum(mondeb) - sum(monhab))  as saldo, abono from ( "
   +"   select  b.nd_mondeb as  mondeb,  b.nd_monhab as  monhab  , abono , a.co_dia "
   +"   from ( "
   +"   select co_emp, co_loc, co_tipdoc, co_dia, co_reg, sum(nd_abo) as abono  from ( "
   +"   select * "
   +"   from ( "
   +"     select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.tx_nomcli as tx_nomcli1, "
   +"   cabmov.co_cli as cocli1, detpag.nd_abo , a1.co_empgrp "
   +"     from tbm_cabdia as a "
   +"    inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "
   +"    inner join tbm_cabpag as cabpag on (cabpag.co_emp=a.co_emp and cabpag.co_loc=a.co_loc and cabpag.co_tipdoc=a.co_tipdoc and cabpag.co_doc=a.co_dia ) "
   +"    left join tbm_detpag as detpag on (detpag.co_emp=cabpag.co_emp and detpag.co_loc=cabpag.co_loc and detpag.co_tipdoc=cabpag.co_tipdoc and detpag.co_doc=cabpag.co_doc ) "
   +"    left join tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag ) "
   +"    left join tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc) "
   +"   LEFT JOIN tbm_cli AS a1 ON (a1.co_emp=cabmov.co_emp and a1.co_cli=cabmov.co_cli ) "
   +"    where  a.st_reg not in ('E','I') "
   +"     "+strFilSqlEmpCtaSolRet+" "   
   +"    and a.fe_dia   between '"+strFecDes+"' and '"+strFecHas+"'  "
   +"   ) as x "
   +"   ) as x "
   +"   group by co_emp, co_loc, co_tipdoc, co_dia, co_reg "
   +"   ) as a "
   +"   inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "
   +"  order by co_dia "
   +"   ) as x   group by co_dia, abono "
   +"   ) as x "
   +"   ) as x   "
   + " /****************************************************/ "
   +"  UNION ALL "
   +"  /*******************SIN RETE *********************************/ "
   +"    select    (sum(mondeb - monhab) ) as saldo from ( "
   +"   select  a.co_tipdoc, a.co_dia, "
   +"   case when a.co_cat in (20) then "
   +"    case when a.abono < 0 then "
   +"     case when b.nd_mondeb in (0) then abs(a.abono) else  case when  b.nd_mondeb in (abs(a.abono)) then b.nd_mondeb else b.nd_mondeb+abs((b.nd_mondeb-abs(a.abono))) end end "
   +"    else 0 end "
   +"   else  b.nd_mondeb end as  mondeb , "
   +"  "
   +"   case when a.co_cat in (20) then "
   +"    case when a.abono > 0 then "
   +"     case when b.nd_monhab in (0) then abs(a.abono) else  case when b.nd_monhab in (abs(a.abono)) then b.nd_monhab else b.nd_monhab+abs((b.nd_monhab-abs(a.abono))) end end "
   +"    else 0 end "
   +"   else  b.nd_monhab end as  monhab "
   +"  "
   +"   from ( "
   +"   select co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cat , sum(nd_abo) as abono  from ( "
   +"   select * "
   +"   from ( "
   +"    select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.tx_nomcli as tx_nomcli1, "
   +"    cabmov.co_cli as cocli1, detpag.nd_abo , a1.co_empgrp, b1.co_cat "
   +"     from tbm_cabdia as a "
   +"       inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "
   +"        inner join tbm_cabtipdoc as b1 on(b1.co_emp=b.co_emp and b1.co_loc=b.co_loc and b1.co_tipdoc=b.co_tipdoc  ) "
   +"        inner join tbm_cabpag as cabpag on (cabpag.co_emp=a.co_emp and cabpag.co_loc=a.co_loc and cabpag.co_tipdoc=a.co_tipdoc and cabpag.co_doc=a.co_dia ) "
   +"        left join tbm_detpag as detpag on (detpag.co_emp=cabpag.co_emp and detpag.co_loc=cabpag.co_loc and detpag.co_tipdoc=cabpag.co_tipdoc and detpag.co_doc=cabpag.co_doc ) "
   +"        left join tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag ) "
   +"        left join tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc) "
   +"        LEFT JOIN tbm_cli AS a1 ON (a1.co_emp=cabmov.co_emp and a1.co_cli=cabmov.co_cli ) "
   +"        where  a.st_reg not in ('E','I') "
   +"        "+strFilSqlEmpCtaSinRet+" "
   +"        and a.fe_dia  between  '"+strFecDes+"' and '"+strFecHas+"' "
   +"   ) as x "
   +"   ) as x "
   +"   group by co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cat "
   +"    ) as a "
   +"   inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "
   +"  ) as x "

   +"  /****************************************************/ "+
   "  ) AS X ";
   //System.out.println("sqlQuerySaldo -> "+sqlQuerySaldo);
   rstLoc=stmLoc.executeQuery(sqlQuerySaldo);
   while(rstLoc.next()){
       dblValor=rstLoc.getDouble("saldo");
   }
   rstLoc.close();
   rstLoc=null;
   stmLoc.close();
   stmLoc=null;

  }}catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
   catch (Exception e){   objUti.mostrarMsgErr_F1(this, e);  }
 return dblValor;
}




// Esto solo retorna 0 pues solo llega hasta el año 2008 // José Mario Marin 8/Jul/2015

private double SaldoIni(java.sql.Connection conn, String strAnio, String strMes, String strFilSql ){
  String sqlQuerySaldo="";
  double dblValor=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  try{
//   if(conn!=null){//José Mario Marin 8/Jul/2015
//      stmLoc=conn.createStatement();
//
//   sqlQuerySaldo="SELECT CASE WHEN saldo IS NULL THEN 0 ELSE saldo END AS saldo \nFROM (\n "
//   + "     SELECT  sum(a.nd_salini) AS saldo \n     FROM tbm_salctacli AS a "+
//   "  \n     WHERE a.anio="+strAnio+"  AND a.mes="+strMes+" "+strFilSql+"  \n) AS x  ";
//   System.out.println("SaldoIni :\n"+ sqlQuerySaldo );
//   // esto solo trae 0 por el año slo llega hasta el 2008  // José Mario Marin 8/Jul/2015
//   rstLoc=stmLoc.executeQuery(sqlQuerySaldo);
//   while(rstLoc.next()){
//       dblValor=rstLoc.getDouble("saldo");
//   }
//   rstLoc.close();//José Mario Marin 8/Jul/2015
//   rstLoc=null;
//   stmLoc.close();
//   stmLoc=null;
      dblValor=0.0;
  }
//  }catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }//José Mario Marin 8/Jul/2015
   catch (Exception e){   objUti.mostrarMsgErr_F1(this, e);  }
 return dblValor;
}


// Esto solo retorna 0 pues solo llega hasta el año 2008 // José Mario Marin 8/Jul/2015

private double SaldoFin(java.sql.Connection conn, String strAnio, String strMes, String strFilSql ){
  String sqlQuerySaldo="";
  double dblValor=0;
//  java.sql.Statement stmLoc;
//  java.sql.ResultSet rstLoc;//José Mario Marin 8/Jul/2015
  try{
//   if(conn!=null){
//      stmLoc=conn.createStatement();
//
//   sqlQuerySaldo="SELECT CASE WHEN saldo IS NULL THEN 0 ELSE saldo END AS saldo \nFROM (\n"
//   + "     SELECT sum(a.nd_salfin) as saldo \n     FROM tbm_salctacli AS a "+
//   "   \n     WHERE  a.anio="+strAnio+"  AND  a.mes="+strMes+" "+strFilSql+"  \n) AS x   ";
//   System.out.println("SaldoFin \n" + sqlQuerySaldo);
   // Esto solo retorna 0 pues solo llega hasta el año 2008 // José Mario Marin 8/Jul/2015
//   rstLoc=stmLoc.executeQuery(sqlQuerySaldo);
//   while(rstLoc.next()){
//       dblValor=rstLoc.getDouble("saldo");//José Mario Marin 8/Jul/2015
//   }
//   rstLoc.close();
//   rstLoc=null;
//   stmLoc.close();
//   stmLoc=null;
      dblValor=0.00;
  }
//}catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }  //José Mario Marin 8/Jul/2015
   catch (Exception e){   objUti.mostrarMsgErr_F1(this, e);  }
 return dblValor;
}









private String strFilSqlVen(java.sql.Connection conn){
  String strFilSql="";
  String strFilAux="";
  int intCodLocBus=0;
  int intCodEmpBus=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="", strTipDoc="";
  boolean blnEst=false;
  try{
      stmLoc=conn.createStatement();


     if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){

         intCodEmpBus = Integer.parseInt(vecLoc.get(cboLocEmp.getSelectedIndex()).toString());
         if(intCodEmpBus!=0)  strFilAux = "   co_emp = "+intCodEmpBus+" ";
         else strFilAux = " co_emp != "+objParSis.getCodigoEmpresaGrupo()+"  ";

         strFilSql= " AND ( ";
         strSql="SELECT co_emp FROM tbm_emp WHERE "+strFilAux+"  ORDER BY co_emp ";
         rstLoc=stmLoc.executeQuery(strSql);
         while(rstLoc.next()){

             strTipDoc =" select co_tipdoc from tbm_cabtipdoc where co_emp="+rstLoc.getInt("co_emp")+" and  co_cat in (  3, 4, 8 , 9  )  group by co_tipdoc ";

             if(blnEst) strFilSql +=" OR ";
             strFilSql += " (  a.co_emp="+rstLoc.getInt("co_emp")+" AND a.co_tipdoc in ( "+strTipDoc+" ) )  ";
             blnEst=true;
         }
         rstLoc.close();
         rstLoc=null;
         strFilSql += "  )  ";
                   
     }else{

         strTipDoc =" select co_tipdoc from tbm_cabtipdoc where co_emp="+objParSis.getCodigoEmpresa()+" and  co_cat in (  3, 4, 8 , 9  )  group by co_tipdoc ";

         intCodLocBus = Integer.parseInt(vecLoc.get(cboLocEmp.getSelectedIndex()).toString());
         if(intCodLocBus!=0)
            strFilAux = " AND a.co_loc="+intCodLocBus+" ";

          strFilSql= " AND (  (  a.co_emp="+objParSis.getCodigoEmpresa()+"  "+strFilAux+" AND a.co_tipdoc in ( "+strTipDoc+" ) )  )  ";
     }

     if(chkEscComp.isSelected())  strFilSql += " AND  a1.co_empgrp is null ";

     stmLoc.close();
     stmLoc=null;

  }catch(java.sql.SQLException e)  {  objUti.mostrarMsgErr_F1(this, e);  }
   catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);    }
  return strFilSql;
}

private String strFilSqlCbEfe(java.sql.Connection conn){
  String strFilSql="";
  String strFilAux="";
  int intCodLocBus=0;
  int intCodEmpBus=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strCta="";
  String strTipDoc="";
  boolean blnEst=false;
  try{
     stmLoc=conn.createStatement();
     
     if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){

         intCodEmpBus = Integer.parseInt(vecLoc.get(cboLocEmp.getSelectedIndex()).toString());
         if(intCodEmpBus!=0)  strFilAux = "   co_emp = "+intCodEmpBus+" ";
         else strFilAux = " co_emp != "+objParSis.getCodigoEmpresaGrupo()+"  ";

         strFilSql= " AND ( ";
         strSql="SELECT co_emp FROM tbm_emp WHERE  "+strFilAux+"  ORDER BY co_emp ";
         rstLoc=stmLoc.executeQuery(strSql);
         while(rstLoc.next()){
             
             strCta = _getCtaEmpPrgUsr(conn, rstLoc.getInt("co_emp")  );

             strTipDoc =" select co_tipdoc from tbm_cabtipdoc where co_emp="+rstLoc.getInt("co_emp")+" and  co_cat in ( 9,10,11,19,20 )  group by co_tipdoc ";

             if(blnEst) strFilSql +=" OR ";
             strFilSql += " (  d1.co_emp="+rstLoc.getInt("co_emp")+" AND d1.co_tipdoc IN( "+strTipDoc+" ) AND  d2.co_cta IN( "+strCta+" )   )  ";
             blnEst=true;
         }
         rstLoc.close();
         rstLoc=null;
         strFilSql += "  )  ";


     }else{
         intCodLocBus = Integer.parseInt(vecLoc.get(cboLocEmp.getSelectedIndex()).toString());
         if(intCodLocBus!=0)
            strFilAux = " AND d1.co_loc="+intCodLocBus+" ";

          strCta = _getCtaEmpPrgUsr(conn, objParSis.getCodigoEmpresa() );

          strFilSql= " AND (  "
          + " ( d1.co_emp="+objParSis.getCodigoEmpresa()+"  "+strFilAux+" AND d1.co_tipdoc IN (  "
          + "   select co_tipdoc from tbm_cabtipdoc where co_emp="+objParSis.getCodigoEmpresa()+" and  co_cat in ( 9,10,11,19,20 )  group by co_tipdoc "
          + "  ) AND d2.co_cta  IN( "+strCta+" )   )  ) ";
     }

     if(chkEscComp.isSelected())  strFilSql += " AND  cli.co_empgrp is null ";
     
     stmLoc.close();
     stmLoc=null;
   

  }catch(java.sql.SQLException e)  {  objUti.mostrarMsgErr_F1(this, e);  }
   catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);    }
  return strFilSql;
}


/**
 * Funcion que permite obtener las cuentas contables configurable por empresa.
 * @param conn
 * @param intCodEmp
 * @return  
 */
private String _getCtaEmpPrgUsr(java.sql.Connection conn, int intCodEmp ){
  String strCtaEmp="";
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strExcCompFilSql="";
  boolean blnEstCta=false;
  try{
   if(conn!=null){
     stmLoc=conn.createStatement();

     if(chkEscComp.isSelected())
       strExcCompFilSql = " WHERE co_ctaven is null  ";

     //strSql="SELECT co_cta FROM tbr_ctalocprgusr WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_mnu="+objParSis.getCodigoMenu()+" AND co_usr="+objParSis.getCodigoUsuario()+" GROUP BY co_cta ";
     
     strSql="SELECT * \nFROM ( "
     + " \nSELECT co_emp, co_cta \nFROM tbr_ctalocprgusr \nWHERE co_emp="+intCodEmp+" AND co_mnu="+objParSis.getCodigoMenu()+" AND co_usr="+objParSis.getCodigoUsuario()+" GROUP BY co_emp, co_cta "
     + " \n) AS x \nLEFT JOIN ( "
     + "  \nSELECT co_emp , co_ctaven  \nFROM tbm_cli \nWHERE co_emp="+intCodEmp+" and co_empgrp=0  and co_ctaven is not null "
     + " \n) AS x1  ON (x1.co_emp=x.co_emp and x1.co_ctaven =  x.co_cta ) "+strExcCompFilSql+" ";
     System.out.println("_getCtaEmpPrgUsr \n" + strSql);
     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){
             if(blnEstCta) strCtaEmp +="," ;
             strCtaEmp +=  rstLoc.getString("co_cta");
             blnEstCta=true;
     }
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;

 }}catch(java.sql.SQLException e)  {  objUti.mostrarMsgErr_F1(this, e);  }
   catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);    }
  return strCtaEmp;
}


/**
 * Funcion que permite obtener las cuentas contables configurable por empresa.
 * @param conn
 * @param intCodEmp
 * @return
 */
private String _getCtaEmpPrgUsrCxP(java.sql.Connection conn, int intCodEmp ){
  String strCtaEmp="";
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strExcCompFilSql="";
  boolean blnEstCta=false;
  try{
   if(conn!=null){
     stmLoc=conn.createStatement();

     if(chkEscComp.isSelected())
       strExcCompFilSql = " WHERE co_ctacom is null  ";

     strSql="SELECT * FROM ( "
     + " SELECT co_emp, co_cta FROM tbr_ctalocprgusr WHERE co_emp="+intCodEmp+" AND co_mnu="+objParSis.getCodigoMenu()+" AND co_usr="+objParSis.getCodigoUsuario()+" GROUP BY co_emp, co_cta "
     + " ) AS x LEFT JOIN ( "
     + "  SELECT co_emp , co_ctacom  FROM tbm_cli WHERE co_emp="+intCodEmp+" and co_empgrp=0  and co_ctacom is not null "
     + " ) AS x1  ON (x1.co_emp=x.co_emp and x1.co_ctacom =  x.co_cta ) "+strExcCompFilSql+" ";
     System.out.println("_getCtaEmpPrgUsrCxP " + strSql);
     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){
             if(blnEstCta) strCtaEmp +=",";
             strCtaEmp +=  rstLoc.getString("co_cta");
             blnEstCta=true;
     }
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;

 }}catch(java.sql.SQLException e)  {  objUti.mostrarMsgErr_F1(this, e);  }
   catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);    }
  return strCtaEmp;
}


private String strFilSqlCbRet(java.sql.Connection conn){
  String strFilSql="";
  String strFilAux="";
  int intCodLocBus=0;
  int intCodEmpBus=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strCta="";
  String strTipDoc="";
  boolean blnEst=false;
  try{
     stmLoc=conn.createStatement();
    
     if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){

         intCodEmpBus = Integer.parseInt(vecLoc.get(cboLocEmp.getSelectedIndex()).toString());
         if(intCodEmpBus!=0)  strFilAux = "   co_emp = "+intCodEmpBus+" ";
         else strFilAux = " co_emp != "+objParSis.getCodigoEmpresaGrupo()+"  ";

         strFilSql= " AND ( ";
         strSql="SELECT co_emp FROM tbm_emp WHERE "+strFilAux+" ORDER BY co_emp ";
         rstLoc=stmLoc.executeQuery(strSql);
         while(rstLoc.next()){
            
             strCta = _getCtaEmpPrgUsr(conn, rstLoc.getInt("co_emp")  );

             strTipDoc =" select co_tipdoc from tbm_cabtipdoc where co_emp="+rstLoc.getInt("co_emp")+" and  co_cat in ( 12 )  group by co_tipdoc ";

             if(blnEst) strFilSql +=" OR ";
             strFilSql += " (  d1.co_emp="+rstLoc.getInt("co_emp")+" AND d1.co_tipdoc IN( "+strTipDoc+" ) AND  d2.co_cta IN( "+strCta+" )   )  ";
             blnEst=true;
         }
         rstLoc.close();
         rstLoc=null;
         strFilSql += "  )  ";
       
     }else{
         intCodLocBus = Integer.parseInt(vecLoc.get(cboLocEmp.getSelectedIndex()).toString());
         if(intCodLocBus!=0)
            strFilAux = " AND d1.co_loc="+intCodLocBus+" ";

          strCta = _getCtaEmpPrgUsr(conn, objParSis.getCodigoEmpresa()  );

          strFilSql= " AND (  "
          + " ( d1.co_emp="+objParSis.getCodigoEmpresa()+"  "+strFilAux+" AND d1.co_tipdoc IN (  "
          + "   select co_tipdoc from tbm_cabtipdoc where co_emp="+objParSis.getCodigoEmpresa()+" and  co_cat in ( 12 )  group by co_tipdoc "
          + "  ) AND d2.co_cta  IN( "+strCta+" )   )  ) ";
     }

     if(chkEscComp.isSelected())  strFilSql += " AND  cli.co_empgrp is null ";

     stmLoc.close();
     stmLoc=null;
   

  }catch(java.sql.SQLException e)  {  objUti.mostrarMsgErr_F1(this, e);  }
   catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);    }
  return strFilSql;
}

private String strFilSqlCom(java.sql.Connection conn){
  String strFilSql="";
  String strFilAux="";
  int intCodLocBus=0;
  int intCodEmpBus=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="", strTipDoc="";
  boolean blnEst=false;
  try{
      stmLoc=conn.createStatement();


     if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){

         intCodEmpBus = Integer.parseInt(vecLoc.get(cboLocEmp.getSelectedIndex()).toString());
         if(intCodEmpBus!=0)  strFilAux = "   co_emp = "+intCodEmpBus+" ";
         else strFilAux = " co_emp != "+objParSis.getCodigoEmpresaGrupo()+"  ";

         strFilSql= " AND ( ";
         strSql="SELECT co_emp FROM tbm_emp WHERE "+strFilAux+"  ORDER BY co_emp ";
         rstLoc=stmLoc.executeQuery(strSql);
         while(rstLoc.next()){

             strTipDoc ="2,4 "; // select co_tipdoc from tbm_cabtipdoc where co_emp="+rstLoc.getInt("co_emp")+" and  co_cat in (  3, 4, 8 , 9  )  group by co_tipdoc ";

             if(blnEst) strFilSql +=" OR ";
             strFilSql += " (  a.co_emp="+rstLoc.getInt("co_emp")+" AND a.co_tipdoc in ( "+strTipDoc+" ) )  ";
             blnEst=true;
         }
         rstLoc.close();
         rstLoc=null;
         strFilSql += "  )  ";

     }else{

         strTipDoc =" 2,4 "; // select co_tipdoc from tbm_cabtipdoc where co_emp="+objParSis.getCodigoEmpresa()+" and  co_cat in (  3, 4, 8 , 9  )  group by co_tipdoc ";

         intCodLocBus = Integer.parseInt(vecLoc.get(cboLocEmp.getSelectedIndex()).toString());
         if(intCodLocBus!=0)
            strFilAux = " AND a.co_loc="+intCodLocBus+" ";

          strFilSql= " AND (  (  a.co_emp="+objParSis.getCodigoEmpresa()+"  "+strFilAux+" AND a.co_tipdoc in ( "+strTipDoc+" ) )  )  ";
     }

     if(chkEscComp.isSelected())  strFilSql += " AND  a1.co_empgrp is null ";

     stmLoc.close();
     stmLoc=null;

  }catch(java.sql.SQLException e)  {  objUti.mostrarMsgErr_F1(this, e);  }
   catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);    }
  return strFilSql;
}


private String strFilSqlPagChq(java.sql.Connection conn){
  String strFilSql="";
  String strFilAux="";
  int intCodLocBus=0;
  int intCodEmpBus=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strCta="";
  String strTipDoc="";
  boolean blnEst=false;
  try{
     stmLoc=conn.createStatement();

     if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){

         intCodEmpBus = Integer.parseInt(vecLoc.get(cboLocEmp.getSelectedIndex()).toString());
         if(intCodEmpBus!=0)  strFilAux = "   co_emp = "+intCodEmpBus+" ";
         else strFilAux = " co_emp != "+objParSis.getCodigoEmpresaGrupo()+"  ";

         strFilSql= " AND ( ";
         strSql="SELECT co_emp FROM tbm_emp WHERE  "+strFilAux+"  ORDER BY co_emp ";
         System.out.println("strSql: \n" + strSql);
         rstLoc=stmLoc.executeQuery(strSql);
         while(rstLoc.next()){
             
             strCta = _getCtaEmpPrgUsrCxP(conn, rstLoc.getInt("co_emp")  );

             strTipDoc =" 32 "; //select co_tipdoc from tbm_cabtipdoc where co_emp="+rstLoc.getInt("co_emp")+" and  co_cat in ( 9,10,11,19,20 )  group by co_tipdoc ";

             if(blnEst) strFilSql +=" OR ";
             strFilSql += " (  d1.co_emp="+rstLoc.getInt("co_emp")+" AND d1.co_tipdoc IN( "+strTipDoc+" ) AND  d2.co_cta IN( "+strCta+" )   )  ";
             blnEst=true;
         }
         rstLoc.close();
         rstLoc=null;
         strFilSql += "  )  ";

     }else{
         intCodLocBus = Integer.parseInt(vecLoc.get(cboLocEmp.getSelectedIndex()).toString());
         if(intCodLocBus!=0)
            strFilAux = " AND d1.co_loc="+intCodLocBus+" ";

          strCta = _getCtaEmpPrgUsrCxP(conn, objParSis.getCodigoEmpresa() );

          strTipDoc =" 32 "; //select co_tipdoc from tbm_cabtipdoc where co_emp="+rstLoc.getInt("co_emp")+" and  co_cat in ( 9,10,11,19,20 )  group by co_tipdoc ";

          strFilSql= " AND (  "
          + " ( d1.co_emp="+objParSis.getCodigoEmpresa()+"  "+strFilAux+" AND d1.co_tipdoc IN (  "
          + "  "+strTipDoc+" "
          + "  ) AND d2.co_cta  IN( "+strCta+" )   )  ) ";
     }

     if(chkEscComp.isSelected())  strFilSql += " AND  cli.co_empgrp is null ";
     
     stmLoc.close();
     stmLoc=null;
   

  }catch(java.sql.SQLException e)  {  objUti.mostrarMsgErr_F1(this, e);  }
   catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);    }
  return strFilSql;
}


private String strFilSqlPagRet(java.sql.Connection conn){
  String strFilSql="";
  String strFilAux="";
  int intCodLocBus=0;
  int intCodEmpBus=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strCta="";
  String strTipDoc="";
  boolean blnEst=false;
  try{
     stmLoc=conn.createStatement();

     if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){

         intCodEmpBus = Integer.parseInt(vecLoc.get(cboLocEmp.getSelectedIndex()).toString());
         if(intCodEmpBus!=0)  strFilAux = "   co_emp = "+intCodEmpBus+" ";
         else strFilAux = " co_emp != "+objParSis.getCodigoEmpresaGrupo()+"  ";

         strFilSql= " AND ( ";
         strSql="SELECT co_emp FROM tbm_emp WHERE "+strFilAux+" ORDER BY co_emp ";
         rstLoc=stmLoc.executeQuery(strSql);
         while(rstLoc.next()){

             strCta = _getCtaEmpPrgUsrCxP(conn, rstLoc.getInt("co_emp")  );

             strTipDoc =" 33 "; // select co_tipdoc from tbm_cabtipdoc where co_emp="+rstLoc.getInt("co_emp")+" and  co_cat in ( 12 )  group by co_tipdoc ";

             if(blnEst) strFilSql +=" OR ";
             strFilSql += " (  d1.co_emp="+rstLoc.getInt("co_emp")+" AND d1.co_tipdoc IN( "+strTipDoc+" ) AND  d2.co_cta IN( "+strCta+" )   )  ";
             blnEst=true;
         }
         rstLoc.close();
         rstLoc=null;
         strFilSql += "  )  ";

     }else{
         intCodLocBus = Integer.parseInt(vecLoc.get(cboLocEmp.getSelectedIndex()).toString());
         if(intCodLocBus!=0)
            strFilAux = " AND d1.co_loc="+intCodLocBus+" ";

          strCta = _getCtaEmpPrgUsrCxP(conn, objParSis.getCodigoEmpresa()  );

          strTipDoc =" 33 "; // select co_tipdoc from tbm_cabtipdoc where co_emp="+objParSis.getCodigoEmpresa()+" and  co_cat in ( 12 )  group by co_tipdoc ";


          strFilSql= " AND (  "
          + " ( d1.co_emp="+objParSis.getCodigoEmpresa()+"  "+strFilAux+" AND d1.co_tipdoc IN (  "
          + "  "+strTipDoc+" "
          + "  ) AND d2.co_cta  IN( "+strCta+" )   )  ) ";
     }

     if(chkEscComp.isSelected())  strFilSql += " AND  cli.co_empgrp is null ";

     stmLoc.close();
     stmLoc=null;


  }catch(java.sql.SQLException e)  {  objUti.mostrarMsgErr_F1(this, e);  }
   catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);    }
  return strFilSql;
}

private boolean _cargarDatosComCxP(String strFecDes, String strFecHas  ){
  boolean blnRes=false;
  java.sql.Connection conn;
  try{
      conn=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos() );
      if(conn!=null){

       if( _cargarDatCompras(conn, strFecDes, strFecHas, strFilSqlCom(conn) ) ){
        if(_cargarDatPagChq(conn, strFecDes, strFecHas,  strFilSqlPagChq(conn) ) ){
         if(_cargarDatPagRet(conn, strFecDes, strFecHas, strFilSqlPagRet(conn)  ) ){
          if(_cargarTblCompras(conn)){
               blnRes=true;
       }} }}

  }}catch(java.sql.SQLException e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}

private boolean _cargarDatCompras(java.sql.Connection conn, String strFecDes, String strFecHas, String strFilSql  ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();

        /******* COMPRAS ********************/
        strSql=" SELECT x.anio, x.mes, abs(x1.valor_compras) AS valor_compras  FROM ( "+strBufAnioVen.toString()+" ) AS x "
        + " LEFT JOIN ( "
        + " SELECT extract(year from a.fe_doc) as anio "
        + " , extract(month from a.fe_doc) as mes  ,  sum(case when a.nd_tot is null then 0 else a.nd_tot end ) as valor_compras "
        + " FROM tbm_cabmovinv AS a "
        + " INNER JOIN tbm_cli AS a1 ON (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli ) "
        + " WHERE  a.st_reg not in ('I','E')   "
        + " AND a.fe_doc between '"+strFecDes+"' and '"+strFecHas+"' "
        + " "+strFilSql+" "
        + " GROUP BY extract(year from a.fe_doc), extract(month from a.fe_doc) "
        + " ORDER BY extract(year from a.fe_doc), extract(month from a.fe_doc) "
        + " ) AS x1 ON(x.anio=x1.anio and x.mes=x1.mes ) ";
        System.out.println("_cargarDatVentas --> "+strSql);
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

            if(blnEstBufCom) strBufCompras.append(" UNION ALL ");
            strBufCompras.append("SELECT "+rstLoc.getString("anio")+" AS anio ,"+rstLoc.getString("mes")+" AS MES ,"+rstLoc.getString("valor_compras")+" AS val_com ");
            blnEstBufCom=true;

        }
        rstLoc.close();
        rstLoc=null;

        stmLoc.close();
        stmLoc=null;

  }}catch(java.sql.SQLException e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}

private boolean _cargarDatPagChq(java.sql.Connection conn, String strFecDes, String strFecHas, String strFilSql  ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();

        /******* PAGOS EN CHEQUE EFECTIVO ********************/
        strSql=" SELECT x.anio, x.mes, abs( case when x1.valor_cobefe is null then 0 else x1.valor_cobefe end ) AS valor_cobefe  \n";
        strSql+=" FROM ( "+strBufAnioVen.toString()+" ) AS x \n";
        strSql+=" LEFT JOIN ( \n";
        strSql+="       SELECT  extract(year from fe_dia) as anio, \n";
        strSql+="               extract(month from fe_dia) as mes,  sum((nd_mondeb  - nd_monhab)) as valor_cobefe \n";  // , sum(detpag.nd_abo) as abono
        strSql+="       FROM   ( \n";
        strSql+="               SELECT d2.co_emp,d2.co_loc, d2.co_tipdoc, d2.co_dia,d2.co_reg,d2.co_cta,d1.fe_dia, \n";
        strSql+="                      d2.nd_mondeb,d2.nd_monhab \n";
        strSql+="               FROM tbm_cabdia as d1  \n";
        strSql+="               INNER JOIN tbm_detdia as d2 on (d2.co_emp=d1.co_emp and d2.co_loc=d1.co_loc and \n";
        strSql+="                                               d2.co_tipdoc=d1.co_tipdoc and d2.co_dia=d1.co_dia)\n";
        strSql+="               INNER JOIN tbm_detpag as detpag on (detpag.co_emp=d1.co_emp and detpag.co_loc=d1.co_loc and \n";
        strSql+="                                                   detpag.co_tipdoc=d1.co_tipdoc and detpag.co_doc=d1.co_dia)\n";
        strSql+="               INNER JOIN tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and \n";
        strSql+="                                                      pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and \n";
        strSql+="                                                      pagmov.co_reg=detpag.co_regpag ) \n";
        strSql+="               INNER JOIN tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and \n";
        strSql+="                                                      cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc) \n";
        strSql+="               INNER JOIN tbm_cli AS cli ON (cli.co_emp=cabmov.co_emp and cli.co_cli=cabmov.co_cli ) \n";
        strSql+="               WHERE  d1.st_reg not in ('I','E') \n";
        strSql+="                     "+strFilSql+" \n";
        strSql+="                     and d1.fe_dia between '"+strFecDes+"' and '"+strFecHas+"'  \n";
        strSql+="               GROUP BY d2.co_emp, d2.co_loc, d2.co_tipdoc, d2.co_dia,  d2.co_reg , d2.co_cta,   d1.fe_dia, \n";
        strSql+="                     d2.nd_mondeb  ,  d2.nd_monhab\n";
        strSql+="       ) AS x\n";
        strSql+=" GROUP BY  extract(year from fe_dia), extract(month from fe_dia) \n";
        strSql+=" ORDER BY  extract(year from fe_dia), extract(month from fe_dia) \n";
        strSql+=" ) AS x1 ON(x.anio=x1.anio and x.mes=x1.mes ) \n";
        strSql+="  ";
        strSql+="  ";
        
        strSql+=" ";
        System.out.println("_cargarDatCobEfe \n"+ strSql );
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

            if(blnEstBufCbEf) strBufCbEfe.append(" UNION ALL ");
            strBufCbEfe.append("SELECT "+rstLoc.getString("anio")+" AS anio ,"+rstLoc.getString("mes")+" AS MES ,"+rstLoc.getString("valor_cobefe")+" AS val_cbefe ");
            blnEstBufCbEf=true;

        }
        rstLoc.close();
        rstLoc=null;

        stmLoc.close();
        stmLoc=null;

  }}catch(java.sql.SQLException e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
 return blnRes;
}


//          String strSqlRete= " SELECT anio2, mes2, abs(sum(abono)) as valor2 from ( "+
//          "  SELECT  extract(year from a.fe_dia) as anio2, extract(month from a.fe_dia) as mes2, d2.co_emp, d2.co_loc, d2.co_tipdoc, d2.co_dia, d2.co_reg  , sum(detpag.nd_abo) as abono   "+
//          "  FROM tbm_cabdia as a   "+
//          "  inner join tbm_detdia as d2 on (d2.co_emp=a.co_emp and d2.co_loc=a.co_loc and d2.co_tipdoc=a.co_tipdoc and d2.co_dia=a.co_dia  )  "+
//          "  inner join tbm_placta as d3 on (d3.co_emp=d2.co_emp and d3.co_cta=d2.co_cta and d3.tx_codcta='2.01.03.01.01')   "+
//          "     inner join tbm_detpag as detpag on (detpag.co_emp=a.co_emp and detpag.co_loc=a.co_loc and detpag.co_tipdoc=a.co_tipdoc and detpag.co_doc=a.co_dia )  "+
//          "     inner join tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag )  "+
//          "     inner join tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc )      "+
//          "  where  "+strSqlAux+"   a.st_reg not in ('I','E')  and a.co_tipdoc in (33)  and a.fe_dia between '"+fecdesde+"' and '"+fechasta+"'  "+
//          " "+strSqlAuxExcComp+" GROUP BY extract(year from a.fe_dia), extract(month from a.fe_dia), d2.co_emp, d2.co_loc, d2.co_tipdoc, d2.co_dia, d2.co_reg    "+
//          " ) as a  GROUP BY anio2, mes2 ";

private boolean _cargarDatPagRet(java.sql.Connection conn, String strFecDes, String strFecHas, String strFilSql ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();

        /******* COBRO EFECTIVO ********************/
        strSql=" SELECT x.anio, x.mes, abs(case when x1.valor_cboRet is null then 0 else x1.valor_cboRet end ) AS valor_cboRet  FROM ( "+strBufAnioVen.toString()+" ) AS x "
        + " LEFT JOIN ( "
        + "  SELECT  extract(year from fe_dia) as anio, extract(month from fe_dia) as mes,  sum((nd_mondeb  - nd_monhab)) as valor_cboRet "
        + "  FROM (   SELECT  d2.co_emp, d2.co_loc, d2.co_tipdoc, d2.co_dia,  d2.co_reg , d2.co_cta,   d1.fe_dia, d2.nd_mondeb  , "
        + "  d2.nd_monhab  FROM tbm_cabdia as d1   "
        + "  INNER JOIN tbm_detdia as d2 on (d2.co_emp=d1.co_emp and d2.co_loc=d1.co_loc and d2.co_tipdoc=d1.co_tipdoc and d2.co_dia=d1.co_dia  ) "
        + "  INNER JOIN  tbm_detpag as detpag on (detpag.co_emp=d1.co_emp and detpag.co_loc=d1.co_loc and detpag.co_tipdoc=d1.co_tipdoc and detpag.co_doc=d1.co_dia ) "
        + "  INNER JOIN  tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag ) "
        + "  INNER JOIN tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc ) "
        + " INNER JOIN tbm_cli AS cli ON (cli.co_emp=cabmov.co_emp and cli.co_cli=cabmov.co_cli ) "
        + "  WHERE  d1.st_reg NOT IN ('I','E') "
        + "  "+strFilSql+"  "
        + "  AND d1.fe_dia between '"+strFecDes+"' and '"+strFecHas+"' "
        + "  GROUP BY d2.co_emp, d2.co_loc, d2.co_tipdoc, d2.co_dia,  d2.co_reg , d2.co_cta,   d1.fe_dia,  d2.nd_mondeb, "
        + "  d2.nd_monhab  ) AS x  "
        + " GROUP BY  extract(year from fe_dia), extract(month from fe_dia) "
        + " ORDER BY  extract(year from fe_dia), extract(month from fe_dia) "
        + " ) AS x1 ON(x.anio=x1.anio and x.mes=x1.mes ) ";
        System.out.println("_cargarDatCobRet---> "+strSql);
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

            if(blnEstBufCbRet) strBufCbRet.append(" UNION ALL ");
            strBufCbRet.append("SELECT "+rstLoc.getString("anio")+" AS anio ,"+rstLoc.getString("mes")+" AS MES ,"+rstLoc.getString("valor_cboRet")+" AS val_cbRet ");
            blnEstBufCbRet=true;

        }
        rstLoc.close();
        rstLoc=null;

        stmLoc.close();
        stmLoc=null;

  }}catch(java.sql.SQLException e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
 return blnRes;
}









private boolean _cargarTblCompras(java.sql.Connection conn){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  double dlbTotComVen=0;
  double dlbTotCboEfe=0, dlbTotCboRet=0, dlbValDifVen=0, dlbTotDifVen=0;
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();

        vecRegVenCom=new Vector();
        vecRegCbEfPag=new Vector();
        vecRegCbRet=new Vector();
        vecRegDif=new Vector();

        vecRegVenCom.add("(+)Compras ");
        vecRegCbEfPag.add("(-)Pago.Chq.");
        vecRegCbRet.add("(-)Pago.Ret.");
        vecRegDif.add("Diferencia ");

        /******* VENTAS ********************/
        strSql="SELECT x.anio, x.mes, x.val_com, x1.val_cbefe  , x2.val_cbRet FROM ( "+strBufCompras.toString()+" ) AS x "
        + " INNER JOIN ( "+strBufCbEfe.toString()+" ) AS x1 ON ( x.anio=x1.anio and x.mes=x1.mes )"
        + " INNER JOIN ( "+strBufCbRet.toString()+" ) AS x2 ON ( x.anio=x2.anio and x.mes=x2.mes ) ";
        System.out.println("_cargarTblVentas--> "+strSql);
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

           vecRegVenCom.add( new Double(rstLoc.getDouble("val_com")));
           vecRegCbEfPag.add( new Double( rstLoc.getDouble("val_cbefe") )); 
           vecRegCbRet.add( new Double( rstLoc.getDouble("val_cbRet") ));  

           dlbTotComVen += rstLoc.getDouble("val_com");
           dlbTotCboEfe += rstLoc.getDouble("val_cbefe");
           dlbTotCboRet += rstLoc.getDouble("val_cbRet");

           dlbValDifVen  = (rstLoc.getDouble("val_com")-rstLoc.getDouble("val_cbefe"))-rstLoc.getDouble("val_cbRet");
           dlbTotDifVen += dlbValDifVen;

           vecRegDif.add( new Double(dlbValDifVen));
        }

       vecRegVenCom.add( new Double(dlbTotComVen));
       vecRegCbEfPag.add( new Double(dlbTotCboEfe));
       vecRegCbRet.add( new Double(dlbTotCboRet));
       vecRegDif.add( new Double(dlbTotDifVen));

       java.util.Vector vecData = new java.util.Vector();
       vecData.add(vecRegVenCom);
       vecData.add(vecRegCbEfPag);
       vecData.add(vecRegCbRet);
       vecData.add(vecRegDif);

       objTblMod.setData(vecData);
       tblDat.setModel(objTblMod);

        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;

  }}catch(java.sql.SQLException e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}




private boolean _cargarDatosVentas(String strFecDes, String strFecHas  ){
  boolean blnRes=false;
  java.sql.Connection conn;
  try{
      conn=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos() );
      if(conn!=null){
      
       if( _cargarDatVentas(conn, strFecDes, strFecHas, strFilSqlVen(conn) ) ){
        if(_cargarDatCobEfe(conn, strFecDes, strFecHas,  strFilSqlCbEfe(conn) ) ){
         if(_cargarDatCobRet(conn, strFecDes, strFecHas, strFilSqlCbRet(conn)  ) ){
          if(_cargarTblVentas(conn)){
               blnRes=true;
       }}}}

  }}catch(java.sql.SQLException e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}

private boolean _cargarTblVentas(java.sql.Connection conn){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  double dlbTotComVen=0;
  double dlbTotCboEfe=0, dlbTotCboRet=0, dlbValDifVen=0, dlbTotDifVen=0;
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();

        vecRegVenCom=new Vector();
        vecRegCbEfPag=new Vector();
        vecRegCbRet=new Vector();
        vecRegDif=new Vector();
          
        vecRegVenCom.add("(+)Ventas ");
        vecRegCbEfPag.add("(-)Cobros.Efe.");
        vecRegCbRet.add("(-)Cobros.Ret.");
        vecRegDif.add("Diferencia ");

        /******* VENTAS ********************/
        strSql="SELECT x.anio, x.mes, x.val_ven, x1.val_cbefe, x2.val_cbRet FROM ( "+strBufVentas.toString()+" ) AS x "
        + " INNER JOIN ( "+strBufCbEfe.toString()+" ) AS x1 ON ( x.anio=x1.anio and x.mes=x1.mes )"
        + " INNER JOIN ( "+strBufCbRet.toString()+" ) AS x2 ON ( x.anio=x2.anio and x.mes=x2.mes ) ";        
        //System.out.println("_cargarTblVentas--> "+strSql);
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

           vecRegVenCom.add( new Double(rstLoc.getDouble("val_ven")));
           vecRegCbEfPag.add( new Double(rstLoc.getDouble("val_cbefe")));
           vecRegCbRet.add( new Double(rstLoc.getDouble("val_cbRet")));

           dlbTotComVen +=rstLoc.getDouble("val_ven");
           dlbTotCboEfe +=rstLoc.getDouble("val_cbefe");
           dlbTotCboRet +=rstLoc.getDouble("val_cbRet");

           dlbValDifVen  = (rstLoc.getDouble("val_ven")-rstLoc.getDouble("val_cbefe"))-rstLoc.getDouble("val_cbRet");
           dlbTotDifVen += dlbValDifVen;

           vecRegDif.add( new Double(dlbValDifVen));
        }

       vecRegVenCom.add( new Double(dlbTotComVen));
       vecRegCbEfPag.add( new Double(dlbTotCboEfe));
       vecRegCbRet.add( new Double(dlbTotCboRet));
       vecRegDif.add( new Double(dlbTotDifVen));

       java.util.Vector vecData = new java.util.Vector();
       vecData.add(vecRegVenCom);
       vecData.add(vecRegCbEfPag);
       vecData.add(vecRegCbRet);
       vecData.add(vecRegDif);
      
       objTblMod.setData(vecData);
       tblDat.setModel(objTblMod);

        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;

  }}catch(java.sql.SQLException e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}

private boolean _cargarDatVentas(java.sql.Connection conn, String strFecDes, String strFecHas, String strFilSql  ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();

        /******* VENTAS ********************/
        strSql=" SELECT x.anio, x.mes, abs(x1.valor_ventas) AS valor_ventas  FROM ( "+strBufAnioVen.toString()+" ) AS x "
        + " LEFT JOIN ( "
        + " SELECT extract(year from a.fe_doc) as anio "
        + " , extract(month from a.fe_doc) as mes  ,  sum(case when a.nd_tot is null then 0 else a.nd_tot end ) as valor_ventas "
        + " FROM tbm_cabmovinv AS a "
        + " INNER JOIN tbm_cli AS a1 ON (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli ) "
        + " WHERE  a.st_reg not in ('I','E')   "
        + " AND a.fe_doc between '"+strFecDes+"' and '"+strFecHas+"' "
        + " "+strFilSql+" "
        + " GROUP BY extract(year from a.fe_doc), extract(month from a.fe_doc) "
        + " ORDER BY extract(year from a.fe_doc), extract(month from a.fe_doc) "
        + " ) AS x1 ON(x.anio=x1.anio and x.mes=x1.mes ) ";
        //System.out.println("_cargarDatVentas --> "+strSql);
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

            if(blnEstBufVen) strBufVentas.append(" UNION ALL ");
            strBufVentas.append("SELECT "+rstLoc.getString("anio")+" AS anio ,"+rstLoc.getString("mes")+" AS MES ,"+rstLoc.getString("valor_ventas")+" AS val_ven ");
            blnEstBufVen=true;

        }
        rstLoc.close();
        rstLoc=null;

        stmLoc.close();
        stmLoc=null;

  }}catch(java.sql.SQLException e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}

private boolean _cargarDatVentasSal(java.sql.Connection conn, String strFecDes, String strFecHas, String strFilSql  ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();

        /******* VENTAS ********************/
        strSql=" SELECT x.anio, x.mes, abs(case when x1.valor_ventas is null then 0 else x1.valor_ventas end ) AS valor_ventas  FROM ( "+strBufAnioSal.toString()+" ) AS x "
        + " LEFT JOIN ( "
        + " SELECT extract(year from a.fe_doc) as anio "
        + " , extract(month from a.fe_doc) as mes  ,  sum(case when a.nd_tot is null then 0 else a.nd_tot end ) as valor_ventas "
        + " FROM tbm_cabmovinv AS a "
        + " INNER JOIN tbm_cli AS a1 ON (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli ) "
        + " WHERE  a.st_reg not in ('I','E')   "
        + " AND a.fe_doc between '"+strFecDes+"' and '"+strFecHas+"' "
        + " "+strFilSql+" "
        + " GROUP BY extract(year from a.fe_doc), extract(month from a.fe_doc) "
        + " ORDER BY extract(year from a.fe_doc), extract(month from a.fe_doc) "
        + " ) AS x1 ON(x.anio=x1.anio and x.mes=x1.mes ) ";
        //System.out.println("_cargarDatVentasSal --> "+strSql);
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

            if(blnEstBufVen) strBufVentas.append(" UNION ALL ");
            strBufVentas.append("SELECT "+rstLoc.getString("anio")+" AS anio ,"+rstLoc.getString("mes")+" AS MES ,"+rstLoc.getString("valor_ventas")+" AS val_ven ");
            blnEstBufVen=true;

        }
        rstLoc.close();
        rstLoc=null;

        stmLoc.close();
        stmLoc=null;

  }}catch(java.sql.SQLException e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}


private double _getSaldoInc(java.sql.Connection conn, String strFilSql, String strFilFecSql  ){
  double dblValSalInc=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();

        /**************************/
        strSql=" SELECT abs(sum(a1.nd_mondeb - a1.nd_monhab )) as saldo_inc "
        + " FROM tbm_cabdia AS a "
        + " INNER JOIN  tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_dia = a.co_dia ) "
        + " WHERE  a.st_reg not in ('I','E')   "
        + " "+strFilFecSql+" "   // AND a.fe_dia between '"+strFecDes+"' and '"+strFecHas+"' "
        + " "+strFilSql+" ";
        //System.out.println("_cargarDatSaldoIncobrable --> "+strSql);
        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()){

            dblValSalInc= rstLoc.getDouble("saldo_inc");

//            if(blnEstBufSalInc) strBufSalInc.append(" UNION ALL ");
//            strBufSalInc.append("SELECT "+rstLoc.getString("anio")+" AS anio ,"+rstLoc.getString("mes")+" AS MES ,"+rstLoc.getString("valor_SalInc")+" AS val_SalInc ");
//            blnEstBufSalInc=true;

        }
        rstLoc.close();
        rstLoc=null;

        stmLoc.close();
        stmLoc=null;

  }}catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  {   objUti.mostrarMsgErr_F1(this, Evt);  }
  return dblValSalInc;
}

private boolean _cargarDatSaldoIncobrable(java.sql.Connection conn, String strFilSql  ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  double dbValSalInc=0;
  double dbValSalObtSql=0;
  boolean blnEstBusFec=false;
  String strFilSqlFec="";
  try{
     if(conn!=null){
      stmLoc=conn.createStatement();

         strSql="SELECT * FROM (  "+strBufAnioSal.toString()+ " ) AS x ";
         // System.out.println("_cargarDatSalIniFin --> "+ strSql );
         rstLoc=stmLoc.executeQuery(strSql);
         strSql="";
         while(rstLoc.next()){
            
             if(blnEstBusFec) strFilSqlFec =" AND a.fe_dia  between  '"+rstLoc.getString("fecha_act")+"' and '"+rstLoc.getString("fecha_fin")+"' ";
             else strFilSqlFec = " AND a.fe_dia <= '" + rstLoc.getString("fecha_fin") + "' ";
             blnEstBusFec=true;

              dbValSalObtSql = _getSaldoInc(conn, strFilSql, strFilSqlFec );

              dbValSalInc =dbValSalObtSql; //   dbValSalInc +=dbValSalObtSql;

            if(blnEstBufSalInc) strBufSalInc.append(" UNION ALL ");
            strBufSalInc.append("SELECT "+rstLoc.getString("anio")+" AS anio ,"+rstLoc.getString("mes")+" AS MES ,"+dbValSalInc+" AS val_SalInc ");
            blnEstBufSalInc=true;

         }
         rstLoc.close();
         rstLoc=null;
     stmLoc.close();
     stmLoc=null;

  }}catch(java.sql.SQLException e)  { blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
    catch (Exception e){  blnRes=false; objUti.mostrarMsgErr_F1(this, e);    }
  return blnRes;
}



private boolean _cargarDatCobEfe(java.sql.Connection conn, String strFecDes, String strFecHas, String strFilSql  ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();
 
        /******* COBRO EFECTIVO ********************/
        strSql=" SELECT x.anio, x.mes, abs( case when x1.valor_cobefe is null then 0 else x1.valor_cobefe end ) AS valor_cobefe  FROM ( "+strBufAnioVen.toString()+" ) AS x "
        + " LEFT JOIN ( "
        + " SELECT  extract(year from fe_dia) as anio, "
        + "  extract(month from fe_dia) as mes,  sum((nd_mondeb  - nd_monhab)) as valor_cobefe FROM   ( "
        + " SELECT "
        + " d2.co_emp, "
        + " d2.co_loc, d2.co_tipdoc, d2.co_dia,  d2.co_reg , d2.co_cta,   d1.fe_dia, "
        + " d2.nd_mondeb  ,  d2.nd_monhab "
        + " FROM tbm_cabdia as d1 "
        + " inner join tbm_detdia as d2 on (d2.co_emp=d1.co_emp and d2.co_loc=d1.co_loc and d2.co_tipdoc=d1.co_tipdoc and d2.co_dia=d1.co_dia  ) "
        + " inner join tbm_detpag as detpag on (detpag.co_emp=d1.co_emp and detpag.co_loc=d1.co_loc and detpag.co_tipdoc=d1.co_tipdoc and detpag.co_doc=d1.co_dia ) "
        + " inner join tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag ) "
        + " inner join tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc ) "
        + " INNER JOIN tbm_cli AS cli ON (cli.co_emp=cabmov.co_emp and cli.co_cli=cabmov.co_cli ) "
        + " where  d1.st_reg not in ('I','E')   "
        + " "+strFilSql+" "
        + " and d1.fe_dia between '"+strFecDes+"' and '"+strFecHas+"' "
        + " GROUP BY d2.co_emp, d2.co_loc, d2.co_tipdoc, d2.co_dia,  d2.co_reg , d2.co_cta,   d1.fe_dia, "
        + " d2.nd_mondeb  ,  d2.nd_monhab "
        + " ) AS x "
        + " GROUP BY  extract(year from fe_dia), extract(month from fe_dia) "
        + " ORDER BY  extract(year from fe_dia), extract(month from fe_dia) "
        + " ) AS x1 ON(x.anio=x1.anio and x.mes=x1.mes ) ";
        System.out.println("_cargarDatCobEfe "+ strSql );
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

            if(blnEstBufCbEf) strBufCbEfe.append(" UNION ALL ");
            strBufCbEfe.append("SELECT "+rstLoc.getString("anio")+" AS anio ,"+rstLoc.getString("mes")+" AS MES ,"+rstLoc.getString("valor_cobefe")+" AS val_cbefe ");
            blnEstBufCbEf=true;

        }
        rstLoc.close();
        rstLoc=null;

        stmLoc.close();
        stmLoc=null;

  }}catch(java.sql.SQLException e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
 return blnRes;
}

private boolean _cargarDatCobRet(java.sql.Connection conn, String strFecDes, String strFecHas, String strFilSql ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();

        /******* COBRO EFECTIVO ********************/
        strSql=" SELECT x.anio, x.mes, abs(case when x1.valor_cboRet is null then 0 else x1.valor_cboRet end ) AS valor_cboRet  FROM ( "+strBufAnioVen.toString()+" ) AS x "
        + " LEFT JOIN ( "
        + "  SELECT  extract(year from fe_dia) as anio, extract(month from fe_dia) as mes,  sum((nd_mondeb  - nd_monhab)) as valor_cboRet "
        + "  FROM (   SELECT  d2.co_emp, d2.co_loc, d2.co_tipdoc, d2.co_dia,  d2.co_reg , d2.co_cta,   d1.fe_dia, d2.nd_mondeb  , "
        + "  d2.nd_monhab  FROM tbm_cabdia as d1   "
        + "  INNER JOIN tbm_detdia as d2 on (d2.co_emp=d1.co_emp and d2.co_loc=d1.co_loc and d2.co_tipdoc=d1.co_tipdoc and d2.co_dia=d1.co_dia  ) "
        + "  INNER JOIN  tbm_detpag as detpag on (detpag.co_emp=d1.co_emp and detpag.co_loc=d1.co_loc and detpag.co_tipdoc=d1.co_tipdoc and detpag.co_doc=d1.co_dia ) "
        + "  INNER JOIN  tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag ) "
        + "  INNER JOIN tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc ) "
        + " INNER JOIN tbm_cli AS cli ON (cli.co_emp=cabmov.co_emp and cli.co_cli=cabmov.co_cli ) "
        + "  WHERE  d1.st_reg NOT IN ('I','E') "
        + "  "+strFilSql+"  "
        + "  AND d1.fe_dia between '"+strFecDes+"' and '"+strFecHas+"' "
        + "  GROUP BY d2.co_emp, d2.co_loc, d2.co_tipdoc, d2.co_dia,  d2.co_reg , d2.co_cta,   d1.fe_dia,  d2.nd_mondeb, "
        + "  d2.nd_monhab  ) AS x  "
        + " GROUP BY  extract(year from fe_dia), extract(month from fe_dia) "
        + " ORDER BY  extract(year from fe_dia), extract(month from fe_dia) "
        + " ) AS x1 ON(x.anio=x1.anio and x.mes=x1.mes ) ";
       // System.out.println("_cargarDatCobRet---> "+strSql);
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

            if(blnEstBufCbRet) strBufCbRet.append(" UNION ALL ");
            strBufCbRet.append("SELECT "+rstLoc.getString("anio")+" AS anio ,"+rstLoc.getString("mes")+" AS MES ,"+rstLoc.getString("valor_cboRet")+" AS val_cbRet ");
            blnEstBufCbRet=true;

        }
        rstLoc.close();
        rstLoc=null;

        stmLoc.close();
        stmLoc=null;

  }}catch(java.sql.SQLException e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
 return blnRes;
}






  
//
//       private boolean cargarDatosSaldoVen_otro(){
//        boolean blnRes=false;
//        int intval3=0;
//         try{
//
//         String fecdesde="2006-05-22",fechasta="2006-05-22";
//         int intMesDes=-1, intMesHas=-1;
//         int intAniDes=0, intAniHas=01;
//         String strSqlAux3="";
//
//         if (objSelDat.chkIsSelected())
//                    {
//                        switch (objSelDat.getTypeSeletion())
//                        {
//                            case 1: //Búsqueda por rangos
//                                  intval3=1;
//
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                   //System.out.println(">> "+intMesDes + " <> "+ intMesHas + " <>" + intAniDes  + " <> " + intAniHas  );
//
//
//                                  break;
//                            case 2: //Fechas mayores o iguales a "Desde".
//                                   intval3=2;
//
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                   intAniHas=intAniDes;
//
//                                   fecdesde =  objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = "12/31/"+String.valueOf(objSelDat.getYearSelected());
//
//
//                             //   System.out.println("22 >> "+ intMesDes + " <>" + intAniDes + " -- " + fecdesde  );
//
//                               break;
//                            case 3: //Fechas menores o iguales a "Hasta".
//                                  intval3=3;
//
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                   intAniDes=intAniHas;
//
//                                   fechasta =  objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = "01/01/"+String.valueOf(objSelDat.getYearSelected());
//
//                                // System.out.println(">> "+ intMesHas + " <>" + intAniHas  + " -- " + fechasta );
//
//
//                                break;
//                            case 4: //Todo.
//                                System.out.println("Error.........");
//                                intval3=4;
//                                break;
//
//                        }
//                    }
//
//
//
//          // if(4==4) return true;
//
//
//             vecDatOrg.clear();
//             con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos() );
//             if (con!=null)
//               {
//
//                 stm=con.createStatement();
//                 vecRegCom=new Vector();
//                 vecRegPag=new Vector();
//                 vecRegRet=new Vector();
//                 vecRegDif=new Vector();
//                 vecRegDias=new Vector();
//                 vecRegRat=new Vector();
//
//           Vector vecCabOrg2 = new Vector();
//           //vecCabOrg.clear();
//           vecCabOrg2.add(" ");
//           StringBuffer stbins2=new StringBuffer();
//           int x=0;
//
//
//           intAniDes=intAniDes-1;
//
//           if(intval3==2){
//           int hasta= 11;
//           int desde = intMesDes;
//           for(int y=intAniDes; y<=intAniHas; y++){
//           //  if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg2.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg2.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg2.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg2.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg2.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg2.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg2.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg2.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg2.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg2.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg2.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg2.add("Diciembre"+y+"");
//
//
//                    if (x>0)
//                      stbins2.append(" UNION ALL ");
//                      stbins2.append(" select "+y+" as anio, "+(i+1)+" as mes, date('"+y+"-"+(i+1)+"-01')-1  AS fecha_ini , date('"+y+"-"+(i+1)+"-01') AS fecha_act  " +
//                      ", date(date('"+y+"-"+(i+1)+"-01')+ interval '1 month')-1 as fecha_fin  ");
//
//                      //select 2006 as anio, 11 as mes, date('2006-11-01')-1  as fecha_ini  , date(date('2006-11-01')+ interval '1 month')-1 as fecha_fin
//
//
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//          if(intval3==3){
//           int hasta=intMesHas;
//           int desde = 0;
//           for(int y=intAniDes; y<=intAniHas; y++){
//            // if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg2.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg2.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg2.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg2.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg2.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg2.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg2.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg2.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg2.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg2.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg2.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg2.add("Diciembre"+y+"");
//
//
//                    if (x>0)
//                      stbins2.append(" UNION ALL ");
//                      stbins2.append(" select "+y+" as anio, "+(i+1)+" as mes, date('"+y+"-"+(i+1)+"-01')-1  AS fecha_ini , date('"+y+"-"+(i+1)+"-01') AS fecha_act  " +
//                      ", date(date('"+y+"-"+(i+1)+"-01')+ interval '1 month')-1 as fecha_fin  ");
//
//
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//
//          if(intval3==1){
//           int hasta=11;
//           int desde = intMesDes;
//           for(int y=intAniDes; y<=intAniHas; y++){
//             if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//
//                     if((i+1)==1)  vecCabOrg2.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg2.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg2.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg2.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg2.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg2.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg2.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg2.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg2.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg2.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg2.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg2.add("Diciembre"+y+"");
//
//
//                   if (x>0)
//                      stbins2.append(" UNION ALL ");
//                      stbins2.append(" select "+y+" as anio, "+(i+1)+" as mes, date('"+y+"-"+(i+1)+"-01')-1  AS fecha_ini , date('"+y+"-"+(i+1)+"-01') AS fecha_act  " +
//                      ", date(date('"+y+"-"+(i+1)+"-01')+ interval '1 month')-1 as fecha_fin  ");
//
//
//
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//       vecCabOrg2.add("Total");
//
//
//          String strSqlAux="a.co_emp="+objParSis.getCodigoEmpresa()+" and ",strSqlAux2=" d1.co_emp="+objParSis.getCodigoEmpresa()+" and ";
//          if(local.getSelectedIndex()>0) {
//            strSqlAux = strSqlAux+ "   a.co_loc = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//            strSqlAux2 = strSqlAux2+"  d1.co_loc = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//          }
//
//
//          String strCodcta = "";
//
//          if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
//              strSqlAux =" ";
//              strSqlAux2 =" ";
//
//          }
//
//
//
//
//           /*   //VERSION ANTERIOR
//
//           String sql2 =" SELECT anio , mes , abs(saldo_inicial), abs(ventas), abs(saldo_final),  ((abs(saldo_inicial)+abs(ventas))-abs(saldo_final)) as final , "+ ///abs(fecha_ini-fecha_fin) as dias  FROM (" +
//           " case when  fecha_fin > current_date  then  abs((fecha_ini-current_date)) else  abs(fecha_ini-fecha_fin)  end as dias   FROM ( "+
//           " SELECT  anio, mes , fecha_ini , fecha_fin , " +
//           " (  " +
//           "     select (sum(nd_mondeb - nd_monhab) ) as saldo from tbm_cabdia as a  " +
//           "     inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) " +
//           "     inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta in ('1.01.03.01.01' ,'1.01.03.01.02' )) "+
//           "     where "+strSqlAux+" a.co_tipdoc in(1,3,18,24,25,26,27,73,5,7,28 ,21, 67 ,29  )  and a.st_reg not in ('E','I') and a.fe_dia <= x.fecha_ini  " +
//           " ) as saldo_inicial," +
//           "(" +
//           "    select (sum(nd_mondeb - nd_monhab) ) as saldo from tbm_cabdia as a " +
//           "    inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia)" +
//           "    inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta in ('1.01.03.01.01' ,'1.01.03.01.02' )) "+
//           "    where "+strSqlAux+" a.co_tipdoc in(1,3,18,24,25,26,27,73,5,7,28 ,21, 67  ,29 )  and a.st_reg not in ('E','I') and a.fe_dia <=  x.fecha_fin " +
//           " ) as saldo_final," +
//           " (" +
//           "    Select   sum(a.nd_tot) as valor_total  from tbm_cabmovinv as a" +
//           "    WHERE "+strSqlAux+" a.st_reg not in ('I','E') and a.fe_doc between  x.fecha_act and x.fecha_fin   and a.co_tipdoc in (1,3,28) " +
//           " ) as ventas " +
//           " FROM (" +
//           " " + stbins2.toString() + " "+
//           " ) AS x  ) AS y  order by anio , mes ";
//
//           */
//
//
//           String sql2 =" SELECT anio , mes , abs(saldo_inicial), abs(ventas), abs(saldo_final),  ((abs(saldo_inicial)+abs(ventas))-abs(saldo_final)) as final , "+ ///abs(fecha_ini-fecha_fin) as dias  FROM (" +
//           " case when  fecha_fin > current_date  then  abs((fecha_ini-current_date)) else  abs(fecha_ini-fecha_fin)  end as dias   FROM ( "+
//           " SELECT  anio, mes , fecha_ini , fecha_fin , " +
//           " (  " +
//           "     select (sum(nd_mondeb - nd_monhab) ) as saldo from tbm_cabdia as a  " +
//           "     inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) " +
//           "     inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta in ('1.01.03.01.01' ,'1.01.03.01.02' , '1.01.03.01.99' )) "+
//           "     where "+strSqlAux+"  a.st_reg not in ('E','I') and a.fe_dia <= x.fecha_ini  " +
//           " ) as saldo_inicial," +
//           "(" +
//           "    select (sum(nd_mondeb - nd_monhab) ) as saldo from tbm_cabdia as a " +
//           "    inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia)" +
//           "    inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta in ('1.01.03.01.01' ,'1.01.03.01.02' , '1.01.03.01.99' )) "+
//           "    where "+strSqlAux+"  a.st_reg not in ('E','I') and a.fe_dia <=  x.fecha_fin " +
//           " ) as saldo_final," +
//           " (" +
//           "    Select   sum(a.nd_tot) as valor_total  from tbm_cabmovinv as a" +
//           "    WHERE "+strSqlAux+" a.st_reg not in ('I','E') and a.fe_doc between  x.fecha_act and x.fecha_fin   and a.co_tipdoc in (1,3,28,17,51) " +
//           " ) as ventas " +
//           " FROM (" +
//           " " + stbins2.toString() + " "+
//           " ) AS x  ) AS y  order by anio , mes ";
//
//            System.out.println(" Query: "+ sql2 ) ;
//
//           rst=stm.executeQuery(sql2);
//
//
//
//
//                        vecRegCom.add(" ");
//                        vecRegPag.add(" ");
//                        vecRegRet.add("CxC");
//                        vecRegDif.add("Cobros ");
//                        vecRegDias.add(" ");
//                        vecRegRat.add("Ratios.");
//
//
//                        double dlbCom=0;
//                        double dlbPag=0;
//                        double dlbRet=0;
//                        double dlbDif=0;
//
//                         int intVal=0;
//
//                    while (rst.next())
//                    {
//                        vecRegCom.add( new Double(rst.getDouble(3)));
//                        vecRegPag.add( new Double(rst.getDouble(4)));
//                        vecRegRet.add( new Double(rst.getDouble(5)));
//                        vecRegDif.add( new Double(rst.getDouble(6)));
//                        vecRegDias.add( rst.getString(7) );
//                        vecRegRat.add("");
//
//                        if(intVal==0) dlbCom +=rst.getDouble(3);
//                        dlbPag +=rst.getDouble(4);
//                        dlbRet =rst.getDouble(5);
//                        dlbDif +=rst.getDouble(6);
//
//                        intVal=1;
//                    }
//
//                        dlbCom = objUti.redondear(dlbCom,2);
//                        dlbPag = objUti.redondear(dlbPag,2);
//                        dlbRet = objUti.redondear(dlbRet,2);
//                        dlbDif = objUti.redondear(dlbDif,2);
//
//
//                        vecRegCom.add(new Double(dlbCom));
//                        vecRegPag.add(new Double(dlbPag));
//                        vecRegRet.add(new Double(dlbRet));
//                        vecRegDif.add(new Double(dlbDif));
//                        vecRegDias.add(" ");
//                        vecRegRat.add("");
//
//
//                        java.util.Vector vecData = new java.util.Vector();
//                        vecData.add(vecRegCom);
//                        vecData.add(vecRegPag);
//                        vecData.add(vecRegRet);
//                        vecData.add(vecRegDif);
//                        vecData.add(vecRegDias);
//                        vecData.add(vecRegRat);
//
//                          vecDatOrg.add(vecRegCom);
//                          vecDatOrg.add(vecRegPag);
//                          vecDatOrg.add(vecRegRet);
//                          vecDatOrg.add(vecRegDif);
//                          vecDatOrg.add(vecRegDias);
//                          vecDatOrg.add(vecRegRat);
//
//                    con.close();
//                    con=null;
//
//
//                     Librerias.ZafTblUti.ZafTblMod.ZafTblMod oblModSal = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
//                     oblModSal.setHeader(vecCabOrg2);
//                     tblDatSal.setModel(oblModSal);
//
//
//                       for(int i=1; i<tblDatSal.getColumnCount(); i++){
//                             objTblCelRenLbl=null;
//                             javax.swing.table.TableColumnModel tcmAux=tblDatSal.getColumnModel();
//                             objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
//                             objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
//                             objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//                             objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
//                             objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
//                             tcmAux.getColumn( i ).setCellRenderer(objTblCelRenLbl);
//                             tcmAux=null;
//                        }
//
//
//                        javax.swing.table.TableColumnModel tcmAux=tblDatSal.getColumnModel();
//                        for(int i=1; i<(tblDatSal.getColumnCount()-tblDat.getColumnCount())+1; i++){
//                                tcmAux.getColumn(i).setWidth(0);
//                                tcmAux.getColumn(i).setMaxWidth(0);
//                                tcmAux.getColumn(i).setMinWidth(0);
//                                tcmAux.getColumn(i).setPreferredWidth(0);
//                                tcmAux.getColumn(i).setResizable(false);
//                        }
//                        tcmAux=null;
//
//
//                     oblModSal.setData(vecData);
//                     tblDatSal.setModel(oblModSal);
//
//                       int  intCon=0;
//                       double dblSalIni=0;
//                       double dblSalFin=0;
//                       double dblVentas=0;
//
//                       for(int i=(tblDatSal.getColumnCount()-tblDat.getColumnCount()+1) ; i< tblDatSal.getColumnCount()-1; i++){
//
//                             if(intCon==0){
//                                 tblDatSal.setValueAt(tblDatSal.getValueAt(0, i).toString() , 0 , tblDatSal.getColumnCount()-1);
//                                 dblSalIni = Double.parseDouble(tblDatSal.getValueAt(0, i).toString());
//                                 dblSalFin = Double.parseDouble(tblDatSal.getValueAt(2, tblDatSal.getColumnCount()-1 ).toString());
//                             }
//                            dblVentas = dblVentas + Double.parseDouble(tblDatSal.getValueAt(1, i).toString());
//                            intCon=1;
//                        }
//
//                     tblDatSal.setValueAt( ""+dblVentas  , 1 , tblDatSal.getColumnCount()-1);
//                     dblVentas = (dblSalIni+dblVentas)-dblSalFin;
//                     tblDatSal.setValueAt( ""+dblVentas  , 3 , tblDatSal.getColumnCount()-1);
//
//
//
//                    new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDatSal);
//
//                    blnRes=true;
//                    //vecCabOrg2.clear();
//                    //vecCabOrg2=null;
//                     vecRegCom=null;
//                     vecRegPag=null;
//                     vecRegRet=null;
//                     vecRegDif=null;
//                     vecRegDias=null;
//                     vecRegRat=null;
//
//
//        }}
//        catch (java.sql.SQLException e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);
//        }
//
//        return blnRes;
//    }
//
//
//
//     private boolean cargarDatosSaldoVen(){
//        boolean blnRes=false;
//        int intval3=0;
//         try{
//
//         String fecdesde="2006-05-22",fechasta="2006-05-22";
//         int intMesDes=-1, intMesHas=-1;
//         int intAniDes=0, intAniHas=01;
//         String strSqlAux3="";
//
//         if (objSelDat.chkIsSelected())
//                    {
//                        switch (objSelDat.getTypeSeletion())
//                        {
//                            case 1: //Búsqueda por rangos
//                                  intval3=1;
//
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//
//
//                                  break;
//                            case 2: //Fechas mayores o iguales a "Desde".
//                                   intval3=2;
//
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                   intAniHas=intAniDes;
//
//                                   fecdesde =  objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = "12/31/"+String.valueOf(objSelDat.getYearSelected());
//
//
//                             //   System.out.println("22 >> "+ intMesDes + " <>" + intAniDes + " -- " + fecdesde  );
//
//                               break;
//                            case 3: //Fechas menores o iguales a "Hasta".
//                                  intval3=3;
//
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                   intAniDes=intAniHas;
//
//                                   fechasta =  objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = "01/01/"+String.valueOf(objSelDat.getYearSelected());
//
//                                // System.out.println(">> "+ intMesHas + " <>" + intAniHas  + " -- " + fechasta );
//
//
//                                break;
//                            case 4: //Todo.
//                                System.out.println("Error.........");
//                                intval3=4;
//                                break;
//
//                        }
//                    }
//
//
//
//          // if(4==4) return true;
//
//
//             vecDatOrg.clear();
//             con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos() );
//             if (con!=null)
//               {
//
//                 stm=con.createStatement();
//                 vecRegCom=new Vector();
//                 vecRegPag=new Vector();
//                 vecRegRet=new Vector();
//                 vecRegDif=new Vector();
//                 vecRegDias=new Vector();
//                 vecRegRat=new Vector();
//
//           Vector vecCabOrg2 = new Vector();
//           //vecCabOrg.clear();
//           vecCabOrg2.add(" ");
//           StringBuffer stbins2=new StringBuffer();
//           int x=0;
//
//
//           intAniDes=intAniDes-1; // ***********************************************
//
//           if(intval3==2){
//           int hasta= 11;
//           int desde = intMesDes;
//           for(int y=intAniDes; y<=intAniHas; y++){
//           //  if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg2.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg2.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg2.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg2.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg2.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg2.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg2.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg2.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg2.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg2.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg2.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg2.add("Diciembre"+y+"");
//
//
//                    if (x>0)
//                      stbins2.append(" UNION ALL ");
//                      stbins2.append(" select "+y+" as anio, "+(i+1)+" as mes, date('"+y+"-"+(i+1)+"-01')-1  AS fecha_ini , date('"+y+"-"+(i+1)+"-01') AS fecha_act  " +
//                      ", date(date('"+y+"-"+(i+1)+"-01')+ interval '1 month')-1 as fecha_fin  ");
//
//                      //select 2006 as anio, 11 as mes, date('2006-11-01')-1  as fecha_ini  , date(date('2006-11-01')+ interval '1 month')-1 as fecha_fin
//
//
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//          if(intval3==3){
//           int hasta=intMesHas;
//           int desde = 0;
//           for(int y=intAniDes; y<=intAniHas; y++){
//            // if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg2.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg2.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg2.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg2.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg2.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg2.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg2.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg2.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg2.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg2.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg2.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg2.add("Diciembre"+y+"");
//
//
//                    if (x>0)
//                      stbins2.append(" UNION ALL ");
//                      stbins2.append(" select "+y+" as anio, "+(i+1)+" as mes, date('"+y+"-"+(i+1)+"-01')-1  AS fecha_ini , date('"+y+"-"+(i+1)+"-01') AS fecha_act  " +
//                      ", date(date('"+y+"-"+(i+1)+"-01')+ interval '1 month')-1 as fecha_fin  ");
//
//
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//
//          if(intval3==1){
//           int hasta=11;
//           int desde = intMesDes;
//           for(int y=intAniDes; y<=intAniHas; y++){
//             if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//
//                     if((i+1)==1)  vecCabOrg2.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg2.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg2.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg2.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg2.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg2.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg2.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg2.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg2.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg2.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg2.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg2.add("Diciembre"+y+"");
//
//
//                   if (x>0)
//                      stbins2.append(" UNION ALL ");
//                      stbins2.append(" select "+y+" as anio, "+(i+1)+" as mes, date('"+y+"-"+(i+1)+"-01')-1  AS fecha_ini , date('"+y+"-"+(i+1)+"-01') AS fecha_act  " +
//                      ", date(date('"+y+"-"+(i+1)+"-01')+ interval '1 month')-1 as fecha_fin  ");
//
//
//
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//       vecCabOrg2.add("Total");
//
//
//          String strSqlAux="a.co_emp="+objParSis.getCodigoEmpresa()+" and ",strSqlAux2=" d1.co_emp="+objParSis.getCodigoEmpresa()+" and ";
//          if(local.getSelectedIndex()>0) {
//            strSqlAux = strSqlAux+ "   a.co_loc = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//            strSqlAux2 = strSqlAux2+"  d1.co_loc = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//          }
//
//
//          String strCodcta = "";
//
//          if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
//              strSqlAux =" ";
//              strSqlAux2 =" ";
//
//          }
//
////         AND NOT (cabmov.co_emp=1 AND cabmov.co_cli in ( 3516 ,602, 603,2600,1039) )
////         AND NOT (cabmov.co_emp=2 AND cabmov.co_cli in ( 2854 ,446, 2105, 789)  )
////         AND NOT (cabmov.co_emp=3 AND cabmov.co_cli in ( 2858 ,453, 2107, 832)  )
////         AND NOT (cabmov.co_emp=4 AND cabmov.co_cli in ( 3117 ,498, 2294, 886) )
//
//
//
//
//          String strInCExc="";
//          String strSalExc="T";
//          if(chkEscComp.isSelected()){
//              strInCExc="  where estado=''  ";
//              strSalExc="E";
//
//            strSqlAux3 = " AND NOT (a.co_emp=1 AND a.co_cli in (3516, 602, 603, 2600, 1039 ) ) "+
//            "   AND NOT (a.co_emp=2 AND a.co_cli in (2854, 446, 2105, 789 ) )" +
//            "   AND NOT (a.co_emp=3 AND a.co_cli in (2858, 453, 2107, 832 ) )" +
//            "   AND NOT (a.co_emp=4 AND a.co_cli in (3117, 498, 2294, 886 )  ) ";
//
//          }
//
///*******************************************************************************************/
//         String strSql="";
//         java.sql.Statement stmLoc;
//         java.sql.ResultSet rstLoc;
//         StringBuffer stbSalIni;
//         StringBuffer stbSalFin;
//         int intConSalIni=0;
//         int intConSalFin=0;
//         double dblSalIni=0;
//         double dblSalFin=0;
//         stbSalIni=new StringBuffer();
//         stbSalFin=new StringBuffer();
//
//         stmLoc=con.createStatement();
//
//         //System.out.println(""+ stbSalIni.toString() );
//
//         strSql="SELECT * FROM (  "+d.toString()+ " ) AS x ";
//         rstLoc=stmLoc.executeQuery(strSql);
//         strSql="";
//         while(rstLoc.next()){
//             double dblVal=0;
//
//             dblVal = SaldoIni(con, strSqlAux, rstLoc.getString("anio"), rstLoc.getString("mes"), strSalExc );
//             if( dblVal != 0 ){
//                dblSalFin=dblVal;
//                if(intConSalIni==1) stbSalIni.append(" UNION ALL ");
//                stbSalIni.append("SELECT "+rstLoc.getString("anio")+" AS anio, "+rstLoc.getString("mes")+" AS mes, "+dblSalFin+" AS salini ");
//                intConSalIni=1;
//
//                dblSalFin = SaldoFin(con, strSqlAux, rstLoc.getString("anio"), rstLoc.getString("mes"), strSalExc );
//                if(intConSalFin==1) stbSalFin.append(" UNION ALL ");
//                 stbSalFin.append("SELECT "+rstLoc.getString("anio")+" AS anio, "+rstLoc.getString("mes")+" AS mes, "+dblSalFin+" AS salfin ");
//                 intConSalFin=1;
//             }else{
//
//                 dblVal=SaldoIniFinal(con, strSqlAux, "1.01.03.01.01' ,'1.01.03.01.02', '1.01.03.01.03', '1.01.03.01.04'  , '1.01.03.01.99", rstLoc.getString("fecha_act"), rstLoc.getString("fecha_fin"), strInCExc );
//                 if(intConSalIni==1) stbSalIni.append(" UNION ALL ");
//                 stbSalIni.append("SELECT "+rstLoc.getString("anio")+" AS anio, "+rstLoc.getString("mes")+" AS mes, "+dblSalFin+" AS salini ");
//                 intConSalIni=1;
//
//                  strSql+=" ; insert into tbm_salctacli "+
//                 " select '"+strSalExc+"', "+dblSalFin+", "+(dblSalFin+dblVal)+", "+objParSis.getCodigoEmpresa()+" , "+vecLoc.elementAt(local.getSelectedIndex()).toString()+", '"+rstLoc.getString("anio")+rstLoc.getString("mes")+"' , "+rstLoc.getString("anio")+", "+rstLoc.getString("mes");
//
//                 dblSalFin+=dblVal;
//                 if(intConSalFin==1) stbSalFin.append(" UNION ALL ");
//                 stbSalFin.append("SELECT "+rstLoc.getString("anio")+" AS anio, "+rstLoc.getString("mes")+" AS mes, "+dblSalFin+" AS salfin ");
//                 intConSalFin=1;
//
//
//
//             }
//         }
//         rstLoc.close();
//         rstLoc=null;
//
//         System.out.println("----> "+ strSql );
//        // stmLoc.executeUpdate(strSql);
//
//       System.out.println("Saldo Inicial "+ stbSalIni.toString() );
//       System.out.println("Saldo Final "+ stbSalFin.toString() );
//
//  /*******************************************************************************************/
//
//
//
//           String sql2 =" SELECT anio , mes , saldo_inicial, abs(ventas), saldo_final,   (((saldo_inicial)+abs(ventas))-(saldo_final)) as final , "+   // ((abs(saldo_inicial)+abs(ventas))-abs(saldo_final)) as final , "+
//           " case when  fecha_fin > current_date  then  abs((fecha_ini-current_date)) else  abs(fecha_ini-fecha_fin)  end as dias   FROM ( "+
//           " SELECT  anio, mes , fecha_ini , fecha_fin " +
//
//           " ,( SELECT  salini FROM(  "+stbSalIni.toString()+" ) as a WHERE a.anio=Salx.anio and a.mes=Salx.mes  " +
//           " ) as saldo_inicial "+
//
//           " ,( SELECT  salfin FROM(  "+stbSalFin.toString()+" ) as a WHERE a.anio=Salx.anio and a.mes=Salx.mes  " +
//           " ) as saldo_final  "+
//
//           " ,(" +
//           "    Select   sum(a.nd_tot) as valor_total  from tbm_cabmovinv as a" +
//           "    WHERE "+strSqlAux+" a.st_reg not in ('I','E') and a.fe_doc between  Salx.fecha_act and Salx.fecha_fin   and a.co_tipdoc in (1,3,28,17,51) " +
//           "   "+strSqlAux3+"  ) as ventas " +
//           " FROM (" +
//           " " + stbins2.toString() + " "+
//           " ) AS  Salx  ) AS y  order by anio , mes ";
//
//            System.out.println(" Query: "+ sql2 ) ;
//
//           rst=stm.executeQuery(sql2);
//
//
//
//
//             if( (objParSis.getCodigoUsuario()==1) || (objParSis.getCodigoUsuario()==24)  ){
//                        vecRegCom.add("Saldo Inicial");
//                        vecRegPag.add("Ventas");
//                        vecRegRet.add("Saldo Final");
//                        vecRegDif.add("Cobros ");
//                        vecRegDias.add(" ");
//                        vecRegRat.add("Ratios.");
//             }else{
//                        vecRegCom.add(" ");
//                        vecRegPag.add(" ");
//                        vecRegRet.add("CxC");
//                        vecRegDif.add("Cobros ");
//                        vecRegDias.add(" ");
//                        vecRegRat.add("Ratios.");
//             }
//
//
//                        double dlbCom=0;
//                        double dlbPag=0;
//                        double dlbRet=0;
//                        double dlbDif=0;
//
//                         int intVal=0;
//
//                    while (rst.next())
//                    {
//                        vecRegCom.add( new Double(rst.getDouble(3)));
//                        vecRegPag.add( new Double(rst.getDouble(4)));
//                        vecRegRet.add( new Double(rst.getDouble(5)));
//                        vecRegDif.add( new Double(rst.getDouble(6)));
//                        vecRegDias.add( rst.getString(7) );
//                        vecRegRat.add("");
//
//                        if(intVal==0) dlbCom +=rst.getDouble(3);
//                        dlbPag +=rst.getDouble(4);
//                        dlbRet =rst.getDouble(5);
//                        dlbDif +=rst.getDouble(6);
//
//                        intVal=1;
//                    }
//
//                        dlbCom = objUti.redondear(dlbCom,2);
//                        dlbPag = objUti.redondear(dlbPag,2);
//                        dlbRet = objUti.redondear(dlbRet,2);
//                        dlbDif = objUti.redondear(dlbDif,2);
//
//
//                        vecRegCom.add(new Double(dlbCom));
//                        vecRegPag.add(new Double(dlbPag));
//                        vecRegRet.add(new Double(dlbRet));
//                        vecRegDif.add(new Double(dlbDif));
//                        vecRegDias.add(" ");
//                        vecRegRat.add("");
//
//
//                        java.util.Vector vecData = new java.util.Vector();
//                        vecData.add(vecRegCom);
//                        vecData.add(vecRegPag);
//                        vecData.add(vecRegRet);
//                        vecData.add(vecRegDif);
//                        vecData.add(vecRegDias);
//                        vecData.add(vecRegRat);
//
//                          vecDatOrg.add(vecRegCom);
//                          vecDatOrg.add(vecRegPag);
//                          vecDatOrg.add(vecRegRet);
//                          vecDatOrg.add(vecRegDif);
//                          vecDatOrg.add(vecRegDias);
//                          vecDatOrg.add(vecRegRat);
//
//                    con.close();
//                    con=null;
//
//
//                     Librerias.ZafTblUti.ZafTblMod.ZafTblMod oblModSal = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
//                     oblModSal.setHeader(vecCabOrg2);
//                     tblDatSal.setModel(oblModSal);
//
//
//                       for(int i=1; i<tblDatSal.getColumnCount(); i++){
//                             objTblCelRenLbl=null;
//                             javax.swing.table.TableColumnModel tcmAux=tblDatSal.getColumnModel();
//                             objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
//                             objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
//                             objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//                             objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
//                             objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
//                             tcmAux.getColumn( i ).setCellRenderer(objTblCelRenLbl);
//                             tcmAux=null;
//                        }
//
//
//                        javax.swing.table.TableColumnModel tcmAux=tblDatSal.getColumnModel();
//                        for(int i=1; i<(tblDatSal.getColumnCount()-tblDat.getColumnCount())+1; i++){
//                                tcmAux.getColumn(i).setWidth(0);
//                                tcmAux.getColumn(i).setMaxWidth(0);
//                                tcmAux.getColumn(i).setMinWidth(0);
//                                tcmAux.getColumn(i).setPreferredWidth(0);
//                                tcmAux.getColumn(i).setResizable(false);
//                        }
//                        tcmAux=null;
//
//
//                     oblModSal.setData(vecData);
//                     tblDatSal.setModel(oblModSal);
//
//                       int  intCon=0;
//                       dblSalIni=0;
//                       dblSalFin=0;
//                       double dblVentas=0;
//
//                       for(int i=(tblDatSal.getColumnCount()-tblDat.getColumnCount()+1) ; i< tblDatSal.getColumnCount()-1; i++){
//
//                             if(intCon==0){
//                                 tblDatSal.setValueAt(tblDatSal.getValueAt(0, i).toString() , 0 , tblDatSal.getColumnCount()-1);
//                                 dblSalIni = Double.parseDouble(tblDatSal.getValueAt(0, i).toString());
//                                 dblSalFin = Double.parseDouble(tblDatSal.getValueAt(2, tblDatSal.getColumnCount()-1 ).toString());
//                             }
//                            dblVentas = dblVentas + Double.parseDouble(tblDatSal.getValueAt(1, i).toString());
//                            intCon=1;
//                        }
//
//                     tblDatSal.setValueAt( ""+dblVentas  , 1 , tblDatSal.getColumnCount()-1);
//                     dblVentas = (dblSalIni+dblVentas)-dblSalFin;
//                     tblDatSal.setValueAt( ""+dblVentas  , 3 , tblDatSal.getColumnCount()-1);
//
//
//
//                    new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDatSal);
//
//                    blnRes=true;
//
//                     vecRegCom=null;
//                     vecRegPag=null;
//                     vecRegRet=null;
//                     vecRegDif=null;
//                     vecRegDias=null;
//                     vecRegRat=null;
//
//
//        }}
//        catch (java.sql.SQLException e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);
//        }
//
//        return blnRes;
//    }
//    
  





  /*******************SOLO AFECTE HA DIARIO Y NO AFECTA TABLA   tbm_cabmovinv   tbm_cabpag   *********************************/
 /*
    SELECT (sum(nd_mondeb - nd_monhab) ) as saldo FROM (
     select co_emp, co_loc, co_tipdoc, co_dia, co_reg  from (
     SELECT * FROM(
      select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.co_doc as a1, detpag.co_doc as a2
      FROM tbm_cabdia as a
      INNER JOIN tbm_detdia as b ON(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia)
      LEFT JOIN tbm_cabmovinv as cabmov on (cabmov.co_emp=a.co_emp and cabmov.co_loc=a.co_loc and cabmov.co_tipdoc=a.co_tipdoc and cabmov.co_doc=a.co_dia  )
      LEFT JOIN tbm_cabpag as detpag on (detpag.co_emp=a.co_emp and detpag.co_loc=a.co_loc and detpag.co_tipdoc=a.co_tipdoc and detpag.co_doc=a.co_dia )
        WHERE a.st_reg not in ('E','I')  AND a.co_emp=1 and b.co_cta in ( 29, 30, 31, 2690, 2691 )
        AND a.fe_dia  between  '2011-01-01' and '2011-01-31'
     ) AS x WHERE a1 is null and a2 is null
     ) as x
     GROUP BY co_emp, co_loc, co_tipdoc, co_dia, co_reg
    ) AS a
    INNER JOIN tbm_detdia AS b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg)

*/


  /*******************SOLO CABMOVINV*********************************/
 /*
  select (sum(nd_mondeb - nd_monhab) ) as saldo from (
    select co_emp, co_loc, co_tipdoc, co_dia, co_reg from (
     select * from (
      select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.tx_nomcli as tx_nomcli1,
      cabmov.co_cli as cocli1, a1.co_empgrp
       from tbm_cabdia as a
          inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia)
          inner join tbm_cabmovinv as cabmov on (cabmov.co_emp=a.co_emp and cabmov.co_loc=a.co_loc and cabmov.co_tipdoc=a.co_tipdoc and cabmov.co_doc=a.co_dia  )
          INNER JOIN tbm_cli AS a1 ON (a1.co_emp=cabmov.co_emp and a1.co_cli=cabmov.co_cli )
          where a.st_reg not in ('E','I')
          AND a.co_emp=1 and b.co_cta in ( 29, 30, 31, 2690, 2691 )
          and a.fe_dia   between  '2011-01-01' and '2011-01-31'   and a1.co_empgrp is null
     ) as x
     ) as x
     group by co_emp, co_loc, co_tipdoc, co_dia, co_reg
     ) as a
     inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg)

*/



    /*******************SOLO RETE *********************************/
/*
     select sum(saldo) from (
     select
     case when  abs(saldo) in (abs(abono)) then saldo else (saldo + abs(abono)) end as saldo
     from (
     select co_dia, (sum(mondeb) - sum(monhab))  as saldo, abono from (
     select  b.nd_mondeb as  mondeb,  b.nd_monhab as  monhab  , abono , a.co_dia
     from (


     select co_emp, co_loc, co_tipdoc, co_dia, co_reg, sum(nd_abo) as abono  from (
     select *
     from (
       select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.tx_nomcli as tx_nomcli1,
      cabmov.co_cli as cocli1, detpag.nd_abo , a1.co_empgrp
       from tbm_cabdia as a
      inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia)
      inner join tbm_cabpag as cabpag on (cabpag.co_emp=a.co_emp and cabpag.co_loc=a.co_loc and cabpag.co_tipdoc=a.co_tipdoc and cabpag.co_doc=a.co_dia )
      left join tbm_detpag as detpag on (detpag.co_emp=cabpag.co_emp and detpag.co_loc=cabpag.co_loc and detpag.co_tipdoc=cabpag.co_tipdoc and detpag.co_doc=cabpag.co_doc )
      left join tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag )
      left join tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc)
      LEFT JOIN tbm_cli AS a1 ON (a1.co_emp=cabmov.co_emp and a1.co_cli=cabmov.co_cli )
      where  a.st_reg not in ('E','I')
         AND a.co_emp=1 and b.co_cta in ( 29, 30, 31, 2690, 2691 )
      and a.fe_dia   between  '2011-01-01' and '2011-05-31'  and a.co_tipdoc in (
           select co_tipdoc from tbm_cabtipdoc where co_emp=1 and  co_cat in ( 12 )  group by co_tipdoc
       )
     ) as x
     ) as x
     group by co_emp, co_loc, co_tipdoc, co_dia, co_reg


     ) as a
     inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg)
     order by co_dia
     ) as x   group by co_dia, abono
     ) as x
     ) as x

*/


                 /*******************SIN RETE *********************************/
  /*
      select    (sum(mondeb - monhab) ) as saldo from (

     select  a.co_tipdoc, a.co_dia,


     case when a.co_cat in (20) then
      case when a.abono < 0 then
       case when b.nd_mondeb in (0) then abs(a.abono) else  case when  b.nd_mondeb in (abs(a.abono)) then b.nd_mondeb else b.nd_mondeb+abs((b.nd_mondeb-abs(a.abono))) end end
      else 0 end
     else  b.nd_mondeb end as  mondeb ,


     case when a.co_cat in (20) then
      case when a.abono > 0 then
       case when b.nd_monhab in (0) then abs(a.abono) else  case when b.nd_monhab in (abs(a.abono)) then b.nd_monhab else b.nd_monhab+abs((b.nd_monhab-abs(a.abono))) end end
      else 0 end
     else  b.nd_monhab end as  monhab



     from (


     select co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cat , sum(nd_abo) as abono  from (
     select *
     from (
      select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.tx_nomcli as tx_nomcli1,
      cabmov.co_cli as cocli1, detpag.nd_abo , a1.co_empgrp, b1.co_cat
       from tbm_cabdia as a
          inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia)
          inner join tbm_cabtipdoc as b1 on(b1.co_emp=b.co_emp and b1.co_loc=b.co_loc and b1.co_tipdoc=b.co_tipdoc  )
          inner join tbm_cabpag as cabpag on (cabpag.co_emp=a.co_emp and cabpag.co_loc=a.co_loc and cabpag.co_tipdoc=a.co_tipdoc and cabpag.co_doc=a.co_dia )
          left join tbm_detpag as detpag on (detpag.co_emp=cabpag.co_emp and detpag.co_loc=cabpag.co_loc and detpag.co_tipdoc=cabpag.co_tipdoc and detpag.co_doc=cabpag.co_doc )
          left join tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag )
          left join tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc)
          LEFT JOIN tbm_cli AS a1 ON (a1.co_emp=cabmov.co_emp and a1.co_cli=cabmov.co_cli )
          where  a.st_reg not in ('E','I')
            AND a.co_emp=1 and b.co_cta in ( 29, 30, 31, 2690, 2691 )
          and a.fe_dia  between  '2011-01-01' and '2011-05-31'  and a.co_tipdoc  not in (
                select co_tipdoc from tbm_cabtipdoc where co_emp=1 and  co_cat in ( 12 )  group by co_tipdoc
           )
     ) as x
     ) as x
     group by co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cat



      ) as a
     inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg)

     ) as x

*/



private double SaldoIniFinTuval(java.sql.Connection conn, String strSqlAux, String strCta, String strFecIni, String strFecFin, String strInCExl){
  String sqlQuerySaldo="";
  double dblValor=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  try{
   if(conn!=null){
      stmLoc=conn.createStatement();

   sqlQuerySaldo="SELECT sum(saldo) as saldo FROM ( " +
   "  /*******************SOLO DIARIO*********************************/ "+
   " select (sum(nd_mondeb - nd_monhab) ) as saldo from ( "+
   "  select co_emp, co_loc, co_tipdoc, co_dia, co_reg  from ( "+
   "  select * "+
   "  from ( "+
   "   select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.co_doc as a1, detpag.co_doc as a2   "+
   "    from tbm_cabdia as a "+
   "       inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "+
   "       inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta in ( '"+strCta+"'   )) "+
   "       left join tbm_cabmovinv as cabmov on (cabmov.co_emp=a.co_emp and cabmov.co_loc=a.co_loc and cabmov.co_tipdoc=a.co_tipdoc and cabmov.co_doc=a.co_dia  ) "+
   "       left join tbm_cabpag as detpag on (detpag.co_emp=a.co_emp and detpag.co_loc=a.co_loc and detpag.co_tipdoc=a.co_tipdoc and detpag.co_doc=a.co_dia ) "+
   "       where  "+strSqlAux+"  a.st_reg not in ('E','I') and a.fe_dia  between  '"+strFecIni+"' and '"+strFecFin+"' "+
   "  ) as x where a1 is null and a2 is null "+
   "  ) as x "+
   "  group by co_emp, co_loc, co_tipdoc, co_dia, co_reg "+
   "  ) as a "+
   "  inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "+
   "  /****************************************************/ "+
   "  UNION ALL "+
   "  /*******************SOLO CABMOVINV*********************************/ "+
   "  select (sum(nd_mondeb - nd_monhab) ) as saldo from ( "+
   "  select co_emp, co_loc, co_tipdoc, co_dia, co_reg  from ( "+
   "  select *, case when cocli1 in ( 3516 ,602, 603,2600,1039 ) then '*' else '' end as estado "+
   "  from ( "+
   "   select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.tx_nomcli as tx_nomcli1, "+
   "   cabmov.co_cli as cocli1 "+ 
   "    from tbm_cabdia as a "+
   "       inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "+
   "       inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta in ( '"+strCta+"' )) "+
   "       inner join tbm_cabmovinv as cabmov on (cabmov.co_emp=a.co_emp and cabmov.co_loc=a.co_loc and cabmov.co_tipdoc=a.co_tipdoc and cabmov.co_doc=a.co_dia  ) "+
   "        where "+strSqlAux+" a.st_reg not in ('E','I') and a.fe_dia   between  '"+strFecIni+"' and '"+strFecFin+"'  "+
   "  ) as x "+
   "  ) as x  "+strInCExl+" "+
   "  group by co_emp, co_loc, co_tipdoc, co_dia, co_reg "+
   "  ) as a "+
   "  inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "+
   "  /****************************************************/ "+
   "  UNION ALL "+
   "  /*******************SIN RETE *********************************/ "+
   "  select sum(saldo) from ( "+
   "  select "+
   "  case when  abs(saldo) in (abs(abono)) then saldo else (saldo + abs(abono)) end as saldo "+
   "  from ( "+
   "  select co_dia, (sum(mondeb) - sum(monhab))  as saldo, abono from ( "+
   "  select  b.nd_mondeb as  mondeb,  b.nd_monhab as  monhab  , abono , a.co_dia "+
   "  from ( "+
   "  select co_emp, co_loc, co_tipdoc, co_dia, co_reg, sum(nd_abo) as abono  from ( "+
   "  select * "+
   "  , case when cocli1 in ( 3516 ,602, 603,2600,1039 ) then '*' else '' end as estado "+
   "  from ( "+
   "    select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.tx_nomcli as tx_nomcli1, "+
   "   cabmov.co_cli as cocli1, detpag.nd_abo "+
   "    from tbm_cabdia as a "+
   "   inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "+
   "   inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta in ( '"+strCta+"'  )) "+
   "   inner join tbm_cabpag as cabpag on (cabpag.co_emp=a.co_emp and cabpag.co_loc=a.co_loc and cabpag.co_tipdoc=a.co_tipdoc and cabpag.co_doc=a.co_dia ) "+
   "   left join tbm_detpag as detpag on (detpag.co_emp=cabpag.co_emp and detpag.co_loc=cabpag.co_loc and detpag.co_tipdoc=cabpag.co_tipdoc and detpag.co_doc=cabpag.co_doc ) "+
   "   left join tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag ) "+
   "   left join tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc) "+
   "   where  "+strSqlAux+"  a.st_reg not in ('E','I') and a.fe_dia   between  '"+strFecIni+"' and '"+strFecFin+"'  and a.co_tipdoc in (25, 73)   "+
   "  ) as x "+
   "  ) as x   "+strInCExl+" "+
   "  group by co_emp, co_loc, co_tipdoc, co_dia, co_reg "+
   "  ) as a "+
   "   inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "+
   "  order by co_dia "+
   "  ) as x   group by co_dia, abono "+
   "  ) as x "+
   "  ) as x "+
   "  /****************************************************/ "+
   "  UNION ALL "+
   "  /*******************SIN RETE *********************************/ "+
   "  select    (sum(mondeb - monhab) ) as saldo from ( "+
   "  select  a.co_tipdoc, a.co_dia, "+
   "  case when a.co_tipdoc in (62) then "+
   "   case when a.abono < 0 then "+
   "    case when b.nd_mondeb in (0) then abs(a.abono) else  case when  b.nd_mondeb in (abs(a.abono)) then b.nd_mondeb else b.nd_mondeb+abs((b.nd_mondeb-abs(a.abono))) end end "+
   "   else 0 end "+
   "  else  b.nd_mondeb end as  mondeb , "+
   "  case when a.co_tipdoc in (62) then "+
   "   case when a.abono > 0 then "+
   "    case when b.nd_monhab in (0) then abs(a.abono) else  case when b.nd_monhab in (abs(a.abono)) then b.nd_monhab else b.nd_monhab+abs((b.nd_monhab-abs(a.abono))) end end "+
   "   else 0 end "+
   "  else  b.nd_monhab end as  monhab "+
   "  from ( "+
   "  select co_emp, co_loc, co_tipdoc, co_dia, co_reg, sum(nd_abo) as abono  from ( "+
   "  select * "+
   "  , case when cocli1 in ( 3516 ,602, 603,2600,1039 ) then '*' else '' end as estado "+
   "  from ( "+
   "   select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.tx_nomcli as tx_nomcli1, "+
   "   cabmov.co_cli as cocli1, detpag.nd_abo "+
   "    from tbm_cabdia as a "+
   "       inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "+
   "       inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta in (  '"+strCta+"'   )) "+
   "       inner join tbm_cabpag as cabpag on (cabpag.co_emp=a.co_emp and cabpag.co_loc=a.co_loc and cabpag.co_tipdoc=a.co_tipdoc and cabpag.co_doc=a.co_dia ) "+
   "       left join tbm_detpag as detpag on (detpag.co_emp=cabpag.co_emp and detpag.co_loc=cabpag.co_loc and detpag.co_tipdoc=cabpag.co_tipdoc and detpag.co_doc=cabpag.co_doc ) "+
   "       left join tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag )  "+
   "       left join tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc) "+
   "       where  "+strSqlAux+" a.st_reg not in ('E','I') and a.fe_dia  between  '"+strFecIni+"' and '"+strFecFin+"'  and a.co_tipdoc  not in ( 25,73 )    "+
   "  ) as x "+
   "  ) as x    "+strInCExl+" "+
   "  group by co_emp, co_loc, co_tipdoc, co_dia, co_reg "+
   "   ) as a "+
   "  inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "+
   "  ) as x "+
   "  /****************************************************/ "+
   "  ) AS X ";
   rstLoc=stmLoc.executeQuery(sqlQuerySaldo);
   while(rstLoc.next()){
       dblValor=rstLoc.getDouble("saldo");
   }
   rstLoc.close();
   rstLoc=null;
   stmLoc.close();
   stmLoc=null;
   
  }}catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
   catch (Exception e){   objUti.mostrarMsgErr_F1(this, e);  }
 return dblValor;
}

private double SaldoIniFinDimul(java.sql.Connection conn, String strSqlAux, String strCta, String strFecIni, String strFecFin, String strInCExl){
  String sqlQuerySaldo="";
  double dblValor=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  try{
   if(conn!=null){
      stmLoc=conn.createStatement();

   sqlQuerySaldo="SELECT sum(saldo) as saldo FROM ( " +
   "  /*******************SOLO DIARIO*********************************/ "+
   " select (sum(nd_mondeb - nd_monhab) ) as saldo from ( "+
   "  select co_emp, co_loc, co_tipdoc, co_dia, co_reg  from ( "+
   "  select * "+
   "  from ( "+
   "   select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.co_doc as a1, detpag.co_doc as a2   "+
   "    from tbm_cabdia as a "+
   "       inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "+
   "       inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta in ( '"+strCta+"'   )) "+
   "       left join tbm_cabmovinv as cabmov on (cabmov.co_emp=a.co_emp and cabmov.co_loc=a.co_loc and cabmov.co_tipdoc=a.co_tipdoc and cabmov.co_doc=a.co_dia  ) "+
   "       left join tbm_cabpag as detpag on (detpag.co_emp=a.co_emp and detpag.co_loc=a.co_loc and detpag.co_tipdoc=a.co_tipdoc and detpag.co_doc=a.co_dia ) "+
   "       where  "+strSqlAux+"  a.st_reg not in ('E','I') and a.fe_dia  between  '"+strFecIni+"' and '"+strFecFin+"' "+
   "  ) as x where a1 is null and a2 is null "+
   "  ) as x "+
   "  group by co_emp, co_loc, co_tipdoc, co_dia, co_reg "+
   "  ) as a "+
   "  inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "+
   "  /****************************************************/ "+
   "  UNION ALL "+
   "  /*******************SOLO CABMOVINV*********************************/ "+
   "  select (sum(nd_mondeb - nd_monhab) ) as saldo from ( "+
   "  select co_emp, co_loc, co_tipdoc, co_dia, co_reg  from ( "+
   "  select *, case when cocli1 in ( 3117, 498, 2294, 886 ) then '*' else '' end as estado "+
   "  from ( "+
   "   select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.tx_nomcli as tx_nomcli1, "+
   "   cabmov.co_cli as cocli1 "+
   "    from tbm_cabdia as a "+
   "       inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "+
   "       inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta in ( '"+strCta+"' )) "+
   "       inner join tbm_cabmovinv as cabmov on (cabmov.co_emp=a.co_emp and cabmov.co_loc=a.co_loc and cabmov.co_tipdoc=a.co_tipdoc and cabmov.co_doc=a.co_dia  ) "+
   "        where "+strSqlAux+" a.st_reg not in ('E','I') and a.fe_dia   between  '"+strFecIni+"' and '"+strFecFin+"'  "+
   "  ) as x "+
   "  ) as x  "+strInCExl+" "+
   "  group by co_emp, co_loc, co_tipdoc, co_dia, co_reg "+
   "  ) as a "+
   "  inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "+
   "  /****************************************************/ "+
   "  UNION ALL "+
   "  /*******************SIN RETE *********************************/ "+
   "  select sum(saldo) from ( "+
   "  select "+
   "  case when  abs(saldo) in (abs(abono)) then saldo else (saldo + abs(abono)) end as saldo "+
   "  from ( "+
   "  select co_dia, (sum(mondeb) - sum(monhab))  as saldo, abono from ( "+
   "  select a.co_loc, a.co_tipdoc, b.nd_mondeb as  mondeb,  b.nd_monhab as  monhab  , abono , a.co_dia "+
   "  from ( "+
   "  select co_emp, co_loc, co_tipdoc, co_dia, co_reg, sum(nd_abo) as abono  from ( "+
   "  select * "+
   "  , case when cocli1 in ( 3117, 498, 2294, 886 ) then '*' else '' end as estado "+
   "  from ( "+
   "    select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.tx_nomcli as tx_nomcli1, "+
   "   cabmov.co_cli as cocli1, detpag.nd_abo "+
   "    from tbm_cabdia as a "+
   "   inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "+
   "   inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta in ( '"+strCta+"'  )) "+
   "   inner join tbm_cabpag as cabpag on (cabpag.co_emp=a.co_emp and cabpag.co_loc=a.co_loc and cabpag.co_tipdoc=a.co_tipdoc and cabpag.co_doc=a.co_dia ) "+
   "   left join tbm_detpag as detpag on (detpag.co_emp=cabpag.co_emp and detpag.co_loc=cabpag.co_loc and detpag.co_tipdoc=cabpag.co_tipdoc and detpag.co_doc=cabpag.co_doc ) "+
   "   left join tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag ) "+
   "   left join tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc) "+
   "   where  "+strSqlAux+"  a.st_reg not in ('E','I') and a.fe_dia   between  '"+strFecIni+"' and '"+strFecFin+"'  and a.co_tipdoc in ( 25, 26, 73 )   "+
   "  ) as x "+
   "  ) as x   "+strInCExl+" "+
   "  group by co_emp, co_loc, co_tipdoc, co_dia, co_reg "+
   "  ) as a "+
   "   inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "+
   "  order by co_dia "+
   "  ) as x   group by co_loc, co_tipdoc, co_dia , abono order by co_dia "+
   "  ) as x "+
   "  ) as x "+
   "  /****************************************************/ "+
   "  UNION ALL "+
   "  /*******************SIN RETE *********************************/ "+
   "  select    (sum(mondeb - monhab) ) as saldo from ( "+
   "  select  a.co_loc,  a.co_tipdoc, a.co_dia, "+
   "  case when a.co_tipdoc in (63) then "+
   "   case when a.abono < 0 then "+
   "    case when b.nd_mondeb in (0) then abs(a.abono) else  case when  b.nd_mondeb in (abs(a.abono)) then b.nd_mondeb else b.nd_mondeb+abs((b.nd_mondeb-abs(a.abono))) end end "+
   "   else 0 end "+
   "  else  b.nd_mondeb end as  mondeb , "+
   "  case when a.co_tipdoc in (63) then "+
   "   case when a.abono > 0 then "+
   "    case when b.nd_monhab in (0) then abs(a.abono) else  case when b.nd_monhab in (abs(a.abono)) then b.nd_monhab else b.nd_monhab+abs((b.nd_monhab-abs(a.abono))) end end "+
   "   else 0 end "+
   "  else  b.nd_monhab end as  monhab "+
   "  from ( "+
   "  select co_emp, co_loc, co_tipdoc, co_dia, co_reg, sum(nd_abo) as abono  from ( "+
   "  select * "+
   "  , case when cocli1 in ( 3117, 498, 2294, 886 ) then '*' else '' end as estado "+
   "  from ( "+
   "   select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.tx_nomcli as tx_nomcli1, "+
   "   cabmov.co_cli as cocli1, detpag.nd_abo "+
   "    from tbm_cabdia as a "+
   "       inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "+
   "       inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta in (  '"+strCta+"'   )) "+
   "       inner join tbm_cabpag as cabpag on (cabpag.co_emp=a.co_emp and cabpag.co_loc=a.co_loc and cabpag.co_tipdoc=a.co_tipdoc and cabpag.co_doc=a.co_dia ) "+
   "       left join tbm_detpag as detpag on (detpag.co_emp=cabpag.co_emp and detpag.co_loc=cabpag.co_loc and detpag.co_tipdoc=cabpag.co_tipdoc and detpag.co_doc=cabpag.co_doc ) "+
   "       left join tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag )  "+
   "       left join tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc) "+
   "       where  "+strSqlAux+" a.st_reg not in ('E','I') and a.fe_dia  between  '"+strFecIni+"' and '"+strFecFin+"'  and a.co_tipdoc  not in ( 25, 26, 73 )    "+
   "  ) as x "+
   "  ) as x    "+strInCExl+" "+
   "  group by co_emp, co_loc, co_tipdoc, co_dia, co_reg "+
   "   ) as a "+
   "  inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "+
   "  ) as x "+
   "  /****************************************************/ "+
   "  ) AS X ";
   rstLoc=stmLoc.executeQuery(sqlQuerySaldo);
   while(rstLoc.next()){
       dblValor=rstLoc.getDouble("saldo");
   }
   rstLoc.close();
   rstLoc=null;
   stmLoc.close();
   stmLoc=null;

  }}catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
   catch (Exception e){   objUti.mostrarMsgErr_F1(this, e);  }
 return dblValor;
}

private double SaldoIniFinCaste(java.sql.Connection conn, String strSqlAux, String strCta, String strFecIni, String strFecFin, String strInCExl){
  String sqlQuerySaldo="";
  double dblValor=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  try{
   if(conn!=null){
      stmLoc=conn.createStatement();

   sqlQuerySaldo="SELECT sum(saldo) as saldo FROM ( " +
   "  /*******************SOLO DIARIO*********************************/ "+
   " select (sum(nd_mondeb - nd_monhab) ) as saldo from ( "+
   "  select co_emp, co_loc, co_tipdoc, co_dia, co_reg  from ( "+
   "  select * "+
   "  from ( "+
   "   select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.co_doc as a1, detpag.co_doc as a2   "+
   "    from tbm_cabdia as a "+
   "       inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "+
   "       inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta in ( '"+strCta+"'   )) "+
   "       left join tbm_cabmovinv as cabmov on (cabmov.co_emp=a.co_emp and cabmov.co_loc=a.co_loc and cabmov.co_tipdoc=a.co_tipdoc and cabmov.co_doc=a.co_dia  ) "+
   "       left join tbm_cabpag as detpag on (detpag.co_emp=a.co_emp and detpag.co_loc=a.co_loc and detpag.co_tipdoc=a.co_tipdoc and detpag.co_doc=a.co_dia ) "+
   "       where  "+strSqlAux+"  a.st_reg not in ('E','I') and a.fe_dia  between  '"+strFecIni+"' and '"+strFecFin+"' "+
   "  ) as x where a1 is null and a2 is null "+
   "  ) as x "+
   "  group by co_emp, co_loc, co_tipdoc, co_dia, co_reg "+
   "  ) as a "+
   "  inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "+
   "  /****************************************************/ "+
   "  UNION ALL "+
   "  /*******************SOLO CABMOVINV*********************************/ "+
   "  select (sum(nd_mondeb - nd_monhab) ) as saldo from ( "+
   "  select co_emp, co_loc, co_tipdoc, co_dia, co_reg  from ( "+
   "  select *, case when cocli1 in ( 2854, 446, 2105, 789  ) then '*' else '' end as estado "+
   "  from ( "+
   "   select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.tx_nomcli as tx_nomcli1, "+
   "   cabmov.co_cli as cocli1 "+
   "    from tbm_cabdia as a "+
   "       inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "+
   "       inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta in ( '"+strCta+"' )) "+
   "       inner join tbm_cabmovinv as cabmov on (cabmov.co_emp=a.co_emp and cabmov.co_loc=a.co_loc and cabmov.co_tipdoc=a.co_tipdoc and cabmov.co_doc=a.co_dia  ) "+
   "        where "+strSqlAux+" a.st_reg not in ('E','I') and a.fe_dia   between  '"+strFecIni+"' and '"+strFecFin+"'  "+
   "  ) as x "+
   "  ) as x  "+strInCExl+" "+
   "  group by co_emp, co_loc, co_tipdoc, co_dia, co_reg "+
   "  ) as a "+
   "  inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "+
   "  /****************************************************/ "+
   "  UNION ALL "+
   "  /*******************SIN RETE *********************************/ "+
   "  select sum(saldo) from ( "+
   "  select "+
   "  case when  abs(saldo) in (abs(abono)) then saldo else (saldo + abs(abono)) end as saldo "+
   "  from ( "+
   "  select co_dia, (sum(mondeb) - sum(monhab))  as saldo, abono from ( "+
   "  select a.co_loc, a.co_tipdoc, b.nd_mondeb as  mondeb,  b.nd_monhab as  monhab  , abono , a.co_dia "+
   "  from ( "+
   "  select co_emp, co_loc, co_tipdoc, co_dia, co_reg, sum(nd_abo) as abono  from ( "+
   "  select * "+
   "  , case when cocli1 in ( 2854, 446, 2105, 789  ) then '*' else '' end as estado "+
   "  from ( "+
   "    select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.tx_nomcli as tx_nomcli1, "+
   "   cabmov.co_cli as cocli1, detpag.nd_abo "+
   "    from tbm_cabdia as a "+
   "   inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "+
   "   inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta in ( '"+strCta+"'  )) "+
   "   inner join tbm_cabpag as cabpag on (cabpag.co_emp=a.co_emp and cabpag.co_loc=a.co_loc and cabpag.co_tipdoc=a.co_tipdoc and cabpag.co_doc=a.co_dia ) "+
   "   left join tbm_detpag as detpag on (detpag.co_emp=cabpag.co_emp and detpag.co_loc=cabpag.co_loc and detpag.co_tipdoc=cabpag.co_tipdoc and detpag.co_doc=cabpag.co_doc ) "+
   "   left join tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag ) "+
   "   left join tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc) "+
   "   where  "+strSqlAux+"  a.st_reg not in ('E','I') and a.fe_dia   between  '"+strFecIni+"' and '"+strFecFin+"'  and a.co_tipdoc in ( 25, 26, 73 )   "+
   "  ) as x "+
   "  ) as x   "+strInCExl+" "+
   "  group by co_emp, co_loc, co_tipdoc, co_dia, co_reg "+
   "  ) as a "+
   "   inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "+
   "  order by co_dia "+
   "  ) as x   group by co_loc, co_tipdoc, co_dia , abono order by co_dia "+
   "  ) as x "+
   "  ) as x "+
   "  /****************************************************/ "+
   "  UNION ALL "+
   "  /*******************SIN RETE *********************************/ "+
   "  select    (sum(mondeb - monhab) ) as saldo from ( "+
   "  select  a.co_loc,  a.co_tipdoc, a.co_dia, "+
   "  case when a.co_tipdoc in (63) then "+
   "   case when a.abono < 0 then "+
   "    case when b.nd_mondeb in (0) then abs(a.abono) else  case when  b.nd_mondeb in (abs(a.abono)) then b.nd_mondeb else b.nd_mondeb+abs((b.nd_mondeb-abs(a.abono))) end end "+
   "   else 0 end "+
   "  else  b.nd_mondeb end as  mondeb , "+
   "  case when a.co_tipdoc in (63) then "+
   "   case when a.abono > 0 then "+
   "    case when b.nd_monhab in (0) then abs(a.abono) else  case when b.nd_monhab in (abs(a.abono)) then b.nd_monhab else b.nd_monhab+abs((b.nd_monhab-abs(a.abono))) end end "+
   "   else 0 end "+
   "  else  b.nd_monhab end as  monhab "+
   "  from ( "+
   "  select co_emp, co_loc, co_tipdoc, co_dia, co_reg, sum(nd_abo) as abono  from ( "+
   "  select * "+
   "  , case when cocli1 in ( 2854, 446, 2105, 789  ) then '*' else '' end as estado "+
   "  from ( "+
   "   select  b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg, nd_mondeb , nd_monhab, cabmov.tx_nomcli as tx_nomcli1, "+
   "   cabmov.co_cli as cocli1, detpag.nd_abo "+
   "    from tbm_cabdia as a "+
   "       inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "+
   "       inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta in (  '"+strCta+"'   )) "+
   "       inner join tbm_cabpag as cabpag on (cabpag.co_emp=a.co_emp and cabpag.co_loc=a.co_loc and cabpag.co_tipdoc=a.co_tipdoc and cabpag.co_doc=a.co_dia ) "+
   "       left join tbm_detpag as detpag on (detpag.co_emp=cabpag.co_emp and detpag.co_loc=cabpag.co_loc and detpag.co_tipdoc=cabpag.co_tipdoc and detpag.co_doc=cabpag.co_doc ) "+
   "       left join tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag )  "+
   "       left join tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc) "+
   "       where  "+strSqlAux+" a.st_reg not in ('E','I') and a.fe_dia  between  '"+strFecIni+"' and '"+strFecFin+"'  and a.co_tipdoc  not in ( 25, 26, 73 )    "+
   "  ) as x "+
   "  ) as x    "+strInCExl+" "+
   "  group by co_emp, co_loc, co_tipdoc, co_dia, co_reg "+
   "   ) as a "+
   "  inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "+
   "  ) as x "+
   "  /****************************************************/ "+
   "  ) AS X ";

   //System.out.println("--> "+ sqlQuerySaldo );

   rstLoc=stmLoc.executeQuery(sqlQuerySaldo);
   while(rstLoc.next()){
       dblValor=rstLoc.getDouble("saldo");
   }
   rstLoc.close();
   rstLoc=null;
   stmLoc.close();
   stmLoc=null;

  }}catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
   catch (Exception e){   objUti.mostrarMsgErr_F1(this, e);  }
 return dblValor;
}








     
//
//     private boolean cargarDatosSaldoCom(){
//        boolean blnRes=false;
//        int intval3=0;
//         try{
//
//         String fecdesde="2006-05-22",fechasta="2006-05-22";
//         int intMesDes=-1, intMesHas=-1;
//         int intAniDes=0, intAniHas=01;
//         String strSqlAux3="";
//
//         if (objSelDat.chkIsSelected())
//                    {
//                        switch (objSelDat.getTypeSeletion())
//                        {
//                            case 1: //Búsqueda por rangos
//                                  intval3=1;
//
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                   //System.out.println(">> "+intMesDes + " <> "+ intMesHas + " <>" + intAniDes  + " <> " + intAniHas  );
//
//
//                                  break;
//                            case 2: //Fechas mayores o iguales a "Desde".
//                                   intval3=2;
//
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                   intAniHas=intAniDes;
//
//                                   fecdesde =  objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = "12/31/"+String.valueOf(objSelDat.getYearSelected());
//
//
//                             //   System.out.println("22 >> "+ intMesDes + " <>" + intAniDes + " -- " + fecdesde  );
//
//                               break;
//                            case 3: //Fechas menores o iguales a "Hasta".
//                                  intval3=3;
//
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                   intAniDes=intAniHas;
//
//                                   fechasta =  objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = "01/01/"+String.valueOf(objSelDat.getYearSelected());
//
//                                // System.out.println(">> "+ intMesHas + " <>" + intAniHas  + " -- " + fechasta );
//
//
//                                break;
//                            case 4: //Todo.
//                                System.out.println("Error.........");
//                                intval3=4;
//                                break;
//
//                        }
//                    }
//
//
//
//
//
//             vecDatOrg.clear();
//             con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos() );
//             if (con!=null)
//               {
//                   stm=con.createStatement();
//
//                 vecRegCom=new Vector();
//                 vecRegPag=new Vector();
//                 vecRegRet=new Vector();
//                 vecRegDif=new Vector();
//
//
//           vecCabOrg.clear();
//           vecCabOrg.add(" ");
//           StringBuffer stbins2=new StringBuffer();
//           int x=0;
//
//
//
//           if(intval3==2){
//           int hasta= 11;
//           int desde = intMesDes;
//           for(int y=intAniDes; y<=intAniHas; y++){
//           //  if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+"");
//
//
//                    if (x>0)
//                      stbins2.append(" UNION ALL ");
//                      stbins2.append(" select "+y+" as anio, "+(i+1)+" as mes, date('"+y+"-"+(i+1)+"-01')-1  AS fecha_ini , date('"+y+"-"+(i+1)+"-01') AS fecha_act  " +
//                      ", date(date('"+y+"-"+(i+1)+"-01')+ interval '1 month')-1 as fecha_fin  ");
//
//                      //select 2006 as anio, 11 as mes, date('2006-11-01')-1  as fecha_ini  , date(date('2006-11-01')+ interval '1 month')-1 as fecha_fin
//
//
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//          if(intval3==3){
//           int hasta=intMesHas;
//           int desde = 0;
//           for(int y=intAniDes; y<=intAniHas; y++){
//            // if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+"");
//
//
//                    if (x>0)
//                      stbins2.append(" UNION ALL ");
//                      stbins2.append(" select "+y+" as anio, "+(i+1)+" as mes, date('"+y+"-"+(i+1)+"-01')-1  AS fecha_ini , date('"+y+"-"+(i+1)+"-01') AS fecha_act  " +
//                      ", date(date('"+y+"-"+(i+1)+"-01')+ interval '1 month')-1 as fecha_fin  ");
//
//
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//
//          if(intval3==1){
//           int hasta=11;
//           int desde = intMesDes;
//           for(int y=intAniDes; y<=intAniHas; y++){
//             if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+"");
//
//
//                   if (x>0)
//                      stbins2.append(" UNION ALL ");
//                      stbins2.append(" select "+y+" as anio, "+(i+1)+" as mes, date('"+y+"-"+(i+1)+"-01')-1  AS fecha_ini , date('"+y+"-"+(i+1)+"-01') AS fecha_act  " +
//                      ", date(date('"+y+"-"+(i+1)+"-01')+ interval '1 month')-1 as fecha_fin  ");
//
//
//
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//        vecCabOrg.add("Total");
//
//
//          String strSqlAux="a.co_emp="+objParSis.getCodigoEmpresa()+" and ";
//          String strSqlAux2=" cabmov.co_emp="+objParSis.getCodigoEmpresa()+" and ";
//          if(local.getSelectedIndex()>0) {
//            strSqlAux = strSqlAux+ "   a.co_loc = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//            strSqlAux2 = strSqlAux2+"  cabmov.co_loc = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//          }
//
//
//          String strCodcta = "";
//
//          if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
//              strSqlAux =" ";
//              strSqlAux2 =" ";
//
//          }
//
//
//
//           strSqlAux3="";
//           String strSqlAuxExcComp = "";
//           String strSqlAuxTuv = "";
//           String strSqlAuxCas="";
//           String strSqlAuxNos="";
//           String strSqlAuxDim="";
//           String strInCExc="";
//           String strSalExc="T";
//
//          if(chkEscComp.isSelected()){
//            strSqlAux3 = " " +
//            "  AND NOT (cabmov.co_emp=1 AND cabmov.co_cli in ( 3516, 602, 603, 2600, 1039 ) ) "+
//            "  AND NOT (cabmov.co_emp=2 AND cabmov.co_cli in ( 2854, 446, 2105, 789 ) ) " +
//            "  AND NOT ( cabmov.co_emp=3 AND cabmov.co_cli in ( 2858, 453, 2107, 832 ) )  " +
//            "  AND NOT (cabmov.co_emp=4 AND cabmov.co_cli in ( 3117,498, 2294, 886 )  ) ";
//
//            strSqlAuxTuv = "  AND NOT (cabmov.co_emp=1 AND cabmov.co_cli in ( 3516, 602, 603, 2600, 1039 ) ) ";
//
//            strSqlAuxCas=" AND NOT (cabmov.co_emp=2 AND cabmov.co_cli in ( 2854, 446, 2105, 789 ) ) ";
//
//            strSqlAuxNos="  AND NOT ( cabmov.co_emp=3 AND cabmov.co_cli in ( 2858, 453, 2107, 832 ) ) ";
//
//            strSqlAuxDim=" AND NOT (cabmov.co_emp=4 AND cabmov.co_cli in ( 3117,498, 2294, 886 )  ) ";
//
//            strInCExc="  where estado=''  ";
//            strSalExc="E";
//          }
//
//           if(objParSis.getCodigoEmpresa()==1){  strSqlAuxExcComp=strSqlAuxTuv;  }
//           if(objParSis.getCodigoEmpresa()==2){  strSqlAuxExcComp=strSqlAuxCas; }
//           if(objParSis.getCodigoEmpresa()==3){  strSqlAuxExcComp=strSqlAuxNos; }
//           if(objParSis.getCodigoEmpresa()==4){  strSqlAuxExcComp=strSqlAuxDim; }
//
//
//
//          if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
//             strSqlAux =" ";
//             strSqlAux2 =" ";
//             strSqlAuxExcComp=strSqlAux3;
//          }
//
//
//
//
///*******************************************************************************************/
//         String strSql="";
//         java.sql.Statement stmLoc;
//         java.sql.ResultSet rstLoc;
//         StringBuffer stbSalIni;
//         StringBuffer stbSalFin;
//         int intConSalIni=0;
//         int intConSalFin=0;
//         double dblSalIni=0;
//         double dblSalFin=0;
//         stbSalIni=new StringBuffer();
//         stbSalFin=new StringBuffer();
//
//         stmLoc=con.createStatement();
//
//         strSql="SELECT * FROM (  "+stbins2.toString()+ " ) AS x ";
//         rstLoc=stmLoc.executeQuery(strSql);
//         strSql="";
//         while(rstLoc.next()){
//             double dblVal=0;
//
//             if(dblSalIni == 0){
//                dblVal=SaldoIniCompras(con, strSqlAux, "2.01.03.01.01", rstLoc.getString("fecha_ini"), strInCExc );
//                dblSalIni=dblVal;
//                dblSalFin=dblVal;
//                if(intConSalIni==1) stbSalIni.append(" UNION ALL ");
//                stbSalIni.append("SELECT "+rstLoc.getString("anio")+" AS anio, "+rstLoc.getString("mes")+" AS mes, "+dblSalFin+" AS salini ");
//                intConSalIni=1;
//
//                dblVal=SaldoFinCompras(con, strSqlAux, "2.01.03.01.01", rstLoc.getString("fecha_act"), rstLoc.getString("fecha_fin"), strInCExc );
//                dblSalFin=dblSalIni+dblVal;
//                dblSalIni=dblSalFin;
//                if(intConSalFin==1) stbSalFin.append(" UNION ALL ");
//                stbSalFin.append("SELECT "+rstLoc.getString("anio")+" AS anio, "+rstLoc.getString("mes")+" AS mes, "+dblSalFin+" AS salfin ");
//                intConSalFin=1;
//             }else{
//                dblSalFin=dblSalIni;
//                if(intConSalIni==1) stbSalIni.append(" UNION ALL ");
//                stbSalIni.append("SELECT "+rstLoc.getString("anio")+" AS anio, "+rstLoc.getString("mes")+" AS mes, "+dblSalFin+" AS salini ");
//                intConSalIni=1;
//
//                dblVal=SaldoFinCompras(con, strSqlAux, "2.01.03.01.01", rstLoc.getString("fecha_act"), rstLoc.getString("fecha_fin"), strInCExc );
//                dblSalFin=dblSalIni+dblVal;
//                dblSalIni=dblSalFin;
//                if(intConSalFin==1) stbSalFin.append(" UNION ALL ");
//                stbSalFin.append("SELECT "+rstLoc.getString("anio")+" AS anio, "+rstLoc.getString("mes")+" AS mes, "+dblSalFin+" AS salfin ");
//                intConSalFin=1;
//
//             }
//         }
//         rstLoc.close();
//         rstLoc=null;
//
//       System.out.println(""+ stbSalIni.toString() );
//       System.out.println(""+ stbSalFin.toString() );
//
//  /*******************************************************************************************/
//
//
//
//           String sql2 =" SELECT anio , mes , abs(saldo_inicial) as saldo_inicial, abs(ventas) as compras, abs(saldo_final) as saldo_final,  ((abs(saldo_inicial)+abs(ventas))-abs(saldo_final)) as final , "+
//           " case when  fecha_fin > current_date  then  abs((fecha_ini-current_date)) else  abs(fecha_ini-fecha_fin)  end as dias   FROM ( "+
//           " SELECT  anio, mes , fecha_ini , fecha_fin " +
//
//           " ,( SELECT  salini FROM(  "+stbSalIni.toString()+" ) as a WHERE a.anio=Salx.anio and a.mes=Salx.mes  " +
//           " ) as saldo_inicial "+
//
//           " ,( SELECT  salfin FROM(  "+stbSalFin.toString()+" ) as a WHERE a.anio=Salx.anio and a.mes=Salx.mes  " +
//           " ) as saldo_final  "+
//
//           " ,(" +
//           "    Select   sum(cabmov.nd_tot) as valor_total  from tbm_cabmovinv as cabmov" +
//           "    WHERE "+strSqlAux2+" cabmov.st_reg not in ('I','E') and cabmov.fe_doc between  Salx.fecha_act and Salx.fecha_fin   and cabmov.co_tipdoc in (2,4) " +
//           " "+strSqlAuxExcComp+" "+
//           " ) as ventas " +
//           " FROM (" +
//           " " + stbins2.toString() + " "+
//           " ) AS  Salx  ) AS y  order by anio , mes ";
//
//
//           System.out.println("Saldo--> "+sql2);
//
//           rst=stm.executeQuery(sql2);
//
//
//                        vecRegCom.add("Saldo Inicial");
//                        vecRegPag.add("Compras");
//                        vecRegRet.add("Saldo Final");
//                        vecRegDif.add("Final ");
//
//
//
//                        double dlbCom=0;
//                        double dlbPag=0;
//                        double dlbRet=0;
//                        double dlbDif=0;
//
//
//                    while (rst.next())
//                    {
//                        vecRegCom.add( new Double(rst.getDouble(3)));
//                        vecRegPag.add( new Double(rst.getDouble(4)));
//                        vecRegRet.add( new Double(rst.getDouble(5)));
//                        vecRegDif.add( new Double(rst.getDouble(6)));
//
//                        dlbCom +=rst.getDouble(3);
//                        dlbPag +=rst.getDouble(4);
//                        dlbRet +=rst.getDouble(5);
//                        dlbDif +=rst.getDouble(6);
//
//                    }
//
//                        dlbCom = objUti.redondear(dlbCom,2);
//                        dlbPag = objUti.redondear(dlbPag,2);
//                        dlbRet = objUti.redondear(dlbRet,2);
//                        dlbDif = objUti.redondear(dlbDif,2);
//
//
//                        vecRegCom.add(new Double(dlbCom));
//                        vecRegPag.add(new Double(dlbPag));
//                        vecRegRet.add(new Double(dlbRet));
//                        vecRegDif.add(new Double(dlbDif));
//
//
//                        java.util.Vector vecData = new java.util.Vector();
//                        vecData.add(vecRegCom);
//                        vecData.add(vecRegPag);
//                        vecData.add(vecRegRet);
//                        vecData.add(vecRegDif);
//
//                          vecDatOrg.add(vecRegCom);
//                          vecDatOrg.add(vecRegPag);
//                          vecDatOrg.add(vecRegRet);
//                          vecDatOrg.add(vecRegDif);
//
//                    con.close();
//                    con=null;
//
//
//                     Librerias.ZafTblUti.ZafTblMod.ZafTblMod oblModSal = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
//                     oblModSal.setHeader(vecCabOrg);
//                     tblDatSal.setModel(oblModSal);
//
//
//
//
//
//                        for(int i=1; i<tblDatSal.getColumnCount(); i++){
//                             objTblCelRenLbl=null;
//                             javax.swing.table.TableColumnModel tcmAux=tblDatSal.getColumnModel();
//                             objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
//                             objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
//                             objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//                             objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
//                             objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
//                             tcmAux.getColumn( i ).setCellRenderer(objTblCelRenLbl);
//                             tcmAux=null;
//                        }
//
//
//
//                     oblModSal.setData(vecData);
//                     tblDatSal.setModel(oblModSal);
//
//
//                    new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDatSal);
//
//                    blnRes=true;
//
//
//
//        }}
//        catch (java.sql.SQLException e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);
//        }
//
//        return blnRes;
//    }




//private double SaldoIniCompras(java.sql.Connection conn, String strSqlAux, String strCta, String strFecFin, String strInCExl){
//  String sqlQuerySaldo="";
//  double dblValor=0;
//  java.sql.Statement stmLoc;
//  java.sql.ResultSet rstLoc;
//  try{
//   if(conn!=null){
//      stmLoc=conn.createStatement();
//
//   sqlQuerySaldo="  SELECT sum(saldo) as saldo FROM ( " +
//   "  /********************************************************/ "+
//   " SELECT sum(saldo) as saldo FROM ( "+
//   " select co_emp, co_loc, co_tipdoc, co_dia, CASE WHEN abs(abono) > abs(saldo) THEN saldo ELSE "+
//   "  case when  abs(saldo) in (abs(abono)) then saldo END "+
//   "  END AS saldo from ( "+
//   "  select a.co_emp, a.co_loc, a.co_tipdoc, a.co_dia, (sum(nd_mondeb) - sum(nd_monhab))  as saldo, a.abono from ( "+
//   "  select co_emp, co_loc, co_tipdoc, co_dia, co_reg, sum(nd_abo) as abono  from ( "+
//   "  select *, " +
//   " CASE WHEN co_emp IN (1) THEN "+
//   "  CASE WHEN co_cli in ( 3516, 602, 603, 2600, 1039 ) THEN '*' ELSE '' END "+
//   " ELSE "+
//   " CASE WHEN co_emp IN (2) THEN "+
//   "  CASE WHEN co_cli in ( 2854, 446, 2105, 789 ) THEN '*' ELSE '' END "+
//   " ELSE "+
//   " CASE WHEN co_emp IN (3) THEN "+
//   "   CASE WHEN co_cli in ( 2858, 453, 2107, 832 ) THEN '*' ELSE '' END "+
//   " ELSE "+
//   "  CASE WHEN co_emp IN (4) THEN "+
//   "    CASE WHEN  co_cli in ( 3117,498, 2294, 886 ) THEN '*' ELSE '' END "+
//   "  END "+
//   " END END  END "+
//   " AS  estado "+
//   "  from ( "+
//   "     select b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg , cabmov.co_cli, detpag.nd_abo from tbm_cabdia as a "+
//   "    inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "+
//   "     inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta IN ( '"+strCta+"' ) ) "+
//   "       inner join tbm_cabpag as cabpag on (cabpag.co_emp=a.co_emp and cabpag.co_loc=a.co_loc and cabpag.co_tipdoc=a.co_tipdoc and cabpag.co_doc=a.co_dia ) "+
//   "       inner join tbm_detpag as detpag on (detpag.co_emp=cabpag.co_emp and detpag.co_loc=cabpag.co_loc and detpag.co_tipdoc=cabpag.co_tipdoc and detpag.co_doc=cabpag.co_doc )   "+
//   "       left join tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag ) "+
//   "       left join tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc ) "+
//   "  where "+strSqlAux+"  a.co_tipdoc in(2,4,32,33)  and a.st_reg not in ('E','I') and a.fe_dia <= '"+strFecFin+"'   "+
//   "  ) as x "+
//   "  ) as x    "+strInCExl+" "+
//   "  GROUP BY  co_emp, co_loc, co_tipdoc, co_dia, co_reg "+
//   "  ) as a "+
//   "  inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "+
//   "  GROUP BY  a.co_emp, a.co_loc, a.co_tipdoc, a.co_dia , a.abono "+
//   "  ) as x "+
//   "  ) as x "+
//   "  /****************************************************/ "+
//   "  UNION ALL "+
//   "  /****************************************************/ "+
//   " select (sum(nd_mondeb) - sum(nd_monhab) ) as saldo FROM ( "+
//   " select co_emp, co_loc, co_tipdoc, co_dia, co_reg  from ( "+
//   " select *, "+
//   " CASE WHEN co_emp IN (1) THEN "+
//   "  CASE WHEN co_cli in ( 3516, 602, 603, 2600, 1039 ) THEN '*' ELSE '' END "+
//   " ELSE "+
//   " CASE WHEN co_emp IN (2) THEN "+
//   "  CASE WHEN co_cli in ( 2854, 446, 2105, 789 ) THEN '*' ELSE '' END "+
//   " ELSE "+
//   " CASE WHEN co_emp IN (3) THEN "+
//   "   CASE WHEN co_cli in ( 2858, 453, 2107, 832 ) THEN '*' ELSE '' END "+
//   " ELSE "+
//   "  CASE WHEN co_emp IN (4) THEN "+
//   "    CASE WHEN  co_cli in ( 3117,498, 2294, 886 ) THEN '*' ELSE '' END "+
//   "  END "+
//   " END END  END "+
//   " AS  estado "+
//   " from ( "+
//   " select b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg , cabmov.co_cli from tbm_cabdia as a "+
//   " inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "+
//   " inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta IN ( '"+strCta+"' ) ) "+
//   "       inner join tbm_cabmovinv as cabmov on (cabmov.co_emp=a.co_emp and cabmov.co_loc=a.co_loc and cabmov.co_tipdoc=a.co_tipdoc and cabmov.co_doc=a.co_dia ) "+
//   " where "+strSqlAux+" a.co_tipdoc in(2,4,32,33)  and a.st_reg not in ('E','I') and a.fe_dia <=  '"+strFecFin+"' "+
//   " ) as x "+
//   " ) as x   "+strInCExl+" "+
//   " GROUP BY  co_emp, co_loc, co_tipdoc, co_dia, co_reg "+
//   " ) as a "+
//   " inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "+
//   "  /****************************************************/ "+
//   " ) AS x ";
//
//   rstLoc=stmLoc.executeQuery(sqlQuerySaldo);
//   while(rstLoc.next()){
//       dblValor=rstLoc.getDouble("saldo");
//   }
//   rstLoc.close();
//   rstLoc=null;
//   stmLoc.close();
//   stmLoc=null;
//
//  }}catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
//   catch (Exception e){   objUti.mostrarMsgErr_F1(this, e);  }
// return dblValor;
//}
//
//
//private double SaldoFinCompras(java.sql.Connection conn, String strSqlAux, String strCta, String strFecIni, String strFecFin, String strInCExl){
//  String sqlQuerySaldo="";
//  double dblValor=0;
//  java.sql.Statement stmLoc;
//  java.sql.ResultSet rstLoc;
//  try{
//   if(conn!=null){
//      stmLoc=conn.createStatement();
//
//   sqlQuerySaldo="  SELECT sum(saldo) as saldo FROM ( " +
//   "  /********************************************************/ "+
//   " SELECT sum(saldo) as saldo FROM ( "+
//   " select co_emp, co_loc, co_tipdoc, co_dia, CASE WHEN abs(abono) > abs(saldo) THEN saldo ELSE "+
//   "  case when  abs(saldo) in (abs(abono)) then saldo END "+
//   "  END AS saldo from ( "+
//   "  select a.co_emp, a.co_loc, a.co_tipdoc, a.co_dia, (sum(nd_mondeb) - sum(nd_monhab))  as saldo, a.abono from ( "+
//   "  select co_emp, co_loc, co_tipdoc, co_dia, co_reg, sum(nd_abo) as abono  from ( "+
//   "  select *, "+
//   " CASE WHEN co_emp IN (1) THEN "+
//   "  CASE WHEN co_cli in ( 3516, 602, 603, 2600, 1039 ) THEN '*' ELSE '' END "+
//   " ELSE "+
//   " CASE WHEN co_emp IN (2) THEN "+
//   "  CASE WHEN co_cli in ( 2854, 446, 2105, 789 ) THEN '*' ELSE '' END "+
//   " ELSE "+
//   " CASE WHEN co_emp IN (3) THEN "+
//   "   CASE WHEN co_cli in ( 2858, 453, 2107, 832 ) THEN '*' ELSE '' END "+
//   " ELSE "+
//   "  CASE WHEN co_emp IN (4) THEN "+
//   "    CASE WHEN  co_cli in ( 3117,498, 2294, 886 ) THEN '*' ELSE '' END "+
//   "  END "+
//   " END END  END "+
//   " AS  estado "+
//   "  from ( "+
//   "     select b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg , cabmov.co_cli, detpag.nd_abo from tbm_cabdia as a "+
//   "    inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "+
//   "     inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta IN ( '"+strCta+"' ) ) "+
//   "       inner join tbm_cabpag as cabpag on (cabpag.co_emp=a.co_emp and cabpag.co_loc=a.co_loc and cabpag.co_tipdoc=a.co_tipdoc and cabpag.co_doc=a.co_dia ) "+
//   "       inner join tbm_detpag as detpag on (detpag.co_emp=cabpag.co_emp and detpag.co_loc=cabpag.co_loc and detpag.co_tipdoc=cabpag.co_tipdoc and detpag.co_doc=cabpag.co_doc )   "+
//   "       left join tbm_pagmovinv as pagmov on (pagmov.co_emp=detpag.co_emp and pagmov.co_loc=detpag.co_locpag and pagmov.co_tipdoc=detpag.co_tipdocpag and pagmov.co_doc=detpag.co_docpag and pagmov.co_reg=detpag.co_regpag ) "+
//   "       left join tbm_cabmovinv as cabmov on (cabmov.co_emp=pagmov.co_emp and cabmov.co_loc=pagmov.co_loc and cabmov.co_tipdoc=pagmov.co_tipdoc and cabmov.co_doc=pagmov.co_doc ) "+
//   "  where "+strSqlAux+"  a.co_tipdoc in(2,4,32,33)  and a.st_reg not in ('E','I') and a.fe_dia  between  '"+strFecIni+"' and '"+strFecFin+"'   "+
//   "  ) as x "+
//   "  ) as x    "+strInCExl+" "+
//   "  GROUP BY  co_emp, co_loc, co_tipdoc, co_dia, co_reg "+
//   "  ) as a "+
//   "  inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "+
//   "  GROUP BY  a.co_emp, a.co_loc, a.co_tipdoc, a.co_dia , a.abono "+
//   "  ) as x "+
//   "  ) as x "+
//   "  /****************************************************/ "+
//   "  UNION ALL "+
//   "  /****************************************************/ "+
//   " select (sum(nd_mondeb) - sum(nd_monhab) ) as saldo FROM ( "+
//   " select co_emp, co_loc, co_tipdoc, co_dia, co_reg  from ( "+
//   " select *, "+
//   " CASE WHEN co_emp IN (1) THEN "+
//   "  CASE WHEN co_cli in ( 3516, 602, 603, 2600, 1039 ) THEN '*' ELSE '' END "+
//   " ELSE "+
//   " CASE WHEN co_emp IN (2) THEN "+
//   "  CASE WHEN co_cli in ( 2854, 446, 2105, 789 ) THEN '*' ELSE '' END "+
//   " ELSE "+
//   " CASE WHEN co_emp IN (3) THEN "+
//   "   CASE WHEN co_cli in ( 2858, 453, 2107, 832 ) THEN '*' ELSE '' END "+
//   " ELSE "+
//   "  CASE WHEN co_emp IN (4) THEN "+
//   "    CASE WHEN  co_cli in ( 3117,498, 2294, 886 ) THEN '*' ELSE '' END "+
//   "  END "+
//   " END END  END "+
//   " AS  estado "+
//   " from ( "+
//   " select b.co_emp, b.co_loc, b.co_tipdoc, b.co_dia, b.co_reg , cabmov.co_cli from tbm_cabdia as a "+
//   " inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia) "+
//   " inner join tbm_placta as d3 on (d3.co_emp=b.co_emp and d3.co_cta=b.co_cta and d3.tx_codcta IN ( '"+strCta+"' ) ) "+
//   "       inner join tbm_cabmovinv as cabmov on (cabmov.co_emp=a.co_emp and cabmov.co_loc=a.co_loc and cabmov.co_tipdoc=a.co_tipdoc and cabmov.co_doc=a.co_dia ) "+
//   " where "+strSqlAux+" a.co_tipdoc in(2,4,32,33)  and a.st_reg not in ('E','I') and a.fe_dia  between  '"+strFecIni+"' and '"+strFecFin+"' "+
//   " ) as x "+
//   " ) as x   "+strInCExl+" "+
//   " GROUP BY  co_emp, co_loc, co_tipdoc, co_dia, co_reg "+
//   " ) as a "+
//   " inner join tbm_detdia as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_dia=a.co_dia and b.co_reg=a.co_reg) "+
//   "  /****************************************************/ "+
//   " ) AS x ";
//
//   rstLoc=stmLoc.executeQuery(sqlQuerySaldo);
//   while(rstLoc.next()){
//       dblValor=rstLoc.getDouble("saldo");
//   }
//   rstLoc.close();
//   rstLoc=null;
//   stmLoc.close();
//   stmLoc=null;
//
//  }}catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
//   catch (Exception e){   objUti.mostrarMsgErr_F1(this, e);  }
// return dblValor;
//}
//
     
     
//     
//       private boolean cargarDatosTodos(){
//        boolean blnRes=false;
//        int intval3=0;
//         try{
//        
//         String fecdesde="2006-05-22",fechasta="2006-05-22";  
//         int intMesDes=-1, intMesHas=-1; 
//         int intAniDes=0, intAniHas=01; 
//         
//         if (objSelDat.chkIsSelected())
//                    {
//                        switch (objSelDat.getTypeSeletion())
//                        {
//                            case 1: //Búsqueda por rangos
//                                  intval3=1;
//                                   
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                  
//                                //   System.out.println(">> "+intMesDes + " <> "+ intMesHas + " <>" + intAniDes  + " <> " + intAniHas  );    
//                                   
//                                 
//                                  break;
//                            case 2: //Fechas mayores o iguales a "Desde".
//                                   intval3=2;
//                                
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                               
//                                   intAniHas=intAniDes;
//                                
//                                   fecdesde =  objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = "12/31/"+String.valueOf(objSelDat.getYearSelected());
//                               
//                                
//                             //   System.out.println("22 >> "+ intMesDes + " <>" + intAniDes + " -- " + fecdesde  );    
//                              
//                               break;
//                            case 3: //Fechas menores o iguales a "Hasta".
//                                  intval3=3;
//                               
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                               
//                                   intAniDes=intAniHas;
//                                   
//                                   fechasta =  objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = "01/01/"+String.valueOf(objSelDat.getYearSelected());
//                                   
//                                // System.out.println(">> "+ intMesHas + " <>" + intAniHas  + " -- " + fechasta );    
//                              
//                                
//                                break;
//                            case 4: //Todo.
//                                System.out.println("Error........."); 
//                                intval3=4;
//                                break;
//                              
//                        }
//                    }
//         
//         
//         
//        
//         
//        
//           if(intval3==4) return true;
//         
//        
//             vecDatOrg.clear();  
//             con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos() );
//             if (con!=null)
//               {  
//                   stm=con.createStatement();
//                 
//                 vecRegCom=new Vector();
//                 vecRegPag=new Vector();   
//                 vecRegDif=new Vector();   
//                 
//           
//           vecCabOrg.clear();
//           vecCabOrg.add(" "); 
//           StringBuffer stbins=new StringBuffer(); 
//           int x=0;
//        
//           
//           
//           if(intval3==2){
//           int hasta= 11;
//           int desde = intMesDes; 
//           for(int y=intAniDes; y<=intAniHas; y++){
//           //  if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" "); 
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" "); 
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" "); 
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" "); 
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" "); 
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+""); 
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" "); 
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" "); 
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" "); 
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" "); 
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+""); 
//                   if (x>0)  
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0; 
//            hasta=11; 
//          }
//          }
//             
//          if(intval3==3){
//           int hasta=intMesHas;
//           int desde = 0; 
//           for(int y=intAniDes; y<=intAniHas; y++){
//            // if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" "); 
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" "); 
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" "); 
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" "); 
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" "); 
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+""); 
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" "); 
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" "); 
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" "); 
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" "); 
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+""); 
//                   if (x>0)  
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0; 
//            hasta=11; 
//          }
//          }
//           
//           
//          if(intval3==1){
//           int hasta=11;
//           int desde = intMesDes; 
//           for(int y=intAniDes; y<=intAniHas; y++){
//             if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                   
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" "); 
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" "); 
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" "); 
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" "); 
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" "); 
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+""); 
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" "); 
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" "); 
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" "); 
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" "); 
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+""); 
//                         
//                   
//                    
//                   if (x>0)  
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0; 
//            hasta=11; 
//          }
//          }
//               
//        vecCabOrg.add("Total");   
//           
//        
//          String strSqlAux="a.co_emp="+objParSis.getCodigoEmpresa()+" and ",strSqlAux2=" c.co_emp="+objParSis.getCodigoEmpresa()+" and ";
//          if(local.getSelectedIndex()>0) {
//            strSqlAux = strSqlAux+ "   a.co_loc = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//            strSqlAux2 = strSqlAux2+"  c.co_locpag = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//          }
//            
//          if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
//              strSqlAux =" ";
//              strSqlAux2 =" ";
//          }
//          
//          
//           String sql_1 =" select anio1, mes1, valor_total, valor, " +
//                      "case when valor is null then valor_total " +
//                      " else (valor_total - valor) end  as diferencia " +
//                      " from ( " +
//                      " select * from ( " +
//                      " select extract(year from a.fe_doc) as anio1, extract(month from a.fe_doc) as mes1, sum(abs(a.nd_tot)) as valor_total from tbm_cabmovinv as a " +
//                      " where  "+ strSqlAux +"  a.co_tipdoc="+txtCodtipdoc.getText()+" and a.st_reg not in ('I','E')  " +
//                      " and a.fe_doc between '"+fecdesde+"' and '"+fechasta+"' " +
//                      " group by extract(year from a.fe_doc), extract(month from a.fe_doc) " +
//                      " ) as x  left join  " +
//                      " ( " +
//                      " SELECT extract(year from d.fe_doc) as anio2, extract(month from d.fe_doc) as mes2 , sum(abs(c.nd_Abo)) as valor FROM tbm_detpag as c " +
//                      " inner join tbm_cabpag as d on (d.co_emp=c.co_emp and d.co_loc=c.co_loc and d.co_tipdoc=c.co_tipdoc and d.co_doc=c.co_doc) " +
//                      " where  "+ strSqlAux2 +" c.co_tipdocpag="+txtCodtipdoc.getText()+" and d.st_reg not in ('I','E') " +
//                      " and d.fe_doc between '"+fecdesde+"' and '"+fechasta+"' " +
//                      "  group by extract(year from d.fe_doc), extract(month from d.fe_doc) " +
//                      " ) as y  on (y.anio2=x.anio1 and y.mes2=x.mes1) " +   
//                      " ) as x order by anio1, mes1 ";
//                      String sql_2 = "select anio, mes, round(valor_total,2) as tot, round(valor,2) as pag   from (  select * from ( "+ stbins.toString() +" ) as x ";
//                      sql_2 = sql_2 + " left join (" +
//                      " select * from ( " + sql_1 + " "+ 
//                      " ) as t ) as t on(t.anio1=x.anio and t.mes1=x.mes)  ) as x order by anio,mes";      
//          
//                        
//                      String sql_3 =" select anio1, mes1, valor_total, valor, " +
//                      "case when valor is null then valor_total " +
//                      " else (valor_total - valor) end  as diferencia " +
//                      " from ( " +
//                      " select * from ( " +
//                      " select extract(year from a.fe_doc) as anio1, extract(month from a.fe_doc) as mes1, sum(abs(b.mo_pag)) as valor_total from tbm_cabmovinv as a " +
//                      " inner join tbm_pagmovinv as b on (b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_doc=a.co_doc) "+
//
//                      " where  "+ strSqlAux +"  a.co_tipdoc in (28,3) and a.st_reg not in ('I','E')  " +
//                      " and a.fe_doc between '"+fecdesde+"' and '"+fechasta+"' " +
//                      " group by extract(year from a.fe_doc), extract(month from a.fe_doc) " +
//                      " ) as x  left join  " +
//                      " ( " +
//                      " SELECT extract(year from d.fe_doc) as anio2, extract(month from d.fe_doc) as mes2 , sum(abs(c.nd_Abo)) as valor FROM tbm_detpag as c " +
//                      " inner join tbm_cabpag as d on (d.co_emp=c.co_emp and d.co_loc=c.co_loc and d.co_tipdoc=c.co_tipdoc and d.co_doc=c.co_doc) " +
//                      " where  "+ strSqlAux2 +" c.co_tipdocpag in (28,3) and d.st_reg not in ('I','E') " +
//                      " and d.fe_doc between '"+fecdesde+"' and '"+fechasta+"' " +
//                      "  group by extract(year from d.fe_doc), extract(month from d.fe_doc) " +
//                      " ) as y  on (y.anio2=x.anio1 and y.mes2=x.mes1) " +   
//                      " ) as x order by anio1, mes1 ";
//                      String sql_4 = "select anio, mes, (round(valor_total,2)*-1) as tot,( round(valor,2)*-1) as pag   from (  select * from ( "+ stbins.toString() +" ) as x ";
//                      sql_4 = sql_4 + " left join (" +
//                      " select * from ( " + sql_3 + " "+ 
//                      " ) as t ) as t on(t.anio1=x.anio and t.mes1=x.mes)  ) as x order by anio,mes";   
//                      
//                      
//                      
//                      String sql2="select mes ,sum(tot),sum(pag) , ( sum(tot) - sum(pag) ) from (" +
//                      " select * from ( "+sql_2+" "+
//                      " ) as a " +
//                      " union all " +
//                      " select * from ( "+sql_4+" ) as b " +
//                      " ) as x  group by anio, mes order by anio, mes ";
//                      
//                      System.out.println(""+ sql2 );
//                     
//                      
//                      
//                      //***************************************************
//                     
//                     
//                       
//                       
//                    rst=stm.executeQuery(sql2);
//               
//                   
//                        vecRegCom.add("Compras");
//                        vecRegPag.add("Pagos");
//                        vecRegDif.add("Diferencia");
//                    
//                        double dlbCom=0;
//                        double dlbPag=0;
//                        double dlbDif=0;
//                        
//                            
//                    while (rst.next())
//                    {
//                        vecRegCom.add( new Double(rst.getDouble(2)));
//                        vecRegPag.add( new Double(rst.getDouble(3)));
//                        vecRegDif.add( new Double(rst.getDouble(4)));
//                        
//                        dlbCom +=rst.getDouble(2);
//                        dlbPag +=rst.getDouble(3);
//                        dlbDif +=rst.getDouble(4);
//                        
//                    }
//                      
//                        dlbCom = objUti.redondear(dlbCom,2);
//                        dlbPag = objUti.redondear(dlbPag,2);
//                        dlbDif = objUti.redondear(dlbDif,2);
//                           
//                        
//                        vecRegCom.add(new Double(dlbCom));
//                        vecRegPag.add(new Double(dlbPag));
//                        vecRegDif.add(new Double(dlbDif));
//            
//                      
//                        java.util.Vector vecData = new java.util.Vector();
//                        vecData.add(vecRegCom);        
//                        vecData.add(vecRegPag);        
//                        vecData.add(vecRegDif);           
//                        
//                          vecDatOrg.add(vecRegCom);
//                          vecDatOrg.add(vecRegPag);
//                          vecDatOrg.add(vecRegDif);
//             
//                    con.close();    
//                    con=null;
//                       
//                     Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod2=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
//                     objTblMod2.setHeader(vecCabOrg);
//                     tblDat.setModel(objTblMod2);
//                     
//                     
//                     
//                     
//                   
//                        for(int i=1; i<tblDat.getColumnCount(); i++){
//                             objTblCelRenLbl=null;
//                             javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
//                             objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
//                             objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
//                             objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//                             objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
//                             objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
//                             tcmAux.getColumn( i ).setCellRenderer(objTblCelRenLbl); 
//                             tcmAux=null;  
//                        }
//
//         
//                     
//                     objTblMod2.setData(vecData);
//                     tblDat.setModel(objTblMod2);    
//                      
//                  
//                    new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
//                     
//                    blnRes=true;
//
//
// 
//        }}
//        catch (java.sql.SQLException e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);  
//        }
//        catch (Exception e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);  
//        }
//        
//        return blnRes;
//    }
//    
//     
     
     
     
//     
//     private boolean cargarDatosNc(){
//        boolean blnRes=false;
//        int intval3=0;
//         try{
//        
//         String fecdesde="2006-05-22",fechasta="2006-05-22";  
//         int intMesDes=-1, intMesHas=-1; 
//         int intAniDes=0, intAniHas=01; 
//         
//         if (objSelDat.chkIsSelected())
//                    {
//                        switch (objSelDat.getTypeSeletion())
//                        {
//                            case 1: //Búsqueda por rangos
//                                  intval3=1;
//                                   
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                  
//                                //   System.out.println(">> "+intMesDes + " <> "+ intMesHas + " <>" + intAniDes  + " <> " + intAniHas  );    
//                                   
//                                 
//                                  break;
//                            case 2: //Fechas mayores o iguales a "Desde".
//                                   intval3=2;
//                                
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                               
//                                   intAniHas=intAniDes;
//                                
//                                   fecdesde =  objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = "12/31/"+String.valueOf(objSelDat.getYearSelected());
//                               
//                                
//                             //   System.out.println("22 >> "+ intMesDes + " <>" + intAniDes + " -- " + fecdesde  );    
//                              
//                               break;
//                            case 3: //Fechas menores o iguales a "Hasta".
//                                  intval3=3;
//                               
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                               
//                                   intAniDes=intAniHas;
//                                   
//                                   fechasta =  objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = "01/01/"+String.valueOf(objSelDat.getYearSelected());
//                                   
//                                // System.out.println(">> "+ intMesHas + " <>" + intAniHas  + " -- " + fechasta );    
//                              
//                                
//                                break;
//                            case 4: //Todo.
//                                System.out.println("Error........."); 
//                                intval3=4;
//                                break;
//                              
//                        }
//                    }
//         
//         
//         
//        
//         
//        
//           if(intval3==4) return true;
//         
//        
//             vecDatOrg.clear();  
//             con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos() );
//             if (con!=null)
//               {  
//                   stm=con.createStatement();
//                 
//                 vecRegCom=new Vector();
//                 vecRegPag=new Vector();   
//                 vecRegDif=new Vector();   
//                 
//           
//           vecCabOrg.clear();
//           vecCabOrg.add(" "); 
//           StringBuffer stbins=new StringBuffer(); 
//           int x=0;
//        
//           
//           
//           if(intval3==2){
//           int hasta= 11;
//           int desde = intMesDes; 
//           for(int y=intAniDes; y<=intAniHas; y++){
//           //  if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" "); 
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" "); 
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" "); 
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" "); 
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" "); 
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+""); 
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" "); 
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" "); 
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" "); 
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" "); 
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+""); 
//                   if (x>0)  
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0; 
//            hasta=11; 
//          }
//          }
//             
//          if(intval3==3){
//           int hasta=intMesHas;
//           int desde = 0; 
//           for(int y=intAniDes; y<=intAniHas; y++){
//            // if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" "); 
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" "); 
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" "); 
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" "); 
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" "); 
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+""); 
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" "); 
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" "); 
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" "); 
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" "); 
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+""); 
//                   if (x>0)  
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0; 
//            hasta=11; 
//          }
//          }
//           
//           
//          if(intval3==1){
//           int hasta=11;
//           int desde = intMesDes; 
//           for(int y=intAniDes; y<=intAniHas; y++){
//             if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                   
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" "); 
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" "); 
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" "); 
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" "); 
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" "); 
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+""); 
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" "); 
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" "); 
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" "); 
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" "); 
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+""); 
//                         
//                   
//                    
//                   if (x>0)  
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0; 
//            hasta=11; 
//          }
//          }
//               
//        vecCabOrg.add("Total");   
//           
//        
//          String strSqlAux="a.co_emp="+objParSis.getCodigoEmpresa()+" and ",strSqlAux2=" c.co_emp="+objParSis.getCodigoEmpresa()+" and ";
//          if(local.getSelectedIndex()>0) {
//            strSqlAux = strSqlAux+ "   a.co_loc = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//            strSqlAux2 = strSqlAux2+"  c.co_locpag = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//          }
//            
//          if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
//              strSqlAux =" ";
//              strSqlAux2 =" ";
//          }
//          
//          
//                        
//                      String sql =" select anio1, mes1, valor_total, valor, " +
//                      "case when valor is null then valor_total " +
//                      " else (valor_total - valor) end  as diferencia " +
//                      " from ( " +
//                      " select * from ( " +
//                      " select extract(year from a.fe_doc) as anio1, extract(month from a.fe_doc) as mes1, sum(abs(b.mo_pag)) as valor_total from tbm_cabmovinv as a " +
//                      " inner join tbm_pagmovinv as b on (b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_doc=a.co_doc) "+
//
//                      " where  "+ strSqlAux +"  a.co_tipdoc in (28,3) and a.st_reg not in ('I','E')  " +
//                      " and a.fe_doc between '"+fecdesde+"' and '"+fechasta+"' " +
//                      " group by extract(year from a.fe_doc), extract(month from a.fe_doc) " +
//                      " ) as x  left join  " +
//                      " ( " +
//                      " SELECT extract(year from d.fe_doc) as anio2, extract(month from d.fe_doc) as mes2 , sum(abs(c.nd_Abo)) as valor FROM tbm_detpag as c " +
//                      " inner join tbm_cabpag as d on (d.co_emp=c.co_emp and d.co_loc=c.co_loc and d.co_tipdoc=c.co_tipdoc and d.co_doc=c.co_doc) " +
//                      " where  "+ strSqlAux2 +" c.co_tipdocpag in (28,3) and d.st_reg not in ('I','E') " +
//                      " and d.fe_doc between '"+fecdesde+"' and '"+fechasta+"' " +
//                      "  group by extract(year from d.fe_doc), extract(month from d.fe_doc) " +
//                      " ) as y  on (y.anio2=x.anio1 and y.mes2=x.mes1) " +   
//                      " ) as x order by anio1, mes1 ";
//                          
//                           
//                             
//                      String sql2 = "select mes, round(valor_total,2), round(valor,2), round(diferencia,2)  from (  select * from ( "+ stbins.toString() +" ) as x ";
//                      sql2 = sql2 + " left join (" +
//                      " select * from ( " + sql + " "+ 
//                      "" +
//                      " ) as t ) as t on(t.anio1=x.anio and t.mes1=x.mes)  ) as x order by anio,mes";   
//                      
//                      System.out.println(""+ sql2 );
//                     
//                      
//                      
//                      //***************************************************
//                     
//                     
//                       
//                       
//                    rst=stm.executeQuery(sql2);
//               
//                   
//                        vecRegCom.add("Compras");
//                        vecRegPag.add("Pagos");
//                        vecRegDif.add("Diferencia");
//                    
//                        double dlbCom=0;
//                        double dlbPag=0;
//                        double dlbDif=0;
//                        
//                            
//                    while (rst.next())
//                    {
//                        vecRegCom.add( new Double(rst.getDouble(2)));
//                        vecRegPag.add( new Double(rst.getDouble(3)));
//                        vecRegDif.add( new Double(rst.getDouble(4)));
//                        
//                        dlbCom +=rst.getDouble(2);
//                        dlbPag +=rst.getDouble(3);
//                        dlbDif +=rst.getDouble(4);
//                        
//                    }
//                      
//                        dlbCom = objUti.redondear(dlbCom,2);
//                        dlbPag = objUti.redondear(dlbPag,2);
//                        dlbDif = objUti.redondear(dlbDif,2);
//                           
//                        
//                        vecRegCom.add(new Double(dlbCom));
//                        vecRegPag.add(new Double(dlbPag));
//                        vecRegDif.add(new Double(dlbDif));
//            
//                      
//                        java.util.Vector vecData = new java.util.Vector();
//                        vecData.add(vecRegCom);        
//                        vecData.add(vecRegPag);        
//                        vecData.add(vecRegDif);           
//                        
//                          vecDatOrg.add(vecRegCom);
//                          vecDatOrg.add(vecRegPag);
//                          vecDatOrg.add(vecRegDif);
//             
//                    con.close();    
//                    con=null;
//                       
//                     Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod2=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
//                     objTblMod2.setHeader(vecCabOrg);
//                     tblDat.setModel(objTblMod2);
//                     
//                     
//                     
//                     
//                   
//                        for(int i=1; i<tblDat.getColumnCount(); i++){
//                             objTblCelRenLbl=null;
//                             javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
//                             objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
//                             objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
//                             objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//                             objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
//                             objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
//                             tcmAux.getColumn( i ).setCellRenderer(objTblCelRenLbl); 
//                             tcmAux=null;  
//                        }
//
//         
//                     
//                     objTblMod2.setData(vecData);
//                     tblDat.setModel(objTblMod2);    
//                      
//                  
//                    new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
//                     
//                    blnRes=true;
//
//
// 
//        }}
//        catch (java.sql.SQLException e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);  
//        }
//        catch (Exception e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);  
//        }
//        
//        return blnRes;
//    }
//    
   
    
    
//
//
//    private boolean cargarDatosVentas_respaldo(){
//        boolean blnRes=false;
//        int intval3=0;
//         try{
//
//         String fecdesde="2006-05-22",fechasta="2006-05-22";
//         int intMesDes=-1, intMesHas=-1;
//         int intAniDes=0, intAniHas=01;
//
//         if (objSelDat.chkIsSelected())
//                    {
//                        switch (objSelDat.getTypeSeletion())
//                        {
//                            case 1: //Búsqueda por rangos
//                                  intval3=1;
//
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                   System.out.println(">> "+intMesDes + " <> "+ intMesHas + " <>" + intAniDes  + " <> " + intAniHas  );
//
//
//                                  break;
//                            case 2: //Fechas mayores o iguales a "Desde".
//                                   intval3=2;
//
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                   intAniHas=intAniDes;
//
//                                   fecdesde =  objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = "12/31/"+String.valueOf(objSelDat.getYearSelected());
//
//
//                                System.out.println("22 >> "+ intMesDes + " <>" + intAniDes + " -- " + fecdesde  );
//
//                               break;
//                            case 3: //Fechas menores o iguales a "Hasta".
//                                  intval3=3;
//
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                   intAniDes=intAniHas;
//
//                                   fechasta =  objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = "01/01/"+String.valueOf(objSelDat.getYearSelected());
//
//                                 System.out.println(">> "+ intMesHas + " <>" + intAniHas  + " -- " + fechasta );
//
//
//                                break;
//                            case 4: //Todo.
//                                System.out.println("Error.........");
//                                intval3=4;
//                                break;
//
//                        }
//                    }
//
//
//
//
//
//
//           if(intval3==4) return true;
//
//
//             vecDatOrg.clear();
//             con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos() );
//             if (con!=null)
//               {
//                   stm=con.createStatement();
//
//                 vecRegCom=new Vector();
//                 vecRegPag=new Vector();
//                 vecRegDif=new Vector();
//
//
//           vecCabOrg.clear();
//           vecCabOrg.add(" ");
//           StringBuffer stbins=new StringBuffer();
//           int x=0;
//
//
//
//           if(intval3==2){
//           int hasta= 11;
//           int desde = intMesDes;
//           for(int y=intAniDes; y<=intAniHas; y++){
//             if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+"");
//                   if (x>0)
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//          if(intval3==3){
//           int hasta=intMesHas;
//           int desde = 0;
//           for(int y=intAniDes; y<=intAniHas; y++){
//             if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+"");
//                   if (x>0)
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//
//          if(intval3==1){
//           int hasta=11;
//           int desde = intMesDes;
//           for(int y=intAniDes; y<=intAniHas; y++){
//             if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+"");
//
//
//
//                   if (x>0)
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//        vecCabOrg.add("Total");
//
//
//          String strSqlAux="a.co_emp="+objParSis.getCodigoEmpresa()+" and ",strSqlAux2=" c.co_emp="+objParSis.getCodigoEmpresa()+" and ";
//          if(local.getSelectedIndex()>0) {
//            strSqlAux = strSqlAux+ "   a.co_loc = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//            strSqlAux2 = strSqlAux2+"  c.co_loc = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//          }
//
//          if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
//              strSqlAux =" ";
//              strSqlAux2 =" ";
//          }
//
//
//
//                      String sql =" select anio1, mes1, valor_total, valor, " +
//                      "case when valor is null then valor_total " +
//                      " else (valor_total - valor) end  as diferencia " +
//                      " from ( " +
//                      " select * from ( " +
//                      " select extract(year from a.fe_doc) as anio1, extract(month from a.fe_doc) as mes1, sum(abs(a.nd_tot)) as valor_total from tbm_cabmovinv as a " +
//                      " where  "+ strSqlAux +"  a.co_tipdoc="+txtCodtipdoc.getText()+" and a.st_reg not in ('I','E')  " +
//                      " and a.fe_doc between '"+fecdesde+"' and '"+fechasta+"' " +
//                      " group by extract(year from a.fe_doc), extract(month from a.fe_doc) " +
//                      " ) as x  left join  " +
//                      " ( " +
//
//                      " SELECT extract(year from c.fe_doc) as anio2, extract(month from c.fe_doc) as mes2 , sum(abs(e.nd_mondeb)) as valor  FROM tbm_cabpag as c " +
//                     " inner join tbm_cabpag as d on (d.co_emp=c.co_emp and d.co_loc=c.co_loc and d.co_tipdoc=c.co_tipdoc and d.co_doc=c.co_doc) " +
//                      " inner join tbm_detdia as e on (e.co_emp=c.co_emp and e.co_loc=c.co_loc and e.co_tipdoc=c.co_tipdoc and e.co_dia=c.co_doc) " +
//                      " inner join tbm_placta as f on (f.co_emp=e.co_emp and f.co_cta=e.co_cta and f.st_ctafluefe='S') "+
//                      " where  "+ strSqlAux2 +" c.st_reg not in ('I','E')  and c.co_tipdoc not in (30,34,35)  " +
//                      " and c.fe_doc between '"+fecdesde+"' and '"+fechasta+"' and e.nd_mondeb>0 " +
//                      "  group by extract(year from c.fe_doc), extract(month from c.fe_doc) " +
//                      " ) as y  on (y.anio2=x.anio1 and y.mes2=x.mes1) " +
//                      " ) as x order by anio1, mes1 ";
//
//
//
//
//
//
//                      String sql2 = "select mes, round(valor_total,2), round(valor,2), round(diferencia,2)  from (  select * from ( "+ stbins.toString() +" ) as x ";
//                      sql2 = sql2 + " left join (" +
//                      " select * from ( " + sql + " "+
//                      "" +
//                      " ) as t ) as t on(t.anio1=x.anio and t.mes1=x.mes)  ) as x order by anio,mes";
//
//                      System.out.println(""+ sql2 );
//
//
//
//                      ***************************************************
//
//
//
//
//                    rst=stm.executeQuery(sql2);
//
//
//                       if(txtCodtipdoc.getText().trim().equals("2")){
//                         vecRegCom.add("Compras");
//                        vecRegPag.add("Pagos");
//                        vecRegDif.add("Diferencia");
//                     }if(txtCodtipdoc.getText().trim().equals("1")){
//                         vecRegCom.add("Ventas");
//                        vecRegPag.add("Cobros");
//                        vecRegDif.add("Diferencia");
//                    }
//
//
//                        double dlbCom=0;
//                        double dlbPag=0;
//                        double dlbDif=0;
//
//
//                    while (rst.next())
//                    {
//                        vecRegCom.add( new Double(rst.getDouble(2)));
//                        vecRegPag.add( new Double(rst.getDouble(3)));
//                        vecRegDif.add( new Double(rst.getDouble(4)));
//
//                        dlbCom +=rst.getDouble(2);
//                        dlbPag +=rst.getDouble(3);
//                        dlbDif +=rst.getDouble(4);
//
//                    }
//
//                        dlbCom = objUti.redondear(dlbCom,2);
//                        dlbPag = objUti.redondear(dlbPag,2);
//                        dlbDif = objUti.redondear(dlbDif,2);
//
//
//                        vecRegCom.add(new Double(dlbCom));
//                        vecRegPag.add(new Double(dlbPag));
//                        vecRegDif.add(new Double(dlbDif));
//
//
//                        java.util.Vector vecData = new java.util.Vector();
//                        vecData.add(vecRegCom);
//                        vecData.add(vecRegPag);
//                        vecData.add(vecRegDif);
//
//                          vecDatOrg.add(vecRegCom);
//                          vecDatOrg.add(vecRegPag);
//                          vecDatOrg.add(vecRegDif);
//
//                    con.close();
//                    con=null;
//
//                     Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod2=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
//                     objTblMod2.setHeader(vecCabOrg);
//                     tblDat.setModel(objTblMod2);
//
//
//
//
//
//                        for(int i=1; i<tblDat.getColumnCount(); i++){
//                             objTblCelRenLbl=null;
//                             javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
//                             objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
//                             objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
//                             objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//                             objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
//                             objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
//                             tcmAux.getColumn( i ).setCellRenderer(objTblCelRenLbl);
//                             tcmAux=null;
//                        }
//
//
//
//                     objTblMod2.setData(vecData);
//                     tblDat.setModel(objTblMod2);
//
//
//                    new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
//
//                    blnRes=true;
//
//
//
//        }}
//        catch (java.sql.SQLException e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);
//        }
//
//        return blnRes;
//    }
//
    
//
//    private boolean cargarDatosVentas(){
//        boolean blnRes=false;
//        int intval3=0;
//        String strSqlAux3="";
//         try{
//
//         String fecdesde="2006-05-22",fechasta="2006-05-22";
//         int intMesDes=-1, intMesHas=-1;
//         int intAniDes=0, intAniHas=01;
//
//         if (objSelDat.chkIsSelected())
//                    {
//                        switch (objSelDat.getTypeSeletion())
//                        {
//                            case 1: //Búsqueda por rangos
//                                  intval3=1;
//
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                //   System.out.println(">> "+intMesDes + " <> "+ intMesHas + " <>" + intAniDes  + " <> " + intAniHas  );
//
//
//                                  break;
//                            case 2: //Fechas mayores o iguales a "Desde".
//                                   intval3=2;
//
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                   intAniHas=intAniDes;
//
//                                   fecdesde =  objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = "12/31/"+String.valueOf(objSelDat.getYearSelected());
//
//
//                             //   System.out.println("22 >> "+ intMesDes + " <>" + intAniDes + " -- " + fecdesde  );
//
//                               break;
//                            case 3: //Fechas menores o iguales a "Hasta".
//                                  intval3=3;
//
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                   intAniDes=intAniHas;
//
//                                   fechasta =  objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = "01/01/"+String.valueOf(objSelDat.getYearSelected());
//
//                                // System.out.println(">> "+ intMesHas + " <>" + intAniHas  + " -- " + fechasta );
//
//
//                                break;
//                            case 4: //Todo.
//                                System.out.println("Error.........");
//                                intval3=4;
//                                break;
//
//                        }
//                    }
//
//
//
//
//
//
//           if(intval3==4) return true;
//
//
//             vecDatOrg.clear();
//             con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos() );
//             if (con!=null)
//               {
//                   stm=con.createStatement();
//
//                 vecRegCom=new Vector();
//                 vecRegPag=new Vector();
//                 vecRegRet=new Vector();
//                 vecRegDif=new Vector();
//
//
//           vecCabOrg.clear();
//           vecCabOrg.add(" ");
//           StringBuffer stbins=new StringBuffer();
//           int x=0;
//
//
//
//           if(intval3==2){
//           int hasta= 11;
//           int desde = intMesDes;
//           for(int y=intAniDes; y<=intAniHas; y++){
//           //  if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+"");
//                   if (x>0)
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//          if(intval3==3){
//           int hasta=intMesHas;
//           int desde = 0;
//           for(int y=intAniDes; y<=intAniHas; y++){
//            // if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+"");
//                   if (x>0)
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//
//          if(intval3==1){
//           int hasta=11;
//           int desde = intMesDes;
//           for(int y=intAniDes; y<=intAniHas; y++){
//             if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+"");
//
//
//
//                   if (x>0)
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//        vecCabOrg.add("Total");
//
//
//          String strSqlAux="a.co_emp="+objParSis.getCodigoEmpresa()+" and ",strSqlAux2=" d1.co_emp="+objParSis.getCodigoEmpresa()+" and ";
//          if(local.getSelectedIndex()>0) {
//            strSqlAux = strSqlAux+ "   a.co_loc = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//            strSqlAux2 = strSqlAux2+"  d1.co_loc = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//          }
//
//          if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
//              strSqlAux =" ";
//              strSqlAux2 =" ";
//
//                strSqlAux3 = " AND NOT (a.co_emp=1 AND a.co_cli=3515)" +
//            "  AND NOT (a.co_emp=1 AND a.co_cli=3516)" +
//            "  AND NOT (a.co_emp=1 AND a.co_cli=602)" +
//            "  AND NOT (a.co_emp=1 AND a.co_cli=603)" +
//            "  AND NOT (a.co_emp=1 AND a.co_cli=2600)" +
//            "  AND NOT (a.co_emp=1 AND a.co_cli=1039)" +
//
//            "   AND NOT (a.co_emp=2 AND a.co_cli=2853)" +
//            "   AND NOT (a.co_emp=2 AND a.co_cli=2854)" +
//            "   AND NOT (a.co_emp=2 AND a.co_cli=446)" +
//            "   AND NOT (a.co_emp=2 AND a.co_cli=447)" +
//            "     AND NOT (a.co_emp=2 AND a.co_cli=2105)" +
//            "     AND NOT (a.co_emp=2 AND a.co_cli=789)" +
//            "     AND NOT (a.co_emp=2 AND a.co_cli=790)" +
//
//            "      AND NOT (a.co_emp=3 AND a.co_cli=2857)" +
//            "      AND NOT (a.co_emp=3 AND a.co_cli=2858)" +
//            "      AND NOT (a.co_emp=3 AND a.co_cli=452)" +
//            "      AND NOT (a.co_emp=3 AND a.co_cli=453)" +
//            "      AND NOT (a.co_emp=3 AND a.co_cli=2107)" +
//            "     AND NOT (a.co_emp=3 AND a.co_cli=832)" +
//
//            "        AND NOT (a.co_emp=4 AND a.co_cli=3116)" +
//            "         AND NOT (a.co_emp=4 AND a.co_cli=3117)" +
//            "        AND NOT (a.co_emp=4 AND a.co_cli=497)" +
//            "        AND NOT (a.co_emp=4 AND a.co_cli=498)" +
//            "         AND NOT (a.co_emp=4 AND a.co_cli=2294)" +
//            "         AND NOT (a.co_emp=4 AND a.co_cli=886)"+
//            "         AND NOT (a.co_emp=4 AND a.co_cli=887)";
//
//
//
//          }
//
//
//
//                      String sql =" select anio1, mes1, " +
//
//                      " case when valor_total is null then 0 else abs(valor_total) end as valor_total, "+
//                      " case when valor is null then 0 else abs(valor) end as valor, "+
//                      " case when valor2 is null then 0 else abs(valor2) end as valor2 "+
//
//                      //"valor_total, valor, valor2, " +
//                      //"case when valor is null then valor_total " +
//                      //" else (abs(valor_total) - (valor+valor2)) end  as diferencia " +
//
//                      " from ( " +
//                      " select * from ( " +
//                     /*
//                      " select extract(year from a.fe_doc) as anio1, extract(month from a.fe_doc) as mes1, sum(abs(a.nd_tot)) as valor_total from tbm_cabmovinv as a " +
//                      " where  "+ strSqlAux +"  a.co_tipdoc="+txtCodtipdoc.getText()+" and a.st_reg not in ('I','E')  " +
//                      " and a.fe_doc between '"+fecdesde+"' and '"+fechasta+"' " + strSqlAux3 + " " +
//                      " group by extract(year from a.fe_doc), extract(month from a.fe_doc) " +
//
//                     */
//
//
//
//                     " Select extract(year from a.fe_doc) as anio1 , extract(month from a.fe_doc) as mes1  ,  sum(a.nd_tot) as valor_total  from tbm_cabmovinv as a" +
//                     " WHERE "+ strSqlAux +"  a.st_reg not in ('I','E') and a.fe_doc between '"+fecdesde+"' and '"+fechasta+"'  and a.co_tipdoc in (1) " +
//                     " AND (a.st_tipDev IS NULL OR a.st_tipDev='C') " + strSqlAux3 + ""+
//                     " group by extract(year from a.fe_doc), extract(month from a.fe_doc)  "+
//
//                     " ) as x  left join  " +
//                     " ( " +
//
//                     " SELECT  extract(year from d1.fe_dia) as anio2, extract(month from d1.fe_dia) as mes2  ,sum(abs(d2.nd_monhab))  as valor  FROM tbm_cabdia as d1 " +
//                     " inner join tbm_detdia as d2 on (d2.co_emp=d1.co_emp and d2.co_loc=d1.co_loc and d2.co_tipdoc=d1.co_tipdoc and d2.co_dia=d1.co_dia  ) "+
//                     " inner join tbm_placta as d3 on (d3.co_emp=d2.co_emp and d3.co_cta=d2.co_cta and d3.tx_codcta='1.01.03.01.01') "+
//                     " where "+ strSqlAux2 +"  d1.st_reg not in ('I','E')  and d1.co_tipdoc in (18,24,25) "+
//                     " and d1.fe_dia between '"+fecdesde+"' and '"+fechasta+"' "+
//                     " group by extract(year from d1.fe_dia), extract(month from d1.fe_dia) "+
//
//
//
//
//                      " ) as y  on (y.anio2=x.anio1 and y.mes2=x.mes1) " +
//
//
//                     " left join  " +
//                     " ( " +
//                     " SELECT  extract(year from d1.fe_dia) as anio2, extract(month from d1.fe_dia) as mes2  ,sum(abs(d2.nd_monhab))  as valor2  FROM tbm_cabdia as d1 " +
//                     " inner join tbm_detdia as d2 on (d2.co_emp=d1.co_emp and d2.co_loc=d1.co_loc and d2.co_tipdoc=d1.co_tipdoc and d2.co_dia=d1.co_dia  ) "+
//                     " inner join tbm_placta as d3 on (d3.co_emp=d2.co_emp and d3.co_cta=d2.co_cta and d3.tx_codcta='1.01.03.01.01') "+
//                     " where "+ strSqlAux2 +"  d1.st_reg not in ('I','E')  and d1.co_tipdoc in (26,27) "+
//                     " and d1.fe_dia between '"+fecdesde+"' and '"+fechasta+"' "+
//                     " group by extract(year from d1.fe_dia), extract(month from d1.fe_dia) "+
//                     " ) as y2  on (y2.anio2=x.anio1 and y2.mes2=x.mes1) " +
//
//
//
//
//                      " ) as x order by anio1, mes1 ";
//
//
//
//
//                      String sql2 = "select mes, round(abs(valor_total),2), round(valor,2), round(valor2,2), (valor_total-(valor+valor2)) as diferencia  from (  select * from ( "+ stbins.toString() +" ) as x ";
//                      sql2 = sql2 + " left join (" +
//                      " select * from ( " + sql + " "+
//                      "" +
//                      " ) as t ) as t on(t.anio1=x.anio and t.mes1=x.mes)  ) as x order by anio,mes";
//
//                      System.out.println(""+ sql2 );
//
//
//
//                      //***************************************************
//
//
//
//
//                    rst=stm.executeQuery(sql2);
//
//                        vecRegCom.add("Ventas");
//                        vecRegPag.add("Cobros");
//                        vecRegRet.add("Retención");
//                        vecRegDif.add("Diferencia");
//
//
//
//                        double dlbCom=0;
//                        double dlbPag=0;
//                        double dlbRet=0;
//                        double dlbDif=0;
//
//
//                    while (rst.next())
//                    {
//                        vecRegCom.add( new Double(rst.getDouble(2)));
//                        vecRegPag.add( new Double(rst.getDouble(3)));
//                        vecRegRet.add( new Double(rst.getDouble(4)));
//                        vecRegDif.add( new Double(rst.getDouble(5)));
//
//                        dlbCom +=rst.getDouble(2);
//                        dlbPag +=rst.getDouble(3);
//                        dlbRet +=rst.getDouble(4);
//                        dlbDif +=rst.getDouble(5);
//
//                    }
//
//                        dlbCom = objUti.redondear(dlbCom,2);
//                        dlbPag = objUti.redondear(dlbPag,2);
//                        dlbRet = objUti.redondear(dlbRet,2);
//                        dlbDif = objUti.redondear(dlbDif,2);
//
//
//                        vecRegCom.add(new Double(dlbCom));
//                        vecRegPag.add(new Double(dlbPag));
//                        vecRegRet.add(new Double(dlbRet));
//                        vecRegDif.add(new Double(dlbDif));
//
//
//                        java.util.Vector vecData = new java.util.Vector();
//                        vecData.add(vecRegCom);
//                        vecData.add(vecRegPag);
//                        vecData.add(vecRegRet);
//                        vecData.add(vecRegDif);
//
//                          vecDatOrg.add(vecRegCom);
//                          vecDatOrg.add(vecRegPag);
//                          vecDatOrg.add(vecRegRet);
//                          vecDatOrg.add(vecRegDif);
//
//                    con.close();
//                    con=null;
//
//                     Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod2=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
//                     objTblMod2.setHeader(vecCabOrg);
//                     tblDat.setModel(objTblMod2);
//
//
//
//
//
//                        for(int i=1; i<tblDat.getColumnCount(); i++){
//                             objTblCelRenLbl=null;
//                             javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
//                             objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
//                             objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
//                             objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//                             objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
//                             objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
//                             tcmAux.getColumn( i ).setCellRenderer(objTblCelRenLbl);
//                             tcmAux=null;
//                        }
//
//
//
//                     objTblMod2.setData(vecData);
//                     tblDat.setModel(objTblMod2);
//
//
//                    new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
//
//                    blnRes=true;
//
//
//
//        }}
//        catch (java.sql.SQLException e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);
//        }
//
//        return blnRes;
//    }
//
    
    
//
//    private boolean cargarDatosCompras(){
//        boolean blnRes=false;
//        String strSqlAux3="";
//        int intval3=0;
//         try{
//
//         String fecdesde="2006-05-22",fechasta="2006-05-22";
//         int intMesDes=-1, intMesHas=-1;
//         int intAniDes=0, intAniHas=01;
//
//         if (objSelDat.chkIsSelected())
//                    {
//                        switch (objSelDat.getTypeSeletion())
//                        {
//                            case 1: //Búsqueda por rangos
//                                  intval3=1;
//
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                //   System.out.println(">> "+intMesDes + " <> "+ intMesHas + " <>" + intAniDes  + " <> " + intAniHas  );
//
//
//                                  break;
//                            case 2: //Fechas mayores o iguales a "Desde".
//                                   intval3=2;
//
//                                   intMesDes = objUti.getMesFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intAniDes = objUti.getMesAnio(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                   intAniHas=intAniDes;
//
//                                   fecdesde =  objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fechasta = "12/31/"+String.valueOf(objSelDat.getYearSelected());
//
//
//                             //   System.out.println("22 >> "+ intMesDes + " <>" + intAniDes + " -- " + fecdesde  );
//
//                               break;
//                            case 3: //Fechas menores o iguales a "Hasta".
//                                  intval3=3;
//
//                                   intAniHas = objUti.getMesAnio(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   intMesHas = objUti.getMesFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//
//                                   intAniDes=intAniHas;
//
//                                   fechasta =  objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
//                                   fecdesde = "01/01/"+String.valueOf(objSelDat.getYearSelected());
//
//                                // System.out.println(">> "+ intMesHas + " <>" + intAniHas  + " -- " + fechasta );
//
//
//                                break;
//                            case 4: //Todo.
//                                System.out.println("Error.........");
//                                intval3=4;
//                                break;
//
//                        }
//                    }
//
//
//
//
//
//
//           if(intval3==4) return true;
//
//
//             vecDatOrg.clear();
//             con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos() );
//             if (con!=null)
//               {
//                   stm=con.createStatement();
//
//                 vecRegCom=new Vector();
//                 vecRegPag=new Vector();
//                 vecRegRet=new Vector();
//                 vecRegDif=new Vector();
//
//
//           vecCabOrg.clear();
//           vecCabOrg.add(" ");
//           StringBuffer stbins=new StringBuffer();
//           int x=0;
//
//
//
//           if(intval3==2){
//           int hasta= 11;
//           int desde = intMesDes;
//           for(int y=intAniDes; y<=intAniHas; y++){
//           //  if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+"");
//                   if (x>0)
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//          if(intval3==3){
//           int hasta=intMesHas;
//           int desde = 0;
//           for(int y=intAniDes; y<=intAniHas; y++){
//            // if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+"");
//                   if (x>0)
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//
//          if(intval3==1){
//           int hasta=11;
//           int desde = intMesDes;
//           for(int y=intAniDes; y<=intAniHas; y++){
//             if(y==intAniHas) hasta = intMesHas;
//             for(int i=desde; i<=hasta; i++){
//
//                     if((i+1)==1)  vecCabOrg.add("Enero"+y+" ");
//                     if((i+1)==2)  vecCabOrg.add("Febrero"+y+" ");
//                     if((i+1)==3)  vecCabOrg.add("Marzo"+y+" ");
//                     if((i+1)==4)  vecCabOrg.add("Abril"+y+" ");
//                     if((i+1)==5)  vecCabOrg.add("Mayo"+y+" ");
//                     if((i+1)==6)  vecCabOrg.add("Junio"+y+"");
//                     if((i+1)==7)  vecCabOrg.add("Julio"+y+" ");
//                     if((i+1)==8)  vecCabOrg.add("Agosto"+y+" ");
//                     if((i+1)==9)  vecCabOrg.add("Septiembre"+y+" ");
//                     if((i+1)==10)  vecCabOrg.add("Octubre"+y+" ");
//                     if((i+1)==11)  vecCabOrg.add("Noviembre"+y+" ");
//                     if((i+1)==12)  vecCabOrg.add("Diciembre"+y+"");
//
//
//
//                   if (x>0)
//                      stbins.append(" UNION ALL ");
//                      stbins.append(" select "+y+" as anio, "+(i+1)+" as mes");
//                 x=1;
//            }
//            desde=0;
//            hasta=11;
//          }
//          }
//
//        vecCabOrg.add("Total");
//
//
//          String strSqlAux="a.co_emp="+objParSis.getCodigoEmpresa()+" and ",strSqlAux2=" d1.co_emp="+objParSis.getCodigoEmpresa()+" and ";
//          if(local.getSelectedIndex()>0) {
//            strSqlAux = strSqlAux+ "   a.co_loc = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//            strSqlAux2 = strSqlAux2+"  d1.co_loc = "+vecLoc.elementAt(local.getSelectedIndex()).toString()+" and ";
//          }
//
//          if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
//              strSqlAux =" ";
//              strSqlAux2 =" ";
//
//                    strSqlAux3 = " AND NOT (a.co_emp=1 AND a.co_cli=3515)" +
//            "  AND NOT (a.co_emp=1 AND a.co_cli=3516)" +
//            "  AND NOT (a.co_emp=1 AND a.co_cli=602)" +
//            "  AND NOT (a.co_emp=1 AND a.co_cli=603)" +
//            "  AND NOT (a.co_emp=1 AND a.co_cli=2600)" +
//            "  AND NOT (a.co_emp=1 AND a.co_cli=1039)" +
//
//            "   AND NOT (a.co_emp=2 AND a.co_cli=2853)" +
//            "   AND NOT (a.co_emp=2 AND a.co_cli=2854)" +
//            "   AND NOT (a.co_emp=2 AND a.co_cli=446)" +
//            "   AND NOT (a.co_emp=2 AND a.co_cli=447)" +
//            "     AND NOT (a.co_emp=2 AND a.co_cli=2105)" +
//            "     AND NOT (a.co_emp=2 AND a.co_cli=789)" +
//            "     AND NOT (a.co_emp=2 AND a.co_cli=790)" +
//
//            "      AND NOT (a.co_emp=3 AND a.co_cli=2857)" +
//            "      AND NOT (a.co_emp=3 AND a.co_cli=2858)" +
//            "      AND NOT (a.co_emp=3 AND a.co_cli=452)" +
//            "      AND NOT (a.co_emp=3 AND a.co_cli=453)" +
//            "      AND NOT (a.co_emp=3 AND a.co_cli=2107)" +
//            "     AND NOT (a.co_emp=3 AND a.co_cli=832)" +
//
//            "        AND NOT (a.co_emp=4 AND a.co_cli=3116)" +
//            "         AND NOT (a.co_emp=4 AND a.co_cli=3117)" +
//            "        AND NOT (a.co_emp=4 AND a.co_cli=497)" +
//            "        AND NOT (a.co_emp=4 AND a.co_cli=498)" +
//            "         AND NOT (a.co_emp=4 AND a.co_cli=2294)" +
//            "         AND NOT (a.co_emp=4 AND a.co_cli=886)"+
//            "         AND NOT (a.co_emp=4 AND a.co_cli=887)";
//
//          }
//
//
//
//                      String sql =" select anio1, mes1," +
//                      "" +
//
//                      " case when valor_total is null then 0 else abs(valor_total) end as valor_total, "+
//                      " case when valor is null then 0 else abs(valor) end as valor, "+
//                      " case when valor2 is null then 0 else abs(valor2) end as valor2 "+
//
//
//                      //" valor_total, valor, " +
//                      //"case when valor is null then valor_total " +
//                      //" else (valor_total - valor) end  as diferencia " +
//
//
//                      " from ( " +
//                      " select * from ( " +
//
//                      /*
//                      " select extract(year from a.fe_doc) as anio1, extract(month from a.fe_doc) as mes1, sum(abs(a.nd_tot)) as valor_total from tbm_cabmovinv as a " +
//                      " where  "+ strSqlAux +"  a.co_tipdoc="+txtCodtipdoc.getText()+" and a.st_reg not in ('I','E')  " +
//                      " and a.fe_doc between '"+fecdesde+"' and '"+fechasta+"' " +
//                      " group by extract(year from a.fe_doc), extract(month from a.fe_doc) " +
//
//                     */
//
//
//
//                     " Select extract(year from a.fe_doc) as anio1 , extract(month from a.fe_doc) as mes1  ,  sum(a.nd_tot) as valor_total  from tbm_cabmovinv as a" +
//                     " WHERE "+ strSqlAux +"  a.st_reg not in ('I','E') and a.fe_doc between '"+fecdesde+"' and '"+fechasta+"'  and a.co_tipdoc in ("+txtCodtipdoc.getText()+") " +
//                     " AND (a.st_tipDev IS NULL OR a.st_tipDev='C') " + strSqlAux3 + ""+
//                     " group by extract(year from a.fe_doc), extract(month from a.fe_doc)  "+
//
//                      " ) as x  left join  " +
//                      " ( " +
//                      " SELECT  extract(year from d1.fe_dia) as anio2, extract(month from d1.fe_dia) as mes2,  sum(abs(d2.nd_mondeb)) as valor  FROM tbm_cabdia as d1 " +
//                      " inner join tbm_detdia as d2 on (d2.co_emp=d1.co_emp and d2.co_loc=d1.co_loc and d2.co_tipdoc=d1.co_tipdoc and d2.co_dia=d1.co_dia  )" +
//                      " inner join tbm_placta as d3 on (d3.co_emp=d2.co_emp and d3.co_cta=d2.co_cta and d3.tx_codcta='2.01.03.01.01') " +
//                      " where "+ strSqlAux2 +"    d1.st_reg not in ('I','E')  and d1.co_tipdoc in (32) " +
//                      " and d1.fe_dia between '"+fecdesde+"' and '"+fechasta+"' " +
//                      "  group by extract(year from d1.fe_dia), extract(month from d1.fe_dia) " +
//                      " ) as y  on (y.anio2=x.anio1 and y.mes2=x.mes1) " +
//
//
//
//                        " left join  " +
//                      " ( " +
//                      " SELECT  extract(year from d1.fe_dia) as anio2, extract(month from d1.fe_dia) as mes2,  sum(abs(d2.nd_mondeb)) as valor2  FROM tbm_cabdia as d1 " +
//                      " inner join tbm_detdia as d2 on (d2.co_emp=d1.co_emp and d2.co_loc=d1.co_loc and d2.co_tipdoc=d1.co_tipdoc and d2.co_dia=d1.co_dia  )" +
//                      " inner join tbm_placta as d3 on (d3.co_emp=d2.co_emp and d3.co_cta=d2.co_cta and d3.tx_codcta='2.01.03.01.01') " +
//                      " where "+ strSqlAux2 +"    d1.st_reg not in ('I','E')  and d1.co_tipdoc in (33) " +
//                      " and d1.fe_dia between '"+fecdesde+"' and '"+fechasta+"' " +
//                      "  group by extract(year from d1.fe_dia), extract(month from d1.fe_dia) " +
//                      " ) as y2  on (y2.anio2=x.anio1 and y2.mes2=x.mes1) " +
//
//
//
//                      " ) as x order by anio1, mes1 ";
//
//                      String sql2 = "select mes, round(valor_total,2), round(valor,2), round(valor2,2),  (valor_total-(valor+valor2)) as diferencia  from (  select * from ( "+ stbins.toString() +" ) as x ";
//                      sql2 = sql2 + " left join (" +
//                      " select * from ( " + sql + " "+
//                      "" +
//                      " ) as t ) as t on(t.anio1=x.anio and t.mes1=x.mes)  ) as x order by anio,mes";
//
//                      System.out.println(""+ sql2 );
//
//
//
//                      //***************************************************
//
//
//
//
//                    rst=stm.executeQuery(sql2);
//
//
//                        vecRegCom.add("Compras");
//                        vecRegPag.add("Pagos");
//                        vecRegRet.add("Retención");
//                        vecRegDif.add("Diferencia");
//
//                        double dlbCom=0;
//                        double dlbPag=0;
//                        double dlbRet=0;
//                        double dlbDif=0;
//
//
//                    while (rst.next())
//                    {
//                        vecRegCom.add( new Double(rst.getDouble(2)));
//                        vecRegPag.add( new Double(rst.getDouble(3)));
//                        vecRegRet.add( new Double(rst.getDouble(4)));
//                        vecRegDif.add( new Double(rst.getDouble(5)));
//
//                        dlbCom +=rst.getDouble(2);
//                        dlbPag +=rst.getDouble(3);
//                        dlbRet +=rst.getDouble(4);
//                        dlbDif +=rst.getDouble(5);
//
//                    }
//
//                        dlbCom = objUti.redondear(dlbCom,2);
//                        dlbPag = objUti.redondear(dlbPag,2);
//                        dlbRet = objUti.redondear(dlbRet,2);
//                        dlbDif = objUti.redondear(dlbDif,2);
//
//
//                        vecRegCom.add(new Double(dlbCom));
//                        vecRegPag.add(new Double(dlbPag));
//                        vecRegRet.add(new Double(dlbRet));
//                        vecRegDif.add(new Double(dlbDif));
//
//
//                        java.util.Vector vecData = new java.util.Vector();
//                        vecData.add(vecRegCom);
//                        vecData.add(vecRegPag);
//                        vecData.add(vecRegRet);
//                        vecData.add(vecRegDif);
//
//                          vecDatOrg.add(vecRegCom);
//                          vecDatOrg.add(vecRegPag);
//                          vecDatOrg.add(vecRegRet);
//                          vecDatOrg.add(vecRegDif);
//
//
//                    con.close();
//                    con=null;
//
//                     Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod2=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
//                     objTblMod2.setHeader(vecCabOrg);
//                     tblDat.setModel(objTblMod2);
//
//
//
//
//
//                        for(int i=1; i<tblDat.getColumnCount(); i++){
//                             objTblCelRenLbl=null;
//                             javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
//                             objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
//                             objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
//                             objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//                             objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
//                             objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
//                             tcmAux.getColumn( i ).setCellRenderer(objTblCelRenLbl);
//                             tcmAux=null;
//                        }
//
//
//
//                     objTblMod2.setData(vecData);
//                     tblDat.setModel(objTblMod2);
//
//
//                    new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
//
//                    blnRes=true;
//
//
//
//        }}
//        catch (java.sql.SQLException e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//           objUti.mostrarMsgErr_F1(this, e);
//        }
//        
//        return blnRes;
//    }
//
    
    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
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

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanCab;
    private javax.swing.JPanel PanDet;
    private javax.swing.JPanel PanDet01;
    private javax.swing.JPanel PanDet02;
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.ButtonGroup butGrpOpt;
    private javax.swing.JComboBox cboLocEmp;
    private javax.swing.JCheckBox chkEscComp;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane srcTbl;
    private javax.swing.JScrollPane srcTblSal;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatSal;
    // End of variables declaration//GEN-END:variables


    
      
  
    public void setEditable(boolean editable)
   {
        if (editable==true){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            
        }else{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }       
       
   }

    
}

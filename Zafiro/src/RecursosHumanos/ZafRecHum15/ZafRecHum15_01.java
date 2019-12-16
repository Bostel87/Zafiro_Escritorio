package RecursosHumanos.ZafRecHum15;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Maestros.ZafMae07.ZafMae07_01;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author  Roberto Flores
 */
public class ZafRecHum15_01 extends javax.swing.JDialog
{
    
    private ZafRecHum15 objZafRecHum15;
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
//    private ZafThreadGUI objThrGUI;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChkAut;                 //Editor: JCheckBox en celda.
    private ZafTblCelEdiChk objTblCelEdiChkDen;                 //Editor: JCheckBox en celda.
    private ZafTblCelEdiButGen objTblCelEdiButGen;              //Editor: JButton en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private java.util.Date datFecAux;                           //Auxiliar: Para almacenar fechas.
    private ArrayList arlParDlg;                                //Parametros a pasar al JDialog que se llama desde el JTable.
    private CxC.ZafCxC23.ZafCxC23_04 objCxC23_04;
    //Variables de la clase.
    private int intOpcSelDlg;                                   //Opción seleccionada en el JDialog.
    private int intCodEmp;                                      //Código de la empresa.
    private int intCodLoc;                                      //Código del local.
//    private int intCodTipDoc;                                   //Código del tipo de documento.
    private int intCodDoc;                                      //Código del docuemnto.
//    private int intNumDoc;
//    private String strFecha="";
//    private int intCodAut;                                      //Código de la autorización.
//    private int intCodCli;                                      //Código del cliente.
//    private String strIdeCli;                                   //Identificación del cliente.
//    private String strEstDoc;                                   //Estado del documento.
//    private String strEstValDoc;                                //Estado de validez del documento.
    
    private ZafTblCelEdiChk objTblCelEdiChk;                         //Editor de Check Box 
    private ZafTblCelEdiChk objTblCelEdiChk2;                         //Editor de Check Box 
    private ZafTblCelRenChk objTblCelRenChkLab;                         //Renderer de Check Box

     // TABLA DE DATOS
    private final int INT_TBL_LIN= 0 ;
    private final int INT_TBL_RUBRO=1;
    private final int INT_TBL_FEC_FAL= 2 ;
    private final int INT_TBL_VALOR= 3 ;

    //private Vector vecDat, vecReg;

    private String strCodEmp="";
    private String strCodLoc="";
    private String strCodTipdoc="";
    private String strCodDoc="";
    private String strNumDoc="";
    private String strHorSupExt="";
    private int intDiaSem=0;
    
    private int intCoTra;
    private int intNeAni;
    private int intNeMes;
    private int intNePer;
    private int intCoRub;
    private double dblNdValRubPag;
    
    private ZafTblTot objTblTot;   
    
    /** Creates new form ZafHer03_01 */
    public ZafRecHum15_01(java.awt.Frame parent, boolean modal, ZafParSis obj) 
    {
        super(parent, modal);
        try{
            initComponents();
            vecDat=new Vector();
            //Inicializar objetos.
            objParSis=obj;
            intOpcSelDlg=0;
            if (!configurarFrm())
                exitForm();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(400, 450));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

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

        spnTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot.setViewportView(tblTot);

        panGenDet.add(spnTot, java.awt.BorderLayout.SOUTH);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCan.setText("Cancelar");
        butCan.setToolTipText("Cierra el cuadro de dialogo");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-454)/2, (screenSize.height-344)/2, 454, 344);
    }// </editor-fold>//GEN-END:initComponents

//    public boolean guardarDat(java.sql.Connection conn){
//        boolean blnRes=false;
//            java.sql.Statement stmLoc,stmLocCab;
//            java.sql.ResultSet resSet, resSetCab;
//            String strSql="";
//            String strHoAutGLBDES="";
//            String strHoAutGLBHAS="";
//            String strParTot="";
//            
//            try{
//                
//                if(conn!=null){
//                    
//                    stmLoc=conn.createStatement();
//                    stmLocCab=conn.createStatement();
//                    int intCanUsrAut=tblDat.getRowCount();
//                    for(int intFil=0; intFil<tblDat.getRowCount(); intFil++){
//                        //System.out.println("linea " + intFil +  "    :"+ tblDat.getValueAt(intFil, INT_TBL_LIN).toString());
//                        if(tblDat.getValueAt(intFil, INT_TBL_LIN).toString().compareTo("M")==0){
//                            
//                            Object objHoAutDes=tblDat.getValueAt(intFil, INT_TBL_HORAUTDES);
//                            String strHoAutDes="";
//                            if(objHoAutDes!=null){
//                                strHoAutDes=objUti.codificar(objHoAutDes.toString());
//                                strHoAutGLBDES=strHoAutDes;
//                            }else{
//                                if(objParSis.getCodigoUsuario()==Integer.valueOf(
//                                    tblDat.getValueAt(intFil, INT_TBL_CODUSR).toString())||objParSis.getCodigoUsuario()==1){
//                                    if(tblDat.getValueAt(intFil, INT_TBL_CHKDEN).equals(true)){
//                                        tblDat.setValueAt("00:00", intFil,INT_TBL_HORAUTDES);
//                                        strHoAutDes=objUti.codificar("00:00");
//                                        strHoAutGLBDES=strHoAutDes;
//                                    }else{
//                                        mostrarMsgInf("Debe ingresar las horas por autorizar");
//                                        return false;
//                                    }
//                                }
//                            }
//                            
//                            Object objHoAutHas=tblDat.getValueAt(intFil, INT_TBL_HORAUTHAS);
//                            String strHoAutHas="";
//                            if(objHoAutHas!=null){
//                                strHoAutHas=objUti.codificar(objHoAutHas.toString());
//                                strHoAutGLBHAS=strHoAutHas;
//                            }else{
//                                if(objParSis.getCodigoUsuario()==Integer.valueOf(
//                                    tblDat.getValueAt(intFil, INT_TBL_CODUSR).toString())||objParSis.getCodigoUsuario()==1){
//                                    if(tblDat.getValueAt(intFil, INT_TBL_CHKDEN).equals(true)){
//                                        tblDat.setValueAt("00:00", intFil,INT_TBL_HORAUTHAS);
//                                        strHoAutHas=objUti.codificar("00:00");
//                                        strHoAutGLBHAS=strHoAutHas;
//                                    }else{
//                                        mostrarMsgInf("Debe ingresar las horas por autorizar");
//                                        return false;
//                                    }
//                                }
//                            }
//                            
//                            Object objObs=tblDat.getValueAt(intFil, INT_TBL_OBS);
//                            String strObs="";
//                            if(objObs!=null){
//                                if(strObs!=null){
//                                    strObs=objUti.codificar(tblDat.getValueAt(intFil, INT_TBL_OBS).toString());
//                                }else{
//                                    strObs=null;
//                                }
//                            }else{
//                                strObs=null;
//                            }
//                            
//                            String strAut="";
//                            
//                            if(tblDat.getValueAt(intFil, INT_TBL_CHKAUT).equals(true)){
//                                strAut="'A'";
//                                strParTot="A";
//                            }
//                            if(tblDat.getValueAt(intFil, INT_TBL_CHKDEN).equals(true)){
//                                strAut="'D'";
////                                strHoAutDes=objUti.codificar("00");
//                                strHoAutGLBDES="null";
////                                strHoAutHas=objUti.codificar("00");
//                                strHoAutGLBHAS="null";
//                            }
//                            if(tblDat.getValueAt(intFil, INT_TBL_CHKAUT).equals(false) && tblDat.getValueAt(intFil, INT_TBL_CHKDEN).equals(false)){
//                                mostrarMsgInf("NO HA INGRESADO SI AUTORIZA O DENIEGA LA SOLICITUD");
//                                return false;
//                            }
//                            
//                            if(objParSis.getCodigoUsuario()==Integer.valueOf(tblDat.getValueAt(intFil, INT_TBL_CODUSR).toString()) || objParSis.getCodigoUsuario()==1){
//                                strSql="update tbm_autSolHorSupExt set st_aut="+ strAut +" ,ho_autdes="+ strHoAutDes + " ,ho_authas="+ strHoAutHas +" , tx_obsaut="+strObs+", "+
//                                    "fe_aut=CURRENT_TIMESTAMP, co_usraut ="+objParSis.getCodigoUsuario()+" , tx_comaut = '"+objParSis.getDireccionIP() + "' " +
//                                    "where co_emp="+ intCodEmp +" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and co_reg="+tblDat.getValueAt(intFil, INT_TBL_COREG).toString();
//                            
//                                System.out.println(strSql);
//                                stmLoc.executeUpdate(strSql);
//                                
//                                strSql="update tbm_cabSolHorSupExt set ho_autdes="+strHoAutDes+",ho_authas="+strHoAutHas+" where co_emp="+ intCodEmp +" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+
//                                   " and co_doc="+intCodDoc;
//                                stmLoc.executeUpdate(strSql);
//                            }
//                        }
//                    }
//                    
//                    strSql="select * from tbm_autSolHorSupExt where co_emp="+ intCodEmp +" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+
//                                   " and co_doc="+intCodDoc+" and st_aut like 'A'";
//                    resSet=stmLoc.executeQuery(strSql);
//                    
//                    int intCantAut=0;
//                    while(resSet.next()){
//                        intCantAut++;
//                    }
//                    
//                    int intFilMax=tblDat.getRowCount()-1;
//                    boolean blnSeCmp=false;
//                    if(objParSis.getCodigoUsuario()==1){
//                        
//                        int intComp=0;
//                        for(int i=0;i < tblDat.getRowCount();i++){
//                            
//                            if(tblDat.getValueAt(i, INT_TBL_LIN).toString().equals("M")){
//                                
//                                if(!(tblDat.getValueAt(i, INT_TBL_CHKAUT).equals(false) && tblDat.getValueAt(i, INT_TBL_CHKAUT).equals(false))){
//                                    intComp++;
//                                }
//                            }
//                        }
//                        
//                        if(intComp==0){
//                            mostrarMsgInf("NO HA AUTORIZADO/DENEGADO LA SOLICITUD");
//                            return false;
//                        }
//                        
//                        if(tblDat.getValueAt(intFilMax, INT_TBL_CHKAUT).equals(true)){
//                            strSql="update tbm_cabSolHorSupExt set st_aut='T',ho_autdes="+strHoAutGLBDES+",ho_authas="+strHoAutGLBHAS;
//                            strSql+=" , fe_aut=current_timestamp where co_emp="+ intCodEmp +" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;
//                            strSql+=" and co_doc="+intCodDoc;
//                            strParTot="T";
//                            stmLoc.executeUpdate(strSql);
//
//                            strSql="select co_tra from tbm_detsolhorsupext where co_emp="+ intCodEmp +" and co_loc="+intCodLoc+
//                                    " and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc;//+" and co_reg="+tblDat.getValueAt(intFil, INT_TBL_COREG).toString();
//                            resSet=stmLoc.executeQuery(strSql);
//                            while(resSet.next()){
//                                String strCo_Tra=resSet.getString("co_tra");
//
//                                strSql="select * from tbm_cabconasitra where fe_dia = '"+strFecha+"' and co_tra = "+strCo_Tra;
//                                resSetCab= stmLocCab.executeQuery(strSql);
//
//                                if(resSetCab.next()){
//                                    strSql="update tbm_cabconasitra set st_autHorSupExt = 'S', ho_supExtAut = "+strHoAutGLBHAS+" "+
//                                            "where fe_dia = '"+strFecha+"' and co_tra = "+strCo_Tra;
//                                    stmLocCab.executeUpdate(strSql);
//                                }else{
//                                    strSql="insert into tbm_cabconasitra (co_tra,fe_dia,st_autHorSupExt,ho_supExtAut) values("+strCo_Tra+", '"+strFecha+"', "+
//                                            "'S', "+ strHoAutGLBHAS + " )";
//                                    System.out.println(strSql);
//                                    stmLocCab.executeUpdate(strSql);
//                                }
//                            }
//
//                            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
//                            butAce.setEnabled(false);
//                            blnSeCmp=true;
//                        }
//                        if(!blnSeCmp){
//                            if(intCanUsrAut==intCantAut){
//                            strSql="update tbm_cabSolHorSupExt set st_aut='T',ho_autdes="+strHoAutGLBDES+",ho_authas="+strHoAutGLBHAS;
//                            strSql+=" , fe_aut= current_timestamp where co_emp="+ intCodEmp +" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+
//                                    " and co_doc="+intCodDoc;
//                            strParTot="T";
//                            stmLoc.executeUpdate(strSql);
//
//                            strSql="select co_tra from tbm_detsolhorsupext where co_emp="+ intCodEmp +" and co_loc="+intCodLoc+
//                                    " and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc;//+" and co_reg="+tblDat.getValueAt(intFil, INT_TBL_COREG).toString();
//                            resSet=stmLoc.executeQuery(strSql);
//
//                            while(resSet.next()){
//                                String strCo_Tra=resSet.getString("co_tra");
//
//                                strSql="select * from tbm_cabconasitra where fe_dia = '"+strFecha+"' and co_tra = "+strCo_Tra;
//                                resSetCab= stmLocCab.executeQuery(strSql);
//
//                                if(resSetCab.next()){
//                                    strSql="update tbm_cabconasitra set st_autHorSupExt = 'S', ho_supExtAut = "+strHoAutGLBHAS+" "+
//                                            "where fe_dia = '"+strFecha+"' and co_tra = "+strCo_Tra;
//                                    stmLocCab.executeUpdate(strSql);
//                                }else{
//                                    strSql="insert into tbm_cabconasitra (co_tra,fe_dia,st_autHorSupExt,ho_supExtAut) values("+strCo_Tra+", '"+strFecha+"', "+
//                                            "'S', "+ strHoAutGLBHAS + " )";
//                                    System.out.println(strSql);
//                                    stmLocCab.executeUpdate(strSql);
//                                }
//                            }
//
//                            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
//                            butAce.setEnabled(false);
//                        }else if(intCantAut>=1){
//                            strSql="update tbm_cabSolHorSupExt set st_aut='A' where co_emp="+ intCodEmp +" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+
//                                   " and co_doc="+intCodDoc;
//                            strParTot="A";
//                            stmLoc.executeUpdate(strSql);
//                            }
//                        }
//                    }else{
//                        
//                        String strCo_Usr=String.valueOf(objParSis.getCodigoUsuario());
//                        int intComp=0;
//                        for(int i=0;i < tblDat.getRowCount();i++){
//                            
//                            if(tblDat.getValueAt(i, INT_TBL_CODUSR).toString().equals(strCo_Usr)){
//                                
//                                if(tblDat.getValueAt(i, INT_TBL_LIN).toString().equals("M")){
//                                    
//                                    if(!(tblDat.getValueAt(i, INT_TBL_CHKAUT).equals(false) && tblDat.getValueAt(i, INT_TBL_CHKAUT).equals(false))){
//                                        intComp++;
//                                    }
//                                }
//                            }
//                        }
//                        
//                        if(intComp==0){
//                            mostrarMsgInf("NO HA AUTORIZADO/DENEGADO LA SOLICITUD");
//                            return false;
//                        }
//                        
//                        if(tblDat.getValueAt(intFilMax, INT_TBL_CHKAUT).equals(true)){
//                            strSql="update tbm_cabSolHorSupExt set st_aut='T',ho_autdes="+strHoAutGLBDES+", ho_authas="+strHoAutGLBHAS + ", fe_aut=current_timestamp";
//                            strSql+=" where co_emp="+ intCodEmp +" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;
//                            strSql+=" and co_doc="+intCodDoc;
//                            strParTot="T";
//                            stmLoc.executeUpdate(strSql);
//
//                            strSql="select co_tra from tbm_detsolhorsupext where co_emp="+ intCodEmp +" and co_loc="+intCodLoc+
//                                    " and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc;//+" and co_reg="+tblDat.getValueAt(intFil, INT_TBL_COREG).toString();
//                            resSet=stmLoc.executeQuery(strSql);
//                            while(resSet.next()){
//                                String strCo_Tra=resSet.getString("co_tra");
//
//                                strSql="select * from tbm_cabconasitra where fe_dia = '"+strFecha+"' and co_tra = "+strCo_Tra;
//                                resSetCab= stmLocCab.executeQuery(strSql);
//
//                                if(resSetCab.next()){
//                                    strSql="update tbm_cabconasitra set st_autHorSupExt = 'S', ho_supExtAut = "+strHoAutGLBHAS+" "+
//                                            "where fe_dia = '"+strFecha+"' and co_tra = "+strCo_Tra;
//                                    stmLocCab.executeUpdate(strSql);
//                                }else{
//                                    strSql="insert into tbm_cabconasitra (co_tra,fe_dia,st_autHorSupExt,ho_supExtAut) values("+strCo_Tra+", '"+strFecha+"', "+
//                                            "'S', "+ strHoAutGLBHAS + " )";
//                                    System.out.println(strSql);
//                                    stmLocCab.executeUpdate(strSql);
//                                }
//                            }
//
//                            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
//                            butAce.setEnabled(false);
//                        }else{
//                            int intRecFil=-1;
//                            //if(objParSis.getCodigoUsuario()!=1){
//                                for(int i=0;i<tblDat.getRowCount();i++){
//                                    if(tblDat.getValueAt(i, INT_TBL_LIN).toString().compareTo("M")==0){
//                                        intRecFil=i;
//                                        i=tblDat.getRowCount();
//                                    }
//                                }
//                                if(intRecFil>-1){
//                                    if(Integer.valueOf(tblDat.getValueAt(intRecFil, INT_TBL_NEJERAUT).toString())<Integer.valueOf(tblDat.getValueAt(intFilMax, INT_TBL_NEJERAUT).toString())){
//                                        strSql="update tbm_cabSolHorSupExt set st_aut='A' where co_emp="+ intCodEmp +" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+
//                                        " and co_doc="+intCodDoc;
//                                        strParTot="A";
//                                        stmLoc.executeUpdate(strSql);
//                                    }else{
//
//                                        strSql="update tbm_cabSolHorSupExt set st_aut='T' , ho_autdes="+strHoAutGLBDES+", ho_authas="+strHoAutGLBHAS;
//                                        strSql+=" , fe_aut=current_timestamp where co_emp="+ intCodEmp +" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+
//                                        " and co_doc="+intCodDoc;
//                                        strParTot="T";
//                                        System.out.println(strSql);
//                                        stmLoc.executeUpdate(strSql);
//
//                                        strSql="select co_tra from tbm_detsolhorsupext where co_emp="+ intCodEmp +" and co_loc="+intCodLoc+
//                                            " and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc;//+" and co_reg="+tblDat.getValueAt(intFil, INT_TBL_COREG).toString();
//                                        resSet=stmLoc.executeQuery(strSql);
//                                        while(resSet.next()){
//                                            String strCo_Tra=resSet.getString("co_tra");
//
//                                            strSql="select * from tbm_cabconasitra where fe_dia = '"+strFecha+"' and co_tra = "+strCo_Tra;
//                                            resSetCab= stmLocCab.executeQuery(strSql);
//
//                                            if(resSetCab.next()){
//                                                strSql="update tbm_cabconasitra set st_autHorSupExt = 'S', ho_supExtAut = "+strHoAutGLBHAS+" "+
//                                                        "where fe_dia = '"+strFecha+"' and co_tra = "+strCo_Tra;
//                                                stmLocCab.executeUpdate(strSql);
//                                            }else{
//                                                strSql="insert into tbm_cabconasitra (co_tra,fe_dia,st_autHorSupExt,ho_supExtAut) values("+strCo_Tra+", '"+strFecha+"', "+
//                                                        "'S', "+ strHoAutGLBHAS + " )";
//                                                System.out.println(strSql);
//                                                stmLocCab.executeUpdate(strSql);
//                                            }
//                                        }
//                                        objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
//                                        //butCan.setEnabled(false);
//                                        butAce.setEnabled(false);
//                                    }
//                                }
//                        }
//                    }
//
//                    stmLoc.close();
//                    stmLoc=null;
//                    resSet.close();
//                    resSet=null;
//                    blnRes=true;
//                    String strHas=strHoAutGLBHAS.replace("'", "");
//                    if(strHas.length()==5){
//                        strHas=strHas+":"+"00";
//                    }
//                    String strDes=strHoAutGLBDES.replace("'", "");
//                    if(strDes.length()==5){
//                        strDes=strDes+":"+"00";
//                    }
//                            
//                    objZafRecHum30.tblDat.setValueAt(strHas, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_HORAUT);
//                    if(strParTot.compareTo("T")==0){
//                        objZafRecHum30.tblDat.setValueAt(Boolean.TRUE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTTOT);
//                        objZafRecHum30.tblDat.setValueAt(Boolean.FALSE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTPAR);
//                        objZafRecHum30.tblDat.setValueAt(Boolean.FALSE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTPEN);
//                    }else if(strParTot.compareTo("A")==0){
//                        objZafRecHum30.tblDat.setValueAt(Boolean.TRUE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTPAR);
//                        objZafRecHum30.tblDat.setValueAt(Boolean.FALSE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTTOT);
//                        objZafRecHum30.tblDat.setValueAt(Boolean.FALSE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTPEN);
//                    }
//                }
//            }catch(java.sql.SQLException Evt) { Evt.printStackTrace(); blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
//            catch(Exception  Evt){ Evt.printStackTrace();blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
//            return blnRes;
//    }
    
    public static java.sql.Time SparseToTime(String hora){
        int h, m, s;
        h = Integer.parseInt(hora.charAt(0)+""+hora.charAt(1)) ;
        m = Integer.parseInt(hora.charAt(3)+""+hora.charAt(4)) ;
        s = Integer.parseInt(hora.charAt(6)+""+hora.charAt(7)) ;
        return (new java.sql.Time(h,m,s));
    }
    
    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        intOpcSelDlg=0;
        dispose();
    }//GEN-LAST:event_butCanActionPerformed
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        intOpcSelDlg=0;
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    // End of variables declaration//GEN-END:variables
 
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v1.0");
            lblTit.setText(strAux);
            arlParDlg=new ArrayList();
            //Configurar los JTables.
            configurarTblDat();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL_LIN,"");
    vecCab.add(INT_TBL_RUBRO,"Rubro");
    vecCab.add(INT_TBL_FEC_FAL,"Fec. Fal.");
    vecCab.add(INT_TBL_VALOR,"Valor");
    
    
    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);
    
    //Configurar ZafTblMod: Establecer las columnas obligatorias.
    java.util.ArrayList arlAux=new java.util.ArrayList();
//    arlAux.add("" + INT_TBL_HORAUTDES);
//    arlAux.add("" + INT_TBL_HORAUTHAS);
    objTblMod.setColumnasObligatorias(arlAux);
    arlAux=null;
    //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
    objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LIN);

    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    tblDat.getTableHeader().setReorderingAllowed(false);

    //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            //objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_VALOR).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_MIN_SEC_SUG).setCellRenderer(objTblCelRenLbl);
            //objTblCelRenLbl=null;
    
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
    tcmAux.getColumn(INT_TBL_RUBRO).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_FEC_FAL).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_VALOR).setPreferredWidth(60);
    
    ArrayList arlColHid=new ArrayList();
//    arlColHid.add(""+INT_TBL_CODUSR);

    //objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
    //arlColHid=null;

    //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
    objMouMotAda=new ZafMouMotAda();
    tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
    
    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    objTblMod.setColumnasEditables(vecAux);
    vecAux=null;
    
    //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
    int intCol[]= new int[1];
    intCol[0]= INT_TBL_VALOR;
    objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
    objTblTot.setValueAt("Total:", 0, 1);
    return blnres;
    }
    
    public boolean cargarReg(int intOp){//intOp==1 sueldo.... intOp==2 horas extras 1
    java.sql.Connection con=null;
    java.sql.Statement stm = null;
    java.sql.ResultSet rst = null;
    boolean blnRes=true;
    String strSQL="";
        try
        {
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                switch(intOp){
                    
                    case 1:
                        strSQL="select * from tbm_ingegrmentra a\n" +
                        "LEFT OUTER JOIN tbm_datgeningegrmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_per=0)\n" +
                        "where a.co_emp="+intCodEmp+"\n" +
                        "and a.ne_ani="+intNeAni+"\n" +
                        "and a.ne_mes="+intNeMes+"\n" +
                        "and a.ne_per=0 \n" +
                        "and a.co_rub="+intCoRub+"\n" +
                        "and a.co_tra="+intCoTra;
                        
                        //Limpiar vector de datos.
                        vecDat.clear();
                        System.out.println("query: " + strSQL);
                        rst=stm.executeQuery(strSQL);

                        double nd_valrubsindes=0;
                        if (rst.next()){
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_LIN,"");
                            vecReg.add(INT_TBL_RUBRO,"Sueldo");
                            vecReg.add(INT_TBL_FEC_FAL,"");
                            nd_valrubsindes=rst.getDouble("nd_valrubsindes");
                            vecReg.add(INT_TBL_VALOR,rst.getDouble("nd_valrubsindes"));
                            vecDat.add(vecReg);
                        }

                        if(nd_valrubsindes>0){
                            if((nd_valrubsindes-this.dblNdValRubPag)>0){
                                strSQL="";
                                strSQL+="select * from (\n" +
                                "select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, (case c.st_jusatr when 'S' then true else false end) as chkJusAtr,  \n" +
                                "c.tx_obsjusatr,  (case c.st_jusfal when 'S' then true else false end) as chkJusFal,  c.tx_obsjusfal, g.ho_ent as ho_entHorTra, \n" +
                                "g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra, \n" +
                                "(case c.ho_sal < g.ho_sal when true then (g.ho_sal-c.ho_sal) else null end) as hor_ade, f.co_emp from (  \n" +
                                "\n" +
                                "select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal, a.tx_obsjusfal,b.st_reg \n" +
                                "from tbm_cabconasitra a  \n" +
                                "inner join tbm_tra b on (a.co_tra = b.co_tra) and b.st_reg like 'A')c  \n" +
                                "inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_reg='A')  \n" +
                                "left outer join tbm_dep d on d.co_dep=f.co_dep   \n" +
                                "inner join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))    \n" +
                                "INNER JOIN tbm_callab h on (h.co_hor=f.co_hor and h.fe_dia=c.fe_dia and h.st_dialab='S') \n"+
                                "WHERE c.fe_dia between (select fe_des from tbm_feccorrolpag where co_emp="+intCodEmp+" and ne_ani="+intNeAni+" and ne_mes="+intNeMes+" and ne_per=1)\n" +
                                "AND (select fe_has from tbm_feccorrolpag where co_emp="+intCodEmp+" and ne_ani="+intNeAni+" and ne_mes="+intNeMes+" and ne_per=2)\n" +
                                "and not (EXTRACT(DOW FROM c.fe_dia ) in (6,7))\n" +
                                "AND (c.ho_ent is null AND c.ho_sal is null) \n" +
                                "\n" +
                                "UNION  \n" +
                                "\n" +
                                "select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, (case c.st_jusatr when 'S' then true else false end) as chkJusAtr,  \n" +
                                "c.tx_obsjusatr,  (case c.st_jusfal when 'S' then true else false end) as chkJusFal,  c.tx_obsjusfal, g.ho_ent as ho_entHorTra, \n" +
                                "g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra, \n" +
                                "(case c.ho_sal < g.ho_sal when true then (g.ho_sal-c.ho_sal) else null end) as hor_ade,f.co_emp  from ( \n" +
                                "select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal, a.tx_obsjusfal,b.st_reg \n" +
                                "from tbm_cabconasitra a  \n" +
                                "inner join tbm_tra b on (a.co_tra = b.co_tra and b.st_reg like 'A' and (a.ho_ent is not null or a.ho_sal is not null)))c  \n" +
                                "inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_horfij like 'N' and f.st_reg='A')  \n" +
                                "left outer join tbm_dep d on d.co_dep=f.co_dep   \n" +
                                "left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))    \n" +
                                "INNER JOIN tbm_callab h on (h.co_hor=f.co_hor and h.fe_dia=c.fe_dia and h.st_dialab='S') \n"+
                                "WHERE c.fe_dia between (select fe_des from tbm_feccorrolpag where co_emp="+intCodEmp+" and ne_ani="+intNeAni+" and ne_mes="+intNeMes+" and ne_per=1)\n" +
                                "AND (select fe_has from tbm_feccorrolpag where co_emp="+intCodEmp+" and ne_ani="+intNeAni+" and ne_mes="+intNeMes+" and ne_per=2)\n" +
                                "and not (EXTRACT(DOW FROM c.fe_dia ) in (6,7))\n" +
                                "AND (c.ho_ent is null AND c.ho_sal is null) \n" +
                                "\n" +
                                ")x\n" +
                                "where x.co_tra="+intCoTra;

        //                        vecDat.clear();
                                System.out.println("query faltas: " + strSQL);
                                rst=stm.executeQuery(strSQL);

                                while(rst.next()){

                                    vecReg=new Vector();
                                    vecReg.add(INT_TBL_LIN,"");
                                    vecReg.add(INT_TBL_RUBRO,"");
                                    //formatearFecha(java.util.Date fecha, String formato)
                                    vecReg.add(INT_TBL_FEC_FAL,objUti.formatearFecha(rst.getDate("fe_dia"), "dd/MMM/yyyy"));
                                    double nd_ValDia=nd_valrubsindes/30;
                                    vecReg.add(INT_TBL_VALOR,(nd_ValDia*-1));
                                    vecDat.add(vecReg);
                                }
                            }
                        }
                        
                        break;
                    case 2:
                        
                        strSQL="";
                        strSQL="select * from tbm_ingegrmentra a\n" +
//                        "LEFT OUTER JOIN tbm_datgeningegrmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_per=0)\n" +
                                "where a.co_emp="+intCodEmp+"\n" +
                                "and a.ne_ani="+intNeAni+"\n" +
                                "and a.ne_mes="+intNeMes+"\n" +
                                "and a.ne_per=0 \n" +
                                "and a.co_rub="+intCoRub+"\n" +
                                "and a.co_tra="+intCoTra;
                        
                        //Limpiar vector de datos.
                        vecDat.clear();
                        System.out.println("query: " + strSQL);
                        rst=stm.executeQuery(strSQL);

                        nd_valrubsindes=0;
                        if (rst.next()){
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_LIN,"");
                            vecReg.add(INT_TBL_RUBRO,"Horas Extras 1");
                            vecReg.add(INT_TBL_FEC_FAL,"");
                            nd_valrubsindes=rst.getDouble("nd_valrubsindes");
                            vecReg.add(INT_TBL_VALOR,rst.getDouble("nd_valrubsindes"));
                            vecDat.add(vecReg);
                        }
                         
                        //query para obtener cantidad de dias sabados en rango de fechas...
                        strSQL="";
                        strSQL+=" select count(distinct fe_dia) as canDiasSab from tbm_callab c \n";
                        strSQL+="where c.fe_dia between (select fe_des from tbm_feccorrolpag where co_emp="+intCodEmp+" and ne_ani="+intNeAni+" and ne_mes="+intNeMes+" and ne_per=1)\n";
                        strSQL+="AND (select fe_has from tbm_feccorrolpag where co_emp="+intCodEmp+" and ne_ani="+intNeAni+" and ne_mes="+intNeMes+" and ne_per=2)\n";
                        strSQL+=" and (EXTRACT(DOW FROM fe_dia ) in (6))";
                        
                        int intCanDiaSabLab=0;
                        
                        rst=stm.executeQuery(strSQL);

                        if(rst.next()){
                            
                            intCanDiaSabLab=rst.getInt("canDiasSab");
                        }
                        
                        if(nd_valrubsindes>0){
                            if((nd_valrubsindes-this.dblNdValRubPag)>0){
                                
                                strSQL="";
                                strSQL="select * from ( \n" +
                                "select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, (case c.st_jusatr when 'S' then true else false end) as chkJusAtr,  \n" +
                                "c.tx_obsjusatr,  (case c.st_jusfal when 'S' then true else false end) as chkJusFal,  c.tx_obsjusfal, g.ho_ent as ho_entHorTra, \n" +
                                "g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra, \n" +
                                "(case c.ho_sal < g.ho_sal when true then (g.ho_sal-c.ho_sal) else null end) as hor_ade, f.co_emp from (  \n" +
                                "select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal, a.tx_obsjusfal,b.st_reg \n" +
                                "from tbm_cabconasitra a  \n" +
                                "inner join tbm_tra b on (a.co_tra = b.co_tra) and b.st_reg like 'A')c  \n" +
                                "inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_reg='A')  \n" +
                                "left outer join tbm_dep d on d.co_dep=f.co_dep   \n" +
                                "inner join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))  \n" +  
                                "INNER JOIN tbm_callab h on (h.co_hor=f.co_hor and h.fe_dia=c.fe_dia and h.st_dialab='S') \n"+
//                                "WHERE c.fe_dia BETWEEN '2013-05-26' AND '2013-06-25' \n" +
                                "WHERE c.fe_dia between (select fe_des from tbm_feccorrolpag where co_emp="+intCodEmp+" and ne_ani="+intNeAni+" and ne_mes="+intNeMes+" and ne_per=1)\n" +
                                "AND (select fe_has from tbm_feccorrolpag where co_emp="+intCodEmp+" and ne_ani="+intNeAni+" and ne_mes="+intNeMes+" and ne_per=2)\n" +   
                                "and (EXTRACT(DOW FROM c.fe_dia ) in (6,7))\n" +
                                "AND (c.ho_ent is null AND c.ho_sal is null) \n" +
                                "and f.co_tra not in (4,6,8,19,24,27,30,31,32,33,37,39,41,42,45,46,80,81,52,65) \n" +
                                "AND not ((case c.st_jusfal when 'S' then true else false end)=true) \n" +
                                "UNION  \n" +
                                "select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, (case c.st_jusatr when 'S' then true else false end) as chkJusAtr,  \n" +
                                "c.tx_obsjusatr,  (case c.st_jusfal when 'S' then true else false end) as chkJusFal,  c.tx_obsjusfal, g.ho_ent as ho_entHorTra, \n" +
                                "g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra, \n" +
                                "(case c.ho_sal < g.ho_sal when true then (g.ho_sal-c.ho_sal) else null end) as hor_ade,f.co_emp  from ( \n" +
                                "select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal, a.tx_obsjusfal,b.st_reg \n" +
                                "from tbm_cabconasitra a  \n" +
                                "inner join tbm_tra b on (a.co_tra = b.co_tra and b.st_reg like 'A' and (a.ho_ent is not null or a.ho_sal is not null)))c  \n" +
                                "inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_horfij like 'N' and f.st_reg='A')  \n" +
                                "left outer join tbm_dep d on d.co_dep=f.co_dep   \n" +
                                "left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))    \n" +
                                "INNER JOIN tbm_callab h on (h.co_hor=f.co_hor and h.fe_dia=c.fe_dia and h.st_dialab='S') \n"+
//                                "WHERE c.fe_dia BETWEEN '2013-05-26' AND '2013-06-25' \n" +
                                "WHERE c.fe_dia between (select fe_des from tbm_feccorrolpag where co_emp="+intCodEmp+" and ne_ani="+intNeAni+" and ne_mes="+intNeMes+" and ne_per=1)\n" +
                                "AND (select fe_has from tbm_feccorrolpag where co_emp="+intCodEmp+" and ne_ani="+intNeAni+" and ne_mes="+intNeMes+" and ne_per=2)\n" +        
                                "and (EXTRACT(DOW FROM c.fe_dia ) in (6,7))\n" +
                                "AND (c.ho_ent is null AND c.ho_sal is null) \n" +
                                "AND not ((case c.st_jusfal when 'S' then true else false end)=true) \n" +
                                "and f.co_tra not in (4,6,8,19,24,27,30,31,32,33,37,39,41,42,45,46,80,81,52,65) \n" +
                                "order by fe_dia asc,tx_ape, tx_nom \n"+
                                ") q \n"+
                                "WHERE q.co_tra="+intCoTra+"\n";
                                
                                System.out.println("query faltas he: " + strSQL);
                                rst=stm.executeQuery(strSQL);

                                while(rst.next()){
                                    
                                    vecReg=new Vector();
                                    vecReg.add(INT_TBL_LIN,"");
                                    vecReg.add(INT_TBL_RUBRO,"");
                                    //formatearFecha(java.util.Date fecha, String formato)
                                    vecReg.add(INT_TBL_FEC_FAL,objUti.formatearFecha(rst.getDate("fe_dia"), "dd/MMM/yyyy"));
                                    double nd_ValDia=nd_valrubsindes/intCanDiaSabLab;
                                    vecReg.add(INT_TBL_VALOR,(nd_ValDia*-1));
                                    vecDat.add(vecReg);
                                }
                            }
                        }
                }
            }

            //Asignar vectores al modelo.
            objTblMod.setData(vecDat);
            
            tblDat.setModel(objTblMod);
            objTblTot.calcularTotales();            
            vecDat.clear();
        }
        catch (java.sql.SQLException e)
        {
            e.printStackTrace();
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }finally {
            try{rst.close();rst=null;}catch(Throwable ignore){}
            try{stm.close();stm=null;}catch(Throwable ignore){}
            try{con.close();con=null;}catch(Throwable ignore){}
        }
        return blnRes;
    }
    
    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            objTblMod.removeAllRows();
            //txaObs1.setText("");
            //txaObs2.setText("");
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Esta función establece los parámetros que debe utilizar el JDialog.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intCodTipDoc El código del tipo de documento.
     * @param intCodDoc El código de la cotización/documento.
     * @param intCodCli El código del cliente.
     * @param strIdeCli La identificación del cliente.
     */
    public void setParDlg(int intCodEmp, int intCodLoc, int intCoTra)
    {
        this.intCodEmp=intCodEmp;
        this.intCodLoc=intCodLoc;
    }
    
    public void setParDlg(ZafParSis objZafParsis, ZafRecHum15 objRecHum15,int intCodEmp, int intCodLoc, int intCoTra, int intNeAni, int intNeMes, int intNePer, int intCoRub, double dblNdValRubPag) 
    {
        this.objParSis=objZafParsis;
        this.intCodEmp=intCodEmp;
        this.intCodLoc=intCodLoc;
        this.intCoTra=intCoTra;
        this.intNeAni=intNeAni;
        this.intNeMes=intNeMes;
        this.intNePer=intNePer;
        this.intCoRub=intCoRub;
        this.dblNdValRubPag=dblNdValRubPag;
        this.objZafRecHum15=objRecHum15;
    }
    
    /**
     * Esta función obtiene la opción que seleccionó el usuario en el JDialog.
     * Puede devolver uno de los siguientes valores:
     * <UL>
     * <LI>0: Click en el botón Cancelar.
     * <LI>1: Click en el botón Aceptar.
     * </UL>
     * <BR>Nota.- La opción predeterminada es el botón Cancelar.
     * @return La opción seleccionada por el usuario.
     */
    public int getOpcSelDlg()
    {
        return intOpcSelDlg;
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_LIN:
                    strMsg="";
                    break;
                case INT_TBL_RUBRO:
                    strMsg="Nombre del rubro";
                    break;
                case INT_TBL_FEC_FAL:
                    strMsg="Fecha de la falta";
                    break;
                case INT_TBL_VALOR:
                    strMsg="Valor";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
}
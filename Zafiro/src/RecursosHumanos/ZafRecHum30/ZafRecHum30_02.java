package RecursosHumanos.ZafRecHum30;

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
import Librerias.ZafUtil.ZafUtil;
import Maestros.ZafMae07.ZafMae07_01;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Roberto Flores
 */
public class ZafRecHum30_02 extends javax.swing.JDialog 
{

    private ZafRecHum30 objZafRecHum30;

    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
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
    //Variables de la clase.
    private int intOpcSelDlg;                                   //Opción seleccionada en el JDialog.
    private int intCodEmp;                                      //Código de la empresa.
    private int intCodLoc;                                      //Código del local.
    private int intCodTipDoc;                                   //Código del tipo de documento.
    private int intCodDoc;                                      //Código del docuemnto.
    private int intNumDoc;
    private String strFecha = "";
    private int intCodAut;                                      //Código de la autorización.
    private int intCodCli;                                      //Código del cliente.
    private String strIdeCli;                                   //Identificación del cliente.
    private String strEstDoc;                                   //Estado del documento.
    private String strEstValDoc;                                //Estado de validez del documento.

    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor de Check Box 
    private ZafTblCelEdiChk objTblCelEdiChk2;                   //Editor de Check Box 
    private ZafTblCelRenChk objTblCelRenChkLab;                 //Renderer de Check Box

    // TABLA DE DATOS
    private final int INT_TBL_LIN = 0;
    private final int INT_TBL_CODUSR = 1;
    private final int INT_TBL_USR = 2;
    private final int INT_TBL_NOMUSR = 3;
    private final int INT_TBL_CHKAUT = 4;
    private final int INT_TBL_CHKDEN = 5;
    private final int INT_TBL_HORAUTDES = 6;
    private final int INT_TBL_HORAUTHAS = 7;
    private final int INT_TBL_OBS = 8;
    private final int INT_TBL_BUTOBS = 9;
    private final int INT_TBL_COREG = 10;
    private final int INT_TBL_NEJERAUT = 11;
    private final int INT_TBL_STOBLSEGJERAUT = 12;

    //private Vector vecDat, vecReg;
    String strCodEmp = "";
    String strCodLoc = "";
    String strCodTipdoc = "";
    String strCodDoc = "";
    String strNumDoc = "";
    String strHorSupExt = "";
    int intDiaSem = 0;

    /**
     * Creates new form ZafHer03_01
     */
    public ZafRecHum30_02(java.awt.Frame parent, boolean modal, ZafParSis obj) 
    {
        super(parent, modal);
        initComponents();
        vecDat = new Vector();
        //Inicializar objetos.
        objParSis = obj;
        intOpcSelDlg = 0;
        if (!configurarFrm()) {
            exitForm();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
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
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBot.add(butAce);

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

        setSize(new java.awt.Dimension(600, 400));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        if (objThrGUI == null) {
            objThrGUI = new ZafThreadGUI();
            objThrGUI.setIndFunEje(0);
            objThrGUI.start();
        }
    }//GEN-LAST:event_butAceActionPerformed

    /**
     * Metodo que obtiene la fecha de corte del mes actual en la empresa de la solicitud de horas extras/suplementaria
     * @param con conexion
     * @return La fecha
     */
    private Calendar obtenerFechCort(Connection con){
        Calendar calFechAct=Calendar.getInstance();
        Calendar calFechCor=Calendar.getInstance();
        int intAni=calFechAct.get(Calendar.YEAR);
        int intMes=calFechAct.get(Calendar.MONTH)+1;
        Statement stm=null;
        ResultSet rst=null;
        String strFecCorte="", strQry="";
        
        try{
            stm=con.createStatement();
            strQry="select fe_has from tbm_feccorrolpag where co_emp="+intCodEmp+ " and ne_ani="+intAni+" and ne_per=2 and ne_mes="+intMes;
            rst=stm.executeQuery(strQry);
            if(rst!=null && rst.next()){
                strFecCorte=rst.getString("fe_has");
            }
            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
            calFechCor.setTime(sf.parse(strFecCorte));
            rst.close();
            stm.close();
            
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return calFechCor;
    }
    
    /**
     * Método que valida si la fecha de autorizacion esta dentro del rango de fecha maxima
     * de autorización.
     * @return true cuando esta dentro del rango y false cuando ya paso su fecha maxima
     */
    private boolean validarEsValFechMaxAut(Connection cnx){
        boolean booRet=false;
        String strFechSol=strFecha;
        Calendar calFechSol,calFechMax, calFechAct;
        try{
            calFechSol=Calendar.getInstance();
            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
            calFechSol.setTime(sf.parse(strFechSol));
            
            /*OBTENGO FECHA MAXIMA YA QUE SON HASTA 7 DIAS LABORALES DESPUES DE LA FECHA SOLICITADA*/
            calFechMax=Calendar.getInstance();
            calFechMax.setTime(sf.parse(strFechSol));
            calFechMax.add(Calendar.DATE, 7);
            /*OBTENGO FECHA MAXIMA YA QUE SON HASTA 7 DIAS LABORALES DESPUES DE LA FECHA SOLICITADA*/
            
            /*COMPARACION FECHA ACTUAL CON FECHA MAXIMA*/

            java.util.Date dateObj = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
             
            calFechAct = java.util.Calendar.getInstance();
            calFechAct.setTime(dateObj);
            
            //calFechAct=Calendar.getInstance();
            /*Le pongo la hora los minutos, los segundos y milisegundos en 0 para que no compares eso sino solo fecha*/
            calFechAct.set(Calendar.HOUR, 0);
            calFechAct.set(Calendar.MINUTE, 0);
            calFechAct.set(Calendar.SECOND, 0);
            calFechAct.set(Calendar.MILLISECOND,0);
            if(calFechAct.compareTo(calFechMax)>0){
                booRet=false;
            }else{
                //Calendar calFechCor=obtenerFechCort(cnx);
                /*if(calFechAct.compareTo(calFechCor)>0){
                    booRet=false;
                }else{*/
                    booRet=true;
                //}
            }
            
        }catch(ParseException ex){
            ex.printStackTrace();
        }
        return booRet;
    }
    
    
    public boolean guardarDat(java.sql.Connection conn) 
    {
        boolean blnRes = false;
        java.sql.Statement stmLoc, stmLocCab;
        java.sql.ResultSet resSet, resSetCab;
        String strSql = "";
        String strHoAutGLBDES = "";
        String strHoAutGLBHAS = "";
        String strParTot = "";
//Aqui entra despues de actualizar registro para autorizar o denegar bostel
        try 
        {
            if (conn != null) 
            {
                stmLoc = conn.createStatement();
                stmLocCab = conn.createStatement();
                int intCanUsrAut = tblDat.getRowCount();
                for (int intFil = 0; intFil < tblDat.getRowCount(); intFil++) {
                    //System.out.println("linea " + intFil +  "    :"+ tblDat.getValueAt(intFil, INT_TBL_LIN).toString());
                    if (tblDat.getValueAt(intFil, INT_TBL_LIN).toString().compareTo("M") == 0) 
                    {
                        //Se comento para pruebas bostel
                        //Validacion de máximo hasta 7 dias calendario para autorizar o denegar solicitudes 
//                        if(!validarEsValFechMaxAut(conn)){
//                            mostrarMsgInf("La Solicitud tiene mas de 7 dias de solicitada, por lo que no se puede procesar");
//                            return false;
//                        }
                        //Coje hora Desde nada mas bostel
                        Object objHoAutDes = tblDat.getValueAt(intFil, INT_TBL_HORAUTDES);
                        String strHoAutDes = "";
                        if (objHoAutDes != null) {
                            strHoAutDes = objUti.codificar(objHoAutDes.toString());
                            strHoAutGLBDES = strHoAutDes;
                        } else {
                            if (objParSis.getCodigoUsuario() == Integer.valueOf(
                                    tblDat.getValueAt(intFil, INT_TBL_CODUSR).toString()) || objParSis.getCodigoUsuario() == 1) {
                                if (tblDat.getValueAt(intFil, INT_TBL_CHKDEN).equals(true)) {
                                    tblDat.setValueAt("00:00", intFil, INT_TBL_HORAUTDES);
                                    strHoAutDes = objUti.codificar("00:00");
                                    strHoAutGLBDES = strHoAutDes;
                                } else {
                                    mostrarMsgInf("Debe ingresar las horas por autorizar");
                                    return false;
                                }
                            }
                        }
                            //Coje hora Hasta nada mas bostel
                        Object objHoAutHas = tblDat.getValueAt(intFil, INT_TBL_HORAUTHAS);
                        String strHoAutHas = "";
                        if (objHoAutHas != null) {
                            strHoAutHas = objUti.codificar(objHoAutHas.toString());
                            strHoAutGLBHAS = strHoAutHas;
                        } else {
                            if (objParSis.getCodigoUsuario() == Integer.valueOf(
                                    tblDat.getValueAt(intFil, INT_TBL_CODUSR).toString()) || objParSis.getCodigoUsuario() == 1) {
                                if (tblDat.getValueAt(intFil, INT_TBL_CHKDEN).equals(true)) {
                                    tblDat.setValueAt("00:00", intFil, INT_TBL_HORAUTHAS);
                                    strHoAutHas = objUti.codificar("00:00");
                                    strHoAutGLBHAS = strHoAutHas;
                                } else {
                                    mostrarMsgInf("Debe ingresar las horas por autorizar");
                                    return false;
                                }
                            }
                        }

                        Object objObs = tblDat.getValueAt(intFil, INT_TBL_OBS);
                        String strObs = "";
                        if (objObs != null) {
                            if (strObs != null) {
                                strObs = objUti.codificar(tblDat.getValueAt(intFil, INT_TBL_OBS).toString());
                            } else {
                                strObs = null;
                            }
                        } else {
                            strObs = null;
                        }

                        String strAut = "";
                        //1ero Aqui entra cuando el checkbox tiene visto en Autorizar
                        if (tblDat.getValueAt(intFil, INT_TBL_CHKAUT).equals(true)) {
                            strAut = "'A'";
                            strParTot = "A";
                        }
                        if (tblDat.getValueAt(intFil, INT_TBL_CHKDEN).equals(true)) {
                            //Aqui entra cuando el checkbox tiene visto en Denegar bostel
                            strAut = "'D'";
//                                strHoAutDes=objUti.codificar("00");
                            strHoAutGLBDES = "null";
//                                strHoAutHas=objUti.codificar("00");
                            strHoAutGLBHAS = "null";
                        }
                        if (tblDat.getValueAt(intFil, INT_TBL_CHKAUT).equals(false) && tblDat.getValueAt(intFil, INT_TBL_CHKDEN).equals(false)) {
                            mostrarMsgInf("NO HA INGRESADO SI AUTORIZA O DENIEGA LA SOLICITUD");
                            return false;
                        }
                        //2do Aqui Cambia estado en la tabla cuando esta autorizada con la hora desde y hasta tabla aut bostel
                        if (objParSis.getCodigoUsuario() == Integer.valueOf(tblDat.getValueAt(intFil, INT_TBL_CODUSR).toString()) || objParSis.getCodigoUsuario() == 1) {
                            strSql = "update tbm_autSolHorSupExt set st_aut=" + strAut + " ,ho_autdes=" + strHoAutDes + " ,ho_authas=" + strHoAutHas + " , tx_obsaut=" + strObs + ", "
                                    + "fe_aut=CURRENT_TIMESTAMP, co_usraut =" + objParSis.getCodigoUsuario() + " , tx_comaut = '" + objParSis.getDireccionIP() + "' "
                                    + "where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc + " and co_doc=" + intCodDoc + " and co_reg=" + tblDat.getValueAt(intFil, INT_TBL_COREG).toString();
                            System.out.println("CoReg"+tblDat.getValueAt(intFil, INT_TBL_COREG).toString());
                            System.out.println(strSql);
                            stmLoc.executeUpdate(strSql);
                            //3ro Aqui update con las fechas de la hora autorizadas bostel tabla cabecera solicitud
                            strSql = "update tbm_cabSolHorSupExt set ho_autdes=" + strHoAutDes + ",ho_authas=" + strHoAutHas + " where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc
                                    + " and co_doc=" + intCodDoc;
                            stmLoc.executeUpdate(strSql);
                        }
                    }
                }
                //4to query en ejecutat
                strSql = "select * from tbm_autSolHorSupExt where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc
                        + " and co_doc=" + intCodDoc + " and st_aut like 'A'";
                resSet = stmLoc.executeQuery(strSql);

                int intCantAut = 0;
                while (resSet.next()) {
                    intCantAut++;
                }

                int intFilMax = tblDat.getRowCount() - 1;
                boolean blnSeCmp = false;
                if (objParSis.getCodigoUsuario() == 1) { //Cuando es Usuario admin

                    int intComp = 0;
                    for (int i = 0; i < tblDat.getRowCount(); i++) {

                        if (tblDat.getValueAt(i, INT_TBL_LIN).toString().equals("M")) {

                            if (!(tblDat.getValueAt(i, INT_TBL_CHKAUT).equals(false) && tblDat.getValueAt(i, INT_TBL_CHKDEN).equals(false))) {
                                intComp++;
                            }
                        }
                    }

                    if (intComp == 0) {
                        mostrarMsgInf("NO HA AUTORIZADO/DENEGADO LA SOLICITUD");
                        return false;
                    }

                    if (tblDat.getValueAt(intFilMax, INT_TBL_CHKDEN).equals(true)) {
                        strSql = "update tbm_cabSolHorSupExt set st_aut='D',ho_autdes=NULL,ho_authas=NULL";
                        strSql += " , fe_aut=NULL where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc;
                        strSql += " and co_doc=" + intCodDoc;
                        stmLoc.executeUpdate(strSql);

                        Object objObs = tblDat.getValueAt(intFilMax, INT_TBL_OBS);
                        String strObs = "";
                        if (objObs != null) {
                            if (strObs != null) {
                                strObs = objUti.codificar(tblDat.getValueAt(intFilMax, INT_TBL_OBS).toString());
                            } else {
                                strObs = null;
                            }
                        } else {
                            strObs = null;
                        }

                        strSql = "update tbm_autSolHorSupExt set st_aut='D' ,ho_autdes=NULL ,ho_authas=NULL , tx_obsaut=" + strObs + ", "
                                + "fe_aut=current_timestamp, co_usraut =" + objParSis.getCodigoUsuario() + " , tx_comaut = '" + objParSis.getDireccionIP() + "' "
                                + "where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc + " and co_doc=" + intCodDoc + " and co_reg=" + tblDat.getValueAt(intFilMax, INT_TBL_COREG).toString();

                        //System.out.println("guardarDat: "+strSql);
                        stmLoc.executeUpdate(strSql);

                        objZafRecHum30.tblDat.setValueAt("", objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_HORAUT);
                        objZafRecHum30.tblDat.setValueAt(Boolean.TRUE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTPEN);
                        objZafRecHum30.tblDat.setValueAt(Boolean.FALSE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTPAR);
                        objZafRecHum30.tblDat.setValueAt(Boolean.FALSE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTTOT);
                        blnRes = true;
                        return blnRes;

                    }

                    if (tblDat.getValueAt(intFilMax, INT_TBL_CHKAUT).equals(true)) {
                        strSql = "update tbm_cabSolHorSupExt set st_aut='T',ho_autdes=" + strHoAutGLBDES + ",ho_authas=" + strHoAutGLBHAS;
                        strSql += " , fe_aut=current_timestamp where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc;
                        strSql += " and co_doc=" + intCodDoc;
                        strParTot = "T";
                        stmLoc.executeUpdate(strSql);

                        strSql = "select co_tra from tbm_detsolhorsupext where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc
                                + " and co_tipdoc=" + intCodTipDoc + " and co_doc=" + intCodDoc;//+" and co_reg="+tblDat.getValueAt(intFil, INT_TBL_COREG).toString();
                        resSet = stmLoc.executeQuery(strSql);
                        while (resSet.next()) {
                            String strCo_Tra = resSet.getString("co_tra");

                            strSql = "select * from tbm_cabconasitra where fe_dia = '" + strFecha + "' and co_tra = " + strCo_Tra;
                            resSetCab = stmLocCab.executeQuery(strSql);

                            if (resSetCab.next()) {
                                strSql = "update tbm_cabconasitra set st_autHorSupExt = 'S', ho_supExtAut = " + strHoAutGLBHAS + " "
                                        + "where fe_dia = '" + strFecha + "' and co_tra = " + strCo_Tra;
                                stmLocCab.executeUpdate(strSql);
                            } else {
                                strSql = "insert into tbm_cabconasitra (co_tra,fe_dia,st_autHorSupExt,ho_supExtAut) values(" + strCo_Tra + ", '" + strFecha + "', "
                                        + "'S', " + strHoAutGLBHAS + " )";
                                System.out.println(strSql);
                                stmLocCab.executeUpdate(strSql);
                            }
                        }

                        objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
                        butAce.setEnabled(false);
                        blnSeCmp = true;
                    }
                    if (!blnSeCmp) {
                        if (intCanUsrAut == intCantAut) {
                            strSql = "update tbm_cabSolHorSupExt set st_aut='T',ho_autdes=" + strHoAutGLBDES + ",ho_authas=" + strHoAutGLBHAS;
                            strSql += " , fe_aut= current_timestamp where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc
                                    + " and co_doc=" + intCodDoc;
                            strParTot = "T";
                            stmLoc.executeUpdate(strSql);

                            strSql = "select co_tra from tbm_detsolhorsupext where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc
                                    + " and co_tipdoc=" + intCodTipDoc + " and co_doc=" + intCodDoc;//+" and co_reg="+tblDat.getValueAt(intFil, INT_TBL_COREG).toString();
                            resSet = stmLoc.executeQuery(strSql);

                            while (resSet.next()) {
                                String strCo_Tra = resSet.getString("co_tra");

                                strSql = "select * from tbm_cabconasitra where fe_dia = '" + strFecha + "' and co_tra = " + strCo_Tra;
                                resSetCab = stmLocCab.executeQuery(strSql);

                                if (resSetCab.next()) {
                                    strSql = "update tbm_cabconasitra set st_autHorSupExt = 'S', ho_supExtAut = " + strHoAutGLBHAS + " "
                                            + "where fe_dia = '" + strFecha + "' and co_tra = " + strCo_Tra;
                                    stmLocCab.executeUpdate(strSql);
                                } else {
                                    strSql = "insert into tbm_cabconasitra (co_tra,fe_dia,st_autHorSupExt,ho_supExtAut) values(" + strCo_Tra + ", '" + strFecha + "', "
                                            + "'S', " + strHoAutGLBHAS + " )";
                                    System.out.println(strSql);
                                    stmLocCab.executeUpdate(strSql);
                                }
                            }

                            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
                            butAce.setEnabled(false);
                        } else if (intCantAut >= 1) {
                            strSql = "update tbm_cabSolHorSupExt set st_aut='A' where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc
                                    + " and co_doc=" + intCodDoc;
                            strParTot = "A";
                            stmLoc.executeUpdate(strSql);
                        }
                    }
                } else {

                    String strCo_Usr = String.valueOf(objParSis.getCodigoUsuario());
                    int intComp = 0;
                    for (int i = 0; i < tblDat.getRowCount(); i++) {

                        if (tblDat.getValueAt(i, INT_TBL_CODUSR).toString().equals(strCo_Usr)) {

                            if (tblDat.getValueAt(i, INT_TBL_LIN).toString().equals("M")) {

                                if (!(tblDat.getValueAt(i, INT_TBL_CHKAUT).equals(false) && tblDat.getValueAt(i, INT_TBL_CHKDEN).equals(false))) {
                                    intComp++;
                                }
                            }
                        }
                    }

                    if (intComp == 0) {
                        mostrarMsgInf("NO HA AUTORIZADO/DENEGADO LA SOLICITUD");
                        return false;
                    }

                    if (tblDat.getValueAt(intFilMax, INT_TBL_CHKDEN).equals(true)) {
                        strSql = "update tbm_cabSolHorSupExt set st_aut='D',ho_autdes=NULL,ho_authas=NULL";
                        strSql += " , fe_aut=NULL where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc;
                        strSql += " and co_doc=" + intCodDoc;
                        stmLoc.executeUpdate(strSql);

                        Object objObs = tblDat.getValueAt(intFilMax, INT_TBL_OBS);
                        String strObs = "";
                        if (objObs != null) {
                            if (strObs != null) {
                                strObs = objUti.codificar(tblDat.getValueAt(intFilMax, INT_TBL_OBS).toString());
                            } else {
                                strObs = null;
                            }
                        } else {
                            strObs = null;
                        }

                        strSql = "update tbm_autSolHorSupExt set st_aut='D' ,ho_autdes=NULL ,ho_authas=NULL , tx_obsaut=" + strObs + ", "
                                + "fe_aut=current_timestamp, co_usraut =" + objParSis.getCodigoUsuario() + " , tx_comaut = '" + objParSis.getDireccionIP() + "' "
                                + "where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc + " and co_doc=" + intCodDoc + " and co_reg=" + tblDat.getValueAt(intFilMax, INT_TBL_COREG).toString();

                        System.out.println(strSql);
                        stmLoc.executeUpdate(strSql);

                        objZafRecHum30.tblDat.setValueAt("", objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_HORAUT);
                        objZafRecHum30.tblDat.setValueAt(Boolean.TRUE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTPEN);
                        objZafRecHum30.tblDat.setValueAt(Boolean.FALSE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTPAR);
                        objZafRecHum30.tblDat.setValueAt(Boolean.FALSE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTTOT);

                        blnRes = true;
                        return blnRes;

                    }
                    
                    if (tblDat.getValueAt(intFilMax, INT_TBL_CHKAUT).equals(true)) {
                        strSql = "update tbm_cabSolHorSupExt set st_aut='T',ho_autdes=" + strHoAutGLBDES + ", ho_authas=" + strHoAutGLBHAS + ", fe_aut=current_timestamp";
                        strSql += " where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc;
                        strSql += " and co_doc=" + intCodDoc;
                        strParTot = "T";
                        stmLoc.executeUpdate(strSql);

                        strSql = "select co_tra from tbm_detsolhorsupext where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc
                                + " and co_tipdoc=" + intCodTipDoc + " and co_doc=" + intCodDoc;//+" and co_reg="+tblDat.getValueAt(intFil, INT_TBL_COREG).toString();
                        resSet = stmLoc.executeQuery(strSql);
                        while (resSet.next()) {
                            String strCo_Tra = resSet.getString("co_tra");

                            strSql = "select * from tbm_cabconasitra where fe_dia = '" + strFecha + "' and co_tra = " + strCo_Tra;
                            resSetCab = stmLocCab.executeQuery(strSql);

                            if (resSetCab.next()) {
                                strSql = "update tbm_cabconasitra set st_autHorSupExt = 'S', ho_supExtAut = " + strHoAutGLBHAS + " "
                                        + "where fe_dia = '" + strFecha + "' and co_tra = " + strCo_Tra;
                                stmLocCab.executeUpdate(strSql);
                            } else {
                                strSql = "insert into tbm_cabconasitra (co_tra,fe_dia,st_autHorSupExt,ho_supExtAut) values(" + strCo_Tra + ", '" + strFecha + "', "
                                        + "'S', " + strHoAutGLBHAS + " )";
                                System.out.println(strSql);
                                stmLocCab.executeUpdate(strSql);
                            }
                        }

                        objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
                        butAce.setEnabled(false);
                    } else {
                        int intRecFil = -1;
                        //if(objParSis.getCodigoUsuario()!=1){
                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            if (tblDat.getValueAt(i, INT_TBL_LIN).toString().compareTo("M") == 0) {
                                intRecFil = i;
                                i = tblDat.getRowCount();
                            }
                        }
                        if (intRecFil > -1) {
                            if (Integer.valueOf(tblDat.getValueAt(intRecFil, INT_TBL_NEJERAUT).toString()) < Integer.valueOf(tblDat.getValueAt(intFilMax, INT_TBL_NEJERAUT).toString())) {
                                strSql = "update tbm_cabSolHorSupExt set st_aut='A' where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc
                                        + " and co_doc=" + intCodDoc;
                                strParTot = "A";
                                stmLoc.executeUpdate(strSql);
                            } else {
                                //5to Update a tabal de cabecera de solicitud se setea estado 'T' de que la solicitud esta totalmete autorizada bostel
                                strSql = "update tbm_cabSolHorSupExt set st_aut='T' , ho_autdes=" + strHoAutGLBDES + ", ho_authas=" + strHoAutGLBHAS;
                                strSql += " , fe_aut=current_timestamp where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc
                                        + " and co_doc=" + intCodDoc;
                                strParTot = "T";
                                System.out.println(strSql);
                                stmLoc.executeUpdate(strSql);
                                //6to Se obtiene el codigo del empleado al cual se le autorizara las horas ext/sup bostel
                                strSql = "select co_tra from tbm_detsolhorsupext where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc
                                        + " and co_tipdoc=" + intCodTipDoc + " and co_doc=" + intCodDoc;//+" and co_reg="+tblDat.getValueAt(intFil, INT_TBL_COREG).toString();
                                resSet = stmLoc.executeQuery(strSql);
                                while (resSet.next()) {
                                    String strCo_Tra = resSet.getString("co_tra");
                                    // 7mo se obtiene la marcacion de ese empleado en cabconasitra bostel 
                                    strSql = "select * from tbm_cabconasitra where fe_dia = '" + strFecha + "' and co_tra = " + strCo_Tra;
                                    resSetCab = stmLocCab.executeQuery(strSql);
                                    //8vo se inserta en cabconasitra el set de autHorsupext en 'S' si y la hora solo hasta en ho_supExtAut bostel
                                    if (resSetCab.next()) {
                                        strSql = "update tbm_cabconasitra set st_autHorSupExt = 'S', ho_supExtAut = " + strHoAutGLBHAS + " "
                                                + "where fe_dia = '" + strFecha + "' and co_tra = " + strCo_Tra;
                                        stmLocCab.executeUpdate(strSql);
                                    } else {
                                        strSql = "insert into tbm_cabconasitra (co_tra,fe_dia,st_autHorSupExt,ho_supExtAut) values(" + strCo_Tra + ", '" + strFecha + "', "
                                                + "'S', " + strHoAutGLBHAS + " )";
                                        System.out.println(strSql);
                                        stmLocCab.executeUpdate(strSql);
                                    }
                                }
                                objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
                                //butCan.setEnabled(false);
                                butAce.setEnabled(false);
                            }
                        }
                    }
                }

                stmLoc.close();
                stmLoc = null;
                
                blnRes = true;
                String strHas = strHoAutGLBHAS.replace("'", "");
                if (strHas.length() == 5) {
                    strHas = strHas + ":" + "00";
                }
                String strDes = strHoAutGLBDES.replace("'", "");
                if (strDes.length() == 5) {
                    strDes = strDes + ":" + "00";
                }

                objZafRecHum30.tblDat.setValueAt(strHas, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_HORAUT);

                if (strParTot.compareTo("T") == 0) {
                    objZafRecHum30.tblDat.setValueAt(Boolean.TRUE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTTOT);
                    objZafRecHum30.tblDat.setValueAt(Boolean.FALSE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTPAR);
                    objZafRecHum30.tblDat.setValueAt(Boolean.FALSE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTPEN);
                } else if (strParTot.compareTo("A") == 0) {
                    objZafRecHum30.tblDat.setValueAt(Boolean.TRUE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTPAR);
                    objZafRecHum30.tblDat.setValueAt(Boolean.FALSE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTTOT);
                    objZafRecHum30.tblDat.setValueAt(Boolean.FALSE, objZafRecHum30.intPosAct, objZafRecHum30.INT_TBL_DAT_CHKAUTPEN);
                }
            }
        } catch (java.sql.SQLException Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    public static java.sql.Time SparseToTime(String hora) {
        int h, m, s;
        h = Integer.parseInt(hora.charAt(0) + "" + hora.charAt(1));
        m = Integer.parseInt(hora.charAt(3) + "" + hora.charAt(4));
        s = Integer.parseInt(hora.charAt(6) + "" + hora.charAt(7));
        return (new java.sql.Time(h, m, s));
    }

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        intOpcSelDlg = 0;
        dispose();
    }//GEN-LAST:event_butCanActionPerformed

    /**
     * Cerrar la aplicación.
     */
    private void exitForm() {
        intOpcSelDlg = 0;
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            //Inicializar objetos.
            objUti = new ZafUtil();
            strAux = "Listado de usuarios que pueden autorizar";
            this.setTitle(strAux + " v1.0");
            lblTit.setText(strAux);
            arlParDlg = new ArrayList();
            //Configurar los JTables.
            configurarTblDat();
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat() {
        boolean blnres = false;

        Vector vecCab = new Vector();    //Almacena las cabeceras
        vecCab.clear();

        vecCab.add(INT_TBL_LIN, "");
        vecCab.add(INT_TBL_CODUSR, "Cód.Usr.");
        vecCab.add(INT_TBL_USR, "Usuario");
        vecCab.add(INT_TBL_NOMUSR, "Nombre");
        vecCab.add(INT_TBL_CHKAUT, "Autorizar");
        vecCab.add(INT_TBL_CHKDEN, "Denegar");
        vecCab.add(INT_TBL_HORAUTDES, "Hor.Aut. Des.");
        vecCab.add(INT_TBL_HORAUTHAS, "Hor.Aut. Has.");
        vecCab.add(INT_TBL_OBS, "Observación");
        vecCab.add(INT_TBL_BUTOBS, "...");
        vecCab.add(INT_TBL_COREG, "Cód. Reg.");
        vecCab.add(INT_TBL_NEJERAUT, "Jer. Aut.");
        vecCab.add(INT_TBL_STOBLSEGJERAUT, "Obl. Jer.");

        objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        //Configurar ZafTblMod: Establecer las columnas obligatorias.
        java.util.ArrayList arlAux = new java.util.ArrayList();
        arlAux.add("" + INT_TBL_HORAUTDES);
        arlAux.add("" + INT_TBL_HORAUTHAS);
        objTblMod.setColumnasObligatorias(arlAux);
        arlAux = null;
        //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
        objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());

        //Configurar JTable: Establecer la fila de cabecera.
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LIN);

        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
        tblDat.getTableHeader().setReorderingAllowed(false);

        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_CODUSR).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_USR).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_NOMUSR).setPreferredWidth(200);
        tcmAux.getColumn(INT_TBL_CHKAUT).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_CHKDEN).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_HORAUTDES).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_HORAUTHAS).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_OBS).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_BUTOBS).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_NEJERAUT).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_STOBLSEGJERAUT).setPreferredWidth(50);

        //Configurar JTable: Ocultar columnas del sistema.
        objTblMod.addSystemHiddenColumn(INT_TBL_CODUSR, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_COREG, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_NEJERAUT, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_STOBLSEGJERAUT, tblDat);

        ArrayList arlColHid = new ArrayList();
        arlColHid.add("" + INT_TBL_CODUSR);

    //objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        //arlColHid=null;
        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        objMouMotAda = new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

        //Configurar JTable: Establecer columnas editables.
        Vector vecAux = new Vector();
        vecAux.add("" + INT_TBL_CHKAUT);
        vecAux.add("" + INT_TBL_CHKDEN);
        //vecAux.add("" + INT_TBL_HORAUTDES);
        //vecAux.add("" + INT_TBL_HORAUTHAS);
        vecAux.add("" + INT_TBL_BUTOBS);
        objTblMod.setColumnasEditables(vecAux);
        vecAux = null;

        if (objParSis.getCodigoUsuario() == 1) {

            objTblCelRenChkLab = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKAUT).setCellRenderer(objTblCelRenChkLab);
            objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKAUT).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    tblDat.setValueAt(new Boolean(true), intFilSel, INT_TBL_CHKAUT);
                    tblDat.setValueAt(new Boolean(false), intFilSel, INT_TBL_CHKDEN);
                }
            });

            objTblCelRenChkLab = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKDEN).setCellRenderer(objTblCelRenChkLab);
            objTblCelEdiChk2 = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKDEN).setCellEditor(objTblCelEdiChk2);
            objTblCelEdiChk2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    tblDat.setValueAt(new Boolean(false), intFilSel, INT_TBL_CHKAUT);
                    tblDat.setValueAt(new Boolean(true), intFilSel, INT_TBL_CHKDEN);
                }
            });

            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTOBS).setCellRenderer(zafTblDocCelRenBut);
            ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_BUTOBS, "Observación") {
                public void butCLick() {
                    int intSelFil = tblDat.getSelectedRow();
                    String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_OBS) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_OBS).toString());
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum30_02.this), true, strObs);
                    zafMae07_01.show();
                    if (zafMae07_01.getAceptar()) {
                        tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_OBS);
                    }
                }
            };

            objTblCelEdiTxt = new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_HORAUTHAS).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_HORAUTDES).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intPosRelColCodEmp;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    intFilSel = tblDat.getSelectedRow();
                    int intHH = 0;
                    int intMM = 0;
                    //DateFormat sdf = new SimpleDateFormat("hh:mm");
                    String strHorIng = null;
                    //Date date = null;
                    try {

                        switch (tblDat.getSelectedColumn()) {
                            case INT_TBL_HORAUTDES:
                                strHorIng = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_HORAUTDES));
                                intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0, 2));
                                intMM = Integer.parseInt(strHorIng.replace(":", "").substring(2, 4));
                                break;
                            case INT_TBL_HORAUTHAS:
                                strHorIng = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_HORAUTHAS));
                                intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0, 2));
                                intMM = Integer.parseInt(strHorIng.replace(":", "").substring(2, 4));
                                break;
                        }

                        if ((intHH >= 0 && intHH <= 24)) {
                            if (!(intMM >= 0 && intMM <= 59)) {
                                {
                                    String strTit = "Mensaje del sistema Zafiro";
                                    String strMen = "Hora ingresada contiene formato erroneo. Revisar e intentar nuevamente.";
                                    JOptionPane.showMessageDialog(ZafRecHum30_02.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                                    objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                                }
                            } else {

                                String strHorEnt = "";
                                java.sql.Time t = null;

                                switch (tblDat.getSelectedColumn()) {
                                    case INT_TBL_HORAUTDES:
                                        strHorEnt = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_HORAUTDES));
                                        if (strHorEnt.length() == 5) {
                                            strHorEnt = strHorEnt + ":00";
                                        }
                                        t = SparseToTime(strHorEnt);
                                        break;
                                    case INT_TBL_HORAUTHAS:
                                        strHorEnt = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_HORAUTHAS));
                                        if (strHorEnt.length() == 5) {
                                            strHorEnt = strHorEnt + ":00";
                                        }
                                        t = SparseToTime(strHorEnt);
                                        break;
                                }
                            }
                        } else {

                            String strTit = "Mensaje del sistema Zafiro";
                            String strMen = "Hora ingresada contiene formato erroneo. Revisar e intentar nuevamente.";
                            JOptionPane.showMessageDialog(ZafRecHum30_02.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                            objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                        }
                    } catch (Exception ex) {
                        String strTit = "Mensaje del sistema Zafiro";
                        String strMen = "Hora ingresada contiene formato erroneo. Revisar e intentar nuevamente.";
                        JOptionPane.showMessageDialog(ZafRecHum30_02.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                        objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                    }
                }
            });
        } else {

            objTblCelRenChkLab = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKAUT).setCellRenderer(objTblCelRenChkLab);
            objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKAUT).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intPosRelColCodEmp;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1) {
                        //Validar que exista el cliente para la empresa especificada.
                        Object objDen = objTblMod.getValueAt(intFilSel, INT_TBL_CHKDEN);
                        boolean blnDen = (Boolean) objDen;

                        Object obj = objTblMod.getValueAt(intFilSel, INT_TBL_CHKAUT);
                        boolean blnAut = (Boolean) obj;
                        String str = objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString();

                        /**
                         * ****************************************************************************************
                         */
                        if (objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString().compareTo("B") == 0) {
                            objTblCelEdiChk.setCancelarEdicion(true);
                        }
                    }

                    if (objParSis.getCodigoUsuario() == 1) {

                    } else {

                        boolean blnActUsr = true;
                        boolean bln = false;
                        Boolean blnsTOBLSEGJERAUT = (Boolean) tblDat.getValueAt(intFilSel, INT_TBL_STOBLSEGJERAUT);
                        if (blnsTOBLSEGJERAUT) {

                            int intNejerautSel = Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_NEJERAUT).toString());
                            if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString()) == objParSis.getCodigoUsuario()) {

                                if (intNejerautSel != 1) {
                                    for (int i = intFilSel; i <= 0; i--) {

                                        if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_NEJERAUT).toString()) < intNejerautSel) {
                                            bln = true;
                                            blnActUsr = false;
                                        }
                                    }
                                }

                            } else {
                                blnActUsr = false;
                            }

                            if (!blnActUsr) {
                                objTblCelEdiChk.setCancelarEdicion(true);
                            } else {
                                if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString()) != objParSis.getCodigoUsuario()) {
                                    if (!bln) {

                                        objTblCelEdiChk.setCancelarEdicion(true);
                                    }
                                }

                            }
                        } else {
                            if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString()) != objParSis.getCodigoUsuario()) {
                                objTblCelEdiChk.setCancelarEdicion(true);
                            }
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    tblDat.setValueAt(Boolean.TRUE, intFilSel, INT_TBL_CHKAUT);
                    tblDat.setValueAt(Boolean.FALSE, intFilSel, INT_TBL_CHKDEN);
                }

            });

            objTblCelRenChkLab = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKDEN).setCellRenderer(objTblCelRenChkLab);
            objTblCelEdiChk2 = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKDEN).setCellEditor(objTblCelEdiChk2);
            objTblCelEdiChk2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intPosRelColCodEmp;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1) {
                    //intPosRelColCodEmp=getPosRelColEspColSel(INT_TBL_DAT_CDI_COD_EMP);
                        //Validar que exista el cliente para la empresa especificada.
                        Object objAut = objTblMod.getValueAt(intFilSel, INT_TBL_CHKAUT);
                        boolean blnAut = (Boolean) objAut;

                        Object obj = objTblMod.getValueAt(intFilSel, INT_TBL_CHKDEN);
                        boolean blnDen = (Boolean) obj;
                        String str = objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString();
                        if (objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString().compareTo("B") == 0) {
                            objTblCelEdiChk2.setCancelarEdicion(true);
                        }
                    }

                    if (objParSis.getCodigoUsuario() == 1) {

                    } else {

                        boolean blnActUsr = true;
                        boolean bln = false;
                        Boolean blnsTOBLSEGJERAUT = (Boolean) tblDat.getValueAt(intFilSel, INT_TBL_STOBLSEGJERAUT);
                        if (blnsTOBLSEGJERAUT) {

                            int intNejerautSel = Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_NEJERAUT).toString());
                            if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString()) == objParSis.getCodigoUsuario()) {

                                if (intNejerautSel != 1) {
                                    for (int i = intFilSel; i <= 0; i--) {

                                        if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_NEJERAUT).toString()) < intNejerautSel) {
                                            bln = true;
                                            blnActUsr = false;
                                        }
                                    }
                                }

                            } else {
                                blnActUsr = false;
                            }

                            if (!blnActUsr) {
                                objTblCelEdiChk2.setCancelarEdicion(true);
                            } else {
                                if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString()) != objParSis.getCodigoUsuario()) {
                                    if (!bln) {

                                        objTblCelEdiChk2.setCancelarEdicion(true);
                                    }
                                }

                            }
                        } else {
                            if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString()) != objParSis.getCodigoUsuario()) {
                                objTblCelEdiChk2.setCancelarEdicion(true);
                            }
                        }

                        /*
                         boolean blnActUsr=true;
                         boolean bln=false;
                         Boolean blnsTOBLSEGJERAUT=(Boolean)tblDat.getValueAt(intFilSel, INT_TBL_STOBLSEGJERAUT);
                         if(blnsTOBLSEGJERAUT){
                        
                         int intNejerautSel=Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_NEJERAUT).toString());
                         if(Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString())==objParSis.getCodigoUsuario()){
                        
                         if(intNejerautSel!=1){
                         for(int i=intFilSel; i <= 0;i--){
                                    
                         if(Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_NEJERAUT).toString())<intNejerautSel){
                         bln=true;
                         blnActUsr=false;
                         }
                         }
                         }
                        
                         }else{
                         blnActUsr=false;
                         }
                        
                         if(!blnActUsr){
                         objTblCelEdiChk2.setCancelarEdicion(true);
                         }else{
                         if(Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString())!=objParSis.getCodigoUsuario()){
                         if(!bln){
                            
                         objTblCelEdiChk2.setCancelarEdicion(true);
                         }
                         }
                            
                         }
                         }else{
                         if(Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString())!=objParSis.getCodigoUsuario()){
                         objTblCelEdiChk2.setCancelarEdicion(true);
                         }
                         }*/
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblDat.getSelectedRow();
                    tblDat.setValueAt(Boolean.FALSE, intFil, INT_TBL_CHKAUT);
                    tblDat.setValueAt(Boolean.TRUE, intFil, INT_TBL_CHKDEN);
                }
            });

            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTOBS).setCellRenderer(zafTblDocCelRenBut);
            ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_BUTOBS, "Observación") {

                public void butCLick() {
                    int intSelFil = tblDat.getSelectedRow();
                    String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_OBS) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_OBS).toString());
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum30_02.this), true, strObs);

                    zafMae07_01.show();
                    if (zafMae07_01.getAceptar()) {

                        if (objParSis.getCodigoUsuario() == 1) {

                        }
                        if (objTblMod.getValueAt(intSelFil, INT_TBL_LIN).toString().compareTo("B") != 0) {
                            if (Integer.valueOf(tblDat.getValueAt(intSelFil, INT_TBL_CODUSR).toString()) == objParSis.getCodigoUsuario() || objParSis.getCodigoUsuario() == 1) {
                                tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_OBS);
                            }
                        }
                    }
                }
            };

            objTblCelEdiTxt = new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_HORAUTDES).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_HORAUTHAS).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intPosRelColCodEmp;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1) {
                        String str = objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString();
                    }

                    if (objParSis.getCodigoUsuario() == 1) {

                    } else {
                        if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString()) != objParSis.getCodigoUsuario()) {
                            objTblCelEdiTxt.setCancelarEdicion(true);
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    intFilSel = tblDat.getSelectedRow();
                    int intHH = 0;
                    int intMM = 0;
                    String strHorIng = null;
                    try {

                        switch (tblDat.getSelectedColumn()) {

                            case INT_TBL_HORAUTDES:
                                strHorIng = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_HORAUTDES));
                                intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0, 2));
                                intMM = Integer.parseInt(strHorIng.replace(":", "").substring(2, 4));
                                break;

                            case INT_TBL_HORAUTHAS:
                                strHorIng = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_HORAUTHAS));
                                intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0, 2));
                                intMM = Integer.parseInt(strHorIng.replace(":", "").substring(2, 4));
                                break;
                        }

                        if ((intHH >= 0 && intHH <= 24)) {
                            if (!(intMM >= 0 && intMM <= 59)) {
                                {
                                    String strTit = "Mensaje del sistema Zafiro";
                                    String strMen = "Hora ingresada contiene formato erroneo. Revisar e intentar nuevamente.";
                                    JOptionPane.showMessageDialog(ZafRecHum30_02.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                                    objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                                }
                            } else {

                                String strHorEnt = "";
                                java.sql.Time t = null;

                                switch (tblDat.getSelectedColumn()) {

                                    case INT_TBL_HORAUTDES:
                                        strHorEnt = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_HORAUTDES));
                                        if (strHorEnt.length() == 5) {
                                            strHorEnt = strHorEnt + ":00";
                                        }
                                        t = SparseToTime(strHorEnt);
                                        break;

                                    case INT_TBL_HORAUTHAS:
                                        strHorEnt = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_HORAUTHAS));
                                        if (strHorEnt.length() == 5) {
                                            strHorEnt = strHorEnt + ":00";
                                        }
                                        t = SparseToTime(strHorEnt);
                                        break;
                                }
                            }
                        } else {

                            String strTit = "Mensaje del sistema Zafiro";
                            String strMen = "Hora ingresada contiene formato erroneo. Revisar e intentar nuevamente.";
                            JOptionPane.showMessageDialog(ZafRecHum30_02.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                            objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                        }
                    } catch (Exception ex) {
                        String strTit = "Mensaje del sistema Zafiro";
                        String strMen = "Hora ingresada contiene formato erroneo. Revisar e intentar nuevamente.";
                        JOptionPane.showMessageDialog(ZafRecHum30_02.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                        objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                    }
                }
            });
        }

        objTblCelRenLbl = new ZafTblCelRenLbl();
        objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
        tcmAux.getColumn(INT_TBL_HORAUTDES).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_HORAUTHAS).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl = null;

        objTblCelRenChkLab = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_STOBLSEGJERAUT).setCellRenderer(objTblCelRenChkLab);
        ZafTblCelEdiChk objTblCelEdiChkST = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_STOBLSEGJERAUT).setCellEditor(objTblCelEdiChkST);

        //Configurar JTable: Editor de la tabla.
        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        return blnres;
    }

    public boolean cargarReg() 
    {
        java.sql.Connection con = null;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        boolean blnRes = true;
        String strSQL = "";
        try 
        {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();

                if (strEstDoc.compareTo("T") == 0) //Autorizados
                {
                    strSQL  = " SELECT DISTINCT a.* , b.co_usr,b.tx_usr,b.tx_nom , d.* \n";
                    strSQL += " FROM tbm_autSolHorSupExt a \n";
                    strSQL += " INNER JOIN tbm_cabSolHorSupExt c ON (c.co_emp=a.co_emp AND c.co_loc=a.co_loc AND c.co_tipdoc=a.co_tipdoc AND c.co_doc=a.co_doc) \n";
                    strSQL += " LEFT OUTER JOIN tbm_usr b on(b.co_usr=a.co_usraut) \n";
                    strSQL += " LEFT OUTER JOIN tbm_usrAutHorSupExtDep d ON (d.co_dep=c.co_dep AND d.co_usr=a.co_usraut AND tx_tiphoraut='S') \n";
                    strSQL += " WHERE a.co_emp = " + intCodEmp + "\n";
                    strSQL += " AND a.co_loc = " + intCodLoc + "\n";
                    strSQL += " AND a.co_tipdoc = " + intCodTipDoc + "\n";
                    strSQL += " AND a.co_doc = " + intCodDoc + "\n";
                    strSQL += " AND b.tx_usr IS NOT NULL";
                } 
                else  //Pendientes de Autorización
                {
                    
                    

                    strSQL  += "    select * from ( \n";
                    strSQL  += "            SELECT a.*,b.co_usr, b.tx_usr, b.tx_nom, d.st_aut, e.ho_autDes, e.ho_autHas, d.tx_obsAut \n";
                    strSQL  += "            FROM tbm_cabSolHorSupExt e \n";
                    strSQL  += "            inner join tbm_autSolHorSupExt d \n";
                    strSQL  += "            on (e.co_emp=d.co_Emp AND e.co_loc=d.co_loc AND e.co_tipDoc=d.co_tipDoc AND e.co_doc=d.co_doc ) \n";
                    strSQL  += "            inner join tbm_usr b on (d.co_usraut=b.co_usr) \n";
                    strSQL  += "            inner join tbm_usrAutHorSupExtDep as a on (b.co_usr=a.co_usr and a.co_dep=e.co_dep) \n";
                    strSQL  += "            WHERE e.co_Emp="+intCodEmp+" AND e.co_loc="+intCodLoc+" AND e.co_tipdoc="+intCodTipDoc+" AND e.co_Doc="+intCodDoc+" and d.st_aut='A' \n";
                    strSQL  += "            AND e.st_aut!='T' \n";
                    strSQL  += "            AND a.tx_tipHorAut='S' \n";

                    strSQL  += "            union \n";



                    strSQL  += "   SELECT a.*,b.co_usr as usua, b.tx_usr, b.tx_nom, d.st_aut, e.ho_autDes, e.ho_autHas, d.tx_obsAut \n";
                    strSQL  += "                                FROM tbm_cabSolHorSupExt e \n";
                    strSQL  += "            inner join tbm_autSolHorSupExt d \n";
                    strSQL  += "            on (e.co_emp=d.co_Emp AND e.co_loc=d.co_loc AND e.co_tipDoc=d.co_tipDoc AND e.co_doc=d.co_doc ) \n";
                    strSQL  += "            left outer join tbm_usrAutHorSupExtDep as a on (a.co_dep=e.co_dep \n";
                    strSQL  += "                                                            and a.co_usr not in  \n";
                    strSQL  += "                                                            (select co_usraut from tbm_autSolHorSupExt where co_Emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+" AND co_Doc="+intCodDoc+" and st_aut='A')) \n";
                    strSQL  += "            left outer join tbm_usr b on (a.co_usr=b.co_usr) \n";
                    strSQL  += "            WHERE e.co_Emp="+intCodEmp+" AND e.co_loc="+intCodLoc+" AND e.co_tipdoc="+intCodTipDoc+" AND e.co_Doc="+intCodDoc+" and d.st_aut='P' \n";
                    strSQL  += "            AND e.st_aut!='T' \n";
                    strSQL  += "    )a \n";
                    strSQL  += "    ORDER BY a.ne_jerAut, a.co_Reg  \n";
                            /*+ "
                    strSQL  = " SELECT DISTINCT a.*, b.co_usr, b.tx_usr, b.tx_nom, d.st_aut, e.ho_autDes, e.ho_autHas, d.tx_obsAut \n";
                    strSQL += " FROM tbm_usrAutHorSupExtDep  a\n";
                    strSQL += " INNER JOIN tbm_usr b ON (b.co_usr=a.co_usr) \n";
                    strSQL += " INNER JOIN tbm_dep c ON (c.co_dep=a.co_dep) \n";
                    strSQL += " LEFT OUTER JOIN  tbm_cabSolHorSupExt e ON (e.co_dep=c.co_Dep) \n";
                    strSQL += " LEFT OUTER JOIN tbm_autSolHorSupExt  d ON (e.co_emp=d.co_Emp AND e.co_loc=d.co_loc AND e.co_tipDoc=d.co_tipDoc AND e.co_doc=d.co_doc ) \n";
                    strSQL += " WHERE e.co_Emp=" + intCodEmp + " AND e.co_loc=" + intCodLoc + " AND e.co_tipdoc=" + intCodTipDoc + " AND e.co_Doc=" + intCodDoc+" \n";
                    strSQL += " AND e.co_dep in (select co_dep from tbm_cabSolHorSupExt where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + "  and co_tipdoc=" + intCodTipDoc + " and co_doc=" + intCodDoc + ")";
                    strSQL += " AND a.tx_tipHorAut='S' AND e.st_aut!='T' \n";
                    strSQL += " ORDER BY a.ne_jerAut, a.co_Reg ";*/
                }
                //Limpiar vector de datos.
                vecDat.clear();

                int intCantAut = 0;
                int intCantUsrAut = 0;
                System.out.println("cargarReg() Query - Listado Usuarios Autorizan HE: " + strSQL);
                rst = stm.executeQuery(strSQL);
                boolean blq = false;

                while (rst.next()) 
                {
                    vecReg = new Vector();
                    String strAut = rst.getString("st_aut");
                    vecReg.add(INT_TBL_LIN, "");
                    vecReg.add(INT_TBL_CODUSR, rst.getString("co_usr"));
                    intCantUsrAut++;
                    vecReg.add(INT_TBL_USR, rst.getString("tx_usr"));
                    System.out.println("aut: " + rst.getString("tx_usr"));
                    vecReg.add(INT_TBL_NOMUSR, rst.getString("tx_nom"));

                    if (strAut == null) 
                    {
                        vecReg.add(INT_TBL_CHKAUT, Boolean.FALSE);
                        vecReg.add(INT_TBL_CHKDEN, Boolean.FALSE);
                    } 
                    else 
                    {
                        if (strAut.compareTo("A") == 0) {
                            vecReg.add(INT_TBL_CHKAUT, Boolean.TRUE);
                            vecReg.add(INT_TBL_CHKDEN, Boolean.FALSE);
                            intCantAut++;
                            blq = true;
                        } else if (strAut.compareTo("D") == 0) {
                            vecReg.add(INT_TBL_CHKAUT, Boolean.FALSE);
                            vecReg.add(INT_TBL_CHKDEN, Boolean.TRUE);
                            blq = true;
                        } else {
                            vecReg.add(INT_TBL_CHKAUT, Boolean.FALSE);
                            vecReg.add(INT_TBL_CHKDEN, Boolean.FALSE);
                            blq = false;
                        }
                    }

                    vecReg.add(INT_TBL_HORAUTDES, objZafRecHum30.tblDat.getValueAt(objZafRecHum30.tblDat.getSelectedRow(), objZafRecHum30.INT_TBL_DAT_HORSOLDES).toString());
                    vecReg.add(INT_TBL_HORAUTHAS, objZafRecHum30.tblDat.getValueAt(objZafRecHum30.tblDat.getSelectedRow(), objZafRecHum30.INT_TBL_DAT_HORSOLHAS).toString());
                    vecReg.add(INT_TBL_OBS, rst.getString("tx_obsaut"));
                    vecReg.add(INT_TBL_BUTOBS, "...");
                    vecReg.add(INT_TBL_COREG, rst.getString("co_reg"));

                    //  if(strEstDoc.compareTo("T")==0){
                    //      vecReg.add(INT_TBL_NEJERAUT,rst.getString(""));
                    //      vecReg.add(INT_TBL_STOBLSEGJERAUT,Boolean.FALSE);
                    //  }else{
                    vecReg.add(INT_TBL_NEJERAUT, rst.getString("ne_jeraut"));

                    boolean bln;
                    String strSt_oblsegjeraut = rst.getString("st_oblsegjeraut");
                    if (strSt_oblsegjeraut == null) {
                        vecReg.add(INT_TBL_STOBLSEGJERAUT, Boolean.FALSE);
                    } else {
                        if (rst.getString("st_oblsegjeraut").compareTo("N") == 0) {
                            bln = Boolean.FALSE;
                        } else {
                            bln = Boolean.TRUE;
                        }
                        vecReg.add(INT_TBL_STOBLSEGJERAUT, bln);
                    }
                    //  }
                    vecDat.add(vecReg);
                }

                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);

                tblDat.setModel(objTblMod);

                if (objParSis.getCodigoUsuario() == 1) //Para el admin
                {
                    int intFilMax = objTblMod.getRowCount() - 1;
                    if (intCantUsrAut == intCantAut) 
                    {
                        objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
                        butAce.setEnabled(false);
                        //butCan.setEnabled(false);
                    } 
                    else if (intCantAut >= 1 && intCantAut < intCantUsrAut)
                    {
                        objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
                        butAce.setEnabled(true);
                        butCan.setEnabled(true);
                    } 
                    else
                    {
                        butAce.setEnabled(true);
                        butCan.setEnabled(true);
                    }
                }
                else
                {
                    boolean blnNoBlq = true; // Para Ing werner o Luigui
                    int intFilMax = objTblMod.getRowCount() - 1;
                    if (objTblMod.getValueAt(intFilMax, INT_TBL_CHKAUT).equals(true)) 
                    {
                        //Entra cuando la solicitud ya esta autorizada deshabilita el boton aceptar bostel
                        objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
                        butAce.setEnabled(false);
                        //butCan.setEnabled(false);
                    }
                    else 
                    {
                        ///busca el de mayor jerarquia que ha autorizado
                        int intPosFilMayJer = 0;
                        for (int i = 0; i < objTblMod.getRowCount(); i++) {
                            if (objTblMod.getValueAt(i, INT_TBL_CHKAUT).equals(false) && objTblMod.getValueAt(i, INT_TBL_CHKDEN).equals(false)) {
                                intPosFilMayJer = i;
                            }
                            if (intPosFilMayJer == 0) {
                                blnNoBlq = false;
                            }
                        }
                        if (blnNoBlq) {
                            for (int i = 0; i < intPosFilMayJer; i++) {
                                tblDat.setValueAt("B", i, INT_TBL_LIN);
                            }
                        } else {
                            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
                            butAce.setEnabled(true);
                            butCan.setEnabled(true);
                        }

                        //buscar si el mayor de la jerarquia ha denegado la solicitud
                        boolean blnDen = (Boolean) tblDat.getValueAt(tblDat.getRowCount() - 1, INT_TBL_CHKDEN);
                        if (blnDen) {
                            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
                            butAce.setEnabled(false);
                            butCan.setEnabled(true);
                        }
                    }
                }
                vecDat.clear();

            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            e.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     *
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg() 
    {
        boolean blnRes = true;
        try
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                strSQL = "";
                strSQL += "SELECT a1.co_aut, a1.tx_obs1, a1.tx_obs2";
                strSQL += " FROM tbm_cabAutCotVen AS a1";
                strSQL += " WHERE a1.co_emp=" + intCodEmp;
                strSQL += " AND a1.co_loc=" + intCodLoc;
                strSQL += " AND a1.co_cot=" + intCodDoc;
                strSQL += " AND a1.co_aut=(";
                strSQL += " SELECT MAX(b1.co_aut) AS co_aut";
                strSQL += " FROM tbm_cabAutCotVen AS b1";
                strSQL += " WHERE b1.co_emp=" + intCodEmp;
                strSQL += " AND b1.co_loc=" + intCodLoc;
                strSQL += " AND b1.co_cot=" + intCodDoc;
                strSQL += " )";
                rst = stm.executeQuery(strSQL);
                if (rst.next()) {
                    intCodAut = rst.getInt("co_aut");
                    strAux = rst.getString("tx_obs1");
                } else {
                    limpiarFrm();
                    blnRes = false;
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite insertar el histórico de las cotizaciones de venta.
     *
     * @return true: Si se pudo insertar el histórico.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarHisCotVen()
    {
        int intCodHis = 1;
        boolean blnRes = true;
        try 
        {
            if (con != null)
            {
                stm = con.createStatement();
                strSQL = "";
                strSQL += "SELECT MAX(co_his) AS co_his FROM tbh_cabCotVen";
                strSQL += " WHERE co_emp=" + intCodEmp;
                strSQL += " AND co_loc=" + intCodLoc;
                strSQL += " AND co_cot=" + intCodDoc;
                rst = stm.executeQuery(strSQL);
                if (rst.next()) {
                    intCodHis = rst.getInt(1) + 1;
                }
                rst.close();
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "INSERT INTO tbh_cabCotVen (co_emp, co_loc, co_cot, co_his, fe_cot, co_cli, co_ven, tx_ate, tx_numPed, co_forPag, nd_sub, nd_porIva, nd_valIva";
                strSQL += ", nd_valDes, nd_tot, ne_val, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod, tx_obsSolAut, tx_obsAutSol, st_aut, st_regRep, fe_his, co_usrHis)";
                strSQL += " SELECT a1.co_emp, a1.co_loc, a1.co_cot, " + intCodHis + " AS co_his, a1.fe_cot, a1.co_cli, a1.co_ven, a1.tx_ate, a1.tx_numPed, a1.co_forPag, a1.nd_sub, a1.nd_porIva, a1.nd_valIva";
                strSQL += ", a1.nd_valDes, a1.nd_tot, a1.ne_val, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultMod, a1.co_usrIng, a1.co_usrMod, a1.tx_obsSolAut, a1.tx_obsAutSol, a1.st_aut, a1.st_regRep";
                strSQL += ", " + objParSis.getFuncionFechaHoraBaseDatos() + " AS fe_his";
                strSQL += ", " + objParSis.getCodigoUsuario() + " AS co_usrHis";
                strSQL += " FROM tbm_cabCotVen AS a1";
                strSQL += " WHERE a1.co_emp=" + intCodEmp;
                strSQL += " AND a1.co_loc=" + intCodLoc;
                strSQL += " AND a1.co_cot=" + intCodDoc;
                stm.executeUpdate(strSQL);
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "INSERT INTO tbh_detCotVen (co_emp, co_loc, co_cot, co_his, co_reg, co_itm, tx_codAlt, tx_codAlt2, tx_nomItm, co_bod, nd_can, nd_preUni, nd_porDes";
                strSQL += ", st_ivaVen, nd_preCom, nd_porDesPreCom, co_prv, st_regRep, fe_his, co_usrHis)";
                strSQL += " SELECT a1.co_emp, a1.co_loc, a1.co_cot, " + intCodHis + " AS co_his, a1.co_reg, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.co_bod, a1.nd_can, a1.nd_preUni, a1.nd_porDes";
                strSQL += ", a1.st_ivaVen, a1.nd_preCom, a1.nd_porDesPreCom, a1.co_prv, a1.st_regRep";
                strSQL += ", " + objParSis.getFuncionFechaHoraBaseDatos() + " AS fe_his";
                strSQL += ", " + objParSis.getCodigoUsuario() + " AS co_usrHis";
                strSQL += " FROM tbm_detCotVen AS a1";
                strSQL += " WHERE a1.co_emp=" + intCodEmp;
                strSQL += " AND a1.co_loc=" + intCodLoc;
                strSQL += " AND a1.co_cot=" + intCodDoc;
                stm.executeUpdate(strSQL);
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "INSERT INTO tbh_pagCotVen (co_emp, co_loc, co_cot, co_his, co_reg, ne_diaCre, fe_ven, nd_porRet, mo_pag, ne_diaGra, co_tipRet, st_regRep, fe_his, co_usrHis)";
                strSQL += " SELECT a1.co_emp, a1.co_loc, a1.co_cot, " + intCodHis + " AS co_his, a1.co_reg, a1.ne_diaCre, a1.fe_ven, a1.nd_porRet, a1.mo_pag, a1.ne_diaGra, a1.co_tipRet, a1.st_regRep";
                strSQL += ", " + objParSis.getFuncionFechaHoraBaseDatos() + " AS fe_his";
                strSQL += ", " + objParSis.getCodigoUsuario() + " AS co_usrHis";
                strSQL += " FROM tbm_pagCotVen AS a1";
                strSQL += " WHERE a1.co_emp=" + intCodEmp;
                strSQL += " AND a1.co_loc=" + intCodLoc;
                strSQL += " AND a1.co_cot=" + intCodDoc;
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite limpiar el formulario.
     *
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm() 
    {
        boolean blnRes = true;
        try 
        {
            objTblMod.removeAllRows();
            //txaObs1.setText("");
            //txaObs2.setText("");
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) 
    {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si y No. El usuario es quien determina lo que debe hacer el
     * sistema seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) 
    {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se
     * grabaron y que debe comunicar de este particular al administrador del
     * sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Esta función establece los parámetros que debe utilizar el JDialog.
     *
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intCodTipDoc El código del tipo de documento.
     * @param intCodDoc El código de la cotización/documento.
     * @param intCodCli El código del cliente.
     * @param strIdeCli La identificación del cliente.
     */
    public void setParDlg(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodCli, String strIdeCli, String strEstDoc) 
    {
        this.intCodEmp = intCodEmp;
        this.intCodLoc = intCodLoc;
        this.intCodTipDoc = intCodTipDoc;
        this.intCodDoc = intCodDoc;
        this.intCodCli = intCodCli;
        this.strIdeCli = strIdeCli;
        this.strEstDoc = strEstDoc;
    }

    public void setParDlg(ZafParSis objZafParsis, ZafRecHum30 objRecHum30, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc, String strFecha, String strEstDoc) 
    {
        this.objParSis = objZafParsis;
        this.intCodEmp = intCodEmp;
        this.intCodLoc = intCodLoc;
        this.intCodTipDoc = intCodTipDoc;
        this.intCodDoc = intCodDoc;
        this.strFecha = strFecha;
        this.intNumDoc = intNumDoc;
        this.strEstDoc = strEstDoc;
        this.objZafRecHum30 = objRecHum30;
    }

    /**
     * Esta función obtiene la opción que seleccionó el usuario en el JDialog.
     * Puede devolver uno de los siguientes valores:
     * <UL>
     * <LI>0: Click en el botón Cancelar.
     * <LI>1: Click en el botón Aceptar.
     * </UL>
     * <BR>Nota.- La opción predeterminada es el botón Cancelar.
     *
     * @return La opción seleccionada por el usuario.
     */
    public int getOpcSelDlg() 
    {
        return intOpcSelDlg;
    }

    private boolean invocarClase(String clase)
    {
        boolean blnRes = true;
        try 
        {
            //Obtener el constructor de la clase que se va a invocar.
            Class objVen = Class.forName(clase);
            Class objCla[] = new Class[1];
            objCla[0] = arlParDlg.getClass();
            java.lang.reflect.Constructor objCon = objVen.getConstructor(objCla);
            //Inicializar el objeto que tiene los parámetros que se van pasar al JDialog.
            arlParDlg.clear();
            arlParDlg.add(javax.swing.JOptionPane.getFrameForComponent(this));
            arlParDlg.add(objParSis);
            arlParDlg.add("" + intCodEmp);
            arlParDlg.add("" + intCodLoc);
            arlParDlg.add(null);
            arlParDlg.add("" + intCodDoc);
            //Inicializar el constructor que se obtuvo.
            Object objObj[] = new Object[1];
            objObj[0] = arlParDlg;
            javax.swing.JDialog dlgVen;
            dlgVen = (javax.swing.JDialog) objCon.newInstance(objObj);
            dlgVen.setVisible(true);
        } catch (ClassNotFoundException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (NoSuchMethodException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (SecurityException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (InstantiationException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (IllegalAccessException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (IllegalArgumentException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (java.lang.reflect.InvocationTargetException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que está ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podría presentar mensajes informativos en un JLabel e ir incrementando un
     * JProgressBar con lo cual el usuario estaría informado en todo momento de
     * lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase ya
     * que si no sólo se apreciaría los cambios cuando ha terminado todo el
     * proceso.
     */
    private class ZafThreadGUI extends Thread 
    {

        private int intIndFun;

        public ZafThreadGUI()
        {
            intIndFun = 0;
        }

        public void run()
        {
            switch (intIndFun) 
            {
                case 0: //Guardar los cambios.

                    if (actualizarReg()) 
                    {
                        mostrarMsgInf("La operación AUTORIZAR/DENEGAR se realizó con éxito.");
                        intOpcSelDlg = 1;
                        dispose();
                    }
            }
            objThrGUI = null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la
         * clase Thread se pueden ejecutar diferentes funciones. Esta función
         * sirve para determinar la función que debe ejecutar el Thread.
         *
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice) {
            intIndFun = indice;
        }
    }

    /**
     * Esta función actualiza el registro en la base de datos.
     *
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg() 
    {
//Aqui Entra para actualizar el Registro y poner si esta denegada o autorizada bostel
        boolean blnRes = false;
        java.sql.Connection conn;
        try 
        {

            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) {
                conn.setAutoCommit(false);

                //blnRes=guardarDat(conn);
                if (guardarDat(conn)) {
                    conn.commit();
                    blnRes = true;
                } else {
                    conn.rollback();
                }

                conn.close();
                conn = null;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }

        return blnRes;
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_CODUSR:
                    strMsg = "Cód. Usr.";
                    break;
                case INT_TBL_NOMUSR:
                    strMsg = "Nombre del usuario";
                    break;
                case INT_TBL_CHKAUT:
                    strMsg = "Autoriza la solicitud";
                    break;
                case INT_TBL_HORAUTDES:
                    strMsg = "Hora autorizada desde";
                    break;
                case INT_TBL_HORAUTHAS:
                    strMsg = "Hora autorizada hasta";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
}

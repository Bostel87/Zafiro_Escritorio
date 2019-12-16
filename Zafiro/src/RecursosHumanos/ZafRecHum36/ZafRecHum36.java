/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RecursosHumanos.ZafRecHum36;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Maestros.ZafMae07.ZafMae07_01;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * Porcentajes de egreso a descontar en la primera quincena.
 * @author Roberto Flores
 * Guayaquil 02/08/2012
 */
public class ZafRecHum36 extends javax.swing.JInternalFrame
{
    private Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                              //Editor: Editor del JTable.
    private ZafUtil objUti;
    private ZafTblCelRenLbl objTblCelRenLblIng;  
    private ZafTblCelRenLbl objTblCelRenLbl2;  
    private ZafTblCelEdiTxt objTblCelEdiTxtIng;                               //Editor: JTextField en celda.
    private ZafThreadGUI objThrGUI;
    private ZafMouMotAda objMouMotAda;                                        //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                        //PopupMenu: Establecer PeopuMenú en JTable.
    private static final int INT_TBL_DAT_LINEA = 0;                           //Índice de columna Linea.
    private static final int INT_TBL_DAT_EST=1;
    private static final int INT_TBL_DAT_COD_EMP = 2;                         //Índice de columna Día.
    private static final int INT_TBL_DAT_NOM_EMP = 3;
    private static final int INT_TBL_DAT_COD_TRA = 4;
    private static final int INT_TBL_DAT_NOM_APE_TRA = 5;
    private static final int INT_TBL_DAT_OBS = 6;
    private static final int INT_TBL_DAT_BUTOBS = 7;
    private static final int INT_TBL_DAT_DESFIL = 8;
    private static final int INT_TBL_DAT_BUT_ASGING = 9;
    private static final int INT_TBL_DAT_BUT_ASGEGR = 10;
    private String strVersion="v1.09";
    private Vector vecDat, vecCab;
    private boolean blnMod;                                                  //Indica si han habido cambios en el documento
    private boolean blnConsDat;
    private ZafTblBus objTblBus;
    private String strCodEmp, strNomEmp;
    private String strCodDep = "";
    private String strDesLarDep = "";
    private String strCodTra = "";
    private String strNomTra = "";
    private ZafVenCon vcoEmp;                                               //Ventana de consulta.
    private ZafVenCon vcoDep;                                               //Ventana de consulta.
    private ZafVenCon vcoTra;
    private ZafPerUsr objPerUsr;
    ArrayList<String> lstPlaIng = null;
    ArrayList<String> lstPlaEgr = null;
    ArrayList<String> lstRub = null;
    final java.awt.Color colFonCol =new java.awt.Color(255,221,187);
  
    /** Creates new form ZafRecHum36 */
    public ZafRecHum36(Librerias.ZafParSis.ZafParSis obj)
    {
        try
        {
            this.objZafParSis=(Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();
            /*INSERTAR LOS AÑOS EXISTENTES EN TBM_INGEGRMENTRA*/
            cargarAños();
            this.setTitle(objZafParSis.getNombreMenu() + "  " + strVersion);
            lblTit.setText(objZafParSis.getNombreMenu());
            objUti=new ZafUtil();
            this.objZafParSis = (ZafParSis) objZafParSis.clone();
            objPerUsr=new ZafPerUsr(objZafParSis);
            butCon.setVisible(false);
            butGua.setVisible(false);
            butCer.setVisible(false);
            if (objPerUsr.isOpcionEnabled(3135))
            {
                butCon.setVisible(true);
            }
            if (objPerUsr.isOpcionEnabled(3136))
            {
                butGua.setVisible(true);
            }
            if (objPerUsr.isOpcionEnabled(3137))
            {
                butCer.setVisible(true);
            }
            configuraTbl();
            tblDat.getModel().addTableModelListener(new MyTableModelListener(tblDat));
            butCon.setVisible(false);
            butGua.setVisible(false);
            butCer.setVisible(false);
            if (objPerUsr.isOpcionEnabled(3135))
            {
                butCon.setVisible(true);
            }
            if (objPerUsr.isOpcionEnabled(3136))
            {
                butGua.setVisible(true);
            }
            if (objPerUsr.isOpcionEnabled(3137))
            {
                butCer.setVisible(true);
            }
            //Configurar las ZafVenCon.
            configurarVenConDep();
            configurarVenConTra();
            configurarVenConEmp();
            blnMod=false;
            blnConsDat=false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private boolean cargarAños()
    {
        boolean blnRes=true;
        java.sql.Connection con=null; 
        java.sql.Statement stmLoc=null;
        java.sql.ResultSet rstLoc=null; 
        String strSQL="";
        try
        {
            con=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stmLoc=con.createStatement();
                strSQL="select distinct ne_ani from tbm_ingEgrMenTra order by ne_ani desc";
                rstLoc=stmLoc.executeQuery(strSQL);
                while (rstLoc.next())
                {
                    cboPerAAAA.addItem(rstLoc.getString("ne_ani"));
                }
            }
        }
        catch (java.sql.SQLException Evt)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        finally
        {
            try
            {
                if (rstLoc!=null) {
                    rstLoc.close();    
                }
                if (stmLoc!=null) {
                    stmLoc.close();
                }
                if (con!=null) {
                    con.close();
                }
            }
            catch (Exception Evt)
            {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
        }
        return blnRes;
    }

    public class MyTableModelListener implements TableModelListener
    {
        JTable table;
        MyTableModelListener(JTable table)
        {
            this.table = table;
        }

        public void tableChanged(TableModelEvent e)
        {
            if (!blnConsDat)
            {
                switch (e.getType())
                {
                    case TableModelEvent.UPDATE:
                        blnMod=true;
                        break;
                }
            }
            blnConsDat=false;
        }
    }
    
    private boolean configuraTbl()
    {
        boolean blnRes=false;
        String strSQL="";
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector();
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LINEA,"");
            vecCab.add(INT_TBL_DAT_EST,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód. Emp.");
            vecCab.add(INT_TBL_DAT_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_DAT_COD_TRA,"Código");
            vecCab.add(INT_TBL_DAT_NOM_APE_TRA,"Empleado");
            vecCab.add(INT_TBL_DAT_OBS,"Observación");
            vecCab.add(INT_TBL_DAT_BUTOBS,"");
            vecCab.add(INT_TBL_DAT_DESFIL,"");
            vecCab.add(INT_TBL_DAT_BUT_ASGING,"Ingresos");
            vecCab.add(INT_TBL_DAT_BUT_ASGEGR,"Egresos");
            java.sql.Connection conIns = null;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            
                try{
                    conIns =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

                        if(conIns!=null){
                            strSQL="";
                            //strSQL="select distinct co_pla,tx_descor from tbm_cabPlaRolPag where st_reg like 'A' order by co_pla ";
                            strSQL="select * from tbm_rubrolpag where co_rub in (select distinct co_rub from tbm_ingEgrMenTra) and tx_tiprub like 'I' order by co_rub asc";
                            stmLoc=conIns.createStatement();
                            rstLoc=stmLoc.executeQuery(strSQL);
                                if(rstLoc.next()){
                                    lstPlaIng=new ArrayList<String>();
                                    lstRub=new ArrayList<String>();
                                    do{
                                        vecCab.add(vecCab.size(),rstLoc.getString("tx_nom"));
                                        lstPlaIng.add(rstLoc.getString("co_rub"));
                                        lstRub.add(rstLoc.getString("co_rub"));
                                    }while(rstLoc.next());
                                }
                        }
                }catch(SQLException ex)
                {
                    blnRes=false;
                    objUti.mostrarMsgErr_F1(this, ex);
                }finally {
                    try{stmLoc.close();}catch(Throwable ignore){}
                    try{rstLoc.close();}catch(Throwable ignore){}
                    try{conIns.close();}catch(Throwable ignore){}
            }
                
                try{
                    conIns =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

                        if(conIns!=null){
                            strSQL="";
                            //strSQL="select distinct co_pla,tx_descor from tbm_cabPlaRolPag where st_reg like 'A' order by co_pla ";
                            strSQL="select * from tbm_rubrolpag where co_rub in (select distinct co_rub from tbm_ingEgrMenTra) and tx_tiprub like 'E' order by co_rub asc";
                            stmLoc=conIns.createStatement();
                            rstLoc=stmLoc.executeQuery(strSQL);
                                if(rstLoc.next()){
                                    lstPlaEgr=new ArrayList<String>();
                                    do{
                                        vecCab.add(vecCab.size(),rstLoc.getString("tx_nom"));
                                        lstPlaEgr.add(rstLoc.getString("co_rub"));
                                        lstRub.add(rstLoc.getString("co_rub"));
                                    }while(rstLoc.next());
                                }
                        }
                }catch(SQLException ex)
                {
                    blnRes=false;
                    objUti.mostrarMsgErr_F1(this, ex);
                }finally {
                    try{stmLoc.close();}catch(Throwable ignore){}
                    try{rstLoc.close();}catch(Throwable ignore){}
                    try{conIns.close();}catch(Throwable ignore){}
            }
            
            vecCab.add(vecCab.size(),"Ingresos");
            vecCab.add(vecCab.size(),"Egresos");
            vecCab.add(vecCab.size(),"Diferencia");
                
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_DAT_LINEA);

            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LINEA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_EST).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EMP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_TRA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_APE_TRA).setPreferredWidth(270);
            tcmAux.getColumn(INT_TBL_DAT_OBS).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BUTOBS).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DESFIL).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ASGING).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ASGEGR).setPreferredWidth(100);
            //tcmAux.getColumn(INT_TBL_DAT_BUT_ASG+lstPlaIng.size()+1).setPreferredWidth(100);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST, tblDat);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUTOBS);
            vecAux.add("" + INT_TBL_DAT_BUT_ASGING);
            vecAux.add("" + INT_TBL_DAT_BUT_ASGEGR);
            final int intP=INT_TBL_DAT_BUT_ASGEGR+1;
                //int intContRecArrLst = 0;
            for (int i=0; i<1; i++)
            {
                for(int x=intP;x<tblDat.getColumnCount();x++){
                    int intPosColEdi=x+i;
                    vecAux.add("" + intPosColEdi);
                    //intContRecArrLst++;
                }
            }
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            
             Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();zafTblDocCelRenBut = new ZafTblCelRenBut();
                tcmAux.getColumn(INT_TBL_DAT_BUTOBS).setCellRenderer(zafTblDocCelRenBut);

               ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_DAT_BUTOBS, "Observación") {
                   int intFilSel;
                   public void butCLick() {
                       int intSelFil = tblDat.getSelectedRow();
                       if(objTblMod.getValueAt(intSelFil, INT_TBL_DAT_DESFIL).toString().equals("")){
                           String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_DAT_OBS) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_DAT_OBS).toString());
                        ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum36.this), true, strObs);
                        zafMae07_01.show();
                        if (zafMae07_01.getAceptar()) {
                           tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_DAT_OBS);
                        }
                       }
                  }
              };
            
            
            zafTblDocCelRenBut = new ZafTblCelRenBut();zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn( INT_TBL_DAT_BUT_ASGING).setCellRenderer(zafTblDocCelRenBut);
           
            
            zafTblDocCelRenBut = new ZafTblCelRenBut();zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn( INT_TBL_DAT_BUT_ASGEGR).setCellRenderer(zafTblDocCelRenBut);

            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblIng=new ZafTblCelRenLbl();
            objTblCelRenLblIng.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblIng.setTipoFormato(objTblCelRenLblIng.INT_FOR_NUM);
            objTblCelRenLblIng.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
            
            objTblCelEdiTxtIng=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);

            int intColDesIng=intP;
            int intColHasIng=intP+lstPlaIng.size()-1;
            
            final int intColDesEgr=intP+lstPlaIng.size();
            final int intColHasEgr=intP+(lstPlaIng.size()-1)+(lstPlaEgr.size()-1);
            
                for(int x=intP;x<tblDat.getColumnCount();x++){
                    
                    objTblMod.setColumnDataType(x, ZafTblMod.INT_COL_DBL, null, null);
                    tcmAux.getColumn(x).setCellEditor(objTblCelEdiTxtIng);
                    tcmAux.getColumn(x).setCellRenderer(objTblCelRenLblIng);
                    
                    objTblCelEdiTxtIng.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                        int intFilSel;
                        int intColSel;
                        double dblNdValRub;
                        double dblNdValPor;
                        double dblNdVal;
                        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                            
                            intFilSel=tblDat.getSelectedRow();
                            intColSel=tblDat.getSelectedColumn();
                            
                            String str = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_DESFIL).toString();
                            if(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_DESFIL).toString().equals("")){
                                //objTblMod.setValueAt(objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()), intFilSel-1, intColSel);
                                objTblCelEdiTxtIng.setCancelarEdicion(true);
                            }
                            
                            if(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_DESFIL).toString().equals("Primera quincena")){
                                //objTblMod.setValueAt(objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()), intFilSel-1, intColSel);
                                objTblCelEdiTxtIng.setCancelarEdicion(true);
                            }
                            
                            if(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_DESFIL).toString().equals("Porcentaje")){
                                //objTblMod.setValueAt(objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()), intFilSel-1, intColSel);
                                dblNdValRub=objUti.redondear(objUti.parseDouble(objTblMod.getValueAt(intFilSel-1, intColSel)), objZafParSis.getDecimalesMostrar());
                                if(dblNdValRub==0){
                                    objTblCelEdiTxtIng.setCancelarEdicion(true);
                                }else{
                                    objTblCelEdiTxtIng.setCancelarEdicion(false);
                                }
                            }
                            
                            if(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_DESFIL).toString().equals("Valor")){
                                //objTblMod.setValueAt(objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()), intFilSel-1, intColSel);
                                dblNdValRub=objUti.redondear(objUti.parseDouble(objTblMod.getValueAt(intFilSel-2, intColSel)), objZafParSis.getDecimalesMostrar());
                                if(dblNdValRub==0){
                                    objTblCelEdiTxtIng.setCancelarEdicion(true);
                                }else{
                                    objTblCelEdiTxtIng.setCancelarEdicion(false);
                                }
                            }
                        }

                        /*******************/
                        
                        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                            intFilSel=tblDat.getSelectedRow();
                            intColSel=tblDat.getSelectedColumn();
                            dblNdValRub = 0;
                            dblNdValPor = 0;
                            dblNdVal = 0;
                            
                            for(int intCol=INT_TBL_DAT_BUT_ASGEGR+1; intCol<(INT_TBL_DAT_BUT_ASGEGR+1)+(lstPlaIng.size()-1)+(lstPlaEgr.size()-1);intCol++){
                                
                            
                                if(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_DESFIL).toString().equals("Valor")){
                                    dblNdValRub=objUti.redondear(objUti.parseDouble(objTblMod.getValueAt(intFilSel-2, intCol)), objZafParSis.getDecimalesMostrar());
                                    dblNdValPor=objUti.redondear(objUti.parseDouble(objTblMod.getValueAt(intFilSel-1, intCol)), objZafParSis.getDecimalesMostrar());
                                    dblNdVal=objUti.redondear(objUti.parseDouble(objTblMod.getValueAt(intFilSel, intCol)), objZafParSis.getDecimalesMostrar());
                                }else if(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_DESFIL).toString().equals("Porcentaje")){
                                    dblNdValRub=objUti.redondear(objUti.parseDouble(objTblMod.getValueAt(intFilSel-1, intCol)), objZafParSis.getDecimalesMostrar());
                                    dblNdValPor=objUti.redondear(objUti.parseDouble(objTblMod.getValueAt(intFilSel, intCol)), objZafParSis.getDecimalesMostrar());
                                    dblNdVal=objUti.redondear(objUti.parseDouble(objTblMod.getValueAt(intFilSel+1, intCol)), objZafParSis.getDecimalesMostrar());
                                }

    //                            String str = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_DESFIL).toString();


                                if(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_DESFIL).toString().equals("Porcentaje")){
                                    if(dblNdValPor<0){
                                        objTblMod.setValueAt(objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()), intFilSel+2, intColSel);
                                        objTblMod.setValueAt(objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()), intFilSel, intColSel);
                                    }else{
                                        //objTblCelEdiTxt.setCancelarEdicion(false);
                                        if(objUti.redondear(objUti.parseDouble(objTblMod.getValueAt(intFilSel, intCol)), objZafParSis.getDecimalesMostrar())>100){
                                            objTblMod.setValueAt(objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()), intFilSel, intColSel);
                                            objTblMod.setValueAt(objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()), intFilSel+2, intColSel+2);
                                        }else{
                                            objTblMod.setValueAt(objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()), intFilSel+1, intColSel);
                                        }
                                        objTblMod.setValueAt(objUti.redondear(objUti.parseDouble((dblNdValRub*(dblNdValPor/100))+dblNdVal), objZafParSis.getDecimalesMostrar()), intFilSel+2, intCol);
                                        calcularTotalEachRow(intFilSel+2);
                                    }
                                        
                                }
                                else if(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_DESFIL).toString().equals("Valor")){
                                    //objTblCelEdiTxt.setCancelarEdicion(false); 
                                    if(dblNdValRub>0){
                                        
                                        if(dblNdVal>dblNdValRub){
                                            objTblMod.setValueAt(objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()), intFilSel, intCol);
                                        }else if(dblNdVal<0){
                                            objTblMod.setValueAt(objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()), intFilSel, intColSel);
                                        }else if(dblNdValRub>dblNdVal){
                                            objTblMod.setValueAt(objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()), intFilSel-1, intColSel);
                                        }
//                                        else{
//                                            if(intCol==intColSel){
//                                                objTblMod.setValueAt(objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()), intFilSel-1, intCol);
//                                            }
                                            

                                        objTblMod.setValueAt(objUti.redondear(objUti.parseDouble((dblNdValRub*(dblNdValPor/100))+dblNdVal), objZafParSis.getDecimalesMostrar()), intFilSel+1, intCol);

                                    }else{
                                        if(dblNdVal>0){
                                            objTblMod.setValueAt(objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()), intFilSel, intColSel);
                                        }else if(dblNdVal<0&&dblNdVal>=dblNdValRub){
                                            if(intColSel>intColDesEgr && intColSel<intColHasEgr){
                                                objTblMod.setValueAt(objUti.redondear(objUti.parseDouble((dblNdValRub*(dblNdValPor/100))+dblNdVal), objZafParSis.getDecimalesMostrar()), intFilSel+1, intCol);
                                            }
                                            
                                        }
                                    }
                                    calcularTotalEachRow(intFilSel+1);
                                }
                            }
                        }
                    });
                    
                    objTblCelRenLblIng.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                    public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                            //Mostrar de color gris las columnas impares.

                            int intCell=objTblCelRenLblIng.getRowRender();
                            String str=tblDat.getValueAt(intCell, INT_TBL_DAT_EST).toString();
                            
                            if(str.equals("S")){
                                objTblCelRenLblIng.setBackground(colFonCol);
                                objTblCelRenLblIng.setForeground(java.awt.Color.BLACK);
                            }else {
                                objTblCelRenLblIng.setBackground(java.awt.Color.WHITE);
                                objTblCelRenLblIng.setForeground(java.awt.Color.BLACK);
                            }
                        }
                        public void afterRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                            //Mostrar de color gris las columnas impares.

                            int intCell=objTblCelRenLblIng.getRowRender();
                            String str=tblDat.getValueAt(intCell, INT_TBL_DAT_EST).toString();

                            if(str.equals("S")){
                                objTblCelRenLblIng.setBackground(colFonCol);
                                objTblCelRenLblIng.setForeground(java.awt.Color.BLACK);
                            }else {
                                objTblCelRenLblIng.setBackground(java.awt.Color.WHITE);
                                objTblCelRenLblIng.setForeground(java.awt.Color.BLACK);
                            }
                        }
                    });
                }
                    
                
                objTblCelRenLbl2=new ZafTblCelRenLbl();
                tcmAux.getColumn(INT_TBL_DAT_EST).setCellRenderer(objTblCelRenLbl2);
                tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setCellRenderer(objTblCelRenLbl2);
                tcmAux.getColumn(INT_TBL_DAT_NOM_EMP).setCellRenderer(objTblCelRenLbl2);
                tcmAux.getColumn(INT_TBL_DAT_COD_TRA).setCellRenderer(objTblCelRenLbl2);
                tcmAux.getColumn(INT_TBL_DAT_NOM_APE_TRA).setCellRenderer(objTblCelRenLbl2);
                tcmAux.getColumn(INT_TBL_DAT_OBS).setCellRenderer(objTblCelRenLbl2);
                tcmAux.getColumn(INT_TBL_DAT_DESFIL).setCellRenderer(objTblCelRenLbl2);
                
                objTblCelRenLbl2.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                    public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                            //Mostrar de color gris las columnas impares.

                            int intCell=objTblCelRenLbl2.getRowRender();

                            //                intCantRubAct
                            String str=tblDat.getValueAt(intCell, INT_TBL_DAT_EST).toString();
                            //
                            if(str.equals("S")){
                                objTblCelRenLbl2.setBackground(colFonCol);
                                objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                            }else {
                                objTblCelRenLbl2.setBackground(java.awt.Color.WHITE);
                                objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                            }
                        }
                        public void afterRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                            //Mostrar de color gris las columnas impares.

                            int intCell=objTblCelRenLbl2.getRowRender();

                            //                intCantRubAct
                            String str=tblDat.getValueAt(intCell, INT_TBL_DAT_EST).toString();
//
                            if(str.equals("S")){
                                objTblCelRenLbl2.setBackground(colFonCol);
                                objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                            }else {
                                objTblCelRenLbl2.setBackground(java.awt.Color.WHITE);
                                objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                            }
                        }
                    });
                
               
            

            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);

            //Configurar JTable: Establecer el ordenamiento en la tabla.
            //objTblOrd=new ZafTblOrd(tblDat);

            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            blnRes=true;
                
        }catch(Exception e) {  e.printStackTrace(); blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
           
        return blnRes;       
    }
    
    private boolean calcularTotalEachRow(int intFilSel)
    {
        boolean blnRes=true;
        try
        {
            int intColPos=INT_TBL_DAT_BUT_ASGEGR+1;
            double dblIng = 0, dblEgr = 0;
            for (int i=0; i<1; i++)
            {
                for (int x=intColPos;x<(intColPos+lstPlaIng.size()-1);x++)
                {
                    int intPosColSum=x+i;
                    double dblValCol=0;
                    if (!tblDat.getValueAt(intFilSel, intPosColSum).toString().equals("") && tblDat.getValueAt(intFilSel, intPosColSum).toString()!=null)
                    {
                        dblValCol= objUti.parseDouble(tblDat.getValueAt(intFilSel, intPosColSum).toString());
                    }
                    dblIng=dblIng+dblValCol;
                }

                for (int x=intColPos+(lstPlaIng.size()-1);x<(intColPos+(lstPlaIng.size()-1)+(lstPlaEgr.size()-1));x++)
                {
                    int intPosColSum=x+i;
                    double dblValCol=0;
                    if (!tblDat.getValueAt(intFilSel, intPosColSum).toString().equals("") && tblDat.getValueAt(intFilSel, intPosColSum).toString()!=null)
                    {
                        dblValCol= objUti.parseDouble(tblDat.getValueAt(intFilSel, intPosColSum).toString());
                    }
                    dblEgr=dblEgr+dblValCol;
                }
            }
            objTblMod.setValueAt(dblIng, intFilSel, objTblMod.getColumnCount()-3);
            objTblMod.setValueAt(dblEgr, intFilSel, objTblMod.getColumnCount()-2);
            objTblMod.setValueAt(dblIng+dblEgr, intFilSel, objTblMod.getColumnCount()-1);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public void setEditable(boolean editable)
    {
        if (editable==true)
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }
        else
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }
  
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtCodDep = new javax.swing.JTextField();
        txtNomDep = new javax.swing.JTextField();
        butDep = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        cboPerAAAA = new javax.swing.JComboBox();
        cboPerMes = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenCab = new javax.swing.JPanel();
        lblNumPag = new javax.swing.JLabel();
        spiNumPag = new javax.swing.JSpinner();
        lblNumPag1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
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

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("titulo"); // NOI18N
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        buttonGroup1.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los empleados");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 24, 400, 20);

        buttonGroup1.add(optFil);
        optFil.setText("Sólo los empleados que cumplan con el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(4, 44, 400, 20);

        jLabel4.setText("Empresa:"); // NOI18N
        panFil.add(jLabel4);
        jLabel4.setBounds(24, 64, 120, 20);

        txtCodEmp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(144, 64, 56, 20);

        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(200, 64, 460, 20);

        butEmp.setText(".."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(660, 64, 20, 20);

        jLabel5.setText("Departamento:"); // NOI18N
        panFil.add(jLabel5);
        jLabel5.setBounds(24, 84, 120, 20);

        txtCodDep.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDepActionPerformed(evt);
            }
        });
        txtCodDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDepFocusLost(evt);
            }
        });
        panFil.add(txtCodDep);
        txtCodDep.setBounds(144, 84, 56, 20);

        txtNomDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDepActionPerformed(evt);
            }
        });
        txtNomDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDepFocusLost(evt);
            }
        });
        panFil.add(txtNomDep);
        txtNomDep.setBounds(200, 84, 460, 20);

        butDep.setText(".."); // NOI18N
        butDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDepActionPerformed(evt);
            }
        });
        panFil.add(butDep);
        butDep.setBounds(660, 84, 20, 20);

        jLabel6.setText("Empleado:"); // NOI18N
        panFil.add(jLabel6);
        jLabel6.setBounds(24, 104, 120, 20);

        txtCodTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTraActionPerformed(evt);
            }
        });
        txtCodTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTraFocusLost(evt);
            }
        });
        panFil.add(txtCodTra);
        txtCodTra.setBounds(144, 104, 56, 20);

        txtNomTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraActionPerformed(evt);
            }
        });
        txtNomTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTraFocusLost(evt);
            }
        });
        panFil.add(txtNomTra);
        txtNomTra.setBounds(200, 104, 460, 20);

        butTra.setText(".."); // NOI18N
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(660, 104, 20, 20);

        jLabel7.setText("Período:"); // NOI18N
        panFil.add(jLabel7);
        jLabel7.setBounds(4, 4, 120, 20);

        cboPerAAAA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Año" }));
        cboPerAAAA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPerAAAAActionPerformed(evt);
            }
        });
        panFil.add(cboPerAAAA);
        cboPerAAAA.setBounds(144, 4, 80, 20);

        cboPerMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        panFil.add(cboPerMes);
        cboPerMes.setBounds(226, 4, 120, 20);

        tabGen.addTab("Filtro", null, panFil, "Filtro");

        jPanel1.setLayout(new java.awt.BorderLayout());

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
        tblDat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblDatFocusGained(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        jPanel1.add(spnDat, java.awt.BorderLayout.CENTER);

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 30));
        panGenCab.setLayout(null);

        lblNumPag.setText("%");
        lblNumPag.setToolTipText("Número de pagos");
        panGenCab.add(lblNumPag);
        lblNumPag.setBounds(240, 8, 20, 20);

        spiNumPag.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spiNumPagStateChanged(evt);
            }
        });
        panGenCab.add(spiNumPag);
        spiNumPag.setBounds(130, 8, 100, 20);

        lblNumPag1.setText("Porcentaje a asignar:");
        lblNumPag1.setToolTipText("Número de pagos");
        panGenCab.add(lblNumPag1);
        lblNumPag1.setBounds(0, 8, 130, 20);

        jPanel1.add(panGenCab, java.awt.BorderLayout.NORTH);

        tabGen.addTab("Reporte", jPanel1);

        getContentPane().add(tabGen, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        jPanel5.add(butCon);

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        jPanel5.add(butGua);

        butCer.setText("Cerrar");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        jPanel5.add(butCer);

        jPanel3.add(jPanel5, java.awt.BorderLayout.EAST);

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

        jPanel3.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        strCodEmp = txtCodEmp.getText();
        txtCodEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp))
        {
            if (txtCodEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            }
            else
            {
                BuscarEmp("a1.co_emp", txtCodEmp.getText(), 0);
            }
        }
        else
        {
            txtCodEmp.setText(strCodEmp);
        }
}//GEN-LAST:event_txtCodEmpFocusLost

    public void BuscarEmp(String campo,String strBusqueda,int tipo)
    {
        vcoEmp.setTitle("Listado de Empresas");
        if (vcoEmp.buscar(campo, strBusqueda ))
        {
            txtCodEmp.setText(vcoEmp.getValueAt(1));
            txtNomEmp.setText(vcoEmp.getValueAt(2));
        }
        else
        {
            vcoEmp.setCampoBusqueda(tipo);
            vcoEmp.cargarDatos();
            vcoEmp.show();
            if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
            {
                txtCodEmp.setText(vcoEmp.getValueAt(1));
                txtNomEmp.setText(vcoEmp.getValueAt(2));
            }
            else
            {
                txtCodEmp.setText(strCodEmp);
                txtNomEmp.setText(strNomEmp);
            }
        }
    }
    
    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        txtNomEmp.transferFocus();
        
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);

}//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        if (txtNomEmp.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp))
            {
                if (txtNomEmp.getText().equals(""))
                {
                    txtCodEmp.setText("");
                    txtNomEmp.setText("");
                }
                else
                {
                    mostrarVenConEmp(2);
                }
            }
            else
                txtNomEmp.setText(strNomEmp);
        }
}//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        strCodEmp=txtCodEmp.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        java.sql.Connection conn=null;
        try
        {
            conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conn!=null)
            {
                conn.setAutoCommit(false);
                if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
                {
                    if (guardarDat(conn))
                    {
                        conn.commit();
                        mostrarMsgInf("La operación GUARDAR se realizó con éxito");
                    }
                    else
                    {
                        conn.rollback();
                    }
                }
            }
        }
        catch(java.sql.SQLException Evt)
        {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch(Exception Evt)
        {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(Throwable ignore)
            {

            }
        }
    }//GEN-LAST:event_butGuaActionPerformed

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
    
    private boolean guardarDat(java.sql.Connection con)
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSQL="";
        try
        {
            if (validaPasoVerificacionFaltas(cboPerAAAA.getSelectedItem().toString(), cboPerMes.getSelectedIndex(), objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), 1)) {
            if(con!=null)
            {
                stmLoc=con.createStatement();
                if (objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo())
                {
                    strSQL+="select * from tbm_cabrolpag " + "\n";
                    strSQL+="where ne_ani= " + cboPerAAAA.getSelectedItem().toString() +"\n";
                    strSQL+="and ne_mes= " + cboPerMes.getSelectedIndex() + "\n";
                    strSQL+="and ne_per=1 " + "\n";
                    strSQL+="and co_tipdoc=192 ";
                }
                else
                {
                    strSQL+="select * from tbm_cabrolpag " + "\n";
                    strSQL+="where co_emp= " + objZafParSis.getCodigoEmpresa() +"\n";
                    strSQL+="and ne_ani= " + cboPerAAAA.getSelectedItem().toString() +"\n";
                    strSQL+="and ne_mes= " + cboPerMes.getSelectedIndex() + "\n";
                    strSQL+="and ne_per=1 " + "\n";
                    strSQL+="and co_tipdoc=192 ";
                }
                rstLoc=stmLoc.executeQuery(strSQL);
                boolean blnRolExi=false;
                if (rstLoc.next())
                {
                    blnRolExi=true;
                }
                if (!blnRolExi)
                {
                    for (int i=0; i<tblDat.getRowCount();i++)
                    {
                        if (tblDat.getValueAt(i, INT_TBL_DAT_LINEA).toString().compareTo("M")==0)
                        {
                            //GUARDADO DE OBSERVACION
                            if (objTblMod.getValueAt(i, INT_TBL_DAT_DESFIL).toString().equals(""))
                            {
                                strSQL="";
                                strSQL+="UPDATE tbm_datgeningegrmentra SET tx_obs1="+objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_OBS).toString());
                                strSQL+=" WHERE co_emp = " + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP).toString();
                                strSQL+=" AND co_tra = " + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_TRA).toString();
                                strSQL+=" AND ne_ani = " + cboPerAAAA.getSelectedItem().toString();
                                strSQL+=" AND ne_mes = " + cboPerMes.getSelectedIndex();
                                strSQL+=" AND ne_per = 0";
                                stmLoc.executeUpdate(strSQL);
                            }
                            //GUARDADO DE PORCENTAJES
                            int intRecRubLstRub=0;
                            if (objTblMod.getValueAt(i, INT_TBL_DAT_DESFIL).toString().equals("Porcentaje"))
                            {
                                int intColHas=(INT_TBL_DAT_BUT_ASGEGR+1)+(lstPlaIng.size())+(lstPlaEgr.size());
                                for(int intCol=INT_TBL_DAT_BUT_ASGEGR+1;intCol<intColHas;intCol++)
                                {
                                    double dblNdPorValRub=objUti.redondear(objUti.parseDouble(objTblMod.getValueAt(i, intCol)), objZafParSis.getDecimalesMostrar());
                                    double dblNdPorValRubDif=objUti.redondear(objUti.parseDouble(100-dblNdPorValRub), objZafParSis.getDecimalesMostrar());
                                    String strNdPorValRub = "";
                                    if (dblNdPorValRub==0)
                                    {
                                        strNdPorValRub="0.00";
                                    }
                                    else
                                    {
                                        strNdPorValRub=String.valueOf(objUti.redondear(objUti.parseDouble((dblNdPorValRub)),objZafParSis.getDecimalesMostrar()));
                                    }
                                    strSQL="";
                                    strSQL+="UPDATE tbm_ingegrmentra SET nd_porrubpag="+strNdPorValRub;
                                    strSQL+=" WHERE co_emp = " + objTblMod.getValueAt(i-1, INT_TBL_DAT_COD_EMP).toString();
                                    strSQL+=" AND co_tra = " + objTblMod.getValueAt(i-1, INT_TBL_DAT_COD_TRA).toString();
                                    strSQL+=" AND ne_ani = " + cboPerAAAA.getSelectedItem().toString();
                                    strSQL+=" AND ne_mes = " + cboPerMes.getSelectedIndex();
                                    strSQL+=" AND co_rub = " + lstRub.get(intRecRubLstRub);
                                    strSQL+=" AND ne_per = 1";
                                    stmLoc.executeUpdate(strSQL);
                                    String strNdPorValRubDif = "";
                                    if (dblNdPorValRubDif==0)
                                    {
                                        //strNdPorValRubDif="null";
                                        strNdPorValRubDif="0.00";
                                    }
                                    else
                                    {
                                        //Tony: Modificacion al guardarse se mostraba decimales en vez de numeros enteros.
                                        //strNdPorValRubDif=String.valueOf(objUti.redondear(objUti.parseDouble((dblNdPorValRubDif)/100),objZafParSis.getDecimalesMostrar()));
                                        strNdPorValRubDif=String.valueOf(objUti.redondear(objUti.parseDouble((dblNdPorValRubDif)),objZafParSis.getDecimalesMostrar()));
                                    }
                                    strSQL="";
                                    strSQL+="UPDATE tbm_ingegrmentra SET nd_porrubpag="+strNdPorValRubDif;
                                    strSQL+=" WHERE co_emp = " + objTblMod.getValueAt(i-1, INT_TBL_DAT_COD_EMP).toString();
                                    strSQL+=" AND co_tra = " + objTblMod.getValueAt(i-1, INT_TBL_DAT_COD_TRA).toString();
                                    strSQL+=" AND ne_ani = " + cboPerAAAA.getSelectedItem().toString();
                                    strSQL+=" AND ne_mes = " + cboPerMes.getSelectedIndex();
                                    strSQL+=" AND co_rub = " + lstRub.get(intRecRubLstRub);
                                    strSQL+=" AND ne_per = 2";
                                    stmLoc.executeUpdate(strSQL);
                                    intRecRubLstRub++;
                                }
                            }
                            //GUARDADO POR VALORES ---> PORCENTAJE
                            intRecRubLstRub=0;
                            if (objTblMod.getValueAt(i, INT_TBL_DAT_DESFIL).toString().equals("Valor"))
                            {
                                int intColHas=(INT_TBL_DAT_BUT_ASGEGR+1)+(lstPlaIng.size())+(lstPlaEgr.size());
                                for (int intCol=INT_TBL_DAT_BUT_ASGEGR+1;intCol<intColHas;intCol++)
                                {
                                    double dblValRubMen=objUti.redondear(objUti.parseDouble(objTblMod.getValueAt(i-2, intCol)), objZafParSis.getDecimalesMostrar());
                                    double dblValRub=objUti.redondear(objUti.parseDouble(objTblMod.getValueAt(i, intCol)), objZafParSis.getDecimalesMostrar());
                                    if (dblValRub>0)
                                    {
                                        double dblNdPorValRub=objUti.redondear(objUti.parseDouble(dblValRub/dblValRubMen), objZafParSis.getDecimalesMostrar());
                                        double dblNdPorValRubDif=objUti.redondear(objUti.parseDouble(1-dblNdPorValRub), objZafParSis.getDecimalesMostrar());
                                        String strNdPorValRub = "";
                                        if (dblNdPorValRub==0)
                                        {
                                            strNdPorValRub="0.00";
                                        }
                                        else
                                        {
                                            strNdPorValRub=String.valueOf(objUti.redondear(objUti.parseDouble(dblNdPorValRub),objZafParSis.getDecimalesMostrar()));
                                        }
                                        strSQL="";
                                        strSQL+="UPDATE tbm_ingegrmentra SET nd_porrubpag="+strNdPorValRub;
                                        strSQL+=" WHERE co_emp = " + objTblMod.getValueAt(i-2, INT_TBL_DAT_COD_EMP).toString();
                                        strSQL+=" AND co_tra = " + objTblMod.getValueAt(i-2, INT_TBL_DAT_COD_TRA).toString();
                                        strSQL+=" AND ne_ani = " + cboPerAAAA.getSelectedItem().toString();
                                        strSQL+=" AND ne_mes = " + cboPerMes.getSelectedIndex();
                                        strSQL+=" AND co_rub = " + lstRub.get(intRecRubLstRub);
                                        strSQL+=" AND ne_per = 1";
                                        stmLoc.executeUpdate(strSQL);
                                        String strNdPorValRubDif = "";
                                        if (dblNdPorValRubDif==0)
                                        {
                                            strNdPorValRubDif="0.00";
                                        }
                                        else
                                        {
                                            strNdPorValRubDif=String.valueOf(objUti.redondear(objUti.parseDouble(dblNdPorValRubDif),objZafParSis.getDecimalesMostrar()));
                                        }
                                        strSQL="";
                                        strSQL+="UPDATE tbm_ingegrmentra SET nd_porrubpag="+strNdPorValRubDif;
                                        strSQL+=" WHERE co_emp = " + objTblMod.getValueAt(i-2, INT_TBL_DAT_COD_EMP).toString();
                                        strSQL+=" AND co_tra = " + objTblMod.getValueAt(i-2, INT_TBL_DAT_COD_TRA).toString();
                                        strSQL+=" AND ne_ani = " + cboPerAAAA.getSelectedItem().toString();
                                        strSQL+=" AND ne_mes = " + cboPerMes.getSelectedIndex();
                                        strSQL+=" AND co_rub = " + lstRub.get(intRecRubLstRub);
                                        strSQL+=" AND ne_per = 2";
                                        stmLoc.executeUpdate(strSQL);
                                        intRecRubLstRub++;
                                    }
                                }
                            }
                        }
                    }
                }
                else
                {
                mostrarMsgInf("NO SE PUEDEN MODIFICAR REGISTROS DE ROLES PASADOS");
                return false;
                }
            }
        }else{
                mostrarMsgInf("NO SE PUEDE MODIFICAR. SE REALIZÓ EL PRIMER PASO PARA AUTORIZAR ROLES DE PAGO. (Revision de Faltas)");
                return false;
            }
        }
        catch (java.sql.SQLException Evt)
        {
            blnRes = false;
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt)
        {
            blnRes = false;
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        finally
        {
            try{stmLoc.close();}catch(Throwable ignore){}
            try{rstLoc.close();}catch(Throwable ignore){}
        }
        return blnRes;
    }
    
    
    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
         exitForm();
}//GEN-LAST:event_butCerActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (objThrGUI==null)
        {
            objThrGUI=new ZafThreadGUI();
            objThrGUI.start();
        }
    }//GEN-LAST:event_butConActionPerformed

    /**
    * Esta función determina si los campos son válidos.
    * @return true: Si los campos son válidos.
    * <BR>false: En el caso contrario.
    */
    private boolean validaCampos()
    {
        //Validar el "Año".
        int intPerAAAA=cboPerAAAA.getSelectedIndex();
        if (intPerAAAA<=0)
        {
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Año</FONT> es obligatorio.<BR>Seleccione el año y vuelva a intentarlo.</HTML>");
            cboPerAAAA.requestFocus();
            return false;
        }
        //Validar el "Mes".
        int intPerMes=cboPerMes.getSelectedIndex();
        if (intPerMes<=0)
        {
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Mes</FONT> es obligatorio.<BR>Seleccione el mes y vuelva a intentarlo.</HTML>");
            cboPerMes.requestFocus();
            return false;
        }
        return true;
    }
    
    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
    
}//GEN-LAST:event_optFilActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        
}//GEN-LAST:event_optFilItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected())
        {
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodDep.setText("");
            txtNomDep.setText("");
            txtCodTra.setText("");
            txtNomTra.setText("");
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
}//GEN-LAST:event_optTodItemStateChanged

    private void txtCodDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDepActionPerformed
        txtCodDep.transferFocus();
    }//GEN-LAST:event_txtCodDepActionPerformed

    private void txtCodDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusGained
        strCodDep = txtCodDep.getText();
        txtCodDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodDepFocusGained

    private void txtCodDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusLost
        if (!txtCodDep.getText().equalsIgnoreCase(strCodDep))
        {
            if (txtCodDep.getText().equals(""))
            {
                txtCodDep.setText("");
                txtNomDep.setText("");
            }
            else
            {
                BuscarDep("a1.co_dep", txtCodDep.getText(), 0);
            }
        }
        else
        {
            txtCodDep.setText(strCodDep);
        }
    }//GEN-LAST:event_txtCodDepFocusLost

    public void BuscarDep(String campo,String strBusqueda,int tipo)
    {
        vcoDep.setTitle("Listado de Departamentos");
        if (vcoDep.buscar(campo, strBusqueda ))
        {
            txtCodDep.setText(vcoDep.getValueAt(1));
            txtNomDep.setText(vcoDep.getValueAt(3));
        }
        else
        {
            vcoDep.setCampoBusqueda(tipo);
            vcoDep.cargarDatos();
            vcoDep.show();
            if (vcoDep.getSelectedButton()==vcoDep.INT_BUT_ACE)
            {
                txtCodDep.setText(vcoDep.getValueAt(1));
                txtNomDep.setText(vcoDep.getValueAt(3));
            }
            else
            {
                txtCodDep.setText(strCodDep);
                txtNomDep.setText(strDesLarDep);
            }
        }
    }
    
    public void BuscarTra(String campo,String strBusqueda,int tipo)
    {
        vcoTra.setTitle("Listado de Empleados");
        if (vcoTra.buscar(campo, strBusqueda ))
        {
            txtCodTra.setText(vcoTra.getValueAt(1));
            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        }
        else
        {
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE)
            {
                txtCodTra.setText(vcoTra.getValueAt(1));
                txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
            }
            else
            {
                txtCodTra.setText(strCodTra);
                txtNomTra.setText(strNomTra);
            }
        }
    }
    
    private void txtNomDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDepActionPerformed
        txtNomDep.transferFocus();
    }//GEN-LAST:event_txtNomDepActionPerformed

    private void txtNomDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusGained
        strDesLarDep=txtNomDep.getText();
        txtNomDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtNomDepFocusGained

    private void txtNomDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusLost
        if (txtNomDep.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomDep.getText().equalsIgnoreCase(strDesLarDep))
            {
                if (txtNomDep.getText().equals(""))
                {
                    txtCodDep.setText("");
                    txtNomDep.setText("");
                }
                else
                {
                    mostrarVenConDep(2);
                }
            }
            else
                txtNomDep.setText(strDesLarDep);
        }
    }//GEN-LAST:event_txtNomDepFocusLost

    private void butDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDepActionPerformed
        strCodDep=txtCodDep.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConDep(0);
    }//GEN-LAST:event_butDepActionPerformed

    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        txtCodTra.transferFocus();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void txtCodTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusGained
        strCodTra = txtCodTra.getText();
        txtCodTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodTraFocusGained

    private void txtCodTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusLost
        if (!txtCodTra.getText().equalsIgnoreCase(strCodTra))
        {
            if (txtCodTra.getText().equals(""))
            {
                txtCodTra.setText("");
                txtNomTra.setText("");
            }
            else
            {
                BuscarTra("a1.co_tra", txtCodTra.getText(), 0);
            }
        }
        else
        {
            txtCodTra.setText(strCodTra);
        }
    }//GEN-LAST:event_txtCodTraFocusLost

    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        txtNomTra.transferFocus();
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        strNomTra=txtNomTra.getText();
        txtNomTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
        if (txtNomTra.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomTra.getText().equalsIgnoreCase(strNomTra))
            {
                if (txtNomTra.getText().equals(""))
                {
                    txtCodTra.setText("");
                    txtNomTra.setText("");
                }
                else
                {
                    mostrarVenConTra(2);
                }
            }
            else
                txtNomTra.setText(strNomTra);
        }
    }//GEN-LAST:event_txtNomTraFocusLost

    private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed
        strCodTra=txtCodTra.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConTra(0);
    }//GEN-LAST:event_butTraActionPerformed

    private void tblDatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatFocusGained

    }//GEN-LAST:event_tblDatFocusGained

    private void spiNumPagStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spiNumPagStateChanged

    }//GEN-LAST:event_spiNumPagStateChanged

    private void cboPerAAAAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerAAAAActionPerformed

    }//GEN-LAST:event_cboPerAAAAActionPerformed

    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);
            if (validaCampos())
            {
                if (cargarDat())
                {
                    if (tblDat.getRowCount()>0)
                    {
                        lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
                    }
                }
            }
            else
            {
                lblMsgSis.setText("Listo");
                pgrSis.setIndeterminate(false);
            }
            objThrGUI=null;
        }
    }

    /**
    * Se encarga de cargar la informacion en la tabla
    * @return  true si esta correcto y false  si hay algun error
    */
    private boolean cargarDat()
    {
        blnConsDat = true;
        boolean blnRes=false;
        blnMod = false;
        java.sql.Connection conn = null;
        java.sql.Statement stmLoc = null;
        java.sql.Statement stmAux;
        java.sql.ResultSet rstLoc = null;
        java.sql.ResultSet rstAux;
        String strSql="",sqlFil="";
        Vector vecDataCon;
        ArrayList<String> lstPla = null;
        try
        {
            conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                stmAux=conn.createStatement();
                vecDataCon = new Vector();
                String sqlFilEmp = "";
                if (txtCodEmp.getText().compareTo("")!=0)
                {
                    sqlFilEmp+= " and b.co_emp  = " + txtCodEmp.getText() + " ";
                }
                if (txtCodTra.getText().compareTo("")!=0)
                {
                    sqlFilEmp+= " AND a.co_tra  = " + txtCodTra.getText() + " ";
                }
                if (txtCodDep.getText().compareTo("")!=0)
                {
                    sqlFilEmp+= " AND b.co_dep  = " + txtCodDep.getText() + " ";
                }
                if (objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo())
                {
                    if (objZafParSis.getCodigoUsuario()==1)
                    {
                        strSql+="SELECT d.co_emp, d.tx_nom as nomemp, a.co_tra, (a.tx_ape || ' ' || a.tx_nom) as nomtra, b.co_dep, b.co_jef, b.co_pla, "+"\n";
                        strSql+="e.tx_descor,e.tx_deslar,c.tx_descor, c.tx_deslar, g.tx_obs1 "+"\n";
                        strSql+="from tbm_tra a "+"\n";
                        strSql+="left outer join tbm_traemp b on (a.co_tra = b.co_tra) "+"\n";
                        strSql+="left outer join tbm_dep c on (c.co_dep=b.co_dep) "+"\n";
                        strSql+="inner join tbm_emp d on (d.co_emp=b.co_emp) "+"\n";
                        strSql+="left outer join tbm_cabPlaRolPag e on (b.co_emp=e.co_emp and b.co_pla=e.co_pla) "+"\n";
                        strSql+="inner join tbm_ingegrmentra f on (f.co_emp=b.co_emp and f.co_tra=b.co_tra) "+"\n";
                        strSql+="left outer join tbm_datgeningegrmentra g on (g.co_emp=b.co_emp and g.co_tra=a.co_tra and g.ne_ani=f.ne_ani and g.ne_mes=f.ne_mes and g.ne_per=0) "+"\n";
                        strSql+="where a.st_reg like 'A' and f.ne_mes="+cboPerMes.getSelectedIndex()+" and f.ne_ani="+cboPerAAAA.getSelectedItem().toString()+"\n";
                        strSql+=sqlFilEmp + " " +"\n";
                        strSql+="group by d.co_emp, d.tx_nom , a.co_tra, nomtra, b.co_dep, b.co_jef, b.co_pla, "+"\n";
                        strSql+="e.tx_descor,e.tx_deslar,c.tx_descor, c.tx_deslar,b.co_emp, g.tx_obs1 "+"\n";
                    }
                    else
                    {
                        strSql+="select d.co_emp, d.tx_nom as nomemp, a.co_tra, (a.tx_ape || ' ' || a.tx_nom) as nomtra, b.co_dep, b.co_jef, b.co_pla, "+"\n";
                        strSql+="e.tx_descor,e.tx_deslar,c.tx_descor, c.tx_deslar, h.tx_obs1 "+"\n";
                        strSql+="from tbm_tra a "+"\n";
                        strSql+="left outer join tbm_traemp b on a.co_tra = b.co_tra "+"\n";
                        strSql+="left outer join tbm_dep c on c.co_dep=b.co_dep "+"\n";
                        strSql+="inner join tbm_emp d on (d.co_emp=b.co_emp)  "+"\n";
                        strSql+="left outer join tbm_cabPlaRolPag e on (b.co_emp=e.co_emp and b.co_pla=e.co_pla) "+"\n";
                        strSql+="left outer join tbr_depprgusr f on(b.co_dep=f.co_dep and f.co_dep in (select co_dep from tbr_depprgusr where co_usr = "+objZafParSis.getCodigoUsuario()+"\n";
                        strSql+="and co_mnu="+objZafParSis.getCodigoMenu()+" )) "+"\n";
                        strSql+="inner join tbm_ingegrmentra g on (g.co_emp=b.co_emp and g.co_tra=b.co_tra) "+"\n";
                        strSql+="left outer join tbm_datgeningegrmentra h on (h.co_emp=b.co_emp and h.co_tra=a.co_tra and h.ne_ani=g.ne_ani and h.ne_mes=g.ne_mes and h.ne_per=0) "+"\n";
                        strSql+="where a.st_reg like 'A' and g.ne_mes="+cboPerMes.getSelectedIndex()+" and g.ne_ani="+cboPerAAAA.getSelectedItem().toString()+"\n";
                        strSql+=sqlFilEmp+ " " +"\n";
                        strSql+="group by d.co_emp, d.tx_nom , a.co_tra, nomtra, b.co_dep, b.co_jef, b.co_pla, "+"\n";
                        strSql+="e.tx_descor,e.tx_deslar,c.tx_descor, c.tx_deslar,b.co_emp, h.tx_obs1 "+"\n";
                    }
                }
                else
                {
                    if (objZafParSis.getCodigoUsuario()==1)
                    {
                        strSql+="select d.co_emp, d.tx_nom as nomemp, a.co_tra, (a.tx_ape || ' ' || a.tx_nom) as nomtra, b.co_dep, b.co_jef, b.co_pla, "+"\n";
                        strSql+="e.tx_descor,e.tx_deslar,c.tx_descor, c.tx_deslar, g.tx_obs1 "+"\n";
                        strSql+="from tbm_tra a "+"\n";
                        strSql+="left outer join tbm_traemp b on (a.co_tra = b.co_tra) "+"\n";
                        strSql+="left outer join tbm_dep c on (c.co_dep=b.co_dep) "+"\n";
                        strSql+="inner join tbm_emp d on (d.co_emp=b.co_emp) "+"\n";
                        strSql+="left outer join tbm_cabPlaRolPag e on (b.co_emp=e.co_emp and b.co_pla=e.co_pla) "+"\n";
                        strSql+="inner join tbm_ingegrmentra f on (f.co_emp=b.co_emp and f.co_tra=b.co_tra) "+"\n";
                        strSql+="left outer join tbm_datgeningegrmentra g on (g.co_emp=b.co_emp and g.co_tra=a.co_tra and g.ne_ani=f.ne_ani and g.ne_mes=f.ne_mes and g.ne_per=0) "+"\n";
                        strSql+="where a.st_reg like 'A' and f.ne_mes="+cboPerMes.getSelectedIndex()+" and f.ne_ani="+cboPerAAAA.getSelectedItem().toString() +"\n";
                        strSql+=sqlFilEmp+ " AND b.co_emp = " + objZafParSis.getCodigoEmpresa() +"\n";
                        strSql+="group by d.co_emp, d.tx_nom , a.co_tra, nomtra, b.co_dep, b.co_jef, b.co_pla, "+"\n";
                        strSql+="e.tx_descor,e.tx_deslar,c.tx_descor, c.tx_deslar,b.co_emp, g.tx_obs1 "+"\n";
                        System.out.println("strSql: " + strSql);
                    }
                    else
                    {
                        strSql+="select d.co_emp, d.tx_nom as nomemp, a.co_tra, (a.tx_ape || ' ' || a.tx_nom) as nomtra, b.co_dep, b.co_jef, b.co_pla, "+"\n";
                        strSql+="e.tx_descor,e.tx_deslar,c.tx_descor, c.tx_deslar, h.tx_obs1 "+"\n";
                        strSql+="from tbm_tra a "+"\n";
                        strSql+="left outer join tbm_traemp b on a.co_tra = b.co_tra "+"\n";
                        strSql+="left outer join tbm_dep c on c.co_dep=b.co_dep "+"\n";
                        strSql+="inner join tbm_emp d on (d.co_emp=b.co_emp) "+"\n";
                        strSql+="left outer join tbm_cabPlaRolPag e on (b.co_emp=e.co_emp and b.co_pla=e.co_pla) "+"\n";
                        strSql+="left outer join tbr_depprgusr f on(b.co_dep=f.co_dep and f.co_dep in (select co_dep from tbr_depprgusr where co_usr = "+objZafParSis.getCodigoUsuario() +"\n";
                        strSql+="and co_mnu="+objZafParSis.getCodigoMenu()+" ))  "+"\n";
                        strSql+="inner join tbm_ingegrmentra g on (g.co_emp=b.co_emp and g.co_tra=b.co_tra) "+"\n";
                        strSql+="left outer join tbm_datgeningegrmentra h on (h.co_emp=b.co_emp and h.co_tra=a.co_tra and h.ne_ani=g.ne_ani and h.ne_mes=g.ne_mes and h.ne_per=0) "+"\n";
                        strSql+="where a.st_reg like 'A' and g.ne_mes="+cboPerMes.getSelectedIndex()+" and g.ne_ani="+cboPerAAAA.getSelectedItem().toString()+"\n";
                        strSql+=sqlFilEmp+ " AND b.co_emp = " + objZafParSis.getCodigoEmpresa() + " "+"\n";
                        strSql+="group by d.co_emp, d.tx_nom , a.co_tra, nomtra, b.co_dep, b.co_jef, b.co_pla,  "+"\n";
                        strSql+="e.tx_descor,e.tx_deslar,c.tx_descor, c.tx_deslar,b.co_emp, h.tx_obs1 "+"\n";
                    }
                }
                strSql+= " order by (a.tx_ape ||  ' '  || a.tx_nom)";
                rstLoc=stmLoc.executeQuery(strSql);
                vecDat = new Vector();
                int intCont=1;
                int intEsPI=1;
                while (rstLoc.next())
                {
                    /*
                    * PARTE OBS + RUBROS
                    */
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_DAT_LINEA,"");
                    if (intEsPI%2==1)
                    {
                        vecReg.add(INT_TBL_DAT_EST,"S");
                    }
                    else
                    {
                        vecReg.add(INT_TBL_DAT_EST,"V");
                    }
                    vecReg.add(INT_TBL_DAT_COD_EMP,rstLoc.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_NOM_EMP,rstLoc.getString("nomemp"));
                    vecReg.add(INT_TBL_DAT_COD_TRA,rstLoc.getString("co_tra"));
                    vecReg.add(INT_TBL_DAT_NOM_APE_TRA,rstLoc.getString("nomtra"));
                    vecReg.add(INT_TBL_DAT_OBS,rstLoc.getString("tx_obs1"));
                    vecReg.add(INT_TBL_DAT_BUTOBS,"...");
                    vecReg.add(INT_TBL_DAT_DESFIL,"");
                    vecReg.add(INT_TBL_DAT_BUT_ASGING,"");
                    vecReg.add(INT_TBL_DAT_BUT_ASGEGR,"");
                    int intPos = INT_TBL_DAT_BUT_ASGEGR+1;
                    strSql="";
                    strSql+="select a.*,b.tx_tiprub,c.tx_ape,c.tx_nom from tbm_ingegrmentra a";
                    strSql+=" inner join tbm_rubrolpag b on (a.co_rub=b.co_rub)";
                    strSql+=" inner join tbm_tra c on (a.co_tra=c.co_tra)";
                    strSql+=" where a.co_emp="+rstLoc.getString("co_emp");
                    strSql+=" and a.ne_per=0";
                    strSql+=" and a.co_tra="+rstLoc.getString("co_tra");
                    strSql+=" and a.ne_ani="+cboPerAAAA.getSelectedItem().toString();
                    strSql+=" and a.ne_mes="+cboPerMes.getSelectedIndex();
                    strSql+=" order by c.tx_ape || ' ' ||c.tx_nom,b.tx_tiprub desc, a.co_rub";
                    rstAux=stmAux.executeQuery(strSql);
                    double dblTotIng=0;
                    double dblTotEgr=0;
                    ArrayList<Double> lstRub=new ArrayList<Double>();
                    if (rstAux.next())
                    {
                        int intCol=intPos;
                        do
                        {
                            vecReg.add(intCol,rstAux.getDouble("nd_valrub"));
                            lstRub.add(rstAux.getDouble("nd_valrub"));
                            if (rstAux.getString("tx_tiprub").equals("I"))
                            {
                            dblTotIng+=objUti.redondear(objUti.parseDouble(rstAux.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                            }
                            else if (rstAux.getString("tx_tiprub").equals("E"))
                            {
                            dblTotEgr+=objUti.redondear(objUti.parseDouble(rstAux.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());                               
                            }
                            intCol++;
                        } while(rstAux.next());
                        vecReg.add(vecReg.size(),dblTotIng);
                        vecReg.add(vecReg.size(),dblTotEgr);
                        vecReg.add(vecReg.size(),objUti.redondear(objUti.parseDouble(dblTotIng+dblTotEgr), objZafParSis.getDecimalesMostrar()));
                    }
                    else
                    {
                        for (int intFil=intPos;intFil<tblDat.getColumnCount();intFil++)
                        {
                            vecReg.add(intFil,objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()));
                        }
                    }
                    vecDat.add(vecReg);
                    vecReg = new java.util.Vector();
                    ArrayList<Double> lstPor=new ArrayList<Double>();
                    /*
                    * PARTE PORCENTAJE
                    */
                    vecReg.add(INT_TBL_DAT_LINEA,"");
                    if (intEsPI%2==1)
                    {
                        vecReg.add(INT_TBL_DAT_EST,"S");
                    }
                    else
                    {
                        vecReg.add(INT_TBL_DAT_EST,"V");
                    }
                    vecReg.add(INT_TBL_DAT_COD_EMP,"");
                    vecReg.add(INT_TBL_DAT_NOM_EMP,"");
                    vecReg.add(INT_TBL_DAT_COD_TRA,"");
                    vecReg.add(INT_TBL_DAT_NOM_APE_TRA,"");
                    vecReg.add(INT_TBL_DAT_OBS,"");
                    vecReg.add(INT_TBL_DAT_BUTOBS,"");
                    vecReg.add(INT_TBL_DAT_DESFIL,"Porcentaje");
                    vecReg.add(INT_TBL_DAT_BUT_ASGING,"");
                    vecReg.add(INT_TBL_DAT_BUT_ASGEGR,"");
                    strSql="";
                    strSql+="select a.*,b.tx_tiprub,c.tx_ape,c.tx_nom from tbm_ingegrmentra a";
                    strSql+=" inner join tbm_rubrolpag b on (a.co_rub=b.co_rub)";
                    strSql+=" inner join tbm_tra c on (a.co_tra=c.co_tra)";
                    strSql+=" where a.co_emp="+rstLoc.getString("co_emp");
                    strSql+=" and a.ne_per=1";
                    strSql+=" and a.co_tra="+rstLoc.getString("co_tra");
                    strSql+=" and a.ne_ani="+cboPerAAAA.getSelectedItem().toString();
                    strSql+=" and a.ne_mes="+cboPerMes.getSelectedIndex();
                    strSql+=" order by c.tx_ape || ' ' ||c.tx_nom,b.tx_tiprub desc, a.co_rub";
                    rstAux=stmAux.executeQuery(strSql);
                    if (rstAux.next())
                    {
                        int intCol=intPos;
                        do
                        {
                            if (rstAux.getString("nd_porrubpag")==null)
                            {
                                vecReg.add(intCol++,objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()));
                                lstPor.add(objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()));
                            }
                            else
                            {
                                vecReg.add(intCol++,rstAux.getDouble("nd_porrubpag"));
                                lstPor.add(rstAux.getDouble("nd_porrubpag"));
                            }
                        } while(rstAux.next());
                        vecReg.add(vecReg.size(),objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()));
                        vecReg.add(vecReg.size(),objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()));
                        vecReg.add(vecReg.size(),objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()));
                    }
                    else
                    {
                        for (int intFil=intPos;intFil<tblDat.getColumnCount();intFil++)
                        {
                            vecReg.add(intFil,objUti.redondear(objUti.parseDouble(0), objZafParSis.getDecimalesMostrar()));
                        }
                    }
                    vecDat.add(vecReg);
                    vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_DAT_LINEA,"");
                    if (intEsPI%2==1)
                    {
                        vecReg.add(INT_TBL_DAT_EST,"S");
                    }
                    else
                    {
                        vecReg.add(INT_TBL_DAT_EST,"V");
                    }
                    vecReg.add(INT_TBL_DAT_COD_EMP,"");
                    vecReg.add(INT_TBL_DAT_NOM_EMP,"");
                    vecReg.add(INT_TBL_DAT_COD_TRA,"");
                    vecReg.add(INT_TBL_DAT_NOM_APE_TRA,"");
                    vecReg.add(INT_TBL_DAT_OBS,"");
                    vecReg.add(INT_TBL_DAT_BUTOBS,"");
                    vecReg.add(INT_TBL_DAT_DESFIL,"Valor");
                    vecReg.add(INT_TBL_DAT_BUT_ASGING,"");
                    vecReg.add(INT_TBL_DAT_BUT_ASGEGR,"");
                    for (int intFil=intPos;intFil<tblDat.getColumnCount();intFil++)
                    {
                        vecReg.add(intFil,"");
                    }
                    vecDat.add(vecReg);
                    vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_DAT_LINEA,"");
                    if (intEsPI%2==1)
                    {
                        vecReg.add(INT_TBL_DAT_EST,"S");
                    }
                    else
                    {
                        vecReg.add(INT_TBL_DAT_EST,"V");
                    }
                    vecReg.add(INT_TBL_DAT_COD_EMP,"");
                    vecReg.add(INT_TBL_DAT_NOM_EMP,"");
                    vecReg.add(INT_TBL_DAT_COD_TRA,"");
                    vecReg.add(INT_TBL_DAT_NOM_APE_TRA,"");
                    vecReg.add(INT_TBL_DAT_OBS,"");
                    vecReg.add(INT_TBL_DAT_BUTOBS,"");
                    vecReg.add(INT_TBL_DAT_DESFIL,"Primera quincena");
                    vecReg.add(INT_TBL_DAT_BUT_ASGING,"");
                    vecReg.add(INT_TBL_DAT_BUT_ASGEGR,"");
                    int intRec=0;
                    int intColHas=intPos+lstPlaIng.size()+lstPlaEgr.size();
                    double dblValTotRec=0;
                    for (int intCol=intPos;intCol<intColHas;intCol++)
                    {
                        double dblA= objUti.redondear(objUti.parseDouble(lstRub.get(intRec)), objZafParSis.getDecimalesMostrar());
                        double dblP= objUti.redondear(objUti.parseDouble(lstPor.get(intRec)/100), objZafParSis.getDecimalesMostrar());
                        vecReg.add(intCol,objUti.redondear(objUti.parseDouble(dblA*dblP), objZafParSis.getDecimalesMostrar()));
                        dblValTotRec+=objUti.redondear(objUti.parseDouble(dblA*dblP), objZafParSis.getDecimalesMostrar());
                        if (intRec<=lstPlaIng.size())
                        {

                        }
                        intRec++;
                    }
                    int intCanCol=tblDat.getColumnCount();
                    for (int intCol=intColHas;intCol<tblDat.getColumnCount();intCol++)
                    {
                        if (intCol==(tblDat.getColumnCount()-1))
                        {
                            vecReg.add(intCol,objUti.redondear(dblValTotRec,objZafParSis.getDecimalesMostrar()));
                        }
                        else if (intCol==(tblDat.getColumnCount()-2))
                        {
                            vecReg.add(intCol,null);
                        }
                        else if (intCol==(tblDat.getColumnCount()-3))
                        {
                            vecReg.add(intCol,objUti.redondear(dblValTotRec,objZafParSis.getDecimalesMostrar()));
                        }
                    }
                    vecDat.add(vecReg);
                    if (intCont==1)
                    {
                        //cantidad de filas por empleados por eso es = 4
                        intEsPI++; 
                        intCont=0;
                    }
                    intCont++;
                }
                if (vecDat.size()==0)
                {
                    mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                    lblMsgSis.setText("Listo");
                }
                else
                {
                    //Asignar vectores al modelo.
                    objTblMod.setData(vecDat);
                    tblDat.setModel(objTblMod);
                    tabGen.setSelectedIndex(1);
                    lblMsgSis.setText("Se encontraron " + (tblDat.getRowCount()/4) + " registros");
                }
                pgrSis.setIndeterminate(false);
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException Evt)
        {
            Evt.printStackTrace();
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt)
        {
            Evt.printStackTrace();
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        finally
        {
            try
            {
                rstLoc.close();
            }
            catch (Throwable ignore)
            {

            }
            try
            {
                stmLoc.close();
            }
            catch (Throwable ignore)
            {

            }
            try
            {
                conn.close();
            }
            catch (Throwable ignore)
            {

            }
        }
        return blnRes;
    }
    
    /**
    * Para salir de la pantalla en donde estamos y pide confirmacion de salidad.
    */
    private void exitForm()
    {
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butDep;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butTra;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboPerAAAA;
    private javax.swing.JComboBox cboPerMes;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNumPag;
    private javax.swing.JLabel lblNumPag1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JSpinner spiNumPag;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabGen;
    public javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodDep;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtNomDep;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomTra;
    // End of variables declaration//GEN-END:variables

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Cód. Emp.";
                    break;
                case INT_TBL_DAT_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_DAT_COD_TRA:
                    strMsg="Código del empleado";
                    break;
                case INT_TBL_DAT_NOM_APE_TRA:
                    strMsg="Nombres y Apellidos del empleado";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    protected void finalize() throws Throwable
    {
        System.gc();
        super.finalize();
    }

    private boolean mostrarVenConEmp(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else
                        {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else
                        {
                            txtNomEmp.setText(strNomEmp);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
    private boolean mostrarVenConTra(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
            case 0: //Mostrar la ventana de consulta.
                vcoTra.setCampoBusqueda(1);
                vcoTra.show();
                if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE)
                {
                    txtCodTra.setText(vcoTra.getValueAt(1));
                    txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                }
                break;
            case 1: //Búsqueda directa por "Número de cuenta".
                if (vcoTra.buscar("a1.co_tra", txtCodTra.getText()))
                {
                    txtCodTra.setText(vcoTra.getValueAt(1));
                    txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                }
                else
                {
                    vcoTra.setCampoBusqueda(0);
                    vcoTra.setCriterio1(11);
                    vcoTra.cargarDatos();
                    vcoTra.show();
                    if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE)
                    {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    else
                    {
                        txtCodTra.setText(strCodTra);
                    }
                }
                break;
            case 2: //Búsqueda directa por "Descripción larga".
                if (vcoTra.buscar("a1.tx_ape", txtNomTra.getText()))
                {
                    txtCodTra.setText(vcoTra.getValueAt(1));
                    txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                }
                else
                {
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.setCriterio1(11);
                    vcoTra.cargarDatos();
                    vcoTra.show();
                    if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE)
                    {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    else
                    {
                        txtNomTra.setText(strNomTra);
                    }
                }
                break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConEmp()
    {
        boolean blnRes=true;
        String strTitVenCon="";
        String strSQL="";
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            if (objZafParSis.getCodigoUsuario()==1)
            {
                if (objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo())
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp<>" + objZafParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
                else
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp in ("+objZafParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
            }
            else
            {
                if (objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo())
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objZafParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp<>" + objZafParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
                else
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objZafParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp in ("+objZafParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
            }
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
    * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
    * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
    * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
    * exista lo que se está buscando) o presentar la ventana de consulta para que
    * el usuario seleccione la opción que desea utilizar.
    * @param intTipBus El tipo de búsqueda a realizar.
    * @return true: Si no se presentó ningún problema.
    * <BR>false: En el caso contrario.
    */
    private boolean mostrarVenConDep(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoDep.setCampoBusqueda(2);
                    vcoDep.setVisible(true);
                    if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));                       
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.co_dep", txtCodDep.getText()))
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
                    else
                    {
                        vcoDep.setCampoBusqueda(1);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                        }
                        else
                        {
                            txtNomDep.setText(strDesLarDep);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoDep.setCampoBusqueda(2);
                    if (vcoDep.buscar("a1.tx_desLar", txtNomDep.getText()))
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
                    else
                    {
                        vcoDep.setCampoBusqueda(2);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                        }
                        else
                        {
                            txtNomDep.setText(strDesLarDep);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
    * Esta función configura la "Ventana de consulta" que será utilizada para
    * mostrar los "Departamentos".
    */
    private boolean configurarVenConDep()
    {
        boolean blnRes=true;
        String strSQL="";
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_dep");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción corta");
            arlAli.add("Descripción larga");
            arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            arlAncCol.add("110");
            arlAncCol.add("40");
            if (objZafParSis.getCodigoUsuario()==1)
            {
                strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where st_reg like 'A' order by co_dep";
            }
            else
            {
                strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objZafParSis.getCodigoUsuario()+" "+
                //"and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";
                "and co_mnu="+objZafParSis.getCodigoMenu()+")";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            vcoDep=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado Departamentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoDep.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoDep.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
    * Esta función configura la "Ventana de consulta" que será utilizada para
    * mostrar los "Empleados".
    */
    private boolean configurarVenConTra()
    {
        boolean blnRes=true;
        String strSQL="";
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ape");
            arlCam.add("a1.tx_nom");
            //arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            //arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            //arlAncCol.add("40");
            if (objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo())
            {
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where a.st_reg like 'A' "+
                "order by (a.tx_ape || ' ' || a.tx_nom)";
            }
            else
            {
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where a.st_reg like 'A' and co_emp = "+ objZafParSis.getCodigoEmpresa() + " " +
                "order by (a.tx_ape || ' ' || a.tx_nom)";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            //intColOcu[0]=3;
            vcoTra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   public boolean validaPasoVerificacionFaltas(String strAnio, int intMes, int intCoEmp,int intCoLoc, int intNePer ){
        String resp = "";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        boolean blnRes = true;
        try {
            conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                String strSql ="select distinct case when st_revfal = 'S' then 'S' else 'N' end as st_revFal "
                    + " from tbm_feccorrolpag a1"
                    + " LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a2.co_emp=a1.co_emp) "
                    + " LEFT OUTER JOIN tbm_cabrolpag a3 ON (a3.co_emp=a1.co_emp and a3.co_tipdoc=a2.co_tipdoc and a3.ne_ani=a1.ne_ani and a3.ne_mes=a1.ne_mes and a3.ne_per=a1.ne_per and a3.st_reg='A')"
                    + " where a1.co_emp=" + intCoEmp +" and a3.co_loc=" +intCoLoc  
                    + " and a2.co_tipdoc IN ( 192) and a1.ne_ani =" + strAnio + " and a1.ne_mes =" + intMes + " and a1.ne_per =" + intNePer;
                    rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    resp = rstLoc.getString("st_revFal");
                    if (resp.equals("S")) {
                        blnRes = false;
                    } else {
                        blnRes = true;
                    }
                }
                conLoc.close();
                conLoc = null;
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
            }
        } catch (Exception e) { 
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;       
    } 
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RecursosHumanos.ZafRecHum30;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import Maestros.ZafMae07.ZafMae07_01;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Listado de usuarios que autorizan horas suplementarias/extraordinarias
 *
 * @author Roberto Flores
 */
public class ZafRecHum30_01 extends javax.swing.JInternalFrame 
{
    ZafParSis objParSis;
    ZafUtil objUti;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiChk objTblCelEdiChk;                         //Editor de Check Box 
    private ZafTblCelEdiChk objTblCelEdiChk2;                         //Editor de Check Box 
    private ZafTblCelRenChk objTblCelRenChkLab;                         //Renderer de Check Box
    private ZafTblCelEdiTxt objTblCelEdiTxt;                            //Editor: JTextField en celda.
    private ZafTblCelEdiButGen objTblCelEdiButGen;                  //Editor: JButton en celda.

    // TABLA DE DATOS
    private final int INT_TBL_LIN = 0;
    private final int INT_TBL_CODUSR = 1;
    private final int INT_TBL_USR = 2;
    private final int INT_TBL_NOMUSR = 3;
    private final int INT_TBL_CHKAUT = 4;
    private final int INT_TBL_CHKDEN = 5;
    private final int INT_TBL_HORAUT = 6;
    private final int INT_TBL_OBS = 7;
    private final int INT_TBL_BUTOBS = 8;
    private final int INT_TBL_COREG = 9;
    private final int INT_TBL_NEJERAUT = 10;
    private final int INT_TBL_STOBLSEGJERAUT = 11;

    private Vector vecDat, vecReg;

    String strCodEmp = "";
    String strCodLoc = "";
    String strCodTipdoc = "";
    String strCodDoc = "";
    String strNumDoc = "";
    String strHorSupExt = "";
    String strFecha = "";
    int intDiaSem = 0;

    //public ZafRecHum0_01(ZafParSis objZafParsis, String strCodEmp, String strCodLoc, String strCodTipdoc, String strCodDoc, String strNumDoc,String strHorSol ) {
    public ZafRecHum30_01(ZafParSis objZafParsis, String strCodEmp, String strCodLoc, String strCodTipdoc, String strCodDoc, String strNumDoc, String strFecha) 
    {
        try 
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) objZafParsis.clone();
            objUti = new ZafUtil();

            this.strCodEmp = strCodEmp;
            this.strCodLoc = strCodLoc;
            this.strCodTipdoc = strCodTipdoc;
            this.strCodDoc = strCodDoc;
            this.strNumDoc = strNumDoc;
            this.strFecha = strFecha;
            /*this.strHorSol=strHorSol;
             this.intDiaSem=obtenerDiaSemana();
             this.strHorSupExt=determinarHoraSuplementariaExtraordinarias(intDiaSem);*/
            initComponents();

            vecDat = new Vector();

            this.setTitle(objParSis.getNombreMenu());

        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /*public int obtenerDiaSemana(){
    
     int intDiaSemAux=0;
     try {

        
     strHorSol=strHorSol.replace("-", "");
     int intDD =Integer.valueOf(strHorSol.substring(6, 8));
     int intMM=Integer.valueOf(strHorSol.substring(4, 6));
     int intYYYY=Integer.valueOf(strHorSol.substring(0, 4));
     //String dateString="20/05/2012";
     //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy"); 
     //Date convertedDate = dateFormat.parse(dateString); 
     //cal.setTime(convertedDate);
            
     Calendar cal = Calendar.getInstance();
            
     cal.set(Calendar.YEAR, intYYYY);
     cal.set(Calendar.DATE, intDD);
     cal.set(Calendar.MONTH, intMM+1);
            
            
     intDiaSemAux = cal.get(Calendar.DAY_OF_WEEK);
     if(intDiaSemAux==7){
     intDiaSemAux=1;
     }else{
     intDiaSemAux=intDiaSemAux+1;
     }
     System.out.println(intDiaSemAux);
     } catch (Exception ex) {
     Logger.getLogger(getName()).log(Level.SEVERE, null, ex);
     }
     return intDiaSemAux;
     }    

     public String determinarHoraSuplementariaExtraordinarias(int intDiaSemSux){
 
     String strHorSupExtAux="";
     if(intDiaSemSux>=1 && intDiaSemSux<=5){
     strHorSupExtAux="S";
     }else{
     strHorSupExtAux="E";
     }
     return strHorSupExtAux;
    
     }*/
    
    private boolean configurarForm() 
    {
        boolean blnres = false;

        Vector vecCab = new Vector();    //Almacena las cabeceras
        vecCab.clear();

        vecCab.add(INT_TBL_LIN, "");
        vecCab.add(INT_TBL_CODUSR, "Cód.Usr.");
        vecCab.add(INT_TBL_USR, "Usuario");
        vecCab.add(INT_TBL_NOMUSR, "Nombre");
        vecCab.add(INT_TBL_CHKAUT, "Autorizar");
        vecCab.add(INT_TBL_CHKDEN, "Denegar");
        vecCab.add(INT_TBL_HORAUT, "Hor.Aut.");
        vecCab.add(INT_TBL_OBS, "Observación");
        vecCab.add(INT_TBL_BUTOBS, "...");
        vecCab.add(INT_TBL_COREG, "Cód. Reg.");
        vecCab.add(INT_TBL_NEJERAUT, "Jer. Aut.");
        vecCab.add(INT_TBL_STOBLSEGJERAUT, "Obl. Jer.");

        objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        //Configurar JTable: Establecer la fila de cabecera.
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LIN);

        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
        tblDat.getTableHeader().setReorderingAllowed(false);

        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CODUSR).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_USR).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_NOMUSR).setPreferredWidth(200);
        tcmAux.getColumn(INT_TBL_CHKAUT).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_CHKDEN).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_HORAUT).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_OBS).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_BUTOBS).setPreferredWidth(30);
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
        //Configurar JTable: Establecer columnas editables.
        Vector vecAux = new Vector();
        vecAux.add("" + INT_TBL_CHKAUT);
        vecAux.add("" + INT_TBL_CHKDEN);
        vecAux.add("" + INT_TBL_HORAUT);
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
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum30_01.this), true, strObs);
                    zafMae07_01.show();
                    if (zafMae07_01.getAceptar()) {
                        tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_OBS);
                    }
                }
            };

            objTblCelEdiTxt = new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_HORAUT).setCellEditor(objTblCelEdiTxt);
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
                            case INT_TBL_HORAUT:
                                strHorIng = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_HORAUT));
                                intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0, 2));
                                intMM = Integer.parseInt(strHorIng.replace(":", "").substring(2, 4));
                                break;
                        }

                        if ((intHH >= 0 && intHH <= 24)) {
                            if (!(intMM >= 0 && intMM <= 59)) {
                                {
                                    String strTit = "Mensaje del sistema Zafiro";
                                    String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                                    JOptionPane.showMessageDialog(ZafRecHum30_01.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                                    objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                                }
                            } else {

                                switch (tblDat.getSelectedColumn()) {

                                    case INT_TBL_HORAUT:
                                        String strHorEnt = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_HORAUT));
                                        if (strHorEnt.length() == 5) {
                                            strHorEnt = strHorEnt + ":00";
                                        }
                                        java.sql.Time t = SparseToTime(strHorEnt);
                                        break;

                                }
                            }
                        } else {

                            String strTit = "Mensaje del sistema Zafiro";
                            String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                            JOptionPane.showMessageDialog(ZafRecHum30_01.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                            objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                        }
                    } catch (Exception ex) {
                        String strTit = "Mensaje del sistema Zafiro";
                        String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                        JOptionPane.showMessageDialog(ZafRecHum30_01.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
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
                    //intPosRelColCodEmp=getPosRelColEspColSel(INT_TBL_DAT_CDI_COD_EMP);
                        //Validar que exista el cliente para la empresa especificada.
                        Object objDen = objTblMod.getValueAt(intFilSel, INT_TBL_CHKDEN);
                        boolean blnDen = (Boolean) objDen;

                        Object obj = objTblMod.getValueAt(intFilSel, INT_TBL_CHKAUT);
                        boolean blnAut = (Boolean) obj;
                    //String str=objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString();

                        /**
                         * ****************************************************************************************
                         */
                        if (blnDen && objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString().compareTo("B") == 0) {
                            objTblCelEdiChk.setCancelarEdicion(true);
                        } else {

                            if (blnAut && objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString().compareTo("B") == 0) {
                                objTblCelEdiChk.setCancelarEdicion(true);
                            }
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
                    tblDat.setValueAt(new Boolean(true), intFilSel, INT_TBL_CHKAUT);
                    tblDat.setValueAt(new Boolean(false), intFilSel, INT_TBL_CHKDEN);
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
                        //String str=objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString();
                        if (blnAut && objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString().compareTo("B") == 0) {
                            objTblCelEdiChk2.setCancelarEdicion(true);
                        } else {
                            if (blnDen && objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString().compareTo("B") == 0) {
                                objTblCelEdiChk2.setCancelarEdicion(true);
                            }
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
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblDat.getSelectedRow();
                    tblDat.setValueAt(new Boolean(false), intFil, INT_TBL_CHKAUT);
                    tblDat.setValueAt(new Boolean(true), intFil, INT_TBL_CHKDEN);
                }
            });

            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTOBS).setCellRenderer(zafTblDocCelRenBut);
            ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_BUTOBS, "Observación") {

                public void butCLick() {
                    int intSelFil = tblDat.getSelectedRow();
                    String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_OBS) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_OBS).toString());
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum30_01.this), true, strObs);

                    zafMae07_01.show();
                    if (zafMae07_01.getAceptar()) {

                        if (objParSis.getCodigoUsuario() == 1) {

                        }
                        if (objTblMod.getValueAt(intSelFil, INT_TBL_LIN).toString().compareTo("B") != 0) {
                            if (Integer.valueOf(tblDat.getValueAt(intSelFil, INT_TBL_CODUSR).toString()) == objParSis.getCodigoUsuario() || objParSis.getCodigoUsuario() == 1) {
                                tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_OBS);
                            }
                        }

                        /*if(objParSis.getCodigoUsuario()==1){
                    
                         }else{
                         if(Integer.valueOf(tblDat.getValueAt(intSelFil, INT_TBL_CODUSR).toString())!=objParSis.getCodigoUsuario()){
                         objTblCelEdiTxt.setCancelarEdicion(true);
                         }
                         }*/
                    }
                }
            };

            objTblCelEdiTxt = new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_HORAUT).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intPosRelColCodEmp;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1) {
                        String str = objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString();
                        if (objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString().compareTo("B") == 0) {
                            objTblCelEdiTxt.setCancelarEdicion(true);
                        }
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
                    //DateFormat sdf = new SimpleDateFormat("hh:mm");
                    String strHorIng = null;
                    //Date date = null;
                    try {

                        switch (tblDat.getSelectedColumn()) {
                            case INT_TBL_HORAUT:
                                strHorIng = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_HORAUT));
                                intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0, 2));
                                intMM = Integer.parseInt(strHorIng.replace(":", "").substring(2, 4));
                                break;
                        }

                        if ((intHH >= 0 && intHH <= 24)) {
                            if (!(intMM >= 0 && intMM <= 59)) {
                                {
                                    String strTit = "Mensaje del sistema Zafiro";
                                    String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                                    JOptionPane.showMessageDialog(ZafRecHum30_01.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                                    objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                                }
                            } else {

                                switch (tblDat.getSelectedColumn()) {

                                    case INT_TBL_HORAUT:
                                        String strHorEnt = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_HORAUT));
                                        if (strHorEnt.length() == 5) {
                                            strHorEnt = strHorEnt + ":00";
                                        }
                                        java.sql.Time t = SparseToTime(strHorEnt);
                                        break;

                                }
                            }
                        } else {

                            String strTit = "Mensaje del sistema Zafiro";
                            String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                            JOptionPane.showMessageDialog(ZafRecHum30_01.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                            objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                        }
                    } catch (Exception ex) {
                        String strTit = "Mensaje del sistema Zafiro";
                        String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                        JOptionPane.showMessageDialog(ZafRecHum30_01.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                        objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                    }
                }

            });

        }

        objTblCelRenChkLab = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_STOBLSEGJERAUT).setCellRenderer(objTblCelRenChkLab);
        ZafTblCelEdiChk objTblCelEdiChkST = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_STOBLSEGJERAUT).setCellEditor(objTblCelEdiChkST);

        //Configurar JTable: Editor de la tabla.
        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        return blnres;
    }

    public static java.sql.Time SparseToTime(String hora) {
        int h, m, s;

        h = Integer.parseInt(hora.charAt(0) + "" + hora.charAt(1));
        m = Integer.parseInt(hora.charAt(3) + "" + hora.charAt(4));
        s = Integer.parseInt(hora.charAt(6) + "" + hora.charAt(7));
        return (new java.sql.Time(h, m, s));
    }


    /*private class ButDoc extends Librerias.ZafTableColBut.ZafTableColBut_uni{
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
     }*/
    
    private void llamarVentana(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodLocRel, String strCodTipDocRel, String strCodDocRel) 
    {
        Compras.ZafCom19.ZafCom19 obj1 = new Compras.ZafCom19.ZafCom19(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, strCodLocRel, strCodTipDocRel, strCodDocRel, 1);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer2 = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
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

        lblTit.setText("Listado de Usuarios que autorizan");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

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
        jScrollPane1.setViewportView(tblDat);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Datos", jPanel1);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.getAccessibleContext().setAccessibleName("Datos");

        jPanel2.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar");
        butCon.setToolTipText("Cierra la ventana.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butGua.setText("Guardar");
        butGua.setToolTipText("Guarda los cambios realizados.");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butCer2.setText("Cerrar");
        butCer2.setToolTipText("Cierra la ventana.");
        butCer2.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCer2ActionPerformed(evt);
            }
        });
        panBot.add(butCer2);

        jPanel2.add(panBot, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-500)/2, (screenSize.height-300)/2, 500, 300);
    }// </editor-fold>//GEN-END:initComponents

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        boolean blnRes = false;
        java.sql.Connection conn;
        try 
        {
            //if(validaCampos()){
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) {
                conn.setAutoCommit(false);

                blnRes = guardarDat(conn);
                if (blnRes) {
                    conn.commit();
                    //blnRes=true;
                } else {
                    conn.rollback();
                }

                conn.close();
                conn = null;
                //blnRes=true;
            }
            //}
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        if (blnRes) {
            mostrarMsgInf("La operación GUARDAR se realizó con éxito");
        } else {
            mostrarMsgInf("La operación GUARDAR no se realizó correctamente");
        }
            //return blnRes;
}//GEN-LAST:event_butGuaActionPerformed

    public boolean guardarDat(java.sql.Connection conn) 
    {
        boolean blnRes = false;
        java.sql.Statement stmLoc, stmLocCab;
        java.sql.ResultSet resSet, resSetCab;
        String strSql = "";
        String strHoAutGLB = "";

        try
        {
            if (conn != null) {

                stmLoc = conn.createStatement();
                stmLocCab = conn.createStatement();
                int intCanUsrAut = tblDat.getRowCount();
                for (int intFil = 0; intFil < tblDat.getRowCount(); intFil++) {
                    System.out.println("linea " + intFil + "    :" + tblDat.getValueAt(intFil, INT_TBL_LIN).toString());
                    if (tblDat.getValueAt(intFil, INT_TBL_LIN).toString().compareTo("M") == 0) {

                        Object objHoAut = tblDat.getValueAt(intFil, INT_TBL_HORAUT);
                        String strHoAut = "";
                        if (objHoAut != null) {
                            strHoAut = objUti.codificar(objHoAut.toString());
                            strHoAutGLB = strHoAut;
                        } else {
                            if (objParSis.getCodigoUsuario() == Integer.parseInt(tblDat.getValueAt(intFil, INT_TBL_CODUSR).toString()) || objParSis.getCodigoUsuario() == 1) {
                                mostrarMsgInf("Debe ingresar las horas por autorizar");
                                return false;
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
                        if (tblDat.getValueAt(intFil, INT_TBL_CHKAUT).equals(true)) {
                            strAut = "'A'";
                        }
                        if (tblDat.getValueAt(intFil, INT_TBL_CHKDEN).equals(true)) {
                            strAut = "'D'";
                        }
                        if (tblDat.getValueAt(intFil, INT_TBL_CHKAUT).equals(false) && tblDat.getValueAt(intFil, INT_TBL_CHKDEN).equals(false)) {
                            strAut = null;
                        }

                        if (objParSis.getCodigoUsuario() == Integer.valueOf(tblDat.getValueAt(intFil, INT_TBL_CODUSR).toString()) || objParSis.getCodigoUsuario() == 1) {
                            strSql = "update tbm_autSolHorSupExt set st_aut=" + strAut + " ,ho_aut=" + strHoAut + " , tx_obsaut=" + strObs + ", "
                                    + "fe_aut=CURRENT_TIMESTAMP, co_usraut =" + objParSis.getCodigoUsuario() + " , tx_comaut = '" + objParSis.getDireccionIP() + "' "
                                    + "where co_emp=" + strCodEmp + " and co_loc=" + strCodLoc + " and co_tipdoc=" + strCodTipdoc + " and co_doc=" + strCodDoc + " and co_reg=" + tblDat.getValueAt(intFil, INT_TBL_COREG).toString();

                            System.out.println(strSql);
                            stmLoc.executeUpdate(strSql);

                            strSql = "update tbm_cabSolHorSupExt set ho_aut=" + strHoAut + " where co_emp=" + strCodEmp + " and co_loc=" + strCodLoc + " and co_tipdoc=" + strCodTipdoc
                                    + " and co_doc=" + strCodDoc;
                            stmLoc.executeUpdate(strSql);

                        }

                    }

                }

                strSql = "select * from tbm_autSolHorSupExt where co_emp=" + strCodEmp + " and co_loc=" + strCodLoc + " and co_tipdoc=" + strCodTipdoc
                        + " and co_doc=" + strCodDoc + " and st_aut like 'A'";
                resSet = stmLoc.executeQuery(strSql);

                int intCantAut = 0;
                while (resSet.next()) {
                    intCantAut++;
                }

                if (intCanUsrAut == intCantAut) {
                    strSql = "update tbm_cabSolHorSupExt set st_aut='T',ho_aut=" + strHoAutGLB + " where co_emp=" + strCodEmp + " and co_loc=" + strCodLoc + " and co_tipdoc=" + strCodTipdoc
                            + " and co_doc=" + strCodDoc;
                    stmLoc.executeUpdate(strSql);

                    strSql = "select co_tra from tbm_detsolhorsupext where co_emp=" + strCodEmp + " and co_loc=" + strCodLoc
                            + " and co_tipdoc=" + strCodTipdoc + " and co_doc=" + strCodDoc;//+" and co_reg="+tblDat.getValueAt(intFil, INT_TBL_COREG).toString();
                    resSet = stmLoc.executeQuery(strSql);

                    while (resSet.next()) {
                        String strCo_Tra = resSet.getString("co_tra");

                        strSql = "select * from tbm_cabconasitra where fe_dia = '" + strFecha + "' and co_tra = " + strCo_Tra;
                        resSetCab = stmLocCab.executeQuery(strSql);

                        if (resSetCab.next()) {
                            strSql = "update tbm_cabconasitra set st_autHorSupExt = 'S', ho_supExtAut = " + strHoAutGLB + " "
                                    + "where fe_dia = '" + strFecha + "' and co_tra = " + strCo_Tra;
                            stmLocCab.executeUpdate(strSql);
                        } else {
                            strSql = "insert into tbm_cabconasitra (co_tra,fe_dia,st_autHorSupExt,ho_supExtAut) values(" + strCo_Tra + ", '" + strFecha + "', "
                                    + "'S', " + strHoAutGLB + " )";
                            System.out.println(strSql);
                            stmLocCab.executeUpdate(strSql);
                        }
                    }

                    objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
                    butCon.setEnabled(false);
                    butGua.setEnabled(false);

                } else if (intCantAut >= 1) {
                    strSql = "update tbm_cabSolHorSupExt set st_aut='A' where co_emp=" + strCodEmp + " and co_loc=" + strCodLoc + " and co_tipdoc=" + strCodTipdoc
                            + " and co_doc=" + strCodDoc;

                    stmLoc.executeUpdate(strSql);
                }

                stmLoc.close();
                stmLoc = null;
                resSet.close();
                resSet = null;
                blnRes = true;
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

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     *
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarForm();
        cargarReg();
    }//GEN-LAST:event_formInternalFrameOpened

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }

    }//GEN-LAST:event_exitForm

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        cargarReg();
    }//GEN-LAST:event_butConActionPerformed

    private void butCer2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCer2ActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCer2ActionPerformed

    private boolean cargarReg()
    {
        java.sql.Connection con;
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

                strSQL = "select  a.*,b.co_usr,b.tx_usr,b.tx_nom, c.st_aut,c.ho_aut,c.tx_obsaut from tbm_usrAutHorSupExtDep a  "
                        + "inner join tbm_usr b on(a.co_usr=b.co_usr and b.st_reg like 'A' and a.tx_tiphoraut like a.tx_tiphoraut) "
                        + "inner join tbm_autSolHorSupExt c on (c.co_emp=" + strCodEmp + " AND c.co_loc=" + strCodLoc + "  AND c.co_tipdoc=" + strCodTipdoc + " AND c.co_doc=" + strCodDoc + " and c.co_reg=a.co_reg) "
                        + "where co_dep in( select co_dep from tbm_cabSolHorSupExt  "
                        + "WHERE co_emp=" + strCodEmp + " AND co_loc=" + strCodLoc + "  AND co_tipdoc=" + strCodTipdoc + " AND co_doc=" + strCodDoc + " and ne_numdoc= " + strNumDoc + ") "
                        + "order by ne_jeraut, co_reg";

                //Limpiar vector de datos.
                vecDat.clear();

                int intCantAut = 0;
                int intCantUsrAut = 0;
                System.out.println("query listado usuarios: " + strSQL);
                rst = stm.executeQuery(strSQL);
                boolean blq = false;

                while (rst.next()) {

                    vecReg = new Vector();

                    String strAut = rst.getString("st_aut");

                    if (strAut.compareTo("A") == 0) {
                        vecReg.add(INT_TBL_LIN, "B");//fila bloqueada
                    } else {
                        vecReg.add(INT_TBL_LIN, "");
                    }

                    vecReg.add(INT_TBL_CODUSR, rst.getString("co_usr"));
                    intCantUsrAut++;
                    vecReg.add(INT_TBL_USR, rst.getString("tx_usr"));
                    vecReg.add(INT_TBL_NOMUSR, rst.getString("tx_nom"));

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

                    vecReg.add(INT_TBL_HORAUT, rst.getString("ho_aut"));
                    vecReg.add(INT_TBL_OBS, rst.getString("tx_obsaut"));
                    vecReg.add(INT_TBL_BUTOBS, "...");
                    vecReg.add(INT_TBL_COREG, rst.getString("co_reg"));

                    vecReg.add(INT_TBL_NEJERAUT, rst.getString("ne_jeraut"));
                    //txtCodTipDoc.setText(((rstPrede.getString("co_tipdoc")==null)?"":rstPrede.getString("co_tipdoc")));

                    boolean bln;
                    if (rst.getString("st_oblsegjeraut").compareTo("N") == 0) {
                        bln = Boolean.FALSE;
                    } else {
                        bln = Boolean.TRUE;
                    }

                    vecReg.add(INT_TBL_STOBLSEGJERAUT, bln);

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

                if (intCantUsrAut == intCantAut) {
                    objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
                    butCon.setEnabled(false);
                    butGua.setEnabled(false);
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer2;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panTit;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

}

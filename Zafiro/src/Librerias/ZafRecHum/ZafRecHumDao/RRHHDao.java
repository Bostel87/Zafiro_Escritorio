/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafRecHum.ZafRecHumDao;
import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import RecursosHumanos.ZafRecHum37.ZafRecHum37;
import java.net.Socket;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sistemas2
 */
public class RRHHDao {

    private ZafUtil objUti;
    private ZafParSis objParSis;
    private String strVer=" v0.7";
    public RRHHDao(ZafUtil objUti, ZafParSis objParSis) {
        this.objUti = objUti;
        this.objParSis = objParSis;
    }

    public boolean generarBoM(Connection con, String strCoEmp, String strCoTra, double dobValBono, double dblPorVar, int intCoRub) {
        String[] arrVar = {"=", "-", "+", "-", "+", "-", "+", "-", "+", "-", "+", "-"};
        java.sql.Statement stmLoc = null;
        java.sql.Statement stmLocAux = null;
        java.sql.ResultSet rstLoc = null;
        String strFeIniCon = null;
        String strFeReaFinCon = null;
        boolean blnRes = false;
        String strSql = "";

        int intRecValMesOrgAux;
        try {
            if (con != null) {
                stmLoc = con.createStatement();
                stmLocAux = con.createStatement();
                int intArrDiasMes[] = new int[12];
                double dblValBonMes[] = new double[13];
                double dblValPrgBonMes[] = new double[13];
                double dblTotBon = 0;
                double dblTotBonPagado = 0;

                java.util.Calendar CalVal = java.util.Calendar.getInstance();
                intRecValMesOrgAux = CalVal.get(Calendar.MONTH) + 1;
                int intCon = 0;
                int intPeriodo = CalVal.get(Calendar.YEAR);
                CalVal.set(java.util.Calendar.MONTH, 12);
                intArrDiasMes[intCon++] = CalVal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);

                for (int intMes = 1; intMes <= 11; intMes++) {
                    CalVal = java.util.Calendar.getInstance();
                    CalVal.set(java.util.Calendar.YEAR, intPeriodo);
                    CalVal.set(java.util.Calendar.MONTH, intMes - 1);
                    intArrDiasMes[intCon++] = CalVal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
                }

                boolean blnTieBon = false;
                System.out.println("Cod_Tra : " + strCoTra);
                double dblBon = objUti.redondear(dobValBono, objParSis.getDecimalesMostrar());

                if (dblBon > 0) {
                    blnTieBon = true;
                }
                strSql = "";
                strSql += "select a.co_emp, a.co_tra,a.fe_inicon, a.fe_finperprucon,a.fe_fincon,a.st_perprucon,";
                strSql += " a.st_fincon,a.fe_reafincon from tbm_traemp a";
                strSql += " where a.co_tra=" + strCoTra;
                strSql += " and a.co_emp=" + strCoEmp;
                strSql += " and a.st_reg='A'";
                System.out.println("consulta reg sel: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
//                vecDat = new Vector();
                java.util.Vector vecReg = null;
                boolean blnAct = false;
                if (rstLoc.next()) {
                    strFeIniCon = rstLoc.getString("fe_inicon");
//                            strFeFinPerPruCon=rstLoc.getString("fe_finperprucon");
//                            strFeFinCon=rstLoc.getString("fe_fincon");
                    strFeReaFinCon = rstLoc.getString("fe_reafincon");
                    blnAct = true;
                }
                if (blnAct) {
                    int intAñoInicial = 0;
                    int intMesInicial = 0;
                    Calendar calAct = Calendar.getInstance();
                    int intMesAct = calAct.get(Calendar.MONTH) + 1;
                    //calAct.set(Calendar.YEAR, Integer.valueOf(jCmbAniPro.getSelectedItem().toString()) );
                    dblValBonMes = new double[13];
                    boolean blnCon = false;
                    if (strFeIniCon == null) {
                        for (int intRecAsg = 0; intRecAsg < dblValBonMes.length; intRecAsg++) {
                            dblValBonMes[intRecAsg] = dblBon;
                        }
                    } else {
                        String[] strArrFeIniCon = strFeIniCon.split("-");
                        intAñoInicial = Integer.valueOf(strArrFeIniCon[0]);
                        intMesInicial = Integer.valueOf(strArrFeIniCon[1]) -1 ;//David
                        int intDiaInicial = Integer.valueOf(strArrFeIniCon[2]);
                        int intCanDiasInicialesTrabajados = (30 - intDiaInicial) + 1;//(intArrDiasMes[intMesInicial - 1] - intDiaInicial) + 1;

                        int intFlgMesesPas = 0;
                        if (intAñoInicial >= calAct.get(Calendar.YEAR)) {
                            if (intMesInicial > 0) {
                                for (int i = 0; i < intMesInicial; i++) {
                                    dblValBonMes[i] = 0;
                                    intFlgMesesPas++;
                                }
                            }

                            for (int intRecAsg = intFlgMesesPas; intRecAsg < dblValBonMes.length; intRecAsg++) {
                                if (intRecAsg == intMesInicial ) {
                                    dblValBonMes[intRecAsg] = objUti.redondear(((dblBon / intArrDiasMes[intRecAsg]) * intCanDiasInicialesTrabajados), 2);
                                } else {
                                    dblValBonMes[intRecAsg] = dblBon;
                                }
                            }
                        } else {
                            for (int intRecAsg = intFlgMesesPas; intRecAsg < dblValBonMes.length; intRecAsg++) {
                                dblValBonMes[intRecAsg] = dblBon;
                            }
                        }
                        if (strFeReaFinCon == null) {
                        } else {
//                                caso: empleado sale de la empresa, no se valida, liquidacion de haberes = NULL
                            String[] strArrFeRealFinCon = strFeReaFinCon.split("-");
                            int intAñoFinal = Integer.valueOf(strArrFeRealFinCon[0]);
                            int intMesFinal = Integer.valueOf(strArrFeRealFinCon[1]);
                            int intDiaFinal = Integer.valueOf(strArrFeRealFinCon[2]);
                            int intCanDiasFinalTrabajados = intDiaFinal;
                            int intCanAñosTrabajados = intAñoFinal - intAñoInicial;
                            int intCanMesesTrabajados = intMesFinal - intMesInicial;
                            int intFlgMesFinTra = 0;
                            for (int intRecAsg = intFlgMesesPas; intRecAsg < dblValBonMes.length; intRecAsg++) {
                                if (intRecAsg == intMesFinal) {
//                                        SOLO SI SE CANCELA EL ULTIMO MES QUE EL EMPLEADO TRABAJO EN ROL PERO NO ES EL CASO = 0
//                                        dblValBonMes[intMesFinal]=  objUti.redondear ( ((dblBon/intArrDiasMes[intMesFinal])*intCanDiasFinalTrabajados) , 2 );
//                                        dblValMovMes[intMesFinal]=  objUti.redondear ( ((dblBon/intArrDiasMes[intMesFinal])*intCanDiasFinalTrabajados) , 2 );
                                    dblValBonMes[intMesFinal] = 0;
                                } else if (intRecAsg > intMesInicial) {
                                    dblValBonMes[intRecAsg] = 0;
                                }
                            }
                        }
                    }
                    dblValPrgBonMes = new double[13];
                    double dblResCalcBon = 0;
                    double dblResCalcMov = 0;
                    boolean blnMesAnt = false;
                    for (int intRecValMesOrg = intRecValMesOrgAux; intRecValMesOrg < dblValBonMes.length; intRecValMesOrg++) {
                        dblTotBon = 0;
                        if (intRecValMesOrg > 0) {
                            if (!blnMesAnt) {
                                int intCan = intRecValMesOrg;
                                dblTotBonPagado += dblValBonMes[0] * intCan;
                                blnMesAnt = true;
                            }
                            if ((dblTotBonPagado) == 0) {
                                blnCon = true;
                            }
                        }
                        if (blnCon) {
                            for (int intRec = 0; intRec < (intRecValMesOrg); intRec++) {
                                if (blnTieBon) {
                                    dblTotBonPagado += dblValBonMes[intRec];
                                }
                            }
                            blnCon = false;
                        }
                        /*
                         * UNA VEZ OBTENIDO LA SECUENCIA "Bono de acuerdo al mes"
                         * GENERAR LA PROGRAMACION DE LOS RUBROS
                         */
//                      suma de todo
                        for (int intRecValTotMes = 0; intRecValTotMes <= dblValBonMes.length - 1; intRecValTotMes++) {
                            dblTotBon += dblValBonMes[intRecValTotMes];
                        }
                        System.out.println("mes: " + intRecValMesOrg);
                        if (intRecValMesOrg == 0) {
                            dblValPrgBonMes[intRecValMesOrg] = dblValBonMes[intRecValMesOrg];
                            dblTotBonPagado += dblValBonMes[intRecValMesOrg];
                            blnRes = graActIngEgrMenProTra(con, strCoEmp, strCoTra, intCoRub, dblValBonMes[intRecValMesOrg], dblPorVar, dblValPrgBonMes[intRecValMesOrg], (calAct.get(Calendar.YEAR) - 1), 12);
                            if (!blnRes) {
                                break;
                            }
                        } else if (intRecValMesOrg == intMesAct) {
                            dblResCalcBon = dblValBonMes[intRecValMesOrg - 1];
                            double dblBonGen = (Math.random() * dblValBonMes[intRecValMesOrg - 1]) * dblPorVar / 100;
                            dblResCalcBon = dblResCalcBon + dblBonGen;/*CASO NEG*/

                            if (dblResCalcBon < 0) {
                                dblResCalcBon = 0;
                            }
                            dblValPrgBonMes[intRecValMesOrg] = objUti.redondear(dblResCalcBon, 2);
                            dblTotBonPagado += objUti.redondear(dblResCalcBon, 2);
                            blnRes = graActIngEgrMenProTra(con, strCoEmp, strCoTra, intCoRub, dblValBonMes[intRecValMesOrg], dblPorVar, dblValPrgBonMes[intRecValMesOrg], calAct.get(Calendar.YEAR), intRecValMesOrg);
                            if (!blnRes) {
                                break;
                            }
                        } else {
                            if (intRecValMesOrg == dblValPrgBonMes.length - 1) {
//                                 decimo tercer sueldo
                                dblValPrgBonMes[intRecValMesOrg] = objUti.redondear((dblTotBon - dblTotBonPagado), 2);
                                blnRes = graActIngEgrMenProTra(con, strCoEmp, strCoTra, intCoRub, dblValBonMes[intRecValMesOrg], dblPorVar, dblValPrgBonMes[intRecValMesOrg], calAct.get(Calendar.YEAR), 12);// Aqui antes estaba 13 no se sabe porqué. Se lo cambio a 12 ya que estaba dando dificultades al momento de calcular los valores de programacion BM. tony
                                if (!blnRes) {
                                    break;
                                }
                                /*....AQUI AL PARECER ESTA EL PROBLEMA....*/

                                dblTotBonPagado = 0;
                                blnMesAnt = false;
                            } else {
                                /*
                                 * LET
                                 */
                                System.out.println("la ultima vez que ingresa..." + intRecValMesOrg);
                                dblResCalcBon = dblValBonMes[intRecValMesOrg - 1];
                                if (arrVar[intRecValMesOrg].compareTo("+") == 0) {

                                    double dblBonGen = (Math.random() * dblValBonMes[intRecValMesOrg - 1]) * dblPorVar / 100;
                                    dblResCalcBon = dblResCalcBon + dblBonGen;/*CASO NEG*/

                                    if (dblResCalcBon < 0) {
                                        dblResCalcBon = 0;
                                    }
                                    dblValPrgBonMes[intRecValMesOrg] = objUti.redondear(dblResCalcBon, 2);
                                    dblTotBonPagado += objUti.redondear(dblResCalcBon, 2);
                                    blnRes = graActIngEgrMenProTra(con, strCoEmp, strCoTra, intCoRub, dblValBonMes[intRecValMesOrg], dblPorVar, dblValPrgBonMes[intRecValMesOrg], calAct.get(Calendar.YEAR), intRecValMesOrg);
                                    if (!blnRes) {
                                        break;
                                    }
                                } else {
                                    double dblBonGen = -1 * (Math.random() * dblValBonMes[intRecValMesOrg - 1]) * dblPorVar / 100;
                                    dblResCalcBon = dblResCalcBon + dblBonGen;
                                    if (dblResCalcBon < 0) {
                                        dblResCalcBon = 0;
                                    }
                                    dblValPrgBonMes[intRecValMesOrg] = objUti.redondear(dblResCalcBon, 2);
                                    dblTotBonPagado += objUti.redondear(dblResCalcBon, 2);
                                    blnRes = graActIngEgrMenProTra(con, strCoEmp, strCoTra, intCoRub, dblValBonMes[intRecValMesOrg], dblPorVar, dblValPrgBonMes[intRecValMesOrg], calAct.get(Calendar.YEAR), intRecValMesOrg);
                                    if (!blnRes) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                if (!ajustarProBonMov(con, strCoEmp, strCoTra, intCoRub, intPeriodo)) {
                    blnRes = false;
                } else {
                    blnRes = true;
                }
            }
        } catch (SQLException e) {
            blnRes = false;
            e.printStackTrace();
//            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
//            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public boolean grabarIngEgrMenProTra(Connection conex, String strCoEmp, String strCoTra, int intCoRub, double douValrub,
            double douPorvar, double douValrubprg, int intAnio, int intMes) {
        boolean resp = false;
        String strSql = "";
        strSql += "insert into tbm_ingegrmenprgtra(co_emp, co_tra, co_rub, ne_ani, ne_mes, nd_valrub, nd_porvar, nd_valrubprg) values ( ";
        strSql += strCoEmp + ", " + strCoTra + ", " + intCoRub + ", ";
        strSql += intAnio + ", " + intMes + ", " + douValrub + ", " + douPorvar + ", " + douValrubprg + " ) ";
        try {
            CallableStatement cs = conex.prepareCall(strSql);
            cs.execute();
            cs.close();
            resp = true;
        } catch (Exception e) {
            resp = false;
            e.printStackTrace();
        }
        return resp;
    }

    public boolean graActIngEgrMenProTra(Connection conex, String strCoEmp, String strCoTra, int intCoRub, double douValrub,
            double douPorvar, double douValrubprg, int intAnio, int intMes) {
        boolean resp = false;
        String strSql = "";
        strSql += "select * from tbm_ingEgrMenPrgTra where co_emp=" + strCoEmp;
        strSql += " and co_tra=" + strCoTra;
        strSql += " and co_rub=" + intCoRub;
        strSql += " and ne_ani=" + intAnio;
        strSql += " and ne_mes=" + intMes;
        try {
            CallableStatement cs = conex.prepareCall(strSql);
            ResultSet rs = cs.executeQuery();
            if (!rs.next()) {
                resp = grabarIngEgrMenProTra(conex, strCoEmp, strCoTra, intCoRub, douValrub, douPorvar, douValrubprg, intAnio, intMes);
            } else {
                resp = actualizarIngEgrMenProTra(conex, strCoEmp, strCoTra, intCoRub, douValrub, douPorvar, douValrubprg, intAnio, intMes);
            }
            rs.close();
            cs.close();
        } catch (Exception e) {
            resp = false;
            e.printStackTrace();
        }
        return resp;
    }

    public boolean actualizarIngEgrMenProTra(Connection conex, String strCoEmp, String strCoTra, int intCoRub, double douValrub,
            double douPorvar, double douValrubprg, int intAnio, int intMes) {
        System.out.println("update tbm_ingegrmenprgtra set nd_valrub = " + douValrub + " , nd_porvar = " + douPorvar + ", nd_valrubprg = " + douValrubprg);
        boolean resp = false;
        String strSql = "";
        strSql += "update tbm_ingegrmenprgtra set nd_valrub = " + douValrub + " , nd_porvar = " + douPorvar + ", nd_valrubprg = " + douValrubprg;
        strSql += " where co_emp=" + strCoEmp;
        strSql += " and co_tra=" + strCoTra;
        strSql += " and co_rub=" + intCoRub;
        strSql += " and ne_ani=" + intAnio;
        strSql += " and ne_mes=" + intMes;
        try {
            CallableStatement cs = conex.prepareCall(strSql);
            cs.execute();
            cs.close();
            resp = true;
        } catch (Exception e) {
            resp = false;
            e.printStackTrace();
        }
        return resp;
    }

    private boolean ajustarProBonMov(Connection conex, String strCoEmp, String strCoTra, int intCoRub, int intAnio) {
        boolean resp = true;
        String strSql = "select a.*, sum_nd_valrub - sum_nd_valrubprg as valorAjuste from (";
        strSql += "select co_emp, co_tra, co_rub, ne_ani, count(1), sum(nd_valrub) as sum_nd_valrub, sum(nd_valrubprg) as sum_nd_valrubprg from tbm_ingegrmenprgtra  \n"
                + "where co_emp=" + strCoEmp + " and co_tra=" + strCoTra + " and ne_ani=" + intAnio + " and co_rub=" + intCoRub + " group by co_emp, co_tra, co_rub, ne_ani ) a";
        try {
            CallableStatement cs = conex.prepareCall(strSql);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                double valAjuste = rs.getDouble("valorAjuste");
                valAjuste = objUti.redondear(valAjuste, objParSis.getDecimalesMostrar());
                if (valAjuste != 0) {
                    strSql = "select co_emp, co_tra, co_rub, ne_ani, nd_valrub, nd_valrubprg, ne_mes, nd_porvar from tbm_ingegrmenprgtra \n"
                            + " where co_emp = " + strCoEmp + " and co_tra = " + strCoTra + " and ne_ani =" + intAnio + " and co_rub = " + intCoRub + " order by ne_mes desc";
                    cs = conex.prepareCall(strSql);
                    ResultSet rs2 = cs.executeQuery();
                    if (rs2.next()) {
                        if (!actualizarIngEgrMenProTra(conex, strCoEmp, strCoTra, intCoRub, rs2.getDouble("nd_valrub"), rs2.getDouble("nd_porvar"),
                                rs2.getDouble("nd_valrubprg") + valAjuste, intAnio, rs2.getInt("ne_mes"))) {
                            resp = false;
                        }
                    }
                    rs2.close();
                }
            }
            rs.close();
            cs.close();
        } catch (Exception e) {
            resp = false;
            e.printStackTrace();
        }
        return resp;
    }
    
    /**
    * Retorna un String con los datos de la ultima quincena generada
    * <br/>
    * @param conex Objeto Connection
    * @param intCoEmp Codigo de la empresa
    * @param booDocGene Si es True verifica la aprobacion de las faltas y este generado el documento<br />
    * Caso contrario solo verifica la aprobacion de las faltas.
    * @return La cadena tiene el siguiente formato:  Anio;mes;periodo
    */
   public String conAnioUltimoRolGenerado( Connection conex, int intCoEmp, boolean  booDocGene){
       String resp = "";
       int intAnio=0;
       String sqlAux = "";
       if(booDocGene){
           sqlAux = " and co_tipdocrelrolpag is not null" ;
       }
      try {
          CallableStatement cs = conex.prepareCall("select max(ne_ani) from tbm_feccorrolpag where co_emp="+intCoEmp+" and st_revfal = 'S'" +  sqlAux);
          ResultSet rs = cs.executeQuery();
          if(rs.next()){
              intAnio=  rs.getInt(1);
              cs = conex.prepareCall("select max(ne_mes) from tbm_feccorrolpag where co_emp="+intCoEmp+" and st_revfal = 'S' and ne_ani =" + intAnio + sqlAux);
              ResultSet rs2 = cs.executeQuery();
              if(rs2.next()){
                  int intMes = rs2.getInt(1);
                  cs = conex.prepareCall("select max(ne_per) from tbm_feccorrolpag where co_emp="+intCoEmp+" and st_revfal = 'S' and ne_ani="+intAnio+"and ne_mes="+intMes+ sqlAux);
                  ResultSet rs3 = cs.executeQuery();
                  if(rs3.next()){
                      resp = intAnio + ";" + intMes + ";" + rs3.getInt(1) ;
                  }
              }
          }
      } catch (SQLException ex) {
          ex.printStackTrace();
          Logger.getLogger(ZafRecHum37.class.getName()).log(Level.SEVERE, null, ex);
      }
      return resp;
   }

    public void callServicio9() {
        try {
            //Socket clientSocket = new Socket("172.16.8.2", 6007);
	
			System.out.println("Servicio 9");
           // clientSocket.close();
        } catch (Exception e) {
            ZafCorEle objCorEle = new ZafCorEle(objParSis);
            objCorEle.enviarCorreoMasivo("sistemas@tuvalsa.com;sistemas1@tuvalsa.com;sistemas2@tuvalsa.com;", "Error Call Servicio9", (getClass() +  e.getMessage()) );
            e.printStackTrace();
        }
    }
}

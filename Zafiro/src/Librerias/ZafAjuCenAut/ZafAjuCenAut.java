/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafAjuCenAut;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jayapata Mail: jaya_gar@hotmail.com
 *
 * @Version: 0.4
 */
public class ZafAjuCenAut {

    Librerias.ZafParSis.ZafParSis objZafParSis;
    Librerias.ZafUtil.ZafUtil objUti;
    javax.swing.JInternalFrame jIntfra = null;
    javax.swing.JDialog jDialo = null;
    private java.util.ArrayList arlRegAniMes, arlDatAniMes;

    final int INT_ARL_ANI_CIE = 0;
    final int INT_ARL_MES_CIE = 1;
    final int INT_ARL_TIP_CIE = 2;

    /**
     * Creates a new instance of ZafAjuCenAut
     */
    public ZafAjuCenAut(javax.swing.JInternalFrame jfrthis, Librerias.ZafParSis.ZafParSis obj) {
        try {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jIntfra = jfrthis;
            objUti = new Librerias.ZafUtil.ZafUtil();
            arlDatAniMes = new java.util.ArrayList();

        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(jfrthis, e);
        }
    }

    /**
     * Permite realizar el ajuste de centavos automatico
     *
     * @param conn coneccion de la base
     * @param intCodEmpRec codigo de empresa cabpag
     * @param intCodLocRec codigo de local cabpag
     * @param intCodTipDocRec codigo tipo documento cabpag
     * @param intCodDocRec codigo documento cabpag
     * @param intTipDocAjuCenAut codigo tipo documento del ajuste a realizar
     * @param fechaAjuCenAutRec fecha de ajuste
     * @return true si se realizo con exito false algun error
     */
    public boolean realizaAjuCenAut(java.sql.Connection conn, int intCodEmpRec, int intCodLocRec, int intCodTipDocRec, int intCodDocRec, int intTipDocAjuCenAut, java.util.Date fechaAjuCenAutRec) {
        boolean blnRes = true;
        java.sql.Statement stmLoc, stmLoc01;
        java.sql.ResultSet rstLoc, rstLoc01;
        String strSql = "";
        int intCodDocAjuCen = 0;
        int intNumDocAjuCen = 0;
        try {
            stmLoc = conn.createStatement();
            stmLoc01 = conn.createStatement();

            strSql = "SELECT codoc, sum(dif) as valaju FROM ( "
                    + "  select * from (  SELECT  a.co_doc as codoc, a1.co_doc, a1.co_reg, (abs(a1.mo_pag)-a1.nd_abo )  as dif "
                    + "  FROM tbm_detpag as a "
                    + "  INNER JOIN tbm_pagmovinv as a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag ) "
                    + "  INNER JOIN tbm_emp as a2 ON (a2.co_emp=a1.co_emp) "
                    + "  WHERE a.co_emp=" + intCodEmpRec + " AND a.co_loc=" + intCodLocRec + " AND a.co_tipdoc=" + intCodTipDocRec + "  AND a.co_doc=" + intCodDocRec + " "
                    + "  AND a1.nd_abo > 0  AND a1.st_reg in ('A','C')  AND (a1.mo_pag+a1.nd_abo) != 0  AND (abs(a1.mo_pag)-a1.nd_abo )   BETWEEN  a2.nd_valminajucenaut AND  a2.nd_valmaxajucenaut"
                    + "  ) as x group by codoc, co_doc, co_reg, dif ) AS x GROUP BY codoc  ";
            System.out.println("--> " + strSql);
            rstLoc = stmLoc.executeQuery(strSql);
            if (rstLoc.next()) {

                strSql = "SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as codoc  FROM tbm_cabpag WHERE "
                        + " co_emp=" + intCodEmpRec + " AND co_loc=" + intCodLocRec + " AND co_tipdoc=" + intTipDocAjuCenAut;
                rstLoc01 = stmLoc01.executeQuery(strSql);
                if (rstLoc01.next()) {
                    intCodDocAjuCen = rstLoc01.getInt("codoc");
                }
                rstLoc01.close();
                rstLoc01 = null;

                strSql = "SELECT case when (Max(ne_ultdoc)+1) is null then 1 else Max(ne_ultdoc)+1 end as neultdoc  FROM tbm_cabtipdoc WHERE "
                        + " co_emp=" + intCodEmpRec + " AND co_loc=" + intCodLocRec + " AND co_tipdoc=" + intTipDocAjuCenAut;
                rstLoc01 = stmLoc01.executeQuery(strSql);
                if (rstLoc01.next()) {
                    intNumDocAjuCen = rstLoc01.getInt("neultdoc");
                }
                rstLoc01.close();
                rstLoc01 = null;

                String strMes = "";
                int intMes = fechaAjuCenAutRec.getMonth() + 1;
                if (intMes < 10) {
                    strMes = "0" + String.valueOf(intMes);
                } else {
                    strMes = String.valueOf(intMes);
                }

                String strPer = String.valueOf((fechaAjuCenAutRec.getYear() + 1900)) + strMes;
                int intCodPer = Integer.parseInt(strPer);

                if (insertarCab(conn, intCodEmpRec, intCodLocRec, intTipDocAjuCenAut, intCodDocAjuCen, intNumDocAjuCen, rstLoc.getString("valaju"), fechaAjuCenAutRec)) {
                    if (insertarDet(conn, intCodEmpRec, intCodLocRec, intCodTipDocRec, intCodDocRec, intTipDocAjuCenAut, intCodDocAjuCen)) {
                        if (actualizarPagMovInv(conn, intCodEmpRec, intCodLocRec, intCodTipDocRec, intCodDocRec)) {
                            if (insertarCabDia(conn, intCodEmpRec, intCodLocRec, intTipDocAjuCenAut, intCodDocAjuCen, intNumDocAjuCen, fechaAjuCenAutRec)) {
                                if (insertarDetDia(conn, intCodEmpRec, intCodLocRec, intTipDocAjuCenAut, intCodDocAjuCen, rstLoc.getDouble("valaju"), intCodPer)) {
                                    blnRes = true;
                                } else {
                                    blnRes = false;
                                }
                            } else {
                                blnRes = false;
                            }
                        } else {
                            blnRes = false;
                        }
                    } else {
                        blnRes = false;
                    }
                } else {
                    blnRes = false;
                }

            }
            //corrigeDescuadrePorCtvs(conn, intCodEmpRec, intCodLocRec, intTipDocAjuCenAut);
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
            stmLoc01.close();
            stmLoc01 = null;
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            mostarErrorSqlException(Evt);
        } catch (Exception evt) {
            blnRes = false;
            mostarErrorException(evt);
        }
        return blnRes;
    }

    /**
     * permite insertar la cabdia de ajuste
     *
     * @param conn coneccion de la base
     * @param intCodEmpRec codigo de empresa cabpag
     * @param intCodLocRec codigo de local cabpag
     * @param intTipDocAjuCenAut codigo tipo documento del ajuste a realizar
     * @param intCodDocAjuCen codigo documento del ajuste
     * @param intNumDoc numero de documento del ajuste
     * @param strValDoc valor del ajuste
     * @param fechaAjuCenAut fecha del ajuste
     * @return true si se realizo con exito false algun error
     */
    private boolean insertarCab(java.sql.Connection conn, int intCodEmpRec, int intCodLocRec, int intTipDocAjuCenAut, int intCodDocAjuCen, int intNumDoc, String strValDoc, java.util.Date fechaAjuCenAut) {
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        String strSql = "";
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();

                strSql = "INSERT INTO tbm_cabpag(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc1, ne_numdoc2  "
                        + " ,nd_mondoc, st_reg, fe_ing, co_usring, tx_coming, co_mnu, st_regrep ) "
                        + " VALUES(" + intCodEmpRec + ", " + intCodLocRec + ", " + intTipDocAjuCenAut + ", " + intCodDocAjuCen + " "
                        + " ,'" + objUti.formatearFecha(fechaAjuCenAut, objZafParSis.getFormatoFechaBaseDatos()) + "', " + intNumDoc + ","
                        + " " + intNumDoc + ", " + strValDoc + ", 'A', " + objZafParSis.getFuncionFechaHoraBaseDatos() + "," + objZafParSis.getCodigoUsuario() + " "
                        + " ,'" + objZafParSis.getNombreComputadoraConDirIP() + "', " + objZafParSis.getCodigoMenu() + ", 'I' ) ; ";

                strSql += " UPDATE tbm_cabtipdoc SET ne_ultdoc=" + intNumDoc + " WHERE co_emp=" + intCodEmpRec + " "
                        + " AND co_loc=" + intCodLocRec + " AND co_tipdoc=" + intTipDocAjuCenAut;

                stmLoc.executeUpdate(strSql);

                stmLoc.close();
                stmLoc = null;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            mostarErrorSqlException(Evt);
        } catch (Exception Evt) {
            blnRes = false;
            mostarErrorException(Evt);
        }
        return blnRes;
    }

    /**
     * permite insertar la detdia de ajuste
     *
     * @param conn coneccion de la base
     * @param intCodEmpRec codigo de empresa cabpag
     * @param intCodLocRec codigo de local cabpag
     * @param intCodTipDocRec codigo tipo documento cabpag
     * @param intCodDocRec codigo documento cabpag
     * @param intTipDocAjuCenAut codigo tipo documento del ajuste a realizar
     * @param intCodDocAjuCen codigo documento del ajuste
     * @return true si se realizo con exito false algun error
     */
    private boolean insertarDet(java.sql.Connection conn, int intCodEmpRec, int intCodLocRec, int intCodTipDocRec, int intCodDocRec, int intTipDocAjuCenAut, int intCodDocAjuCen) {
        boolean blnRes = false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        String strSqlDet = "";
        int intNumRegAjuCenAut = 0;
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();

                strSql = "select * from ( SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.mo_pag, a1.nd_abo, (abs(a1.mo_pag)-a1.nd_abo ) as dif "
                        + " FROM tbm_detpag as a "
                        + " INNER JOIN tbm_pagmovinv as a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag ) "
                        + " INNER JOIN tbm_emp as a2 ON (a2.co_emp=a1.co_emp) "
                        + " WHERE a.co_emp=" + intCodEmpRec + " AND a.co_loc=" + intCodLocRec + " AND a.co_tipdoc=" + intCodTipDocRec + "  AND a.co_doc=" + intCodDocRec + " "
                        + " AND a1.nd_abo > 0  AND a1.st_reg in ('A','C')  AND (a1.mo_pag+a1.nd_abo) != 0  AND (abs(a1.mo_pag)-a1.nd_abo )  BETWEEN  a2.nd_valminajucenaut AND  a2.nd_valmaxajucenaut "
                        + " ) as x group by co_emp, co_loc, co_tipdoc, co_doc, co_reg, mo_pag, nd_abo, dif  ";
                rstLoc = stmLoc.executeQuery(strSql);
                while (rstLoc.next()) {

                    intNumRegAjuCenAut++;
                    strSqlDet += " INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, co_docpag, co_regpag, nd_abo, st_regrep )"
                            + " SELECT " + intCodEmpRec + ", " + intCodLocRec + ", " + intTipDocAjuCenAut + ", " + intCodDocAjuCen + ", " + intNumRegAjuCenAut + " "
                            + " , " + rstLoc.getInt("co_loc") + ", " + rstLoc.getInt("co_tipdoc") + ", " + rstLoc.getInt("co_doc") + ", " + rstLoc.getInt("co_reg") + " "
                            + " , " + rstLoc.getString("dif") + ", 'I'   ;  ";
                }
                rstLoc.close();
                rstLoc = null;

                if (!(strSqlDet.equals(""))) {
                    stmLoc.executeUpdate(strSqlDet);
                    blnRes = true;
                }
                stmLoc.close();
                stmLoc = null;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            mostarErrorSqlException(Evt);
        } catch (Exception Evt) {
            blnRes = false;
            mostarErrorException(Evt);
        }
        return blnRes;
    }

    /**
     * Permite realizar la actualizacion en tbm_pagmovinv correspondiente a cada
     * registro de factura
     *
     * @param conn coneccion de la base
     * @param intCodEmpRec codigo de empresa cabpag
     * @param intCodLocRec codigo de local cabpag
     * @param intCodTipDocRec codigo tipo documento cabpag
     * @param intCodDocRec codigo documento cabpag
     * @return true si se realizo con exito false algun error
     */
    private boolean actualizarPagMovInv(java.sql.Connection conn, int intCodEmpRec, int intCodLocRec, int intCodTipDocRec, int intCodDocRec) {
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        String strSql = "";
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();

                strSql = "UPDATE tbm_pagmovinv SET nd_abo=nd_abo+x.dif, st_regrep='M' FROM ( "
                        + "   select * from ( SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, (abs(a1.mo_pag)-a1.nd_abo ) as dif "
                        + "   FROM tbm_detpag as a "
                        + "   INNER JOIN tbm_pagmovinv as a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag  ) "
                        + "   INNER JOIN tbm_emp as a2 ON (a2.co_emp=a1.co_emp) "
                        + "   WHERE a.co_emp=" + intCodEmpRec + " AND a.co_loc=" + intCodLocRec + " AND a.co_tipdoc=" + intCodTipDocRec + "  AND a.co_doc=" + intCodDocRec + " "
                        + "   AND a1.nd_abo > 0  AND a1.st_reg in ('A','C')  AND (a1.mo_pag+a1.nd_abo) != 0  AND (abs(a1.mo_pag)-a1.nd_abo )  BETWEEN  a2.nd_valminajucenaut AND  a2.nd_valmaxajucenaut "
                        + " ) as x group by co_emp, co_loc, co_tipdoc, co_doc, co_reg, dif  "
                        + " ) AS x WHERE tbm_pagmovinv.co_emp=x.co_emp and tbm_pagmovinv.co_loc=x.co_loc and tbm_pagmovinv.co_tipdoc=x.co_tipdoc and "
                        + " tbm_pagmovinv.co_doc=x.co_doc and tbm_pagmovinv.co_reg=x.co_reg ";
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc = null;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            mostarErrorSqlException(Evt);
        } catch (Exception Evt) {
            blnRes = false;
            mostarErrorException(Evt);
        }
        return blnRes;
    }

    /**
     * Permite realizar la insercion de tbm_cabdia
     *
     * @param conn coneccion de la base
     * @param intCodEmpRec codigo de empresa cabpag
     * @param intCodLocRec codigo de local cabpag
     * @param intTipDocAjuCenAut codigo tipo documento del ajuste a realizar
     * @param intCodDocAjuCen codigo documento del ajuste
     * @param intNumDoc numero de documento del ajuste
     * @param fechaAjuCenAut fecha del ajuste
     * @return true si se realizo con exito false algun error
     */
    private boolean insertarCabDia(java.sql.Connection conn, int intCodEmpRec, int intCodLocRec, int intTipDocAjuCenAut, int intCodDocAjuCen, int intNumDoc, java.util.Date fechaAjuCenAut) {
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        String strSql = "";
        try {
            if (conn != null) {

                /**
                 * *****************************************************************************************
                 */
                int intArlAni = 0;
                int intArlMes = 0;
                String strArlTipCie = "";
                int intRefAniNew = 0;
                int intRefMesNew = 0;
                intRefAniNew = (fechaAjuCenAut.getYear() + 1900);
                intRefMesNew = (fechaAjuCenAut.getMonth() + 1);

                //SI EL Aï¿½O NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
                if (!(objZafParSis.isAnioDocumentoCreadoSistema(intRefAniNew))) {
                    mostrarMsg("<HTML>El documento ajuste no puede ser grabado en el año<FONT COLOR=\"blue\"> " + intRefAniNew + " </FONT> debido a que dicho año todavía no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
                    return false;
                }
                //ESTE CODIGO ES NUEVO, Y PERMITE VALIDAR Q NO SE INGRESEN DIARIOS CON CIERRES MENSUALES O ANUALES
                if (!(retAniMesCie(conn, intRefAniNew))) {
                    return false;
                }
                for (int k = 0; k < arlDatAniMes.size(); k++) {
                    intArlAni = objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
                    intArlMes = objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
                    strArlTipCie = (objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE) == null ? "" : objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));
                    if ((strArlTipCie.toString().equals("M"))) {
                        if (intRefAniNew == intArlAni) {
                            if (intRefMesNew == intArlMes) {
                                mostrarMsg("<HTML>El mes que desea ingresar está cerrado. <BR>Está tratando de INSERTAR un documento ajuste en un periodo cerrado. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                                return false;
                            }
                        }
                    } else {
                        mostrarMsg("<HTML>La fecha del documento ejuste es incorrecta. <BR>Está tratando de INSERTAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                        return false;
                    }
                }
                /**
                 * *****************************************************************************************
                 */
                stmLoc = conn.createStatement();

                strSql = "INSERT INTO tbm_cabdia(co_emp, co_loc, co_tipdoc, co_dia, fe_dia, tx_numdia, st_reg, fe_ing, co_usring, st_regrep, st_imp ) "
                        + " VALUES(" + intCodEmpRec + ", " + intCodLocRec + ", " + intTipDocAjuCenAut + ", " + intCodDocAjuCen + " "
                        + " ,'" + objUti.formatearFecha(fechaAjuCenAut, objZafParSis.getFormatoFechaBaseDatos()) + "', '" + intNumDoc + "',"
                        + " 'A', " + objZafParSis.getFuncionFechaHoraBaseDatos() + "," + objZafParSis.getCodigoUsuario() + ", 'I', 'N' ) ; ";

                stmLoc.executeUpdate(strSql);

                stmLoc.close();
                stmLoc = null;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            mostarErrorSqlException(Evt);
        } catch (Exception Evt) {
            blnRes = false;
            mostarErrorException(Evt);
        }
        return blnRes;
    }

    private boolean insertarDetDia(java.sql.Connection conn, int intCodEmpRec, int intCodLocRec, int intTipDocAjuCenAut, int intCodDocAjuCen, double dblrValDoc, int intCodPer) {
        boolean blnRes = false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "", strSqlDet = "";
        int intNumRegDetDia = 0;
        double dblValDebCli = 0;
        double dblValHabCli = 0;
        double dblValDebAju = 0;
        double dblValHabAju = 0;
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();

                if (dblrValDoc < 0) {
                    dblValDebCli = dblrValDoc;
                    dblValHabCli = 0;
                    dblValDebAju = 0;
                    dblValHabAju = dblrValDoc;
                } else {
                    dblValDebCli = 0;
                    dblValHabCli = dblrValDoc;
                    dblValDebAju = dblrValDoc;
                    dblValHabAju = 0;
                }

                strSql = "SELECT co_ctadeb, co_ctahab FROM tbm_cabtipdoc WHERE co_emp=" + intCodEmpRec + " AND co_loc=" + intCodLocRec + " AND co_tipdoc=" + intTipDocAjuCenAut + " ";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {

                    // CUENTA DE CLIENTE
                    intNumRegDetDia++;
                    strSqlDet += " INSERT INTO tbm_detdia(co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, nd_monhab, st_regrep ) "
                            + " VALUES(" + intCodEmpRec + ", " + intCodLocRec + ", " + intTipDocAjuCenAut + ", " + intCodDocAjuCen + ", " + intNumRegDetDia + ", "
                            + " " + rstLoc.getInt("co_ctahab") + ", abs(" + dblValDebCli + "), abs(" + dblValHabCli + "), 'I' ) ; ";

                    // REALIZA MAYORIZACION CUENTA DE CLIENTE
                    /*COMENTADO EL 07 FEB 2019 POR TRIGGER QUE MAYORIZA*/
                    /*strSqlDet += " UPDATE tbm_salcta SET nd_salcta=x.saldo FROM ( "
                            + " 	SELECT co_emp, co_cta, co_per, case when nd_salcta is null then  (abs(" + dblValDebCli + ")-(" + dblValHabCli + ")) "
                            + " 	else  nd_salcta+ (abs(" + dblValDebCli + ")-(" + dblValHabCli + ")) end as saldo "
                            + " 	from tbm_salcta WHERE co_emp=" + intCodEmpRec + " "
                            + "    and co_cta=" + rstLoc.getInt("co_ctahab") + "  and co_per=" + intCodPer + " "
                            + " ) as x "
                            + " where tbm_salcta.co_emp=x.co_emp and tbm_salcta.co_cta=x.co_cta "
                            + " and tbm_salcta.co_per=x.co_per "
                            + " and tbm_salcta.co_emp=" + intCodEmpRec + " and tbm_salcta.co_cta=" + rstLoc.getInt("co_ctahab") + " "
                            + " and tbm_salcta.co_per=" + intCodPer + " ; ";*/
                    /*COMENTADO EL 07 FEB 2019 POR TRIGGER QUE MAYORIZA*/
                    // CUENTA DE AJUSTE
                    intNumRegDetDia++;
                    strSqlDet += " INSERT INTO tbm_detdia(co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, nd_monhab, st_regrep ) "
                            + " VALUES(" + intCodEmpRec + ", " + intCodLocRec + ", " + intTipDocAjuCenAut + ", " + intCodDocAjuCen + ", " + intNumRegDetDia + ", "
                            + " " + rstLoc.getInt("co_ctadeb") + ", abs(" + dblValDebAju + "), abs(" + dblValHabAju + "), 'I' ) ; ";

                    // REALIZA MAYORIZACION CUENTA DE AJUSTE
                    /*COMENTADO EL 07 FEB 2019 POR TRIGGER QUE MAYORIZA*/
                    /*
                    strSqlDet += " UPDATE tbm_salcta SET nd_salcta=x.saldo FROM ( "
                            + " 	SELECT co_emp, co_cta, co_per, case when nd_salcta is null then  (abs(" + dblValDebAju + ")-(" + dblValHabAju + ")) "
                            + " 	else  nd_salcta+ (abs(" + dblValDebAju + ")-(" + dblValHabAju + ")) end as saldo "
                            + " 	from tbm_salcta WHERE co_emp=" + intCodEmpRec + " "
                            + "    and co_cta=" + rstLoc.getInt("co_ctadeb") + "  and co_per=" + intCodPer + " "
                            + " ) as x "
                            + " where tbm_salcta.co_emp=x.co_emp and tbm_salcta.co_cta=x.co_cta "
                            + " and tbm_salcta.co_per=x.co_per "
                            + " and tbm_salcta.co_emp=" + intCodEmpRec + " and tbm_salcta.co_cta=" + rstLoc.getInt("co_ctadeb") + " "
                            + " and tbm_salcta.co_per=" + intCodPer + " ; ";
                    */
                    /*COMENTADO EL 07 FEB 2019 POR TRIGGER QUE MAYORIZA*/

                }
                rstLoc.close();
                rstLoc = null;

                if (!(strSqlDet.equals(""))) {
                    stmLoc.executeUpdate(strSqlDet);
                    blnRes = true;
                }

                stmLoc.close();
                stmLoc = null;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            mostarErrorSqlException(Evt);
        } catch (Exception Evt) {
            blnRes = false;
            mostarErrorException(Evt);
        }
        return blnRes;
    }

    private boolean retAniMesCie(java.sql.Connection conn, int anio) {
        boolean blnRes = true;
        arlDatAniMes.clear();
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSQL = "";
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();
                strSQL = "";
                strSQL += "select a1.ne_ani, a2.ne_mes, a1.tx_tipCie";
                strSQL += " from tbm_cabciesis as a1 left outer join tbm_detciesis as a2";
                strSQL += " on a1.co_emp=a2.co_emp and a1.ne_ani=a2.ne_ani";
                strSQL += " where a1.co_emp=" + objZafParSis.getCodigoEmpresa() + "";
                strSQL += " and a1.ne_ani=" + anio + "";
                rstLoc = stmLoc.executeQuery(strSQL);
                while (rstLoc.next()) {
                    arlRegAniMes = new java.util.ArrayList();
                    arlRegAniMes.add(INT_ARL_ANI_CIE, "" + rstLoc.getInt("ne_ani"));
                    arlRegAniMes.add(INT_ARL_MES_CIE, "" + rstLoc.getInt("ne_mes"));
                    arlRegAniMes.add(INT_ARL_TIP_CIE, "" + rstLoc.getString("tx_tipCie"));
                    arlDatAniMes.add(arlRegAniMes);
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            mostarErrorSqlException(Evt);
        } catch (Exception Evt) {
            blnRes = false;
            mostarErrorException(Evt);
        }
        return blnRes;
    }

    public void mostrarMsg(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (jDialo == null) {
            oppMsg.showMessageDialog(jIntfra, strMsg, strTit, javax.swing.JOptionPane.OK_OPTION);
        } else {
            oppMsg.showMessageDialog(jDialo, strMsg, strTit, javax.swing.JOptionPane.OK_OPTION);
        }
    }

    private void mostarErrorException(Exception evt) {
        if (jDialo == null) {
            objUti.mostrarMsgErr_F1(jIntfra, evt);
        } else {
            objUti.mostrarMsgErr_F1(jDialo, evt);
        }
    }

    private void mostarErrorSqlException(java.sql.SQLException evt) {
        if (jDialo == null) {
            objUti.mostrarMsgErr_F1(jIntfra, evt);
        } else {
            objUti.mostrarMsgErr_F1(jDialo, evt);
        }
    }

    private void corrigeDescuadrePorCtvs(java.sql.Connection conn, int intCodEmpRec, int intCodLocRec, int intTipDocAjuCenAut) {
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc2;
        java.sql.ResultSet rstLoc2 = null;
        String strSql = "", strSqlDet = "", strSql2 = "";

        try {
            if (conn != null) {
                stmLoc = conn.createStatement();
                stmLoc2 = conn.createStatement();
                strSql = "SELECT co_ctahab FROM tbm_cabtipdoc WHERE co_emp=" + intCodEmpRec + " AND co_loc=" + intCodLocRec + " AND co_tipdoc=" + intTipDocAjuCenAut + " ";
                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next()) {
                    strSql2 = " select a2.fe_doc,a3.co_emp,a3.co_loc,a3.co_tipdoc,a3.co_dia,a3.co_cta,a3.nd_mondeb,sum(a1.nd_abo) as val,a3.nd_mondeb+sum(a1.nd_abo) as total ";
                    strSql2 += " from tbm_detpag as a1 ";
                    strSql2 += " inner join tbm_cabpag as a2 on (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc) ";
                    strSql2 += " inner join tbm_detdia as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_dia=a2.co_doc) ";
                    strSql2 += " where a1.co_tipdoc =" + intTipDocAjuCenAut;
                    strSql2 += " and a1.co_emp=" + intCodEmpRec;
                    strSql2 += " and a1.co_loc=" + intCodLocRec;
                    strSql2 += " and a2.fe_doc>='2019-01-01' ";
                    strSql2 += " and a3.co_cta=" + rstLoc.getInt("co_ctahab");
                    strSql2 += " group by a1.co_loc,a3.nd_mondeb,a3.co_cta,a2.fe_doc,a3.co_emp,a3.co_loc,a3.co_tipdoc,a3.co_dia ";
                    strSql2 += " HAVING a3.nd_mondeb+sum(a1.nd_abo) < 0.00";
                    rstLoc2 = stmLoc2.executeQuery(strSql2);
                    while (rstLoc2.next()) {
                        strSqlDet += " update tbm_detdia\n"
                                + " set nd_mondeb=-1*" + rstLoc2.getDouble("total")
                                + " , nd_monhab=0.00\n"
                                + " where co_emp=" + rstLoc2.getInt("co_emp")
                                + "  and co_loc= " + rstLoc2.getInt("co_loc")
                                + "  and co_tipdoc= " + rstLoc2.getInt("co_tipdoc")
                                + "  and co_dia=" + rstLoc2.getInt("co_dia")
                                + "  and co_cta =" + rstLoc.getInt("co_ctahab") + ";";

                        strSqlDet += " update tbm_detdia\n"
                                + " set nd_mondeb=0.00"
                                + " , nd_monhab=-1*" + +rstLoc2.getDouble("total")
                                + " where co_emp=" + rstLoc2.getInt("co_emp")
                                + "  and co_loc= " + rstLoc2.getInt("co_loc")
                                + "  and co_tipdoc= " + rstLoc2.getInt("co_tipdoc")
                                + "  and co_dia=" + rstLoc2.getInt("co_dia")
                                + "  and co_cta !=" + rstLoc.getInt("co_ctahab") + ";";
                    }

                }
                rstLoc.close();
                rstLoc = null;
                if (rstLoc2 != null) {
                    rstLoc2.close();
                    rstLoc2 = null;
                }

                if (!(strSqlDet.equals(""))) {
                    stmLoc.executeUpdate(strSqlDet);
                }

                stmLoc.close();
                stmLoc = null;
                stmLoc2.close();
                stmLoc2 = null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}

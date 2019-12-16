package CxC.ZafCxC79;

/**
 *
 * @author jayapata
 */
public class ZafCxC79_02 
{
    Librerias.ZafParSis.ZafParSis objZafParSis;
    Librerias.ZafUtil.ZafUtil objUti;
    javax.swing.JInternalFrame jIntfra = null;
    javax.swing.JDialog jDialo = null;

    /**
     * Creates a new instance 
     */
    public ZafCxC79_02(javax.swing.JInternalFrame jfrthis, Librerias.ZafParSis.ZafParSis obj)
    {
        try 
        {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jIntfra = jfrthis;
            objUti = new Librerias.ZafUtil.ZafUtil();

        }
        catch (CloneNotSupportedException e) {     objUti.mostrarMsgErr_F1(jfrthis, e);     }
    }

    public boolean _getVerificaPagTotFac(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        boolean blnRes = false;
        java.sql.Statement stm;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try 
        {
            if (conn != null) 
            {
                stm = conn.createStatement();

                strSql = "select *, (pagfac+monchq) as dif from (  "
                        + " select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc,  sum(a1.mo_pag+a1.nd_Abo) as pagfac , sum(a1.nd_monchq) as monchq  "
                        + " from tbm_pagmovinv as a1 "
                        + " left join tbr_detrecdocpagmovinv as a2 "
                        + " on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg  )  "
                        + " where a1.co_emp=" + strCodEmp + " and a1.co_loc=" + strCodLoc + " and a1.co_tipdoc=" + strCodTipDoc + " and a1.co_doc=" + strCodDoc + "  and  a1.nd_porret=0  and a1.st_reg in ('A','C')  "
                        + " group by a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc  ) as x  ";
                //System.out.println("--->  "+strSql);
                rstLoc = stm.executeQuery(strSql);
                if (rstLoc.next()) {
                    if (rstLoc.getDouble("monchq") > 0) {
                        if (rstLoc.getDouble("dif") >= 0) {
                            if (_getVerificaFecChqFac(conn, rstLoc.getString("co_emp"), rstLoc.getString("co_loc"), rstLoc.getString("co_tipdoc"), rstLoc.getString("co_doc"))) {
                                blnRes = true;
                            }
                        } else {
                            if (rstLoc.getDouble("dif") >= -0.01) {
                                if (_getVerificaFecChqFac(conn, rstLoc.getString("co_emp"), rstLoc.getString("co_loc"), rstLoc.getString("co_tipdoc"), rstLoc.getString("co_doc"))) {
                                    blnRes = true;
                                }
                            }
                        }
                    }
                }
                rstLoc.close();
                rstLoc = null;

                if (!blnRes) {

                    strSql = "select *, (pagfac+abofac) as dif from (  "
                            + " select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc,  sum(a1.mo_pag) as pagfac , sum(a1.nd_abo) as abofac  "
                            + " from tbm_pagmovinv as a1 "
                            + " where a1.co_emp=" + strCodEmp + " and a1.co_loc=" + strCodLoc + " and a1.co_tipdoc=" + strCodTipDoc + " and a1.co_doc=" + strCodDoc + "  and  a1.nd_porret=0  and a1.st_reg in ('A','C')  "
                            + " group by a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc ) as x   ";
                    //System.out.println("--->  "+strSql);
                    rstLoc = stm.executeQuery(strSql);
                    if (rstLoc.next()) {
                        if (rstLoc.getDouble("abofac") > 0) {
                            if (rstLoc.getDouble("dif") >= -0.01) {
                                blnRes = true;
                            }
                        }
                    }
                    rstLoc.close();
                    rstLoc = null;

                }

                stm.close();
                stm = null;

            }
        }
        catch (java.sql.SQLException e) {         blnRes = false;      mostarErrorSqlException(e);     }
        catch (Exception e) {         blnRes = false;          mostarErrorException(e);       }
        return blnRes;
    }

    public boolean _getVerificaFecChqFac(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        boolean blnRes = false;
        java.sql.Statement stm;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try
        {
            if (conn != null) 
            {
                stm = conn.createStatement();

                strSql = "select * "
                        + " , case when fe_venchq <= fe_ven then 'S' else 'N' end as FecCont "
                        + " , case when fe_venchq <= fecvenchq then 'S' else 'N' end as FecnoCont "
                        + " from ( "
                        + "select ne_diacre, fe_ven, (fe_ven+1) as fecvenchq , fe_venchq  from tbm_pagmovinv "
                        + " where co_emp=" + strCodEmp + " and co_loc=" + strCodLoc + " and co_tipdoc=" + strCodTipDoc + " and co_doc=" + strCodDoc + " and nd_porret=0 and st_reg in ('A','C') "
                        + " and  ( mo_pag + nd_Abo) != 0   ) as x  ";
                //System.out.println("-> "+strSql );
                rstLoc = stm.executeQuery(strSql);
                while (rstLoc.next()) {

                    if (rstLoc.getInt("ne_diacre") == 0) {
                        if (rstLoc.getString("FecCont").equals("S")) {
                            blnRes = true;
                        } else {
                            blnRes = false;
                            break;
                        }
                    } else {
                        if (rstLoc.getString("FecnoCont").equals("S")) {
                            blnRes = true;
                        } else {
                            blnRes = false;
                            break;
                        }
                    }

                }
                rstLoc.close();
                rstLoc = null;
                stm.close();
                stm = null;

            }
        }
        catch (java.sql.SQLException e) {      blnRes = false;         mostarErrorSqlException(e);       } 
        catch (Exception e) {          blnRes = false;          mostarErrorException(e);       }
        return blnRes;
    }

    private void mostarErrorException(Exception evt) 
    {
        if (jDialo == null) 
            objUti.mostrarMsgErr_F1(jIntfra, evt);
        else 
            objUti.mostrarMsgErr_F1(jDialo, evt);
    }

    private void mostarErrorSqlException(java.sql.SQLException evt) 
    {
        if (jDialo == null) 
            objUti.mostrarMsgErr_F1(jIntfra, evt);
        else 
            objUti.mostrarMsgErr_F1(jDialo, evt);
    }

}

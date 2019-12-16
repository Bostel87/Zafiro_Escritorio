/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Compras.ZafCom78;

import java.sql.Connection;

/**
 *
 * @author sistemas1
 */
public interface ZafComOrdenConteo {
    
    /** 
     * Registrar codigo de empresa-grupo.
     * @param intCodEmpGrp: Codigo de empresa-grupo.
     */
    public void setCodEmpGrp(int intCodEmpGrp);
    public int getCodEmpGrp();
    public void setCodBodGrp(int intCodBodGrp);
    public int getCodBodGrp();
    public void setCodUsrResCon(int intCodUsrResCon);
    public int getCodUsrResCon();
    public int getCodTipDoc();
    public void setUsrResConOrdConInv(Connection conn);
    public void setCodDep(int intCodDep);
    public int getCodDep();
    public void setParams(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intCodBodGrpRel);
    public int getMaxCodDocOrdConInv(Connection conn, int codEmp, int codLoc, int codTipDoc);
    public int getMaxNumDocOrdConInv(Connection conn, int codEmp, int codLoc, int codTipDoc);
    public int getUsrResConOrdConInv(Connection conn, int codEmp, int codLoc, int codTipDoc, int codDoc);
    public int getMaxCodDocConInv(Connection conn, int codEmp);
    public boolean insertarCabOrdConInv(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc, int intCodUsrResCon, int intCodBod, String strTxtObs, String strTxtObs2, String strStReg, int intCodUsrIng, int intCodUsrMod, String strStRegRep);
    public int getCodItmGrp(Connection conn, int codEmpGrp, String codAltItmEmp);
    public boolean insertarDetOrdConInv(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCoReg, int intCodItm, String strRegRep) ;
    public boolean insertarTbmConInv(Connection conn, int intCodEmp, int intCoReg, int intUsrResCon, int intCodBod, int intCodItm) ;
    public boolean actualizarCabTipDoc(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc);
}

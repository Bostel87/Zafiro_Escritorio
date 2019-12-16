/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author usuario
 */
public class ZafPagFac {
    private Date datFecVec;
    private int intDiaCrd;
    private BigDecimal bigPorRet;
    private BigDecimal bigMontPag;
    private int intDiaGra;
    private int intCodTipRet;
    private String strSopChq;

    public Date getDatFecVec() {
        return datFecVec;
    }

    public void setDatFecVec(Date datFecVec) {
        this.datFecVec = datFecVec;
    }

    public int getIntDiaCrd() {
        return intDiaCrd;
    }

    public void setIntDiaCrd(int intDiaCrd) {
        this.intDiaCrd = intDiaCrd;
    }

    public BigDecimal getBigPorRet() {
        return bigPorRet;
    }

    public void setBigPorRet(BigDecimal bigPorRet) {
        this.bigPorRet = bigPorRet;
    }

    public BigDecimal getBigMontPag() {
        return bigMontPag;
    }

    public void setBigMontPag(BigDecimal bigMontPag) {
        this.bigMontPag = bigMontPag;
    }

    public int getIntDiaGra() {
        return intDiaGra;
    }

    public void setIntDiaGra(int intDiaGra) {
        this.intDiaGra = intDiaGra;
    }

    public int getIntCodTipRet() {
        return intCodTipRet;
    }
                
    public void setIntCodTipRet(int intCodTipRet) {
        this.intCodTipRet = intCodTipRet;
    }

    public String getStrSopChq() {
        return strSopChq;
    }

    public void setStrSopChq(String strSopChq) {
        this.strSopChq = strSopChq;
    }
    
    
            
    
    
}

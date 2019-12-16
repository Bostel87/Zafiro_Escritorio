/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafRecHum.ZafRecHumPoj;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Guayaquil, 19/09/2013
 * @author Roberto Flores
 * v1.0
 */

public class TbhSuetra implements Serializable {
    private static final long serialVersionUID = 1L;
    protected TbhSuetraPK tbhSuetraPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private BigDecimal ndValrub;
    private Date feHis;
    private Short coUsrhis;

    public TbhSuetra() {
    }

    public TbhSuetra(TbhSuetraPK tbhSuetraPK) {
        this.tbhSuetraPK = tbhSuetraPK;
    }

    public TbhSuetra(short coEmp, short coTra, short coRub, short coHis) {
        this.tbhSuetraPK = new TbhSuetraPK(coEmp, coTra, coRub, coHis);
    }

    public TbhSuetraPK getTbhSuetraPK() {
        return tbhSuetraPK;
    }

    public void setTbhSuetraPK(TbhSuetraPK tbhSuetraPK) {
        this.tbhSuetraPK = tbhSuetraPK;
    }

    public BigDecimal getNdValrub() {
        return ndValrub;
    }

    public void setNdValrub(BigDecimal ndValrub) {
        this.ndValrub = ndValrub;
    }

    public Date getFeHis() {
        return feHis;
    }

    public void setFeHis(Date feHis) {
        this.feHis = feHis;
    }

    public Short getCoUsrhis() {
        return coUsrhis;
    }

    public void setCoUsrhis(Short coUsrhis) {
        this.coUsrhis = coUsrhis;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tbhSuetraPK != null ? tbhSuetraPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbhSuetra)) {
            return false;
        }
        TbhSuetra other = (TbhSuetra) object;
        if ((this.tbhSuetraPK == null && other.tbhSuetraPK != null) || (this.tbhSuetraPK != null && !this.tbhSuetraPK.equals(other.tbhSuetraPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Librerias.ZafRecHum.ZafRecHumPoj.TbhSuetra[ tbhSuetraPK=" + tbhSuetraPK + " ]";
    }
    
}

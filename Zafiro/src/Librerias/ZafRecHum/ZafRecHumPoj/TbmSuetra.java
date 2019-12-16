/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafRecHum.ZafRecHumPoj;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * Guayaquil, 19/09/2013
 * @author Roberto Flores
 * v1.0
 */

public class TbmSuetra implements Serializable {
    private static final long serialVersionUID = 1L;

    protected TbmSuetraPK tbmSuetraPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    private BigDecimal ndValrub;

    public TbmSuetra() {
    }

    public TbmSuetra(TbmSuetraPK tbmSuetraPK) {
        this.tbmSuetraPK = tbmSuetraPK;
    }

    public TbmSuetra(short coEmp, short coRub, short coTra) {
        this.tbmSuetraPK = new TbmSuetraPK(coEmp, coRub, coTra);
    }

    public TbmSuetraPK getTbmSuetraPK() {
        return tbmSuetraPK;
    }

    public void setTbmSuetraPK(TbmSuetraPK tbmSuetraPK) {
        this.tbmSuetraPK = tbmSuetraPK;
    }

    public BigDecimal getNdValrub() {
        return ndValrub;
    }

    public void setNdValrub(BigDecimal ndValrub) {
        this.ndValrub = ndValrub;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tbmSuetraPK != null ? tbmSuetraPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbmSuetra)) {
            return false;
        }
        TbmSuetra other = (TbmSuetra) object;
        if ((this.tbmSuetraPK == null && other.tbmSuetraPK != null) || (this.tbmSuetraPK != null && !this.tbmSuetraPK.equals(other.tbmSuetraPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Librerias.ZafRecHum.ZafRecHumPoj.TbmSuetra[ tbmSuetraPK=" + tbmSuetraPK + " ]";
    }
    
}

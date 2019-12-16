/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafRecHum.ZafRecHumPoj;

import java.io.Serializable;

/**
 * Guayaquil, 19/09/2013
 * @author Roberto Flores
 * v1.0
 */

public class TbmSuetraPK implements Serializable {

    private short coEmp;
    private short coRub;
    private short coTra;

    public TbmSuetraPK() {
    }

    public TbmSuetraPK(short coEmp, short coRub, short coTra) {
        this.coEmp = coEmp;
        this.coRub = coRub;
        this.coTra = coTra;
    }

    public short getCoEmp() {
        return coEmp;
    }

    public void setCoEmp(short coEmp) {
        this.coEmp = coEmp;
    }

    public short getCoRub() {
        return coRub;
    }

    public void setCoRub(short coRub) {
        this.coRub = coRub;
    }

    public short getCoTra() {
        return coTra;
    }

    public void setCoTra(short coTra) {
        this.coTra = coTra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) coEmp;
        hash += (int) coRub;
        hash += (int) coTra;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbmSuetraPK)) {
            return false;
        }
        TbmSuetraPK other = (TbmSuetraPK) object;
        if (this.coEmp != other.coEmp) {
            return false;
        }
        if (this.coRub != other.coRub) {
            return false;
        }
        if (this.coTra != other.coTra) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Librerias.ZafRecHum.ZafRecHumPoj.TbmSuetraPK[ coEmp=" + coEmp + ", coRub=" + coRub + ", coTra=" + coTra + " ]";
    }
    
}

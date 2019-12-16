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

public class TbhSuetraPK implements Serializable {
    private short coEmp;
    private short coTra;
    private short coRub;
    private short coHis;

    public TbhSuetraPK() {
    }

    public TbhSuetraPK(short coEmp, short coTra, short coRub, short coHis) {
        this.coEmp = coEmp;
        this.coTra = coTra;
        this.coRub = coRub;
        this.coHis = coHis;
    }

    public short getCoEmp() {
        return coEmp;
    }

    public void setCoEmp(short coEmp) {
        this.coEmp = coEmp;
    }

    public short getCoTra() {
        return coTra;
    }

    public void setCoTra(short coTra) {
        this.coTra = coTra;
    }

    public short getCoRub() {
        return coRub;
    }

    public void setCoRub(short coRub) {
        this.coRub = coRub;
    }

    public short getCoHis() {
        return coHis;
    }

    public void setCoHis(short coHis) {
        this.coHis = coHis;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) coEmp;
        hash += (int) coTra;
        hash += (int) coRub;
        hash += (int) coHis;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbhSuetraPK)) {
            return false;
        }
        TbhSuetraPK other = (TbhSuetraPK) object;
        if (this.coEmp != other.coEmp) {
            return false;
        }
        if (this.coTra != other.coTra) {
            return false;
        }
        if (this.coRub != other.coRub) {
            return false;
        }
        if (this.coHis != other.coHis) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Librerias.ZafRecHum.ZafRecHumPoj.TbhSuetraPK[ coEmp=" + coEmp + ", coTra=" + coTra + ", coRub=" + coRub + ", coHis=" + coHis + " ]";
    }
    
}

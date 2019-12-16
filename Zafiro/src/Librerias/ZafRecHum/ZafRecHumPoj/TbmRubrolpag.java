/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafRecHum.ZafRecHumPoj;

import java.io.Serializable;
import java.util.Date;

/**
 * Guayaquil, 19/09/2013
 * @author Roberto Flores
 * v1.0
 */

public class TbmRubrolpag implements Serializable {
    private static final long serialVersionUID = 1L;
    private Short coRub;
    private String txNom;
    private char txTiprub;
    private char txTipvalrub;
    private Character stEgrprg;
    private String txObs1;
    private char stReg;
    private Date feIng;
    private Date feUltmod;

    public TbmRubrolpag() {
    }

    public TbmRubrolpag(Short coRub) {
        this.coRub = coRub;
    }

    public TbmRubrolpag(Short coRub, String txNom, char txTiprub, char txTipvalrub, char stReg) {
        this.coRub = coRub;
        this.txNom = txNom;
        this.txTiprub = txTiprub;
        this.txTipvalrub = txTipvalrub;
        this.stReg = stReg;
    }

    public Short getCoRub() {
        return coRub;
    }

    public void setCoRub(Short coRub) {
        this.coRub = coRub;
    }

    public String getTxNom() {
        return txNom;
    }

    public void setTxNom(String txNom) {
        this.txNom = txNom;
    }

    public char getTxTiprub() {
        return txTiprub;
    }

    public void setTxTiprub(char txTiprub) {
        this.txTiprub = txTiprub;
    }

    public char getTxTipvalrub() {
        return txTipvalrub;
    }

    public void setTxTipvalrub(char txTipvalrub) {
        this.txTipvalrub = txTipvalrub;
    }

    public Character getStEgrprg() {
        return stEgrprg;
    }

    public void setStEgrprg(Character stEgrprg) {
        this.stEgrprg = stEgrprg;
    }

    public String getTxObs1() {
        return txObs1;
    }

    public void setTxObs1(String txObs1) {
        this.txObs1 = txObs1;
    }

    public char getStReg() {
        return stReg;
    }

    public void setStReg(char stReg) {
        this.stReg = stReg;
    }

    public Date getFeIng() {
        return feIng;
    }

    public void setFeIng(Date feIng) {
        this.feIng = feIng;
    }

    public Date getFeUltmod() {
        return feUltmod;
    }

    public void setFeUltmod(Date feUltmod) {
        this.feUltmod = feUltmod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coRub != null ? coRub.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbmRubrolpag)) {
            return false;
        }
        TbmRubrolpag other = (TbmRubrolpag) object;
        if ((this.coRub == null && other.coRub != null) || (this.coRub != null && !this.coRub.equals(other.coRub))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Librerias.ZafRecHum.ZafRecHumPoj.TbmRubrolpag[ coRub=" + coRub + " ]";
    }
    
}

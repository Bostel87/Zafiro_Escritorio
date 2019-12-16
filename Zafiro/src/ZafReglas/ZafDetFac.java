/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas;

import java.io.Serializable;

/**
 *
 * @author sistemas4
 */
public class ZafDetFac implements Serializable{
    
    private int         co_emp, 
                        co_loc, 
                        co_tipdoc, 
                        co_doc, 
                        co_reg; 
    private String      tx_codAlt, 
                        tx_codAlt2, 
                        co_itm, 
                        co_itmact,  
                        tx_nomItm, 
                        tx_unimed; 
    private int         co_bod; 
    private double      nd_can,nd_tot, 
                        nd_cosUnigrp,
                        nd_costot, 
                        nd_preUni, 
                        nd_porDes;
    private char        st_ivaCom ;
    private double      nd_costotgrp;
    private char        st_regrep, 
                        st_meringegrfisbod;
    private double      nd_cancon, 
                        nd_preunivenlis, 
                        nd_pordesvenmax;
    private int         ne_numfil;

            
            
            
            
            
            
            
            
            
    public int getCo_emp() {
        return co_emp;
    }

    public void setCo_emp(int co_emp) {
        this.co_emp = co_emp;
    }

    public int getCo_loc() {
        return co_loc;
    }

    public void setCo_loc(int co_loc) {
        this.co_loc = co_loc;
    }

    public int getCo_tipdoc() {
        return co_tipdoc;
    }

    public void setCo_tipdoc(int co_tipdoc) {
        this.co_tipdoc = co_tipdoc;
    }

    public int getCo_doc() {
        return co_doc;
    }

    public void setCo_doc(int co_doc) {
        this.co_doc = co_doc;
    }

    public int getCo_reg() {
        return co_reg;
    }

    public void setCo_reg(int co_reg) {
        this.co_reg = co_reg;
    }

    public String getTx_codAlt() {
        return tx_codAlt;
    }

    public void setTx_codAlt(String tx_codAlt) {
        this.tx_codAlt = tx_codAlt;
    }

    public String getTx_codAlt2() {
        return tx_codAlt2;
    }

    public void setTx_codAlt2(String tx_codAlt2) {
        this.tx_codAlt2 = tx_codAlt2;
    }

    public String getCo_itm() {
        return co_itm;
    }

    public void setCo_itm(String co_itm) {
        this.co_itm = co_itm;
    }

    public String getCo_itmact() {
        return co_itmact;
    }

    public void setCo_itmact(String co_itmact) {
        this.co_itmact = co_itmact;
    }

    public String getTx_nomItm() {
        return tx_nomItm;
    }

    public void setTx_nomItm(String tx_nomItm) {
        this.tx_nomItm = tx_nomItm;
    }

    public String getTx_unimed() {
        return tx_unimed;
    }

    public void setTx_unimed(String tx_unimed) {
        this.tx_unimed = tx_unimed;
    }

    public int getCo_bod() {
        return co_bod;
    }

    public void setCo_bod(int co_bod) {
        this.co_bod = co_bod;
    }

    public double getNd_can() {
        return nd_can;
    }

    public void setNd_can(double nd_can) {
        this.nd_can = nd_can;
    }

    public double getNd_tot() {
        return nd_tot;
    }

    public void setNd_tot(double nd_tot) {
        this.nd_tot = nd_tot;
    }

    public double getNd_cosUnigrp() {
        return nd_cosUnigrp;
    }

    public void setNd_cosUnigrp(double nd_cosUnigrp) {
        this.nd_cosUnigrp = nd_cosUnigrp;
    }

    public double getNd_costot() {
        return nd_costot;
    }

    public void setNd_costot(double nd_costot) {
        this.nd_costot = nd_costot;
    }

    public double getNd_preUni() {
        return nd_preUni;
    }

    public void setNd_preUni(double nd_preUni) {
        this.nd_preUni = nd_preUni;
    }

    public double getNd_porDes() {
        return nd_porDes;
    }

    public void setNd_porDes(double nd_porDes) {
        this.nd_porDes = nd_porDes;
    }

    public char getSt_ivaCom() {
        return st_ivaCom;
    }

    public void setSt_ivaCom(char st_ivaCom) {
        this.st_ivaCom = st_ivaCom;
    }

    public double getNd_costotgrp() {
        return nd_costotgrp;
    }

    public void setNd_costotgrp(double nd_costotgrp) {
        this.nd_costotgrp = nd_costotgrp;
    }

    public char getSt_regrep() {
        return st_regrep;
    }

    public void setSt_regrep(char st_regrep) {
        this.st_regrep = st_regrep;
    }

    public char getSt_meringegrfisbod() {
        return st_meringegrfisbod;
    }

    public void setSt_meringegrfisbod(char st_meringegrfisbod) {
        this.st_meringegrfisbod = st_meringegrfisbod;
    }

    public double getNd_cancon() {
        return nd_cancon;
    }

    public void setNd_cancon(double nd_cancon) {
        this.nd_cancon = nd_cancon;
    }

    public double getNd_preunivenlis() {
        return nd_preunivenlis;
    }

    public void setNd_preunivenlis(double nd_preunivenlis) {
        this.nd_preunivenlis = nd_preunivenlis;
    }

    public double getNd_pordesvenmax() {
        return nd_pordesvenmax;
    }

    public void setNd_pordesvenmax(double nd_pordesvenmax) {
        this.nd_pordesvenmax = nd_pordesvenmax;
    }

    public int getNe_numfil() {
        return ne_numfil;
    }

    public void setNe_numfil(int ne_numfil) {
        this.ne_numfil = ne_numfil;
    }
    
            
            
            
            
}

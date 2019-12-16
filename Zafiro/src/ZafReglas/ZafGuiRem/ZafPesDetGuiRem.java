/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas.ZafGuiRem;

import java.io.Serializable;

/**
 *
 * @author postgres
 */
public class ZafPesDetGuiRem implements Serializable{
    
    
    private int co_emp;
    private int co_loc;
    private int co_tipdoc;
    private int co_doc;
    private int co_reg;
    private double nd_peso;

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

    public double getNd_peso() {
        return nd_peso;
    }

    public void setNd_peso(double nd_peso) {
        this.nd_peso = nd_peso;
    }

    public int getCo_reg() {
        return co_reg;
    }

    public void setCo_reg(int co_reg) {
        this.co_reg = co_reg;
    }
    
}

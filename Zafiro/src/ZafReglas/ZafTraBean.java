/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas;

/**
 *
 * @author sistemas4
 */
public class ZafTraBean {
    private int emp;
    private int loc;
    private int tipdoc;
    private int numdoc;
    private int coDoc;
    private int intCoBodOri;//para OD por transferencias desde bodProy
    private int intCoBodDes;//para OD por transferencias desde bodProy
    //*************************para detalle**************************************************//
    private int coreg;
    private int co_itm;
    
    private String tx_nomitm;
    private String tx_codalt;
    
    private String tx_unimed;
    private double nd_can;
    
    private String tx_obs1;
    private String st_regrep;
    
    private String st_meregrfisbod;

    public ZafTraBean(int emp, int loc, int tipdoc, int numdoc, int coDoc, int intCoBodOri, int intCoBodDes) {
        this.emp = emp;
        this.loc = loc;
        this.tipdoc = tipdoc;
        this.numdoc = numdoc;
        this.coDoc = coDoc;
        this.intCoBodOri = intCoBodOri;
        this.intCoBodDes = intCoBodDes;
    }

    public ZafTraBean() {
    }

    public ZafTraBean(int emp, int loc, int tipdoc, int numdoc, int coDoc, int intCoBodOri, int intCoBodDes, int coreg, int co_itm, String tx_nomitm, String tx_codalt, String tx_unimed, double nd_can, String tx_obs1, String st_regrep, String st_meregrfisbod) {
        this.emp = emp;
        this.loc = loc;
        this.tipdoc = tipdoc;
        this.numdoc = numdoc;
        this.coDoc = coDoc;
        this.intCoBodOri = intCoBodOri;
        this.intCoBodDes = intCoBodDes;
        this.coreg = coreg;
        this.co_itm = co_itm;
        this.tx_nomitm = tx_nomitm;
        this.tx_codalt = tx_codalt;
        this.tx_unimed = tx_unimed;
        this.nd_can = nd_can;
        this.tx_obs1 = tx_obs1;
        this.st_regrep = st_regrep;
        this.st_meregrfisbod = st_meregrfisbod;
    }
    
    
    
    

    public int getEmp() {
        return emp;
    }

    public void setEmp(int emp) {
        this.emp = emp;
    }

    public int getLoc() {
        return loc;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    public int getTipdoc() {
        return tipdoc;
    }

    public void setTipdoc(int tipdoc) {
        this.tipdoc = tipdoc;
    }

    public int getNumdoc() {
        return numdoc;
    }

    public void setNumdoc(int numdoc) {
        this.numdoc = numdoc;
    }

    public int getCoDoc() {
        return coDoc;
    }

    public void setCoDoc(int coDoc) {
        this.coDoc = coDoc;
    }

    public int getIntCoBodOri() {
        return intCoBodOri;
    }

    public void setIntCoBodOri(int intCoBodOri) {
        this.intCoBodOri = intCoBodOri;
    }

    public int getIntCoBodDes() {
        return intCoBodDes;
    }

    public void setIntCoBodDes(int intCoBodDes) {
        this.intCoBodDes = intCoBodDes;
    }

    public String getTx_codalt() {
        return tx_codalt;
    }

    public void setTx_codalt(String tx_codalt) {
        this.tx_codalt = tx_codalt;
    }

    public String getTx_nomitm() {
        return tx_nomitm;
    }

    public void setTx_nomitm(String tx_nomitm) {
        this.tx_nomitm = tx_nomitm;
    }

    public String getTx_unimed() {
        return tx_unimed;
    }

    public void setTx_unimed(String tx_unimed) {
        this.tx_unimed = tx_unimed;
    }

    public double getNd_can() {
        return nd_can;
    }

    public void setNd_can(double nd_can) {
        this.nd_can = nd_can;
    }

    public String getSt_regrep() {
        return st_regrep;
    }

    public void setSt_regrep(String st_regrep) {
        this.st_regrep = st_regrep;
    }

    public String getTx_obs1() {
        return tx_obs1;
    }

    public void setTx_obs1(String tx_obs1) {
        this.tx_obs1 = tx_obs1;
    }

    public String getSt_meregrfisbod() {
        return st_meregrfisbod;
    }

    public void setSt_meregrfisbod(String st_meregrfisbod) {
        this.st_meregrfisbod = st_meregrfisbod;
    }

    public int getCoreg() {
        return coreg;
    }

    public void setCoreg(int coreg) {
        this.coreg = coreg;
    }

    public int getCo_itm() {
        return co_itm;
    }

    public void setCo_itm(int co_itm) {
        this.co_itm = co_itm;
    }
    
    
    
    
    
    
}

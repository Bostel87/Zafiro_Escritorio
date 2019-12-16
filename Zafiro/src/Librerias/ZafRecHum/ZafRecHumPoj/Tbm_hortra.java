/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumPoj;

import java.util.Date;

/**
 * Pojo contiene campos de las tablas Tbm_cabhortra, Tbm_dethortra.
 * @author Roberto Flores
 * Guayaquil 08/09/2011
 */
public class Tbm_hortra implements Cloneable {

    private int intCo_hor;
    private String strTx_nom;
    private String strTxa_obs1;
    private String strSt_reg;
    private Date datFe_ing;
    private Date datFe_ultMod;
    private int intCo_usrIng;
    private int intCo_usrMod;

    private int intNe_diadet;
    private String strHo_entdet;
    private String strHo_saldet;

    private String[][] strTbl_entsaldet;

    private String strTx_minGra;

    /**
     * @return the intCo_hor
     */
    public int getIntCo_hor() {
        return intCo_hor;
    }

    /**
     * @param intCo_hor the intCo_hor to set
     */
    public void setIntCo_hor(int intCo_hor) {
        this.intCo_hor = intCo_hor;
    }

    /**
     * @return the strTx_nom
     */
    public String getStrTx_nom() {
        return strTx_nom;
    }

    /**
     * @param strTx_nom the strTx_nom to set
     */
    public void setStrTx_nom(String strTx_nom) {
        this.strTx_nom = strTx_nom;
    }

    /**
     * @return the strTxa_obs1
     */
    public String getStrTxa_obs1() {
        return strTxa_obs1;
    }

    /**
     * @param strTxa_obs1 the strTxa_obs1 to set
     */
    public void setStrTxa_obs1(String strTxa_obs1) {
        this.strTxa_obs1 = strTxa_obs1;
    }

    /**
     * @return the strSt_reg
     */
    public String getStrSt_reg() {
        return strSt_reg;
    }

    /**
     * @param strSt_reg the strSt_reg to set
     */
    public void setStrSt_reg(String strSt_reg) {
        this.strSt_reg = strSt_reg;
    }

    /**
     * @return the datFe_ing
     */
    public Date getDatFe_ing() {
        return datFe_ing;
    }

    /**
     * @param datFe_ing the datFe_ing to set
     */
    public void setDatFe_ing(Date datFe_ing) {
        this.datFe_ing = datFe_ing;
    }

    /**
     * @return the datFe_ultMod
     */
    public Date getDatFe_ultMod() {
        return datFe_ultMod;
    }

    /**
     * @param datFe_ultMod the datFe_ultMod to set
     */
    public void setDatFe_ultMod(Date datFe_ultMod) {
        this.datFe_ultMod = datFe_ultMod;
    }

    /**
     * @return the intCo_usrIng
     */
    public int getIntCo_usrIng() {
        return intCo_usrIng;
    }

    /**
     * @param intCo_usrIng the intCo_usrIng to set
     */
    public void setIntCo_usrIng(int intCo_usrIng) {
        this.intCo_usrIng = intCo_usrIng;
    }

    /**
     * @return the intCo_usrMod
     */
    public int getIntCo_usrMod() {
        return intCo_usrMod;
    }

    /**
     * @param intCo_usrMod the intCo_usrMod to set
     */
    public void setIntCo_usrMod(int intCo_usrMod) {
        this.intCo_usrMod = intCo_usrMod;
    }

    /**
     * @return the intNe_diadet
     */
    public int getIntNe_diadet() {
        return intNe_diadet;
    }

    /**
     * @param intNe_diadet the intNe_diadet to set
     */
    public void setIntNe_diadet(int intNe_diadet) {
        this.intNe_diadet = intNe_diadet;
    }

    /**
     * @return the strHo_entdet
     */
    public String getStrHo_entdet() {
        return strHo_entdet;
    }

    /**
     * @param strHo_entdet the strHo_entdet to set
     */
    public void setStrHo_entdet(String strHo_entdet) {
        this.strHo_entdet = strHo_entdet;
    }

    /**
     * @return the strHo_saldet
     */
    public String getStrHo_saldet() {
        return strHo_saldet;
    }

    /**
     * @param strHo_saldet the strHo_saldet to set
     */
    public void setStrHo_saldet(String strHo_saldet) {
        this.strHo_saldet = strHo_saldet;
    }

    /**
     * @return the strTbl_entsaldet
     */
    public String[][] getStrTbl_entsaldet() {
        return strTbl_entsaldet;
    }

    /**
     * @param strTbl_entsaldet the strTbl_entsaldet to set
     */
    public void setStrTbl_entsaldet(String[][] strTbl_entsaldet) {
        this.strTbl_entsaldet = strTbl_entsaldet;
    }

    /**
     * @return the strTx_minGra
     */
    public String getStrTx_minGra() {
        return strTx_minGra;
    }

    /**
     * @param strTx_minGra the strTx_minGra to set
     */
    public void setStrTx_minGra(String strTx_minGra) {
        this.strTx_minGra = strTx_minGra;
    }

    /**
     * Método que permite clonar el objeto
     * @return Retorna objeto clonado o nulo
     */
    @Override
    public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }
}
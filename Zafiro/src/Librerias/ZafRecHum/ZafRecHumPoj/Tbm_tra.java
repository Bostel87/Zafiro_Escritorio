
package Librerias.ZafRecHum.ZafRecHumPoj;

import java.util.ArrayList;
import java.util.Date;

/**
 * Pojo contiene campos de la tabla tbm_tra
 * @author Carlos Lainez, Roberto Flores.
 * Guayaquil 01/04/2011
 * v1.04
 */
public class Tbm_tra implements Cloneable {
    private int intCo_tra;
    private String strTx_ide;
    private String strTx_nom;
    private String strTx_ape;
    private String strTx_dir;
    private String strTx_refdir;
    private String strTx_tel1;
    private String strTx_tel2;
    private String strTx_tel3;
    private String strTx_corele;
    private String strTx_sex;
    private Date datFe_nac;
    private int intCo_ciunac;
    private int intCo_estciv;
    private int intNe_numhij;
    private String strTx_NumCtaBan;
    private String strTx_TipCtaBan;
    private String strTx_obs1;
    private String strTx_obs2;
    private String strSt_reg;
    private Date datFe_ing;
    private Date datFe_ultmod;
    private int intCo_usring;
    private int intCo_usrmod;
    
    private Date datFe_IniCon;
    private Date datFe_FinPerPru;
    private Date datFe_FinCon;
    
    private String strSt_perprucon;
    private String strSt_fincon;
    private Date datFe_FinReaCon;
    
    private int intCoPla;
    private int intCoDep;
    private int intCoOfi;
    private int intCoCarLab;
    
    private String strStRecAlm;
    private String strTxForRecAlm;
    private double dblNdValAlm;
    
    private ArrayList<TbhSuetra> arrLstTbhSueTra;
    private ArrayList<TbmSuetra> arrLstTbmSueTra;
    
    public Tbm_tra (){
        arrLstTbhSueTra = new ArrayList<TbhSuetra>();
        arrLstTbmSueTra = new ArrayList<TbmSuetra>();
    }

    /**
     * @return the intCo_tra
     */
    public int getIntCo_tra() {
        return intCo_tra;
    }

    /**
     * @param intCo_tra the intCo_tra to set
     */
    public void setIntCo_tra(int intCo_tra) {
        this.intCo_tra = intCo_tra;
    }

    /**
     * @return the strTx_ide
     */
    public String getStrTx_ide() {
        return strTx_ide;
    }

    /**
     * @param strTx_ide the strTx_ide to set
     */
    public void setStrTx_ide(String strTx_ide) {
        this.strTx_ide = strTx_ide;
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
     * @return the strTx_ape
     */
    public String getStrTx_ape() {
        return strTx_ape;
    }

    /**
     * @param strTx_ape the strTx_ape to set
     */
    public void setStrTx_ape(String strTx_ape) {
        this.strTx_ape = strTx_ape;
    }

    /**
     * @return the strTx_dir
     */
    public String getStrTx_dir() {
        return strTx_dir;
    }

    /**
     * @param strTx_dir the strTx_dir to set
     */
    public void setStrTx_dir(String strTx_dir) {
        this.strTx_dir = strTx_dir;
    }

    /**
     * @return the strTx_refdir
     */
    public String getStrTx_refdir() {
        return strTx_refdir;
    }

    /**
     * @param strTx_refdir the strTx_refdir to set
     */
    public void setStrTx_refdir(String strTx_refdir) {
        this.strTx_refdir = strTx_refdir;
    }

    /**
     * @return the strTx_tel1
     */
    public String getStrTx_tel1() {
        return strTx_tel1;
    }

    /**
     * @param strTx_tel1 the strTx_tel1 to set
     */
    public void setStrTx_tel1(String strTx_tel1) {
        this.strTx_tel1 = strTx_tel1;
    }

    /**
     * @return the strTx_tel2
     */
    public String getStrTx_tel2() {
        return strTx_tel2;
    }

    /**
     * @param strTx_tel2 the strTx_tel2 to set
     */
    public void setStrTx_tel2(String strTx_tel2) {
        this.strTx_tel2 = strTx_tel2;
    }

    /**
     * @return the strTx_tel3
     */
    public String getStrTx_tel3() {
        return strTx_tel3;
    }

    /**
     * @param strTx_tel3 the strTx_tel3 to set
     */
    public void setStrTx_tel3(String strTx_tel3) {
        this.strTx_tel3 = strTx_tel3;
    }

    /**
     * @return the strTx_corele
     */
    public String getStrTx_corele() {
        return strTx_corele;
    }

    /**
     * @param strTx_corele the strTx_corele to set
     */
    public void setStrTx_corele(String strTx_corele) {
        this.strTx_corele = strTx_corele;
    }

    /**
     * @return the strTx_sex
     */
    public String getStrTx_sex() {
        return strTx_sex;
    }

    /**
     * @param strTx_sex the strTx_sex to set
     */
    public void setStrTx_sex(String strTx_sex) {
        this.strTx_sex = strTx_sex;
    }

    /**
     * @return the datFe_nac
     */
    public Date getDatFe_nac() {
        return datFe_nac;
    }

    /**
     * @param datFe_nac the datFe_nac to set
     */
    public void setDatFe_nac(Date datFe_nac) {
        this.datFe_nac = datFe_nac;
    }

    /**
     * @return the intCo_ciunac
     */
    public int getIntCo_ciunac() {
        return intCo_ciunac;
    }

    /**
     * @param intCo_ciunac the intCo_ciunac to set
     */
    public void setIntCo_ciunac(int intCo_ciunac) {
        this.intCo_ciunac = intCo_ciunac;
    }

    /**
     * @return the intCo_estciv
     */
    public int getIntCo_estciv() {
        return intCo_estciv;
    }

    /**
     * @param intCo_estciv the intCo_estciv to set
     */
    public void setIntCo_estciv(int intCo_estciv) {
        this.intCo_estciv = intCo_estciv;
    }

    /**
     * @return the intNe_numhij
     */
    public int getIntNe_numhij() {
        return intNe_numhij;
    }

    /**
     * @param intNe_numhij the intNe_numhij to set
     */
    public void setIntNe_numhij(int intNe_numhij) {
        this.intNe_numhij = intNe_numhij;
    }

    /**
     * @return the strTx_obs1
     */
    public String getStrTx_obs1() {
        return strTx_obs1;
    }

    /**
     * @param strTx_obs1 the strTx_obs1 to set
     */
    public void setStrTx_obs1(String strTx_obs1) {
        this.strTx_obs1 = strTx_obs1;
    }

    /**
     * @return the strTx_obs2
     */
    public String getStrTx_obs2() {
        return strTx_obs2;
    }

    /**
     * @param strTx_obs2 the strTx_obs2 to set
     */
    public void setStrTx_obs2(String strTx_obs2) {
        this.strTx_obs2 = strTx_obs2;
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
     * @return the datFe_ultmod
     */
    public Date getDatFe_ultmod() {
        return datFe_ultmod;
    }

    /**
     * @param datFe_ultmod the datFe_ultmod to set
     */
    public void setDatFe_ultmod(Date datFe_ultmod) {
        this.datFe_ultmod = datFe_ultmod;
    }

    /**
     * @return the intCo_usring
     */
    public int getIntCo_usring() {
        return intCo_usring;
    }

    /**
     * @param intCo_usring the intCo_usring to set
     */
    public void setIntCo_usring(int intCo_usring) {
        this.intCo_usring = intCo_usring;
    }

    /**
     * @return the intCo_usrmod
     */
    public int getIntCo_usrmod() {
        return intCo_usrmod;
    }

    /**
     * @param intCo_usrmod the intCo_usrmod to set
     */
    public void setIntCo_usrmod(int intCo_usrmod) {
        this.intCo_usrmod = intCo_usrmod;
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

    /**
     * @return the strTx_NumCtaBan
     */
    public String getStrTx_NumCtaBan() {
        return strTx_NumCtaBan;
    }

    /**
     * @param strTx_NumCtaBan the strTx_NumCtaBan to set
     */
    public void setStrTx_NumCtaBan(String strTx_NumCtaBan) {
        this.strTx_NumCtaBan = strTx_NumCtaBan;
    }

    /**
     * @return the strTx_NumTipCtaBan
     */
    public String getStrTx_TipCtaBan() {
        return strTx_TipCtaBan;
    }

    /**
     * @param strTx_NumTipCtaBan the strTx_NumTipCtaBan to set
     */
    public void setStrTx_TipCtaBan(String strTx_TipCtaBan) {
        this.strTx_TipCtaBan = strTx_TipCtaBan;
    }

    /**
     * @return the datFe_IniCon
     */
    public Date getDatFe_IniCon() {
        return datFe_IniCon;
    }

    /**
     * @param datFe_IniCon the datFe_IniCon to set
     */
    public void setDatFe_IniCon(Date datFe_IniCon) {
        this.datFe_IniCon = datFe_IniCon;
    }

    /**
     * @return the datFe_FinPerPru
     */
    public Date getDatFe_FinPerPru() {
        return datFe_FinPerPru;
    }

    /**
     * @param datFe_FinPerPru the datFe_FinPerPru to set
     */
    public void setDatFe_FinPerPru(Date datFe_FinPerPru) {
        this.datFe_FinPerPru = datFe_FinPerPru;
    }

    /**
     * @return the datFe_FinCon
     */
    public Date getDatFe_FinCon() {
        return datFe_FinCon;
    }

    /**
     * @param datFe_FinCon the datFe_FinCon to set
     */
    public void setDatFe_FinCon(Date datFe_FinCon) {
        this.datFe_FinCon = datFe_FinCon;
    }

    /**
     * @return the strSt_perprucon
     */
    public String getStrSt_perprucon() {
        return strSt_perprucon;
    }

    /**
     * @param strSt_perprucon the strSt_perprucon to set
     */
    public void setStrSt_perprucon(String strSt_perprucon) {
        this.strSt_perprucon = strSt_perprucon;
    }

    /**
     * @return the strSt_fincon
     */
    public String getStrSt_fincon() {
        return strSt_fincon;
    }

    /**
     * @param strSt_fincon the strSt_fincon to set
     */
    public void setStrSt_fincon(String strSt_fincon) {
        this.strSt_fincon = strSt_fincon;
    }

    /**
     * @return the datFe_FinReaCon
     */
    public Date getDatFe_FinReaCon() {
        return datFe_FinReaCon;
    }

    /**
     * @param datFe_FinReaCon the datFe_FinReaCon to set
     */
    public void setDatFe_FinReaCon(Date datFe_FinReaCon) {
        this.datFe_FinReaCon = datFe_FinReaCon;
    }

    /**
     * @return the intCoPla
     */
    public int getIntCoPla() {
        return intCoPla;
    }

    /**
     * @param intCoPla the intCoPla to set
     */
    public void setIntCoPla(int intCoPla) {
        this.intCoPla = intCoPla;
    }

    /**
     * @return the intCoDep
     */
    public int getIntCoDep() {
        return intCoDep;
    }

    /**
     * @param intCoDep the intCoDep to set
     */
    public void setIntCoDep(int intCoDep) {
        this.intCoDep = intCoDep;
    }

    /**
     * @return the strStRecAlm
     */
    public String getStrStRecAlm() {
        return strStRecAlm;
    }

    /**
     * @param strStRecAlm the strStRecAlm to set
     */
    public void setStrStRecAlm(String strStRecAlm) {
        this.strStRecAlm = strStRecAlm;
    }

    /**
     * @return the strTxForRecAlm
     */
    public String getStrTxForRecAlm() {
        return strTxForRecAlm;
    }

    /**
     * @param strTxForRecAlm the strTxForRecAlm to set
     */
    public void setStrTxForRecAlm(String strTxForRecAlm) {
        this.strTxForRecAlm = strTxForRecAlm;
    }

    /**
     * @return the dblNdValAlm
     */
    public double getDblNdValAlm() {
        return dblNdValAlm;
    }

    /**
     * @param dblNdValAlm the dblNdValAlm to set
     */
    public void setDblNdValAlm(double dblNdValAlm) {
        this.dblNdValAlm = dblNdValAlm;
    }

    /**
     * @return the intCoOfi
     */
    public int getIntCoOfi() {
        return intCoOfi;
    }

    /**
     * @param intCoOfi the intCoOfi to set
     */
    public void setIntCoOfi(int intCoOfi) {
        this.intCoOfi = intCoOfi;
    }

    /**
     * @return the intCoCarLab
     */
    public int getIntCoCarLab() {
        return intCoCarLab;
    }

    /**
     * @param intCoCarLab the intCoCarLab to set
     */
    public void setIntCoCarLab(int intCoCarLab) {
        this.intCoCarLab = intCoCarLab;
    }

    /**
     * @return the arrLstTbhSueTra
     */
    public ArrayList<TbhSuetra> getArrLstTbhSueTra() {
        return arrLstTbhSueTra;
    }

    /**
     * @param arrLstTbhSueTra the arrLstTbhSueTra to set
     */
    public void setArrLstTbhSueTra(ArrayList<TbhSuetra> arrLstTbhSueTra) {
        this.arrLstTbhSueTra = arrLstTbhSueTra;
    }

    /**
     * @return the arrLstTbmSueTra
     */
    public ArrayList<TbmSuetra> getArrLstTbmSueTra() {
        return arrLstTbmSueTra;
    }

    /**
     * @param arrLstTbmSueTra the arrLstTbmSueTra to set
     */
    public void setArrLstTbmSueTra(ArrayList<TbmSuetra> arrLstTbmSueTra) {
        this.arrLstTbmSueTra = arrLstTbmSueTra;
    }

}

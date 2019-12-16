package Librerias.ZafRecHum.ZafRecHumPoj;

import java.math.BigInteger;

/**
 * Pojo contiene campos de la tabla tbr_traemp
 * @author Carlos Lainez
 * Guayaquil 17/06/2011
 */
public class Tbm_traemp implements Cloneable {
    
    private String strTx_Ide;
    private String strEmpleado;
    
    private int intCo_emp;
    private int intCo_tra;
    private int intCo_Ofi;
    private int intCo_Dep;
    private int intCo_Jef;
    private String strSt_HorFij;
    private int intCo_Hor;
    private String strSt_RecAlm;
    private int intCo_Car;
    private BigInteger bgNd_MinSecAsi;
    private String strTx_Obs1;
    private String strSt_Reg;
    
    private String strTx_TipModSue;
    private int intCo_Pla;
    private String strTx_NumCtaBan;
    private String strTx_TipCtaBan;
    
    
    

    /**
     * @return the intCo_emp
     */
    public int getIntCo_emp() {
        return intCo_emp;
    }

    /**
     * @param intCo_emp the intCo_emp to set
     */
    public void setIntCo_emp(int intCo_emp) {
        this.intCo_emp = intCo_emp;
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
     * @return the strSt_HirFij
     */
    public String getStrSt_HorFij() {
        return strSt_HorFij;
    }

    /**
     * @return the intCo_Hor
     */
    public int getIntCo_Hor() {
        return intCo_Hor;
    }

    /**
     * @param intCo_Hor the intCo_Hor to set
     */
    public void setIntCo_Hor(int intCo_Hor) {
        this.intCo_Hor = intCo_Hor;
    }

    /**
     * @param strSt_HirFij the strSt_HirFij to set
     */
    public void setStrSt_HirFij(String strSt_HorFij) {
        this.setStrSt_HorFij(strSt_HorFij);
    }

    /**
     * @return the intCo_Ofi
     */
    public int getIntCo_Ofi() {
        return intCo_Ofi;
    }

    /**
     * @param intCo_Ofi the intCo_Ofi to set
     */
    public void setIntCo_Ofi(int intCo_Ofi) {
        this.intCo_Ofi = intCo_Ofi;
    }

    /**
     * @return the intCo_Dep
     */
    public int getIntCo_Dep() {
        return intCo_Dep;
    }

    /**
     * @param intCo_Dep the intCo_Dep to set
     */
    public void setIntCo_Dep(int intCo_Dep) {
        this.intCo_Dep = intCo_Dep;
    }

    /**
     * @return the intCo_Jef
     */
    public int getIntCo_Jef() {
        return intCo_Jef;
    }

    /**
     * @param intCo_Jef the intCo_Jef to set
     */
    public void setIntCo_Jef(int intCo_Jef) {
        this.intCo_Jef = intCo_Jef;
    }

    /**
     * @param strSt_HorFij the strSt_HorFij to set
     */
    public void setStrSt_HorFij(String strSt_HorFij) {
        this.strSt_HorFij = strSt_HorFij;
    }

    /**
     * @return the strSt_RecAlm
     */
    public String getStrSt_RecAlm() {
        return strSt_RecAlm;
    }

    /**
     * @param strSt_RecAlm the strSt_RecAlm to set
     */
    public void setStrSt_RecAlm(String strSt_RecAlm) {
        this.strSt_RecAlm = strSt_RecAlm;
    }

    /**
     * @return the intCo_Car
     */
    public int getIntCo_Car() {
        return intCo_Car;
    }

    /**
     * @param intCo_Car the intCo_Car to set
     */
    public void setIntCo_Car(int intCo_Car) {
        this.intCo_Car = intCo_Car;
    }

    /**
     * @return the bgNd_MinSecAsi
     */
    public BigInteger getBgNd_MinSecAsi() {
        return bgNd_MinSecAsi;
    }

    /**
     * @param bgNd_MinSecAsi the bgNd_MinSecAsi to set
     */
    public void setBgNd_MinSecAsi(BigInteger bgNd_MinSecAsi) {
        this.bgNd_MinSecAsi = bgNd_MinSecAsi;
    }

    /**
     * @return the strTx_Obs1
     */
    public String getStrTx_Obs1() {
        return strTx_Obs1;
    }

    /**
     * @param strTx_Obs1 the strTx_Obs1 to set
     */
    public void setStrTx_Obs1(String strTx_Obs1) {
        this.strTx_Obs1 = strTx_Obs1;
    }

    /**
     * @return the strSt_Reg
     */
    public String getStrSt_Reg() {
        return strSt_Reg;
    }

    /**
     * @param strSt_Reg the strSt_Reg to set
     */
    public void setStrSt_Reg(String strSt_Reg) {
        this.strSt_Reg = strSt_Reg;
    }

    /**
     * @return the strTx_TipModSue
     */
    public String getStrTx_TipModSue() {
        return strTx_TipModSue;
    }

    /**
     * @param strTx_TipModSue the strTx_TipModSue to set
     */
    public void setStrTx_TipModSue(String strTx_TipModSue) {
        this.strTx_TipModSue = strTx_TipModSue;
    }

    /**
     * @return the intCo_Pla
     */
    public int getIntCo_Pla() {
        return intCo_Pla;
    }

    /**
     * @param intCo_Pla the intCo_Pla to set
     */
    public void setIntCo_Pla(int intCo_Pla) {
        this.intCo_Pla = intCo_Pla;
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
     * @return the strTx_TipCtaBan
     */
    public String getStrTx_TipCtaBan() {
        return strTx_TipCtaBan;
    }

    /**
     * @param strTx_TipCtaBan the strTx_TipCtaBan to set
     */
    public void setStrTx_TipCtaBan(String strTx_TipCtaBan) {
        this.strTx_TipCtaBan = strTx_TipCtaBan;
    }

    /**
     * @return the strTx_Ide
     */
    public String getStrTx_Ide() {
        return strTx_Ide;
    }

    /**
     * @param strTx_Ide the strTx_Ide to set
     */
    public void setStrTx_Ide(String strTx_Ide) {
        this.strTx_Ide = strTx_Ide;
    }

    /**
     * @return the strEmpleado
     */
    public String getStrEmpleado() {
        return strEmpleado;
    }

    /**
     * @param strEmpleado the strEmpleado to set
     */
    public void setStrEmpleado(String strEmpleado) {
        this.strEmpleado = strEmpleado;
    }

}

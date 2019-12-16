/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sistemas4
 */
public class ZafFac implements Serializable{
    
    private int     co_emp;
    private int     co_loc;
    private int     co_tipdoc;
    private int     co_doc;
    private int     fe_doc;
    private int     co_cli;
    private int     co_com; 
    private String   tx_ate,
                    tx_nomCli, 
                    tx_dirCli,  
                    tx_ruc, 
                    tx_telCli, 
                    tx_ciuCli, 
                    tx_nomven; 
    private int     ne_numDoc, 
                    ne_numCot;
    private String  tx_obs1, 
                    tx_obs2; 
    private double  nd_sub, 
                    nd_porIva, 
                    nd_tot,
                    nd_valiva; 
   private int      co_forPag;
    private String  tx_desforpag;
    private Date    fe_ing;
    private int     co_usrIng;
    private Date    fe_ultMod;
    private int     co_usrMod,
                    co_forret;
    private String  tx_vehret,
                    tx_choret;
    private String  st_reg;
    private int     ne_secgrp,
                    ne_secemp;
    private String  tx_numped;
    private String  st_regrep , 
                    st_tipdev, 
                    st_imp; 
    private int     co_mnu;
    private char    st_coninvtraaut, 
                    st_excDocConVenCon, 
                    st_coninv , 
                    st_creguirem ;
    
    /* campos de relacion solicitud de devolucion */
    
    private int     intCodEmpSolDev;
    private int     intCodLocSolDev;
    private int     intCodTipDocSolDev;/*co_tipdoc_solicituddev;*/
    private int     intCodDocSolDev;/*co_doc_solicituddev;*/
    
    /* campos de relacion solicitud de devolucion */
    
    private int     intTipPer;
    private int     ne_tipforpag;
    private int     co_empsol;
    private int     co_locrel;
    
    
    //private List<ZafDetalleFacturaBean>detalle=new ArrayList<ZafDetalleFacturaBean>();
    private List<ZafDetFac> detalle=new ArrayList<ZafDetFac>();

    public ZafFac(int co_emp, int co_loc, int co_tipdoc, int co_doc, int fe_doc, int co_cli, int co_com, String tx_ate, String tx_nomCli, String tx_dirCli, String tx_ruc, String tx_telCli, String tx_ciuCli, String tx_nomven, int ne_numDoc, int ne_numCot, String tx_obs1, String tx_obs2, double nd_sub, double nd_porIva, double nd_tot, double nd_valiva, int co_forPag, String tx_desforpag, Date fe_ing, int co_usrIng, Date fe_ultMod, int co_usrMod, int co_forret, String tx_vehret, String tx_choret, String st_reg, int ne_secgrp, int ne_secemp, String tx_numped, String st_regrep, String st_tipdev, String st_imp, int co_mnu, char st_coninvtraaut, char st_excDocConVenCon, char st_coninv, char st_creguirem) {
        this.co_emp = co_emp;
        this.co_loc = co_loc;
        this.co_tipdoc = co_tipdoc;
        this.co_doc = co_doc;
        this.fe_doc = fe_doc;
        this.co_cli = co_cli;
        this.co_com = co_com;
        this.tx_ate = tx_ate;
        this.tx_nomCli = tx_nomCli;
        this.tx_dirCli = tx_dirCli;
        this.tx_ruc = tx_ruc;
        this.tx_telCli = tx_telCli;
        this.tx_ciuCli = tx_ciuCli;
        this.tx_nomven = tx_nomven;
        this.ne_numDoc = ne_numDoc;
        this.ne_numCot = ne_numCot;
        this.tx_obs1 = tx_obs1;
        this.tx_obs2 = tx_obs2;
        this.nd_sub = nd_sub;
        this.nd_porIva = nd_porIva;
        this.nd_tot = nd_tot;
        this.nd_valiva = nd_valiva;
        this.co_forPag = co_forPag;
        this.tx_desforpag = tx_desforpag;
        this.fe_ing = fe_ing;
        this.co_usrIng = co_usrIng;
        this.fe_ultMod = fe_ultMod;
        this.co_usrMod = co_usrMod;
        this.co_forret = co_forret;
        this.tx_vehret = tx_vehret;
        this.tx_choret = tx_choret;
        this.st_reg = st_reg;
        this.ne_secgrp = ne_secgrp;
        this.ne_secemp = ne_secemp;
        this.tx_numped = tx_numped;
        this.st_regrep = st_regrep;
        this.st_tipdev = st_tipdev;
        this.st_imp = st_imp;
        this.co_mnu = co_mnu;
        this.st_coninvtraaut = st_coninvtraaut;
        this.st_excDocConVenCon = st_excDocConVenCon;
        this.st_coninv = st_coninv;
        this.st_creguirem = st_creguirem;
    }

    
    public ZafFac() {
    }
    
    
    
    public void anadeDetalle(ZafDetFac d){
    
        detalle.add(d);
    
    }//fin de anade
    
    

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

    public int getFe_doc() {
        return fe_doc;
    }

    public void setFe_doc(int fe_doc) {
        this.fe_doc = fe_doc;
    }

    public int getCo_cli() {
        return co_cli;
    }

    public void setCo_cli(int co_cli) {
        this.co_cli = co_cli;
    }

    public int getCo_com() {
        return co_com;
    }

    public void setCo_com(int co_com) {
        this.co_com = co_com;
    }

    public String getTx_ate() {
        return tx_ate;
    }

    public void setTx_ate(String tx_ate) {
        this.tx_ate = tx_ate;
    }

    public String getTx_nomCli() {
        return tx_nomCli;
    }

    public void setTx_nomCli(String tx_nomCli) {
        this.tx_nomCli = tx_nomCli;
    }

    public String getTx_dirCli() {
        return tx_dirCli;
    }

    public void setTx_dirCli(String tx_dirCli) {
        this.tx_dirCli = tx_dirCli;
    }

    public String getTx_ruc() {
        return tx_ruc;
    }

    public void setTx_ruc(String tx_ruc) {
        this.tx_ruc = tx_ruc;
    }

    public String getTx_telCli() {
        return tx_telCli;
    }

    public void setTx_telCli(String tx_telCli) {
        this.tx_telCli = tx_telCli;
    }

    public String getTx_ciuCli() {
        return tx_ciuCli;
    }

    public void setTx_ciuCli(String tx_ciuCli) {
        this.tx_ciuCli = tx_ciuCli;
    }

    public String getTx_nomven() {
        return tx_nomven;
    }

    public void setTx_nomven(String tx_nomven) {
        this.tx_nomven = tx_nomven;
    }

    public int getNe_numDoc() {
        return ne_numDoc;
    }

    public void setNe_numDoc(int ne_numDoc) {
        this.ne_numDoc = ne_numDoc;
    }

    public int getNe_numCot() {
        return ne_numCot;
    }

    public void setNe_numCot(int ne_numCot) {
        this.ne_numCot = ne_numCot;
    }

    public String getTx_obs1() {
        return tx_obs1;
    }

    public void setTx_obs1(String tx_obs1) {
        this.tx_obs1 = tx_obs1;
    }

    public String getTx_obs2() {
        return tx_obs2;
    }

    public void setTx_obs2(String tx_obs2) {
        this.tx_obs2 = tx_obs2;
    }

    public double getNd_sub() {
        return nd_sub;
    }

    public void setNd_sub(double nd_sub) {
        this.nd_sub = nd_sub;
    }

    public double getNd_porIva() {
        return nd_porIva;
    }

    public void setNd_porIva(double nd_porIva) {
        this.nd_porIva = nd_porIva;
    }

    public double getNd_tot() {
        return nd_tot;
    }

    public void setNd_tot(double nd_tot) {
        this.nd_tot = nd_tot;
    }

    public double getNd_valiva() {
        return nd_valiva;
    }

    public void setNd_valiva(double nd_valiva) {
        this.nd_valiva = nd_valiva;
    }

    public int getCo_forPag() {
        return co_forPag;
    }

    public void setCo_forPag(int co_forPag) {
        this.co_forPag = co_forPag;
    }

    public String getTx_desforpag() {
        return tx_desforpag;
    }

    public void setTx_desforpag(String tx_desforpag) {
        this.tx_desforpag = tx_desforpag;
    }

    public Date getFe_ing() {
        return fe_ing;
    }

    public void setFe_ing(Date fe_ing) {
        this.fe_ing = fe_ing;
    }

    public int getCo_usrIng() {
        return co_usrIng;
    }

    public void setCo_usrIng(int co_usrIng) {
        this.co_usrIng = co_usrIng;
    }

    public Date getFe_ultMod() {
        return fe_ultMod;
    }

    public void setFe_ultMod(Date fe_ultMod) {
        this.fe_ultMod = fe_ultMod;
    }

    public int getCo_usrMod() {
        return co_usrMod;
    }

    public void setCo_usrMod(int co_usrMod) {
        this.co_usrMod = co_usrMod;
    }

    public int getCo_forret() {
        return co_forret;
    }

    public void setCo_forret(int co_forret) {
        this.co_forret = co_forret;
    }

    public String getTx_vehret() {
        return tx_vehret;
    }

    public void setTx_vehret(String tx_vehret) {
        this.tx_vehret = tx_vehret;
    }

    public String getTx_choret() {
        return tx_choret;
    }

    public void setTx_choret(String tx_choret) {
        this.tx_choret = tx_choret;
    }

    public int getNe_secgrp() {
        return ne_secgrp;
    }

    public void setNe_secgrp(int ne_secgrp) {
        this.ne_secgrp = ne_secgrp;
    }

    public int getNe_secemp() {
        return ne_secemp;
    }

    public void setNe_secemp(int ne_secemp) {
        this.ne_secemp = ne_secemp;
    }

    public String getTx_numped() {
        return tx_numped;
    }

    public void setTx_numped(String tx_numped) {
        this.tx_numped = tx_numped;
    }


    public String getSt_tipdev() {
        return st_tipdev;
    }

    public void setSt_tipdev(String st_tipdev) {
        this.st_tipdev = st_tipdev;
    }

    public String getSt_imp() {
        return st_imp;
    }

    public void setSt_imp(String st_imp) {
        this.st_imp = st_imp;
    }

    public int getCo_mnu() {
        return co_mnu;
    }

    public void setCo_mnu(int co_mnu) {
        this.co_mnu = co_mnu;
    }

    public char getSt_coninvtraaut() {
        return st_coninvtraaut;
    }

    public void setSt_coninvtraaut(char st_coninvtraaut) {
        this.st_coninvtraaut = st_coninvtraaut;
    }

    public char getSt_excDocConVenCon() {
        return st_excDocConVenCon;
    }

    public void setSt_excDocConVenCon(char st_excDocConVenCon) {
        this.st_excDocConVenCon = st_excDocConVenCon;
    }

    public char getSt_coninv() {
        return st_coninv;
    }

    public void setSt_coninv(char st_coninv) {
        this.st_coninv = st_coninv;
    }

    public char getSt_creguirem() {
        return st_creguirem;
    }

    public void setSt_creguirem(char st_creguirem) {
        this.st_creguirem = st_creguirem;
    }

    public List<ZafDetFac> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<ZafDetFac> detalle) {
        this.detalle = detalle;
    }

    public int getIntCodEmpSolDev() {
        return intCodEmpSolDev;
    }

    public void setIntCodEmpSolDev(int intCodEmpSolDev) {
        this.intCodEmpSolDev = intCodEmpSolDev;
    }

    public int getIntCodLocSolDev() {
        return intCodLocSolDev;
    }

    public void setIntCodLocSolDev(int intCodLocSolDev) {
        this.intCodLocSolDev = intCodLocSolDev;
    }

    public int getIntCodTipDocSolDev() {
        return intCodTipDocSolDev;
    }

    public void setIntCodTipDocSolDev(int intCodTipDocSolDev) {
        this.intCodTipDocSolDev = intCodTipDocSolDev;
    }

    public int getIntCodDocSolDev() {
        return intCodDocSolDev;
    }

    public void setIntCodDocSolDev(int intCodDocSolDev) {
        this.intCodDocSolDev = intCodDocSolDev;
    }

    public String getSt_reg() {
        return st_reg;
    }

    public void setSt_reg(String st_reg) {
        this.st_reg = st_reg;
    }

    public String getSt_regrep() {
        return st_regrep;
    }

    public void setSt_regrep(String st_regrep) {
        this.st_regrep = st_regrep;
    }

    public int getIntTipPer() {
        return intTipPer;
    }

    public void setIntTipPer(int intTipPer) {
        this.intTipPer = intTipPer;
    }

    public int getNe_tipforpag() {
        return ne_tipforpag;
    }

    public void setNe_tipforpag(int ne_tipforpag) {
        this.ne_tipforpag = ne_tipforpag;
    }

    public int getCo_empsol() {
        return co_empsol;
    }

    public void setCo_empsol(int co_empsol) {
        this.co_empsol = co_empsol;
    }

    public int getCo_locrel() {
        return co_locrel;
    }

    public void setCo_locrel(int co_locrel) {
        this.co_locrel = co_locrel;
    }
    
    
    
    
    
}//fin de clase

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafEstFacEle;

import java.io.Serializable;

/**
 *
 * @author: Dennis Betancourt
 */
public class ZafCabEstFacEle implements Serializable
{
    String strNomTbl;
    int intCodEmp, intCodLoc, intCodTipDoc, intCodDoc;
    
    public ZafCabEstFacEle()
    {
    
    }

    public int getCodEmp()
    {
       return intCodEmp;
    }

    public void setCodEmp(int codEmp)
    {
       this.intCodEmp = codEmp;
    }
    
    public int getCodLoc()
    {
       return intCodLoc;
    }

    public void setCodLoc(int codLoc)
    {
       this.intCodLoc = codLoc;
    }
    
    public int getCodTipDoc()
    {
       return intCodTipDoc;
    }

    public void setCodTipDoc(int codTipDoc)
    {
       this.intCodTipDoc = codTipDoc;
    }
    
    public int getCodDoc()
    {
       return intCodDoc;
    }

    public void setCodDoc(int codDoc)
    {
       this.intCodDoc = codDoc;
    }
    
    public String getNomTbl() {
        return strNomTbl;
    }

    public void setNomTbl(String nomTbl) {
        this.strNomTbl = nomTbl;
    }
}
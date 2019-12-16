/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * v0.2
 */

package CxC.ZafCxC23;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Eddye Lino
 */
public class ZafCxC23_04_01 extends JScrollPane
{
    private JTextArea txaObsCxC;
    private int intCodEmp;
    private String strNomEmp;
    private int intCodCli;
    private String strObsCxCSinMod;
    
    public ZafCxC23_04_01()
    {
        txaObsCxC=new JTextArea();
        txaObsCxC.setLineWrap(true);
        this.setViewportView(txaObsCxC);
    }

    public int getCodEmp()
    {
        return intCodEmp;
    }

    public void setCodEmp(int intCodEmp)
    {
        this.intCodEmp=intCodEmp;
    }
    
    public String getNomEmp()
    {
        return strNomEmp;
    }

    public void setNomEmp(String strNomEmp)
    {
        this.strNomEmp=strNomEmp;
    }

    public int getCodCli()
    {
        return intCodCli;
    }

    public void setCodCli(int intCodCli)
    {
        this.intCodCli=intCodCli;
    }
    
    public String getObsCxC()
    {
        return txaObsCxC.getText();
    }

    public void setObsCxC(String strObsCxC)
    {
        if (strObsCxC==null)
            strObsCxC="";
        txaObsCxC.setText(strObsCxC);
        strObsCxCSinMod=strObsCxC;
    }

    public String getObsCxCSinMod()
    {
        return strObsCxCSinMod;
    }
    
}

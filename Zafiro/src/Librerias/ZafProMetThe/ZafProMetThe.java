/*
 * ZafProMetTem.java
 *
 * Created on 4 de marzo de 2007, 23:24
 * v0.1
 */

package Librerias.ZafProMetThe;
import Librerias.ZafParSis.ZafParSis;
import javax.swing.plaf.ColorUIResource;

/**
 *
 * @author  elino
 */
public class ZafProMetThe extends javax.swing.plaf.metal.DefaultMetalTheme
{
    //Variables
    private ZafParSis objParSis;
    private String strRutTem;                           //Ruta y nombre del archivo a cargar.
    private String strNomTem;                           //Nombre del tema.
    private ColorUIResource objColPri1;                 //Color primario1 del tema.
    private ColorUIResource objColPri2;                 //Color primario2 del tema.
    private ColorUIResource objColPri3;                 //Color primario3 del tema.
    private ColorUIResource objColSec1;                 //Color secundario1 del tema.
    private ColorUIResource objColSec2;                 //Color secundario2 del tema.
    private ColorUIResource objColSec3;                 //Color secundario3 del tema.
    private ColorUIResource objColBla;                  //Color blanco del tema.
    private ColorUIResource objColNeg;                  //Color negro del tema.
    
    /** Creates a new instance of ZafProMetTem 
      * tema: Nombre del tema a cargar
     */
    public ZafProMetThe(ZafParSis obj, String tema)
    {
        //Inicializar objetos.
        objParSis=obj;
        if (tema!=null)
        {
            if (!tema.equals(""))
            {
                if (objParSis.getTipoRutaReportes().equals("R"))
                    strRutTem=objParSis.getDirectorioSistema() + "/Temas/" + tema;
                else
                    strRutTem=tema;
//                System.out.println("strRutTem: " + strRutTem);
                iniProMetTem();
                cargarTem();
            }
        }
//        strRutTem=tema;
//        strRutTem="D:\\Por respaldar\\Eddye\\Zafiro\\Zafiro_desarrollo\\Temas\\ZafTem01.theme";
//        strRutTem="D:\\Eddye\\Desarrollo\\Java\\Zafiro_desarrollo\\Temas\\ZafTem00.theme";
//        iniProMetTem();
//        cargarTem();
    }
    
    /**
     * Inicializa la clase con los valores predeterminados de la clase "DefaultMetalTheme". Es decir,
     * inicializa nuestra clase con la configuración predeterminada de la clase "DefaultMetalTheme".
     */
    private void iniProMetTem()
    {
        objColPri1=super.getPrimary1();
//        System.out.println("objColPri1: " + objColPri1);
        objColPri2=super.getPrimary2();
//        System.out.println("objColPri2: " + objColPri2);
        objColPri3=super.getPrimary3();
//        System.out.println("objColPri3: " + objColPri3);
        objColSec1=super.getSecondary1();
//        System.out.println("getSecondary1: " + objColSec1);
        objColSec2=super.getSecondary2();
//        System.out.println("getSecondary2: " + objColSec2);
        objColSec3=super.getSecondary3();
//        System.out.println("getSecondary3: " + objColSec3);
        objColBla=super.getWhite();
//        System.out.println("objColBla: " + objColBla);
        objColNeg=super.getBlack();
//        System.out.println("objColNeg: " + objColNeg);
    }
    
    /*Cargar tema.*/
    private void cargarTem()
    {
        try
        {
            //Leer archivo de configuración "ZafParSis.properties".
            java.util.Properties proArc=new java.util.Properties();
            java.io.FileInputStream fis=new java.io.FileInputStream(strRutTem);
            proArc.load(fis);
            fis.close();
            //Leer las propiedades.
            strNomTem=proArc.getProperty("strNomTem");
            objColPri1=parseColor(proArc.getProperty("ColorPrimario1"));
            objColPri2=parseColor(proArc.getProperty("ColorPrimario2"));
            objColPri3=parseColor(proArc.getProperty("ColorPrimario3"));
            objColSec1=parseColor(proArc.getProperty("ColorSecundario1"));
            objColSec2=parseColor(proArc.getProperty("ColorSecundario2"));
            objColSec3=parseColor(proArc.getProperty("ColorSecundario3"));
            objColBla=parseColor(proArc.getProperty("ColorBlanco"));
            objColNeg=parseColor(proArc.getProperty("ColorNegro"));
           
        }
        catch (java.io.IOException e)
        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(null, e);
        }
    }
    
    public String getName() { return strNomTem; }

    protected ColorUIResource getPrimary1() { return objColPri1; }
    protected ColorUIResource getPrimary2() { return objColPri2; }
    protected ColorUIResource getPrimary3() { return objColPri3; }

    protected ColorUIResource getSecondary1() { return objColSec1; }
    protected ColorUIResource getSecondary2() { return objColSec2; }
    protected ColorUIResource getSecondary3() { return objColSec3; }

    protected ColorUIResource getBlack() { return objColNeg; }
    protected ColorUIResource getWhite() { return objColBla; }
    
    public void setPrimary1(String color)
    {
        objColPri1=parseColor(color);
    }
    
    public void setPrimary2(String color)
    {
        objColPri2=parseColor(color);
    }
    
    public void setPrimary3(String color)
    {
        objColPri3=parseColor(color);
    }
    
    public void setSecondary1(String color)
    {
        objColSec1=parseColor(color);
    }
    
    public void setSecondary2(String color)
    {
        objColSec2=parseColor(color);
    }
    
    public void setSecondary3(String color)
    {
        objColSec3=parseColor(color);
    }
    
    public void setWhite(String color)
    {
        objColBla=parseColor(color);
    }
    
    public void setBlack(String color)
    {
        objColNeg=parseColor(color);
    }
    
    /**
      * parse a comma delimited list of 3 strings into a Color
      */
    private ColorUIResource parseColor(String s) {
        int red = 0;
	int green = 0;
	int blue = 0;
	try {
	    java.util.StringTokenizer st = new java.util.StringTokenizer(s, ",");

	    red = Integer.parseInt(st.nextToken());
	    green = Integer.parseInt(st.nextToken());
	    blue = Integer.parseInt(st.nextToken());

	} catch (Exception e) {
	    System.out.println(e);
	    System.out.println("Couldn't parse color :" + s);
	}

	return new ColorUIResource(red, green, blue);
    }
    
}

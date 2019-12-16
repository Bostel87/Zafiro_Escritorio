/*
 * ZafGlaPanLbl.java
 *
 * Created on 30 de septiembre de 2005, 12:24 PM
 */

package Librerias.ZafToolBar;

/**
 * Esta clase utiliza un JLabel para mostrar mensajes en un GlassPane. La idea inicial es
 * presentar una etiqueta con el mensaje "ANULADO" en los diferentes documentos que genera
 * el sistema. Por ejemplo, si una factura está anulada se mostrará una etiqueta de letras
 * rojas con el texto "ANULADO" de manera que sea más sencillo identificar si un documento
 * está anulado.
 * @author  Eddye Lino
 */
public class ZafGlaPanLbl extends javax.swing.JLabel
{
    
    /** Crea una nueva instancia de la clase ZafGlaPanLbl */
    public ZafGlaPanLbl()
    {
        this.setText("");
        this.setFont(new java.awt.Font("Arial Black", 1, 40));
        this.setForeground(new java.awt.Color(255, 0, 0));
        this.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    }
 
}

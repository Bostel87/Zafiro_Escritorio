/*
 * ZafConMem.java
 *
 * Created on 2 de julio de 2006, 12:36 PM
 * v0.1
 */

package Librerias.ZafConMem;
import javax.swing.JProgressBar;
import javax.swing.JButton;

/**
 * Esta clase muestra el consumo de memoria de nuestra aplicación.
 * Contiene los siguientes elementos:
 * <UL>
 * <LI>JProgressBar: Muestra el consumo de memoria de la aplicación.
 * <LI>JButton: Llama al "Garbage Collector".
 * </UL>
 * @author  Eddye Lino
 */
public class ZafConMem extends javax.swing.JPanel
{
    private JProgressBar pgrConMem;
    private JButton butGarCol;
    
    /** Creates a new instance of ZafConMem */
    public ZafConMem()
    {
        //Configurar el JPanel.
        this.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        this.setLayout(new java.awt.BorderLayout());
        this.setPreferredSize(new java.awt.Dimension(140, 20));
        //Configurar el JProgressBar.
        pgrConMem=new JProgressBar();
        pgrConMem.setBorderPainted(false);
        pgrConMem.setStringPainted(true);
        pgrConMem.setBackground(new java.awt.Color(255, 255, 255));
        pgrConMem.setForeground(new java.awt.Color(0, 0, 0));
        //Configurar el JButton.
        butGarCol=new JButton("...");
        butGarCol.setToolTipText("Liberar memoria");
        butGarCol.setPreferredSize(new java.awt.Dimension(20, 20));
        butGarCol.setFocusPainted(false);
        butGarCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGarColActionPerformed(evt);
            }
        });
        //Adicionar objetos al JDialog.
        this.add(pgrConMem, java.awt.BorderLayout.CENTER);
        this.add(butGarCol, java.awt.BorderLayout.EAST);
        //Crear y correr el hilo que controla la memoria.
        ZafThrConMem objThrConMem=new ZafThrConMem();
        objThrConMem.start();
    }
    
    /**
     * Esta función le sugiere al "Garbage Collector" que libere la memoria.
     */
    private void butGarColActionPerformed(java.awt.event.ActionEvent evt)
    {
        System.gc();
    }
    
    /**
     * Esta clase crea un hilo que muestra el consumo de memoria en un JProgressBar.
     */
    private class ZafThrConMem extends Thread
    {
        private static final float KB=1024.0f;
        private static final float MB=1048576.0f;
        private static final String SKB=" KB";
        private static final String SMB=" MB";
        private static final String SLASH=" / ";
        private float heap=Runtime.getRuntime().freeMemory();
        private float total=Runtime.getRuntime().totalMemory();
        private float divisor=KB,remain=KB;
        private int itotal=0,iremain=0;
        private String sufix=null,sremain=null,stotal=null;
        
        public ZafThrConMem()
        {

        }
        
        public void run()
        {
            try
            {
                while (true)
                {
                    heap=Runtime.getRuntime().freeMemory();
                    total=Runtime.getRuntime().totalMemory();

                    divisor=(total<MB)?KB:MB;
                    sufix=(total<MB)?SKB:SMB;

                    heap=heap/divisor;
                    total=total/divisor;
                    remain=total-heap;

                    itotal=Math.round(total*1000);
                    iremain=Math.round(remain*1000);

                    sremain=String.valueOf(remain);
                    stotal=String.valueOf(total);

                    pgrConMem.setMaximum(itotal);
                    pgrConMem.setValue(iremain);
                    pgrConMem.setString(new StringBuffer(sremain.substring(0, sremain.indexOf('.')+2)).append(SLASH).
                    append(stotal.substring(0, stotal.indexOf('.')+2)).append(sufix).toString());

                    Thread.sleep(5000);
                }
            }
            catch (InterruptedException e)
            {
                System.out.println("Excepción: " + e.toString());
            }
        }
    }
    
}

/*
 * ZafNotAle => Presenta Notificaciones de Alertas
 *
 * Created on Nov 10 2016, 16:00 PM
 */
package Librerias.ZafNotAle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JProgressBar;

public class ZafNotAle extends javax.swing.JPanel 
{
    private ZafParSis objParSis;
    private ZafNotHB objNotHB;
    private JProgressBar pgrConMem;
    private JButton butGarCol;
    private ZafUtil objUti;
    java.awt.Frame parent2;
    javax.swing.JDesktopPane dskGen;
    private int intEstConVenCon = 0;
    private int intCodUsr = 0;
    private String strVer = " v0.1 ";

    /**
     * Creates a new instance of ZafNotAle
     */
    public ZafNotAle(ZafParSis obj, Frame parent) 
    {
        try 
        {
            objParSis = obj;
            parent2 = parent;
            objUti = new ZafUtil();  
            objNotHB = new ZafNotHB(parent2, false, objParSis, dskGen);
        }
        catch (Exception e) {    objUti.mostrarMsgErr_F1(this, e);    }
    }

    public ZafNotAle(ZafParSis obj, java.awt.Frame parent, javax.swing.JDesktopPane dskGe) 
    {
        try 
        {
            objParSis = obj;
            parent2 = parent;
            dskGen = dskGe;
            this.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
            this.setLayout(new java.awt.BorderLayout());
            this.setPreferredSize(new java.awt.Dimension(140, 20));

            objUti = new ZafUtil();
            intCodUsr = objParSis.getCodigoUsuario();

            //Configurar el JProgressBar.
            pgrConMem = new JProgressBar();
            pgrConMem.setBorderPainted(false);
            pgrConMem.setStringPainted(true);
            pgrConMem.setBackground(new java.awt.Color(255, 255, 255));
            pgrConMem.setForeground(new java.awt.Color(0, 0, 0));
            //Configurar el JButton.
            butGarCol = new JButton("...");
            butGarCol.setToolTipText("Liberar memoria");
            butGarCol.setPreferredSize(new java.awt.Dimension(20, 20));
            butGarCol.setFocusPainted(false);
            butGarCol.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    butGarColActionPerformed(evt);
                }
            });
            
            //Crear y correr el hilo que controla la memoria.
            ZafThrConMem objThrConMem = new ZafThrConMem();
            objThrConMem.start();
            
            //Muestra todas las notificaciones de alerta.
            mostrarNotificaciones();
        } 
        catch (Exception e) {     objUti.mostrarMsgErr_F1(this, e);       }

    }

    /**
     * Esta función le sugiere al "Garbage Collector" que libere la memoria.
     */
    private void butGarColActionPerformed(java.awt.event.ActionEvent evt) {
        //System.gc();
        Runtime.getRuntime().gc();
    }

    /**
     * Esta clase crea un hilo que muestra el consumo de memoria en un
     * JProgressBar.
     */
    private class ZafThrConMem extends Thread 
    {
        private static final float KB = 1024.0f;
        private static final float MB = 1048576.0f;
        private static final String SKB = " KB";
        private static final String SMB = " MB";
        private static final String SLASH = " / ";
        private float heap = Runtime.getRuntime().freeMemory();
        private float total = Runtime.getRuntime().totalMemory();
        private float divisor = KB, remain = KB;
        private int itotal = 0, iremain = 0;
        private String sufix = null, sremain = null, stotal = null;

        public ZafThrConMem() {
        }

        @Override
        public void run() 
        {
            try {
                while (true) {
                    heap = Runtime.getRuntime().freeMemory();
                    total = Runtime.getRuntime().totalMemory();

                    divisor = (total < MB) ? KB : MB;
                    sufix = (total < MB) ? SKB : SMB;

                    heap = heap / divisor;
                    total = total / divisor;
                    remain = total - heap;

                    itotal = Math.round(total * 1000);
                    iremain = Math.round(remain * 1000);

                    sremain = String.valueOf(remain);
                    stotal = String.valueOf(total);

                    pgrConMem.setMaximum(itotal);
                    pgrConMem.setValue(iremain);
                    pgrConMem.setString(new StringBuffer(sremain.substring(0, sremain.indexOf('.') + 2)).append(SLASH).
                            append(stotal.substring(0, stotal.indexOf('.') + 2)).append(sufix).toString());

                    Thread.sleep(5000);
                }
            }
            catch (InterruptedException e) {    System.out.println("Excepción: " + e.toString());          }
        }
    }
    
    private void mostrarNotificaciones()
    {
        try 
        {
            //Alerta de Cumpleaños
            objNotHB = new ZafNotHB(parent2, false, objParSis, dskGen);
            if(objNotHB.isUsrHappyBirthday(intCodUsr))
            {
                cargarImagenHappyBirthday();
            }
            objNotHB=null;
            
            
        } 
        catch (Exception e) {    }
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="/* Notificaciones de Cumpleaños */">
    /* Carga imagen de cumpleaños.*/
    private void cargarImagenHappyBirthday() 
    {
        try 
        {
            if (intEstConVenCon == 0) 
            {
                ZafThrHappyBirthdayUsr objThrHappyBirthdayUsr = new ZafThrHappyBirthdayUsr();
                objThrHappyBirthdayUsr.start();
                intEstConVenCon = 1;
            }
        } 
        catch (Exception e) {    }
    }

    private class ZafThrHappyBirthdayUsr extends Thread 
    {
        public ZafThrHappyBirthdayUsr() 
        {
        }

        @Override
        public void run() 
        {
            ZafNotHB objNotHB = new ZafNotHB(parent2, false, objParSis, dskGen);
            objNotHB.setVisible(true);
            objNotHB=null;            
        }
    }
    //</editor-fold>

    
}

/*
 * ZafCxC32_01.java
 *
 * Created on 21 de junio de 2007, 17:20
 */

package CxC.ZafCxC32;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafToolBar.ZafToolBar;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafPopupMenu.ZafPopupMenu;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafColNumerada.ZafColNumerada;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg; //para la ventana de dialogo
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;

///para el boton abrir archivos///
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.File;

///Para llamar a la ventana ZafCxC27
import CxC.ZafCxC27.ZafCxC27;

/**
 *
 * @author  root
 */
public class ZafCxC32_01 extends javax.swing.JDialog {
    
    /** Creates new form ZafCxC32_01 */
    public ZafCxC32_01(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        panCon = new javax.swing.JPanel();
        spnCon = new javax.swing.JScrollPane();
        tblCon = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        panCon.setLayout(new java.awt.BorderLayout());

        panCon.setMinimumSize(new java.awt.Dimension(183, 145));
        panCon.setPreferredSize(new java.awt.Dimension(458, 576));
        tblCon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnCon.setViewportView(tblCon);

        panCon.add(spnCon, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCon, java.awt.BorderLayout.CENTER);

        pack();
    }//GEN-END:initComponents
    
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        new ZafCxC32_01(new javax.swing.JFrame(), true).show();
//    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panCon;
    private javax.swing.JScrollPane spnCon;
    private javax.swing.JTable tblCon;
    // End of variables declaration//GEN-END:variables
    
}

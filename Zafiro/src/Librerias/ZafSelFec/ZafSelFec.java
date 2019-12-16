/*
 * ZafSelFec.java
 *
 * Created on 30 de marzo de 2007, 09:06 PM
 * v0.3
 */

package Librerias.ZafSelFec;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafDate.ZafDatePicker;

/**
 * Esta clase agrupa una serie de elementos que permiten manipular de manera más sencilla las fechas.
 * Por medio del selector de fechas es posible seleccionar un rango de fechas, las fechas menores o 
 * iguales a la fecha especificada en el "Hasta", las fechas mayores o iguales a la fecha especificada
 * en el "Desde" o simplemente seleccionar todas las fechas.
 * @author  Eddye Lino
 */
public class ZafSelFec extends javax.swing.JPanel implements java.io.Serializable
{
    //Constantes:
    public static final int INT_SEL_RNG=0;              /**Un valor para setTipoSeleccion: Indica "Rango de fechas".*/
    public static final int INT_SEL_MEN=1;              /**Un valor para setTipoSeleccion: Indica "Fechas menores o iguales que 'Hasta'".*/
    public static final int INT_SEL_MAY=2;              /**Un valor para setTipoSeleccion: Indica "Fechas mayores o iguales que 'Desde'".*/
    public static final int INT_SEL_TOD=3;              /**Un valor para setTipoSeleccion: Indica "Todas las fechas".*/
    //Variables.
    private ZafUtil objUti;
    private ZafTblMod objTblMod;                        //Modelo del JTable.
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafDatePicker dtpFecDes, dtpFecHas;
    //Variables de la clase.
    private int intTipSel;                              //Tipo de selección.
    private String strForFec;                           //Formato de fecha que se utilizará en los ZafDatePicker.
    
    /**
     * Este constructor crea una nueva instancia de la clase ZafSelFec.
     */
    public ZafSelFec()
    {
        initComponents();
        //Inicializar objetos.
        configurarFrm();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        chkSelFec = new javax.swing.JCheckBox();
        lblDes = new javax.swing.JLabel();
        lblHas = new javax.swing.JLabel();
        lblAni = new javax.swing.JLabel();
        spiAni = new javax.swing.JSpinner();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        chkFleIzq = new javax.swing.JCheckBox();
        chkFleDer = new javax.swing.JCheckBox();
        lblFleIzq = new javax.swing.JLabel();
        lblFleDer = new javax.swing.JLabel();

        setLayout(null);

        setBorder(new javax.swing.border.TitledBorder("      Rango de fechas"));
        chkSelFec.setSelected(true);
        chkSelFec.setFocusable(false);
        chkSelFec.setOpaque(false);
        add(chkSelFec);
        chkSelFec.setBounds(4, -2, 464, 20);

        lblDes.setText("Desde:");
        add(lblDes);
        lblDes.setBounds(64, 20, 60, 20);

        lblHas.setText("Hasta:");
        add(lblHas);
        lblHas.setBounds(212, 20, 60, 20);

        lblAni.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAni.setText("A\u00f1o");
        add(lblAni);
        lblAni.setBounds(408, 20, 56, 20);

        spiAni.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spiAniStateChanged(evt);
            }
        });

        add(spiAni);
        spiAni.setBounds(408, 44, 56, 20);

        spnDat.setPreferredSize(new java.awt.Dimension(454, 18));
        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnDat.setViewportView(tblDat);

        add(spnDat);
        spnDat.setBounds(32, 44, 348, 20);

        chkFleIzq.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        chkFleIzq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/FleIzq_3_16x16_nor.gif")));
        chkFleIzq.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/FleIzq_3_16x16_sob.gif")));
        chkFleIzq.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/FleIzq_3_16x16_act.gif")));
        chkFleIzq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFleIzqActionPerformed(evt);
            }
        });

        add(chkFleIzq);
        chkFleIzq.setBounds(8, 44, 24, 20);

        chkFleDer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        chkFleDer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/FleDer_3_16x16_nor.gif")));
        chkFleDer.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/FleDer_3_16x16_sob.gif")));
        chkFleDer.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/FleDer_3_16x16_act.gif")));
        chkFleDer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFleDerActionPerformed(evt);
            }
        });

        add(chkFleDer);
        chkFleDer.setBounds(380, 44, 24, 20);

        lblFleIzq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/FleIzq_4_168x16_nor.gif")));
        add(lblFleIzq);
        lblFleIzq.setBounds(32, 20, 172, 20);

        lblFleDer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/FleDer_4_168x16_nor.gif")));
        add(lblFleDer);
        lblFleDer.setBounds(212, 20, 172, 20);

    }//GEN-END:initComponents

    private void spiAniStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spiAniStateChanged
        seleccionarRanMes();
    }//GEN-LAST:event_spiAniStateChanged

    private void chkFleDerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFleDerActionPerformed
        seleccionarFleDer();
    }//GEN-LAST:event_chkFleDerActionPerformed

    private void chkFleIzqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFleIzqActionPerformed
        seleccionarFleIzq();
    }//GEN-LAST:event_chkFleIzqActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkFleDer;
    private javax.swing.JCheckBox chkFleIzq;
    private javax.swing.JCheckBox chkSelFec;
    private javax.swing.JLabel lblAni;
    private javax.swing.JLabel lblDes;
    private javax.swing.JLabel lblFleDer;
    private javax.swing.JLabel lblFleIzq;
    private javax.swing.JLabel lblHas;
    private javax.swing.JSpinner spiAni;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            intTipSel=INT_SEL_RNG;
            strForFec="dd/MM/yyyy";
            //Configurar el JSpinner:
            java.util.Calendar cal=java.util.Calendar.getInstance();
            spiAni.setModel(new javax.swing.SpinnerNumberModel(cal.get(java.util.Calendar.YEAR), 0, 9999, 1));
            spiAni.setEditor(new javax.swing.JSpinner.NumberEditor(spiAni, "#"));

            //Configurar ZafDatePicker:
            dtpFecDes=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            this.add(dtpFecDes);
            dtpFecDes.setBounds(114, 20, 90, 20);
            
            dtpFecHas=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            this.add(dtpFecHas);
            dtpFecHas.setBounds(262, 20, 90, 20);
            //Configurar objetos.
            lblFleIzq.setVisible(false);
            lblFleDer.setVisible(false);
            //Configurar los JTables.
            configurarTblDat();
            cargarDetReg();
            //Establecer Enero como mes predeterminado.
            tblDat.setRowSelectionInterval(0, 0);
        }
        catch(Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            objTblMod=new ZafTblMod();
            objTblMod.setSize(1, 12);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setCellSelectionEnabled(true);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
            //Configurar JTable: Establecer el alto de la fila.
            tblDat.setRowHeight(0, 18);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            for (int i=0; i<12; i++)
                tcmAux.getColumn(i).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnDat.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            spnDat.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            tblDat.setTableHeader(null);
            //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsm=tblDat.getColumnModel().getSelectionModel();
            lsm.addListSelectionListener(new ZafLisSelLis());
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite carga los meses en el JTable.
     * @return true: Si se pudo cargar los meses.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        try
        {
            objTblMod.setValueAt("Ene", 0, 0);
            objTblMod.setValueAt("Feb", 0, 1);
            objTblMod.setValueAt("Mar", 0, 2);
            objTblMod.setValueAt("Abr", 0, 3);
            objTblMod.setValueAt("May", 0, 4);
            objTblMod.setValueAt("Jun", 0, 5);
            objTblMod.setValueAt("Jul", 0, 6);
            objTblMod.setValueAt("Ago", 0, 7);
            objTblMod.setValueAt("Sep", 0, 8);
            objTblMod.setValueAt("Oct", 0, 9);
            objTblMod.setValueAt("Nov", 0, 10);
            objTblMod.setValueAt("Dic", 0, 11);
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función obtiene el título del selector de fechas.
     * @return El título del selector de fechas.
     */
    public String getTitulo()
    {
        if (chkSelFec.isVisible())
            return ((javax.swing.border.TitledBorder)this.getBorder()).getTitle().substring(6);
        else
            return ((javax.swing.border.TitledBorder)this.getBorder()).getTitle();
    }
    
    /**
     * Esta función establece el título del selector de fechas.
     * @param titulo El título del selector de fechas.
     */
    public void setTitulo(String titulo)
    {
        if (chkSelFec.isVisible())
            ((javax.swing.border.TitledBorder)this.getBorder()).setTitle("      " + titulo);
        else
            ((javax.swing.border.TitledBorder)this.getBorder()).setTitle(titulo);
    }
    
    /**
     * Esta función determina si la casilla de verificación del selector de fechas está marcada.
     * @return true: Si la casilla de verificación está marcada.
     * <BR>false: En el caso contrario.
     */
    public boolean isCheckBoxChecked()
    {
        return chkSelFec.isSelected();
    }
    
    /**
     * Esta función establece si se debe marcar/desmarcar la casilla de verificación del selector de fechas.
     * @param checked Valor booleano que determina si se debe marcar/desmarcar la casilla de verificación.
     */
    public void setCheckBoxChecked(boolean checked)
    {
        chkSelFec.setSelected(checked);
    }
    
    /**
     * Esta función determina si la casilla de verificación del selector de fechas es visible.
     * @return true: Si la casilla de verificación es visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isCheckBoxVisible()
    {
        return chkSelFec.isVisible();
    }
    
    /**
     * Esta función establece si debe mostrar la casilla de verificación del selector de fechas.
     * @param visible Valor booleano que determina si se debe mostrar la casilla de verificación.
     */
    public void setCheckBoxVisible(boolean visible)
    {
        if (chkSelFec.isVisible())
        {
            if (!visible)
                ((javax.swing.border.TitledBorder)this.getBorder()).setTitle(((javax.swing.border.TitledBorder)this.getBorder()).getTitle().substring(6));
        }
        else
        {
            if (visible)
                ((javax.swing.border.TitledBorder)this.getBorder()).setTitle("      " + ((javax.swing.border.TitledBorder)this.getBorder()).getTitle());
        }
        chkSelFec.setVisible(visible);
    }
    
    /**
     * Esta función obtiene el tipo de selección establecido en el selector de fechas.
     * @return El tipo de selección establecido en el selector de fechas.
     * <BR>Puede retornar uno de los siguientes valores:
     * <UL>
     * <LI>0: Rango de fechas. Es decir, fechas mayores o iguales que "Desde" y menores o iguales que "Hasta".
     * <LI>1: Fechas menores o iguales que "Hasta".
     * <LI>2: Fechas mayores o iguales que "Desde".
     * <LI>3: Todo. Es decir, no hay restricción por fechas.
     * </UL>
     */
    public int getTipoSeleccion()
    {
        return intTipSel;
    }
    
    /**
     * Esta función establece el tipo de selección del selector de fechas.
     * @param tipo El tipo de selección del selector de fechas.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Rango de fechas. Es decir, fechas mayores o iguales que "Desde" y menores o iguales que "Hasta".
     * <LI>1: Fechas menores o iguales que "Hasta".
     * <LI>2: Fechas mayores o iguales que "Desde".
     * <LI>3: Todo. Es decir, no hay restricción por fechas.
     * </UL>
     */
    public void setTipoSeleccion(int tipo)
    {
        intTipSel=tipo;
    }
    
    /**
     * Esta función obtiene la fecha "Desde".
     * @return La fecha que se encuentra en el "Desde".
     */
    public String getFechaDesde()
    {
        return dtpFecDes.getText();
    }
    
    /**
     * Esta función establece la fecha "Desde".
     * @param fecha La fecha a establecer en el "Desde".
     */
    public void setFechaDesde(String fecha)
    {
        dtpFecDes.setText(fecha);
    }
    
    /**
     * Esta función obtiene la fecha "Hasta".
     * @return La fecha que se encuentra en el "Hasta".
     */
    public String getFechaHasta()
    {
        return dtpFecHas.getText();
    }
    
    /**
     * Esta función establece la fecha "Hasta".
     * @param fecha La fecha a establecer en el "Hasta".
     */
    public void setFechaHasta(String fecha)
    {
        dtpFecHas.setText(fecha);
    }
    
    /**
     * Esta función obtiene el formato de fecha aplicado en el selector de fechas.
     * @return El formato de fecha aplicado en el selector de fechas.
     */
    public String getFormatoFecha()
    {
        return strForFec;
    }
    
    /**
     * Esta función establece el formato de fecha aplicado en el selector de fechas.
     * @param formato El formato de fecha a aplicar en el selector de fechas.
     */
    public void setFormatoFecha(String formato)
    {
        strForFec=formato;
    }
    
    /**
     * Esta función determina si la flecha de la izquierda del selector de fechas está habilitada.
     * @return true: Si la flecha de la izquierda está habilitada.
     * <BR>false: En el caso contrario.
     */
    public boolean isFlechaIzquierdaHabilitada()
    {
        return chkFleIzq.isEnabled();
    }
    
    /**
     * Esta función establece si se debe habilitar/deshabilitar la flecha de la izquierda.
     * @param habilitada Valor booleano que determina si se debe habilitar/deshabilitar la flecha de la izquierda.
     */
    public void setFlechaIzquierdaHabilitada(boolean habilitada)
    {
        chkFleIzq.setEnabled(habilitada);
    }
    
    /**
     * Esta función determina si la flecha de la derecha del selector de fechas está habilitada.
     * @return true: Si la flecha de la derecha está habilitada.
     * <BR>false: En el caso contrario.
     */
    public boolean isFlechaDerechaHabilitada()
    {
        return chkFleDer.isEnabled();
    }
    
    /**
     * Esta función establece si se debe habilitar/deshabilitar la flecha de la derecha.
     * @param habilitada Valor booleano que determina si se debe habilitar/deshabilitar la flecha de la derecha.
     */
    public void setFlechaDerechaHabilitada(boolean habilitada)
    {
        chkFleDer.setEnabled(habilitada);
    }
    
    /**
     * Esta función selecciona loa meses de acuerdo al tipo de selección.
     */
    private boolean seleccionarRanMes()
    {
        int intColSel[];
        int intMin, intMax, intAni;
        boolean blnRes=true;
        try
        {
            intColSel=tblDat.getSelectedColumns();
            intAni=Integer.parseInt(spiAni.getValue().toString());
            if (intColSel.length>0)
            {
                intMin=intColSel[0];
                intMax=intColSel[intColSel.length-1];
                switch (intTipSel)
                {
                    case INT_SEL_RNG: //Búsqueda por rangos
                        spiAni.setEnabled(true);
                        break;
                    case INT_SEL_MEN: //Fechas menores o iguales a "Hasta".
                        tblDat.setColumnSelectionInterval(0, intMax);
                        spiAni.setEnabled(true);
                        break;
                    case INT_SEL_MAY: //Fechas mayores o iguales a "Desde".
                        tblDat.setColumnSelectionInterval(intMin, 11);
                        spiAni.setEnabled(true);
                        break;
                    case INT_SEL_TOD: //Todo.
                        tblDat.setColumnSelectionInterval(0, 11);
                        spiAni.setEnabled(false);
                        break;
                }
                dtpFecDes.setText(1, intMin + 1, intAni);
                dtpFecHas.setText(objUti.getUltimoDiaMes(intAni, intMax), intMax + 1, intAni);
            }
            else
            {
                dtpFecDes.setAnio(intAni);
                dtpFecHas.setAnio(intAni);
            }
        }
        catch (NumberFormatException e)
        {
            blnRes=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función selecciona la flecha de la izquierda.
     */
    private void seleccionarFleIzq()
    {
        if (chkFleIzq.isSelected())
        {
            lblDes.setVisible(false);
            dtpFecDes.setVisible(false);
            lblFleIzq.setVisible(true);
            //Determinar el tipo de selección.
            if (chkFleDer.isSelected())
                intTipSel=INT_SEL_TOD;
            else
                intTipSel=INT_SEL_MEN;
        }
        else
        {
            lblDes.setVisible(true);
            dtpFecDes.setVisible(true);
            lblFleIzq.setVisible(false);
            //Determinar el tipo de selección.
            if (chkFleDer.isSelected())
                intTipSel=INT_SEL_MAY;
            else
                intTipSel=INT_SEL_RNG;
        }
        seleccionarRanMes();
    }
    
    /**
     * Esta función selecciona la flecha de la derecha.
     */
    private void seleccionarFleDer()
    {
        if (chkFleDer.isSelected())
        {
            lblHas.setVisible(false);
            dtpFecHas.setVisible(false);
            lblFleDer.setVisible(true);
            //Determinar el tipo de selección.
            if (chkFleIzq.isSelected())
                intTipSel=INT_SEL_TOD;
            else
                intTipSel=INT_SEL_MAY;
        }
        else
        {
            lblHas.setVisible(true);
            dtpFecHas.setVisible(true);
            lblFleDer.setVisible(false);
            //Determinar el tipo de selección.
            if (chkFleIzq.isSelected())
                intTipSel=INT_SEL_MEN;
            else
                intTipSel=INT_SEL_RNG;
        }
        seleccionarRanMes();
    }
    
    /**
     * Esta función determina si la flecha de la izquierda del selector de fechas está seleccionada.
     * @return true: Si la flecha de la izquierda está seleccionada.
     * <BR>false: En el caso contrario.
     */
    public boolean isFlechaIzquierdaSeleccionada()
    {
        return chkFleIzq.isSelected();
    }
    
    /**
     * Esta función establece si se debe seleccionar la flecha de la izquierda.
     * @param seleccionada Valor booleano que determina si se debe seleccionar la flecha de la izquierda.
     */
    public void setFlechaIzquierdaSeleccionada(boolean seleccionada)
    {
        chkFleIzq.setSelected(seleccionada);
        seleccionarFleIzq();
    }
    
    /**
     * Esta función determina si la flecha de la derecha del selector de fechas está seleccionada.
     * @return true: Si la flecha de la derecha está seleccionada.
     * <BR>false: En el caso contrario.
     */
    public boolean isFlechaDerechaSeleccionada()
    {
        return chkFleDer.isSelected();
    }
    
    /**
     * Esta función establece si se debe seleccionar la flecha de la derecha.
     * @param seleccionada Valor booleano que determina si se debe seleccionar la flecha de la derecha.
     */
    public void setFlechaDerechaSeleccionada(boolean seleccionada)
    {
        chkFleDer.setSelected(seleccionada);
        seleccionarFleDer();
    }
    
    /**
     * Esta clase implementa la interface "ListSelectionListener" para determinar
     * cambios en la selección. Es decir, cada vez que se selecciona una fila
     * diferente en el JTable se ejecutará el "ListSelectionListener".
     */
    private class ZafLisSelLis implements javax.swing.event.ListSelectionListener
    {
        public void valueChanged(javax.swing.event.ListSelectionEvent e)
        {
            seleccionarRanMes();
        }
    }
    
}
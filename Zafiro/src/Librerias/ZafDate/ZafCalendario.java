/*
 * DatePicker.java
 *
 * Created on 23 de julio de 2004, 13:30
 */
package Librerias.ZafDate;
import java.awt.event.*;
import java.util.Vector;

/**
 * Presenta un Calendario en pantalla, que actuara de forma modal para el JFrame enviado por parametro
 * @author Idi Reyes Marcillo
 */
public class ZafCalendario extends javax.swing.JDialog {
    //Variables
    private LisPopUpMnuMes ObjLisPopUpMnuMes;
    private Vector vecTblCab, vecTblDia;
    private int intMesActual;  //Contiene el numero correspondiente al mes que se esta presentado en pantalla
    private int intAnioActual; //Contiene el numero correspondiente al Año que se esta presentado en pantalla
    private int intDiaActual;  //Contiene el numero de DIA DE LA FECHA ACTUAL
    private java.util.Calendar calendary;// Objeto del Tipo calendar para trabajar los dias del mes
    private int RowDefault;
    private int ColDefault;
    private int RowIniMes, ColIniMes;//Fila , columna (en la tabla) en la que empieza el mes que se esta trabajando
    private int RowFinMes, ColFinMes;//Fila , columna (en la tabla) en la que termina el mes que se esta trabajando    
    
    /**
     * Crea un nuevo calendario
     * @param parent un JFrame para el que se actuara de forma modal
     * @param modal true si queremos que sea de forma modal o false si queremos que el codigo se siga ejecutando
     */
    public ZafCalendario(java.awt.Frame parent, boolean modal) 
    {
        super(parent, modal);
        calendary = java.util.Calendar.getInstance();
        
        initComponents();
        setFecha(calendary.get(java.util.Calendar.DAY_OF_MONTH), calendary.get(java.util.Calendar.MONTH)+1, calendary.get(java.util.Calendar.YEAR));
        
        ObjLisPopUpMnuMes = new LisPopUpMnuMes();
        lblHoy.setText("Hoy es: " +
        calendary.get(java.util.Calendar.DAY_OF_MONTH )+ " de " + NomMes(intMesActual) + " de "+ intAnioActual );
        
        tblDias.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDias.setRowSelectionAllowed(true);
        tblDias.setColumnSelectionAllowed(true);
        
        //Agregando el listener para cuando el usuario de click en un dia
        tblDias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClickEnDia(evt);
            }
        });

        //Agregando el listener para cuando el usuario de click la etiqueta mes
        lblMes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClickEnMes(evt);
            }
        });
        
        mnuEne.addActionListener(ObjLisPopUpMnuMes);
        mnuFeb.addActionListener(ObjLisPopUpMnuMes);
        mnuMar.addActionListener(ObjLisPopUpMnuMes);
        mnuAbr.addActionListener(ObjLisPopUpMnuMes);
        mnuMay.addActionListener(ObjLisPopUpMnuMes);
        mnuJun.addActionListener(ObjLisPopUpMnuMes);
        mnuJul.addActionListener(ObjLisPopUpMnuMes);
        mnuAgo.addActionListener(ObjLisPopUpMnuMes);
        mnuSep.addActionListener(ObjLisPopUpMnuMes);
        mnuOct.addActionListener(ObjLisPopUpMnuMes);
        mnuNov.addActionListener(ObjLisPopUpMnuMes);
        mnuDic.addActionListener(ObjLisPopUpMnuMes);
        
        tblDias.setShowGrid(false);
        spiAnio.setVisible(false);
        
        tblDias.setDefaultRenderer(Object.class, new RenderColorFondo());
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-255)/2, (screenSize.height-182)/2, 255, 182);        
        llenaDias();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnuMes = new javax.swing.JPopupMenu();
        mnuEne = new javax.swing.JMenuItem();
        mnuFeb = new javax.swing.JMenuItem();
        mnuMar = new javax.swing.JMenuItem();
        mnuAbr = new javax.swing.JMenuItem();
        mnuMay = new javax.swing.JMenuItem();
        mnuJun = new javax.swing.JMenuItem();
        mnuJul = new javax.swing.JMenuItem();
        mnuAgo = new javax.swing.JMenuItem();
        mnuSep = new javax.swing.JMenuItem();
        mnuOct = new javax.swing.JMenuItem();
        mnuNov = new javax.swing.JMenuItem();
        mnuDic = new javax.swing.JMenuItem();
        tblDias = new javax.swing.JTable();
        panNorte = new javax.swing.JPanel();
        butNextMes = new javax.swing.JButton();
        butBackMes = new javax.swing.JButton();
        lblCab = new javax.swing.JLabel();
        panNorCentro = new javax.swing.JPanel();
        lblMes = new javax.swing.JLabel();
        lblAnio = new javax.swing.JLabel();
        spiAnio = new javax.swing.JSpinner();
        lblHoy = new javax.swing.JLabel();

        mnuEne.setText("Enero");
        pnuMes.add(mnuEne);

        mnuFeb.setText("Febrero");
        pnuMes.add(mnuFeb);

        mnuMar.setText("Marzo");
        pnuMes.add(mnuMar);

        mnuAbr.setText("Abril");
        pnuMes.add(mnuAbr);

        mnuMay.setText("Mayo");
        pnuMes.add(mnuMay);

        mnuJun.setText("Junio");
        pnuMes.add(mnuJun);

        mnuJul.setText("Julio");
        pnuMes.add(mnuJul);

        mnuAgo.setText("Agosto");
        pnuMes.add(mnuAgo);

        mnuSep.setText("Septiembre");
        pnuMes.add(mnuSep);

        mnuOct.setText("Octubre");
        pnuMes.add(mnuOct);

        mnuNov.setText("Noviembre");
        pnuMes.add(mnuNov);

        mnuDic.setText("Diciembre");
        pnuMes.add(mnuDic);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        tblDias.setBackground(new java.awt.Color(153, 204, 255));
        tblDias.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        tblDias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblDias.setSelectionBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(tblDias, java.awt.BorderLayout.CENTER);

        panNorte.setLayout(new java.awt.BorderLayout());

        butNextMes.setBackground(new java.awt.Color(0, 153, 204));
        butNextMes.setText(">");
        butNextMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butNextMesActionPerformed(evt);
            }
        });
        panNorte.add(butNextMes, java.awt.BorderLayout.EAST);

        butBackMes.setBackground(new java.awt.Color(0, 153, 204));
        butBackMes.setText("<");
        butBackMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBackMesActionPerformed(evt);
            }
        });
        panNorte.add(butBackMes, java.awt.BorderLayout.WEST);

        lblCab.setBackground(new java.awt.Color(0, 153, 204));
        lblCab.setForeground(new java.awt.Color(204, 255, 255));
        lblCab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCab.setText("  Lun     Mar    Mie     Jue     Vie      Sab    Dom");
        lblCab.setOpaque(true);
        panNorte.add(lblCab, java.awt.BorderLayout.SOUTH);

        panNorCentro.setBackground(new java.awt.Color(0, 153, 204));

        lblMes.setBackground(new java.awt.Color(0, 153, 204));
        lblMes.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMes.setOpaque(true);
        panNorCentro.add(lblMes);

        lblAnio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnio.setText("2004");
        lblAnio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnioMouseClicked(evt);
            }
        });
        panNorCentro.add(lblAnio);

        spiAnio.setBorder(null);
        spiAnio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                spiAnioFocusLost(evt);
            }
        });
        panNorCentro.add(spiAnio);

        panNorte.add(panNorCentro, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNorte, java.awt.BorderLayout.NORTH);

        lblHoy.setBackground(new java.awt.Color(0, 153, 204));
        lblHoy.setFont(new java.awt.Font("MS Sans Serif", 0, 14)); // NOI18N
        lblHoy.setForeground(new java.awt.Color(255, 255, 255));
        lblHoy.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblHoy.setText("Hoy es:");
        lblHoy.setOpaque(true);
        getContentPane().add(lblHoy, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        llenaDias();  
        tblDias.setRowSelectionInterval(RowDefault, RowDefault);
        tblDias.setColumnSelectionInterval(ColDefault ,ColDefault);
    }//GEN-LAST:event_formComponentShown
    
    private void spiAnioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_spiAnioFocusLost
        //Perdio el foco el spinner del año asi que lo ocultamos
        spiAnio.setVisible(false);
        intAnioActual = Integer.parseInt(spiAnio.getValue().toString());
        lblAnio.setVisible(true);
        llenaDias();
    }//GEN-LAST:event_spiAnioFocusLost
    
    private void lblAnioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnioMouseClicked
        //Dio click en el año entonces presentaremos la opcion para seleccionar año
        spiAnio.setVisible(true);
        spiAnio.setValue(new Integer(lblAnio.getText()));
        lblAnio.setVisible(false);
        spiAnio.requestFocus();
    }//GEN-LAST:event_lblAnioMouseClicked
    
    
    private void butBackMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBackMesActionPerformed
        // Boton para retroceder en los meses
        Sum_Res_Mes(false);
        llenaDias();
    }//GEN-LAST:event_butBackMesActionPerformed

    private void butNextMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butNextMesActionPerformed
        // Boton para ir al siguiente mes
        Sum_Res_Mes(true);
        llenaDias();
    }//GEN-LAST:event_butNextMesActionPerformed
    

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBackMes;
    private javax.swing.JButton butNextMes;
    private javax.swing.JLabel lblAnio;
    private javax.swing.JLabel lblCab;
    private javax.swing.JLabel lblHoy;
    private javax.swing.JLabel lblMes;
    private javax.swing.JMenuItem mnuAbr;
    private javax.swing.JMenuItem mnuAgo;
    private javax.swing.JMenuItem mnuDic;
    private javax.swing.JMenuItem mnuEne;
    private javax.swing.JMenuItem mnuFeb;
    private javax.swing.JMenuItem mnuJul;
    private javax.swing.JMenuItem mnuJun;
    private javax.swing.JMenuItem mnuMar;
    private javax.swing.JMenuItem mnuMay;
    private javax.swing.JMenuItem mnuNov;
    private javax.swing.JMenuItem mnuOct;
    private javax.swing.JMenuItem mnuSep;
    private javax.swing.JPanel panNorCentro;
    private javax.swing.JPanel panNorte;
    private javax.swing.JPopupMenu pnuMes;
    private javax.swing.JSpinner spiAnio;
    private javax.swing.JTable tblDias;
    // End of variables declaration//GEN-END:variables

    private class LisPopUpMnuMes implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Actualizo el Mes que se selecciono en el popupmenu
            javax.swing.JComponent objCom = (javax.swing.JComponent)e.getSource();
            intMesActual = pnuMes.getComponentIndex(objCom);
            llenaDias();
        }
    }
    
    private class RenderColorFondo extends javax.swing.table.DefaultTableCellRenderer {
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
            java.awt.Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            super.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            
            if(value instanceof  Integer){
                cell.setBackground( new java.awt.Color(153, 204, 255) );
                cell.setForeground(new java.awt.Color(0,0,0));
            }
            if(value instanceof  String){
                cell.setBackground( new java.awt.Color(153, 200, 235)  );
                cell.setForeground(new java.awt.Color(204,240,255));
            }
            if(hasFocus){
                cell.setBackground(new java.awt.Color(0, 0, 0));
                cell.setForeground(new java.awt.Color(255,255,255));
                cell.setFont(new java.awt.Font("MS Sans Serif", 1, 18));
            }
            if(isSelected){
                cell.setBackground(new java.awt.Color(0, 0, 0));
                cell.setForeground(new java.awt.Color(255,255,255));
                cell.setFont(new java.awt.Font("MS Sans Serif", 1, 18));
            }
            return cell;
        }
    }    

    /**
     * Asigna una fecha para que aparezca en pantalla
     * @param dia un Entero que representa el dia que se asignara al calendario
     * @param mes un Entero que representa el mes que se asignara al calendario
     * @param anio un Entero que representa el año que se asignara al calendario
     */    
    public void setFecha(int dia,int mes, int anio){
        intDiaActual = dia;
        intMesActual = mes-1;
        intAnioActual = anio;
    }
    
    /**
     * Función que aumenta o disminuye los meses.
     * <BR>true:  Aumenta uno al mes actual
     * <BR>false: Disminuye uno al mes actual
     * @param suma 
     */
    private void Sum_Res_Mes(boolean suma){
        if(suma){
            if(intMesActual == 11){
                intMesActual = 0;
                intAnioActual++;
            }else
                intMesActual++;
        }else{
            if(intMesActual == 0){
                intMesActual = 11;
                intAnioActual--;
            }else
                intMesActual--;
        }    
    }    

    /**
     * Función obtiene el nombre del mes correspondiente
     * @param NumMes El número del mes, representa un numero desde 0 a 11
     * @return 
     */
    private String NomMes(int NumMes){
        switch(NumMes){
            case 0:
                return "Enero";
            case 1:
                return "Febrero";
            case 2:
                return "Marzo";
            case 3:
                return "Abril";
            case 4:
                return "Mayo";
            case 5:
                return "Junio";
            case 6:
                return "Julio";
            case 7:
                return "Agosto";
            case 8:
                return "Septiembre";
            case 9:
                return "Octubre";
            case 10:
                return "Noviembre";
            default:
                return "Diciembre";
        }
    }

    /**
     * Función que llena los DIAS del mes en el JTable
     */
    private void llenaDias()
    {
        //Presentando en Pantalla el Mes en que se esta trabajando
        RowDefault=0;
        ColDefault=0;
        lblMes.setText(NomMes(intMesActual) +" de ");
        lblAnio.setText(""+intAnioActual);
        
        vecTblCab = new Vector();
        vecTblDia = new Vector();
        
        //LLeno el Vector con los dias de el mes
        calendary.set(java.util.Calendar.MONTH,intMesActual-1);
        calendary.set(java.util.Calendar.YEAR,intAnioActual);
        calendary.set(java.util.Calendar.DAY_OF_MONTH, calendary.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
        
        int cont=(calendary.get(java.util.Calendar.DAY_OF_MONTH)- (calendary.get(java.util.Calendar.DAY_OF_WEEK)-2 ));
        boolean presenta=false;
        
        //Lleno el Vector de las Cabeceras vacio porque no presentamos la cabcera.
        vecTblCab.setSize(7);
        
        for(int i=0; i<=5;i++ ){
            Vector vecReg= new Vector();
            for(int j=0; j<=6;j++) {
                if(cont>calendary.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)){
                    if(!presenta){
                        RowIniMes = i;
                        ColIniMes = j;
                    }else{
                        RowFinMes = i;
                        ColFinMes = j;
                    }
                        
                    cont=1;
                    //Aumentando el mes para presentar el siguiente valor
                    calendary.set(intAnioActual, calendary.get(java.util.Calendar.MONTH) +1 ,1);
                    presenta = !presenta;
                }
                if(presenta){
                    vecReg.addElement(new Integer(cont));
                    if(cont == intDiaActual){
                        RowDefault = i;
                        ColDefault = j;
                    }
                }
                else
                    vecReg.addElement(new String(""+cont));
                cont++;
            }
            vecTblDia.addElement(vecReg);
        }

        //Envío los datos actualizados al modelo de la Tabla.
        tblDias.setModel( new javax.swing.table.DefaultTableModel( vecTblDia, vecTblCab  ) { 
            public boolean isCellEditable(int row, int col) {
               //Esto es para que las celdas no sean editables
               return false;
           }
        });
        
        tblDias.requestFocus();
    }

    /**
     * Evento que ocurre cuando se da click en un dia respectivo del calendario
     * @param evt 
     */
    private void ClickEnDia(java.awt.event.MouseEvent evt) {
        if(! ((tblDias.getSelectedColumn()==-1) || (tblDias.getSelectedRow() ==-1)) )
        {
            if(evt.getButton()==1){
                intDiaActual = Integer.parseInt(tblDias.getValueAt(tblDias.getSelectedRow(),tblDias.getSelectedColumn()).toString());
                if(tblDias.getSelectedRow()<= RowIniMes && tblDias.getSelectedColumn()< ColIniMes )
                    Sum_Res_Mes(false);

                if((tblDias.getSelectedRow()== RowFinMes && tblDias.getSelectedColumn()>= ColFinMes ) || (tblDias.getSelectedRow()> RowFinMes ))
                    Sum_Res_Mes(true);

                //if(tblDias.getSelectedRow()> RowFinMes )
                //    Sum_Res_Mes(true);

                dispose();
            }      
        }  
    }
        
    private void ClickEnMes(java.awt.event.MouseEvent evt) {
        if(evt.getButton()==1){
            pnuMes.show(evt.getComponent(),evt.getX(),evt.getY());
        }
    }
    
    /**
     * Obtiene el dia que selecciono el usuario
     * @return un entero que representa el dia que selecciono el usuario
     */    
    public int Dia() { 
        return intDiaActual; 
    }
    /**
     * Obtiene el mes que selecciono el usuario
     * @return un entero que representa el mes que selecciono el usuario
     */    
    public int Mes() {
        return intMesActual + 1;
    }
    /**
     * Obtiene el año que selecciono el usuario
     * @return un entero que representa el año que selecciono el usuario
     */    
    public int Anio() {
        return intAnioActual;
    }    
    
    
}

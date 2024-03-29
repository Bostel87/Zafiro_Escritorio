
package RecursosHumanos.ZafRecHum06;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Pantalla de Mantenimiento para Horarios de Trabajo.
 * @author Roberto Flores
 * Guayaquil 06/09/2011
 */
public class ZafRecHum06 extends javax.swing.JInternalFrame {

    private final String strVer = " v 1.1 ";                      //Versión de clase
    private ZafRecHum06Bean zafRecHum06Bean;                      //Bean manejador de éste JInternalFrame
    private String[] strTblRepHdrTooTip;                          //Tooltips de la cabecera de las columnas del reporte
    final int INT_TBL_LINEA = 0;                                  //Índice de columna
    final int INT_TBL_DIA = 1;                                    //Índice de columna Dia.
    final int INT_TBL_ENT = 2;                                    //Indice de columna Hora de Entrada.
    final int INT_TBL_SAL = 3;                                    //Indice de columna Hora de Salida.
    final int INT_TBL_MINGRA = 4;                                 //Indice de columna Minutos de Gracia.
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                  //Editor: Editor del JTable.
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafMouMotAda objMouMotAda;                            //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                           //PopupMenu: Establecer PeopuMenú en JTable.

    private Vector vecCab;
    private Vector vecAux;

    private ZafTblCelRenLbl objTblCelRenLbl;                     //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                     //Editor: JTextField en celda.

    /** Creates new form ZafRecHum06 */
    public ZafRecHum06(ZafParSis obj) {
        try {
            zafRecHum06Bean = new ZafRecHum06Bean(this, obj);
            strTblRepHdrTooTip = new String[]{null, "Dia de la semana", "Hora de entrada", "Hora de salida", "Minutos de gracia"};
            initComponents();
            objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();
            setTitle(zafRecHum06Bean.getZafParSis().getNombreMenu() + strVer);
            initTblRep();
            zafRecHum06Bean.initTooBar(this);
            panBar.add(zafRecHum06Bean.getTooBar());
            zafRecHum06Bean.agregarDocLis();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ZafRecHum06.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    class EnterListenerRepTbl extends KeyAdapter
    {
            public void keyPressed(KeyEvent e) {
            {
                int p = e.getKeyCode();
                if (p == KeyEvent.VK_ENTER && tblRep.isEditing())
                {
                    e.consume();
                    int fila = tblRep.getSelectedRow();
                    int col = tblRep.getSelectedColumn();
                    if(col==2){
                        int posSal = 1;
                        String strhorSal = (String) tblRep.getValueAt(fila, col+posSal);
                        if(strhorSal==null || strhorSal.compareTo("")==0){

                        }else{
                            int posGra = 2;
                            String strMinGra = (String) tblRep.getValueAt(fila, col+posGra);
                            if(strMinGra==null || strMinGra.compareTo("")==0){
                                tblRep.setValueAt(txtMinGra.getText() , fila, col+posGra);
                            }
                        }
                    }else if(col==3){
                        int posSal = 1;
                        String strhorSal = (String) tblRep.getValueAt(fila, col-posSal);
                        if(strhorSal==null || strhorSal.compareTo("")==0){

                        }else{
                            int posGra = 1;
                            String strMinGra = (String) tblRep.getValueAt(fila, col+posGra);
                            if(strMinGra==null || strMinGra.compareTo("")==0){
                                tblRep.setValueAt(txtMinGra.getText() , fila, col+posGra);
                            }
                        }
                    }
                }
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblCodCom = new javax.swing.JLabel();
        lblNomHor = new javax.swing.JLabel();
        lblMinGra = new javax.swing.JLabel();
        txtCodHor = new javax.swing.JTextField();
        butHorTra = new javax.swing.JButton();
        txtNomHor = new javax.swing.JTextField();
        txtMinGra = new javax.swing.JTextField();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblRep = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs = new javax.swing.JTextArea();
        panBar = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(500, 350));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                exitForm(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Horarios de Trabajo");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(0, 100));
        jPanel1.setLayout(null);

        lblCodCom.setText("Código:");
        jPanel1.add(lblCodCom);
        lblCodCom.setBounds(4, 30, 100, 20);

        lblNomHor.setText("Nombre:");
        jPanel1.add(lblNomHor);
        lblNomHor.setBounds(4, 51, 90, 20);

        lblMinGra.setText("Gracia:");
        lblMinGra.setToolTipText("Minutos de Gracia");
        jPanel1.add(lblMinGra);
        lblMinGra.setBounds(4, 70, 90, 20);

        txtCodHor.setBackground(zafRecHum06Bean.getZafParSis().getColorCamposSistema());
        txtCodHor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodHor.getDocument().addDocumentListener(zafRecHum06Bean.getDocLis());
        txtCodHor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodHorActionPerformed(evt);
            }
        });
        jPanel1.add(txtCodHor);
        txtCodHor.setBounds(150, 30, 100, 20);

        butHorTra.setText("...");
        butHorTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butHorTraActionPerformed(evt);
            }
        });
        jPanel1.add(butHorTra);
        butHorTra.setBounds(250, 30, 20, 20);

        txtNomHor.setBackground(zafRecHum06Bean.getZafParSis().getColorCamposObligatorios());
        txtNomHor.getDocument().addDocumentListener(zafRecHum06Bean.getDocLis());
        txtNomHor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomHorActionPerformed(evt);
            }
        });
        jPanel1.add(txtNomHor);
        txtNomHor.setBounds(150, 51, 500, 20);

        txtMinGra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNomHor.getDocument().addDocumentListener(zafRecHum06Bean.getDocLis());
        txtMinGra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMinGraActionPerformed(evt);
            }
        });
        txtMinGra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMinGraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMinGraFocusLost(evt);
            }
        });
        jPanel1.add(txtMinGra);
        txtMinGra.setBounds(150, 72, 120, 20);

        panGen.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        panGenDet.setLayout(new java.awt.BorderLayout());

        tblRep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblRep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblRepFocusGained(evt);
            }
        });
        spnDat.setViewportView(tblRep);

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        jPanel2.setPreferredSize(new java.awt.Dimension(34, 70));
        jPanel2.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.BorderLayout());

        lblObs1.setText("Observación:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1, java.awt.BorderLayout.CENTER);

        jPanel2.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.BorderLayout());

        txaObs.setLineWrap(true);
        spnObs1.setViewportView(txaObs);

        panGenTotObs.add(spnObs1, java.awt.BorderLayout.CENTER);

        jPanel2.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panGen.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm

        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }

    }//GEN-LAST:event_exitForm

    private void butHorTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butHorTraActionPerformed
        zafRecHum06Bean.mostrarVenCon();
}//GEN-LAST:event_butHorTraActionPerformed

    private void txtCodHorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodHorActionPerformed
        zafRecHum06Bean.getTooBar().consultar();
}//GEN-LAST:event_txtCodHorActionPerformed

    private void txtNomHorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomHorActionPerformed
        zafRecHum06Bean.getTooBar().consultar();
        zafRecHum06Bean.setBlnMod(false);
}//GEN-LAST:event_txtNomHorActionPerformed

    private void txtMinGraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMinGraActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_txtMinGraActionPerformed

    private void txtMinGraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMinGraFocusLost

        try{
            txtMinGra.setText(txtMinGra.getText().trim());
            if(txtMinGra.getText().compareTo("")!=0){
                String strHorIng = objUti.parseString(txtMinGra.getText());

                int intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0,2));
                int intMM = Integer.parseInt(strHorIng.replace(":","").substring(2,4));

                if((intHH>=0 && intHH<=24)){
                    if(!(intMM>=0 && intMM<=59)){
                        String strTit = "Mensaje del sistema Zafiro";
                        String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                        JOptionPane.showMessageDialog(ZafRecHum06.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        if(strHorIng.length()==5){
                            strHorIng = strHorIng + ":00";
                        }
                        java.sql.Time t = SparseToTime(strHorIng);
                    }
                }
                else{
                String strTit = "Mensaje del sistema Zafiro";
                String strMen = "Minutos de gracia ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                JOptionPane.showMessageDialog(ZafRecHum06.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                txtMinGra.setText("");
                txtMinGra.requestFocus();
                }
            }
        }catch(Exception e){
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "Minutos de gracia ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
            JOptionPane.showMessageDialog(ZafRecHum06.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
            txtMinGra.setText("");
            txtMinGra.requestFocus();
        }

    }//GEN-LAST:event_txtMinGraFocusLost

    private void tblRepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblRepFocusGained
        if(txtMinGra.getText().equals("")){
            txtMinGra.setText(" ");
        }
    }//GEN-LAST:event_tblRepFocusGained

    private void txtMinGraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMinGraFocusGained

    }//GEN-LAST:event_txtMinGraFocusGained

    private void initTblRep(){
        
        vecCab=new Vector(20);  //Almacena las cabeceras
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_DIA,"Día");
        vecCab.add(INT_TBL_ENT,"Entrada");
        vecCab.add(INT_TBL_SAL,"Salida");
        vecCab.add(INT_TBL_MINGRA,"Gracia");

        objTblMod=new ZafTblMod();
        objTblMod.setHeader(vecCab);
        
        //Configurar JTable: Establecer el modelo de la tabla.
        tblRep.setModel(objTblMod);
        
        //Configurar JTable: Establecer tipo de selección.
        tblRep.setRowSelectionAllowed(true);
        tblRep.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        new Librerias.ZafColNumerada.ZafColNumerada(tblRep,INT_TBL_LINEA);
        
        //Configurar JTable: Establecer el menú de contexto.
        objTblPopMnu=new ZafTblPopMnu(tblRep);
        
        //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblRep.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        
        //Configurar JTable: Establecer el ancho de las columnas.
        javax.swing.table.TableColumnModel tcmAux=tblRep.getColumnModel();
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_DIA).setPreferredWidth(130);
        tcmAux.getColumn(INT_TBL_ENT).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_SAL).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_MINGRA).setPreferredWidth(100);
        
        //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
        tblRep.getTableHeader().setReorderingAllowed(false);

        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        objMouMotAda=new ZafMouMotAda();
        tblRep.getTableHeader().addMouseMotionListener(objMouMotAda);

        //Configurar JTable: Establecer columnas editables.
        vecAux=new Vector();
        vecAux.add("" + INT_TBL_ENT);
        vecAux.add("" + INT_TBL_SAL);
        vecAux.add("" + INT_TBL_MINGRA);
        //vecAux.add("" + INT_TBL_DAT_BUT_ITM);
        //objTblMod.setColumnasEditables(vecAux);
        objTblMod.addColumnasEditables(vecAux);
        vecAux=null;

        //Configurar JTable: Editor de la tabla.
        objTblEdi=new ZafTblEdi(tblRep);

        //Configurar JTable: Renderizar celdas.
        objTblCelRenLbl=new ZafTblCelRenLbl();
        tcmAux.getColumn(INT_TBL_ENT).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_SAL).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_MINGRA).setCellRenderer(objTblCelRenLbl);

        String[] dias = {"Lunes","Martes","Miércoles","Jueves","Viernes","Sabado","Domingo"};
        Vector vecData = new Vector();
        for(int intRecDias  = 0; intRecDias < dias.length; intRecDias++){
            java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_LINEA,"");
            vecReg.add(INT_TBL_DIA, dias[intRecDias]);
            vecReg.add(INT_TBL_ENT, "");
            vecReg.add(INT_TBL_SAL, "");
            vecReg.add(INT_TBL_MINGRA, "");
            vecData.add(vecReg);
        }
       
        /*objTblMod.setData(vecData);
        tblRep.setModel(objTblMod);
        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);*/


        //objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
        //tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setCellEditor(objTblCelEdiTxtVcoItm);
        objTblCelEdiTxt=new ZafTblCelEdiTxt(tblRep);
        tcmAux.getColumn(INT_TBL_ENT).setCellEditor(objTblCelEdiTxt);
        tcmAux.getColumn(INT_TBL_SAL).setCellEditor(objTblCelEdiTxt);
        tcmAux.getColumn(INT_TBL_MINGRA).setCellEditor(objTblCelEdiTxt);
        objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            int intFilSel;
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                intFilSel=tblRep.getSelectedRow();
                switch (tblRep.getSelectedColumn())
                {
                    case INT_TBL_MINGRA:
                        String strMinGra=objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_MINGRA));
                        if (strMinGra.equals("")){
                            objTblMod.setValueAt(txtMinGra.getText(), intFilSel, INT_TBL_MINGRA);
                            objTblCelEdiTxt.setCancelarEdicion(true);
                        }
                }
            }

            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                intFilSel=tblRep.getSelectedRow();
                int intHH = 0;
                int intMM  = 0;
                String strHorIng = null;
                try {

                    switch(tblRep.getSelectedColumn()){
                        case INT_TBL_ENT:
                            strHorIng = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_ENT));
                            intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0,2));
                            intMM = Integer.parseInt(strHorIng.replace(":","").substring(2,4));
                            break;
                        case INT_TBL_SAL:
                            strHorIng = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_SAL));
                            intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0,2));
                            intMM = Integer.parseInt(strHorIng.replace(":","").substring(2,4));
                            break;
                        case INT_TBL_MINGRA:
                            strHorIng = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_MINGRA));
                            intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0,2));
                            intMM = Integer.parseInt(strHorIng.replace(":","").substring(2,4));
                            break;
                    }
                        
                    if((intHH>=0 && intHH<=24)){
                        if(!(intMM>=0 && intMM<=59)){
                            {
                                String strTit = "Mensaje del sistema Zafiro";
                                String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                                JOptionPane.showMessageDialog(ZafRecHum06.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                                objTblMod.setValueAt("",intFilSel, tblRep.getSelectedColumn());
                            }
                        }else{

                            switch (tblRep.getSelectedColumn()){
                                
                                case INT_TBL_ENT:
                                    
                                    String strHorEnt=objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_ENT));
                                    if(strHorEnt.length()==5){
                                        strHorEnt = strHorEnt + ":00";
                                    }
                                    java.sql.Time t = SparseToTime(strHorEnt);
                                    break;
                                    
                                case INT_TBL_SAL:

                                    String strHorSal=objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_SAL));
                                    if(strHorSal.length()==5){
                                        strHorSal = strHorSal + ":00";
                                    }
                                    t = SparseToTime(strHorSal);
                                    break;
                                    
                                case INT_TBL_MINGRA:

                                    String strMinGra=objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_MINGRA));
                                    if(strMinGra.length()==5){
                                        strMinGra = strMinGra + ":00";
                                    }
                                    t = SparseToTime(strMinGra);
                                    break;
                            }
                        }
                    }else{
                            
                        String strTit = "Mensaje del sistema Zafiro";
                        String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                        JOptionPane.showMessageDialog(ZafRecHum06.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                        objTblMod.setValueAt("",intFilSel, tblRep.getSelectedColumn());
                    }
                } catch (Exception ex) {
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(ZafRecHum06.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                    objTblMod.setValueAt("",intFilSel, tblRep.getSelectedColumn());
                }
            }
        });
        
        objTblMod.setData(vecData);
        tblRep.setModel(objTblMod);
        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        
    }

    public static java.sql.Time SparseToTime(String hora){
        int h, m, s;
        h = Integer.parseInt(hora.charAt(0)+""+hora.charAt(1)) ;
        m = Integer.parseInt(hora.charAt(3)+""+hora.charAt(4)) ;
        s = Integer.parseInt(hora.charAt(6)+""+hora.charAt(7)) ;
        return (new java.sql.Time(h,m,s));
    }

private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblRep.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LINEA:
            strMsg="";
            break;
        case INT_TBL_DIA:
            strMsg="Día";
            break;
        case INT_TBL_ENT:
            strMsg="Hora de entrada";
            break;
        case INT_TBL_SAL:
            strMsg="Hora de salida";
            break;
        case INT_TBL_MINGRA:
            strMsg="Minutos de gracia";
            break;

        default:
            strMsg="";
            break;
    }
    tblRep.getTableHeader().setToolTipText(strMsg);
}
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton butHorTra;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCodCom;
    private javax.swing.JLabel lblMinGra;
    private javax.swing.JLabel lblNomHor;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JTabbedPane tabFrm;
    public javax.swing.JTable tblRep;
    public javax.swing.JTextArea txaObs;
    public javax.swing.JTextField txtCodHor;
    public javax.swing.JTextField txtMinGra;
    public javax.swing.JTextField txtNomHor;
    // End of variables declaration//GEN-END:variables

}
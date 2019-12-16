package RecursosHumanos.ZafRecHum18;

import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Maestros.ZafMae07.ZafMae07_01;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * Mantenimiento Calendario Laboral
 * @author Roberto Flores
 * Guayaquil 21/09/2011
 */
public class ZafRecHum18 extends javax.swing.JInternalFrame {

    private static final int INT_TBL_LINEA =0;                                         //Índice de columna Linea.
    private static final int INT_TBL_DIA=1;                                            //Índice de columna Día.
    private static final int INT_TBL_CODHOR=2;                                         //Indice de columna Codigo de Horario.
    private static final int INT_TBL_HOR=3;                                            //Indice de columna Nombre de Horario.
    private static final int INT_TBL_CHKLAB=4;                                         //Indice de columna ES LABORABLE.
    private static final int INT_TBL_ENT=5;                                            //Indice de columna Entrada.
    private static final int INT_TBL_SAL=6;                                            //Indice de columna Salida.
    private static final int INT_TBL_MINGRA =7;                                        //Indice de columna Minutos de Gracia.
    private static final int INT_TBL_OBS =8;                                           //Indice de columna Observación.
    private static final int INT_TBL_BUTOBS =9;                                        //Indice del boton Observación.

    private Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                                        //Editor: Editor del JTable.
    private ZafUtil objUti;
    private ZafTblCelEdiChk zafTblCelEdiChkLab;                                         //Editor de Check Box para campo Laborable
    private ZafTblCelRenChk zafTblCelRenChkLab;                                         //Renderer de Check Box para campo Laborable
    private ZafThreadGUI objThrGUI;
    private ZafSelFec objSelFec;
    private ZafMouMotAda objMouMotAda;                                                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                                  //PopupMenu: Establecer PeopuMenú en JTable.

    private ZafTblCelRenLbl objTblCelRenLbl;                                            //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                                            //Editor: JTextField en celda.

    Vector vecCab=new Vector();                                                         //Vector que contiene la cabecera del Table.

    private boolean blnMod;                                                             //Indica si han habido cambios en el documento
    private boolean blnConsDat;
    
    
    
    /** Creates new form pantalla1 */
    public ZafRecHum18(Librerias.ZafParSis.ZafParSis obj) {
        try{
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();
	    
            this.setTitle(objZafParSis.getNombreMenu()+"  v 1.0 ");
            lblTit.setText(objZafParSis.getNombreMenu());
            
	    objUti = new ZafUtil();
	    
            configuraTbl();
            tblDat.getModel().addTableModelListener(new MyTableModelListener(tblDat));
            
             //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setCheckBoxChecked(false);
            //objSelFec.setTitulo("Rango de fechas");
            panCab.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
             
            //*****************************************************************************
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setHoy();
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
            int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if(fecDoc!=null){
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(java.util.Calendar.DATE, -30 );
            
            dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");

            // System.out.println("Fecha: "+ objUti.formatearFecha(fe1, "dd/MM/yyyy") );
            // System.out.println("Fecha: "+ objSelFec.getFechaDesde() );
            
            objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );
            //*******************************************************************************
            //objDocLis = new DocLis();
            //agregarDocLis();
            blnMod = false;
            blnConsDat = false;

	 }catch (CloneNotSupportedException e){
               objUti.mostrarMsgErr_F1(this, e);
        }
    }

    public class MyTableModelListener implements TableModelListener {
    JTable table;

    // It is necessary to keep the table since it is not possible
    // to determine the table from the event's source
    MyTableModelListener(JTable table) {
        this.table = table;
    }

    public void tableChanged(TableModelEvent e) {

        if(!blnConsDat){
            switch (e.getType()) {
                case TableModelEvent.UPDATE:
                    blnMod = true;
                    break;
            }
        }
        blnConsDat = false;
    }
}

private boolean configuraTbl(){
       
    boolean blnRes=false;
    try{
        //Configurar JTable: Establecer el modelo.
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_DIA,"Día");
        vecCab.add(INT_TBL_CODHOR,"Cód. Hor.");
        vecCab.add(INT_TBL_HOR,"Horario");
        vecCab.add(INT_TBL_CHKLAB,"Laborable");
        vecCab.add(INT_TBL_ENT,"Entrada");
        vecCab.add(INT_TBL_SAL,"Salida");
        vecCab.add(INT_TBL_MINGRA,"Gracia");
        vecCab.add(INT_TBL_OBS,"Observación");
        vecCab.add(INT_TBL_BUTOBS,"...");

        objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);
            
        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);
    	
        //Configurar JTable: Establecer el menú de contexto.
        objTblPopMnu=new ZafTblPopMnu(tblDat);
        
        //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
	    
        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        objMouMotAda=new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
        //Configurar JTable: Establecer columnas editables.
        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_CHKLAB);
        vecAux.add("" + INT_TBL_ENT);
        vecAux.add("" + INT_TBL_SAL);
        vecAux.add("" + INT_TBL_MINGRA);
        vecAux.add("" + INT_TBL_BUTOBS);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;

        //Configurar JTable: Editor de la tabla.
        objTblEdi=new ZafTblEdi(tblDat);
            
        //Configurar JTable: Renderizar celdas.
        objTblCelRenLbl=new ZafTblCelRenLbl();
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
        tcmAux.getColumn(INT_TBL_ENT).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_SAL).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_MINGRA).setCellRenderer(objTblCelRenLbl);

        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_DIA).setPreferredWidth(90);
        tcmAux.getColumn(INT_TBL_CODHOR).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_HOR).setPreferredWidth(130);
        tcmAux.getColumn(INT_TBL_CHKLAB).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_ENT).setPreferredWidth(75);
        tcmAux.getColumn(INT_TBL_SAL).setPreferredWidth(75);
        tcmAux.getColumn(INT_TBL_MINGRA).setPreferredWidth(75);
        tcmAux.getColumn(INT_TBL_OBS).setPreferredWidth(127);
        tcmAux.getColumn(INT_TBL_BUTOBS).setPreferredWidth(20);

        zafTblCelRenChkLab = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_CHKLAB).setCellRenderer(zafTblCelRenChkLab);

        zafTblCelEdiChkLab = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_CHKLAB).setCellEditor(zafTblCelEdiChkLab);
             
        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
        tblDat.getTableHeader().setReorderingAllowed(false);

        //Configurar JTable: Ocultar columnas del sistema.
        objTblMod.addSystemHiddenColumn(INT_TBL_CODHOR, tblDat);
            
        Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_BUTOBS).setCellRenderer(zafTblDocCelRenBut);
        ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_BUTOBS, "Observación") {
            public void butCLick() {
                int intSelFil = tblDat.getSelectedRow();
                String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_OBS) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_OBS).toString());
                ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum18.this), true, strObs);
                zafMae07_01.show();
                    if (zafMae07_01.getAceptar()) {
                        tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_OBS);
                    }
            }
        };
            
        Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
        Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk; 
        objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        objTblCelRenChk=null;
        objTblCelEdiChk=null; 
        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);


        objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
        tcmAux.getColumn(INT_TBL_ENT).setCellEditor(objTblCelEdiTxt);
        tcmAux.getColumn(INT_TBL_SAL).setCellEditor(objTblCelEdiTxt);
        tcmAux.getColumn(INT_TBL_MINGRA).setCellEditor(objTblCelEdiTxt);
        objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            int intFilSel;
            /*public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                intFilSel=tblDat.getSelectedRow();
                    /*switch (tblDat.getSelectedColumn())
                    {
                         case INT_TBL_MINGRA:
                             String strMinGra=objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_MINGRA));
                             //System.out.println("que esta sucediendo:" + strCan);
                             if (strMinGra.equals("")){
                                 objTblMod.setValueAt(txtMinGra.getText(), intFilSel, INT_TBL_MINGRA);
                                 //objTblCelEdiTxtVcoItm.setCancelarEdicion(true);
                                 objTblCelEdiTxt.setCancelarEdicion(true);
                             }
                    }*

                }*/

            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                intFilSel=tblDat.getSelectedRow();
                    int intHH = 0;
                    int intMM  = 0;
                    //DateFormat sdf = new SimpleDateFormat("hh:mm");
                    String strHorIng = null;
                    //Date date = null;
                    try {

                        switch(tblDat.getSelectedColumn()){
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
                                    JOptionPane.showMessageDialog(ZafRecHum18.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                                    objTblMod.setValueAt("",intFilSel, tblDat.getSelectedColumn());
                                }
                            }else{

                                switch (tblDat.getSelectedColumn()){
                                    
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
                            JOptionPane.showMessageDialog(ZafRecHum18.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                            objTblMod.setValueAt("",intFilSel, tblDat.getSelectedColumn());
                         }
                    } catch (Exception ex) {
                        String strTit = "Mensaje del sistema Zafiro";
                        String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                        JOptionPane.showMessageDialog(ZafRecHum18.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                        objTblMod.setValueAt("",intFilSel, tblDat.getSelectedColumn());
                    }
            }
        });

        blnRes=true;
    }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
    return blnRes;
}

public static java.sql.Time SparseToTime(String hora){
    int h, m, s;

    h = Integer.parseInt(hora.charAt(0)+""+hora.charAt(1)) ;
    m = Integer.parseInt(hora.charAt(3)+""+hora.charAt(4)) ;
    s = Integer.parseInt(hora.charAt(6)+""+hora.charAt(7)) ;
    return (new java.sql.Time(h,m,s));
}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panFilTabGen = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panCab = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        panButPanBut = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
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

        lblTit.setText("titulo");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        panFilTabGen.setLayout(new java.awt.BorderLayout());

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
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
        scrTbl.setViewportView(tblDat);

        panFilTabGen.add(scrTbl, java.awt.BorderLayout.CENTER);

        panCab.setPreferredSize(new java.awt.Dimension(0, 105));
        panCab.setLayout(null);
        panFilTabGen.add(panCab, java.awt.BorderLayout.PAGE_START);

        tabGen.addTab("General", panFilTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panBot.setLayout(new java.awt.BorderLayout());

        butCon.setFont(new java.awt.Font("SansSerif", 0, 11));
        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panButPanBut.add(butCon);

        butGua.setFont(new java.awt.Font("SansSerif", 0, 11));
        butGua.setText("Guardar");
        butGua.setEnabled(false);
        butGua.setPreferredSize(new java.awt.Dimension(79, 23));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panButPanBut.add(butGua);

        butCer.setFont(new java.awt.Font("SansSerif", 0, 11));
        butCer.setText("Cerrar");
        butCer.setPreferredSize(new java.awt.Dimension(79, 23));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panButPanBut.add(butCer);

        panBot.add(panButPanBut, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBot.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBot, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed

        if(blnMod){
            String strMsg = "Existen cambios sin guardar! \nEstá Seguro que desea cerrar este programa?";
            String strTit = "Mensaje del sistema Zafiro";
            boolean blnOk = (JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
            if(blnOk){
                dispose();
            }
        }else{
             String strTit, strMsg;
             strTit="Mensaje del sistema Zafiro";
             strMsg="¿Está seguro que desea cerrar este programa?";
             if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
             {
                 dispose();
             }
         }
    }//GEN-LAST:event_butCerActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        
        if (objThrGUI==null)
        {
            objThrGUI=new ZafThreadGUI();
            objThrGUI.start();
        }
    }//GEN-LAST:event_butConActionPerformed

private class ZafThreadGUI extends Thread
    {
     public void run(){

         lblMsgSis.setText("Obteniendo datos...");
         pgrSis.setIndeterminate(true);

         consultarDat();

         objThrGUI=null;
    }
}
    
    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed

        guardarDat();
       
        
    }//GEN-LAST:event_butGuaActionPerformed

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

private boolean guardarDat(){
 boolean blnRes=false;
 java.sql.Connection conn;
 PreparedStatement stmLoc = null;
 String strSql="";
 boolean blnMReg = false;
 try{
   conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn!=null){
       conn.setAutoCommit(false);
     
      for(int i=0; i<tblDat.getRowCount(); i++){

          if( tblDat.getValueAt(i, INT_TBL_DIA)!= null ){

                 if(tblDat.getValueAt(i, INT_TBL_LINEA).toString().compareTo("M")==0){

                 blnMReg = true;

                 DateFormat sdf = new SimpleDateFormat("hh:mm");
                 Date date = null;
                 String strDiaLAB;
                 if(tblDat.getValueAt(i, INT_TBL_CHKLAB)!=null){
                        strDiaLAB = ((Boolean) tblDat.getValueAt(i, INT_TBL_CHKLAB)?"S":"N");
                    }else{
                        strDiaLAB = "N";
                    }

                 strSql= "UPDATE tbm_callab SET st_dialab = '"+ strDiaLAB +"', "+
                         "ho_ent = ?, "+
                         "ho_sal = ?, "+
                         "ho_mingraent = ?, "+
                         "tx_obs1 = ? "+
                         "WHERE fe_dia = '"+tblDat.getValueAt(i, INT_TBL_DIA).toString()+"' "+
                         "AND co_hor = "+tblDat.getValueAt(i, INT_TBL_CODHOR);

                stmLoc = conn.prepareStatement(strSql);

                if(tblDat.getValueAt(i, INT_TBL_ENT)==null || tblDat.getValueAt(i, INT_TBL_ENT).toString().compareTo("")==0){
                    stmLoc.setTime(1, null);
                }else{
                    date = sdf.parse(tblDat.getValueAt(i, INT_TBL_ENT).toString());
                    stmLoc.setTime(1, new Time(date.getTime()));
                }
                if(tblDat.getValueAt(i, INT_TBL_SAL)==null || tblDat.getValueAt(i, INT_TBL_SAL).toString().compareTo("")==0){
                    stmLoc.setTime(2, null);
                }else{
                    date = sdf.parse(tblDat.getValueAt(i, INT_TBL_SAL).toString());
                    stmLoc.setTime(2, new Time(date.getTime()));
                }
                if(tblDat.getValueAt(i, INT_TBL_MINGRA)==null || tblDat.getValueAt(i, INT_TBL_MINGRA).toString().compareTo("")==0){
                    stmLoc.setTime(3, null);
                }else{
                    date = sdf.parse(tblDat.getValueAt(i, INT_TBL_MINGRA).toString());
                    stmLoc.setTime(3, new Time(date.getTime()));
                }
                if(tblDat.getValueAt(i, INT_TBL_OBS)==null){
                    stmLoc.setNull(4, Types.VARCHAR);
                }else{
                    stmLoc.setString(4, tblDat.getValueAt(i, INT_TBL_OBS).toString());
                }
                
                stmLoc.executeUpdate();
                 }

          }
      }
    
       if(stmLoc!=null){
           stmLoc.close();
           stmLoc=null;
       }

       conn.commit();
       conn.close();
       conn=null;
       blnRes=true;
       
   }}catch(java.sql.SQLException Evt) {
       blnRes = false;
       objUti.mostrarMsgErr_F1(this, Evt);
       //Evt.printStackTrace();
  }catch(Exception Evt) {
      blnRes = false;
      objUti.mostrarMsgErr_F1(this, Evt);
      //Evt.printStackTrace();
 }

 if(blnMReg){
     if(blnRes){
         consultarDat();
         mostrarMsgInf("La operación GUARDAR se realizó con éxito");
         blnMod = false;
     }
 }else{
        mostrarMsgInf("No se han realizado cambios para que sean guardados");
        butGua.setEnabled(false);
 }
 return blnRes;
}

private boolean consultarDat(){
    blnConsDat = true;
    boolean blnRes=false;
    java.sql.Connection conn;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql="",sqlFil="";
    Vector vecData;
    try{
        conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        if(conn!=null){
            stmLoc=conn.createStatement();
            vecData = new Vector();

            switch (objSelFec.getTipoSeleccion())
            {
                case 0: //Búsqueda por rangos
                    sqlFil+=" WHERE a.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    sqlFil+=" WHERE a.fe_dia<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    sqlFil+=" WHERE a.fe_dia>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                    break;
                case 3: //Todo.
                    break;
            }

            strSql="SELECT a.fe_dia, a.co_hor, b.tx_nom, a.st_dialab, a.ho_ent, a.ho_sal, a.ho_mingraent, a.tx_obs1 " +
                    "from tbm_callab as a " +
                    "inner join tbm_cabhortra b on a.co_hor=b.co_hor "+sqlFil+
                    "ORDER BY a.fe_dia asc, a.co_hor asc";
            //System.out.println(""+strSql);
            rstLoc=stmLoc.executeQuery(strSql);
            
            int i = 0;//while(rstLoc.next()){
            if(rstLoc.next()){
                do{
                    boolean blnDiaLab;
                    if(rstLoc.getString("st_dialab")!=null){
                        if(rstLoc.getString("st_dialab").equals("S")){
                            blnDiaLab = true;
                        }else{
                            blnDiaLab = false;
                        }
                    }else{
                        blnDiaLab = false;
                    }

                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add(INT_TBL_DIA, rstLoc.getString("fe_dia") );
                    vecReg.add(INT_TBL_CODHOR, rstLoc.getString("co_hor") );
                    vecReg.add(INT_TBL_HOR, rstLoc.getString("tx_nom") );
                    vecReg.add(INT_TBL_CHKLAB, blnDiaLab);
                    vecReg.add(INT_TBL_ENT, rstLoc.getString("ho_ent") );
                    vecReg.add(INT_TBL_SAL, rstLoc.getString("ho_sal") );
                    vecReg.add(INT_TBL_MINGRA, rstLoc.getString("ho_mingraent") );
                    vecReg.add(INT_TBL_OBS, rstLoc.getString("tx_obs1") );
                    vecReg.add(INT_TBL_BUTOBS, ".." );
                    vecData.add(vecReg);
                    i++;
                }while(rstLoc.next());
            }
            if(vecData.size()>0){
                butGua.setEnabled(true);
            }else{
                butGua.setEnabled(false);
            }
            objTblMod.setData(vecData);
            tblDat .setModel(objTblMod);
            rstLoc.close();
            rstLoc=null;
            stmLoc.close();
            stmLoc=null;
            conn.close();
            conn=null;
            lblMsgSis.setText("Listo");
            pgrSis.setValue(0);
            pgrSis.setIndeterminate(false);
            blnRes=true;
        }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panButPanBut;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panFilTabGen;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
    

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_DIA:
                    strMsg="Día";
                    break;
                case INT_TBL_CODHOR:
                    strMsg="Código de horario de trabajo";
                    break;
                case INT_TBL_HOR:
                    strMsg="Nombre del horario de trabajo";
                    break;
                case INT_TBL_CHKLAB:
                    strMsg="¿El día es laborable?";
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
                case INT_TBL_OBS:
                    strMsg="Observación";
                    break;
                case INT_TBL_BUTOBS:
                    strMsg="";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
}
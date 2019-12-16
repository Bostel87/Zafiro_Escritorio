/*
 * ZafConsulta.java
 *
 * 
 * v0.3
 */

package Librerias.ZafConsulta;
import java.sql.*;

/**
 * Clase zafConItm:
 * <p>
 * Realiza una consulta utilizando la fuente de origen de datos OBDC, para lo
 * cual es necesario crear un odbc de usuario que se conecte a la base que queremos
 * consultar
 * </p>
 * @author Idi Reyes Marcillo
 * @since 01/Julio/2004
 * @see <strong>Como Usar zafConsulta?</strong><br> Por ejemplo:<p>
 *    Queremos realizar una consulta de la tabla tbl_Clientes, en la base db_Principal,
 *    y los campos "codCli , nomCli, dirCli"; pero queremos que se presenten en pantalla con los
 *    nombre:"Codigo,Nombre del Cliente,Direccion" respectivamente;
 *    Somos el usuario: ireyes y password: code24.
 *    Y tenemos creado un origen de datos (odbc) que se llama dsnDb_Principal
 *     </p><pre>
 *
 *    public class miframe extends javax.swing.JFrame {
 *        private JButton butCon;
 *        private JTextField txtCod, txtNom;
 *        private utileria.zafConsulta conCli;
 *        public miframe() {
 *            ...
 *            ...
 *            ...
 *            butCon.addActionListener(new java.awt.event.ActionListener() {
 *            public void actionPerformed(java.awt.event.ActionEvent evt) {
 *                conCli = new zafConsulta(this,"Codigo,Nombre del Cliente,Direccion", "codCli , nomCli, dirCli" ,"tblClientes","","dsnDb_Principal","ireyes","code24");
 *                conCli.show();
 *                txtCod.setText(objcon.GetCamSel(1));
 *                txtNom.setText(objcon.GetCamSel(2));
 *            }
 *            });
 *
 *        }
 *    }</pre>
 * v0.1
 */
public class ZafConItm extends javax.swing.JDialog {
    private boolean blnEnvioSql = false; // en caso de que hayan usado el constructor en donde se envia el Sql.
    private boolean blnAcepta = false;  // True si el usuario dio click en aceptar; false si el usuario cancelo o cerro la ventana
    private Librerias.ZafParSis.ZafParSis objZafParSis;
    
    
    /**
     * Construye el objeto del tipo Consulta para presentar los datos en pantalla
     * @param parent <p>Un objeto del tipo JFrame.
     *       Recibe el nombre del JFrame para el cual
     *       la consulta actura de forma modal
     * </p>
     * <hr>
     * @param Var_Alias Un String con los  alias que se utilizaran para los
     * los campos de la tabla que se van a presentar
     * <PRE>Ej:
     * En una tabla de maestro de clientes los campos pueden llamarse :
     *        codCli , nomCli, dirCli
     * Pero nosotros queremos que en pantalla se vean con otro nombre como esto:
     *        "Codigo,Nombre del Cliente,Direccion"</PRE>
     * <hr>
     * @param Var_Campos Un String con los  campos que se utilizaran y presentaran  en la consulta
     *
     * <PRE>Ej:
     * En una Consulta de la tabla de maestro de clientes queremos
     * que se presenten el codigo, nombre y direccion  :
     *        "codCli , nomCli, dirCli"
     * </PRE>
     * <hr>
     * @param strFiltroConsulta <pre>Un string con una descripcion que se utilizara para realizar
     *     una consulta de manera Default con el criterio de busqueda
     *     "que contengan" comparando con el primer campo a presentarse de
     *     izquierda a derecha.
     * </pre>
     * <hr>
     * @param strConeccion <pre>Un string con el  Nombre del origen de datos (ODBC)
     *     utilizado para conectarse a la base.
     *     Ej: dsnDB_Usuarios o dsnDb_Principal
     * </pre>
     * <hr>
     * @param Var_Usr <pre>Un String con el nombre de Usuario vï¿½lido para conectarse a la base
     *     este usuario debe estar registrado en la base de datos y con lo permisos
     *     necesarios para consultar dicha tabla
     * ej: "ireyes", "elino"
     *
     *    En el caso de las base de Access este campo se lo puede dejar vacio
     *    ej: ""
     * </pre>
     * <hr>
     * @param Var_Clv <pre>Un String con el password o clave para el Usuario que va conectarse a la base
     *     este usuario debe estar registrado en la base de datos y con lo permisos
     *     necesarios para consultar dicha tabla
     * ej: usuario : "ireyes"
     *    password: "code24"
     *
     *    En el caso de las base de Access este campo se lo puede dejar vacio
     *    ej: ""
     * </pre>
     * <hr>
     * @param strSql Una instruccion sql que se utilizara para traer los campos a presentar, puede ser : <pre>
     *        select codigo,nombre from clientes where tipo='GYE'
     *     o puede ser:
     *        select codigo,nombre from clientes where tipo='GYE' union ....etc
     * </pre>
     */
    public ZafConItm(java.awt.Frame parent, String strFiltroConsulta, Librerias.ZafParSis.ZafParSis objZafParSis) {
        super(parent, true);
        
        this.objZafParSis = objZafParSis;
        
        Str_Dsn  = objZafParSis.getStringConexion();
        Str_Usr  = objZafParSis.getUsuarioBaseDatos();
        Str_Clv  = objZafParSis.getClaveBaseDatos();
        Str_Drv  = objZafParSis.getDriverConexion();
//        Str_Sql  = " SELECT a7.tx_codAlt, a7.co_itm, a7.tx_nomItm, a7.nd_stkAct, a6.nd_stkTot, a7.nd_preVta1,a7.st_ivaVen " +
//                   " FROM " +
//                   " ( " +
//                   " SELECT a3.co_itmMae, SUM(a3.nd_stkAct) AS nd_stkTot " +
//                   " FROM  " +
//                   " ( " +
//                   " SELECT a1.co_itmMae, a1.co_emp, a1.co_itm, a2.nd_stkAct" +
//                   " FROM tbm_equInv AS a1, tbm_inv AS a2 " +
//                   " WHERE a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm" +
//                   " ORDER BY a1.co_itmMae " +
//                   " ) a3 " +
//                   " GROUP BY a3.co_itmMae " +
//                   " ) AS a6," +
//                   " (" +
//                   " SELECT a4.co_itmMae, a5.co_itm, a5.tx_codAlt, a5.tx_nomItm, a5.nd_stkAct, a5.nd_preVta1,a5.st_ivaVen" +
//                   " FROM tbm_equInv AS a4, tbm_inv AS a5 " +
//                   " WHERE a4.co_emp=a5.co_emp AND a4.co_itm=a5.co_itm " +
//                   " AND a5.co_emp=  " + objZafParSis.getCodigoEmpresa() + 
//                   " ) AS a7 " +
//                   " WHERE a6.co_itmMae=a7.co_itmMae "; 
       
        
        
        Str_Sql="SELECT a7.tx_codAlt, a7.co_itm, a7.tx_nomItm, a7.nd_stkAct, a7.nd_stkTot, a7.nd_preVta1, a7.st_ivaVen";
        Str_Sql+=" FROM (";
        Str_Sql+=" SELECT a2.tx_codAlt, a2.co_itm, a2.tx_nomItm, a1.nd_stkAct, a1.nd_stkAct AS nd_stkTot, a2.nd_preVta1, a2.st_ivaVen";
        Str_Sql+=" FROM (";
        Str_Sql+=" SELECT b1.co_itmMae, SUM(b2.nd_stkAct) AS nd_stkAct";
        Str_Sql+=" FROM tbm_equInv AS b1";
        Str_Sql+=" INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
        Str_Sql+=" INNER JOIN tbr_bodEmp AS b3 ON (b2.co_emp=b3.co_empPer AND b2.co_bod=b3.co_bodPer)";
        Str_Sql+=" WHERE b3.co_emp=" + objZafParSis.getCodigoEmpresa();
        Str_Sql+=" AND b3.co_loc=" + objZafParSis.getCodigoLocal();
        Str_Sql+=" GROUP BY b1.co_itmMae";
        Str_Sql+=" ) AS a1, (";
        Str_Sql+=" SELECT c2.co_itmMae, c1.co_itm, c1.tx_codAlt, c1.tx_nomItm, c1.nd_preVta1, c1.st_ivaVen";
        Str_Sql+=" FROM tbm_inv AS c1";
        Str_Sql+=" INNER JOIN tbm_equInv AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
        Str_Sql+=" WHERE c2.co_emp=" + objZafParSis.getCodigoEmpresa();
        Str_Sql+=" ) AS a2";
        Str_Sql+=" WHERE a1.co_itmMae=a2.co_itmMae";
        Str_Sql+=" ) AS a7";
        
//        Str_Count = 
//                    "SELECT count(a7.co_itm)" +
//                    " FROM " +
//                    " ( " +
//                    " SELECT a3.co_itmMae, SUM(a3.nd_stkAct) AS nd_stkTot " +
//                    " FROM  " +
//                    " ( " +
//                    " SELECT a1.co_itmMae, a1.co_emp, a1.co_itm, a2.nd_stkAct" + 
//                    " FROM tbm_equInv AS a1, tbm_inv AS a2 " +
//                    " WHERE a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm " +
//                    " ORDER BY a1.co_itmMae " +
//                    " ) a3 " +
//                    " GROUP BY a3.co_itmMae" +
//                    ") AS a6, " +
//                    "( " +
//                    " SELECT a4.co_itmMae, a5.co_itm, a5.tx_codAlt, a5.tx_nomItm, a5.nd_stkAct " +
//                    " FROM tbm_equInv AS a4, tbm_inv AS a5 " +
//                    " WHERE a4.co_emp=a5.co_emp AND a4.co_itm=a5.co_itm" +
//                    " AND a5.co_emp= " + objZafParSis.getCodigoEmpresa() + 
//                    " ) AS a7 " +
//                    " WHERE a6.co_itmMae=a7.co_itmMae " ;        
        
        Str_Count="SELECT count(a7.co_itm)";
        Str_Count+=" FROM ";
        Str_Count+=" ( ";
        Str_Count+=" SELECT a2.tx_codAlt, a2.co_itm, a2.tx_nomItm, a1.nd_stkAct, a1.nd_stkAct AS nd_stkTot, a2.nd_preVta1, a2.st_ivaVen";
        Str_Count+=" FROM (";
        Str_Count+=" SELECT b1.co_itmMae, SUM(b2.nd_stkAct) AS nd_stkAct";
        Str_Count+=" FROM tbm_equInv AS b1";
        Str_Count+=" INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
        Str_Count+=" INNER JOIN tbr_bodEmp AS b3 ON (b2.co_emp=b3.co_empPer AND b2.co_bod=b3.co_bodPer)";
        Str_Count+=" WHERE b3.co_emp=" + objZafParSis.getCodigoEmpresa();
        Str_Count+=" AND b3.co_loc=" + objZafParSis.getCodigoLocal();
        Str_Count+=" GROUP BY b1.co_itmMae";
        Str_Count+=" ) AS a1, (";
        Str_Count+=" SELECT c2.co_itmMae, c1.co_itm, c1.tx_codAlt, c1.tx_nomItm, c1.nd_preVta1, c1.st_ivaVen";
        Str_Count+=" FROM tbm_inv AS c1";
        Str_Count+=" INNER JOIN tbm_equInv AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
        Str_Count+=" WHERE c2.co_emp=" + objZafParSis.getCodigoEmpresa();
        Str_Count+=" ) AS a2";
        Str_Count+=" WHERE a1.co_itmMae=a2.co_itmMae";
        Str_Count+=" ) AS a7 ";
        
        blnEnvioSql = true;
        
        initComponents();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                Cancela();
            }
        });
        
        cargaCboCam();
        cargaCboTip(1);
        //Asignando el Tamaï¿½o del arrglo ke tiene los items seleccionados
        Str_RegSel = new String[cboCam.getItemCount()];
        
        txtDes.setText(strFiltroConsulta);
        //cargarCon();
        ExeCon();

    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        panNor = new javax.swing.JPanel();
        lblCam = new javax.swing.JLabel();
        cboCam = new javax.swing.JComboBox();
        lblDes = new javax.swing.JLabel();
        txtDes = new javax.swing.JTextField();
        lblTip = new javax.swing.JLabel();
        cboTip = new javax.swing.JComboBox();
        spnCon = new javax.swing.JScrollPane();
        tblCon = new javax.swing.JTable();
        panSur = new javax.swing.JPanel();
        panSurSur = new javax.swing.JPanel();
        butBus = new javax.swing.JButton();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panSurNor = new javax.swing.JPanel();
        prgCon = new javax.swing.JProgressBar();
        lblSts = new javax.swing.JLabel();
        lblSts2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        panNor.setLayout(new java.awt.GridLayout(3, 2));

        lblCam.setText("Campo:");
        panNor.add(lblCam);

        cboCam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "codigo", "co_sis", "nombre", "stock", "total", "precio", "iva" }));
        cboCam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCamActionPerformed(evt);
            }
        });

        panNor.add(cboCam);

        lblDes.setText("Descripci\u00f3n:");
        panNor.add(lblDes);

        txtDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesActionPerformed(evt);
            }
        });

        panNor.add(txtDes);

        lblTip.setText("Tipo de Busqueda:");
        panNor.add(lblTip);

        panNor.add(cboTip);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        tblCon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        tblCon.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        spnCon.setViewportView(tblCon);

        getContentPane().add(spnCon, java.awt.BorderLayout.CENTER);

        panSur.setLayout(new java.awt.BorderLayout());

        panSurSur.setLayout(new java.awt.GridLayout(1, 3));

        butBus.setMnemonic('B');
        butBus.setText("Buscar");
        butBus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusActionPerformed(evt);
            }
        });

        panSurSur.add(butBus);

        butAce.setMnemonic('A');
        butAce.setText("Aceptar");
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });

        panSurSur.add(butAce);

        butCan.setMnemonic('C');
        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });

        panSurSur.add(butCan);

        panSur.add(panSurSur, java.awt.BorderLayout.NORTH);

        panSurNor.setLayout(new java.awt.BorderLayout());

        prgCon.setStringPainted(true);
        panSurNor.add(prgCon, java.awt.BorderLayout.NORTH);

        panSurNor.add(lblSts, java.awt.BorderLayout.WEST);

        lblSts2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        panSurNor.add(lblSts2, java.awt.BorderLayout.CENTER);

        panSur.add(panSurNor, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panSur, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-400)/2, (screenSize.height-400)/2, 400, 400);
    }//GEN-END:initComponents
    
    private void txtDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesActionPerformed
        // Ejecuto la consulta al dar enter en la caja de texto
        ExeCon();
    }//GEN-LAST:event_txtDesActionPerformed
    
    private void cboCamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCamActionPerformed
        //Poner aki para cambiar loa tipos de busqueda
    }//GEN-LAST:event_cboCamActionPerformed
    
    
    private void butBusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusActionPerformed
        //Ejecuto la consulta
        if(butBus.getText().equals("Detener"))
            ViveHiloCon=false;
        else
            ExeCon();
        
        
    }//GEN-LAST:event_butBusActionPerformed
    
    private void ExeCon(){
        
        Act_But(false);
        prgCon.setVisible(true);
        ViveHilo=true;
        ViveHiloCon = true;
        
        cargarCon Consultar = new cargarCon();
        Consultar.start();
        
    }

    /**
     *  Se utiliza para saber si el usuario dio click en aceptar o dio click en <br>
     *  cerrar la ventana
     * @return true  - si el usuario dio click en aceptar
     * false - si el usuario dio click en cerrar o cancelar
     */    
    public boolean acepto(){
        return blnAcepta;
    }
    
    /*
     *Actualiza el estado de los botones
     */
    
    /**
     * <pre>
     * Realiza una busqueda en la tabla que se envio como parametro
     * sin presentar la ventana de consulta, y retorna true si es que
     * la busqueda se realizo con exito y false si es que no se encontro nada
     * </pre>
     * @param strCriBus un String con el criterio de busqueda ejemplo:<pre>
     * Para el caso de campos numericos puede utilizar de la siguiente manera
     *    boolean seencontro = objcon.buscar("CLI_TELEFONOS = " +  txt1.getText())
     * Para el caso de campos string puede utilizar de la siguiente manera
     *    boolean seencontro = objcon.buscar("CLI_NOMBRE = '" +  txt1.getText() + "'")
     *
     * </pre>
     * @return <pre>
     *    true encontro alguna coincidencia
     *    false si no encontro nada    </pre>
     */
    
    public void buscar2(String txt){
       txtDes.setText(txt);
    }
    
    public boolean buscar(String strCriBus){
        //"codigo = " + txtCod.getText() ;
        Connection con;
        Statement stm;
        ResultSet rst;
        String sSQL;
        try {
            con=DriverManager.getConnection(Str_Dsn,Str_Usr,Str_Clv);
            
            if (con!=null) {
                stm=con.createStatement();
                
                //ARMO EL SQL CON LOS DATOS PASADOS POR PARAMETROS
                sSQL= Str_Sql;
                if(!strCriBus.trim().equals(""))
                    sSQL= Str_Sql + " and " + strCriBus;
                
                
                rst=stm.executeQuery(sSQL);
                
                
                if(rst.next()){
                    for(int col=0 ; col< rst.getMetaData().getColumnCount();col++)
                        
                        Str_RegSel[col] = (rst.getString(col+1)==null)?"":rst.getString(col+1);
                }else{
                    return false;
                }
                
                rst.close();
                stm.close();
                con.close();
            }
        }
        catch(SQLException Evt) {
            System.out.println(Evt.toString());
        }
        
        catch(Exception Evt) {
            System.out.println(Evt.toString());
        }
        
        return true;
    }
    
    private void Act_But(boolean Activo){
        if(butBus.getText().equals("Buscar"))
            butBus.setText("Detener");
        else
            butBus.setText("Buscar");
        txtDes.setEnabled(Activo);
        butAce.setEnabled(Activo);
        butCan.setEnabled(Activo);
    }
    
    private String armaStringSql(String strCriterio){
        Str_Sql= Str_Sql.trim();
        String strResultado = "";
        if(Str_Sql.toLowerCase().indexOf("where")>-1){
          /* 
           * Estaba de esta forma pero era bug  debido a que si enviaban escrito 'WHERE' o 'WheRE' 
           * no reemplazaba la cadena como  deberia ser
           *     strResultado = Str_Sql.replaceAll("where" , "where " + strCriterio + FilSql() + " and "); 
           */
             
           String strPrefijo = Str_Sql.substring(0, Str_Sql.toLowerCase().indexOf("where"));
           strResultado =  strPrefijo + "where " + strCriterio + FilSql() + " and " ;
           strResultado =  strResultado + Str_Sql.substring(Str_Sql.toLowerCase().indexOf("where")+ 5 , Str_Sql.length());
        }else{
            strResultado = Str_Sql + " where " + strCriterio + FilSql() ;
        }

        return strResultado;
    }

    private String armaStringSqlCount(String strCount){
        
        int intIni= strCount.trim().toLowerCase().indexOf("from");
        
        if(intIni==-1)
            return "";
            
        String strResultado = strCount.trim().substring(intIni);
        
        int fin = strResultado.toLowerCase().indexOf("select");
        
        if(fin==-1)
            return "Select count(*) " + strResultado;
        
        strResultado = "Select count(*) " + strResultado.substring(0,fin) + armaStringSqlCount(strResultado.substring(fin));
        
        return strResultado;
    }
    
    /**
     *  Cargar Consulta en el Jtable
     */
    class cargarCon extends Thread{
        public void run(){
            Connection con;
            Statement stm;
            ResultSet rst;
            String sSQL;
            
            // Realiza la Consulta segun los campos seleccionados y muestra en el grid
            try {//odbc,usuario,password
                con=DriverManager.getConnection(Str_Dsn,Str_Usr,Str_Clv);
                
                   if (con!=null) {
                    stm=con.createStatement();
                    
                    //ARMO EL SQL CON LOS DATOS PASADOS POR PARAMETROS
                    String StrCount="";
//                    if(blnEnvioSql){
//                       sSQL     = armaStringSql("");
//                       StrCount = armaStringSqlCount(sSQL);
                    
                       sSQL     = Str_Sql + FilSql() ;
                       
                       StrCount = Str_Count + FilSql();
//                    }
                    lblSts.setText("Ejecutando Consulta  ");

                  
                    rst=stm.executeQuery(StrCount);
                    rst.next();
                    Var_Rows = rst.getInt(1);
                    rst=stm.executeQuery(sSQL);
                   
                     //System.out.println("Javier: "+ sSQL );
                                      
                    lblSts.setText("");
                    
                    
                    Var_Cols = cboCam.getItemCount();
                    
                    prgCon.setMaximum(Var_Rows);
                    
                    VecTbl = new java.util.Vector();
                    
                    int i=0, col;
                    
                    Progreso ObjPrg = new Progreso();
                    ObjPrg.start();

                    for(i=0 ; rst.next() && ViveHiloCon; i++ ){
                        
                        java.util.Vector VecReg = new java.util.Vector();
                        
                        for(col=0 ; col< Var_Cols;col++){
                             
                            if( rst.getString(col+1) == null ) 
                                  VecReg.addElement( 0 +"");
                                else
                                     VecReg.addElement(rst.getString(col+1)+"");
                               
                         }

                        VecTbl.addElement(VecReg);
                        VarCurReg = i+1;
                        ObjPrg.resume();
                    }
                    
                    ViveHilo=false;
                    ViveHiloCon=false;
                    ObjPrg.resume();
                    
                    //Obteniendo las cabeceras del Jtable desde el comboBox
                    java.util.Vector VecColNom = new java.util.Vector();
                    
                    for(int tem=0; tem< Var_Cols;tem++)
                        VecColNom.addElement(cboCam.getItemAt(tem).toString());
                    
                    //Cargando los datos en el Jtable
                    
                    TblOrdenada = new TableSorter(new javax.swing.table.DefaultTableModel(
                    VecTbl ,VecColNom )
                    {  public boolean isCellEditable(int row, int col) {
                           //Esto es para que las celdas no sean editables
                           return false;
                       }
                    }
                    );
                    
                    
                    
                    tblCon.setModel(TblOrdenada);
                    TblOrdenada.setTableHeader(tblCon.getTableHeader());
                    tblCon.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                    tblCon.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                    tblCon.getTableHeader().setReorderingAllowed(false);                    

                    //Agregando el listener para cuando el usuario de click en un dia
                    tblCon.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            if(! ((tblCon.getSelectedColumn()==-1) || (tblCon.getSelectedRow() ==-1)) )
                                if(evt.getClickCount()==2){
                                    for(int i=0; i<=cboCam.getItemCount()-1;i++){
                                        Str_RegSel[i]= (tblCon.getValueAt(tblCon.getSelectedRow(), i )==null)?"":tblCon.getValueAt(tblCon.getSelectedRow(), i ).toString();
                                    }
                                    blnAcepta = true;
                                    dispose();
                                }
                        }
                    });
                    /*
                     *   POner aki codigo para manejar enter
                     *
                     */

                    javax.swing.KeyStroke enter = javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER,0);


                    /*  Registrando el KeyBoard Listener para la tecla enter
                     *  para ke se agregue una fila cuando la tecla enter es presionada 
                     */


                    tblCon.registerKeyboardAction( new java.awt.event.ActionListener(){
                        public void actionPerformed(java.awt.event.ActionEvent evt){
                            if(! ((tblCon.getSelectedColumn()==-1) || (tblCon.getSelectedRow() ==-1)) ){
                                    for(int i=0; i<=cboCam.getItemCount()-1;i++)
                                        Str_RegSel[i]= (tblCon.getValueAt(tblCon.getSelectedRow(), i )==null)?"":tblCon.getValueAt(tblCon.getSelectedRow(), i ).toString();
                                    blnAcepta = true;
                                    dispose();
                            }
                        }
                    }, "enter", enter,javax.swing.JComponent.WHEN_FOCUSED );

                    
                    if(VarCurReg==0)
                        lblSts.setText("No se encontro ninguna coincidencia");
                    else{
                        lblSts.setText("Todos los registros Cargados");
                        tblCon.requestFocus();
                        tblCon.setRowSelectionInterval(0,0);
                        tblCon.changeSelection(0, 0, false, false);
                    }
                    lblSts2.setText("Total de Registros : " + VarCurReg);
                    
                    
                    VarCurReg=0;
                    prgCon.setValue(0);
                    prgCon.setVisible(false);
                    prgCon.setString("0 %");
                    
                    rst.close();
                    stm.close();
                    con.close();
                    Act_But(true);
                    
                }
            }
            catch(SQLException Evt) {
                System.out.println(Evt.toString());
            }
            
            catch(Exception Evt) {
                System.out.println(Evt.toString());
            }
        }
    }
    
    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        Cancela();
    }//GEN-LAST:event_butCanActionPerformed

    private void Cancela(){
        ViveHilo=false;
        ViveHiloCon = false;        
        Str_RegSel = null;
        blnAcepta = false;
        dispose();
    }
    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        
        
        if((tblCon.getSelectedColumn()==-1)|| (tblCon.getSelectedRow() ==-1))
            lblSts.setText("Seleccione un registro de la consulta");
        else{
            
            //Asignando la el registro seleccionado a Str_RegSel[]
            for(int i=0; i<=cboCam.getItemCount()-1;i++)
                Str_RegSel[i]= (tblCon.getValueAt(tblCon.getSelectedRow(), i )==null)?"":tblCon.getValueAt(tblCon.getSelectedRow(), i ).toString();
            blnAcepta = true;    
            dispose();
        }
        
    }//GEN-LAST:event_butAceActionPerformed
    
    //Recibe un int con el menu que se mostrara
    // 1 = menu de busqueda de Textos
    // 2 = menu de busqueda de Numeros o fechas
    
    private void cargaCboTip(int IdxMen){
        
        cboTip.setModel(new javax.swing.DefaultComboBoxModel(StrBusChar));
        
    }
    
    private void CargarDriver(){
        try {
            Class.forName(Str_Drv);
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
    }
    
    private void cargaCboCam() {
        
//        java.util.StringTokenizer st = new java.util.StringTokenizer(Str_Alias,",");
//        
//        while (st.hasMoreTokens())
//            cboCam.addItem(st.nextToken());
        
    }
    
    //Retorna el campo a buscar segun la seleccion en el ComboBox
    //Recibe el indice de la selececcion del combobox y regresa el campo que va con
    //ese alias
    private String CamBus(int IndCbo) {
        //codigo, co_sis, nombre, stock, total, precio, iva     
        String StrCam="";        
        switch (IndCbo) {
            case 0 :
                StrCam = "a7.tx_codAlt";
                break ;
            case 1 :
                StrCam = "a7.co_itm";
                break ;
            case 2 :
                StrCam = "a7.tx_nomItm";
                break ;
            case 3 :
                StrCam = "a7.nd_stkAct";
                break ;
            case 4 :
                StrCam = " a6.nd_stkTot";
                break ;
            case 5 :
                StrCam = "a7.nd_preVta1";
                break ;
            case 6 :
                StrCam = "a7.st_ivaVen";
                break ;
        }
        return StrCam;
    }
    
    private String FilSql() {
        
        String TmpSql = CamBus(cboCam.getSelectedIndex()) , ValBus = txtDes.getText().replaceAll("'","''").replace('*', '%').toLowerCase();
        
        switch (cboTip.getSelectedIndex()) {
            case 0:
                //Opcion que Contegan la descripcion
                TmpSql = " WHERE LOWER(" + TmpSql + ") LIKE '%" + ValBus +"%'";
                break;
            case 1:
                //Opcion Busqueda Directa
                TmpSql = " WHERE LOWER(" + TmpSql + ") LIKE '" + ValBus +"'";
                break;
            case 2:
                //Opcion Que Comienza con
                TmpSql = " WHERE LOWER(" + TmpSql + ") LIKE '" + ValBus + "%'";
                break;
            case 3:
                //Opcion Que No Comienza con
                TmpSql = " WHERE  not  LOWER(" + TmpSql + ") LIKE '" + ValBus + "%'";
                break;
            case 4:
                //Opcion Que Termina con
                TmpSql = " WHERE LOWER(" + TmpSql + ") LIKE '%" + ValBus + "'";
                break;
            case 5:
                //Opcion Que No Termina com
                TmpSql = " WHERE  not " + TmpSql + " LIKE '%" + ValBus + "'";
                break;
            case 6:
                //Es mayor que
                TmpSql = TmpSql + " >= '" + ValBus + "'";
                break;
        }
        
        return TmpSql ;
    }
    
    
    
    //  Obtiene Un campo del Registro Seleccionado
    //    Retorna un string con el campo seleccionado
    //    en el mismo orden en que se enviaron los como parametros en el constructor
    //    va desde 1 a el numero de campos de la tabla
    /**
     * Obtiene el campo que se selecciono en la consulta.
     * @param Idx Recibe un int con el numero correspondiente al campo que se quiere retornar<pre>
     * Puede ser 1 en adelante dependiendo cuantos campos se hayan consultado
     * Ej: si nosotros creamos una consulta a un maestro de clientes con los campos
     *     "codCli , nomCli, dirCli"
     *     y queremos que la consulta nos devuelva el campo codigo
     *     que selecciono el usuario deberiamos hacer lo siguiente
     *     strCodigo = obj_zafConsulta.GetCamSel(1);
     * </pre>
     * @return <pre>Un String con el valor del campo que se selecciono<br>
     * retorna ""      en caso de que no se hubiese seleccionado nada en la consulta<br>
     * retorna "El parametro debe ser entre 1 y #"      si el se envio como parametro <=0 o<br>
     *         mayor que el numero de campos en la consulta<br>
     * </pre>
     */
    public String GetCamSel(int Idx){
        if(!(Str_RegSel==null)){
            if(Idx <= 0 || Idx > Str_RegSel.length)
                return "El parametro debe ser entre 1 y " + Integer.toString(Str_RegSel.length) ;
            else
                return Str_RegSel[Idx-1];
        }else{
            return "";
        }
        
    }
    
    /**
     * Selecciona en el combo el campo ke se buscara.
     * @param Idx Recibe un int con el indice que sera seleccionado, de manera predeterminda sera indice 0
     */
    public void setSelectedCamBus(int Idx){
        cboCam.setSelectedIndex(Idx);
    }
    
    class Progreso extends Thread{
        
        public void run(){
            
            int porcent;
            while(ViveHilo) {
                lblSts.setText("Cargando datos en la tabla...");
                
                lblSts2.setText("Registros : "+ VarCurReg + " de " + Var_Rows);
                
                prgCon.setValue(VarCurReg);
                //Porcentaje de completado de consulta
                porcent = (VarCurReg==0)? 0 : (VarCurReg*100/Var_Rows);
                
                prgCon.setString(Integer.toString(porcent) + " %");
                suspend();
            }
            
        }
    }
  
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butBus;
    private javax.swing.JButton butCan;
    private javax.swing.JComboBox cboCam;
    private javax.swing.JComboBox cboTip;
    private javax.swing.JLabel lblCam;
    private javax.swing.JLabel lblDes;
    private javax.swing.JLabel lblSts;
    private javax.swing.JLabel lblSts2;
    private javax.swing.JLabel lblTip;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panSur;
    private javax.swing.JPanel panSurNor;
    private javax.swing.JPanel panSurSur;
    private javax.swing.JProgressBar prgCon;
    private javax.swing.JScrollPane spnCon;
    private javax.swing.JTable tblCon;
    private javax.swing.JTextField txtDes;
    // End of variables declaration//GEN-END:variables
    
    /*       VARIABLE QUE CONTIENE LOS CAMPOS QUE SE MOSTRARAN
             SEPARADAS POR "," EJ:
                                "CO_CLI,NO_BRE,RUC_CLI,MON_CLI"        */
    
    private String Str_Campos;
    
    /*       VARIABLE QUE CONTIENE ALIAS DE LOS CAMPOS QUE SE MOSTRARAN
             SEPARADAS POR "," EJ:
                                "CODIGO,NOMBRE DEL CLIENTE,RUC,MONTO"  */
    
    private String Str_Alias;
    private String Str_Tabla;// Nombre de la tabla a donde se hara la consulta
    private String Str_Dsn;// Nombre del DSN (ODBC DRIVER) QUE UTILIZA PARA CONECTARSE A LA TABLA
    private String Str_Drv;// Driver de coneccion a la base
    private String Str_Usr;// Nombre de USUARIO que utiliza para conectarse a la base
    private String Str_Clv;// Password del usuario que utiliza para conectarse a la base
    private String Str_Sql;// Password del usuario que utiliza para conectarse a la base
    private String Str_Count;
    
    private java.util.Vector VecTbl;//Objeto que tendra el contenido de la consulta para ponerlo en el jtable
    private java.util.Vector VecColNom;//Tendra los nombres de las cabeceras.
    
    private String Str_RegSel[];//Tendra los datos del registro seleccionado
    private String StrBusChar[]={"Contengan", "Directa", "Comienzan con", "No Comienzan con", "Terminan con", "No Terminan con" };//Contiene los metodos de busqueda para tipos Char
    private String StrBusNum[]={ "Es mayor que", "Es mayor o igual que", "Es menor que", "Es menor o igual que" };//Contiene los metodos de busqueda para tipos Numericos y fechas
    
    private int Var_Rows=0;
    private int Var_Cols=0;
    private int VarCurReg=0; //Current Register regstro actual
    
    private boolean ViveHilo;//true = Si vive el hilo de el progressbar, false=muere
    private boolean ViveHiloCon ;//true = Si vive el hilo de la cunsulta, false=muere
    private TableSorter TblOrdenada;
    
    
}




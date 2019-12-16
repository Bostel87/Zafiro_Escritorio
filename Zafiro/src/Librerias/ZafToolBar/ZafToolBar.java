/*
 * ZafBarra.java
 *
 * Created on 5 de octubre de 2004, 16:19
 * v0.8
 */ 
package Librerias.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafToolBar.ZafGlaPanLbl;

/**
 * ZafToolBar es una clase abstracta que hereda de un componente JPanel, en la cual se van a
 * controlar todas las opciones que manejará el sistema por medio de una Barra de Botones,
 * asi como también los mensajes que se mostrarán a los usuarios.
 * Consta de 2 partes:
 * <UL>
 * <LI>1) Barra de botones.
 * <LI>2) Barra de mensajes.
 * </UL>
 * La Barra de botones consta a su vez de los botones que manejan las opciones del sistema,
 * Está formada por los siguientes botones:
 * Botones de Navegación:
 * <UL>
 * <LI>Inicio.
 * <LI>Anterior.
 * <LI>Siguiente.
 * <LI>Fin.
 * </UL>
 * Botones de Operaciones:
 * <UL>
 * <LI>Insertar.
 * <LI>Consultar.
 * <LI>Modificar.
 * <LI>Eliminar.
 * <LI>Anular.
 * </UL>
 * Botones de Impresión
 * <UL>
 * <LI>Imprimir.
 * <LI>Vista preliminar:
 * </UL>
 * Botones de confirmación:
 * <UL>
 * <LI>Aceptar.
 * <LI>Cancelar
 * </UL>
 * La Barra de Mensajes consta de los siguientes elementos:
 * <UL>
 * <LI>Mensajes de Sistema.
 * <LI>Posición relativa del registro.
 * <LI>Modo de operación actual.
 * <LI>Estado del registro.
 * <LI>Barra de progreso.
 * </UL>
 * @author José Javier Naranjo Chacón
 * Revisión: 26/Sep/2005 Eddye Lino
 */
public abstract class ZafToolBar extends javax.swing.JPanel implements ZafComandos
{
    //Constantes:
    public static final int INT_BUT_INI=0;                      /**Botón Inicio.*/
    public static final int INT_BUT_ANT=1;                      /**Botón Anterior.*/
    public static final int INT_BUT_SIG=2;                      /**Botón Siguiente.*/
    public static final int INT_BUT_FIN=3;                      /**Botón Fin.*/
    public static final int INT_BUT_INS=4;                      /**Botón Insertar.*/
    public static final int INT_BUT_CON=5;                      /**Botón Consultar.*/
    public static final int INT_BUT_MOD=6;                      /**Botón Modificar.*/
    public static final int INT_BUT_ELI=7;                      /**Botón Eliminar.*/
    public static final int INT_BUT_ANU=8;                      /**Botón Anular.*/
    public static final int INT_BUT_IMP=9;                      /**Botón Imprimir.*/
    public static final int INT_BUT_VIS=10;                     /**Botón Vista preliminar.*/
    public static final int INT_BUT_ACE=11;                     /**Botón Aceptar.*/
    public static final int INT_BUT_CAN=12;                     /**Botón Cancelar.*/
    //Variables:
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafGlaPanLbl objGlaPanLbl;
    private javax.swing.JInternalFrame ifruso;
    private javax.swing.JOptionPane oppMsg;
    private boolean blnConIns, blnConMod, blnConEli, blnConAnu, blnConImp;
    int ValMax=0, ValAct=0, ValCon,
        estAct,//estado de cotizacion
        subest; //subestado de consulta
    int intEstAnt;      //Estado anterior.
    int intSubEstAnt;   //Estado anterior.
    //Arreglo que contiene los estados en los que se puede encontrar la cotizacion
    char est[] = {'l', //Listo
                  'c', //Consulta
                  'x', //Modifica
                  'y', //Elimina
                  'z', //Anula
                  'n', //NuevoInsertar
                  'w'}; //aceptando consulta
    //Eeste arreglo permite que luego de realizar una consulta pueda cambiarme a estados de modificar, eliminar, insertar                  
    char subestcon[] = {'m', //Modifica
                        'e', //Elimina
                        'a', //anula
                        'j'};//ninguna
    private String strOpeClk;
    private int intButCli=-1;                                   //Botón en el que se dió click.
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.

    public int getEstAct() {
        return estAct;
    }
    
    
    /**
     * Crea una nueva instancia de la clase ZafToolBar.
     * @param ifrVen Componente en el cual se adiciona la clase abstracta ZafToolBar.
     * @param objParSis El objeto que contiene los parámetros del sistema.
     */
    public ZafToolBar(javax.swing.JInternalFrame ifrVen, Librerias.ZafParSis.ZafParSis objParSis)
    {
        initComponents();
        //Inicializar objetos.
        ifruso=ifrVen;
        this.objParSis=objParSis;
        objUti=new ZafUtil();
        oppMsg=new javax.swing.JOptionPane();
        //Configurar el GlassPane.
        objGlaPanLbl=new ZafGlaPanLbl();
        ifruso.setGlassPane(objGlaPanLbl);
        //Variables que determinan si se debe "Confirmar la operacion" antes de realizarla.
        blnConIns=false;
        blnConMod=false;
        blnConEli=true;
        blnConAnu=true;
        blnConImp=true;
        //Obbtener los permisos del usuario.
        objPerUsr=new ZafPerUsr(objParSis);
        if (!objPerUsr.isInsertarEnabled())
            butIns.setVisible(false);
        if (!objPerUsr.isConsultarEnabled())
            butCon.setVisible(false);
        if (!objPerUsr.isModificarEnabled())
            butMod.setVisible(false);
        if (!objPerUsr.isEliminarEnabled())
            butEli.setVisible(false);
        if (!objPerUsr.isAnularEnabled())
            butAnu.setVisible(false);
        if (!objPerUsr.isImprimirEnabled())
            butImp.setVisible(false);
        if (!objPerUsr.isVistaPreliminarEnabled())
            butVis.setVisible(false);
        if (!objPerUsr.isAceptarEnabled())
            butAce.setVisible(false);
        if (!objPerUsr.isCancelarEnabled())
            butCan.setVisible(false);
        //Establecer los ToolTipText.
        butIni.setToolTipText("Inicio");
        butAnt.setToolTipText("Anterior");
        butSig.setToolTipText("Siguiente");
        butFin.setToolTipText("Fin");
        butIns.setToolTipText("Insertar");
        butCon.setToolTipText("Consultar");
        butMod.setToolTipText("Modificar");
        butEli.setToolTipText("Eliminar");
        butAnu.setToolTipText("Anular");
        butImp.setToolTipText("Imprimir");
        butVis.setToolTipText("Vista preliminar");
        butAce.setToolTipText("Aceptar");
        butCan.setToolTipText("Cancelar");
        estListo();
        strOpeClk="n";
      }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbrHer = new javax.swing.JToolBar();
        butIni = new javax.swing.JButton();
        butAnt = new javax.swing.JButton();
        butSig = new javax.swing.JButton();
        butFin = new javax.swing.JButton();
        sepuno = new javax.swing.JSeparator();
        butIns = new javax.swing.JButton();
        butCon = new javax.swing.JButton();
        butMod = new javax.swing.JButton();
        sepdos = new javax.swing.JSeparator();
        butEli = new javax.swing.JButton();
        butAnu = new javax.swing.JButton();
        septre = new javax.swing.JSeparator();
        butImp = new javax.swing.JButton();
        butVis = new javax.swing.JButton();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panSur = new javax.swing.JPanel();
        lblmen = new javax.swing.JLabel();
        panAux = new javax.swing.JPanel();
        lblPosRel = new javax.swing.JLabel();
        lblmodope = new javax.swing.JLabel();
        lblEstReg = new javax.swing.JLabel();
        pgrest = new javax.swing.JProgressBar();

        setLayout(new java.awt.BorderLayout());

        tbrHer.setRollover(true);
        tbrHer.setAutoscrolls(true);

        butIni.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimedia/Imagenes/primero.gif"))); // NOI18N
        butIni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                butIniMousePressed(evt);
            }
        });
        butIni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butIniActionPerformed(evt);
            }
        });
        tbrHer.add(butIni);

        butAnt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimedia/Imagenes/anterior.gif"))); // NOI18N
        butAnt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                butAntMousePressed(evt);
            }
        });
        butAnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAntActionPerformed(evt);
            }
        });
        tbrHer.add(butAnt);

        butSig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimedia/Imagenes/siguiente.gif"))); // NOI18N
        butSig.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                butSigMousePressed(evt);
            }
        });
        butSig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSigActionPerformed(evt);
            }
        });
        tbrHer.add(butSig);

        butFin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimedia/Imagenes/ultimo.gif"))); // NOI18N
        butFin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                butFinMousePressed(evt);
            }
        });
        butFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butFinActionPerformed(evt);
            }
        });
        tbrHer.add(butFin);

        sepuno.setOrientation(javax.swing.SwingConstants.VERTICAL);
        sepuno.setMaximumSize(new java.awt.Dimension(2, 32));
        tbrHer.add(sepuno);

        butIns.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimedia/Imagenes/nuevo.gif"))); // NOI18N
        butIns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butInsActionPerformed(evt);
            }
        });
        tbrHer.add(butIns);

        butCon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimedia/Imagenes/consultar.gif"))); // NOI18N
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        tbrHer.add(butCon);

        butMod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimedia/Imagenes/modificar.gif"))); // NOI18N
        butMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butModActionPerformed(evt);
            }
        });
        tbrHer.add(butMod);

        sepdos.setOrientation(javax.swing.SwingConstants.VERTICAL);
        sepdos.setMaximumSize(new java.awt.Dimension(2, 32));
        tbrHer.add(sepdos);

        butEli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimedia/Imagenes/eliminar.gif"))); // NOI18N
        butEli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEliActionPerformed(evt);
            }
        });
        tbrHer.add(butEli);

        butAnu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimedia/Imagenes/cancelar.gif"))); // NOI18N
        butAnu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAnuActionPerformed(evt);
            }
        });
        tbrHer.add(butAnu);

        septre.setOrientation(javax.swing.SwingConstants.VERTICAL);
        septre.setMaximumSize(new java.awt.Dimension(2, 32));
        tbrHer.add(septre);

        butImp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimedia/Imagenes/print.gif"))); // NOI18N
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        tbrHer.add(butImp);

        butVis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimedia/Imagenes/printpreview.gif"))); // NOI18N
        butVis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisActionPerformed(evt);
            }
        });
        tbrHer.add(butVis);

        butAce.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimedia/Imagenes/ok.gif"))); // NOI18N
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        tbrHer.add(butAce);

        butCan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimedia/Imagenes/cancelar.gif"))); // NOI18N
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        tbrHer.add(butCan);

        add(tbrHer, java.awt.BorderLayout.CENTER);

        panSur.setLayout(new java.awt.GridLayout(1, 2));

        lblmen.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblmen.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblmen.setText("Listo");
        lblmen.setAlignmentX(5.0F);
        lblmen.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblmen.setPreferredSize(new java.awt.Dimension(80, 15));
        panSur.add(lblmen);

        panAux.setLayout(new java.awt.GridLayout(1, 4));

        lblPosRel.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblPosRel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panAux.add(lblPosRel);

        lblmodope.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblmodope.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panAux.add(lblmodope);

        lblEstReg.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblEstReg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panAux.add(lblEstReg);

        pgrest.setBorderPainted(false);
        pgrest.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        panAux.add(pgrest);

        panSur.add(panAux);

        add(panSur, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        intButCli=INT_BUT_CAN;
        if (beforeClickCancelar())
        {
            if (estAct==0)
            {
                salida();
            }
            else
            {
                clickCancelar();
                if (beforeCancelar())
                {
                    if (cancelar())
                    {
                        estListo();
                        afterCancelar();
                    }
                }
            }
        }
}//GEN-LAST:event_butCanActionPerformed

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        intButCli=INT_BUT_ACE;
        if (beforeClickAceptar())
        {
            switch(getEstado())
            {
                case 'l'://Estado 0 => Listo
                    break;
                case 'c'://Estado 1 => clickConsultar
                    if (beforeConsultar())
                    {
                        if(consultar())
                        {
                            estAceConsulta();
                            afterConsultar();
                        }
                    }
                    break;
                case 'x'://Estado 2 => clickModificar
                    if (beforeConsultar())
                    {
                        if(consultar())
                        {
                            estAceModifica();
                            clickModificar();
                        }
                    }
                    break;
                case 'y'://Estado 3 => clickEliminar
                    if (beforeConsultar())
                    {
                        if (consultar())
                        {
                            estAceElimina();
                            clickEliminar();
                        }
                    }
                    break;
                case 'z'://Estado 4 => clickAnular
                    if (beforeConsultar())
                    {
                        if (consultar())
                        {
                            estAceAnula();
                            clickAnular();
                        }
                    }
                    break;
                case 'n': //Insertar.
                    if (blnConIns)
                    {
                        if (mostrarMsgCon("¿Está seguro que desea INSERTAR éste registro?")==0)
                        {
                            if (beforeInsertar())
                            {
                                if (insertar())
                                {
                                    menOperacionOk("INSERTAR");
                                    afterInsertar();
                                }
                                else
                                {
                                    mostrarMsgErr("No se pudo realizar la operación INSERTAR.\nRevise los datos, haga las correcciones necesarias y vuelva a intentarlo.\nSi el problema persiste comuníquelo a su administrador del sistema.");
                                }
                            }
                        }
                    }
                    else
                    {
                        if (beforeInsertar())
                        {
                            if (insertar())
                            {
                                menOperacionOk("INSERTAR");
                                afterInsertar();
                            }
                            else
                            {
                                mostrarMsgErr("No se pudo realizar la operación INSERTAR.\nRevise los datos, haga las correcciones necesarias y vuelva a intentarlo.\nSi el problema persiste comuníquelo a su administrador del sistema.");
                            }
                        }
                    }
                    break;
                case 'm': //Modificar
                    if (blnConMod)
                    {
                        if (mostrarMsgCon("¿Está seguro que desea MODIFICAR éste registro?")==0)
                        {
                            if (beforeModificar())
                            {
                                if (modificar())
                                {
                                    menOperacionOk("MODIFICAR");
                                    afterModificar();
                                }
                                else
                                {
                                    mostrarMsgErr("No se pudo realizar la operación MODIFICAR.\nRevise los datos, haga las correcciones necesarias y vuelva a intentarlo.\nSi el problema persiste comuníquelo a su administrador del sistema.");
                                }
                            }
                        }
                    }
                    else
                    {
                        if (beforeModificar())
                        {
                            if (modificar())
                            {
                                menOperacionOk("MODIFICAR");
                                afterModificar();
                            }
                            else
                            {
                                mostrarMsgErr("No se pudo realizar la operación MODIFICAR.\nRevise los datos, haga las correcciones necesarias y vuelva a intentarlo.\nSi el problema persiste comuníquelo a su administrador del sistema.");
                            }
                        }
                    }
                    break;
                case 'e': //Eliminar
                    if (blnConEli)
                    {
                        if (mostrarMsgCon("¿Está seguro que desea ELIMINAR éste registro?")==0)
                        {
                            if (beforeEliminar())
                            {
                                if (eliminar())
                                {
                                    menOperacionOk("ELIMINAR");
                                    afterEliminar();
                                }
                                else
                                {
                                    mostrarMsgErr("No se pudo realizar la operación ELIMINAR.\nRevise los datos, haga las correcciones necesarias y vuelva a intentarlo.\nSi el problema persiste comuníquelo a su administrador del sistema.");
                                }
                            }

                        }
                    }
                    else
                    {
                        if (beforeEliminar())
                        {
                            if (eliminar())
                            {
                                menOperacionOk("ELIMINAR");
                                afterEliminar();
                            }
                            else
                            {
                                mostrarMsgErr("No se pudo realizar la operación ELIMINAR.\nRevise los datos, haga las correcciones necesarias y vuelva a intentarlo.\nSi el problema persiste comuníquelo a su administrador del sistema.");
                            }
                        }
                    }
                    break;
                case 'a': //Anular
                    if (blnConAnu)
                    {
                        if (mostrarMsgCon("¿Está seguro que desea ANULAR éste registro?")==0)
                        {
                            if (beforeAnular())
                            {
                                if (anular())
                                {
                                    menOperacionOk("ANULAR");
                                    afterAnular();
                                }
                                else
                                {
                                    mostrarMsgErr("No se pudo realizar la operación ANULAR.\nRevise los datos, haga las correcciones necesarias y vuelva a intentarlo.\nSi el problema persiste comuníquelo a su administrador del sistema.");
                                }
                            }
                        }
                    }
                    else
                    {
                        if (beforeAnular())
                        {
                            if (anular())
                            {
                                menOperacionOk("ANULAR");
                                afterAnular();
                            }
                            else
                            {
                                mostrarMsgErr("No se pudo realizar la operación ANULAR.\nRevise los datos, haga las correcciones necesarias y vuelva a intentarlo.\nSi el problema persiste comuníquelo a su administrador del sistema.");
                            }
                        }
                    }
                    break;
            }
        }
}//GEN-LAST:event_butAceActionPerformed

    private void butVisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisActionPerformed
        intButCli=INT_BUT_VIS;
        if (beforeClickVistaPreliminar())
        {
            if (beforeVistaPreliminar())
            {
                if (vistaPreliminar())
                {
                    afterVistaPreliminar();
                }
                else
                {
                    mostrarMsgErr("No se pudo realizar la operación VISTA PRELIMINAR.\nRevise los datos, haga las correcciones necesarias y vuelva a intentarlo.\nSi el problema persiste comuníquelo a su administrador del sistema.");
                }
            }
        }
}//GEN-LAST:event_butVisActionPerformed

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        intButCli=INT_BUT_IMP;
        if (beforeClickImprimir())
        {
            if (blnConImp)
            {
                if (mostrarMsgCon("¿Está seguro que desea IMPRIMIR éste registro?")==0)
                {
                    if (beforeImprimir())
                    {
                        if (imprimir())
                        {
                            afterImprimir();
                        }
                        else
                        {
                            mostrarMsgErr("No se pudo realizar la operación IMPRIMIR.\nRevise los datos, haga las correcciones necesarias y vuelva a intentarlo.\nSi el problema persiste comuníquelo a su administrador del sistema.");
                        }
                    }
                }
            }
            else
            {
                if (beforeImprimir())
                {
                    if (imprimir())
                    {
                        afterImprimir();
                    }
                    else
                    {
                        mostrarMsgErr("No se pudo realizar la operación IMPRIMIR.\nRevise los datos, haga las correcciones necesarias y vuelva a intentarlo.\nSi el problema persiste comuníquelo a su administrador del sistema.");
                    }
                }
            }
        }
}//GEN-LAST:event_butImpActionPerformed

    private void butAnuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAnuActionPerformed
        intButCli=INT_BUT_ANU;
        if (beforeClickAnular())
        {
            if (est[estAct]=='w')
            {
                estAceAnula();
                clickAnular();
            }
            else
            {
                estAnula();
                setMenSis("Ingrese el criterio de búsqueda");
                clickConsultar();
            }
        }
}//GEN-LAST:event_butAnuActionPerformed

    private void butEliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEliActionPerformed
        intButCli=INT_BUT_ELI;
        if (beforeClickEliminar())
        {
            if (est[estAct]=='w')
            {
                estAceElimina();
                clickEliminar();
            }
            else
            {
                estElimina();
                setMenSis("Ingrese el criterio de búsqueda");
                clickConsultar();
            }
        }
}//GEN-LAST:event_butEliActionPerformed

    private void butModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butModActionPerformed
        intButCli=INT_BUT_MOD;
        if (beforeClickModificar())
        {
            if (est[estAct]=='w')
            {
                estAceModifica();
                clickModificar();
            }
            else
            {
                estModifica();
                clickConsultar();
            }
        }
}//GEN-LAST:event_butModActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        intButCli=INT_BUT_CON;
        if (beforeClickConsultar())
        {
            //Validar si la consulta ya fue realizada.
            if (est[estAct]=='w')
            {
                estAceConsulta();
                clickConsultar();
                lblmodope.setText("Consultar");
            }
            else
            {
                estConsulta();
                clickConsultar();
            }
        }
}//GEN-LAST:event_butConActionPerformed

    private void butInsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butInsActionPerformed
        intButCli=INT_BUT_INS;
        if (beforeClickInsertar())
        {
            butIns.setBackground(new java.awt.Color(0, 0, 0));
            estInserta();
            clickInsertar();
        }
}//GEN-LAST:event_butInsActionPerformed

    private void butFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butFinActionPerformed
        intButCli=INT_BUT_FIN;
        if (beforeClickFin())
            clickFin();
}//GEN-LAST:event_butFinActionPerformed

    private void butSigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSigActionPerformed
        intButCli=INT_BUT_SIG;
        if (beforeClickSiguiente())
            clickSiguiente();
}//GEN-LAST:event_butSigActionPerformed

    private void butAntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAntActionPerformed
        intButCli=INT_BUT_ANT;
        if (beforeClickAnterior())
            clickAnterior();
}//GEN-LAST:event_butAntActionPerformed

    private void butIniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butIniActionPerformed
        intButCli=INT_BUT_INI;
        if (beforeClickInicio())
            clickInicio();
}//GEN-LAST:event_butIniActionPerformed

    private void butIniMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butIniMousePressed
        strOpeClk="i";
}//GEN-LAST:event_butIniMousePressed

    private void butAntMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butAntMousePressed
        strOpeClk="a";
}//GEN-LAST:event_butAntMousePressed

    private void butSigMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butSigMousePressed
        strOpeClk="s";
}//GEN-LAST:event_butSigMousePressed

    private void butFinMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butFinMousePressed
        strOpeClk="f";
}//GEN-LAST:event_butFinMousePressed
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butAnt;
    private javax.swing.JButton butAnu;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEli;
    private javax.swing.JButton butFin;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butIni;
    private javax.swing.JButton butIns;
    private javax.swing.JButton butMod;
    private javax.swing.JButton butSig;
    private javax.swing.JButton butVis;
    private javax.swing.JLabel lblEstReg;
    private javax.swing.JLabel lblPosRel;
    private javax.swing.JLabel lblmen;
    private javax.swing.JLabel lblmodope;
    private javax.swing.JPanel panAux;
    private javax.swing.JPanel panSur;
    private javax.swing.JProgressBar pgrest;
    private javax.swing.JSeparator sepdos;
    private javax.swing.JSeparator septre;
    private javax.swing.JSeparator sepuno;
    private javax.swing.JToolBar tbrHer;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Esta función obtiene el estado en el que se encuentra actualmente el ZafToolBar.
     * <BR>Los valores posibles son:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Caracter</I></TD></TD><TD><I>Conclusión</I></TD></TR>
     *     <TR><TD>l</TD><TD>Modo Listo.</TD></TR>
     *     <TR><TD>c</TD><TD>Modo Consultar (Previo).</TD></TR>
     *     <TR><TD>x</TD><TD>Modo Modificar (Previo).</TD></TR>
     *     <TR><TD>y</TD><TD>Modo Eliminar (Previo).</TD></TR>
     *     <TR><TD>z</TD><TD>Modo Anular (Previo).</TD></TR>
     *     <TR><TD>n</TD><TD>Modo Insertar.</TD></TR>
     *     <TR><TD>w</TD><TD>Modo Consulta realizada.</TD></TR>
     *     <TR><TD>m</TD><TD>Modo Modificar.</TD></TR>
     *     <TR><TD>e</TD><TD>Modo Eliminar.</TD></TR>
     *     <TR><TD>a</TD><TD>Modo Anular.</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return El estado actual del ZafToolBar.
     */
    public char getEstado()
    {
        if (est[estAct]=='w')
            return subestcon[subest];
        else
            return est[estAct];
    }
    
    /**
     * Esta función establece el estado al que debe cambiar el ZafToolBar.
     * <BR>Los valores posibles son:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Caracter</I></TD></TD><TD><I>Conclusión</I></TD></TR>
     *     <TR><TD>l</TD><TD>Modo Listo.</TD></TR>
     *     <TR><TD>c</TD><TD>Modo Consultar (Previo).</TD></TR>
     *     <TR><TD>x</TD><TD>Modo Modificar (Previo).</TD></TR>
     *     <TR><TD>y</TD><TD>Modo Eliminar (Previo).</TD></TR>
     *     <TR><TD>z</TD><TD>Modo Anular (Previo).</TD></TR>
     *     <TR><TD>n</TD><TD>Modo Insertar.</TD></TR>
     *     <TR><TD>w</TD><TD>Modo Consulta realizada.</TD></TR>
     *     <TR><TD>m</TD><TD>Modo Modificar.</TD></TR>
     *     <TR><TD>e</TD><TD>Modo Eliminar.</TD></TR>
     *     <TR><TD>a</TD><TD>Modo Anular.</TD></TR>
     * </TABLE>
     * </CENTER>
     * @param chEst El nuevo estado del ZafToolBar.
     */
    public void setEstado(char chEst)
    {
        switch (chEst)
        {
            case 'l'://Estado listo
                estListo();
                break;
            case 'c'://Consultar (Previo)
                estConsulta();
                clickConsultar();
                break;
            case 'x'://Modificar (Previo)
                estModifica();
                clickConsultar();
                break;
            case 'y'://Eliminar (Previo)
                estElimina();
                setMenSis("Ingrese el criterio de búsqueda");
                clickConsultar();
                break;
            case 'z'://Anular (Previo)
                estAnula();
                setMenSis("Ingrese el criterio de búsqueda");
                clickConsultar();
                break;
            case 'n'://Insertar
                estInserta();
                clickInsertar();
                break;
            case 'm'://Modificar (Luego de consultar)
                estAceModifica();
                clickModificar();
                break;
            case 'e'://Eliminar (Luego de consultar)
                estAceElimina();
                clickEliminar();
                break;
            case 'a'://Anular (Luego de consultar)
                estAceAnula();
                clickAnular();
                break;
            case 'w'://Consulta realizada
                estAceConsulta();
                break;
                
                
                
                
                
        }
    }
    
    /**
     * Método que retorna un caracter que indica en cual de los siguientes estados se encuentra
     * el usuario pero después de que se ha realizado una Consulta previa:
     *
     * Los Posibles estados son:
     *
     *     Retorno      Descripcion
     *        m           Modificar
     *        e           Eliminar
     *        a           Anular
     *        n           Ninguno
     * @return Retorna un caracter que indica el estado en el que se encuentra posterior a una consulta
     * realizada.
     */    
    private char getSubEstado()
    {
        return subestcon[subest];
    }
    
    private void estInserta()
    {
        //Guardar el estado anterior.
        intEstAnt=estAct;
        estAct=5;
        desactivarBot();
        butAce.setEnabled(true);
        butCan.setEnabled(true);
        objUti.activarCom(ifruso);
        lblPosRel.setText("");
        lblmodope.setText("Insertar");
        lblEstReg.setText("");
        lblmen.setText("Ingrese los datos del registro");
        objGlaPanLbl.setVisible(false);
    }
    
    private void estListo()
    {
        int i;
        //Guardar el estado anterior.
        intEstAnt=estAct;
        estAct=0;
        activarBot();
        desactivarBotDir();
        butAce.setEnabled(false);
        butVis.setEnabled(false);
        butImp.setEnabled(false);
        objUti.desactivarCom(ifruso);
        setMenSis("Listo");
        lblmodope.setText("");
        lblPosRel.setText("");
        lblEstReg.setText("");
        objGlaPanLbl.setVisible(false);
        //Restaurar el botón "Insertar".
        butIns.setBackground(butAce.getBackground());
        //Inactivar los botones agregados dinámicamente.
        for (i=16;i<tbrHer.getComponentCount();i++)
            tbrHer.getComponentAtIndex(i).setEnabled(false);
    }
    
    private void estConsulta()
    {
        //Guardar el estado anterior.
        intEstAnt=estAct;
        estAct=1; //Estado de consulta
        desactivarBot();
        butAce.setEnabled(true);
        butCan.setEnabled(true);
        objUti.activarCom(ifruso);
        setMenSis("Ingrese el criterio de búsqueda");
        lblmodope.setText("Consultar");
    }
    
    private void estModifica()
    {
        //Guardar el estado anterior.
        intEstAnt=estAct;
        estAct=2;
        desactivarBot();
        butAce.setEnabled(true);
        butCan.setEnabled(true);
        objUti.activarCom(ifruso);
        lblmodope.setText("Modificar");
        setMenSis("Ingrese el criterio de búsqueda");
    }
    
    private void estAnula()
    {
        //Guardar el estado anterior.
        intEstAnt=estAct;
        estAct=4;
        desactivarBot();
        butAce.setEnabled(true);
        butCan.setEnabled(true);
        objUti.activarCom(ifruso);
        lblmodope.setText("Anular");
    }
    
    private void estElimina()
    {
        //Guardar el estado anterior.
        intEstAnt=estAct;
        estAct=3;
        desactivarBot();
        butAce.setEnabled(true);
        butCan.setEnabled(true);
        objUti.activarCom(ifruso);
        lblmodope.setText("Eliminar");
    }
    
    private void estAceConsulta()
    {
        //Guardar el estado anterior.
        intEstAnt=estAct;
        intSubEstAnt=subest;
        estAct=6;//
        subest=3;//
        activarBot();
        butCon.setEnabled(false);
        butAce.setEnabled(false);
        objUti.desactivarCom(ifruso);
        lblmodope.setText("Consultar");
        //Restaurar el botón "Insertar".
        butIns.setBackground(butAce.getBackground());
    }
    
    private void estAceModifica()
    {
        //Guardar el estado anterior.
        intEstAnt=estAct;
        intSubEstAnt=subest;
        estAct=6;
        subest=0;
        activarBot();
        butMod.setEnabled(false);
        objUti.activarCom(ifruso);
        lblmodope.setText("Modificar");
    }
    
    private void estAceElimina()
    {
        //Guardar el estado anterior.
        intEstAnt=estAct;
        intSubEstAnt=subest;
        estAct=6; //permanece en estado consulta
        subest=1;
        activarBot();
        butEli.setEnabled(false);
        objUti.desactivarCom(ifruso);
        lblmodope.setText("Eliminar");
    }

    private void estAceAnula()
    {
        //Guardar el estado anterior.
        intEstAnt=estAct;
        intSubEstAnt=subest;
        estAct=6; //permanece en estado consulta
        subest=2;
        activarBot();
        butAnu.setEnabled(false);
        objUti.desactivarCom(ifruso);
        lblmodope.setText("Anular");
    }
    
  
    
    
    
    /**
     * Activar todos los botones de la barra de herramientas
     */    
    private void activarBot()
    {
        butIni.setEnabled(true);
        butAnt.setEnabled(true);
        butSig.setEnabled(true);
        butFin.setEnabled(true);
        butIns.setEnabled(true);
        butCon.setEnabled(true);
        butMod.setEnabled(true);
        butEli.setEnabled(true);
        butAnu.setEnabled(true);
        butImp.setEnabled(true);
        butVis.setEnabled(true);
        butAce.setEnabled(true);
        butCan.setEnabled(true);
    }
    
    /**
     * Desactivar todos los botones de la barra de herramientas
     */    
    private void desactivarBot()
    {
        butIni.setEnabled(false);
        butAnt.setEnabled(false);
        butSig.setEnabled(false);
        butFin.setEnabled(false);
        butIns.setEnabled(false);
        butCon.setEnabled(false);
        butMod.setEnabled(false);
        butEli.setEnabled(false);
        butAnu.setEnabled(false);
        butImp.setEnabled(false);
        butVis.setEnabled(false);
        butAce.setEnabled(false);
        butCan.setEnabled(false);
    }


    /**
     * Desactiva los botones de direccion de la barra de herramientas
     */
    private void desactivarBotDir()
    {
        butIni.setEnabled(false);
        butAnt.setEnabled(false);
        butSig.setEnabled(false);
        butFin.setEnabled(false);
    }        

    /**
     * Activa los botones de direccion de la barra de herramientas
     */
    private void activarBotDir()
    {
        butIni.setEnabled(true);
        butAnt.setEnabled(true);
        butSig.setEnabled(true);
        butFin.setEnabled(true);
    }        

    public void salida()
    {
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        int op = oppMsg.showConfirmDialog(ifruso,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
        if (op==0)
        {
            ifruso.dispose();
        }
    }
    
    private void menOperacionOk(String strOpe){
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="La operación " + strOpe +  " se realizó con éxito.";
        oppMsg.showMessageDialog(ifruso,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private boolean menEliminacion(){
        String strTit, strMsg;
        boolean choice=false;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea eliminar este registro?";
        int op = oppMsg.showConfirmDialog(ifruso,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
        if (op == 0)
            choice=true;
        else
            choice=false;
        return choice;
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(ifruso,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que algo salió mal.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(ifruso,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Cambia los TextToolTipText de los botones de la barra de herramientas
     * @param strnue TextToolTipText del botón Nuevo
     * @param strcon TextToolTipText del botón Consultar
     * @param strmod TextToolTipText del botón Modificar
     * @param streli TextToolTipText del botón Eliminar
     * @param stranu TextToolTipText del botón Anular
     * @param strimp TextToolTipText del botón Imprimir
     * @param strvis TextToolTipText del botón Vista Previa
     * @param strace TextToolTipText del botón Aceptar
     * @param strcan TextToolTipText del botón Cancelar
     * @param strpri TextToolTipText del botón de dirección que va al primer elemento
     * @param strant TextToolTipText del botón de dirección que va al elemento anterior
     * @param strade TextToolTipText del botón de dirección que va al siguiente elemento
     * @param strult TextToolTipText del botón de dirección que va al último elemento
     */
    public void cambiarTexTooTipTex(String strpri, String strant, String strade, String strult,
                                    String strnue, String strcon, String strmod, String streli,
                                    String stranu, String strimp, String strvis, String strace,
                                    String strcan)
    {
        butIni.setToolTipText(strpri);
        butAnt.setToolTipText(strant);
        butSig.setToolTipText(strade);
        butFin.setToolTipText(strult);
        butIns.setToolTipText(strnue);
        butCon.setToolTipText(strcon);
        butMod.setToolTipText(strmod);
        butEli.setToolTipText(streli);
        butAnu.setToolTipText(stranu);
        butImp.setToolTipText(strimp);
        butVis.setToolTipText(strvis);
        butAce.setToolTipText(strace);
        butCan.setToolTipText(strcan);
    }

    /**
     * Estable el Modo de Operaciï¿½n en el que se encuentre el usuario. Los modos pueden ser
     * Consulta, Modificacion, Eliminaciï¿½n, etc
     * @param strmodope String que contiene el Modo de Operaciï¿½n del Usuario
     */
    private void setModOpe(String strmodope)
    {
        lblmodope.setText(strmodope);
    }
    
    /**
     * Establece los mensajes que el sistema pueda generar de salida para el usuario
     * @param strmen String que contiene el mensaje de sistema a mostrar en el tag que se encuentra en
     * la parte inferior izquierda de la barra de tareas.
     */
    public void setMenSis(String strmen)
    {
        lblmen.setText(strmen); 
    }
    
    /**
     * Recibe una variable de tipo entero que establece el tamaï¿½o mï¿½ximo del JProgressBar
     * que maneja esta clase.
     * @param Val_Max Entero que representa el tamaï¿½o del JProgressBar
     */
    public void setValMax(int Val_Max){
        ValMax = Val_Max;
        pgrest.setMaximum(ValMax);
     }
   
    /**
     * Inicia el hilo de ejecuciï¿½n que es el encargado de controlar al JProgressBar
     */
    public void iniciarPro(){
        hilo obj_hilo = new hilo();
        pgrest.setStringPainted(true);
        obj_hilo.start();
     }
    
    /**
     * Recibe una variable de tipo entero y se encarga de establecer el valor actual del
     * JProgressBar
     * @param Val_Pro Entero que contiene el valor actual del JProgressBar
     */    
     public void setValAct (int Val_Pro){
        ValAct=Val_Pro;
     }
     
     private void finalizarPro(){
        ValMax=0;
        ValAct=0;
        pgrest.setValue(0);
        pgrest.setString("");
        pgrest.setStringPainted(false);
     }
     
    /**
     * Esta función obtiene el estado del registro. El campo "Estado del registro"
     * se utiliza para mostrar el estado en el que se encuentra un registro. Por ejemplo,
     * podría contener "Activo" indicando que dicho registro se encuentra activo.
     * @return El estado del registro.
     */
    public String getEstadoRegistro()
    {
        return lblEstReg.getText();
    }
    
    /**
     * Esta función establece el estado del registro. El campo "Estado del registro"
     * se utiliza para mostrar el estado en el que se encuentra un registro. Por ejemplo,
     * podría contener "Activo" indicando que dicho registro se encuentra activo.
     * @param estado El estado del registro.
     */
    public void setEstadoRegistro(String estado)
    {
        lblEstReg.setText(estado);
        if (estado.equals("Anulado"))
        {
            objGlaPanLbl.setText("Anulado");
            objGlaPanLbl.setVisible(true);
        }
        else if (estado.equals("Eliminado"))
        {
            objGlaPanLbl.setText("Eliminado");
            objGlaPanLbl.setVisible(true);
        }
        else if (estado.equals("Predocumento"))
        {
            objGlaPanLbl.setText("Predocumento");
            objGlaPanLbl.setVisible(true);
        }
        else
        {
            objGlaPanLbl.setText("");
            objGlaPanLbl.setVisible(false);
        }
    }
    
    /**
     * Esta función obtiene la posición relativa del registro. Por ejemplo, si se
     * ha hecho una consulta que devuelve 80 registros y actualmente nos encontramos
     * en el registro 12 se debería mostrar 12/80.
     * @return El estado del registro.
     */
    public String getPosicionRelativa()
    {
        return lblPosRel.getText();
    }
    
    /**
     * Esta función establece la posición relativa del registro. Por ejemplo, si se
     * ha hecho una consulta que devuelve 80 registros y actualmente nos encontramos
     * en el registro 12 se debería mostrar 12/80.
     * @param estado El estado del registro.
     */
    public void setPosicionRelativa(String posicion)
    {
        lblPosRel.setText(posicion);
    }
    
    /**
     * Esta función determina si se debe confirmar antes de realizar la operación "Insertar".
     * @return true: Si se debe solicitar confirmación.
     * <BR>false: En el caso contrario.
     */
    public boolean isConfirmarInsertar()
    {
        return blnConIns;
    }
    
    /**
     * Esta función establece si se debe confirmar antes de realizar la operación "Insertar".
     * @param confirmar Un valor booleano que determina si se debe solicitar confirmación.
     */
    public void setConfirmarInsertar(boolean confirmar)
    {
        blnConIns=confirmar;
    }
    
    /**
     * Esta función determina si se debe confirmar antes de realizar la operación "Modificar".
     * @return true: Si se debe solicitar confirmación.
     * <BR>false: En el caso contrario.
     */
    public boolean isConfirmarModificar()
    {
        return blnConMod;
    }
    
    /**
     * Esta función establece si se debe confirmar antes de realizar la operación "Modificar".
     * @param confirmar Un valor booleano que determina si se debe solicitar confirmación.
     */
    public void setConfirmarModificar(boolean confirmar)
    {
        blnConMod=confirmar;
    }
    
    /**
     * Esta función determina si se debe confirmar antes de realizar la operación "Eliminar".
     * @return true: Si se debe solicitar confirmación.
     * <BR>false: En el caso contrario.
     */
    public boolean isConfirmarEliminar()
    {
        return blnConEli;
    }
    
    /**
     * Esta función establece si se debe confirmar antes de realizar la operación "Eliminar".
     * @param confirmar Un valor booleano que determina si se debe solicitar confirmación.
     */
    public void setConfirmarEliminar(boolean confirmar)
    {
        blnConEli=confirmar;
    }
    
    /**
     * Esta función determina si se debe confirmar antes de realizar la operación "Anular".
     * @return true: Si se debe solicitar confirmación.
     * <BR>false: En el caso contrario.
     */
    public boolean isConfirmarAnular()
    {
        return blnConAnu;
    }
    
    /**
     * Esta función establece si se debe confirmar antes de realizar la operación "Anular".
     * @param confirmar Un valor booleano que determina si se debe solicitar confirmación.
     */
    public void setConfirmarAnular(boolean confirmar)
    {
        blnConAnu=confirmar;
    }

    /**
     * Esta función determina si el botón "Insertar" es visible o invisible.
     * @return true: Si el botón "Insertar" es visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isVisibleInsertar()
    {
        return butIns.isVisible();
    }
    
    /**
     * Esta función establece si el botón "Insertar" debe ser visible o invisible.
     * @param visible El estado que determina si el botón "Insertar" es visible o invisible.
     */
    public void setVisibleInsertar(boolean visible)
    {
        butIns.setVisible(visible);
    }
    
    /**
     * Esta función establece si el botón "Insertar" debe estar habilitado o inhabilitado.
     * @param enabled El estado que determina si el botón "Insertar" está habilitado o inhabilitado.
     */
    public void setEnabledInsertar(boolean enabled)
    {
        butIns.setEnabled(enabled);
    }
    
    /**
     * Esta función determina si el botón "Consultar" es visible o invisible.
     * @return true: Si el botón "Consultar" es visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isVisibleConsultar()
    {
        return butCon.isVisible();
    }
    
    /**
     * Esta función establece si el botón "Consultar" debe ser visible o invisible.
     * @param visible El estado que determina si el botón "Consultar" es visible o invisible.
     */
    public void setVisibleConsultar(boolean visible)
    {
        butCon.setVisible(visible);
    }
    
    /**
     * Esta función establece si el botón "Consultar" debe estar habilitado o inhabilitado.
     * @param enabled El estado que determina si el botón "Consultar" está habilitado o inhabilitado.
     */
    public void setEnabledConsultar(boolean enabled)
    {
        butCon.setEnabled(enabled);
    }
    
    /**
     * Esta función determina si el botón "Modificar" es visible o invisible.
     * @return true: Si el botón "Modificar" es visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isVisibleModificar()
    {
        return butMod.isVisible();
    }
    
    /**
     * Esta función establece si el botón "Modificar" debe ser visible o invisible.
     * @param visible El estado que determina si el botón "Modificar" es visible o invisible.
     */
    public void setVisibleModificar(boolean visible)
    {
        butMod.setVisible(visible);
    }
    
    /**
     * Esta función establece si el botón "Modificar" debe estar habilitado o inhabilitado.
     * @param enabled El estado que determina si el botón "Modificar" está habilitado o inhabilitado.
     */
    public void setEnabledModificar(boolean enabled)
    {
        butMod.setEnabled(enabled);
    }
    
    /**
     * Esta función determina si el botón "Eliminar" es visible o invisible.
     * @return true: Si el botón "Eliminar" es visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isVisibleEliminar()
    {
        return butEli.isVisible();
    }
    
    /**
     * Esta función establece si el botón "Eliminar" debe ser visible o invisible.
     * @param visible El estado que determina si el botón "Eliminar" es visible o invisible.
     */
    public void setVisibleEliminar(boolean visible)
    {
        butEli.setVisible(visible);
    }
    
    /**
     * Esta función establece si el botón "Eliminar" debe estar habilitado o inhabilitado.
     * @param enabled El estado que determina si el botón "Eliminar" está habilitado o inhabilitado.
     */
    public void setEnabledEliminar(boolean enabled)
    {
        butEli.setEnabled(enabled);
    }
    
    /**
     * Esta función determina si el botón "Anular" es visible o invisible.
     * @return true: Si el botón "Anular" es visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isVisibleAnular()
    {
        return butAnu.isVisible();
    }
    
    /**
     * Esta función establece si el botón "Anular" debe ser visible o invisible.
     * @param visible El estado que determina si el botón "Anular" es visible o invisible.
     */
    public void setVisibleAnular(boolean visible)
    {
        butAnu.setVisible(visible);
    }
    
    /**
     * Esta función establece si el botón "Anular" debe estar habilitado o inhabilitado.
     * @param enabled El estado que determina si el botón "Anular" está habilitado o inhabilitado.
     */
    public void setEnabledAnular(boolean enabled)
    {
        butAnu.setEnabled(enabled);
    }
    
    /**
     * Esta función determina si el botón "Imprimir" es visible o invisible.
     * @return true: Si el botón "Imprimir" es visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isVisibleImprimir()
    {
        return butImp.isVisible();
    }
    
    /**
     * Esta función establece si el botón "Imprimir" debe ser visible o invisible.
     * @param visible El estado que determina si el botón "Imprimir" es visible o invisible.
     */
    public void setVisibleImprimir(boolean visible)
    {
        butImp.setVisible(visible);
    }
    
    /**
     * Esta función establece si el botón "Imprimir" debe estar habilitado o inhabilitado.
     * @param enabled El estado que determina si el botón "Imprimir" está habilitado o inhabilitado.
     */
    public void setEnabledImprimir(boolean enabled)
    {
        butImp.setEnabled(enabled);
    }
    
    /**
     * Esta función determina si el botón "VistaPreliminar" es visible o invisible.
     * @return true: Si el botón "VistaPreliminar" es visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isVisibleVistaPreliminar()
    {
        return butVis.isVisible();
    }
    
    /**
     * Esta función establece si el botón "VistaPreliminar" debe ser visible o invisible.
     * @param visible El estado que determina si el botón "VistaPreliminar" es visible o invisible.
     */
    public void setVisibleVistaPreliminar(boolean visible)
    {
        butVis.setVisible(visible);
    }
    
    /**
     * Esta función establece si el botón "VistaPreliminar" debe estar habilitado o inhabilitado.
     * @param enabled El estado que determina si el botón "VistaPreliminar" está habilitado o inhabilitado.
     */
    public void setEnabledVistaPreliminar(boolean enabled)
    {
        butVis.setEnabled(enabled);
    }
    
    /**
     * Esta función determina si el botón "Aceptar" es visible o invisible.
     * @return true: Si el botón "Aceptar" es visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isVisibleAceptar()
    {
        return butAce.isVisible();
    }
    
    /**
     * Esta función establece si el botón "Aceptar" debe ser visible o invisible.
     * @param visible El estado que determina si el botón "Aceptar" es visible o invisible.
     */
    public void setVisibleAceptar(boolean visible)
    {
        butAce.setVisible(visible);
    }
    
    /**
     * Esta función establece si el botón "Aceptar" debe estar habilitado o inhabilitado.
     * @param enabled El estado que determina si el botón "Aceptar" está habilitado o inhabilitado.
     */
    public void setEnabledAceptar(boolean enabled)
    {
        butAce.setEnabled(enabled);
    }
    
    /**
     * Esta función determina si el botón "Cancelar" es visible o invisible.
     * @return true: Si el botón "Cancelar" es visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isVisibleCancelar()
    {
        return butCan.isVisible();
    }
    
    /**
     * Esta función establece si el botón "Cancelar" debe ser visible o invisible.
     * @param visible El estado que determina si el botón "Cancelar" es visible o invisible.
     */
    public void setVisibleCancelar(boolean visible)
    {
        butCan.setVisible(visible);
    }
    
    /**
     * Esta función establece si el botón "Cancelar" debe estar habilitado o inhabilitado.
     * @param enabled El estado que determina si el botón "Cancelar" está habilitado o inhabilitado.
     */
    public void setEnabledCancelar(boolean enabled)
    {
        butCan.setEnabled(enabled);
    }
    
    /**
     * Esta función obtiene el estado en el que se encontraba anteriormente el ZafToolBar.
     * Por ejemplo: el estado anterior podría haber sido "Consultar" y actualmente puede ser "Modificar".
     * <BR>Los valores posibles son:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Caracter</I></TD></TD><TD><I>Conclusión</I></TD></TR>
     *     <TR><TD>l</TD><TD>Modo Listo.</TD></TR>
     *     <TR><TD>c</TD><TD>Modo Consultar (Previo).</TD></TR>
     *     <TR><TD>x</TD><TD>Modo Modificar (Previo).</TD></TR>
     *     <TR><TD>y</TD><TD>Modo Eliminar (Previo).</TD></TR>
     *     <TR><TD>z</TD><TD>Modo Anular (Previo).</TD></TR>
     *     <TR><TD>n</TD><TD>Modo Insertar.</TD></TR>
     *     <TR><TD>w</TD><TD>Modo Consulta realizada.</TD></TR>
     *     <TR><TD>m</TD><TD>Modo Modificar.</TD></TR>
     *     <TR><TD>e</TD><TD>Modo Eliminar.</TD></TR>
     *     <TR><TD>a</TD><TD>Modo Anular.</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return El estado anterior del ZafToolBar.
     */
    public char getEstadoAnterior()
    {
        if (est[intEstAnt]=='w')
            return subestcon[intSubEstAnt];
        else
            return est[intEstAnt];
    }
    
//    /**
//     * Esta función establece si se debe mostrar el mensaje de fracaso de operación. Luego
//     * de realizar una operación de inserción, modificación, etc. se debe mostrar un mensaje
//     * de éxito o de fracaso. Por lo general antes de realizar una operación se valida los
//     * datos. Si dicho código se implementa en los métodos "insertar, modificar, etc." y se
//     * muestra un mensaje informativo cuando no se cumple alguna validación. Lo que ocurriría
//     * es que se mostraría el mensaje que corresponde a la validación y también el mensaje
//     * que corresponde al fracaso. Para evitar éste problema se podría utilizar el método 
//     * "setMostrarFracasoOperacion(false)" antes de iniciar las validaciones y 
//     * "setMostrarFracasoOperacion(true)" luego de haber cumplido todas las validaciones.
//     * @param mostrar El estado que determina si se muestra el mensaje de fracaso.
//     */
//    public void setMostrarFracasoOperacion(boolean mostrar)
//    {
//        blnMosMsgFra=mostrar;
//    }
    
    public class hilo extends Thread
    {
        int PorPro;

        /**
         * El método run() del hilo de ejecuciï¿½n se encarga de interactuar con un mï¿½todo, funciï¿½n u
         * otro hilo, recibe los valores de cambio que sufre el JProgressBar y aplica los cambios en
         * el mismo. Este mï¿½todo se encarga bï¿½sicamente de hacer que el objeto JProgressBar incremente
         * su valor y pinte el porcentaje del trabajo realizado hasta el momento.
         */        
        public void run()
        {
            while(true)
            {
                pgrest.setValue(ValAct); 
                PorPro = (ValAct==0)? 0 : (ValAct*100/ValMax);
                pgrest.setString(Integer.toString(PorPro) + " %");
                if(ValAct==ValMax)
                {
                    finalizarPro();
                    this.stop();
                }
            }//fin while
        }//fin run
     }//fin de hilo
    
    
    public void setOperacionSeleccionada(String strOpeClk){
        this.strOpeClk=strOpeClk;
    }
    
    public String getOperacionSeleccionada(){
        return strOpeClk;
    }

    /**
     * Esta función se ejecuta antes que la función "clickInicio()" y condiciona
     * la ejecución de la misma. Es decir, de acuerdo al valor retornado por esta
     * función se determinará si se ejecuta o no la función "clickInicio()".
     * @return true: Permitirá que se ejecute la función "clickInicio()".
     * <BR>false: Evitará que se ejecute la función "clickInicio()".
     * <BR>Nota.- La función devolverá "true" de forma predeterminada si Ud. no implementa
     * el código para ésta función.
     */
    public boolean beforeClickInicio()
    {
        return true;
    }

    /**
     * Esta función se ejecuta antes que la función "clickAnterior()" y condiciona
     * la ejecución de la misma. Es decir, de acuerdo al valor retornado por esta
     * función se determinará si se ejecuta o no la función "clickAnterior()".
     * @return true: Permitirá que se ejecute la función "clickAnterior()".
     * <BR>false: Evitará que se ejecute la función "clickAnterior()".
     * <BR>Nota.- La función devolverá "true" de forma predeterminada si Ud. no implementa
     * el código para ésta función.
     */
    public boolean beforeClickAnterior()
    {
        return true;
    }

    /**
     * Esta función se ejecuta antes que la función "clickSiguiente()" y condiciona
     * la ejecución de la misma. Es decir, de acuerdo al valor retornado por esta
     * función se determinará si se ejecuta o no la función "clickSiguiente()".
     * @return true: Permitirá que se ejecute la función "clickSiguiente()".
     * <BR>false: Evitará que se ejecute la función "clickSiguiente()".
     * <BR>Nota.- La función devolverá "true" de forma predeterminada si Ud. no implementa
     * el código para ésta función.
     */
    public boolean beforeClickSiguiente()
    {
        return true;
    }

    /**
     * Esta función se ejecuta antes que la función "clickFin()" y condiciona
     * la ejecución de la misma. Es decir, de acuerdo al valor retornado por esta
     * función se determinará si se ejecuta o no la función "clickFin()".
     * @return true: Permitirá que se ejecute la función "clickFin()".
     * <BR>false: Evitará que se ejecute la función "clickFin()".
     * <BR>Nota.- La función devolverá "true" de forma predeterminada si Ud. no implementa
     * el código para ésta función.
     */
    public boolean beforeClickFin()
    {
        return true;
    }

    /**
     * Esta función se ejecuta antes que la función "clickInsertar()" y condiciona
     * la ejecución de la misma. Es decir, de acuerdo al valor retornado por esta 
     * función se determinará si se ejecuta o no la función "clickInsertar()".
     * @return true: Permitirá que se ejecute la función "clickInsertar()".
     * <BR>false: Evitará que se ejecute la función "clickInsertar()".
     * <BR>Nota.- La función devolverá "true" de forma predeterminada si Ud. no implementa
     * el código para ésta función.
     */
    public boolean beforeClickInsertar()
    {
        return true;
    }

    /**
     * Esta función se ejecuta antes que la función "clickConsultar()" y condiciona
     * la ejecución de la misma. Es decir, de acuerdo al valor retornado por esta
     * función se determinará si se ejecuta o no la función "clickConsultar()".
     * @return true: Permitirá que se ejecute la función "clickConsultar()".
     * <BR>false: Evitará que se ejecute la función "clickConsultar()".
     * <BR>Nota.- La función devolverá "true" de forma predeterminada si Ud. no implementa
     * el código para ésta función.
     */
    public boolean beforeClickConsultar()
    {
        return true;
    }

    /**
     * Esta función se ejecuta antes que la función "clickModificar()" y condiciona
     * la ejecución de la misma. Es decir, de acuerdo al valor retornado por esta
     * función se determinará si se ejecuta o no la función "clickModificar()".
     * @return true: Permitirá que se ejecute la función "clickModificar()".
     * <BR>false: Evitará que se ejecute la función "clickModificar()".
     * <BR>Nota.- La función devolverá "true" de forma predeterminada si Ud. no implementa
     * el código para ésta función.
     */
    public boolean beforeClickModificar()
    {
        return true;
    }

    /**
     * Esta función se ejecuta antes que la función "clickEliminar()" y condiciona
     * la ejecución de la misma. Es decir, de acuerdo al valor retornado por esta
     * función se determinará si se ejecuta o no la función "clickEliminar()".
     * @return true: Permitirá que se ejecute la función "clickEliminar()".
     * <BR>false: Evitará que se ejecute la función "clickEliminar()".
     * <BR>Nota.- La función devolverá "true" de forma predeterminada si Ud. no implementa
     * el código para ésta función.
     */
    public boolean beforeClickEliminar()
    {
        return true;
    }

    /**
     * Esta función se ejecuta antes que la función "clickAnular()" y condiciona
     * la ejecución de la misma. Es decir, de acuerdo al valor retornado por esta
     * función se determinará si se ejecuta o no la función "clickAnular()".
     * @return true: Permitirá que se ejecute la función "clickAnular()".
     * <BR>false: Evitará que se ejecute la función "clickAnular()".
     * <BR>Nota.- La función devolverá "true" de forma predeterminada si Ud. no implementa
     * el código para ésta función.
     */
    public boolean beforeClickAnular()
    {
        return true;
    }

    /**
     * Esta función se ejecuta antes que la función "clickImprimir()" y condiciona
     * la ejecución de la misma. Es decir, de acuerdo al valor retornado por esta
     * función se determinará si se ejecuta o no la función "clickImprimir()".
     * @return true: Permitirá que se ejecute la función "clickImprimir()".
     * <BR>false: Evitará que se ejecute la función "clickImprimir()".
     * <BR>Nota.- La función devolverá "true" de forma predeterminada si Ud. no implementa
     * el código para ésta función.
     */
    public boolean beforeClickImprimir()
    {
        return true;
    }
    
    /**
     * Esta función se ejecuta antes que la función "clickVistaPreliminar()" y condiciona
     * la ejecución de la misma. Es decir, de acuerdo al valor retornado por esta
     * función se determinará si se ejecuta o no la función "clickVistaPreliminar()".
     * @return true: Permitirá que se ejecute la función "clickVistaPreliminar()".
     * <BR>false: Evitará que se ejecute la función "clickVistaPreliminar()".
     * <BR>Nota.- La función devolverá "true" de forma predeterminada si Ud. no implementa
     * el código para ésta función.
     */
    public boolean beforeClickVistaPreliminar()
    {
        return true;
    }

    /**
     * Esta función se ejecuta antes que la función "clickAceptar()" y condiciona
     * la ejecución de la misma. Es decir, de acuerdo al valor retornado por esta
     * función se determinará si se ejecuta o no la función "clickAceptar()".
     * @return true: Permitirá que se ejecute la función "clickAceptar()".
     * <BR>false: Evitará que se ejecute la función "clickAceptar()".
     * <BR>Nota.- La función devolverá "true" de forma predeterminada si Ud. no implementa
     * el código para ésta función.
     */
    public boolean beforeClickAceptar()
    {
        return true;
    }

    /**
     * Esta función se ejecuta antes que la función "clickCancelar()" y condiciona
     * la ejecución de la misma. Es decir, de acuerdo al valor retornado por esta
     * función se determinará si se ejecuta o no la función "clickCancelar()".
     * @return true: Permitirá que se ejecute la función "clickCancelar()".
     * <BR>false: Evitará que se ejecute la función "clickCancelar()".
     * <BR>Nota.- La función devolverá "true" de forma predeterminada si Ud. no implementa
     * el código para ésta función.
     */
    public boolean beforeClickCancelar()
    {
        return true;
    }

    /**
     * Esta función obtiene el botón sobre el cual se dió click.
     * @return El botón sobre el cual se dió click.
     * Puede devolver uno de los siguientes valores:
     * <UL>
     * <LI>0: Botón Inicio (INT_BUT_INI)
     * <LI>1: Botón Anterior (INT_BUT_ANT)
     * <LI>2: Botón Siguiente (INT_BUT_SIG)
     * <LI>3: Botón Fin (INT_BUT_FIN)
     * <LI>4: Botón Insertar (INT_BUT_INS)
     * <LI>5: Botón Consultar (INT_BUT_CON)
     * <LI>6: Botón Modificar (INT_BUT_MOD)
     * <LI>7: Botón Eliminar (INT_BUT_ELI)
     * <LI>8: Botón Anular (INT_BUT_ANU)
     * <LI>9: Botón Imprimir (INT_BUT_IMP)
     * <LI>10: Botón Vista preliminar (INT_BUT_VIS)
     * <LI>11: Botón Aceptar (INT_BUT_ACE)
     * <LI>12: Botón Cancelar (INT_BUT_CAN)
     * </UL>
     */
    public int getBotonClick()
    {
        return intButCli;
    }

    /**
     * Esta función determina si el usuario tiene acceso a la opción correpondiente al código de menú especificado.
     * @param codigoMenu El código del menú del que se desea conocer si tiene acceso o no.
     * @return true: Si el usuario tiene acceso a la opción correpondiente al código de menú especificado.
     * <BR>false: En el caso contrario.
     */
    public boolean isOpcionEnabled(int codigoMenu)
    {
        return objPerUsr.isOpcionEnabled(codigoMenu);
    }

    /**
     * Esta función permite agregar un separador en el ZafToolBar.
     * @return true: Si se pudo agregar el separador.
     * <BR>false: En el caso contrario.
     */
    public boolean agregarSeparador()
    {
        boolean blnRes=true;
        try
        {
            javax.swing.JSeparator sepGen=new javax.swing.JSeparator();
            sepGen.setOrientation(javax.swing.SwingConstants.VERTICAL);
            sepGen.setMaximumSize(new java.awt.Dimension(2, 32));
            tbrHer.add(sepGen);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite agregar un botón en el ZafToolBar.
     * @param boton El botón a agregar.
     * @return true: Si se pudo agregar el botón.
     * <BR>false: En el caso contrario.
     */
    public boolean agregarBoton(javax.swing.JButton boton)
    {
        boolean blnRes=true;
        try
        {
            tbrHer.add(boton);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

}
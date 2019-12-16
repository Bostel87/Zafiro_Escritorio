package Librerias.ZafTreeTable;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.table.*;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.Graphics;


/**
 * Es un tabla que contiene un arbol en una columna
 * @see <strong>Como usar zafJTreeTable ?</strong><pre>
 * Se puede utilizar el modelo zafTreeTableModel o en su defecto crear un modelo propio
 * 	zafJTreeTable treeTable = new zafJTreeTable(new ZafTreeTableModel(ColumnNames, VecTbl), "d:\\icono.gif");
 * 	zafJTreeTable treeTable = new zafJTreeTable(mimodelo(), "d:\\icono.gif");
 * </pre>
 */
public class ZafJTreeTable extends JTable {
    protected TreeTableCellRenderer tree;
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
    /**
     * Construye un zafJTreeTable y recibe un modelo treetable con el String de la ruta de la imagen a mostrar en las hojas del tree
     * @param treeTableModel Un modelo del tipo TreeTableModel, se puede usar el zafTreeTableModel
     * @param IconoPath un String con la ruta donde esta el archivo de imagen a utilizar para presentar en las hojas del arbol
     */    
    public ZafJTreeTable(TreeTableModel treeTableModel, String IconoPath) {
	super();

	// Create the tree. It will be used as a renderer and editor. 
	tree = new TreeTableCellRenderer(treeTableModel); 

        //javax.swing.tree.DefaultTreeCellRenderer renderer = new javax.swing.tree.DefaultTreeCellRenderer();
        Librerias.ZafTreeTable.ZafTreeCellRenderer renderer = new Librerias.ZafTreeTable.ZafTreeCellRenderer();
        renderer.setLeafIcon(new javax.swing.ImageIcon(IconoPath));
        tree.setCellRenderer(renderer);

	// Install a tableModel representing the visible rows in the tree. 
	super.setModel(new TreeTableModelAdapter(treeTableModel, tree));

	// Force the JTable and JTree to share their row selection models. 
	tree.setSelectionModel(new DefaultTreeSelectionModel() { 
	    // Extend the implementation of the constructor, as if: 
	 /* public this() */ {
		setSelectionModel(listSelectionModel); 
	    } 
	}); 
        
	// Make the tree and table row heights the same. 
	tree.setRowHeight(getRowHeight());

	// Install the tree editor renderer and editor. 
	setDefaultRenderer(TreeTableModel.class, tree); 
	setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());  

	setShowGrid(false);
	setIntercellSpacing(new Dimension(0, 0)); 	        
    }
/**
 * Se agrego parametro para ver arbol de desplegado 
 * true: muestra el Tree abierto
 * false: muestra en el 1er nivel del arbol
 * @autor: jsalazar
 */
    public ZafJTreeTable(TreeTableModel treeTableModel, String IconoPath, boolean blnMostrar) {
	super();

	// Create the tree. It will be used as a renderer and editor. 
	tree = new TreeTableCellRenderer(treeTableModel); 

        //javax.swing.tree.DefaultTreeCellRenderer renderer = new javax.swing.tree.DefaultTreeCellRenderer();
        Librerias.ZafTreeTable.ZafTreeCellRenderer renderer = new Librerias.ZafTreeTable.ZafTreeCellRenderer();
        renderer.setLeafIcon(new javax.swing.ImageIcon(IconoPath));
        tree.setCellRenderer(renderer);

	// Install a tableModel representing the visible rows in the tree. 
	super.setModel(new TreeTableModelAdapter(treeTableModel, tree,blnMostrar));

	// Force the JTable and JTree to share their row selection models. 
	tree.setSelectionModel(new DefaultTreeSelectionModel() { 
	    // Extend the implementation of the constructor, as if: 
	 /* public this() */ {
		setSelectionModel(listSelectionModel); 
	    } 
	}); 
        
	// Make the tree and table row heights the same. 
	tree.setRowHeight(getRowHeight());

	// Install the tree editor renderer and editor. 
	setDefaultRenderer(TreeTableModel.class, tree); 
	setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());  

	setShowGrid(false);
	setIntercellSpacing(new Dimension(0, 0)); 	        
    }

    /**
     * Construye un zafJTreeTable y recibe un modelo treetable
     * @param treeTableModel Un modelo del tipo TreeTableModel, se puede usar el zafTreeTableModel
     */    
    public ZafJTreeTable(TreeTableModel treeTableModel) {
	super();

	// Create the tree. It will be used as a renderer and editor. 
	tree = new TreeTableCellRenderer(treeTableModel); 

	// Install a tableModel representing the visible rows in the tree. 
	super.setModel(new TreeTableModelAdapter(treeTableModel, tree));

	// Force the JTable and JTree to share their row selection models. 
	tree.setSelectionModel(new DefaultTreeSelectionModel() { 
	    // Extend the implementation of the constructor, as if: 
	 /* public this() */ {
		setSelectionModel(listSelectionModel); 
	    } 
	}); 
        
	// Make the tree and table row heights the same. 
	tree.setRowHeight(getRowHeight());

	// Install the tree editor renderer and editor. 
	setDefaultRenderer(TreeTableModel.class, tree); 
	setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());  

	setShowGrid(false);
	setIntercellSpacing(new Dimension(0, 0)); 	        
    }
    
    

    /* Workaround for BasicTableUI anomaly. Make sure the UI never tries to 
     * paint the editor. The UI currently uses different techniques to 
     * paint the renderers and editors and overriding setBounds() below 
     * is not the right thing to do for an editor. Returning -1 for the 
     * editing row in this case, ensures the editor is never painted. 
     */
    public int getEditingRow() {
        return (getColumnClass(editingColumn) == TreeTableModel.class) ? -1 : editingRow;  
    }

    // 
    // The renderer used to display the tree nodes, a JTree.  
    //

    public class TreeTableCellRenderer extends JTree implements TableCellRenderer {

	protected int visibleRow;
   
	public TreeTableCellRenderer(TreeModel model) { 
	    super(model); 
	}

	public void setBounds(int x, int y, int w, int h) {
	    super.setBounds(x, 0, w, ZafJTreeTable.this.getHeight());
	}

	public void paint(Graphics g) {
	    g.translate(0, -visibleRow * getRowHeight());
	    super.paint(g);
	}

	public Component getTableCellRendererComponent(JTable table,
						       Object value,
						       boolean isSelected,
						       boolean hasFocus,
						       int row, int column) { 
	    if(isSelected)
		this.setBackground(table.getSelectionBackground());
	    else
		this.setBackground(table.getBackground());
       
	    visibleRow = row;
	    return this;
	}
    }

  
    // 
    // The editor used to interact with tree nodes, a JTree.  
    //

    public class TreeTableCellEditor extends AbstractCellEditor implements TableCellEditor {
	public Component getTableCellEditorComponent(JTable table, Object value,
						     boolean isSelected, int r, int c) {
	    return tree;
	}
    }

}


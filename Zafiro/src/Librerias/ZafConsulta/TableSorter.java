/*
 * TableSorter.java
 *
 * Created on 11 de agosto de 2005, 9:35
 */

package Librerias.ZafConsulta;

/**
 *
 * @author  Sun MycroSystems
 * Modificado por: IdiTrix
 */
class TableSorter extends javax.swing.table.AbstractTableModel {
    
    protected javax.swing.table.TableModel tableModel;
    
    public static final int DESCENDING = -1;
    public static final int NOT_SORTED = 0;
    public static final int ASCENDING = 1;
    
    private static Directive EMPTY_DIRECTIVE = new Directive(-1, NOT_SORTED);
    
    public static final java.util.Comparator COMPARABLE_COMAPRATOR = new java.util.Comparator() {
        public int compare(Object o1, Object o2) {
            return ((Comparable) o1).compareTo(o2);
        }
    };
    public static final java.util.Comparator LEXICAL_COMPARATOR = new java.util.Comparator() {
        public int compare(Object o1, Object o2) {
            return o1.toString().compareTo(o2.toString());
        }
    };
    
    private Row[] viewToModel;
    private int[] modelToView;
    private javax.swing.table.JTableHeader tableHeader;
    private java.awt.event.MouseListener mouseListener;
    private javax.swing.event.TableModelListener tableModelListener;
    private java.util.Map columnComparators = new java.util.HashMap();
    private java.util.List sortingColumns = new java.util.ArrayList();
    
    public TableSorter() {
        this.mouseListener = new MouseHandler();
        this.tableModelListener = new TableModelHandler();
    }
    
    public TableSorter(javax.swing.table.TableModel tableModel) {
        this();
        setTableModel(tableModel);
    }
    
    public TableSorter(javax.swing.table.TableModel tableModel, javax.swing.table.JTableHeader tableHeader) {
        this();
        setTableHeader(tableHeader);
        setTableModel(tableModel);
    }
    
    private void clearSortingState() {
        viewToModel = null;
        modelToView = null;
    }
    
    public javax.swing.table.TableModel getTableModel() {
        return tableModel;
    }
    
    public void setTableModel(javax.swing.table.TableModel tableModel) {
        if (this.tableModel != null) {
            this.tableModel.removeTableModelListener(tableModelListener);
        }
        
        this.tableModel = tableModel;
        if (this.tableModel != null) {
            this.tableModel.addTableModelListener(tableModelListener);
        }
        
        clearSortingState();
        fireTableStructureChanged();
    }
    
    public javax.swing.table.JTableHeader getTableHeader() {
        return tableHeader;
    }
    
    public void setTableHeader(javax.swing.table.JTableHeader tableHeader) {
        if (this.tableHeader != null) {
            this.tableHeader.removeMouseListener(mouseListener);
            javax.swing.table.TableCellRenderer defaultRenderer = this.tableHeader.getDefaultRenderer();
            if (defaultRenderer instanceof SortableHeaderRenderer) {
                this.tableHeader.setDefaultRenderer(((SortableHeaderRenderer) defaultRenderer).tableCellRenderer);
            }
        }
        this.tableHeader = tableHeader;
        if (this.tableHeader != null) {
            this.tableHeader.addMouseListener(mouseListener);
            this.tableHeader.setDefaultRenderer(
            new SortableHeaderRenderer(this.tableHeader.getDefaultRenderer()));
        }
    }
    
    public boolean isSorting() {
        return sortingColumns.size() != 0;
    }
    
    private Directive getDirective(int column) {
        for (int i = 0; i < sortingColumns.size(); i++) {
            Directive directive = (Directive)sortingColumns.get(i);
            if (directive.column == column) {
                return directive;
            }
        }
        return EMPTY_DIRECTIVE;
    }
    
    public int getSortingStatus(int column) {
        return getDirective(column).direction;
    }
    
    private void sortingStatusChanged() {
        clearSortingState();
        fireTableDataChanged();
        if (tableHeader != null) {
            tableHeader.repaint();
        }
    }
    
    public void setSortingStatus(int column, int status) {
        Directive directive = getDirective(column);
        if (directive != EMPTY_DIRECTIVE) {
            sortingColumns.remove(directive);
        }
        if (status != NOT_SORTED) {
            sortingColumns.add(new Directive(column, status));
        }
        sortingStatusChanged();
    }
    
    protected javax.swing.Icon getHeaderRendererIcon(int column, int size) {
        Directive directive = getDirective(column);
        if (directive == EMPTY_DIRECTIVE) {
            return null;
        }
        return new Arrow(directive.direction == DESCENDING, size, sortingColumns.indexOf(directive));
    }
    
    private void cancelSorting() {
        sortingColumns.clear();
        sortingStatusChanged();
    }
    
    public void setColumnComparator(Class type, java.util.Comparator comparator) {
        if (comparator == null) {
            columnComparators.remove(type);
        } else {
            columnComparators.put(type, comparator);
        }
    }
    
    protected java.util.Comparator getComparator(int column) {
        Class columnType = tableModel.getColumnClass(column);
        java.util.Comparator comparator = (java.util.Comparator) columnComparators.get(columnType);
        if (comparator != null) {
            return comparator;
        }
        if (Comparable.class.isAssignableFrom(columnType)) {
            return COMPARABLE_COMAPRATOR;
        }
        return LEXICAL_COMPARATOR;
    }
    
    private Row[] getViewToModel() {
        if (viewToModel == null) {
            int tableModelRowCount = tableModel.getRowCount();
            viewToModel = new Row[tableModelRowCount];
            for (int row = 0; row < tableModelRowCount; row++) {
                viewToModel[row] = new Row(row);
            }
            
            if (isSorting()) {
                java.util.Arrays.sort(viewToModel);
            }
        }
        return viewToModel;
    }
    
    public int modelIndex(int viewIndex) {
        return getViewToModel()[viewIndex].modelIndex;
    }
    
    private int[] getModelToView() {
        if (modelToView == null) {
            int n = getViewToModel().length;
            modelToView = new int[n];
            for (int i = 0; i < n; i++) {
                modelToView[modelIndex(i)] = i;
            }
        }
        return modelToView;
    }
    
    // TableModel interface methods
    
    public int getRowCount() {
        return (tableModel == null) ? 0 : tableModel.getRowCount();
    }
    
    public int getColumnCount() {
        return (tableModel == null) ? 0 : tableModel.getColumnCount();
    }
    
    public String getColumnName(int column) {
        return tableModel.getColumnName(column);
    }
    
    public Class getColumnClass(int column) {
        return tableModel.getColumnClass(column);
    }
    
    public boolean isCellEditable(int row, int column) {
        return tableModel.isCellEditable(modelIndex(row), column);
    }
    
    public Object getValueAt(int row, int column) {
        return tableModel.getValueAt(modelIndex(row), column);
    }
    
    public void setValueAt(Object aValue, int row, int column) {
        tableModel.setValueAt(aValue, modelIndex(row), column);
    }
    
    // Helper classes
    
    private class Row implements Comparable {
        private int modelIndex;
        
        public Row(int index) {
            this.modelIndex = index;
        }
        
        public int compareTo(Object o) {
            int row1 = modelIndex;
            int row2 = ((Row) o).modelIndex;
       
            for (java.util.Iterator it = sortingColumns.iterator(); it.hasNext();) {
                Directive directive = (Directive) it.next();
                int column = directive.column;
                Object o1 = tableModel.getValueAt(row1, column);
                Object o2 = tableModel.getValueAt(row2, column);
                
                int comparison = 0;
                // Define null less than everything, except null.
                if (o1 == null && o2 == null) {
                    comparison = 0;
                } else if (o1 == null) {
                    comparison = -1;
                } else if (o2 == null) {
                    comparison = 1;
                } else {
                    comparison = getComparator(column).compare(o1, o2);
                }
                if (comparison != 0) {
                    return directive.direction == DESCENDING ? -comparison : comparison;
                }
            }
            return 0;
        }
    }
    
    private class TableModelHandler implements javax.swing.event.TableModelListener{
        public void tableChanged(javax.swing.event.TableModelEvent e) {
            
            if (!isSorting()) {
                clearSortingState();
                fireTableChanged(e);
                return;
            }
            
            if (e.getFirstRow() == javax.swing.event.TableModelEvent.HEADER_ROW) {
                cancelSorting();
                fireTableChanged(e);
                return;
            }
            
            int column = e.getColumn();
            if (e.getFirstRow() == e.getLastRow()
            && column != javax.swing.event.TableModelEvent.ALL_COLUMNS
            && getSortingStatus(column) == NOT_SORTED
            && modelToView != null) {
                int viewIndex = getModelToView()[e.getFirstRow()];
                fireTableChanged(new javax.swing.event.TableModelEvent(TableSorter.this,
                viewIndex, viewIndex,
                column, e.getType()));
                return;
            }
            
            // Something has happened to the data that may have invalidated the row order.
            clearSortingState();
            fireTableDataChanged();
            return;
        }
    }
    
    private class MouseHandler extends java.awt.event.MouseAdapter {
        public void mouseClicked(java.awt.event.MouseEvent e) {
            javax.swing.table.JTableHeader h = (javax.swing.table.JTableHeader) e.getSource();
            javax.swing.table.TableColumnModel columnModel = h.getColumnModel();
            int viewColumn = columnModel.getColumnIndexAtX(e.getX());
            int column = columnModel.getColumn(viewColumn).getModelIndex();
            if (column != -1) {
                int status = getSortingStatus(column);
                if (!e.isControlDown()) {
                    cancelSorting();
                }
                // Cycle the sorting states through {NOT_SORTED, ASCENDING, DESCENDING} or
                // {NOT_SORTED, DESCENDING, ASCENDING} depending on whether shift is pressed.
                status = status + (e.isShiftDown() ? -1 : 1);
                status = (status + 4) % 3 - 1; // signed mod, returning {-1, 0, 1}
                setSortingStatus(column, status);
            }
        }
    }
    
    private static class Arrow implements javax.swing.Icon {
        private boolean descending;
        private int size;
        private int priority;
        
        public Arrow(boolean descending, int size, int priority) {
            this.descending = descending;
            this.size = size;
            this.priority = priority;
        }
        
        public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y) {
            java.awt.Color color = c == null ? java.awt.Color.GRAY : c.getBackground();
            // In a compound sort, make each succesive triangle 20%
            // smaller than the previous one.
            int dx = (int)(size/2*Math.pow(0.8, priority));
            int dy = descending ? dx : -dx;
            // Align icon (roughly) with font baseline.
            y = y + 5*size/6 + (descending ? -dy : 0);
            int shift = descending ? 1 : -1;
            g.translate(x, y);
            
            // Right diagonal.
            g.setColor(color.darker());
            g.drawLine(dx / 2, dy, 0, 0);
            g.drawLine(dx / 2, dy + shift, 0, shift);
            
            // Left diagonal.
            g.setColor(color.brighter());
            g.drawLine(dx / 2, dy, dx, 0);
            g.drawLine(dx / 2, dy + shift, dx, shift);
            
            // Horizontal line.
            if (descending) {
                g.setColor(color.darker().darker());
            } else {
                g.setColor(color.brighter().brighter());
            }
            g.drawLine(dx, 0, 0, 0);
            
            g.setColor(color);
            g.translate(-x, -y);
        }
        
        public int getIconWidth() {
            return size;
        }
        
        public int getIconHeight() {
            return size;
        }
    }
    
    private class SortableHeaderRenderer implements javax.swing.table.TableCellRenderer {
        private javax.swing.table.TableCellRenderer tableCellRenderer;
        
        public SortableHeaderRenderer(javax.swing.table.TableCellRenderer tableCellRenderer) {
            this.tableCellRenderer = tableCellRenderer;
        }
        
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table,
        Object value,
        boolean isSelected,
        boolean hasFocus,
        int row,
        int column) {
            java.awt.Component c = tableCellRenderer.getTableCellRendererComponent(table,
            value, isSelected, hasFocus, row, column);
            if (c instanceof javax.swing.JLabel) {
                javax.swing.JLabel l = (javax.swing.JLabel) c;
                l.setHorizontalTextPosition(javax.swing.JLabel.LEFT);
                int modelColumn = table.convertColumnIndexToModel(column);
                l.setIcon(getHeaderRendererIcon(modelColumn, l.getFont().getSize()));
            }
            return c;
        }
    }
    
    private static class Directive {
        private int column;
        private int direction;
        
        public Directive(int column, int direction) {
            this.column = column;
            this.direction = direction;
        }
    }
}

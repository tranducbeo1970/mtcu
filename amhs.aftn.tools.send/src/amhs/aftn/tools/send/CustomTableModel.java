/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.aftn.tools.send;

import java.util.ArrayList;
import javax.swing.DefaultRowSorter;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter.SortKey;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author andh
 */
public abstract class CustomTableModel extends DefaultTableModel {
    
    protected JTable table;
    protected DefaultRowSorter sorter;

    public CustomTableModel() {
    }
    
    public CustomTableModel(JTable table) {
        this.table = table;
        this.table.setModel(this);
        this.initialize();
        sorter = ((DefaultRowSorter)table.getRowSorter()); 
    }
    
    protected abstract void initialize();
    
    protected void setColumnWidth(int colIndex, int minWidth, int maxWidth, int prefer) {
        table.getColumnModel().getColumn(colIndex).setMinWidth(minWidth);
	table.getColumnModel().getColumn(colIndex).setMaxWidth(maxWidth);
	table.getColumnModel().getColumn(colIndex).setPreferredWidth(prefer);
    }
    
    public void clear() {
        final int count = this.getRowCount() - 1;
        for (int i = count; i >= 0; i--) {
            this.removeRow(i);
        }
    }
    
    public void setSortKey(SortKey sortkey) {
        final ArrayList list = new ArrayList();
        list.add(sortkey);
        sorter.setSortKeys(list);
        sorter.sort();
    }
    
    public void setFilter(String filter) {
        if (sorter == null)  {
            return;
        }
        sorter.setRowFilter(RowFilter.regexFilter(filter));
    }
}

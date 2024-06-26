/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.tool.gui;

import java.awt.Font;
import java.util.List;
import javax.swing.DefaultRowSorter;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ANDH
 * @param <T>
 */
public abstract class BaseModel<T> extends DefaultTableModel {

    protected JTable table;
    protected DefaultRowSorter sorter;
    protected boolean clearingData = false;
    private int checkpoint;

    public BaseModel() {
        super();
        checkpoint = 0;
    }

    public BaseModel(JTable table) {
        super();
        this.table = table;
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.setModel(this);
//        this.initialize();
        this.table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 12));

    }
    
    public abstract String getKey();

    public abstract void add(T t);

    public abstract void update(T t);

    public abstract List<T> get();

    public abstract boolean read(int row);

    public abstract Integer getID(int selectedRow);

    public void add(List<T> t) {
        if (t == null || t.isEmpty()) {
            return;
        }

        for (T item : t) {
            add(item);
        }
    }

    protected abstract void initialize();

    protected void updateCheckpoint(int id) {
        if (id <= this.getCheckpoint()) {
            return;
        }
        this.setCheckpoint(id);
    }

    public void hideHeader() {
        this.table.setTableHeader(null);

        JViewport jviewPort = (JViewport) this.table.getParent();
        if (jviewPort == null) {
            return;
        }

        JScrollPane jscrollPane = (JScrollPane) jviewPort.getParent();
        if (jscrollPane == null) {
            return;
        }
        jscrollPane.setColumnHeader(null);
        jscrollPane.setColumnHeaderView(null);
        jscrollPane.getColumnHeader().setVisible(false);
    }

    public void setFont(Font font) {
        this.table.setFont(font);
    }

    public void setRowHeight(int height) {
        this.table.setRowHeight(height);
    }

    public synchronized void clear() {
        try {
            clearingData = true;

//	    this.sorter.allRowsChanged();
            final int count = this.getRowCount() - 1;
            for (int i = count; i >= 0; i--) {
                this.removeRow(i);
            }
//            this.checkpoint = 0;

        } finally {
            this.checkpoint = 0;
            clearingData = false;
        }
    }

    public void delete(int id) {
        int rowCount = this.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            int seq = this.getID(i);

            if (id != seq) {
                continue;
            }

            this.removeRow(i);
            break;
        }
    }

    /*
    public void setSortKey(SortKey sortkey) {
        table.setAutoCreateRowSorter(true);
        List<SortKey> list = new ArrayList<>();
        list.add(sortkey);
        sorter = ((DefaultRowSorter) table.getRowSorter());
        if (sorter == null) {
            return;
        }
        sorter.setSortKeys(list);
        sorter.sort();
    }
     */

 /*
    public void setSortKey(List<SortKey> sortkey) {
        table.setAutoCreateRowSorter(true);
        sorter = ((DefaultRowSorter) table.getRowSorter());
        if (sorter == null) {
            return;
        }
        sorter.setSortKeys(sortkey);
        sorter.sort();
    }
     */

 /*
    public void setSort(int column) {
        table.setAutoCreateRowSorter(true);
        TableRowSorter<TableModel> tableSortkey = new TableRowSorter<>(table.getModel());
        table.setRowSorter(tableSortkey);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        // sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(column, SortOrder.ASCENDING));
        tableSortkey.setSortKeys(sortKeys);
    }
     */
    @SuppressWarnings("unchecked")
    public void setFilter(String filter) {
        sorter = ((DefaultRowSorter) table.getRowSorter());
        if (sorter == null) {
            return;
        }
        sorter.setRowFilter(RowFilter.regexFilter(filter));
        sorter.setSortsOnUpdates(true);
    }

    @SuppressWarnings("unchecked")
    public void setFilter(RowFilter rowFilter) {
        sorter = ((DefaultRowSorter) table.getRowSorter());
        if (sorter == null) {
            return;
        }
        sorter.setRowFilter(rowFilter);
        sorter.setSortsOnUpdates(true);
    }

    public int getDisplayedRow() {
        return this.sorter.getViewRowCount();
    }

    public void refresh() {
        this.sorter.sort();
    }

    public Boolean isClearingData() {
        return this.clearingData;
    }

    protected void setColumnWidth(int colIndex, int minWidth, int maxWidth, int prefer) {
        table.getColumnModel().getColumn(colIndex).setMinWidth(minWidth);
        table.getColumnModel().getColumn(colIndex).setMaxWidth(maxWidth);
        table.getColumnModel().getColumn(colIndex).setPreferredWidth(prefer);
    }

    protected void setColumnWidth(int colIndex, int minWidth, int maxWidth) {
        table.getColumnModel().getColumn(colIndex).setMinWidth(minWidth);
        table.getColumnModel().getColumn(colIndex).setMaxWidth(maxWidth);
    }

    /**
     * @return the checkpoint
     */
    public int getCheckpoint() {
        return checkpoint;
    }

    /**
     * @param checkpoint the checkpoint to set
     */
    public void setCheckpoint(int checkpoint) {
        if (this.checkpoint >= checkpoint) {
            return;
        }
        this.checkpoint = checkpoint;
    }

    public void removeRow(int[] selectedRows) {
        for (int i = 0; i < selectedRows.length; i++) {
            this.removeRow(selectedRows[i] - i);
        }
    }
}

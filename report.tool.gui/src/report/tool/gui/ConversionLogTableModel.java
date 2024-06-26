/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.tool.gui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JTable;
import report.tool.gui.database.ent.MessageConversionLog;

/**
 *
 * @author ANDH
 */
public class ConversionLogTableModel extends BaseModel<MessageConversionLog> {

    private final DateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public ConversionLogTableModel(JTable table) {
        super(table);
    }

    @Override
    public boolean read(int row) {
        return true;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    protected void initialize() {
        this.addColumn("ID");
        this.addColumn("FROM");
        this.addColumn("SUBJECT");
        this.addColumn("CONTENT");
        this.addColumn("CONVERTED_TIME");

        setColumnWidth(0, 0, 0, 0);
        setColumnWidth(1, 120, 320, 120);
        setColumnWidth(2, 120, 320, 120);
        setColumnWidth(4, 100, 150, 120);
    }

    @Override
    public Class getColumnClass(int column) {
//        switch (column) {
//            case 2:
//            case 3:
//                return ImageIcon.class;
//            case 9:
//                return Integer.class;
//            default:
//                return String.class;
//        }
        return Object.class;
    }

    @Override
    public void add(MessageConversionLog t) {
        int rowCount = this.getRowCount();
        boolean added = false;
        for (int i = 0; i < rowCount; i++) {
            int id = ((Long) this.getValueAt(i, 0)).intValue();
            if (t.getId() < id) {
                continue;
            }

            if (t.getId() == id) {
                added = true;
                break;
            }

            if (t.getId() > id) {

                this.insertRow(i, createRow(t));
                added = true;
                break;
            }
        }
        if (!added) {
            this.addRow(createRow(t));
        }

        this.setCheckpoint((int) t.getId());
    }

    @Override
    public void update(MessageConversionLog t) {
    }

    private Object[] createRow(MessageConversionLog t) {
        return new Object[]{
            t.getId(),
            t.getOrigin(),
            t.getSubject(),
            t.getContent(),
            formater.format(t.getConvertedTime())
        };
    }

    @Override
    public List<MessageConversionLog> get() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Integer getID(int selectedRow) {
        return ((Long) this.getValueAt(selectedRow, 0)).intValue();
    }

    @Override
    public String getKey() {
        return "INBOX";
    }
}

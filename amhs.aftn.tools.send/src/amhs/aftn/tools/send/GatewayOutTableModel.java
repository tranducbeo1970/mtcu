/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.aftn.tools.send;

import com.attech.amhs.mtcu.database.entity.GatewayOut;
import javax.swing.JTable;

/**
 *
 * @author ANDH
 */
public class GatewayOutTableModel extends CustomTableModel {

    private long currentMaxId;

    public GatewayOutTableModel(JTable table) {
	super(table);
	currentMaxId = 0;
    }

    public void addRow(GatewayOut gatewayOut) {
	if (gatewayOut.getMsgid() <= getCurrentMaxId()) {
	    return;
	}
	this.currentMaxId = gatewayOut.getMsgid();

	this.addRow(new Object[]{
	    getPriorityStr(gatewayOut.getPriority()),
	    gatewayOut.getFilingTime(),
	    gatewayOut.getOriginator(),
	    gatewayOut.getAddress(),
	    gatewayOut.getText(),
	    gatewayOut.getOptionalHeading(),
	    gatewayOut.getInsertedTime()
	});
    }

    @Override
    protected void initialize() {
	this.addColumn("Pri");
	this.addColumn("Filing Time");
	this.addColumn("Origin");
	this.addColumn("Address");
	this.addColumn("Text");
	this.addColumn("OHI");
	this.addColumn("Time");
	setColumnWidth(0, 30, 30, 30);
	setColumnWidth(1, 50, 50, 50);
	setColumnWidth(2, 90, 90, 90);
	setColumnWidth(3, 200, 300, 250);
	setColumnWidth(5, 120, 200, 150);
	setColumnWidth(6, 120, 200, 150);
    }

    private String getPriorityStr(int value) {
	switch (value) {
	    case 0:
		return "SS";
	    case 1:
		return "DD";
	    case 2:
		return "FF";
	    case 3:
		return "GG";
	    default:
		return "KK";
	}
    }
    
    /**
     * @return the currentMaxId
     */
    public long getCurrentMaxId() {
	return currentMaxId;
    }

}

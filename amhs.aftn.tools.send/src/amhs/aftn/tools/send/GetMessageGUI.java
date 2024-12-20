/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.aftn.tools.send;

import com.attech.amhs.mtcu.database.dao.GatewayOutDao;
import com.attech.amhs.mtcu.database.entity.GatewayOut;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author hong
 */
public class GetMessageGUI extends javax.swing.JFrame {

    private GatewayOutDao dao;
    private GatewayOutTableModel model;

    /**
     * Creates new form GetMessageGUI
     */
    public GetMessageGUI() {
	initComponents();
	this.dao = new GatewayOutDao();
	this.model = new GatewayOutTableModel(tblMessage);

	this.tblMessage.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	    @Override
	    public void valueChanged(ListSelectionEvent e) {
		int selectedRow = tblMessage.getSelectedRow();
		String priority = (String) tblMessage.getValueAt(selectedRow, 0);
		String filingTime = (String) tblMessage.getValueAt(selectedRow, 1);
		String origin = (String) tblMessage.getValueAt(selectedRow, 2);
		String address = (String) tblMessage.getValueAt(selectedRow, 3);
		String text = (String) tblMessage.getValueAt(selectedRow, 4);
		String opionalHeadingInfo = (String) tblMessage.getValueAt(selectedRow, 5);

		String[] addresses = address.trim().split(" ");
		List<String> lines = reconstructAddressLine(addresses);
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("ZCZC ABC123 %s\r\n", opionalHeadingInfo));
		builder.append(String.format("%s %s\r\n", priority, lines.get(0)));
		for (int i = 1; i < lines.size(); i++) {
		    builder.append(String.format("%s\r\n", lines.get(i)));
		}
		builder.append(String.format("%s %s\r\n", filingTime, origin));
		builder.append(String.format("%s\r\n", text));
		builder.append("\r\n");
		builder.append("\r\n");
		builder.append("\r\n");
		builder.append("\r\n");
		builder.append("NNNN");
		txtMessage.setText(builder.toString());

	    }
	});
    }

    private List<String> reconstructAddressLine(String[] addreses) {
	List<String> lines = new ArrayList<>();
	StringBuilder builder = new StringBuilder();
	int index = 1;
	for (String str : addreses) {
	    if (index < 7) {
		index++;
		builder.append(str).append(" ");
		continue;
	    }

	    builder.append(str);
	    lines.add(builder.toString());
	    builder = new StringBuilder();
	    index = 1;
	}

	if (!builder.toString().isEmpty()) {
	    lines.add(builder.toString().trim());
	}

	return lines;

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnRefresh = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnSendGUI = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMessage = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMessage = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        btnRefresh.setText("Refresh");
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRefresh);

        btnClear.setText("Clear");
        btnClear.setFocusable(false);
        btnClear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClear.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jToolBar1.add(btnClear);

        btnSendGUI.setText("Send GUI");
        btnSendGUI.setFocusable(false);
        btnSendGUI.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSendGUI.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSendGUI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendGUIActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSendGUI);

        jSplitPane1.setDividerLocation(400);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(1.0);

        tblMessage.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tblMessage.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblMessage);

        jSplitPane1.setTopComponent(jScrollPane1);

        txtMessage.setColumns(20);
        txtMessage.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        txtMessage.setRows(5);
        jScrollPane2.setViewportView(txtMessage);

        jSplitPane1.setRightComponent(jScrollPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 923, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
	try {

	    List<GatewayOut> gatewayouts = dao.getGatewayOut(this.model.getCurrentMaxId());
	    for (GatewayOut gatewayout : gatewayouts) {
		this.model.addRow(gatewayout);
	    }

	} catch (SQLException ex) {
	    Logger.getLogger(GetMessageGUI.class.getName()).log(Level.SEVERE, null, ex);
	}

    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
	try {

	    this.dao.clearGatewayOut();
	    this.model.clear();

	} catch (SQLException ex) {
	    Logger.getLogger(GetMessageGUI.class.getName()).log(Level.SEVERE, null, ex);
	}
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnSendGUIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendGUIActionPerformed
	GUI gui = new GUI();
	gui.setVisible(true);
    }//GEN-LAST:event_btnSendGUIActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSendGUI;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tblMessage;
    private javax.swing.JTextArea txtMessage;
    // End of variables declaration//GEN-END:variables
}

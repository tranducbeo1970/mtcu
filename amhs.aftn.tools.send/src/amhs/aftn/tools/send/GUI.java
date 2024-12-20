/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.aftn.tools.send;


import com.attech.amhs.mtcu.database.Connection;
import com.attech.amhs.mtcu.database.dao.GatewayInDao;
import com.attech.amhs.mtcu.database.entity.GatewayIn;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author andh
 */
public class GUI extends javax.swing.JFrame {

    private final SimpleDateFormat format = new SimpleDateFormat("ddHHmm");
    private final DecimalFormat dformat = new DecimalFormat("0000");
    
    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        this.txtFilingTime.setText(format.format(new Date()));
        this.txtOrigin.setText("VVTSAFTN");
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtChannel = new javax.swing.JTextField();
        txtSeq = new javax.swing.JTextField();
        txtPriority = new javax.swing.JTextField();
        txtAddress1 = new javax.swing.JTextField();
        txtAddress2 = new javax.swing.JTextField();
        txtAddress3 = new javax.swing.JTextField();
        txtAddress6 = new javax.swing.JTextField();
        txtAddress5 = new javax.swing.JTextField();
        txtAddress4 = new javax.swing.JTextField();
        txtAddress7 = new javax.swing.JTextField();
        txtAddress8 = new javax.swing.JTextField();
        txtAddress9 = new javax.swing.JTextField();
        txtAddress10 = new javax.swing.JTextField();
        txtAddress11 = new javax.swing.JTextField();
        txtAddress12 = new javax.swing.JTextField();
        txtAddress13 = new javax.swing.JTextField();
        txtAddress14 = new javax.swing.JTextField();
        txtAddress15 = new javax.swing.JTextField();
        txtAddress16 = new javax.swing.JTextField();
        txtAddress17 = new javax.swing.JTextField();
        txtAddress18 = new javax.swing.JTextField();
        txtAddress19 = new javax.swing.JTextField();
        txtAddress20 = new javax.swing.JTextField();
        txtAddress21 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtContent = new javax.swing.JTextArea();
        btnSent = new javax.swing.JButton();
        txtFilingTime = new javax.swing.JTextField();
        txtOrigin = new javax.swing.JTextField();
        txtNum = new javax.swing.JTextField();
        btnFilingTime = new javax.swing.JButton();
        txtOtherInfo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Send AFTN message");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("ZCZC");

        txtChannel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtChannel.setText("ABC");
        txtChannel.setPreferredSize(new java.awt.Dimension(100, 26));

        txtSeq.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtSeq.setText("001");
        txtSeq.setPreferredSize(new java.awt.Dimension(100, 26));

        txtPriority.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtPriority.setText("GG");
        txtPriority.setMinimumSize(new java.awt.Dimension(6, 26));
        txtPriority.setPreferredSize(new java.awt.Dimension(28, 26));

        txtAddress1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress1.setText("VVTSYFYX");
        txtAddress1.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress1.setMinimumSize(new java.awt.Dimension(100, 24));
        txtAddress1.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress2.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress2.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress3.setMaximumSize(new java.awt.Dimension(100, 24));
        txtAddress3.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress6.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress6.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress5.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress5.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress4.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress4.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress7.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress7.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress8.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress8.setMinimumSize(new java.awt.Dimension(120, 24));
        txtAddress8.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress9.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress9.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress10.setMaximumSize(new java.awt.Dimension(100, 24));
        txtAddress10.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress11.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress11.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress12.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress12.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress13.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress13.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress14.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress14.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress15.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress15.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress16.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress16.setPreferredSize(new java.awt.Dimension(100, 26));
        txtAddress16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddress16ActionPerformed(evt);
            }
        });

        txtAddress17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress17.setMaximumSize(new java.awt.Dimension(100, 24));
        txtAddress17.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress18.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress18.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress19.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress19.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress20.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress20.setPreferredSize(new java.awt.Dimension(100, 26));

        txtAddress21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAddress21.setMaximumSize(new java.awt.Dimension(120, 24));
        txtAddress21.setPreferredSize(new java.awt.Dimension(100, 26));

        txtContent.setColumns(20);
        txtContent.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtContent.setRows(5);
        jScrollPane1.setViewportView(txtContent);

        btnSent.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnSent.setText("Send");
        btnSent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSentActionPerformed(evt);
            }
        });

        txtFilingTime.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtFilingTime.setMaximumSize(new java.awt.Dimension(120, 24));
        txtFilingTime.setPreferredSize(new java.awt.Dimension(100, 26));

        txtOrigin.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtOrigin.setMaximumSize(new java.awt.Dimension(120, 24));
        txtOrigin.setPreferredSize(new java.awt.Dimension(100, 26));

        txtNum.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNum.setText("1");
        txtNum.setPreferredSize(new java.awt.Dimension(100, 26));

        btnFilingTime.setText("Filing time");
        btnFilingTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilingTimeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSent, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtPriority, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnFilingTime))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 736, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtChannel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtAddress1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtAddress8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtAddress15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtFilingTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtOrigin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtAddress2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtAddress9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtAddress16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtAddress17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtAddress10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtAddress3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtAddress4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtAddress11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtAddress18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(6, 6, 6)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtAddress5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtAddress12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtAddress19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtSeq, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtOtherInfo)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtAddress6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtAddress13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtAddress20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtAddress7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtAddress14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtAddress21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtChannel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSeq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOtherInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPriority, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddress8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddress15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFilingTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOrigin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFilingTime))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSent, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAddress16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddress16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddress16ActionPerformed

    private void btnSentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSentActionPerformed
        try {
            
	    int num = Integer.parseInt(txtNum.getText());
	    for (int i = 0; i < num; i++) {
		send();
		Thread.sleep(100);
	    }
	    
            
	    
            // JOptionPane.showMessageDialog(txtContent, messageBuilder.toString(), "message sent", 0);
	    JOptionPane.showMessageDialog(txtContent, "OK", "message sent", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(txtContent, "OK", "Error: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSentActionPerformed

    private void btnFilingTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilingTimeActionPerformed
        txtFilingTime.setText(format.format(new Date()));
    }//GEN-LAST:event_btnFilingTimeActionPerformed

    private void send() throws SQLException {
	// TODO add your handling code here:
            String channel = txtChannel.getText();
            String seq = txtSeq.getText();
            String priority  =txtPriority.getText();
            String otherInformation = txtOtherInfo.getText();
            int s = Integer.parseInt(seq);
            seq = dformat.format(s);
            s++;
            txtSeq.setText(dformat.format(s));
            
            String [] addresses = getAddresses();
            StringBuilder address = new StringBuilder();
            int count = 0;
            for (String add : addresses) {
                if (add == null || add.isEmpty() || add.length() != 8) continue;
                address.append(add);
                count++;
                if (count % 7 == 0 && count != 21) address.append("\r\n");
                else address.append(" ");
            }
            
            String filingTime = format.format(new Date());
            String origin = txtOrigin.getText();
            String contents = txtContent.getText().replace("\n", "\r\n");
            
            this.txtFilingTime.setText(filingTime);
            
            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append(String.format("ZCZC %s%s %s    \r\n", channel, seq, otherInformation));
            messageBuilder.append(String.format("%s %s\r\n", priority, address.toString()));
            messageBuilder.append(String.format("%s %s\r\n", filingTime, origin));
            messageBuilder.append(String.format("%s\r\n", contents));
            messageBuilder.append("\r\n\r\n\r\n\r\nNNNN");
            System.out.println(messageBuilder.toString());
            
            GatewayIn in = new GatewayIn();
            in.setAddress(address.toString().replace("\r\n", " "));
            in.setCpa("A");
            in.setPriority(priorityMapping(priority));
            in.setSource("SC");
            in.setText(messageBuilder.toString());
            in.setTime(new Date());
            
            GatewayInDao dao = new GatewayInDao();
            dao.save(in);
    }
    
    public Integer priorityMapping(String priority) {
        if (priority == null) {
            return 4;
        }
        switch (priority) {
            case "SS":
                return 0;
            case "DD":
                return 1;
            case "FF":
                return 2;
            case "GG":
                return 3;
            case "KK":
                return 4;
            default:
                return 4;
        }
    }
     
    private String [] getAddresses() {
        String [] addresses = new String[] {
            txtAddress1.getText(), txtAddress2.getText(),txtAddress3.getText(),txtAddress4.getText(),txtAddress5.getText(),txtAddress6.getText(),txtAddress7.getText(),
            txtAddress8.getText(),txtAddress9.getText(),txtAddress10.getText(),txtAddress11.getText(),txtAddress12.getText(),txtAddress13.getText(),txtAddress14.getText(),
            txtAddress15.getText(),txtAddress16.getText(),txtAddress17.getText(),txtAddress18.getText(),txtAddress19.getText(),txtAddress20.getText(),txtAddress21.getText()
        };
        return addresses;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Connection.configure("database.xml");
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFilingTime;
    private javax.swing.JButton btnSent;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtAddress1;
    private javax.swing.JTextField txtAddress10;
    private javax.swing.JTextField txtAddress11;
    private javax.swing.JTextField txtAddress12;
    private javax.swing.JTextField txtAddress13;
    private javax.swing.JTextField txtAddress14;
    private javax.swing.JTextField txtAddress15;
    private javax.swing.JTextField txtAddress16;
    private javax.swing.JTextField txtAddress17;
    private javax.swing.JTextField txtAddress18;
    private javax.swing.JTextField txtAddress19;
    private javax.swing.JTextField txtAddress2;
    private javax.swing.JTextField txtAddress20;
    private javax.swing.JTextField txtAddress21;
    private javax.swing.JTextField txtAddress3;
    private javax.swing.JTextField txtAddress4;
    private javax.swing.JTextField txtAddress5;
    private javax.swing.JTextField txtAddress6;
    private javax.swing.JTextField txtAddress7;
    private javax.swing.JTextField txtAddress8;
    private javax.swing.JTextField txtAddress9;
    private javax.swing.JTextField txtChannel;
    private javax.swing.JTextArea txtContent;
    private javax.swing.JTextField txtFilingTime;
    private javax.swing.JTextField txtNum;
    private javax.swing.JTextField txtOrigin;
    private javax.swing.JTextField txtOtherInfo;
    private javax.swing.JTextField txtPriority;
    private javax.swing.JTextField txtSeq;
    // End of variables declaration//GEN-END:variables
}

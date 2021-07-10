/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.ui;

import com.shoessys.dao.NhanVienDAO;
import com.shoessys.entity.NhanVien;
import com.shoessys.ultil.MsgBox;
import java.awt.Color;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author Hoang Nguyen
 */
public class OTPJDialog extends javax.swing.JDialog {

    NhanVien nv;
    MainJFrame parent;
    int randomCode;
    NhanVienDAO dao = new NhanVienDAO();

    /**
     * Creates new form OTPDialog
     */
    public OTPJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        pnlOTP.setVisible(false);
        btnXacNhan.setVisible(false);
        setLocationRelativeTo(null);

    }

    void voHieuButton() {
        Thread th = new Thread() {
            @Override
            public void run() {
                int count = 20;
                while (count > -1) {
                    btnGui.setEnabled(false);
                    btnGui.setText("Gửi(" + count + ")");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                    count--;
                }
                btnGui.setEnabled(true);
                btnGui.setText("Gửi");
            }
        };
        th.start();
    }

    void SendGmail() {
        try {
            String manv = txtMaNV.getText();
            NhanVien nhanvien = dao.selectById(manv);
            String email = nhanvien.getEmail();
            Random rand = new Random();
            randomCode = rand.nextInt(999999);
            String host = "smtp.gmail.com";
            String user = "hoangnhps11609@fpt.edu.vn";
            String pass = "Verz@123";
            String to = email;
            String subject = "Mã Xác Nhận";
            String message = "Mã xác nhận của bạn là " + randomCode;
            boolean sessionDebug = false;
            Properties pros = System.getProperties();
            pros.put("mail.smtp.starttls.enable", "true");
            pros.put("mail.smtp.host", "host");
            pros.put("mail.smtp.port", "587");
            pros.put("mail.smtp.auth", "true");
            pros.put("mail.smtp.starttle.required", "true");
            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(pros, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(user));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setText(message);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, user, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            JOptionPane.showMessageDialog(null, "Mã xác nhận đã được gửi qua Gmail của bạn!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel1 = new keeptoo.KGradientPanel();
        panel = new javax.swing.JPanel();
        pnlMaNV = new javax.swing.JPanel();
        txtMaNV = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        pnlEmail = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        btnGui = new keeptoo.KButton();
        pnlOTP = new javax.swing.JPanel();
        txtMaOTP = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnXacNhan = new keeptoo.KButton();
        lblClose = new javax.swing.JLabel();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel1.setkBorderRadius(0);
        kGradientPanel1.setkEndColor(new java.awt.Color(255, 129, 112));
        kGradientPanel1.setkStartColor(new java.awt.Color(40, 61, 90));
        kGradientPanel1.setPreferredSize(new java.awt.Dimension(280, 420));

        panel.setOpaque(false);
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlMaNV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        pnlMaNV.setOpaque(false);
        pnlMaNV.setPreferredSize(new java.awt.Dimension(0, 40));

        txtMaNV.setBackground(new java.awt.Color(240, 240, 240));
        txtMaNV.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMaNV.setForeground(new java.awt.Color(255, 255, 255));
        txtMaNV.setText("nhập mã nhân viên");
        txtMaNV.setBorder(null);
        txtMaNV.setDisabledTextColor(new java.awt.Color(240, 240, 240));
        txtMaNV.setOpaque(false);
        txtMaNV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaNVFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaNVFocusLost(evt);
            }
        });
        txtMaNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMaNVMouseClicked(evt);
            }
        });
        txtMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNVActionPerformed(evt);
            }
        });
        txtMaNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMaNVKeyReleased(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ShoesSys/ICON/icons8_user_filled_24px.png"))); // NOI18N
        jLabel3.setToolTipText("");

        javax.swing.GroupLayout pnlMaNVLayout = new javax.swing.GroupLayout(pnlMaNV);
        pnlMaNV.setLayout(pnlMaNVLayout);
        pnlMaNVLayout.setHorizontalGroup(
            pnlMaNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMaNVLayout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
        );
        pnlMaNVLayout.setVerticalGroup(
            pnlMaNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaNV)
            .addGroup(pnlMaNVLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel.add(pnlMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, -1));

        pnlEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        pnlEmail.setOpaque(false);
        pnlEmail.setPreferredSize(new java.awt.Dimension(0, 40));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_google_plus_24px.png"))); // NOI18N
        jLabel4.setToolTipText("");

        lblEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(255, 255, 255));
        lblEmail.setText("email đăng ký");
        lblEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEmailMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlEmailLayout = new javax.swing.GroupLayout(pnlEmail);
        pnlEmail.setLayout(pnlEmailLayout);
        pnlEmailLayout.setHorizontalGroup(
            pnlEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEmailLayout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))
        );
        pnlEmailLayout.setVerticalGroup(
            pnlEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEmailLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(pnlEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        panel.add(pnlEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 223, -1));

        btnGui.setText("Gửi");
        btnGui.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGui.setkHoverEndColor(new java.awt.Color(83, 140, 150));
        btnGui.setkHoverForeGround(new java.awt.Color(255, 255, 0));
        btnGui.setkHoverStartColor(new java.awt.Color(83, 140, 150));
        btnGui.setkPressedColor(new java.awt.Color(83, 140, 150));
        btnGui.setkSelectedColor(new java.awt.Color(83, 140, 150));
        btnGui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuiActionPerformed(evt);
            }
        });
        panel.add(btnGui, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 200, 35));

        pnlOTP.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        pnlOTP.setOpaque(false);
        pnlOTP.setPreferredSize(new java.awt.Dimension(0, 40));
        pnlOTP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pnlOTPFocusGained(evt);
            }
        });

        txtMaOTP.setBackground(new java.awt.Color(102, 102, 102));
        txtMaOTP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMaOTP.setForeground(new java.awt.Color(255, 255, 255));
        txtMaOTP.setText("nhập mã OTP");
        txtMaOTP.setBorder(null);
        txtMaOTP.setOpaque(false);
        txtMaOTP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaOTPFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaOTPFocusLost(evt);
            }
        });
        txtMaOTP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMaOTPMouseClicked(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_qr_code_24px_1.png"))); // NOI18N
        jLabel6.setToolTipText("");

        javax.swing.GroupLayout pnlOTPLayout = new javax.swing.GroupLayout(pnlOTP);
        pnlOTP.setLayout(pnlOTPLayout);
        pnlOTPLayout.setHorizontalGroup(
            pnlOTPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOTPLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaOTP, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
        );
        pnlOTPLayout.setVerticalGroup(
            pnlOTPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaOTP)
            .addGroup(pnlOTPLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel.add(pnlOTP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, -1));

        btnXacNhan.setText("Xác Nhận");
        btnXacNhan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnXacNhan.setkHoverEndColor(new java.awt.Color(83, 140, 150));
        btnXacNhan.setkHoverForeGround(new java.awt.Color(255, 255, 0));
        btnXacNhan.setkHoverStartColor(new java.awt.Color(83, 140, 150));
        btnXacNhan.setkPressedColor(new java.awt.Color(83, 140, 150));
        btnXacNhan.setkSelectedColor(new java.awt.Color(83, 140, 150));
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });
        panel.add(btnXacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 200, 35));

        lblClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_delete_15px_1.png"))); // NOI18N
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCloseMouseExited(evt);
            }
        });

        kGradientPanel2.setBackground(new java.awt.Color(255, 255, 255));
        kGradientPanel2.setForeground(new java.awt.Color(255, 255, 255));
        kGradientPanel2.setkBorderRadius(170);
        kGradientPanel2.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel2.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel2.setOpaque(false);
        kGradientPanel2.setPreferredSize(new java.awt.Dimension(100, 100));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_bee_120px.png"))); // NOI18N

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80))
                    .addComponent(lblClose, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addComponent(lblClose)
                .addGap(34, 34, 34)
                .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        getContentPane().add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMaNVMouseClicked
        // TODO add your handling code here:
        txtMaNV.setText("");
        txtMaNV.setForeground(Color.white);
    }//GEN-LAST:event_txtMaNVMouseClicked

    private void txtMaOTPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMaOTPMouseClicked
        // TODO add your handling code here:
        txtMaOTP.setText("");
    }//GEN-LAST:event_txtMaOTPMouseClicked

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked
        // TODO add your handling code here:
        dispose();
        new DangNhapJDialog(parent, true).setVisible(true);
    }//GEN-LAST:event_lblCloseMouseClicked

    private void btnGuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuiActionPerformed
        // TODO add your handling code here:
        if (txtMaNV.getText().equals("")) {
            MsgBox.alert(this, "Mời nhập mã Nhân Viên");
        } else {
            SendGmail();
            voHieuButton();
            pnlMaNV.setVisible(false);
            pnlEmail.setVisible(false);
            pnlOTP.setVisible(true);
            btnGui.setVisible(false);
            btnXacNhan.setVisible(true);
        }
    }//GEN-LAST:event_btnGuiActionPerformed

    private void txtMaNVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaNVKeyReleased
        // TODO add your handling code here:
//        String manv = txtMaNV.getText();
//        NhanVien nhanvien = dao.selectById(manv);
//        if (nhanvien == null) {
//            MsgBox.alert(this, "Không có mã Nhân Viên cần tìm!!");
//        } else {
//            lblEmail.setText(nhanvien.getEmail().substring(0, 4) + "*****@*****.***");
//            this.nv = nhanvien;
//        }
    }//GEN-LAST:event_txtMaNVKeyReleased

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        // TODO add your handling code here:
        if (Integer.valueOf(txtMaOTP.getText()) == randomCode) {
            this.dispose();
            new TaoMatKhauJDialog(parent, true, nv).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Mã xác nhận không chính xác!!");
        }
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void txtMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNVActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtMaNVActionPerformed

    private void lblEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEmailMouseClicked
        // TODO add your handling code here:
        String manv = txtMaNV.getText();
        NhanVien nhanvien = dao.selectById(manv);
        if (txtMaNV.getText().equals("") || txtMaNV.getText().equals("nhập mã nhân viên")) {
            MsgBox.alert(this, "Mời bạn nhập mã nhân viên");
        } else if (nhanvien == null) {
            MsgBox.alert(this, "Không có mã Nhân Viên cần tìm");
        } else {
            lblEmail.setText(nhanvien.getEmail().substring(0, 5) + "*****@*****.***");
            this.nv = nhanvien;
        }
    }//GEN-LAST:event_lblEmailMouseClicked

    private void lblCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseEntered
        lblClose.setOpaque(true);
        lblClose.setBackground(new java.awt.Color(204,0,0));
    }//GEN-LAST:event_lblCloseMouseEntered

    private void lblCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseExited
        lblClose.setOpaque(false);
        lblClose.setBackground(new java.awt.Color(240,240,240));
    }//GEN-LAST:event_lblCloseMouseExited

    private void txtMaNVFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNVFocusGained
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtMaNVFocusGained

    private void txtMaNVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNVFocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtMaNVFocusLost

    private void pnlOTPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pnlOTPFocusGained
        // TODO add your handling code here:
        
    }//GEN-LAST:event_pnlOTPFocusGained

    private void txtMaOTPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaOTPFocusGained
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtMaOTPFocusGained

    private void txtMaOTPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaOTPFocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtMaOTPFocusLost

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OTPJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OTPJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OTPJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OTPJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                OTPJDialog dialog = new OTPJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private keeptoo.KButton btnGui;
    private keeptoo.KButton btnXacNhan;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel pnlEmail;
    private javax.swing.JPanel pnlMaNV;
    private javax.swing.JPanel pnlOTP;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaOTP;
    // End of variables declaration//GEN-END:variables
}

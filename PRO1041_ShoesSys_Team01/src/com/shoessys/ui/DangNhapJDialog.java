/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.ui;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.shoessys.dao.NhanVienDAO;
import com.shoessys.entity.NhanVien;
import com.shoessys.ultil.Auth;
import com.shoessys.ultil.MsgBox;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.prefs.Preferences;
import javax.swing.border.MatteBorder;

/**
 *
 * @author trann
 */
public class DangNhapJDialog extends javax.swing.JDialog implements Runnable, ThreadFactory{
    MainJFrame parent;

    public Preferences pref = Preferences.userRoot().node("Rememberme");
    
    /**
     * Creates new form DangNhapJDialog
     */
    public DangNhapJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.init();
//        String user = null;
//        user = pref.get("Username", user);
//        txtMaNV.setText(user);
    }
    
//    public void rememberUser(String MaNV, String MatKhau) {
//        if (MaNV.length() == 0 || MatKhau.length() == 0) {
//            System.out.println("Mời bạn nhập đủ thông tin");
//        } else {
//            String user = MaNV;
//            pref.put("Username", MaNV);
//            String pass = MatKhau;
//            pref.put("Password", MatKhau);
//            System.out.println("Lưu tên đăng nhập thành công");
//        }
//    }
//
//    public final void checked() {
//        rememberUser(txtMaNV.getText(), txtMatKhau.getText());
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel1 = new keeptoo.KGradientPanel();
        jPanel1 = new javax.swing.JPanel();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblClose = new javax.swing.JLabel();
        lblTroLai = new javax.swing.JLabel();
        pnlBody = new javax.swing.JPanel();
        pnlDangNhap = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        lblEye = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        btnDangNhap = new keeptoo.KButton();
        lblQR = new javax.swing.JLabel();
        lblQuenMK = new javax.swing.JLabel();
        lblLoiMK = new javax.swing.JLabel();
        lblLoiTK = new javax.swing.JLabel();
        pnlQR = new javax.swing.JPanel();
        pnlWebCam = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        kGradientPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        kGradientPanel1.setkBorderRadius(0);
        kGradientPanel1.setkEndColor(new java.awt.Color(255, 129, 112));
        kGradientPanel1.setkStartColor(new java.awt.Color(40, 61, 90));

        jPanel1.setOpaque(false);
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });

        kGradientPanel2.setkBorderRadius(200);
        kGradientPanel2.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel2.setkFillBackground(false);
        kGradientPanel2.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel2.setOpaque(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_bee_120px.png"))); // NOI18N

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setOpaque(false);

        lblClose.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
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

        lblTroLai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_back_arrow_15px.png"))); // NOI18N
        lblTroLai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblTroLai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTroLaiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTroLaiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTroLaiMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(lblTroLai)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblClose))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClose)
                    .addComponent(lblTroLai))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnlBody.setOpaque(false);
        pnlBody.setLayout(new java.awt.CardLayout());

        pnlDangNhap.setOpaque(false);
        pnlDangNhap.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ĐĂNG NHẬP");
        pnlDangNhap.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 11, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_user_shield_25px.png"))); // NOI18N
        pnlDangNhap.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 46, -1, -1));

        txtMaNV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMaNV.setForeground(new java.awt.Color(153, 153, 153));
        txtMaNV.setText("Tên tài khoản");
        txtMaNV.setBorder(null);
        txtMaNV.setOpaque(false);
        txtMaNV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaNVFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaNVFocusLost(evt);
            }
        });
        txtMaNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMaNVKeyReleased(evt);
            }
        });
        pnlDangNhap.add(txtMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 46, 200, 30));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        pnlDangNhap.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 76, 234, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_password_25px.png"))); // NOI18N
        pnlDangNhap.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 95, -1, -1));

        txtMatKhau.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMatKhau.setForeground(new java.awt.Color(153, 153, 153));
        txtMatKhau.setText("Mật khẩu");
        txtMatKhau.setBorder(null);
        txtMatKhau.setOpaque(false);
        txtMatKhau.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMatKhauFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMatKhauFocusLost(evt);
            }
        });
        txtMatKhau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMatKhauKeyReleased(evt);
            }
        });
        pnlDangNhap.add(txtMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 95, 170, 30));

        lblEye.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_invisible_16px.png"))); // NOI18N
        lblEye.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblEye.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEyeMouseClicked(evt);
            }
        });
        pnlDangNhap.add(lblEye, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 95, 30, 30));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        pnlDangNhap.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 125, 234, -1));

        btnDangNhap.setText("Đăng nhập");
        btnDangNhap.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDangNhap.setkHoverEndColor(new java.awt.Color(83, 140, 150));
        btnDangNhap.setkHoverForeGround(new java.awt.Color(255, 255, 0));
        btnDangNhap.setkHoverStartColor(new java.awt.Color(83, 140, 150));
        btnDangNhap.setkPressedColor(new java.awt.Color(83, 140, 150));
        btnDangNhap.setkSelectedColor(new java.awt.Color(83, 140, 150));
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangNhapActionPerformed(evt);
            }
        });
        pnlDangNhap.add(btnDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 144, 210, 30));

        lblQR.setForeground(new java.awt.Color(255, 255, 255));
        lblQR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_qr_code_20px_2.png"))); // NOI18N
        lblQR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblQR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQRMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQRMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQRMouseExited(evt);
            }
        });
        pnlDangNhap.add(lblQR, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 149, -1, -1));

        lblQuenMK.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblQuenMK.setForeground(new java.awt.Color(255, 255, 255));
        lblQuenMK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQuenMK.setText("Quên mật khẩu?");
        lblQuenMK.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        lblQuenMK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblQuenMK.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblQuenMKMouseMoved(evt);
            }
        });
        lblQuenMK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQuenMKMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQuenMKMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblQuenMKMousePressed(evt);
            }
        });
        pnlDangNhap.add(lblQuenMK, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, -1, -1));

        lblLoiMK.setForeground(new java.awt.Color(255, 153, 0));
        pnlDangNhap.add(lblLoiMK, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 127, 234, 17));

        lblLoiTK.setForeground(new java.awt.Color(255, 153, 0));
        pnlDangNhap.add(lblLoiTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 78, 234, 17));

        pnlBody.add(pnlDangNhap, "card2");

        pnlQR.setBackground(new java.awt.Color(255, 255, 255));

        pnlWebCam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout pnlQRLayout = new javax.swing.GroupLayout(pnlQR);
        pnlQR.setLayout(pnlQRLayout);
        pnlQRLayout.setHorizontalGroup(
            pnlQRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQRLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlWebCam, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlQRLayout.setVerticalGroup(
            pnlQRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQRLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlWebCam, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlBody.add(pnlQR, "card3");

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlBody, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblCloseMouseClicked

    private void lblCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseEntered
        lblClose.setOpaque(true);
        lblClose.setBackground(new java.awt.Color(204,0,0));
    }//GEN-LAST:event_lblCloseMouseEntered

    private void lblCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseExited
        lblClose.setOpaque(false);
        lblClose.setBackground(new java.awt.Color(240,240,240));
    }//GEN-LAST:event_lblCloseMouseExited

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        int xX = evt.getXOnScreen();
        int yY = evt.getYOnScreen();
        
        this.setLocation(xX - x, yY - y);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void lblEyeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeMouseClicked
        if(txtMatKhau.getEchoChar()==('\u25cf')){
            lblEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_visible_16px.png")));
            txtMatKhau.setEchoChar((char)0);
        }else{
            lblEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_invisible_16px.png")));
            txtMatKhau.setEchoChar('\u25cf');
        }
    }//GEN-LAST:event_lblEyeMouseClicked

    private void txtMaNVFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNVFocusGained
        if(txtMaNV.getText().equals("Tên tài khoản")){
            txtMaNV.setText("");
            txtMaNV.setForeground(new java.awt.Color(255,255,255));
        }
    }//GEN-LAST:event_txtMaNVFocusGained

    private void txtMaNVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNVFocusLost
        if(txtMaNV.getText().equals("")){
            txtMaNV.setText("Tên tài khoản");
            txtMaNV.setForeground(new java.awt.Color(153,153,153));
        }
    }//GEN-LAST:event_txtMaNVFocusLost

    private void txtMatKhauFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMatKhauFocusGained
        if(new String(txtMatKhau.getPassword()).equals("Mật khẩu")){
            txtMatKhau.setText("");
            txtMatKhau.setForeground(new java.awt.Color(255,255,255));
        }
    }//GEN-LAST:event_txtMatKhauFocusGained

    private void txtMatKhauFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMatKhauFocusLost
        if(new String(txtMatKhau.getPassword()).equals("")){
            txtMatKhau.setText("Mật khẩu");
            txtMatKhau.setForeground(new java.awt.Color(153,153,153));
        }
    }//GEN-LAST:event_txtMatKhauFocusLost

    private void lblQRMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQRMouseEntered
        lblQR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_qr_code_20px_3.png")));
    }//GEN-LAST:event_lblQRMouseEntered

    private void lblQRMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQRMouseExited
        lblQR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_qr_code_20px_2.png")));
    }//GEN-LAST:event_lblQRMouseExited

    private void lblQuenMKMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuenMKMouseEntered
        lblQuenMK.setForeground(new java.awt.Color(0,204,204));
        lblQuenMK.setBorder(new MatteBorder(0, 0, 1, 0, new java.awt.Color(0,204,204)));
    }//GEN-LAST:event_lblQuenMKMouseEntered

    private void lblQuenMKMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuenMKMouseExited
        lblQuenMK.setForeground(new java.awt.Color(255,255,255));
        lblQuenMK.setBorder(new MatteBorder(0, 0, 1, 0, new java.awt.Color(255,255,255)));
    }//GEN-LAST:event_lblQuenMKMouseExited

    private void lblQRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQRMouseClicked
        pnlDangNhap.show(false);
        pnlQR.show();
        lblTroLai.setVisible(true);
        this.webCam();
    }//GEN-LAST:event_lblQRMouseClicked

    private void lblTroLaiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTroLaiMouseEntered
        lblTroLai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_back_arrow_15px_1.png")));
    }//GEN-LAST:event_lblTroLaiMouseEntered

    private void lblTroLaiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTroLaiMouseExited
        lblTroLai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_back_arrow_15px.png")));
    }//GEN-LAST:event_lblTroLaiMouseExited

    private void lblTroLaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTroLaiMouseClicked
        pnlQR.show(false);
        pnlDangNhap.show();
        lblTroLai.setVisible(false);
        webCam.close();
    }//GEN-LAST:event_lblTroLaiMouseClicked

    private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangNhapActionPerformed
        if(isValidation()){
//            checked();
            this.dangNhap();
        }
        
    }//GEN-LAST:event_btnDangNhapActionPerformed

    private void txtMaNVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaNVKeyReleased
        if(!txtMaNV.getText().equals("Tên tài khoản")){
            lblLoiTK.setText("");
        }
    }//GEN-LAST:event_txtMaNVKeyReleased

    private void txtMatKhauKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMatKhauKeyReleased
        if(!new String(txtMatKhau.getPassword()).equals("Mật khẩu")){
            lblLoiMK.setText("");
        }
    }//GEN-LAST:event_txtMatKhauKeyReleased

    private void lblQuenMKMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuenMKMouseMoved
        // TODO add your handling code here:
        lblQuenMK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblQuenMKMouseMoved

    private void lblQuenMKMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuenMKMousePressed
        // TODO add your handling code here:
         dispose();
        new OTPJDialog(parent, true).setVisible(true);
    }//GEN-LAST:event_lblQuenMKMousePressed

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
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DangNhapJDialog dialog = new DangNhapJDialog(new javax.swing.JFrame(), true);
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
    private keeptoo.KButton btnDangNhap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblEye;
    private javax.swing.JLabel lblLoiMK;
    private javax.swing.JLabel lblLoiTK;
    private javax.swing.JLabel lblQR;
    private javax.swing.JLabel lblQuenMK;
    private javax.swing.JLabel lblTroLai;
    private javax.swing.JPanel pnlBody;
    private javax.swing.JPanel pnlDangNhap;
    private javax.swing.JPanel pnlQR;
    private javax.swing.JPanel pnlWebCam;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtMatKhau;
    // End of variables declaration//GEN-END:variables
    private int x, y;
    private WebcamPanel panel = null;
    private Webcam webCam = null;
    
    private static final long serialVersionUID = 6441489157408381878L;
    private Executor executor = Executors.newSingleThreadExecutor(this);
    NhanVienDAO dao = new NhanVienDAO();
    private void init() {
        setLocationRelativeTo(null);
        lblTroLai.setVisible(false);
    }
    private void webCam(){
        Dimension size = WebcamResolution.QVGA.getSize();
        webCam = Webcam.getWebcams().get(0);
        webCam.setViewSize(size);
        
        panel = new WebcamPanel(webCam);
        panel.setPreferredSize(size);
        
        pnlWebCam.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 284, 210));
        executor.execute(this);
    }

    @Override
    public void run() {
        do{
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Result rs = null;
            BufferedImage image = null;
            if(webCam.isOpen()){
                if((image = webCam.getImage()) == null){
                    continue;
                }
            }
            if((image = webCam.getImage()) !=null){
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                try {
                    rs = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException e) {
                    //No result...
                }
                if(rs != null){
                    String manv = rs.getText();
                    try {
                        NhanVien nv = dao.selectById(manv);
                        if(nv!=null){
                            Auth.User = nv;
                            MsgBox.alert(this, "Đăng nhập thành công!");
                            this.dispose();
                            webCam.close();
                            parent.processLoginSuccessful();
                            break;
                        }else{
                            MsgBox.alert(this, "Tài khoản không tồn tại!");
                        }
                    } catch (Exception e) {
                        MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
                    }
                }
            }
        }while(true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread th = new Thread(r, "My Thread");
        th.setDaemon(true);
        return th;
    }
    
    void dangNhap(){
        String manv = txtMaNV.getText();
        String matkhau = new String(txtMatKhau.getPassword());
        try {
            NhanVien nv = dao.selectById(manv);
            if(nv == null){
               lblLoiTK.setText("Sai tên đăng nhập!");
            }else if(!matkhau.equals(nv.getMatKhau())){
                lblLoiMK.setText("Sai mật khẩu!");
            }else{
                Auth.User = nv;
                MsgBox.alert(this, "Đăng nhập thành công!");
                parent.processLoginSuccessful();
                this.dispose();
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    
    private boolean isValidation(){
        if(txtMaNV.getText().equals("Tên tài khoản") && 
                new String(txtMatKhau.getPassword()).equals("Mật khẩu")){
            lblLoiTK.setText("Vui lòng nhập tài khoản!");
            lblLoiMK.setText("Vui lòng nhập mật khẩu!");
            return false;
        }
        if(txtMaNV.getText().equals("Tên tài khoản")){
            lblLoiTK.setText("Vui lòng nhập tài khoản!");
            return false;
        }
        if(new String(txtMatKhau.getPassword()).equals("Mật khẩu")){
            lblLoiMK.setText("Vui lòng nhập mật khẩu!");
            return false;
        }
        return true;
    }
}

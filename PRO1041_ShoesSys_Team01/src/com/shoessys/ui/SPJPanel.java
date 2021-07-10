/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.ui;

import com.shoessys.dao.DonHangChiTietDAO;
import com.shoessys.dao.SanPhamDAO;
import com.shoessys.entity.DonHangChiTiet;
import com.shoessys.entity.SanPham;
import com.shoessys.ultil.MsgBox;
import com.shoessys.ultil.Ximage;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author trann
 */
public class SPJPanel extends javax.swing.JPanel {
   
    /**
     * Creates new form SPJPanel
     */
    public SPJPanel() {
        initComponents();
    }
    
    public SPJPanel(int madh, int masp, String tensp, String hinh, double dongia) {
        initComponents();
        lblTen.setText(tensp);
        String tien = String.format(VND.format(dongia));
        lblGia.setText(tien.replace("đ", ""));
        lblHinh.setIcon(Ximage.read(hinh, 200, 165, "sanpham"));
        this.madh = madh;
        this.masp = masp;
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblHinh = new javax.swing.JLabel();
        lblTen = new javax.swing.JLabel();
        lblGia = new javax.swing.JLabel();
        lblDonVi = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        lblHinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblTen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblGia.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblGia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblDonVi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDonVi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDonVi.setText("VNĐ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDonVi, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTen, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblGia, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblDonVi)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        this.setBackground(new java.awt.Color(83, 140, 150));
    }//GEN-LAST:event_formMouseEntered

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        this.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_formMouseExited

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        if(evt.getClickCount()==2){
            this.them();
        }
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDonVi;
    private javax.swing.JLabel lblGia;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblTen;
    // End of variables declaration//GEN-END:variables
    int madh;
    int masp;
    Locale vn = new Locale("vi", "VN");
    NumberFormat VND = NumberFormat.getCurrencyInstance(vn);
    DonHangChiTietDAO dao = new DonHangChiTietDAO();
    SanPhamDAO spdao = new SanPhamDAO();
    void them(){
        double dongia = Double.parseDouble(lblGia.getText().replace(".", ""));
        try {
            DonHangChiTiet dhct = dao.selectByMahdAndMasp(madh, masp);
            SanPham sp = spdao.selectById(masp);
            if(sp.getSoLuong()==0){
                MsgBox.alert(this, "Sản phẩm này hiện tại hết hàng!");
                return;
            }
            if(dhct==null){
                dhct = new DonHangChiTiet();
                dhct.setMaDH(madh);
                dhct.setMaSP(masp);
                dhct.setDonGia(dongia);
                dhct.setSoLuong(1);
                dao.insert(dhct);
                sp.setSoLuong(sp.getSoLuong()-1);
                spdao.update(sp);
            }else{
                dhct.setSoLuong(dhct.getSoLuong()+1);
                dao.update(dhct);
                sp.setSoLuong(sp.getSoLuong()-1);
                spdao.update(sp);
            }
            ChiTietDonHangJDialog.fillTable();
            ChiTietDonHangJDialog.tongTien();
        } catch (Exception e) {}
    }
}
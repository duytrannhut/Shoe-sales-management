/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.ui;

import com.shoessys.dao.LoaiDAO;
import com.shoessys.entity.Loai;
import com.shoessys.ultil.Auth;
import com.shoessys.ultil.DataValidator;
import com.shoessys.ultil.MsgBox;
import com.shoessys.ultil.XSTT;
import java.awt.Color;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author trann
 */
public class LoaiJPanel extends javax.swing.JPanel {

    /**
     * Creates new form LoaiJPanel
     */
    public LoaiJPanel() {
        initComponents();
        init();
        sort();
    }
    public void maLoaiTT(){
       lblMaLoai.setText(dao.idNext()+"");
    }
    private void init(){
        loadTable();
        updateStatus(); 
        this.maLoaiTT();
    }
    List<Loai> list = new ArrayList<Loai>();
    LoaiDAO dao = new LoaiDAO();
    private void loadTable(){
        DefaultTableModel model = (DefaultTableModel) this.tblLoai.getModel();
        model.setRowCount(0);
        list = dao.selecbyKeyword("");
        for (int i = 0; i < list.size(); i++) {
            model.addRow(new Object[]{list.get(i).getMaLoai(), list.get(i).getTenLoai()});
        }
    }
    private void loadTableByKeyword(){
        DefaultTableModel model = (DefaultTableModel) this.tblLoai.getModel();
        model.setRowCount(0);
        list = dao.selecbyKeyword(txtTimKiem.getText());
        for (int i = 0; i < list.size(); i++) {
            model.addRow(new Object[]{list.get(i).getMaLoai(), list.get(i).getTenLoai()});
        }
    }
    
    private void timKiem(){
        this.loadTableByKeyword();
        this.moi();
        this.row = -1;
        this.updateStatus();
    }
    
    void edit(){
        int maLoai = Integer.parseInt(tblLoai.getValueAt(this.row, 0).toString());
        Loai l = dao.selectById(maLoai);
        this.setForm(l);
        this.updateStatus();
        tblLoai.setEditingRow(row);
    }

    private void updateStatus(){
    boolean edit = this.row >= 0;
        boolean first = this.row == 0;
        boolean last = this.row == tblLoai.getRowCount() - 1;
        //Tạng Thái Form
//        txtMaLoai.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
        
        //Trạng thái điều hướng
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
        
       if(!edit){
            btnThem.setkStartColor(new java.awt.Color(0,153,153));
            
            btnSua.setkStartColor(new java.awt.Color(204,204,204));
            btnXoa.setkStartColor(new java.awt.Color(204,204,204));
            
            btnSua.setkHoverStartColor(new java.awt.Color(204,204,204));
            btnXoa.setkHoverStartColor(new java.awt.Color(204,204,204));
            btnThem.setkHoverStartColor(new java.awt.Color(40,61,90));
        }else{
            btnThem.setkStartColor(new java.awt.Color(204,204,204));
            
            btnSua.setkStartColor(new java.awt.Color(0,153,153));
            btnXoa.setkStartColor(new java.awt.Color(0,153,153));
            
            btnSua.setkHoverStartColor(new java.awt.Color(40,61,90));
            btnXoa.setkHoverStartColor(new java.awt.Color(40,61,90));
            btnThem.setkHoverStartColor(new java.awt.Color(204,204,204));
        }
        if(edit && !first){
            btnFirst.setkStartColor(new java.awt.Color(0,153,153));
            btnPrev.setkStartColor(new java.awt.Color(0,153,153));
            
            btnFirst.setkHoverStartColor(new java.awt.Color(40,61,90));
            btnPrev.setkHoverStartColor(new java.awt.Color(40,61,90));
        }else{
            btnFirst.setkStartColor(new java.awt.Color(204,204,204));
            btnPrev.setkStartColor(new java.awt.Color(204,204,204));
            
            btnFirst.setkHoverStartColor(new java.awt.Color(204,204,204));
            btnPrev.setkHoverStartColor(new java.awt.Color(204,204,204));
        }
        if(edit && !last){
            btnNext.setkStartColor(new java.awt.Color(0,153,153));
            btnLast.setkStartColor(new java.awt.Color(0,153,153));
            
            btnNext.setkHoverStartColor(new java.awt.Color(40,61,90));
            btnLast.setkHoverStartColor(new java.awt.Color(40,61,90));
        }else{
            btnNext.setkStartColor(new java.awt.Color(204,204,204));
            btnLast.setkStartColor(new java.awt.Color(204,204,204));
            
            btnNext.setkHoverStartColor(new java.awt.Color(204,204,204));
            btnLast.setkHoverStartColor(new java.awt.Color(204,204,204));
        }
    }
    private Loai getForm(){
        return new Loai(Integer.parseInt(lblMaLoai.getText()), txtTenLoai.getText());
    }
    private void setForm(Loai l){
        lblMaLoai.setText(String.valueOf(l.getMaLoai()));
        txtTenLoai.setText(l.getTenLoai());
    }
    private void xuatFile(){}
    int row = -1;
    private void first(){
        this.row=0;
        this.edit();
    }
    
    private void previous(){
        if(this.row>0){
            this.row--;
            this.edit();
        }
    }
    
    private void next(){
        if (this.row < tblLoai.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }
    private void last(){
        this.row = tblLoai.getRowCount()-1;
        this.edit();
    }
    private void them(){
        Loai l = getForm();
        try {
            dao.insert(l);
            this.loadTable();
            this.moi();
            MsgBox.alert(this, "Thêm mới thành công.");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm mới Thất bại.");
        }
    }
    private void xoa(){
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền xóa!");
        }else{
            int maLoai = Integer.parseInt(lblMaLoai.getText());
            if(MsgBox.comfirm(this, "Bạn thật sự muốn xóa loại sản phẩm này?")){
                try {
                    dao.delete(maLoai);this.loadTable();this.moi();
                    MsgBox.alert(this, "Xóa thành công.");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại.");
                }
            }
        }
    }
    private void sua(){
        Loai l = getForm();
        try {
            dao.update(l);
            this.loadTable();
            this.moi();
            MsgBox.alert(this, "Thay đổi thành công.");
        } catch (Exception e) {
            MsgBox.alert(this, "Thay đổi Thất bại.");
        }
    }
    private void moi(){
        Loai l = new Loai();
        this.setForm(l);
        this.row = -1;
        this.updateStatus();
        this.maLoaiTT();
    }
    private void tbl(java.awt.event.MouseEvent evt){
        if (evt.getClickCount() == 2) {
            this.row = tblLoai.getSelectedRow();
            this.edit();
        } 
    }
    
    void print(JTable table, String header){
        MessageFormat Header = new MessageFormat(header);
        MessageFormat Footer = new MessageFormat("Trang (0,number,integer)");
        try {
//            changePrintDialogIcon(Ximage.APP_ICON);
            table.print(JTable.PrintMode.FIT_WIDTH, Header, Footer);
        } catch (Exception e) {
            MsgBox.alert(null, "Lỗi:"+ header.toLowerCase() +"!");
        }
    }
    
    private void sort(){
        DefaultTableModel model = (DefaultTableModel) tblLoai.getModel();
        TableRowSorter<DefaultTableModel> sorter =  new TableRowSorter<>(model);
        tblLoai.setRowSorter(sorter);
//        model.setRowCount(3);
    }
    
    boolean kiemTra(){
        String Email_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        String Anphabet_REGEX = "\\D+";
        String Phone_REGEX = "^0[35789]{1}\\d{8}$";
        StringBuilder sb = new StringBuilder();
        DataValidator.validateEmpty(txtTenLoai, sb, "Tên loại ");
        if(sb.length()>0){
            MsgBox.alert(this, sb.toString() + "không được để trống!");
            return false;
        }else{
            if (!txtTenLoai.getText().matches(Anphabet_REGEX)) {
                MsgBox.alert(this, "Họ tên chỉ chứa alphabet và ký tự trắng");
                return false;
            }
        }
        return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblMaLoai = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTenLoai = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLoai = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnThem = new keeptoo.KButton();
        btnSua = new keeptoo.KButton();
        btnXoa = new keeptoo.KButton();
        btnMoi = new keeptoo.KButton();
        jPanel2 = new javax.swing.JPanel();
        btnFirst = new keeptoo.KButton();
        btnPrev = new keeptoo.KButton();
        btnNext = new keeptoo.KButton();
        btnLast = new keeptoo.KButton();
        lblErro = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setVerifyInputWhenFocusTarget(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Thông tin_________________________________________________________________________________");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Mã loại:");

        lblMaLoai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMaLoai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Tên loại:");

        txtTenLoai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtTenLoai.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTenLoaiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTenLoaiFocusLost(evt);
            }
        });
        txtTenLoai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenLoaiMouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Danh sách loại sản phẩm:");

        txtTimKiem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtTimKiem.setForeground(new java.awt.Color(153, 153, 153));
        txtTimKiem.setText("Tìm kiếm");
        txtTimKiem.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTimKiem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTimKiemFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTimKiemFocusLost(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_search_16px.png"))); // NOI18N

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));

        tblLoai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblLoai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "MÃ LOẠI", "TÊN LOẠI"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLoai.setGridColor(new java.awt.Color(255, 255, 255));
        tblLoai.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblLoai.setRowHeight(22);
        tblLoai.setSelectionBackground(new java.awt.Color(83, 140, 150));
        tblLoai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLoaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblLoai);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnThem.setText("Thêm");
        btnThem.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnThem.setkBorderRadius(15);
        btnThem.setkEndColor(new java.awt.Color(51, 0, 255));
        btnThem.setkHoverEndColor(new java.awt.Color(83, 140, 150));
        btnThem.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btnThem.setkHoverStartColor(new java.awt.Color(40, 61, 90));
        btnThem.setkPressedColor(new java.awt.Color(83, 140, 150));
        btnThem.setkSelectedColor(new java.awt.Color(83, 140, 150));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSua.setkBorderRadius(15);
        btnSua.setkEndColor(new java.awt.Color(51, 0, 255));
        btnSua.setkHoverEndColor(new java.awt.Color(83, 140, 150));
        btnSua.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btnSua.setkHoverStartColor(new java.awt.Color(40, 61, 90));
        btnSua.setkPressedColor(new java.awt.Color(83, 140, 150));
        btnSua.setkSelectedColor(new java.awt.Color(83, 140, 150));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnXoa.setkBorderRadius(15);
        btnXoa.setkEndColor(new java.awt.Color(51, 0, 255));
        btnXoa.setkHoverEndColor(new java.awt.Color(83, 140, 150));
        btnXoa.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btnXoa.setkHoverStartColor(new java.awt.Color(40, 61, 90));
        btnXoa.setkPressedColor(new java.awt.Color(83, 140, 150));
        btnXoa.setkSelectedColor(new java.awt.Color(83, 140, 150));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setText("Mới");
        btnMoi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnMoi.setkBorderRadius(15);
        btnMoi.setkEndColor(new java.awt.Color(51, 0, 255));
        btnMoi.setkHoverEndColor(new java.awt.Color(83, 140, 150));
        btnMoi.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btnMoi.setkHoverStartColor(new java.awt.Color(40, 61, 90));
        btnMoi.setkPressedColor(new java.awt.Color(83, 140, 150));
        btnMoi.setkSelectedColor(new java.awt.Color(83, 140, 150));
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnFirst.setText("|<");
        btnFirst.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnFirst.setkBorderRadius(15);
        btnFirst.setkEndColor(new java.awt.Color(51, 0, 255));
        btnFirst.setkHoverEndColor(new java.awt.Color(83, 140, 150));
        btnFirst.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btnFirst.setkHoverStartColor(new java.awt.Color(40, 61, 90));
        btnFirst.setkPressedColor(new java.awt.Color(83, 140, 150));
        btnFirst.setkSelectedColor(new java.awt.Color(83, 140, 150));
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setText("<<");
        btnPrev.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnPrev.setkBorderRadius(15);
        btnPrev.setkEndColor(new java.awt.Color(51, 0, 255));
        btnPrev.setkHoverEndColor(new java.awt.Color(83, 140, 150));
        btnPrev.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btnPrev.setkHoverStartColor(new java.awt.Color(40, 61, 90));
        btnPrev.setkPressedColor(new java.awt.Color(83, 140, 150));
        btnPrev.setkSelectedColor(new java.awt.Color(83, 140, 150));
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnNext.setkBorderRadius(15);
        btnNext.setkEndColor(new java.awt.Color(51, 0, 255));
        btnNext.setkHoverEndColor(new java.awt.Color(83, 140, 150));
        btnNext.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btnNext.setkHoverStartColor(new java.awt.Color(40, 61, 90));
        btnNext.setkPressedColor(new java.awt.Color(83, 140, 150));
        btnNext.setkSelectedColor(new java.awt.Color(83, 140, 150));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLast.setkBorderRadius(15);
        btnLast.setkEndColor(new java.awt.Color(51, 0, 255));
        btnLast.setkHoverEndColor(new java.awt.Color(83, 140, 150));
        btnLast.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btnLast.setkHoverStartColor(new java.awt.Color(40, 61, 90));
        btnLast.setkPressedColor(new java.awt.Color(83, 140, 150));
        btnLast.setkSelectedColor(new java.awt.Color(83, 140, 150));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblMaLoai, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(txtTenLoai))
                        .addGap(18, 18, 18)
                        .addComponent(lblErro, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
                            .addComponent(jSeparator1))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(lblMaLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtTenLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblErro, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblLoaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiMouseClicked
        txtTenLoai.setBackground(Color.white);
        this.tbl(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_tblLoaiMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if(kiemTra()){
            this.them();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if(kiemTra()){
            this.sua();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        this.xoa();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        this.moi();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        this.first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        this.previous();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        this.next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        this.last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        if(txtTimKiem.getText().equals("Tìm kiếm")){
            txtTimKiem.setText("");
            txtTimKiem.setForeground(new java.awt.Color(0,0,0));
        }
    }//GEN-LAST:event_txtTimKiemFocusGained

    private void txtTimKiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusLost
        if(txtTimKiem.getText().equals("")){
            txtTimKiem.setText("Tìm kiếm");
            txtTimKiem.setForeground(new java.awt.Color(153,153,153));
        }
    }//GEN-LAST:event_txtTimKiemFocusLost

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        this.timKiem();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void txtTenLoaiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenLoaiFocusGained
        
    }//GEN-LAST:event_txtTenLoaiFocusGained

    private void txtTenLoaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenLoaiFocusLost
        
    }//GEN-LAST:event_txtTenLoaiFocusLost

    private void txtTenLoaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTenLoaiMouseClicked
        // TODO add your handling code here:
        txtTenLoai.setBackground(Color.white);
    }//GEN-LAST:event_txtTenLoaiMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private keeptoo.KButton btnFirst;
    private keeptoo.KButton btnLast;
    private keeptoo.KButton btnMoi;
    private keeptoo.KButton btnNext;
    private keeptoo.KButton btnPrev;
    private keeptoo.KButton btnSua;
    private keeptoo.KButton btnThem;
    private keeptoo.KButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblErro;
    private javax.swing.JLabel lblMaLoai;
    private javax.swing.JTable tblLoai;
    private javax.swing.JTextField txtTenLoai;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}

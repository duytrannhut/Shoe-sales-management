/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.ui;

import com.shoessys.dao.NhaCungCapDAO;
import com.shoessys.entity.NhaCungCap;
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
public class NhaCungCapJPanel extends javax.swing.JPanel {

    /**
     * Creates new form NhanCungCapJPanel
     */
    public NhaCungCapJPanel() {
        initComponents();
        init();
        sort();
    }
    private void init(){
        loadTable();
        updateStatus();
        this.maLoaiTT();
    }
    public void maLoaiTT(){
        lblNCC.setText(dao.idNext()+"");
    }
    List<NhaCungCap> list = new ArrayList<>();
    NhaCungCapDAO dao = new NhaCungCapDAO();
    private void loadTable(){
        DefaultTableModel model = (DefaultTableModel) this.tblNhaCC.getModel();
        model.setRowCount(0);
        list = dao.selecbyKeyword("");
        for (int i = 0; i < list.size(); i++) {
            model.addRow(new Object[]{list.get(i).getMaNCC(), list.get(i).getTenNCC(), list.get(i).getSoDT(),list.get(i).getDiaChi()});
        }
    }

    private void loadTableByKeyword(){
        DefaultTableModel model = (DefaultTableModel) this.tblNhaCC.getModel();
        model.setRowCount(0);
        list = dao.selecbyKeyword(txtTimKiem.getText());
        for (int i = 0; i < list.size(); i++) {
            model.addRow(new Object[]{list.get(i).getMaNCC(), list.get(i).getTenNCC(), list.get(i).getSoDT(),list.get(i).getDiaChi()});
        }
    } 
    
    private void timKiem(){
        this.loadTableByKeyword();
        this.moi();
        this.row = -1;
        this.updateStatus();
    }
    
    void edit(){
        int maNCC = Integer.parseInt(tblNhaCC.getValueAt(this.row, 0).toString());
        NhaCungCap  ncc = dao.selectById(maNCC);
        this.setForm(ncc);
        this.updateStatus();
        tblNhaCC.setEditingRow(row);
    }

    private void updateStatus(){
    boolean edit = this.row >= 0;
        boolean first = this.row == 0;
        boolean last = this.row == tblNhaCC.getRowCount() - 1;
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
    private NhaCungCap getForm(){
        return new NhaCungCap(Integer.parseInt(lblNCC.getText()), txtTenNCC.getText(), txtSDT.getText(), txtDiaChi.getText());
    }
    private void setForm(NhaCungCap ncc){
        lblNCC.setText(String.valueOf(ncc.getMaNCC()));
        txtTenNCC.setText(ncc.getTenNCC());
        txtSDT.setText(ncc.getSoDT());
        txtDiaChi.setText(ncc.getDiaChi());
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
        if (this.row < tblNhaCC.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }
    private void last(){
        this.row = tblNhaCC.getRowCount()-1;
        this.edit();
    }
    private void them(){
        NhaCungCap ncc = getForm();
        try {
            dao.insert(ncc);
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
            int maLoai = Integer.parseInt(lblNCC.getText());
            if(MsgBox.comfirm(this, "Bạn thật sự muốn xóa nhà cung cấp này?")){
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
        NhaCungCap l = getForm();
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
        NhaCungCap l = new NhaCungCap();
        this.setForm(l);
        this.row = -1;
        this.updateStatus();
        this.maLoaiTT();
    }
    private void tblClick(java.awt.event.MouseEvent evt){
        if (evt.getClickCount() == 2) {
            this.row = tblNhaCC.getSelectedRow();
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
        DefaultTableModel model = (DefaultTableModel) tblNhaCC.getModel();
        TableRowSorter<DefaultTableModel> sorter =  new TableRowSorter<>(model);
        tblNhaCC.setRowSorter(sorter);
//        model.setRowCount(3);
    }
    
    boolean kiemTra(){
        String Email_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        String Anphabet_REGEX = "\\D+";
        String Phone_REGEX = "^0[35789]{1}\\d{8}$";
        StringBuilder sb = new StringBuilder();
        DataValidator.validateEmpty(txtTenNCC, sb, "Tên nhà cung cấp, ");
        DataValidator.validateEmpty(txtSDT, sb, "Số điện thoại, ");
        DataValidator.validateEmpty(txtDiaChi, sb, "Địa chỉ ");
        if(sb.length()>0){
            MsgBox.alert(this, sb.toString() + "\nkhông được để trống!");
            return false;
        }else{
            if (!txtTenNCC.getText().matches(Anphabet_REGEX)) {
                MsgBox.alert(this, "Tên nhà cung cấp chỉ chứa alphabet và ký tự trắng");
                return false;
            }
            if (!txtSDT.getText().matches(Phone_REGEX)) {
                MsgBox.alert(this, "Số điện thoại không đúng định dạng!");
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
        lblNCC = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTenNCC = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhaCC = new javax.swing.JTable();
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

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Thông tin_________________________________________________________________________________");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Mã nhà cung cấp:");

        lblNCC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNCC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Tên nhà cung cấp:");

        txtTenNCC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtTenNCC.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTenNCCFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTenNCCFocusLost(evt);
            }
        });
        txtTenNCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenNCCMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Số điện thoại:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Địa chỉ:");

        txtSDT.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSDT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSDTFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSDTFocusLost(evt);
            }
        });
        txtSDT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenNCCMouseClicked(evt);
            }
        });

        txtDiaChi.setColumns(20);
        txtDiaChi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDiaChi.setRows(5);
        txtDiaChi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenNCCMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(txtDiaChi);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Danh sách nhà cung cấp:");

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

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));

        tblNhaCC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblNhaCC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "MÃ NHÀ CUNG CẤP", "TÊN NHÀ CUNG CẤP", "SỐ ĐIỆN THOẠI", "ĐỊA CHỈ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhaCC.setGridColor(new java.awt.Color(255, 255, 255));
        tblNhaCC.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblNhaCC.setRowHeight(22);
        tblNhaCC.setSelectionBackground(new java.awt.Color(83, 140, 150));
        tblNhaCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhaCCMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNhaCC);

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
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(240, 240, 240)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(134, 134, 134)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblNCC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTenNCC)
                                    .addComponent(txtSDT)
                                    .addComponent(jScrollPane1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblErro, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2)
                            .addComponent(jSeparator1))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblNCC, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenNCC)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSDT)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                        .addGap(49, 49, 49))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblErro, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblNhaCCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhaCCMouseClicked
        txtTenNCC.setBackground(Color.white);
        txtSDT.setBackground(Color.white);
        txtDiaChi.setBackground(Color.white);
        this.tblClick(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_tblNhaCCMouseClicked

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

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        this.timKiem();
    }//GEN-LAST:event_txtTimKiemKeyReleased

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

    private void txtTenNCCFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenNCCFocusGained
//        XSTT.XFocusGained(txtTenNCC, "Nhập tên nhà cung cấp", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtTenNCCFocusGained

    private void txtTenNCCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenNCCFocusLost
//        XSTT.XFocusLost("checkNull", txtTenNCC, "Nhập tên nhà cung cấp", "Nhập thông tin chưa đầy đủ", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtTenNCCFocusLost

    private void txtSDTFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSDTFocusGained
//        XSTT.XFocusGained(txtSDT, "Nhập số điện thoại", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtSDTFocusGained

    private void txtSDTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSDTFocusLost
//        XSTT.XFocusLost("checkPhone", txtSDT, "Nhập số điện thoại", "Phone chưa hợp lệ", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtSDTFocusLost

    private void txtTenNCCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTenNCCMouseClicked
        // TODO add your handling code here:
        txtTenNCC.setBackground(Color.white);
        txtSDT.setBackground(Color.white);
        txtDiaChi.setBackground(Color.white);
    }//GEN-LAST:event_txtTenNCCMouseClicked


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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblErro;
    private javax.swing.JLabel lblNCC;
    private javax.swing.JTable tblNhaCC;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenNCC;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.ui;

import com.shoessys.dao.KhachHangDAO;
import com.shoessys.entity.DonHang;
import com.shoessys.entity.KhachHang;
import com.shoessys.ultil.Auth;
import com.shoessys.ultil.DataValidator;
import com.shoessys.ultil.MsgBox;
import com.shoessys.ultil.XSTT;
import com.shoessys.ultil.Xdate;
import java.awt.Color;
import java.text.MessageFormat;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author trann
 */
public class KhachHangJPanel extends javax.swing.JPanel {

    /**
     * Creates new form KhachHangJPanel
     */
    public KhachHangJPanel() {
        initComponents();
        init();
        sort();
    }

    KhachHangDAO dao = new KhachHangDAO();
    int row = -1;

    private void init() {
        this.row = -1;
        this.fillTable();
        this.updateStatus();
        this.idLblNext();
        txtNgaySinh.setDate(Xdate.now());
    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtTimKiem.getText();
            if (txtTimKiem.getText().equals("Tìm kiếm")) {
                keyword = "";
            }
            List<KhachHang> list = dao.selectBySearch(keyword);
            for (KhachHang kh : list) {
                Object[] row = {
                    kh.getMaKH(),
                    kh.getTenKH(),
                    kh.getSoDT(),
                    kh.getEmail(),
                    kh.isGioiTinh() ? "Nam" : "Nữ",
                    kh.getNgaySinh(),
                    kh.getDiaChi(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    private void edit() {
        Integer maKH = (Integer) tblKhachHang.getValueAt(this.row, 0);
        KhachHang kh = dao.selectById(maKH);
        this.setForm(kh);
        this.updateStatus();
    }

    private KhachHang getFrom() {
        KhachHang kh = new KhachHang();
        kh.setMaKH(!lblMaKH.getText().equals("") ? Integer.parseInt(lblMaKH.getText()) : 0);
        kh.setTenKH(txtTenKH.getText());
        kh.setSoDT(txtSoDT.getText());
        kh.setEmail(txtEmail.getText());
        kh.setGioiTinh(rdoNam.isSelected());
        kh.setNgaySinh(txtNgaySinh.getDate());
        kh.setDiaChi(txtDiaChi.getText());
        return kh;
    }

    private void setForm(KhachHang kh) {
        lblMaKH.setText(String.valueOf(kh.getMaKH()));
        txtTenKH.setText(kh.getTenKH());
        txtEmail.setText(kh.getEmail());
        txtSoDT.setText(kh.getSoDT());
        rdoNam.setSelected(kh.isGioiTinh());
        rdoNu.setSelected(!kh.isGioiTinh());
        txtNgaySinh.setDate(kh.getNgaySinh());
        txtDiaChi.setText(kh.getDiaChi());
    }

    private void insert() {
        KhachHang kh = getFrom();
        try {
            dao.insert(kh);
            this.fillTable();
            this.clearForm();
            MsgBox.alert(this, "Thêm khách hàng thành công");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm khách hàng thất bại!");
        }
    }

    private void update() {
        KhachHang kh = getFrom();
        try {
            dao.update(kh);
            this.fillTable();
            this.clearForm();
            MsgBox.alert(this, "Cập nhật khách hàng thành công");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật khách hàng thất bại!");
        }
    }

    private void delete() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền xóa khách hàng này!");
        } else if (MsgBox.comfirm(this, "Bạn có chắc chắn muốn xóa khách hàng này!")) {
            try {
                Integer makh = Integer.valueOf(tblKhachHang.getValueAt(this.row, 0).toString());
//                String makh = txtMaKH.getText();
                dao.delete(makh);
                this.fillTable();
                this.clearForm();
                MsgBox.alert(this, "Xóa khách hàng thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa khách hàng thất bại!");
            }
        }
    }

    private void clearForm() {
        KhachHang kh = new KhachHang();
        this.setForm(kh);
        this.row = -1;
        this.updateStatus();
        this.idLblNext();
        txtNgaySinh.setDate(Xdate.now());
    }

    private void first() {
        this.row = 0;
        this.edit();
    }

    private void back() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }

    private void next() {
        if (this.row < tblKhachHang.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    private void last() {
        this.row = tblKhachHang.getRowCount() - 1;
        this.edit();
    }

    private void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblKhachHang.getRowCount() - 1);

        // Trạng thái Form
        lblMaKH.setEnabled(!edit);             // Mã là duy nhất, không cho phép sửa
        btnThem.setEnabled(!edit);              // Khi form đang hiện nhân viên thì Thêm bị mờ đi
        btnSua.setEnabled(edit);                // Khi form đang hiện thông tin thì Sửa, Xóa hiện lên
        btnXoa.setEnabled(edit);

        // Trạng thái điều hướng
        btnFirst.setEnabled(edit && !first);    // !first : không phải hàng đầu tiên
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);      // !last : không phải hàng cuối cùng
        btnLast.setEnabled(edit && !last);

        if (!edit) {
            btnThem.setkStartColor(new java.awt.Color(0, 153, 153));

            btnSua.setkStartColor(new java.awt.Color(204, 204, 204));
            btnXoa.setkStartColor(new java.awt.Color(204, 204, 204));

            btnSua.setkHoverStartColor(new java.awt.Color(204, 204, 204));
            btnXoa.setkHoverStartColor(new java.awt.Color(204, 204, 204));
            btnThem.setkHoverStartColor(new java.awt.Color(40, 61, 90));
        } else {
            btnThem.setkStartColor(new java.awt.Color(204, 204, 204));

            btnSua.setkStartColor(new java.awt.Color(0, 153, 153));
            btnXoa.setkStartColor(new java.awt.Color(0, 153, 153));

            btnSua.setkHoverStartColor(new java.awt.Color(40, 61, 90));
            btnXoa.setkHoverStartColor(new java.awt.Color(40, 61, 90));
            btnThem.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        }
        if (edit && !first) {
            btnFirst.setkStartColor(new java.awt.Color(0, 153, 153));
            btnPrev.setkStartColor(new java.awt.Color(0, 153, 153));

            btnFirst.setkHoverStartColor(new java.awt.Color(40, 61, 90));
            btnPrev.setkHoverStartColor(new java.awt.Color(40, 61, 90));
        } else {
            btnFirst.setkStartColor(new java.awt.Color(204, 204, 204));
            btnPrev.setkStartColor(new java.awt.Color(204, 204, 204));

            btnFirst.setkHoverStartColor(new java.awt.Color(204, 204, 204));
            btnPrev.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        }
        if (edit && !last) {
            btnNext.setkStartColor(new java.awt.Color(0, 153, 153));
            btnLast.setkStartColor(new java.awt.Color(0, 153, 153));

            btnNext.setkHoverStartColor(new java.awt.Color(40, 61, 90));
            btnLast.setkHoverStartColor(new java.awt.Color(40, 61, 90));
        } else {
            btnNext.setkStartColor(new java.awt.Color(204, 204, 204));
            btnLast.setkStartColor(new java.awt.Color(204, 204, 204));

            btnNext.setkHoverStartColor(new java.awt.Color(204, 204, 204));
            btnLast.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        }
    }

//    private boolean check(){
//        if(txtTenKH.equals("")){
//            MsgBox.alert(this, "Vui lòng nhập họ tên!");
//            txtTenKH.requestFocus();
//            return false;
//        }
//        if(txtNgaySinh.getDate()==null){
//            MsgBox.alert(this, "Vui lòng chọn ngày đặt!");
//            txtNgaySinh.requestFocus();
//            return false;
//        }
//        if(txtSoDT.equals("")){
//            MsgBox.alert(this, "Vui lòng nhập số điện thoại!");
//            txtSoDT.requestFocus();
//            return false;
//        }
//         if(txtEmail.equals("")){
//            MsgBox.alert(this, "Vui lòng nhập email!");
//            txtEmail.requestFocus();
//            return false;
//        }
//         if(txtTichDiem.equals("")){
//            MsgBox.alert(this, "Vui lòng nhập tích điểm!");
//            txtTichDiem.requestFocus();
//            return false;
//        }
//         if(txtDiaChi.equals("")){
//            MsgBox.alert(this, "Vui lòng nhập địa chỉ!");
//            txtDiaChi.requestFocus();
//            return false;
//        }
//             
//            return true;
//        
//    }
    boolean kiemTra() {
        String Email_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        String Anphabet_REGEX = "\\D+";
        String Phone_REGEX = "^0[35789]{1}\\d{8}$";
        StringBuilder sb = new StringBuilder();
        DataValidator.validateEmpty(txtTenKH, sb, "Tên khách hàng, ");
        DataValidator.validateEmpty(txtEmail, sb, "Email, ");
        DataValidator.validateEmpty(txtSoDT, sb, "Số điện thoại, ");
        DataValidator.validateEmpty(txtDiaChi, sb, "Địa chỉ ");
        if (sb.length() > 0) {
            MsgBox.alert(this, sb.toString() + "\nKhông được để trống!");
            return false;
        } else {
            if (!txtEmail.getText().matches(Email_REGEX)) {
                MsgBox.alert(this, "Email không đúng định dạng!");
                return false;
            }
            if (!txtTenKH.getText().matches(Anphabet_REGEX)) {
                MsgBox.alert(this, "Họ tên chỉ chứa alphabet và ký tự trắng");
                return false;
            }
            if (!txtSoDT.getText().matches(Phone_REGEX)) {
                MsgBox.alert(this, "Số điện thoại không đúng định dạng!");
                return false;
            }
        }
        return true;
    }

    void print(JTable table, String header) {
        MessageFormat Header = new MessageFormat(header);
        MessageFormat Footer = new MessageFormat("Trang (0,number,integer)");
        try {
//            changePrintDialogIcon(Ximage.APP_ICON);
            table.print(JTable.PrintMode.FIT_WIDTH, Header, Footer);
        } catch (Exception e) {
            MsgBox.alert(null, "Lỗi:" + header.toLowerCase() + "!");
        }
    }

    private void sort() {
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblKhachHang.setRowSorter(sorter);
//        model.setRowCount(3);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblMaKH = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSoDT = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnFirst = new keeptoo.KButton();
        btnPrev = new keeptoo.KButton();
        btnNext = new keeptoo.KButton();
        btnLast = new keeptoo.KButton();
        jPanel1 = new javax.swing.JPanel();
        btnThem = new keeptoo.KButton();
        btnSua = new keeptoo.KButton();
        btnXoa = new keeptoo.KButton();
        btnMoi = new keeptoo.KButton();
        lblErro = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(818, 553));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Thông tin_________________________________________________________________________________");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Mã khách hàng:");

        lblMaKH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMaKH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Họ và tên:");

        txtTenKH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtTenKH.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTenKHFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTenKHFocusLost(evt);
            }
        });
        txtTenKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenKHMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Số điện thoại:");

        txtSoDT.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSoDT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSoDTFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSoDTFocusLost(evt);
            }
        });
        txtSoDT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenKHMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Giới tính:");

        rdoNam.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoNam.setText("Nam");

        rdoNu.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoNu.setSelected(true);
        rdoNu.setText("Nữ");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Ngày sinh:");

        txtNgaySinh.setBackground(new java.awt.Color(255, 255, 255));
        txtNgaySinh.setDateFormatString("MM/dd/yyyy");
        txtNgaySinh.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Địa chỉ:");

        txtDiaChi.setColumns(20);
        txtDiaChi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDiaChi.setRows(5);
        txtDiaChi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenKHMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(txtDiaChi);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Danh sách khách hàng:");

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

        tblKhachHang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ KHÁCH HÀNG", "HỌ VÀ TÊN", "SỐ ĐIỆN THOẠI", "EMAIL", "GIỚI TÍNH", "NGÀY SINH", "ĐỊA CHỈ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.setGridColor(new java.awt.Color(255, 255, 255));
        tblKhachHang.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblKhachHang.setRowHeight(22);
        tblKhachHang.setSelectionBackground(new java.awt.Color(83, 140, 150));
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblKhachHang);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Email:");

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailFocusLost(evt);
            }
        });
        txtEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenKHMouseClicked(evt);
            }
        });

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtSoDT, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane1))
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdoNam)
                                .addGap(18, 18, 18)
                                .addComponent(rdoNu))
                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(lblErro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(102, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel6)
                        .addComponent(rdoNam)
                        .addComponent(rdoNu)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblErro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblKhachHang.getSelectedRow();
            txtTenKH.setBackground(Color.white);
            txtDiaChi.setBackground(Color.white);
            txtEmail.setBackground(Color.white);
            txtSoDT.setBackground(Color.white);
            this.edit();
        }
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        this.row = 0;
        this.edit();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (this.row < tblKhachHang.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        this.row = tblKhachHang.getRowCount() - 1;
        this.edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (kiemTra()) {
            this.insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (kiemTra()) {
            this.update();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        this.delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        this.clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        if (txtTimKiem.getText().equals("Tìm kiếm")) {
            txtTimKiem.setText("");
            txtTimKiem.setForeground(new java.awt.Color(0, 0, 0));
        }
    }//GEN-LAST:event_txtTimKiemFocusGained

    private void txtTimKiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusLost
        if (txtTimKiem.getText().equals("")) {
            txtTimKiem.setText("Tìm kiếm");
            txtTimKiem.setForeground(new java.awt.Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtTimKiemFocusLost

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        this.fillTable();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void txtSoDTFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSoDTFocusGained
//        XSTT.XFocusGained(txtSoDT, "Nhập số điện thoại", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtSoDTFocusGained

    private void txtSoDTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSoDTFocusLost
//        XSTT.XFocusLost("checkPhone", txtSoDT, "Nhập số điện thoại", "Phone chưa hợp lệ", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtSoDTFocusLost

    private void txtTenKHFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenKHFocusGained
//        XSTT.XFocusGained(txtTenKH, "Nhập họ và tên", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtTenKHFocusGained

    private void txtTenKHFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenKHFocusLost
//        XSTT.XFocusLost("checkNull", txtTenKH, "Nhập họ và tên", "Nhập thông tin chưa đầy đủ", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtTenKHFocusLost

    private void txtEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusGained
//        XSTT.XFocusGained(txtEmail, "Nhập Email", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtEmailFocusGained

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
//        XSTT.XFocusLost("checkMail", txtEmail, "Nhập Email", "Mail không hợp lệ", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtEmailFocusLost

    private void txtTenKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTenKHMouseClicked
        // TODO add your handling code here:
        txtTenKH.setBackground(Color.white);
        txtDiaChi.setBackground(Color.white);
        txtEmail.setBackground(Color.white);
        txtSoDT.setBackground(Color.white);
    }//GEN-LAST:event_txtTenKHMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private keeptoo.KButton btnFirst;
    private keeptoo.KButton btnLast;
    private keeptoo.KButton btnMoi;
    private keeptoo.KButton btnNext;
    private keeptoo.KButton btnPrev;
    private keeptoo.KButton btnSua;
    private keeptoo.KButton btnThem;
    private keeptoo.KButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblErro;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtSoDT;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
    void idLblNext() {
        lblMaKH.setText(dao.idNext() + "");
    }
}

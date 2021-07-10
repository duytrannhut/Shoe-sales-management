/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.ui;

import com.shoessys.dao.NhanVienDAO;
import com.shoessys.entity.NhanVien;
import com.shoessys.ultil.Auth;
import com.shoessys.ultil.DataValidator;
import com.shoessys.ultil.MsgBox;
import com.shoessys.ultil.Xdate;
import com.shoessys.ultil.Ximage;
import java.awt.Color;
import java.io.File;
import java.text.MessageFormat;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author trann
 */
public class NhanVienPanel extends javax.swing.JPanel {

    private MainJFrame parentForm;
    int row = -1;
    NhanVienDAO dao = new NhanVienDAO();

    /**
     * Creates new form NhanVienJPanel
     */
    public NhanVienPanel() {
        initComponents();
        init();
        sort();
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        if (!Auth.isManager()) {
            model.setColumnIdentifiers(new Object[]{"MÃ NHÂN VIÊN", "HỌ VÀ TÊN", "SỐ ĐIỆN THOẠI", "EMAIL", "VAI TRÒ", "GIỚI TÍNH", "NGÀY SINH"});
        }
        model.setRowCount(0);
        try {
            String keyword = txtTimKiem.getText();
            if (txtTimKiem.getText().equals("Tìm kiếm")) {
                keyword = "";
            }
            List<NhanVien> list = dao.selectByKeyword(keyword);
            Object[] row;
            for (NhanVien nv : list) {
                row = new Object[]{
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getSoDT(),
                    nv.getEmail(),
                    nv.getMatKhau(),
                    nv.isVaiTro() ? "Admin" : "User",
                    nv.isGioiTinh() ? "Nam" : "Nữ",
                    Xdate.toString(nv.getNgaySinh(), "MM/dd/yyyy")
                };
                if (!Auth.isManager()) {
                    row = new Object[]{
                        nv.getMaNV(),
                        nv.getTenNV(),
                        nv.getSoDT(),
                        nv.getEmail(),
                        nv.getMatKhau(),
                        nv.isVaiTro() ? "Admin" : "User",
                        nv.isGioiTinh() ? "Nam" : "Nữ",
                        Xdate.toString(nv.getNgaySinh(), "MM/dd/yyyy")
                    };
                }
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy xuất dữ liệu!");
        }
    }

    void init() {
        this.fillTable();
        this.row = -1;
        this.updateStatus();
        ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/128-no-camera.png"));
        lblHinh.setIcon(icon);
        lblHinh.setToolTipText("128-no-camera.png");
        dcsNgaySinh.setDate(Xdate.now());
    }

    public void clearForm() {
        NhanVien nv = new NhanVien();
        this.setForm(nv);
        this.row = -1;
        this.updateStatus();
        ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/128-no-camera.png"));
        lblHinh.setIcon(icon);
        lblHinh.setToolTipText("128-no-camera.png");
        dcsNgaySinh.setDate(Xdate.now());
    }

    void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblNhanVien.getRowCount() - 1);
        txtMaNV.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);

        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
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

    void setForm(NhanVien nv) {
        txtMaNV.setText(nv.getMaNV());
        txtHoTen.setText(nv.getTenNV());
        txtMatKhau.setText(nv.getMatKhau());
        rdoAdmin.setSelected(nv.isVaiTro());
        rdoUser.setSelected(!nv.isVaiTro());
        txtSoDT.setText(nv.getSoDT());
        txtEmail.setText(nv.getEmail());
        rdoNam.setSelected(nv.isGioiTinh());
        rdoNu.setSelected(!nv.isGioiTinh());
        txtDiaChi.setText(nv.getDiaChi());
        dcsNgaySinh.setDate(nv.getNgaySinh());
        if (nv.getHinh() != null) {
            ImageIcon icon = Ximage.read(nv.getHinh(), 150, 180, "nhanvien");
            lblHinh.setToolTipText(nv.getHinh());
            lblHinh.setIcon(icon);
        }
    }

    NhanVien getForm() {
        NhanVien nv = new NhanVien();
        nv.setMaNV(txtMaNV.getText());
        nv.setTenNV(txtHoTen.getText());
        nv.setMatKhau(txtMatKhau.getText());
        nv.setVaiTro(rdoAdmin.isSelected());
        nv.setEmail(txtEmail.getText());
        nv.setSoDT(txtSoDT.getText());
        nv.setGioiTinh(rdoNam.isSelected());
        nv.setDiaChi(txtDiaChi.getText());
        nv.setNgaySinh(dcsNgaySinh.getDate());
        nv.setHinh(lblHinh.getToolTipText());
        return nv;
    }

    void edit() {
        String manv = (String) tblNhanVien.getValueAt(this.row, 0);
        NhanVien nv = dao.selectById(manv);
        this.setForm(nv);
        this.updateStatus();
    }

    void insert() {
        NhanVien nv = getForm();
        try {
            dao.insert(nv);
            this.fillTable();
            this.clearForm();
            MsgBox.alert(this, "Thêm tài khoản thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm tài khoản thất bại!");
            e.printStackTrace();
        }
    }

    void update() {
        NhanVien nv = getForm();
        try {
            dao.update(nv);
            this.fillTable();
            MsgBox.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật thất bại!!");
        }

    }

    void delete() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền xóa nhân viên!");
        } else {
            String manv = txtMaNV.getText();
            if (manv.equals(Auth.User.getMaNV())) {
                MsgBox.alert(this, "Bạn không thể xóa tài khoản của mình!");
            } else if (MsgBox.comfirm(this, "Bạn có muốn xóa nhân viên này??")) {
                try {
                    dao.delete(manv);
                    this.fillTable();
                    this.clearForm();
                    MsgBox.alert(this, "Xóa Thành Công!!!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa Thất Bại!!!");
                }
            }
        }
    }

    void chonAnh() {
        if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            Ximage.save(file, "nhanvien");
            ImageIcon icon = Ximage.read(file.getName(), 150, 180, "nhanvien");
            lblHinh.setIcon(icon);
            lblHinh.setToolTipText(file.getName());
        }
    }

    boolean kiemTra() {
        String Email_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        String Anphabet_REGEX = "\\D+";
        String Phone_REGEX = "^0[35789]{1}\\d{8}$";
        StringBuilder sb = new StringBuilder();
        DataValidator.validateEmpty(txtMaNV, sb, "Mã Nhân Viên, ");
        DataValidator.validateEmpty(txtMatKhau, sb, "Mật khẩu, ");
        DataValidator.validateEmpty(txtEmail, sb, "Email, ");
        DataValidator.validateEmpty(txtHoTen, sb, "Họ và tên, ");
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
            if (!txtHoTen.getText().matches(Anphabet_REGEX)) {
                MsgBox.alert(this, "Họ tên chỉ chứa alphabet và ký tự trắng");
                return false;
            }
            if (txtMatKhau.getText().length() < 6) {
                MsgBox.alert(this, "Mật khẩu phải có trên 6 kí tự");
                return false;
            }
            if (!txtSoDT.getText().matches(Phone_REGEX)) {
                MsgBox.alert(this, "Số điện thoại không đúng định dạng!");
                return false;
            }
//            if (dcsNgaySinh.getDate().equals("")) {
//                MsgBox.alert(this, "Ngày sinh không được bỏ trống!");
//                return false;
//            }
        }
        return true;
    }

    private void sort() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblNhanVien.setRowSorter(sorter);
//        model.setRowCount(3);
    }

//    static void changePrintDialogIcon(final Image icon){
//        int delay = 10;
//        final int maxCount = 100;
//        final Container callerRoot = FocusManager.getCurrentManager().getCurrentFocusCycleRoot();
//        final Timer timer = new Timer(delay, null);
//    }
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jFileChooser = new javax.swing.JFileChooser();
        lblHinh = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        rdoAdmin = new javax.swing.JRadioButton();
        rdoUser = new javax.swing.JRadioButton();
        txtHoTen = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtSoDT = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        dcsNgaySinh = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        txtTimKiem = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
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

        jFileChooser.setCurrentDirectory(new java.io.File("D:\\hoang\\PRO1041\\PRO1041_ShoesSys_Team01\\image\\nhanvien"));

        setBackground(new java.awt.Color(255, 255, 255));

        lblHinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/128-no-camera.png"))); // NOI18N
        lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(40, 61, 90), 2));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Mật khẩu:");

        txtMatKhau.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMatKhau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMaNVMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Số điện thoại:");

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEmailMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Vai trò:");

        rdoAdmin.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoAdmin);
        rdoAdmin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoAdmin.setText("Admin");

        rdoUser.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoUser);
        rdoUser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoUser.setSelected(true);
        rdoUser.setText("User");

        txtHoTen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtHoTen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMaNVMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Họ và tên:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Mã nhân viên:");

        txtMaNV.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMaNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMaNVMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Email:");

        txtSoDT.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSoDT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMaNVMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Ngày sinh:");

        dcsNgaySinh.setBackground(new java.awt.Color(255, 255, 255));
        dcsNgaySinh.setToolTipText("");
        dcsNgaySinh.setDateFormatString("MM/dd/yyyy");
        dcsNgaySinh.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dcsNgaySinh.setName("sdasdas"); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Giới tính:");

        rdoNam.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        rdoNu.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoNu.setText("Nữ");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Địa chỉ:");

        txtDiaChi.setColumns(20);
        txtDiaChi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        txtDiaChi.setRows(5);
        txtDiaChi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDiaChiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(txtDiaChi);

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
        txtTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTimKiemMouseClicked(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Danh sách nhân viên:");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Thông tin_________________________________________________________________________________");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_search_16px.png"))); // NOI18N

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));

        tblNhanVien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ NHÂN VIÊN", "HỌ VÀ TÊN", "SỐ ĐIỆN THOẠI", "EMAIL", "MẬT KHẨU", "VAI TRÒ", "GIỚI TÍNH", "NGÀY SINH"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.setGridColor(new java.awt.Color(255, 255, 255));
        tblNhanVien.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblNhanVien.setRowHeight(22);
        tblNhanVien.setSelectionBackground(new java.awt.Color(83, 140, 150));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

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
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtSoDT)
                                                .addComponent(txtMaNV)
                                                .addComponent(txtHoTen, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(rdoAdmin)
                                                .addGap(18, 18, 18)
                                                .addComponent(rdoUser)))
                                        .addGap(28, 28, 28)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel7)
                                                .addComponent(jLabel2))
                                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                            .addComponent(txtEmail)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(rdoNam)
                                                .addGap(18, 18, 18)
                                                .addComponent(rdoNu))
                                            .addComponent(dcsNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSoDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8)
                                .addComponent(jLabel3))
                            .addComponent(dcsNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu)
                            .addComponent(jLabel4)
                            .addComponent(rdoAdmin)
                            .addComponent(rdoUser))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTimKiemMouseClicked
//        // TODO add your handling code here:
        txtTimKiem.setText("");
        txtTimKiem.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtTimKiemMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
//        // TODO add your handling code here:
        fillTable();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            txtMaNV.setBackground(Color.white);
            txtDiaChi.setBackground(Color.white);
            txtEmail.setBackground(Color.white);
            txtHoTen.setBackground(Color.white);
            txtMatKhau.setBackground(Color.white);
            txtSoDT.setBackground(Color.white);
            this.row = tblNhanVien.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        // TODO add your handling code here:
        chonAnh();
    }//GEN-LAST:event_lblHinhMouseClicked

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
        if (this.row < tblNhanVien.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        this.row = tblNhanVien.getRowCount() - 1;
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

    private void txtMaNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMaNVMouseClicked
        // TODO add your handling code here:
        txtMaNV.setBackground(Color.white);
        txtDiaChi.setBackground(Color.white);
        txtEmail.setBackground(Color.white);
        txtHoTen.setBackground(Color.white);
        txtMatKhau.setBackground(Color.white);
        txtSoDT.setBackground(Color.white);
    }//GEN-LAST:event_txtMaNVMouseClicked

    private void txtEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEmailMouseClicked
        // TODO add your handling code here:
        txtMaNV.setBackground(Color.white);
        txtDiaChi.setBackground(Color.white);
        txtEmail.setBackground(Color.white);
        txtHoTen.setBackground(Color.white);
        txtMatKhau.setBackground(Color.white);
        txtSoDT.setBackground(Color.white);
    }//GEN-LAST:event_txtEmailMouseClicked

    private void txtDiaChiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDiaChiMouseClicked
        // TODO add your handling code here:
        txtMaNV.setBackground(Color.white);
        txtDiaChi.setBackground(Color.white);
        txtEmail.setBackground(Color.white);
        txtHoTen.setBackground(Color.white);
        txtMatKhau.setBackground(Color.white);
        txtSoDT.setBackground(Color.white);
    }//GEN-LAST:event_txtDiaChiMouseClicked


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
    private javax.swing.ButtonGroup buttonGroup2;
    private com.toedter.calendar.JDateChooser dcsNgaySinh;
    private javax.swing.JFileChooser jFileChooser;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JRadioButton rdoAdmin;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoUser;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtSoDT;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}

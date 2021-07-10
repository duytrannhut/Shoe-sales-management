/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.ui;

import com.shoessys.dao.LoaiDAO;
import com.shoessys.dao.NhaCungCapDAO;
import com.shoessys.dao.SanPhamDAO;
import com.shoessys.entity.Loai;
import com.shoessys.entity.NhaCungCap;
import com.shoessys.entity.SanPham;
import com.shoessys.ultil.Auth;
import com.shoessys.ultil.DataValidator;
import com.shoessys.ultil.MsgBox;
import com.shoessys.ultil.Ximage;
import java.awt.Color;
import java.io.File;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author trann
 */
public class SanPhamJPanel extends javax.swing.JPanel {

    int row = - 1;
    SanPhamDAO dao = new SanPhamDAO();
    LoaiDAO ldaoDao = new LoaiDAO();
    NhaCungCapDAO nccdao = new NhaCungCapDAO();
    Locale vn = new Locale("vi", "VN");
    NumberFormat VND = NumberFormat.getCurrencyInstance(vn);

    /**
     * Creates new form SanPhamJPanel
     */
    public SanPhamJPanel() {
        initComponents();
        sort();
        this.init();
    }

    void init() {
        this.load();
        this.row = -1;
        this.updateStatus();
        load();
        fillComboBoxLoai();
        fillComboBoxNCC();
        this.maLoaiTT();
        ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/128-no-camera.png"));
        lblHinh.setIcon(icon);
        lblHinh.setToolTipText("128-no-camera.png");
    }

    public void maLoaiTT() {
        lblMaSP.setText(dao.idNext() + "");
    }

    private void sort() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblSanPham.setRowSorter(sorter);
//        model.setRowCount(3);
    }

    void load() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtTimKiem.getText();
            if (txtTimKiem.getText().equals("Tìm kiếm")) {
                keyword = "";
            }
            List<SanPham> list = dao.selectByKeyword(keyword);
            for (SanPham sp : list) {
                String dongia = VND.format(sp.getDonGia());
                Object[] row = {
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getSoLuong(),
                    sp.getMaNCC(),
                    sp.getMaLoai(),
                    sp.isGioiTinh() ? "Nam" : "Nữ",
                    sp.getSize(),
                    sp.getChatLieu(),
                    dongia.replace("đ", ""),};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void fillComboBoxNCC() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNCC.getModel();
        model.removeAllElements();
        List<NhaCungCap> list = nccdao.selectALL();
        for (NhaCungCap ncc : list) {
            model.addElement(ncc);
        }
    }

    void fillComboBoxLoai() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboLoai.getModel();
        model.removeAllElements();
        List<Loai> list = ldaoDao.selectALL();
        for (Loai loai : list) {
            model.addElement(loai);
        }
    }

    void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblSanPham.getRowCount() - 1);
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

    void setForm(SanPham sp) {
        lblMaSP.setText(String.valueOf(sp.getMaSP()));
        txtTenSP.setText(sp.getTenSP());
        txtHang.setText(sp.getHang());
        rdoNam.setSelected(sp.isGioiTinh());
        rdoNu.setSelected(!sp.isGioiTinh());
        cboSize.setSelectedItem(String.valueOf(sp.getSize()));
        txtSoLuong.setText(sp.getSoLuong() == 0 ? "" : String.valueOf(sp.getSoLuong()));
        txtMoTa.setText(sp.getMoTa());
        txtDonGia.setText(sp.getDonGia() == 0 ? "" : VND.format(sp.getDonGia()).replace("đ", ""));
        DefaultComboBoxModel modelNCC = (DefaultComboBoxModel) cboNCC.getModel();
        modelNCC.setSelectedItem(nccdao.selectById(sp.getMaNCC()));
        txtChatLieu.setText(sp.getChatLieu());
        DefaultComboBoxModel modelLoai = (DefaultComboBoxModel) cboLoai.getModel();
        modelLoai.setSelectedItem(ldaoDao.selectById(sp.getMaLoai()));
        if (sp.getHinh() != null) {
            ImageIcon icon = Ximage.read(sp.getHinh(), 150, 180, "sanpham");
            lblHinh.setToolTipText(sp.getHinh());
            lblHinh.setIcon(icon);
        }
    }

    SanPham getForm() {
        SanPham sp = new SanPham();
        sp.setMaSP(Integer.parseInt(lblMaSP.getText()));
        sp.setTenSP(txtTenSP.getText());
        sp.setHang(txtHang.getText());
        sp.setGioiTinh(rdoNam.isSelected());
        sp.setSize(Integer.parseInt(cboSize.getSelectedItem().toString()));
        sp.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        sp.setMoTa(txtMoTa.getText());
        sp.setDonGia(Double.parseDouble(txtDonGia.getText().replace(".", "")));
        NhaCungCap ncc = (NhaCungCap) cboNCC.getSelectedItem();
        sp.setMaNCC(ncc.getMaNCC());
        sp.setHinh(lblHinh.getToolTipText());
        sp.setChatLieu(txtChatLieu.getText());
        Loai loai = (Loai) cboLoai.getSelectedItem();
        sp.setMaLoai(loai.getMaLoai());
        return sp;
    }

    void edit() {
        Integer masp = (Integer) tblSanPham.getValueAt(this.row, 0);
        SanPham sp = dao.selectById(masp);
        this.setForm(sp);
        this.updateStatus();
    }

    void insert() {
        SanPham sp = getForm();
        try {
            dao.insert(sp);
            this.load();
            this.clearForm();
            MsgBox.alert(this, " Thêm sản Phẩm  thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm sản phẩm  thất bại!");
        }
    }

    public void clearForm() {
        SanPham sp = new SanPham();
        this.setForm(sp);
        this.row = -1;
        this.updateStatus();
        cboNCC.setSelectedIndex(0);
        cboLoai.setSelectedIndex(0);
        ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/128-no-camera.png"));
        lblHinh.setIcon(icon);
        lblHinh.setToolTipText("128-no-camera.png");
        this.maLoaiTT();
    }

    void update() {
        SanPham sp = getForm();
        try {
            dao.update(sp);
            this.load();
            MsgBox.alert(this, "Cập nhật sản phẩm thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật sản phẩm thất bại!!");
            e.printStackTrace();
        }

    }

    void delete() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền xóa!");
        } else {
            Integer masp = Integer.parseInt(lblMaSP.getText());
            if (MsgBox.comfirm(this, "Bạn có muốn xóa sản phẩm này không?")) {
                try {
                    dao.delete(masp);
                    this.load();
                    this.clearForm();
                    MsgBox.alert(this, "Xóa Thành Công!!!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa Thất Bại: tồn tại sản phẩm bên đơn hàng!!!");
                }
            }
        }
    }

    void loadimage() {
        if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            Ximage.save(file, "sanpham");
            ImageIcon icon = Ximage.read(file.getName(), 150, 180, "sanpham");
            lblHinh.setIcon(icon);
            lblHinh.setToolTipText(file.getName());
        }
    }

    boolean kiemTra() {
        String Email_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        String Anphabet_REGEX = "\\D+";
        String Phone_REGEX = "^0[35789]{1}\\d{8}$";
        String Number_REGEX = "\\d+";

        StringBuilder sb = new StringBuilder();
        DataValidator.validateEmpty(txtTenSP, sb, "Tên sản phẩm, ");
        DataValidator.validateEmpty(txtHang, sb, "Hãng, ");
        DataValidator.validateEmpty(txtSoLuong, sb, "Số lượng, ");
        DataValidator.validateEmpty(txtDonGia, sb, "Đơn giá, ");
        DataValidator.validateEmpty(txtChatLieu, sb, "Chất liệu, ");
        DataValidator.validateEmpty(txtMoTa, sb, "Mô tả ");
        if (sb.length() > 0) {
            MsgBox.alert(this, sb.toString() + "\nKhông được để trống!");
            return false;
        } else {
            if (!txtTenSP.getText().matches(Anphabet_REGEX)) {
                MsgBox.alert(this, "Tên sản phẩm chỉ chứa alphabet và ký tự trắng!");
                return false;
            }
            try {
                Integer.parseInt(txtSoLuong.getText());
            } catch (Exception e) {
                MsgBox.alert(this, "Vui lòng nhập số lượng là số!");
                return false;
            }
            try {
                Double.parseDouble(txtDonGia.getText().replace(".", ""));
            } catch (Exception e) {
                MsgBox.alert(this, "Vui lòng nhập đơn giá là số!");
                return false;
            }
            if (Integer.parseInt(txtSoLuong.getText()) < 0) {
                MsgBox.alert(this, "Số lượng phải nhập số lớn hơn 0!");
                return false;
            }
            if (Double.parseDouble(txtDonGia.getText().replace(".", "")) < 0) {
                MsgBox.alert(this, "Đơn giá phải nhập số lớn hơn 0!");
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jFileChooser = new javax.swing.JFileChooser();
        jLabel12 = new javax.swing.JLabel();
        lblHinh = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblMaSP = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtHang = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cboSize = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtChatLieu = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        cboNCC = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        cboLoai = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
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

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Thông tin_________________________________________________________________________________");

        lblHinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/128-no-camera.png"))); // NOI18N
        lblHinh.setToolTipText("128-no-camera.png");
        lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(40, 61, 90), 2));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Mã sản phẩm:");

        lblMaSP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMaSP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Tên sản phẩm:");

        txtTenSP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtTenSP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTenSPFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTenSPFocusLost(evt);
            }
        });
        txtTenSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenSPMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Hãng:");

        txtHang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtHang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHangFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHangFocusLost(evt);
            }
        });
        txtHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenSPMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Số lượng:");

        txtSoLuong.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSoLuong.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSoLuongFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSoLuongFocusLost(evt);
            }
        });
        txtSoLuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenSPMouseClicked(evt);
                txtSoLuongMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Đơn giá:");

        txtDonGia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDonGia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDonGiaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDonGiaFocusLost(evt);
            }
        });
        txtDonGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenSPMouseClicked(evt);
                txtDonGiaMouseClicked(evt);
            }
        });
        txtDonGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonGiaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Size:");

        cboSize.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cboSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50" }));
        cboSize.setOpaque(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Chất liệu:");

        txtChatLieu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtChatLieu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtChatLieuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtChatLieuFocusLost(evt);
            }
        });
        txtChatLieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenSPMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Giới tính:");

        rdoNam.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoNam.setText("Nam");

        rdoNu.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoNu.setSelected(true);
        rdoNu.setText("Nữ");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Tên NCC:");

        cboNCC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cboNCC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNCC.setOpaque(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Tên loại:");

        cboLoai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cboLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLoai.setOpaque(false);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Mô tả:");

        txtMoTa.setColumns(20);
        txtMoTa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMoTa.setRows(5);
        txtMoTa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenSPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(txtMoTa);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Danh sách sản phẩm:");

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

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/shoessys/icon/icons8_search_16px.png"))); // NOI18N

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));

        tblSanPham.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ SẢN PHẨM", "TÊN SẢN PHẨM", "SỐ LƯỢNG", "NHÀ CUNG CẤP", "LOẠI", "GIỚI TÍNH", "SIZE", "CHẤT LIỆU", "ĐƠN GIÁ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.setGridColor(new java.awt.Color(255, 255, 255));
        tblSanPham.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblSanPham.setRowHeight(22);
        tblSanPham.setSelectionBackground(new java.awt.Color(83, 140, 150));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPham);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lblMaSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtHang)
                                            .addComponent(txtDonGia)
                                            .addComponent(txtChatLieu)
                                            .addComponent(cboNCC, 0, 200, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTenSP)
                                    .addComponent(txtSoLuong)
                                    .addComponent(cboSize, 0, 200, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rdoNam)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdoNu))
                                    .addComponent(cboLoai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblErro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addComponent(jScrollPane2)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblMaSP, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(txtChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(cboNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13)
                                    .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(rdoNam)
                                    .addComponent(rdoNu))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblErro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

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
        if (this.row < tblSanPham.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        this.row = tblSanPham.getRowCount() - 1;
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

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblSanPham.getSelectedRow();
            txtChatLieu.setBackground(Color.white);
            txtDonGia.setBackground(Color.white);
            txtHang.setBackground(Color.white);
            txtMoTa.setBackground(Color.white);
            txtSoLuong.setBackground(Color.white);
            txtTenSP.setBackground(Color.white);
            this.edit();
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        this.loadimage();
    }//GEN-LAST:event_lblHinhMouseClicked

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
        this.load();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void txtHangFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHangFocusGained
//        XSTT.XFocusGained(txtHang, "Nhập hãng", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtHangFocusGained

    private void txtHangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHangFocusLost
//        XSTT.XFocusLost("checkNull", txtHang, "Nhập hãng", "Nhập thông tin chưa đầy đủ", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtHangFocusLost

    private void txtDonGiaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDonGiaFocusGained
//        XSTT.XFocusGained(txtDonGia, "Nhập đơn giá", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtDonGiaFocusGained

    private void txtDonGiaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDonGiaFocusLost
//        XSTT.XFocusLost("checkNull", txtDonGia, "Nhập đơn giá", "Nhập thông tin chưa đầy đủ", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtDonGiaFocusLost

    private void txtChatLieuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtChatLieuFocusGained
//        XSTT.XFocusGained(txtChatLieu, "Nhập chất liệu", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtChatLieuFocusGained

    private void txtChatLieuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtChatLieuFocusLost
//        XSTT.XFocusLost("checkNull", txtChatLieu, "Nhập chất liệu", "Nhập thông tin chưa đầy đủ", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtChatLieuFocusLost

    private void txtTenSPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenSPFocusGained
//        XSTT.XFocusGained(txtTenSP, "Nhập tên sản phẩm", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtTenSPFocusGained

    private void txtTenSPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenSPFocusLost
//        XSTT.XFocusLost("checkNull", txtTenSP, "Nhập tên sản phẩm", "Nhập thông tin chưa đầy đủ", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtTenSPFocusLost

    private void txtSoLuongFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSoLuongFocusGained
//        XSTT.XFocusGained(txtSoLuong, "Nhập số lượng", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongFocusGained

    private void txtSoLuongFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSoLuongFocusLost
//        XSTT.XFocusLost("checkNull", txtSoLuong, "Nhập số lượng", "Nhập thông tin chưa đầy đủ", lblErro);// TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongFocusLost

    private void txtDonGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonGiaActionPerformed

    private void txtTenSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTenSPMouseClicked
        // TODO add your handling code here:
        txtChatLieu.setBackground(Color.white);
        txtDonGia.setBackground(Color.white);
        txtHang.setBackground(Color.white);
        txtMoTa.setBackground(Color.white);
        txtSoLuong.setBackground(Color.white);
        txtTenSP.setBackground(Color.white);
    }//GEN-LAST:event_txtTenSPMouseClicked

    private void txtDonGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDonGiaMouseClicked
        // TODO add your handling code here:
        txtDonGia.setText("");
    }//GEN-LAST:event_txtDonGiaMouseClicked

    private void txtSoLuongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSoLuongMouseClicked
        // TODO add your handling code here:
        txtSoLuong.setText("");
    }//GEN-LAST:event_txtSoLuongMouseClicked


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
    private javax.swing.JComboBox<String> cboLoai;
    private javax.swing.JComboBox<String> cboNCC;
    private javax.swing.JComboBox<String> cboSize;
    private javax.swing.JFileChooser jFileChooser;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblErro;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblMaSP;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtChatLieu;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtHang;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}

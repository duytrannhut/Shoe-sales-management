/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.dao;

import com.shoessys.entity.NhanVien;
import com.shoessys.ultil.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author trann
 */
public class NhanVienDAO extends SmartBeesDAO<NhanVien, String>{
    String INSERT_SQL = "INSERT INTO NhanVien (MaNV, MatKhau, TenNV, SoDT, Email, "
            + "GioiTinh, NgaySinh, VaiTro, DiaChi, Hinh) VALUES(?,?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE NhanVien SET MatKhau=?, TenNV=?, SoDT=?, Email=?, "
            + "GioiTinh=?, NgaySinh=?, VaiTro=?, DiaChi=?, Hinh=? WHERE MaNV=?";
    String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNV=?";
    String SELECT_ALL_SQL= "SELECT * FROM NhanVien";
    String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNV=?";
    String SELECT_BY_KEYWORD = "SELECT * FROM NhanVien WHERE tenNV LIKE ? or sodt like ?";
    String SELECT_EMAIL_BY_ID = "select email  from nhanvien where manv=?";
    String UPDATE_PASSWORD = "Update NhanVien set matkhau=? where MaNV=?";
    
    @Override
    public void insert(NhanVien entity) {
        Xjdbc.update(INSERT_SQL, 
                entity.getMaNV(), entity.getMatKhau(), entity.getTenNV(), 
                entity.getSoDT(), entity.getEmail(), entity.isGioiTinh(), 
                entity.getNgaySinh(), entity.isVaiTro(), entity.getDiaChi(), 
                entity.getHinh());
    }

    @Override
    public void update(NhanVien entity) {
        Xjdbc.update(UPDATE_SQL, 
                entity.getMatKhau(), entity.getTenNV(), entity.getSoDT(), 
                entity.getEmail(), entity.isGioiTinh(), entity.getNgaySinh(), 
                entity.isVaiTro(), entity.getDiaChi(), entity.getHinh(), 
                entity.getMaNV());
    }

    @Override
    public void delete(String key) {
        Xjdbc.update(DELETE_SQL, key);
    }

    @Override
    public List<NhanVien> selectALL() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien selectById(String key) {
        List<NhanVien> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
    
    public void updatePW(NhanVien model){
        Xjdbc.update(UPDATE_PASSWORD,
                model.getMatKhau(),
                model.getMaNV()
        );
    }
    
    public NhanVien findGmailbyID(String manv){
        List<NhanVien> list = selectBySql(SELECT_EMAIL_BY_ID, manv);
        return  list.size() > 0 ? list.get(0) : null;
    }
    
    public List<NhanVien> selectByKeyword(String keyword){
        return this.selectBySql(SELECT_BY_KEYWORD, "%"+keyword+"%", "%"+keyword+"%");
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while(rs.next()){
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setTenNV(rs.getString("TenNV"));
                entity.setSoDT(rs.getString("SoDT"));
                entity.setEmail(rs.getString("Email"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                entity.setDiaChi(rs.getString("DiaChi"));
                entity.setHinh(rs.getString("Hinh"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.dao;

import com.shoessys.entity.SanPham;
import com.shoessys.ultil.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author trann
 */
public class SanPhamDAO extends SmartBeesDAO<SanPham, Integer>{
    String INSERT_SQL = "INSERT INTO SanPham (TenSP, Hang, GioiTinh, Size, SoLuong, "
            + "MoTa, DonGia, MaNCC, Hinh, ChatLieu, MaLoai) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE SanPham SET TenSP=?, Hang=?, GioiTinh=?, Size=?,"
            + " SoLuong=?, MoTa=?, DonGia=?, MaNCC=?, Hinh=?, ChatLieu=?, MaLoai=? WHERE MaSP=?";
    String DELETE_SQL = "DELETE FROM SanPham WHERE MaSP=?";
    String SELECT_ALL_SQL= "SELECT * FROM SanPham";
    String SELECT_BY_ID_SQL = "SELECT * FROM SanPham WHERE MaSP=?";
    @Override
    public void insert(SanPham entity) {
        Xjdbc.update(INSERT_SQL, entity.getTenSP(), entity.getHang(), 
               entity.isGioiTinh(), entity.getSize(), entity.getSoLuong(), 
               entity.getMoTa(), entity.getDonGia(), entity.getMaNCC(), 
               entity.getHinh(), entity.getChatLieu(), entity.getMaLoai());
    }

    @Override
    public void update(SanPham entity) {
        Xjdbc.update(UPDATE_SQL, entity.getTenSP(), entity.getHang(), 
                entity.isGioiTinh(), entity.getSize(), entity.getSoLuong(),
                entity.getMoTa(), entity.getDonGia(), entity.getMaNCC(), 
                entity.getHinh(), entity.getChatLieu(), entity.getMaLoai(), 
                entity.getMaSP());
    }

    @Override
    public void delete(Integer key) {
        Xjdbc.update(DELETE_SQL, key);
    }

    @Override
    public List<SanPham> selectALL() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public SanPham selectById(Integer key) {
        List<SanPham> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if(list.isEmpty()){
           return null;
        }
        return list.get(0);
    }

    @Override
    protected List<SanPham> selectBySql(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while(rs.next()){
                SanPham entity = new SanPham();
                entity.setMaSP(rs.getInt("MaSP"));
                entity.setTenSP(rs.getString("TenSP"));
                entity.setHang(rs.getString("Hang"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setSize(rs.getInt("Size"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setMoTa(rs.getString("MoTa"));
                entity.setDonGia(rs.getDouble("DonGia"));
                entity.setMaNCC(rs.getInt("MaNCC"));
                entity.setHinh(rs.getString("Hinh"));
                entity.setChatLieu(rs.getString("ChatLieu"));
                entity.setMaLoai(rs.getInt("MaLoai"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<SanPham> selectSearch(String tensp, String mancc, String maloai,
            String gt){
        String sql = "SELECT * FROM SanPham WHERE TenSP LIKE ? AND "
                + "MaNCC LIKE ? AND MaLoai LIKE ? AND GioiTinh LIKE ?";
        return this.selectBySql(sql, "%"+tensp+"%", "%"+mancc+"%",
                "%"+maloai+"%", "%"+gt+"%");
    }
    
    public List<SanPham> selectByKeyword(String keyword){
        String sql = "SELECT * FROM SanPham WHERE TenSP LIKE ?";
        return this.selectBySql(sql, "%"+keyword+"%");
    }
     
    public int idNext(){
        int idnext = 0;
        String sql = "SELECT IDENT_CURRENT('SanPham')+IDENT_INCR('SanPham')";
        try {
            ResultSet rs = Xjdbc.query(sql);
            while(rs.next()){
                idnext = rs.getInt(1);
            }
            rs.getStatement().getConnection().close();
            return idnext;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.dao;

import com.shoessys.entity.KhachHang;
import com.shoessys.ultil.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author trann
 */
public class KhachHangDAO extends SmartBeesDAO<KhachHang, Integer>{
    String INSERT_SQL = "INSERT INTO KhachHang (TenKH, SoDT, Email, GioiTinh, "
            + "NgaySinh, DiaChi) VALUES(?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE KhachHang SET TenKH=?, SoDT=?, Email=?, "
            + "GioiTinh=?, NgaySinh=?, DiaChi=? WHERE MaKH=?";
    String DELETE_SQL = "DELETE FROM KhachHang WHERE MaKH=?";
    String SELECT_ALL_SQL= "SELECT * FROM KhachHang";
    String SELECT_BY_ID_SQL = "SELECT * FROM KhachHang WHERE MaKH=?";
    @Override
    public void insert(KhachHang entity) {
        Xjdbc.update(INSERT_SQL, entity.getTenKH(), entity.getSoDT(), 
                entity.getEmail(), entity.isGioiTinh(), entity.getNgaySinh(), 
                entity.getDiaChi());
    }

     
    @Override
    public void update(KhachHang entity) {
        Xjdbc.update(UPDATE_SQL, entity.getTenKH(), entity.getSoDT(), 
                entity.getEmail(), entity.isGioiTinh(), entity.getNgaySinh(), 
                entity.getDiaChi(), entity.getMaKH());
    }

    @Override
    public void delete(Integer key) {
        Xjdbc.update(DELETE_SQL, key);
    }

    @Override
    public List<KhachHang> selectALL() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KhachHang selectById(Integer key) {
        List<KhachHang> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while(rs.next()){
                KhachHang entity = new KhachHang();
                entity.setMaKH(rs.getInt("MaKH"));
                entity.setTenKH(rs.getString("TenKH"));
                entity.setSoDT(rs.getString("SoDT"));
                entity.setEmail(rs.getString("Email"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setDiaChi(rs.getString("DiaChi"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<KhachHang> selectBySearch(String search){
        String sql = "SELECT * FROM KhachHang WHERE MaKH LIKE ? OR TenKH LIKE ? "
                + "OR SoDT LIKE ?";
        return this.selectBySql(sql, "%"+search+"%", "%"+search+"%", "%"+search+"%");
    }
    
    public int idNext(){
        int idnext = 0;
        String sql = "SELECT IDENT_CURRENT('KhachHang')+IDENT_INCR('KhachHang')";
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

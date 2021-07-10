/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.dao;

import com.shoessys.entity.DonHang;
import com.shoessys.ultil.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author trann
 */
public class DonHangDAO extends SmartBeesDAO<DonHang, Integer>{
    String INSERT_SQL = "INSERT INTO DonHang (NgayDatHang, MaNV, MaKH) VALUES(?,?,?)";
    String UPDATE_SQL = "UPDATE DonHang SET NgayDatHang=?, MaNV=?, MaKH=? WHERE MaDH=?";
    String DELETE_SQL = "DELETE FROM DonHang WHERE MaDH=?";
    String SELECT_ALL_SQL= "SELECT * FROM DonHang";
    String SELECT_BY_ID_SQL = "SELECT * FROM DonHang WHERE MaDH=?";
    @Override
    public void insert(DonHang entity) {
        Xjdbc.update(INSERT_SQL, entity.getNgayDatHang(), 
                entity.getMaNV(), entity.getMaKH());
    }

    @Override
    public void update(DonHang entity) {
        Xjdbc.update(UPDATE_SQL, entity.getNgayDatHang(), 
                entity.getMaNV(), entity.getMaKH(), entity.getMaDH());
    }

    @Override
    public void delete(Integer key) {
        Xjdbc.update(DELETE_SQL, key);
    }

    @Override
    public List<DonHang> selectALL() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public DonHang selectById(Integer key) {
        List<DonHang> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<DonHang> selectBySql(String sql, Object... args) {
        List<DonHang> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while(rs.next()){
                DonHang entity = new DonHang();
                entity.setMaDH(rs.getInt("MaDH"));
                entity.setNgayDatHang(rs.getDate("NgayDatHang"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMaKH(rs.getInt("MaKH"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<DonHang> selectByKeyword(String keyword){
        String sql = "SELECT * FROM DonHang WHERE MaDH LIKE ? ORDER BY MaDH DESC";
        return this.selectBySql(sql, "%"+keyword+"%");
    }
    
    public int idNext(){
        int idnext = 0;
        String sql = "SELECT IDENT_CURRENT('DonHang')+IDENT_INCR('DonHang')";
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

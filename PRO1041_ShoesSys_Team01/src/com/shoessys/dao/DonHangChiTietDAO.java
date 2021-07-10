/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.dao;

import com.shoessys.entity.DonHangChiTiet;
import com.shoessys.ultil.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author trann
 */
public class DonHangChiTietDAO extends SmartBeesDAO<DonHangChiTiet, Integer>{
    String INSERT_SQL = "INSERT INTO DonHangChiTiet (MaDH, MaSP, DonGia, SoLuong) VALUES(?,?,?,?)";
    String UPDATE_SQL = "UPDATE DonHangChiTiet SET SoLuong=? WHERE MaDH=? AND MaSP=?";
    String DELETE_SQL = "DELETE FROM DonHangChiTiet WHERE MaDH=? AND MaSP=?";
    String SELECT_ALL_SQL= "SELECT * FROM DonHangChiTiet";
    String SELECT_BY_ID_SQL = "SELECT * FROM DonHangChiTiet WHERE MaSP=?";
    @Override
    public void insert(DonHangChiTiet entity) {
        Xjdbc.update(INSERT_SQL, entity.getMaDH(), entity.getMaSP(), 
                entity.getDonGia(), entity.getSoLuong());
    }

    @Override
    public void update(DonHangChiTiet entity) {
        Xjdbc.update(UPDATE_SQL, entity.getSoLuong(), entity.getMaDH(), entity.getMaSP());
    }

    @Override
    public void delete(Integer key) {
        Xjdbc.update(DELETE_SQL, key);
    }

    @Override
    public List<DonHangChiTiet> selectALL() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public DonHangChiTiet selectById(Integer key) {
        List<DonHangChiTiet> list = this.selectBySql(SELECT_BY_ID_SQL,key);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<DonHangChiTiet> selectBySql(String sql, Object... args) {
        List<DonHangChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while(rs.next()){
                DonHangChiTiet entity = new DonHangChiTiet();
                entity.setMaDH(rs.getInt("MaDH"));
                entity.setMaSP(rs.getInt("MaSP"));
                entity.setDonGia(rs.getDouble("DonGia"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<DonHangChiTiet> selectByG(int madh){
        String sql = "SELECT * FROM DonHangChiTiet WHERE MaDH = ?";
        return this.selectBySql(sql, madh);
    }
    public DonHangChiTiet selectByMahdAndMasp(int madh, int masp){
        String sql = "SELECT * FROM DonHangChiTiet WHERE MaDH = ? AND MaSP=?";
        List<DonHangChiTiet> list = this.selectBySql(sql,madh, masp);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
    
    public void delete(Integer madh, Integer masp) {
        Xjdbc.update(DELETE_SQL, madh, masp);
    }
    
    public DonHangChiTiet selectById(Integer madh, Integer masp) {
        String sql = "SELECT * FROM DonHangChiTiet WHERE MaDH=? AND MaSP=?";
        List<DonHangChiTiet> list = this.selectBySql(sql, madh, masp);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
}

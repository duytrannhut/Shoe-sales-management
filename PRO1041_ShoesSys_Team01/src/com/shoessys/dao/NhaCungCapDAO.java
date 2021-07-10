/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.dao;

import com.shoessys.entity.NhaCungCap;
import com.shoessys.ultil.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author khanh
 */

public class NhaCungCapDAO extends SmartBeesDAO<NhaCungCap, Integer>{
    String INSERT_SQL = "INSERT INTO NhaCungCap(tenNCC, soDT, diaChi) VALUES(?,?,?)";
    String UPDATE_SQL = "UPDATE NhaCungCap SET tenNCC = ?, soDT = ?, diaChi = ? WHERE maNCC = ?";
    String DELETE_SQL = "DELETE FROM NhaCungCap WHERE maNCC = ?";
    String SELECT_ALL_SQL = "SELECT * FROM NhaCungCap";
    String SELECT_BY_ID_SQL = "SELECT * FROM NhaCungCap WHERE maNCC = ?";
    @Override
    public void insert(NhaCungCap en) {
        try {
            Xjdbc.update(INSERT_SQL, en.getTenNCC(), en.getSoDT(), en.getDiaChi());
        } catch (Exception e) {
            System.out.println("ERRO: "+e+"\nat NhaCungCapDao");
        }
    }

    @Override
    public void update(NhaCungCap en) {
        try {
            Xjdbc.update(UPDATE_SQL, en.getTenNCC(), en.getSoDT(), en.getDiaChi(), en.getMaNCC());
        } catch (Exception e) {
            System.out.println("ERRO: "+e+"\nat NhaCungCapDao");
        }
    }

    @Override
    public void delete(Integer key) {
        try {
            Xjdbc.update(DELETE_SQL, key );
        } catch (Exception e) {
            System.out.println("ERRO: "+e+" at NhaCungCapDao");
        }
    }

    @Override
    public NhaCungCap selectById(Integer key) {
        List<NhaCungCap> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhaCungCap> selectALL() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<NhaCungCap> selectBySql(String sql, Object... args) {
        List<NhaCungCap> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {                
                NhaCungCap ncc = new NhaCungCap(
                        rs.getInt("MaNCC"),
                        rs.getString("TenNCC"),
                        rs.getString("SoDT"),
                        rs.getString("DiaChi")
                );
                list.add(ncc);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<NhaCungCap> selecbyKeyword(String str) {
        String sql = "Select * from NhaCungCap where tenNCC like ? or soDT like ? or diaChi like ? or MaNCC like ?";
        return this.selectBySql(sql, "%"+str+"%", "%"+str+"%", "%"+str+"%", "%"+str+"%");
    }
    
    public int idNext(){
        int idnext = 0;
        String sql = "SELECT IDENT_CURRENT('NhaCungCap')+IDENT_INCR('NhaCungCap')";
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

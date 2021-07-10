/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.dao;

import com.shoessys.entity.Loai;
import com.shoessys.ultil.Xjdbc;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author khanh
 */
public class LoaiDAO extends SmartBeesDAO<Loai, Integer>{
    String INSERT_SQL = "INSERT INTO Loai (tenLoai) VALUES(?)";
    String UPDATE_SQL = "UPDATE Loai SET  tenLoai = ? WHERE maLoai = ?";
    String DELETE_SQL = "DELETE FROM Loai WHERE maLoai = ?";
    String SELECT_ALL_SQL = "SELECT * FROM Loai";
    String SELECT_BY_ID_SQL = "SELECT * FROM Loai WHERE maLoai = ?";

    @Override
    public void insert(Loai en) {
        try {
            Xjdbc.update(INSERT_SQL, en.getTenLoai());
        } catch (Exception e) {
            System.out.println("ERRO: "+e+"\nat LoaiDAO");
        }
    }

    @Override
    public void update(Loai en) {
        try {
            Xjdbc.update(UPDATE_SQL, en.getTenLoai(), en.getMaLoai());
        } catch (Exception e) {
            System.out.println("ERRO: "+e+"\nat LoaiDAO");
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            Xjdbc.update(DELETE_SQL, id );
        } catch (Exception e) {
            System.out.println("ERRO: "+e+" at LoaiDAO");
        }
    }

    @Override
    public Loai selectById(Integer id) {
        List<Loai> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Loai> selectALL() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public List<Loai> selectBySql(String sql, Object... args) {
        List<Loai> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {                
                Loai l = new Loai(
                        rs.getInt("MaLoai"),
                        rs.getString("TenLoai")
                );
                list.add(l);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }}

    public List<Loai> selecbyKeyword(String str) {
        String sql = "Select * from Loai where tenLoai like ? or Maloai like ?";
        return this.selectBySql(sql, "%"+str+"%", "%"+str+"%");
    }
    
     public int idNext(){
        int idnext = 0;
        String sql = "SELECT IDENT_CURRENT('Loai')+IDENT_INCR('Loai')";
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

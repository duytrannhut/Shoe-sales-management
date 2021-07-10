/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.dao;

import com.shoessys.ultil.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author trann
 */
public class ThongKeDAO {
    private List<Object[]> getListOfArray(String sql, String[] cols, Object...args){
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = Xjdbc.query(sql, args);
            while(rs.next()){
                Object[] vals = new Object[cols.length];
                for(int i=0;i<cols.length;i++){
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Object[]> getSanPhamNam(Date nambd, Date namkt){
        String sql = "{CALL sp_SanPhamNam(?,?)}";
        String[] cols = {"Nam","SoLuong"};
        return this.getListOfArray(sql, cols, nambd, namkt);
    }
    public List<Object[]> getSanPhamThang(Date nambd, Date namkt){
        String sql = "{CALL sp_SanPhamThang(?,?)}";
        String[] cols = {"TenSP","SoLuong"};
        return this.getListOfArray(sql, cols, nambd, namkt);
    }
    
    public List<Object[]> getDonHangNam(Date nambd, Date namkt){
        String sql = "{CALL sp_DonHangNam(?,?)}";
        String[] cols = {"Nam","SoLuong"};
        return this.getListOfArray(sql, cols, nambd, namkt);
    }
    public List<Object[]> getDonHangThang(Date nambd, Date namkt){
        String sql = "{CALL sp_DonHangThang(?,?)}";
        String[] cols = {"Nam","Thang","SoLuong"};
        return this.getListOfArray(sql, cols, nambd, namkt);
    }
    
    public List<Object[]> getDoanhThuNam(Date nambd, Date namkt){
        String sql = "{CALL sp_DoanhThuNam(?,?)}";
        String[] cols = {"Nam","DoanhThu"};
        return this.getListOfArray(sql, cols, nambd, namkt);
    }
    public List<Object[]> getDoanhThuThang(Date nambd, Date namkt){
        String sql = "{CALL sp_DoanhThuThang(?,?)}";
        String[] cols = {"Nam","Thang","DoanhThu"};
        return this.getListOfArray(sql, cols, nambd, namkt);
    }
}

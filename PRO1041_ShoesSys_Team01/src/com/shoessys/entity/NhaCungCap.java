/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.entity;

/**
 *
 * @author ASUS
 * CREATE TABLE NhaCungCap
(
	MaNCC INT IDENTITY(1,1) NOT NULL,
	TenNCC NVARCHAR(50) NOT NULL,
	SoDT NVARCHAR(15) NOT NULL,
	DiaChi NVARCHAR(200) NOT NULL,
	PRIMARY KEY(MaNCC)
)
 */
public class NhaCungCap {
    private int maNCC;
    private String tenNCC;
    private String soDT;
    private String diaChi;

    public NhaCungCap() {
        
    }

    @Override
    public String toString() {
        return tenNCC;
    }

    public NhaCungCap(int maNCC, String tenNCC, String soDT, String diaChi) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.soDT = soDT;
        this.diaChi = diaChi;
    }

    
    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    
}

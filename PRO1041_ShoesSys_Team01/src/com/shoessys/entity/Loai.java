/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.entity;

/**
 *
 * @author ASUS
 * CREATE TABLE Loai
(
	MaLoai INT IDENTITY(1,1) NOT NULL,
	TenLoai NVARCHAR(50) NOT NULL,
	PRIMARY KEY(MaLoai)
)
 */
public class Loai {
    private int maLoai;
    private String tenLoai;

    public Loai() {
        
    }

    @Override
    public String toString() {
        return tenLoai;
    }

    public Loai(int maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.ultil;

import com.shoessys.entity.NhanVien;

/**
 *
 * @author trann
 */
public class Auth {
    public static NhanVien User = null;
    
    public static void clear(){
        Auth.User = null;
    }
    
    public static boolean isLogin(){
        return Auth.User != null;
    }
    
    public static boolean isManager(){
        return Auth.isLogin() && User.isVaiTro();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.ultil;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author khanh
 */
// info: hiển thị mẫu ẩn, cần điền vào txt
//infoErro: Hiển thị lỗi.
//lblErro: nơi hiển thị Lỗi
//typeCheck: Chọn mục check?(checkNull/ checkPhone/ checkMail)
//txt Dùng để thây đổi thuộc tính của JTextFiel
public class XSTT {
    public static void reFom(JTextField txt, String info, String infoErro, JLabel lblErro){
        txt.setText(info);
        txt.setForeground(new Color(153,153,153));
        lblErro.setText(infoErro);
        lblErro.setForeground(Color.red);
    }

    public static void XFocusLost(String typeCheck,JTextField txt, String info, String infoErro, JLabel lblErro){
        switch(typeCheck){
            case "checkNull": 
                if (checkNull(txt.getText())) {
                    reFom(txt, info, infoErro, lblErro);
                }
                break;
            case "checkPhone":
                if (!checkPhone(txt.getText())) {
                    reFom(txt, info, infoErro, lblErro);
                }
                break;
            case "checkMail":
                if (!checkMail(txt.getText())) {
                    reFom(txt, info, infoErro, lblErro);
                }
                break;
        }
    }
    
    public static void XFocusGained(JTextField txt, String info, JLabel lblErro){
        if(txt.getText().equals(info)) {
            txt.setText("");
            txt.setForeground(new Color(0,0,0));
            lblErro.setText("");
        }
    }
    
    public static boolean checkNull(String str){
       return str.equals(""); 
    }
    
    public static boolean checkPhone(String str){
        String phonePattern = "0\\d{9,10}";
        return str.matches(phonePattern);
    }
    
    public static boolean checkMail(String str){
        String mailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$";
        return str.matches(mailPattern);
    }
    
}

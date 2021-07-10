/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.ultil;

import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Hoang Nguyen
 */
public class DataValidator {
    public static void validateEmpty(JTextField field, StringBuilder sb, String errorMessage){
        if(field.getText().equals("")){
            sb.append(errorMessage).append("");
//            field.setText("mời bạn nhập thông tin");
//            field.setForeground(Color.RED);
            field.setBackground(new Color(204,204,204));
            field.requestFocus();
        }else{
            field.setBackground(Color.white);
        }
    }
    
    
    public static void validateEmpty(JPasswordField field, StringBuilder sb, String errorMessage){
        String password = new String(field.getPassword());
        if(password.equals("")){
            sb.append(errorMessage).append("");
//            field.setText("mời bạn nhập thông tin");
//            field.setForeground(Color.RED);
            field.setBackground(new Color(204,204,204));
            field.setEchoChar((char)0);
            field.requestFocus();
        }else{
            field.setBackground(Color.white);
        }
    }
    public static void validateEmpty(JTextArea field, StringBuilder sb, String errorMessage){
        if(field.getText().equals("")){
            sb.append(errorMessage).append("");
//            field.setText("mời bạn nhập thông tin");
//            field.setForeground(Color.red);
            field.setBackground(new Color(204,204,204));
            field.requestFocus();
        }else{
            field.setBackground(Color.white);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.ultil;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author trann
 */
public class MsgBox {
    public static void alert(Component parent, String message){
        JOptionPane.showMessageDialog(parent, message, 
                "SmartBees", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static boolean comfirm(Component parent, String message){
        int result = JOptionPane.showConfirmDialog(parent, message, 
                "SmartBees", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }
    
    public static String prompt(Component parent, String message){
        return JOptionPane.showInputDialog(parent, message, 
                "SmartBees", JOptionPane.INFORMATION_MESSAGE);
    }
}

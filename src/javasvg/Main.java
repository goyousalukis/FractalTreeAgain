/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasvg;

import javax.swing.UIManager;

/**
 *
 * @author justinAhrens
 */


public class Main {




    public static void main(String[] args) {
    
        try { 
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); 
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        }
        
        JavaFrame frame = new JavaFrame();

        frame.setVisible(true);
        

        
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferry;

import java.awt.TextArea;
import javax.swing.JFrame;

/**
 *
 * @author jflopez
 */
public class Window {
    JFrame frame = new JFrame();
    
    TextArea texto = new TextArea();
    
    Window(String nom) {
        frame.setName(nom);
        frame.setTitle(nom);
        frame.setSize(400, 450);
        frame.setVisible(true);
        frame.add(texto);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void write (String cad) {
        texto.setText(texto.getText()+cad+"\n");
    }
    
}

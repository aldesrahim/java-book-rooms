/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

/**
 *
 * @author aldes
 */
public class NumberInputField extends JTextField {

    public NumberInputField() {
        init();
    }

    public NumberInputField(Integer value) {
        setText(value.toString());
        init();
    }

    private void init() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                char c = ke.getKeyChar();

                if (!Character.isDigit(c)) {
                    ke.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                //
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                //
            }
        });
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JButton;

/**
 *
 * @author aldes
 */
public class Button extends JButton {

    public Button() {
        putClientProperty(FlatClientProperties.STYLE, ""
            + "borderWidth:0;"
            + "focusWidth:0;"
            + "arc:10;"
            + "margin:5,11,5,11;");
    }
}

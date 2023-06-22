/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components.table.cell;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author aldes
 */
public class ActionButton extends JButton {

    public ActionButton(String iconName) {

        putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "arc:999;"
                + "margin:5,11,5,11;");

        setIcon(new ImageIcon(getClass().getResource("/resource/image/table/cell/" + iconName + ".png")));
    }
}

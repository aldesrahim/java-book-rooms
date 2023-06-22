/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author aldes
 */
public class CardPanel extends JPanel {
    public CardPanel() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new MigLayout("fillx, ins 25 15 15 15"));
        putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:10;"
                + "background:darken(@background,10%)");
    }
}

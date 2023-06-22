/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author aldes
 */
public class HeaderWithButton extends JPanel {

    private String buttonText = "Button Text";
    private String titleText = "Header Text";
    
    private ButtonEvent event;
    
    public HeaderWithButton(ButtonEvent event) {
        this.event = event;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new MigLayout("fill", "[center][center,right]"));
//        putClientProperty(FlatClientProperties.STYLE, ""
//                + "border:50,0,20,0");
        
        title = new JLabel(titleText);
        title.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold $h00.font;");
        button = new JButton(buttonText);
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "arc:10;"
                + "margin:8,14,8,14;");
        button.addActionListener((ActionEvent ae) -> {
            event.onClick(ae);
        });
        
        add(title, "grow");
        add(button);
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
        button.setText(buttonText);
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;        
        title.setText(titleText);
    }

    public JButton getButton() {
        return button;
    }   
    
    private JButton button;
    private JLabel title;
    
}

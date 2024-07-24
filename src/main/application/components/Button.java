package main.application.components;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JButton;

/**
 *
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

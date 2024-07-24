package main.application.components;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
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

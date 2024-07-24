package main.application.components;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 */
public class Header extends JPanel {
    private String titleText = "Header Text";
    
    public Header() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new MigLayout("fill", "[center][center,right]"));
        
        title = new JLabel();
        title.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold $h00.font;");
        
        add(title, "grow");
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;        
        title.setText(titleText);
    }
    
    private JLabel title;
    
}

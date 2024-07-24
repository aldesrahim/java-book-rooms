package main.application.components;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 */
public class BodyPanel extends JScrollPane {

    public BodyPanel() {
        initComponents();
    }
    
    private void initComponents() {
        panel = new JPanel(new MigLayout("fillx, ins 25 15 15 15, hidemode 3"));
        
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setViewportView(panel);
    }

    public JPanel getPanel() {
        return panel;
    }
    
    private JPanel panel;
}

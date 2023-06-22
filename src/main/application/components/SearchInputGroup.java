/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author aldes
 */
public class SearchInputGroup extends JPanel {

    private String inputPlaceholder = "Cari...";
    private boolean isWithResetBtn = false;
    
    private SearchInputGroupEvent event;
    private String searchedText;
    
    public SearchInputGroup(SearchInputGroupEvent event) {
        this.event = event;
        
        initComponents();
        initEvents();
    }
    
    private void initComponents() {
        input = new JTextField();
        cmdSearch = new Button();
        cmdReset = new Button();
        
        input.putClientProperty(FlatClientProperties.STYLE, ""
                + "margin:5,11,5,11;");
        input.setPreferredSize(UIScale.scale(new Dimension(200, 0)));
        
        cmdSearch.setText("Cari");
        cmdReset.setText("Reset");
        
        setInputPlaceholder(inputPlaceholder);
        setLayout(new MigLayout("hidemode 3", "[center]"));
        
        add(input, "grow");
        add(cmdSearch);
        add(cmdReset);
        
        cmdReset.setVisible(false);
    }
    
    private void initEvents() {
        cmdSearch.addActionListener((e) -> {
            event.onSearch(input.getText());
        });
        cmdReset.addActionListener((e) -> {
            event.onReset(input.getText());
            input.setText("");
        });
    }

    public void setInputPlaceholder(String inputPlaceholder) {
        this.inputPlaceholder = inputPlaceholder;
        
        input.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, inputPlaceholder);
    }
    
    public void setIsWithResetBtn(boolean isWithResetBtn) {
        this.isWithResetBtn = isWithResetBtn;
        
        cmdReset.setVisible(isWithResetBtn);
    }

    public JTextField getInput() {
        return input;
    }

    public Button getCmdSearch() {
        return cmdSearch;
    }

    public Button getCmdReset() {
        return cmdReset;
    }
    
    private JTextField input;
    private Button cmdSearch;
    private Button cmdReset;
    
}

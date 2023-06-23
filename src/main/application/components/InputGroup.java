/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author aldes
 * @param <T>
 */
public abstract class InputGroup<T> extends JPanel {

    protected final T inputComponent;

    protected Dimension panelSize = new Dimension(200, 55);

    public InputGroup(T inputComponent) {
        this.inputComponent = inputComponent;
        initComponents();
    }

    private void initComponents() {
        setOpaque(false);

        lbTitle = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout(0, 7));
        setPreferredSize(UIScale.scale(panelSize));

        lbTitle.setText("Title");
        lbTitle.setLabelFor((JComponent) inputComponent);
        add(lbTitle, java.awt.BorderLayout.PAGE_START);

        add((JComponent) inputComponent, java.awt.BorderLayout.CENTER);
    }

    public void setPanelSize(Dimension defaultSize) {
        setPreferredSize(UIScale.scale(defaultSize));
        
        this.panelSize = defaultSize;

        repaint();
        revalidate();
    }

    public void setTitleText(String titleText) {
        lbTitle.setText(titleText);
    }
    
    public String getTitleText() {
        return lbTitle.getText();
    }

    public void showError() {
        ((JComponent) inputComponent).putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.OUTLINE_ERROR);
    }

    public void hideError() {
        ((JComponent) inputComponent).putClientProperty(FlatClientProperties.OUTLINE, "");
    }

    public abstract String getInputValue();

    public T getInputField() {
        return inputComponent;
    }

    private javax.swing.JLabel lbTitle;
}

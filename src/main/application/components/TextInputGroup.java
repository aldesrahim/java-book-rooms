/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components;

import javax.swing.JTextField;

/**
 *
 * @author aldes
 */
public class TextInputGroup extends InputGroup {

    public TextInputGroup() {
        super(new JTextField());
    }

    @Override
    public final JTextField getInputField() {
        return (JTextField) super.getInputField();
    }

    @Override
    public String getInputValue() {
        return getInputField().getText();
    }
}

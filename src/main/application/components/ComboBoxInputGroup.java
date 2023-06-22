/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components;

import javax.swing.JComboBox;

/**
 *
 * @author aldes
 * @param <T>
 */
public class ComboBoxInputGroup<T> extends InputGroup {

    public ComboBoxInputGroup() {
        super(new JComboBox<T>());
    }

    @Override
    public final JComboBox getInputField() {
        return (JComboBox) super.getInputField();
    }

    @Override
    public String getInputValue() {
        return getInputField().getSelectedItem().toString();
    }
}

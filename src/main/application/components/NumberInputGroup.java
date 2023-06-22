/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components;

/**
 *
 * @author aldes
 */
public class NumberInputGroup extends InputGroup {

    public NumberInputGroup() {
        super(new NumberInputField());
    }

    @Override
    public final NumberInputField getInputField() {
        return (NumberInputField) super.getInputField();
    }

    @Override
    public String getInputValue() {
        return getInputField().getText();
    }
}

package main.application.components;

import javax.swing.JTextField;

/**
 *
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

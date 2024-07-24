package main.application.components;

import javax.swing.JComboBox;

/**
 *
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
    
    public Object getSelectedItem() {
        return getInputField().getSelectedItem();
    }
    
    public Object getSelectedIndex() {
        return getInputField().getSelectedIndex();
    }
}

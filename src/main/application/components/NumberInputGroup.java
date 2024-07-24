package main.application.components;

/**
 *
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

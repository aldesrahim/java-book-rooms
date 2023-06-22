package main.application.components;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JPasswordField;

/**
 *
 * @author aldes
 */
public class PasswordInputGroup extends InputGroup {

    public PasswordInputGroup() {
        super(new JPasswordField());

        getInputField().putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true");
    }

    @Override
    public final JPasswordField getInputField() {
        return (JPasswordField) super.getInputField();
    }

    @Override
    public String getInputValue() {
        return String.valueOf(getInputField().getPassword());
    }
}

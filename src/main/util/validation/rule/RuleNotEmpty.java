/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.validation.rule;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import main.application.components.DateTimeInputField;

/**
 *
 * @author aldes
 */
public class RuleNotEmpty implements Rule {

    @Override
    public boolean validate(Object component) {
        if (component instanceof JTextField _com) {
            return _com.getText().isEmpty() == false;
        } 
        
        if (component instanceof JPasswordField _com) {
            return _com.getPassword().length > 0;
        }
        
        if (component instanceof DateTimeInputField _com) {
            return _com.getDateTime() != null;
        }
        
        return false;
    }

    @Override
    public String getErrorMessage() {
        return "tidak boleh kosong";
    }
    
}

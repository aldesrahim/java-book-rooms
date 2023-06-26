/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.validation;

import java.util.ArrayList;
import java.util.List;
import main.application.components.InputGroup;

/**
 *
 * @author aldes
 */
public class Validation {

    private List<ValidationItem> items = new ArrayList<>();
    private List<String> errorMessages = new ArrayList<>();

    public Validation addItem(ValidationItem component) {
        this.items.add(component);
        return this;
    }

    public Validation addItem(ValidationItem component, boolean status) {
        if (status) {
            addItem(component);
        }
        
        return this;
    }

    public Validation addErrorMessage(String message) {
        errorMessages.add(message);
        return this;
    }

    public String getErrorMessageString() {
        String message = "";

        int size = errorMessages.size();
        for (int i = 0; i < size; i++) {
            message += errorMessages.get(i);

            if (i < size - 1) {
                message += "\r\n";
            }
        }

        return message;
    }

    public boolean validate() {
        boolean isPassed = true;

        int size = items.size();

        for (int i = 0; i < size; i++) {
            ValidationItem item = items.get(i);
            boolean status = item.validate();

            if (!status) {
                addErrorMessage(item.getErrorMessageString());
                isPassed = false;
            }

            toggleComponentState(item.getComponent(), status);
        }

        return isPassed;
    }

    private void toggleComponentState(Object component, boolean status) {
        if (component instanceof InputGroup _com) {
            if (status) {
                _com.showError();
            } else {
                _com.hideError();
            }
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.validation;

import java.util.ArrayList;
import java.util.List;
import main.application.components.InputGroup;
import main.util.validation.rule.Rule;

/**
 *
 * @author aldes
 */
public class ValidationItem {
    private String name;
    private Object component;
    private List<Rule> rules = new ArrayList<>();
    private List<String> errorMessages = new ArrayList<>();

    public ValidationItem(InputGroup inputGroup) {
        this.name = inputGroup.getTitleText();
        this.component = inputGroup.getInputField();
    }

    public ValidationItem(String name, Object components) {
        this.name = name;
        this.component = components;
    }

    public String getName() {
        return name;
    }

    public Object getComponent() {
        return component;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
    
    public ValidationItem addRule(Rule rule) {
        rules.add(rule);
        return this;
    }
    
    public ValidationItem addErrorMessage(String message) {
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
        
        for (int i = 0; i < rules.size(); i++) {
            Rule rule = rules.get(i);
            
            if (!rule.validate(component)) {
                addErrorMessage(name + " " + rule.getErrorMessage());
                isPassed = false;
            }
        }
        
        return isPassed;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components;

import java.sql.Timestamp;

/**
 *
 * @author aldes
 */
public class DateTimeInputGroup extends InputGroup {
    public DateTimeInputGroup() {
        super(new DateTimeInputField());
    }

    @Override
    public final DateTimeInputField getInputField() {
        return (DateTimeInputField) super.getInputField();
    }

    @Override
    public String getInputValue() {
        return getInputField().getText();
    }
    
    public Timestamp getTimestamp() {
        return getInputField().getTimestamp();
    }
}

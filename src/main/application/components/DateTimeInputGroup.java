/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components;

import com.formdev.flatlaf.util.UIScale;
import java.awt.Dimension;
import java.util.Date;

/**
 *
 * @author aldes
 */
public class DateTimeInputGroup extends InputGroup {
    public DateTimeInputGroup() {
        super(new DateTimeInputField());
        
        setMinimumSize(UIScale.scale(panelSize));
    }

    @Override
    public final DateTimeInputField getInputField() {
        return (DateTimeInputField) super.getInputField();
    }

    @Override
    public String getInputValue() {
        return getInputField().getText();
    }
    
    public Date getDateTime() {
        return getInputField().getDateTime();
    }
    
    public void setDateTime(Date date) {
        getInputField().setDateTime(date);
    }

    @Override
    public void setPanelSize(Dimension defaultSize) {
        setMinimumSize(UIScale.scale(defaultSize));
        
        super.setPanelSize(defaultSize);
    }
    
    
}

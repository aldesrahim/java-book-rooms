package main.application.components;

import com.formdev.flatlaf.util.UIScale;
import java.awt.Dimension;
import java.util.Date;

/**
 *
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

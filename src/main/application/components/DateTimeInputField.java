/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.sql.Timestamp;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author aldes
 */
public class DateTimeInputField extends JXDatePicker {
    
    public DateTimeInputField() {
        super(new Date());
        
        DateFormat dateFormat = new SimpleDateFormat("EEEEE, dd MMMMM yyyy HH:mm", new Locale("id", "ID"));        
        setFormats(dateFormat);
        
        getMonthView().setLocale(new Locale("id", "ID"));
        getMonthView().setFirstDayOfWeek(Calendar.MONDAY);   
    }
    
    public String getText() {
        Date date = getDateTime();
        
        if (date == null) {
            return null;
        }
        
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
    
    public Date getDateTime() {
        return (Date) getEditor().getValue();
    }
    
}

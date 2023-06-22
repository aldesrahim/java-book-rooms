/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
        Date date = (Date) getEditor().getValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        if (date == null) {
            return null;
        }
        
        return sdf.format(date);
    }
    
    public LocalDateTime getDateTime() {
        Date date = (Date) getEditor().getValue();
        
        if (date == null) {
            return null;
        }
        
        return new Timestamp(date.getTime()).toLocalDateTime();
    }
    
    
}

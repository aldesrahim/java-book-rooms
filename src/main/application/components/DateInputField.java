package main.application.components;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 */
public class DateInputField extends JXDatePicker {

    private DateFormat dateFormat = new SimpleDateFormat("EEEEE, dd MMMMM yyyy", new Locale("id", "ID"));

    public DateInputField() {
        super(new Date());

        initFormats();
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    private void initFormats() {
        setFormats(dateFormat);

        getMonthView().setLocale(new Locale("id", "ID"));
        getMonthView().setFirstDayOfWeek(Calendar.MONDAY);
    }

    public String getText() {
        Date date = getDate();

        if (date == null) {
            return null;
        }

        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

}

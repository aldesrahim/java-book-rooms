package main.util;

import java.awt.Component;
import java.awt.Window;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 */
public class Dialog extends JOptionPane {
    
    private String title;

    public Dialog() {
        this.title = "Perhatian";
        this.message = JOptionPane.INFORMATION_MESSAGE;
        this.optionType = JOptionPane.DEFAULT_OPTION;
    }

    public Dialog(String title) {
        this.title = title;
        this.message = JOptionPane.INFORMATION_MESSAGE;
        this.optionType = JOptionPane.DEFAULT_OPTION;
    }

    public String getTitle() {
        return title;
    }

    public Object show(Component component) {
        Window window = SwingUtilities.windowForComponent(component);

        if (getWantsInput()) {
            return JOptionPane.showInputDialog(
                    window,
                    getMessage(),
                    getTitle(),
                    getMessageType(),
                    getIcon(),
                    null,
                    null);
        } else {
            return JOptionPane.showOptionDialog(
                    window,
                    getMessage(),
                    getTitle(),
                    getOptionType(),
                    getMessageType(),
                    getIcon(),
                    getOptions(),
                    getInitialValue());
        }
    }
}

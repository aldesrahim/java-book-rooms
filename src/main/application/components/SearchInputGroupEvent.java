package main.application.components;

import java.awt.event.ActionEvent;
import javax.swing.JTextField;

/**
 *
 */
public interface SearchInputGroupEvent {
    public void onSearch(String searchText);
    public void onReset(String searchText);
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.application.components;

import java.awt.event.ActionEvent;
import javax.swing.JTextField;

/**
 *
 * @author aldes
 */
public interface SearchInputGroupEvent {
    public void onSearch(String searchText);
    public void onReset(String searchText);
}

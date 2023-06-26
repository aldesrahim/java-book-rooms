/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components.table.cell;

import java.awt.Component;

/**
 *
 * @author aldes
 */
public class TableActionVisibility {
    
    protected boolean viewActionVisible = false;
    protected boolean editActionVisible = true;
    protected boolean deleteActionVisible = true;
    
    public void toggleViewVisibility(Component com, Integer row, Integer column) {
        com.setVisible(viewActionVisible);
    }
    
    public void toggleEditVisibility(Component com, Integer row, Integer column) {
        com.setVisible(editActionVisible);
    }
    
    public void toggleDeleteVisibility(Component com, Integer row, Integer column) {
        com.setVisible(deleteActionVisible);
    }
    
}

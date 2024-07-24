package main.application.components.table.cell;

import java.awt.Component;

/**
 *
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

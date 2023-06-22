package main.application.components.table.cell;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author aldes
 */
public class TableActionCellRender extends DefaultTableCellRenderer {
    
    private final TableActionVisibility actionVisibility;
    
    public TableActionCellRender() {
        this.actionVisibility = new TableActionVisibility();
    }
    
    public TableActionCellRender(TableActionVisibility actionVisibility) {
        this.actionVisibility = actionVisibility;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        ActionPanel action = new ActionPanel();
        
        actionVisibility.setRow(row);
        actionVisibility.setColumn(column);
        
        action.setActionVisibility(actionVisibility);
        action.setBackground(com.getBackground());
        return action;
    }
    
}

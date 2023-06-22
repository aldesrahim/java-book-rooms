package main.application.components.table.cell;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author aldes
 */
public class TableActionCellEditor extends DefaultCellEditor {
    private final TableActionEvent event;
    private final TableActionVisibility actionVisibility;

    public TableActionCellEditor(TableActionEvent event) {
        super(new JCheckBox());
        this.event = event;
        this.actionVisibility = new TableActionVisibility();
    }
    
    public TableActionCellEditor(TableActionEvent event, TableActionVisibility actionVisibility) {
        super(new JCheckBox());
        this.event = event;
        this.actionVisibility = actionVisibility;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        ActionPanel action = new ActionPanel();
        
        actionVisibility.setRow(row);
        actionVisibility.setColumn(column);
        
        action.setActionVisibility(actionVisibility);
        action.setBackground(table.getBackground());
        action.initEvent(event, row);
        
        return action;
    }
}

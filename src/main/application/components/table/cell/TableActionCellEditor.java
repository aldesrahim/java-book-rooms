package main.application.components.table.cell;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import main.model.table.TableModel;

/**
 *
 * @author aldes
 */
public class TableActionCellEditor extends DefaultCellEditor {

    private final TableActionEvent event;

    public TableActionCellEditor(TableActionEvent event) {
        super(new JCheckBox());
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        ActionPanel action = new ActionPanel();

        if (table.getModel() instanceof TableModel _model) {
            action.setActionVisibility(_model.getActionVisiblity(), row, column);
        }

        action.setBackground(table.getBackground());
        action.initEvent(event, row);

        return action;
    }
}

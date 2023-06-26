package main.application.components.table.cell;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import main.model.table.TableModel;

/**
 *
 * @author aldes
 */
public class TableActionCellRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        ActionPanel action = new ActionPanel();

        if (table.getModel() instanceof TableModel _model) {
            action.setActionVisibility(_model.getActionVisiblity(), row, column);
        }

        action.setBackground(com.getBackground());
        return action;
    }

}

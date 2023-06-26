package main.application.components.table.cell;

/**
 *
 * @author aldes
 */
public abstract class TableActionEvent {
    public abstract void onEdit(int row);
    public abstract void onDelete(int row);
    public abstract void onView(int row);
}

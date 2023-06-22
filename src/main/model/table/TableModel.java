package main.model.table;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import main.application.components.table.cell.TableActionEvent;
import main.application.components.table.cell.TableActionVisibility;

/**
 *
 * @author aldes
 * @param <T>
 */
public abstract class TableModel<T> extends AbstractTableModel {

    protected T model;
    protected List<T> rows;

    protected boolean isWithAction = false;
    protected int actionIndex = 0;
    protected TableActionEvent actionEvent;

    protected TableActionVisibility actionVisiblity;

    public abstract Long getAllRowCount();

    public abstract String[] getHeaders();

    public T getModel() {
        return model;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setTableAction(int tableActionIndex, TableActionEvent event) {
        this.isWithAction = true;
        this.actionIndex = tableActionIndex;
        this.actionEvent = event;
    }

    public void setTableAction(TableActionEvent event) {
        this.isWithAction = true;
        this.actionEvent = event;
    }

    public boolean isWithAction() {
        return isWithAction;
    }

    public int getActionIndex() {
        return actionIndex;
    }

    public TableActionVisibility getActionVisiblity() {
        if (actionVisiblity == null) {
            actionVisiblity = new TableActionVisibility();
        }
        
        return actionVisiblity;
    }

    public TableActionEvent getActionEvent() {
        return actionEvent;
    }

    public void insert(T product) {
        getRows().add(product);
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    public void update(int index, T data) {
        getRows().set(index, data);
        fireTableRowsUpdated(index, index);
    }

    public void delete(int index) {
        getRows().remove(index);
        fireTableRowsDeleted(index, index);
    }

    public T find(int index) {
        return getRows().get(index);
    }

    @Override
    public abstract Object getValueAt(int rowIndex, int columnIndex);

    @Override
    public int getRowCount() {
        return getRows().size();
    }

    @Override
    public int getColumnCount() {
        return getHeaders().length;
    }

    @Override
    public String getColumnName(int column) {
        return getHeaders()[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (isWithAction()) {
            return columnIndex == getActionIndex();
        }

        return true;
    }
}

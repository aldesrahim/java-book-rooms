/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components.table.cell;

/**
 *
 * @author aldes
 */
public class TableActionVisibility {
    
    protected boolean viewActionVisible = false;
    protected boolean editActionVisible = true;
    protected boolean deleteActionVisible = true;
    
    private int row;
    private int column;

    public boolean isViewActionVisible() {
        return viewActionVisible;
    }

    public void setViewActionVisible(boolean viewActionVisible) {
        this.viewActionVisible = viewActionVisible;
    }

    public boolean isEditActionVisible() {
        return editActionVisible;
    }

    public void setEditActionVisible(boolean editActionVisible) {
        this.editActionVisible = editActionVisible;
    }

    public boolean isDeleteActionVisible() {
        return deleteActionVisible;
    }

    public void setDeleteActionVisible(boolean deleteActionVisible) {
        this.deleteActionVisible = deleteActionVisible;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.table;

import java.awt.Component;
import java.sql.SQLException;
import main.application.Application;
import main.application.components.table.cell.ActionPanel;
import main.application.components.table.cell.TableActionVisibility;
import main.model.User;

/**
 *
 * @author aldes
 */
public class UserTableModel extends TableModel {

    public UserTableModel() {
        this.model = new User();
        this.actionIndex = 2;
    }

    @Override
    public Long getAllRowCount() {
        try {
            return getModel().count();
        } catch (SQLException ex) {
            ex.printStackTrace();

            return Long.valueOf(0);
        }
    }

    @Override
    public void delete(int index) {
        super.delete(index);
    }

    @Override
    public User getModel() {
        return (User) this.model;
    }

    @Override
    public String[] getHeaders() {
        return new String[]{"Nama", "Username", "Aksi"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User item = (User) find(rowIndex);

        return switch (columnIndex) {
            case 0 ->
                item.getName();
            case 1 ->
                item.getUsername();
            case 2 ->
                item.getId();
            default ->
                null;
        };
    }

    @Override
    public TableActionVisibility getActionVisiblity() {
        final UserTableModel thisModel = this;

        return new TableActionVisibility() {
            @Override
            public void toggleDeleteVisibility(Component com, Integer row, Integer column) {
                User user = (User) thisModel.find(row);

                if (user.isAdmin()) {
                    com.setVisible(false);
                    return;
                }

                if (Application.getAuthUser().isAdmin()) {
                    com.setVisible(true);
                    return;
                }

                com.setVisible(false);
            }

            @Override
            public void toggleEditVisibility(Component com, Integer row, Integer column) {
                User user = (User) thisModel.find(row);

                if (Application.isAuthenticated()) {
                    com.setVisible(Application.getAuthUser().isAdmin() || user.getId().equals(Application.getAuthUser().getId()));
                    return;
                }

                com.setVisible(!user.isAdmin());
            }

        };
    }

}

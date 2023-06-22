/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.table;

import java.sql.SQLException;
import main.application.Application;
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

        switch (columnIndex) {
            case 0:
                return item.getName();

            case 1:
                return item.getUsername();

            case 2:
                return item.getId();

            default:
                return null;
        }
    }

    @Override
    public TableActionVisibility getActionVisiblity() {
        final UserTableModel thisModel = this;

        return new TableActionVisibility() {
            @Override
            public boolean isDeleteActionVisible() {
                User user = (User) thisModel.find(getRow());

                if (user.isAdmin()) {
                    return false;
                }

                if (Application.getAuthUser().isAdmin()) {
                    return true;
                }

                return false;
            }

            @Override
            public boolean isEditActionVisible() {
                User user = (User) thisModel.find(getRow());

                if (Application.isAuthenticated()) {
                    return Application.getAuthUser().isAdmin() || user.getId().equals(Application.getAuthUser().getId());
                }

                return !user.isAdmin();
            }

        };
    }

}

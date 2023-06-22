/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.table;

import java.sql.SQLException;
import main.application.components.table.cell.TableActionVisibility;
import main.model.Room;

/**
 *
 * @author aldes
 */
public class RoomTableModel extends TableModel {

    public RoomTableModel() {
        this.model = new Room();
        this.actionIndex = 5;
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
    public Room getModel() {
        return (Room) this.model;
    }

    @Override
    public String[] getHeaders() {
        return new String[]{"Nama", "Tipe", "Kapasitas", "Deskripsi", "Fasilitas", "Aksi"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Room item = (Room) find(rowIndex);

        switch (columnIndex) {
            case 0:
                return item.getName();

            case 1:
                return item.getType().getName();

            case 2:
                return item.getCapacity();

            case 3:
                return item.getDescription();

            case 4:
                return item.getFacilityCount();

            case 5:
                return item.getId();

            default:
                return null;
        }
    }

    @Override
    public TableActionVisibility getActionVisiblity() {
        return new TableActionVisibility() {

            @Override
            public boolean isViewActionVisible() {
                return true;
            }

        };
    }

}

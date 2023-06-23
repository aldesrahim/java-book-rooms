/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.table;

import java.sql.SQLException;
import main.application.components.table.cell.TableActionVisibility;
import main.model.Reservation;

/**
 *
 * @author aldes
 */
public class ReservationTableModel extends TableModel {

    public ReservationTableModel() {
        this.model = new Reservation();
        this.actionIndex = 4;
    }

    @Override
    public Long getAllRowCount() {
        try {
            return getModel().count();
        } catch (SQLException ex) {
            ex.printStackTrace();

            return Long.valueOf("0");
        }
    }

    @Override
    public void delete(int index) {
        super.delete(index);
    }

    @Override
    public Reservation getModel() {
        return (Reservation) this.model;
    }

    @Override
    public String[] getHeaders() {
        return new String[]{"Instansi/Penyelenggara", "No.Telp", "Status", "Gedung/Ruangan", "Aksi"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Reservation item = (Reservation) find(rowIndex);

        switch (columnIndex) {
            case 0:
                return item.getName();

            case 1:
                return item.getPhoneNumber();

            case 2:
                return item.getStatus().toString();

            case 3:
                return item.getRoom().getName();

            case 4:
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
            
            @Override
            public boolean isDeleteActionVisible() {
                return false;
            }

        };
    }

}

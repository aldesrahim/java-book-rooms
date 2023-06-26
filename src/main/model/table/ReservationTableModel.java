/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.table;

import java.awt.Component;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import main.application.Application;
import main.application.components.table.cell.TableActionVisibility;
import main.application.enums.ReservationStatus;
import main.model.Reservation;

/**
 *
 * @author aldes
 */
public class ReservationTableModel extends TableModel {

    public ReservationTableModel() {
        this.model = new Reservation();
        this.actionIndex = 9;
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
        return new String[]{"Instansi/Penyelenggara", "No.Telp", "Status", "Gedung/Ruangan", "Dari Tgl Reservasi", "Sampai Tgl Reservasi", "Tgl Check In", "Tgl Check Out", "Dibuat Oleh", "Aksi"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Reservation item = (Reservation) find(rowIndex);

        DateFormat dateFormat = new SimpleDateFormat("EEEEE, dd MMMMM yyyy HH:mm", new Locale("id", "ID"));

        return switch (columnIndex) {
            case 0 ->
                item.getName();
            case 1 ->
                item.getPhoneNumber();
            case 2 ->
                item.getStatus().toString();
            case 3 ->
                item.getRoom().getName();
            case 4 ->
                (item.getStartedAt() != null ? dateFormat.format(item.getStartedAt()) : "");
            case 5 ->
                (item.getEndedAt() != null ? dateFormat.format(item.getEndedAt()) : "");
            case 6 ->
                (item.getCheckedInAt() != null ? dateFormat.format(item.getCheckedInAt()) : "");
            case 7 ->
                (item.getCheckedOutAt() != null ? dateFormat.format(item.getCheckedOutAt()) : "");
            case 8 ->
                item.getUser() != null ? item.getUser().getName() : "";
            case 9 ->
                item.getId();
            default ->
                null;
        };
    }

    @Override
    public TableActionVisibility getActionVisiblity() {
        return new TableActionVisibility() {

            @Override
            public void toggleViewVisibility(Component com, Integer row, Integer column) {
                com.setVisible(true);
            }

            @Override
            public void toggleEditVisibility(Component com, Integer row, Integer column) {
                Reservation item = (Reservation) find(row);

                if (!item.getStatus().equals(ReservationStatus.BOOKED)) {
                    com.setVisible(false);
                    return;
                }

                if (Application.isAuthenticated()) {
                    com.setVisible(Application.getAuthUser().isAdmin());
                    return;
                }

                com.setVisible(false);
            }

            @Override
            public void toggleDeleteVisibility(Component com, Integer row, Integer column) {
                com.setVisible(false);
            }

        };
    }

}

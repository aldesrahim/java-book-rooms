/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.table;

import java.sql.SQLException;
import main.model.Facility;

/**
 *
 * @author aldes
 */
public class FacilityTableModel extends TableModel {    
    public FacilityTableModel() {        
        this.model = new Facility();
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
    public Facility getModel() {
        return (Facility) this.model;
    }

    @Override
    public String[] getHeaders() {
        return new String[]{"Nama", "Aksi"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Facility item = (Facility) find(rowIndex);
        
        return switch (columnIndex) {
            case 0 -> item.getName();
            case 1 -> item.getId();
            default -> null;
        };
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.table;

import java.sql.SQLException;
import main.model.Consumption;

/**
 *
 * @author aldes
 */
public class ConsumptionTableModel extends TableModel {    
    public ConsumptionTableModel() {        
        this.model = new Consumption();
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
    public Consumption getModel() {
        return (Consumption) this.model;
    }

    @Override
    public String[] getHeaders() {
        return new String[]{"Nama", "Aksi"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Consumption item = (Consumption) find(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return item.getName();
                
            case 1:
                return item.getId();
                
            default:
                return null;
        }
    }

}

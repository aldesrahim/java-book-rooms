/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.table;

import java.sql.SQLException;
import main.model.Type;

/**
 *
 * @author aldes
 */
public class TypeTableModel extends TableModel {    
    public TypeTableModel() {        
        this.model = new Type();
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
    public Type getModel() {
        return (Type) this.model;
    }

    @Override
    public String[] getHeaders() {
        return new String[]{"Nama", "Aksi"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Type item = (Type) find(rowIndex);
        
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

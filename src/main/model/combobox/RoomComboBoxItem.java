/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.combobox;

import main.model.Room;

/**
 *
 * @author aldes
 */
public class RoomComboBoxItem extends ComboBoxItem<Room> {

    public RoomComboBoxItem(Room model) {
        super(model);
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", getModel().getName(), getModel().getType().getName());
    }

}

package main.model.combobox;

import main.model.Room;

/**
 *
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

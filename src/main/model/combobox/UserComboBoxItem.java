/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.combobox;

import main.model.User;

/**
 *
 * @author aldes
 */
public class UserComboBoxItem extends ComboBoxItem<User> {

    public UserComboBoxItem(User model) {
        super(model);
    }

    @Override
    public String toString() {
        return getModel().getName();
    }

}

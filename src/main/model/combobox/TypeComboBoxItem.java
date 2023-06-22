/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.combobox;

import main.model.Type;

/**
 *
 * @author aldes
 */
public class TypeComboBoxItem extends ComboBoxItem<Type> {

    public TypeComboBoxItem(Type model) {
        super(model);
    }

    @Override
    public String toString() {
        return getModel().getName();
    }

}

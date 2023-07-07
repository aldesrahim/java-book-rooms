/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.combobox;

import main.model.Consumption;

/**
 *
 * @author aldes
 */
public class ConsumptionComboBoxItem extends ComboBoxItem<Consumption> {

    public ConsumptionComboBoxItem(Consumption model) {
        super(model);
    }

    @Override
    public String toString() {
        return getModel().getName();
    }

}

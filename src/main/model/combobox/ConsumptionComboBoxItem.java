package main.model.combobox;

import main.model.Consumption;

/**
 *
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

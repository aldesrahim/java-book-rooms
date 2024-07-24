package main.model.combobox;

import main.model.Type;

/**
 *
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

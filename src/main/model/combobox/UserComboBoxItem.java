package main.model.combobox;

import main.model.User;

/**
 *
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

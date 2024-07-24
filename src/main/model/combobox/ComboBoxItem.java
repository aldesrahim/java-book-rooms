package main.model.combobox;

/**
 *
 * @param <T>
 */
public abstract class ComboBoxItem<T> {

    private T model;

    public ComboBoxItem(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    @Override
    public abstract String toString();
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.combobox;

/**
 *
 * @author aldes
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

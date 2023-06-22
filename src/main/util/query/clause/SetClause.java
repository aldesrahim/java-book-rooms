/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.query.clause;

/**
 *
 * @author aldes
 */
public class SetClause {
    protected Object column;
    protected Object value;
    protected int index;

    public SetClause(Object column, Object value) {
        this.column = column;
        this.value = value;
    }

    public Object getColumn() {
        return column;
    }

    public Object getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public SetClause setIndex(int index) {
        this.index = index;
        return this;
    }
}

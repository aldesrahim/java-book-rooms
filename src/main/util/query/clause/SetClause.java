package main.util.query.clause;

/**
 *
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

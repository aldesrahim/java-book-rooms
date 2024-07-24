package main.util.query.clause;

/**
 *
 */
public class LimitClause {
    protected Object limit;
    protected Object offset;
    
    public LimitClause(Object limit) {
        this.limit = limit;
        this.offset = 0;
    }

    public LimitClause(Object limit, Object offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public Object getLimit() {
        return limit;
    }

    public Object getOffset() {
        return offset;
    }
}

package main.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.util.query.QueryBuilder;
import main.util.query.QueryFetch;
import main.util.query.QueryUpdate;
import main.util.query.clause.LimitClause;
import main.util.query.clause.WhereClause;

/**
 *
 * @author aldes
 */
public abstract class Model<T> {

    public QueryBuilder query() {
        return QueryBuilder.getInstance()
                .setFrom(getTable());
    }

    public abstract String getTable();

    public String getPrimaryKey() {
        return getTable() + ".id";
    }

    public abstract T fromResultSet(ResultSet rs) throws SQLException;

    public abstract QueryUpdate save() throws SQLException;

    public abstract QueryUpdate delete() throws SQLException;

    public QueryUpdate delete(Object id) throws SQLException {
        return query()
                .addWhere(new WhereClause(getPrimaryKey(), id))
                .delete();
    }

    public T find(Object id) throws SQLException {
        QueryFetch fetch = query()
                .addWhere(new WhereClause(getPrimaryKey(), id))
                .fetch();

        ResultSet rs = fetch.get();

        if (rs.next()) {
            return fromResultSet(rs);
        }

        return null;
    }

    public List<T> all() throws SQLException {
        List<T> rows = new ArrayList<>();

        ResultSet rs = query().fetch().get();

        while (rs.next()) {
            rows.add(fromResultSet(rs));
        }

        return rows;
    }

    public Long count() throws SQLException {
        QueryFetch fetch = query()
                .addSelect("COUNT(*) AS total")
                .fetch();

        ResultSet rs = fetch.get();

        if (rs.next()) {
            return rs.getLong("total");
        }

        return Long.valueOf(0);
    }

    public T scopeLimit(int limit) {
        query()
                .setLimit(new LimitClause(limit));

        return (T) this;
    }

    public T scopeLimit(int limit, int offset) {
        query()
                .setLimit(new LimitClause(limit, offset));

        return (T) this;
    }

    public T scopePaginate(int currentPage, int perPage) {
        query()
                .setLimit(new LimitClause(perPage, perPage * (currentPage - 1)));

        return (T) this;
    }

    public T scopeSearch(String term) {
        return (T) this;
    }
}

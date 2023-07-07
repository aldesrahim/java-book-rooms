package main.util.query;

import java.util.Date;
import main.util.query.clause.WhereClause;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Connection;
import java.time.LocalDateTime;
import main.util.Database;
import main.util.query.clause.JoinClause;
import main.util.query.clause.JoinOnClause;
import main.util.query.clause.LimitClause;
import main.util.query.clause.OrderByClause;
import main.util.query.clause.SetClause;
import main.util.query.clause.WhereClauseInterface;
import main.util.query.clause.WhereInClause;

/**
 *
 * @author aldes
 */
public class QueryBuilder {

    protected String from;

    protected List<String> select = new ArrayList<>();
    protected List<SetClause> setClause = new ArrayList<>();
    protected List<WhereClauseInterface> whereClause = new ArrayList<>();
    protected List<JoinClause> joinClause = new ArrayList<>();
    protected List<OrderByClause> orderByClause = new ArrayList<>();
    protected LimitClause limitClause;

    protected List<QueryBuildType> buildOrder = new ArrayList<>();

    protected int whereClauseIndex = 0;
    protected int joinOnClauseIndex = 0;
    protected int setClauseIndex = 0;
    protected int psIndex = 1;

    protected Connection dbConnection;
    protected PreparedStatement preparedStatement;
    
    protected boolean debug = false;

    protected static QueryBuilder instance;

    public static QueryBuilder getInstance() {
        if (instance == null) {
            instance = new QueryBuilder();
        }

        return instance;
    }

    protected void reset() {
        select.clear();
        setClause.clear();
        whereClause.clear();
        joinClause.clear();
        orderByClause.clear();
        limitClause = null;
        buildOrder.clear();

        whereClauseIndex = 0;
        joinOnClauseIndex = 0;
        setClauseIndex = 0;
        psIndex = 1;
    }

    public void setDbConnection(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Connection getDbConnection() {
        if (dbConnection == null) {
            try {
                Database.getInstance().connect();
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex.getMessage());
            }

            dbConnection = Database.getInstance().getConnection();
        }

        return dbConnection;
    }

    public void beginTransaction() {
        try {
            getDbConnection().setAutoCommit(false);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void endTransaction() {
        try {
            getDbConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void commit() {
        try {
            getDbConnection().commit();
            getDbConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void rollBack() {
        try {
            getDbConnection().rollback();
            getDbConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public QueryBuilder setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public QueryBuilder setFrom(String from) {
        this.from = from;
        return this;
    }

    public QueryBuilder addSelect(String clause) {
        this.select.add(clause);
        return this;
    }

    public QueryBuilder addSelect(String clause, boolean status) {
        if (status) {
            addSelect(clause);
        }
        return this;
    }

    public QueryBuilder addSet(SetClause clause) {
        this.setClause.add(clause);
        return this;
    }

    public QueryBuilder addSet(SetClause clause, boolean status) {
        if (status) {
            addSet(clause);
        }
        return this;
    }

    public QueryBuilder addJoin(JoinClause clause) {
        this.joinClause.add(clause);
        return this;
    }

    public QueryBuilder addJoin(JoinClause clause, boolean status) {
        if (status) {
            addJoin(clause);
        }
        return this;
    }

    public QueryBuilder addWhere(WhereClauseInterface clause) {
        this.whereClause.add(clause);
        return this;
    }

    public QueryBuilder addWhere(WhereClauseInterface clause, boolean status) {
        if (status) {
            addWhere(clause);
        }
        return this;
    }

    public QueryBuilder addOrderBy(OrderByClause clause) {
        this.orderByClause.add(clause);
        return this;
    }

    public QueryBuilder addOrderBy(OrderByClause clause, boolean status) {
        if (status) {
            addOrderBy(clause);
        }
        return this;
    }

    public QueryBuilder setLimit(LimitClause clause) {
        this.limitClause = clause;
        return this;
    }

    public QueryBuilder setLimit(LimitClause clause, boolean status) {
        if (status) {
            setLimit(clause);
        }
        return this;
    }

    protected String getSelectSql() {
        List<String> items = this.select;

        String sql = "";

        int size = items.size();
        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);

            sql += item;

            if (i < size - 1) {
                sql += ", ";
            }
        }

        return sql;
    }

    protected String getSetSql() {
        List<SetClause> items = this.setClause;

        String sql = "";

        int size = items.size();
        for (int i = 0; i < size; i++) {
            SetClause item = items.get(i);

            sql += String.format("%s = ?", item.getColumn());
            item.setIndex(setClauseIndex++);

            if (i < size - 1) {
                sql += ", ";
            }
        }

        return sql;
    }

    protected String getWhereSql() {
        List<WhereClauseInterface> items = this.whereClause;

        return getWhereSql(items);
    }

    protected String getWhereSql(List<WhereClauseInterface> items) {
        String sql = "";
        String condition;

        boolean isFirst = true;

        for (int i = 0; i < items.size(); i++) {
            WhereClauseInterface item = items.get(i);

            if (item.hasClause()) {
                condition = isFirst ? "" : String.format(" %s ", item.getCondition());

                if (item instanceof WhereClause _item) {
                    if (_item.getValue() instanceof QueryRaw) {
                        sql += String.format("%s%s %s %s", condition, item.getColumn(), item.getOperator(), _item.getValue().toString());
                    } else {
                        sql += String.format("%s%s %s ?", condition, item.getColumn(), item.getOperator());
                    }

                } else if (item instanceof WhereInClause _item) {
                    sql += String.format("%s%s %s (%s)", condition, item.getColumn(), item.getOperator(), _item.getPlaceholder());
                } else {
                    continue;
                }

                item.setIndex(whereClauseIndex++);
                isFirst = false;
            }

            if (!item.getSub().isEmpty()) {
                condition = isFirst ? "" : String.format(" %s ", item.getCondition());

                sql += String.format("%s(%s)", condition, getWhereSql(item.getSub()));

                isFirst = false;
            }
        }

        return sql;
    }

    protected String getJoinSql() {
        List<JoinClause> items = this.joinClause;

        String sql = "";
        boolean isFirst = true;

        for (int i = 0; i < items.size(); i++) {
            JoinClause item = items.get(i);

            String spacing = isFirst ? "" : " ";

            sql += String.format("%s%s %s ON %s",
                    spacing,
                    item.getCondition(),
                    item.getTable(),
                    getJoinOnSql(item.getJoinOn())
            );

            isFirst = false;
        }

        return sql;
    }

    protected String getJoinOnSql(List<JoinOnClause> items) {
        String sql = "";
        String condition;

        boolean isFirst = true;

        for (int i = 0; i < items.size(); i++) {
            JoinOnClause item = items.get(i);

            if (item.hasClause()) {
                condition = isFirst ? "" : String.format(" %s ", item.getCondition());

                if (item.isAsWhere()) {
                    sql += String.format("%s%s %s ?", condition, item.getColumn(), item.getOperator());
                    item.setIndex(joinOnClauseIndex++);
                } else {
                    sql += String.format("%s%s %s %s", condition, item.getColumn(), item.getOperator(), item.getValue());
                }

                isFirst = false;
            }

            if (!item.getSubJoinOn().isEmpty()) {
                condition = isFirst ? "" : String.format(" %s ", item.getCondition());

                sql += String.format("%s(%s)", condition, getJoinOnSql(item.getSubJoinOn()));

                isFirst = false;
            }
        }

        return sql;
    }

    protected String getOrderBySql() {
        List<OrderByClause> items = this.orderByClause;

        String sql = "";

        int size = items.size();
        for (int i = 0; i < items.size(); i++) {
            OrderByClause item = items.get(i);

            sql += String.format("%s %s", item.getColumn(), item.getDirection());

            if (i < size - 1) {
                sql += ", ";
            }
        }

        return sql;
    }

    protected String getLimitSql() {
        return "?, ?"; // OFFSET, LIMIT
    }

    protected void buildSetClause() throws SQLException {
        List<SetClause> items = this.setClause;

        for (int i = 0; i < items.size(); i++) {
            SetClause item = items.get(i);
            mapParam(psIndex++, item.getValue());
        }
    }

    protected void buildWhereClause() throws SQLException {
        List<WhereClauseInterface> items = this.whereClause;

        this.buildWhereClause(items);
    }

    protected void buildWhereClause(List<WhereClauseInterface> items) throws SQLException {
        for (int i = 0; i < items.size(); i++) {
            WhereClauseInterface item = items.get(i); // EDIT

            if (item.hasClause()) {

                if (item instanceof WhereClause _item) {
                    if (_item.getValue() instanceof QueryRaw) {
                        continue;
                    }

                    mapParam(psIndex++, _item.getValue());
                } else if (item instanceof WhereInClause _item) {
                    Object[] values = _item.getValues();

                    for (int j = 0; j < values.length; j++) {
                        if (values[j] instanceof QueryRaw) {
                            continue;
                        }

                        mapParam(psIndex++, values[j]);
                    }
                }
            }

            if (!item.getSub().isEmpty()) {
                buildWhereClause(item.getSub());
            }
        }
    }

    protected void buildJoinClause() throws SQLException {
        List<JoinClause> items = this.joinClause;

        for (int i = 0; i < items.size(); i++) {
            JoinClause item = items.get(i);
            buildJoinOnClause(item.getJoinOn());
        }
    }

    protected void buildJoinOnClause(List<JoinOnClause> items) throws SQLException {
        for (int i = 0; i < items.size(); i++) {
            JoinOnClause item = items.get(i);

            if (item.hasClause()) {
                if (item.isAsWhere()) {
                    mapParam(psIndex++, item.getValue());
                }
            }

            if (!item.getSubJoinOn().isEmpty()) {
                buildJoinOnClause(item.getSubJoinOn());
            }
        }
    }

    protected void buildLimitClause() throws SQLException {
        LimitClause item = this.limitClause;

        mapParam(psIndex++, item.getOffset());
        mapParam(psIndex++, item.getLimit());
    }

    public void build(QueryMethod method) throws SQLException {
        String sql = "";
        String selectSql = "";
        String joinSql = "";
        String whereSql = "";
        String orderBySql = "";
        String limitSql = "";

        if (method.equals(QueryMethod.SELECT) || method.equals(QueryMethod.UPDATE) || method.equals(QueryMethod.DELETE)) {

            if (!this.whereClause.isEmpty()) {
                whereSql = "WHERE " + getWhereSql();
            }

            if (!this.joinClause.isEmpty()) {
                joinSql = getJoinSql();
            }
        }

        if (!this.orderByClause.isEmpty() && method.equals(QueryMethod.SELECT)) {
            orderBySql = "ORDER BY " + getOrderBySql();
        }

        if (this.limitClause != null && method.equals(QueryMethod.SELECT)) {
            limitSql = "LIMIT " + getLimitSql();
        }

        if (method.equals(QueryMethod.SELECT)) {
            selectSql = getSelectSql();

            if (selectSql.isEmpty()) {
                selectSql = "*";
            }
        }

        if (method.equals(QueryMethod.DELETE)) {
            selectSql = getSelectSql();

            if (selectSql.isEmpty()) {
                selectSql = "";
            }
        }

        switch (method) {
            case INSERT -> {
                sql = "INSERT INTO " + from + " SET " + getSetSql();
                buildOrder.add(QueryBuildType.SET_CLAUSE);
            }
            case UPDATE -> {
                sql = "UPDATE " + from;

                if (!joinSql.isEmpty()) {
                    sql += (" " + joinSql);
                    buildOrder.add(QueryBuildType.JOIN_CLAUSE);
                }

                sql += (" SET " + getSetSql());
                buildOrder.add(QueryBuildType.SET_CLAUSE);

                if (!whereSql.isEmpty()) {
                    sql += (" " + whereSql);
                    buildOrder.add(QueryBuildType.WHERE_CLAUSE);
                }
            }
            case DELETE -> {
                sql = "DELETE " + selectSql + " FROM " + from;

                if (!joinSql.isEmpty()) {
                    sql += (" " + joinSql);
                    buildOrder.add(QueryBuildType.JOIN_CLAUSE);
                }

                if (!whereSql.isEmpty()) {
                    sql += (" " + whereSql);
                    buildOrder.add(QueryBuildType.WHERE_CLAUSE);
                }
            }
            case SELECT -> {
                sql = "SELECT " + selectSql + " FROM " + from;

                if (!joinSql.isEmpty()) {
                    sql += (" " + joinSql);
                    buildOrder.add(QueryBuildType.JOIN_CLAUSE);
                }

                if (!whereSql.isEmpty()) {
                    sql += (" " + whereSql);
                    buildOrder.add(QueryBuildType.WHERE_CLAUSE);
                }

                if (!orderBySql.isEmpty()) {
                    sql += (" " + orderBySql);
                }

                if (!limitSql.isEmpty()) {
                    sql += (" " + limitSql);
                    buildOrder.add(QueryBuildType.LIMIT_OFFSET_CLAUSE);
                }
            }
        }

        if (debug) {
            System.out.println(sql);
        }

        if (method.equals(QueryMethod.SELECT)) {
            preparedStatement = getDbConnection().prepareStatement(sql);
        } else {
            preparedStatement = getDbConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }

        for (int i = 0; i < buildOrder.size(); i++) {
            QueryBuildType get = buildOrder.get(i);

            switch (get) {
                case SET_CLAUSE ->
                    buildSetClause();
                case WHERE_CLAUSE ->
                    buildWhereClause();
                case LIMIT_OFFSET_CLAUSE ->
                    buildLimitClause();
                case JOIN_CLAUSE ->
                    buildJoinClause();
            }
        }
    }

    public QueryUpdate insert() throws SQLException {
        this.build(QueryMethod.INSERT);

        QueryUpdate update = new QueryUpdate(getPreparedStatement(), QueryMethod.INSERT);

        reset();

        return update;
    }

    public QueryUpdate update() throws SQLException {
        this.build(QueryMethod.UPDATE);

        QueryUpdate update = new QueryUpdate(getPreparedStatement(), QueryMethod.UPDATE);

        reset();

        return update;
    }

    public QueryUpdate delete() throws SQLException {
        this.build(QueryMethod.DELETE);

        QueryUpdate update = new QueryUpdate(getPreparedStatement(), QueryMethod.DELETE);

        reset();

        return update;
    }

    public QueryFetch fetch() throws SQLException {
        this.build(QueryMethod.SELECT);

        QueryFetch fetch = new QueryFetch(getPreparedStatement());

        reset();

        return fetch;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }
    
    public String toSql(QueryMethod method) throws SQLException {
        this.build(method);
        
        String sql = preparedStatement.toString();
        
        reset();
        
        return sql.substring( sql.indexOf( ": " ) + 2 );
    }

    protected void mapParam(int index, Object arg) throws SQLException {
        if (arg instanceof Date) {
            preparedStatement.setTimestamp(index, new Timestamp(((Date) arg).getTime()));
        } else if (arg instanceof Integer) {
            preparedStatement.setInt(index, (Integer) arg);
        } else if (arg instanceof Long) {
            preparedStatement.setLong(index, (Long) arg);
        } else if (arg instanceof Double) {
            preparedStatement.setDouble(index, (Double) arg);
        } else if (arg instanceof Float) {
            preparedStatement.setFloat(index, (Float) arg);
        } else if (arg instanceof LocalDateTime) {
            preparedStatement.setTimestamp(index, Timestamp.valueOf(((LocalDateTime) arg)));
        } else if (arg instanceof Timestamp) {
            preparedStatement.setTimestamp(index, (Timestamp) arg);
        } else {
            preparedStatement.setString(index, (String) arg);
        }
    }
}

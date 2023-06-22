/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.validation.rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTextField;
import main.util.query.QueryBuilder;
import main.util.query.QueryFetch;
import main.util.query.clause.WhereClause;

/**
 *
 * @author aldes
 */
public class RuleUnique implements Rule {

    private String table;
    private String column;
    private String except;
    private String exceptColumn = "id";

    public RuleUnique(String table, String column) {
        this.table = table;
        this.column = column;
    }

    public RuleUnique(String table, String column, String except) {
        this.table = table;
        this.column = column;
        this.except = except;
    }

    public RuleUnique(String table, String column, String except, String exceptColumn) {
        this.table = table;
        this.column = column;
        this.except = except;
        this.exceptColumn = exceptColumn;
    }

    public RuleUnique setExcept(String except) {
        this.except = except;

        return this;
    }

    public RuleUnique setExcept(String except, String exceptColumn) {
        this.except = except;
        this.exceptColumn = exceptColumn;

        return this;
    }

    private QueryBuilder getQueryBuilder() {
        return QueryBuilder
                .getInstance()
                .setFrom(table);
    }

    private boolean validateFromDb(String search) {
        try {
            QueryFetch fetch = getQueryBuilder()
                    .addWhere(new WhereClause(column, search))
                    .addWhere(new WhereClause(exceptColumn, "!=", except), except != null && !except.isEmpty())
                    .fetch();
            
            return !fetch.get().next();
        } catch (SQLException ex) {
            ex.printStackTrace();

            return false;
        }
    }

    @Override
    public boolean validate(Object component) {
        if (component instanceof JTextField _com) {
            return validateFromDb(_com.getText());
        }

        return false;
    }

    @Override
    public String getErrorMessage() {
        return "sudah ada sebelumnya";
    }
}

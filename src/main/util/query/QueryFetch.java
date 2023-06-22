/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 * @author aldes
 */
public class QueryFetch {
    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet;
    protected ResultSetMetaData resultSetMetaData;

    public QueryFetch(PreparedStatement preparedStatement) throws SQLException {
        this.preparedStatement = preparedStatement;
        this.resultSet = preparedStatement.executeQuery();
        this.resultSetMetaData = this.resultSet.getMetaData();
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public ResultSet get() {
        return resultSet;
    }

    public ResultSetMetaData getMetaData() {
        return resultSetMetaData;
    }
}

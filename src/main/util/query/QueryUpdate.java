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
public class QueryUpdate {
    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet;
    protected ResultSetMetaData resultSetMetaData;
    
    protected int updateStatus;
    protected Long insertedId;

    public QueryUpdate(PreparedStatement preparedStatement, QueryMethod method) throws SQLException {
        this.preparedStatement = preparedStatement;
        this.updateStatus = preparedStatement.executeUpdate();
        this.resultSet = preparedStatement.getGeneratedKeys();
        this.resultSetMetaData = this.resultSet.getMetaData();
        
        if (method.equals(QueryMethod.INSERT)) {
            this.resultSet.next();
            try {
                this.insertedId = resultSet.getLong(1);
            } catch (SQLException ex) {
                //
            }
        }
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

    public int getStatus() {
        return updateStatus;
    }

    public Long getInsertedId() {
        return insertedId;
    }
    
    
}

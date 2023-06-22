/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.query.clause;

/**
 *
 * @author aldes
 */
public class OrderByClause {
    protected String column;
    protected String direction;

    public OrderByClause(String column) {
        this.column = column;
        this.direction = "ASC";
    }
    
    public OrderByClause(String column, String direction) {
        this.column = column;
        this.direction = direction;
    }

    public String getColumn() {
        return column;
    }

    public String getDirection() {
        return direction;
    }
}

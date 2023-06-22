/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.query.clause;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aldes
 */
public class JoinClause {
    protected Object table;
    protected String condition = "INNER JOIN";
    protected List<JoinOnClause> JoinOn = new ArrayList<>();
    
    public JoinClause(Object table, JoinOnClause joinOn) {
        this.table = table;
        this.JoinOn.add(joinOn);
        this.condition = "INNER JOIN";
    }
    
    public JoinClause(Object table, List<JoinOnClause> joinOn) {
        this.table = table;
        this.JoinOn = joinOn;
        this.condition = "INNER JOIN";
    }
    
    public JoinClause(Object table, JoinOnClause joinOn, String condition) {
        this.table = table;
        this.JoinOn.add(joinOn);
        this.condition = condition;
    }
    
    public JoinClause(Object table, List<JoinOnClause> joinOn, String condition) {
        this.table = table;
        this.JoinOn = joinOn;
        this.condition = condition;
    }
    
    public JoinClause(Object table, Object column, Object value) {
        this.table = table;
        this.JoinOn.add(new JoinOnClause(column, value));
        this.condition = "INNER JOIN";
    }

    public JoinClause(Object table, Object column, Object operator, Object value) {
        this.table = table;
        this.JoinOn.add(new JoinOnClause(column, operator, value));
        this.condition = "INNER JOIN";
    }

    public JoinClause(Object table, Object column, Object operator, Object value, String condition) {
        this.table = table;
        this.JoinOn.add(new JoinOnClause(column, operator, value));
        this.condition = condition;
    }

    public Object getTable() {
        return table;
    }

    public String getCondition() {
        return condition;
    }

    public List<JoinOnClause> getJoinOn() {
        return JoinOn;
    }
    
    public JoinClause addOn(JoinOnClause joinOn) {
        this.JoinOn.add(joinOn);
        return this;
    }
    
    public JoinClause addOn(JoinOnClause joinOn, boolean status) {
        if (status) {
            addOn(joinOn);
        }
        return this;
    }
}

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
public class WhereClause implements WhereClauseInterface {

    protected Object column;
    protected Object operator = "=";
    protected Object value;
    protected String condition = "AND";
    protected List<WhereClauseInterface> sub = new ArrayList<>();
    protected int index;
    
    public WhereClause() {
    }
    
    public WhereClause(String condition) {
        this.condition = condition;
    }
    
    public WhereClause(WhereClause sub) {
        this.sub.add(sub);
    }
    
    public WhereClause(WhereClause sub, String condition) {
        this.sub.add(sub);
        this.condition = condition;
    }
    
    public WhereClause(List<WhereClauseInterface> sub) {
        this.sub = sub;
    }
    
    public WhereClause(List<WhereClauseInterface> sub, String condition) {
        this.sub = sub;
        this.condition = condition;
    }
    
    public WhereClause(Object column, Object value) {
        this.column = column;
        this.value = value;
    }
    
    public WhereClause(Object column, Object operator, Object value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }
    
    public WhereClause(Object column, Object operator, Object value, String condition) {
        this.column = column;
        this.operator = operator;
        this.value = value;
        this.condition = condition;
    }

    @Override
    public Object getColumn() {
        return column;
    }

    @Override
    public Object getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String getCondition() {
        return condition;
    }
    
    @Override
    public List<WhereClauseInterface> getSub() {
        return sub;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public WhereClause setIndex(int index) {
        this.index = index;
        return this;
    }
    
    @Override
    public WhereClause addSub(WhereClauseInterface sub) {
        this.sub.add(sub);
        return this;
    }
    
    @Override
    public WhereClause addSub(WhereClauseInterface sub, boolean status) {
        if (status) {
            addSub(sub);
        }
        return this;
    }

    @Override
    public boolean hasClause() {
        return getColumn() != null && !getColumn().toString().isEmpty();
    }
}

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
public class WhereInClause implements WhereClauseInterface<WhereInClause>  {

    protected Object column;
    protected Object operator = "IN";
    protected Object[] values;
    protected String condition = "AND";
    protected List<WhereClauseInterface> sub = new ArrayList<>();
    protected int index;
    
    public WhereInClause() {
        this.condition = "AND";
    }
    
    public WhereInClause(String condition) {
        this.condition = condition;
    }
    
    public WhereInClause(WhereInClause sub) {
        this.sub.add(sub);
    }
    
    public WhereInClause(WhereInClause sub, String condition) {
        this.sub.add(sub);
        this.condition = condition;
    }
    
    public WhereInClause(List<WhereClauseInterface> sub) {
        this.sub = sub;
    }
    
    public WhereInClause(List<WhereClauseInterface> sub, String condition) {
        this.sub = sub;
        this.condition = condition;
    }
    
    public WhereInClause(Object column, Object[] values) {
        this.column = column;
        this.values = values;
    }
    
    public WhereInClause(Object column, Object operator, Object[] values) {
        this.column = column;
        this.operator = operator;
        this.values = values;
    }
    
    public WhereInClause(Object column, Object operator, Object[] values, String condition) {
        this.column = column;
        this.operator = operator;
        this.values = values;
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

    public Object[] getValues() {
        return values;
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
    public WhereInClause setIndex(int index) {
        this.index = index;
        return this;
    }
    
    @Override
    public WhereInClause addSub(WhereClauseInterface sub) {
        this.sub.add(sub);
        return this;
    }
    
    @Override
    public WhereInClause addSub(WhereClauseInterface sub, boolean status) {
        if (status) {
            addSub(sub);
        }
        return this;
    }
    
    public String getPlaceholder() {
        String placeholder = "";
        
        int size = values.length;
        for (int i = 0; i < size; i++) {
            placeholder += "?";
            
            if (i < size - 1) {
                placeholder += ", ";
            }
        }
        
        return placeholder;
    }

    @Override
    public boolean hasClause() {
        return getColumn() != null && !getColumn().toString().isEmpty();
    }
}

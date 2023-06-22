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
public class JoinOnClause {

    protected Object column;
    protected Object operator;
    protected Object value;
    protected String condition = "AND";
    protected List<JoinOnClause> subJoinOn = new ArrayList<>();
    protected int index;
    protected boolean asWhere = false;

    public JoinOnClause() {
        this.condition = "AND";
    }

    public JoinOnClause(String condition) {
        this.condition = condition;
    }

    public JoinOnClause(JoinOnClause subJoinOn) {
        this.subJoinOn.add(subJoinOn);
        this.condition = "AND";
    }

    public JoinOnClause(JoinOnClause subJoinOn, String condition) {
        this.subJoinOn.add(subJoinOn);
        this.condition = condition;
    }

    public JoinOnClause(List<JoinOnClause> subJoinOn) {
        this.subJoinOn = subJoinOn;
        this.condition = "AND";
    }

    public JoinOnClause(List<JoinOnClause> subJoinOn, String condition) {
        this.subJoinOn = subJoinOn;
        this.condition = condition;
    }

    public JoinOnClause(Object column, Object value) {
        this.column = column;
        this.operator = "=";
        this.value = value;
        this.condition = "AND";
    }

    public JoinOnClause(Object column, Object operator, Object value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
        this.condition = "AND";
    }

    public JoinOnClause(Object column, Object operator, Object value, String condition) {
        this.column = column;
        this.operator = operator;
        this.value = value;
        this.condition = condition;
    }

    public Object getColumn() {
        return column;
    }

    public Object getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    public String getCondition() {
        return condition;
    }

    public List<JoinOnClause> getSubJoinOn() {
        return subJoinOn;
    }

    public int getIndex() {
        return index;
    }

    public JoinOnClause setIndex(int index) {
        this.index = index;
        return this;
    }

    public boolean isAsWhere() {
        return asWhere;
    }

    public JoinOnClause asWhere() {
        this.asWhere = true;

        return this;
    }

    public JoinOnClause addSub(JoinOnClause on) {
        this.subJoinOn.add(on);
        return this;
    }

    public JoinOnClause addSub(JoinOnClause on, boolean status) {
        if (status) {
            addSub(on);
        }

        return this;
    }

    public boolean hasClause() {
        return getColumn() != null && !getColumn().toString().isEmpty();
    }
}

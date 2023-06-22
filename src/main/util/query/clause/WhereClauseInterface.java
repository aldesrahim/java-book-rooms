/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.util.query.clause;

import java.util.List;

/**
 *
 * @author aldes
 */
public interface WhereClauseInterface<T> {
    

    public Object getColumn();

    public Object getOperator();

    public String getCondition();
    
    public int getIndex();
    
    public T setIndex(int index);
    
    public List<WhereClauseInterface> getSub();
    
    public WhereClauseInterface addSub(WhereClauseInterface sub);
    
    public WhereClauseInterface addSub(WhereClauseInterface sub, boolean status);
    
    public boolean hasClause();
}

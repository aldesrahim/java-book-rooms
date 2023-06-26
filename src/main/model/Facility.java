/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import main.util.query.QueryUpdate;
import main.util.query.clause.OrderByClause;
import main.util.query.clause.SetClause;
import main.util.query.clause.WhereClause;

/**
 *
 * @author aldes
 */
public class Facility extends Model {

    private Long id;
    private String name;

    public Facility() {
    }

    public Facility(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTable() {
        return "facilities";
    }

    @Override
    public Facility scopeSearch(String term) {
        if (term == null || term.isEmpty()) {
            return this;
        }

        query()
                .addWhere(new WhereClause(
                        new WhereClause("name", "like", "%" + term + "%", "OR")
                ));

        return this;
    }

    @Override
    public QueryUpdate save() throws SQLException {
        QueryUpdate queryUpdate;

        query()
                .addSet(new SetClause("name", getName()));

        if (getId() == null) {
            queryUpdate = query()
                    .insert();

            setId(queryUpdate.getInsertedId());
        } else {
            queryUpdate = query()
                    .addWhere(new WhereClause(getPrimaryKey(), getId()))
                    .update();
        }

        return queryUpdate;
    }

    @Override
    public QueryUpdate delete() throws SQLException {
        return delete(getId());
    }

    @Override
    public Facility fromResultSet(ResultSet rs) throws SQLException {
        return new Facility(
                rs.getLong("id"),
                rs.getString("name")
        );
    }

    @Override
    public List all() throws SQLException {
        query()
                .addOrderBy(new OrderByClause("facilities.created_at", "desc"));
        
        return super.all();
    }
    

    @Override
    public String toString() {
        return "Facility{" + "id=" + id + ", name=" + name + '}';
    }
}

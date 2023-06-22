/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model;

import java.sql.SQLException;
import java.sql.ResultSet;
import main.util.query.QueryUpdate;
import main.util.query.clause.SetClause;
import main.util.query.clause.WhereClause;

/**
 *
 * @author aldes
 */
public class Consumption extends Model {

    private Long id;
    private String name;

    public Consumption() {
    }

    public Consumption(Long id, String name) {
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
        return "consumptions";
    }

    @Override
    public Consumption scopeSearch(String term) {
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
    public Consumption fromResultSet(ResultSet rs) throws SQLException {
        return new Consumption(
                rs.getLong("id"),
                rs.getString("name")
        );
    }

    @Override
    public String toString() {
        return "Consumption{" + "id=" + id + ", name=" + name + '}';
    }
}

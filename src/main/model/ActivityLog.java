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
public class ActivityLog extends Model {

    private Long id;
    private Long userId;
    private String externalType;
    private Long externalId;
    private String data;
    private String description;

    public ActivityLog() {
    }

    public ActivityLog(Long id, Long userId, String externalType, Long externalId, String data, String description) {
        this.id = id;
        this.userId = userId;
        this.externalType = externalType;
        this.externalId = externalId;
        this.data = data;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getExternalType() {
        return externalType;
    }

    public void setExternalType(String externalType) {
        this.externalType = externalType;
    }

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getTable() {
        return "activity_logs";
    }

    @Override
    public Object fromResultSet(ResultSet rs) throws SQLException {
        return new ActivityLog(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("external_type"),
                rs.getLong("external_id"),
                rs.getString("data"),
                rs.getString("description")
        );
    }

    @Override
    public ActivityLog scopeSearch(String term) {
        if (term == null || term.isEmpty()) {
            return this;
        }

        query()
                .addWhere(new WhereClause(
                        new WhereClause("description", "like", "%" + term + "%", "OR")
                ));

        return this;
    }

    @Override
    public QueryUpdate save() throws SQLException {
        QueryUpdate queryUpdate;

        query()
                .addSet(new SetClause("user_id", getUserId()))
                .addSet(new SetClause("external_type", getExternalType()))
                .addSet(new SetClause("external_id", getExternalId()))
                .addSet(new SetClause("data", getData()))
                .addSet(new SetClause("description", getDescription()));

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
    public List all() throws SQLException {
        query()
                .addOrderBy(new OrderByClause("activity_logs.created_at", "desc"));
        
        return super.all();
    }
    

    @Override
    public String toString() {
        return "ActivityLog{" + "id=" + id + ", userId=" + userId + ", externalType=" + externalType + ", externalId=" + externalId + ", data=" + data + ", description=" + description + '}';
    }
}

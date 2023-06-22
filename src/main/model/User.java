package main.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import main.util.query.QueryUpdate;
import main.util.query.clause.SetClause;
import main.util.query.clause.WhereClause;

public class User extends Model {

    private Long id;
    private String name;
    private String username;
    private String password;

    public User() {
    }

    public User(Long id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        if (id == null) {
            return false;
        }

        return id.equals(Long.valueOf(1));
    }

    @Override
    public String getTable() {
        return "users";
    }

    @Override
    public QueryUpdate save() throws SQLException {
        QueryUpdate queryUpdate;

        query()
                .addSet(new SetClause("name", getName()))
                .addSet(new SetClause("username", getUsername()))
                .addSet(new SetClause("password", getPassword()));

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
    public User fromResultSet(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("username"),
                rs.getString("password")
        );
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", username=" + username + '}';
    }

    @Override
    public User scopeSearch(String term) {
        query()
                .addWhere(new WhereClause()
                        .addSub(new WhereClause("users.name", "like", "%" + term + "%"))
                        .addSub(new WhereClause("users.username", "like", "%" + term + "%"))
                );

        return this;
    }

}

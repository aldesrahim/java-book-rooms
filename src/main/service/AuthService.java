package main.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import main.model.User;
import main.util.Password;
import main.util.query.QueryBuilder;
import main.util.query.QueryFetch;
import main.util.query.clause.WhereClause;

public class AuthService {

    public User attempt(String username, String password) throws SQLException, Exception {
        User user = new User();

        QueryFetch fetch = new QueryBuilder()
                .setFrom(user.getTable())
                .addWhere(new WhereClause("username", username))
                .fetch();

        ResultSet rs = fetch.get();

        if (rs.next()) {
            String hashedPassword = rs.getString("password");

            if (Password.verify(password, hashedPassword)) {
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(hashedPassword);

                return user;
            }
        }

        throw new Exception("Username atau password salah");
    }
}

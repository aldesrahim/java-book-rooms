package main.util.query;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 */
public class QueryHelper {

    public static boolean hasColumn(ResultSet rs, ResultSetMetaData rsmd, String columnName) throws SQLException {
        int columns = rsmd.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(rsmd.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();

        return hasColumn(rs, rsmd, columnName);
    }
}

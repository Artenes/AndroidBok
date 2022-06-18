package io.github.artenes.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDataRepository {

    private final String connectionName;

    public UserDataRepository(String dbName) throws SQLException {
        connectionName = "jdbc:sqlite:" + dbName;
        if (!migrate()) {
            throw new SQLException("Impossible to migrate database");
        }
    }

    public boolean insertAll(List<User> users) throws SQLException {
        String dataToInsert = usersToInsertClause(users);

        String insertClause = "INSERT INTO user_data (name, email) VALUES " + dataToInsert;

        try (Connection connection = DriverManager.getConnection(connectionName)) {
            Statement statement = connection.createStatement();
            return !statement.execute(insertClause);
        }
    }

    public int getCount() throws SQLException {
        String query = "SELECT count(*) as total FROM user_data";
        try (Connection connection = DriverManager.getConnection(connectionName)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("total");
        }
    }

    private String usersToInsertClause(List<User> users) {
        return users.stream()
                .map(user -> "('" + user.getName() + "', '" + user.getEmail() + "')")
                .reduce((first, second) -> first + ", " + second)
                .orElse("");
    }

    private boolean migrate() throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionName)) {
            Statement statement = connection.createStatement();
            return !statement.execute("CREATE TABLE IF NOT EXISTS user_data (name TEXT NOT NULL, email TEXT NOT NULL PRIMARY KEY)");
        }
    }

}

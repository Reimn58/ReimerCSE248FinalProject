package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
	private Connection connection;
	
	public LoginModel() {
		connection = SqliteConnection.connect();
		if(connection == null) {
			System.exit(1);
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public boolean isDBConnected() {
		try {
			return !connection.isClosed();
		} catch(SQLException e) {
			System.out.println("Error connecting to the DB");
			return false;
		}
	}
	
	public boolean isLogin(String username, String password) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from customers where username=? and password=?;";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
		return false;
	}

}
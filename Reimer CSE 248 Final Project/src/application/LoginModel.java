package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
	private Connection connection;
	private int currentAccountID;
	
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
		PreparedStatement preparedStatement2 = null;
		ResultSet resultSet = null;
		String query = "select * from customers where username=? and password=?;";
		String query2 = "update customers set isLoggedIn = '1' where id=?;";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				setCurrentAccountID(resultSet.getInt(1));
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			preparedStatement2 = connection.prepareStatement(query2);
			preparedStatement2.setInt(1, getCurrentAccountID());
			preparedStatement2.execute();
			preparedStatement2.close();
			resultSet.close();
		}
		return false;
	}

	public int getCurrentAccountID() {
		return currentAccountID;
	}

	public void setCurrentAccountID(int currentAccountID) {
		this.currentAccountID = currentAccountID;
	}

}
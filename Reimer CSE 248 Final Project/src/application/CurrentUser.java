package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrentUser {

	private int id;
	
	public CurrentUser() {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from customers where isLoggedIn = '1';";
		try {
			preparedStatement = new LoginModel().getConnection().prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			this.id = resultSet.getInt(1);
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getID() {
		return id;
	}
}

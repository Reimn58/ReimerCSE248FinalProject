package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqliteConnection {
	public static Connection connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:HumanityDB.sqlite");
			return conn;
		} catch(Exception e) {
			return null;
		}
	}

}
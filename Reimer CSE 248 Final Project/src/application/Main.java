package application;
	
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Habitat for Humanity Restore");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setOnCloseRequest(e -> {
				PreparedStatement preparedStatement2 = null;
				String query2 = "update customers set isLoggedIn = '0' where id=?;";
				int currentID = 0;
				try {
					currentID = new CurrentUser().getID();
				} finally {
					try {
						preparedStatement2 = new LoginModel().getConnection().prepareStatement(query2);
						preparedStatement2.setInt(1, currentID);
						preparedStatement2.execute();
						preparedStatement2.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
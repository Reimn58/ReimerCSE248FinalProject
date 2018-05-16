package application;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuController implements Initializable {
	
	public void logout(ActionEvent event) throws IOException, SQLException {
		
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		ResultSet resultSet = null;
		String query = "select * from customers where isLoggedIn = '1';";
		String query2 = "update customers set isLoggedIn = '0' where id=?;";
		int currentID = 0;
		try {
			preparedStatement = new LoginModel().getConnection().prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			currentID = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resultSet.close();
			preparedStatement.close();
			preparedStatement2 = new LoginModel().getConnection().prepareStatement(query2);
			preparedStatement2.setInt(1, currentID);
			preparedStatement2.execute();
			preparedStatement2.close();
		}
		
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void displayItems(ActionEvent event) throws IOException, SQLException {
		Parent root = FXMLLoader.load(getClass().getResource("DisplayItems.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		DisplayItemsController display = new DisplayItemsController();
		display.generateDisplay();
	}
	
	public void donateItem(ActionEvent event) throws IOException, SQLException {
		Parent root = FXMLLoader.load(getClass().getResource("DonateItem.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}

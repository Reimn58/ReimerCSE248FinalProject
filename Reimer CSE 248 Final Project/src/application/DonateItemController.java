package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DonateItemController {

	@FXML
	private TextField itemName = new TextField();
	@FXML
	private TextField quantity = new TextField();
	@FXML
	private Label msgLabel = new Label();
	
	public void donate(ActionEvent event) throws SQLException, IOException {
		Connection connection = new LoginModel().getConnection();
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from customers where isLoggedIn = '1';";
		preparedStatement = new LoginModel().getConnection().prepareStatement(query);
		resultSet = preparedStatement.executeQuery();
		int currentID = resultSet.getInt(1);
		resultSet.close();
		preparedStatement.close();
		
		PreparedStatement preparedStatement2 = null;
		String query2 = "INSERT INTO Donations (accountID, itemName, quantity, date) VALUES(?, ?, ?, ?)";
		try {
			preparedStatement2 = connection.prepareStatement(query2);
			preparedStatement2.setInt(1, currentID);
			preparedStatement2.setString(2, itemName.getText());
			while(true) {
				if(itemName.getText().length() == 0) {
					msgLabel.setText("Please enter an item name.");
					return;
				}
				try {
					preparedStatement2.setInt(3, Integer.parseInt(quantity.getText()));
					break;
				} catch (NumberFormatException e) {
					msgLabel.setText("Quantity must be a number, please try again.");
					return;
				}
			}
			preparedStatement2.setLong(4, new Date().getTime());
			preparedStatement2.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatement2.close();
		}
		
		Random gen = new Random();
		
		PreparedStatement preparedStatement3 = null;
		
		String query3 = "select * from Inventory where itemName=?";
		preparedStatement3 = connection.prepareStatement(query3);
		preparedStatement3.setString(1, itemName.getText());
		ResultSet resultSet2 = preparedStatement3.executeQuery();
		if(resultSet2.next()) {
			int currentQuantity = resultSet2.getInt(4);
			preparedStatement3.close();
			resultSet2.close();
			
			PreparedStatement preparedStatement4 = null;
			String query4 = "update inventory set quantity=? where itemName=?;";
			preparedStatement4 = connection.prepareStatement(query4);
			preparedStatement4.setInt(1, currentQuantity + Integer.parseInt(quantity.getText()));
			preparedStatement4.setString(2, itemName.getText());
			preparedStatement4.execute();
			msgLabel.setText("Quantity must be a number, please try again.");
			preparedStatement4.close();
		} else {
			PreparedStatement preparedStatement4 = null;
			String query4 = "INSERT INTO Inventory (itemName, itemPrice, quantity) VALUES(?,?,?)";
			preparedStatement4 = connection.prepareStatement(query4);
			preparedStatement4.setString(1, itemName.getText());
			preparedStatement4.setDouble(2, gen.nextInt(100));
			preparedStatement4.setInt(3, Integer.parseInt(quantity.getText()));
			preparedStatement4.execute();
			preparedStatement4.close();
		}
		
		Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void mainMenu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
}
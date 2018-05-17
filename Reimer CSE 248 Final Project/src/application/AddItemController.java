package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddItemController {

	@FXML
	private Label msgLabel = new Label();
	
	@FXML
	private TextField itemNameField = new TextField();
	
	@FXML
	private TextField itemPriceField = new TextField();
	
	@FXML
	private TextField quantityField = new TextField();
	
	public void add(ActionEvent event) throws IOException {
		PreparedStatement pS = null;
		String query = "INSERT INTO Inventory (itemName, itemPrice, quantity) VALUES (?,?,?);";
		Connection connection = new LoginModel().getConnection();
		
		if(itemNameField.getText().length() > 0 && itemPriceField.getText().length() > 0 && quantityField.getText().length() > 0) {
			try {
				pS = connection.prepareStatement(query);
				pS.setString(1, itemNameField.getText());
				pS.setDouble(2, Double.parseDouble(itemPriceField.getText()));
				pS.setInt(3, Integer.parseInt(quantityField.getText()));
				pS.execute();
				pS.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} else {
			msgLabel.setText("Please enter all of the information.");
		}
	}
	
	public void mainMenu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void adminArea(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
}

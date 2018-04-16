package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateNewAccountController {

	LoginModel loginModel = new LoginModel();
	Connection connection = loginModel.getConnection();
	@FXML
	private Label messageLbl;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField confirmPasswrodField;
	
	public void create(ActionEvent event) throws SQLException, IOException {
		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO Customers (username, password) VALUES(?, ?)";
		if(passwordField.getText().equals(confirmPasswrodField.getText()) && passwordField.getText().length() > 2) {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, usernameField.getText());
			preparedStatement.setString(2, passwordField.getText());
			preparedStatement.execute();
			Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
			
		} else if(passwordField.getText().length() <= 2) {
			messageLbl.setText("Invalid password, please make sure your password at least 3 characters");
		} else {
			messageLbl.setText("Passwords do not match, please try again");
		}
	}
}
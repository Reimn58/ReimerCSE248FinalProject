package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	private LoginModel loginModel = new LoginModel();
	@FXML
	private Label messageLbl;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;

	public void login(ActionEvent event) throws SQLException, IOException {
		if(loginModel.isLogin(usernameField.getText(), passwordField.getText())) {
			Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} else {
			messageLbl.setText("Login failure!");
		}
	}
	
	public void createNewAccount(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("CreateNewAccount.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (loginModel.isDBConnected()) {
			messageLbl.setText("Connected");
		} else {
			messageLbl.setText("Not Connected");
		}
	}

}
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
		
		PreparedStatement preparedStatement2 = null;
		String query2 = "update customers set isLoggedIn = '0' where id=?;";
		int currentID = 0;
		try {
			currentID = new CurrentUser().getID();
		} finally {
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
	
	public void displayMyCart(ActionEvent event) throws SQLException, IOException {
		Parent root = FXMLLoader.load(getClass().getResource("MyCart.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		MyCartController myCart = new MyCartController();
		myCart.generateDisplay();
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
	
	public void checkOut(ActionEvent event) throws SQLException, IOException {
		InvoiceController invoice = new InvoiceController();
		invoice.generateDisplay();
		Parent root = FXMLLoader.load(getClass().getResource("Invoice.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void salesReport(ActionEvent event) throws SQLException, IOException {
		DonationHistoryController report = new DonationHistoryController();
		report.generateDisplay();
		Parent root = FXMLLoader.load(getClass().getResource("DonationHistory.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void adminArea(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AdminPassword.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void returnItem(ActionEvent event) throws IOException, SQLException {
		ReturnItemController ric = new ReturnItemController();
		ric.generateDisplay();
		Parent root = FXMLLoader.load(getClass().getResource("ReturnItem.fxml"));
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

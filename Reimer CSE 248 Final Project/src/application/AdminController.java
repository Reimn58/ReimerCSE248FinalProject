package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminController {
	
	public void salesReport(ActionEvent event) throws SQLException, IOException{
		SalesReportController src = new SalesReportController();
		src.generateDisplay();
		Parent root = FXMLLoader.load(getClass().getResource("SalesReport.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void donations(ActionEvent event) throws SQLException, IOException {
		DonationHistoryController dhc = new DonationHistoryController();
		dhc.generateDisplay();
		Parent root = FXMLLoader.load(getClass().getResource("DonationHistory.fxml"));
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
	
	public void returnReport(ActionEvent event) throws IOException, SQLException {
		ReturnReportController rrc = new ReturnReportController();
		rrc.generateDisplay();
		Parent root = FXMLLoader.load(getClass().getResource("ReturnReport.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void addItem(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AddItem.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
}

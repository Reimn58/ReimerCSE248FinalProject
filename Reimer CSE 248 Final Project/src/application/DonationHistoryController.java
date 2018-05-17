package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DonationHistoryController implements Initializable {
	
	@FXML
	private TableView<Donation> itemTable = new TableView<>();
	
	@FXML 
	private TableColumn<Donation, Integer> donatorIDColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Donation, String> itemNameColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Donation, Integer> itemQuantityColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Donation, Date> dateDonatedColumn = new TableColumn<>();
	
	public void generateDisplay() throws SQLException {
		itemTable.setItems(getItems());
	}
	
	public ObservableList<Donation> getItems() throws SQLException {
		ObservableList<Donation> items = FXCollections.observableArrayList();
		LoginModel loginModel = new LoginModel();
		Connection connection = loginModel.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from Donations;";
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int donatorID = resultSet.getInt(2);
				String itemName = resultSet.getString(3);
				int quantity = resultSet.getInt(4);
				Date date = resultSet.getDate(5);
				
				
				items.add(new Donation(donatorID, itemName, quantity, date));
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
		return items;
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		donatorIDColumn.setCellValueFactory(new PropertyValueFactory<>("donatorID"));
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		itemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		dateDonatedColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		try {
			itemTable.setItems(getItems());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
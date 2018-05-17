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

public class ReturnReportController implements Initializable {

	
	@FXML
	private TableView<Returns> itemTable = new TableView<>();
	
	@FXML 
	private TableColumn<Returns, Integer> customerIDColumn = new TableColumn<>();
	
	@FXML 
	private TableColumn<Returns, Integer> itemNameColumn = new TableColumn<>();
	
	@FXML 
	private TableColumn<Returns, Integer> quantityColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Returns, Double> totalPriceColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Returns, Date> dateReturnedColumn = new TableColumn<>();
	
	public void generateDisplay() throws SQLException {
		itemTable.setItems(getItems());
	}
	
	public ObservableList<Returns> getItems() throws SQLException {
		ObservableList<Returns> items = FXCollections.observableArrayList();
		LoginModel loginModel = new LoginModel();
		Connection connection = loginModel.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from Returns;";
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int customerID = resultSet.getInt(2);
				String itemName = resultSet.getString(3);
				double totalPrice = resultSet.getDouble(4);
				int quantity = resultSet.getInt(5);
				Date date = resultSet.getDate(6);
				totalPrice = totalPrice * 100;
				totalPrice = (int) totalPrice;
				totalPrice = totalPrice / 100;
				
				items.add(new Returns(customerID, itemName, totalPrice, quantity, date));
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
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
		totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("moneyReturned"));
		dateReturnedColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		try {
			itemTable.setItems(getItems());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DisplayItemsController implements Initializable {

	@FXML
	private TableView<Item> itemTable = new TableView<>();
	
	@FXML 
	private TableColumn<Item, String> itemNameColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Item, Double> itemPriceColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Item, Integer> itemQuantityColumn = new TableColumn<>();
	
	
	public void generateDisplay() throws SQLException {
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
		itemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		
		itemTable.setItems(getItems());
		itemTable.getColumns().addAll(itemNameColumn, itemPriceColumn, itemQuantityColumn);
	}
	
	public ObservableList<Item> getItems() throws SQLException {
		ObservableList<Item> accounts = FXCollections.observableArrayList();
		LoginModel loginModel = new LoginModel();
		Connection connection = loginModel.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from inventory;";
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				accounts.add(new Item(resultSet.getString(1), resultSet.getDouble(2), resultSet.getInt(3)));
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
		return accounts;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
		itemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		try {
			itemTable.setItems(getItems());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
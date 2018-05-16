package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DisplayItemsController implements Initializable {

	@FXML
	private TableView<Item> itemTable = new TableView<>();
	
	@FXML 
	private TableColumn<Item, String> itemNameColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Item, Double> itemPriceColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Item, Integer> itemQuantityColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Item, Button> purchaseItemColumn = new TableColumn<>();
	
	public void generateDisplay() throws SQLException {
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
		itemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		purchaseItemColumn.setCellValueFactory(new PropertyValueFactory<>("button"));
		
		itemTable.setItems(getItems());
		itemTable.getColumns().addAll(itemNameColumn, itemPriceColumn, itemQuantityColumn, purchaseItemColumn);
	}
	
	public ObservableList<Item> getItems() throws SQLException {
		ObservableList<Item> items = FXCollections.observableArrayList();
		LoginModel loginModel = new LoginModel();
		Connection connection = loginModel.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from inventory;";
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				String itemName = resultSet.getString(2);
				Double itemPrice = resultSet.getDouble(3);
				int quantity = resultSet.getInt(4);
				Button button = new Button("Add to cart");
				
				button.setOnAction(e -> {
					PreparedStatement preparedStatementGetUser = null;
					ResultSet resultSetGetUser = null;
					String queryGetUser = "select * from customers where isLoggedIn = '1';";
					try {
						preparedStatementGetUser = new LoginModel().getConnection().prepareStatement(queryGetUser);
						resultSetGetUser = preparedStatementGetUser.executeQuery();
						int currentID = resultSetGetUser.getInt(1);
						resultSetGetUser.close();
						preparedStatementGetUser.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					System.out.println(itemName);
				});
				
				items.add(new Item(itemName, itemPrice, quantity, button));
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
		itemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		purchaseItemColumn.setCellValueFactory(new PropertyValueFactory<>("button"));
		try {
			itemTable.setItems(getItems());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
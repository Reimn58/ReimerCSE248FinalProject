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

public class MyCartController implements Initializable {
	
	@FXML
	private TableView<Item> itemTable = new TableView<>();
	
	@FXML 
	private TableColumn<Item, String> itemNameColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Item, Double> itemPriceColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Item, Integer> itemQuantityColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Item, Button> removeFromCartColumn = new TableColumn<>();
	
	public void generateDisplay() throws SQLException {
		
		itemTable.setItems(getItems());
	}
	
	public ObservableList<Item> getItems() throws SQLException {
		ObservableList<Item> items = FXCollections.observableArrayList();
		Connection connection = new LoginModel().getConnection();
		int currentID = new CurrentUser().getID();
		PreparedStatement preparedStatement2 = null;
		ResultSet resultSet2 = null;
		String query2 = "select * from ItemsInCart where userID=?;";
		try {
			preparedStatement2 = connection.prepareStatement(query2);
			preparedStatement2.setInt(1, currentID);
			resultSet2 = preparedStatement2.executeQuery();
			while(resultSet2.next()) {
				String itemName = resultSet2.getString(2);
				Double itemPrice = resultSet2.getDouble(3);
				int quantity = resultSet2.getInt(4);
				Button button = new Button("Remove from cart");
				items.add(new Item(itemName, itemPrice, quantity, button));
				
				button.setOnAction(e -> {
					PreparedStatement preparedStatement = null;
					String query = "delete from ItemsInCart where userID=? and itemName=?;";
					try {
						preparedStatement = connection.prepareStatement(query);
						preparedStatement.setInt(1, currentID);
						preparedStatement.setString(2, itemName);
						preparedStatement.execute();
						preparedStatement.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					try {
						generateDisplay();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					PreparedStatement preparedStatement3 = null;
					ResultSet resultSet3 = null;
					String query3 = "select * from Inventory where itemName=?;";
					try {
						preparedStatement3 = connection.prepareStatement(query3);
						preparedStatement3.setString(1, itemName);
						resultSet3 = preparedStatement3.executeQuery();
						int currentQuantity = resultSet3.getInt(4);
						preparedStatement3.close();
						resultSet3.close();
						PreparedStatement preparedStatement4 = null;
						String query4 = "update inventory set quantity=? where itemName=?;";
						preparedStatement4 = connection.prepareStatement(query4);
						preparedStatement4.setInt(1, currentQuantity + quantity);
						preparedStatement4.setString(2, itemName);
						preparedStatement4.execute();
						preparedStatement4.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatement2.close();
			resultSet2.close();
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
	
	public void checkOut(ActionEvent event) throws IOException, SQLException {
		InvoiceController invoice = new InvoiceController();
		invoice.generateDisplay();
		Parent root = FXMLLoader.load(getClass().getResource("Invoice.fxml"));
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
		removeFromCartColumn.setCellValueFactory(new PropertyValueFactory<>("button"));
		try {
			itemTable.setItems(getItems());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

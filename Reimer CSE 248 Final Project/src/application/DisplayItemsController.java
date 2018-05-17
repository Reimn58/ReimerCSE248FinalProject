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
import javafx.scene.control.Label;
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
	
	@FXML
	private Label msgLabel = new Label();
	
	public void generateDisplay() throws SQLException {
		itemTable.setItems(getItems());
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
					
					if(quantity == 0) {
						msgLabel.setText("Sorry, we are out of that item.");
					} else {
					
						int currentID = new CurrentUser().getID();
					
						PreparedStatement preparedStatement2 = null;
						String query2 = "update Inventory set quantity=? where itemName=?;";
						try {
							preparedStatement2 = connection.prepareStatement(query2);
							preparedStatement2.setInt(1, quantity - 1);
							preparedStatement2.setString(2, itemName);
							preparedStatement2.execute();
							preparedStatement2.close();
						} catch (SQLException e3) {
							e3.printStackTrace();
						}
					
						PreparedStatement preparedStatement3 = null;
					
						String query3 = "select * from ItemsInCart where itemName=? AND userID=?";
						try {
							preparedStatement3 = connection.prepareStatement(query3);
							preparedStatement3.setString(1, itemName);
							preparedStatement3.setInt(2, currentID);
							ResultSet resultSet2 = preparedStatement3.executeQuery();
							if(resultSet2.next()) {
								int currentQuantity = resultSet2.getInt(4);
								preparedStatement3.close();
								resultSet2.close();
								
								PreparedStatement preparedStatement4 = null;
								String query4 = "update ItemsInCart set quantity=? where itemName=? AND userID=?;";
								preparedStatement4 = connection.prepareStatement(query4);
								preparedStatement4.setInt(1, currentQuantity + 1);
								preparedStatement4.setString(2, itemName);
								preparedStatement4.setInt(3, currentID);
								preparedStatement4.execute();
								
								preparedStatement4.close();
							} else {
								PreparedStatement pSInsertCart = null;
								ResultSet rsInsertCart = null;
								String queryInsertCart = "INSERT INTO ItemsInCart (itemName, itemPrice, quantity, userID) VALUES (?,?,?,?)";
								try {
									pSInsertCart = connection.prepareStatement(queryInsertCart);
									pSInsertCart.setString(1, itemName);
									pSInsertCart.setDouble(2, itemPrice);
									pSInsertCart.setInt(3, 1);
									pSInsertCart.setInt(4, currentID);
									pSInsertCart.execute();
									pSInsertCart.close();
								} catch (SQLException e2) {
									
									e2.printStackTrace();
								}
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						} 
						try {
							generateDisplay();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
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
	
	public void search(ActionEvent event) {
		
	}
	
	public void myCart(ActionEvent event) throws SQLException, IOException {
		MyCartController myCart = new MyCartController();
		myCart.generateDisplay();
		Parent root = FXMLLoader.load(getClass().getResource("MyCart.fxml"));
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
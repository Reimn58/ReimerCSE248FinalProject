package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class InvoiceController implements Initializable {

	@FXML
	private TableView<Item> itemTable = new TableView<>();
	
	@FXML 
	private TableColumn<Item, String> itemNameColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Item, Double> itemPriceColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Item, Integer> itemQuantityColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Item, Double> itemTotalPriceColumn = new TableColumn<>();
	
	@FXML
	private Label msgLabel = new Label();
	
	@FXML
	private Label subtotalLabel = new Label();;
	
	@FXML
	private Label taxLabel = new Label();
	
	@FXML
	private Label totalLabel = new Label();
	
	@FXML
	private TextField textFieldFirstName =  new TextField();
	
	@FXML
	private TextField textFieldLastName =  new TextField();
	
	@FXML
	private TextField textFieldShippingAddress =  new TextField();
	
	@FXML
	private TextField textFieldCity =  new TextField();
	
	@FXML
	private TextField textFieldCardNumber =  new TextField();
	
	@FXML
	private TextField textFieldExpirationDate  =  new TextField();
	
	@FXML
	private TextField textFieldCVV =  new TextField();
	
	private double subtotal = 0;
	
	private int quantityItems = 0;
	
	
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
				items.add(new Item(itemName, itemPrice, quantity));
				subtotal = subtotal + (double)(itemPrice * quantity);
				quantityItems = quantityItems + quantity;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatement2.close();
			resultSet2.close();
		}
		
		double tax = subtotal * 0.08875;
		double totalPrice = subtotal + tax;
		subtotal = subtotal * 100;
		subtotal = (int) subtotal;
		subtotal = subtotal / 100;
		tax = tax * 100;
		tax = (int) tax;
		tax = tax / 100;
		totalPrice = totalPrice * 100;
		totalPrice = (int) totalPrice;
		totalPrice = totalPrice / 100;
		subtotalLabel.setText("$" + Double.toString(subtotal));
		taxLabel.setText("$" + Double.toString(tax));
		totalLabel.setText("$" + Double.toString(totalPrice));
		return items;
	}
	
	public void submitOrder(ActionEvent event) throws IOException {
		
		if(textFieldFirstName.getText().length() > 0 && textFieldLastName.getText().length() > 0 && textFieldShippingAddress.getText().length() > 0 &&
				textFieldCity.getText().length() > 0 && textFieldCardNumber.getText().length() > 0 && textFieldExpirationDate.getText().length() > 0 &&
				textFieldCVV.getText().length() > 0) {
			
			Connection connection = new LoginModel().getConnection();
			
			PreparedStatement preparedStatement3 = null;
			ResultSet resultSet3 = null;
			String query3 = "select * from ItemsInCart where userID=?;";
			ArrayList<Sale> salesList = new ArrayList<>();
			try {
				preparedStatement3 = connection.prepareStatement(query3);
				preparedStatement3.setInt(1, new CurrentUser().getID());
				resultSet3 = preparedStatement3.executeQuery();
				while(resultSet3.next()) {
					
					salesList.add(new Sale(new CurrentUser().getID(), resultSet3.getString(2), (resultSet3.getInt(4) * resultSet3.getDouble(3) * 1.08875), 
							          resultSet3.getInt(4), new Date()));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				try {
					preparedStatement3.close();
					resultSet3.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			for(int i = 0; i < salesList.size(); i++) {
				Sale currentSale = salesList.get(i);
				PreparedStatement preparedStatement = null;
				String query = "INSERT INTO Sales (userID, itemName, totalPrice, quantity, date) VALUES(?, ?, ?, ?, ?)";
				try {
					preparedStatement = new LoginModel().getConnection().prepareStatement(query);
					preparedStatement.setInt(1, currentSale.getCustomerID());
					preparedStatement.setString(2, currentSale.getItemName());
					preparedStatement.setDouble(3, currentSale.getTotalPrice());
					preparedStatement.setInt(4, currentSale.getQuantity());
					preparedStatement.setLong(5, currentSale.getDate().getTime());
					preparedStatement.execute();
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			PreparedStatement preparedStatement2 = null;
			String query2 = "DELETE from ItemsInCart where userID=?;";
			try {
				preparedStatement2 = new LoginModel().getConnection().prepareStatement(query2);
				preparedStatement2.setInt(1, new CurrentUser().getID());
				preparedStatement2.execute();
				preparedStatement2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} else {
			msgLabel.setText("Please fill out ALL of the information and try again:");
			return;
		}
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
		itemTotalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
		try {
			itemTable.setItems(getItems());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void mainMenu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
}
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ReturnItemController implements Initializable{

	@FXML
	private TableView<Returns> itemTable = new TableView<>();
	
	@FXML
	private TableColumn<Returns, String> itemNameColumn = new TableColumn<>();
	
	@FXML 
	private TableColumn<Returns, Double> totalPriceColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Returns, Double> datePurchasedColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Returns, Integer> quantityItemsPurchasedColumn = new TableColumn<>();
	
	@FXML
	private TableColumn<Returns, Button> returnItemColumn = new TableColumn<>();
	
	public void generateDisplay() throws SQLException {
		itemTable.setItems(getItems());
	}
	
	public ObservableList<Returns> getItems() throws SQLException {
		ObservableList<Returns> items = FXCollections.observableArrayList();
		LoginModel loginModel = new LoginModel();
		Connection connection = loginModel.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from Sales where userID=? and isReturned='0';";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, new CurrentUser().getID());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				String itemName = resultSet.getString(3);
				double totalPrice = resultSet.getDouble(4);
				int quantity = resultSet.getInt(5);
				Date date = resultSet.getDate(6);
				int saleID = resultSet.getInt(1);
				Button button = new Button("Return Item");
				
				button.setOnAction(e -> {
					
					int currentID = new CurrentUser().getID();
					
					PreparedStatement preparedStatement2 = null;
					String query2 = "INSERT INTO Returns (userID, itemName, totalPrice, quantity, date) VALUES (?,?,?,?,?)";
					try {
						
						preparedStatement2 = connection.prepareStatement(query2);
						preparedStatement2.setInt(1, currentID);
						preparedStatement2.setString(2, itemName);
						preparedStatement2.setDouble(3, totalPrice);
						preparedStatement2.setInt(4, quantity);
						preparedStatement2.setLong(5, new Date().getTime());
						preparedStatement2.execute();
						preparedStatement2.close();
					} catch (SQLException e3) {
						e3.printStackTrace();
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
					
					PreparedStatement preparedStatement5 = null;
					ResultSet resultSet5 = null;
					String query5 = "update Sales set isReturned='1' where id=?;";
					try {
						preparedStatement5 = new LoginModel().getConnection().prepareStatement(query5);
						preparedStatement5.setInt(1, saleID);
						preparedStatement5.execute();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					
					try {
						generateDisplay();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				});
				
				items.add(new Returns(new CurrentUser().getID(), itemName, totalPrice, quantity, date, button));
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
		
		return items;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("moneyReturned"));
		datePurchasedColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		quantityItemsPurchasedColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		returnItemColumn.setCellValueFactory(new PropertyValueFactory<>("button"));
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

package application;

import java.util.Date;

import javafx.scene.control.Button;

public class Returns {
	
	private int userID;
	private String itemName;
	private double moneyReturned;
	private int quantity;
	private Date date;
	private Button button;
	
	public Returns(int userID, double moneyReturned, int quantity, Date date) {
		super();
		this.userID = userID;
		this.moneyReturned = moneyReturned;
		this.quantity = quantity;
		this.date = date;
	}
	
	public Returns(int userID, String itemName, double moneyReturned, int quantity, Date date, Button button) {
		super();
		this.userID = userID;
		this.setItemName(itemName);
		this.moneyReturned = moneyReturned;
		this.quantity = quantity;
		this.date = date;
		this.setButton(button);
	}
	
	public Returns(int userID, String itemName, double moneyReturned, int quantity, Date date) {
		super();
		this.userID = userID;
		this.setItemName(itemName);
		this.moneyReturned = moneyReturned;
		this.quantity = quantity;
		this.date = date;
	}
	
	public Returns(int userID, double moneyReturned, int quantity, Button button) {
		super();
		this.userID = userID;
		this.moneyReturned = moneyReturned;
		this.quantity = quantity;
		this.setButton(button);
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public double getMoneyReturned() {
		return moneyReturned;
	}

	public void setMoneyReturned(double moneyReturned) {
		this.moneyReturned = moneyReturned;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

}

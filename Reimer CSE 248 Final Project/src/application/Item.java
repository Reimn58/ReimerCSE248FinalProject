package application;

import javafx.scene.control.Button;

public class Item {

	private String itemName;
	private double itemPrice;
	private int quantity;
	private Button button;
	
	public Item(String itemName, double itemPrice, int quantity, Button button) {
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.quantity = quantity;
		this.button = button;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}
}
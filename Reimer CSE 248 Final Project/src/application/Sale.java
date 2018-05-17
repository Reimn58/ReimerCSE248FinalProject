package application;

import java.util.Date;

public class Sale {
	
	private int userID;
	private String itemName;
	private double totalPrice;
	private int quantity;
	private Date date;

	public Sale(int userID, String itemName, double totalPrice, int quantity, Date date) {
		super();
		this.userID = userID;
		this.setItemName(itemName);
		this.totalPrice = totalPrice;
		this.setQuantity(quantity);
		this.date = date;
	}


	public int getCustomerID() {
		return userID;
	}

	public void setCustomerID(int customerID) {
		this.userID = customerID;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

}

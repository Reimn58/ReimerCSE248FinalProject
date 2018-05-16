package application;

import java.util.Date;

public class Donation {

	private int accountID;
	private String itemName;
	private int quantity;
	private Date date;
	
	public Donation(int accountID, String itemName, int quantity) {
		this.accountID = accountID;
		this.itemName = itemName;
		this.quantity = quantity;
		this.date = new Date();
	}
	
	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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
}

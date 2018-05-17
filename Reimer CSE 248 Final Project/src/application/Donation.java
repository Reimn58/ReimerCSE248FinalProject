package application;

import java.util.Date;

public class Donation {

	private int donatorID;
	private String itemName;
	private int quantity;
	private Date date;
	
	public Donation(int donatorID, String itemName, int quantity, Date date) {
		this.donatorID = donatorID;
		this.itemName = itemName;
		this.quantity = quantity;
		this.date = date;
	}
	
	public int getDonatorID() {
		return donatorID;
	}

	public void setDonatorID(int donatorID) {
		this.donatorID = donatorID;
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

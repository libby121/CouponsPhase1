package beans;

import java.util.ArrayList;

public class Company {

	private int id;
	private String name;
	private String email;
	private String password;
	/**
	 * I added a balance variable to Java and sql table, 
	 * the company balance is updated with a customer purchase.
	 */
	private double balance;  

	private ArrayList<Coupon> coupons;

	public Company(String name, String email, String password, double balance) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.balance = balance;
	}

	public Company(int id, String name, String email, String password,
			double balance) {
		// a CTOR for reading a company from sql table
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.balance = balance;
	}

	
	public Company(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", email=" + email
				+ ", password=" + password + ", balance=" + balance + "]";
	}

}

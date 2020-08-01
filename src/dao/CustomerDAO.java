package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Customer;
/**
 * DAO interfaces represent the atomic commands and  basic communication with the database.
 * all methods are automatically public and are realized by DBDAO's.
 * 
 *
 */
public interface CustomerDAO {

	boolean isCustomerExist(String email,String password)throws SQLException;
	void addCustomer(Customer customer)throws SQLException;
	void updateCustomer(Customer customer)throws SQLException;
	void deleteCustomer(int id)throws SQLException;
	void deleteCustomerPurchase(int id)throws SQLException;
	ArrayList<Customer>getAllcustomers()throws SQLException;
	Customer getOneCustmer (int id)throws SQLException;
}

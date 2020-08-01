package facades;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import dao.CompanyDBDAO;
import exceptions.CompanyExistsException;
import exceptions.CustomerExistsException;
import exceptions.noSuchCompanyException;

public class AdminFacade extends Facade {

	public AdminFacade() {
	}

	public boolean login(String email, String password) throws SQLException {
		if (email.equals("com.admin@admin") && password.equals("admain")) {

			return true;

		}

		return false;
	}

	public void addCompany(Company company) throws SQLException,
	CompanyExistsException {

		for (Company compa : compDBD.getAllCompanies()) {
			if ((company.getName().equals(compa.getName()))

					|| compa.getEmail().equals(company.getEmail())) {
				throw new CompanyExistsException();

			}
		}
		compDBD.addCompany(company);

	}

	public void updateCompany(Company company) throws SQLException {
		compDBD.updateCompany(company);

	}

	public void deleteCompany(int id) throws SQLException {

		for (Coupon coupon : coupDBD.getCouponsByCompanyId(id)) {
			coupDBD.deleteCouponPurchase(coupon.getId());
			coupDBD.deleteCoupon(coupon.getId());
		}
		compDBD.deleteCompany(id);


	}



	public void deleteCustomer(int customerID) throws SQLException{
		custDBD.deleteCustomerPurchase(customerID);
		
		custDBD.deleteCustomer(customerID);
	}


	public ArrayList<Company> getAllCompanies() throws SQLException {
		return compDBD.getAllCompanies();

	}

	public Company getOneCompany(int id) throws SQLException, noSuchCompanyException{

		return compDBD.getOneCompany(id);

	}

	public void addCustomer(Customer customr) throws SQLException,
	CustomerExistsException {
		for (Customer customer1 : custDBD.getAllcustomers()) {
			if (customer1.getEmail().equals(customr.getEmail())) {
				throw new CustomerExistsException();
			}
		}
		custDBD.addCustomer(customr);

	}

	public void updateCustomer(Customer customer) throws SQLException {

		custDBD.updateCustomer(customer);
	}

	public ArrayList<Customer> getAllCustomers() throws SQLException {
		return custDBD.getAllcustomers();
	}

	public ArrayList<Coupon>getCouponsByCompanyId(int id) throws SQLException{
		return coupDBD.getCouponsByCompanyId(id);
	}
}


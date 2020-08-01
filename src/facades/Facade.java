package facades;

import java.sql.SQLException;

import dao.CompanyDAO;
import dao.CompanyDBDAO;
import dao.CouponDBDAO;
import dao.CustomerDBDAO;
import exceptions.CompanyDoesntExistException;

public abstract class Facade {
	
	protected CompanyDAO compDBD = new CompanyDBDAO();
	protected CouponDBDAO coupDBD = new CouponDBDAO();
	protected CustomerDBDAO custDBD = new CustomerDBDAO();
	public abstract boolean login(String email,String password) throws SQLException, CompanyDoesntExistException;//method will be overridden in each case separately
	
}

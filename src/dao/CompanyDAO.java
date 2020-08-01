package dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import exceptions.noSuchCompanyException;
import beans.Company;
import beans.Coupon;

/**
 * DAO interfaces represent the atomic commands and  basic communication with the database.
 * all methods are automatically public and are realized by DBDAO's.
 * @author μιαι
 *
 */
public interface CompanyDAO {

	boolean isCompanyExists(String email, String password)throws SQLException;

	void addCompany(Company company) throws SQLException;

	void updateCompany(Company company)throws SQLException;

	void deleteCompany(int id)throws SQLException;

	ArrayList<Company> getAllCompanies()throws SQLException;
	
	Company getOneCompany(int id)throws SQLException, noSuchCompanyException;
	

	
}

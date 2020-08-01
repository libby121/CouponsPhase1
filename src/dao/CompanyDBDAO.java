package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import exceptions.noSuchCompanyException;
import beans.Company;


public class CompanyDBDAO implements CompanyDAO {

	private ConnectionPool pool = ConnectionPool.getInstance();

	@Override
	public void addCompany(Company company) throws SQLException {
		Connection con = pool.getConnection();
		try {
			PreparedStatement stmt = con
					.prepareStatement("insert into companies(name, email,password)values(?,?,?)");
			stmt.setString(1, company.getName());
			stmt.setString(2, company.getEmail());
			stmt.setString(3, company.getPassword());

			stmt.execute();

		} finally {
			pool.restoreConnection(con);
		}

	}

	/**
	 * Company' name and id cannot be updated.
	 */
	@Override
	public void updateCompany(Company company) throws SQLException {
		Connection con = pool.getConnection();
		try {
			PreparedStatement stmt = con
					.prepareStatement("update companies set email=?,password=?,balance=? where id="
							+ company.getId());

			stmt.setString(1, company.getEmail());
			stmt.setString(2, company.getPassword());
			stmt.setDouble(3, company.getBalance());

			stmt.execute();
		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public void deleteCompany(int id) throws SQLException {
		Connection con = pool.getConnection();
		try {
			PreparedStatement stmt = con
					.prepareStatement("delete from companies where id=" + id);
			stmt.execute();
		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public ArrayList<Company> getAllCompanies() throws SQLException {
		Connection con = pool.getConnection();
		try {
			ArrayList<Company> companies = new ArrayList<Company>();
			PreparedStatement stmt = con
					.prepareStatement("select * from companies");
			ResultSet et = stmt.executeQuery();
			while (et.next()) {
				companies
				.add(new Company(et.getInt("id"), et.getString("name"),
						et.getString("email"),
						et.getString("password"), et
						.getDouble("balance")));

			}

			return companies;

		} finally {
			pool.restoreConnection(con);
		}

	}

	@Override
	public Company getOneCompany(int id) throws SQLException,
	noSuchCompanyException {
		Connection con = pool.getConnection();
		try {
			PreparedStatement stmt = con
					.prepareStatement("select * from companies where id=" + id);
			ResultSet et = stmt.executeQuery();
			if (et.last()) {
				Company company = new Company(id, et.getString("name"),
						et.getString("email"), et.getString("password"),
						et.getDouble("balance"));

				return company;

			} else
				throw new noSuchCompanyException();

		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public boolean isCompanyExists(String email, String password)
			throws SQLException {
		Connection con = pool.getConnection();
		try {
			PreparedStatement stmt = con
					.prepareStatement("select * from companies");
			ResultSet et = stmt.executeQuery();
			while (et.next()) {
				if (et.getString("email").equals(email)
						&& et.getString("password").equals(password))
					return true;

			}

		} finally {
			pool.restoreConnection(con);

		}
		return false;

	}
}

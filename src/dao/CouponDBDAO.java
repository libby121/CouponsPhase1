package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import exceptions.NoSuchCouponsCategoryException;
import beans.Category;
import beans.Coupon;


public class CouponDBDAO implements CouponDAO {

	private ConnectionPool pool = ConnectionPool.getInstance();

	@Override
	public void addCoupon(Coupon coupon) throws SQLException {
		Connection con = pool.getConnection();
		try {
			PreparedStatement stmt = con
					.prepareStatement("insert into coupons (company_id,category_id,title,description,start_date,end_date,amount,price,image,discount_status)values(?,?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1, coupon.getCompanyID());
			stmt.setInt(2, coupon.getCategory().ordinal() + 1);
			stmt.setString(3, coupon.getTitle());
			stmt.setString(4, coupon.getDescription());
			stmt.setDate(5, coupon.getStartDate());
			stmt.setDate(6, coupon.getEndDate());
			stmt.setInt(7, coupon.getAmount());
			stmt.setDouble(8, coupon.getPrice());
			stmt.setString(9, coupon.getImage());
			stmt.setInt(10, coupon.isSalePrice()==true?1:0);

			stmt.execute();

		} finally {
			pool.restoreConnection(con);
		}
	}
	/**
	 * UpdateCoupon won't allow changing company id.
	 */

	@Override
	public void updateCoupon(Coupon coupon) throws SQLException {
		Connection con = pool.getConnection();
		try {
			String query="update coupons set category_id=?, title=?,description=?,start_date=?,end_date=?,amount=?,price=?,image=?,discount_status=? where id=?";
			PreparedStatement stmt = con
					.prepareStatement(query);
			
			stmt.setInt(1, coupon.getCategory().ordinal() + 1);
			stmt.setString(2, coupon.getTitle());
			stmt.setString(3, coupon.getDescription());
			stmt.setDate(4, coupon.getStartDate());
			stmt.setDate(5, coupon.getEndDate());
			stmt.setInt(6, coupon.getAmount());
			stmt.setDouble(7, coupon.getPrice());
			stmt.setString(8, coupon.getImage());
			
			stmt.setInt(9, coupon.isSalePrice()==true?1:0);
			stmt.setInt(10, coupon.getId());

			stmt.execute();
		} finally {
			pool.restoreConnection(con);
		}

	}

	@Override
	public void deleteCoupon(int id) throws SQLException {
		Connection con = pool.getConnection();
		try {
			PreparedStatement stmt = con
					.prepareStatement("delete from coupons where id= " + id);
			stmt.execute();


		} finally {
			pool.restoreConnection(con);
		}

	}

	@Override
	public ArrayList<Coupon> getAllcoupons() throws SQLException {
		Connection con = pool.getConnection();
		try {
			ArrayList<Coupon> coupns = new ArrayList<Coupon>();
			PreparedStatement stmt = con
					.prepareStatement("select * from coupons join Categories on coupons.category_id=categories.id ");
			ResultSet et = stmt.executeQuery();
			while (et.next()) {
				
				coupns.add(new Coupon(et.getInt("id"), et.getInt("COMPANY_ID"),
						Category.values()[et.getInt("CATEGORY_ID") - 1], et
								.getString("title"), et
								.getString("description"), et
								.getDate("start_date"), et.getDate("END_DATE"),
						et.getInt("amount"), et.getDouble("price"), et
								.getString("image"),et.getInt("discount_status")==1?true:false));
			}
			 
			return coupns;

		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public Coupon getOneCoupn(int id) throws SQLException {
		Connection con = pool.getConnection();
		try {

			PreparedStatement stmt = con
					.prepareStatement("select * from coupons where id=" + id);
			ResultSet et = stmt.executeQuery();
			if (et.last()) {
				Coupon coup = new Coupon(et.getInt("id"),
						et.getInt("company_id"),
						Category.values()[et.getInt("category_id") - 1],
						et.getString("title"), et.getString("description"),
						et.getDate("start_date"), et.getDate("end_date"),
						et.getInt("amount"), et.getDouble("price"),
						et.getString("image"),et.getInt("discount_status")==1?true:false);
				return coup;

			}

		} finally {
			pool.restoreConnection(con);
		}
		return null;

	}

	@Override
	public void addCouponPurchase(int CustomerId, int CouponId)
			throws SQLException {
		Connection con = pool.getConnection();
		try {
			PreparedStatement stmt = con
					.prepareStatement("insert into customers_vs_coupons (customer_ID,coupon_ID) values(?,?) ");
			stmt.setInt(1, CustomerId);
			stmt.setInt(2, CouponId);
			stmt.execute();
		} finally {
			pool.restoreConnection(con);
		}

	}



	public ArrayList<Coupon> getCouponsByCompanyId(int CompanyId)
			throws SQLException {
		Connection con = pool.getConnection();
		try {
			PreparedStatement stmt = con
					.prepareStatement("select * from coupons where company_Id="+CompanyId);
						
			ResultSet st = stmt.executeQuery();
			ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while (st.next()) {

				coupons.add(new Coupon(st.getInt("ID"),
						st.getInt("company_Id"), Category.values()[st.getInt("category_id")-1], st.getString("title"), st
								.getString("description"), st
								.getDate("start_date"), st.getDate("end_date"),
						st.getInt("amount"), st.getDouble("price"), st
								.getString("image"),st.getInt("discount_status")==1?true:false));
			}

			return coupons;

		} finally {
			pool.restoreConnection(con);
		}

	}

	public ArrayList<Coupon>getCompanyCouponsByCategory(Category category, int id) throws SQLException, NoSuchCouponsCategoryException{//from all companies
		Connection con=pool.getConnection();
		try{
			PreparedStatement stmt=con.prepareStatement("select * from coupons where company_id="+id+" and category_id="+(category.ordinal()+1));
			ResultSet et =stmt.executeQuery();
			ArrayList<Coupon>coupons=new ArrayList<Coupon>();
			/**
			 * If the resultSet is empty then no coupon was found with such category. 
			 */
			if(!et.isBeforeFirst())throw new NoSuchCouponsCategoryException();
			
			 while(et.next()){
				coupons.add(new Coupon(et.getInt("id"), et.getInt("company_id"), Category.values()[et.getInt("category_id")-1], et.getString("title"), et.getString("description"), et.getDate("start_Date"), et.getDate("end_Date"), et.getInt("amount"),et.getDouble("price"), et.getString("image"),et.getInt("discount_status")==1?true:false));
				
			}
			
			 return coupons;
		}finally{pool.restoreConnection(con);}
	}
	
	public ArrayList<Coupon> getCouponsByCustomerId(int customerId)
			throws SQLException {
		Connection con = pool.getConnection();
		try {
			PreparedStatement stmt = con
					.prepareStatement("select * from coupons join customers_vs_coupons on coupons.id=customers_vs_coupons.coupon_ID where Customer_ID="
							+ customerId);
			ResultSet st = stmt.executeQuery();
			ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while (st.next()) {
				coupons.add(new Coupon(st.getInt("ID"),
						st.getInt("company_Id"), Category.values()[st
								.getInt("category_id") - 1], st
								.getString("title"), st
								.getString("description"), st
								.getDate("start_date"), st.getDate("end_date"),
						st.getInt("amount"), st.getDouble("price"), st
								.getString("image"),st.getInt("discount_status")==1?true:false));

			}
			return coupons;
		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public void deleteCouponPurchase(int CustomerId, int CouponId)
			throws SQLException {
		Connection con = pool.getConnection();
		try{
		PreparedStatement stmt = con
				.prepareStatement("delete from customers_vs_coupons where customer_Id="
						+ CustomerId + " and Coupon_Id=" + CouponId);
		stmt.execute();
	}finally{pool.restoreConnection(con);

}}
	
	/**
	 * An additional method for deletion of coupon purchase only by couponId. Will be used in jobThread class
	 * and also in the AdminFacade and CompanyFacade.
	 */
	@Override
	public void deleteCouponPurchase(int couponId) throws SQLException{	
	Connection con=pool.getConnection();
	try{
		PreparedStatement stmt=con.prepareStatement("delete from customers_vs_coupons where coupon_ID="+couponId);
		stmt.execute();
	}finally{pool.restoreConnection(con);}
	}
	
	
	
	}

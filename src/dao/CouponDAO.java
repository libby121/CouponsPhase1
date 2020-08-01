package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import exceptions.NoSuchCouponsCategoryException;
import beans.Category;
import beans.Coupon;
/**
 * DAO interfaces represent the atomic commands and  basic communication with the database.
 * all methods are automatically public and are realized by DBDAO's.
 * @author μιαι
 *
 */
public interface CouponDAO {

	void addCoupon(Coupon coupon)throws SQLException;
	void updateCoupon(Coupon coupon)throws SQLException;
	void deleteCoupon(int id)throws SQLException;
	ArrayList<Coupon>getAllcoupons()throws SQLException;
	ArrayList<Coupon> getCouponsByCustomerId(int customerId)throws SQLException ;
	ArrayList<Coupon>getCompanyCouponsByCategory(Category category, int id) throws SQLException, NoSuchCouponsCategoryException;
	ArrayList<Coupon> getCouponsByCompanyId(int CompanyId)throws SQLException;
	Coupon getOneCoupn(int id)throws SQLException;
	void addCouponPurchase(int CustomerId,int CouponId)throws SQLException;
	void deleteCouponPurchase(int CustomerId,int CouponId)throws SQLException;
	void deleteCouponPurchase(int id) throws SQLException;
	
	
}

package facades;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import exceptions.CompanyDoesntExistException;
import exceptions.CouponExpiredException;
import exceptions.CouponNotFoundException;
import exceptions.NoSuchCouponsCategoryException;
import exceptions.couponOutOfStockException;
import exceptions.couponPurchaseDuplication;
import exceptions.maxPriceException;
import exceptions.noSuchCompanyException;
import exceptions.unchangableCompanyIdException;
import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;

public class CustomerFacade extends Facade {

	private int id;

	public CustomerFacade() {

	}

	@Override
	public boolean login(String email, String password) throws SQLException,
			CompanyDoesntExistException {
		for (Customer cust : custDBD.getAllcustomers()) {
			if (cust.getEmail().equalsIgnoreCase(email)
					&& cust.getPassword().equals(password)) {
				id = cust.getId();
				return true;
			}
		}
		return false;
	}

	public void purchaseCoupon(Coupon coup) throws SQLException,
			couponPurchaseDuplication, couponOutOfStockException,
			CouponExpiredException, noSuchCompanyException,
			CouponNotFoundException {
		for (Coupon coupon : coupDBD.getCouponsByCustomerId(id)) {

			if (coupon.getId() == coup.getId())
				throw new couponPurchaseDuplication();
		}
		if (coup.getAmount() == 0)
			throw new couponOutOfStockException();
		Calendar cal = Calendar.getInstance();
		if (coup.getEndDate().before(new Date(cal.getTimeInMillis())))
			throw new CouponExpiredException();

		else
			coupDBD.addCouponPurchase(id, coup.getId());
		coup.setAmount(coup.getAmount() - 1);
		coupDBD.updateCoupon(coup);

		Company c = compDBD.getOneCompany(coup.getCompanyID());
		c.setBalance((c.getBalance() + coup.getPrice()));
		compDBD.updateCompany(c);

	}

	public ArrayList<Coupon> getCustomerCoupons() throws SQLException {

		return coupDBD.getCouponsByCustomerId(id);
	}

	public ArrayList<Coupon> getCustomerCoupons(Category category)
			throws SQLException, NoSuchCouponsCategoryException {

		ArrayList<Coupon> couponsByCategory = new ArrayList<Coupon>();
		for (Coupon coup : getCustomerCoupons()) {
			if (coupDBD.getOneCoupn(coup.getId()).getCategory()
					.equals(category)) {
				couponsByCategory.add(coupDBD.getOneCoupn(coup.getId()));
			}

		}
		if (couponsByCategory.isEmpty())
			throw new NoSuchCouponsCategoryException();
		else
			return couponsByCategory;
	}

	public ArrayList<Coupon> getCustomerCoupons(double maxPrice)
			throws maxPriceException, SQLException {
		ArrayList<Coupon> couponsUpToMaxPrice = new ArrayList<Coupon>();
		for (Coupon coupon : getCustomerCoupons()) {
			if (coupon.getPrice() <= maxPrice)
				couponsUpToMaxPrice.add(coupDBD.getOneCoupn(coupon.getId()));

		}
		if (!couponsUpToMaxPrice.isEmpty())
			return couponsUpToMaxPrice;
		else
			throw new maxPriceException();
	}

	public Customer getCustomerDetails() throws SQLException {
		return custDBD.getOneCustmer(id);
	}
}
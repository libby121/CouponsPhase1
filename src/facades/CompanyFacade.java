package facades;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import exceptions.CompanyDoesntExistException;
import exceptions.CouponNotFoundException;
import exceptions.CouponTitleDupliactionException;
import exceptions.NoSuchCouponsCategoryException;
import exceptions.UnfitCouponDateException;
import exceptions.couponOfAnotherCompanyException;
import exceptions.deleteCouponException;
import exceptions.maxPriceException;
import exceptions.noSuchCompanyException;
import exceptions.unchangableCompanyIdException;
import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;

public class CompanyFacade extends Facade {

	private int id;

	public CompanyFacade(int id) {

		this.id = id;
	}

	public CompanyFacade() {

	}

	@Override
	public boolean login(String email, String password) throws SQLException,
			CompanyDoesntExistException {
		if (compDBD.isCompanyExists(email, password)) {
			for (Company comp : compDBD.getAllCompanies()) {
				if (comp.getEmail().equals(email)
						&& comp.getPassword().equals(password)) {
					id = comp.getId();
					return true;
				}

			}
		}
		return false;
	}

	public void addCoupon(Coupon coup) throws SQLException,
			CouponTitleDupliactionException, UnfitCouponDateException,
			couponOfAnotherCompanyException {
		Calendar cal = Calendar.getInstance();
		if (coup.getCompanyID() != id)
			throw new couponOfAnotherCompanyException();
		if(coup.getEndDate().before((new Date(cal.getTimeInMillis())))
				|| (cal.getTime().after(coup.getEndDate())))
			throw new UnfitCouponDateException();
		for (Coupon coupon : coupDBD.getCouponsByCompanyId(id)) {
			if ((coupon.getTitle().equals(coup.getTitle()))) {
				throw new CouponTitleDupliactionException();

			}

		}
		coupDBD.addCoupon(coup); 
	}
 
	public void updateCoupon(Coupon coup) throws SQLException,
			couponOfAnotherCompanyException {
		if (coup.getCompanyID() == id)
			coupDBD.updateCoupon(coup);

		else
			throw new couponOfAnotherCompanyException();

	}

	/**
	 * deleteCoupon requires a preceding method-deleteCouponPurchase, to assure
	 * that there will be no reference in another table to that coupon.
	 * An exception is thrown if a company tries to delete a coupon that 
	 * isn't in data base for some reason.
	 * 
	 * In case the company will be interested in the lost coupon, and since
	 * there's no documentation of it, I added below a method of writing to an
	 * archive file the coupon number and the date of deletion.I call this
	 * method from deleteCoupon method. Since I'll use it again in the thread
	 * section,I decided to create a separate method for this functionality.
	 * (For now there's only one file for all companies together). There's also
	 * a readDeletedCouponsFile method to read from the log. I use it in main.
	 * **File is attached to the project.
	 * 
	 * @param couponId
	 * @throws deleteCouponException
	 * @throws SQLException
	 * @throws CouponNotFoundException
	 * @throws IOException
	 * @throws couponOfAnotherCompanyException
	 *             -in case a company tries to delete another company's coupon.
	 */

	public void deleteCoupon(int couponId) throws deleteCouponException,
			SQLException, CouponNotFoundException, IOException,
			couponOfAnotherCompanyException {
		boolean flag = false;
		for (Coupon c : coupDBD.getAllcoupons()) {
			if (c.getId() == couponId)
				flag = true;

		}if (flag==false) throw new CouponNotFoundException();  

		if (coupDBD.getOneCoupn(couponId).getCompanyID() != id)
			throw new couponOfAnotherCompanyException();
		
		
		coupDBD.deleteCouponPurchase(couponId);
		coupDBD.deleteCoupon(couponId);
		coupnDeletionWriter(couponId);
	}

	public ArrayList<Coupon> getCompanyCoupons() throws SQLException {
		return coupDBD.getCouponsByCompanyId(id);
	}

	public void coupnDeletionWriter(int id) throws IOException, SQLException {
		Calendar Time = Calendar.getInstance();
		try (FileWriter writer = new FileWriter("CouponsArchive.txt", true)) {
			writer.write("\ncoupon:" + id + " was deleted on : "
					+ Time.getTime() + "\n");
		}
	}

	public void readDeletedCouponsFile(String file)
			throws FileNotFoundException, IOException {
		try (FileReader reader = new FileReader(file)) {
			int tav = reader.read();
			while (tav != -1) {
				System.out.print((char) tav);
				tav = reader.read();
			}

		}

	}

	public ArrayList<Coupon> getCompanyCoupons(Category category)
			throws SQLException, NoSuchCouponsCategoryException {

		return coupDBD.getCompanyCouponsByCategory(category, id);
	}

	public ArrayList<Coupon> getCompanyCoupons(double maxPrice)
			throws SQLException, maxPriceException {
		ArrayList<Coupon> maxPriceCoupons = new ArrayList<Coupon>();
		for (Coupon coupon : coupDBD.getCouponsByCompanyId(id)) {
			if (coupon.getPrice() <= maxPrice) {
				maxPriceCoupons.add(coupon);
			}

		}
		if (maxPriceCoupons.isEmpty()) {
			throw new maxPriceException();
		} else
			return maxPriceCoupons;

	}

	public Company getCompanyDetails() throws SQLException,
			noSuchCompanyException {
		Company comp = compDBD.getOneCompany(id);
		comp.setCoupons(coupDBD.getCouponsByCompanyId(id));
		return compDBD.getOneCompany(comp.getId());
	}

}

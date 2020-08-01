package Tests;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import dao.ConnectionPool;
import dao.CouponDAO;
import dao.CouponDBDAO;
import jobThread.CouponExpirationDailyJob;
import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import exceptions.AccessDeniedException;
import exceptions.AdminAccessDeniedException;
import exceptions.CompanyAccessDeniedException;
import exceptions.CompanyDoesntExistException;
import exceptions.CompanyExistsException;
import exceptions.CouponDeleteException;
import exceptions.CouponExpiredException;
import exceptions.CouponNotFoundException;
import exceptions.CouponTitleDupliactionException;
import exceptions.CustomerAccessDeniedException;
import exceptions.NoSuchCouponsCategoryException;
import exceptions.couponOutOfStockException;
import exceptions.couponPurchaseDuplication;
import exceptions.deleteCouponException;
import exceptions.maxPriceException;
import exceptions.noSuchCompanyException;
import exceptions.unchangableCompanyIdException;
import facades.AdminFacade;
import facades.CompanyFacade;
import facades.CustomerFacade;
import facades.Facade;
import Login.ClientType;
import Login.LoginManager;

public class Program {

	public static void main(String[] args) {

		testAll();

	}

	public static void testAll() {
		ConnectionPool pool = ConnectionPool.getInstance();
		CouponExpirationDailyJob thread = new CouponExpirationDailyJob();

		CouponDAO coupDBDAO = new CouponDBDAO();
		try {
			thread.start();

			CouponDBDAO coupDBD = new CouponDBDAO();

			LoginManager loginManager = LoginManager.getInstance();

			Facade facade = loginManager.login("com.admin@admin", "admain",
					ClientType.Administrator);
			if (facade instanceof AdminFacade) {

			}
			AdminFacade admin = ((AdminFacade) facade);

			//	admin.addCompany(new Company("IdealDeals%$",
			//	"Deals$@gmail.gifts",
			//		"3437%%$"));

			System.out.println(admin.getOneCompany(7));
			for (Company c : admin.getAllCompanies()) {
				System.out.println(c);

			}


			//	admin.addCustomer(new Customer("efrat", "pinhas",
			//		"efrat1973@.gmail.com", "effi9**"));

			//	admin.deleteCustomer(5);

			//admin.deleteCompany(2);

			Calendar cal = Calendar.getInstance();
			cal.set(2020, Calendar.FEBRUARY, 28);
			Calendar cal2 = Calendar.getInstance();
			cal2.set(2021, Calendar.FEBRUARY, 24);

			System.out.println("\n\n");
			System.out.println("-----------------company facade------------------------");
			System.out.println("\n\n");
			Facade facade2 = loginManager.login("coupons4life@hotmail.com",
					"1555##89#", ClientType.Company);


			if (facade2 instanceof CompanyFacade) {
				CompanyFacade companyFacade = ((CompanyFacade) facade2);
				//				companyFacade.addCoupon(new Coupon(3, Category.clothes,
				//						"rainbow T-shirts for boys age 3-9", "a pack of 8 rainbow T-shirts", new Date(cal
				//								.getTimeInMillis()), new Date(cal2
				//										.getTimeInMillis()), 190, 302.8,
				//										"colorful T-shirts.", false));



				System.out.println(companyFacade.getCompanyDetails());

				for (Coupon c : companyFacade.getCompanyCoupons(120)) {
					System.out.println(c);

				}

				companyFacade.updateCoupon(new Coupon(18,3,Category.pastries, "a family pastries box", "mixed sweet pastries box,a box of happinness ", new Date(cal
						.getTimeInMillis()), new Date(cal2
								.getTimeInMillis()), 890, 182.8,
								"bakery box", false));
				for (Coupon co :
					companyFacade.getCompanyCoupons(Category.clothes)) {
					System.out.println(co);
				}

				for (Coupon coupon : companyFacade.getCompanyCoupons(250)) {
					System.out.println(coupon);
				}


				//companyFacade.deleteCoupon(37);

				companyFacade.readDeletedCouponsFile("CouponsArchive.txt");

				System.out.println("\n\n");
				System.out.println("--------customer facade---------------------------------");
				System.out.println("\n\n");

				Facade facade3 = loginManager.login("batya@.hotmail.com",
						"aytab**", ClientType.Customer);
				if (facade3 instanceof CustomerFacade) {
					CustomerFacade customerFacade = ((CustomerFacade) facade3);
					//customerFacade.purchaseCoupon(coupDBD.getOneCoupn(37));
					for (Coupon coupon : customerFacade.getCustomerCoupons()) {
						System.out.println(coupon);
					}

					for (Coupon coup : customerFacade
							.getCustomerCoupons(Category.spa)) {
						System.out.println(coup);
					}

					for (Coupon cou : customerFacade.getCustomerCoupons(1110.5)) {
						System.out.println(cou);
					}
					System.out.println(customerFacade.getCustomerDetails());

				}}}


		catch (Exception e) {
			System.err.println(e.getMessage());
		}

		finally {
			/**
			 * The JobThread is interrupted at the end of the program and so it will all be stopped and closed.
			 * Before stopping the JobThread, the main thread and program are being delayed for one second so that everything will be printed
			 * properly to console.
			 */
 
			try {
				Thread.sleep(1000 );
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			thread.JobStop();



			pool.closeAllConnections();
		}

	}
}

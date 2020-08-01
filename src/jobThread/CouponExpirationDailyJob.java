package jobThread;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import beans.Coupon;
import dao.CouponDAO;
import dao.CouponDBDAO;
import facades.CompanyFacade;
/**
 * ExpriationDailyJob thread with extra features.
 * The thread eliminates coupons after comparing their endDate with the current time.
 * The comparison requires the conversion from Calendar of java.util to SQL. 
 * 
 * Only when testing on particular hours the thread deletes the coupons of the current day- which 
 * are not yet expired. Therefore, for extra precision, I added the comparison of the day, month and
 * year of the endDate with the current day, month and year.
 * 
 * I added an extra loop to check about the coupon's amount. a discount of 20% off is announced 
 * when the amount is between 10-100. An after-sale coupon is marked, and so each coupon 
 * will get a discount only once.
 *  
 * @author μιαι
 *
 */
public class CouponExpirationDailyJob extends Thread {

	private CouponDAO coupDAO = new CouponDBDAO();// downcasting
	private boolean quit;
	public CouponExpirationDailyJob() {
		super();
	}
	private CompanyFacade compFacde=new CompanyFacade();


	public void run() {
		while (!quit) {
			Calendar time = Calendar.getInstance();
			int day=time.get(Calendar.DATE);
			int month=time.get(Calendar.MONTH);
			int year=time.get(Calendar.YEAR);


			try {
				for (Coupon coup : coupDAO.getAllcoupons()) {
					Calendar cal=Calendar.getInstance();
					cal.setTime(coup.getEndDate());

					int coupday=cal.get(Calendar.DATE);
					int coupmonth=cal.get(Calendar.MONTH);
					int coupyear=cal.get(Calendar.YEAR);

					if((coup.getEndDate()).before((new Date(time.getTimeInMillis())))
							&&
							((!(coupday==day)&&(coupmonth==month)&&(coupyear==year)))){

System.out.println("teeeeeessssstttt");
						coupDAO.deleteCouponPurchase(coup.getId());
						coupDAO.deleteCoupon(coup.getId());



						System.out.println("coupon " + coup.getId()
								+ " has expired and deleted ");



						try {
							compFacde.coupnDeletionWriter(coup.getId());
						} catch (IOException e) {System.out.println(
								e.getMessage());
						}


					}
					else if ((coup.getAmount() <= 100 && coup.getAmount()>10)
							&& 
							(coup.isSalePrice()==false)) {

						coup.setPrice(coup.getPrice() * 0.8);

						coup.setIsSalePrice(true);
						coupDAO.updateCoupon(coup);
						System.out
						.println("**limited time sale** 20% off on coupon number "
								+ coup.getId()+" ** "+coup.getTitle()+"**  of company: "+coup.getCompanyID());
					}
					
				} }catch (SQLException e) {
					e.printStackTrace();
				}
			try {
				Thread.sleep(86_400_000 );
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

	}



	public void JobStop() {
		quit = true;
		interrupt();
		System.out.println("interapted");
		//		try {
		//			join();//after the thread ends, continue the main. add some delay
		//		} catch (InterruptedException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
	}
}

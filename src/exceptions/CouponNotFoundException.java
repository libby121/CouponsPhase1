package exceptions;

public class CouponNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponNotFoundException(){
		super("Coupon was not found, please enter a valid coupon Id");
	}
}

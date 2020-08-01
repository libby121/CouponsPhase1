package exceptions;

public class CouponExpiredException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponExpiredException(){
		super("You cannot purchase this coupon since current date is after expiration date/coupon expires today ");
	}

}

package exceptions;

public class CouponTitleDupliactionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponTitleDupliactionException(){
		super("coupon title already exists!");
	}

}

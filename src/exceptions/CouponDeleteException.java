package exceptions;

public class CouponDeleteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponDeleteException(){
		super("please enter a valid coupon's id");
	}
}

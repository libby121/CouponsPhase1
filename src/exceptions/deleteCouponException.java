package exceptions;

public class deleteCouponException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public deleteCouponException(){
	super("action denied. please enter a valid coupon's id");
}
}

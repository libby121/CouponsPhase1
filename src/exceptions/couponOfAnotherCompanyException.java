package exceptions;

public class couponOfAnotherCompanyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public couponOfAnotherCompanyException(){
		super("You cannot delete, add or update a coupon of another company/ an expired coupon. please enter a valid coupon id");
	}
}

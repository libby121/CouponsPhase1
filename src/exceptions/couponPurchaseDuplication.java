package exceptions;

public class couponPurchaseDuplication extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public couponPurchaseDuplication(){
		super("coupon cannot be purchased twice!");
	}
}

package exceptions;

public class couponOutOfStockException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public couponOutOfStockException(){
		super("this coupon is currently out of stock!");
	}
}

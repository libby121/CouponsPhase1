package exceptions;

public class CustomerExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerExistsException(){
		super("a customer is already registered by this email. please insert another email address");
	}
}

package exceptions;

public class unchangableCompanyIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6636879976974357427L;

	public unchangableCompanyIdException(){
		super("comapny id is unchangable!");
	}
	
}

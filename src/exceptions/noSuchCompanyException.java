package exceptions;

public class noSuchCompanyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public noSuchCompanyException(){
		super("no company founed. Please enter a valid id");
	}
}

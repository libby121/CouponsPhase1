package exceptions;

public class CompanyAccessDeniedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CompanyAccessDeniedException(){
		super("access denied to company member client! wrong email or password");
	}
}

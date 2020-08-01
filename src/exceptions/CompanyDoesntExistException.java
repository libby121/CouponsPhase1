package exceptions;

public class CompanyDoesntExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public	CompanyDoesntExistException(){//public ctor?
		super("no such company! ");
	}

}
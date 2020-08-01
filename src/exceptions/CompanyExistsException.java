package exceptions;

public class CompanyExistsException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CompanyExistsException(){
		super("company already exists in dataBase");//should i print here?
		
	}

}

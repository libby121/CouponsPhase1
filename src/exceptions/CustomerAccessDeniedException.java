package exceptions;

public class CustomerAccessDeniedException extends Exception {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerAccessDeniedException(){ 
		super("access denied to customer client, wrong email or password");
	}

}

package exceptions;

public class AdminAccessDeniedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public AdminAccessDeniedException(){
	super("access denied to admin client, wrong password or email");
}

}


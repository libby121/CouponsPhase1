package Login;

import java.sql.SQLException;

import exceptions.AccessDeniedException;
import exceptions.AdminAccessDeniedException;
import exceptions.CompanyAccessDeniedException;
import exceptions.CompanyDoesntExistException;
import exceptions.CustomerAccessDeniedException;
import facades.AdminFacade;
import facades.CompanyFacade;
import facades.CustomerFacade;
import facades.Facade;

public class LoginManager {

	private static LoginManager instance=new LoginManager(); 

	private LoginManager(){ 

	}

	public static LoginManager getInstance() {
		return instance;
	}


	public Facade login(String email,String password, ClientType clientType) throws SQLException, AdminAccessDeniedException, CompanyDoesntExistException, CompanyAccessDeniedException, CustomerAccessDeniedException, AccessDeniedException{
		switch(clientType){
		case Administrator:
			AdminFacade adminFacade=new AdminFacade();
			if(adminFacade.login(email, password))

				return adminFacade;
			else throw new AdminAccessDeniedException();


		case Company:
			CompanyFacade compFacade=new CompanyFacade();
			if(compFacade.login(email, password))
				return	compFacade;
			else throw new CompanyAccessDeniedException();

		case Customer:
			CustomerFacade custFacade=new CustomerFacade();

			if(custFacade.login(email, password))
				return custFacade;
			else throw new CustomerAccessDeniedException();

		default:

			throw new AccessDeniedException();


		}


	}
}

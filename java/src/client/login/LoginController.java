package client.login;

import client.base.*;
import client.data.PlayerInfo;
import client.misc.*;
import client.session.SessionManager;

import java.util.*;

import shared.communication.user.Login_Input;
import shared.communication.user.Login_Output;
import shared.communication.user.Register_Input;
import shared.communication.user.Register_Output;


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController, Observer {

	private IMessageView messageView;
	private IAction loginAction;
	
	/**
	 * LoginController constructor
	 * 
	 * @param view Login view
	 * @param messageView Message view (used to display error messages that occur during the login process)
	 */
	public LoginController(ILoginView view, IMessageView messageView) {

		super(view);
		
		this.messageView = messageView;
		
		SessionManager.instance().addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub
	}
	
	public ILoginView getLoginView() {
		
		return (ILoginView)super.getView();
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	
	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {
		
		loginAction = value;
	}
	
	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {
		
		return loginAction;
	}

	@Override
	public void start() {
		
		getLoginView().showModal();
	}

	@Override
	public void signIn() 
	{
		String username = getLoginView().getLoginUsername().trim();
		String password = getLoginView().getLoginPassword().trim();
		
		if(!canSignIn(username, password))
		{
			return;
		}
		
		Login_Output result = SessionManager.instance().getServer().login(new Login_Input(username, password));
		
		if(result.getResponse()==null)
		{
			messageView.setTitle("Sign In Error");
			messageView.setMessage("There was an error signing in. Please try again.");
			messageView.setController(this);
			messageView.showModal();
			return;
		}
		else if(result.getResponse().toUpperCase().equals("SUCCESS")) //todo warn on fail
		{
			// If log in succeeded
			getLoginView().closeModal();
			loginAction.execute();
		}
		else
		{
			messageView.setTitle("Sign In Error");
			messageView.setMessage("There was an error signing in. Please try again.");
			messageView.setController(this);
			messageView.showModal();
		}
	}

	@Override
	public void register() 
	{
		String username = getLoginView().getRegisterUsername().trim();
		String password = getLoginView().getRegisterPassword().trim();
		String passwordRepeat = getLoginView().getRegisterPasswordRepeat().trim();
		
		if(!canRegister(username,password,passwordRepeat)) //todo warn on fail
		{
			return;
		}
		
		Register_Output result = SessionManager.instance().getServer().register(new Register_Input(username, password));

		if(result.getResponse().toUpperCase().equals("SUCCESS"))
		{
			// If register succeeded
//			SessionManager.instance().setPlayerInfo(new PlayerInfo());
			getLoginView().closeModal();
			loginAction.execute();
		}
		else if(result.getResponse().toUpperCase().substring(0, 6).equals("FAILED"))
		{
			messageView.setTitle("Registration Error");
			messageView.setMessage("That username is already in use. Please try a different username.");
			messageView.setController(this);
			messageView.showModal();
		}
		else
		{
			messageView.setTitle("Registration Error");
			messageView.setMessage("There was an error with your registration. Please try again.");
			messageView.setController(this);
			messageView.showModal();
		}
		
		
	}
	
	private boolean canSignIn(String u, String p)
	{
		if(u==null || u.isEmpty())
		{
			messageView.setTitle("Sign In Error");
			messageView.setMessage("Please enter a username.");
			messageView.setController(this);
			messageView.showModal();
			return false;
		}
		else if(p==null || p.isEmpty())
		{
			messageView.setTitle("Sign In Error");
			messageView.setMessage("Please enter your password.");
			messageView.setController(this);
			messageView.showModal();
			return false;
		}
		return true;
	}
	
	private boolean canRegister(String u, String p, String p2)
	{
		if(u==null || u.isEmpty() || u.length() > 7 || u.length() < 3)
		{
			messageView.setTitle("Registration Error");
			messageView.setMessage("Username must be between 3 and 7 characters long.");
			messageView.setController(this);
			messageView.showModal();
			return false;
		}
		else if(p==null || p.isEmpty() || p.length() < 5)
		{
			messageView.setTitle("Registration Error");
			messageView.setMessage("Password must at least 5 characters long.");
			messageView.setController(this);
			messageView.showModal();
			return false;
		}
		else if(!p.equals(p2))
		{
			messageView.setTitle("Registration Error");
			messageView.setMessage("Passwords must match");
			messageView.setController(this);
			messageView.showModal();
			return false;
		}
		else if(!passwordIsAllowed(p))
		{
			messageView.setTitle("Registration Error");
			messageView.setMessage("Password can only contain alphanumeric characters, underscores, or hyphens.");
			messageView.setController(this);
			messageView.showModal();
			return false;
		}
		return true;
	}
	
	private boolean passwordIsAllowed(String p)
	{
		for(char c : p.toCharArray())
		{
			int a = (int) c; //ascii code
			if(!((a >= 47 && a <= 57) || (a >= 65 && a <= 90) || (a >= 97 && a <= 122) || (a==95) || (a==45)))
			{
				return false;
			}
		}
		return true;
	}

}


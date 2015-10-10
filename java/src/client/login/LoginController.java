package client.login;

import client.base.*;
import client.misc.*;
import client.session.SessionManager;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;

import shared.communication.user.Login_Input;
import shared.communication.user.Login_Output;
import shared.communication.user.Register_Input;
import shared.communication.user.Register_Output;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


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
		String username = getLoginView().getLoginUsername();
		String password = getLoginView().getLoginPassword();
		
		Login_Output result = SessionManager.instance().getServer().login(new Login_Input(username, password));

		if(result.getResponse().toUpperCase().equals("SUCCESS")) //todo warn on fail
		{
			// If log in succeeded
			getLoginView().closeModal();
			loginAction.execute();
		}
	}

	@Override
	public void register() 
	{
		String username = getLoginView().getRegisterUsername();
		String password = getLoginView().getRegisterPassword();
		String passwordRepeat = getLoginView().getRegisterPasswordRepeat();
		
		if(password.equals(passwordRepeat)) //todo warn on fail
		{
			Register_Output result = SessionManager.instance().getServer().register(new Register_Input(username, password));

			if(result.getResponse().toUpperCase() == "SUCCESS")
			{
				// If register succeeded
				getLoginView().closeModal();
				loginAction.execute();
			}
		}
	}

}


package shared.communication.game;

import java.io.Serializable;

import com.google.gson.Gson;

public class AddAI_Input implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7090784884090247646L;
	// used only for testing purposes
	private boolean catan_user;
	private boolean catan_game;
	
	private String AIType;
	
	public AddAI_Input()
	{
		
	}
	
	public AddAI_Input(String ai_type)
	{
		this.setAi_type(ai_type);
	}

	public String getAi_type() {
		return AIType;
	}

	public void setAi_type(String ai_type) {
		this.AIType = ai_type;
	}
	
	public String toJSON()
	{
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public boolean hasCatan_user() {
		return catan_user;
	}

	public void setCatan_user(boolean catan_user) {
		this.catan_user = catan_user;
	}

	public boolean hasCatan_game() {
		return catan_game;
	}

	public void setCatan_game(boolean catan_game) {
		this.catan_game = catan_game;
	}
}

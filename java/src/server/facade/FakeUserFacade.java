package server.facade;

import shared.communication.user.Login_Input;
import shared.communication.user.Register_Input;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class FakeUserFacade implements IUserFacade {

	@Override
	public JsonElement login(Login_Input params) {
		if (params.getUsername().equals("Ian")
				&& params.getPassword().equals("ian"))
			return new JsonPrimitive("Success");

		else
			return new JsonPrimitive(
					"Failed to login - bad username or password.");
	}

	@Override
	public JsonElement register(Register_Input params) {
		if (params.getUsername().equals("NewUser")
				&& params.getPassword().equals("newuser"))
			return new JsonPrimitive("Success");

		else
			return new JsonPrimitive(
					"Failed to register - someone already has that username.");
	}

}

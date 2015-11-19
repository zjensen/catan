package server.facade;

import server.main.ServerInvalidRequestException;
import shared.communication.user.Login_Input;
import shared.communication.user.Register_Input;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class FakeUserFacade implements IUserFacade {

	@Override
	public JsonElement login(Login_Input params) throws ServerInvalidRequestException {
		if (params.getUsername().equals("Ian")
				&& params.getPassword().equals("ian"))
			return new JsonPrimitive("Success");

		else
			return new JsonPrimitive(
					"Failed to login - bad username or password.");
	}

	@Override
	public JsonElement register(Register_Input params) throws ServerInvalidRequestException {
		if (params.getUsername().equals("NewUser") && params.getPassword().equals("newuser")){
			JsonObject result = new JsonObject();
			result.addProperty("id",5);
			result.addProperty("username",params.getUsername());
			result.addProperty("password",params.getPassword());
			return result;
		}

		else
			return new JsonPrimitive(
					"Failed to register user");
	}

}

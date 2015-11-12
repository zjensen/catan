package server.facade;

import com.google.gson.JsonElement;

import server.main.ServerInvalidRequestException;
import shared.communication.user.Login_Input;
import shared.communication.user.Register_Input;

public interface IUserFacade {

	public JsonElement login(Login_Input params) throws ServerInvalidRequestException;

	public JsonElement register(Register_Input params) throws ServerInvalidRequestException;
}

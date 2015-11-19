package server.facade;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import shared.communication.game.GameModel_Input;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class FakeGameFacade implements IGameFacade {

	@Override
	public JsonElement currentModel(GameModel_Input params, Integer gameId) {

		if (params.getVersion() == 0 && gameId.intValue() == Integer.valueOf(3)) {
			JsonElement primitive = new JsonPrimitive("true");
			return primitive;
		} else {
			return serializeTestClientModel();
		}
	}

	private JsonElement serializeTestClientModel() {
		StringBuilder result = new StringBuilder("");
		File file = new File("java/MovesFacadeTestJSON.txt");

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}
			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		String jsonString = result.toString();

		JsonElement jsonToParse = new JsonParser().parse(jsonString)
				.getAsJsonObject();

		return jsonToParse;
	}

}

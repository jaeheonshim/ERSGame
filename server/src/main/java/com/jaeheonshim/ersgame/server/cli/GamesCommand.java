package com.jaeheonshim.ersgame.server.cli;

import com.jaeheonshim.ersgame.game.GameStateManager;
import com.jaeheonshim.ersgame.game.model.GameState;
import com.jaeheonshim.ersgame.server.ERSServer;
import com.jaeheonshim.ersgame.server.GameManager;

import java.util.Map;

public class GamesCommand implements ICliCommand {
    @Override
    public String execute(String[] args, ERSServer server) {
        Map<String, GameState> gameStates = GameManager.getInstance().getGameStates();
        StringBuilder stringBuilder = new StringBuilder(gameStates.size() + " games:\n");
        for(GameState gameState : gameStates.values()) {
            stringBuilder.append(gameState.getGameCode() + ":\n");
            stringBuilder.append("\t" + gameState.getPlayerList().size + " players\n");
            stringBuilder.append("\t" + gameState.getGamePhase().toString() + "\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public String getKeyword() {
        return "games";
    }

    @Override
    public String getDescription() {
        return "Lists all games";
    }
}

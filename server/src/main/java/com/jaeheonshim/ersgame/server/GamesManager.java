package com.jaeheonshim.ersgame.server;

import com.jaeheonshim.ersgame.game.CardType;
import com.jaeheonshim.ersgame.game.GameState;
import com.jaeheonshim.ersgame.game.Player;
import com.jaeheonshim.ersgame.net.packet.GameStateUpdateEvent;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GamesManager {
    private Map<String, GameState> games = new HashMap<>();

    private static GamesManager instance;
    private ServerGameEventListener globalListener;

    private GamesManager(ERSServer server) {
        globalListener = new ServerGameEventListener(server);
    }

    public static void initialize(ERSServer ersServer) {
        instance = new GamesManager(ersServer);
    }

    public GameState newGame() {
        GameState gameState = new GameState();
        gameState.setGameEventListener(globalListener);
        String joinCode = "000000";
        gameState.setJoinCode(joinCode);

        games.put(joinCode, gameState);

        return gameState;
    }

    public boolean gameExists(String joinCode) {
        return games.containsKey(joinCode);
    }

    public GameState getGame(String joinCode) {
        return games.get(joinCode.trim().toLowerCase(Locale.ROOT));
    }

    public String generateJoinCode() {
        if(games.size() > 999999) {
            throw new RuntimeException("Games join code set is full");
        }

        String joincode;
        do {
            joincode = Integer.toString((int) (Math.random() * 1000000));
        } while (games.containsKey(joincode));

        return joincode;
    }

    public static GameStateUpdateEvent generateUpdateEvent(GameState gameState) {
        GameStateUpdateEvent updateEvent = new GameStateUpdateEvent();
        updateEvent.pile = gameState.getPile().toArray(new CardType[0]);
        updateEvent.currentTurnUUID = gameState.getCurrentTurn() == null ? null : gameState.getCurrentTurn().getUuid();
        updateEvent.players = new GameStateUpdateEvent.PlayerInfo[gameState.getPlayerMap().size()];
        updateEvent.joinCode = gameState.getJoinCode();

        int i = 0;
        for(Player player : gameState.getPlayers()) {
            GameStateUpdateEvent.PlayerInfo playerInfo = new GameStateUpdateEvent.PlayerInfo();
            playerInfo.uuid = player.getUuid();
            playerInfo.cardCount = player.getCards().size();
            playerInfo.name = player.getName();
            playerInfo.ordinal = player.getOrdinal();

            updateEvent.players[i++] = playerInfo;
        }

        return updateEvent;
    }

    public static GamesManager getInstance() {
        return instance;
    }
}

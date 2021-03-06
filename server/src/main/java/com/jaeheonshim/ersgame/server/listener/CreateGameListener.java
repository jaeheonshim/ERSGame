package com.jaeheonshim.ersgame.server.listener;

import com.jaeheonshim.ersgame.game.model.GameState;
import com.jaeheonshim.ersgame.net.model.UIMessageType;
import com.jaeheonshim.ersgame.net.packet.*;
import com.jaeheonshim.ersgame.server.ERSServer;
import com.jaeheonshim.ersgame.server.GameManager;
import com.jaeheonshim.ersgame.server.ServerPacketListener;
import org.java_websocket.WebSocket;

public class CreateGameListener extends ServerPacketListener {
    public CreateGameListener(ERSServer server) {
        super(server);
    }

    @Override
    public boolean receive(WebSocket socket, SocketPacket packet) {
        if(packet instanceof CreateGamePacket) {
            CreateGamePacket createGamePacket = ((CreateGamePacket) packet);

            String uuid = socket.getAttachment();
            String username = createGamePacket.username;

            try {
                GameState newGame = GameManager.getInstance().createNewGame(uuid, username);

                server.send(new UIMessagePacket(UIMessageType.SUCCESS, "New game created"), uuid);
                server.broadcast(new GameStatePacket(newGame), newGame);
            } catch(Exception e) {
                server.send(new UIMessagePacket(UIMessageType.ERROR, e.getMessage()), uuid);
            }
            return true;
        }

        return false;
    }
}

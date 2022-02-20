package com.jaeheonshim.ersgame.server.cli;

import com.jaeheonshim.ersgame.server.ERSServer;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketListener;

public class ClientsCommand implements ICliCommand {
    @Override
    public String execute(String[] args, ERSServer server) {
        StringBuilder resultBuilder = new StringBuilder(server.getConnections().size() + " connections:\n");
        for(WebSocket socket : server.getConnections()) {
            resultBuilder.append(socket.getAttachment() + " : " + socket.getRemoteSocketAddress().getAddress().toString());
            resultBuilder.append("\n");
        }

        return resultBuilder.toString();
    }

    @Override
    public String getKeyword() {
        return "clients";
    }

    @Override
    public String getDescription() {
        return "List all clients connected to server";
    }
}

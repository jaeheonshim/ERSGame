package com.jaeheonshim.ersgame.server.cli;

import com.jaeheonshim.ersgame.server.ERSServer;

import java.util.HashMap;
import java.util.Map;

public class CommandParser {
    private ERSServer server;
    private Map<String, ICliCommand> commands = new HashMap<>();

    public CommandParser(ERSServer server) {
        this.server = server;

        registerCommand(new ClientsCommand());
        registerCommand(new StatsCommand());
        registerCommand(new GcCommand());
        registerCommand(new GamesCommand());
    }

    private void registerCommand(ICliCommand command) {
        commands.put(command.getKeyword().toLowerCase().trim(), command);
    }

    public String getCommandResult(String lineInput) {
        lineInput = lineInput.trim().toLowerCase();
        if(!lineInput.startsWith("/")) return "";

        StringBuilder resultBuilder = new StringBuilder();

        String[] parts = lineInput.split(" ");
        String commandKeyword = parts[0].substring(1);
        ICliCommand command = commands.get(commandKeyword);
        if(command == null) {
            resultBuilder.append("Command '" + commandKeyword + "' not found.\n");
        } else {
            String[] args = new String[parts.length - 1];
            System.arraycopy(parts, 0, args, 0, parts.length - 1);
            resultBuilder.append(command.execute(args, server));
        }

        return resultBuilder.toString();
    }
}

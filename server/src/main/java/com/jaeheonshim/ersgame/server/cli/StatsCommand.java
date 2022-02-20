package com.jaeheonshim.ersgame.server.cli;

import com.jaeheonshim.ersgame.server.ERSServer;

public class StatsCommand implements ICliCommand {
    @Override
    public String execute(String[] args, ERSServer server) {
        StringBuilder stringBuilder = new StringBuilder("Server stats:\n");
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;
        stringBuilder.append(String.format("Total Memory: %.2fMB%n", totalMemory / 1e6));
        stringBuilder.append(String.format("Free Memory: %.2fMB%n", freeMemory / 1e6));
        stringBuilder.append(String.format("Used Memory: %.2fMB%n", usedMemory / 1e6));

        return stringBuilder.toString();
    }

    @Override
    public String getKeyword() {
        return "stats";
    }

    @Override
    public String getDescription() {
        return "Displays server stats";
    }
}

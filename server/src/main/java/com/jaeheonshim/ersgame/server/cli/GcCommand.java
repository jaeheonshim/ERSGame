package com.jaeheonshim.ersgame.server.cli;

import com.jaeheonshim.ersgame.server.ERSServer;

public class GcCommand implements ICliCommand {
    @Override
    public String execute(String[] args, ERSServer server) {
        System.gc();
        return "Requested JVM garbage collection\n";
    }

    @Override
    public String getKeyword() {
        return "gc";
    }

    @Override
    public String getDescription() {
        return "Request the JVM to run garbage collection";
    }
}

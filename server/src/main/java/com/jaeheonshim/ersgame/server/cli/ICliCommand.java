package com.jaeheonshim.ersgame.server.cli;

import com.jaeheonshim.ersgame.server.ERSServer;

public interface ICliCommand {
    String execute(String[] args, ERSServer server);
    String getKeyword();
    String getDescription();
}

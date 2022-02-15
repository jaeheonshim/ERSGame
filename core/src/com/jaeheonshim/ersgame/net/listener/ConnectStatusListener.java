package com.jaeheonshim.ersgame.net.listener;

import com.jaeheonshim.ersgame.net.ConnectionStatus;

public interface ConnectStatusListener {
    void onStatusChange(ConnectionStatus newStatus);
}
